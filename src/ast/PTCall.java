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
         //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        code.add("; PTFun");
        code.add("\tldc 0");
        code.add("\tpush");
        code.add("\tldc " + getName().getName());
        code.add("\tstr 0");
        if(getArgs() != null){
        for(int i = 0; i < getArgs().size(); i++){
        code.add("\tldc " + getArgs().get(i));
        code.add("\tpush");
        }
        }
        code.add("\tldr -1");
        code.add("\tpush");
        code.add("\tldr 0");
        code.add("\tcali");
        if(getArgs() != null){
        code.add("\tdloc " + getArgs().size());
        }
        code.add("\tstr -1");
        
               
        if(getArgs()!= null){
            getArgs().generate(code, inFunction);
            
        }
}
}
