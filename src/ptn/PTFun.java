
package ptn;

import ast.ASTNode;
import parser.Scope;

/**
 *
 * @author daugh
 */
public class PTFun extends VarDecl{
    Scope scope;
    public PTFun(){
    
    super();
    }

        public Name getName() {
        return (Name) children.get(1);
    }

    public void setName(Name name) {
        children.set(1, name);
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

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
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
        ptFun.setName(getName().toAST());
        if (hasArgs()) {
            ptFun.setArgs((ast.TypeList) getTypeList().toAST());
        }
        ptFun.setScope(scope);
        return ptFun;
    }
}
