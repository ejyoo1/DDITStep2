package kr.or.ddit.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 클래스에 선언된 메서드의 메타정보 가져오기
 * 순서는 보장되지 않아서 뒤죽박죽 나옴
 */
public class T03_MethodMetadataTest {
	public static void main(String[] args) {
		
		// class 객체 생성
		Class<?> clazz = SampleVO.class;
		
		// 클래스에 선언된 모든 메서드의 메타데이터 정보 가져오기
		//getDeclaredMethods() : 선언한 클래스만 가져오는 것 ===> 메서드 타입으로 받아옴 
		//methods() : Object 까지 표시해줌.
		Method[] methodArr = clazz.getDeclaredMethods();//선언된 메서드 개수만큼 리턴
		
		for(Method method : methodArr) {//필요한 정보를 얻을 수 있음.
			System.out.println("메서드 명 : " + method.getName());
			System.out.println("메서드 리턴타입 : " 
					+ method.getReturnType());
			
			// 해당 메서드의 접근제어자 정보 가져오기
			int modFlag = method.getModifiers();
			System.out.println("메서드 접근제어자 : " 
						+ Modifier.toString(modFlag));
			
			// 해당 메서드의 파라미터 값 가져오기
			Class<?>[] paramList = method.getParameterTypes();
			System.out.println("메서드 파라미터 타입 : ");
			for(Class<?> klass : paramList) {
				System.out.print(klass.getName() + " | ");
			}
			System.out.println();
			
			// 해당 메서드에서 던지는 예외 타입 가져오기
			Class<?>[] exTypeArr = method.getExceptionTypes();
			System.out.println("메서드에서 던지는 예외타입 목록 :");
			for(Class<?> klass : exTypeArr) {
				System.out.print(klass.getName() + " | ");
			}
			System.out.println("----------------------------");
		}
	}
}
