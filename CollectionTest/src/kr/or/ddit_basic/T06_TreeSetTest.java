package kr.or.ddit_basic;

import java.util.SortedSet;
import java.util.TreeSet;

public class T06_TreeSetTest {
	public static void main(String[] args) {
//		TreeSet은 자동정렬 기능이 있다.
//		TreeSet 메서드를 호출하기 위해 TreeSet 객체 생성함.
		TreeSet<String> ts = new TreeSet<String>();
		
//		영어 대문자를 문자열로 반환하여 TreeSet에 저장
//		TreeSet은 Add 하자마자 정렬이되므로 임의로 문자를 내림차순으로 삽입함.
		for(char ch='Z'; ch>='A'; ch--) {
//			캐릭터 타입을 문자로 바꾸어주어야하기 때문에 valueOf를 사용하여 형변환 해야한다.
//			래퍼클래스에넌 valueOf가 존재한다.
//			String temp = (String) ch;//오류 발생함.
//			쌩뚱맞은 타입을 변환하기 위해 사용한다.(캐스팅이 안되는 경우에만 사용)
			String temp = String.valueOf(ch);
//			캐스팅을 해야되는 경우 : Object -> String을 넣을 수 있다.
			Object obj = new String("AAA"); //가능
//			위의 경우는 String 객체를 넣어준것 뿐이지 String의 메서드를 이용할 수 있는 것은 아니다.
//			obj. 시 다형성으로 인해 String으로 줄여졌기때문에 메서드는 Object 객체의 메서드만 보여지는 것이다.
//			obj. => Object 메서드만 표시함
//			따라서 캐스팅을 통하여 String 객체의 메서드를 가져올 수 있다.(다운 캐스팅)
//			((String)obj). => Object, String 메서드 표시함.
//			
			ts.add(temp);
		}
		
		System.out.println("TreeSet 자료 : " + ts);
		
//		TreeSet에 저장된 자료 중 특정한 자료보다 작은 자료를 찾아서 SortedSet으로 반환하는 메서드가 있다.
//		=> headSet(기준값) : 기본적으로 '기준값'은 포함시키지 않는다.
//		=> headSet(기준값, 논리값) : 논리값이 true이면 '기준값'을 포함시킨다.
		SortedSet<String> ss1 = ts.headSet("K");
		System.out.println("K 이전 자료 : " + ss1);//기준값 미포함
		System.out.println("K 이전 자료 (기준값 포함) : " + 
							ts.headSet("K", true));
		
//		'기준값' 보다 큰 자료를 찾아 SortedSet으로 반환하는 메서드
//		=> tailSet(기준값) => 기본적으로 '기준값'을 포함시킨다.
//		=> tailSet(기준값, 논리값) => 논리값이 false이면 '기준값'을 포함시키지 않는다.
		SortedSet<String> ss2 = ts.tailSet("K");
		System.out.println("K 이후 자료 : " + ss2);//기준값 포함
		System.out.println("K 이후 자료 (기준값 미포함) :" + 
							ts.tailSet("K",false));
		
//		subSet : 부분집합 / 일정 범위의 데이터를 추출해오겠다.
//		subSet(기준값1, 기준값2) => 기준값1 ~ 기준값2 사이의 값을 가져온다.
//		=> '기준값1' 포함, '기준값2' 미포함
//		subSet(기준값1, 논리값1, 기준값2, 논리값2)
//		=> 각 기준값을 포함할지 여부를 설정한다. (논리값이 true이면 포함, false면 미포함)
		System.out.println("K(포함) 부터 N(미포함)까지 : " + 
							ts.subSet("K", true, "N", false));
		System.out.println("K(포함) 부터 N(미포함)까지 : " + 
							ts.subSet("K", true, "N", true));
		System.out.println("K(포함) 부터 N(미포함)까지 : " + 
							ts.subSet("K", false, "N", true));
		System.out.println("K(포함) 부터 N(미포함)까지 : " + 
							ts.subSet("K", false, "N", false));
	}
}
