package dev.gabrielchl.intellijpets.settings;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class AppSettingsComponent {

    private final JPanel myMainPanel;
    private final ComboBox<String> petVariantComboBox = new ComboBox<>(new String[]{
            "cat-1",
            "cat-2",
            "cat-3",
            "cat-4",
            "cat-5"
    });

    public AppSettingsComponent() {
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Pet variant: "), petVariantComboBox, 1, false)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return petVariantComboBox;
    }

    @NotNull
    public String getPetVariant() {
        return petVariantComboBox.getItem();
    }

    public void setPetVariant(@NotNull String newVariant) {
        petVariantComboBox.setItem(newVariant);
    }

}