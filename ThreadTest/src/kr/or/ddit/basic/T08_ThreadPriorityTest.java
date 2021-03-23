package kr.or.ddit.basic;

public class T08_ThreadPriorityTest {
	public static void main(String[] args) {
		Thread th1 = new ThreadTest1();
		Thread th2 = new ThreadTest1();
		Thread th3 = new ThreadTest1();
		Thread th4 = new ThreadTest1();
		Thread th5 = new ThreadTest1();
		Thread th6 = new ThreadTest1();
		Thread th7 = new ThreadTest1();
		Thread th8 = new ThreadTest1();
		Thread th9 = new ThreadTest1();
		Thread th10 = new ThreadTest2();
		
//		우선순위는 start() 메서드를 호출하기 전에 설정해야 한다.
		th1.setPriority(1);
		th2.setPriority(1);
		th3.setPriority(1);
		th4.setPriority(1);
		th5.setPriority(1);
		System.out.println("t5의 우선순위 : " + th5.getPriority());
		th6.setPriority(1);
		th7.setPriority(1);
		th8.setPriority(1);
		th9.setPriority(1);
		//쓰레드 클래스에서 getter, setter Priority 를 기본으로 제공해준다.
		System.out.println("t9의 우선순위 : " + th9.getPriority());
		th10.setPriority(10);
		System.out.println("t10의 우선순위 : " + th10.getPriority());
		
		th1.start();
		th2.start();
		th3.start();
		th4.start();
		th5.start();
		th6.start();
		th7.start();
		th8.start();
		th9.start();
		th10.start();
		
		try {
			th1.join();
			th2.join();
			th3.join();
			th4.join();
			th5.join();
			th6.join();
			th7.join();
			th8.join();
			th9.join();
			th10.join();
		}catch(InterruptedException ex) {
			ex.printStackTrace();
		}
		
		//이것은 운영체제의 정책에 따라 먹힐 수 있고 안먹힐 수 있음.. 보장받을 순 없지만 이런게 있다..
		System.out.println("최대 우선순위 : " + Thread.MAX_PRIORITY);
		System.out.println("최소 우선순위 : " + Thread.MIN_PRIORITY);
		System.out.println("보통 우선순위 : " + Thread.NORM_PRIORITY);
	}
}

//대문자를 출력하는 쓰레드
class ThreadTest1 extends Thread {
	@Override
	public void run() {
		for(char ch = 'A' ; ch <= 'Z' ; ch++) {
			System.out.println(ch);
			
//			아무것도 하지 않는 반복문(시간 때우기용)
			for(long i = 1 ; i <= 100000000L ; i++) {}
		}
	}
}

//소문자를 출력하는 쓰레드
class ThreadTest2 extends Thread {
	@Override
	public void run() {
		for(char ch = 'a' ; ch <= 'z' ; ch++) {
			System.out.println(ch);
			
//			아무것도 하지 않는 반복문(시간 때우기용)
			for(long i = 1 ; i <= 100000000L ; i++) {}
		}
	}
}