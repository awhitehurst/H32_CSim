package parser;

import java.util.ArrayList;
import lexer.Lexer;
import lexer.SType;
import lexer.Token;
import ptn.ArrayDecl;
import ptn.AssignState;
import ptn.Block;
import ptn.Decl;
import ptn.DeclStats;
import ptn.ExprList;
import ptn.Expression;
import ptn.ForState;
import ptn.Funcall;
import ptn.Funct;
import ptn.Functs;
import ptn.IfState;
import ptn.IncDecState;
import ptn.Literal;
import ptn.Name;
import ptn.Operator;
import ptn.PTFun;
import ptn.PTNode;
import ptn.Param;
import ptn.ParamList;
import ptn.Proccall;
import ptn.Program;
import ptn.RetState;
import ptn.Statement;
import ptn.Type;
import ptn.TypeList;
import ptn.VarDecl;
import ptn.VarRef;
import ptn.WhileState;

/**
 *
 * @author Lab Admin
 */
public class Parser {

    public Parser(Lexer lex) {
        this.lex = lex;
        this.stab = SymbolTable.getInstance();
        errorMessages = new ArrayList<>();
        errors = false;
    }

    public SymbolTable getSymbolTable() {
        return stab;
    }

    public boolean hasErrors() {
        return errors;
    }

    public ArrayList<String> getErrorMessages() {
        return errorMessages;
    }

    public PTNode parse() {
        try {
            return parseProgram();
        } catch (ParseException ex) {
            errorMessages.add(ex.getMessage());
            errors = true;
            return null;
        }
    }

    private Program parseProgram() throws ParseException {
        Token s = (Token) lex.peek();
        Program n = new Program();
        if (s==null){
            throwParseException("Empty program.",new Token("ERROR",SType.KW,0,0));
        }
        if (!s.getSymbol().equals("main")) {
            n.setFuncts(parseFuncts());
        }
        s = (Token) lex.next(); // main
        if (s==null || !s.getSymbol().equals("main")) {
            throwParseException("expecting 'main'", s);
        }
        s = (Token) lex.next(); // (
        if (s==null || !s.getSymbol().equals("(")) {
            throwParseException("expecting '('", s);
        }
        s = (Token) lex.next(); // )
        if (s==null || !s.getSymbol().equals(")")) {
            throwParseException("expecting ')'", s);
        }
        n.setBody(parseBlock());
        if (lex.hasNext()) {
            throwParseException("unexpected input after end of program", s);
        }
        return n;
    }

    private Functs parseFuncts() throws ParseException {
        Functs n = new Functs();
        Token s = lex.peek();
        while (s != null && !s.getSymbol().equals("main")) {
            try {
                n.addChild(parseFunct());
                s = lex.peek();
            } catch (ParseException ex) {
                errorMessages.add(ex.getMessage());
                errors = true;
                synchToToken("}");
                s = lex.peek();
            }
        }
        return n;
    }

    private Funct parseFunct() throws ParseException {
        Token s;
        Funct n = new Funct();
        n.setType(parseType());
        n.setName(parseName());
        s = (Token) lex.next();
        if (!s.getSymbol().equals("(")) {
            throwParseException("expecting '('", s);
        }
        stab.addFunction(n.getName().toString());
        s = (Token) lex.peek();
        if (!s.getSymbol().equals(")")) {
            n.setParamList(parseParamList());
        }
        s = (Token) lex.next();
        if (!s.getSymbol().equals(")")) {
            throwParseException("missing ')'", s);
        }

        n.getName().getSymbol().setSymbol(n.getNameMangle());
        stab.add(n.getName(), n.getType());
        stab.pushScope();
        n.setScope(stab.getCurrentScope());
        if (n.getParamList() != null) {
            n.getParamList().pushParams(stab);
        }
        n.setBody(parseBlock());
        RetState ret = n.getReturn();
        String type = n.getType().getSymbol().getSymbol();
        if(ret == null){
       if(!type.equals("void")){
       throwParseException("Expecting return statement", s);
       }
        }else{
            if(!stab.checkReturn(n, ret)){
           throwParseException("Wrong Return type. Expecting " + type, s);
            }
        
        
        }
        stab.popScope();
        return n;
    }

