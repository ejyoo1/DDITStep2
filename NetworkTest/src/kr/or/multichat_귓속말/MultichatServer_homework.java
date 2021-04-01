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

public class MultichatServer_homework {
	Map<String, Socket> clients;
	
	//생성자
	public MultichatServer_homework() {
		clients = Collections.synchronizedMap(new HashMap<String, Socket>());
	}
	
	//서버시작
	public void startServer() {
		Socket socket = null;
		
		//서버소켓 가져옴
		try(ServerSocket serverSocket = new ServerSocket(7777)){
			System.out.println("서버가 시작되었습니다.");
			
			while(true) {
				socket = serverSocket.accept();
				
				System.out.println("[" + socket.getInetAddress() + " : " + socket.getPort() + "] 에서 접속하였습니다.");
				
				ServerReceiver receiver = new ServerReceiver(socket);
				receiver.start();
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
		Iterator<String> it = clients.keySet().iterator();
		while(it.hasNext()) {
			try {
				String name = it.next();
				
				DataOutputStream dos = new DataOutputStream(clients.get(name).getOutputStream()); 
				dos.writeUTF(msg);
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
		Iterator<String> it = clients.keySet().iterator();
		while(it.hasNext()) {
			try {
				String name = it.next(); 
				
				DataOutputStream dos = new DataOutputStream(clients.get(name).getOutputStream()); 
				dos.writeUTF("[" + from + "]" + msg);
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
			DataOutputStream dos = new DataOutputStream(clients.get(to).getOutputStream());//보낼사람의 소켓을 가져옴
			dos.writeUTF("[" + from + "님의 귓속말]" + msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	class ServerReceiver extends Thread {
		private Socket socket;
		private DataInputStream dis;
		private String name;
		
		public ServerReceiver(Socket socket) {
			this.socket = socket;
			try {
				//수신용
				dis = new DataInputStream(socket.getInputStream());
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				name = dis.readUTF();
				sendMessage("#" + name + "님이 입장했습니다."); 
				sendMessage("귓속말을 하려면 [\\w 닉네임 보낼메시지] 형식으로 입력하세요.");
				
				clients.put(name, socket);
				System.out.println("현재 서버 접속자 수는 " + clients.size() + "명 입니다.");
				while(dis != null) { 
					String userMessage = dis.readUTF();
					System.out.println("구분:"+userMessage.indexOf("\\w"));
					if(userMessage.indexOf("\\w")==0) {
						System.out.println("귓속말 실행");
						//귓속말 전용 데이터 분리
						String[] userMessages = userMessage.split(" ");
						int length = userMessages.length;
						System.out.println("귓속말 보낼 사람 : " + userMessages[1]);
						
						String str = "";
						for(int i = 2 ; i < length ; i++) {
							str += userMessages[i] + " ";
						}
						System.out.println("귓속말 내용 : " + str);
						
						sendMessage(str, name, userMessages[1]);
					}else {
						sendMessage(userMessage, name); 
					}
				}
			}catch(IOException ex) {
				ex.printStackTrace();
			}finally {
				sendMessage(name+"님이 나가셨습니다.");
				clients.remove(name);
				System.out.println("[" + socket.getInetAddress() + " : " + socket.getPort() + "] 에서 접속을 종료했습니다.");
				System.out.println("현재 접속자 수는 " + clients.size() + "명 입니다.");
			}
		}
	}
	
	public static void main(String[] args) {
		new MultichatServer_homework().startServer();
	}
}
