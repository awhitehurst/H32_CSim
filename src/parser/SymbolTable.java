/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.ArrayList;
import java.util.Stack;
import lexer.SType;
import lexer.Token;
import ptn.ExprList;
import ptn.Name;
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
    
    public boolean containsFunction(String name){
        return functionNames.contains(name);
    }
    /**
     * Takes ExprList, locates the variable names, and passes back a Polish string.
     * @param params ExprList containing the arguments to be checked.
     * @return String in Polish representing the parameters.
     */
    public String convertNames(ExprList params){
        ArrayList<Token> paramList = params.getContent();
        String types = "$";
        Type t;
    for(int i = 0; i < paramList.size(); i++){
        String s = paramList.get(i).getSymbol();
           if(contains(s)){
           t = getType(s);
           types += "_" + t.toPolish(); 
    }else{
           String v = SType.getSTypeName(paramList.get(i).getSType());
           types += "_" + v.toLowerCase().substring(0,1);
           
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

}
