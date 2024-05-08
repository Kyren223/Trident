package me.kyren223.trident.data

import com.intellij.ui.JBColor
import com.intellij.ui.JBIntSpinner
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.JBFont
import javax.swing.JPanel

class SettingsData {

    private val minValue = 1
    private val maxValue = 5000
    private val step = 10

    val panel: JPanel

    private val width = JBIntSpinner(SettingsState.instance.width, minValue, maxValue, step)
    private val height = JBIntSpinner(SettingsState.instance.height, minValue, maxValue, step)
    private val fontSize = JBIntSpinner(SettingsState.instance.fontSize, minValue, maxValue, step)

    private val enterToSelect = JBCheckBox(null, SettingsState.instance.enterToSelect)
    private val rememberLine = JBCheckBox(null, SettingsState.instance.rememberLine)
    private val automaticMapping = JBCheckBox(null, SettingsState.instance.automaticMapping)
    private val recursiveMapping = JBCheckBox(null, SettingsState.instance.recursiveMapping)

    init {
        panel = FormBuilder.createFormBuilder()
            .addComponent(JBLabel("Popup settings").withFont(JBFont.h4()))
            .addLabeledComponent("Popup width", width)
            .addLabeledComponent("Popup height", height)
            .addLabeledComponent("Popup font size", fontSize)
            .addSeparator()

            .addComponent(JBLabel("Trident settings").withFont(JBFont.h4()))
            .addLabeledComponent("Enter to select item", enterToSelect)
            .addComponent(desc("If enabled while the TridentList is open, " +
                    "pressing enter will select the item in the current line and open the file (if it exists)."))
            .addVerticalGap(step)

            .addLabeledComponent("Automatic mapping (when possible)", automaticMapping)
            .addComponent(desc("If enabled, when using the Append action, " +
                    "the full file path will be added to the Trident Mappings."))
            .addComponent(desc("Mapping Entry: $<file>=<path>/<file>.<extension>"))
            .addVerticalGap(step)

            .addLabeledComponent("Recursive mapping", recursiveMapping)
            .addComponent(desc("If enabled, when expanding a mapping for a path, " +
                    "each entry will be expanded recursively."))
            .addComponent(desc("This allows mapping entries to be nested (include other mappings)."))
            .addVerticalGap(step)

            .addLabeledComponent("Remember last line", rememberLine)
            .addComponent(desc("If enabled, the current line of the TridentList will be remembered " +
                    "and will be selected when the TridentList is opened."))
            .addComponent(desc("Note, this does not persist across restarts, it's run-time only."))

            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    private fun desc(text: String): JBLabel {
        val label = JBLabel(text).withFont(JBFont.small())
        label.foreground = JBColor.GRAY
        return label
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

    fun getAutomaticMapping(): Boolean {
        return automaticMapping.isSelected
    }

    fun setAutomaticMapping(automaticMapping: Boolean) {
        this.automaticMapping.isSelected = automaticMapping
    }

    fun getRememberLine(): Boolean {
        return rememberLine.isSelected
    }

    fun setRememberLine(rememberLine: Boolean) {
        this.rememberLine.isSelected = rememberLine
    }

    fun getRecursiveMapping(): Boolean {
        return recursiveMapping.isSelected
    }

    fun setRecursiveMapping(recursiveMapping: Boolean) {
        this.recursiveMapping.isSelected = recursiveMapping
    }
}
