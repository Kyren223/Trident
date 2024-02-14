package me.kyren223.harpoonforjb

import com.intellij.ui.JBIntSpinner
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder
import javax.swing.JPanel

class SettingsData {

    val panel: JPanel
    private val harpoonWidth: JBIntSpinner = JBIntSpinner(800, 1, 5000, 10)
    private val harpoonHeight: JBIntSpinner = JBIntSpinner(400, 1, 5000, 10)
    private val harpoonFontSize: JBIntSpinner = JBIntSpinner(20, 1, 100)
    private val harpoonEnter: JBCheckBox = JBCheckBox()

    init {
        panel = FormBuilder.createFormBuilder()
                .addLabeledComponent(JBLabel("Harpoon width"), harpoonWidth, 1, false)
                .addLabeledComponent(JBLabel("Harpoon height"), harpoonHeight, 1, false)
                .addLabeledComponent(JBLabel("Harpoon font size"), harpoonFontSize, 1, false)
                .addLabeledComponent(JBLabel("Press enter to select item in the QuickMenu"), harpoonEnter, 1, false)
                .addComponentFillVertically(JPanel(), 0)
                .panel
    }

    fun getHarpoonWidth(): Int {
        return harpoonWidth.number
    }

    fun getHarpoonHeight(): Int {
        return harpoonHeight.number
    }

    fun getHarpoonFontSize(): Int {
        return harpoonFontSize.number
    }

    fun getHarpoonEnterToSelect(): Boolean {
        return harpoonEnter.isSelected
    }

    fun setHarpoonWidth(width: Int) {
        harpoonWidth.number = width
    }

    fun setHarpoonHeight(height: Int) {
        harpoonHeight.number = height
    }

    fun setHarpoonFontSize(fontSize: Int) {
        harpoonFontSize.number = fontSize
    }

    fun setHarpoonEnterToSelect(enter: Boolean) {
        harpoonEnter.isSelected = enter
    }
}
