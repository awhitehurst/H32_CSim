/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Alan
 */
public class DeclStats extends PTNode {
    
    public DeclStats(){
        decls = new ArrayList<Decl>();
        stats = new ArrayList<Statement>();
    }

    //return decls array values 
    public ArrayList<Decl> getDecls() {
        return decls;
    }

    //set decls array value 
    public void setDecls(ArrayList<Decl> decls) {
        this.decls = decls;
    }

    //return stats array
    public ArrayList<Statement> getStats() {
        return stats;
    }

    //set stats array value 
    public void setStats(ArrayList<Statement> stats) {
        this.stats = stats;
    }
    
    //add decl value to array 
    public void addDecl(Decl decl){
        decls.add(decl);
    }
    
    //add stat value to array 
    public void addStat(Statement stat){
        stats.add(stat);
    }
    
    // check to see if array is empty 
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
            
    private ArrayList<Decl> decls;
    private ArrayList<Statement> stats;
    
}
