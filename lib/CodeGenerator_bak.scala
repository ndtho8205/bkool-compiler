/**
  * Nguyen Duc Tho
  * MSSV: 1413817
  */

package bkool.codegen

import java.io.File

import bkool.checker.{Attribute, Class, Identifier, Kind, Method, Parameter, SpecialMethod}
import bkool.utils.{Utils, _}

import scala.collection.mutable

/** ***************************************************************************
  * Data structures
  * ***************************************************************************
  */

case class MethodType(in: List[Type], out: Type) extends Type

case object NullType extends Type

class SimpleSymbolTable extends Context {

    val table                  = new mutable.HashMap[String, SimpleSymbol]
    var treeName: List[String] = List[String]()

    def init(): Unit = {
        val ioSymbol = SimpleSymbol("io", Class, null, this.treeName)
        put(ioSymbol)

        put(SimpleSymbol("readInt", Method, IntType, ioSymbol.url, Static))
        put(SimpleSymbol("writeInt", Method, VoidType, ioSymbol.url, Static))
        put(SimpleSymbol("writeIntLn", Method, VoidType, ioSymbol.url, Static))
        put(SimpleSymbol("readFloat", Method, FloatType, ioSymbol.url, Static))
        put(SimpleSymbol("writeFloat", Method, VoidType, ioSymbol.url, Static))
        put(SimpleSymbol("writeFloatLn", Method, VoidType, ioSymbol.url, Static))
        put(SimpleSymbol("readBool", Method, BoolType, ioSymbol.url, Static))
        put(SimpleSymbol("writeBool", Method, VoidType, ioSymbol.url, Static))
        put(SimpleSymbol("writeBoolLn", Method, VoidType, ioSymbol.url, Static))
        put(SimpleSymbol("readStr", Method, StringType, ioSymbol.url, Static))
        put(SimpleSymbol("writeStr", Method, VoidType, ioSymbol.url, Static))
        put(SimpleSymbol("writeStrLn", Method, VoidType, ioSymbol.url, Static))

        put(SimpleSymbol("anArg", Parameter, IntType, "writeInt" :: ioSymbol.url))
        put(SimpleSymbol("anArg", Parameter, IntType, "writeIntLn" :: ioSymbol.url))
        put(SimpleSymbol("anArg", Parameter, FloatType, "writeFloat" :: ioSymbol.url))
        put(SimpleSymbol("anArg", Parameter, FloatType, "writeFloatLn" :: ioSymbol.url))
        put(SimpleSymbol("anArg", Parameter, BoolType, "writeBool" :: ioSymbol.url))
        put(SimpleSymbol("anArg", Parameter, BoolType, "writeBoolLn" :: ioSymbol.url))
        put(SimpleSymbol("anArg", Parameter, StringType, "writeStr" :: ioSymbol.url))
        put(SimpleSymbol("anArg", Parameter, StringType, "writeStrLn" :: ioSymbol.url))
    }

    def put(symbol: SimpleSymbol): Unit = {
        get(symbol.toString) match {
            case Some(_) =>
            case None    => table(symbol.toString) = symbol
        }
    }

    def get(key: String): Option[SimpleSymbol] = table.get(key)

    def pushTree(p: String) {treeName = p :: treeName}

    def popTree() {treeName = treeName.tail}

    def getCurrentClass: String = treeName.last

    def getCurrentMethod: String = treeName.init.last
}

case class SimpleSymbol(index: Int, name: String, skind: SIKind, kind: Kind, dataType: Type, url: List[String], valueExpr: Option[Expr]) {
    val isStatic  : Boolean = skind == Static
    val isConstant: Boolean = valueExpr match {
        case Some(_) => true
        case None    => false
    }

    override def toString: String = url.mkString("<-")
}

object SimpleSymbol {
    val extendsTable     = new mutable.HashMap[String, String]
    var indexObject: Int = -1

