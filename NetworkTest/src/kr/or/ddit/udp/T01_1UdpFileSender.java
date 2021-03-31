package kr.or.ddit.udp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * datagramSocket, datagramPaket 필요
 * @author 유은지
 *
 */
public class T01_1UdpFileSender {
	public static final int DEFAULT_BUFFER_SIZE = 1000;
	
	public static void main(String[] args) {
		String serverIp = "127.0.0.1";
		int port = 8888;
		
		File file = new File("d:/C_Lib/lion.png");//전송할 파일 정의
		
		DatagramSocket ds = null;//소켓 변수 선언
		if(!file.exists()) {//파일 없으면 종료
			System.out.println("파일이 존재하지 않습니다.");
			System.exit(0);
		}
		
		//파일에 관련된 정보 얻어옴
		long fileSize = file.length();//파일 사이즈 가져옴
		long totalReadBytes = 0;//전체 파일 사이즈(얼마나 읽었나 확인하기 위해)
		double startTime = 0;//얼마나 시간이 걸렸는지 확인하기 위해
		//아래부터는 패킷담아 전송하는 부분
		try {
			ds = new DatagramSocket();//객체 생성 
			InetAddress serverAddr = InetAddress.getByName(serverIp);//객체 얻음(데이타그램 소켓이 요구하는 객체임)
			startTime = System.currentTimeMillis();//현재 시간을 가져옴
			String str = "start"; //전송시작 알림 ==> 이 문자를 getbyte로 바꿔서 넣음
			DatagramPacket dp = new DatagramPacket(str.getBytes(), str.getBytes().length, serverAddr, port);//여기에서 byte배열로 변환해서 넣음(바이트배열,사이즈(몇바이트보내는지),패킷을 날릴 상대방ip주소,port) ==> 보낼 상대방쪽 (서버라는 용어는 약간 모호함)
			ds.send(dp);//실질적으로 보내는 객체(누군가는 의식을 하고있어야 한다고 가정해야됨 ==> 그게 없으면 그냥 날리는 것이 됨.)(start파일 전송하기 시작하겠구나라고 인식)
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			
			//총 파일 사이즈 정보를 알려줌
			str = String.valueOf(fileSize);//내가 사이즈를 얼마나 보내겠다 라고 알려주기위해
			dp = new DatagramPacket(str.getBytes(), str.getBytes().length, serverAddr, port);//사이즈 정보 전송
			ds.send(dp);//보내기 시작
			
			//보내기 시작(파일 다 보낼때까지 무한 반복!)
			while(true) {
				try {
					Thread.sleep(10); //패킷전송간의 간격을 주기 위해서...(쉬었다가 보낼랬다가 하려고)
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int readBytes = fis.read(buffer, 0, buffer.length);//버퍼 사이즈 만큼 읽고 있는 중
				if(readBytes == -1) {//fis의 buffer가 10000바이트 씩 읽다가 더이상 읽을 게 없을 때
					break;//무한루프를 벗어남
				}
				
				//읽은게 -1이 ㅇ아니면 ==>읽은게 있으면
				//읽어온 파일 내용 패킷에 담기
				dp = new DatagramPacket(buffer, readBytes, serverAddr, port);//읽은 것이 10000바이트가 아닐 수 있기 때문에, 두번째 파라미터로 읽은 바이트 수를 파라미터로 넘겨줌
				
				ds.send(dp);//보냄
				totalReadBytes += readBytes;//센드하자마자 읽은 바이트 수를 누적시킴
				System.out.println("In progress : " //현재 진행 상황을 출력함 (무한루프로 인해서 읽을때마다 계속 나올것임)
						+ totalReadBytes + "/" 
						+ fileSize + " Bytes ("
						+ (totalReadBytes * 100 / fileSize)
						+ "%)");
			}
			
			//파일을 다 보냈을 때 아래 작업 시작
			//시간 계산
			double endTime = System.currentTimeMillis();
			double diffTime = (endTime - startTime) / 1000;
			double transferSpeed = (fileSize/1000) / diffTime;
			
			//얼마큼 걸렸는지 찍음
			System.out.println("걸린 시간 : " + diffTime + " second(s)");
			System.out.println("평균 전송 속도 : " + transferSpeed + " KB/s");
			
			/* 파일 사이즈로 Receiver로 체크 가능하므로 아래 코드는 무의미해서 주석처리함.
			str = "end"; //전송이 완료되었음을 알림
			dp = new DatagramPacket(str.getBytes(), str.getBytes().length, serverAddr, port);//패킷에 담아서
			ds.send(dp);//상대에게 쏴줌(상대는 end가 오면 다 보냇구나 하고 앎)
			System.out.println("전송완료...");//끝~ 파일 전송하는 놈이 하는 일.
			*/
			
		}catch(IOException ex){
			ex.printStackTrace();
		}finally {
			
		}
	}
}
