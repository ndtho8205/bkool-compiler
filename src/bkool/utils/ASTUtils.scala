package bkool.utils

import scala.util.parsing.combinator.JavaTokenParsers

//For making AST from String of AST
abstract class Item

case class ItemList(items: List[Item]) extends Item {
    override def toString = "[" + items.mkString(",") + "]"
}

case class Ident(name: String) extends Item {
    override def toString = name
}

case class Operator(op: String) extends Item {
    override def toString = op
}

case class Term(name: String, args: List[Item]) extends Item {
    override def toString = name + "(" + args.mkString(",") + ")"
}

case class IntItem(value: IntLiteral) extends Item {
    override def toString = "" + value
}

case class FloatItem(value: FloatLiteral) extends Item {
    override def toString = "" + value
}

case class StringItem(value: StringLiteral) extends Item {
    override def toString = "" + value // TODO: Quotation
}

case class BoolItem(value: BooleanLiteral) extends Item {
    override def toString = "" + value // TODO: Quotation
}


class AstRebuild extends IntermediateBKOOLParser {
    def generate(str: String): AST = {
        val result = parseAll(item, str)
        //println(result.get)
        val ast = ASTGenerator.program(result.get)
        ast
    }
}

class IntermediateBKOOLParser extends JavaTokenParsers {
    def item: Parser[Item] = ("For(" ~> ident) ~ (":=" ~> item) ~ (";" ~ ident ~> ("<" | ">")) ~ item ~ (";" ~ ident ~ ("++" | "--") ~> item <~ ")") ^^ {
        case id ~ exp1 ~ op ~ exp2 ~ loop => Term("For", List(Ident(id), exp1, if (op == ">") Ident("false") else Ident("true"), exp2, loop))
    } |
        "[" ~> repsep(item, ",") <~ "]" ^^ {
            ItemList(_)
        } |
        ident ~ opt("(" ~> repsep(item, "," | ";") <~ opt(",") ~ ")") ^^ {
            case id ~ None      => Ident(id)
            case id ~ Some(lst) => Term(id, lst)
        } |
        floatingPointNumber ^^ { x =>
            if (x.contains("E") || x.contains("e") || x.contains(".")) FloatItem(FloatLiteral(x.toFloat))
            else IntItem(IntLiteral(x.toInt));
        } |
        rep1(stringLiteral) ^^ { lst => StringItem(StringLiteral(lst.mkString(""))) } |
        ("+" | "-" | "*" | "/" | "\\" | "%" | "^" | "==" | "!=" | ">=" | "<=" | ">" | "<" | "!" | "||" | "&&") ^^ { x =>
            Operator(x)
        }
}

object ASTGenerator {
    def typ(item: Item): Type = item match {
        case Ident(name)                                                       => name match {
            case "IntType"    => IntType
            case "FloatType"  => FloatType
            case "StringType" => StringType
            case "BoolType"   => BoolType
            case "VoidType"   => VoidType
            case _            => throw new IllegalStateException("Type Wrong")
        }
        case Term("ArrayType", List(ty, Term("IntLit", List(IntItem(value))))) => ArrayType(value, typ(ty))
        case Term("ClassType", List(value))                                    => ClassType(value.toString)
        case _                                                                 => throw new IllegalStateException("Type Wrong")
    }

    def expr(item: Item): Expr = item match {
        case Term("StringLit", List(StringItem(value)))              => value
        case Term("IntLit", List(IntItem(value)))                    => value
        case Term("FloatLit", List(FloatItem(value)))                => value
        case Term("BoolLit", List(Ident(boolVal)))                   => BooleanLiteral(boolVal.toBoolean)
        case Term("Null", _)                                         => NullLiteral
        case Term("Self", _)                                         => SelfLiteral
        case Term("CallExp", List(exp, Ident(name), ItemList(list))) => CallExpr(expr(exp), Id(name), list.map(a => expr(a)))
        case Term("NewExp", List(Ident(name), ItemList(list)))       => NewExpr(Id(name), list.map(a => expr(a)))
        case Term("ArrayCell", List(exp1, exp2))                     => ArrayCell(expr(exp1), expr(exp2))
        case Term("FieldAccess", List(exp1, Ident(name)))            => FieldAccess(expr(exp1), Id(name))
        case Term("UnaryOp", List(Operator(op), exp))                => UnaryOp(op, expr(exp))
        case Term("BinaryOp", List(Operator(op), left, right))       => BinaryOp(op, expr(left), expr(right))
        case Ident("self")                                           => SelfLiteral
        case Ident(name)                                             => Id(name)
        case _                                                       => throw new IllegalStateException("Expression Wrong")
    }

