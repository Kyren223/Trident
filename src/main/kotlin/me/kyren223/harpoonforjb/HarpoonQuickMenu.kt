@file:Suppress("SameParameterValue")

package me.kyren223.harpoonforjb

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

class HarpoonQuickMenu(private val content: String) : DialogWrapper(true) {
    private lateinit var editorTextField: EditorTextField

    init {
        val settings = AppSettingsState.instance
        this.title = "Harpoon Quick Menu"
        setSize(settings.harpoonWidth, settings.harpoonHeight)
        super.init()
    }

    override fun createCenterPanel(): JComponent {
        val settings = AppSettingsState.instance
        editorTextField = EditorTextField(this.content)
        editorTextField.setOneLineMode(false)
        editorTextField.addSettingsProvider {
            it.setFontSize(settings.harpoonFontSize)
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

    private fun simulateKeyPress(key: String, editor: VimEditor, context: ExecutionContext.Editor) {
        KeyHandler.getInstance().handleKey(editor, injector.parser.parseKeys(key)[0], context)
    }

    override fun getPreferredFocusedComponent(): JComponent {
        return this.editorTextField
    }

    fun select() {
        if (this.editorTextField.editor == null) return
        HarpoonState.quickMenuSelectedIndex = this.editorTextField.editor!!.caretModel.logicalPosition.line
        doOKAction()
    }

    override fun doOKAction() {
        HarpoonState.quickMenuContent = editorTextField.text
        super.doOKAction()
    }

    override fun doCancelAction() {
        HarpoonState.quickMenuContent = editorTextField.text
        super.doCancelAction()
    }

    override fun createSouthPanel(): JComponent? {
        return null
    }
}
