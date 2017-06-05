package bkool.utils

/**
 * @author nhphung
 */
class BaseVisitor extends Visitor {
  def visitProgram(ast: Program, c: Context): Object = c
  def visitVarDecl(ast: VarDecl, c: Context): Object  = c
  def visitConstDecl(ast: ConstDecl, c: Context): Object  = c
  def visitParamDecl(ast: ParamDecl, c: Context): Object  = c
  def visitClassDecl(ast: ClassDecl, c: Context): Object  = c
  def visitMethodDecl(ast: MethodDecl, c: Context): Object = c
  def visitAttributeDecl(ast: AttributeDecl, c: Context): Object = c
  def visitInstance(ast: Instance.type, c: Context): Object = c
  def visitStatic(ast: Static.type, c: Context): Object = c

  def visitIntType(ast:IntType.type,c:Context):Object = c
  def visitFloatType(ast:FloatType.type,c:Context):Object = c
  def visitStringType(ast:StringType.type,c:Context):Object = c
  def visitBoolType(ast:BoolType.type,c:Context):Object = c
  def visitVoidType(ast:VoidType.type,c:Context):Object = c
  def visitArrayType(ast:ArrayType,c:Context):Object = c
  def visitClassType(ast:ClassType,c:Context):Object = c

  def visitBinaryOp(ast: BinaryOp, c: Context): Object = c
  def visitUnaryOp(ast: UnaryOp, c: Context): Object = c
  def visitNewExpr(ast: NewExpr, c: Context): Object = c
  def visitCallExpr(ast: CallExpr, c: Context): Object = c
  def visitId(ast: Id, c: Context): Object = c
  def visitArrayCell(ast: ArrayCell, c: Context): Object = c
  def visitFieldAccess(ast: FieldAccess, c: Context): Object = c
  def visitBlock(ast: Block, c: Context): Object = c
  def visitAssign(ast: Assign, c: Context): Object = c
  def visitIf(ast: If, c: Context): Object = c
  def visitCall(ast: Call, c: Context): Object = c
  def visitFor(ast: For, c: Context): Object = c
  def visitBreak(ast: Break.type, c: Context): Object = c
  def visitContinue(ast: Continue.type, c: Context): Object = c
  def visitReturn(ast: Return, c: Context): Object = c
  def visitIntLiteral(ast: IntLiteral, c: Context): Object = c
  def visitFloatLiteral(ast: FloatLiteral, c: Context): Object = c
  def visitStringLiteral(ast: StringLiteral, c: Context): Object = c
  def visitBooleanLiteral(ast: BooleanLiteral, c: Context): Object = c
  def visitNullLiteral(ast: NullLiteral.type, c: Context): Object = c
  def visitSelfLiteral(ast: SelfLiteral.type, c: Context): Object = c
}
