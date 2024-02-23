<div align="center">

# Trident
##### Effortless file traversal to minimize keystrokes and maximize productivity

[![GitHub release](https://img.shields.io/github/v/release/Kyren223/Trident?style=for-the-badge)](https://github.com/Kyren223/Trident/releases/tag/1.0)
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
Trident is a plugin for JetBrains IDEs that allows you to quickly navigate to your most used files with as few keystrokes as possible.

The plugin is inspired by the [Harpoon](https://github.com/ThePrimeagen/harpoon) NeoVim plugin by ThePrimeagen but aims to add additional features and proper integration with the IDE.

## Features
* Navigate to your most used files with a single keystroke.
* Easily append files to a per-project list for fast access.
* Quick Menu
  * Select a file from the list and open it.
  * Delete a file from the list.
  * Reorder / Move files in the list.
  * Add / Insert files to the list.
  * Project-relative file paths using `...` syntax.
* Per-project lists.
* Configurable IdeaVim and IDE keymaps for all actions.

## Installation
* Version 2023.1 or later of any IntelliJ-based IDE is required.
* Install the plugin from the [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/23818-trident).

## Getting Started
* All actions can be mapped to IDE keymaps like usual, the examples below are for IdeaVim only.
* Make sure to download IdeaVim if you want to configure the keymaps via .ideavimrc

### Example .ideavimrc Configuration
```vimrc
" Append the current file to the list
map <leader>a :action TridentAppend<CR>

" Open the quick menu
map <C-h> :action TridentToggleQuickMenu<CR>

" Select the given file (from the Quick Menu) and open it
map <leader>o :action TridentQuickMenuSelect<CR>

" Hotkeys to open the first 4 items in the list
map <C-h> :action TridentSelect1<CR>
map <C-t> :action TridentSelect2<CR>
map <C-n> :action TridentSelect3<CR>
map <C-s> :action TridentSelect4<CR>

" Toggle previous & next buffers stored within the list
map <C-S-P> :action TridentSelectPrev<CR>
map <C-S-N> :action TridentSelectNext<CR>
```

### My .ideavimrc Configuration
```vimrc
map <leader>a :action TridentAppend<CR>
map <C-e> :action TridentToggleQuickMenu<CR>
" I am Using "Enter" from settings so no need to map it here

map <C-1> :action TridentSelect1<CR>
map <C-2> :action TridentSelect2<CR>
map <C-3> :action TridentSelect3<CR>
map <C-4> :action TridentSelect4<CR>
```

### Settings

**Descriptions**
* Width - Describes how wide the Quick Menu should be.
* Height - Describes how tall the Quick Menu
* Font Size - Describes the font size of the Quick Menu
* Enter to select - When checked the Quick Menu will open the file when you press enter, otherwise it will look for your .ideavimrc file for the keybinding.

**Defaults**
* Width - 800
* Height - 400
* Font Size - 20
* Enter to select - true

## Contribution

This plugin is open-source and contributions are welcome.

I recommend looking at the issues tab or the [TODO.md](TODO.md) file for ideas on what to work on.

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

