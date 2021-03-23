package kr.or.ddit.basic;

//총 4개의 쓰레드가 돌아가는 멀티쓰레드 코드
public class T02_ThreadTest {
	public static void main(String[] args) {
		//멀티 쓰레드 프로그램 방식
		
		//방법 1 : Thread클래스를 상속한 class의 인스턴스를 생성한 후 이 인스턴스의 start() 메서드를 호출한다.
		MyThread1 th1 = new MyThread1();
//		구동
		th1.start();//무조건 해야됨. start를 함으로서 독자적인 Thread 역할을 수행할 수 있게됨.
		
		//방법 2 : Runnable 인터페이스를 구현한 class의 인스턴스를 생성한 후 이 인스턴스를 Thread 객체의 인스턴스를 생성할 때 
//				생성자의 매개변수로 넘겨준다.
//				이때, 생성된 Thread객체의 인스턴스 start() 메서드를 호출한다.
		
		MyThread2 r1 = new MyThread2();
		Thread th2 = new Thread(r1);
		th2.start();//쓰레드는 독립적인 쓰레드로 동작하기 위해 start()를 실행해야 함.
		
		//방법 3 : 익명클래스를 이용하는 방법
		//Runnable 인터페이스를 구현한 익명클래스를 Thread 인스턴스를 생성할 때 매개변수로 넘겨준다.
		//별도의 클래스 생성 없이 만듬
		//한번만 생성해서 안쓸경우에 이 방법을 쓰고 계속 쓰는 경우 방법 2를 사용한다.
		Thread th3 = new Thread(new Runnable() {

			@Override
			public void run() {
				//필요한 비즈니스 로직 작성
				for(int i = 1 ; i <= 200 ; i++) {
					System.out.println("@");
				
					try {
//						Thread.sleep(시간) => 주어진 시간동안 작업을 잠시 멈춘다.
//						시간은 밀리세컨드 단위를 사용한다.
//						즉 1000은 1초를 의미한다.
						Thread.sleep(100);//util 기능 0.1초 기다림
						
						
					}catch(InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		th3.start();
		System.out.println("main메서드 작업 끝...");
	}
}

class MyThread1 extends Thread {
	@Override
	public void run() {//쓰레드에서는 run이 중요함 (클래스의 메인과 같은 존재임)
		for(int i = 1 ; i <= 200 ; i++) {
			System.out.println("*");
		
			try {
//				Thread.sleep(시간) => 주어진 시간동안 작업을 잠시 멈춘다.
//				시간은 밀리세컨드 단위를 사용한다.
//				즉 1000은 1초를 의미한다.
				Thread.sleep(100);//util 기능 0.1초 기다림
				
				
			}catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
}

// 멀티 상속이 가능하도록 하기위해 implements를 사용하여 쓰레드를 만들 수 있게 하기 위해 인터페이스로 빼놓은 것임.(이미 다른 클래스를 상속을 받았더라면..)
class MyThread2 implements Runnable{
	@Override
	public void run() {//쓰레드에서는 run이 중요함 (클래스의 메인과 같은 존재임) runnable 들어가보면 runnable 에 run이 있음.
		for(int i = 1 ; i <= 200 ; i++) {
			System.out.println("$");
		
			try {
//				Thread.sleep(시간) => 주어진 시간동안 작업을 잠시 멈춘다.
//				시간은 밀리세컨드 단위를 사용한다.
//				즉 1000은 1초를 의미한다.
				Thread.sleep(100);
				
				
			}catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
}