package bkool.astgen

import bkool.parser.BKOOLParser._
import bkool.parser._
import bkool.utils.{Static, _}
import org.antlr.v4.runtime.tree._

import scala.collection.JavaConverters._

class ASTGeneration extends BKOOLBaseVisitor[Object] {

  override def visitProgram(ctx: BKOOLParser.ProgramContext) =
      Program(ctx.classDeclaration().asScala.toList.map(_.accept(this).asInstanceOf[ClassDecl]))

    override def visitClassDeclaration(ctx: ClassDeclarationContext) =
        ClassDecl(
            Id(ctx.ID(0).getText),
            if (ctx.ID().size() < 2) null else Id(ctx.ID(1).getText),
            ctx.memberDeclaration().asScala.toList.foldLeft(List[MemDecl]())(
                (L, c) => L ::: c.accept(this).asInstanceOf[List[MemDecl]]
            )
        )

    override def visitMemberDeclaration(ctx: MemberDeclarationContext) =
        if (ctx.attributeDeclaration() != null)
            ctx.attributeDeclaration().accept(this).asInstanceOf[List[AttributeDecl]]
        else
    ctx.methodDeclaration().accept(this).asInstanceOf[List[MethodDecl]]

    override def visitAttributeDeclaration(ctx: AttributeDeclarationContext) =
        (if (ctx.variableDeclaration() != null) ctx.variableDeclaration()
        else ctx.constDeclaration())
            .accept(this).asInstanceOf[List[Decl]].map(
                AttributeDecl(
                    if (ctx.getChild(0).getText.equals("static")) Static else Instance,
                    _
                )
            )

    override def visitConstDeclaration(ctx: ConstDeclarationContext) =
        List(
            ConstDecl(
                Id(ctx.ID().getText),
                ctx.typeType().accept(this).asInstanceOf[Type],
                ctx.expression().accept(this).asInstanceOf[Expr]
            )
        )

    override def visitVariableDeclaration(ctx: VariableDeclarationContext) =
        ctx.variableDeclarator().accept(this).asInstanceOf[List[VarDecl]]

    override def visitMethodDeclaration(ctx: MethodDeclarationContext) =
        List(
            MethodDecl(
                if (ctx.getChild(1).getText.equals("static")) Static else Instance,
                Id(ctx.ID().getText),
                ctx.parameterList().accept(this).asInstanceOf[List[ParamDecl]],
                if (ctx.typeType() != null)
                    ctx.typeType().accept(this).asInstanceOf[Type]
                else null,
                ctx.blockStatement().accept(this).asInstanceOf[Stmt]
            )
        )

    override def visitParameterList(ctx: ParameterListContext) =
        ctx.variableDeclarator().asScala.toList.foldLeft(List[ParamDecl]())(
            (L, c) => L ::: c.accept(this).asInstanceOf[List[VarDecl]].map(
                x => ParamDecl(x.variable, x.varType)
            )
        )

    override def visitTypeType(ctx: TypeTypeContext) =
        if (ctx.primitiveType() != null)
            ctx.primitiveType().accept(this).asInstanceOf[Type]
        else if (ctx.arrayType() != null)
            ctx.arrayType().accept(this).asInstanceOf[Type]
        else
            ctx.classType().accept(this).asInstanceOf[Type]

    override def visitPrimitiveType(ctx: PrimitiveTypeContext) =
        ctx.getChild(0).getText match {
            case "int" => IntType
            case "float" => FloatType
            case "boolean" => BoolType
            case "string" => StringType
            case "void" => VoidType
        }

    override def visitArrayType(ctx: ArrayTypeContext) =
        ArrayType(
            IntLiteral(ctx.INTLIT().getText.toInt),
            if (ctx.classType() != null)
                ctx.classType().accept(this).asInstanceOf[Type]
            else
                ctx.primitiveType().accept(this).asInstanceOf[Type]
        )

    override def visitClassType(ctx: ClassTypeContext) =
        ClassType(ctx.ID().getText)

