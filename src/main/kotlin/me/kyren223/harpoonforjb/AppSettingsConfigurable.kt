// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package me.kyren223.harpoonforjb

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.util.NlsContexts.ConfigurableName
import javax.swing.JComponent

class AppSettingsConfigurable : Configurable {
    private var settings: SettingsData? = null
    override fun getDisplayName(): @ConfigurableName String {
        return "HarpoonForJB Settings"
    }

    override fun createComponent(): JComponent {
        settings = SettingsData()
        return settings!!.panel
    }

    override fun isModified(): Boolean {
        val settings = AppSettingsState.instance
        if (settings.harpoonWidth != this.settings!!.getHarpoonWidth()) return true
        if (settings.harpoonHeight != this.settings!!.getHarpoonHeight()) return true
        if (settings.harpoonFontSize != this.settings!!.getHarpoonFontSize()) return true
        if (settings.harpoonEnterToSelect != this.settings!!.getHarpoonEnterToSelect()) return true
        return false
    }

    override fun apply() {
        val settings = AppSettingsState.instance
        settings.harpoonWidth = this.settings!!.getHarpoonWidth()
        settings.harpoonHeight = this.settings!!.getHarpoonHeight()
        settings.harpoonFontSize = this.settings!!.getHarpoonFontSize()
        settings.harpoonEnterToSelect = this.settings!!.getHarpoonEnterToSelect()
    }

    override fun reset() {
        val settings = AppSettingsState.instance
        if (this.settings == null) {
            this.settings = SettingsData()
        }
        this.settings!!.setHarpoonWidth(settings.harpoonWidth)
        this.settings!!.setHarpoonHeight(settings.harpoonHeight)
        this.settings!!.setHarpoonFontSize(settings.harpoonFontSize)
        this.settings!!.setHarpoonEnterToSelect(settings.harpoonEnterToSelect)
    }

    override fun disposeUIResources() {
        settings = null
    }
}
