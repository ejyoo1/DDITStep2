package kr.or.ddit.basic;

/**
 * Thread의 stop() 메서드를 호출하면 스레드가 바로 멈춘다.
 * 이때 사용하던 자원을 정리하지 못하고 프로그램이 종료되어서
 * 나중에 실행되는 프로그램에 영향을 줄 수 있다.
 * 그래서 현재는 stop()메서드는 비추천(Deprecated)으로 되어있다.
 * @author 유은지
 *
 */
public class T13_ThreadStopTest {
	public static void main(String[] args) {
		ThreadStopEx1 th = new ThreadStopEx1();
		th.start();
		
		try {
			Thread.sleep(1000);//1초
		}catch(InterruptedException ex) {
			ex.printStackTrace();
		}
		//방법1) stop을 걸어주는 방법
//		th.stop();//이 표시는 제공은 하지만 앞으로는 쓰지 마세요 라고 나온것임(비추천기능)
		th.setStop(true); //상태 플래그 값을 이용한 종료 방법

		
		//th.stop();
		th.setStop(true);//상태 클래그 값을 이용한 종료방법
		
		//방법 2) interrupt() 당한 쓰레드에 대한 인터럽 걸린 정보를 갖고올 수 있다.
		//interrupt() 메서드를 이용한 스레드 멈추기
		ThreadStopEx2 th2 = new ThreadStopEx2();
		try {
			Thread.sleep(1000);
		}catch(InterruptedException ex) {
			ex.printStackTrace();
		}
		th2.interrupt();
		System.out.println("자원 정리 중...");
		System.out.println("실행 종료.");
	}
}

class ThreadStopEx1 extends Thread {
	private boolean stop;
	
	public void setStop(boolean stop) {
		this.stop = stop;
	}
	
	@Override
	public void run() {
		while(!stop) {
			System.out.println("스레드 처리중 ....");
		}
		System.out.println("자원 정리 중...");
		System.out.println("실행 종료.");
	}
}

//interrupt()메서드를 이용하여 스레드를 멈추게 하는 방법(예외처리 이용방법) 
class ThreadStopEx2 extends Thread{
	
	@Override
	public void run() {
		/*
			방법1 => sleep() 메서드나 join() 메서드 등을 사용했을 때 interrupt() 메서드를 호출하면
			InterruptedException 이 발생한다.
		 
		try {
			while(true) {//인터럽 걸리면 종료가 됨.
				System.out.println("스레드 처리중...");
				Thread.sleep(1);//중요
			}
		}catch(InterruptedException ex) { } //인터럽이 거리면 발생하는 (외부에서 인터럽을 넘기면 여기로 가는)
		*/
		
		// 방법 2 => interrupt() 메서드가 호출되었는지 검사하기 (이게 더 많이 사용할듯?)
		while(true) {
			System.out.println("스레드 처리 중 ...");
			
			/*//검사방법1 => 스레드의 인스턴스 객체용 메서드를 이용하는 방법 (인터럽트 걸리면 계속 걸려있는 상태임)
			if(this.isInterrupted()) {//interrupt() 메서드가 호출되면 true(인터럽트가 걸렸는지 안걸렸는지 정보를 가져올 수 있음)
				System.out.println("인스턴스용 isInterrupted()");
				break;//인터럽트 걸렸으면 끝내기(강제로 끝내버리면 duplicated와 같음)(외부에서 혹시 인터럽트 메서드 걸었으면 true값이 오며 반복을 빠져나감)
			}*/
			
			//검사방법2 => 스레드의 정적 메서드를 이용하는 방법
			if(Thread.interrupted()) {//interrupted() 가 호출되면 true(기존 인터럽트 설정값을 가져와서 그 반대로 돌려놓음. 두번 연달아 호출하면 반대로 나옴)
				System.out.println("정적 메서드 interrupted()");
				break;
			}
		}
		System.out.println("자원 정리 중...");
		System.out.println("실행 종료.");
	}
}