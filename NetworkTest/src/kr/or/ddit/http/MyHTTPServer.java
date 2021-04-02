package kr.or.ddit.http;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.util.StringTokenizer;

/**
 * 간단한 웹 서버 예제
 * 사용자가 요청하면 요청한 정보를 파싱해서 
 * 얘가 원하는 페이지가 이런거구나 하고 
 * 리스폰스 메시지를 규격에 맞추어 
 * 상대방 소켓으로 보내주는 기능
 * 
 * @author 유은지
 * HTTP 요청(응답) 메시지 구조 : RequestLine/StatusLine Header EmptyLine Body
 * - RequestLine : GET /restapi/v1.0 HTTP/1.1 [메소드 /경로 버전] ==> [전송방식 /접근할리소스위치 HTTP버전]
 * - Status Line : HTTP/1.1 200 OK (200 : HTTP 상태코드)
 * - Header : 헤더 정보를 의미함(몇줄 들어갈지는 모름) response 시 contentType을 보냄(마인타임-text인지 html인지 json인지)
 * - Empty Line : 공백(빈줄)을 의미하고, Header Body를 분리해주는 역할을 함(HTTP 프로토콜이 요구하는 양식이기에 지켜야 함.)
 * - Body : 보내거나 받고자 하는 실제 데이터를 의미함 (메서드가 Get등의 경우에는 요청시 Body부분 생략 가능함. ==> 헤더에이미 데이터가 존재해서)
 * OSI 7계층은 분리된것이 아니라 아래에서 위로 갔을 때 위에 있는 계층은 아래있는 계층을 모두 포함하는 개념이다.
 * 따라서 HTTP프로토콜이 속한 계층은 TCP 즉 전송계층보다 위에있으므로
 * HTTP TCP라고 할 수 있다. 
 * 
 * HTTP 프로토콜로 응답데이터를 파싱해서 CSS,JavaScript 웹 애플리케이션을 실행하기 위해 양식은 지켜야함.
 */
public class MyHTTPServer {
	private final int port = 80;
	private final String encoding = "UTF-8";
	
	/**
	 * 응답 헤더 생성하기
	 * @param contentLength 응답내용 크기
	 * @param mimeType 마임타입 (ex: text/html)
	 * @return 바이트 배열
	 */
	private byte[] makeResponseHeader(int contentLength, String mimeType) {
		String header = "HTTP/1.1 200 OK\r\n" //statusLine \r\n은 운영체제별로 한칸 내리기위해 다음과 같이 사용함.
				+ "Server: MyHTTPServer 1.0\r\n"//header정보(우리 1.0 서버에서 돌아가고 있어)
				+ "Content-length: " + contentLength + "\r\n"//바디 사이즈는 이러해..
				+ "Content-type: " + mimeType + "; charset="//마임타입은 이러하고, 인코딩 타입은 이러해.
				+ this.encoding + "\r\n\r\n";//empty Line
		
		return header.getBytes();
	}
	
	/**
	 * 응답 내용 생성하기
	 * @param filePath 응답으로 사용할 파일 경로
	 * @return 바이트 배열 데이터
	 * @throws IOException
	 */
	private byte[] makeResponseBody(String filePath) throws IOException {
		FileInputStream fis = null;
		byte[] data = null;
		try {
			File file = new File(filePath);
			data = new byte[(int)file.length()];//필요한 바이트 배열 수를 파일 길이만큼 설정
			
			fis = new FileInputStream(file);
			fis.read(data);//바이트 배열로 파일을 읽어옴
		}finally {
			if(fis != null) {
				fis.close();
			}
		}
		return data;//바이트 배열 리턴
	}
	
