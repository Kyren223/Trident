package me.kyren223.trident

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile

object TridentState {
    private var projectFiles: MutableMap<String, MutableList<VirtualFile>> = mutableMapOf()
    private var pathMappings: MutableMap<String, MutableMap<String, String>> = mutableMapOf()
    private const val TRIDENT_LIST_KEY = "TridentList"
    private const val PATH_MAPPINGS_LIST_KEY = "PathMappings"
    private var quickMenu: QuickMenu? = null
    var quickMenuSelectedIndex: Int? = null
    var quickMenuContent: String = ""
    private var pathMappingsMenu: PathMappingsMenu? = null
    var pathMappingsContent: String = ""

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

        val pathMappingsList = properties.getList(PATH_MAPPINGS_LIST_KEY)
        var map = mutableMapOf<String, String>()
        if (pathMappingsList == null) {
            map["..."] = project.basePath!!
        } else {
            for (entry in pathMappingsList) {
                val split = entry.split("=")
                if (split.size != 2) continue
                map[split[0]] = split[1]
            }
        }
        this.pathMappings[project.name] = map

        val list = properties.getList(TRIDENT_LIST_KEY)
        if (list == null) {
            projectFiles[project.name] = mutableListOf()
        } else {
            val fs = LocalFileSystem.getInstance()
            val files = list.stream()
                .map { fs.findFileByPath(it) }
                .toList()
                .filterNotNull()
                .filter { it.isValid }
                .toMutableList()
            projectFiles[project.name] = files
        }
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

        val pathMappingsString = pathMappings[project.name]!!.entries
            .joinToString("\n") { "${it.key}=${it.value}" }
            .split("\n")
        properties.setList(PATH_MAPPINGS_LIST_KEY, pathMappingsString)
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
        quickMenu = QuickMenu(content)
        val result = quickMenu!!.showAndGet()

        if (content != quickMenuContent) {
            retrieveProjectFiles(project, quickMenuContent)
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
        loadProject(project)
        val map = pathMappings[project.name] ?: throw IllegalStateException("Path mappings not found")
        println("map: $map")
        var content = projectFiles[project.name]!!
            .joinToString("\n") { it.path }
            .trim()
        println("content: $content")
        for ((key, value) in map) {
            println("key: $key | value: $value")
            content = content.replace(value, key)
        }
        println("content: $content")
        return content
    }

    private fun retrieveProjectFiles(project: Project, content: String) {
        loadProject(project)
        val files = content
            .split("\n")
            .asSequence()
            .map { it.trim() }
            .map { line ->
                pathMappings[project.name]!!.entries.fold(line) { acc, (key, value) ->
                    acc.replace(key, value)
                }
            }
//            .map {
//                pathMappings[project.name]!!.entries.forEach { (key, value) -> it.replace(key, value) }
//                it
//            }
            .filter { it.isNotBlank() }
            .map { LocalFileSystem.getInstance().findFileByPath(it) }
            .filterNotNull()
            .filter { it.isValid }
            .toMutableList()
        projectFiles[project.name] = files
    }

    fun togglePathMappingsMenu(project: Project) {
        if (pathMappingsMenu != null && pathMappingsMenu!!.isShowing) {
            pathMappingsMenu!!.doCancelAction()
            return
        }

        loadProject(project)
        val content = pathMappings[project.name]!!.entries
            .joinToString("\n") { "${it.key}=${it.value}" }
        pathMappingsMenu = PathMappingsMenu(content)
        pathMappingsMenu!!.showAndGet()

        if (content != pathMappingsContent) {
            pathMappingsContent.split("\n").forEach {
                val split = it.split("=")
                if (split.size != 2) return@forEach
                this.pathMappings[project.name]!![split[0]] = split[1]
            }
            saveProject(project)
        }
    }
}