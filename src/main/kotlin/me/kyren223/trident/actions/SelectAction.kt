package me.kyren223.trident.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.fileEditor.FileEditorManager
import me.kyren223.trident.utils.TridentList

class SelectAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        var index = when (val actionId = e.actionManager.getId(this)) {
            NEXT -> {
                val file = e.getData(PlatformDataKeys.VIRTUAL_FILE) ?: return
                val current = TridentList.getIndexOfFile(project, file) ?: return
                current + 1
            }

            PREV -> {
                val file = e.getData(PlatformDataKeys.VIRTUAL_FILE) ?: return
                val current = TridentList.getIndexOfFile(project, file) ?: return
                current - 1
            }

            else -> {
                getIndex(actionId!!)
            }
        }

        val file = TridentList.select(project, index) ?: return
        FileEditorManager.getInstance(project).openFile(file, true)
    }

    private fun getIndex(actionId: String): Int {
        return actionId.substring(SELECT.length).toInt() - 1
    }

    companion object {
        const val NEXT = "TridentSelectNext"
        const val PREV = "TridentSelectPrev"
        const val SELECT = "TridentSelect"
    }
}

