package me.kyren223.trident.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import me.kyren223.trident.ui.TridentMappingsMenu

class TridentMappingsAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        TridentMappingsMenu.open(project)
    }
}
