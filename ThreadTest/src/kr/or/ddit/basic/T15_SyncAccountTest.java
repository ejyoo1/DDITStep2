package kr.or.ddit.basic;

/**
 * 은행의 입출금을 스레드로 처리하는 예제
 * synchronized를 이용한 동기화 처리
 * @author 유은지
 *
 */
public class T15_SyncAccountTest {
	public static void main(String[] args) {
		SyncAccount sAcc = new SyncAccount();
		//돈을 입금
		sAcc.setBalance(10000);//입금처리
		
		//두개의 쓰레드 객체 생성
		BankThread bth1 = new BankThread(sAcc);
		BankThread bth2 = new BankThread(sAcc);
		
		//스타트
		bth1.start();
		bth2.start();
	}
}

//은행의 입출금을 관리하는 클래스 정의(공통 객체)
//역할 : 잔액을 저장하는 클래스
class SyncAccount {
	private int balance; //잔액이 저장될 변수

	public int getBalance() { 
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	//입금 처리를 수행하는 메서드
	public void deposit(int money) {//입금
		balance += money;
	}
	
	//출금을 처리하는 메서드(출금 성공 : true, 출금 실패 : false)
	//동기화 영역에서 호출하는 메서드도 동기화 처리를 해주어야 한다. => 고정된 상수값을 읽는 경우는 영향이 없지만, write하는 작업이 있으면 그땐 동기화를 꼭 해주어야 한다.
	//synchronized 는 순서에 상관없음.
	//스레드는 순서를 예측할 수 없기때문에 결과를 아무것도 모른다. 돈같은 부분은 어케될 지 모르니 동기화 처리는 필수다.(속도보다 정확성이 중요하기에)
	//누가 첫번째로 들어올지 예측할 수 없다. 우연의 일치로 첫번째에 만든 쓰레드가 먼저올 수 있다.
	synchronized public boolean withdraw(int money) {//인출
		if(balance >= money) {//잔액이 충분할 경우...(잔액이 더 크다)
			for(int i = 0 ; i <= 1000000000 ; i++) {} //시간 때우기
			
			balance -= money;
			System.out.println("메서드 안에서 balance = " + getBalance());
			return true;
		}else {
			return false;
		}
	}
}

//은행 업무를 처리하는 스레드
class BankThread extends Thread{
	private SyncAccount sAcc;
	
	public BankThread(SyncAccount sAcc) {
		this.sAcc = sAcc;
	}
	
	@Override
	public void run() {
		boolean result = sAcc.withdraw(6000); //6000원 인출
		System.out.println("스레드 안에서 result = " + result + ", balance = " + sAcc.getBalance());
	}
}