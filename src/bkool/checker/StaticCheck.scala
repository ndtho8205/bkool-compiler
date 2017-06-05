/*
 * Nguyen Duc Tho
 * MSSV: 1413817
 */

package bkool.checker

import bkool.utils._

import scala.collection.mutable

class StaticChecker(ast: AST) {
    def check() = {
        val symbolTable = new SymbolTable
        symbolTable.init

        val visitor = new GlobalVisitor
        visitor.visit(ast, symbolTable)

        val typeCheckVisitor = new TypeCheckVisitor
        typeCheckVisitor.visit(ast, symbolTable)
    }
}

/** ***************************************************************************
  * Visitors
  * ***************************************************************************
  */

class GlobalVisitor extends MyBaseVisitor {

    private var isAttribute: Boolean = false
    private var isMethodBlock: Boolean = false
    private var ifCount = 0
    private var forCount = 0
    private var blockCount = 0

    override def visitProgram(ast: Program, c: Context) = {
        ast.decl.foreach(_.accept(this, c))
        c.asInstanceOf[SymbolTable]
    }

    override def visitClassDecl(ast: ClassDecl, c: Context) = {
        val classSymbol = Symbol(
            ast.name.toString, Class, null,
            c.asInstanceOf[SymbolTable].treeName
        )
        c.asInstanceOf[SymbolTable].put(classSymbol)

        c.asInstanceOf[SymbolTable].treeName = classSymbol.url
        ast.decl.foreach(_.accept(this, c))
        c.asInstanceOf[SymbolTable].treeName = classSymbol.url.tail

        null
    }

    override def visitAttributeDecl(ast: AttributeDecl, c: Context) = {
        isAttribute = true
        val attrSymbol = ast.decl.accept(this, c).asInstanceOf[Symbol]
        isAttribute = false

        if (attrSymbol.id == c.asInstanceOf[SymbolTable].treeName.last)
            throw Redeclared(Attribute, attrSymbol.id)
        attrSymbol.isStatic = ast.kind == Static

        null
    }

    override def visitVarDecl(ast: VarDecl, c: Context) = {
        val varSymbol = Symbol(
            ast.variable.toString, if (isAttribute) Attribute else Variable, ast.varType,
            c.asInstanceOf[SymbolTable].treeName
        )
        c.asInstanceOf[SymbolTable].put(varSymbol)

        varSymbol
    }

    override def visitConstDecl(ast: ConstDecl, c: Context) = {
        val constSymbol = Symbol(
            ast.id.toString, if (isAttribute) Attribute else Constant, ast.constType,
            c.asInstanceOf[SymbolTable].treeName,
            isConstant = true
        )
        c.asInstanceOf[SymbolTable].put(constSymbol)

        constSymbol
    }

    override def visitMethodDecl(ast: MethodDecl, c: Context) = {
        val methodSymbol = Symbol(
            ast.name.toString,
            if (c.asInstanceOf[SymbolTable].treeName.head == ast.name.toString) SpecialMethod
            else Method,
            ast.returnType,
            c.asInstanceOf[SymbolTable].treeName,
            isStatic = ast.kind == Static
        )
        if (methodSymbol.kind == SpecialMethod && methodSymbol.dataType != null)
            throw Redeclared(Method, methodSymbol.id)
        c.asInstanceOf[SymbolTable].put(methodSymbol)

        c.asInstanceOf[SymbolTable].treeName = methodSymbol.url
        isMethodBlock = true
        ast.param.foreach(_.accept(this, c))
        ast.body.accept(this, c)
        isMethodBlock = false
        c.asInstanceOf[SymbolTable].treeName = methodSymbol.url.tail

        null
    }

    override def visitParamDecl(ast: ParamDecl, c: Context) = {
        val paramSymbol = Symbol(
            ast.id.toString, Parameter, ast.paramType,
            c.asInstanceOf[SymbolTable].treeName
        )
        c.asInstanceOf[SymbolTable].put(paramSymbol)

        null
    }

