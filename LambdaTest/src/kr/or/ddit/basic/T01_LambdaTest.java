package kr.or.ddit.basic;

/**
 * 람다식 
 * => 익명함수를 생성하기 위한 식. 자바에서는 '매개변수를 가진 코드 블럭'
 * => 런타임시, 익명 구현 객체로 생성된다.
 * 
 * 형식 ) 인터페이스명 객체변수명 = 람다식;
 * 
 * 람다식의 형식) (매개변수들...) -> {처리할 코드들; ...}
 * 
 * => 제약조건 : 람다식으로 변환할 수 있는 인터페이스는 추상메서드가 1개인 인터페이스만 처리할 수 있다.(모든것을 바꿀 수 있는것은 아님)
 * 	이런 인터페이스를 '함수적 인터페이스' 라고 한다.
 * 	이 함수적 인터페이스를 만들 때는 @FunctionalInterfase로 지정한다.
 * 어노테이션이  @FunctionalInterfase 것만 가능함.
 * @author 유은지
 * 함수형 형태로 만들어놔도 내부적으로 컴파일 될 때 객체로 컴파일이 잘됨.
 * 람다식이라는 문법을 이용해서 모양을 식 으로 바꿔놓으면 불필요한 코드가 많이 사라진다.
 * 불필요한 코드가 줄일 수 있다 => 가독성이 좋아진다.
 * JAVA8 이상만 지원됨.
 * 
 * 추상 메서드가 1개 인것? : 인터페이스를 구현한 객체가 있다면 람다식으로 표현할 수 있는데,
 * 그 인터페이스가 1개이어야 한다.
 * 대표적인 객체 쓰레드임. Runnable => run이라는 메서드 1개임.
 */
public class T01_LambdaTest {
	public static void main(String[] args) {
		//람다식을 사용하지 않는 경우
		Thread th1 = new Thread(new Runnable() {//쓰레드 3번째방법 익명클래스 생성방법 (함수적 인터페이스인 Runnable 구현)
			@Override
			public void run() {
				for(int i = 1 ; i <= 10 ; i++) {
					System.out.println(i);
				}
			}
		});
		
		th1.start();
		
//		() == run() : run에 파라미터 있으면 ()안에 똑같이 파라미터 넣기.
		
//		functionalInterfase이기에 람다여도 서로 반대 경우로 구현할 수 있음
		
		//람다식을 사용하는 경우
		Thread th2 = new Thread(() -> { //함수적 인터페이스라 람다식을 사용할 수 있음.(비즈니스 로직만 딱 있는 느낌)
			for(int i = 1 ; i <= 10; i++) {
				System.out.println("람다-" + i);
			}
		});
		th2.start();
	}
}
