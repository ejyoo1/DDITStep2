package kr.or.ddit.basic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 부모 클래스가 Serializable 인터페이스를 구현하고 있지 않을 경우
 * 부모 객체의 필드값 처리 방법 
 * 
 * 1. 부모클래스가 Serializable 인터페이스를 구현하도록 해야한다.
 * 2. 자식 클래스에 writeObject()와 readObject()메서드를 이용하여 부모객체의 필드값을 처리할 수 있도록 직접 구현한다.
 * @author PC-19
 *
 */
public class T17_NonSerializableParentTest {
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		FileOutputStream fos = 
				new FileOutputStream("d:/D_Other/nonSerializableTest.bin");
		
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		Child child = new Child();
		child.setParentName("부모");
		child.setChildName("자식");
		oos.writeObject(child);//직렬화
		oos.flush();
		oos.close();
		
		FileInputStream fis = new FileInputStream("d:/D_Other/nonSerializableTest.bin");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Child child2 = (Child)ois.readObject();// 역직렬화 [ClassNotFoundException] 예외
		System.out.println("parentName : " + child2.getParentName());
		System.out.println("childName : " + child2.getChildName());
		
		ois.close();
		fis.close();//생략 가능
	}
}


/**
 * Serializable을 구현하지 않은 부모 클래스
 * @author PC-19
 *
 */
//클래스 정의
class Parent {//이영역에 implements Serializable 없이 사용하고자 함. child에 writeObject 구현
	private String parentName;

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}

/**
 * Serializable을 구현한 자식 클래스
 * @author PC-19
 *
 */
class Child extends Parent implements Serializable{
	private String childName;

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}
	
	//private void writeObject 이 형태는 정해져 있는 것이므로 동일하게 작성
	/**
	 * 직렬화 될 때 자동으로 호출됨
	 * (접근 제한자가 private가 아니면 자동으로 호출되지 않는다.)
	 * 부모객체를 수동으로 writeUTF로 작성한 뒤 원래 기능인 defaultWriteObject()를 호출함
	 * 가져올때도 동일함.
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream out) throws IOException{
		//String 값이기 때문에 writeUTF 로 저장
		//ObjectOutputStream 개체의 메서드 이용하여 부모객체 필드값 처리하기
		out.writeUTF(getParentName());//DataOutputStream에도 writeUTF가 있음
		
//		원래 기능을 호출하게 됨.
		out.defaultWriteObject();
	}
	
	/**
	 * 역직렬화 될 때 자동으로 호출됨
	 * (접근 제한자가 private이 아니면 자동호출되지 않음)
	 * write와 마찬가지로 수동으로 저장했던 문자열 하나를 readUTF()를 사용하여 읽어오고
	 * 디폴트로 읽는 부분을 실행한다.
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream in) 
			throws IOException, ClassNotFoundException{
//		ObjectInputStream객체의 메서드를 이용하여 부모 객체 필드값처리하기
		setParentName(in.readUTF());
		
		in.defaultReadObject();
	}
}