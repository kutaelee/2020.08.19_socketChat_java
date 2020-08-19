import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client {

	JFrame frame = new JFrame("Clinet");
	JTextArea textarea = new JTextArea();
	JTextField inputText = new JTextField();
	Font f1= new Font("돋움", Font.BOLD , 20);
	Socket clientSocket;
	DataOutputStream outputStream;
	DataInputStream inputStream;

	public Client(String host, int port) {
		try {
			clientSocket = throwSocket(host, port);
			inputStream = connectInputStream();
			outputStream = connectOutputStream();
			sendMessageToServer("hi im client");
			showClientView();
			while(true) {
				String msg=reciveMessageFromServer();
				textarea.append(msg+"\n");
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void eventHandler() {
		inputText.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent args) {
				try {
					sendMessageToServer(inputText.getText());
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
	public void showClientView() {
		textarea.setFont(f1);
		textarea.setText("클라이언트화면 \n");
		frame.add("Center", textarea);
		frame.add("South", inputText);
		frame.setSize(300, 500);
		frame.setVisible(true);
		eventHandler();
	}

	public Socket throwSocket(String host, int port) throws UnknownHostException, IOException {
		clientSocket = new Socket(host, port);
		System.out.println("socket throw server");
		return clientSocket;
	}

	public DataOutputStream connectOutputStream() throws IOException {
		outputStream = new DataOutputStream(clientSocket.getOutputStream());
		System.out.println("output stream connect");
		return outputStream;
	}

	public DataInputStream connectInputStream() throws IOException {
		inputStream = new DataInputStream(clientSocket.getInputStream());
		System.out.println("input stream connect");
		return inputStream;
	}

	public void sendMessageToServer(String msg) throws IOException {
		outputStream.writeUTF("Client : "+msg);
		System.out.println("send Message To server");
	}

	public String reciveMessageFromServer() throws IOException {
		String msg = inputStream.readUTF();
		System.out.println("rcv Message To client");
		return msg;
	}

	public static void main(String[] args) {
		Client client = new Client("localhost", 18181);
		
	}
}
