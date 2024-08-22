package me.kyren223.trident.utils

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import me.kyren223.trident.data.AutomaticMapping
import me.kyren223.trident.data.AutomaticReplacing
import me.kyren223.trident.data.SettingsState

object TridentList {
    private const val PROPERTIES_KEY = "TridentList"
    
    private fun isValidFile(project: Project, path: String): Boolean {
        // Requirements:
        // - File must exist on disk after expansion
        val expandedPath = TridentMappings.expand(project, path)
        return expandedPath.isNotBlank() && LocalFileSystem.getInstance().findFileByPath(expandedPath) != null
    }
    
    private fun getModifiedPath(project: Project, path: String): String {
        val automaticMapping = SettingsState.instance.automaticMapping
        val automaticReplacing = SettingsState.instance.automaticReplacing
        val mappings = TridentMappings.get(project)
        
        val hasReplacement = automaticReplacing != AutomaticReplacing.Disabled
                && mappings.containsValue(path)
        
        if (hasReplacement) {
            return mappings.filterValues { it == path }.keys.firstOrNull()!!
        }
        
        if (automaticMapping == AutomaticMapping.Disabled) {
            return if (automaticReplacing == AutomaticReplacing.Smart) {
                return smartReplacement(path, mappings)
            } else path
        }
        
        return path.split("/")
            .lastOrNull()
            ?.let {
                if (automaticMapping == AutomaticMapping.Filename) {
                    return@let it
                }
                val dotIndex = it.lastIndexOf(".")
                val filename = if (dotIndex == -1) it else it.substring(0, dotIndex).ifBlank { null }
                val extension = if (dotIndex == -1) null else it.substring(dotIndex + 1)
                return@let (filename ?: extension)!!
            }?.let {
                val key = "\$$it"
                if (TridentMappings.exists(project, key)) {
                    return@let null
                }
                TridentMappings.append(project, key, path)
                return@let key
            } ?: (if (automaticReplacing == AutomaticReplacing.Smart) smartReplacement(path, mappings) else path)
    }
    
    private fun smartReplacement(path: String, mappings: Map<String, String>): String {
        // This is technically redundant,
        // The smart path is sorted based on length, so the longest path will be the first
        // And the longest path will be the first path that exactly matches
        val exactPath = mappings.filter { it.value == path }.keys.firstOrNull()
        exactPath?.let { return it }
        
        var smartPath = path
        for ((key, value) in mappings
            .toList()
            .sortedByDescending { it.second.length }
            .associate { it.first to it.second }) {
            smartPath = smartPath.replace(value, key)
        }
        return smartPath
    }
    
    fun append(project: Project, file: VirtualFile) {
        println("TridentList.append - file: ${file.path}")
        val files = get(project).toMutableList()
        val path = getModifiedPath(project, file.path)
        println("TridentList.append - path: $path")
        files.add(path)
        println("TridentList.append - add: " + files.joinToString(", "))
        set(project, files)
        println("TridentList.append - set")
        
        println("TridentList.append - TridentList: " + get(project).joinToString(", "))
        println("TridentList.append - TridentMappings: " + TridentMappings.get(project)
            .map { "${it.key}=${it.value}" }
            .joinToString(", "))
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
        return get(project)
            .map { TridentMappings.expand(project, it) }
            .mapNotNull { LocalFileSystem.getInstance().findFileByPath(it) }
    }
    
    fun getIndexOfFile(project: Project, file: VirtualFile): Int? {
        val files = getFiles(project)
        return files.indexOfFirst { it.path == file.path }.takeUnless { it == -1 }
    }
    
    fun select(project: Project, index: Int): VirtualFile? {
        val cycle = SettingsState.instance.indexCycling
        val count = getFiles(project).size
        val i = if (cycle) ((index % count) + count) % count else index
        val files = getFiles(project)
        return files.getOrNull(i)
    }
}

object TridentMappings {
    private const val PROPERTIES_KEY = "TridentMappings"
    
    private fun isValidEntry(key: String, value: String): Boolean {
        // Requirements:
        // - Key and value must not be blank
        // - key must start with $
        // - key must only contain alphanumeric characters, underscores, hyphens, and dots
        // - value must not contain =
        return key.isNotBlank() && value.isNotBlank() &&
                key.matches(Regex("""^\$[a-zA-Z0-9_.\-]+$""")) &&
                value.matches(Regex("""^[^=]+$"""))
    }
    
    fun append(project: Project, key: String, value: String, overwrite: Boolean = false) {
        println("TridentMappings.append - key: $key, value: $value")
        val map = get(project).toMutableMap()
        if (map.containsKey(key) && !overwrite) return
        println("TridentMappings.append - map: $map")
        map[key] = value
        println("TridentMappings.append - map after: $map")
        set(project, map)
        println("TridentMappings.append - set: ${get(project).map { "${it.key}=${it.value}" }.joinToString(", ")}")
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
        val map = list
            .map { it.split("=") }
            .associate { it[0] to it[1] }
            .toMutableMap()
        project.guessProjectDir()?.let {
            map["..."] = it.path
            map["\$project"] = it.path
        }
        return map
    }
    
    fun expand(project: Project, path: String): String {
        val recursive = SettingsState.instance.recursiveMapping
        val mappings = get(project).toList().sortedByDescending { it.first.length }
        var expandedPath = path
        while (true) {
            var expanded = false
            mappings.forEach { (key, value) ->
                val expansion = expandedPath.replace(key, value)
                if (expansion != expandedPath) {
                    expandedPath = expansion
                    expanded = true
                }
            }
            if (!recursive || !expanded) break
        }
        
        return expandedPath
    }
    
    fun exists(project: Project, key: String): Boolean {
        return get(project).containsKey(key)
    }
}