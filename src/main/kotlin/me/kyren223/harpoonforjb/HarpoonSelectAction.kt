package me.kyren223.harpoonforjb

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.fileEditor.FileEditorManager

class HarpoonSelectAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val actionId = e.actionManager.getId(this)
        var index = when (actionId) {
            NEXT -> {
                val current = HarpoonState.getIndexOfFile(project, e.getData(PlatformDataKeys.VIRTUAL_FILE))
                if (current == -1) return
                current + 1
            }
            PREV -> {
                val current = HarpoonState.getIndexOfFile(project, e.getData(PlatformDataKeys.VIRTUAL_FILE))
                if (current == -1) return
                current - 1
            }
            else -> {
                getIndex(actionId)
            }
        }

        val count = HarpoonState.getFileCount(project)
        index = ((index % count) + count) % count
        val file = HarpoonState.selectFile(project, index) ?: return
        FileEditorManager.getInstance(project).openFile(file, true)
    }

    private fun getIndex(actionId: String): Int {
        return actionId.substring(SELECT.length).toInt() - 1
    }

    companion object {
        const val NEXT = "HarpoonSelectNext"
        const val PREV = "HarpoonSelectPrev"
        const val SELECT = "HarpoonSelect"
    }
}

