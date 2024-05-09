package me.kyren223.trident.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import me.kyren223.trident.ui.TridentListMenu

class TridentListAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        TridentListMenu.open(project)
    }

}
