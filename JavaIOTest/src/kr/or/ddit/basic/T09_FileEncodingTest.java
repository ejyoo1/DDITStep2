package kr.or.ddit.basic;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 문자
 * @author 유은지
 *
 */
public class T09_FileEncodingTest {
	/**
	 InputStreamReader 객체는 파일의 인코딩 방식을 지정할 수 있다.
	 형식) new InputStreamReader(바이트 기반 스트림 객체, 인코딩 방식)
	  
	 * 인코딩 방식
	 한글 인코딩 방식은 크게 UTF-8 과 EUC-KR 방식 두가지로 나누어 볼 수 있다.
	 원래 한글윈도우는 CP949방식을 사용했는데, 윈도우를 개발한 마이크로소프트에서
	 EUC-KR 방식을 확정하였기 때문에 MS949라고도 부른다.
	 한글 윈도우의 메모장에서 말하는 ANSI 인코딩이란, CP949(Code Page 949)를 말한다.
	 CP949는 EUC-KR의 확장이며, 하위 호환성이 있다.
	 - MS949 => 윈도우의 기본 한글 인코딩 방식(ANSI 계열)
	 - UTF-8 => 유니코드 UTF-8 인코딩 방식(영문자 및 숫자 : 1byte, 한글: 3byte)
	 - US-ASCII => 영문 전용 인코딩 방식
	 
	 ANSI는 영어를 표기하기위해 만든 코드로 규격 자체에 한글이 없다가 나중에 여기에 EUC-KR, CP949 이라는 식으로
	 한글이 포함되었다. (ANSI 계열 윈도우 : CP949, MS949) (현재는 최근 시스템은 EUC-KR)
	 
	 참고)
	 ASCII => extended ASCII(ISO 8859-1) => 조합형(초성,중성,종성), 완성형(KSC5601)
	 -----------------------------------------------------------------------
	 윈도우 계열 : CP949(확장완성형) - 일부문자(8822자) 추가함 	|
	 유닉스 계열 : EUC-KR(확장 유닉스코드) 					|=> ANSI계열(EUC-KR) 
	 
	 
	 유니코드(UTF-8)가 갑자기 생성됨.
	 */
	
	public static void main(String[] args) {
//		파일 인코딩을 이용하여 읽어오기
		FileInputStream fis = null;
		InputStreamReader isr = null;
		
		try {
			fis = new FileInputStream("d:/D_Other/test_ansi.txt");
			
			//기반 스트림을 보조하기 위해 보조스트림을 사용
			//보조 스트림은 반드시 기반 스트림이 필요하다. 보조스트림 혼자 사용을 못함.
			//보조스트림 인자값으로 기반 스트림 객체를 넣어주저야 한다.
			isr = new InputStreamReader(fis, "CP949");
			
			int c;
			while( (c = isr.read()) != -1 ) {
				System.out.print((char)c);
			}
			System.out.println();
			System.out.println("출력 끝...");
			
			isr.close();//보조스트림만 닫아도 된다.
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
	}
}
