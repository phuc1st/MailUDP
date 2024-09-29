package master;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.util.Hashtable;

import client.decodeMail;

public class EmailServer extends Thread {
	private static final int PORT = 9876;
	private static Hashtable<String, InetAddress> userAddress = new Hashtable<>();
	private static Hashtable<String, Integer> userPort = new Hashtable<>();
	private MainForm mainForm;
	public EmailServer(MainForm mainForm) {
		this.mainForm = mainForm;
		this.start();
	}
	public void run() 
	{
		try (DatagramSocket socket = new DatagramSocket(PORT)) {
			byte[] buffer = new byte[1024*1024];
			
			while (true) {
				
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);

				String incoming = new String(packet.getData(), 0, packet.getLength());
				String[] parts = incoming.split(":"); // "from:to:message"
				this.mainForm.appendMessageClient(incoming);
				if(parts.length == 2) {
					String username = parts[0].trim();
					createFolder(username);	
					InetAddress address = packet.getAddress();				
					int clientPort = packet.getPort();
					readEmails(username, socket, address, clientPort);
					userAddress.put(username, address);
					userPort.put(username, clientPort);
					this.mainForm.appendMessage("Client: "+ address + " "+ clientPort + " connected");
				}
				else {				
					String receiver = decodeMail.decode(incoming).getReceiver();	
					saveEmail(incoming, receiver);
					if(userAddress.containsKey(receiver)) {
						sendResponse(socket, userAddress.get(receiver), userPort.get(receiver), incoming);
					}
				}		
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveEmail(String mail, String username) {
		String directory = "D:\\eclipse-workspace\\MailUDP\\Data\\" + username;

		File file = new File(directory);
		if (!file.exists())
			file.mkdirs();
		String filePath = directory + "\\" + System.currentTimeMillis() + ".txt"; // Tạo tên file duy nhất

		try (FileWriter writer = new FileWriter(filePath, true)) {
			writer.write(mail);
			this.mainForm.appendMessage("Thư đã được lưu vào " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void createFolder(String username) {
		String directory = "D:\\eclipse-workspace\\MailUDP\\Data\\" + username;
		File file = new File(directory);
		if (!file.exists())
			file.mkdirs();
	}
	private void readEmails(String user, DatagramSocket socket, InetAddress address, int clientPort) {
		this.mainForm.appendMessage("Đang đọc mail của người dùng: "+ user);
		String directory = "D:\\eclipse-workspace\\MailUDP\\Data\\" + user;

		File dir = new File(directory);
		if (dir.exists()) {
			File[] files = dir.listFiles();
			if (files != null) {
				for (File file : files) {
					try {
						String content = new String(Files.readAllBytes(file.toPath()));
						sendResponse(socket, address,clientPort, content);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			System.out.println("Rỗng");
		}
	}
	private void sendResponse(DatagramSocket socket, InetAddress address, int clientPort, String response) throws IOException {
        byte[] responseData = response.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, address, clientPort);
        socket.send(responsePacket);
    }
}
