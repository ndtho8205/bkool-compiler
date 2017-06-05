/**
  * Nguyen Duc Tho
  * MSSV: 1413817
  */

package bkool.codegen

import java.io.File

import bkool.checker.{Attribute, Class, Constant, Identifier, Kind, Method, Parameter, SpecialMethod, Undeclared, Variable}
import bkool.utils.{Utils, _}

import scala.collection.mutable

/** ***************************************************************************
  * Data structures
  * ***************************************************************************
  */

case class MethodType(in: List[Type], out: Type) extends Type

case object NullType extends Type

case object SelfType extends Type

case class ClassLiteral(value: String) extends Literal


class SimpleSymbolTable {
    val table        = new mutable.HashMap[String, SimpleSymbol]
    val extendsTable = new mutable.HashMap[String, String]

    def init(): Unit = {
        val ioSymbol = SimpleSymbol("io", Class, null, List())
        put(ioSymbol)

        put(SimpleSymbol("readInt", Method, Some(IntType), ioSymbol.url, Static))
        put(SimpleSymbol("writeInt", Method, Some(VoidType), ioSymbol.url, Static))
        put(SimpleSymbol("writeIntLn", Method, Some(VoidType), ioSymbol.url, Static))
        put(SimpleSymbol("readFloat", Method, Some(FloatType), ioSymbol.url, Static))
        put(SimpleSymbol("writeFloat", Method, Some(VoidType), ioSymbol.url, Static))
        put(SimpleSymbol("writeFloatLn", Method, Some(VoidType), ioSymbol.url, Static))
        put(SimpleSymbol("readBool", Method, Some(BoolType), ioSymbol.url, Static))
        put(SimpleSymbol("writeBool", Method, Some(VoidType), ioSymbol.url, Static))
        put(SimpleSymbol("writeBoolLn", Method, Some(VoidType), ioSymbol.url, Static))
        put(SimpleSymbol("readStr", Method, Some(StringType), ioSymbol.url, Static))
        put(SimpleSymbol("writeStr", Method, Some(VoidType), ioSymbol.url, Static))
        put(SimpleSymbol("writeStrLn", Method, Some(VoidType), ioSymbol.url, Static))

        put(SimpleSymbol("anArg", Parameter, Some(IntType), "writeInt" :: ioSymbol.url))
        put(SimpleSymbol("anArg", Parameter, Some(IntType), "writeIntLn" :: ioSymbol.url))
        put(SimpleSymbol("anArg", Parameter, Some(FloatType), "writeFloat" :: ioSymbol.url))
        put(SimpleSymbol("anArg", Parameter, Some(FloatType), "writeFloatLn" :: ioSymbol.url))
        put(SimpleSymbol("anArg", Parameter, Some(BoolType), "writeBool" :: ioSymbol.url))
        put(SimpleSymbol("anArg", Parameter, Some(BoolType), "writeBoolLn" :: ioSymbol.url))
        put(SimpleSymbol("anArg", Parameter, Some(StringType), "writeStr" :: ioSymbol.url))
        put(SimpleSymbol("anArg", Parameter, Some(StringType), "writeStrLn" :: ioSymbol.url))
    }

    def put(symbol: SimpleSymbol): Unit = {
        get(symbol.toString) match {
            case Some(_) => throw new Exception("SimpleSymbolTable - put - Redeclared")
            case None    => table(symbol.toString) = symbol
        }
    }

    def get(key: String): Option[SimpleSymbol] = table.get(key)

    def putParent(childClass: String, parentClass: String): Unit = {
        getParent(childClass) match {
            case Some(_) => throw new Exception("SimpleSymbolTable - putParent - Redeclared")
            case None    => extendsTable(childClass) = parentClass
        }
    }

    def getParent(childClass: String): Option[String] = extendsTable.get(childClass)
}

case class SimpleSymbol(name: String, skind: SIKind, kind: Kind, dataType: Option[Type], url: List[String], value: Option[Literal]) {
    val isStatic  : Boolean = skind == Static
    val isConstant: Boolean = value match {
        case Some(_) => true
        case None    => false
    }
    var index     : Int     = 0

    override def toString: String = url.mkString("<-")
}

object SimpleSymbol {
    def apply(name: String, kind: Kind, dataType: Option[Type], treeName: List[String],
              skind: SIKind = Instance, valueExpr: Option[Literal] = None): SimpleSymbol = {
        val symbol = SimpleSymbol(name, skind, kind, dataType, name :: treeName, valueExpr)
        symbol
    }
}


case class ExtendContext(treeName: List[String]) extends Context

case class CodeGenContext(treeName: List[String], emitter: Emitter, frame: Option[Frame]) extends Context

class AccessContext(val codeGenContext: CodeGenContext, val isLhs: Boolean, val isFirstAccess: Boolean) extends Context

class IfExprAccessContext(override val codeGenContext: CodeGenContext, val thenLabel: Int, val elseLabel: Int) extends AccessContext(codeGenContext, false, true)

object AccessContext {
    def apply(codeGenContext: CodeGenContext, isLhs: Boolean, isFirstAccess: Boolean): AccessContext = new AccessContext(codeGenContext, isLhs, isFirstAccess)
}

object IfExprAccessContext {
    def apply(codeGenContext: CodeGenContext, thenLabel: Int, elseLabel: Int): IfExprAccessContext = new IfExprAccessContext(codeGenContext, thenLabel, elseLabel)
}

case class DataObject(value: String, code: String, dataType: Type, isLiteral: Boolean = true)


/** ***************************************************************************
  * Utilities
  * ***************************************************************************
  */

trait MyUtils {
    var maxStackSize : Int = 0
    var currStackSize: Int = 0

