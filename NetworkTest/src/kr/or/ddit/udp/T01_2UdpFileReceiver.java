package kr.or.ddit.udp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * sender가 보내는 것 저장
 * @author 유은지
 *
 */
public class T01_2UdpFileReceiver {
	public static final int DEFAULT_BUFFER_SIZE = 1000;
	
	public static void main(String[] args) throws IOException {
		int port = 8888;
		long fileSize = 0;
		long totalReadBytes = 0;
		
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		
		int readBytes = 0;
		System.out.println("파일 수신 대기중...");
		
		DatagramSocket ds = new DatagramSocket(port);
		FileOutputStream fos = null;	
		fos = new FileOutputStream("d:/D_Other/ejyooFile.jpg");
		
		DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
		ds.receive(dp);//block되다가 Sender가 Start를 보냄 (byte배열로.)
		
		String str = new String(dp.getData()).trim();//바이프 배열로 얻어지고 그것을 String 객체로 만듬
		
		if(str.equals("start")) {//sender에서 전송을 시작한 경우..//start가 왔다면 정상적으로 보낼라는 갑다.
			dp = new DatagramPacket(buffer, buffer.length);
			ds.receive(dp);//아무이유 없이 대기하다가 받아오면 파일 사이즈를 가져옴.
			str = new String(dp.getData()).trim();//String 으로 변환하고
			fileSize = Long.parseLong(str);//파일사이즈를 long타입으로 변환함. ==>여기까지 파일 사이즈 가져옴
			
			double startTime = System.currentTimeMillis();
			
			while(true) {//무한루프
				ds.receive(dp);//block => 실제 파일을 가져옴
				str = new String(dp.getData()).trim();
				readBytes += dp.getLength();//읽어온 패킷 사이즈 저장하고
				fos.write(dp.getData(), 0, readBytes);//파일 입력 (읽어온바이트배열, 0부터, 읽은 사이즈)수만큼 입력해라.
				
				totalReadBytes += readBytes;//얼만큼 읽었는지 바이트 수 누적중
						
				System.out.println("In progress : " //현재 상태 찍어줌
						+ totalReadBytes + "/"
						+ fileSize + " Bytes ("
						+ (totalReadBytes * 100 / fileSize) 
						+ "%)");
				
				if(totalReadBytes >= fileSize) {//체크 토탈바이트가 fileSize랑 같거나 크면 다 읽은 것이므로
					break;//무한루프를 빠져나감
				}//그게 아니면 계속 무한루프를 돌게됨.
			}
			
			double endTime = System.currentTimeMillis();
			double diffTime = (endTime - startTime) / 1000;
			double transferSpeed = (fileSize / 1000) / diffTime;
			
			System.out.println("걸린 시간 : " + diffTime + " second(s)");
			System.out.println("평균전송속도 : " + transferSpeed + " KB/s");
			System.out.println("수신 처리 완료...");
			
			fos.close();
			ds.close();
		}else {
			System.out.println("수신처리 불가!!!");
			fos.close();
			ds.close();
		}
	}
}
