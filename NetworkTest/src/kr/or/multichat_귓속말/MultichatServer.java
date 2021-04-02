package kr.or.multichat_귓속말;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MultichatServer {
	//대화명, 클라이언트의 socket을 저장하기 위한 Map변수 선언
	Map<String, Socket> clients;//클라이언트를 담을 맵 객체 변수 생성
	
	//생성자
	public MultichatServer() {
		//동기화 처리가 가능하도록 Map 객체 생성 (여러개의 클라이언트가 동시에 접근할 수 있기에 동기화 처리 추가)
		clients = Collections.synchronizedMap(new HashMap<String, Socket>());
	}
	
	//서버시작
	public void startServer() {
		Socket socket = null;
		
		//서버소켓 가져옴
		try(ServerSocket serverSocket = new ServerSocket(7777)){//이 문장을 사용하면 알아서 close를 해준다. (try with resource문법)//서버소켓은 소스코드상 총 1개만 있음
			System.out.println("서버가 시작되었습니다.");
			
			while(true) {
				//클라이언트의 접속을 대기한다.(클라이언트 접속이 여러명일 것이니, 접속을 받아들일 소켓을 계속 반복해야됨.)
				socket = serverSocket.accept();//사용자의 요청이 오면 소켓을 만들어주는 역할(server block, 클라이언트가 서버쪽으로 소켓정보를 보내면 클라이언트 block되고 둘만의 소켓이 생성되면 block이 풀린다.
				//소켓은 서버소켓과 클라이언트 소켓(클라이언트에서 new 해서 뭔가 한것) 한 세트로 이루어짐.
				
				System.out.println("[" + socket.getInetAddress() + " : " + socket.getPort() + "] 에서 접속하였습니다.");
				//socket.getPort()는 7777이 아닐 수 있음. 실행할 때마다 56908, 57472, 57479 이렇게 다르게 나왔음.
				//메시지를 전송 처리하는 스레드 생성 및 실행
				ServerReceiver receiver = new ServerReceiver(socket);//내부클래스 파라미터로 넣고(이것은 들어온 사람만큼 생성됨) 그래야 사람수만큼 채팅을 보낼 수 있으므로.
				receiver.start();//시작한뒤 위의 accept() 으로 인해 대기 (이것 반복)
			}
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 대화방 즉, Map에 저장된 전체 유저에게 안내 메시지를 전송하는 메서드(입장, 퇴장)
	 * @param msg : name, socket 이 포함된 메시지 또는 사용자가 보낸 메시지
	 */
	public void sendMessage(String msg) {
		//Map에 저장된 유저의 대화명 리스트 추출(key값 구하기)
		Iterator<String> it = clients.keySet().iterator();
		while(it.hasNext()) {
			try {
				String name = it.next(); //대화명(키를 꺼내옴)
				
				//대화명에 해당하는 Socket의 OutputStream 구하기
				DataOutputStream dos = new DataOutputStream(clients.get(name).getOutputStream()); //이름을 사용하여 소켓꺼내와서 그 소켓에 outputStream으로 뽑아서 DataOutputStream의 기반스트림으로 사용한다. 문자 쉽게 전송하고 싶어서 보조스트림을 사용
				dos.writeUTF(msg);//메시지 보내기(모든 소켓 수만큼)
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * 대화방 즉, Map에 저장된 전체 유저에게 안내메시지를 전송하는 메서드(클라이언트의 채팅)
	 * @param msg 보낸 메시지
	 * @param from : 보낸사람
	 */
	public void sendMessage(String msg, String from) {
		//Map에 저장된 유저의 대화명 리스트 추출(key값 구하기)
		Iterator<String> it = clients.keySet().iterator();
		while(it.hasNext()) {
			try {
				String name = it.next(); //대화명(키를 꺼내옴)
				
				//대화명에 해당하는 Socket의 OutputStream 구하기
				DataOutputStream dos = new DataOutputStream(clients.get(name).getOutputStream()); //이름을 사용하여 소켓꺼내와서 그 소켓에 outputStream으로 뽑아서 DataOutputStream의 기반스트림으로 사용한다. 문자 쉽게 전송하고 싶어서 보조스트림을 사용
				dos.writeUTF("[" + from + "]" + msg);//메시지 보내기(모든 소켓 수만큼)
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * 대화방에서 Map에 저장된 유저 중 특정 유저에게 귓속말을 보내는 메서드
	 * @author 유은지
	 * @param from : 보낸사람
	 * @param to : 받는사람
	 */
	public void sendMessage(String msg, String from, String to) {
		try {
			//가져올 소켓이 없는 사람일 때, 처리가 누락됨.
			DataOutputStream dos = new DataOutputStream(clients.get(to).getOutputStream());//보낼사람의 소켓을 가져옴
			dos.writeUTF("[" + from + "님의 귓속말]" + msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//서버에서 클라이언트로 메시지를 전송할 스레드를 Inner클래스로 정의(이곳에서만 사용하기에 내부클레스로 구현함)
	//====> Inner클래스 에서는 부모 클래스의 멤버들을 직접 사용할 수 있다.
	//====> 내부클래스 : 클래스 안에있는것.
	//====> 내부클래스 장점 : 내부에서 나만 쓰는 것들을 굳이 외부 public으로 만들지 않고도 사용하기 위함.
	//====> 부모 클래스의 멤버(변수, 메서드 등) 맘대로 사용 가능. ===> 전역변수 처럼 사용
	
	//ServerReceiver를 쓰레드로 만든 이유 : 서버와 클라이언트를 통신하려면 쓰레드가 클라이언트가 나갈때 까지 대기해야 된다. 
	class ServerReceiver extends Thread {
		private Socket socket;
		private DataInputStream dis;
		private String name;
		
		public ServerReceiver(Socket socket) {//소켓넣어 쓰레드로 만듬.
			this.socket = socket;//소켓을 멤버변수로 보관
			try {
				//수신용
				dis = new DataInputStream(socket.getInputStream());//UTF8 방식으로 읽기위해 DataInputStream 을 만들고 기반스트림을 소켓에서 꺼내옴
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				//서버에서는 클라이언트가 보내는 최초의 메시지 즉, 대화명을 수신해야 한다.
				name = dis.readUTF();//클라이언트를 시작하면 무조건 name 값을 가져올것이다 (약속!)
				//대화명을 받아서 다른 모든 클라이언트에게 대화방 참여메시지를 보낸다.
				sendMessage("#" + name + "님이 입장했습니다.");//접속되어있는 모든 사람들에게(Map<String,Socket>) str 값을 뿌리기 위함.(모든사람들에게 알림 전송) 
				sendMessage("귓속말을 하려면 [\\w 닉네임 보낼메시지] 형식으로 입력하세요.");
				
				//대화명과 소켓정보를 Map에 저장한다.
				clients.put(name, socket);//접속된 사람이므로 맵에 put 으로 등록 (K : name , V : socket)
				System.out.println("현재 서버 접속자 수는 " + clients.size() + "명 입니다.");
				//이 이후의 메시지 처리는 반복문으로 처리한다.
				//한 클라이언트가 보낸 메시지를 다른 모든 클라이언트에게 보내준다.
				while(dis != null) {//당연히 nll이 아님 (이렇게 한 이유는 클라이언트 소켓이 데이터를 보내면 서버에 보내야 하므로 대기해야함.) 
					String userMessage = dis.readUTF();
					System.out.println("구분:"+userMessage.indexOf("\\w"));
					if(userMessage.indexOf("\\w")==0) {
						System.out.println("귓속말 실행");
						//귓속말 전용 데이터 분리
						String[] userMessages = userMessage.split(" ");//split 시 횟수 조절 가능 split(" ",3);
						int length = userMessages.length;
						System.out.println("귓속말 보낼 사람 : " + userMessages[1]);
						//여기에 귓속말 보낼 사람이 유효한지 검사가 필요함(현재 미구현)
						
						String str = "";
						for(int i = 2 ; i < length ; i++) {
							str += userMessages[i] + " ";
						}
						System.out.println("귓속말 내용 : " + str);
						
						sendMessage(str, name, userMessages[1]);
					}else {
						sendMessage(userMessage, name); //접속한 사람이 메시지를 보냈다면 sendMessage에서 뿌려줌
					}
				}
			}catch(IOException ex) {
				ex.printStackTrace();
			}finally {
				//이 finally영역이 실행된다는 것은 클라이언트의 접속이 종료되었다는 의미이다.
				sendMessage(name+"님이 나가셨습니다.");
				//Map에서 해당 대화명을 삭제한다.
				clients.remove(name);
				System.out.println("[" + socket.getInetAddress() + " : " + socket.getPort() + "] 에서 접속을 종료했습니다.");
				System.out.println("현재 접속자 수는 " + clients.size() + "명 입니다.");
			}
		}
	}
	
	public static void main(String[] args) {
		new MultichatServer().startServer();
	}
}
