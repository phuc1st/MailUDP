package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class clientObj {
	private String hostname;
	private int port;
	private DatagramSocket socket;
	private DatagramPacket outgoing;
	private DatagramPacket incoming;
	private InetAddress address;
	private byte[] buffer = new byte[1024*1024];
	
	public clientObj(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		init();
	}
	
	private void init() {
		try {
			this.socket = new DatagramSocket();	
			this.address = InetAddress.getByName(hostname);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void send(String data) {
		byte[] buffer = data.getBytes();	
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String receive() {
		incoming = new DatagramPacket(buffer, buffer.length);
		try {
			socket.receive(incoming);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(incoming.getData(), 0, incoming.getLength());
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
