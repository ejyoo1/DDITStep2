package kr.or.ddit.basic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 리플렉션 API를 사용해서 어노테이션 얻어오기
 * @author PC-19
 * 메서드 정보에 접근해서 count 수 만큼 쭉 찍고 해당 메서드를 실행
 * xml 설정파일을 쓰지 않고 자바에 코드를 넣음으로 써 
 * 설정파일을 까지 않고 코드상에 어노테이션에 설정정보를 세팅해가면서 
 * 코드를 완성할 수 있어서 편하다.
 * 
 * 어노테이션 단점 : 클래스를 컴파일 해야됨.
 */
public class AnnotationTest {
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		
		// PrintAnnotation의 static변수값 출력하기
		System.out.println(PrintAnnotation.id);
		
		// Reflection API를 이용하여 메서드 정보 추출 및 실행
		//메서드 타입의 객체를 결과로 받음
		Method[] methodArr = Service.class.getDeclaredMethods();
		
		for(Method m : methodArr) {
			System.out.println(m.getName()); // 메서드명 출력
			
			// 애너테이션 객체 가져오기(애너테이션은 메서드, 클래스 멤버변수에도 붙일 수 있다.) 
			//의미있는 정보를 우리가 세팅했기 때문에 가져오는 것임.
			PrintAnnotation printAnn = 
					m.getDeclaredAnnotation(PrintAnnotation.class);//어노테이션을 가져옴(class객체 넣어서)******
			
			for(int i=0; i<printAnn.count(); i++) {// count값 만큼...
				System.out.print(printAnn.value()); // value값 출력
			}
			System.out.println(""); // 줄바꿈 처리
			
//			m.invoke(new Service()); // 메서드 실행(접근한 메서드를 api를 사용하여 실행할 수 있음. 실행할 객체를 넣어주면 됨)
//			m.invoke 대신에 아래와 같은 방법 사용 가능(리플랙션 객체를 사용해서 메서드 실행 가능함.)
			Class<Service> clazz = Service.class;
			Service service = (Service) clazz.newInstance();
			m.invoke(service);
			
		}
	}
}