    def getClass(symbolTable: SimpleSymbolTable, className: String): Option[SimpleSymbol] =
        symbolTable.get(className)

    def getClassMember(symbolTable: SimpleSymbolTable, className: String, member: String, kind: Kind): Option[SimpleSymbol] = {
        symbolTable.get(member + "<-" + className) match {
            case Some(symbol) => if (symbol.kind == kind) Some(symbol) else None
            case None         =>
                symbolTable.getParent(className) match {
                    case Some(superClassName) => getClassMember(symbolTable, superClassName, member, kind)
                    case None                 => None
                }
        }
    }

    def isSuperclass(symbolTable: SimpleSymbolTable, subclass: String, superclass: String): Boolean =
        if (superclass == subclass) true
        else
            symbolTable.getParent(subclass) match {
                case Some(className) => isSuperclass(symbolTable, className, superclass)
                case None            => false
            }

    def getIdentifier(symbolTable: SimpleSymbolTable, treeName: List[String], id: String,
                      func: SimpleSymbol => Boolean = _ => true): Option[SimpleSymbol] =
        symbolTable.get(treeNameToString(treeName, id)) match {
            case Some(symbol) => if (func(symbol)) Some(symbol) else None
            case None         =>
                if (treeName.length > 2) getIdentifier(symbolTable, treeName.tail, id, func)
                else None
        }

    def getConst(ast: Literal) = ast match {
        case IntLiteral(i)     => i.toString
        case FloatLiteral(i)   => i.toString
        case StringLiteral(i)  => i
        case BooleanLiteral(i) => if (i) "1" else "0"
        case NullLiteral       => "null"
        case SelfLiteral       => "this"
    }

    def saveStackState(frame: Frame): Unit = {
        maxStackSize = frame.getMaxOpStackSize()
        currStackSize = frame.getStackSize()
    }

    def restoreStackState(frame: Frame): Unit = {
        frame.maxOpStackSize = maxStackSize
        frame.currOpStackSize = currStackSize
    }

    private def treeNameToString(treeName: List[String], id: String) = (id :: treeName).mkString("<-")


}

class IndexUtils {
    var currIndex = 0

    def getCurrIndex = currIndex

    def getNewIndex: Int = {
        currIndex = currIndex + 1
        currIndex
    }
}

/** ***************************************************************************
  * Code Generator
  * ***************************************************************************
  */

object CodeGenerator extends Utils {
    def check(ast: AST, dir: File) = {
        val env = new SimpleSymbolTable
        env.init()

        val globalVisitor = new GlobalVisitor(env)
        globalVisitor.visit(ast, ExtendContext(List()))

        val codeGenVisitor = new CodeGenVisitor(env, dir)
        codeGenVisitor.visit(ast, null)
    }

}

/** ***************************************************************************
  * Global Enviroment
  * ***************************************************************************
  */

class GlobalVisitor(env: SimpleSymbolTable) extends BaseVisitor {
    val indexUtils = new IndexUtils

    override def visitProgram(ast: Program, c: Context) = {
        ast.decl.foreach(_.accept(this, c))
        null
    }

    override def visitClassDecl(ast: ClassDecl, c: Context) = {
        val context = c.asInstanceOf[ExtendContext]
        val classSymbol = SimpleSymbol(ast.name.toString, Class, None, context.treeName)
        env.put(classSymbol)

        if (ast.parent != null) env.putParent(ast.name.toString, ast.parent.toString)

        ast.decl.foreach(_.accept(this, ExtendContext(classSymbol.url)))
        null
    }

    override def visitAttributeDecl(ast: AttributeDecl, c: Context) = {
        val context = c.asInstanceOf[ExtendContext]
        val member = ast.decl match {
            case VarDecl(id, varType)                =>
                SimpleSymbol(
                    id.name, Attribute, Some(varType),
                    context.treeName, ast.kind
                )
            case ConstDecl(id, constType, constExpr) =>
                SimpleSymbol(
                    id.name, Attribute, Some(constType),
                    context.treeName, ast.kind,
                    Some(visit(constExpr, context).asInstanceOf[Literal])
                )
        }
        env.put(member)
        null
    }

    override def visitVarDecl(ast: VarDecl, c: Context) = {
        val varSymbol = SimpleSymbol(
            ast.variable.toString, Variable, Some(ast.varType),
            c.asInstanceOf[ExtendContext].treeName
        )
        env.put(varSymbol)

        varSymbol
    }

    override def visitConstDecl(ast: ConstDecl, c: Context) = {
        val constSymbol = SimpleSymbol(
            ast.id.name, Constant, Some(ast.constType),
            c.asInstanceOf[ExtendContext].treeName,
            valueExpr = Some(visit(ast.const, c).asInstanceOf[Literal])
        )
        env.put(constSymbol)

        constSymbol
    }

    override def visitMethodDecl(ast: MethodDecl, c: Context) = {
        val context = c.asInstanceOf[ExtendContext]
        val methodSymbol = SimpleSymbol(
            ast.name.toString,
            if (context.treeName.last == ast.name.toString) SpecialMethod else Method,
            Some(ast.returnType),
            context.treeName,
            ast.kind
        )
        env.put(methodSymbol)

        val methodContext = ExtendContext(methodSymbol.url)
        ast.param.foreach(_.accept(this, methodContext))
        ast.body.asInstanceOf[Block].decl.foreach(_.accept(this, methodContext))
        ast.body.asInstanceOf[Block].stmt.foreach(_.accept(this, methodContext))

        null
    }

    override def visitParamDecl(ast: ParamDecl, c: Context) = {
        val paramSymbol = SimpleSymbol(
            ast.id.toString, Parameter, Some(ast.paramType),
            c.asInstanceOf[ExtendContext].treeName
        )
        env.put(paramSymbol)

        null
    }

