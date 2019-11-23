package com.rogersalumni.calculator.desktop

import javafx.geometry.Pos
import javafx.scene.control.TextField
import tornadofx.*

@Suppress("DuplicatedCode")
class DesktopView : View() {
    companion object {
        private fun flipSign(arg: String): String {
            if (arg.startsWith(opUMinus)) {
                return arg.substring(1)
            } else if (arg != char0) {
                return opUMinus + arg
            }
            return arg
        }

        private fun condInsertDot(arg: String): String {
            if (arg.length > 1 && !arg.contains(charDot)) {
                return arg + charDot
            }
            return arg
        }
    }

    private var calcTextField: TextField by singleAssign()
    private val controller = DesktopController()

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
                    calcTextField.text = resetCalculator()
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
                    calcTextField.text = controller.calc(opPct)
                }
            }
            button(labelDiv) {
                style {
                    backgroundColor += Styles.operationBgColor
                }
                action {
                    calcTextField.text = controller.calc(opDiv)
                }
            }
        }
        hbox {
            button(char7) {
                action {
                    calcTextField.text = controller.accumulate(char7)
                }
            }
            button(char8) {

                action {
                    calcTextField.text = controller.accumulate(char8)
                }
            }
            button(char9) {
                action {
                    calcTextField.text = controller.accumulate(char9)
                }
            }
            button(labelMul) {
                style {
                    backgroundColor += Styles.operationBgColor
                }
                action {
                    calcTextField.text = controller.calc(opMul)
                }
            }
        }
        hbox {
            button(char4) {
                action {
                    calcTextField.text = controller.accumulate(char4)
                }
            }
            button(char5) {
                action {
                    calcTextField.text = controller.accumulate(char5)
                }
            }
            button(char6) {
                action {
                    calcTextField.text = controller.accumulate(char6)
                }
            }
            button(labelSub) {
                style {
                    backgroundColor += Styles.operationBgColor
                }
                action {
                    calcTextField.text = controller.calc(opSub)
                }
            }
        }
        hbox {
            button(char1) {
                action {
                    calcTextField.text = controller.accumulate(char1)
                }
            }
            button(char2) {
                action {
                    calcTextField.text = controller.accumulate(char2)
                }
            }
            button(char3) {
                action {
                    calcTextField.text = controller.accumulate(char3)
                }
            }
            button(labelAdd) {
                style {
                    backgroundColor += Styles.operationBgColor
                }
                action {
                    calcTextField.text = controller.calc(opAdd)
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
            button(opEqu) {
                style {
                    backgroundColor += Styles.operationBgColor
                }
                action {
                    calcTextField.text = controller.calc(opEqu)
                }
            }
        }

    }

    init {
        this.title = "Calculator"
        primaryStage.icons.add(resources.image("/Calculator-icon.png"))
        this.resetCalculator()
    }

    private fun resetCalculator(): String {
        return controller.reset();
    }

}

/**
 * This controller holds no state. It delegates to CalculatorEngine that does hold state and calculator expression
 * logic.
 */
class DesktopController : Controller() {
    private val engine = CalculatorEngine()

    fun reset(): String {
        return engine.reset()
    }

    fun calc(operator: String): String {
        return engine.calc(operator)
    }

    fun accumulate(aChar: String): String {
        return engine.accumulate(aChar)
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
