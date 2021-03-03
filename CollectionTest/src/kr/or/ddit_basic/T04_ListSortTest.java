package kr.or.ddit_basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * Comparable 및 Comparator 예제
 *
 */
public class T04_ListSortTest {
	public static void main(String[] args) {
		List<Member> memList = new ArrayList<Member>();
		memList.add(new Member(1, "홍길동", "010-1111-1111"));
		memList.add(new Member(4, "변학도", "010-2222-2222"));
		memList.add(new Member(6, "성춘향", "010-3333-3333"));
		memList.add(new Member(3, "이순신", "010-4444-4444"));
		memList.add(new Member(2, "강감찬", "010-5555-5555"));
		memList.add(new Member(5, "일지매", "010-6666-6666"));
		
		System.out.println("정렬 전 : ");
		for(Member mem : memList) {
			System.out.println(mem);
		}
		System.out.println("=============================");
		
		Collections.sort(memList);//정렬하기
		
		System.out.println("이름의 오름차순으로 정렬 후");
		for(Member mem : memList) {
			System.out.println(mem);
		}
		
		System.out.println("=============================");
		System.out.println("총점 같을 때 학번 정렬");
//		외부 정렬 기준을 이용한 정렬하기
		Collections.sort(memList, new SortNumDesc());
		for(Member mem : memList) {
			System.out.println(mem);
		}
	}	
}

/*
 * Member의 번호(num)의 내림차순으로 정렬하기
 * */
class SortNumDesc implements Comparator<Member>{

	@Override
	public int compare(Member mem1, Member mem2) {
		/*
		if(mem1.getNum() > mem2.getNum()) {
			return -1;//규칙임 (음수)
		}else if(mem1.getNum() == mem2.getNum()) {
			return 0;//규칙임 ( 같음)
		}else {
			return 1;//규칙임 (양수)
		}
		*/
		
		//Wrapper 클래스에서 제공되는 메서드를 이용하는 방법
		//내림차순인 경우에는 -1을 곱해준다.
		return new Integer(mem1.getNum()).compareTo(mem2.getNum())* -1;
	}
	
}

class Member implements Comparable<Member>{
//	기본적으로 필요한 멤버변수 정의
	private int num;//번호
	private String name;//이름
	private String tel;//전화번호
	
//	이름을 기준으로 오름차순 정렬이 되도록 설정한다.
	@Override
	public int compareTo(Member mem) {
//		아무것도 붙이지 않으면 오름차순 정렬이되고
//		내림차순으로 정렬하고 싶은 경우에는 -1을 붙인다.
		return this.getName().compareTo(mem.getName());
	}

	public Member() {
//		나 자신을 의미하는 생성자 => 아래는 오류남
//		this();
//		this(5,"홍길순","000-1234-5678");
//		this를 사용하여 생성자를 호출하지 않은 경우에는 기본값으로 초기화된다
//		Member [num=0, name=null, tel=null]
	}
	
	public Member(int num, String name, String tel) {
//		모든 생성자는 Object생성자를 호출해야하기 때문에 super()를 호출한다.
//		하지만 super()가 없어도 컴파일러가 알아서 호출한다.
//		super();
//		this를 쓰지 않아도 컴파일러가 알아서 붙여준다.
//		하지만 여기에서는 파라미터로 동일한 변수가 있기 때문에 this를 써야한다.
		this.num = num;
		this.name = name;
		this.tel = tel;
	}

//	오버라이드라는 것을 쓰는 이유가 오버라이드 해야되는 메서드가 있는데 
//	컴파일 시 그것의 오타를 막기위해 사용한다.
	@Override
	public String toString() {
		return "Member [num=" + num + ", name=" + name + ", tel=" + tel + "]";
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	 
}