package kr.or.ddit.basic;

/**
 * 함수적 인터페이스 = > 추상 메서드가 1개 선언된 인터페이스
 * @author 유은지
 *
 */

//반환값이 없고 매개변수도 없는 추상메서드
@FunctionalInterface //함수적 인터페이스라는 것을 보장받을 수 있음. (컴파일러가 이것이 잘못된 경우 체크함 => 추상 메서드가 1개만 가질 수 있는데 2개를 가진경우 걸러줌)
public interface LambdaTestInterface1 {
	public void test();
}

//반환값이 없고 매개변수는 있는 추상메서드
@FunctionalInterface
interface LambdaTestInterface2 {
	public void test(int a);
}

//반환값, 매개변수가 있는 추상 메서드
@FunctionalInterface
interface LambdaTestInterface3 {
	public int test(int a, int b);
}