    override def visitBlockStatement(ctx: BlockStatementContext) =
        Block(
            ctx.declaration().asScala.toList.foldLeft(List[Decl]())(
                (L, c) => L ::: c.accept(this).asInstanceOf[List[Decl]]
            ),
            ctx.statement().asScala.toList.map(_.accept(this).asInstanceOf[Stmt])
        )

    override def visitDeclaration(ctx: DeclarationContext) =
        if (ctx.constDeclaration() != null)
            ctx.constDeclaration().accept(this).asInstanceOf[List[Decl]]
        else
            ctx.variableDeclaration().accept(this).asInstanceOf[List[Decl]]

    override def visitContinueStmt(ctx: ContinueStmtContext) = Continue

    override def visitIfStmt(ctx: IfStmtContext) =
        If(
            ctx.expression().accept(this).asInstanceOf[Expr],
            ctx.statement(0).accept(this).asInstanceOf[Stmt],
            elseStmt = try {
                Some(ctx.statement(1).accept(this).asInstanceOf[Stmt])
            } catch {
                case e: Exception => None
            }
        )

    override def visitAssignStmt(ctx: AssignStmtContext) =
        Assign(
            ctx.lhsAssign().accept(this).asInstanceOf[LHS],
            ctx.expression().accept(this).asInstanceOf[Expr]
        )

    override def visitBreakStmt(ctx: BreakStmtContext) = Break

    override def visitBlockStmt(ctx: BlockStmtContext) =
        ctx.blockStatement().accept(this).asInstanceOf[Block]

    override def visitCallStmt(ctx: CallStmtContext) =
        Call(
            ctx.expression().accept(this).asInstanceOf[Expr],
            Id(ctx.ID().getText),
            ctx.arguments().accept(this).asInstanceOf[List[Expr]]
        )

    override def visitForStmt(ctx: ForStmtContext) =
        For(
            ctx.forControl().ID().getText,
            ctx.forControl().expression(0).accept(this).asInstanceOf[Expr],
            ctx.forControl().getChild(3).getText.equals("to"),
            ctx.forControl().expression(1).accept(this).asInstanceOf[Expr],
            ctx.statement().accept(this).asInstanceOf[Stmt]
        )

    override def visitReturnStmt(ctx: ReturnStmtContext) =
        Return(
            ctx.expression().accept(this).asInstanceOf[Expr]
        )

    override def visitStaticAttributeAccess(ctx: StaticAttributeAccessContext) =
        FieldAccess(
            Id(ctx.ID(0).getText),
            Id(ctx.ID(1).getText)
        )

    override def visitInstanceAttributeAccess(ctx: InstanceAttributeAccessContext) =
        FieldAccess(
            ctx.expression().accept(this).asInstanceOf[Expr],
            Id(ctx.ID().getText)
        )

    override def visitVariableAccess(ctx: VariableAccessContext) =
        Id(ctx.ID().getText)

    override def visitArrayElementAccess(ctx: ArrayElementAccessContext) =
        ArrayCell(
            ctx.expression(0).accept(this).asInstanceOf[Expr],
            ctx.expression(1).accept(this).asInstanceOf[Expr]
        )

    override def visitForControl(ctx: ForControlContext): Object = super.visitForControl(ctx)

    override def visitVariableDeclarator(ctx: VariableDeclaratorContext) = {
        val varType = ctx.typeType().accept(this).asInstanceOf[Type]
        ctx.ID().asScala.toList.map(x => VarDecl(Id(x.getText), varType))
    }

    override def visitArguments(ctx: ArgumentsContext) =
        if (ctx.expressionList() != null)
            ctx.expressionList().accept(this).asInstanceOf[List[Expr]]
        else List[Expr]()

    override def visitExpressionList(ctx: ExpressionListContext) =
        ctx.expression().asScala.toList.map(
            _.accept(this).asInstanceOf[Expr]
        )

