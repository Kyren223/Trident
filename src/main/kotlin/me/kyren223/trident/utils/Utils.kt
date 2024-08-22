package me.kyren223.trident.utils

import com.maddyhome.idea.vim.api.injector
import com.maddyhome.idea.vim.command.MappingMode
import com.maddyhome.idea.vim.key.MappingOwner

fun injectEnterToSelectMapping() {
    injector.keyGroup.putKeyMapping(
        MappingMode.NV,
        injector.parser.parseKeys("<cr>"),
        MappingOwner.Plugin.get("Trident"),
        injector.parser.parseKeys(":action TridentListSelect<cr>"),
        false
    )
}

