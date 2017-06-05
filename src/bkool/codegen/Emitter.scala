/**
  * Nguyen Duc Tho
  * MSSV: 1413817
  */
package bkool.codegen

import java.io.FileWriter
import java.text.DecimalFormat

import bkool.utils._


class Emitter(filename: String) {
    val buff = new StringBuffer()
    val jvm  = new JasminCode()

    /** ***********************************************************************************************
      * Generate code
      * ***********************************************************************************************
      */

    // generate some starting directives for a class.<p>
    //  .source MPC.CLASSNAME.java<p>
    //  .class public MPC.CLASSNAME<p>
    //  .super java/lang/Object<p>
    def emitPROLOG(name: String, parent: String) = {
        val result = new StringBuffer()
        result.append(jvm.emitSOURCE(name + ".java"))
        result.append(jvm.emitCLASS("public " + name))
        result.append(jvm.emitSUPER(if (parent == "") "java/lang/Object" else parent))
        result.append("\n")
        result.toString
    }

    //generate code that represents a label
    def emitLABEL(label: Int, frame: Frame) = jvm.emitLABEL(label)

    // generate code to jump to a label
    def emitGOTO(label: Int, frame: Frame) = jvm.emitGOTO(label)

    def emitLIMITSTACK(num: Int) = jvm.emitLIMITSTACK(num)

    def emitLIMITLOCAL(num: Int) = jvm.emitLIMITLOCAL(num)

    // generate the method directive for a function.
    def emitMETHOD(lexeme: String, in: Type, isStatic: Boolean, frame: Frame) = jvm.emitMETHOD(lexeme, getJVMType(in), isStatic)

    // generate the end directive for a function.
    def emitENDMETHOD(frame: Frame) = {
        val buffer = new StringBuffer()
        buffer.append(jvm.emitLIMITSTACK(frame.getMaxOpStackSize()))
        buffer.append(jvm.emitLIMITLOCAL(frame.getMaxIndex()))
        buffer.append(jvm.emitENDMETHOD())
        buffer.toString
    }

    def emitEPILOG() = {
        val file = new FileWriter(filename)
        file.write(buff.toString)
        file.close()
    }

    /** ***********************************************************************************************
      * Push Constant
      * ***********************************************************************************************
      */

    def emitPUSHCONST(value: String, typ: Type, frame: Frame) =
        typ match {
            case (IntType | BoolType) => emitPUSHICONST(value, frame)
            case FloatType            => emitPUSHFCONST(value, frame)
            case StringType           =>
                frame.push()
                jvm.emitLDC(value)
            case NullType             =>
                frame.push()
                jvm.emitPUSHNULL()
            case SelfType             =>
                frame.push()
                jvm.emitALOAD(0) // index of "this" is always 0
            case _                    => throw IllegalOperandException(value)
        }

    def emitPUSHICONST(value: String, frame: Frame): String =
        value match {
            case "true"  => emitPUSHICONST(1, frame)
            case "false" => emitPUSHICONST(0, frame)
            case _       => emitPUSHICONST(value.toInt, frame)
        }

    private def emitPUSHICONST(value: Int, frame: Frame): String = {
        frame.push()
        if (value >= -1 && value <= 5) jvm.emitICONST(value)
        else if (value >= -128 && value <= 127) jvm.emitBIPUSH(value)
        else if (value >= -32768 && value <= 32767) jvm.emitSIPUSH(value)
        else jvm.emitLDC("" + value)
    }

    def emitPUSHFCONST(in: String, frame: Frame): String = {
        val f = in.toFloat
        frame.push()
        val myFormatter = new DecimalFormat("###0.0###")
        val rst = myFormatter.format(f)
        if (rst.equals("0.0") || rst.equals("1.0") || rst.equals("2.0"))
            jvm.emitFCONST(rst)
        else
            jvm.emitLDC(in)
    }

    /** ***********************************************************************************************
      * Attribute
      * ***********************************************************************************************
      */

    //generate the field (static) directive for a class mutable or immutable attribute.
    def emitATTRIBUTE(attrName: String, skind: SIKind, attrType: Type, isFinal: Boolean, value: String) =
    if (isFinal)
        emitCONSTATTRIBUTE(attrName, skind, attrType, value)
    else
        emitVARATTRIBUTE(attrName, skind, attrType)

    def emitVARATTRIBUTE(name: String, skind: SIKind, typ: Type) =
        (skind match {
            case Static => ".field static "
            case _      => ".field "
        }) + name + " " + getJVMType(typ) + "\n"

