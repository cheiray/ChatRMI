import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.*;

public class GUI extends JFrame {
    private final Chat chat;
    private final JTextArea chatTextArea;
    private final JTextField messageTextField;
    private final JButton sendButton;
    private String pseudo;
    Client_chat_impl clientImpl;

    public GUI(Chat chat, Client_chat client) throws RemoteException {
        super("Chat RMI");
        this.chat = chat;
        this.clientImpl = (Client_chat_impl) client;
        clientImpl.setPseudo(this.promptForName());
        clientImpl.setGui(this);

        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        chatTextArea = new JTextArea();
        chatTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatTextArea);

        messageTextField = new JTextField(30);
        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageTextField.getText();
                if (!message.isEmpty()) {
                    try {
                        System.out.println(client+" "+message);
                        chat.newMsg(client, message);
                        messageTextField.setText("");
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(messageTextField);
        inputPanel.add(sendButton);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        addHistory(chat.getHistory());

        setVisible(true);
    }

    public String promptForName() {
        return JOptionPane.showInputDialog(this, "Enter your pseudo:");
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void addMessage(ChatMessage message) {
        chatTextArea.append(message.toString() + "\n");
    }

    public void addNotification(String message) {
        chatTextArea.append(message.toString() + "\n");
    }

    public void addHistory(List<ChatMessage> history) {
        for (ChatMessage message : history) {
            addMessage(message);
        }
    }
}
