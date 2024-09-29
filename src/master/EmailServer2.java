package master;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;

import client.decodeMail;

public class EmailServer2 {
	private static final int PORT = 9876;
	private static final String BASE_DIR = "emails/";
	private static Hashtable<String, InetAddress> userAddress = new Hashtable<>();
	private static Hashtable<String, Integer> userPort = new Hashtable<>();

	private static void WriteData(String userName, String content) {
		String path = "D:\\eclipse-workspace\\MailUDP\\Data\\" + userName + "\\";
		path += System.currentTimeMillis() + ".txt";
		// Instantiate the File class
		File f1 = new File(path);
		// Creating a folder using mkdir() method
		boolean bool = false;
		try {
			if (!f1.exists())
				bool = f1.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (bool) {
			System.out.println("File is created successfully");
			FileWriter myWriter;
			try {
				myWriter = new FileWriter(path);
				myWriter.write(content);
				myWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("Error Found!");
		}

	}

	public static void main(String[] args) {

		try (DatagramSocket socket = new DatagramSocket(PORT)) {
			System.out.println("Email Server đang lắng nghe trên cổng " + PORT);

			while (true) {
				byte[] buffer = new byte[1024*1024];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);

				String incoming = new String(packet.getData(), 0, packet.getLength());
				String[] parts = incoming.split(":"); // "from:to:message"
				System.out.println(incoming);
				if(parts.length == 2) {
					String username = parts[0].trim();
					createFolder(username);	
					InetAddress address = packet.getAddress();				
					int clientPort = packet.getPort();
					readEmails(username, socket, address, clientPort);
					userAddress.put(username, address);
					userPort.put(username, clientPort);
				}
				else {				
					String receiver = decodeMail.decode(incoming).getReceiver();	
					saveEmail(incoming, receiver);
					if(userAddress.containsKey(receiver)) {
						System.out.println("Đang gửi thư");
						sendResponse(socket, userAddress.get(receiver), userPort.get(receiver), incoming);
					}
				}		
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void saveEmail(String mail, String username) {
		String directory = "D:\\eclipse-workspace\\MailUDP\\Data\\" + username;

		File file = new File(directory);
		if (!file.exists())
			file.mkdirs();
		String filePath = directory + "\\" + System.currentTimeMillis() + ".txt"; // Tạo tên file duy nhất

		try (FileWriter writer = new FileWriter(filePath, true)) {
			writer.write(mail);
			System.out.println("Thư đã được lưu vào " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void createFolder(String username) {
		String directory = "D:\\eclipse-workspace\\MailUDP\\Data\\" + username;
		File file = new File(directory);
		if (!file.exists())
			file.mkdirs();
	}
	private static void readEmails(String user, DatagramSocket socket, InetAddress address, int clientPort) {
		System.out.println("Đang đọc mail của người dùng: "+ user +"*");
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
	private static void sendResponse(DatagramSocket socket, InetAddress address, int clientPort, String response) throws IOException {
        byte[] responseData = response.getBytes();
        DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, address, clientPort);
        socket.send(responsePacket);
    }
}
