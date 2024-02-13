package me.kyren223.harpoonforjb

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class HarpoonSelectAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val actionId = e.actionManager.getId(this)
        if (actionId == NEXT) {
            println("Next")
        } else if (actionId.equals(PREV)) {
            println("Previous")
        } else {
            val index = getIndex(actionId)
            println("Goto $index")
        }
    }

    private fun getIndex(actionId: String): Int {
        return actionId.substring("HarpoonGoto".length).toInt()
    }

    companion object {
        const val NEXT = "HarpoonSelectNext"
        const val PREV = "HarpoonSelectPrev"
        const val SELECT = "HarpoonSelect"
    }
}

