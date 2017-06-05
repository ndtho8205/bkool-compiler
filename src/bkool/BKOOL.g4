/**
 * Student name:	Nguyen Duc Tho
 * Student ID:	  1413817
 */

grammar BKOOL;

/*
@lexer::header{
	package bkool.parser;
}
*/

@lexer::members{
@Override
public Token emit() {
	switch (getType()) {
	case UNCLOSE_STRING:
		Token result = super.emit();
		// you'll need to define this method
		throw new UncloseString(result.getText());

	case ILLEGAL_ESCAPE:
		result = super.emit();
		throw new IllegalEscape(result.getText());
	case ERROR_CHAR:
		result = super.emit();
		throw new ErrorToken(result.getText());
	default:
		return super.emit();
	}
}
}


/*
@parser::header{
	package bkool.parser;
}
*/

options{
	language=Java;
}


program
	:   classDeclaration+ EOF ;

classDeclaration
	:	'class' ID ('extends' ID)? '{' memberDeclaration* '}';

memberDeclaration
	:	attributeDeclaration
	|	methodDeclaration
	;

attributeDeclaration
	:   'static'? variableDeclaration
	|   'static'? constDeclaration
	;

constDeclaration
	:	 'final' typeType ID '=' expression ';';

variableDeclaration
	:	variableDeclarator ';';

methodDeclaration
	:	typeType 'static'? ID parameterList blockStatement
	|	ID parameterList blockStatement
	;

parameterList
	: '(' (variableDeclarator (';' variableDeclarator)*)? ')'
	;

typeType
	:	primitiveType
	|	arrayType
	|	classType
	;

primitiveType
	:	'int'
	|	'float'
	|	'boolean'
	|	'string'
	|	'void'
	;

classType
	:	ID;

arrayType
	:	(primitiveType | classType) LSBRACE INTLIT RSBRACE;

//	Statements
blockStatement
	:	'{' (declaration)* statement* '}'
	;

declaration
	:	'static'? variableDeclaration
	|	'static'? constDeclaration
	;

statement
	:	blockStatement                                          #BlockStmt
	|	expression '.' ID arguments ';'                         #CallStmt
	|	lhsAssign ':=' expression ';'                           #AssignStmt
	|	'if' expression 'then' statement ('else' statement)?    #IfStmt
	|	'for' forControl statement                              #ForStmt
	|	'break' ';'                                             #BreakStmt
	|	'continue' ';'                                          #ContinueStmt
	|	'return' expression? ';'                                #ReturnStmt
	;

lhsAssign
	:   ID                                                      #VariableAccess
	|   expression '.' ID                                       #InstanceAttributeAccess
	|   ID '.' ID                                               #StaticAttributeAccess
	|   expression '[' expression ']'                           #ArrayElementAccess
	;

forControl
	:	ID ':=' expression ('to'|'downto') expression 'do'
	;

variableDeclarator
	: ID (',' ID)* ':' typeType
	;

//  Expressions
arguments
	:	'(' expressionList? ')'
	;

expressionList
	:	expression (',' expression)*
	;

expression
	:	term
	|	term ('=='|'!=') term
	;

term
	:	primary
	|	primary ('<'|'>'|'<='|'>=') primary
	;

primary
	:	'(' expression ')'                      #ParensExpr
	|	literal                                 #LiteralExpr
	|	ID                                      #IdExpr
	|	'new' ID arguments                      #ObjectCreationExpr
	|	primary '.' ID                          #AttributeExpr
	|	primary '.' ID arguments                #CallMethodExpr
	|	primary '[' expression ']'              #ArrayElementExpr
	|	('+'|'-') primary                       #SignExpr
	|	'!' primary                             #NotExpr
	|	primary '^' primary                     #PowerExpr
	|	primary ('*'|'/'|'\\'|'%') primary      #MulExpr
	|	primary ('+'|'-') primary               #AddExpr
	|	primary ('&&'|'||') primary             #AndExpr
	;

literal
	:	INTLIT
	|	FLOATLIT
	|	'true'
	|   'false'
	|	STRINGLIT
	|   'this'
	|	'nil'
	;

//	LEXER

ASSIGN			:	':=';
CONST			:	'=';

//  Keywords

BOOLEAN			:	'boolean';
BREAK			:	'break';
CLASS			:	'class';
CONTINUE		:	'continue';
DO              :	'do';
ELSE			:	'else';
EXTENDS			:	'extends';
FLOAT			:	'float';
IF				:	'if';
INT				:	'int';
STRING			:	'string';
THEN			:	'then';
FOR				:	'for';
RETURN			:	'return';
VOID			:	'void';
NIL				:	'nil';
THIS			:	'this';
FINAL			:	'final';
STATIC			:	'static';
TO				:	'to';
DOWNTO			:	'downto';

//  Operators

ADD				:	'+';
SUB				:	'-';
MUL				:	'*';
IDIV			:	'\\';
FDIV			:	'/';
MOD				:	'%';
EQUAL			:	'==';
NEQUAL			:	'!=';
LT				:	'<';
GT				:	'>';
LE				:	'<=';
GE				:	'>=';
OR				:	'||';
AND				:	'&&';
NOT				:	'!';
CONCAT			:	'^';
OBJCREATE		:	'new';

//  Seperators

LSBRACE			:	'[';
RSBRACE			:	']';
LPAREN			:	'{';
RPAREN			:	'}';
LBRACE			:	'(';
RBRACE			:	')';
SEMI			:	';';
COLON			:	':';
DOT				:	'.';
COMMA			:	',';

//  Identifiers

ID	:	[a-zA-Z_][a-zA-Z0-9_]*
	;

//  Integer Literal

INTLIT
	:	[0-9]+
	;

//  Floating-point Literal

FLOATLIT
	:	[0-9]+ (DecimalPart | DecimalPart? ExponentPart)
	;

fragment
DecimalPart
	:	'.'[0-9]*
	;

fragment
ExponentPart
	:	[Ee][+-]?[0-9]+
	;

//  Boolean Literal

BOOLLIT
	:	'true'
	|	'false'
	;

//  String Literals

STRINGLIT
	:	'"' StringCharacter*? '"'
	;

fragment
StringCharacter
	:	~["\\\r\n]
	|	EscapeSequence
	;

fragment
EscapeSequence
	:	'\\'[bfrnt"\\]
	;

//  Whitespace, comments

WS	:	[ \t\r\n]+		-> skip
	;

BLOCK_CMT
	:	'/*'.*?'*/'		-> skip
	;

LINE_CMT
	:	'%%'~[\r\n]*	-> skip
	;

//  Lexical errors

UNCLOSE_STRING
	:	'"' StringCharacter*
	;

ILLEGAL_ESCAPE
	:	'"' StringCharacter* ('\\' ~[bfrnt"\\])
	;

ERROR_CHAR
	:	.
	;
