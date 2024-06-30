package dev.gabrielchl.intellijPets.toolWindow

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import dev.gabrielchl.intellijPets.settings.PetsSettings
import dev.gabrielchl.intellijPets.utils.Constants
import java.awt.BorderLayout
import java.awt.Graphics
import java.util.ArrayList
import java.util.Timer
import javax.swing.JPanel
import kotlin.concurrent.timerTask

class PetsToolWindowFactory : ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val toolWindowContent = PetsToolWindowContent(toolWindow)
        val content = ContentFactory.getInstance().createContent(toolWindowContent.contentContainerPanel, "", false)
        toolWindow.contentManager.addContent(content)
    }

    class PetsToolWindowContent(toolWindow: ToolWindow) {
        var contentContainerPanel = JPanel(BorderLayout())
        var contentPanel = PetsToolWindowContentPanel()

        init {
            contentContainerPanel.add(contentPanel, BorderLayout.CENTER)
            val actionGroup = ActionManager.getInstance().getAction("Pets.Actions") as DefaultActionGroup
            val actionToolbar = ActionManager.getInstance().createActionToolbar("PetsToolBar", actionGroup, true)
            actionToolbar.targetComponent = contentContainerPanel
            contentContainerPanel.add(actionToolbar.component, BorderLayout.NORTH)
        }
    }

    class PetsToolWindowContentPanel: JPanel() {
        var pets = ArrayList<Pet>()
        var petScale: Double

        init {
            createPets()
            petScale = PetsSettings.instance.state.petScale

            // TODO: figure out a way to stop this when the tool window is closed
            Timer().scheduleAtFixedRate(timerTask {
                if (pets.joinToString { it.variant } != PetsSettings.instance.state.petList.joinToString() || petScale != PetsSettings.instance.state.petScale) {
                    createPets()
                    petScale = PetsSettings.instance.state.petScale
                }

                for (pet in pets) {
                    pet.tick()
                }
                repaint()
            }, 150, 150)
        }

        override fun paintComponent(g: Graphics) {
            super.paintComponent(g)
            for (pet in pets) {
                g.drawImage(pet.image, pet.currentX, this.height - pet.SPRITE_HEIGHT, pet.SPRITE_WIDTH, pet.SPRITE_HEIGHT, null)
            }
        }

        fun createPets() {
            pets.clear()
            for (petVariant in PetsSettings.instance.state.petList) {
                if (petVariant in Constants.PET_TYPES) {
                    pets.add(Pet(petVariant, this))
                }
            }
        }
    }
}