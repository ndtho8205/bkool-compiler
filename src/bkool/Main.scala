/**
  * @author nhphung
  */
package bkool

import java.io.{File, PrintWriter}
import java.util.concurrent.{Executors, TimeUnit, TimeoutException}

import bkool.checker.TestChecker
import org.antlr.v4.runtime.ANTLRFileStream

//import bkool.utils._
import bkool.codegen._
import bkool.parser._

trait Timed {
    def timeoutAfter(timeout: Long)(codeToTest: => Unit): Unit = {
        val executor = Executors.newSingleThreadExecutor
        val future = executor.submit(new Runnable {
            def run = codeToTest
        })

        try {
            future.get(timeout, TimeUnit.MILLISECONDS)
        }
        finally {
            executor.shutdown()
        }
    }
}

object Main extends Timed {

    val sepa = "/"
    val a3option = "testa3"
    val a4option = "testa4"
    val a4boption = "testa4b"
    val a2option = "testa2"


    def main(args: Array[String]): Unit = {
        if (args.length == 0) {
            val option = a4option
//            val option = a3option

            val starta3 = 0
            val enda3 = 0
            val indira3 = "checker"
            val outdira3 = "checker"

            val starta4 = 0
            val enda4 = 0
            val indira4 = "codegen"
            val outdira4 = "codegen"
//            val indira4 = "my_codegen"
//            val outdira4 = "my_codegen"
            val indira4b = "codegen_ast"
            val outdira4b = "codegen_ast"
            val indira2 = "codegen_ast"
            val outdira2 = "codegen_ast"

            option match {

                /*case "testphase1" => runTest(option,startphase1,endphase1,indirphase1,outdirphase1)
                case "testphase2" => {
                    runTest("testphase1",startphase1,endphase1,indirphase1,outdirphase1)
                    runTest(option,startphase2,endphase2,indirphase2,outdirphase2)
                }
                case "testphase3" => {
                    //runTest("testphase1",startphase1,endphase1,indirphase1,outdirphase1)
                    //runTest("testphase2",startphase2,endphase2,indirphase2,outdirphase2)
                    runTest(option,startphase3,endphase3,indirphase3,outdirphase3)
                }*/

                case `a3option` => runTest(a3option,starta3,enda3,indira3,outdira3)
                case `a2option`  => runTest(a2option, starta4, enda4, indira2, outdira2)
                case `a4option`  => runTest(a4option, starta4, enda4, indira4, outdira4)
                case `a4boption` => runTest(a4boption, starta4, enda4, indira4b, outdira4b)

                case _ => throw new ClassCastException
            }
        }
        else println("Usage: scala Main -option ")
    }

    def runTest(opt: String, start: Int, end: Int, indir: String, outdir: String) = {
        val testcases="/home/ndtho8205/Desktop/untitled/testcases/"
        for (i <- start to end) {

            println("Test " + i)


            val source = new ANTLRFileStream(s"$testcases$indir$sepa$i.txt")
            val dest = new PrintWriter(new File(s"$testcases$outdir$sepa$i-sol.txt"))

            try {
//                timeoutAfter(1000) {
                    opt match {
                        /*case "testphase1" => TestLexer.test(source,dest)
                        case "testphase2" => TestParser.test(source,dest)*/
                        case `a3option` => TestChecker.test(source,dest)

                        case `a2option`  => TestAst.test(source, dest)
                        case `a4option`  => TestCodeGen.test(source, s"$testcases$outdir", sepa, i, hasParser = true)
                        case `a4boption` => TestCodeGen.test(source, s"$testcases$outdir", sepa, i, hasParser = false)

                        case _ => throw new ClassCastException
                    }
                }
//            }
            catch {
                case te: TimeoutException => dest.println("Test runs timeout")
                case re: RuntimeException => dest.println(re.getMessage)
                //case e : Exception => dest.println(e)
            }
            finally {
                //source.close()
                dest.close()

            }
        }
    }
}