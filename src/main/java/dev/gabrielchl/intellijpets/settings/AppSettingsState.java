package dev.gabrielchl.intellijpets.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "dev.gabrielchl.intellijpets.settings.AppSettingsState",
        storages = @Storage("IntellijPetsSettings.xml")
)
public class AppSettingsState implements PersistentStateComponent<AppSettingsState> {
    private Boolean settingsChanged = false;
    private String petVariant = "cat-1";

    public String getPetVariant() {
        return petVariant;
    }

    public void setPetVariant(String petVariant) {
        this.petVariant = petVariant;
        this.settingsChanged = true;
    }

    public Boolean getSettingsChanged() {
        if (settingsChanged) {
            settingsChanged = false;
            return true;
        } else {
            return false;
        }
    }

    public static AppSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(AppSettingsState.class);
    }

    @Nullable
    @Override
    public AppSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull AppSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