    override def visitIf(ast: If, c: Context) = {
        val ifSymbol = Symbol(
            "If" + ifCount.toString, KIf, null,
            c.asInstanceOf[SymbolTable].treeName
        )
        ifCount = ifCount + 1
        c.asInstanceOf[SymbolTable].put(ifSymbol)

        c.asInstanceOf[SymbolTable].treeName = ifSymbol.url
        ast.thenStmt.accept(this, c)
        ast.elseStmt match {
            case Some(stmt) => stmt.accept(this, c)
            case None       =>
        }
        c.asInstanceOf[SymbolTable].treeName = ifSymbol.url.tail

        null
    }

    override def visitFor(ast: For, c: Context) = {
        val forSymbol = Symbol(
            "For" + forCount.toString, KFor, null,
            c.asInstanceOf[SymbolTable].treeName
        )
        forCount = forCount + 1
        c.asInstanceOf[SymbolTable].put(forSymbol)

        c.asInstanceOf[SymbolTable].treeName = forSymbol.url
        isMethodBlock = true
        ast.loop.accept(this, c)
        isMethodBlock = false
        c.asInstanceOf[SymbolTable].treeName = forSymbol.url.tail

        null
    }

    override def visitBlock(ast: Block, c: Context) = {
        val blockSymbol = Symbol(
            "Block" + blockCount.toString, KBlock, null,
            c.asInstanceOf[SymbolTable].treeName
        )
        if (!isMethodBlock) {
            blockCount = blockCount + 1
            c.asInstanceOf[SymbolTable].put(blockSymbol)
            c.asInstanceOf[SymbolTable].treeName = blockSymbol.url
            ast.decl.foreach(_.accept(this, c))
            ast.stmt.foreach(_.accept(this, c))
            c.asInstanceOf[SymbolTable].treeName = blockSymbol.url.tail
        } else {
            isMethodBlock = false
            ast.decl.foreach(_.accept(this, c))
            ast.stmt.foreach(_.accept(this, c))
        }
        null
    }
}


class TypeCheckVisitor extends MyBaseVisitor with Utils {
    private var isMethodBlock: Boolean = false
    private var ifCount = 0
    private var forCount = 0
    private var blockCount = 0
    private var inLoop: Boolean = false
    private var memberAccess: Boolean = false


    override def visitProgram(ast: Program, c: Context) = {
        ast.decl.foreach(_.accept(this, c))
        null
    }

    override def visitClassDecl(ast: ClassDecl, c: Context) = {
        if (ast.parent != null) {
            getClass(c.asInstanceOf[SymbolTable], ast.parent.toString)
            Symbol.extendsTable(ast.name.toString) = ast.parent.toString
        }

        c.asInstanceOf[SymbolTable].treeName = ast.name.toString :: c.asInstanceOf[SymbolTable].treeName
        ast.decl.foreach(_.accept(this, c))
        c.asInstanceOf[SymbolTable].treeName = c.asInstanceOf[SymbolTable].treeName.tail

        null
    }

    override def visitAttributeDecl(ast: AttributeDecl, c: Context) = ast.decl.accept(this, c)

    override def visitVarDecl(ast: VarDecl, c: Context) = ast.varType.accept(this, c)

    override def visitConstDecl(ast: ConstDecl, c: Context) = {
        val lhs = ast.constType.accept(this, c).asInstanceOf[ExtendType]
        val rhs = ast.const.accept(this, c).asInstanceOf[ExtendType]

        if (!checkType(lhs.dataType, rhs.dataType))
            throw TypeMismatchInConstant(ast)
        if (!rhs.isConstant)
            throw NotConstantExpression(ast.const)

        null
    }

