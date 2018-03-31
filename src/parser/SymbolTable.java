package parser;

import java.util.ArrayList;
import java.util.Stack;
import lexer.SType;
import lexer.Token;
import ptn.ExprList;
import ptn.Expression;
import ptn.Funcall;
import ptn.Funct;
import ptn.Name;
import ptn.RetState;
import ptn.Type;

/**
 *
 * @author Alan
 */
public class SymbolTable {

    private SymbolTable() {
        global = new Scope(0);
        scopeStack = new Stack<>();
        scopeStack.push(global);
        functionNames = new ArrayList<>();
        ptrFunctions = new ArrayList<>();
    }
    
    public static SymbolTable getInstance(){
        return symtab;
    }

    public void add(Name name, Type type) throws ParseException {
        if (contains(name.toString(), true)) {
            throw new ParseException(String.format(
                    "duplicate identifier; found %s at line %d, column %d",
                    name.getSymbol().getSymbol(),
                    name.getSymbol().getLine(),
                    name.getSymbol().getCol()));
        }
        scopeStack.peek().add(name, type);
    }
    
    public void addFunction(String name){
        functionNames.add(name);
    }
        public void addPtrFunction(String name){
        ptrFunctions.add(name);
    }
    /**
     * Checks the return statement of a given Funct against its return requirements.
     * @param funct the Funct to be checked.
     * @return 
     */
    public boolean checkReturn(Funct funct){
    return checkReturn(funct, funct.getReturn());
    }
   /**
    * Checks a RetState against a Funct's return requirements.
    * @param funct the function to check.
    * @param ret the RetState to check.
    * @return true if the RetState satisfies the type requirement, otherwise false.
    */
    public boolean checkReturn(Funct funct, RetState ret){
    String mangle = funct.getName().toString();
    Type t = getType(mangle);
    Funcall call = ret.hasFuncall();
    if(call != null){
    return checkReturn(mangle, call);
    
    }
    Expression e =ret.getExpr();
    if(e == null){
    return t.toString().equals("void");
    }
    String varName = e.getSymbol().getSymbol();
    Type s = getType(varName);
    if(s != null){
    return s.toString().equals(t.toString());
    }else{
    SType varType =ret.getExpr().getSymbol().getSType();
    if(varType == SType.ID){//This protects against unrecognized var references. Presumably, no unrecognized var reference should be passed here. But just in case...
    return false;
    }
    String type = SType.getSTypeName(varType);
    return type.equals(t.toString());
    
    }
    }
    /**
     * Checks the Return requirement of one function against the return type of another function.
     * @param mangle name of the function with return requirement.
     * @param call the Funcall proposed as a return statement.
     * @return 
     */
    private boolean checkReturn(String mangle, Funcall call){
    Type t = getType(mangle);
    String retMangle = call.getMangle();
    Type s = getType(retMangle);
    if(s != null){
    return t.toString().equals(s.toString());
    }
    return false;
    }
    
    public boolean containsFunction(String name){
        return functionNames.contains(name);
    }
        public boolean containsPtrFunction(String name){
        return ptrFunctions.contains(name);
    }
    /**
     * Uses a Name and an ExprList to check if the SymbolTable contains a corresponding function.
     * @param ident the Name to search for
     * @param args the ExprList provided
     * @return ArrayList containing true/false in index 0, and the function searched for in index 1.
     */
    public ArrayList contains(Name ident, ExprList args){
    String params = convertNames(args);
    ArrayList results = new ArrayList();
    results.add(contains(ident.toString() + params));
    results.add(ident.toString() + params);
    return results;
    
    }
    /**
     * Takes ExprList, locates the variable names, and passes back a Polish string.
     * @param params ExprList containing the arguments to be checked.
     * @return String in Polish representing the parameters.
     */
    public String convertNames(ExprList params){
        
        String types = "$";
        if(params != null){
        ArrayList<Token> paramList = params.getContents();
        
        Type t;
    for(int i = 0; i < paramList.size(); i++){
        String s = paramList.get(i).getSymbol();
           if(contains(s)){
           t = getType(s);
           types += "_" + t.toPolish(); 
    }else{
           String v = paramList.get(i).getTypeName();
           types += "_" + v.toLowerCase().substring(0,1);
           
           }
           
           }
        }
    return types;
    }
    
    public boolean contains(String token){
        return contains(token, false);
    }

    public boolean contains(String token, boolean currentScopeOnly) {
        Scope scope = scopeStack.peek();
        while (scope != null) {
            if (scope.contains(token)) {
                return true;
            }
            if(currentScopeOnly){
                break;
            }
            scope = scope.getSuperScope();
        }
        return false;
    }
    
    public Scope getContainingScope(String token){
        Scope scope = scopeStack.peek();
        while(scope != null){
            if(scope.contains(token)){
                return scope;
            }
            scope = scope.getSuperScope();
        }
        return null;
    }
    
    
    public Name getName(String token){
        Scope scope = scopeStack.peek();
        while(scope!=null){
            if(scope.contains(token)){
                return scope.getName(token);
            }
            scope = scope.getSuperScope();
        }
        return null;
    }

    public Token getSymbol(String token) {
        Scope scope = scopeStack.peek();
        while (scope != null) {
            if (scope.contains(token)) {
                return scope.getSymbol(token);
            }
            scope = scope.getSuperScope();
        }
        return null;
    }

    public Type getType(String token) {
        Scope scope = scopeStack.peek();
        while (scope != null) {
            if (scope.contains(token)) {
                return scope.getType(token);
            }
            scope = scope.getSuperScope();
        }
        return null;
    }

    public void pushScope() {
        //Scope scope = new Scope(scopeCount++);
        Scope scope = new Scope(scopeStack.peek().getSubScopeCount());
        scope.linkSuperScope(scopeStack.peek());
        scopeStack.peek().linkSubScope(scope);
        scopeStack.push(scope);
    }

    public void popScope() {
        scopeStack.pop();
    }
    
    public Scope getCurrentScope() {
        return scopeStack.peek();
    }
    
    public Scope getGlobalScope() {
        return global;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(global.toString());
        return sb.toString();
    }

     //
    private final static SymbolTable symtab = new SymbolTable();
    //
    private final Scope global;
    private final Stack<Scope> scopeStack;
    private final ArrayList<String> functionNames;
    private final ArrayList<String> ptrFunctions;

}