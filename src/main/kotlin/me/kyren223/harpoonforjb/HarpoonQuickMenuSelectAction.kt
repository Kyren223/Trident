package me.kyren223.harpoonforjb

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.maddyhome.idea.vim.api.injector
import com.maddyhome.idea.vim.command.MappingMode
import com.maddyhome.idea.vim.key.MappingOwner.Plugin.Companion.get

class HarpoonQuickMenuSelectAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        HarpoonState.quickMenuSelect()
    }

}
