package kr.or.ddit.tcp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * 소켓을 통해서 메시지를 보내는 역할을 담당한다. => 쓰레드처리
 * @author 유은지
 *
 */
public class Sender extends Thread{
	private Scanner scan;
	private String name;//id 정보를 담기 위한 변수
	private DataOutputStream dos;//문자열을 UTF8로 보내기 위한 보조스트림
	
	public Sender(Socket socket) {//생성자를 호출하는 시점에 변수 값 세팅함.(소켓의 파라미터로 받아서)
		name = "[" + socket.getInetAddress() + " : " + socket.getLocalPort() + "]";//ip주소, port번호 얻어옴(name 역할로 저장)
		scan = new Scanner(System.in);
		
		try {
			dos = new DataOutputStream(socket.getOutputStream());//보조스트릠 soket안의 outputStream을 넣어서 보조스트림 객체 생성
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void run() {//프로그램 시작
		while(dos != null) {//만들었다고 가정하에 null이 아닐때까지 돌림(데이터가 있을 때 까지) ==> 객체를 만들었으니 당연히 null일 때까지
			try {
				dos.writeUTF(name+ " >>> " + scan.nextLine()); //writeUTF를 함. (Enter를 치는 시점에 writeUTF가 실행됨) ==> 채팅하고 엔터치면 한줄 한줄 씩 보내짐.
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