    private Type parseType() throws ParseException {
        Token s = lex.next();
        if (s.getSType() != SType.TY) {
            throwParseException("expecting type", s);
        }
        Type t = new Type(s);
        s = lex.peek();
        // THIS WHILE LOOP IS HANDLING POINTER DECLARATION
        while (s.getSymbol().equals("*")) {
            lex.next();
            t.incPointer();
            s = lex.peek();
        }
        return t;
    }

    private ParamList parseParamList() throws ParseException {
        ParamList n = new ParamList();
        Token s = lex.peek();
        while (!s.getSymbol().equals(")")) {
            n.addParam(parseParam());
            s = lex.peek();
            if (s.getSymbol().equals(",")) {
                lex.next();
                s = lex.peek();
            }else if(!s.getSymbol().equals(")")){
            throwParseException("Expecting \",\"", s);
            }
        }
        return n;
    }

    private Param parseParam() throws ParseException {
        Param n = new Param();
        n.setType(parseType());
        n.setName(parseName());
        //stab.getCurrentScope().add(n.getName(), n.getType());
        //n.setScope(stab.getCurrentScope());
        return n;
    }

    private Block parseBlock() throws ParseException {
        Token s = lex.next();
        Block n = new Block();
        if (!s.getSymbol().equals("{")) {
            throwParseException("missing opening brace", s);
        }
        stab.pushScope();
        n.setScope(stab.getCurrentScope());
        n.setBody(parseDeclStats());
        stab.popScope();
        s = lex.next();
        if (!s.getSymbol().equals("}")) {
            throwParseException("missing closing brace", s);
        }
        return n;
    }

    private DeclStats parseDeclStats() throws ParseException {
        DeclStats n = new DeclStats();
        Token s = lex.peek();
        while (!s.getSymbol().equals("}")) {
            if (SType.isType(s.getSymbol()) || s.getSymbol().equals("static")) {
                try {
                    Decl decl = parseDecl();
                    n.addDecl(decl);
                } catch (ParseException parseException) {
                    errorMessages.add(parseException.getMessage());
                    errors = true;
                    synchToToken(";", "}");
                    lex.peek();
                }
            } else {
                try {
                    Statement stat = parseStatement();
                    n.addStat(stat);
                } catch (ParseException parseException) {
                    errorMessages.add(parseException.getMessage());
                    errors = true;
                    synchToToken(";", "}");
                    lex.peek();
                }
            }
            s = lex.peek();
        }
        return n;
    }

    private Decl parseDecl() throws ParseException {
        VarDecl n = new VarDecl();
        Token s = lex.peek();
        boolean isStatic = false;
        if(s.getSymbol().equals("static")){
            lex.next();
            isStatic = true;
        }
        n.setType(parseType());
        n.getType().setStatic(isStatic);
        s =lex.peek();
        if(s.getSymbol().equals("(")){
        return parsePTFun(n.getType());
        }
        n.setName(parseName());
        n.setScope(stab.getCurrentScope());
        s = lex.peek();
        if (s.getSymbol().equals("[")) {
            return parseArrayDecl(n);
        }
        if (s.getSymbol().equals("=")) {
            lex.next();
            n.setInit(parseExpression());
        }
        s = lex.next();
        if (!s.getSymbol().equals(";")) {
            throwParseException("missing ';'", s);
        }
        stab.add(n.getName(), n.getType());
        return n;
    }

    public Decl parseArrayDecl(VarDecl v) throws ParseException {
        ArrayDecl n = new ArrayDecl(v);
        Token s = lex.next();
        if (!s.getSymbol().equals("[")) {
            throwParseException("expecting '['", s);
        }
        s = lex.peek();
        if (!s.getSymbol().equals("]")) {
            n.setSize(parseExpression());
        }
        s = lex.peek();
        if (!s.getSymbol().equals("]")) {
            throwParseException("expecting ']'", s);
        }
        lex.next();
        s = lex.peek();
        if (s.getSymbol().equals("=")) {
            lex.next();
            s = lex.peek();
            if (!s.getSymbol().equals("{")) {
                throwParseException("expecting '{'", s);
            }
            lex.next();
            n.setInit(parseExprList());
            s = lex.next();
            if (!s.getSymbol().equals("}")) {
                throwParseException("expecting '}'", s);
            }
        }
        s = lex.next();
        if (!s.getSymbol().equals(";")) {
            throwParseException("missing ';'", s);
        }
        stab.add(n.getName(), n.getType());
        return n;
    }

