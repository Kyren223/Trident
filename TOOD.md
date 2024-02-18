
* Add $ syntax for quick menu, dollar syntax means there can only be one file with that name in the project and it will always know how to find it that way it can show only `$Main.java` rather than entire path
* Add # syntax for quick menu, which will use some per-project configured path similar to `...` for project path, `#` will be replaced with the configured project-specific path
* Look into having an option to have no extention to files
* Fix "out of window" issue with quick menu enter press
* Add a setting to allow you to keep in memory the line you were on when you opened the quick menu between quick menu opens
* Add a Global Quick Menu (and maybe actions too?) for global files like .ideavimrc
* Consider adding a local (and global) "shortcut" list where you can assign a name (prefixed with $) to a path, ex: $Kotlin -> src/main/kotlin
* Add an option (or an action) to create a shortcut automatically when adding a file, so "path/to/file.java" -> "$file"
* Add a shortcut menu like the quick menu but for the list of shortcuts
