package me.kyren223.trident.ui

import com.intellij.openapi.editor.EditorSettings
import com.intellij.openapi.editor.LogicalPosition
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.EditorTextField
import com.maddyhome.idea.vim.newapi.vim
import com.maddyhome.idea.vim.state.mode.Mode
import me.kyren223.trident.data.SettingsState
import me.kyren223.trident.utils.TridentMappings
import me.kyren223.trident.utils.setEditorMode
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import javax.swing.JComponent

class TridentMappingsMenu(private val content: String) : DialogWrapper(true) {
    private lateinit var editor: EditorTextField

    init {
        val settings = SettingsState.instance.state
        this.title = "Trident Mappings"
        setSize(settings.width, settings.height)
        super.init()
    }

    override fun createCenterPanel(): JComponent {
        editor = EditorTextField(this.content)
        editor.setOneLineMode(false)
        editor.addSettingsProvider {
            it.setFontSize(SettingsState.instance.fontSize)
            it.isInsertMode = false
            it.caretModel.moveToLogicalPosition(LogicalPosition(0, 0))
            it.settings.isLineNumbersShown = true
            it.settings.lineNumerationType = EditorSettings.LineNumerationType.ABSOLUTE
        }

        editor.addFocusListener(object : FocusListener {
            override fun focusGained(e: FocusEvent?) {
                val editor = editor.editor!!.vim
                setEditorMode(editor, Mode.NORMAL())
            }

            override fun focusLost(e: FocusEvent?) { /* Do nothing */ }
        })

        return editor
    }

    override fun getPreferredFocusedComponent(): JComponent {
        return this.editor
    }

    override fun doOKAction() {
        save()
        super.doOKAction()
    }

    override fun doCancelAction() {
        save()
        super.doCancelAction()
    }

    private fun save() {
        map = editor.text.split("\n")
            .map { entry -> entry.split("=") }
            .filter { entry -> entry.size == 2 }
            .associate { it[0] to it[1] }
    }

    override fun createSouthPanel(): JComponent? {
        return null
    }

    companion object {
        var map: Map<String, String>? = null
        fun open(project: Project) {
            val content = TridentMappings.get(project).entries
                .joinToString("\n") { "${it.key}=${it.value}" }
            val menu = TridentMappingsMenu(content)
            menu.show()
            map?.let { TridentMappings.set(project, it) }
            map = null
        }
    }
}
