package kr.or.ddit.basic;

import java.util.ArrayList;
import java.util.List;

/**
 와일드 카드 예제
 <? extends T> 	=> 와일드 카드의 상한 제한. T와 그 자손들만 가능
 <? super T> 	=> 와일드 카드의 하한 제한. T와 그 조상들만 가능.
 <?>			=> 모든 타입이 가능 <? extends Object>와 동일.
 */
public class T06_WildcardTest {
	public static void main(String[] args) {
//		타입을 확정지을 수 없을 대 사용
//		List<?> aaa = new ArrayList<String>();
//		List<?> aaa = new ArrayList<Number>();
//		List<?> aaa = new ArrayList<Object>();
		
//		List<? extends String> aaa = new ArrayList<Object>(); : 컴파일 에러 문장 (String과 자손들만 올 수 있음)
//		List<? extends String> aaa = new ArrayList<String>(); : 에러 X
//		List<? super Integer> aaa = new ArrayList<Number>(); : 에러 X
		
		
//		사과와 과일을 담을 상자 준비함
		FruitBox<Fruit> fruitBox = new FruitBox<>(); //과일상자
		FruitBox<Apple> appleBox = new FruitBox<Apple>(); //사과상자
		
//		사과와 포도를 담자.
		fruitBox.add(new Apple());
		fruitBox.add(new Grape());
		
		appleBox.add(new Apple());
		appleBox.add(new Apple());
		
		Juicer.makeJuice(fruitBox);//과일 상자인 경우에는 아무런 문제 없음
		Juicer.makeJuice(appleBox);//사과상자는 문제있음.
	}
}

/**
 주서:주스를 만들어줌(유틸기능이므로 static으로 선언)
 */
class Juicer {
//	static void makeJuice(FruitBox<Fruit> box) {//하드코딩
//	Fruitbox 하위의 appleBox도 사용할 수 있는 메서드 생성
//	Juicer.makeJuice(appleBox);로 인하여 제너릭 타입이 applyBox로 결정
//	static <T extends Fruit> void makeJuice(FruitBox<T> box) {
//	와일드 카드 사용하여 변경 : 제너릭 메서드를 사용하지 않고 파라미터에 와일드카드를 사용하여 표현할 수 있다.
	static void makeJuice(FruitBox<? extends Fruit> box) {
//		static void makeJuice(FruitBox<?> box) { 이렇게 사용하는경우 for(Fruit f : box.getFruitList()) {에서 에러가 난다, 타입을 모르는데 비교를 하고 있다고 컴파일러가 아려줌.
		String fruitListStr = "";//과일목록
		
		int cnt = 0;
//		for(Fruit f : box.getFruitList()) {
//		Fruitbox 하위의 appleBox도 사용할 수 있게 for 문 수정
//		for(T f : box.getFruitList()) {
//		와일드 카드 사용 시
		for(Fruit f : box.getFruitList()) {
			if(cnt == 0) {
				fruitListStr += f;
			} else {
				fruitListStr += "," + f;
			}
			cnt++;
		}
		System.out.println(fruitListStr + "=> 쥬스 완성!");
	}
}

//상속관계 정의하기 위한 클래스 생성

/**
 과일
 */

class Fruit {
	private String name; //과일이름

	public Fruit(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "과일(" + name + ")";
	}
	
	
}


/**
 사과 클래스
 */
class Apple extends Fruit {
	public Apple() {
		super("사과");
	}
}

class Grape extends Fruit {
	public Grape() {
		super("포도");
	}
}

/**
 과일상자
 */
class FruitBox<T> {
	private List<T> fruitList;
	
	public FruitBox() {
		fruitList = new ArrayList<>();
	}
	
	public List<T> getFruitList(){
		return fruitList;
	}
	
	public void setFruitList(List<T> fruitList) {
		this.fruitList = fruitList;
	}
	
	public void add(T fruit) {
		fruitList.add(fruit);
	}
}