package com.rogersalumni.calculator.desktop

import javafx.geometry.Pos
import javafx.scene.control.TextField
import tornadofx.*

// Dual: can form arithmetic operand as well as being render-able
private const val uMinus = "-"
private const val opAdd = "+"
private const val opSub = "-"
private const val opMul = "*"
private const val opDiv = "/"
private const val opPct = "%"
private const val opEqual = "="

// Dual: label text as well as used to build a numeric string from keystrokes
private const val charDot = "."
private const val char0 = "0"
private const val char1 = "1"
private const val char2 = "2"
private const val char3 = "3"
private const val char4 = "4"
private const val char5 = "5"
private const val char6 = "6"
private const val char7 = "7"
private const val char8 = "8"
private const val char9 = "9"

// Button Labels only
private const val labelClear = "C"
private const val labelPlusMinus = "\u00B1"
private const val labelAdd = "\uFF0B"
private const val labelSub = "\u2212"
private const val labelMul = "\u2A09"
private const val labelDiv = "\u00F7"

private fun flipSign(arg: String): String {
    if (arg.startsWith(uMinus)) {
        return arg.substring(1)
    } else if (arg != char0) {
        return uMinus + arg
    }
    return arg
}

private fun condInsertDot(arg: String): String {
    if (arg.length > 1 && !arg.contains(charDot)) {
        return arg + charDot
    }
    return arg
}

private fun stripLeadingZero(arg: String): String {
    if (arg.isNotEmpty() && arg.startsWith(char0)) {
        return arg.substring(1)
    }
    return arg
}

@Suppress("DuplicatedCode")
class DesktopView : View() {
    private var calcTextField: TextField by singleAssign()
    private val model = DesktopModel()

    override val root = vbox {
        addClass(Styles.wrapper)

        hbox {
            calcTextField = textfield(char0) {
                requestFocus()

//                textProperty().addListener { evt, _, _ ->
//                    println(evt.value)
//                }

                alignmentProperty().set(Pos.CENTER_RIGHT)

                style {
                    backgroundColor += Styles.operationBgDarkColor
                    textFill = Styles.fontColor
                }
            }
        }
        hbox {
            button(labelClear) {
                style {
                    backgroundColor += Styles.operationBgDarkColor
                }
                action {
                    resetCalculator()
                }
            }
            button(labelPlusMinus) {
                style {
                    backgroundColor += Styles.operationBgDarkColor
                }
                action {
                    calcTextField.text = flipSign(calcTextField.text)
                }
            }
            button(opPct) {
                style {
                    fontSize = 28.px
                    backgroundColor += Styles.operationBgDarkColor
                }
                action {
                    calcTextField.text = model.calc(opPct, calcTextField.text)
                }
            }
            button(labelDiv) {
                style {
                    backgroundColor += Styles.operationBgColor
                }
                action {
                    calcTextField.text = model.calc(opDiv, calcTextField.text)
                }
            }
        }
        hbox {
            button(char7) {
                action {
                    calcTextField.text = model.accumulate(char7, calcTextField.text)
                }
            }
            button(char8) {

                action {
                    calcTextField.text = model.accumulate(char8, calcTextField.text)
                }
            }
            button(char9) {
                action {
                    calcTextField.text = model.accumulate(char9, calcTextField.text)
                }
            }
            button(labelMul) {
                style {
                    backgroundColor += Styles.operationBgColor
                }
                action {
                    calcTextField.text = model.calc(opMul, calcTextField.text)
                }
            }
        }
        hbox {
            button(char4) {
                action {
                    calcTextField.text = model.accumulate(char4, calcTextField.text)
                }
            }
            button(char5) {
                action {
                    calcTextField.text = model.accumulate(char5, calcTextField.text)
                }
            }
            button(char6) {
                action {
                    calcTextField.text = model.accumulate(char6, calcTextField.text)
                }
            }
            button(labelSub) {
                style {
                    backgroundColor += Styles.operationBgColor
                }
                action {
                    calcTextField.text = model.calc(opSub, calcTextField.text)
                }
            }
        }
        hbox {
            button(char1) {
                action {
                    calcTextField.text = model.accumulate(char1, calcTextField.text)
                }
            }
            button(char2) {
                action {
                    calcTextField.text = model.accumulate(char2, calcTextField.text)
                }
            }
            button(char3) {
                action {
                    calcTextField.text = model.accumulate(char3, calcTextField.text)
                }
            }
            button(labelAdd) {
                style {
                    backgroundColor += Styles.operationBgColor
                }
                action {
                    calcTextField.text = model.calc(opAdd, calcTextField.text)
                }
            }
        }
        hbox {
            button(char0) {
                style {
                    minWidth = 168.px
                }
                action {
                    calcTextField.text += char0
                }
            }
            button(charDot) {
                action {
                    calcTextField.text = condInsertDot(calcTextField.text)
                }
            }
            button(opEqual) {
                style {
                    backgroundColor += Styles.operationBgColor
                }
                action {
                    calcTextField.text = model.calc(opEqual, calcTextField.text)
                }
            }
        }

    }

    init {
        this.title = "Calculator"

        primaryStage.icons.add(resources.image("/Calculator-icon.png"))

        this.resetCalculator()
    }

    private fun resetCalculator() {
        calcTextField.text = "0"
        model.reset()
    }

}

class DesktopModel {

    private var left: String = char0
    private var laggingOp = ""
    private val validOps = setOf<String>(opAdd, opDiv, opMul, opPct, opSub)

    fun reset() {
        left = char0
        laggingOp = ""
    }

    fun calc(operator: String, right: String): String {
        println("before: " + left)

        var work = left.toDouble()


        // This could be a map impl instead
        if (laggingOp == opAdd) {
            work += right.toDouble()
        }
        if (laggingOp == opSub) {
            work -= right.toDouble()
        }
        if (laggingOp == opMul) {
            work *= right.toDouble()
        }
        if (laggingOp == opMul) {
            work /= right.toDouble()
        }
        if (laggingOp == opPct) {
            work *= right.toDouble()*0.01
        }

        if (operator == opEqual) {
            work = right.toDouble()
        }

        if (validOps.contains(operator)){
            laggingOp = operator
        }

        left = work.toString()
        println("after: " + left)
        return left
    }

    fun accumulate(aChar: String, str: String): String {
        if (laggingOp != "") {
            laggingOp = ""
            return aChar
        }
        return stripLeadingZero(str) + aChar
    }
}

class DesktopApp : App(DesktopView::class, Styles::class) {

    companion object;

    init {
        reloadStylesheetsOnFocus()
    }

}

fun main() {
    launch<DesktopApp>()
}
