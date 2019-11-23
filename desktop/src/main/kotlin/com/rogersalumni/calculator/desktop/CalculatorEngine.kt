package com.rogersalumni.calculator.desktop

import java.util.*

class CalculatorEngine {
    companion object {
        private const val percentFactor = 0.01

        private val evalMap: Map<String, (Double, Double) -> Double> = hashMapOf(
            opAdd to { l, r -> l + r },
            opSub to { l, r -> l - r },
            opMul to { l, r -> l * r },
            opDiv to { l, r -> l / r },
            opPct to { l, r -> l + r * percentFactor },
            opNop to { l, _ -> l }
        )

        private fun stripLeadingZero(arg: String): String {
            if (arg.length < 2 || !arg.startsWith(char0)) return arg

            return stripLeadingZero(arg.substring(1))
        }

        // The heart of the calculator binary op expression evaluation
        private fun String.evaluate(left: Double, right: Double): Double {
            return evalMap[this]?.invoke(left, right) ?: left
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

    fun calc(inboundOperator: String): String {
        println("calc opStack before : $opStack")
        println("calc argStack before: $argStack")

        // Flag the next accumulate fn invocation signaling it to start building new arg from numeric button clicks
        isNewOp = true

        val left = argStack.pop().toDouble()
        val right = argStack.pop().toDouble()
        val stackedOp = opStack.pop()

        // 1. Evaluate the stacked operation and pair of stacked arguments to a result
        val result = stackedOp.evaluate(left, right).toString()

        println("evaluated $left $this $right to $result")

        // 2. If inbound operator is not "=", push inbound operator
        //    else push noOp, a "0" arg, and push result arg from
        if (inboundOperator != opEqu) {
            opStack.push(inboundOperator)
        } else {
            opStack.push(opNop)
            argStack.push(char0)
        }
        // 3 Always push result last (top of arg stack)
        argStack.push(result)

        println("calc opStack after : $opStack")
        println("calc argStack after: $argStack")

        // 3. Return result from 1 to calling view
        return result
    }

    fun accumulate(aChar: String): String {
        if (isNewOp) {
//            println("accumulate: arg stack before encountering new op is $argStack")

            argStack.push(aChar)
            isNewOp = false
//            println("accumulate: arg stack after encountering new op is $argStack")
        } else {
            argStack.push(stripLeadingZero(argStack.pop() + aChar))
        }
//        println("accumulate: arg stack at return is $argStack")
//        println("accumulate: op stack at return is $opStack")
        return argStack.peek()
    }

}