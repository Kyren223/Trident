// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package me.kyren223.trident.ui

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.util.NlsContexts.ConfigurableName
import me.kyren223.trident.data.SettingsData
import me.kyren223.trident.data.Settings
import javax.swing.JComponent

class SettingsConfigurable : Configurable {

    private var settings: SettingsData? = null

    override fun getDisplayName(): @ConfigurableName String {
        return "Trident Settings"
    }

    override fun createComponent(): JComponent {
        settings = SettingsData()
        return settings!!.panel
    }

    override fun isModified(): Boolean {
        val settings = Settings.state
        if (settings.width != this.settings!!.getWidth()) return true
        if (settings.height != this.settings!!.getHeight()) return true
        if (settings.fontSize != this.settings!!.getFontSize()) return true
        if (settings.enterToSelect != this.settings!!.getEnterToSelect()) return true
        if (settings.automaticMapping != this.settings!!.getAutomaticMapping()) return true
        if (settings.automaticReplacing != this.settings!!.getAutomaticReplacing()) return true
        if (settings.recursiveMapping != this.settings!!.getRecursiveMapping()) return true
        if (settings.rememberLine != this.settings!!.getRememberLine()) return true
        if (settings.indexCycling != this.settings!!.getIndexCycling()) return true
        return false
    }

    override fun apply() {
        val settings = Settings.state
        settings.width = this.settings!!.getWidth()
        settings.height = this.settings!!.getHeight()
        settings.fontSize = this.settings!!.getFontSize()
        settings.enterToSelect = this.settings!!.getEnterToSelect()
        settings.automaticMapping = this.settings!!.getAutomaticMapping()
        settings.automaticReplacing = this.settings!!.getAutomaticReplacing()
        settings.recursiveMapping = this.settings!!.getRecursiveMapping()
        settings.rememberLine = this.settings!!.getRememberLine()
        settings.indexCycling = this.settings!!.getIndexCycling()
    }

    override fun reset() {
        val settings = Settings.state
        if (this.settings == null) this.settings = SettingsData()
        this.settings!!.setWidth(settings.width)
        this.settings!!.setHeight(settings.height)
        this.settings!!.setFontSize(settings.fontSize)
        this.settings!!.setEnterToSelect(settings.enterToSelect)
        this.settings!!.setAutomaticMapping(settings.automaticMapping)
        this.settings!!.setAutomaticReplacing(settings.automaticReplacing)
        this.settings!!.setRecursiveMapping(settings.recursiveMapping)
        this.settings!!.setRememberLine(settings.rememberLine)
        this.settings!!.setIndexCycling(settings.indexCycling)
    }

    override fun disposeUIResources() {
        settings = null
    }
}