    private Statement parseStatement() throws ParseException {
        Token s = lex.peek();
        if (s.getSymbol().equals("{")) {
            return parseBlock();
        } else {
            return parseSimpleState();
        }
    }

    private Statement parseSimpleState() throws ParseException {
        Token s = lex.peek();
        if (s.getSymbol().equals("for")) {
            return parseForState();
        } else if (s.getSymbol().equals("if")) {
            return parseIfState();
        } else if (s.getSymbol().equals("while")) {
            return parseWhileState();
        } else if (s.getSymbol().equals("return")) {
            return parseReturnState();
        } else if (s.getSymbol().equals("++") || s.getSymbol().equals("--")) {
            return parseIncDecState();
        } else if (s.getSymbol().equals("*")) {
            VarRef ident = parseVarRef();
            return parseAssignState(ident);
        }else if (s.getSymbol().equals("(")){
        return parsePTCall();
        }
        
        else if (s.getSType() == SType.ID) {
            Name ident = parseName();
            s = lex.peek();
            if (s.getSymbol().equals("(")) {
            /*    if (!stab.containsFunction(ident.toString())) {
                    throwParseException("unrecognized function", ident.getSymbol());
                }*/
                return parseProccallState(ident);
            } else {
                //VarRef v = new VarRef();
                VarRef v = parseVarRef(ident);
//                v.setName(ident);
//                if (!stab.contains(v.getName().toString())) {
//                    throwParseException("undeclared identifier", v.getName().getSymbol());
//                } else {
//                    v.setScope(stab.getContainingScope(v.getName().toString()));
//                }
                return parseAssignState(v);

            }
        } else {
            throwParseException("not a valid statement", s);
            return null;
        }
    }

    private ForState parseForState() throws ParseException {
        Token s = lex.next();
        if (!s.getSymbol().equals("for")) {
            throwParseException("expecting 'for'", s);
        }
        ForState n = new ForState();
        s = lex.next();
        if (!s.getSymbol().equals("(")) {
            throwParseException("expecting '('", s);
        }
        n.setInit(parseAssignment());
        s = lex.next();
        if (!s.getSymbol().equals(";")) {
            throwParseException("expecting ';'", s);
        }
        n.setTest(parseExpression());
        s = lex.next();
        if (!s.getSymbol().equals(";")) {
            throwParseException("expecting ';'", s);
        }
        n.setIncr(parseAssignment());
        s = lex.next();
        if (!s.getSymbol().equals(")")) {
            throwParseException("expecting ')'", s);
        }
        n.setBody(parseBlock());
        return n;
    }

    private IfState parseIfState() throws ParseException {
        Token s = lex.next();
        if (!s.getSymbol().equals("if")) {
            throwParseException("expecting 'if'", s);
        }
        IfState n = new IfState();
        s = lex.next();
        if (!s.getSymbol().equals("(")) {
            throwParseException("expecting '('", s);
        }
        n.setTest(parseExpression());
        s = lex.next();
        if (!s.getSymbol().equals(")")) {
            throwParseException("expecting '('", s);
        }
        n.setBody(parseBlock());
        s = lex.peek();
        if (s.getSymbol().equals("else")) {
            lex.next(); // consume else
            n.setElse(parseBlock());
        }
        return n;
    }

    private WhileState parseWhileState() throws ParseException {
        Token s = lex.next();
        if (!s.getSymbol().equals("while")) {
            throwParseException("expecting 'while'", s);
        }
        WhileState n = new WhileState();
        s = lex.next();
        if (!s.getSymbol().equals("(")) {
            throwParseException("expecting '('", s);
        }
        n.setTest(parseExpression());
        s = lex.next();
        if (!s.getSymbol().equals(")")) {
            throwParseException("expecting '('", s);
        }
        n.setBody(parseBlock());
        return n;
    }

