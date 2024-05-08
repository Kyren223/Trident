package me.kyren223.trident.utils

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import me.kyren223.trident.data.SettingsState

object TridentList {
    private const val PROPERTIES_KEY = "TridentList"

    private fun isValidFile(project: Project, path: String): Boolean {
        // Requirements:
        // - File must exist on disk after expansion
        val expandedPath = TridentMappings.expand(project, path)
        return expandedPath.isNotBlank() && LocalFileSystem.getInstance().findFileByPath(expandedPath) != null
    }

    fun append(project: Project, file: VirtualFile) {
        // TODO add a setting to automatically add an expansion to mappings if it doesn't exist
        // Should be `$<file>` for `<path>/<file>.<extension>`
        // Unless if the file starts with a dot, in which case it should be `$<extension>`
        val files = get(project).toMutableList()
        files.add(file.path)
        set(project, files)
    }

    fun set(project: Project, files: List<String>) {
        val properties = PropertiesComponent.getInstance(project)
        val list = files.filter { isValidFile(project, it) }
        properties.setList(PROPERTIES_KEY, list)
    }

    fun get(project: Project): List<String> {
        val properties = PropertiesComponent.getInstance(project)
        return properties.getList(PROPERTIES_KEY) ?: emptyList()
    }

    private fun getFiles(project: Project): List<VirtualFile> {
        return get(project).mapNotNull { LocalFileSystem.getInstance().findFileByPath(it) }
    }

    fun getIndexOfFile(project: Project, file: VirtualFile): Int? {
        val files = getFiles(project)
        return files.indexOfFirst { it.path == file.path }.takeUnless { it == -1 }
    }

    fun select(project: Project, index: Int): VirtualFile? {
        // TODO make this a setting if it should "cycle" or not
        val count = getFiles(project).size
        val i = ((index % count) + count) % count
        val files = getFiles(project)
        return files.getOrNull(i)
    }
}

object TridentMappings {
    private const val PROPERTIES_KEY = "TridentMappings"

    private fun isValidEntry(key: String, value: String): Boolean {
        // TODO change requirements, key should be alphanumeric and must only have 1 $ as the first character
        // Requirements:
        // - Key and value must not be blank
        // - key must only contain alphanumeric characters and $
        // - value must not contain =
        return key.isNotBlank() && value.isNotBlank() &&
                key.matches(Regex("^[a-zA-Z0-9\$]+$")) &&
                value.matches(Regex("^[^=]+$"))
    }

    fun append(project: Project, key: String, value: String, overwrite: Boolean = false) {
        val map = get(project).toMutableMap()
        if (map.contains(key) && !overwrite) return
        map[key] = value
        set(project, map)
    }

    fun set(project: Project, mappings: Map<String, String>) {
        val properties = PropertiesComponent.getInstance(project)

        val list = mappings
            .filter { isValidEntry(it.key, it.value) }
            .map { "${it.key}=${it.value}" }
            .toMutableList()

        properties.setList(PROPERTIES_KEY, list)
    }

    fun get(project: Project): Map<String, String> {
        val properties = PropertiesComponent.getInstance(project)
        val list = properties.getList(PROPERTIES_KEY) ?: return emptyMap()
        return list.map { it.split("=") }.associate { it[0] to it[1] }
    }

    fun expand(project: Project, path: String): String {
        val recursive = SettingsState.instance.recursiveMapping
        val mappings = get(project).toList().sortedByDescending { it.first.length }
        var expanded = path

        mappings.forEach { (key, value) ->
            expanded = expanded.replace(key, if (recursive) expand(project, value) else value)
        }

        return expanded
    }
}