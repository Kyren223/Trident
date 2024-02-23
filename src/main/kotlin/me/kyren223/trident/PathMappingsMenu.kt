package me.kyren223.trident

import com.intellij.openapi.editor.LogicalPosition
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.EditorTextField
import com.maddyhome.idea.vim.api.injector
import com.maddyhome.idea.vim.newapi.vim
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import javax.swing.JComponent

class PathMappingsMenu(private val content: String) : DialogWrapper(true) {
    private lateinit var editorTextField: EditorTextField

    init {
        val settings = SettingsState.instance
        this.title = "Trident Path Mappings Menu"
        setSize(settings.width, settings.height)
        super.init()
    }

    override fun createCenterPanel(): JComponent {
        val settings = SettingsState.instance
        editorTextField = EditorTextField(this.content)
        editorTextField.setOneLineMode(false)
        editorTextField.addSettingsProvider {
            it.setFontSize(settings.fontSize)
            it.isInsertMode = true
            it.caretModel.moveToLogicalPosition(LogicalPosition(0, 0))
            it.settings.isLineNumbersShown = true
        }

        editorTextField.addFocusListener(object : FocusListener {
            override fun focusGained(e: FocusEvent?) {
                val editor = editorTextField.editor!!.vim
                val context = injector.executionContextManager.onEditor(editor, null)
                simulateKeyPress("<ESC>", editor, context)
            }

            override fun focusLost(e: FocusEvent?) { /* Do nothing */ }
        })

        return editorTextField
    }

    override fun getPreferredFocusedComponent(): JComponent {
        return this.editorTextField
    }

    override fun doOKAction() {
        TridentState.pathMappingsContent = editorTextField.text
        super.doOKAction()
    }

    override fun doCancelAction() {
        TridentState.pathMappingsContent = editorTextField.text
        super.doCancelAction()
    }

    override fun createSouthPanel(): JComponent? {
        return null
    }
}
