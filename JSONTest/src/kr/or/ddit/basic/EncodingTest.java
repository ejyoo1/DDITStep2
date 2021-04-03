package kr.or.ddit.basic;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 웹 파라미터 넘겨줄 때 퍼센트 인코딩을 사용해야 함.
 * @author ejyoo
 * URL은 다음과 같이 정해진 일부 아스키 문자 집합으로 표현되어야 한다.
 */
public class EncodingTest {
	public static void main(String[] args) {
		//인코딩할 문자열
		String word = "충청";// 기대결과 : %EC%B6%A9%EC%B2%AD
		System.out.println("인코딩 전 : " + word);
		try {
			//URL 인코딩 - URI에서 사용할 수 없는 문자를 인코딩(이스케이프) 처리 : (퍼센트 인코딩)
			//영숫자, 밑줄, 하히픈, 마침표, 별표 문자는 인코딩 대상에서 포함되지 않음. 공백은 더하기 기호(+)로 변환
			String encodedString = URLEncoder.encode(word, "UTF-8");
			System.out.println("인코딩 된 문자 : " + encodedString);
			
			//URL 디코딩 - 인코딩한 문자열을 원래 문자열로 복원
			String decodedString = URLDecoder.decode(encodedString, "UTF-8");
			System.out.println("디코딩 된 문자 : " + decodedString);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