	/**
	 * 에러페이지 생성
	 * @param out
	 * @param statusCode
	 * @param errMsg
	 */
	private void makeErrorPage(OutputStream out, int statusCode, String errMsg) {
		String statusLine = "HTTP/1.1" + " " + statusCode//statusLine statusCode(200:정상,???:비정상)
				+ " " + errMsg;
		try {
			out.write(statusLine.getBytes()); //바이트 배열로 변환
			out.flush();
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * HTTP 서버 시작
	 */
	public void startServer() {
		
		try(ServerSocket server = new ServerSocket(this.port)){
			while(true) {
				try {
					Socket socket = server.accept();//요청 올때마다 소켓 새로 생성(멀티챗과 유사) //block -> 요청옴 -> 소켓생성
					//HTTP 요청처리를 위한 스레드 객체 생성
					HttpHandler handler = new HttpHandler(socket);//소켓 넣어 쓰레드 만듬
					new Thread(handler).start();//start 실행 다시 위로가서 block
					
				}catch(IOException ex) {
					System.out.println("커넥션 오류 !!!");
					ex.printStackTrace();
				}catch(RuntimeException ex) {
					System.out.println("알수없는 오류 !!!");
					ex.printStackTrace();
				}
			}
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}//close startServer()
	
	/**
	 * HTTP 요청 처리를 위한 Runnable 객체
	 * @author 유은지
	 *
	 */
	private class HttpHandler implements Runnable{//핵심 기능
		private final Socket socket;
		
		public HttpHandler(Socket socket) {//소켓을 받아서 멤버변수에 세팅
			this.socket = socket;
		}
		
		@Override
		public void run() {//시작
			OutputStream out = null;
			BufferedReader br = null;
			
			try {
				out = 
						new BufferedOutputStream(//output용
								socket.getOutputStream());
				br = 
						new BufferedReader(//문자 처리하기 위해 Reader로 사용함. (http파일을 읽을 때, 그것을 사용자에게 보내주는데(원래는 웹서버가 현재 예제는 웹서버 흉내) / http 파일은 텍스트 파일이라서 텍스트를 읽어와야 하기 떄문에 바이너리 데이터 처리 스트림이 아닌 문자 스트림을 사용한것임.)
								new InputStreamReader(
										socket.getInputStream()));
				
				//요청 헤더 정보 파싱하기(requestline, header 영역 읽는 중)
//				(GET /restapi/v1.0 HTTP/1.1 헤더정보 EmptyLine Body)
				StringBuilder request = new StringBuilder();
				while(true) {
					String str = br.readLine();//소켓정보 한줄한줄씩 읽어와서(계속 읽음)
					
					if(str.equals("")) break; //empyLine체크(empty라인 도달 시 읽는것을 멈춤)(GET /restapi/v1.0 HTTP/1.1 헤더정보 )
					
					request.append(str + "\n");//읽은거 append
				}
				
				System.out.println("요청헤더 : \n" 
						+ request.toString());//그냥 찍음
				
				String reqPath = "";
				
				//요청페이지 정보 가져오기GET /restapi/v1.0 HTTP/1.1 [함수 /경로 프로토콜버전]
				StringTokenizer st = 
						new StringTokenizer(request.toString());//디폴트로 공백 기준으로 나뉘어짐.
				while(st.hasMoreTokens()) {//읽을게 있으면
					String token = st.nextToken();//가져왕
					if(token.startsWith("/")) {//공백기준으로 나뉘어진 텍스트 중 /로 시작하는 것 가져옴(경로 추출)
						reqPath = token;
					}
				}
				
				//상대경로(프로젝트 폴더 기준) 설정
				String fileName = "./WebContent" + reqPath;//파일경로 세팅(여기에 저장할거임)
				
				//파일 이름을 이용하여 Content-type 정보 추출하기
				String contentType = URLConnection//contentType 헤더 중요. 이것을 가지고 브라우저가 바일 타입에 대한 파싱여부를 판단하여 올바른 작업을 수행한다.
						.getFileNameMap()
						.getContentTypeFor(fileName);//jpg 파일인지 html 파일인지 mime type 규격에 맞추어 리턴됨.
				System.out.println("contentType => " + contentType);//마임타입으로 변환된 컨텐트 타입 찍어 정상여부 확인하기.
				
				File file = new File(fileName);//고객이 요청한 정보를 파일객체로 만들어서
				if(!file.exists()) {//파일이 없으면
					makeErrorPage(out, 404, "Not Found");//에러페이지로 전송
					return;
				}
				//읽어서 응답메시지 만들어 상대에게 보내자
				
				byte[] body = makeResponseBody(fileName);//파일을 읽어서 바이트배열로 만드는 작업
				byte[] header = 
						makeResponseHeader(body.length, contentType);//파라미터 넣어준 바디 길이(emptyLIne 다음 바디이기 때문에 길이가 어느정도인지 알면 좋아서 파라미터로 넘겨줌), 마임타입 파라미터로 넣어줘서 헤더 만들때 헤더정보로 사용할 수 있도록 넣어줌
				
				
				//보내자
				//요청 헤더가 HTTP/1.0이나 그 이후의 버전을 지원할 경우 MIME 헤더를 전송한다.
				if(request.toString().indexOf("HTTP/") != -1) {//HTTP/ ==> 1.0 이나 이후의 버전은 이것이 존재함. 프로토콜이 우리랑 주고받기에 문제없는 프로토콜이구나 라는 것을 앎.
					out.write(header);// 응답헤더 보내기(헤더정보를 보냈음)
				}
				
				System.out.println("응답헤더 : \n" + new String(header));//응답헤더로 뭘 보냈는지 함 찍어봄(status code + header + emptyLine)
				
				out.write(body); //응답 내용 보내기//(body 만)
				out.flush();//혹시나 남아있는거 있으면 다 보내라고
				
			}catch(IOException ex) {
				System.out.println("입출력 오류!!!");
				ex.printStackTrace();
			}finally {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}//close HttpHandler class

	public static void main(String[] args) {
		new MyHTTPServer().startServer();
	}//close main
}
