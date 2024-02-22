package me.kyren223.trident

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile

object TridentState {
    private var projectFiles: MutableMap<String, MutableList<VirtualFile>> = mutableMapOf()
    private const val TRIDENT_LIST_KEY = "TridentList"
    private var quickMenu: TridentQuickMenu? = null
    var quickMenuSelectedIndex: Int? = null
    var quickMenuContent: String? = ""

    fun appendFile(project: Project, file: VirtualFile) {
        if (!file.isValid) return
        loadProject(project)
        val files = projectFiles[project.name]
        files!!.add(file)
        saveProject(project)
    }

    fun selectFile(project: Project, index: Int): VirtualFile? {
        if (index < 0) return null
        loadProject(project)
        val files = projectFiles[project.name]
        if (files!!.size <= index) return null
        return files[index]
    }

    private fun loadProject(project: Project) {
        if (project.name in projectFiles) return
        val properties = PropertiesComponent.getInstance(project)
        val list = properties.getList(TRIDENT_LIST_KEY)
        if (list == null) {
            projectFiles[project.name] = mutableListOf()
            return
        }
        val fs = LocalFileSystem.getInstance()
        val files = list.stream()
                .map { fs.findFileByPath(it) }
                .toList()
                .filterNotNull()
                .filter { it.isValid }
                .toMutableList()
        projectFiles[project.name] = files
    }

    private fun saveProject(project: Project) {
        if (project.name !in projectFiles) return
        val files = projectFiles[project.name]
        val properties = PropertiesComponent.getInstance(project)
        val stringList = files!!.stream()
                .filter { it.isValid }
                .map { it.path }
                .toList()
        properties.setList(TRIDENT_LIST_KEY, stringList)
    }

    fun getIndexOfFile(project: Project, data: VirtualFile?): Int {
        loadProject(project)
        val files = projectFiles[project.name]
        if (files == null || data == null) return -1
        return files.indexOfFirst { it.path == data.path }
    }

    fun getFileCount(project: Project): Int {
        loadProject(project)
        return projectFiles[project.name]!!.size
    }

    fun toggleQuickMenu(project: Project) {
        if (quickMenu != null && quickMenu!!.isShowing) {
            quickMenu!!.doCancelAction()
            return
        }

        loadProject(project)
        val content = getProjectContent(project)
        quickMenu = TridentQuickMenu(content)
        val result = quickMenu!!.showAndGet()

        if (quickMenuContent != null && content != quickMenuContent) {
            retrieveProjectFiles(project, quickMenuContent!!)
            saveProject(project)
        }

        if (!result) return
        val index = quickMenuSelectedIndex ?: return
        quickMenuSelectedIndex = null
        val file = selectFile(project, index) ?: return
        FileEditorManager.getInstance(project).openFile(file, true)
    }

    fun quickMenuSelect() {
        if (quickMenu == null || !quickMenu!!.isShowing) return
        quickMenu!!.select()
    }

    private fun getProjectContent(project: Project): String {
        val content = projectFiles[project.name]!!
                .joinToString("\n") { it.path }
                .replace(project.basePath!!, "...")
                .trim()
        return content
    }

    private fun retrieveProjectFiles(project: Project, content: String) {
        loadProject(project)
        val files = content
                .split("\n")
                .asSequence()
                .map { it.trim() }
                .map { it.replace("...", project.basePath!!) }
                .filter { it.isNotBlank() }
                .map { LocalFileSystem.getInstance().findFileByPath(it) }
                .filterNotNull()
                .filter { it.isValid }
                .toMutableList()
        projectFiles[project.name] = files
    }
}