    override def visitIf(ast: If, c: Context) = {
        val ifContext = ExtendContext(("%If" + indexUtils.getNewIndex) :: c.asInstanceOf[ExtendContext].treeName)
        ast.thenStmt.accept(this, ifContext)
        ast.elseStmt match {
            case Some(stmt) => stmt.accept(this, ifContext)
            case None       =>
        }

        null
    }

    override def visitFor(ast: For, c: Context) = {
        val forContext = ExtendContext(("%For" + indexUtils.getNewIndex) :: c.asInstanceOf[ExtendContext].treeName)
        ast.loop.accept(this, forContext)

        null
    }

    override def visitBlock(ast: Block, c: Context) = {
        val blockContext = ExtendContext(("%Block" + indexUtils.getNewIndex) :: c.asInstanceOf[ExtendContext].treeName)
        ast.decl.foreach(_.accept(this, blockContext))
        ast.stmt.foreach(_.accept(this, blockContext))

        null
    }

    override def visitIntLiteral(ast: IntLiteral, c: Context) = ast

    override def visitFloatLiteral(ast: FloatLiteral, c: Context) = ast

    override def visitBooleanLiteral(ast: BooleanLiteral, c: Context) = ast

    override def visitStringLiteral(ast: StringLiteral, c: Context) = ast

    override def visitSelfLiteral(ast: SelfLiteral.type, c: Context) = ast

    override def visitNullLiteral(ast: NullLiteral.type, c: Context) = ast
}

/** ***************************************************************************
  * Code Generation Visitor
  * ***************************************************************************
  */

class CodeGenVisitor(gEnv: SimpleSymbolTable, dir: File) extends BaseVisitor with MyUtils {

    val indexUtils = new IndexUtils

    override def visitProgram(ast: Program, c: Context) = ast.decl.map(_.accept(this, CodeGenContext(List(), null, None)))

    override def visitClassDecl(ast: ClassDecl, c: Context) = {
        val path = dir.getPath
        val emitter = new Emitter(path + "/" + ast.name.name + ".j")
        val classContext = CodeGenContext(ast.name.name :: c.asInstanceOf[CodeGenContext].treeName, emitter, None)
        val parentClass = if (ast.parent != null) ast.parent.name else "java/lang/Object"

        // generate some starting directives for a class
        emitter.printout(emitter.emitPROLOG(ast.name.name, parentClass))

        // generate field
        val (initDecl, clinitDecl) = ast.decl.filter(_.isInstanceOf[AttributeDecl]).foldLeft((List[SimpleSymbol](), List[SimpleSymbol]()))(
            (L, attrDecl) => {
                val symbol = visit(attrDecl, classContext).asInstanceOf[SimpleSymbol]
                (symbol.isConstant, symbol.skind, symbol.dataType.get) match {
                    case (true, Static, ClassType(_)) | (false, Static, ArrayType(_, _))     => (L._1, L._2 ::: List(symbol))
                    case (true, Instance, ClassType(_)) | (false, Instance, ArrayType(_, _)) => (L._1 ::: List(symbol), L._2)
                    case _                                                                   => L
                }
            }
        )

        // generate constructor
        getClassMember(gEnv, ast.name.name, ast.name.name, SpecialMethod) match {
            case Some(methodSymbol) =>
                // if already exists user constructor
                val userConstructor = ast.decl.filter(
                    x => x.isInstanceOf[MethodDecl] && x.asInstanceOf[MethodDecl].name.name == methodSymbol.name
                ).head.asInstanceOf[MethodDecl]
                genINITMETHOD(
                    userConstructor, initDecl, parentClass,
                    CodeGenContext(classContext.treeName, emitter, Some(new Frame("<init>", VoidType)))
                )
            case None               =>
                // default constructor
                genINITMETHOD(
                    MethodDecl(Instance, ast.name, List(), null, Block(List(), List())), initDecl, parentClass,
                    CodeGenContext(classContext.treeName, emitter, Some(new Frame("<init>", VoidType)))
                )
        }

        // generate class members' code
        ast.decl.filter(_.isInstanceOf[MethodDecl]).foreach(visit(_, classContext))

        // generate code for a class init method
        if (clinitDecl.nonEmpty)
            genCLINITMETHOD(
                clinitDecl,
                CodeGenContext(classContext.treeName, emitter, Some(new Frame("<clinit>", VoidType)))
            )

        emitter.emitEPILOG()
        c
    }

    def genINITMETHOD(methodDecl: MethodDecl, init: List[SimpleSymbol], superClass: String, c: Context): Unit = {
        val context = c.asInstanceOf[CodeGenContext]
        val emitter = context.emitter
        val frame = context.frame.get
        val methodContext = CodeGenContext(methodDecl.name.name :: context.treeName, emitter, Some(frame))

        val className = context.treeName.last
        val methodBody = methodDecl.body.asInstanceOf[Block]
        val methodParam = methodDecl.param.map(_.accept(this, methodContext).asInstanceOf[SimpleSymbol])
        val methodType = MethodType(methodParam.map(_.dataType.get), VoidType)

        //generate code for method's prototype
        emitter.printout(emitter.emitMETHOD("<init>", methodType, isStatic = false, frame))
        frame.enterScope(true)

        // generate local variable
        emitter.printout(
            emitter.emitVAR(frame.getNewIndex(), "this", ClassType(className), frame.getStartLabel(), frame.getEndLabel(), frame)
        )
        methodParam.foreach(
            x => {
                x.index = frame.getNewIndex()
                emitter.printout(emitter.emitVAR(x.index, x.name, x.dataType.get, frame.getStartLabel(), frame.getEndLabel(), frame))
            }
        )

        // start LABEL ====================================================================================
        emitter.printout(emitter.emitLABEL(frame.getStartLabel(), frame))

        // call super's constructor
        emitter.printout(emitter.emitREADVAR(0, "this", ClassType(className), frame))
        //TODO: call super's constructor: <methodType>???
        emitter.printout(emitter.emitINVOKESPECIAL(superClass + "/<init>", if (superClass == "java/lang/Object") MethodType(List(), VoidType) else methodType, frame))
        emitter.printout("\n")

        // generate code for init
        emitter.printout(emitter.emitFieldInitialization(className, init, clinit = false, frame = frame))

        // generate code for staments
        methodBody.decl.foreach(x => visit(x, methodContext))
        methodBody.stmt.map(x => visit(x, methodContext))

        // end LABEL ====================================================================================
        emitter.printout(emitter.emitLABEL(frame.getEndLabel(), frame))

        // return
        emitter.printout(emitter.emitRETURN(VoidType, frame))
        emitter.printout(emitter.emitENDMETHOD(frame))
        frame.exitScope()
    }

