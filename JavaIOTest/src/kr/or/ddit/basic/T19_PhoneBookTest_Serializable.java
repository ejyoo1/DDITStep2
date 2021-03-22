package kr.or.ddit.basic;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*
문제) 이름, 주소, 전화번호 속성을 갖는 Phone클래스를 만들고, 이 Phone클래스를 이용하여 
	  전화번호 정보를 관리하는 프로그램을 완성하시오.
	  이 프로그램에는 전화번호를 등록, 수정, 삭제, 검색, 전체출력하는 기능이 있다.
	  
	  전체의 전화번호 정보는 Map을 이용하여 관리한다.
	  (key는 '이름'으로 하고 value는 'Phone클래스의 인스턴스'로 한다.)


실행예시)
===============================================
   전화번호 관리 프로그램(파일로 저장되지 않음)
===============================================

  메뉴를 선택하세요.
  1. 전화번호 등록
  2. 전화번호 수정
  3. 전화번호 삭제
  4. 전화번호 검색
  5. 전화번호 전체 출력
  0. 프로그램 종료
  번호입력 >> 1  <-- 직접 입력
  
  새롭게 등록할 전화번호 정보를 입력하세요.
  이름 >> 홍길동  <-- 직접 입력
  전화번호 >> 010-1234-5678  <-- 직접 입력
  주소 >> 대전시 중구 대흥동 111  <-- 직접 입력
  
  메뉴를 선택하세요.
  1. 전화번호 등록
  2. 전화번호 수정
  3. 전화번호 삭제
  4. 전화번호 검색
  5. 전화번호 전체 출력
  0. 프로그램 종료
  번호입력 >> 5  <-- 직접 입력
  
  =======================================
  번호   이름       전화번호         주소
  =======================================
   1    홍길동   010-1234-5678    대전시
   ~~~~~
   
  =======================================
  출력완료...
  
  메뉴를 선택하세요.
  1. 전화번호 등록
  2. 전화번호 수정
  3. 전화번호 삭제
  4. 전화번호 검색
  5. 전화번호 전체 출력
  0. 프로그램 종료
  번호입력 >> 0  <-- 직접 입력
  
  프로그램을 종료합니다...
  
*/
public class T19_PhoneBookTest_Serializable {
	
	private Map<String, Phone2> phoneBookMap;
	
	public T19_PhoneBookTest_Serializable() {
		phoneBookMap = new HashMap<String, Phone2>();
	}
	
	// 메뉴를 출력하는 메서드
	public void displayMenu(){
		System.out.println();
		System.out.println("메뉴를 선택하세요.");
		System.out.println(" 1. 전화번호 등록");
		System.out.println(" 2. 전화번호 수정");
		System.out.println(" 3. 전화번호 삭제");
		System.out.println(" 4. 전화번호 검색");
		System.out.println(" 5. 전화번호 전체 출력");
		System.out.println(" 0. 프로그램 종료");
		System.out.print(" 번호입력 >> ");		
	}
	
	// 프로그램을 시작하는 메서드
	public void phoneBookStart(){
		Scanner scan = new Scanner(System.in);
		System.out.println("===============================================");
		System.out.println("   전화번호 관리 프로그램(파일로 저장되지 않음)");
		System.out.println("===============================================");
		File f1 = new File("d:/D_Other/ejyooPhoneBook.bin");
		
		if(f1.exists()) {
			System.out.println(f1.getAbsolutePath() + "은 존재합니다.");
			//파일로 저장된 폰북 불러오기
			phoneBookImport();
		}	
			
		
		while(true){
			
			displayMenu();  // 메뉴 출력
			
			int menuNum = scan.nextInt();   // 메뉴 번호 입력
			System.out.println();
			
			switch(menuNum){
				case 1 : insert();		// 등록
					break;
				case 2 : update();		// 수정
					break;
				case 3 : delete();		// 삭제
					break;
				case 4 : search();		// 검색
					break;
				case 5 : displayAll();	// 전체 출력
					break;
				case 0 : 
					phoneBookExport(); // 전화번호 파일 생성
					System.out.println("프로그램을 종료합니다...");
					return;
				default :
					System.out.println("잘못 입력했습니다. 다시입력하세요.");
			} // switch문
		} // while문
	}
	
	/**
	 * 파일로 저장된 폰북 Import
	 */
	
