// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package me.kyren223.trident

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.util.NlsContexts.ConfigurableName
import javax.swing.JComponent

class AppSettingsConfigurable : Configurable {
    private var settings: SettingsData? = null
    override fun getDisplayName(): @ConfigurableName String {
        return "Trident Settings"
    }

    override fun createComponent(): JComponent {
        settings = SettingsData()
        return settings!!.panel
    }

    override fun isModified(): Boolean {
        val settings = AppSettingsState.instance
        if (settings.width != this.settings!!.getWidth()) return true
        if (settings.height != this.settings!!.getHeight()) return true
        if (settings.fontSize != this.settings!!.getFontSize()) return true
        if (settings.enterToSelect != this.settings!!.getEnterToSelect()) return true
        return false
    }

    override fun apply() {
        val settings = AppSettingsState.instance
        settings.width = this.settings!!.getWidth()
        settings.height = this.settings!!.getHeight()
        settings.fontSize = this.settings!!.getFontSize()
        settings.enterToSelect = this.settings!!.getEnterToSelect()
    }

    override fun reset() {
        val settings = AppSettingsState.instance
        if (this.settings == null) {
            this.settings = SettingsData()
        }
        this.settings!!.setWidth(settings.width)
        this.settings!!.setHeight(settings.height)
        this.settings!!.setFontSize(settings.fontSize)
        this.settings!!.setEnterToSelect(settings.enterToSelect)
    }

    override fun disposeUIResources() {
        settings = null
    }
}
