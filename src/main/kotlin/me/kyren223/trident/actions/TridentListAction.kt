package me.kyren223.trident.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.maddyhome.idea.vim.api.injector
import com.maddyhome.idea.vim.command.MappingMode
import com.maddyhome.idea.vim.key.MappingOwner
import me.kyren223.trident.data.SettingsState
import me.kyren223.trident.ui.TridentListMenu

class TridentListAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        if (!remapped) remap()
        val project = e.project ?: return
        TridentListMenu.open(project)
    }

    // TODO move the remap to somewhere else
    private fun remap() {
        if (!SettingsState.instance.enterToSelect) return
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
