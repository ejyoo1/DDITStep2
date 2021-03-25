package kr.or.ddit.basic;

/**
 * <스레드의 상태>
 * @author 유은지
 * New : 스레드가 생성되고 아직 start()가 호출되지 않은 상태
 * Runnable : 실행 중 또는 실행 가능한 상태
 * blocked : 동기화 블럭에 의해서 일시 정지된 상태 (Lock이 풀릴때까지 기다리는 상태)
 * waiting, timed_waiting : 스레드의 작업이 종료되지는 않았지만 실행가능하지 않은 일시정지 상태.
 * 						timed_waiting은 일시정지 시간이 지정된 경우임
 * terminated : 스레드의 작업이 종료된 상태
 */
public class T10_ThreadStateTest {
	public static void main(String[] args) {
		StatePrintThread spt = new StatePrintThread(new TargetThread());
		spt.start();
	}
}

//스레드의 상태를 출력하는 클래스(이 클래스도 스레드로 작성)
//스레드이므로 extends Thread
class StatePrintThread extends Thread {
	private Thread targetThread; //상태를 출력할 스레드가 지정될 변수
	
	public StatePrintThread(Thread targetThread) {
		this.targetThread = targetThread; //모니터링할 쓰레드 변수를 가지고 있음
	}
	
	@Override
	public void run() {//main과 같음
		while(true) {
			//Thread의 상태 구하기(getState()메서드 이용) - getter
			//Thread.State 타입 ===> enum 타입임
			Thread.State state = targetThread.getState();//쓰레드가 실행되고나서 현재 상태
			System.out.println("타겟 스레드의 상태값 : " + state);
			
			//New 상태인지 검사
			if(state == Thread.State.NEW) {//쓰레드가 start()가 안된 상태
				targetThread.start();//메인쓰레드만 start()를 할 수 있는 것이 아니라 다른 쓰레드에서도 start()를 실행할 수 있음.
			}
			
			//종료(TERMINATED) 상태인지 검사
			if(state == Thread.State.TERMINATED) {
				break;
			}
			
			try {
				Thread.sleep(500);//0.5초 동안 [TimedWaiting 상태 : 일정 시간이 지나면 동작][!= waiting 상태 : 누군가 깨워줘야 동작]
			}catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
}

//Target 스레드
class TargetThread extends Thread {
	@Override
	public void run() {
		for(int i = 1 ; i <= 1000000000L ; i++) {} //시간 지연 용(실제 CPU는 작업중)
		try {
			Thread.sleep(1500);//1.5초
			
		}catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		for(int i = 1 ; i <= 1000000000L ; i++) {} //시간 지연 용
	}
}