    override def visitMethodDecl(ast: MethodDecl, c: Context) = {
        if (ast.returnType != null) ast.returnType.accept(this, c)
        c.asInstanceOf[SymbolTable].treeName = ast.name.toString :: c.asInstanceOf[SymbolTable].treeName
        isMethodBlock = true
        ast.param.foreach(_.accept(this, c))
        ast.body.accept(this, c)
        isMethodBlock = false
        c.asInstanceOf[SymbolTable].treeName = c.asInstanceOf[SymbolTable].treeName.tail

        if (ast.returnType != VoidType && ast.returnType != null && !checkReturn(ast, ast.body))
            throw MethodNotReturn(ast.name.toString)
        null
    }

    override def visitParamDecl(ast: ParamDecl, c: Context) = ast.paramType.accept(this, c)

    override def visitIf(ast: If, c: Context) = {
        val exprType = ast.expr.accept(this, c).asInstanceOf[ExtendType]
        exprType.dataType match {
            case BoolType =>
                c.asInstanceOf[SymbolTable].treeName = ("If" + ifCount.toString) :: c.asInstanceOf[SymbolTable].treeName
                ifCount = ifCount + 1
                ast.thenStmt.accept(this, c)
                ast.elseStmt match {
                    case Some(stmt) => stmt.accept(this, c)
                    case None       =>
                }
                c.asInstanceOf[SymbolTable].treeName = c.asInstanceOf[SymbolTable].treeName.tail
                null
            case _        =>
                throw TypeMismatchInStatement(ast)
        }
    }

    override def visitFor(ast: For, c: Context) = {
        val indexSymbol = getIdentifier(c.asInstanceOf[SymbolTable], ast.idx)
        indexSymbol.kind match {
            case Variable | Constant =>
                if (!isInt(indexSymbol.dataType))
                    throw TypeMismatchInStatement(ast)
                if (indexSymbol.isConstant)
                    throw CannotAssignToConstant(Assign(Id(ast.idx), ast.expr1))

                val expr1Type = ast.expr1.accept(this, c).asInstanceOf[ExtendType]
                val expr2Type = ast.expr2.accept(this, c).asInstanceOf[ExtendType]

                if (!isInt(expr1Type.dataType) || !isInt(expr2Type.dataType))
                    throw TypeMismatchInStatement(ast)

                //TODO: FOR trong FOR break

                c.asInstanceOf[SymbolTable].treeName = ("For" + forCount.toString) :: c.asInstanceOf[SymbolTable].treeName
                forCount = forCount + 1
                isMethodBlock = true
                inLoop = true
                ast.loop.accept(this, c)
                inLoop = false
                isMethodBlock = false
                c.asInstanceOf[SymbolTable].treeName = c.asInstanceOf[SymbolTable].treeName.tail
            case _                   =>
                throw Undeclared(Identifier, ast.idx)
        }
        null
    }

    override def visitBlock(ast: Block, c: Context) = {
        if (!isMethodBlock) {
            c.asInstanceOf[SymbolTable].treeName = ("Block" + blockCount.toString) :: c.asInstanceOf[SymbolTable].treeName
            blockCount = blockCount + 1
            ast.decl.foreach(_.accept(this, c))
            ast.stmt.foreach(_.accept(this, c))
            c.asInstanceOf[SymbolTable].treeName = c.asInstanceOf[SymbolTable].treeName.tail
        } else {
            isMethodBlock = false
            ast.decl.foreach(_.accept(this, c))
            ast.stmt.foreach(_.accept(this, c))
        }
        null
    }

    override def visitBreak(ast: Break.type, c: Context) = if (!inLoop) throw BreakNotInLoop(0) else null

    override def visitContinue(ast: Continue.type, c: Context) = if (!inLoop) throw ContinueNotInLoop(0) else null