    private RetState parseReturnState() throws ParseException {
        Token s = lex.next();
        if (!s.getSymbol().equals("return")) {
            throwParseException("expecting 'return'", s);
        }
        RetState n = new RetState();
        s = lex.peek();
        if (!s.getSymbol().equals(";")) {
            if(s.getSType()==SType.ID)
            {
             n.setExpr(parsePrimary());

            
            }else{
            n.setExpr(parseExpression());
            }
        }
        s = lex.next();
        if (!s.getSymbol().equals(";")) {
            throwParseException("missing ';'", s);
        }
        return n;
    }

    private Statement parseIncDecState() throws ParseException {
        Token s = lex.peek();
        if (!s.getSymbol().equals("++") && !s.getSymbol().equals("--")) {
            throwParseException("expecting '++' or '--'", s);
        }
        IncDecState n = new IncDecState();
        n.setOp(parseOp());
        n.setVarRef(parseVarRef());
        s = lex.next();
        if (!s.getSymbol().equals(";")) {
            throwParseException("missing ';'", s);
        }
        return n;
    }

    private Proccall parseProccallState(Name ident) throws ParseException {
        Proccall n = parseProccall(ident);
        Token s = lex.next();
        if (!s.getSymbol().equals(";")) {
            throwParseException("missing ';'", s);
        }
        return n;
    }

    private Proccall parseProccall() throws ParseException {
        Name ident = parseName();
        return parseProccall(ident);
    }

    private Proccall parseProccall(Name ident) throws ParseException {
        Proccall n = new Proccall();
        n.setIdent(ident);
        Token s = lex.next();
        if (!s.getSymbol().equals("(")) {
            throwParseException("expecting '('", s);
        }
        s = lex.peek();
        if (!s.getSymbol().equals(")")) {
            n.setArgs(parseExprList());
                 
            }
            ArrayList results = stab.contains(ident, n.getArgs());
        if((Boolean)results.get(0) == false){
            throwParseException("No function found with provided arguments. Function found: " + results.get(1), s);
            }
        s = lex.next();
        if (!s.getSymbol().equals(")")) {
            throwParseException("expecting ')'", s);
        }
        return n;
    
    }

    private AssignState parseAssignment() throws ParseException {
        VarRef varRef = parseVarRef();
        return parseAssignment(varRef);
    }

    private AssignState parseAssignState(VarRef varRef) throws ParseException {
        AssignState n = parseAssignment(varRef);
        Token s = lex.next();
        if (!s.getSymbol().equals(";")) {
            throwParseException("missing ';'", s);
        }
        return n;
    }

    private AssignState parseAssignment(VarRef target) throws ParseException {
        AssignState n = new AssignState();
        n.setIdent(target);
        Token s = lex.next();
        if (!s.getSymbol().equals("=")) {
            throwParseException("expecting '='", s);
        }
        n.setExpression(parseExpression());
        return n;
    }

    // this should only be called if we know we have
    // at least one expression
    private ExprList parseExprList() throws ParseException {
        ExprList n = new ExprList();
        Expression e = parseExpression();
        n.addChild(e);
        Token s = lex.peek();
        while (s.getSymbol().equals(",")) {
            lex.next(); // consume ','
            e = parseExpression();
            n.addChild(e);
            s = lex.peek();
        }
        return n;
    }

    private Expression parseExpression() throws ParseException {
        Expression n = new Expression();
        n.setLhs(parseConjunction());
        Token s = lex.peek();
        if (s.getSymbol().equals("||")) {
            n.setOp(parseOp());
            n.setRhs(parseConjunction());
        }
        return n;
    }

    private Expression parseConjunction() throws ParseException {
        Expression n = new Expression();
        n.setLhs(parseEquality());
        Token s = lex.peek();
        if (s.getSymbol().equals("&&")) {
            n.setOp(parseOp());
            n.setRhs(parseEquality());
        }
        return n;
    }

    private Expression parseEquality() throws ParseException {
        Expression n = new Expression();
        n.setLhs(parseRelation());
        Token s = lex.peek();
        if (s.getSymbol().equals("==") || s.getSymbol().equals("!=")) {
            n.setOp(parseOp());
            n.setRhs(parseRelation());
        }
        return n;
    }

    private Expression parseRelation() throws ParseException {
        Expression n = new Expression();
        n.setLhs(parseAddition());
        Token s = lex.peek();
        if (s.getSymbol().equals(">=") || s.getSymbol().equals("<=")
                || s.getSymbol().equals(">") || s.getSymbol().equals("<")) {
            n.setOp(parseOp());
            n.setRhs(parseAddition());
        }
        return n;
    }

