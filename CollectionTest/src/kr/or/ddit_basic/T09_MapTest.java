package kr.or.ddit_basic;

import java.util.HashMap;
import java.util.Map;

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
	}
}