    def apply(name: String, kind: Kind, dataType: Type, treeName: List[String],
              skind: SIKind = Instance, valueExpr: Option[Expr] = None): SimpleSymbol = {
        indexObject = indexObject + 1
        val symbol = SimpleSymbol(indexObject, name, skind, kind, dataType, name :: treeName, valueExpr)
        symbol
    }
}

case class ExtendContext(emitter: Emitter) extends Context


case class SubContext(emit: Emitter, classname: String, parent: String, decl: List[Decl]) extends Context

case class SubBody(emit: Emitter, classname: String, frame: Frame, sym: List[(String, Type, Val)]) extends Context

class Access(val emit: Emitter, val classname: String, val frame: Frame,
             val sym: List[(String, Type, Val)], val isLeft: Boolean, val isFirst: Boolean) extends Context


trait Val

case class Index(value: Int) extends Val

case class Const(value: Expr) extends Val

case object StringBuff extends Type

/** ***************************************************************************
  * Utilities
  * ***************************************************************************
  */

trait MyUtils {
    def getClass(symbolTable: SimpleSymbolTable, className: String): Option[SimpleSymbol] =
        symbolTable.get(className)

    def getClassMember(symbolTable: SimpleSymbolTable, className: String, member: String, kind: Kind): Option[SimpleSymbol] = {
        symbolTable.get(member + "<-" + className) match {
            case Some(symbol) => if (symbol.kind == kind) Some(symbol) else None
            case None         =>
                SimpleSymbol.extendsTable.get(className) match {
                    case Some(superClassName) => getClassMember(symbolTable, superClassName, member, kind)
                    case None                 => None
                }
        }
    }

    def getIdentifier(symbolTable: SimpleSymbolTable, id: String): Option[SimpleSymbol] =
        getIdentifier(symbolTable, symbolTable.treeName, id)

    def isSuperclass(subclass: String, superclass: String): Boolean =
        if (superclass == subclass) true
        else
            SimpleSymbol.extendsTable.get(subclass) match {
                case Some(className) => isSuperclass(className, superclass)
                case None            => false
            }

    private def treeNameToString(treeName: List[String], id: String) = (id :: treeName).mkString("<-")

    private def getIdentifier(symbolTable: SimpleSymbolTable, treeName: List[String], id: String): Option[SimpleSymbol] = {
        treeName match {
            case List()    => symbolTable.get(id)
            case _ :: tail =>
                symbolTable.get(treeNameToString(treeName, id)) match {
                    case Some(symbol) => Some(symbol)
                    case None         =>
                        if (tail.length >= 2) getIdentifier(symbolTable, tail, id)
                        else None
                }
        }
    }
}

/** ***************************************************************************
  * Code Generator
  * ***************************************************************************
  */

object CodeGenerator extends Utils {
    def check(ast: AST, dir: File) = {
        val globalVisitor = new GlobalVisitor
        val env = globalVisitor.visit(ast, null).asInstanceOf[SimpleSymbolTable]

        val codeGenVisitor = new CodeGenVisitor(env, dir)

        codeGenVisitor.visit(ast, null)
    }

}

/** ***************************************************************************
  * Global Enviroment
  * ***************************************************************************
  */
class GlobalVisitor extends BaseVisitor {

    override def visitProgram(ast: Program, c: Context) = {
        ast.decl.foreach(_.accept(this, c))
        c.asInstanceOf[SimpleSymbolTable]
    }

    override def visitClassDecl(ast: ClassDecl, c: Context) = {
        val classSymbol = SimpleSymbol(
            ast.name.toString, Class, null,
            c.asInstanceOf[SimpleSymbolTable].treeName
        )
        c.asInstanceOf[SimpleSymbolTable].put(classSymbol)

        c.asInstanceOf[SimpleSymbolTable].treeName = classSymbol.url
        ast.decl.foreach(_.accept(this, c))
        c.asInstanceOf[SimpleSymbolTable].treeName = classSymbol.url.tail

        null
    }

