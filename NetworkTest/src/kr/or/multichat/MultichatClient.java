package kr.or.multichat;

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
public class MultichatClient {
	Scanner scan = new Scanner(System.in);
	private String nickName;//대화명
	
	//프로그램 시작
	public void startClient() {
		//대화명 입력받기(Server에서 클라이언트가 보낸 대화명 먼저 찍기때문)
		System.out.print("대화명>>");
		nickName = scan.next();
		
		//소켓접속 시작
		Socket socket = null;
		
		try {
			//여기서 뉴하면 서버쪽에서 소켓을 받게됨.
			//서버로 송신 또는 수신용 스레드가 있어야함 ==> 클래스 필요!
			socket = new Socket("192.168.43.174", 7777);
			
			System.out.println("서버에 연결되었습니다.");
			//송신용 스레드 생성(클라이언트가 입력한거 보내는것)
			ClientSender sender = new ClientSender(socket, nickName);
			//수신용 스레드 생성(서버가 보내는 메시지를 받는 부분)
			ClientReceiver receiver = new ClientReceiver(socket);
			
			sender.start();
			receiver.start();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	//메시지를 전송하는 스레드 ==> 내부클래스 정의
	
	class ClientSender extends Thread {
		Socket socket;
		DataOutputStream dos;
		String name;
		Scanner scan = new Scanner(System.in);
		
		//소켓을 받아서 이름을 저장한뒤
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
				//시작하자 마자 자신의 대화명을 서버로 전송
				if(dos != null) {//당연히 true
					dos.writeUTF(name);//writeUTF로 이름 설정
				}
				
				while(dos != null) {//당연히 true 
					//키보드를 입력받은 메시지를 서버로 전송
					dos.writeUTF(scan.nextLine());//클라이언트가 입력하면 writeUTF로 날림
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
					//서버로부터 수신한 메시지 출력하기
					System.out.println(dis.readUTF());//읽을 데이터가 있으면 읽어옴
				}catch(IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new MultichatClient().startClient();
	}
}
