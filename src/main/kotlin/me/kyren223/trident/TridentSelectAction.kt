package me.kyren223.trident

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.fileEditor.FileEditorManager

class TridentSelectAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        var index = when (val actionId = e.actionManager.getId(this)) {
            NEXT -> {
                val current = TridentState.getIndexOfFile(project, e.getData(PlatformDataKeys.VIRTUAL_FILE))
                if (current == -1) return
                current + 1
            }
            PREV -> {
                val current = TridentState.getIndexOfFile(project, e.getData(PlatformDataKeys.VIRTUAL_FILE))
                if (current == -1) return
                current - 1
            }
            else -> {
                getIndex(actionId)
            }
        }

        val count = TridentState.getFileCount(project)
        index = ((index % count) + count) % count
        val file = TridentState.selectFile(project, index) ?: return
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

