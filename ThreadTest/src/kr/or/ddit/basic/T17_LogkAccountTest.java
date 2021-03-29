package kr.or.ddit.basic;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 은행의 입출금을 스레드로 처리하는 예제
 * (Lock 객체를 이용한 동기화 처리)
 * @author 유은지
 *
 *Lock 기능을 제공하는 클래스
 *ReentrantLock(리엔터런트) : Read 및 Write 구분없이 사용하기 위한 락 클래스로 동기화 처리를 위해 사용됨. (락걸리면 끝 - 락을 해제할 때까지)
 *(Synchronized를 이용한 동기화처리보다 부가적인 기능이 제공됨. ex)Fairness 설정 등)
 *
 *ReentrantReadWriteLock : Read 및 Write 락을 구분사요 사용 가능함.(Fair mode)
 */
public class T17_LogkAccountTest {
	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock(); //락객체 생성
		
		LockAccount lAcc = new LockAccount(lock); //공유 객체 생성
		lAcc.deposit(10000); //입금
		
		BankThread2 bth1 = new BankThread2(lAcc);
		BankThread2 bth2 = new BankThread2(lAcc);
		
		bth1.start();
		bth2.start();
		
	}
}

//입출금을 담당하는 클래스
class LockAccount{
	private int balance; //잔액
	
	//Lock객체에 저장할 변수 => 가능하면 private final로 만든다.
	private final Lock lock;
	
	public LockAccount(Lock lock) {
		this.lock = lock;
	}
	
	public int getBalance() {
		return balance;
	}
	
	//입금처리 메서드
	//장점 : 어디든 락을 풀 수 있음
	//단점 : 락을 못푸는 상황이 올 수 있음.
	public void deposit(int money) {
		//Lock 객체의 lock()메서드 동기화 시작이고, unlock()메서드가 동기화 끝을 나타낸다.
		//lock() 메서드로 동기화를 설정한 곳에서는 반드시 unlock() 메서드로 해제해 주어야 한다.
		lock.lock(); //시작 (락을 획득하기 전까지 BLOCKED 됨.) 공유객체인 Account는 다른 객체는 접근할 수 없음.
		balance += money;
		lock.unlock();//해제 // 이것이 있어야 락잠금이 풀림
	}
	
	//출금처리 메서드(출금 성공 : true, 출금 실패 : false)
	public boolean withdraw(int money) {
		lock.lock(); //작업전 락 획독(Account에는 아무것도 오지않음.)
		boolean chk = false;
		
//		try ~ catch 블럭을 사용할 경우에는 예외가 발생할 수 있기에 예외에 상관없이 unlock()이 되어야 함.
//		unlock() 메서드 호출은 finally 블럭에서 하도록 한다.
		try {
			if(balance >= money) {
				for(int i = 1 ; i <=1000000000 ; i++) {} //시간 때우기
				balance -= money;
				System.out.println("메서드 안에서 balance = " + getBalance());
				
				chk = true;
			}
		} catch(Exception ex) {
			chk = false;
		}finally {
			lock.unlock();//락 반납(해제)
		}
		
		return chk;
	}
}

//은행 업무를 처리하는 스레드
class BankThread2 extends Thread {
	private LockAccount lAcc;
	
	public BankThread2(LockAccount lAcc) {
		this.lAcc = lAcc;
	}
	
	@Override
	public void run() {
		boolean result = lAcc.withdraw(6000);
		System.out.println("스레드 안에서 result = " + result + ", balance = " + lAcc.getBalance());
	}
}