    override def visitCall(ast: Call, c: Context) = {
        if (ast.parent.isInstanceOf[Id]) memberAccess = true
        val callerType = ast.parent.accept(this, c).asInstanceOf[ExtendType]
        callerType.dataType match {
            case ClassType(className) =>
                val methodSymbol = getClassMember(c.asInstanceOf[SymbolTable], className, ast.method.toString, Method)
                methodSymbol.dataType match {
                    case VoidType =>
                        val lhs = c.asInstanceOf[SymbolTable].table.filter(p => p._1.endsWith(methodSymbol.toString) && p._2.kind == Parameter)
                            .foldLeft(List[Symbol]())((L, p) => p._2 :: L).sortBy(symbol => symbol.index)
                        val rhs = ast.params.map(_.accept(this, c).asInstanceOf[ExtendType])
                        if (lhs.size == rhs.size) {
                            lhs.zip(rhs).foreach(p =>
                                if (!checkType(p._1.dataType, p._2.dataType)) throw TypeMismatchInStatement(ast)
                            )
                            null
                        } else throw TypeMismatchInStatement(ast)
                    case _        => throw TypeMismatchInStatement(ast)
                }
            case _                    => throw TypeMismatchInStatement(ast)
        }
    }

    override def visitAssign(ast: Assign, c: Context) = {
        val lhs = ast.leftHandSide.accept(this, c).asInstanceOf[ExtendType]
        val rhs = ast.expr.accept(this, c).asInstanceOf[ExtendType]
        if (lhs.isConstant) throw CannotAssignToConstant(ast)
        if (!checkType(lhs.dataType, rhs.dataType))
            throw TypeMismatchInStatement(ast)
        null
    }

    override def visitReturn(ast: Return, c: Context) = {
        val rhs = ast.expr.accept(this, c).asInstanceOf[ExtendType]
        val lhs = getClassMember(
            c.asInstanceOf[SymbolTable],
            c.asInstanceOf[SymbolTable].treeName.last,
            c.asInstanceOf[SymbolTable].treeName.init.last,
            Method
        )
        if (!checkType(lhs.dataType, rhs.dataType))
            throw TypeMismatchInStatement(ast)

        null
    }

    override def visitBinaryOp(ast: BinaryOp, c: Context) = {
        val leftType = ast.left.accept(this, c).asInstanceOf[ExtendType]
        val rightType = ast.right.accept(this, c).asInstanceOf[ExtendType]
        val isConstant = leftType.isConstant && rightType.isConstant

        ast.op match {
            case "+" | "-" | "*"         =>
                if (isNumber(leftType.dataType) && isNumber(rightType.dataType))
                    if (isFloat(leftType.dataType) || isFloat(rightType.dataType))
                        ExtendType(FloatType, isConstant)
                    else
                        ExtendType(IntType, isConstant)
                else throw TypeMismatchInExpression(ast)
            case "/"                     =>
                if (isNumber(leftType.dataType) && isNumber(rightType.dataType))
                    ExtendType(FloatType, isConstant)
                else throw TypeMismatchInExpression(ast)
            case "\\" | "%"              =>
                if (isInt(leftType.dataType) && isInt(rightType.dataType))
                    ExtendType(IntType, isConstant)
                else throw TypeMismatchInExpression(ast)
            case "&&" | "||"             =>
                if (isBool(leftType.dataType) && isBool(rightType.dataType))
                    ExtendType(BoolType, isConstant)
                else throw TypeMismatchInExpression(ast)
            case "==" | "!="             =>
                if ((isInt(leftType.dataType) || isBool(leftType.dataType)) && leftType.dataType == rightType.dataType)
                    ExtendType(BoolType, isConstant)
                else throw TypeMismatchInExpression(ast)
            case ">" | "<" | ">=" | "<=" =>
                if (isNumber(leftType.dataType) && isNumber(rightType.dataType))
                    ExtendType(BoolType, isConstant)
                else throw TypeMismatchInExpression(ast)
            case "^"                     =>
                if (isString(leftType.dataType) && isString(rightType.dataType))
                    ExtendType(StringType, isConstant)
                else throw TypeMismatchInExpression(ast)
        }
    }