    def genCLINITMETHOD(clinit: List[SimpleSymbol], c: Context): Unit = {
        val context = c.asInstanceOf[CodeGenContext]
        val emitter = context.emitter
        val frame = context.frame.get
        val className = context.treeName.last

        emitter.printout(emitter.emitMETHOD("<clinit>", MethodType(List(), VoidType), isStatic = true, frame))
        frame.enterScope(true)

        emitter.printout(emitter.emitFieldInitialization(className, clinit, clinit = true, frame))

        emitter.printout(emitter.emitRETURN(VoidType, frame))
        emitter.printout(emitter.emitENDMETHOD(frame))
        frame.exitScope()
    }

    def genMETHOD(methodDecl: MethodDecl, c: Context): Unit = {
        val context = c.asInstanceOf[CodeGenContext]
        val emitter = context.emitter
        val frame = context.frame.get
        val methodContext = CodeGenContext(methodDecl.name.name :: context.treeName, emitter, Some(frame))

        val className = context.treeName.last
        val methodRType = methodDecl.returnType
        val methodName = methodDecl.name.name
        val methodBody = methodDecl.body.asInstanceOf[Block]
        val isMain = methodDecl.kind == Static && methodName == "main" && methodDecl.param.isEmpty && methodRType == VoidType

        val methodParam =
            if (isMain) List(SimpleSymbol("args", Parameter, Some(ArrayType(IntLiteral(0), StringType)), methodContext.treeName))
            else
                methodDecl.param.map(_.accept(this, methodContext).asInstanceOf[SimpleSymbol])

        //generate code for method's prototype
        emitter.printout(emitter.emitMETHOD(
            methodName,
            MethodType(methodParam.map(_.dataType.get), methodRType),
            methodDecl.kind == Static,
            frame
        ))

        frame.enterScope(true)

        // generate local variable
        if (methodDecl.kind == Instance)
            emitter.printout(emitter.emitVAR(frame.getNewIndex(), "this", ClassType(className), frame.getStartLabel(), frame.getEndLabel(), frame))
        methodParam.foreach(
            x => {
                x.index = frame.getNewIndex()
                emitter.printout(emitter.emitVAR(x.index, x.name, x.dataType.get, frame.getStartLabel(), frame.getEndLabel(), frame))
            }
        )

        // generate code for statements
        emitter.printout(emitter.emitLABEL(frame.getStartLabel(), frame))
        methodBody.decl.foreach(x => visit(x, methodContext).asInstanceOf[SimpleSymbol])
        methodBody.stmt.map(x => visit(x, methodContext))
        emitter.printout(emitter.emitLABEL(frame.getEndLabel(), frame))

        if (methodRType == VoidType) emitter.printout(emitter.emitRETURN(VoidType, frame));
        emitter.printout(emitter.emitENDMETHOD(frame))
        frame.exitScope()
    }

    override def visitAttributeDecl(ast: AttributeDecl, c: Context) = {
        val context = c.asInstanceOf[CodeGenContext]
        val emitter = context.emitter
        val symbol =
            (ast.decl match {
                case VarDecl(id, _)      =>
                    getClassMember(gEnv, context.treeName.last, id.name, Attribute)
                case ConstDecl(id, _, _) =>
                    getClassMember(gEnv, context.treeName.last, id.name, Attribute)
            }).get

        emitter.printout(
            if (symbol.isConstant) {
                emitter.emitCONSTATTRIBUTE(symbol.name, symbol.skind, symbol.dataType.get, getConst(symbol.value.get))
            } else {
                emitter.emitVARATTRIBUTE(symbol.name, symbol.skind, symbol.dataType.get)
            }
        )

        symbol
    }

    override def visitMethodDecl(ast: MethodDecl, c: Context) = {
        val context = c.asInstanceOf[CodeGenContext]
        if (ast.returnType != null)
            genMETHOD(
                ast,
                CodeGenContext(context.treeName, context.emitter, Some(new Frame(ast.name.name, ast.returnType)))
            )
        c
    }

    override def visitParamDecl(ast: ParamDecl, c: Context) = {
        val context = c.asInstanceOf[CodeGenContext]
        val symbol = getIdentifier(gEnv, context.treeName, ast.id.name, x => x.kind == Parameter).get

        symbol
    }

    override def visitVarDecl(ast: VarDecl, c: Context) = {
        val context = c.asInstanceOf[CodeGenContext]
        val emitter = context.emitter
        val frame = context.frame.get
        val symbol = getIdentifier(gEnv, context.treeName, ast.variable.name, x => x.kind == Variable).get

        symbol.index = frame.getNewIndex()
        ast.varType match {
            case typ: ArrayType =>
                emitter.printout(emitter.emitNEWARRAY(typ, frame) + emitter.emitWRITEVAR(symbol.index, symbol.name, typ, frame))
                emitter.printout("\n")
            case _              =>
        }

        symbol
    }

