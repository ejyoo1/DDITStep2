package kr.or.ddit_basic;

import java.util.HashSet;
import java.util.Set;

public class T08_equals_hashCodeTest {
	public static void main(String[] args) {
		/**
		 해시함수(hash function)은 임의의 데이터를 고정된 길이의 데이터로 매핑하는 함수이다.
		 해시 함수에 의해 얻어지는 값은 해시 값, 해시 코드, 해시 체크섬 또는 간단하게 해시라고 한다.
		 (암호학적 해시함수 및 비암호학적 해시함수)
		 
		 HashSet, HashMap, HashTable과 같은 객체들을 사용할 경우...
		 객체가 서로 같은지를 비교하기 위해 equals()메서드와 hashCode() 메서드를 호출한다.
		 그래서 객체가 서로 같은지 여부를 결정하려면 두 메서드를 재정의 해야 한다.
		 HashSet, HashMap, HashTable에서는 객체가 같은지 여부는 데이터를 추가할 때 검사한다.
		 
		 - equals()메서드는 두 객체의 내용(값)이 같은지 비교하는 메서드이고,
		 - hashCode()메서드는 두 객체가 같은 객체인지 비교하는 메서드이다.
		 
		 - equals()메서드와 hashCode()메서드에 관련된 규칙
		 1. 두 객체가 같으면 반드시 같은 hashCode를 가져야 한다.
		 2. 두 객체가 같으면 equals()메서드를 호출했을 때 true를 반환해야 한다.
		 	즉, 객체 a, b가 같다면 a.equals(b)와 b.equals(a) 둘다 true 이어야 한다.
		 3. 두 객체의 hashcode가 같다고 해서 두 객체가 반드시 같은 객체는 아니다.
		 	하지만, 두 객체가 같으면 반드시 hashcode가 같아야 한다.
		 4. equals() 메서드를 override 하면 반드시 hashCode()메서드도 override 해야 한다.
		 5. hashCode()는 기본적으로 Heap에 있는 각 객체에 대한 메모리 주소 매핑 정보를 기반으로한 정수값을 반환한다.
		 	그러므로, 클래스에서 hashCode() 메서드를 override 하지 않으면 절대로 두 객체가 같은 것으로 간주될 수 있다.
		 	
		 	- hashCode()메서드에서 사용하는 '해싱알고리즘'에서 서로 다른 객체에 대하여 같은 hashcode값을 만들어 낼 수 있다.
		 	  그래서 객체가 같지 않더라도 hashcode가 같을 수 있다.
		 */
		
		/**
		 해시를 호출해서 나오는 값은 해시코드 즉 int값임.
		 a객체를 만든 후 a.hashcode = 3; 이 된뒤 b객체를 만든 후 b.hashcode를 했을 때. b.hashcode=3이 나올 수 있음. 0.000000001%확률로 같은 인수값이 될 수 있다.
		 확률이 적지만 그런 경우가 있다.
		 따라서 해쉬 알고리즘을 사용하는 컬렉션들은 중복체크 시 해쉬코드를 사용하여 비교를 하는데,
		 그중에 같을 수 있는 확률이 있다.
		 확률적으로 적더라도 개발자 입장에서는 확률이 있는 것이 문제가 있다.
		 따라서 두 객체의 hashcode가 같다고 해서 두 객체가 반드시 같은 객체가 아니라고 하는 것이다.
		 따라서 hashCode와 equals를 둘다 써서 비교해야하는 것이다.
		 hashCode를 사용했을 때 해시코드가 다른 객체는 무조건 다르다고 가정할 수있다.
		 혹시나 두 객체가 해시코드가 같을때가 문제가 된다. 그럴때 equals를 쓴다.(이렇게 내부적으로 동작하고 있음)
		 처음에 무조건 hashCode 호출하여 비교를 하고 해시코드가 다르면 거기서 끝난다. 
		 하지만 확률적으로 적으나 해시코드가 같은 경우가 존재할 때, 그때 equals를 한번 더 호출해서 확실하게 파악하고 종료한다.
		 결국 해시 알고리즘에서는 hashCode 메서드를 먼저 호출하고 우연치 않게 해시코드가 같으면 equals를 호출해서 명확화 한다.
		 따라서 오버라이드 할때에는 둘다 오버라이드 한다.	
		 */
		
//		해시코드 테스트
//		Person 객체 생성
//		메모리는 다르고 필드값만 같음(p1,p2)
		Person p1 = new Person(1, "홍길동");
		Person p2 = new Person(1, "홍길동");
		Person p3 = new Person(1, "이순신");
		
//		비교
//		오버라이드 한 equals가 있을 때 true가 나옴
//		오버라이드 한 객체가 없을 때, Object 의 equals를 사용한 경우 false가 나옴 
		System.out.println("p1.equals(p2) : " + p1.equals(p2));
//		identity 비교 
		System.out.println("p1 == p1 : " + (p1==p1));
		System.out.println("p1 == p2 : " + (p1==p2));
		
//		중복제거가 잘 되는지 set에 넣어봄
		Set<Person> set = new HashSet<Person>();
//		set은 add하는 시점에 중복체크를 하기때문에 p1과 p2가 같다면 들어가지 않을것이다.
		set.add(p1);
		set.add(p2);
		
		System.out.println("p1, p2 등록 후 데이터");
		for(Person p : set) {
			System.out.println(p.getId() + " : " + p.getName());
		}
		
		System.out.println("add(p3) 성공여부 : " + set.add(p3));
		
		System.out.println("add(p3) 후 데이터");
		for(Person p : set) {
			System.out.println(p.getId() + " : " + p.getName());
		}
		
		System.out.println("remove(p2) 성공여부 : " + set.remove(p2));
		
		System.out.println("remove(p2) 후 데이터");
		for(Person p : set) {
			System.out.println(p.getId() + " : " + p.getName());
		}
		
		
		
	}
}




//우리만의 객체를 만들기위해 클래스화 함.
class Person{
	private int id;
	private String name;
	public Person(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
//	이클립스에서 자동 완성 기능을 활용하여 만듬(hashCode, equals)
//	오버라이드 된 이유는 Object에 기본적으로 제공하는 기능이라서 오버라이드 할 수 있는것임.
//	오버라이드 한 이유는 Object에서 제공하는 기능을 사용하지 않고 새로운 형태로 사용을 하겠다. 라는 의미임.
//	Object는 두 객체가 다르면 false
	@Override
	public int hashCode() {
//		이 알고리즘을 해도 잘 동작할거야.
		final int prime = 31;//소수 : 1과 자기자신으로만 나눌 수 있는 것.
		int result = 1;
		result = prime * result + id;//객체의 id 값
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
//	Object에 정의된 equals를 사용하는 경우 생성한 객체에 대해 equals를 사용하면 값이 같은지 비교할 수 없다.
//	따라서 오버라이드 해서 재정의 해야 한다.
	@Override
	public boolean equals(Object obj) {
		if (this == obj)//this : p1 = p1넣은 경우 (this.p1.equals(object))
			return true;
		if (obj == null)//p1.equals(null)
			return false;
//		getClass() : Object가 속한 클래스정보를 가져옴
//		Object를 만들라면 Class가 존재해야된다. 클래스 없이 Object를 만들 수 없다.
		if (getClass() != obj.getClass())//getClass() : Person , obj.getClass() : Person
			return false;
		Person other = (Person) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	

	
}
