package me.kyren223.trident.utils

import com.maddyhome.idea.vim.api.VimEditor
import com.maddyhome.idea.vim.api.injector
import com.maddyhome.idea.vim.command.OperatorArguments
import com.maddyhome.idea.vim.state.mode.Mode

fun setEditorMode(editor: VimEditor, mode: Mode) {
    val context = injector.executionContextManager.onEditor(editor, null)
    val operatorArguments = OperatorArguments(false, 0, mode)
    editor.exitInsertMode(context, operatorArguments)
    editor.vimChangeActionSwitchMode = Mode.NORMAL()
}



