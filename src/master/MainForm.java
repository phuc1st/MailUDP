/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package master;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.*;


public class MainForm extends JFrame {

	SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
	
	private EmailServer emailServer;

	// Tạo cấu trúc form MainForm

	public MainForm() {
		initComponents();
		MyInit();
	}

	void MyInit() {
		setLocationRelativeTo(null);
	}

	public void appendMessage(String msg) {
		Date date = new Date();
		jTextArea1.append(sdf.format(date) + ": " + msg + "\n");
		jTextArea1.setCaretPosition(jTextArea1.getText().length() - 1);
	}
	public void appendMessageClient(String msg) {
		Date date = new Date();
		jTextArea2.append(sdf.format(date) + ": " + msg + "\n");
		jTextArea2.setCaretPosition(jTextArea2.getText().length() - 1);
	}

	private void initComponents() {

		jLabel1 = new JLabel();
		jTextField1 = new JTextField();
		jButton1 = new JButton();
		jScrollPane1 = new JScrollPane();
		jTextArea1 = new JTextArea();
		jTextArea2 = new JTextArea();
		jLabel2 = new JLabel();
		
		//mới
		tabPane = new JTabbedPane();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Server");

		jLabel1.setText("Port:");

		jTextField1.setText("8000");
		
		//mới
		tabPane.add("Socket connect", jTextArea1);		
		tabPane.add("Data from client", jTextArea2);	
		
		jButton1.setText("Start Server");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jTextArea1.setEditable(false);
		jTextArea1.setBackground(new java.awt.Color(255, 193, 193));
		jTextArea1.setColumns(20);
		jTextArea1.setForeground(new java.awt.Color(0, 0, 0));
		jTextArea1.setRows(5);
		
		//new code
		jTextArea2.setEditable(false);
		jTextArea2.setBackground(new java.awt.Color(255, 193, 193));
		jTextArea2.setColumns(20);
		jTextArea2.setForeground(new java.awt.Color(0, 0, 0));
		jTextArea2.setRows(5);
		//new code
		jScrollPane1.setViewportView(tabPane);
	
		
		jLabel2.setIcon(new ImageIcon(getClass().getResource("/image/rabiit.png"))); // NOI18N

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(15, 15, 15)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(jLabel1)
						.addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 139,
								GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
						.createSequentialGroup()
						.addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 86,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jButton1))
						.addComponent(jScrollPane1))
				.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 217,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 217,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel1).addComponent(jTextField1, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(jButton1))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		pack();
		this.setSize(600, 310);
	}// </editor-fold>//GEN-END:initComponents

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		// TODO add your handling code here:
		int port = Integer.parseInt(jTextField1.getText());
		System.out.println(port);
		emailServer = new EmailServer(this);
		this.appendMessage("[Server]: Server started!");

		jButton1.setEnabled(false);
	}// GEN-LAST:event_jButton1ActionPerformed



	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
			
				new MainForm().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JButton jButton1;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JScrollPane jScrollPane1;
	private JTextArea jTextArea1;
	private JTextArea jTextArea2;
	private JTextField jTextField1;
	private JTabbedPane tabPane;
	// End of variables declaration//GEN-END:variables

}