    def stmt(item: Item): Stmt = item match {
        case Term("Assign", List(left, right))                             => left match {
            case Ident(name)                                  => Assign(Id(name), expr(right))
            case Term("ArrayCell", List(exp1, exp2))          => Assign(ArrayCell(expr(exp1), expr(exp2)), expr(right))
            case Term("FieldAccess", List(exp1, Ident(name))) => Assign(FieldAccess(expr(exp1), Id(name)), expr(right))
            case _                                            => throw new IllegalStateException("Assign Wrong")
        }
        case Term("If", List(cond, pos, neg))                              => If(expr(cond), stmt(pos), Option(stmt(neg)))
        case Term("If", List(cond, pos))                                   => If(expr(cond), stmt(pos), None)
        case Term("For", List(Ident(name), exp1, Ident(flag), exp2, blck)) => For(name, expr(exp1), flag.toBoolean, expr(exp2), stmt(blck))
        case Term("Break", _)                                              => Break
        case Term("Continue", _)                                           => Continue
        case Term("Call", List(exp, Ident(name), ItemList(args)))          => Call(expr(exp), Id(name), args.map(expr _))
        case Term("Return", List(ret))                                     => Return(expr(ret)) // TODO check
        case Term("Block", List(ItemList(decls), ItemList(stmts)))         => Block(decls.map(b => declareInBlock(b)), stmts.map(b => stmt(b)))
        case _                                                             => throw new IllegalStateException("Statement Wrong")
    }

    def param(item: Item): ParamDecl = item match {
        case Term("param", List(Ident(name), ty)) => ParamDecl(Id(name), typ(ty))
        case _                                    => throw new IllegalStateException("Parameter Declaration Wrong")
    }

    def declareInBlock(item: Item): Decl = item match {
        case Term("VarDecl", List(Ident(name), ty))        => VarDecl(Id(name), typ(ty))
        case Term("ConstDecl", List(Ident(name), ty, exp)) => ConstDecl(Id(name), typ(ty), expr(exp))
        case _                                             => throw new IllegalStateException("Declaration In Block Wrong")
    }

    def declareInMember(item: Item): Decl = item match {
        case Term("VarDecl", List(Ident(name), ty))        => VarDecl(Id(name), typ(ty))
        case Term("ConstDecl", List(Ident(name), ty, exp)) => ConstDecl(Id(name), typ(ty), expr(exp))
        case _                                             => throw new IllegalStateException("Declaration In Member Wrong")
    }

    def siKind(item: Item): SIKind = item match {
        case Ident(name) => name match {
            case "Static"   => Static
            case "Instance" => Instance
            case _          => throw new IllegalStateException("SI Kind Wrong")
        }
        case _           => throw new IllegalStateException("SI Kind Wrong")
    }

    def declareMember(item: Item): MemDecl = item match {
        case Term("AttributeDecl", List(si, decl))                                 => AttributeDecl(siKind(si), declareInMember(decl))
        case Term("MethodDecl", List(Ident(name), si, ItemList(args), blck))       => MethodDecl(siKind(si), Id(name), args.map(a => param(a)), null, stmt(blck))
        case Term("MethodDecl", List(Ident(name), si, ItemList(args), rtyp, blck)) => MethodDecl(siKind(si), Id(name), args.map(a => param(a)), typ(rtyp), stmt(blck))

        case _ => throw new IllegalStateException("Member Declaration Wrong")
    }

    def declareClass(item: Item): ClassDecl = item match {
        case Term("ClassDecl", List(Ident(name), ItemList(args)))                => ClassDecl(Id(name), null, args.map(a => declareMember(a)))
        case Term("ClassDecl", List(Ident(name), Ident(parent), ItemList(args))) => ClassDecl(Id(name), Id(parent), args.map(a => declareMember(a)))
        case _                                                                   => throw new IllegalStateException("Class Declaration Wrong")
    }

    def program(item: Item): Program = item match {
        case Term("Program", List(ItemList(decs))) => Program(decs.map(a => declareClass(a)))
        case _                                     => throw new IllegalStateException("Program Wrong")
    }
}

