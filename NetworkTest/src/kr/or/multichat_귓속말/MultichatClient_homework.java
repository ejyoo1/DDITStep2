package kr.or.multichat_귓속말;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * 추가 필요작업 : 서버 끊기면 클라이언트도 종료되는 기능 끝나야함.
 * @author 유은지
 *
 */
public class MultichatClient_homework {
	Scanner scan = new Scanner(System.in);
	private String nickName;//대화명
	
	//프로그램 시작
	public void startClient() {
		System.out.print("대화명>>");
		nickName = scan.next();
		
		//소켓접속 시작
		Socket socket = null;
		
		try {
			socket = new Socket("192.168.43.174", 7777);
			
			System.out.println("서버에 연결되었습니다.");
			
			//송신용 스레드 생성
			ClientSender sender = new ClientSender(socket, nickName);
			//수신용 스레드 생성
			ClientReceiver receiver = new ClientReceiver(socket);
			
			sender.start();
			receiver.start();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	class ClientSender extends Thread {
		Socket socket;
		DataOutputStream dos;
		String name;
		Scanner scan = new Scanner(System.in);
		
		public ClientSender(Socket socket, String name) {
			this.socket = socket;
			this.name = name;
			
			try {
				dos = new DataOutputStream(socket.getOutputStream());//여기에 세팅하고 
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				if(dos != null) {
					dos.writeUTF(name);
				}
				
				while(dos != null) {
					dos.writeUTF(scan.nextLine());
				}
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	//수신용 Thread
	class ClientReceiver extends Thread{
		Socket socket;
		DataInputStream dis;
		
		public ClientReceiver(Socket socket) {
			this.socket = socket;
			
			try {
				dis = new DataInputStream(socket.getInputStream());
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			while(dis != null) {
				try {
					System.out.println(dis.readUTF());
				}catch(IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new MultichatClient_homework().startClient();
	}
}
