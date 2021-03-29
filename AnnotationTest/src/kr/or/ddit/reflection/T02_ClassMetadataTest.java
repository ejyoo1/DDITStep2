package kr.or.ddit.reflection;

import java.lang.reflect.Modifier;

/**
 * 클래스메타데이타 : 클래스에 정의되어 있는 클래스 정보를 가져온다
 * @author 유은지
 * Class의 메타데이터 가져오는 예제
 */
public class T02_ClassMetadataTest {
	public static void main(String[] args) {
		//클래스 오브젝트 생성하기 - 와일드카드 써서 제너릭하게 만들어줌.
		Class<?> clazz = SampleVO.class; //클래스 타입 가져오는 세번째 방법
		
		System.out.println("심플 클래스 명 : " + clazz.getSimpleName());
		System.out.println("클래스 명 : " + clazz.getName());
		System.out.println("상위클래스명 : " + clazz.getSuperclass());
		
		//해당 클래스에서 구현하고 있는 인터페이스 목록 가져오기
		Class<?>[] interfaceList = clazz.getInterfaces();
		
		System.out.println("인터페이스 목록");
		for(Class<?> inf : interfaceList) {
			System.out.println(inf.getName() + " | " );
		}
		System.out.println();
		
		//클래스의 접근제어자 가져오기
		int modFlag = clazz.getModifiers();
		System.out.println("접근 제어자 : " + Modifier.toString(modFlag));
	}
}
