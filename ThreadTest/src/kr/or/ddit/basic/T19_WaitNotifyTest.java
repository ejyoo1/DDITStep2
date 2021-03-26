package kr.or.ddit.basic;

/**
 * join과 같이 쓰레드 객체가 갖고 있음
 * WaitNotifyTest는 Object 가 가지고 있음
 * 동기화 처리를 했는데, 동기화 처리 안에 있는 영역의 쓰레드가 효율적인 작업을 하면 괜찮은데
 * 할 작업도 없는데 그 안에서 죽치고 있다면,
 * 다른 급한 작업들은 이 쓰레드가 종료될 때 까지 기다리게 된다.
 * 굳이 있을 필요가 없으면 그 쓰레드는 나오고 다른 쓰레드가 작업할 수 있도록 하는 메서드가 Wait과 Notify임
 * 그래서 동기화에만 의미있는 메서드가 Wait Notify이다.
 * @author 유은지
 * 대기실로 빠지면서 기존에 자기가 걸어놨던 락을 풀면서 나간다.
 * 그러면 기다리던 다른 쓰레드가 락을 획득하면서 들어오게 된다.
 * 대기실(쓰레드 : Wait 상태)로 빠질 때, 주구장창 wait 하고있다.
 * 지금 급한 쓰레드가 작업 완료 후 대기실에 있는 쓰레드를 불러올 때 Notify를 사용한다. 
 * Notify All 은 기다리던 쓰레드를 모두 가져온다. => Notify를 사용하면 랜덤하게 대기실에서 대기하고 있는 쓰레드를 랜덤으로 뽑아올때, 원하지 않는 쓰레드를 뽑아올 수 있다. 그래서 NotifyAll을 쓰는것이다.
 */
/**
 * wait() 메서드 => 동기화 영역에서 락을 풀고 wait-set영역(공유객체별 존재)으로 이동시킨다.
 * notify() 또는 notifyAll() 메서드 => wait-set영역에 있는 스레드를 깨워서 실행될 수 있도록 한다.
 * 				(notify()는 하나, notifyAll()은 모두 깨운다.)
 * => wait()과 nofity(), notifyAll() 메서드는 동기화 영역에서만 실행할 수 있고,
 * 	Object클래스에서 제공하는 메서드이다.
 * @author 유은지
 *
 *
 *하나의 쓰레드에 읽고 쓰는 작업이 동시에 존재
 */


public class T19_WaitNotifyTest {
	public static void main(String[] args) {
		
		//공유객체 선언
		WorkObject workObj = new WorkObject();
		
		//쓰레드 객체 생성
		Thread th1 = new ThreadA(workObj);
		Thread th2 = new ThreadB(workObj);
		
		//쓰레드 시작
		th1.start();
		th2.start();
		
	}
}

//공통으로 사용할 객체
class WorkObject{
	public synchronized void methodA() {//lock걸음 : synchronized
		System.out.println("methodA()메서드 작업중...");
		
		notify();//대기실에 있는 쓰레드 깨움(없으면 말고)
		
		try {
			wait();//지금 들어온 쓰레드가 대기실로 이동됨(앞에 this가 생략되어있음)
		}catch(InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	public synchronized void methodB() {
		System.out.println("methodB()메서드 작업중...");
		
		notify();//혹시나 모를 대기실에 있는 쓰레드를 깨움(없으면 말고) A가 대기실에 있으므로 깨움
		
		try {
			wait();//A를 깨워서 B는 대기실로 들어감
		}catch(InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}


//workObj의 methodA()메서드만 호출하는 스레드(10번)
class ThreadA extends Thread {
	private WorkObject workObj;
	
	public ThreadA(WorkObject workObj) {
		this.workObj = workObj;
	}
	
	@Override
	public void run() {
		for(int i = 1 ; i <= 10 ; i++) {
			workObj.methodA();
		}
		System.out.println("ThreadA 종료");
	}
}

//workObj의 methodB()메서드만 호출하는 스레드(10번)
class ThreadB extends Thread {
	private WorkObject workObj;
	
	public ThreadB(WorkObject workObj) {
		this.workObj = workObj;
	}
	
	@Override
	public void run() {
		for(int i = 1 ; i <= 10 ; i++) {
			workObj.methodB();
		}
		System.out.println("ThreadB 종료");
	}
}