/*
*	CodeGenerator
*/
package bkool.codegen

import scala.collection.mutable.StringBuilder
import scala.collection.immutable.List
import scala.io.Source
import java.io.File
import java.io.PrintWriter
import org.antlr.v4.runtime.ANTLRFileStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree._
import bkool.utils._
import bkool.parser._
import bkool.astgen._


object TestCodeGen {
    def test(infile:ANTLRFileStream,outdir:String,sepa:String,i:Int,hasParser:Boolean) = {
        val ast = if (hasParser) {
            val lexer = new BKOOLLexer(infile);
            val tokens = new CommonTokenStream(lexer);
            val parser = new BKOOLParser(tokens);
            val progtree = parser.program()
        
            val astbuild = new ASTGeneration()
            astbuild.visit(progtree).asInstanceOf[Program]
        } else {
            val astbuild = new AstRebuild()
            astbuild.generate(infile.toString).asInstanceOf[Program]
        }
        
        try {
            val flder = new File(outdir + sepa + i)
            val succ = flder.mkdir
             
            CodeGenerator.check(ast, flder)
            
            
        } catch {
            case e:Exception  => e.printStackTrace
        }
    }
}