    override def visitUnaryOp(ast: UnaryOp, c: Context) = {
        val exprType = ast.body.accept(this, c).asInstanceOf[ExtendType]

        ast.op match {
            case "+" | "-" =>
                if (isNumber(exprType.dataType))
                    ExtendType(exprType.dataType, exprType.isConstant)
                else throw TypeMismatchInExpression(ast)
            case "!"       =>
                if (isBool(exprType.dataType))
                    ExtendType(exprType.dataType, exprType.isConstant)
                else throw TypeMismatchInExpression(ast)
        }
    }

    override def visitNewExpr(ast: NewExpr, c: Context) = {
        getClass(c.asInstanceOf[SymbolTable], ast.name.toString)
        c.asInstanceOf[SymbolTable].get(ast.name.toString + "<-" + ast.name.toString) match {
            case Some(constructor) =>
                val lhs = c.asInstanceOf[SymbolTable].table.filter(p => p._1.endsWith(constructor.toString) && p._2.kind == Parameter)
                    .foldLeft(List[Symbol]())((L, p) => p._2 :: L).sortBy(symbol => symbol.index)
                val rhs = ast.exprs.map(_.accept(this, c).asInstanceOf[ExtendType])
                if (lhs.size == rhs.size) {
                    lhs.zip(rhs).foreach(p =>
                        if (!checkType(p._1.dataType, p._2.dataType)) throw TypeMismatchInExpression(ast)
                    )
                } else
                    throw TypeMismatchInExpression(ast)
            case None              =>
                // Default constructor
                if (ast.exprs != null && ast.exprs.nonEmpty) throw TypeMismatchInExpression(ast)
        }
        ExtendType(ClassType(ast.name.toString), isConstant = false)
    }

    override def visitCallExpr(ast: CallExpr, c: Context) = {
        if (ast.cName.isInstanceOf[Id]) memberAccess = true
        val callerType = ast.cName.accept(this, c).asInstanceOf[ExtendType]
        callerType.dataType match {
            case ClassType(className) =>
                val methodSymbol = getClassMember(c.asInstanceOf[SymbolTable], className, ast.method.toString, Method)
                methodSymbol.dataType match {
                    case VoidType =>
                        throw TypeMismatchInExpression(ast)
                    case _        =>
                        val lhs = c.asInstanceOf[SymbolTable].table.filter(p => p._1.endsWith(methodSymbol.toString) && p._2.kind == Parameter)
                            .foldLeft(List[Symbol]())((L, p) => p._2 :: L).sortBy(symbol => symbol.index)
                        val rhs = ast.params.map(_.accept(this, c).asInstanceOf[ExtendType])
                        if (lhs.size == rhs.size) {
                            lhs.zip(rhs).foreach(p =>
                                if (!checkType(p._1.dataType, p._2.dataType)) throw TypeMismatchInExpression(ast)
                            )
                            ExtendType(methodSymbol.dataType, isConstant = false)
                        } else
                            throw TypeMismatchInExpression(ast)
                }
            case _                    => throw TypeMismatchInExpression(ast)
        }

    }

    override def visitFieldAccess(ast: FieldAccess, c: Context) = {
        if (ast.name.isInstanceOf[Id]) memberAccess = true
        val callerType = ast.name.accept(this, c).asInstanceOf[ExtendType]
        callerType.dataType match {
            case ClassType(className) =>
                val fieldSymbol = getClassMember(c.asInstanceOf[SymbolTable], className, ast.field.toString, Attribute)
                if (!fieldSymbol.isStatic && !isSuperclass(c.asInstanceOf[SymbolTable].treeName.last, className))
                    throw CannotAccessPrivateAttribute(className, fieldSymbol.id)
                ExtendType(fieldSymbol.dataType, fieldSymbol.isConstant)
            case _                    => throw TypeMismatchInExpression(ast)
        }
    }

