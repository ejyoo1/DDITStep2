package kr.or.ddit.basic;

/**
 * 애너테이션 사용예제
 * @author 유은지
 *Reflection API 사용
 *- Member라는 클래스를 사용해서 멤버 접근할 떄 Reflection API를 사용하면 쉽게 접근할 수 있다.
 */


public class Service {
	@PrintAnnotation
	public void method1() {
		System.out.println("메서드1에서 출력되었습니다.");
	}
	
	@PrintAnnotation(value = "%") //value만 있으면 ("%")만 간단히 할 수 있다.
	public void method2() {
		System.out.println("메서드2에서 출력되었습니다.");
	}
	
	@PrintAnnotation(value = "#", count = 30)
	public void method3() {
		System.out.println("메서드3에서 출력되었습니다.");
	}
}
