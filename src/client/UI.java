package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;


public class UI extends Thread{
	private JFrame jframe;
	private JPanel funcPal, listPal, contentPal, mainPanel, sidePanel, sendPanel;
	private JTextArea textArea;
	private JButton sendButton;
	private JTextField subjectField, 
	   					toField;
	private JLabel newMailLbl, usernameLbl;
	private clientObj clObj;
	private String username;
	private Mail mail;
	
	
	public UI(String username, String serverAddress) {
		this.username = username;
		init(username);	
		this.clObj = new clientObj(serverAddress,9876);
		connect();
		this.start();
	}

	private void init(String username) {
		jframe = new JFrame("Giao diện với 3 JPanel");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(800, 600);
        
        // Tạo JPanel chính với BorderLayout
        mainPanel = new JPanel(new GridLayout(1, 2));
        
        // Tạo funcPal và listPal với GridLayout
        funcPal = new JPanel();
        funcPal.setBackground(Color.decode("#1874bf"));
        listPal = new JPanel();
        listPal.setBackground(Color.WHITE);
        
        // Tạo contentPal
//        CardLayout cardLayout = new CardLayout();
        contentPal = new JPanel();
//        contentPal.setBackground(Color.BLUE);
        
        // Tạo JPanel chứa funcPal và listPal
        sidePanel = new JPanel(new GridLayout(1, 2));
        JScrollPane scrollPane = new JScrollPane(listPal);
        sidePanel.add(funcPal);
        sidePanel.add(scrollPane);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Thêm sidePanel và contentPal vào mainPanel
        mainPanel.add(sidePanel);
        mainPanel.add(contentPal);
        
        // Thêm mainPanel vào frame
        jframe.add(mainPanel);
        
		listPal.setLayout(new BoxLayout(listPal, 1));		
		
		usernameLbl = new JLabel("  "+username);
		usernameLbl.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		usernameLbl.setForeground(Color.WHITE);
		
        funcPal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		funcPal.setLayout(new BoxLayout(funcPal, 1));
		funcPal.add(usernameLbl);
		
		funcPal.add(Box.createRigidArea(new Dimension(0, 10))); // Khoảng cách 10 pixel
		
		newMailLbl = new JLabel("+ New mail");
		newMailLbl.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		newMailLbl.setForeground(Color.WHITE);
		funcPal.add(newMailLbl);
		newMailLbl.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent e) {
	            	sendPanel.setVisible(true);
	            	toField.setText("");
	            	subjectField.setText("");
	            	textArea.setText("");
	            }
	        }					);
		funcPal.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent e) {
	                // Xử lý sự kiện click chuột
//	               cardLayout.show(contentPal, "p2");
	            }
	        });
		 setContentPanel();
		jframe.setVisible(true);
	
	}

	private JPanel titLetter(String date, String sender, String subject, String content, JTextArea jTextArea) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JLabel label1 = new JLabel(date);
		JLabel label2 = new JLabel(sender);
		JLabel label3 = new JLabel(subject);
		 panel.setBorder(BorderFactory.createCompoundBorder(
				 BorderFactory.createLineBorder(Color.BLACK, 2),
		            BorderFactory.createEmptyBorder(10, 10, 10, 10) 
		        ));
		
		panel.add(label1);
		panel.add(label2);
		panel.add(label3);
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80)); // Chiều rộng tối đa
		 panel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
	                // Xử lý sự kiện click chuột
					textArea.setText(subject +"\nNgười gửi: "+sender+"\nThời gian: "+ date+"\n");
	              	textArea.append(content);
	               sendPanel.setVisible(false);
	            }
	        });
		return panel;
	}
	
	private void setContentPanel() {
		
		BorderLayout layout = new BorderLayout();
		layout.setVgap(10);
		contentPal.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        sendPanel = new JPanel(new GridBagLayout());
        // Cài đặt các thuộc tính cho GridBagConstraints
        gbc.fill = GridBagConstraints.NONE; // Làm cho các thành phần chiếm chiều rộng
        gbc.weightx = 1.0; // Kích thước tương đối chiều rộng
        
        // Tạo và thêm nút Send
        sendButton = new JButton("Send");
        sendButton.addActionListener(e -> {
        	this.sendMail();
        });
        gbc.gridx = 1; // Cột thứ 2
        gbc.gridy = 0; // Hàng đầu tiên
        gbc.weightx = 1.0 / 5.0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.anchor = GridBagConstraints.CENTER;
        sendPanel.add(sendButton, gbc);
        
        gbc.weightx =1.0 / 1.0;
        gbc.anchor = GridBagConstraints.PAGE_START;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
        // Tạo và thêm vùng nhập người gửi
        gbc.gridx = 0; // Cột thứ 1
        gbc.gridy = 1; // Hàng thứ 2
        sendPanel.add(new JLabel("To:"), gbc);
        
        toField = new JTextField(20);
        gbc.gridx = 1; // Cột thứ 2
        sendPanel.add(toField, gbc);
        
        // Tạo và thêm vùng nhập subject
        gbc.gridx = 0; // Cột thứ 1
        gbc.gridy = 2; // Hàng thứ 3
        sendPanel.add(new JLabel("Subject:"), gbc);
        
        gbc.gridx = 1;
        subjectField = new JTextField(20);
        sendPanel.add(subjectField, gbc);
        

        textArea = new JTextArea(); // 5 dòng, 20 cột
        textArea.setLineWrap(true); // Cho phép xuống dòng
        textArea.setWrapStyleWord(true); // Bẻ dòng theo từ
        contentPal.add(sendPanel, BorderLayout.NORTH);
        contentPal.add(textArea, BorderLayout.CENTER);
        contentPal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sendPanel.setVisible(false);
	}
	
	private void connect() {
		this.clObj.send(this.username + ":" + "connect");
	}
	private void sendMail() {
		mail = new Mail(username, this.toField.getText(), this.subjectField.getText() , this.textArea.getText());
		this.clObj.send(decodeMail.encode(mail));
    	toField.setText("");
    	subjectField.setText("");
    	textArea.setText("");
	}
	@Override
	public void run() {
		while(true) {
			String data = this.clObj.receive();
			this.mail = decodeMail.decode(data);
			listPal.add(
					titLetter(mail.getDate(), mail.getSender(), mail.getSubject(), mail.getContent(), textArea)
					,0);
			jframe.revalidate();
			jframe.repaint();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new UI("huy", "localhost");
	}

}