    private Expression parseAddition() throws ParseException {
        Expression n = new Expression();
        n.setLhs(parseTerm());
        Token s = lex.peek();
        if (s.getSymbol().equals("+") || s.getSymbol().equals("-")) {
            n.setOp(parseOp());
            n.setRhs(parseTerm());
        }
        return n;
    }

    private Expression parseTerm() throws ParseException {
        Expression n = new Expression();
        n.setLhs(parseFactor());
        Token s = lex.peek();
        if (s.getSymbol().equals("*") || s.getSymbol().equals("/") || s.getSymbol().equals("%")) {
            n.setOp(parseOp());
            n.setRhs(parseFactor());
        }
        return n;
    }

    private Expression parseFactor() throws ParseException {
        Expression n = new Expression();
        Token s = lex.peek();
        if (s.getSType() == SType.OP) {
            n.setOp(parseOp());
        }
        n.setLhs(parsePrimary());
        return n;
    }

    private Expression parsePrimary() throws ParseException {
        Token s = lex.peek();
        if (s.getSymbol().equals("(")) {
            lex.next();
            Expression e = parseExpression();
            s = lex.next();
            if (!s.getSymbol().equals(")")) {
                throwParseException("expecting ')'", s);
            }
            return e;
        } else if (s.getSType() == SType.ID) {
            Name ident = parseName();
            s = lex.peek();
            if (s.getSymbol().equals("(")) {
                if (!stab.containsFunction(ident.toString())) {
                    throwParseException("unrecognized function", ident.getSymbol());
                }
                return parseFuncall(ident);
            } else {
                return parseVarRef(ident);
            }
        } else if (SType.isUniOp(s.getSymbol())) {
            Operator op = parseOp();
            VarRef vr = parseVarRef();
            Expression ex = new Expression();
            ex.setOp(op);
            ex.setLhs(vr);
            return ex;
        } else if (SType.isLiteral(s.getSymbol())) {
            return parseLiteral();
        } else {
            throwParseException("bad primary value", s);
            return null;
        }
    }

    private Funcall parseFuncall(Name ident) throws ParseException {
        Funcall n = new Funcall();
        n.setName(ident);
        n.setSymbol(ident.getSymbol());
        Token s = lex.next();
        if (!s.getSymbol().equals("(")) {
            throwParseException("expecting '('", s);
        }
        s = lex.peek();
        if (!s.getSymbol().equals(")")) {
            n.setArgs(parseExprList());
        }
        s = lex.next();
        if (!s.getSymbol().equals(")")) {
            throwParseException("expecting ')'", s);
        }
        ArrayList results = stab.contains(ident, n.getArgs());
        //n.getSymbol().setSymbol((String)results.get(1));
        if (!(Boolean)results.get(0)) {
            throwParseException("undefined function. Function found: " + results.get(1), n.getName().getSymbol());
        } else {
            n.setMangle((String)results.get(1));
            n.setScope(stab.getGlobalScope());
        }
        return n;
    }

    private Literal parseLiteral() throws ParseException {
        Token s = lex.next();
        if (!SType.isLiteral(s.getSymbol())) {
            throwParseException("expecting literal value", s);
        }
        Literal n = new Literal();
        n.setSymbol(s);
        return n;
    }

    private Operator parseOp() throws ParseException {
        Token s = lex.next();
        if (s.getSType() != SType.OP) {
            throwParseException("expecting operator", s);
        }
        Operator n = new Operator(s);
        return n;
    }

    private VarRef parseVarRef() throws ParseException {
        VarRef n = new VarRef();
        Token s = lex.peek();
        while (s.getSymbol().equals("*")) {
            lex.next();
            n.incIndirect();
            s = lex.peek();
        }
        n.setName(parseName());
        s = lex.peek();
        if (s.getSymbol().equals("[")) {
            lex.next();
            n.setIndex(parseExpression());
            s = lex.next();
            if (!s.getSymbol().equals("]")) {
                throwParseException("expecting ']'", s);
            }
        }
        if (!stab.contains(n.getName().toString())) {
            throwParseException("undefined identifier", n.getName().getSymbol());
        } else {
            n.setScope(stab.getContainingScope(n.getName().toString()));
        }
        return n;
    }

