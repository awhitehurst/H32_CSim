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
     @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<PTRef: ");
        sb.append(v);
        sb.append(" (");
        sb.append(indirect);
        sb.append(")");
        if (index != null) {
            sb.append(" = ");
            sb.append(index);
        }
        sb.append(" >");
        return sb.toString();
    }
        @Override
    public String format(int indent, boolean suppressNL) {
        StringBuilder sb = new StringBuilder();
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("[PTRef: ");
        if (isIndirect()) {
            for (int i = 0; i < indirect; ++i) {
                sb.append("*");
            }
        }
        sb.append(this.getVariable().format(indent, true));
        sb.append(" ");
        if (isArrayRef()) {
            sb.append("index: ");
            sb.append(index.format(indent, true));
        }
        sb.append(" ]");
        if (!suppressNL) {
            sb.append("\n");
        }
        return sb.toString();
    }
}
