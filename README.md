<div align="center">

# Trident

##### Effortless file traversal to minimize keystrokes and maximize productivity

<a href="https://plugins.jetbrains.com/plugin/23818-trident" alt="JetBrains Plugin Downloads">
  <img alt="JetBrains Plugin Downloads" src="https://img.shields.io/jetbrains/plugin/d/23818-trident?style=for-the-badge">
</a>

<a href="https://plugins.jetbrains.com/plugin/23818-trident/versions" alt="JetBrains Plugin Version">
  <img alt="JetBrains Plugin Version" src="https://img.shields.io/jetbrains/plugin/v/23818-trident?style=for-the-badge">
</a>

<a href="https://plugins.jetbrains.com/plugin/23818-trident/reviews" alt="JetBrains Plugin Rating">
  <img alt="JetBrains Plugin Rating" src="https://img.shields.io/jetbrains/plugin/r/stars/23818-trident?style=for-the-badge&link=https%3A%2F%2Fplugins.jetbrains.com%2Fplugin%2F23818-trident%2Freviews">
</a>

<a href="https://github.com/Kyren223/Trident/releases" alt="GitHub Release">
  <img alt="GitHub Release" src="https://img.shields.io/github/v/release/Kyren223/Trident?sort=semver&style=for-the-badge">
</a>

</div>

## Table of Contents

* [Features](#features)
* [Installation](#installation)
* [Getting Started](#getting-started)
* [Configuration](#configuration)
* [Contribution](#contribution)
* [License](#license)
* [Contact](#contact)

## Description

Trident is a plugin for JetBrains IDEs that allows you to quickly navigate to your most used files with as few
keystrokes as possible.

The plugin is inspired by the [Harpoon](https://github.com/ThePrimeagen/harpoon) NeoVim plugin by ThePrimeagen but aims
to add additional features and proper integration with the IDE.

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
* Install the plugin through the IDE's plugin manager by searching for "Trident".
* Install the plugin from the [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/23818-trident).
* Install the plugin from the [GitHub Releases](https://github.com/Kyren223/Trident/releases).

## Getting Started

* The default settings are sensible, but you can customize them if you wish.

* Actions can be mapped to IDE keymaps, search for "Trident" in the IDE's keymap settings.
* If you are using IdeaVim, you can configure the keymaps through .ideavimrc

### Example .ideavimrc Configuration

```vimrc
" Append the current file to the list
map <leader>a :action TridentAppend<cr>

" Open the Trident list
map <C-h> :action TridentList<cr>

" Select the given file (from the Trident list) and open it
map <leader>o :action TridentListSelect<cr>

" You can assign up to 10 hotkeys using TridentSelect[1-10] 
" This is an example for the first 4 items in the list
map <C-h> :action TridentSelect1<CR>
map <C-t> :action TridentSelect2<CR>
map <C-n> :action TridentSelect3<CR>
map <C-s> :action TridentSelect4<CR>

" Toggle previous & next buffers stored within the list
map <C-S-P> :action TridentSelectPrev<CR>
map <C-S-N> :action TridentSelectNext<CR>
```

## Configuration

### Settings

* Search for "Trident Settings" in the IDE's settings
* You can customize the width, height, font size of the Trident list
* You can also customize other settings, each setting has an explanation under it

* All actions can be mapped to IDE keymaps
* Keymaps can also be configured through .ideavimrc (requires IdeaVim)

### Full .ideavimrc Configuration with all keymaps

* The comments explain the keymaps and their functionality

```vimrc
" Append the current file to the list
map <leader>a :action TridentAppend<cr>

" Open the Trident list
map <C-h> :action TridentList<cr>

" Select the given file (from the Trident list) and open it
map <leader>o :action TridentListSelect<cr>

" You can assign up to 10 hotkeys using TridentSelect[1-10] 
" These hotkeys are used to quickly navigate to the corresponding Trident list item
map <C-1> :action TridentSelect1<CR>
map <C-2> :action TridentSelect2<CR>
map <C-3> :action TridentSelect3<CR>
map <C-4> :action TridentSelect4<CR>
map <C-5> :action TridentSelect5<CR>
map <C-6> :action TridentSelect6<CR>
map <C-7> :action TridentSelect7<CR>
map <C-8> :action TridentSelect8<CR>
map <C-9> :action TridentSelect9<CR>
map <C-0> :action TridentSelect10<CR>

" Toggle previous & next buffers stored within the list
map <C-S-P> :action TridentSelectPrev<CR>
map <C-S-N> :action TridentSelectNext<CR>
```

### My settings values

* Width - 800
* Height - 400
* Font Size - 30

* Enter to select item - true
* Automatic Mappings - true
* Recursive Mappings - true
* Remember last line - false
* Index Cycling - true

### My .ideavimrc Configuration

```vimrc
map <leader>a :action TridentAppend<cr>
map <C-e> :action TridentToggleQuickMenu<cr>
" I am Using "Enter to select" from settings 
" so I don't need to map a TridentListSelect keymap here

map <C-1> :action TridentSelect1<cr>
map <C-2> :action TridentSelect2<cr>
map <C-3> :action TridentSelect3<cr>
map <C-4> :action TridentSelect4<cr>
```

## Contribution

This plugin is open-source and contributions are welcome.

If you are looking for what to work on, see the [issues tab](https://github.com/Kyren223/Trident/issues)

It's recommended to first open an issue before starting to work on a feature.

### Steps to contribute

1. Fork the repository.
2. Make your changes and commit them.
3. Push your changes to your fork.
4. Create a pull request.
5. Wait for the pull request to be reviewed and merged.

## License

This project is licensed under the MIT License, see the [LICENSE](LICENSE) file for details.

The logo was designed by me and is copyrighted.
You may not use it without my permission.

## Contact

* [GitHub](https://github.com/Kyren223)
* Kyren223 on Discord