    def emitCONSTATTRIBUTE(name: String, skind: SIKind, typ: Type, value: String) =
        (skind match {
            case Static   => ".field static final "
            case Instance => ".field final "
        }) + name + " " + getJVMType(typ) + (
            typ match {
                case ClassType(_) => ""
                case _            => " = " + value;
            }) + "\n"


    def emitFieldInitialization(classname: String, lst: List[SimpleSymbol], clinit: Boolean, frame: Frame) = {
        val buffer = new StringBuffer()
        lst.foreach(symbol => {
            val fieldAccess = classname + "." + symbol.name
            if (!symbol.isConstant)
                symbol.dataType.get match {
                    case value: ArrayType =>
                        if (!clinit) buffer.append(emitREADVAR(0, "this", ClassType(classname), frame))
                        buffer.append(emitNEWARRAY(value, frame))
                        buffer.append(if (!clinit) emitPUTFIELD(fieldAccess, value, frame) else emitPUTSTATIC(fieldAccess, value, frame))
                        buffer.append("\n")
                    case _                => //buffer.append("")
                } else
                symbol.dataType.get match {
                    case value: ClassType =>
                        if (!clinit) buffer.append(emitREADVAR(0, "this", ClassType(classname), frame))
                        val cst = getConst(symbol.value.get)
                        buffer.append(emitPUSHCONST(cst._1, cst._2, frame))
                        buffer.append(if (!clinit) emitPUTFIELD(fieldAccess, value, frame) else emitPUTSTATIC(fieldAccess, value, frame))
                        buffer.append("\n")
                    case _                => //buffer.append("")
                }
        })
        buffer.toString
    }

    def emitALOAD(in: Type, frame: Frame) = {
        //..., arrayref, index -> ..., value
        frame.pop()
        in match {
            case IntType                                       => jvm.emitIALOAD()
            case FloatType                                     => jvm.emitFALOAD()
            case BoolType                                      => jvm.emitBALOAD()
            case (ArrayType(_, _) | ClassType(_) | StringType) => jvm.emitAALOAD()
            case _                                             => throw IllegalOperandException(in.toString);
        }
    }

    def emitASTORE(in: Type, frame: Frame) = {
        //..., arrayref, index, value -> ...
        frame.pop()
        frame.pop()
        frame.pop()
        in match {
            case IntType                                       => jvm.emitIASTORE()
            case FloatType                                     => jvm.emitFASTORE()
            case BoolType                                      => jvm.emitBASTORE()
            case (ArrayType(_, _) | ClassType(_) | StringType) => jvm.emitAASTORE()
            case _                                             => throw IllegalOperandException(in.toString)
        }
    }

    def emitGETSTATIC(lexeme: String, in: Type, frame: Frame) = {
        frame.push()
        jvm.emitGETSTATIC(lexeme, getJVMType(in))
    }

    def emitPUTSTATIC(lexeme: String, in: Type, frame: Frame) = {
        frame.pop()
        jvm.emitPUTSTATIC(lexeme, getJVMType(in))
    }

    def emitGETFIELD(lexeme: String, in: Type, frame: Frame) =
        jvm.emitGETFIELD(lexeme, getJVMType(in))

    def emitPUTFIELD(lexeme: String, in: Type, frame: Frame) = {
        frame.pop()
        frame.pop()
        jvm.emitPUTFIELD(lexeme, getJVMType(in))
    }

    /** ***********************************************************************************************
      * Variables
      * ***********************************************************************************************
      */

    //generate the var directive for a local variable.
    def emitVAR(idx: Int, varName: String, varType: Type, fromLabel: Int, toLabel: Int, frame: Frame) =
        jvm.emitVAR(idx, varName, getJVMType(varType), fromLabel, toLabel)

    // generate code to put the value of a variable onto the operand stack.
    def emitREADVAR(idx: Int, varName: String, varType: Type, frame: Frame): String = {
        //... -> ..., value
        frame.push()
        varType match {
            case (IntType | BoolType)                          => jvm.emitILOAD(idx)
            case FloatType                                     => jvm.emitFLOAD(idx)
            case (ArrayType(_, _) | ClassType(_) | StringType) => jvm.emitALOAD(idx)
            case _                                             => throw IllegalOperandException(varName)
        }
    }

    // generate the second instruction for array cell access
    def emitREADVAR2(varName: String, varType: Type, frame: Frame) = {
        //... -> ..., value
        //frame.push();
        varType match {
            case ArrayType(_, elemType) => emitALOAD(elemType, frame)
            case _                      => throw IllegalOperandException(varName)
        }
    }

