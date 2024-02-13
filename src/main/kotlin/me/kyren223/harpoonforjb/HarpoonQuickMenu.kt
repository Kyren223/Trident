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
        println("Content: \n$content")
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

            override fun focusLost(e: FocusEvent?) { /* Do nothing */}
        })

        return editorTextField
    }

    private fun simulateKeyPress(key: String, editor: VimEditor, context: ExecutionContext.Editor) {
        KeyHandler.getInstance().handleKey(editor, injector.parser.parseKeys(key)[0], context)
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return this.editorTextField
    }
}
