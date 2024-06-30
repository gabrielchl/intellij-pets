package dev.gabrielchl.intellijPets.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.*

@Service
@State(
    name = "dev.gabrielchl.intellijpets.settings.AppSettingsState",
    storages = [Storage("IntellijPetsSettings.xml")]
)
class PetsSettings: PersistentStateComponent<PetsSettings.State> {
    class State {
        var petList = arrayListOf("cat-1")
        var petScale = 1.0
    }

    companion object {
        val instance: PetsSettings
            get() = ApplicationManager.getApplication().getService(PetsSettings::class.java)
    }

    var petsState: State = State()

    override fun getState(): State {
        return petsState
    }

    override fun loadState(state: State) {
        this.petsState = state
    }
}