    // generate code to pop a value on top of the operand stack and store it to a block-scoped variable.
    def emitWRITEVAR(index: Int, name: String, inType: Type, frame: Frame): String = {
        //..., value -> ...
        frame.pop()
        inType match {
            case (IntType | BoolType)                          => jvm.emitISTORE(index)
            case FloatType                                     => jvm.emitFSTORE(index)
            case (ArrayType(_, _) | ClassType(_) | StringType) => jvm.emitASTORE(index)
            case _                                             => throw IllegalOperandException(name)
        }

    }

    // generate the second instruction for array cell access*/
    def emitWRITEVAR2(varName: String, varType: Type, frame: Frame) = {
        //... -> ..., value
        //frame.push();
        varType match {
            case ArrayType(_, elemType) => emitASTORE(elemType, frame)
            case _                      => throw IllegalOperandException(varName)
        }
    }
    /** ***********************************************************************************************
      * Invoke method
      * ***********************************************************************************************
      */

    def emitINVOKESTATIC(lexeme: String, in: Type, frame: Frame) = {
        val typ = in.asInstanceOf[MethodType]
        typ.in.foreach(_ => frame.pop())
        if (typ.out != VoidType)
            frame.push()
        jvm.emitINVOKESTATIC(lexeme, getJVMType(in))
    }

    def emitINVOKEVIRTUAL(lexeme: String, in: Type, frame: Frame) = {
        val typ = in.asInstanceOf[MethodType]
        typ.in.foreach(_ => frame.pop())
        frame.pop()
        if (typ.out != VoidType)
            frame.push()
        jvm.emitINVOKEVIRTUAL(lexeme, getJVMType(in))
    }

    def emitINVOKESPECIAL(lexeme: String, in: Type, frame: Frame) = {
        val typ = in.asInstanceOf[MethodType]
        typ.in.foreach(_ => frame.pop())
        frame.pop()
        if (typ.out != VoidType)
            frame.push()
        jvm.emitINVOKESPECIAL(lexeme, getJVMType(in))
    }

    def emitINVOKESPECIAL(frame: Frame) = {
        frame.pop()
        jvm.emitINVOKESPECIAL()
    }

    // generate code to return.
    def emitRETURN(in: Type, frame: Frame) = {
        in match {
            case (IntType | BoolType)                                   => frame.pop(); jvm.emitIRETURN()
            case FloatType                                              => frame.pop(); jvm.emitFRETURN()
            case VoidType                                               => jvm.emitRETURN()
            case ArrayType(_, _) | ClassType(_) | StringType | NullType =>
                //println("Inside return in "+frame.name+" "+frame.getStackSize())
                frame.pop()
                jvm.emitARETURN()
            //case ClassType(_) => frame.pop();jvm.emitARETURN()
        }
    }

    /** ***********************************************************************************************
      * Operators
      * ***********************************************************************************************
      */

    // generate code to create an object
    def emitNEW(in: ClassType, frame: Frame) = {
        val buffer = new StringBuffer()
        buffer.append(jvm.emitNEW(in.classType))
        frame.push()
        buffer.append(jvm.emitDUP())
        frame.push()
        buffer.toString
    }

    def emitNEGOP(in: Type, frame: Frame) = {
        //..., value -> ..., result
        if (in == IntType) jvm.emitINEG() else jvm.emitFNEG()
    }

    def emitADDOP(lexeme: String, in: Type, frame: Frame) = {
        //..., value1, value2 -> ..., result
        frame.pop()
        if (lexeme.equals("+")) {
            if (in == IntType) jvm.emitIADD() else jvm.emitFADD()
        } else {
            if (in == IntType) jvm.emitISUB() else jvm.emitFSUB()
        }
    }

    def emitMULOP(lexeme: String, in: Type, frame: Frame) = {
        //..., value1, value2 -> ..., result
        frame.pop()
        if (lexeme.equals("*")) {
            if (in == IntType) jvm.emitIMUL() else jvm.emitFMUL()
        }
        else {
            if (in == IntType) jvm.emitIDIV() else jvm.emitFDIV()
        }
    }

    def emitDIV(frame: Frame) = {
        frame.pop()
        jvm.emitIDIV()
    }

    def emitMOD(frame: Frame) = {
        frame.pop()
        jvm.emitIREM()
    }


    def emitANDOP(frame: Frame) = {
        frame.pop()
        jvm.emitIAND()
    }

    def emitOROP(frame: Frame) = {
        frame.pop()
        jvm.emitIOR()
    }

