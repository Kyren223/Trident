package me.kyren223.trident.data

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.*

// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
@Service
@State(name = "me.kyren223.trident.data.SettingsState", storages = [Storage("SdkSettingsPlugin.xml")])
class Settings : SimplePersistentStateComponent<SettingsState>(SettingsState()) {
    companion object {
        val instance: Settings
            get() = ApplicationManager.getApplication().getService(Settings::class.java)
        val state: SettingsState
            get() = instance.state
    }
}

class SettingsState : BaseState() {
    var width = 800
    var height = 400
    var fontSize = 20
    var enterToSelect = true
    var automaticMapping = false
    var recursiveMapping = false
    var rememberLine = false
    var indexCycling = false
}
