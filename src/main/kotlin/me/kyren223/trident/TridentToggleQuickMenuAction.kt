package me.kyren223.trident

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.maddyhome.idea.vim.api.injector
import com.maddyhome.idea.vim.command.MappingMode
import com.maddyhome.idea.vim.key.MappingOwner

class TridentToggleQuickMenuAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        if (!remapped) remap()
        if (e.project != null) {
            TridentState.toggleQuickMenu(e.project!!)
        }
    }

    private fun remap() {
        if (!AppSettingsState.instance.enterToSelect) return
        val keys = injector.parser.parseKeys(":action TridentQuickMenuSelect<cr>")
        val keyGroup = injector.keyGroup
        keyGroup.putKeyMapping(MappingMode.NVO,
                injector.parser.parseKeys("<cr>"),
                MappingOwner.Plugin.get("Trident"), keys, false
        )
        remapped = true
    }

    companion object {
        var remapped = false
    }
}