    def emitREOP(op: String, in: Type, frame: Frame) = {
        //..., value1, value2 -> ..., result
        val result = new StringBuffer()
        val labelF = frame.getNewLabel()
        val labelO = frame.getNewLabel()

        frame.pop()
        frame.pop()
        op match {
            case ">"  => if (in == FloatType) {
                result.append(jvm.emitFCMPL())
                result.append(jvm.emitIFLE(labelF))
            }
            else {
                result.append(jvm.emitIFICMPLE(labelF))
            }
            case ">=" => if (in == FloatType) {
                result.append(jvm.emitFCMPL())
                result.append(jvm.emitIFLT(labelF))
            } else {
                result.append(jvm.emitIFICMPLT(labelF))
            }
            case "<"  => if (in == FloatType) {
                result.append(jvm.emitFCMPL())
                result.append(jvm.emitIFGE(labelF))
            } else {
                result.append(jvm.emitIFICMPGE(labelF))
            }
            case "<=" => if (in == FloatType) {
                result.append(jvm.emitFCMPL())
                result.append(jvm.emitIFGT(labelF))
            }
            else {
                result.append(jvm.emitIFICMPGT(labelF))
            }
            case "!=" => if (in == FloatType) {
                result.append(jvm.emitFCMPL())
                result.append(jvm.emitIFEQ(labelF))
            } else if (in.isInstanceOf[ClassType] || in.isInstanceOf[ArrayType] || in == StringType || in == NullType) {
                result.append(jvm.emitIFACMPEQ(labelF))
            }
            else {
                result.append(jvm.emitIFICMPEQ(labelF))
            }
            case "==" => if (in == FloatType) {
                result.append(jvm.emitFCMPL())
                result.append(jvm.emitIFNE(labelF))
            }
            else if (in.isInstanceOf[ClassType] || in.isInstanceOf[ArrayType] || in == StringType || in == NullType) {
                result.append(jvm.emitIFACMPNE(labelF))
            }
            else {
                result.append(jvm.emitIFICMPNE(labelF))
            }
        }
        result.append(emitPUSHCONST("true", BoolType, frame))
        frame.pop()
        result.append(emitGOTO(labelO, frame))
        result.append(emitLABEL(labelF, frame))
        result.append(emitPUSHCONST("false", BoolType, frame))
        result.append(emitLABEL(labelO, frame))
        result.toString
    }

    def emitNOT(in: Type, frame: Frame) = {
        val label1 = frame.getNewLabel()
        val label2 = frame.getNewLabel()
        val result = new StringBuffer()
        result.append(emitIFTRUE(label1, frame))
        result.append(emitPUSHCONST("true", in, frame))
        result.append(emitGOTO(label2, frame))
        result.append(emitLABEL(label1, frame))
        result.append(emitPUSHCONST("false", in, frame))
        result.append(emitLABEL(label2, frame))
        result.toString
    }

    def emitSTRBUILDER(frame: Frame) = {
        emitNEW(ClassType("java/lang/StringBuilder"), frame) +
        emitINVOKESPECIAL("java/lang/StringBuilder/<init>", MethodType(List(), VoidType), frame)
    }

    def emitAPPEND(frame: Frame) = {
        val strBuilderClass = "java/lang/StringBuilder"
        emitINVOKEVIRTUAL(strBuilderClass + "/append", MethodType(List(StringType), ClassType(strBuilderClass)), frame)
    }

    def emitTOSTRING(frame: Frame) = {
        val strBuilderClass = "java/lang/StringBuilder"
        emitINVOKEVIRTUAL(strBuilderClass + "/toString", MethodType(List(), StringType), frame)
    }

    /** ***********************************************************************************************
      * If Statements
      * ***********************************************************************************************
      */

    // generate code to jump to label if the value on top of operand stack is true.
    def emitIFTRUE(label: Int, frame: Frame) = {
        frame.pop()
        jvm.emitIFGT(label)
    }

    // generate code to jump to label if the value on top of operand stack is false.
    def emitIFFALSE(label: Int, frame: Frame) = {
        frame.pop()
        jvm.emitIFLE(label)
    }

    def emitIFICMPGT(label: Int, frame: Frame) = {
        frame.pop()
        jvm.emitIFICMPGT(label)
    }

    def emitIFICMPLT(label: Int, frame: Frame) = {
        frame.pop()
        jvm.emitIFICMPLT(label)
    }

