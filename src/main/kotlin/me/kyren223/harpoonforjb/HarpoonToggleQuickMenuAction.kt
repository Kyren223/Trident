package me.kyren223.harpoonforjb

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.maddyhome.idea.vim.api.injector
import com.maddyhome.idea.vim.command.MappingMode
import com.maddyhome.idea.vim.key.MappingOwner

class HarpoonToggleQuickMenuAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        if (!remapped) remap()
        if (e.project != null) {
            HarpoonState.toggleQuickMenu(e.project!!)
        }
    }

    private fun remap() {
        if (!AppSettingsState.instance.harpoonEnterToSelect) return
        val keys = injector.parser.parseKeys(":action HarpoonQuickMenuSelect<cr>")
        val keyGroup = injector.keyGroup
        keyGroup.putKeyMapping(MappingMode.NVO,
                injector.parser.parseKeys("<cr>"),
                MappingOwner.Plugin.get("HarpoonForJB"), keys, false
        )
        remapped = true
    }

    companion object {
        var remapped = false
    }
}