    override def visitExpression(ctx: ExpressionContext) =
        if (ctx.getChildCount < 3)
            ctx.term(0).accept(this).asInstanceOf[Expr]
        else
            BinaryOp(
                ctx.getChild(1).getText,
                ctx.term(0).accept(this).asInstanceOf[Expr],
                ctx.term(1).accept(this).asInstanceOf[Expr]
            )

    override def visitTerm(ctx: TermContext) =
        if (ctx.getChildCount < 3)
            ctx.primary(0).accept(this).asInstanceOf[Expr]
        else
            BinaryOp(
                ctx.getChild(1).getText,
                ctx.primary(0).accept(this).asInstanceOf[Expr],
                ctx.primary(1).accept(this).asInstanceOf[Expr]
            )

    override def visitObjectCreationExpr(ctx: ObjectCreationExprContext) =
        NewExpr(Id(ctx.ID().getText), ctx.arguments().accept(this).asInstanceOf[List[Expr]])

    override def visitMulExpr(ctx: MulExprContext) =
        BinaryOp(
            ctx.getChild(1).getText,
            ctx.primary(0).accept(this).asInstanceOf[Expr],
            ctx.primary(1).accept(this).asInstanceOf[Expr]
        )

    override def visitAndExpr(ctx: AndExprContext) =
        BinaryOp(
            ctx.getChild(1).getText,
            ctx.primary(0).accept(this).asInstanceOf[Expr],
            ctx.primary(1).accept(this).asInstanceOf[Expr]
        )

    override def visitIdExpr(ctx: IdExprContext) =
        Id(ctx.ID().getText)

    override def visitCallMethodExpr(ctx: CallMethodExprContext) =
        CallExpr(
            ctx.primary().accept(this).asInstanceOf[Expr],
            Id(ctx.ID().getText),
            ctx.arguments().accept(this).asInstanceOf[List[Expr]]
        )

    override def visitAttributeExpr(ctx: AttributeExprContext) =
        FieldAccess(ctx.primary().accept(this).asInstanceOf[Expr], Id(ctx.ID().getText))

    override def visitAddExpr(ctx: AddExprContext) =
        BinaryOp(
            ctx.getChild(1).getText,
            ctx.primary(0).accept(this).asInstanceOf[Expr],
            ctx.primary(1).accept(this).asInstanceOf[Expr]
        )

    override def visitSignExpr(ctx: SignExprContext) =
        UnaryOp(
            ctx.getChild(0).getText,
            ctx.primary().accept(this).asInstanceOf[Expr]
        )

    override def visitPowerExpr(ctx: PowerExprContext) =
        BinaryOp(
            "^",
            ctx.primary(0).accept(this).asInstanceOf[Expr],
            ctx.primary(1).accept(this).asInstanceOf[Expr]
        )

    override def visitArrayElementExpr(ctx: ArrayElementExprContext) =
        ArrayCell(
            ctx.primary().accept(this).asInstanceOf[Expr],
            ctx.expression().accept(this).asInstanceOf[Expr]
        )

    override def visitParensExpr(ctx: ParensExprContext) =
        ctx.expression().accept(this).asInstanceOf[Expr]

    override def visitLiteralExpr(ctx: LiteralExprContext) =
        ctx.literal().accept(this).asInstanceOf[Literal]

    override def visitNotExpr(ctx: NotExprContext) =
        UnaryOp("!", ctx.primary().accept(this).asInstanceOf[Expr])

    override def visitLiteral(ctx: LiteralContext) =
        if (ctx.INTLIT() != null)
            IntLiteral(ctx.INTLIT().getText.toInt)
        else if (ctx.FLOATLIT() != null)
            FloatLiteral(ctx.FLOATLIT().getText.toFloat)
        else if (ctx.STRINGLIT() != null)
            StringLiteral(ctx.STRINGLIT().getText)
        else ctx.getChild(0).getText match {
            case "true" => BooleanLiteral(true)
            case "false" => BooleanLiteral(false)
            case "this" => SelfLiteral
            case "nil" => NullLiteral
        }

    override def visitTerminal(node: TerminalNode) = node.getText

}