    override def visitConstDecl(ast: ConstDecl, c: Context) = {
        val context = c.asInstanceOf[CodeGenContext]
        val frame = context.frame.get
        val symbol = getIdentifier(gEnv, context.treeName, ast.id.name, x => x.kind == Constant).get

        symbol.index = frame.getNewIndex()

        symbol
    }

    override def visitIf(ast: If, c: Context) = {
        val context = c.asInstanceOf[CodeGenContext]
        val emitter = context.emitter
        val frame = context.frame.get

        val ifContext = CodeGenContext(("%If" + indexUtils.getNewIndex) :: context.treeName, emitter, Some(frame))

        val thenLabel = frame.getNewLabel()
        val elseLabel = frame.getNewLabel()
        val fiLabel = ast.elseStmt match {
            case Some(_) => frame.getNewLabel()
            case None       => elseLabel
        }

        val expr = visit(ast.expr, IfExprAccessContext(ifContext, thenLabel, elseLabel)).asInstanceOf[DataObject]
        emitter.printout(expr.code)
        emitter.printout("\n")

        emitter.printout(emitter.emitLABEL(thenLabel, frame))
        visit(ast.thenStmt, ifContext)
        emitter.printout(emitter.emitGOTO(fiLabel, frame))

        emitter.printout(emitter.emitLABEL(elseLabel, frame))
        ast.elseStmt match {
            case Some(stmt) =>
                visit(stmt, ifContext)
                emitter.printout(emitter.emitLABEL(fiLabel, frame))
            case None       =>
        }

        emitter.printout("\n")
        c
    }

    override def visitFor(ast: For, c: Context) = {
        val context = c.asInstanceOf[CodeGenContext]
        val emitter = context.emitter
        val frame = context.frame.get

        val forContext = CodeGenContext(("%For" + indexUtils.getNewIndex) :: context.treeName, emitter, Some(frame))

        getIdentifier(gEnv, context.treeName, ast.idx, x => x.kind != Constant) match {
            case Some(symbol) =>
                frame.enterLoop()
                val checkLabel = frame.getNewLabel()
                val thenLabel = frame.getNewLabel()
                val incLabel = frame.getContinueLabel()
                val elseLabel = frame.getBreakLabel()

                emitter.printout(visit(ast.expr1, AccessContext(context, isLhs = false, isFirstAccess = true)).asInstanceOf[DataObject].code)
                emitter.printout(emitter.emitWRITEVAR(symbol.index, symbol.name, symbol.dataType.get, frame))


                emitter.printout(emitter.emitLABEL(checkLabel, frame))
                emitter.printout(visit(BinaryOp(if (ast.up) "<=" else ">=", Id(ast.idx), ast.expr2), IfExprAccessContext(context, thenLabel, elseLabel)).asInstanceOf[DataObject].code)

                emitter.printout(emitter.emitLABEL(thenLabel, frame))
                visit(ast.loop, forContext)

                emitter.printout(emitter.emitLABEL(incLabel, frame))
                visit(Assign(Id(ast.idx), BinaryOp(if (ast.up) "+" else "-", Id(ast.idx), IntLiteral(1))), forContext)
                emitter.printout(emitter.emitGOTO(checkLabel, frame))

                emitter.printout(emitter.emitLABEL(elseLabel, frame))
                emitter.printout("\n")
                frame.exitLoop()
            case None         => throw Undeclared(Identifier, ast.idx)
        }
        c
    }

    override def visitBlock(ast: Block, c: Context) = {
        val context = c.asInstanceOf[CodeGenContext]
        val emitter = context.emitter
        val frame = context.frame.get

        val blockContext = CodeGenContext(("%Block" + indexUtils.getNewIndex) :: context.treeName, emitter, Some(frame))

        frame.enterScope(false)

        // generate code for statements
        emitter.printout(emitter.emitLABEL(frame.getStartLabel(), frame))
        ast.decl.foreach(x => visit(x, blockContext).asInstanceOf[SimpleSymbol])
        ast.stmt.map(x => visit(x, blockContext))
        emitter.printout(emitter.emitLABEL(frame.getEndLabel(), frame))

        frame.exitScope()

        c
    }

    override def visitBreak(ast: Break.type, c: Context) = {
        val context = c.asInstanceOf[CodeGenContext]
        val emitter = context.emitter
        val frame = context.frame.get

        emitter.printout(emitter.emitGOTO(frame.getBreakLabel(), frame))
        c
    }

    override def visitContinue(ast: Continue.type, c: Context) = {
        val context = c.asInstanceOf[CodeGenContext]
        val emitter = context.emitter
        val frame = context.frame.get

        emitter.printout(emitter.emitGOTO(frame.getContinueLabel(), frame))
        c
    }

    override def visitCall(ast: Call, c: Context) = {
        val context = c.asInstanceOf[CodeGenContext]
        val emitter = context.emitter
        val frame = context.frame.get

        val caller = visit(ast.parent, AccessContext(context, isLhs = false, isFirstAccess = true)).asInstanceOf[DataObject]
        val callerClass = caller.dataType.asInstanceOf[ClassType].classType
        val methodAccess = callerClass + "/" + ast.method.name

        emitter.printout(caller.code)

        val params = ast.params.foldLeft(("", List[Type]()))((y, x) => {
            val paramData = visit(x, AccessContext(context, isLhs = false, isFirstAccess = true)).asInstanceOf[DataObject]
            (y._1 + paramData.code, y._2 :+ paramData.dataType)
        })
        // load params to stack
        emitter.printout(params._1)

        // invoke
        getClassMember(gEnv, callerClass, ast.method.name, Method) match {
            case Some(symbol) =>
                if (symbol.skind == Static)
                    emitter.printout(emitter.emitINVOKESTATIC(methodAccess, MethodType(params._2, symbol.dataType.get), frame))
                else
                    emitter.printout(emitter.emitINVOKEVIRTUAL(methodAccess, MethodType(params._2, symbol.dataType.get), frame))
            case None         =>
                throw Undeclared(Method, ast.method.name)
        }

        emitter.printout("\n")
        c
    }

