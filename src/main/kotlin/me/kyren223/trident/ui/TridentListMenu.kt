package me.kyren223.trident.ui

import com.intellij.openapi.editor.EditorSettings
import com.intellij.openapi.editor.LogicalPosition
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.EditorTextField
import com.maddyhome.idea.vim.newapi.vim
import com.maddyhome.idea.vim.state.mode.Mode
import me.kyren223.trident.data.Settings
import me.kyren223.trident.utils.TridentList
import me.kyren223.trident.utils.injectEnterToSelectMapping
import me.kyren223.trident.utils.setEditorMode
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import javax.swing.JComponent

class TridentListMenu(private val content: String) : DialogWrapper(true) {

    private lateinit var editor: EditorTextField

    init {
        this.title = "Trident List"
        val settings = Settings.state
        setSize(settings.width, settings.height)
        super.init()
    }

    override fun createCenterPanel(): JComponent {
        editor = EditorTextField(this.content)
        editor.setOneLineMode(false)
        editor.addSettingsProvider {
            it.setFontSize(Settings.state.fontSize)
            it.isInsertMode = false
            val lineNumber = if (Settings.state.rememberLine) line else 0
            it.caretModel.moveToLogicalPosition(LogicalPosition(lineNumber, 0))
            it.settings.isLineNumbersShown = true
            it.settings.lineNumerationType = EditorSettings.LineNumerationType.ABSOLUTE
        }

        editor.addFocusListener(object : FocusListener {
            override fun focusGained(e: FocusEvent?) {
                val editor = editor.editor!!.vim
                setEditorMode(editor, Mode.NORMAL())
            }

            override fun focusLost(e: FocusEvent?) { /* Do nothing */
            }
        })

        return editor
    }


    override fun getPreferredFocusedComponent(): JComponent {
        return this.editor
    }

    fun select() {
        doOKAction()
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
        instance = null
        files = editor.text.split("\n")
        line = editor.editor?.caretModel?.logicalPosition?.line ?: 0
    }

    override fun createSouthPanel(): JComponent? {
        return null
    }

    companion object {
        var instance: TridentListMenu? = null
        var files: List<String>? = null
        var line = 0
        fun open(project: Project) {
            val content = TridentList.get(project).joinToString("\n")
            instance = TridentListMenu(content)
            injectEnterToSelectMapping()
            val selected = instance!!.showAndGet()

            files?.let { TridentList.set(project, it) }
            files = null

            if (!selected) return
            val file = TridentList.select(project, line) ?: return
            FileEditorManager.getInstance(project).openFile(file, true)
        }
    }
}
