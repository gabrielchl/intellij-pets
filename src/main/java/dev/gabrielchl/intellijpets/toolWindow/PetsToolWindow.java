package dev.gabrielchl.intellijpets.toolWindow;

import dev.gabrielchl.intellijpets.settings.AppSettingsState;

import javax.swing.*;

public class PetsToolWindow {
    private JPanel contentPanel;
    private JPanel petPanel;
    private JPanel petInnerPanel;
    private JLabel petLabel;
    private Pet pet;

    protected Timer refresherTimer = null;

    public PetsToolWindow() {
        pet = new Pet(contentPanel, petInnerPanel, petLabel);
        startRefreshing();
    }

    protected void stopRefreshing() {
        if (refresherTimer != null) {
            refresherTimer.stop();
            refresherTimer = null;
        }
    }

    protected void startRefreshing() {
        stopRefreshing();
        refresherTimer = new Timer(150, e -> {
            if (AppSettingsState.getInstance().getSettingsChanged()) {
                pet = new Pet(contentPanel, petInnerPanel, petLabel);
            }
            pet.tick();
        });
        refresherTimer.start();
    }

    public JPanel getContent() {
        return contentPanel;
    }
}
