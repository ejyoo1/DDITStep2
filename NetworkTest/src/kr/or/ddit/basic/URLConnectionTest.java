package kr.or.ddit.basic;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class URLConnectionTest {
	public static void main(String[] args) throws IOException {
		//URLConnection => 애플리케이션(java)과 URL간의 통신연결을 위한 추상클래스
		
		//특정 서버(예:naver)의 정보와 파일내용을 출력하기
		URL url = new URL("https://www.naver.com/index.html");                                                                                                                                                                                                                                                                                                                                                                                  
		
		//Header 정보 가져오기
		URLConnection urlCon = url.openConnection();//연결
		System.out.println("Content-Type : " + urlCon.getContentType());
		
		System.out.println("Encoding : " + urlCon.getContentEncoding());//인코딩 정보
		System.out.println("Content : " + urlCon.getContent());
		System.out.println();
		
		//전체 Header 정보 출력하기
		Map<String, List<String>> headerMap = urlCon.getHeaderFields();//응답된 헤더 정보만 추출해옴
		
		//Header의 key값 구하기
		Iterator<String> it = headerMap.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			System.out.println(key + " : " + headerMap.get(key));
		}
		System.out.println("=======================================");
		
		
		
		
		//해당 호스트의 페이지 내용 가져오기
		
		//파일을 읽어오기 위한 스트림 생성
		//네트워크는 1bit 씩 읽어옴.
		InputStream is = urlCon.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");//보조스트림(콘솔로 읽어올 때 한글방식으로 복원을 해야 하기때문에)
		
		int c = 0;
		while((c=isr.read()) != -1) {
			System.out.print((char) c);
		}
		
		isr.close();//스트림 닫기
	}
	
}
