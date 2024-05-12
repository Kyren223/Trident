package me.kyren223.trident.data

import com.intellij.openapi.ui.ComboBox
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

    private val width = JBIntSpinner(Settings.state.width, minValue, maxValue, step)
    private val height = JBIntSpinner(Settings.state.height, minValue, maxValue, step)
    private val fontSize = JBIntSpinner(Settings.state.fontSize, minValue, maxValue, step)

    private val enterToSelect = JBCheckBox(null, Settings.state.enterToSelect)
    private val rememberLine = JBCheckBox(null, Settings.state.rememberLine)
    private val automaticMapping = ComboBox(AutomaticMapping.entries.toTypedArray())
    private val automaticReplacing = ComboBox(AutomaticReplacing.entries.toTypedArray())
    private val recursiveMapping = JBCheckBox(null, Settings.state.recursiveMapping)
    private val indexCycling = JBCheckBox(null, Settings.state.indexCycling)

    init {
        automaticMapping.selectedItem = Settings.state.automaticMapping
        automaticReplacing.selectedItem = Settings.state.automaticReplacing
        
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
            .addComponent(desc("Disabled - adds the full file path to the Trident Mappings."))
            .addComponent(desc("Filename - Adds the filename (with extension)."))
            .addComponent(desc("Filename (no extension) - Adds the filename (without extension)."))
            .addVerticalGap(step)
            
            .addLabeledComponent("Automatic replacing (when possible)", automaticReplacing)
            .addComponent(desc("Disabled - Keeps the full file path."))
            .addComponent(desc("Exact - Replaces the full path if an exact match is found."))
            .addComponent(desc("Smart - Like exact but if no exact match found, " +
                    "it will try to make the path shorter."))
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

            .addLabeledComponent("Index cycling", indexCycling)
            .addComponent(desc("If enabled, indexes will be cyclic, " +
                    "For example if you have 3 files and hotkey to 4, it'll cycle to 1."))

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

    fun getAutomaticMapping(): AutomaticMapping {
        return automaticMapping.selectedItem as AutomaticMapping
    }

    fun setAutomaticMapping(automaticMapping: AutomaticMapping) {
        this.automaticMapping.selectedItem = automaticMapping
    }
    
    fun getAutomaticReplacing(): AutomaticReplacing {
        return automaticReplacing.selectedItem as AutomaticReplacing
    }
    
    fun setAutomaticReplacing(automaticReplacing: AutomaticReplacing) {
        this.automaticReplacing.selectedItem = automaticReplacing
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

    fun getIndexCycling(): Boolean {
        return indexCycling.isSelected
    }

    fun setIndexCycling(indexCycling: Boolean) {
        this.indexCycling.isSelected = indexCycling
    }
}

enum class AutomaticMapping {
    Disabled,
    Filename,
    FilenameNoExtension,
    ;
    
    override fun toString(): String {
        if (this == FilenameNoExtension) return "Filename (without extension)"
        return super.toString()
    }
}

enum class AutomaticReplacing {
    Disabled,
    Exact,
    Smart,
}