    override def visitAttributeDecl(ast: AttributeDecl, c: Context) = {
        val member = ast.decl match {
            case VarDecl(id, varType)                =>
                SimpleSymbol(
                    id.name, Attribute, varType,
                    c.asInstanceOf[SimpleSymbolTable].treeName,
                    ast.kind
                )
            case ConstDecl(id, constType, constExpr) =>
                SimpleSymbol(
                    id.name, Attribute, constType,
                    c.asInstanceOf[SimpleSymbolTable].treeName,
                    ast.kind, Some(constExpr)
                )
        }
        c.asInstanceOf[SimpleSymbolTable].put(member)
        null
    }


    override def visitMethodDecl(ast: MethodDecl, c: Context) = {
        val methodSymbol = SimpleSymbol(
            ast.name.toString,
            if (c.asInstanceOf[SimpleSymbolTable].treeName.head == ast.name.toString) SpecialMethod
            else Method,
            ast.returnType,
            c.asInstanceOf[SimpleSymbolTable].treeName,
            ast.kind
        )
        c.asInstanceOf[SimpleSymbolTable].put(methodSymbol)

        c.asInstanceOf[SimpleSymbolTable].treeName = methodSymbol.url
        ast.param.foreach(_.accept(this, c))
        c.asInstanceOf[SimpleSymbolTable].treeName = methodSymbol.url.tail

        null
    }

    override def visitParamDecl(ast: ParamDecl, c: Context) = {
        val paramSymbol = SimpleSymbol(
            ast.id.toString, Parameter, ast.paramType,
            c.asInstanceOf[SimpleSymbolTable].treeName
        )
        c.asInstanceOf[SimpleSymbolTable].put(paramSymbol)

        null
    }
}

/** ***************************************************************************
  * Code Generation Visitor
  * ***************************************************************************
  */

class CodeGenVisitor(envTable: SimpleSymbolTable, dir: File) extends BaseVisitor with MyUtils {

    override def visitProgram(ast: Program, c: Context) = ast.decl.map(_.accept(this, c))

    override def visitClassDecl(ast: ClassDecl, c: Context) = {
        envTable.pushTree(ast.name.name)

        val path = dir.getPath
        val emitter = new Emitter(path + "/" + ast.name.name + ".j")

        emitter.printout(emitter.emitPROLOG(ast.name.name, if (ast.parent != null) ast.parent.name else ""))

        ast.decl.map(_.accept(this, ExtendContext(emitter)))

        // auto-generate default constructor if necessary
        getClassMember(envTable, ast.name.name, ast.name.name, Method) match {
            case Some(_) =>
            case None    => genMETHOD(
                MethodDecl(Instance, ast.name, List(), null, Block(List(), List())),
                c,
                List(),
                new Frame("<init>", VoidType), emitter
            )
        }

        envTable.popTree()
        emitter.emitEPILOG()

        c
    }

