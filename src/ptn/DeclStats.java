/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *creates a declaration for statements 
 * @author Alan
 */
public class DeclStats extends PTNode {
    
    /**
     * set the array for the statements and declerations 
     */
    public DeclStats(){
        decls = new ArrayList<Decl>();
        stats = new ArrayList<Statement>();
    }

    /**
     * return the array declerations 
     * @return 
     */
    public ArrayList<Decl> getDecls() {
        return decls;
    }

    /**
     * set the values of the declerations in the array 
     * @param decls 
     */
    public void setDecls(ArrayList<Decl> decls) {
        this.decls = decls;
    }

    /**
     * return the values in the statement array  
     * @return 
     */
    public ArrayList<Statement> getStats() {
        return stats;
    }

    /**
     * set the values of the statement array 
     * @param stats 
     */
    public void setStats(ArrayList<Statement> stats) {
        this.stats = stats;
    }
    
    /**
     * add declerations to the array 
     * @param decl 
     */
    public void addDecl(Decl decl){
        decls.add(decl);
    }
    
    /**
     * add statements to the array 
     * @param stat 
     */ 
    public void addStat(Statement stat){
        stats.add(stat);
    }
    
    /**
     * check to see if array is empty 
     * @return 
     */
    public boolean isEmpty(){
        return decls.isEmpty() && stats.isEmpty();
    }
    
    @Override
    public String format(int indent){
        StringBuilder sb = new StringBuilder();
        Iterator<Decl> myDecls = decls.iterator();
        while(myDecls.hasNext()){
            sb.append(myDecls.next().format(indent));
        }
        Iterator<Statement> myStats = stats.iterator();
        while(myStats.hasNext()){
            sb.append(myStats.next().format(indent));
        }
        return sb.toString();
    }
    
    @Override
    public ast.ASTNode toAST(){
        throw new UnsupportedOperationException("toAST not supported by DeclStats");
    }
    
    //itorate declaration array 
    public ArrayList<ast.Declaration> toDeclarations(){
        ArrayList<ast.Declaration> declarations = new ArrayList<ast.Declaration>();
        Iterator<Decl> it = decls.iterator();
        while(it.hasNext()){
            Decl decl = it.next();
            declarations.add((ast.Declaration)decl.toAST());
        }
        return declarations;
    }
    
    //itorate statments array 
    public ArrayList<ast.Statement> toStatements(){
        ArrayList<ast.Statement> statements = new ArrayList<ast.Statement>();
        Iterator<Statement> it = stats.iterator();
        while(it.hasNext()){
            Statement stat = it.next();
            statements.add((ast.Statement)stat.toAST());
        }
        return statements;
    }
    /**
     * Locates any return statement in the DeclStates.
     * @return the RetState if found, or null.
     */
    public RetState getReturn(){
    if(isEmpty()){
    return null;
    }
    for(Statement s: stats){
    if(s instanceof RetState){
    return (RetState)s;
    }
    }
    return null;
    }
            
    private ArrayList<Decl> decls;
    private ArrayList<Statement> stats;
    
}
