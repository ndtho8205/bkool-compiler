// Generated from /home/ndtho8205/Desktop/untitled/src/bkool/BKOOL.g4 by ANTLR 4.5.3
package bkool.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BKOOLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, ASSIGN=3, CONST=4, BOOLEAN=5, BREAK=6, CLASS=7, CONTINUE=8, 
		DO=9, ELSE=10, EXTENDS=11, FLOAT=12, IF=13, INT=14, STRING=15, THEN=16, 
		FOR=17, RETURN=18, VOID=19, NIL=20, THIS=21, FINAL=22, STATIC=23, TO=24, 
		DOWNTO=25, ADD=26, SUB=27, MUL=28, IDIV=29, FDIV=30, MOD=31, EQUAL=32, 
		NEQUAL=33, LT=34, GT=35, LE=36, GE=37, OR=38, AND=39, NOT=40, CONCAT=41, 
		OBJCREATE=42, LSBRACE=43, RSBRACE=44, LPAREN=45, RPAREN=46, LBRACE=47, 
		RBRACE=48, SEMI=49, COLON=50, DOT=51, COMMA=52, ID=53, INTLIT=54, FLOATLIT=55, 
		BOOLLIT=56, STRINGLIT=57, WS=58, BLOCK_CMT=59, LINE_CMT=60, UNCLOSE_STRING=61, 
		ILLEGAL_ESCAPE=62, ERROR_CHAR=63;
	public static final int
		RULE_program = 0, RULE_classDeclaration = 1, RULE_memberDeclaration = 2, 
		RULE_attributeDeclaration = 3, RULE_constDeclaration = 4, RULE_variableDeclaration = 5, 
		RULE_methodDeclaration = 6, RULE_parameterList = 7, RULE_typeType = 8, 
		RULE_primitiveType = 9, RULE_classType = 10, RULE_arrayType = 11, RULE_blockStatement = 12, 
		RULE_declaration = 13, RULE_statement = 14, RULE_lhsAssign = 15, RULE_forControl = 16, 
		RULE_variableDeclarator = 17, RULE_arguments = 18, RULE_expressionList = 19, 
		RULE_expression = 20, RULE_term = 21, RULE_primary = 22, RULE_literal = 23;
	public static final String[] ruleNames = {
		"program", "classDeclaration", "memberDeclaration", "attributeDeclaration", 
		"constDeclaration", "variableDeclaration", "methodDeclaration", "parameterList", 
		"typeType", "primitiveType", "classType", "arrayType", "blockStatement", 
		"declaration", "statement", "lhsAssign", "forControl", "variableDeclarator", 
		"arguments", "expressionList", "expression", "term", "primary", "literal"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'true'", "'false'", "':='", "'='", "'boolean'", "'break'", "'class'", 
		"'continue'", "'do'", "'else'", "'extends'", "'float'", "'if'", "'int'", 
		"'string'", "'then'", "'for'", "'return'", "'void'", "'nil'", "'this'", 
		"'final'", "'static'", "'to'", "'downto'", "'+'", "'-'", "'*'", "'\\'", 
		"'/'", "'%'", "'=='", "'!='", "'<'", "'>'", "'<='", "'>='", "'||'", "'&&'", 
		"'!'", "'^'", "'new'", "'['", "']'", "'{'", "'}'", "'('", "')'", "';'", 
		"':'", "'.'", "','"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, "ASSIGN", "CONST", "BOOLEAN", "BREAK", "CLASS", "CONTINUE", 
		"DO", "ELSE", "EXTENDS", "FLOAT", "IF", "INT", "STRING", "THEN", "FOR", 
		"RETURN", "VOID", "NIL", "THIS", "FINAL", "STATIC", "TO", "DOWNTO", "ADD", 
		"SUB", "MUL", "IDIV", "FDIV", "MOD", "EQUAL", "NEQUAL", "LT", "GT", "LE", 
		"GE", "OR", "AND", "NOT", "CONCAT", "OBJCREATE", "LSBRACE", "RSBRACE", 
		"LPAREN", "RPAREN", "LBRACE", "RBRACE", "SEMI", "COLON", "DOT", "COMMA", 
		"ID", "INTLIT", "FLOATLIT", "BOOLLIT", "STRINGLIT", "WS", "BLOCK_CMT", 
		"LINE_CMT", "UNCLOSE_STRING", "ILLEGAL_ESCAPE", "ERROR_CHAR"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "BKOOL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public BKOOLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(BKOOLParser.EOF, 0); }
		public List<ClassDeclarationContext> classDeclaration() {
			return getRuleContexts(ClassDeclarationContext.class);
		}
		public ClassDeclarationContext classDeclaration(int i) {
			return getRuleContext(ClassDeclarationContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(48);
				classDeclaration();
				}
				}
				setState(51); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CLASS );
			setState(53);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDeclarationContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(BKOOLParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(BKOOLParser.ID, i);
		}
		public List<MemberDeclarationContext> memberDeclaration() {
			return getRuleContexts(MemberDeclarationContext.class);
		}
		public MemberDeclarationContext memberDeclaration(int i) {
			return getRuleContext(MemberDeclarationContext.class,i);
		}
		public ClassDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitClassDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDeclarationContext classDeclaration() throws RecognitionException {
		ClassDeclarationContext _localctx = new ClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_classDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			match(CLASS);
			setState(56);
			match(ID);
			setState(59);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(57);
				match(EXTENDS);
				setState(58);
				match(ID);
				}
			}

			setState(61);
			match(LPAREN);
			setState(65);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << FLOAT) | (1L << INT) | (1L << STRING) | (1L << VOID) | (1L << FINAL) | (1L << STATIC) | (1L << ID))) != 0)) {
				{
				{
				setState(62);
				memberDeclaration();
				}
				}
				setState(67);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(68);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MemberDeclarationContext extends ParserRuleContext {
		public AttributeDeclarationContext attributeDeclaration() {
			return getRuleContext(AttributeDeclarationContext.class,0);
		}
		public MethodDeclarationContext methodDeclaration() {
			return getRuleContext(MethodDeclarationContext.class,0);
		}
		public MemberDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memberDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitMemberDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MemberDeclarationContext memberDeclaration() throws RecognitionException {
		MemberDeclarationContext _localctx = new MemberDeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_memberDeclaration);
		try {
			setState(72);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(70);
				attributeDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(71);
				methodDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AttributeDeclarationContext extends ParserRuleContext {
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public ConstDeclarationContext constDeclaration() {
			return getRuleContext(ConstDeclarationContext.class,0);
		}
		public AttributeDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitAttributeDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeDeclarationContext attributeDeclaration() throws RecognitionException {
		AttributeDeclarationContext _localctx = new AttributeDeclarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_attributeDeclaration);
		int _la;
		try {
			setState(82);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(75);
				_la = _input.LA(1);
				if (_la==STATIC) {
					{
					setState(74);
					match(STATIC);
					}
				}

				setState(77);
				variableDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(79);
				_la = _input.LA(1);
				if (_la==STATIC) {
					{
					setState(78);
					match(STATIC);
					}
				}

				setState(81);
				constDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstDeclarationContext extends ParserRuleContext {
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(BKOOLParser.ID, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ConstDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitConstDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstDeclarationContext constDeclaration() throws RecognitionException {
		ConstDeclarationContext _localctx = new ConstDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_constDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			match(FINAL);
			setState(85);
			typeType();
			setState(86);
			match(ID);
			setState(87);
			match(CONST);
			setState(88);
			expression();
			setState(89);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclarationContext extends ParserRuleContext {
		public VariableDeclaratorContext variableDeclarator() {
			return getRuleContext(VariableDeclaratorContext.class,0);
		}
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_variableDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			variableDeclarator();
			setState(92);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodDeclarationContext extends ParserRuleContext {
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(BKOOLParser.ID, 0); }
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public BlockStatementContext blockStatement() {
			return getRuleContext(BlockStatementContext.class,0);
		}
		public MethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitMethodDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodDeclarationContext methodDeclaration() throws RecognitionException {
		MethodDeclarationContext _localctx = new MethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_methodDeclaration);
		int _la;
		try {
			setState(106);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(94);
				typeType();
				setState(96);
				_la = _input.LA(1);
				if (_la==STATIC) {
					{
					setState(95);
					match(STATIC);
					}
				}

				setState(98);
				match(ID);
				setState(99);
				parameterList();
				setState(100);
				blockStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(102);
				match(ID);
				setState(103);
				parameterList();
				setState(104);
				blockStatement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterListContext extends ParserRuleContext {
		public List<VariableDeclaratorContext> variableDeclarator() {
			return getRuleContexts(VariableDeclaratorContext.class);
		}
		public VariableDeclaratorContext variableDeclarator(int i) {
			return getRuleContext(VariableDeclaratorContext.class,i);
		}
		public ParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterListContext parameterList() throws RecognitionException {
		ParameterListContext _localctx = new ParameterListContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_parameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			match(LBRACE);
			setState(117);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(109);
				variableDeclarator();
				setState(114);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SEMI) {
					{
					{
					setState(110);
					match(SEMI);
					setState(111);
					variableDeclarator();
					}
					}
					setState(116);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(119);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeTypeContext extends ParserRuleContext {
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public ArrayTypeContext arrayType() {
			return getRuleContext(ArrayTypeContext.class,0);
		}
		public ClassTypeContext classType() {
			return getRuleContext(ClassTypeContext.class,0);
		}
		public TypeTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitTypeType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeTypeContext typeType() throws RecognitionException {
		TypeTypeContext _localctx = new TypeTypeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_typeType);
		try {
			setState(124);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(121);
				primitiveType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(122);
				arrayType();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(123);
				classType();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimitiveTypeContext extends ParserRuleContext {
		public PrimitiveTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitiveType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitPrimitiveType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimitiveTypeContext primitiveType() throws RecognitionException {
		PrimitiveTypeContext _localctx = new PrimitiveTypeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_primitiveType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOLEAN) | (1L << FLOAT) | (1L << INT) | (1L << STRING) | (1L << VOID))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassTypeContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(BKOOLParser.ID, 0); }
		public ClassTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitClassType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassTypeContext classType() throws RecognitionException {
		ClassTypeContext _localctx = new ClassTypeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_classType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayTypeContext extends ParserRuleContext {
		public TerminalNode LSBRACE() { return getToken(BKOOLParser.LSBRACE, 0); }
		public TerminalNode INTLIT() { return getToken(BKOOLParser.INTLIT, 0); }
		public TerminalNode RSBRACE() { return getToken(BKOOLParser.RSBRACE, 0); }
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public ClassTypeContext classType() {
			return getRuleContext(ClassTypeContext.class,0);
		}
		public ArrayTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitArrayType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayTypeContext arrayType() throws RecognitionException {
		ArrayTypeContext _localctx = new ArrayTypeContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_arrayType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			switch (_input.LA(1)) {
			case BOOLEAN:
			case FLOAT:
			case INT:
			case STRING:
			case VOID:
				{
				setState(130);
				primitiveType();
				}
				break;
			case ID:
				{
				setState(131);
				classType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(134);
			match(LSBRACE);
			setState(135);
			match(INTLIT);
			setState(136);
			match(RSBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockStatementContext extends ParserRuleContext {
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitBlockStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockStatementContext blockStatement() throws RecognitionException {
		BlockStatementContext _localctx = new BlockStatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_blockStatement);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			match(LPAREN);
			setState(142);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(139);
					declaration();
					}
					} 
				}
				setState(144);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			}
			setState(148);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << BREAK) | (1L << CONTINUE) | (1L << IF) | (1L << FOR) | (1L << RETURN) | (1L << NIL) | (1L << THIS) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << OBJCREATE) | (1L << LPAREN) | (1L << LBRACE) | (1L << ID) | (1L << INTLIT) | (1L << FLOATLIT) | (1L << STRINGLIT))) != 0)) {
				{
				{
				setState(145);
				statement();
				}
				}
				setState(150);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(151);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public ConstDeclarationContext constDeclaration() {
			return getRuleContext(ConstDeclarationContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_declaration);
		int _la;
		try {
			setState(161);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(154);
				_la = _input.LA(1);
				if (_la==STATIC) {
					{
					setState(153);
					match(STATIC);
					}
				}

				setState(156);
				variableDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(158);
				_la = _input.LA(1);
				if (_la==STATIC) {
					{
					setState(157);
					match(STATIC);
					}
				}

				setState(160);
				constDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ContinueStmtContext extends StatementContext {
		public ContinueStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitContinueStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IfStmtContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public IfStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitIfStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignStmtContext extends StatementContext {
		public LhsAssignContext lhsAssign() {
			return getRuleContext(LhsAssignContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AssignStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitAssignStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BreakStmtContext extends StatementContext {
		public BreakStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitBreakStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BlockStmtContext extends StatementContext {
		public BlockStatementContext blockStatement() {
			return getRuleContext(BlockStatementContext.class,0);
		}
		public BlockStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitBlockStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CallStmtContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ID() { return getToken(BKOOLParser.ID, 0); }
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public CallStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitCallStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ForStmtContext extends StatementContext {
		public ForControlContext forControl() {
			return getRuleContext(ForControlContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ForStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitForStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ReturnStmtContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitReturnStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_statement);
		int _la;
		try {
			setState(196);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				_localctx = new BlockStmtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(163);
				blockStatement();
				}
				break;
			case 2:
				_localctx = new CallStmtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(164);
				expression();
				setState(165);
				match(DOT);
				setState(166);
				match(ID);
				setState(167);
				arguments();
				setState(168);
				match(SEMI);
				}
				break;
			case 3:
				_localctx = new AssignStmtContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(170);
				lhsAssign();
				setState(171);
				match(ASSIGN);
				setState(172);
				expression();
				setState(173);
				match(SEMI);
				}
				break;
			case 4:
				_localctx = new IfStmtContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(175);
				match(IF);
				setState(176);
				expression();
				setState(177);
				match(THEN);
				setState(178);
				statement();
				setState(181);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
				case 1:
					{
					setState(179);
					match(ELSE);
					setState(180);
					statement();
					}
					break;
				}
				}
				break;
			case 5:
				_localctx = new ForStmtContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(183);
				match(FOR);
				setState(184);
				forControl();
				setState(185);
				statement();
				}
				break;
			case 6:
				_localctx = new BreakStmtContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(187);
				match(BREAK);
				setState(188);
				match(SEMI);
				}
				break;
			case 7:
				_localctx = new ContinueStmtContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(189);
				match(CONTINUE);
				setState(190);
				match(SEMI);
				}
				break;
			case 8:
				_localctx = new ReturnStmtContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(191);
				match(RETURN);
				setState(193);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << NIL) | (1L << THIS) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << OBJCREATE) | (1L << LBRACE) | (1L << ID) | (1L << INTLIT) | (1L << FLOATLIT) | (1L << STRINGLIT))) != 0)) {
					{
					setState(192);
					expression();
					}
				}

				setState(195);
				match(SEMI);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LhsAssignContext extends ParserRuleContext {
		public LhsAssignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lhsAssign; }
	 
		public LhsAssignContext() { }
		public void copyFrom(LhsAssignContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class StaticAttributeAccessContext extends LhsAssignContext {
		public List<TerminalNode> ID() { return getTokens(BKOOLParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(BKOOLParser.ID, i);
		}
		public StaticAttributeAccessContext(LhsAssignContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitStaticAttributeAccess(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InstanceAttributeAccessContext extends LhsAssignContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ID() { return getToken(BKOOLParser.ID, 0); }
		public InstanceAttributeAccessContext(LhsAssignContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitInstanceAttributeAccess(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VariableAccessContext extends LhsAssignContext {
		public TerminalNode ID() { return getToken(BKOOLParser.ID, 0); }
		public VariableAccessContext(LhsAssignContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitVariableAccess(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrayElementAccessContext extends LhsAssignContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArrayElementAccessContext(LhsAssignContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitArrayElementAccess(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LhsAssignContext lhsAssign() throws RecognitionException {
		LhsAssignContext _localctx = new LhsAssignContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_lhsAssign);
		try {
			setState(211);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				_localctx = new VariableAccessContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(198);
				match(ID);
				}
				break;
			case 2:
				_localctx = new InstanceAttributeAccessContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(199);
				expression();
				setState(200);
				match(DOT);
				setState(201);
				match(ID);
				}
				break;
			case 3:
				_localctx = new StaticAttributeAccessContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(203);
				match(ID);
				setState(204);
				match(DOT);
				setState(205);
				match(ID);
				}
				break;
			case 4:
				_localctx = new ArrayElementAccessContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(206);
				expression();
				setState(207);
				match(LSBRACE);
				setState(208);
				expression();
				setState(209);
				match(RSBRACE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForControlContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(BKOOLParser.ID, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ForControlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forControl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitForControl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForControlContext forControl() throws RecognitionException {
		ForControlContext _localctx = new ForControlContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_forControl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			match(ID);
			setState(214);
			match(ASSIGN);
			setState(215);
			expression();
			setState(216);
			_la = _input.LA(1);
			if ( !(_la==TO || _la==DOWNTO) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(217);
			expression();
			setState(218);
			match(DO);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclaratorContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(BKOOLParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(BKOOLParser.ID, i);
		}
		public TypeTypeContext typeType() {
			return getRuleContext(TypeTypeContext.class,0);
		}
		public VariableDeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclarator; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitVariableDeclarator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclaratorContext variableDeclarator() throws RecognitionException {
		VariableDeclaratorContext _localctx = new VariableDeclaratorContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_variableDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(220);
			match(ID);
			setState(225);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(221);
				match(COMMA);
				setState(222);
				match(ID);
				}
				}
				setState(227);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(228);
			match(COLON);
			setState(229);
			typeType();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentsContext extends ParserRuleContext {
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
			match(LBRACE);
			setState(233);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << NIL) | (1L << THIS) | (1L << ADD) | (1L << SUB) | (1L << NOT) | (1L << OBJCREATE) | (1L << LBRACE) | (1L << ID) | (1L << INTLIT) | (1L << FLOATLIT) | (1L << STRINGLIT))) != 0)) {
				{
				setState(232);
				expressionList();
				}
			}

			setState(235);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitExpressionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			expression();
			setState(242);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(238);
				match(COMMA);
				setState(239);
				expression();
				}
				}
				setState(244);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_expression);
		int _la;
		try {
			setState(250);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(245);
				term();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(246);
				term();
				setState(247);
				_la = _input.LA(1);
				if ( !(_la==EQUAL || _la==NEQUAL) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(248);
				term();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TermContext extends ParserRuleContext {
		public List<PrimaryContext> primary() {
			return getRuleContexts(PrimaryContext.class);
		}
		public PrimaryContext primary(int i) {
			return getRuleContext(PrimaryContext.class,i);
		}
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_term);
		int _la;
		try {
			setState(257);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(252);
				primary(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(253);
				primary(0);
				setState(254);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LT) | (1L << GT) | (1L << LE) | (1L << GE))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(255);
				primary(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryContext extends ParserRuleContext {
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
	 
		public PrimaryContext() { }
		public void copyFrom(PrimaryContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ObjectCreationExprContext extends PrimaryContext {
		public TerminalNode ID() { return getToken(BKOOLParser.ID, 0); }
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public ObjectCreationExprContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitObjectCreationExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MulExprContext extends PrimaryContext {
		public List<PrimaryContext> primary() {
			return getRuleContexts(PrimaryContext.class);
		}
		public PrimaryContext primary(int i) {
			return getRuleContext(PrimaryContext.class,i);
		}
		public MulExprContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitMulExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AndExprContext extends PrimaryContext {
		public List<PrimaryContext> primary() {
			return getRuleContexts(PrimaryContext.class);
		}
		public PrimaryContext primary(int i) {
			return getRuleContext(PrimaryContext.class,i);
		}
		public AndExprContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitAndExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdExprContext extends PrimaryContext {
		public TerminalNode ID() { return getToken(BKOOLParser.ID, 0); }
		public IdExprContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitIdExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CallMethodExprContext extends PrimaryContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public TerminalNode ID() { return getToken(BKOOLParser.ID, 0); }
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public CallMethodExprContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitCallMethodExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AttributeExprContext extends PrimaryContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public TerminalNode ID() { return getToken(BKOOLParser.ID, 0); }
		public AttributeExprContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitAttributeExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AddExprContext extends PrimaryContext {
		public List<PrimaryContext> primary() {
			return getRuleContexts(PrimaryContext.class);
		}
		public PrimaryContext primary(int i) {
			return getRuleContext(PrimaryContext.class,i);
		}
		public AddExprContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitAddExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SignExprContext extends PrimaryContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public SignExprContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitSignExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PowerExprContext extends PrimaryContext {
		public List<PrimaryContext> primary() {
			return getRuleContexts(PrimaryContext.class);
		}
		public PrimaryContext primary(int i) {
			return getRuleContext(PrimaryContext.class,i);
		}
		public PowerExprContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitPowerExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrayElementExprContext extends PrimaryContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ArrayElementExprContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitArrayElementExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParensExprContext extends PrimaryContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParensExprContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitParensExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LiteralExprContext extends PrimaryContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public LiteralExprContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitLiteralExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotExprContext extends PrimaryContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public NotExprContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitNotExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		return primary(0);
	}

	private PrimaryContext primary(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PrimaryContext _localctx = new PrimaryContext(_ctx, _parentState);
		PrimaryContext _prevctx = _localctx;
		int _startState = 44;
		enterRecursionRule(_localctx, 44, RULE_primary, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(273);
			switch (_input.LA(1)) {
			case LBRACE:
				{
				_localctx = new ParensExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(260);
				match(LBRACE);
				setState(261);
				expression();
				setState(262);
				match(RBRACE);
				}
				break;
			case T__0:
			case T__1:
			case NIL:
			case THIS:
			case INTLIT:
			case FLOATLIT:
			case STRINGLIT:
				{
				_localctx = new LiteralExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(264);
				literal();
				}
				break;
			case ID:
				{
				_localctx = new IdExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(265);
				match(ID);
				}
				break;
			case OBJCREATE:
				{
				_localctx = new ObjectCreationExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(266);
				match(OBJCREATE);
				setState(267);
				match(ID);
				setState(268);
				arguments();
				}
				break;
			case ADD:
			case SUB:
				{
				_localctx = new SignExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(269);
				_la = _input.LA(1);
				if ( !(_la==ADD || _la==SUB) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(270);
				primary(6);
				}
				break;
			case NOT:
				{
				_localctx = new NotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(271);
				match(NOT);
				setState(272);
				primary(5);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(301);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(299);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
					case 1:
						{
						_localctx = new PowerExprContext(new PrimaryContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_primary);
						setState(275);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(276);
						match(CONCAT);
						setState(277);
						primary(5);
						}
						break;
					case 2:
						{
						_localctx = new MulExprContext(new PrimaryContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_primary);
						setState(278);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(279);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << IDIV) | (1L << FDIV) | (1L << MOD))) != 0)) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(280);
						primary(4);
						}
						break;
					case 3:
						{
						_localctx = new AddExprContext(new PrimaryContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_primary);
						setState(281);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(282);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(283);
						primary(3);
						}
						break;
					case 4:
						{
						_localctx = new AndExprContext(new PrimaryContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_primary);
						setState(284);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(285);
						_la = _input.LA(1);
						if ( !(_la==OR || _la==AND) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(286);
						primary(2);
						}
						break;
					case 5:
						{
						_localctx = new AttributeExprContext(new PrimaryContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_primary);
						setState(287);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(288);
						match(DOT);
						setState(289);
						match(ID);
						}
						break;
					case 6:
						{
						_localctx = new CallMethodExprContext(new PrimaryContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_primary);
						setState(290);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(291);
						match(DOT);
						setState(292);
						match(ID);
						setState(293);
						arguments();
						}
						break;
					case 7:
						{
						_localctx = new ArrayElementExprContext(new PrimaryContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_primary);
						setState(294);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(295);
						match(LSBRACE);
						setState(296);
						expression();
						setState(297);
						match(RSBRACE);
						}
						break;
					}
					} 
				}
				setState(303);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode INTLIT() { return getToken(BKOOLParser.INTLIT, 0); }
		public TerminalNode FLOATLIT() { return getToken(BKOOLParser.FLOATLIT, 0); }
		public TerminalNode STRINGLIT() { return getToken(BKOOLParser.STRINGLIT, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BKOOLVisitor ) return ((BKOOLVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(304);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << NIL) | (1L << THIS) | (1L << INTLIT) | (1L << FLOATLIT) | (1L << STRINGLIT))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 22:
			return primary_sempred((PrimaryContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean primary_sempred(PrimaryContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		case 2:
			return precpred(_ctx, 2);
		case 3:
			return precpred(_ctx, 1);
		case 4:
			return precpred(_ctx, 9);
		case 5:
			return precpred(_ctx, 8);
		case 6:
			return precpred(_ctx, 7);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3A\u0135\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\3\2\6\2\64\n\2\r\2\16\2\65\3\2\3\2\3\3\3\3\3\3\3\3\5\3>\n\3\3\3\3\3\7"+
		"\3B\n\3\f\3\16\3E\13\3\3\3\3\3\3\4\3\4\5\4K\n\4\3\5\5\5N\n\5\3\5\3\5\5"+
		"\5R\n\5\3\5\5\5U\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\5"+
		"\bc\n\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\bm\n\b\3\t\3\t\3\t\3\t\7\ts"+
		"\n\t\f\t\16\tv\13\t\5\tx\n\t\3\t\3\t\3\n\3\n\3\n\5\n\177\n\n\3\13\3\13"+
		"\3\f\3\f\3\r\3\r\5\r\u0087\n\r\3\r\3\r\3\r\3\r\3\16\3\16\7\16\u008f\n"+
		"\16\f\16\16\16\u0092\13\16\3\16\7\16\u0095\n\16\f\16\16\16\u0098\13\16"+
		"\3\16\3\16\3\17\5\17\u009d\n\17\3\17\3\17\5\17\u00a1\n\17\3\17\5\17\u00a4"+
		"\n\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\5\20\u00b8\n\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\5\20\u00c4\n\20\3\20\5\20\u00c7\n\20\3\21\3\21\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u00d6\n\21"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\7\23\u00e2\n\23\f\23"+
		"\16\23\u00e5\13\23\3\23\3\23\3\23\3\24\3\24\5\24\u00ec\n\24\3\24\3\24"+
		"\3\25\3\25\3\25\7\25\u00f3\n\25\f\25\16\25\u00f6\13\25\3\26\3\26\3\26"+
		"\3\26\3\26\5\26\u00fd\n\26\3\27\3\27\3\27\3\27\3\27\5\27\u0104\n\27\3"+
		"\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5"+
		"\30\u0114\n\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\7\30"+
		"\u012e\n\30\f\30\16\30\u0131\13\30\3\31\3\31\3\31\2\3.\32\2\4\6\b\n\f"+
		"\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\2\n\6\2\7\7\16\16\20\21\25\25"+
		"\3\2\32\33\3\2\"#\3\2$\'\3\2\34\35\3\2\36!\3\2()\6\2\3\4\26\2789;;\u014c"+
		"\2\63\3\2\2\2\49\3\2\2\2\6J\3\2\2\2\bT\3\2\2\2\nV\3\2\2\2\f]\3\2\2\2\16"+
		"l\3\2\2\2\20n\3\2\2\2\22~\3\2\2\2\24\u0080\3\2\2\2\26\u0082\3\2\2\2\30"+
		"\u0086\3\2\2\2\32\u008c\3\2\2\2\34\u00a3\3\2\2\2\36\u00c6\3\2\2\2 \u00d5"+
		"\3\2\2\2\"\u00d7\3\2\2\2$\u00de\3\2\2\2&\u00e9\3\2\2\2(\u00ef\3\2\2\2"+
		"*\u00fc\3\2\2\2,\u0103\3\2\2\2.\u0113\3\2\2\2\60\u0132\3\2\2\2\62\64\5"+
		"\4\3\2\63\62\3\2\2\2\64\65\3\2\2\2\65\63\3\2\2\2\65\66\3\2\2\2\66\67\3"+
		"\2\2\2\678\7\2\2\38\3\3\2\2\29:\7\t\2\2:=\7\67\2\2;<\7\r\2\2<>\7\67\2"+
		"\2=;\3\2\2\2=>\3\2\2\2>?\3\2\2\2?C\7/\2\2@B\5\6\4\2A@\3\2\2\2BE\3\2\2"+
		"\2CA\3\2\2\2CD\3\2\2\2DF\3\2\2\2EC\3\2\2\2FG\7\60\2\2G\5\3\2\2\2HK\5\b"+
		"\5\2IK\5\16\b\2JH\3\2\2\2JI\3\2\2\2K\7\3\2\2\2LN\7\31\2\2ML\3\2\2\2MN"+
		"\3\2\2\2NO\3\2\2\2OU\5\f\7\2PR\7\31\2\2QP\3\2\2\2QR\3\2\2\2RS\3\2\2\2"+
		"SU\5\n\6\2TM\3\2\2\2TQ\3\2\2\2U\t\3\2\2\2VW\7\30\2\2WX\5\22\n\2XY\7\67"+
		"\2\2YZ\7\6\2\2Z[\5*\26\2[\\\7\63\2\2\\\13\3\2\2\2]^\5$\23\2^_\7\63\2\2"+
		"_\r\3\2\2\2`b\5\22\n\2ac\7\31\2\2ba\3\2\2\2bc\3\2\2\2cd\3\2\2\2de\7\67"+
		"\2\2ef\5\20\t\2fg\5\32\16\2gm\3\2\2\2hi\7\67\2\2ij\5\20\t\2jk\5\32\16"+
		"\2km\3\2\2\2l`\3\2\2\2lh\3\2\2\2m\17\3\2\2\2nw\7\61\2\2ot\5$\23\2pq\7"+
		"\63\2\2qs\5$\23\2rp\3\2\2\2sv\3\2\2\2tr\3\2\2\2tu\3\2\2\2ux\3\2\2\2vt"+
		"\3\2\2\2wo\3\2\2\2wx\3\2\2\2xy\3\2\2\2yz\7\62\2\2z\21\3\2\2\2{\177\5\24"+
		"\13\2|\177\5\30\r\2}\177\5\26\f\2~{\3\2\2\2~|\3\2\2\2~}\3\2\2\2\177\23"+
		"\3\2\2\2\u0080\u0081\t\2\2\2\u0081\25\3\2\2\2\u0082\u0083\7\67\2\2\u0083"+
		"\27\3\2\2\2\u0084\u0087\5\24\13\2\u0085\u0087\5\26\f\2\u0086\u0084\3\2"+
		"\2\2\u0086\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u0089\7-\2\2\u0089"+
		"\u008a\78\2\2\u008a\u008b\7.\2\2\u008b\31\3\2\2\2\u008c\u0090\7/\2\2\u008d"+
		"\u008f\5\34\17\2\u008e\u008d\3\2\2\2\u008f\u0092\3\2\2\2\u0090\u008e\3"+
		"\2\2\2\u0090\u0091\3\2\2\2\u0091\u0096\3\2\2\2\u0092\u0090\3\2\2\2\u0093"+
		"\u0095\5\36\20\2\u0094\u0093\3\2\2\2\u0095\u0098\3\2\2\2\u0096\u0094\3"+
		"\2\2\2\u0096\u0097\3\2\2\2\u0097\u0099\3\2\2\2\u0098\u0096\3\2\2\2\u0099"+
		"\u009a\7\60\2\2\u009a\33\3\2\2\2\u009b\u009d\7\31\2\2\u009c\u009b\3\2"+
		"\2\2\u009c\u009d\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u00a4\5\f\7\2\u009f"+
		"\u00a1\7\31\2\2\u00a0\u009f\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a2\3"+
		"\2\2\2\u00a2\u00a4\5\n\6\2\u00a3\u009c\3\2\2\2\u00a3\u00a0\3\2\2\2\u00a4"+
		"\35\3\2\2\2\u00a5\u00c7\5\32\16\2\u00a6\u00a7\5*\26\2\u00a7\u00a8\7\65"+
		"\2\2\u00a8\u00a9\7\67\2\2\u00a9\u00aa\5&\24\2\u00aa\u00ab\7\63\2\2\u00ab"+
		"\u00c7\3\2\2\2\u00ac\u00ad\5 \21\2\u00ad\u00ae\7\5\2\2\u00ae\u00af\5*"+
		"\26\2\u00af\u00b0\7\63\2\2\u00b0\u00c7\3\2\2\2\u00b1\u00b2\7\17\2\2\u00b2"+
		"\u00b3\5*\26\2\u00b3\u00b4\7\22\2\2\u00b4\u00b7\5\36\20\2\u00b5\u00b6"+
		"\7\f\2\2\u00b6\u00b8\5\36\20\2\u00b7\u00b5\3\2\2\2\u00b7\u00b8\3\2\2\2"+
		"\u00b8\u00c7\3\2\2\2\u00b9\u00ba\7\23\2\2\u00ba\u00bb\5\"\22\2\u00bb\u00bc"+
		"\5\36\20\2\u00bc\u00c7\3\2\2\2\u00bd\u00be\7\b\2\2\u00be\u00c7\7\63\2"+
		"\2\u00bf\u00c0\7\n\2\2\u00c0\u00c7\7\63\2\2\u00c1\u00c3\7\24\2\2\u00c2"+
		"\u00c4\5*\26\2\u00c3\u00c2\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c5\3\2"+
		"\2\2\u00c5\u00c7\7\63\2\2\u00c6\u00a5\3\2\2\2\u00c6\u00a6\3\2\2\2\u00c6"+
		"\u00ac\3\2\2\2\u00c6\u00b1\3\2\2\2\u00c6\u00b9\3\2\2\2\u00c6\u00bd\3\2"+
		"\2\2\u00c6\u00bf\3\2\2\2\u00c6\u00c1\3\2\2\2\u00c7\37\3\2\2\2\u00c8\u00d6"+
		"\7\67\2\2\u00c9\u00ca\5*\26\2\u00ca\u00cb\7\65\2\2\u00cb\u00cc\7\67\2"+
		"\2\u00cc\u00d6\3\2\2\2\u00cd\u00ce\7\67\2\2\u00ce\u00cf\7\65\2\2\u00cf"+
		"\u00d6\7\67\2\2\u00d0\u00d1\5*\26\2\u00d1\u00d2\7-\2\2\u00d2\u00d3\5*"+
		"\26\2\u00d3\u00d4\7.\2\2\u00d4\u00d6\3\2\2\2\u00d5\u00c8\3\2\2\2\u00d5"+
		"\u00c9\3\2\2\2\u00d5\u00cd\3\2\2\2\u00d5\u00d0\3\2\2\2\u00d6!\3\2\2\2"+
		"\u00d7\u00d8\7\67\2\2\u00d8\u00d9\7\5\2\2\u00d9\u00da\5*\26\2\u00da\u00db"+
		"\t\3\2\2\u00db\u00dc\5*\26\2\u00dc\u00dd\7\13\2\2\u00dd#\3\2\2\2\u00de"+
		"\u00e3\7\67\2\2\u00df\u00e0\7\66\2\2\u00e0\u00e2\7\67\2\2\u00e1\u00df"+
		"\3\2\2\2\u00e2\u00e5\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4"+
		"\u00e6\3\2\2\2\u00e5\u00e3\3\2\2\2\u00e6\u00e7\7\64\2\2\u00e7\u00e8\5"+
		"\22\n\2\u00e8%\3\2\2\2\u00e9\u00eb\7\61\2\2\u00ea\u00ec\5(\25\2\u00eb"+
		"\u00ea\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\u00ee\7\62"+
		"\2\2\u00ee\'\3\2\2\2\u00ef\u00f4\5*\26\2\u00f0\u00f1\7\66\2\2\u00f1\u00f3"+
		"\5*\26\2\u00f2\u00f0\3\2\2\2\u00f3\u00f6\3\2\2\2\u00f4\u00f2\3\2\2\2\u00f4"+
		"\u00f5\3\2\2\2\u00f5)\3\2\2\2\u00f6\u00f4\3\2\2\2\u00f7\u00fd\5,\27\2"+
		"\u00f8\u00f9\5,\27\2\u00f9\u00fa\t\4\2\2\u00fa\u00fb\5,\27\2\u00fb\u00fd"+
		"\3\2\2\2\u00fc\u00f7\3\2\2\2\u00fc\u00f8\3\2\2\2\u00fd+\3\2\2\2\u00fe"+
		"\u0104\5.\30\2\u00ff\u0100\5.\30\2\u0100\u0101\t\5\2\2\u0101\u0102\5."+
		"\30\2\u0102\u0104\3\2\2\2\u0103\u00fe\3\2\2\2\u0103\u00ff\3\2\2\2\u0104"+
		"-\3\2\2\2\u0105\u0106\b\30\1\2\u0106\u0107\7\61\2\2\u0107\u0108\5*\26"+
		"\2\u0108\u0109\7\62\2\2\u0109\u0114\3\2\2\2\u010a\u0114\5\60\31\2\u010b"+
		"\u0114\7\67\2\2\u010c\u010d\7,\2\2\u010d\u010e\7\67\2\2\u010e\u0114\5"+
		"&\24\2\u010f\u0110\t\6\2\2\u0110\u0114\5.\30\b\u0111\u0112\7*\2\2\u0112"+
		"\u0114\5.\30\7\u0113\u0105\3\2\2\2\u0113\u010a\3\2\2\2\u0113\u010b\3\2"+
		"\2\2\u0113\u010c\3\2\2\2\u0113\u010f\3\2\2\2\u0113\u0111\3\2\2\2\u0114"+
		"\u012f\3\2\2\2\u0115\u0116\f\6\2\2\u0116\u0117\7+\2\2\u0117\u012e\5.\30"+
		"\7\u0118\u0119\f\5\2\2\u0119\u011a\t\7\2\2\u011a\u012e\5.\30\6\u011b\u011c"+
		"\f\4\2\2\u011c\u011d\t\6\2\2\u011d\u012e\5.\30\5\u011e\u011f\f\3\2\2\u011f"+
		"\u0120\t\b\2\2\u0120\u012e\5.\30\4\u0121\u0122\f\13\2\2\u0122\u0123\7"+
		"\65\2\2\u0123\u012e\7\67\2\2\u0124\u0125\f\n\2\2\u0125\u0126\7\65\2\2"+
		"\u0126\u0127\7\67\2\2\u0127\u012e\5&\24\2\u0128\u0129\f\t\2\2\u0129\u012a"+
		"\7-\2\2\u012a\u012b\5*\26\2\u012b\u012c\7.\2\2\u012c\u012e\3\2\2\2\u012d"+
		"\u0115\3\2\2\2\u012d\u0118\3\2\2\2\u012d\u011b\3\2\2\2\u012d\u011e\3\2"+
		"\2\2\u012d\u0121\3\2\2\2\u012d\u0124\3\2\2\2\u012d\u0128\3\2\2\2\u012e"+
		"\u0131\3\2\2\2\u012f\u012d\3\2\2\2\u012f\u0130\3\2\2\2\u0130/\3\2\2\2"+
		"\u0131\u012f\3\2\2\2\u0132\u0133\t\t\2\2\u0133\61\3\2\2\2 \65=CJMQTbl"+
		"tw~\u0086\u0090\u0096\u009c\u00a0\u00a3\u00b7\u00c3\u00c6\u00d5\u00e3"+
		"\u00eb\u00f4\u00fc\u0103\u0113\u012d\u012f";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}