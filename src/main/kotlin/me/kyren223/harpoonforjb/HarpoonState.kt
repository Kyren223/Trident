package me.kyren223.harpoonforjb

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile

object HarpoonState {
    private var projectFiles: MutableMap<String, MutableList<VirtualFile>> = mutableMapOf()
    private const val HARPOON_LIST_KEY = "HarpoonList"

    fun setFile(project: Project, index: Int, file: VirtualFile) {
        if (index < 0) return
        loadProject(project)
        val files = projectFiles[project.name]
        if (files!!.size <= index) return
        files[index] = file
        saveProject(project)
    }

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
        val list = properties.getList(HARPOON_LIST_KEY)
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
        properties.setList(HARPOON_LIST_KEY, stringList)
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
}