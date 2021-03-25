package kr.or.ddit.basic;

/**
 * 동기화 : 1번 쓰레드가 작업할때 2번쓰레드는 진입을 못한다.
 * 공유객체에 여러개 쓰레드가 접근했을 때 한쓰레드는 작업하고 한 쓰레드는 대기한다.
 * 동기화는 성능이 저하될 우려가 있기에 동기화 블럭을 사용하도록 한다.
 * @author PC-19
 *
 */
public class T14_SyncThreadTest {
	public static void main(String[] args) {
		//쓰레드 2개를 만들어 동시에 작업을 해보자
		ShareObject sObj = new ShareObject();
		
		WorkerThread th1 = new WorkerThread("1번쓰레드", sObj);
		WorkerThread th2 = new WorkerThread("2번쓰레드", sObj);
		
		th1.start();
		th2.start();
	}
}

//공통으로 사용할 객체
class ShareObject {
	private int sum = 0;
	
	//동기화 하는 방법 1 : 메서드 자체에 동기화 설정하기 
	/*
	public synchronized void add() {
		for(int i = 0 ; i < 1000000000 ; i++) { }//동기화 처리 전까지의 시간벌기용
			
		int n = sum;
		n += 10; //기존 값에서 10 씩 증가
		sum = n;
		
		System.out.println(Thread.currentThread().getName() //현재 쓰레드 객체에 접근하여 이름을 가져옴
				+ "합계 : " + sum);
	}
	
	
	public int getSum() {
		return sum;
	}
	*/
	//방법2. 동기화 블럭 사용하기(원하는 동기화 블럭을 설정할 수 있다) 크리티컬 섹션에만 적용함. 이것이 좀더 세밀하게 동기화를 제어함.
	public void add() {
		synchronized (this) {//공유하는 객체 삽입 (this:현재 나 자신의 객체) (sum 할때까지만 동기화 하겠다)
			for(int i = 0 ; i < 1000000000 ; i++) { }//동기화 처리 전까지의 시간벌기용
		
			//동기화 처리 작성
			int n = sum;
			n += 10; //기존 값에서 10 씩 증가
			sum = n;
		
			System.out.println(Thread.currentThread().getName() //현재 쓰레드 객체에 접근하여 이름을 가져옴
				+ "합계 : " + sum);
		}
	}
	
	
	public int getSum() {
		return sum;
	}
}

//작업을 수행하는 스레드 클래스
class WorkerThread extends Thread{
	ShareObject sObj;
	
	public WorkerThread(String name, ShareObject sObj) {//자기자신 이름과 객체를 받음
		super(name);
		this.sObj = sObj;
	}
	
	@Override
	public void run() {
		for(int i = 1 ; i <= 10; i++) {
			sObj.add();
		}
	}
}