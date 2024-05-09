package me.kyren223.trident.utils

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import me.kyren223.trident.data.Settings

object TridentList {
    private const val PROPERTIES_KEY = "TridentList"

    private fun isValidFile(project: Project, path: String): Boolean {
        // Requirements:
        // - File must exist on disk after expansion
        val expandedPath = TridentMappings.expand(project, path)
        return expandedPath.isNotBlank() && LocalFileSystem.getInstance().findFileByPath(expandedPath) != null
    }

    private fun getShortPath(path: String): String? {
        // For path `/path/to/file.ext` the result should be `$file`
        // Unless for dot files, in which case it should be `$ext`
        // Examples:
        // - `/path/to/TridentUtils.kt` -> `$TridentUtils`
        // - `/path/to/someBinary` -> `$someBinary`
        // - `/path/to/.ideavimrc` -> `$ideavimrc`
        return path.split("/")
            .lastOrNull()
            ?.let {
                it.split(".")
                    .let { parts ->
                        when (parts.size) {
                            0 -> it
                            1, 2 -> parts.firstOrNull()
                            else -> return null
                        }
                    }
            }
            ?.let { "\$${it}" }
    }

    fun append(project: Project, file: VirtualFile) {
        val files = get(project).toMutableList()

        val automaticMapping = Settings.state.automaticMapping
        val path = if (automaticMapping) {
            val shortPath = getShortPath(file.path) ?: file.path
            if (TridentMappings.exists(project, shortPath)) file.path else {
                TridentMappings.append(project, shortPath, file.path)
                shortPath
            }
        } else file.path

        files.add(path)
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
        return get(project)
            .map { TridentMappings.expand(project, it) }
            .mapNotNull { LocalFileSystem.getInstance().findFileByPath(it) }
    }

    fun getIndexOfFile(project: Project, file: VirtualFile): Int? {
        val files = getFiles(project)
        return files.indexOfFirst { it.path == file.path }.takeUnless { it == -1 }
    }

    fun select(project: Project, index: Int): VirtualFile? {
        val cycle = Settings.state.indexCycling
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
        // - key must only contain alphanumeric characters and start with $
        // - value must not contain =
        return key.isNotBlank() && value.isNotBlank() &&
                key.matches(Regex("""^\$[a-zA-Z0-9]+$""")) &&
                value.matches(Regex("""^[^=]+$"""))
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
        val recursive = Settings.state.recursiveMapping
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