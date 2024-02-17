import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.*;

public class GUI extends JFrame
{
    private JScrollPane jScrollPane;
    private JTextField jTextFieldMsgInput;
    private JTextArea jTextAreaChat;

    private final Chat chat;
    private String pseudo;

    private Client_chat client;


    public GUI(Chat chat)
    {
        super("CHAT RMI");

        this.chat = chat;

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720, 480);
        setResizable(false);

        initiateComponents();

        setVisible(true);
    }

    private void downloadHistory()
    {
        try {
            List<ChatMessage> history = chat.getHistory();
            for(ChatMessage msg: history){
                inputChat(msg);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void initiateComponents()
    {
        jTextFieldMsgInput = new JTextField();
        jTextAreaChat = new JTextArea();
        jScrollPane = new JScrollPane(jTextAreaChat);

        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jTextAreaChat.setLineWrap(true);

        jTextFieldMsgInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    chat.newMsg(client, jTextFieldMsgInput.getText());
                    jTextFieldMsgInput.setText("");
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        getContentPane().setLayout(new BorderLayout());

        getContentPane().add(jScrollPane, BorderLayout.CENTER);
        getContentPane().add(jTextFieldMsgInput, BorderLayout.SOUTH);

        }

    public String promptForName()
    {
        return JOptionPane.showInputDialog(this, "Entrez votre pseudo", JOptionPane.INPUT_VALUE_PROPERTY);
    }

    private void addClient(Client_chat client)
    {
        this.client = client;
    }

    public void inputChat(ChatMessage msg)
    {
        jTextAreaChat.append(msg.getTimestamp()+" "+msg.getAuthor()+": "+msg.getMessage()+"\n\n");
        jScrollPane.getVerticalScrollBar().setValue(jScrollPane.getVerticalScrollBar().getMaximum());
    }

}