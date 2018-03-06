package ast;

import csim.Generator;
import java.util.ArrayList;
import java.util.Iterator;
import parser.Scope;

/**
 * A Block contains an ArrayList of statements, an ArrayList of declarations, and a scope.
 * @author Alan
 */
public class Block extends Statement {
/**
 * Constructor creates an empty Block.
 */
    public Block() {
        decls = new ArrayList<>();
        stats = new ArrayList<>();
        scope = null;
    }
/**
 * Adds a Declaration.
 * @param d 
 */
    public void addDecl(Declaration d) {
        decls.add(d);
    }
/**
 * Retrieves all declarations in this block
 * @return the ArrayList containing the declarations.
 */
    public ArrayList<Declaration> getDecls() {
        return decls;
    }
/**
 * Sets the ArrayList of Declarations to a new ArrayList of Declarations.
 * @param decls the new ArrayList of Declarations.
 */
    public void setDecls(ArrayList<Declaration> decls) {
        this.decls = decls;
    }
/**
 * Adds a Statement
 * @param s the statement to be added.
 */
    public void addStat(Statement s) {
        stats.add(s);
    }
/**
 * Returns an ArrayList containing all Statements.
 * @return 
 */
    public ArrayList<Statement> getStats() {
        return stats;
    }
/**
 * Sets the ArrayList containing Statements to a new provided ArrayList.
 * @param stats The new ArrayList of Statements.
 */
    public void setStats(ArrayList<Statement> stats) {
        this.stats = stats;
    }
/**
 * Returns the scope of the block
 * @return 
 */
    public Scope getScope() {
        return scope;
    }
/**
 * Sets the scope of the block to a new scope.
 * @param scope The new Scope.
 */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        Iterator<Declaration> theDecls = decls.iterator();
        while (theDecls.hasNext()) {
            theDecls.next().typeCheck(msgs);
        }
        Iterator<Statement> theStats = stats.iterator();
        while (theStats.hasNext()) {
            theStats.next().typeCheck(msgs);
        }
    }


    /**
     * Takes the array of delecrations and determins the total size of the stack
     * @return 
     */
    private int calculateTotalStackSize() {
        int totalSize = 0;
        if (getDecls() != null) {
            for (Declaration decl : getDecls()) {
                if (!decl.getType().isStatic()) {
                    if (decl instanceof ArrayDecl) {
                        ArrayDecl aDecl = (ArrayDecl) decl;
                        if (aDecl.getInitList() != null) {
                            totalSize += aDecl.getInitList().size() * aDecl.getAllocSize();
                        } else {
                            totalSize += ((Literal) aDecl.getSize()).toInt();
                        }
                    } else {
                        totalSize += decl.getAllocSize();
                    }
                }
            }
        }
        return totalSize;
    }

    private int assignRelativeAddresses(int offset) {
        if (getDecls() != null) {
            for (Declaration decl : getDecls()) {
                if (!decl.getType().isStatic()) {
                    if (decl instanceof ArrayDecl) {
                        ArrayDecl aDecl = (ArrayDecl) decl;
                        if (aDecl.getInitList() != null) {
                            offset += aDecl.getInitList().size();
                        } else {
                            offset += ((Literal) aDecl.getSize()).toInt();
                        }
                        getScope().setRelOffset(aDecl.getVariable().getName(), offset * -1);
                        System.out.println("Assigning " + aDecl.getVariable().getName() + " offset " + offset * -1);
                    } else {
                        ++offset;
                        getScope().setRelOffset(((VarDecl) decl).getVariable().getName(), offset * -1);
                        System.out.println("Assigning " + ((VarDecl) decl).getVariable().getName() + " offset " + offset * -1);
                    }
                }
            }
        }
        return offset;
    }

    public void generateDynamic(ArrayList<String> code, int baseOffset) {
        int offset = assignRelativeAddresses(baseOffset);
        if (getDecls() != null) {
            Iterator<Declaration> theDecls = decls.iterator();
            while (theDecls.hasNext()) {
                Declaration decl = theDecls.next();
                decl.generate(code, true);
            }
        }
        if (getStats() != null) {
            Iterator<Statement> theStats = stats.iterator();
            while (theStats.hasNext()) {
                Statement stat = theStats.next();
                stat.generate(code, true, offset);
            }
        }
        int totalSize = calculateTotalStackSize();
        if (totalSize > 0) {
            code.add("\tdloc " + totalSize);
        }
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        generate(code, inFunction, 0);
    }

    public void generate(ArrayList<String> code, boolean inFunction, int baseOffset) {
        if (inFunction) {
            generateDynamic(code, baseOffset);
        } else {
            if (!decls.isEmpty()) {
                String jumpLabel = Generator.getLabel();
                code.add("     ja " + jumpLabel);
                Iterator<Declaration> theDecls = decls.iterator();
                while (theDecls.hasNext()) {
                    Declaration decl = theDecls.next();
                    decl.generate(code, inFunction);
                }
                code.add(jumpLabel + ":");
            }
            if (!stats.isEmpty()) {
                Iterator<Statement> theStats = stats.iterator();
                while (theStats.hasNext()) {
                    Statement stat = theStats.next();
                    stat.generate(code, inFunction);
                }
            }
        }
    }

    @Override
    public String format(int indent, boolean suppressNL) {
        StringBuilder sb = new StringBuilder();
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("[Block:\n");
        Iterator<Declaration> theDecls = decls.iterator();
        while (theDecls.hasNext()) {
            sb.append(theDecls.next().format(indent + 3, false));
        }
        Iterator<Statement> theStats = stats.iterator();
        while (theStats.hasNext()) {
            sb.append(theStats.next().format(indent + 3, false));
        }
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("]");
        if (!suppressNL) {
            sb.append("\n");
        }
        return sb.toString();
    }
    //
    private ArrayList<Declaration> decls;
    private ArrayList<Statement> stats;
    private Scope scope;

}