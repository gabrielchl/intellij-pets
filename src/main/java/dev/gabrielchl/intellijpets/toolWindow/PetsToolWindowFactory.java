package dev.gabrielchl.intellijpets.toolWindow;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class PetsToolWindowFactory implements ToolWindowFactory, DumbAware {

    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        PetsToolWindow petsToolWindow = new PetsToolWindow();
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(petsToolWindow.getContent(), "", false);
        toolWindow.getContentManager().addContent(content);
    }

}