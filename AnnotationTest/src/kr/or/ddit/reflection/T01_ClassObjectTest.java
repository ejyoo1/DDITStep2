package kr.or.ddit.reflection;

/**
 * Class 오브젝트(오브젝트 내부의 세부 정보를 담고있는)를 생성하기
 * @author 유은지
 * reflection API 예제 : 클래스 정보를 얻기위한 API(프레임 워크를 만들고 싶을 때 사용)
 * 
 * Java Reflection에 대하여...
 * 1. 리플렉션은 **런타임 시점**에 클래스 또는 멤버변수, 메서드, 생성자에 대한 정보를 가져오거나 수정할 수 있고,
 * ****새로운 객체를 생성*****하거나, *****메서드를 실행*****할 수 있다.
 * 2. Reflection API는 Java.lang.reflect 패키지와 Java.lang.Class를 통해 제공된다.
 * 3. Java.lang.Class의 주요 메서드
 * - getName(), getSuperclass(), getInterface(), getModifiers() 등.
 * 4. Java.lang.reflect 패키지의 주요 클래스
 * - Field, Method, Constructor, Modifier 등.
 * 
 */

/**
 *	3가지 방법을 이용해서 클래스 변수에 담을 수 있다.
 */
public class T01_ClassObjectTest {
	public static void main(String[] args) throws ClassNotFoundException {
		//클래스 객체를 얻어오는 첫번째 방법 : Class.forName() 메서드 이용
		Class<?> klass = //객체를 만들때 사용한 타입을 관리하기 위한 Class 클래스일수도있고 인터페이스일수도 있고 타입일 수도 있음.(객체를 만들때 사용했던 것... enum 등)
				Class.forName("kr.or.ddit.reflection.ClassObjectTest");//forName static 메서드 ===> 접근하고자 하는 프로젝트 경로를 담은 객체가 리턴됨.
		
		//클래스 객체를 얻어오는 두번째 방법 : getClass() 메서드 이용
		T01_ClassObjectTest obj = new T01_ClassObjectTest(); //객체를 만들고 
		klass = obj.getClass();//클래스에 정의된 getClass() 메서드를 가져와서 Class 타입의 객체를 리턴함.
		
		//클래스 객체를 얻어오는 세번째 방법 : .class 이용
		klass = T01_ClassObjectTest.class;//진짜 그 클래스 파일을 얻어옴.
	}
}
