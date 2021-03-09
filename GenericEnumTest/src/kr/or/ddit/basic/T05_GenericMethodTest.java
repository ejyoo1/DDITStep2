package kr.or.ddit.basic;


class Util2 {
	public static <T extends Number> int compare(T t1, T t2) {
		double v1 = t1.doubleValue();
		double v2 = t2.doubleValue();
		
		return Double.compare(v1, v2);
	}
}
/**
 제한된 타입 파라미터(Bounded Parameter) 예제
 어느 범위의 타입만 올 수있게 컴파일러에 요청하여 막아버릴 코드
 */
public class T05_GenericMethodTest {
	public static void main(String[] args) {
		int result1 = Util2.compare(10, 20);//AutoBoxing
		System.out.println(result1);
		
		//Util2.<Double, Double>compare(3.14,3);은 컴파일 에러 발생함. 제한된 파라미터에서는 안먹히는 것같음
		int result2 = Util2.compare(3.14, 3);
		System.out.println(result2);
		
//		Util2.compare("c","java"); Number만 올 수 있는데 String 타입이 와서 컴파일 에러 발생함.
	}
}
