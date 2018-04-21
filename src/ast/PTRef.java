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
public class PTRef extends VarRef{
    
    //This code is simply copied over from VarRef. It may or may not need to be edited.
        @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        if (!getType().isStatic() && inFunction) {
            if (isArrayRef()) {
                getIndex().generate(code, inFunction);
                code.add("\taddc " + getScope().getRelOffset(getVariable().getName()));
                code.add("\tcora");
                code.add("\tldi");
            } else {
                code.add("\tldr " + getScope().getRelOffset(getVariable().getName()));
            }
        } else if (isArrayRef()) {
            getIndex().generate(code, inFunction);
            code.add("\taddc " + getQualifiedVariableName());
            code.add("\tldi");
//        } else if (getType().isPointer()) {
//            code.add("\tldc " + getQualifiedVariableName());
        } else {
            code.add("\tld " + getQualifiedVariableName());
        }
    }
}