    override def visitArrayCell(ast: ArrayCell, c: Context) = {
        val arrayType = ast.arr.accept(this, c).asInstanceOf[ExtendType]
        val indexType = ast.idx.accept(this, c).asInstanceOf[ExtendType]
        (arrayType.dataType, indexType.dataType) match {
            case (a: ArrayType, e: IntType.type) => ExtendType(a.eleType, isConstant = false)
            case _                               => throw TypeMismatchInExpression(ast)
        }
    }

    override def visitId(ast: Id, c: Context) = {
        try {
            val idSymbol = getIdentifier(c.asInstanceOf[SymbolTable], ast.toString)
            ExtendType(idSymbol.dataType, isConstant = idSymbol.isConstant)
        } catch {
            case Undeclared(kind, id) =>
                if (memberAccess) {
                    memberAccess = false
                    try {
                        val classSymbol = getClass(c.asInstanceOf[SymbolTable], ast.toString)
                        ExtendType(ClassType(classSymbol.id), isConstant = false)
                    } catch {
                        case Undeclared(_, id) => throw Undeclared(Identifier, id)
                    }
                }
                else
                    throw Undeclared(kind, id)
        }
    }

    override def visitSelfLiteral(ast: SelfLiteral.type, c: Context) =
        ExtendType(ClassType(c.asInstanceOf[SymbolTable].treeName.last), isConstant = true)

    override def visitArrayType(ast: ArrayType, c: Context) = {
        val elementType = ast.eleType.accept(this, c)
        elementType match {
            case VoidType | ArrayType => throw TypeMismatchInStatement(null)
            case _                    => ExtendType(ast, isConstant = false)
        }
    }

    override def visitClassType(ast: ClassType, c: Context) = {
        getClass(c.asInstanceOf[SymbolTable], ast.classType)
        ExtendType(ast, isConstant = false)
    }

}


