package kr.or.ddit.basic;

public class T09_ThreadDaemonTest {
	public static void main(String[] args) {
		AutoSaveThread autoSave = new AutoSaveThread();
		
//		데몬 스레드로 설정하기(start() 메서드 호출하기 전에 설정한다.)
		//스타드 전에 데몬이 설정이 되어야함. 스타트 이후에는 의미없음.
//		자동 저장 기능은 프로그램이 종료되는 시점에 알아서 종료되길 원한다.
		//일반쓰레드를 보조해주는 데몬쓰레드.
		//일반쓰레드가 살아있는 동안만 돌아가다가 일반쓰레드가 종료되면 끝
		autoSave.setDaemon(true);//이것을 사용하여 true로 설정하면 데몬스레드로 동작한다. 
								//이것을 쓰지 않으면 일반스레드 처럼 계속 동작한다.
								//(메인스레드(일반스레드)가 종료되면 데몬 스레드도 죽는다.)
		autoSave.start();
		
		try {
			for(int i = 1 ; i <= 20 ; i++) {
				System.out.println("작업 " + i);
				Thread.sleep(1000);
			}
		}catch(InterruptedException ex) {
			ex.printStackTrace();
		}
		
		System.out.println("메인 스레드 종료...");
	}
}

/**
 * 자동저장하는 스레드 클래스(3초에 한번씩 저장하기)
 * @author PC-19
 *
 */
class AutoSaveThread extends Thread {
	public void save() {
		System.out.println("작업 내용을 저장합니다...");
	}
	
	@Override
	public void run() {
		while(true) {
			try { 
				Thread.sleep(3000);
			}catch(InterruptedException ex) {
				ex.printStackTrace();
			}
			save();//저장기능 호출
		}
	}
}