    override def visitAssign(ast: Assign, c: Context) = {
        val context = c.asInstanceOf[CodeGenContext]
        val emitter = context.emitter

        val lhsFirstAccess = visit(ast.leftHandSide, AccessContext(context, isLhs = true, isFirstAccess = true)).asInstanceOf[DataObject]
        val rhs = visit(ast.expr, AccessContext(context, isLhs = false, isFirstAccess = true)).asInstanceOf[DataObject]
        val lhsSecondAccess = visit(ast.leftHandSide, AccessContext(context, isLhs = true, isFirstAccess = false)).asInstanceOf[DataObject]

        emitter.printout(lhsFirstAccess.code + rhs.code + lhsSecondAccess.code)
        emitter.printout("\n")
    }

    override def visitReturn(ast: Return, c: Context) = {
        val context = c.asInstanceOf[CodeGenContext]
        val emitter = context.emitter
        val frame = context.frame.get

        getClassMember(gEnv, context.treeName.last, context.treeName.init.last, Method) match {
            case Some(symbol) =>
                emitter.printout(visit(ast.expr, AccessContext(context, isLhs = false, isFirstAccess = true)).asInstanceOf[DataObject].code)
                emitter.printout(emitter.emitRETURN(symbol.dataType.get, frame))
            case None         => throw Undeclared(Method, "")
        }

        c
    }

    override def visitBinaryOp(ast: BinaryOp, c: Context) = {
        val context = c.asInstanceOf[AccessContext]
        val emitter = context.codeGenContext.emitter
        // TODO: ASSUMPTION: (frame == NONE) if (<const expression>: BINOP(op, left, right))
        val frame = context.codeGenContext.frame.get

        val left = visit(ast.left, context).asInstanceOf[DataObject]
        val right = visit(ast.right, context).asInstanceOf[DataObject]

        ast.op match {
            case "+" | "-"                             =>
                (left.dataType, right.dataType) match {
                    case (IntType, IntType)     =>
                        DataObject("", left.code + right.code + emitter.emitADDOP(ast.op, IntType, frame), IntType, isLiteral = false)
                    case (FloatType, FloatType) =>
                        DataObject("", left.code + right.code + emitter.emitADDOP(ast.op, FloatType, frame), FloatType, isLiteral = false)
                    case (IntType, FloatType)   =>
                        DataObject("", left.code + emitter.emitI2F(frame) + right.code + emitter.emitADDOP(ast.op, FloatType, frame), FloatType, isLiteral = false)
                    case (FloatType, IntType)   =>
                        DataObject("", left.code + right.code + emitter.emitI2F(frame) + emitter.emitADDOP(ast.op, FloatType, frame), FloatType, isLiteral = false)
                }
            case "*"                                   =>
                (left.dataType, right.dataType) match {
                    case (IntType, IntType)     =>
                        DataObject("", left.code + right.code + emitter.emitMULOP(ast.op, IntType, frame), IntType, isLiteral = false)
                    case (FloatType, FloatType) =>
                        DataObject("", left.code + right.code + emitter.emitMULOP(ast.op, FloatType, frame), FloatType, isLiteral = false)
                    case (IntType, FloatType)   =>
                        DataObject("", left.code + emitter.emitI2F(frame) + right.code + emitter.emitMULOP(ast.op, FloatType, frame), FloatType, isLiteral = false)
                    case (FloatType, IntType)   =>
                        DataObject("", left.code + right.code + emitter.emitI2F(frame) + emitter.emitMULOP(ast.op, FloatType, frame), FloatType, isLiteral = false)
                }
            case "/"                                   =>
                DataObject(
                    "",
                    left.code + (if (left.dataType == IntType) emitter.emitI2F(frame) else "")
                    + right.code + (if (right.dataType == IntType) emitter.emitI2F(frame) else "")
                    + emitter.emitMULOP(ast.op, FloatType, frame),
                    FloatType, isLiteral = false)
            case "\\"                                  =>
                DataObject("", left.code + right.code + emitter.emitMULOP(ast.op, IntType, frame), IntType, isLiteral = false)
            case "%"                                   =>
                DataObject("", left.code + right.code + emitter.emitMOD(frame), IntType, isLiteral = false)
            case "^"                                   =>
                if (left.isLiteral && right.isLiteral) {
                    val value = left.value.substring(0, left.value.length - 1) + right.value.substring(1, right.value.length)
                    DataObject(value, emitter.emitPUSHCONST(value, StringType, frame), StringType)
                }
                else
                    DataObject(
                        "",
                        emitter.emitSTRBUILDER(frame) + left.code + emitter.emitAPPEND(frame) + right.code + emitter.emitAPPEND(frame) + emitter.emitTOSTRING(frame),
                        StringType, isLiteral = false
                    )
            case "&&"                                  =>
                c match {
                    case ifExprContext: IfExprAccessContext =>
                        val andContext = IfExprAccessContext(ifExprContext.codeGenContext, ifExprContext.thenLabel, ifExprContext.elseLabel)
                        val left = visit(ast.left, andContext).asInstanceOf[DataObject]
                        val right = visit(ast.right, andContext).asInstanceOf[DataObject]
                        DataObject("", left.code + right.code, BoolType, isLiteral = false)
                    case _                                  =>
                        DataObject("", left.code + right.code + emitter.emitANDOP(frame), BoolType, isLiteral = false)
                }
            case "||"                                  =>
                c match {
                    case ifExprContext: IfExprAccessContext =>
                        val label = frame.getNewLabel()
                        val left = visit(ast.left, IfExprAccessContext(ifExprContext.codeGenContext, ifExprContext.thenLabel, label)).asInstanceOf[DataObject]
                        val right = visit(ast.right, IfExprAccessContext(ifExprContext.codeGenContext, ifExprContext.thenLabel, ifExprContext.elseLabel)).asInstanceOf[DataObject]
                        DataObject("", left.code + emitter.emitGOTO(ifExprContext.thenLabel, frame) + emitter.emitLABEL(label, frame) + right.code, BoolType, isLiteral = false)
                    case _                                  =>
                        DataObject("", left.code + right.code + emitter.emitOROP(frame), BoolType, isLiteral = false)
                }
            case "==" | "!=" | ">" | "<" | ">=" | "<=" =>
                c match {
                    case ifExprContext: IfExprAccessContext =>
                        val left = visit(ast.left, AccessContext(ifExprContext.codeGenContext, isLhs = false, isFirstAccess = true)).asInstanceOf[DataObject]
                        val right = visit(ast.right, AccessContext(ifExprContext.codeGenContext, isLhs = false, isFirstAccess = true)).asInstanceOf[DataObject]
                        DataObject(
                            "",
                            left.code + right.code + emitter.emitRELOP(ast.op, if (left.dataType == FloatType || right.dataType == FloatType) FloatType else IntType, ifExprContext.elseLabel, frame),
                            BoolType, isLiteral = false
                        )
                    case _                                  =>
                        DataObject("", left.code + right.code + emitter.emitREOP(ast.op, if (left.dataType == FloatType || right.dataType == FloatType) FloatType else IntType, frame), BoolType, isLiteral = false)
                }
        }
    }

