package client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel palselect = new JPanel();
	JPanel pal = new JPanel();
	
	JLabel lblDangNhap = new JLabel("Đăng nhập");
	JLabel lblTaikhoan = new JLabel("Tên");
	JLabel lblPass= new JLabel("Ip Server");
	
	JTextField tfUsername = new JTextField(30);
	JTextField tfIpServer = new JTextField(30);
	JPasswordField tfPass = new JPasswordField(30);
	
	JButton btnDn = new JButton("Đăng nhập");
	
	
	login(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(400,400);
		this.setLayout(new FlowLayout());
		palselect.setLayout(new GridLayout(1,2));
		palselect.add(lblDangNhap);
		pal.setLayout(new GridLayout(7,1));
		tfIpServer.setText("127.0.0.1");
		
		pal.add(palselect);
		pal.add(lblTaikhoan);
		pal.add(tfUsername);
		pal.add(lblPass);
		pal.add(tfIpServer);
//		pal.add(lblDK);
		pal.add(btnDn);
		btnDn.setBackground(Color.red);
		btnDn.setForeground(Color.white);
		btnDn.setFont(new Font( null, ABORT, 18));
		btnDn.addActionListener(e->{
			this.dispose();
			new UI(tfUsername.getText(),tfIpServer.getText() );
		});
		
		this.add(pal);		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new login();
	}
}
