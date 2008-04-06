package org.newdawn.commet.chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.newdawn.commet.message.Message;
import org.newdawn.commet.message.MessageChannel;

public class ChatClient extends JFrame {
	private JTextArea area = new JTextArea(80,20);
	private JTextField in = new JTextField();
	private MessageChannel channel;
	private String name = "User"+System.currentTimeMillis();
	
	public ChatClient() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		JScrollPane scroll = new JScrollPane(area);
		scroll.setBounds(0,0,600,375);
		in.setBounds(0,375,600,25);
		panel.add(scroll);
		panel.add(in);
		area.setEditable(false);
		
		in.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendChat();
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(panel);
		setSize(600,450);
		setResizable(false);
	}
	
	private void sendChat() {
		String text = in.getText();
		in.setText("");
	
		// do local echo
		append(name, text);
		
		// send remote chat
		try {
			channel.write(new ChatMessage(name, text), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void append(String sender, String text) {
		String line = sender+":"+text+"\n";
		area.append(line);
		area.select(area.getText().length(), area.getText().length());
		
	}
	public void connect() throws IOException {
		channel = new MessageChannel(new ChatMessageFactory(), "localhost", 12345);
		System.out.println("Connected with ID: "+channel.getChannelID());
		setVisible(true);
		
		while (!channel.isClosed()) {
			Message message = channel.read();
			if (message != null) {
				if (message.getID() == ChatMessage.ID) {
					ChatMessage chat = (ChatMessage) message;
					append(chat.getSender(), chat.getText());
				}
			}
			
			try { Thread.sleep(5); } catch (Exception e) {};
		}
	}
	
	public static void main(String[] argv) throws IOException {
		ChatClient client = new ChatClient();
		client.connect();
	}
}
