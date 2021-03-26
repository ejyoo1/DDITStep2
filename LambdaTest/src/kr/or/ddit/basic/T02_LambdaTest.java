package kr.or.ddit.basic;


/**
 * 람다식을 익명객체로 넣었지만 컴파일 시 람다식이 원래의 객체로 변환됨.
 * 개발자를 위해 코드를 간결하게 했으므로
 * 컴파일한 코드 까보면 람다식은 보이지 않음.
 * @author PC-19
 *
 */
public class T02_LambdaTest {
	public static void main(String[] args) {
		// 람다식을 사용하지 않았을 경우
		LambdaTestInterface1 lam1 = new LambdaTestInterface1() {//익명객체로 넣음(람다식 안쓴경우)
			
			@Override
			public void test() {
				System.out.println("안녕하세요");
				System.out.println("익명 구현 객체 방식입니다.");
			}
		};
		lam1.test(); // 메서드 호출
		
		LambdaTestInterface1 lam2 = ()-> //lam1을 람다식으로 변경 (컴파일 하는 시점에는 lam1로 컴파일러가 자동으로 변경함)(수식을 확줄여서 사용할 수 있다.)
			System.out.println("람다식 구현 방식입니다.");
			
		lam2.test(); // 메서드 호출
		
		System.out.println("---------------------------------");

/*
      람다식 작성 방법(좀더 간결하게 하는 방법이랄까) ( 꼭 하라는건 아니고 이렇게 해도 된다 )
      
   기본형식) (자료형이름 매개변수명, ...) -> {실행문들; ...}
   
  1) 매개변수의 '자료형이름'은  생략할 수 있다.
     예) (int a) -> { System.out.println(a);} //오리지널 작성
      (a) -> { System.out.println(a);} //이렇게 생략가능(컴파일러가 유추 가능 => 찾아서 들어가보면 뭔지 앎)
   
  2) 매개변수가 1개일 경우에는 괄호'()'를 생략할 수 있다.
   (단, '자료형이름'을 지정할 경우에는 괄호를 생략할 수 없다.)
   예) a -> {System.out.println(a);}
   
  3) '실행문'이 1개일 경우에는 '{}'(중괄호)를 생략할 수 있다.
   (이때 문장의 끝을 나타내는 세미콜론(;)도 생략한다.)
     예)   a -> System.out.println(a) //안감싸도 안다..세미콜론도 생략할 수 있다.
     
  4) 매개변수가 하나도 없으면 괄호 '()'를 생략할 수 없다.
     예) () -> System.out.println(a) //없는 경우는 명시적으로 알려주어야 한다.
     
  5) 반환값이 있을 경우에는 return 명령을 사용한다.
    예) (a, b) -> { return a+b;}
      (a, b) -> return a+b
     
  6) 실행문에 return문만 있을 경우 return명령과 '{}'를 생략할 수 있다.
    예) (a, b) -> a + b
*/
		LambdaTestInterface2 lam3 = //리턴타입 없음 
			(int z) -> {//z값 하나 들어가고
				int result = z + 100; //결과 더하고
				System.out.println("result = " + result);//찍어주는 로직
			};
		lam3.test(30);//익명객체를 30 ==> 30 + 130
		
		LambdaTestInterface2 lam4 = 
			z -> {//int를 생략 ()도 생략
				int result = z + 300;
				System.out.println("result = " + result);
			};
		lam4.test(60);//360
		
		LambdaTestInterface2 lam5 =
			z -> System.out.println("result = " + (z+500));//statement 1개밖에없어서 중괄호 생략 , 문장끝 구분위해 세미콜론은 생략하지 않음
		lam5.test(90);//590
		
		System.out.println("----------------------------------");
		
		LambdaTestInterface3 lam6 =//리턴값있고 파라미터2개가 있는 인터페이스
			(int x, int y) -> {//리턴값 2개 받아서
				int r = x + y;//두개를 더하는 람다식 구현
				return r;
			};
		int k = lam6.test(20, 50);//실행 ==> 리턴 70
		System.out.println("k = " + k);//70출력
		
		LambdaTestInterface3 lam7 =
			(x, y)->{//int 생략
				return x + y;//두개 더하는것
			};
		k = lam7.test(80, 50);
		System.out.println("k = " + k);//130
		
		LambdaTestInterface3 lam8 =
			(x, y) -> x + y;//중괄호 생략 뒤와 구분하기 위해 세미콜론 생략 안함
		k = lam8.test(100, 200);
		System.out.println("k = " + k);//300
		
		LambdaTestInterface3 lam9 =
			(x, y) -> { return x > y ? x : y;}; //두개 받아서 그중에 큰값을 선택하는 로직
		k = lam9.test(100, 200);
		System.out.println("k = " + k);//200
	}
}