class MyBaseVisitor extends Visitor {
    override def visitProgram(ast: Program, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitClassDecl(ast: ClassDecl, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitAttributeDecl(ast: AttributeDecl, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitVarDecl(ast: VarDecl, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitConstDecl(ast: ConstDecl, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitMethodDecl(ast: MethodDecl, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitParamDecl(ast: ParamDecl, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitBinaryOp(ast: BinaryOp, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitUnaryOp(ast: UnaryOp, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitNewExpr(ast: NewExpr, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitCallExpr(ast: CallExpr, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitSelfLiteral(ast: SelfLiteral.type, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitArrayCell(ast: ArrayCell, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitFieldAccess(ast: FieldAccess, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitBlock(ast: Block, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitAssign(ast: Assign, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitIf(ast: If, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitCall(ast: Call, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitFor(ast: For, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitBreak(ast: Break.type, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitContinue(ast: Continue.type, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitReturn(ast: Return, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitArrayType(ast: ArrayType, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitClassType(ast: ClassType, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitId(ast: Id, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitInstance(ast: Instance.type, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitStatic(ast: Static.type, c: Context): Object = c.asInstanceOf[SymbolTable]

    override def visitIntType(ast: IntType.type, c: Context): Object = ExtendType(IntType, isConstant = false)

    override def visitFloatType(ast: FloatType.type, c: Context): Object = ExtendType(FloatType, isConstant = false)

    override def visitBoolType(ast: BoolType.type, c: Context): Object = ExtendType(BoolType, isConstant = false)

    override def visitStringType(ast: StringType.type, c: Context): Object = ExtendType(StringType, isConstant = false)

    override def visitVoidType(ast: VoidType.type, c: Context): Object = ExtendType(VoidType, isConstant = false)

    override def visitIntLiteral(ast: IntLiteral, c: Context) = ExtendType(IntType, isConstant = true)

    override def visitFloatLiteral(ast: FloatLiteral, c: Context) = ExtendType(FloatType, isConstant = true)

    override def visitStringLiteral(ast: StringLiteral, c: Context) = ExtendType(StringType, isConstant = true)

    override def visitBooleanLiteral(ast: BooleanLiteral, c: Context) = ExtendType(BoolType, isConstant = true)

    override def visitNullLiteral(ast: NullLiteral.type, c: Context) = ExtendType(NullLit, isConstant = true)

}


/** ***************************************************************************
  * Data structures
  * ***************************************************************************
  */
case object KBlock extends Kind

case object KIf extends Kind

case object KFor extends Kind

object NullLit extends Type


case class ExtendType(dataType: Type, isConstant: Boolean)


class SymbolTable extends Context {

    val table = new mutable.HashMap[String, Symbol]
    var treeName = List[String]()

    def init(): Unit = {
        val ioSymbol = Symbol("io", Class, null, this.treeName)
        put(ioSymbol)

        put(Symbol("readInt", Method, IntType, ioSymbol.url, isStatic = true))
        put(Symbol("writeInt", Method, VoidType, ioSymbol.url, isStatic = true))
        put(Symbol("writeIntLn", Method, VoidType, ioSymbol.url, isStatic = true))
        put(Symbol("readFloat", Method, FloatType, ioSymbol.url, isStatic = true))
        put(Symbol("writeFloat", Method, VoidType, ioSymbol.url, isStatic = true))
        put(Symbol("writeFloatLn", Method, VoidType, ioSymbol.url, isStatic = true))
        put(Symbol("readBool", Method, BoolType, ioSymbol.url, isStatic = true))
        put(Symbol("writeBool", Method, VoidType, ioSymbol.url, isStatic = true))
        put(Symbol("writeBoolLn", Method, VoidType, ioSymbol.url, isStatic = true))
        put(Symbol("readStr", Method, StringType, ioSymbol.url, isStatic = true))
        put(Symbol("writeStr", Method, VoidType, ioSymbol.url, isStatic = true))
        put(Symbol("writeStrLn", Method, VoidType, ioSymbol.url, isStatic = true))

        put(Symbol("anArg", Parameter, IntType, "writeInt" :: ioSymbol.url))
        put(Symbol("anArg", Parameter, IntType, "writeIntLn" :: ioSymbol.url))
        put(Symbol("anArg", Parameter, FloatType, "writeFloat" :: ioSymbol.url))
        put(Symbol("anArg", Parameter, FloatType, "writeFloatLn" :: ioSymbol.url))
        put(Symbol("anArg", Parameter, BoolType, "writeBool" :: ioSymbol.url))
        put(Symbol("anArg", Parameter, BoolType, "writeBoolLn" :: ioSymbol.url))
        put(Symbol("anArg", Parameter, StringType, "writeStr" :: ioSymbol.url))
        put(Symbol("anArg", Parameter, StringType, "writeStrLn" :: ioSymbol.url))
    }

    def put(symbol: Symbol): Unit = {
        get(symbol.toString) match {
            case Some(s) => throw Redeclared(symbol.kind, symbol.id)
            case None    => table(symbol.toString) = symbol
        }
    }

    def get(key: String): Option[Symbol] = table.get(key)
}

case class Symbol(index: Int, id: String, kind: Kind, dataType: Type,
                  var isStatic: Boolean, isConstant: Boolean,
                  url: List[String]) {
    override def toString: String = url.mkString("<-")
}

object Symbol {
    val extendsTable = new mutable.HashMap[String, String]
    var indexObject = -1

    def apply(id: String, kind: Kind, dataType: Type, treeName: List[String],
              isStatic: Boolean = false, isConstant: Boolean = false): Symbol = {
        indexObject = indexObject + 1
        val symbol = Symbol(indexObject, id, kind, dataType, isStatic, isConstant, id :: treeName)
        symbol
    }
}

/** ***************************************************************************
  * Utilities
  * ***************************************************************************
  */
trait Utils {
    def lookup(symbolTable: SymbolTable, id: String): Boolean =
        symbolTable.get(id) match {
            case Some(_) => true
            case None    => false
        }

    def getClass(symbolTable: SymbolTable, className: String): Symbol = {
        symbolTable.get(className) match {
            case Some(symbol) => symbol
            case None         => throw Undeclared(Class, className)
        }
    }

    def getClassMember(symbolTable: SymbolTable, className: String, member: String, kind: Kind): Symbol = {
        symbolTable.get(member + "<-" + className) match {
            case Some(symbol) => if (symbol.kind == kind) symbol else throw Undeclared(kind, member)
            case None         =>
                Symbol.extendsTable.get(className) match {
                    case Some(superClassName) => getClassMember(symbolTable, superClassName, member, kind)
                    case None                 =>
                        throw Undeclared(kind, member)
                }
        }
    }

    def getIdentifier(symbolTable: SymbolTable, id: String): Symbol =
        getIdentifier(symbolTable, symbolTable.treeName, id)

    def isSuperclass(subclass: String, superclass: String): Boolean =
        if (superclass == subclass) true
        else
            Symbol.extendsTable.get(subclass) match {
                case Some(className) => isSuperclass(className, superclass)
                case None            => false
            }

    def checkType(lhs: Type, rhs: Type): Boolean = {
        lhs match {
            case ClassType(lhsClassName)         =>
                rhs match {
                    case ClassType(rhsClassName) =>
                        if (lhsClassName == rhsClassName) true
                        else {
                            Symbol.extendsTable.get(rhsClassName) match {
                                case Some(superRhsClassName) => checkType(lhs, ClassType(superRhsClassName))
                                case None                    => false
                            }
                        }
                    case NullLit                 => true
                    case _                       => false
                }
            case ArrayType(lhsSize, lhsElemType) =>
                rhs match {
                    case ArrayType(rhsSize, rhsElemType) =>
                        checkType(lhsElemType, rhsElemType) && lhsSize.value == rhsSize.value
                    case _                               => false

                }
            case FloatType                       => isNumber(rhs)
            case VoidType                        => false
            case _                               => lhs == rhs
        }
    }

    def checkReturn(ast: MethodDecl, stmt: Stmt): Boolean =
        stmt match {
            case _: Return    => true
            case value: If    =>
                val thenReturn = checkReturn(ast, value.thenStmt)
                val elseReturn = value.elseStmt match {
                    case Some(elseBodyStmt) => checkReturn(ast, elseBodyStmt)
                    case None               => false
                }
                thenReturn && elseReturn
            case value: For   => checkReturn(ast, value.loop)
            case block: Block => if (block.stmt.nonEmpty) checkReturn(ast, block.stmt.last) else false
            case _            => false
        }

    def isNumber(dataType: Type): Boolean = isFloat(dataType) || isInt(dataType)

    def isFloat(dataType: Type): Boolean = dataType == FloatType

    def isInt(dataType: Type): Boolean = dataType == IntType

    def isBool(dataType: Type): Boolean = dataType == BoolType

    def isString(dataType: Type): Boolean = dataType == StringType

    private def treeNameToString(treeName: List[String], id: String) = (id :: treeName).mkString("<-")

    private def getIdentifier(symbolTable: SymbolTable, treeName: List[String], id: String): Symbol = {
        treeName match {
            case List()       =>
                symbolTable.get(id) match {
                    case Some(symbol) => symbol
                    case None         => throw Undeclared(Identifier, id)
                }
            case _ :: tail =>
                symbolTable.get(treeNameToString(treeName, id)) match {
                    case Some(symbol) => symbol
                    case None         =>
                        if (tail.length >= 2) getIdentifier(symbolTable, tail, id)
                        else throw Undeclared(Identifier, id)
                }
        }
    }
}