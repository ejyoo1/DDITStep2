package kr.or.ddit.basic;

import javax.swing.JOptionPane;

/**
 * 멀티 스레드를 활용한 카운트 다운 처리
 * @author 유은지
 * 멀티스레드를 사용한 경우, 모든 스레드가 종료됐을 때 프로그램이 끝나는 것이다.
 * 스레드가 하나라도 남아있을 때, 종료가 되지 않는다.
 */
public class T06_ThreadTest {
	//입력여부를 확인하기 위한 변수 선언
	//모든 쓰레드에서 공통으로 사용될 변수
	public static boolean inputCheck = false;
	
	public static void main(String[] args) {
		Thread th1 = new DataInput();
		Thread th2 = new CountDown();
		
		th1.start();
		th2.start();
	}
}

/**
 * 데이터 입력을 처리하는 클래스
 * @author 유은지
 */
class DataInput extends Thread {
	
	@Override
	public void run() {
		String str = JOptionPane.showInputDialog("아무거나 입력하세요");
		
		//입력이 완료되면 inputCheck 변수를 true로 변경한다.
		T06_ThreadTest.inputCheck = true;
		
		System.out.println("입력한 값은 " + str + "입니다.");
	}
}

/**
 * 카운트다운을 처리하는 스레드 클래스
 * @author 유은지
 */
class CountDown extends Thread{
	@Override
	public void run() {
		for(int i = 10 ; i >= 1 ; i--) {
//			입력이 완료되었는지 여부를 검사하고 입력이 완료되면
//			run()메서드를 종료시킨다.. 즉 현재 스레드를 종료시킨다.
			if(T06_ThreadTest.inputCheck == true) {
				return; //run() 메서드가 종료되면 쓰레드도 끝난다.
			}
			
			System.out.println(i);
			try {
				Thread.sleep(1000);
			}catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		
//		10초가 경과되었는데도 입력이 없으면 프로그램을 종료한다.
		System.out.println("10초가 지났습니다. 프로그램을 종료합니다.");
//		현재 이 코드는 2개의 쓰레드가 있는데 하나는 사용자 입력을 계속 기다리는 쓰레드 하나는 카운트 다운 쓰레드이다.
//		사용자가 입력을 하지 않은 경우 쓰레드가 계속 돌며 프로그램이 끝나지 않는다.
//		값을 입력하지 않아도 아래의 코드를 작성함으로 써 프로그램이 종료될 수 있다.
		System.exit(0);//프로그램을 종료시키는 명령 0:정상적 종료 1은 비정상적 종료
	}
}