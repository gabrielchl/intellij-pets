package dev.gabrielchl.intellijPets.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import dev.gabrielchl.intellijPets.settings.PetsSettings
import dev.gabrielchl.intellijPets.utils.Constants
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel


class AddPetAction: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val wrapper = AddPetDialogWrapper()
        if (wrapper.showAndGet()) {
            PetsSettings.instance.state.petList.add(wrapper.comboBox.item)
        }
    }

    class AddPetDialogWrapper: DialogWrapper(true) {
        val comboBox = ComboBox(Constants.PET_TYPES.toTypedArray())

        init {
            title = "Add Pet"
            init()
        }

        override fun createCenterPanel(): JComponent {
            val dialogPanel = JPanel()

            val label = JLabel("Select pet variant to add:")
            dialogPanel.add(label)

            dialogPanel.add(comboBox)

            return dialogPanel
        }
    }
}