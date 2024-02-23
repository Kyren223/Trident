package me.kyren223.trident

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class QuickMenuSelectAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        TridentState.quickMenuSelect()
    }

}
