package me.kyren223.trident.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import me.kyren223.trident.ui.TridentListMenu
import me.kyren223.trident.utils.TridentList

class TridentListSelectAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        TridentList.select(project, TridentListMenu.line)
    }

}
