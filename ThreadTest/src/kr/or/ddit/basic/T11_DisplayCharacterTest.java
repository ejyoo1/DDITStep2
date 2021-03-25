package kr.or.ddit.basic;

/**
 * 3개(명)의 스레드가 각각 알파벳 대문자를 출력하는데 출력을 끝낸 순서대로 결과물을 나타내는 프로그램 작성하기
 * @author 유은지
 *
 */
public class T11_DisplayCharacterTest {
	
	static String strRank = "";
	
	public static void main(String[] args) {
		DisplayCharacter[] disChars = new DisplayCharacter[] {
			new DisplayCharacter("홍길동"),
			new DisplayCharacter("일지매"),
			new DisplayCharacter("변학도")
		};
		
		for(int i = 0 ; i < disChars.length ; i++) {
			disChars[i].start();
		}
		
		for(DisplayCharacter dc : disChars) {//disChars가 끝날때까지 기다리려고 기다리는 것임
			try {
				dc.join();//게임이 다 끝날때 까지 기다림
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("경기 끝...");
		System.out.println("-------------------------------------");
		System.out.println();
		System.out.println("경기 결과");
		System.out.println("순위 : " + strRank);
	}
}

//대문자 출력하는 스레드 클래스
class DisplayCharacter extends Thread {
	private String name;
	
//	생성자
	public DisplayCharacter(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		for(char ch = 'A' ; ch <= 'Z'; ch++) {
			System.out.println(name + "의 출력 문자 : " + ch);
			
			try {
				// sleep()메서드의 값을 200~500 사이의 난수로 한다.
				Thread.sleep((int)(Math.random() * 301 + 200));
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println(name + " 출력 끝....");
		T11_DisplayCharacterTest.strRank += name + " ";
		
	}
}