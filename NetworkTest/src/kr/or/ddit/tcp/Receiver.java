package kr.or.ddit.tcp;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 데이터를 가져오는 
 * @author 유은지
 *
 */
public class Receiver extends Thread {
	private DataInputStream dis;
	
	public Receiver(Socket socket) {
		try {
			dis = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		while(dis != null){//상대방이 보낸게 있을 때 까지 반복(당연시 not null) ==> 읽을 데이터가 있을 때 까지 스레드 block 상태
			try {
				System.out.println(dis.readUTF());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