    def emitRELOP(op: String, in: Type, elseLabel: Int, frame: Frame) = {
        //..., value1, value2 -> ..., result
        val result = new StringBuffer()
        //val (isFalse,label) = if (trueLabel == CodeGenVisitor.FallThrough) (true,falseLabel) else (false,trueLabel)
        frame.pop()
        frame.pop()
        op match {
            case ">"  =>
                if (in != FloatType) {
                    result.append(jvm.emitIFICMPLE(elseLabel))
                    //result.append(jvm.emitGOTO(trueLabel))
                } else {
                    result.append(jvm.emitFCMPL())
                    result.append(jvm.emitIFLE(elseLabel))
                }
            case ">=" =>
                if (in != FloatType)
                    result.append(jvm.emitIFICMPLT(elseLabel))
                else {
                    result.append(jvm.emitFCMPL())
                    result.append(jvm.emitIFLT(elseLabel))
                }
            case "<"  =>
                if (in != FloatType)
                    result.append(jvm.emitIFICMPGE(elseLabel))
                else {
                    result.append(jvm.emitFCMPL())
                    result.append(jvm.emitIFGE(elseLabel))
                }
            case "<=" =>
                if (in != FloatType)
                    result.append(jvm.emitIFICMPGT(elseLabel))
                else {
                    result.append(jvm.emitFCMPL())
                    result.append(jvm.emitIFGT(elseLabel))
                }
            case "!=" =>
                if (in.isInstanceOf[ClassType] || in.isInstanceOf[ArrayType] || in == StringType || in == NullType) {
                    result.append(jvm.emitIFACMPEQ(elseLabel))
                } else if (in == FloatType) {
                    result.append(jvm.emitFCMPL())
                    result.append(jvm.emitIFEQ(elseLabel))
                } else
                    result.append(jvm.emitIFICMPEQ(elseLabel))
            case "==" =>
                if (in.isInstanceOf[ClassType] || in.isInstanceOf[ArrayType] || in == StringType || in == NullType) {
                    result.append(jvm.emitIFACMPNE(elseLabel))
                } else if (in == FloatType) {
                    result.append(jvm.emitFCMPL())
                    result.append(jvm.emitIFNE(elseLabel))
                } else
                    result.append(jvm.emitIFICMPNE(elseLabel))
        }
        //result.append(jvm.emitGOTO(trueLabel))
        result.toString
    }

    /** ***********************************************************************************************
      * Utilities
      * ***********************************************************************************************
      */

    def printout(in: String) = buff.append(in)

    def clearBuff() = buff.setLength(0)

    def getJVMType(inType: Type): String = inType match {
        case IntType                            => "I"
        case FloatType                          => "F"
        case BoolType                           => "Z"
        case StringType                         => "Ljava/lang/String;"
        case VoidType                           => "V"
        case ArrayType(_, elemType)             => "[" + getJVMType(elemType)
        case ClassType(className)               => "L" + className + ";"
        case MethodType(paramsType, returnType) =>
            "(" + paramsType.foldLeft("")(_ + getJVMType(_)) + ")" + getJVMType(returnType)
    }

    def getFullType(inType: Type): String = inType match {
        case IntType              => "int"
        case FloatType            => "float"
        case BoolType             => "boolean"
        case StringType           => "java/lang/String"
        case ClassType(className) => className
        case VoidType             => "void"
    }

    def getConst(ast: Literal) = ast match {
        case IntLiteral(i)     => (i.toString, IntType)
        case FloatLiteral(i)   => (i.toString, FloatType)
        case StringLiteral(i)  => (i, StringType)
        case BooleanLiteral(i) => (i.toString, BoolType)
        case NullLiteral       => ("null", NullType)
        case SelfLiteral       => ("this", SelfType)
    }

    // generate code to exchange an integer on top of stack to a floating-point number.
    def emitI2F(frame: Frame) = jvm.emitI2F()

    // generate code to duplicate the value on the top of the operand stack.
    def emitDUP(frame: Frame) = {
        frame.push()
        jvm.emitDUP()
    }

    // generate code to pop the value on the top of the operand stack.
    def emitPOP(frame: Frame) = {
        frame.pop()
        jvm.emitPOP()
    }

    // generate code to create an array
    def emitNEWARRAY(in: ArrayType, frame: Frame) = {
        val buffer = new StringBuffer()
        buffer.append(emitPUSHICONST(in.dimen.value, frame))
        in.eleType match {
            case (ClassType(_) | StringType)      => buffer.append(jvm.emitANEWARRAY(getFullType(in.eleType)))
            case (IntType | FloatType | BoolType) => buffer.append(jvm.emitNEWARRAY(getFullType(in.eleType)))
        }
        buffer.toString
    }
}
