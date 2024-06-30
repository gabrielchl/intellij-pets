package dev.gabrielchl.intellijPets.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import dev.gabrielchl.intellijPets.settings.PetsSettings
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel


class RemovePetAction: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val wrapper = RemovePetDialogWrapper()
        if (wrapper.showAndGet()) {
            PetsSettings.instance.state.petList.remove(wrapper.comboBox.item)
        }
    }

    class RemovePetDialogWrapper: DialogWrapper(true) {
        val comboBox = ComboBox(PetsSettings.instance.state.petList.toTypedArray())

        init {
            title = "Remove Pet"
            init()
        }

        override fun createCenterPanel(): JComponent {
            val dialogPanel = JPanel()

            val label = JLabel("Select pet to remove:")
            dialogPanel.add(label)

            dialogPanel.add(comboBox)

            return dialogPanel
        }

    }
}