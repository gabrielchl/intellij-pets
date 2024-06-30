package dev.gabrielchl.intellijPets.settings

import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder
import javax.swing.JPanel

class PetsSettingsComponent {
    val panel: JPanel
    val petScaleComboBox = ComboBox(
        (1..15)
            .map { it.toDouble() * 2 / 10 }
            .toTypedArray()
    )

    init {
        panel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Pet scale: "), petScaleComboBox, 1, false)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }
}