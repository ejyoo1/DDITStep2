package kr.or.ddit_basic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class T09_MapTest {
	public static void main(String[] args) {
		/**
		 	Map => key값과 value 값을 한 쌍으로 관리하는 객체
		 		=> key값은 중복을 허용하지 않고 순서가 없다.(Set의 특징)
		 		=> value값은 중복을 허용한다.
		 */
		Map<String, String> map = new HashMap<String, String>();
//		자료 추가 => put(key 값, value 값);
		map.put("name", "홍길동");
		map.put("addr", "대전");
		map.put("tel", "010-1234-5678");
		
		System.out.println("map => " + map);
		
//		자료수정 	=> 데이터를 저장할 때 key 값이 같으면 나중에 입력한 값이 저장된다.
//				=> put(수정할 key값, 새로운value 값)
//		추가인지 수정인지 어케 판단 ? 키이 존재하는지 확인하면 됨.
		map.put("addr", "서울");
		System.out.println("map => " + map);
		
//		자료 삭제
		map.remove("name");
		System.out.println("map => " + map);
		
//		자료 읽기 => get(key 값)
		System.out.println("addr = " + map.get("addr"));
		System.out.println("============================================");
		
//		key 값들을 읽어와 자료를 출력하는 방법
		
//		방법 1 => keySet() 메서드 이용하기
//		keySet() 메서드 => Map의 Key값들만 읽어와 Set형으로 반환한다.
		Set<String> keySet = map.keySet();
		
		System.out.println("Iterator를 이용한 방법");
		
		Iterator<String> it = keySet.iterator();
		while(it.hasNext()) {
			String key = it.next();
			System.out.println(key + " : " + map.get(key));
		}
		System.out.println("-------------------------------------------");
		
//		방법 2 => Set형의 데이터를 '향상된 for문' 으로 처리해도 된다.
//		Set은 iterable 인터페이스로 구현하였기 때문에 향상된 for문으로 가능
		System.out.println("향상된 for문을 이용한 방법");
		for(String key : keySet) {
			System.out.println(key + " : " + map.get(key));
		}
		System.out.println("-------------------------------------------");
		
//		방법3 => value값만 읽어와 출력하기 => values() 메서드 이용하기
//		얘는 Set 처리하면 안됨 => value는 중복값이 있을 수 있음.
		System.out.println("values()메서드 이용한 방법");
		for(String value : map.values()) {
			System.out.println(value);
		}
		System.out.println("-------------------------------------------");
		
//		방법4 => Map에는 Entry라는 내부 class가 만들어져 있다.
//				이 클래스는 key와 value라는 멤버 변수로 구성되어 있다.
//				Map에서 이 Entry들을 Set형식으로 저장하여 관리한다.
//		Entry 객체 전체를 가져오기(가져온 Entry들은 Set으로 되어있다.
//		=> entrySet() 메서드를 이용하여 가져온다.
//		=> 왜 Map 안에 Entry 를 구현하였나 ? Map에서만 쓰기때문에 내부 클래스로 정의한것임 굳이 외부 클래스로 뺄 이유가 없다.
		Set<Map.Entry<String, String>> mapSet = map.entrySet();
		
//		가져온 Entry 객체들을 순서대로 처리하기 위해서 Iterator객체로 변환
//		이것도 향상된for문 사용할 수 있음.
		Iterator<Map.Entry<String, String>> entryIt = mapSet.iterator();
		
		while(entryIt.hasNext()) {
			Map.Entry<String, String> entry = entryIt.next();
			
			System.out.println("key값 : " + entry.getKey());
			System.out.println("value값 : " + entry.getValue());
			System.out.println();
		}
		/**
		 제너릭이 없어도 가능. Object 타입임 
		 제너릭을 쓰는 이유는 형을 제한하기 위해서임.
		 Iterator entryIt = mapSet.iterator();
		
			while(entryIt.hasNext()) {
				Map.Entry entry = entryIt.next();
				
				System.out.println("key값 : " + entry.getKey());
				System.out.println("value값 : " + entry.getValue());
				System.out.println();
			}
		 
		 
		 */
		
	}
}
