package com.rogersalumni.calculator.desktop

import java.util.*

class CalculatorEngine {
    companion object {
        //        private val validOps = setOf(opAdd, opDiv, opMul, opPct, opSub, opNop, opEqu)
        private const val percentFactor = 0.01

        private val evalMap: HashMap<String, (Double, Double) -> Double> = hashMapOf(
            opAdd to { l, r -> l + r },
            opSub to { l, r -> l - r },
            opMul to { l, r -> l * r },
            opDiv to { l, r -> l / r },
            opPct to { l, r -> l + r * percentFactor }
        )

        private fun stripLeadingZero(arg: String): String {
            if (arg.length > 1 && arg.startsWith(char0)) return arg.substring(1)
            return arg
        }

        private fun String.evaluate(left: Double, right: Double): Double {
            val result = evalMap[this]?.invoke(left, right) ?: left

            println("evaluated $left $this $right to $result")
            return result
        }

    }

    private var opStack: Stack<String> = Stack()
    private var argStack: Stack<String> = Stack()
    private var isNewOp = true


    fun reset(): String {
        opStack.empty()
        argStack.empty()
        isNewOp = true

        opStack.push(opNop)
        return argStack.push(char0)
    }

    fun calc(inboundOperator: String, rightArg: String): String {
        println("calc opStack before : ${opStack.peek()}")
        println("calc rightArg       : $rightArg")
        println("calc argStack before: ${argStack.peek()}")

        isNewOp = true
        argStack.push(rightArg)

        val right = argStack.pop().toDouble()
        val left = argStack.pop().toDouble()
        val stackedOp = opStack.pop()


        val result = argStack.push(stackedOp.evaluate(left, right).toString())

        if (inboundOperator == opEqu) {
            opStack.push(opNop)
        } else {
            opStack.push(inboundOperator)
        }

        println("calc opStack after : ${opStack.peek()}")
        println("calc argStack after: ${argStack.peek()}")
        return result

    }

    fun accumulate(aChar: String): String {
        if (isNewOp) {
            println("accumulate: arg stack before encountering new op is $argStack")

            // Replace stack top with aChar
            argStack.pop()
            argStack.push(aChar)
            isNewOp = false
            println("accumulate: arg stack after encountering new op is $argStack")
        } else {
            argStack.push(stripLeadingZero(argStack.pop() + aChar))
        }
        println("accumulate: arg stack at return is $argStack")
        println("accumulate: op stack at return is $opStack")
        return argStack.peek()
    }

}