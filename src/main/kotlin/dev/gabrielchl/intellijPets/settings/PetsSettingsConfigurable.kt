package dev.gabrielchl.intellijPets.settings

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent

class PetsSettingsConfigurable: Configurable {
    private var petsSettingsComponent = PetsSettingsComponent()

    override fun createComponent(): JComponent {
        petsSettingsComponent = PetsSettingsComponent()
        return petsSettingsComponent.panel
    }

    override fun isModified(): Boolean {
        val state = PetsSettings.instance.state
        return petsSettingsComponent.petScaleComboBox.item !== state.petScale
    }

    override fun apply() {
        val state = PetsSettings.instance.state
        state.petScale = petsSettingsComponent.petScaleComboBox.item
    }

    override fun reset() {
        val state = PetsSettings.instance.state
        petsSettingsComponent.petScaleComboBox.item = state.petScale
    }

    override fun getDisplayName(): String {
        return "Pets"
    }
}