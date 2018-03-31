package ptn;

import ast.ASTNode;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Alan
 */
public class TypeList extends PTNode {
    
    public TypeList(){
    }
    
    public void addType(Type type){
        children.add(type);
    }
    
    public Type getType(int index){
        return (Type) children.get(index);
    }
    
    public void setType(int index, Type type){
        children.set(index, type);
    }
    
     public String toPolish() {
        StringBuilder sb = new StringBuilder("$");
        Iterator<PTNode> types = children.iterator();
        while (types.hasNext()) {
            Type arg = (Type) types.next();
            sb.append(arg.toPolish());
        }
        return sb.toString();
    }
    
    @Override
    public String format(int indent){
        StringBuilder sb = new StringBuilder();
        Iterator<PTNode> expressions = children.iterator();
        while(expressions.hasNext()){
            sb.append(expressions.next().format(indent));
            if(expressions.hasNext()){
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    @Override
    public ast.TypeList toAST() {
        ast.TypeList typeList = new ast.TypeList();
        Iterator<PTNode> types = children.iterator();
        while(types.hasNext()){
            typeList.add((ast.Type)types.next().toAST());
        }
        return null;
    }
}
