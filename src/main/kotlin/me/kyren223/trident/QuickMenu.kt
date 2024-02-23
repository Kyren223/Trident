@file:Suppress("SameParameterValue")

package me.kyren223.trident

import com.intellij.openapi.editor.LogicalPosition
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.EditorTextField
import com.maddyhome.idea.vim.KeyHandler
import com.maddyhome.idea.vim.api.ExecutionContext
import com.maddyhome.idea.vim.api.VimEditor
import com.maddyhome.idea.vim.api.injector
import com.maddyhome.idea.vim.newapi.vim
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import javax.swing.JComponent

class QuickMenu(private val content: String) : DialogWrapper(true) {
    private lateinit var editorTextField: EditorTextField

    init {
        val settings = SettingsState.instance
        this.title = "Trident Quick Menu"
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

    fun select() {
        if (this.editorTextField.editor == null) return
        TridentState.quickMenuSelectedIndex = this.editorTextField.editor!!.caretModel.logicalPosition.line
        doOKAction()
    }

    override fun doOKAction() {
        TridentState.quickMenuContent = editorTextField.text
        super.doOKAction()
    }

    override fun doCancelAction() {
        TridentState.quickMenuContent = editorTextField.text
        super.doCancelAction()
    }

    override fun createSouthPanel(): JComponent? {
        return null
    }
}
