package kr.or.ddit.tcp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 소켓을 만들어서 클라이언트의 요청을 기다리는 클래스
 * @author PC-19
 * 소켓 통신 할 때, IP주소와 포트정보가 필요하다.
 * 포트는 프로그램 식별을 위해 존재하는 것으로. IP주소만 있다고 해서 원하는 프로그램과 통신할 수 있는것은 아니다.
 * 포트가 있어야 원하는 프로그램과 통신할 수 있는것이다.
 */
public class TcpServer {
	public static void main(String[] args) throws IOException {
		//TCP 소켓 통신을 하기 위해 ServerSocket 객체 생성
		ServerSocket server = new ServerSocket(7777);
		System.out.println("서버가 접속을 기다립니다.");
		
		//accept()메서드는 client에서 연결 요청이 올 때 까지 계속 기다린다.
		//연결 요청이 오면 Socket객체를 생성해서 Client의 Socket과 연결한다.
		Socket socket = server.accept();//accept() : 클라이언트의 서버 요청을 받아들이는 곳... ==> blocking됨. 상대방이 이쪽 으로 소켓이 만나서 하나의 소켓이 될 때까지 기다렸다가 통신 소켓이 만들어지면 리턴
		
		//상대방으로부터 메시지를 받고 상대방과 통신할 소켓이 만들어진 후다음작업이 진행된다.
		//이 이후는 클라이언트와 연결된 후의 작업을 진행하면 된다.
		//데이터 주고받을때 스트림을 사용한다.
		System.out.println("접속한 클라이언트 정보");
		System.out.println("주소 : " + socket.getInetAddress());
		
		//client에 메시지 보내기
		//OutputStream  객체를 구성하여 전송한다.
		//접속한 Socket의 getOutputStream()메서드를 이용하여 구한다.
		OutputStream out = socket.getOutputStream();//소켓을 가져옴. 상대방에게 보내고 싶을 때 output 상대방이 보낸 데이터를 가져오려면 inputstream 을 사용하여 read를 사용한다.
		
		DataOutputStream dos = new DataOutputStream(out);//byte, int, float 등 맞는 형태로 처리하기 위해 보조스트림을 사용
		dos.writeUTF("어서오세요 ...");//메시지 보내기 (문자열을 UTF8로 하려고)
		System.out.println("메시지를 보냈습니다.");
		
		dos.close();
		server.close();
	}
}
