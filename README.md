<div align="center">

# Harpoon for JetBrains
##### Getting you where you want with the fewest keystrokes.
</div>

## Table of Contents
* [Features](#features)
* [Installation](#installation)
* [Getting Started](#getting-started)
* [Settings](#settings)
* [Contribution](#contribution)
* [License](#license)
* [Contact](#contact)

## Description
This plugin is inspired by the original [Harpoon](https://github.com/ThePrimeagen/harpoon) NeoVim plugin by ThePrimeagen
but aims to provide the same functionality for JetBrains IDEs.

## Features
* Navigate to your most used files with a single keystroke.
* Easily append files to your Harpoon list.
* Quick Menu
  * Select a file from your Harpoon list and open it.
  * Delete a file from your Harpoon list.
  * Reorder / Move files in your Harpoon list.
  * Add / Insert files to your Harpoon list.
  * Project-relative file paths using `...` syntax.
* Per-project Harpoon lists.
* Configurable Vim & IDE keymaps for all actions.

## Installation
* Version 2023.1 or later of any IntelliJ-based IDE is required.
* Install the plugin from the [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/23771-harpoonforjb).

## Getting Started

### Example .ideavimrc Configuration
```vimrc
" Append the current file to the Harpoon list
map <leader>a :action HarpoonAppend<CR>

" Open the Harpoon quick menu
map <C-h> :action HarpoonToggleQuickMenu<CR>

" Select the given file (from the Quick Menu) and open it
map <leader>o :action HarpoonQuickMenuSelect<CR>

" Hotkeys to open the first 4 items in the Harpoon list
map <C-h> :action HarpoonSelect1<CR>
map <C-t> :action HarpoonSelect2<CR>
map <C-n> :action HarpoonSelect3<CR>
map <C-s> :action HarpoonSelect4<CR>

" Toggle previous & next buffers stored within Harpoon list
map <C-S-P> :action HarpoonSelectPrev<CR>
map <C-S-N> :action HarpoonSelectNext<CR>
```

### My .ideavimrc Configuration
```vimrc
map <leader>a :action HarpoonAppend<CR>
map <C-e> :action HarpoonToggleQuickMenu<CR>
" I am Using "Enter" from settings so no need to map it here

map <C-1> :action HarpoonSelect1<CR>
map <C-2> :action HarpoonSelect2<CR>
map <C-3> :action HarpoonSelect3<CR>
map <C-4> :action HarpoonSelect4<CR>
```

### Settings

**Descriptions**
*  Harpoon Width - Describes how wide the Harpoon Quick Menu should be.
* Harpoon Height - Describes how tall the Harpoon Quick Menu
* Harpoon Font Size - Describes the font size of the Harpoon Quick Menu
* Enter - When checked the Harpoon Quick Menu will open the file when you press enter, otherwise it will look for your .ideavimrc file for the keybinding.

**Defaults**
* Harpoon Width - 800
* Harpoon Height - 400
* Harpoon Font Size - 20
* Enter - true

## Contribution

This plugin is open-source and contributions are welcome.

Note to beginners or first-time contributors: I recommend starting by going to the issues tab and looking for issues to work on.

### Steps to contribute
1. Fork the repository.
2. Make your changes.
3. Push your changes to your fork.
4. Create a pull request.
5. Wait for the pull request to be reviewed.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

The logo was design by me and is copyrighted.
You may not use it without my permission.

## Contact
* [GitHub](https://github.com/Kyren223)
* Kyren223 on Discord