    override def visitUnaryOp(ast: UnaryOp, c: Context) = {
        val context = c.asInstanceOf[AccessContext]
        val emitter = context.codeGenContext.emitter
        val frame = context.codeGenContext.frame.get

        val expr = visit(ast.body, context).asInstanceOf[DataObject]

        ast.op match {
            case "+" => DataObject("", expr.code, expr.dataType, isLiteral = false)
            case "-" => DataObject("", expr.code + emitter.emitNEGOP(expr.dataType, frame), expr.dataType, isLiteral = false)
            case "!" => DataObject("", expr.code + emitter.emitNOT(expr.dataType, frame), expr.dataType, isLiteral = false)
        }
    }

    override def visitNewExpr(ast: NewExpr, c: Context) = {
        val context = c.asInstanceOf[AccessContext]
        val emitter = context.codeGenContext.emitter
        val frame = context.codeGenContext.frame.get

        val newCode = emitter.emitNEW(ClassType(ast.name.name), frame)
        val params = ast.exprs.map(visit(_, context).asInstanceOf[DataObject])
        val invokeInit = emitter.emitINVOKESPECIAL(ast.name.name + "/<init>", MethodType(params.map(_.dataType), VoidType), frame)

        DataObject(ast.name.name, newCode + params.foldLeft("")((L, x) => L + x.code) + invokeInit, ClassType(ast.name.name), isLiteral = false)
    }

    override def visitCallExpr(ast: CallExpr, c: Context) = {
        val context = c.asInstanceOf[AccessContext]
        val emitter = context.codeGenContext.emitter
        val frame = context.codeGenContext.frame.get

        val caller = visit(ast.cName, context).asInstanceOf[DataObject]
        val callerClass = caller.dataType.asInstanceOf[ClassType].classType
        val methodAccess = callerClass + "/" + ast.method.name
        val params = ast.params.map(visit(_, context).asInstanceOf[DataObject])

        getClassMember(gEnv, callerClass, ast.method.name, Method) match {
            case Some(methodSymbol) =>
                DataObject(
                    "",
                    caller.code + params.foldLeft("")((L, x) => L + x.code) +
                    (if (methodSymbol.skind == Static)
                        emitter.emitINVOKESTATIC(methodAccess, MethodType(params.map(_.dataType), methodSymbol.dataType.get), frame)
                    else
                        emitter.emitINVOKEVIRTUAL(methodAccess, MethodType(params.map(_.dataType), methodSymbol.dataType.get), frame)),
                    methodSymbol.dataType.get,
                    isLiteral = false
                )
            case None               => throw Undeclared(Method, ast.method.name)
        }
    }

    override def visitFieldAccess(ast: FieldAccess, c: Context) = {
        val context = c.asInstanceOf[AccessContext]
        val emitter = context.codeGenContext.emitter
        val frame = context.codeGenContext.frame.get


        // TODO: visit 2 times => limit stack 4 > limit stack 2 (double)
        saveStackState(frame)
        val caller = visit(ast.name, AccessContext(context.codeGenContext, isLhs = false, isFirstAccess = true)).asInstanceOf[DataObject]
        val callerClass = caller.dataType.asInstanceOf[ClassType].classType
        val fieldAccess = callerClass + "." + ast.field.name

        getClassMember(gEnv, callerClass, ast.field.name, Attribute) match {
            case Some(attrSymbol) =>
                if (attrSymbol.isConstant) {
                    restoreStackState(frame)
                    DataObject(getConst(attrSymbol.value.get), visit(attrSymbol.value.get, context).asInstanceOf[DataObject].code, attrSymbol.dataType.get)
                } else {
                    DataObject(
                        "",
                        if (context.isLhs) {
                            if (context.isFirstAccess)
                                caller.code
                            else {
                                restoreStackState(frame)
                                if (attrSymbol.skind == Static)
                                    emitter.emitPUTSTATIC(fieldAccess, attrSymbol.dataType.get, frame)
                                else {
                                    emitter.emitPUTFIELD(fieldAccess, attrSymbol.dataType.get, frame)
                                }
                            }
                        } else {
                            if (attrSymbol.skind == Static) {
                                restoreStackState(frame)
                                emitter.emitGETSTATIC(fieldAccess, attrSymbol.dataType.get, frame)
                            }
                            else {
                                caller.code + emitter.emitGETFIELD(fieldAccess, attrSymbol.dataType.get, frame)
                            }
                        },
                        attrSymbol.dataType.get,
                        isLiteral = false
                    )
                }
            case None             => throw Undeclared(Attribute, ast.field.name)
        }
    }

