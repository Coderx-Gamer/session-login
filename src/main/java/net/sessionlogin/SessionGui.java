package net.sessionlogin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;
import net.sessionlogin.mixin.accessor.MinecraftClientAccessor;
import net.sessionlogin.mixin.accessor.SessionAccessor;
import org.apache.commons.codec.binary.Hex;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.util.Optional;

public class SessionGui {
    public static SessionGui defaultGui = null;

    private JFrame frame;

    private JLabel usernameLabel;
    private JTextField usernameField;

    private JLabel uuidLabel;
    private JTextField uuidField;

    private JLabel tokenLabel;
    private JTextField tokenField;

    private JLabel accountTypeLabel;
    private JComboBox<String> accountTypeField;

    private JLabel xuidLabel;
    private JTextField xuidField;

    private JLabel clientIdLabel;
    private JTextField clientIdField;

    private JButton copyToClipboardButton;

    private JButton loadFromClipboardButton;

    private JButton loginButton;

    private JButton loadCurrentAccountButton;

    public SessionGui() {
        init();
    }

    private void init() {
        MinecraftClient mc = MinecraftClient.getInstance();

        frame = new JFrame("Session");
        frame.setBounds(0, 0, 500, 270);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(10, 10, 90, 20);
        usernameLabel.setFocusable(false);

        usernameField = new JTextField(1);
        usernameField.setBounds(90, 10, 120, 20);
        usernameField.setText(mc.getSession().getUsername());
        usernameField.setBorder(BorderFactory.createEmptyBorder());

        uuidLabel = new JLabel("UUID:");
        uuidLabel.setBounds(10, 40, 60, 20);
        uuidLabel.setFocusable(false);

        uuidField = new JTextField(1);
        uuidField.setBounds(60, 40, 120, 20);
        uuidField.setText(mc.getSession().getUuid());
        uuidField.setBorder(BorderFactory.createEmptyBorder());

        tokenLabel = new JLabel("Token:");
        tokenLabel.setBounds(10, 70, 60, 20);
        tokenLabel.setFocusable(false);

        tokenField = new JTextField(1);
        tokenField.setBounds(60, 70, 120, 20);
        tokenField.setText(mc.getSession().getAccessToken());
        tokenField.setBorder(BorderFactory.createEmptyBorder());

        accountTypeLabel = new JLabel("Account Type:");
        accountTypeLabel.setBounds(10, 100, 100, 20);
        accountTypeLabel.setFocusable(false);

        accountTypeField = new JComboBox<>(new String[]{"MSA", "LEGACY", "MOJANG"});
        accountTypeField.setBounds(100, 100, 140, 22);
        accountTypeField.setSelectedItem(mc.getSession().getAccountType().toString());
        accountTypeField.setFocusable(false);
        accountTypeField.setBorder(BorderFactory.createEmptyBorder());

        xuidLabel = new JLabel("XUID (optional):");
        xuidLabel.setBounds(10, 130, 90, 20);
        xuidLabel.setFocusable(false);

        xuidField = new JTextField(1);
        xuidField.setBounds(90, 130, 120, 20);
        xuidField.setText(mc.getSession().getXuid().isEmpty() ? "" : mc.getSession().getXuid().get());
        xuidField.setBorder(BorderFactory.createEmptyBorder());

        clientIdLabel = new JLabel("Client ID (optional):");
        clientIdLabel.setBounds(10, 160, 130, 20);
        clientIdLabel.setFocusable(false);

        clientIdField = new JTextField(1);
        clientIdField.setBounds(130, 160, 120, 20);
        clientIdField.setText(mc.getSession().getClientId().isEmpty() ? "" : mc.getSession().getClientId().get());
        clientIdField.setBorder(BorderFactory.createEmptyBorder());

        copyToClipboardButton = new JButton("Copy To Clipboard");
        copyToClipboardButton.setBounds(10, 190, 140, 20);
        copyToClipboardButton.setFocusable(false);
        copyToClipboardButton.addActionListener((e) -> {
            StringBuilder builder = new StringBuilder();
            builder.append((usernameField.getText().equals("") ? "empty" : usernameField.getText()) + "~");
            builder.append((uuidField.getText().equals("") ? "empty" : uuidField.getText()) + "~");
            builder.append((tokenField.getText().equals("") ? "empty" : tokenField.getText()) + "~");
            builder.append((accountTypeField.getSelectedItem().toString().equals("") ? "empty" : accountTypeField.getSelectedItem().toString()) + "~");
            builder.append((xuidField.getText().equals("") ? "empty" : xuidField.getText()) + "~");
            builder.append((clientIdField.getText().equals("") ? "empty" : clientIdField.getText()));
            String configString = new String(Hex.encodeHex(builder.toString().getBytes()));
            StringSelection stringSelection = new StringSelection(configString);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });

        loadFromClipboardButton = new JButton("Load From Clipboard");
        loadFromClipboardButton.setBounds(170, 190, 140, 20);
        loadFromClipboardButton.setFocusable(false);
        loadFromClipboardButton.addActionListener((e) -> {
            try {
                String configString = new String(Hex.decodeHex(Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString().toCharArray()));
                int charCount = 0;
                for (char c : configString.toCharArray()) {
                    if (c == '~') {
                        charCount++;
                    }
                }
                if (charCount == 5) {
                    String[] data = configString.split("~");
                    String username = data[0];
                    String uuid = data[1];
                    String token = data[2];
                    usernameField.setText(username);
                    uuidField.setText(uuid);
                    tokenField.setText(token);
                    accountTypeField.setSelectedItem(data[3]);
                    xuidField.setText(data[4].equals("empty") ? "" : data[4]);
                    clientIdField.setText(data[5].equals("empty") ? "" : data[5]);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        loginButton = new JButton("Login");
        loginButton.setBounds(330, 190, 70, 20);
        loginButton.setFocusable(false);
        loginButton.addActionListener((e) -> {
            String username = usernameField.getText();
            String uuid = uuidField.getText();
            String token = tokenField.getText();
            Session.AccountType accountType;
            switch (accountTypeField.getSelectedItem().toString()) {
                case "MOJANG" -> accountType = Session.AccountType.MOJANG;
                case "LEGACY" -> accountType = Session.AccountType.LEGACY;
                default -> accountType = Session.AccountType.MSA;
            }
            Optional<String> xuid = xuidField.getText().equals("empty") ? Optional.empty() : Optional.of(xuidField.getText());
            Optional<String> clientId = clientIdField.getText().equals("empty") ? Optional.empty() : Optional.of(clientIdField.getText());

            MinecraftClient client = MinecraftClient.getInstance();
            ((SessionAccessor) client.getSession()).setUsername(username);
            ((SessionAccessor) client.getSession()).setUuid(uuid);
            ((SessionAccessor) client.getSession()).setAccessToken(token);
            ((SessionAccessor) client.getSession()).setAccountType(accountType);
            ((SessionAccessor) client.getSession()).setXuid(xuid);
            ((SessionAccessor) client.getSession()).setClientId(clientId);
            ((MinecraftClientAccessor) MinecraftClient.getInstance()).setSession(client.getSession());
        });

        loadCurrentAccountButton = new JButton("Load Current Account");
        loadCurrentAccountButton.setBounds(260, 160, 150, 20);
        loadCurrentAccountButton.setFocusable(false);
        loadCurrentAccountButton.addActionListener((e) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            String username = client.getSession().getUsername();
            String uuid = client.getSession().getUuid();
            String token = client.getSession().getAccessToken();
            String accountType = client.getSession().getAccountType().toString();
            String xuid = client.getSession().getXuid().isEmpty() ? "" : client.getSession().getXuid().get();
            String clientId = client.getSession().getClientId().isEmpty() ? "" : client.getSession().getClientId().get();
            usernameField.setText(username);
            uuidField.setText(uuid);
            tokenField.setText(token);
            accountTypeField.setSelectedItem(accountType);
            xuidField.setText(xuid);
            clientIdField.setText(clientId);
        });

        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(uuidLabel);
        frame.add(uuidField);
        frame.add(tokenLabel);
        frame.add(tokenField);
        frame.add(accountTypeLabel);
        frame.add(accountTypeField);
        frame.add(xuidLabel);
        frame.add(xuidField);
        frame.add(clientIdLabel);
        frame.add(clientIdField);
        frame.add(copyToClipboardButton);
        frame.add(loadFromClipboardButton);
        frame.add(loginButton);
        frame.add(loadCurrentAccountButton);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void show() {
        frame.setVisible(true);
    }

    public void hide() {
        frame.setVisible(false);
    }

    public void toggleVisibility() {
        if (isVisible()) {
            hide();
        } else {
            show();
        }
    }

    public boolean isVisible() {
        return frame.isVisible();
    }
}
