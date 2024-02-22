package me.kyren223.trident

import com.intellij.ui.JBIntSpinner
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder
import javax.swing.JPanel

class SettingsData {

    val panel: JPanel
    private val width: JBIntSpinner = JBIntSpinner(800, 1, 5000, 10)
    private val height: JBIntSpinner = JBIntSpinner(400, 1, 5000, 10)
    private val fontSize: JBIntSpinner = JBIntSpinner(20, 1, 100)
    private val enterToSelect: JBCheckBox = JBCheckBox()

    init {
        panel = FormBuilder.createFormBuilder()
                .addLabeledComponent(JBLabel("QuickMenu width"), width, 1, false)
                .addLabeledComponent(JBLabel("QuickMenu height"), height, 1, false)
                .addLabeledComponent(JBLabel("QuickMenu font size"), fontSize, 1, false)
                .addLabeledComponent(JBLabel("Enter to select item in the QuickMenu"), enterToSelect, 1, false)
                .addComponentFillVertically(JPanel(), 0)
                .panel
    }

    fun getWidth(): Int {
        return width.number
    }

    fun getHeight(): Int {
        return height.number
    }

    fun getFontSize(): Int {
        return fontSize.number
    }

    fun getEnterToSelect(): Boolean {
        return enterToSelect.isSelected
    }

    fun setWidth(width: Int) {
        this.width.number = width
    }

    fun setHeight(height: Int) {
        this.height.number = height
    }

    fun setFontSize(fontSize: Int) {
        this.fontSize.number = fontSize
    }

    fun setEnterToSelect(enter: Boolean) {
        this.enterToSelect.isSelected = enter
    }
}
