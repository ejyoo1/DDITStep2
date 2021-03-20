package kr.or.ddit.basic;

import java.io.File;
import java.io.IOException;

public class T01_FileTest {
	public static void main(String[] args) throws IOException {
		/**
		 * 파일 객체 만들기 연습
		 * 1. new File(String 파일 또는 경로)
		 * => 디렉토리와 디렉토리 사이 또는 디렉토리와 파일명 사이의 구분 문자는
		 * 	'\'을 사용하거나 '/'를 사용할 수 있다.
		 */
//		File file = new File("d:/D_Other/test.txt");
		File file = new File("d:\\D_Other\\test.txt"); //이스케이프로 인해서 \를 2개 씀. Java에서 \n 이런식으로 사용하고 있기 때문임.
		System.out.println("파일 명 : " + file.getName());
		System.out.println("파일 여부 : " + file.isFile());
		System.out.println("디렉토리(폴더) 여부 : " + file.isDirectory());
		System.out.println("==================================");
		
//		File file2 = new File("d:/D_Other");
		File file2 = new File("d:/D_Other/test.txt");
		System.out.print(file2.getName() + "은 ");
		if(file2.isFile()) {
			System.out.println("파일");
		}else if(file2.isDirectory()){
			System.out.println("디렉토리(폴더)");
		}
		System.out.println("---------------------------------");
		
//		2. new FIle(File parent, String child)
//		=> 'parent'디렉토리 안에 있는 'child'파일 또는 디렉토리를 말한다.
		File file3 = new File(file2, "test.txt");
		System.out.println(file3.getName() + "의 용량 크기 : "
				+ file.length() + "bytes");
		
//		3. new File(String parent, String child)
		File file4 = new File("d:/D_Other", "test.txt");
		System.out.println("절대 경로 : " + file4.getAbsolutePath());//관측자가 어디있던지 간에 동일한 경로를 가리킴
		System.out.println("경로 : " + file4.getPath());//파일객체 만들 때 parent영역에 넣은 초기화 파라미터 값.
		
		//비어있을 때 표준경로 c:에 넣기 , 알수없는 글자일 때 / 자주는 안씀...
		File file5 = new File("", "test.txt");
//		File file5 = new File("", "test.txt");// 프로젝트 위치 내 폴더에 저장
		System.out.println("표준 경로 : " + file5.getCanonicalPath());//예외 발생하므로 다른 클래스에서 처리하도록 던짐
		
		
		
		//현재 클래스의 URL : file:/C:/Users/PC-19/Desktop/test/DDITStep2/JavaIOTest/bin/kr/or/ddit/basic/T01_FileTest.class
//		파일에 접근한다.
		System.out.println("현재 클래스의 URL : " 
				+ T01_FileTest.class.getResource("T01_FileTest.class"));
//		현재 클래스의 절대 경로를 가져오기
//		/C:/Users/PC-19/Desktop/test/DDITStep2/JavaIOTest/bin/kr/or/ddit/basic/
		System.out.println(T01_FileTest.class.getResource("").getPath());
		System.out.println(T01_FileTest.class.getResource(""));
		
		
		
		
		/**
		 * 디렉토리 핸들링
		 * 디렉토리 폴더 만들기
		 * 1. mkdir() 	=> File객체의 경로 중 마지막 위치의 디렉토리를 만든다.
		 * 				=> 중간의 경로가 모두 미리 만들어져 있어야 한다.
		 * 2. mkdirs()	=> 중간의 경로가 없으면 중간의 경로도 새롭게 만든 후 마지막 위치의 디렉토리를 만들어준다.
		 * => 위 두 메서드 모두 만들기를 성공하면 true, 실패하면 false를 반환
		 */
		File file6 = new File("d:/D_Other/연습용");
		if(file6.mkdir()) {
			System.out.println(file6.getName() + "만들기 성공!");
		}else {
			System.out.println(file6.getName() + "만들기 실패!!!");
		}
		System.out.println();
		
		File file7 = new File("d:/D_Other/test/java/src");
		if(file7.mkdirs()) {
			System.out.println(file7.getName() + "만들기 성공!");
		}else { 
			System.out.println(file7.getName() + "만들기 실패!!!");
		}
		
	}
}
