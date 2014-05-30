/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.parser;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import edu.diploma.metamodel.Metamodel;
import edu.diploma.metamodel.TranslationUnit;
import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 *
 * @author alexander
 */
public class JavaParser {
    private static class Params {
        @Parameter
        public List<String> inputs = new LinkedList<>();

        @Parameter(names = "-o")
        public String output = "metamodel.xml";
    }
    
    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        final Params params = new Params();
        final JCommander commandParser = new JCommander(params);
        commandParser.parse(args);
        
        List<TranslationUnit> result = new LinkedList<>();
        for (final String input : params.inputs) {
            ANTLRInputStream in = new ANTLRInputStream(new FileInputStream(input));
            JavaGrammarLexer lexer = new JavaGrammarLexer(in);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            JavaGrammarParser parser = new JavaGrammarParser(tokens);
            result.add(parser.compilationUnit().result);
        }      
        
        final Serializer serializer = new Persister();
        final File xmlresult = new File(params.output);
        serializer.write(new Metamodel(result), xmlresult);
    }
    
}
