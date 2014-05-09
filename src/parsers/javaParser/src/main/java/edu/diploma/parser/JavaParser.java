/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.parser;

import java.io.File;
import java.io.FileInputStream;
import main.java.edu.diploma.metamodel.TranslationUnit;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 *
 * @author alexander
 */
public class JavaParser {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: javaParser <file>");
            System.exit(1);
        }
        
        ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(args[0]));
        JavaGrammarLexer lexer = new JavaGrammarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaGrammarParser parser = new JavaGrammarParser(tokens);
        TranslationUnit unit = parser.compilationUnit().result;
        
        Serializer serializer = new Persister();
        File result = new File("metamodel.xml");
        serializer.write(unit, result);
    }
    
}