    override def visitArrayCell(ast: ArrayCell, c: Context) = {
        val context = c.asInstanceOf[AccessContext]
        val emitter = context.codeGenContext.emitter
        val frame = context.codeGenContext.frame.get

        saveStackState(frame)
        val array = visit(ast.arr, AccessContext(context.codeGenContext, isLhs = false, isFirstAccess = true)).asInstanceOf[DataObject]
        val index = visit(ast.idx, AccessContext(context.codeGenContext, isLhs = false, isFirstAccess = true)).asInstanceOf[DataObject]
        val elemType = array.dataType.asInstanceOf[ArrayType].eleType

        DataObject(
            "",
            if (context.isLhs) {
                if (context.isFirstAccess)
                    array.code + index.code
                else {
                    restoreStackState(frame)
                    emitter.emitASTORE(elemType, frame)
                }
            } else {
                array.code + index.code + emitter.emitALOAD(elemType, frame)
            },
            elemType,
            isLiteral = false
        )
    }

    override def visitId(ast: Id, c: Context) = {
        val context = c.asInstanceOf[AccessContext]
        val emitter = context.codeGenContext.emitter

        context.codeGenContext.frame match {
            case Some(frame) =>
                getIdentifier(gEnv, context.codeGenContext.treeName, ast.name) match {
                    case Some(symbol) =>
                        if (symbol.isConstant) {
                            DataObject(getConst(symbol.value.get), visit(symbol.value.get, context).asInstanceOf[DataObject].code, symbol.dataType.get)
                        } else {
                            DataObject(
                                "",
                                if (context.isLhs) {
                                    if (context.isFirstAccess) {
                                        ""
                                    }
                                    else
                                        emitter.emitWRITEVAR(symbol.index, symbol.name, symbol.dataType.get, frame)
                                }
                                else
                                    emitter.emitREADVAR(symbol.index, symbol.name, symbol.dataType.get, frame)
                                ,
                                symbol.dataType.get,
                                isLiteral = false
                            )
                        }
                    case None         =>
                        getClass(gEnv, ast.name) match {
                            case Some(_) =>
                                DataObject(ast.name, "", ClassType(ast.name), isLiteral = false)
                            case None    => throw Undeclared(Identifier, ast.name)
                        }
                }
            case None        =>
                //TODO: final a = 4; final b = this.a; ?????
                throw Undeclared(Identifier, ast.name)
        }
    }


    override def visitIntLiteral(ast: IntLiteral, c: Context) = {
        val context = c.asInstanceOf[AccessContext]
        val emitter = context.codeGenContext.emitter

        DataObject(
            ast.value.toString,
            context.codeGenContext.frame match {
                case Some(frame) => emitter.emitPUSHICONST(ast.value.toString, frame)
                case None        => ""
            },
            IntType
        )

    }

    override def visitFloatLiteral(ast: FloatLiteral, c: Context) = {
        val context = c.asInstanceOf[AccessContext]
        val emitter = context.codeGenContext.emitter

        DataObject(
            ast.value.toString,
            context.codeGenContext.frame match {
                case Some(frame) => emitter.emitPUSHFCONST(ast.value.toString, frame)
                case None        => ""
            },
            FloatType
        )
    }

    override def visitBooleanLiteral(ast: BooleanLiteral, c: Context) = {
        val context = c.asInstanceOf[AccessContext]
        val emitter = context.codeGenContext.emitter

        DataObject(
            if (ast.value) "1" else "0",
            context.codeGenContext.frame match {
                case Some(frame) => emitter.emitPUSHICONST(ast.value.toString, frame)
                case None        => ""
            },
            BoolType
        )
    }

    override def visitStringLiteral(ast: StringLiteral, c: Context) = {
        val context = c.asInstanceOf[AccessContext]
        val emitter = context.codeGenContext.emitter

        DataObject(
            ast.value,
            context.codeGenContext.frame match {
                case Some(frame) => emitter.emitPUSHCONST(ast.value, StringType, frame)
                case None        => ""
            },
            StringType
        )
    }

    override def visitSelfLiteral(ast: SelfLiteral.type, c: Context) = {
        val context = c.asInstanceOf[AccessContext]
        val emitter = context.codeGenContext.emitter
        val className = context.codeGenContext.treeName.last

        DataObject(
            className,
            context.codeGenContext.frame match {
                case Some(frame) => emitter.emitREADVAR(0, "this", ClassType(className), frame)
                case None        => ""
            },
            ClassType(className)
        )
    }

    override def visitNullLiteral(ast: NullLiteral.type, c: Context) = {
        val context = c.asInstanceOf[AccessContext]
        val emitter = context.codeGenContext.emitter

        DataObject("null",
            context.codeGenContext.frame match {
                case Some(frame) => emitter.emitPUSHCONST("", NullType, frame)
                case None        => ""
            },
            NullType
        )
    }

}