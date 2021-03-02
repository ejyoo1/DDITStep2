package kr.or.ddit_basic_ejyoo;

import java.util.ArrayList;
import java.util.List;

public class ArrayListTestRemind {
	public static void main(String[] args) {
//		ArrayList는 기본적인 사용법이 Vector와 같다.
//		default_caracity = 10
//		List 객체를 생성한다.
		List list1 = new ArrayList<>();
		
//		add() 메서드를 사용해서 데이터를 추가한다.(오토박싱)
//		add 메서드는 Object를 받을 수 있다. 참조형이 아닌 자료형은 오토박싱이 일어난다.(래퍼클래스로 변환)
//		"aaa"는 String형태인 참조변수이므로 오토박싱이 일어나지 않는다.
		list1.add("aaa");
//		참조형태 String 을 사용하여 삽입
		list1.add("bbb");
//		기본형인 int인데 Integer로 오토박싱
		list1.add(111);
//		기본형인 char인데 Charactor로 오토박싱
		list1.add('k');
//		기본형인 boolean인데 Boolean로 오토박싱
		list1.add(true);
//		기본형인 double인데 Double로 오토박싱
		
//		size() 메서드를 통하여 데이터의 크기를 가져옴
		System.out.println("list1의 size() : " + list1.size());//결과 : 5
		System.out.println("list1만 출력했을 때 : " + list1);//결과 : list 내에 있는 것을 가져옴.
		
//		get으로 데이터 꺼내오기
		System.out.println("list1의 1번째 자료 가져오기(0부터 시작) : " + list1.get(1));
		
//		데이터를 ArrayList 중간에 삽입하기
		list1.add(1,"zzz");
		System.out.println("list1만 출력했을 때 : " + list1);//결과 : list 내에 있는 것을 가져옴
		
//		set() 메서드로 데이터 변경하기
//		set을 쓰기전에 temp 라는 String 변수에 캐스팅하여 삽입하면 변경하기 전 값을 저장할 수 있다.
		String temp = (String) list1.set(0, "YYY");
		System.out.println("temp 값 출력 : " + temp);
		System.out.println("list1만 출력했을 때 : " + list1);
		System.out.println("temp 값 출력 : " + temp);
		
//		remove() 메서드로 데이터 삭제하기
		System.out.println("삭제 전 : " + list1);
		list1.remove(0);
		System.out.println("삭제 후 : " + list1);
		
//		remove() 메서드로 특정 값에 해당하는 데이터 삭제하기
		System.out.println("삭제 전 : " + list1);
		list1.remove("bbb");
		System.out.println("삭제 후 : " + list1);
		
		System.out.println("==============================================");
		
//		제너릭을 지정하여 List 선언하기
		List<String> list2 = new ArrayList<String>();
		list2.add("AAA");//오토박싱 일어나지 않음
		list2.add("BBB");
		list2.add("CCC");
		list2.add("DDD");
		list2.add("EEE");
		
//		일반적인 for 문을 돌려서 List 데이터 찍기
		for(int i = 0 ; i < list2.size(); i++) {
			System.out.println(i + " : " + list2.get(i));
		}
		
		System.out.println("==============================================");
		
//		향상된 for 문을 사용 (최적화가 잘되어있음)
		for(String s : list2) {
			System.out.println(s);
		}
		
		System.out.println("==============================================");
		
//		contains() 메서드를 사용하여 값 비교
//		리스트에 비교할 객체가 있으면 true, 없으면 false
		System.out.println(list2.contains("DDD"));//ture
		System.out.println(list2.contains("ZZZ"));//false
		
		System.out.println("==============================================");
		
//		toArray() 메서드를 사용하여 리스트 안의 데이터를 배열로 변환하여 반환한다.
//		해당 경우는 흔치않은 경우로 대부분은 List로 사용하는데
//		특정 사용자가 만든 메서드를 가져올때 그 메서드가 String 배열 형태라면
//		데이터를 배열로 반환해야 하는 상황이 생기므로 아래의 방법을 사용한다.
//		=> 리스트 안의 데이터들을 배열로 변환하여 반환한다.
//		=> 기본적으로 Object 형 배열로 변환한다.
		Object[] strArr = list2.toArray();
		System.out.println("배열의 개수 : " + strArr.length);
		
//		리스트2 제너릭(String)에 맞는 자료형의 배열로 변환하는 방법
//		제너릭 타입의 0개짜리 배열을 생성해서 매개변수로 넣어준다.
//		배열의 크기가 리스트 크기보다 작으면 리스트의 크기에 맞는 배열을 넣어준다.
		String[] strArr2 = list2.toArray(new String[0]);
//		아래 형변환은 컴파일 에러를 발생함(컴파일 전에는 오류가 나지 않음)
//		String[] strArr2 = (String[])list2.toArray();
		System.out.println("strArr2의 개수 : " + strArr2.length);
	}
}
