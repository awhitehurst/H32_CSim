/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;

/**
 *
 * @author daugh
 */
public class PTCall extends Proccall{
   
      @Override
    public String format(int indent, boolean suppressNL) {
        StringBuilder sb = new StringBuilder();
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("[PTCall: ");
        sb.append(this.getName().format(indent, true));
        sb.append(" Args: ");
        if (args != null) {
            sb.append(args.format(indent, true));
        } else {
            sb.append("()");
        }
        sb.append(" ]");
        if (!suppressNL) {
            sb.append("\n");
        }
        return sb.toString();
    }
    //This may need to be edited.
        @Override
    public void generate(ArrayList<String> code, boolean inFunction, int offset) {
        if(args!=null){
            args.generate(code, inFunction);
        }
        code.add("\tcall " + getName().getName() + (args!=null?args.toPolish():"$"));
        if(args!=null){
            code.add("\tdloc " + args.size());
        }
    }
}
