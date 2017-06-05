// Generated from /home/ndtho8205/Desktop/untitled/src/bkool/BKOOL.g4 by ANTLR 4.5.3
package bkool.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link BKOOLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface BKOOLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(BKOOLParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(BKOOLParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#memberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberDeclaration(BKOOLParser.MemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#attributeDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeDeclaration(BKOOLParser.AttributeDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#constDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstDeclaration(BKOOLParser.ConstDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(BKOOLParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#methodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclaration(BKOOLParser.MethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(BKOOLParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#typeType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeType(BKOOLParser.TypeTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(BKOOLParser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#classType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassType(BKOOLParser.ClassTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#arrayType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(BKOOLParser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatement(BKOOLParser.BlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(BKOOLParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BlockStmt}
	 * labeled alternative in {@link BKOOLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStmt(BKOOLParser.BlockStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CallStmt}
	 * labeled alternative in {@link BKOOLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallStmt(BKOOLParser.CallStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignStmt}
	 * labeled alternative in {@link BKOOLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStmt(BKOOLParser.AssignStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfStmt}
	 * labeled alternative in {@link BKOOLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(BKOOLParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ForStmt}
	 * labeled alternative in {@link BKOOLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(BKOOLParser.ForStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BreakStmt}
	 * labeled alternative in {@link BKOOLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStmt(BKOOLParser.BreakStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ContinueStmt}
	 * labeled alternative in {@link BKOOLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStmt(BKOOLParser.ContinueStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ReturnStmt}
	 * labeled alternative in {@link BKOOLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(BKOOLParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code VariableAccess}
	 * labeled alternative in {@link BKOOLParser#lhsAssign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableAccess(BKOOLParser.VariableAccessContext ctx);
	/**
	 * Visit a parse tree produced by the {@code InstanceAttributeAccess}
	 * labeled alternative in {@link BKOOLParser#lhsAssign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceAttributeAccess(BKOOLParser.InstanceAttributeAccessContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StaticAttributeAccess}
	 * labeled alternative in {@link BKOOLParser#lhsAssign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStaticAttributeAccess(BKOOLParser.StaticAttributeAccessContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayElementAccess}
	 * labeled alternative in {@link BKOOLParser#lhsAssign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayElementAccess(BKOOLParser.ArrayElementAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#forControl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForControl(BKOOLParser.ForControlContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#variableDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarator(BKOOLParser.VariableDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(BKOOLParser.ArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(BKOOLParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(BKOOLParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(BKOOLParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ObjectCreationExpr}
	 * labeled alternative in {@link BKOOLParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectCreationExpr(BKOOLParser.ObjectCreationExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulExpr}
	 * labeled alternative in {@link BKOOLParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulExpr(BKOOLParser.MulExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AndExpr}
	 * labeled alternative in {@link BKOOLParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpr(BKOOLParser.AndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdExpr}
	 * labeled alternative in {@link BKOOLParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExpr(BKOOLParser.IdExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CallMethodExpr}
	 * labeled alternative in {@link BKOOLParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallMethodExpr(BKOOLParser.CallMethodExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AttributeExpr}
	 * labeled alternative in {@link BKOOLParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeExpr(BKOOLParser.AttributeExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddExpr}
	 * labeled alternative in {@link BKOOLParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddExpr(BKOOLParser.AddExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SignExpr}
	 * labeled alternative in {@link BKOOLParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSignExpr(BKOOLParser.SignExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PowerExpr}
	 * labeled alternative in {@link BKOOLParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowerExpr(BKOOLParser.PowerExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ArrayElementExpr}
	 * labeled alternative in {@link BKOOLParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayElementExpr(BKOOLParser.ArrayElementExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParensExpr}
	 * labeled alternative in {@link BKOOLParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParensExpr(BKOOLParser.ParensExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LiteralExpr}
	 * labeled alternative in {@link BKOOLParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralExpr(BKOOLParser.LiteralExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NotExpr}
	 * labeled alternative in {@link BKOOLParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpr(BKOOLParser.NotExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link BKOOLParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(BKOOLParser.LiteralContext ctx);
}