	private void phoneBookImport() {
		//저장한 객체를 읽어와 출력하기
		try {
			//입력용 스트림 객체 생성
			ObjectInputStream ois = 
					new ObjectInputStream(
							new BufferedInputStream(
									new FileInputStream("d:/D_Other/ejyooPhoneBook.bin")));
			
			Object obj = null;
		
		
			while((obj = ois.readObject()) != null) { //ois.readObject() 역직렬화(내부적으로 역직렬화 작업 수행)
				//마지막에 다다르면 EOF 예외 발생함.
				Phone2 pho = (Phone2) obj;
				phoneBookMap.put(pho.getName(), new Phone2(pho.getName(), pho.getTel(), pho.getAddr()));
			}
			ois.close();
		
		}catch(IOException ex) {//ioet 스페이스바 [마지막에 다다르면 EndOfFileException을 이용하여 끝났다 라는 것을 판단함]
			ex.printStackTrace();
			//더이상 읽어올 객체가 없으면 예외 발생함.
			System.out.println("출력 작업 끝...");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * 전화번호 파일을 생성하는 메서드
	 * @throws IOException 
	 */
	private void phoneBookExport() {
		//맵을 읽어온다.
		//EntrySet으로 변환하여 출력(구분자 넣을것이기 때문에 이 방법을 사용함)
		Set<String> keySet = phoneBookMap.keySet();
		try {
			ObjectOutputStream oos = 
					new ObjectOutputStream(
							new BufferedOutputStream(
									new FileOutputStream("d:/D_Other/ejyooPhoneBook.bin")));
			
			System.out.println("향상된 for문을 이용한 방법");
			for(String key : keySet) {
				System.out.println(key + " : " + phoneBookMap.get(key));
			
				
//				쓰기 작업
				oos.writeObject(phoneBookMap.get(key));
				System.out.println("바이너리 형태로 쓰기 작업 완료");
				
			}
			oos.close();	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 전체 자료를 출력하는 메서드
	 */
	private void displayAll() {
		Set<String> keySet = phoneBookMap.keySet();
		
		System.out.println("===================================================================");
		System.out.println("번호    이름    전화번호    주소");
		System.out.println("===================================================================");
		
		if(keySet.size() == 0) {
			System.out.println("등록된 전화번호 정보가 없습니다.");
		} else {
			Iterator<String> it = keySet.iterator();
			int cnt = 0;
			while(it.hasNext()) {
				cnt++;
				String name = it.next();
				Phone2 p = phoneBookMap.get(name);
				System.out.println(" " + cnt + "\t" + p.getName()
									+ "\t" + p.getTel()
									+ "\t" + p.getAddr());
			}
		}
		
	}

	/**
	 이름을 이용한 전화번호 정보를 검색하는 메서드
	 */
	private void search() {
		Scanner scan = new Scanner(System.in);
		System.out.println("검색할 전화번호 정보를 입력하세요. > ");
		
		System.out.print("이름 > ");
		String name = scan.nextLine();
		
		Phone2 p = phoneBookMap.get(name);
		
		if(p == null) {
			System.out.println(name + "씨의 전화번호 정보가 없습니다.");
		}else {
			System.out.println(name + "씨의 전화번호 정보");
			System.out.println("이름 : " + p.getName());
			System.out.println("전화번호 : " + p.getTel());
			System.out.println("주소 : " + p.getAddr());
		}
	}

	/**
	 전화번호 정보를 삭제하는 메서드
	 */
	private void delete() {
		Scanner scan = new Scanner(System.in);
		System.out.println("삭제할 전화번호 정보를 입력하세요. > ");
		
		System.out.print("이름 > ");
		String name = scan.nextLine();
		
//		remove(key) => 삭제 성공하면 삭제된 value 값을 반환하고 실패하면 null 반환함
		if(phoneBookMap.remove(name) == null){
			System.out.println(name + "씨는 등록된 사람이 아닙니다.");
		} else {
			System.out.println(name + "씨 정보를 삭제했습니다.");
		}
		System.out.println("삭제 작업 완료...");
	}

	/**
	 전화번호를 수정하는 메서드
	 */
	private void update() {
		Scanner scan = new Scanner(System.in);
		System.out.println("수정할 전화번호 정보를 입력하세요. > ");
		
		System.out.print("이름 > ");
		String name = scan.nextLine();
//		만약 next() 로 받았을 때, next 는 띄어쓰기 기준으로 구분이 되는데 next가 종료되고 나면 엔터가 자동으로 들어간다.
//		그래서 next()로 받은 경우 nextLine()을 한번 더 써서 next()메서드 수행 결과로 생긴 엔터를 nextLine으로 제거한다.

		
//		수정할 자료가 있는지 검사
		if(phoneBookMap.get(name) == null) {
			System.out.println(name + "씨의 전화번호 정보가 없습니다.");
			return;
		}
		
		System.out.print("전화번호 > ");
		String tel = scan.nextLine();
		
		System.out.print("주소 > ");
//		입력 버퍼에 남아있는 엔터키 값까지 읽어와 버리는 역할을 수행함.
		String addr = scan.nextLine();
		
		Phone2 p = new Phone2(name, tel, addr);
		phoneBookMap.put(name, p);
		
		System.out.println(name + "씨 정보 수정 완료");
		
	}

	/**
	 새로운 전화번호 정보를 등록하는 메서드
	 이미 등록한 사람은 등록되지 않는다.
	 */
	private void insert() {
		Scanner scan = new Scanner(System.in);
		System.out.println("새롭게 등록할 전화번호 정보를 입력하세요. > ");
		System.out.print("이름 > ");
		String name = scan.nextLine();
		
//		이미 등록된 사람인지 분석
//		get() 메서드로 값을 가져올 때 가져올 자료가 없으면 null을 반환한다.
		if(phoneBookMap.get(name) != null) {
			System.out.println(name + "씨는이미 등록된 사람입니다.");
			return;
		}
		System.out.print("전화번호 > ");
		String tel = scan.nextLine();
		
		System.out.print("주소 > ");
//		입력 버퍼에 남아있는 엔터키 값까지 읽어와 버리는 역할을 수행함.
		String addr = scan.nextLine();
		
		phoneBookMap.put(name, new Phone2(name, tel, addr));
		System.out.println(name + "씨 등록완료...");
		
		
	}

	public static void main(String[] args) {
		new T19_PhoneBookTest_Serializable().phoneBookStart();
	}
}



/**
 전화번호 정보를 저장할 수 있는 VO 클래스
 */
class Phone2 implements Serializable {
	private String name;	//이름
	private String tel;		//전화번호
	private String addr;	//주소
	
	public Phone2(String name, String tel, String addr) {
		this.name = name;
		this.tel = tel;
		this.addr = addr;
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

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Override
	public String toString() {
		return "Phone [name=" + name + ", tel=" + tel + ", addr=" + addr + "]";
	}
	
	
}