    private VarRef parseVarRef(Name name) throws ParseException {
        VarRef n = new VarRef();
        n.setName(name);
        Token s = lex.peek();
        if (s.getSymbol().equals("[")) {
            lex.next();
            n.setIndex(parseExpression());
            s = lex.next();
            if (!s.getSymbol().equals("]")) {
                throwParseException("expecting ']'", s);
            }
        }
        
        if (!stab.contains(n.getName().toString())) { //Added containsFunction for PTFun
            throwParseException("undefined identifier", n.getName().getSymbol());
        }
        else {
            n.setScope(stab.getContainingScope(n.getName().toString()));
        }
        return n;
    }

    private Name parseName() throws ParseException {
        Token s = lex.next();
        if (s.getSType() != SType.ID) {
            throwParseException("expecting identifier", s);
        }
        Name n = new Name(s);
        return n;
    }

    private void synchToToken(String token) {
        synchToToken(token, token);
    }

    private void synchToToken(String token1, String token2) {
        if(!token1.equals(token2)){
        Token s = lex.peek();
        if (s != null && (s.getSymbol().equals(token1) || s.getSymbol().equals(token2))) {
            s = lex.next();
        }
        while (s != null) {
            if (s.getSymbol().equals(token1) || s.getSymbol().equals(token2)) {
                return;
            } else {
                s = lex.next();
            }
        }
    }}

    private void throwParseException(String cause, Token token) throws ParseException {
        throw new ParseException(
                String.format(
                        "%s; found %s at line %d, column %d",
                        cause,
                        token.getSymbol(),
                        token.getLine(),
                        token.getCol() + 1));
    }
    // private instance variables
    //
    private final Lexer lex;
    private final SymbolTable stab;
    private final ArrayList<String> errorMessages;
    private boolean errors;

    private Decl parsePTFun(Type t) throws ParseException{
        PTFun n= new PTFun();
        Token s = lex.next();
        n.setType(t);
        if(!s.getSymbol().equals("(")){
        throwParseException("Expecting ( ", s);
        }
        s = lex.next();
        if(!s.getSymbol().equals("*")){
        throwParseException("Expecting * ", s);
        
        }
        n.setName(parseName());
        s = lex.next();
        if(!s.getSymbol().equals(")")){
        throwParseException("Expecting )", s);
        }
        s = lex.next();
        if(!s.getSymbol().equals("(")){
        throwParseException("Expecting )", s);
        }
        n.setTypeList(parseTypeList());
//        if (s.getSymbol().equals("=")) {
//            lex.next();
//            n.setInit(parseExpression());
//        }
        s = lex.next();
        if(!s.getSymbol().equals(")")){
        throwParseException("Expecting )", s);
        }
        s = lex.next();
        if (!s.getSymbol().equals(";")) {
            throwParseException("missing ';'", s);
        }
        stab.add(n.getName(), n.getType());
        n.getSymbol().setSymbol(n.getNameMangle());
        stab.add(n.getName(), n.getType());
        stab.addPtrFunction(n.getName().toString()); //Make Mangle
        
        
        n.setScope(stab.getCurrentScope());
        
    return n;
    }
    public TypeList parseTypeList() throws ParseException{
    TypeList types = new TypeList();
    Token s = lex.peek();
    while(!s.getSymbol().equals(")")){
    types.addType(parseType());
    s = lex.peek();
            if (s.getSymbol().equals(",")) {
                lex.next();
                s = lex.peek();
            }else if(!s.getSymbol().equals(")")){
            throwParseException("Expecting \",\"", s);
            }
    }
    
    return types;
    }

    private Proccall parsePTCall() throws ParseException{
    Token s = lex.next();
    if(!s.getSymbol().equals("(")){
   throwParseException("Expecting \"(\"", s);
    }
    s = lex.next();
    if(!s.getSymbol().equals("*")){
    throwParseException("Expecting \"*\"", s);
    }
    //lex.next();
    // 
    Name n = parseName();
   s = lex.next();
    if(!s.getSymbol().equals(")")){
      throwParseException("Expecting \")\"", s);  
        
    }
    return parseProccallState(n);
    
    }
}