package me.kyren223.harpoonforjb

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class HarpoonSetAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val actionId = e.actionManager.getId(this)
        if (!actionId.startsWith("HarpoonSet")) {
            return
        }
        val index = getIndex(actionId)
        println("Set $index")
    }

    private fun getIndex(actionId: String): Int {
        return actionId.substring("HarpoonSet".length).toInt()
    }
}