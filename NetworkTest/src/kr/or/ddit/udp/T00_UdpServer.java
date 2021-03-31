package kr.or.ddit.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * UDP : 비연결성. 신뢰성이 떨어짐
 * @author 유은지
 *
 */
public class T00_UdpServer {
	private DatagramSocket socket;
	
	//Udp할 때 DatagramSocket, DatagramPacket 이 있어야 함.
	public void start() throws IOException {
		
		socket = new DatagramSocket(8888);//포트 8888번을 사용하는 소켓을 생성한다.
		
		//패킷 송수신을 위한 객체변수 선언
		//실제 패킷에 담을 때 바이트 기반으로 담음. 모든 데이터 기반은 바이트 배열로 담아서 처리할 수 ㅇ있다.
		DatagramPacket inPacket, outPacket;
		
		//패킷에 담을 때 타입이 바이트 배열로 처리하기때문에 byte타입의 배열 변수 선언
		/**
		 * 패킷 수신용==> 1바이트인 이유? 
		 * 1바이트만 있어도 처리가 가능함 ==> 
		 * 이 예제에서는 상대방이 서버쪽에 현재시간을 보내주고 싶은상황임. 
		 * TCP는 커넥션으로 소켓ㅇ가지고 보내고 쏘면 되는데, 
		 * UDP는 연결기반이 아니라서 보내주는 사람이 포트번호를 알아야 보낼 수 있으므로, 
		 * 메시지를 1byte짜리 보내면 그것으로 port를 받아옴
		 * 
		 * 패킷 담을 준비 => 상대가 패킷을 보냄
		 * 패킷을 담음 => ip,port 번호 얻어냄
		 * => 바이트 계열로 변환 => 소켓에 패킷담아 상대에게 전송
		 */
		byte[] inMsg = new byte[1];	//패킷이 요구할 때 byte를 요구하기 때문에 1byte씩 읽어와야함.
		byte[] outMsg;				//패킷 송신용==>출력
		
		while(true) {
			//데이터를 수신하기 위한 패킷을 생성한다.
			inPacket = new DatagramPacket(inMsg, inMsg.length);
			
			System.out.println("패킷 수신 대기중...");
			
			//패킷을 통해 데이터를 수신(receive)한다.
			socket.receive(inPacket);//socket 블락 ==> 상대방이 패킷을 던져줄 때까지.
			
			System.out.println("패킷 수신 완료.");
			
			//수신한 패킷으로부터 client의 IP주소와 Port번호를 얻어온다.
			//이 정보를 바탕으로 현재시간을 쏴주기 위함
			InetAddress addr = inPacket.getAddress();
			int port = inPacket.getPort();
			
			//서버의 현재 시간을 시분초 형태([hh:mm:ss])로 반환한다.
			SimpleDateFormat sdf = new SimpleDateFormat("[hh:mm:ss]");
			String time = sdf.format(new Date());
			//시간 문자열을 byte배열로 반환한다.(보낼때는 바이트로 처리하기 때문에)
			outMsg = time.getBytes();//스트링 데이터를 바이트 계열로 (모든 데이터는 바이트로 바꿀 수 있다.)
			
			//패킷을 생성해서 client에게 전송(send)한다.
			outPacket = new DatagramPacket(outMsg, outMsg.length, addr, port);//바이트배열, 바이트 배열 사이즈, 상대방 ip주소, 포트번호 작성
			socket.send(outPacket);//패킷을 보내주는 소켓. 소켓에 패킷을 담아서 보냄. udp 이기때문에 상대방이 receive상태면 받을 수 있는것이고 그렇지 않으면 날리기만 한것이고 상대방은 받지 못함.
		}
	}
	public static void main(String[] args) throws IOException {
		new T00_UdpServer().start();
	}
}
