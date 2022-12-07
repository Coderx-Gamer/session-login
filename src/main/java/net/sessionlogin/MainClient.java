package net.sessionlogin;

import net.fabricmc.api.ClientModInitializer;

import javax.swing.*;

public class MainClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        System.setProperty("java.awt.headless", "false");
        SessionGui.defaultGui = new SessionGui();
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(SessionGui.defaultGui.getFrame());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
