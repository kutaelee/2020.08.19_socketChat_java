import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.peer.FontPeer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server {
	JFrame frame = new JFrame("Server");
	JTextArea textarea=new JTextArea();
	JTextField inputText=new JTextField();
	JPanel panel = new JPanel();
	Font f1= new Font("돋움", Font.BOLD , 20);
	ServerSocket server;
	int port;
	Socket serverSocket;
	DataOutputStream outputStream;
	DataInputStream inputStream;
	
	public Server(int port) {
		try {
			serverSocket=makeServer(port);
			inputStream = connectInputStream();
			outputStream = connectOutputStream();
			sendMessageToClient("hi im server");
			showServerView();
			while(true) {
				String msg=reciveMessageFromClient();
				textarea.append(msg+"\n");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void eventHandler() {
		inputText.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent args) {
				try {
					sendMessageToClient(inputText.getText());
					System.out.println("event catch!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				textarea.append("Me : "+inputText.getText()+"\n");
				inputText.setText("");
			}
		});
	}
	public void showServerView() {
		textarea.setFont(f1);
		textarea.setText("서버 화면 \n");
		frame.add("Center",textarea);
		frame.add("South",inputText);
		frame.setSize(300,500);
		frame.setVisible(true);
		eventHandler();
	}
	
	public Socket makeServer(int port) throws IOException {
		server = new ServerSocket(port);
		System.out.println("socket loading");
		serverSocket = server.accept();
		System.out.println("socket accept");
		return serverSocket;
	}
	public DataOutputStream connectOutputStream() throws IOException {
		outputStream = new DataOutputStream(serverSocket.getOutputStream());
		System.out.println("output stream connect");
		return outputStream;
	}
	public DataInputStream connectInputStream() throws IOException {
		inputStream = new DataInputStream(serverSocket.getInputStream());
		System.out.println("input stream connect");
		return inputStream;
	}
	
	public void sendMessageToClient(String msg) throws IOException {
		outputStream.writeUTF("Server : "+msg);
		System.out.println("send Message To client");
	}
	
	public String reciveMessageFromClient() throws IOException {
		String msg = inputStream.readUTF();
		System.out.println("rcv Message To client");
		return msg;
	}
	
	public static void main(String[] args) {
		Server server=new Server(18181);

	}
}
