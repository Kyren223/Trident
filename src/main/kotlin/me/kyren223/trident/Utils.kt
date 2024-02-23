package me.kyren223.trident

import com.maddyhome.idea.vim.KeyHandler
import com.maddyhome.idea.vim.api.ExecutionContext
import com.maddyhome.idea.vim.api.VimEditor
import com.maddyhome.idea.vim.api.injector

fun simulateKeyPress(key: String, editor: VimEditor, context: ExecutionContext.Editor) {
    KeyHandler.getInstance().handleKey(editor, injector.parser.parseKeys(key)[0], context)
}


