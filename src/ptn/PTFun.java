
package ptn;

import ast.ASTNode;
import parser.Scope;

/**
 *
 * @author daugh
 */
public class PTFun extends VarDecl{
    public PTFun(){
    
    super();
    }

    public TypeList getTypeList(){
    return (TypeList) children.get(3);
    
    }
    public void setTypeList(TypeList list){
    
    children.set(3, list);
    }
        public boolean hasArgs() {
        return children.get(3) != null;
    }

   public String getNameMangle() {
        if (getTypeList() == null) {
            return getName().toString() + "$";
        } else {
            return getName().toString() + getTypeList().toPolish();
        }
    }
    @Override
    public ast.PTFun toAST() {
        ast.PTFun ptFun = new ast.PTFun();
        ptFun.setVariable(getName().toAST());
        if (hasArgs()) {
            ptFun.setArgs((ast.TypeList) getTypeList().toAST());
        }
        ptFun.setScope(getScope());
        return ptFun;
    }
}
