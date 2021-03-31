package kr.or.ddit.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class T00_UdpClient {
	public void start() throws IOException {
		DatagramSocket datagramSocket = new DatagramSocket();
		InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
		
		//데이터가 저장될 공간으로 byte배열을 생성한다.(패킷 수신용)
		byte[] msg = new byte[100];//패킷이라는 객체를 만들때, msg에서 요구하는 것이 바이트 배열이다.
		//전송하는 모든것은 바이트 배열로 만들어주어야함. 그래서 바이트 배열을 먼저 선언하는 것임.
		
		//msg는 1바이트만 담겠다.패킷안에는 내정보와 상대방의 패킷 포트 정보도 들어가야하기 때문에 아무 의미없는 1바이트를 일단 보내고난 뒤 정보를 얻어온다.)
		DatagramPacket outPacket = new DatagramPacket(msg, 1, serverAddress, 8888);//보낼때 사용할 데이터 패킷 객체
		
		DatagramPacket inPacket = new DatagramPacket(msg, msg.length);
		
		datagramSocket.send(outPacket);//전송//서버에게 전송
		datagramSocket.receive(inPacket);//수신(서버의 응답을 받기 위해 기다림)
		System.out.println("현재 서버 시간 => " + new String(inPacket.getData()));//서버가 쏴준것을 작업함.(꺼낼때도 바이트 배열이기 때문에 바이트배열을 스트링객체로 만들음)
		
		datagramSocket.close();//소켓종료
	}
	
	public static void main(String[] args) throws IOException {
		new T00_UdpClient().start();
	}
}
