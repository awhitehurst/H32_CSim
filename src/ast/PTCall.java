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
