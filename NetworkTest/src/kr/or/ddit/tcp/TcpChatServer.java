package kr.or.ddit.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 서버 소켓을 만들고, 클라이언트가 접속하면 
 * 소켓을 만들어 데이터를 받는 스레드 클래스와 데이터를 보내는 스레드 클래스에
 * 이 소켓을 넘겨준다.
 * 
 * 서버소켓과 소켓이 필요!
 * @author 유은지
 *
 */
public class TcpChatServer {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		try {
			serverSocket = new ServerSocket(7777);//서버 소켓 만듦
			System.out.println("서버 준비 완료...");
			socket = serverSocket.accept();
			
			//객체 만들때 만든 소켓을 담음
			Sender sender = new Sender(socket);
			Receiver receiver = new Receiver(socket);
			
			sender.start();
			receiver.start();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}
