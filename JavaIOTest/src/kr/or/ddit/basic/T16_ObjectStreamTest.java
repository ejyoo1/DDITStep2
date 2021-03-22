package kr.or.ddit.basic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class T16_ObjectStreamTest {
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
//		Member 인스턴스 생성
		Members mem1 = new Members("홍길동", 20, "대전");
		Members mem2 = new Members("일지매", 30, "서울");
		Members mem3 = new Members("이몽룡", 40, "대구");
		Members mem4 = new Members("성춘향", 50, "부산");
		
		
		//객체를 파일에 저장하기
		
		//출력용 스트림 객체 생성
		//보조스트림은 여러개가 들어가도 상관없음.(기반스트림만 있으면 됨) 기반 스트림이 중요
		//버퍼 스트림이 없어도 프로그램 수행에 영향이 없다. 읽을때 빠르게 읽기 위해 사용하는 것이다.
		ObjectOutputStream oos = 
			new ObjectOutputStream(
					new BufferedOutputStream(
							new FileOutputStream("d:/D_Other/memObj.bin")));
	//			쓰기 작업
		oos.writeObject(mem1);//직렬화
		oos.writeObject(mem2);//직렬화
		oos.writeObject(mem3);//직렬화
		oos.writeObject(mem4);//직렬화
		
		System.out.println("바이너리 형태로 쓰기 작업 완료");
		oos.close();
		
		//=================================================
		//저장한 객체를 읽어와 출력하기
		
		//입력용 스트림 객체 생성
		ObjectInputStream ois = 
				new ObjectInputStream(
						new BufferedInputStream(
								new FileInputStream("d:/D_Other/memObj.bin")));
		
		Object obj = null;
		
		try {
			while((obj = ois.readObject()) != null) { //ois.readObject() 역직렬화(내부적으로 역직렬화 작업 수행)
				//마지막에 다다르면 EOF 예외 발생함.
				Members mem = (Members) obj;
				System.out.println("이름 : " + mem.getName());
				System.out.println("나이 : " + mem.getAge());
				System.out.println("주소 : " + mem.getAddr());
				System.out.println("===========================");
			}
			ois.close();
		
		}catch(IOException ex) {//ioet 스페이스바 [마지막에 다다르면 EndOfFileException을 이용하여 끝났다 라는 것을 판단함]
			ex.printStackTrace();
			//더이상 읽어올 객체가 없으면 예외 발생함.
			System.out.println("출력 작업 끝...");
		}
	}//close main
}//close class

/**
 * 회원 정보 VO
 * @author PC-19
 *
 */
//Serializable:표식 인터페이스라고 한다.ㅣ 내부적으로 무엇을 가지고 있지 않지만 직렬화 하는 의미를 부여할 수 있기 때문이다.
class Members implements Serializable {
//		 자바는 Serializable 인터페이스를 구현한 클래스만 직렬화할 수 있도록 제한하고 있음
//		구현하지 않으면 직렬화 작업 시 java.io.NotSerializableException 예외 발생!
	
	/**
	 * transient => 직렬화가 되지 않을 멤버변수에 지정한다.
	 * (static 필드도 직렬화가 되지 않는다.) - static 변수는 객체에 대한 정보가 아닌 다른 클래스와 공유를 위한 정보이므로 직렬화 대상에서 제외된다.
	 * 직렬화가 되지 않는 멤버변수는 기본값으로 저장된다.
	 * (참조형 변수 : null, 숫자형 변수 : 0)
	 */
	
	private String name;
	private transient int age;//보안상의 이유 등 이런 경우로 인해 직렬화를 원하지 않을 때 사용한다. (중요한 데이터인 경우 사용) (일시적[transient]으로 갖고있는 정보니까 안하겠다.)
	private String addr;
	
	public Members(String name, int age, String addr) {
		super();
		this.name = name;
		this.age = age;
		this.addr = addr;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
}

