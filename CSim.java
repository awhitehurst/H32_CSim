/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csim;

import ast.ASTNode;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import lexer.Lexer;
import ptn.PTNode;
import parser.Parser;

/**
 * Makes use of the lexer and parser to generate an ArrayList<String> of assembly code.
 * Checks first for lexer and parse errors. Will display any found errors to the system
 * @author alan.whitehurst
 */
public class CSim {

    /**
     * If the there are multiple args in the command line or none it will give an error. 
     * Starts by lexing the file given in the command line.
     * Then parses the lexer by creating a Parser object using the lexer as a parameter.
     * Starts a Parser Tree with the parser.parse() method being used to make the root.
     * If there are any errors in the parser it will display before going any further.
     * 
     * Displays the Formatted Parse Tree, Symbol Table, and Abstract Syntax Tree.
     * Will then check the parser for errors using comparing the symbol table with astRoot
     * If there are any errors it stops and displays the errors.
     * 
     * If no errors found it will call the generate method and generate assembly code and display it to the system.
     * Creates a new .as file containing the assembly code generated.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("usage: csim <filename>");
            System.exit(1);
        }
        Lexer lexer = new Lexer(args[0]);
        Parser parser = new Parser(lexer);
        PTNode ptnRoot = parser.parse();
        if (parser.hasErrors()) {
            Iterator<String> msgs = parser.getErrorMessages().iterator();
            while (msgs.hasNext()) {
                System.out.println(msgs.next());
            }
        } else {
            System.out.println("\n\nFormatted Parse Tree: \n");
            System.out.println(ptnRoot.format(0));
            System.out.println("\n\nSymbol Table: \n");
            System.out.println(parser.getSymbolTable());
            System.out.println("\n\nAbstract Syntax Tree: ");
            ASTNode astRoot = ptnRoot.toAST();
            System.out.println(astRoot.format(0));
            Checker checker = new Checker(parser.getSymbolTable(), astRoot);
            checker.check();
            if (checker.hasError()) {
                Iterator<String> msgs = checker.getErrorMessages().iterator();
                while (msgs.hasNext()) {
                    System.out.println(msgs.next());
                }
            } else {
                BufferedWriter writer = null;
                try {
                    Generator cg = new Generator("test", parser.getSymbolTable(), astRoot);
                    ArrayList<String> output = cg.generate();
                    System.out.println("Output:\n\n");
                    for (String line : output) {
                        System.out.println(line);
                    }
                    String ofilename = args[0];
                    if (ofilename.endsWith(".csm")) {
                        ofilename = ofilename.substring(0, ofilename.length() - 4) + ".as";
                    } else {
                        ofilename += ".as";
                    }
                    System.out.println("Writing '" + ofilename + "'");
                    writer = new BufferedWriter(new FileWriter(ofilename));
                    for (String line : output) {
                        writer.append(line);
                        writer.append("\n");
                    }
                    writer.flush();
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(CSim.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        if (writer != null) {
                            writer.close();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(CSim.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
