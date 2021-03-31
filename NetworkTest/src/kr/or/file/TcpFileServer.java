package kr.or.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 서버는 클라이언트가 접속하면 서버 컴퓨터의 D:\D_Other\폴더에있는 Tulips.jpa파일을 클라이언트로 전송한다.
 * @author 유은지
 * 바이너리 데이터를 전송
 * 선생님에게 질문함 : 
 * Q. 만약에 파일을 전송하는 기능을 만든다고 했을 때 서버 소켓이 별도로 필요한가?
 * A. 웹쪽에서 파일 업로드 기능 수행시 그때 소켓을 만들어서 뿌려주면 된다. 
 */
public class TcpFileServer {
	//서버에있는 특정 파일을 읽어서 바이트로 가지고있어서 소켓이 연결되면 전송
	private ServerSocket serverSocket;
	private Socket socket;//클라이언트와 통신하기 위한 소켓 담기용
	private OutputStream out;//소켓안에서 꺼내올때 파일을 담기위해 선언
	private FileInputStream fis;//내보낼 파일 정보를 읽어오려고
//	private File file = new File();
	
	public void startServer() {
		while(true) {//무한. (상대방에게 쏴주고 또 대기하게됨) 요청오는 족족 소켓에다가 write해주는 기능
			try {
				//파일을 읽어서 소켓에 쏴줌
				serverSocket = new ServerSocket(7777);
				System.out.println("서버 준비 완료...");
				
				socket = serverSocket.accept();
				System.out.println("파일 전송 시작...");
				
				fis = new FileInputStream("d:/C_Lib/lion.png");
				out = socket.getOutputStream();
				
				//한꺼번에 읽어와 전송할 데이터 저장 변수 선언
				byte[] tmp = new byte[1024]; 
				int c = 0;
				while((c = fis.read(tmp)) != -1) {
					out.write(tmp,0,c);
				}
				out.flush();
				
				System.out.println("파일 전송 완료...");
				
			}catch(IOException ex) {
				ex.printStackTrace();
			}finally {//원래 이것이 안전한것임.(정석) => 모두 close(socket,jdbc,thread같은것들 닫아주기)
				if(fis != null) {
					try{fis.close();}catch(IOException ex) {}
				}
				if(out != null) {
					try{out.close();}catch(IOException ex) {}
				}
				if(socket != null) {
					try{socket.close();}catch(IOException ex) {}
				}
				if(serverSocket != null) {
					try{serverSocket.close();}catch(IOException ex) {}
				}
			}
		}
		
	}
	public static void main(String[] args) {
		new TcpFileServer().startServer();
	}
	
	
}