    def genMETHOD(methodDecl: MethodDecl, c: Context, lst: List[Decl], frame: Frame, emit: Emitter) = {

        val isInit = methodDecl.returnType == null
        val isMain = methodDecl.kind == Static && methodDecl.name.name == "main" && methodDecl.param.isEmpty && methodDecl.returnType == VoidType

        val className = envTable.getCurrentClass
        val methodName = if (isInit) "<init>" else methodDecl.name.name
        val returnType = if (isInit) VoidType else methodDecl.returnType

        envTable.pushTree(methodName)

        val param =
            if (isMain) List(SimpleSymbol("args", Parameter, StringType, envTable.treeName))
            else
                envTable.table.filter(p => p._2.url.tail.head == methodName && p._2.kind == Parameter)
                    .foldLeft(List[SimpleSymbol]())((L, p) => p._2 :: L).sortBy(symbol => symbol.id)

        emit.printout(emit.emitMETHOD(methodName, MethodType(param.map(_.dataType), returnType), methodDecl.kind == Static, frame))
        frame.enterScope(true)

        if (methodDecl.kind == Instance)
            emit.printout(emit.emitVAR(frame.getNewIndex(), "this", ClassType(envTable.treeName.last), frame.getStartLabel(), frame.getEndLabel(), frame))
        param.foreach(x => emit.printout(emit.emitVAR(frame.getNewIndex(), x.name, x.dataType, frame.getStartLabel(), frame.getEndLabel(), frame))

        val body = methodDecl.body.asInstanceOf[Block]
        val newenv = body.decl.foldLeft(List[(String, Type, Val)]())((y, x) => visit(x, SubBody(emit, classname, frame, y)).asInstanceOf[List[(String, Type, Val)]])

        emit.printout(emit.emitLABEL(frame.getStartLabel(), frame))
        //Generate code for statements
        if (isInit) {
            emit.printout(emit.emitREADVAR(0, "this", ClassType(classname), frame))
            emit.printout(emit.emitINVOKESPECIAL(frame))
        }
        body.stmt.map(x => visit(x, SubBody(emit, classname, frame, env ++ newenv)))
        emit.printout(emit.emitLABEL(frame.getEndLabel(), frame))

        if (returnType == VoidType) emit.printout(emit.emitRETURN(VoidType, frame))
        emit.printout(emit.emitENDMETHOD(frame))
        frame.exitScope()

        envTable.popTree()
    }

    override def visitMethodDecl(ast: MethodDecl, c: Context) = {
        val subContext = c.asInstanceOf[SubContext]
        genMETHOD(
            subContext.classname,
            ast,
            c,
            subContext.decl,
            new Frame(ast.name.name, ast.returnType),
            subContext.emit
        )
        c
    }

    override def visitParamDecl(ast: ParamDecl, o: Context) = {
        val ctxt = o.asInstanceOf[SubBody]
        val emit = ctxt.emit
        val frame = ctxt.frame
        val env = ctxt.sym
        val idx = frame.getNewIndex
        emit.printout(emit.emitVAR(idx, ast.id.name, ast.paramType, frame.getStartLabel(), frame.getEndLabel(), frame))
        env :+ (ast.id.name, ast.paramType, Index(idx))
    }


    override def visitCall(ast: Call, o: Context) = {
        val ctxt = o.asInstanceOf[SubBody]
        val emit = ctxt.emit
        val frame = ctxt.frame
        val nenv = ctxt.sym
        val (str, typ) = visit(ast.parent, new Access(emit, ctxt.classname, frame, nenv, false, true)).asInstanceOf[(String, Type)]

        val in = ast.params.foldLeft(("", List[Type]()))((y, x) => {
            val (str1, typ1) = visit(x, new Access(emit, ctxt.classname, frame, nenv, false, true)).asInstanceOf[(String, Type)]
            (y._1 + str1, y._2 :+ typ1)
        }
        )
        emit.printout(in._1)

        emit.printout(emit.emitINVOKESTATIC(str + "/" + ast.method.name, MethodType(in._2, VoidType), frame))
    }

    override def visitId(ast: Id, o: Context) = {
        val ctxt = o.asInstanceOf[Access]
        val emitter = ctxt.emit
        val frame = ctxt.frame

        lookup(ast.name, ctxt.sym, (x: (String, Type, Val)) => x._1) match {
            case Some(v) =>
                if (ctxt.isLeft)
                    (emitter.emitWRITEVAR(), v._2)
                else
                    (emitter.emitREADVAR(frame = frame), v._2)
            case None    => lookup(ast.name, envTable, (x: ClassData) => x.cname) match {
                case Some(c) => (ast.name, ClassType(ast.name))
                case None    => throw Undeclared(Identifier, ast.name)
            }
        }

    }


    override def visitIntLiteral(ast: IntLiteral, o: Context) = {
        val ctxt = o.asInstanceOf[Access]
        val emit = ctxt.emit
        val frame = ctxt.frame
        (emit.emitPUSHICONST(ast.value, frame), IntType)
    }

    override def visitFloatLiteral(ast: FloatLiteral, c: Context) = {
        val ctxt = c.asInstanceOf[Access]
        val emit = ctxt.emit
        val frame = ctxt.frame
        (emit.emitPUSHFCONST(ast.value.toString, frame), FloatType)
    }

    override def visitBinaryOp(ast: BinaryOp, c: Context) = {
        val ctxt = c.asInstanceOf[Access]
        val emitter = ctxt.emit
        val frame = ctxt.frame

        val left = ast.left.accept(this, c).asInstanceOf[(String, Type)]
        val right = ast.right.accept(this, c).asInstanceOf[(String, Type)]

        val resType = if (left._2 == FloatType || right._2 == FloatType) FloatType else IntType
        ast.op match {
            case "+" | "-" =>
                if (left._2 == IntType && right._2 == IntType)
                    (left._1 + right._1 + emitter.emitADDOP("+", resType, frame), resType)
                else {
                    val leftOp = left._1 + (if (left._2 == IntType) emitter.emitI2F(frame) else "")
                    val rightOp = right._1 + (if (right._2 == IntType) emitter.emitI2F(frame) else "")
                    (leftOp + rightOp + emitter.emitADDOP(ast.op, FloatType, frame), FloatType)
                }
            case "*"       =>
                (left._1 + right._1 + emitter.emitMULOP("*", resType, frame), resType)
            case "/"       =>
                val leftOp = left._1 + (if (left._2 == IntType) emitter.emitI2F(frame) else "")
                val rightOp = right._1 + (if (right._2 == IntType) emitter.emitI2F(frame) else "")
                (leftOp + rightOp + emitter.emitMULOP("/", FloatType, frame), FloatType)
        }
    }


    override def visitVarDecl(ast: VarDecl, c: Context) = {
        val ctxt = c.asInstanceOf[SubBody]
        val emit = ctxt.emit
        val frame = ctxt.frame
        val env = ctxt.sym
        val idx = frame.getNewIndex

        emit.printout(emit.emitVAR(idx, ast.variable.name, ast.varType, frame.getStartLabel(), frame.getEndLabel(), frame))
        env :+ (ast.variable.name, ast.varType, Index(idx))
    }

    override def visitAssign(ast: Assign, c: Context) = {
        val ctxt = c.asInstanceOf[SubBody]
        val emitter = ctxt.emit
        val frame = ctxt.frame
        val nenv = ctxt.sym

        val rhs = ast.expr.accept(this, new Access(emitter, ctxt.classname, frame, nenv, false, true)).asInstanceOf[(String, Type)]
        val lhs = ast.leftHandSide.accept(this, new Access(emitter, ctxt.classname, frame, nenv, true, true)).asInstanceOf[(String, Type)]

        emitter.printout(rhs._1 + lhs._1)
    }

    override def visitBooleanLiteral(ast: BooleanLiteral, c: Context) = {
        val ctxt = c.asInstanceOf[Access]
        val emit = ctxt.emit
        val frame = ctxt.frame

        (emit.emitPUSHICONST(if (ast.value) 1 else 0, frame), BoolType)
    }

    override def visitIf(ast: If, c: Context) = {
        val ctxt = c.asInstanceOf[SubBody]
        val emitter = ctxt.emit
        val frame = ctxt.frame
        val nenv = ctxt.sym

        val expr1 = ast.expr.accept(this, new Access(emitter, ctxt.classname, frame, nenv, false, true)).asInstanceOf[(String, Type)]
        val thenLabel = frame.getNewLabel()
        val elseLabel = frame.getNewLabel()
        val thenStmt = emitter.emitIFTRUE(thenLabel, frame)
        val expr2 = ast.expr.accept(this, new Access(emitter, ctxt.classname, frame, nenv, false, true)).asInstanceOf[(String, Type)]
        val elseStmt = emitter.emitIFFALSE(elseLabel, frame)

        emitter.printout(expr1._1 + thenStmt + expr2._1 + elseStmt)
    }

}