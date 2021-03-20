package kr.or.ddit.basic;

import java.io.File;
import java.io.IOException;

public class test {
	public static void main(String[] args) throws IOException {
//		파일 객체는 데이터를 가져오는 것만 할 수 있음. 입력은 스트림이 함.
		File f1 = new File("d:/D_Other/sample.txt");
		File f2 = new File("d:/D_Other/test.txt");
		
		if(f1.exists()) {
			System.out.println(f1.getAbsolutePath() + "은 존재합니다.");
		} else {
			System.out.println(f1.getAbsolutePath() + "은 없는 파일입니다.");
			
			if(f1.createNewFile()) {
				System.out.println(f1.getAbsolutePath() + "파일을 새로 만들었습니다.");
			}//end if
		}//end else
		
		if(f2.exists()) {
			System.out.println(f2.getAbsolutePath() + "은 존재합니다.");
		} else {
			System.out.println(f2.getAbsolutePath() + "은 없는 파일입니다.");
		}
		System.out.println("=================================================");
	}//end main
}//end class
