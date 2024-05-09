package me.kyren223.trident.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import me.kyren223.trident.utils.TridentList

class TridentAppendAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project: Project = e.project ?: return
        val file: VirtualFile = e.getData(PlatformDataKeys.VIRTUAL_FILE) ?: return
        TridentList.append(project, file)
    }
}
