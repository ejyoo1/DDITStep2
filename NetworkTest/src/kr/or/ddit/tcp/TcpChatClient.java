package kr.or.ddit.tcp;

import java.io.IOException;
import java.net.Socket;

public class TcpChatClient {
	public static void main(String[] args) {
		Socket socket = null;
		try {
			socket = new Socket("localhost", 7777);
			
			Sender sender = new Sender(socket);
			Receiver receiver = new Receiver(socket);
			
			sender.start();
			receiver.start();
			
			//메인 쓰레드는 죽고 나머지 쓰레드만 작업 수행함
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}
