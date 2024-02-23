package me.kyren223.trident

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
@State(name = "me.kyren223.trident.SettingsState", storages = [Storage("SdkSettingsPlugin.xml")])
class SettingsState : PersistentStateComponent<SettingsState?> {

    var enterToSelect = true
    var width = 800
    var height = 400
    var fontSize = 20

    override fun getState(): SettingsState {
        return this
    }

    override fun loadState(state: SettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: SettingsState
            get() = ApplicationManager.getApplication().getService(SettingsState::class.java)
    }
}
