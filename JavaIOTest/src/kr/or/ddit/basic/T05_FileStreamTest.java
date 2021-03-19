package kr.or.ddit.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class T05_FileStreamTest {
	public static void main(String[] args) throws IOException {
//		FileInputStream객체를 이용한 파일 내용 읽기
		FileInputStream fis = null; //변수 선언
		
		//방법1 ( 파일 정보를 문자열로 지정하기)
		fis = new FileInputStream("d:/D_Other/test.txt");//생성 (FileNotFoundException)
		
		//방법2 (파일 정보를 File 객체를 이용하여 지정하기)
		File file = new File("d:/D_Other/test.txt");
		//FileInputStream 생성자가 오버라이드한  file 객체를 담을 수 있게 존대하므로 file에 담아서 처리할 수 있다.
		fis = new FileInputStream(file);//생성 (FileNotFoundException)
		
		int c; //읽어온 데이터를 저장할 변수
		
		//읽어온 값이 -1 이면 파일을 끝까지 읽었다는 의미이다.
		//1바이트 씩 읽어옴.
		while((c = fis.read()) != -1) {//(IOException)
			//읽어온 자료 출력하기
			System.out.print((char)c);
		}
		fis.close();//작업완료 후 스트림 닫기
	}
}
