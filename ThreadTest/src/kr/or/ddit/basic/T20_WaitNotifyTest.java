package kr.or.ddit.basic;

/**
 * 하나의 쓰레드는 읽고 하나의 쓰레드는 쓰고
 * 서로 필요한 경우 wait()을 써서 공유객체 영역을 벗어나고, 다른 쓰레드가 공유객체에서 작업할 수 있게 도와주며
 * 작업한 쓰레드는 wait() 상태인 다른 쓰레드가 다음 작업을 할 수 있게 깨워주도록 notify()를 사용한다. 
 * @author 유은지
 *
 */
public class T20_WaitNotifyTest {
	public static void main(String[] args) {
		
		DataBox dataBox = new DataBox();
		
		ProducerThread pth = new ProducerThread(dataBox);
		ConsumerThread cth = new ConsumerThread(dataBox);
		
		pth.start();
		cth.start();
	}
}

// 데이터를 공통으로 사용하는 클래스 : 공유객체
class DataBox {//getter,setter를 가지고 있는 데이터 처리 위한 데이터 박스
	private String data; //데이터를 가지고 있는 데이터 박스는 데이터를 스트링으로 작업한다.
	
	// data가 null이 아닐때 data값을 반환하는 메서드
	public synchronized String getData() {
		if(data == null) {//멤버변수 데이터가 없으면
			try {
				wait();//꺼내올 데이터가 없으므로 기다림. 대기실로 이동됨.
			}catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		//혹시나 누군가 데이터를 세팅한 경우
		String returnData = data; //데이터를 꺼내서 변수에 넣음
		System.out.println("읽어온 데이터 : " + returnData);
		data = null;//꺼내온 데이터를 비워야 함.(꺼내왔다는 의미로)
		System.out.println(Thread.currentThread().getName()
				 +  " notify() 호출");//notify를 했는지 안했는지 보기위해 찍어봄
		notify();//notify 호출
		
		return returnData;//뽑아낸 데이터 찍어냄
	}
	
	// data가 null 일 경우에만 자료를 세팅하는 메서드
	public synchronized void setData(String data) {//값이 없는 경우에 데이터를 세팅하는 메서드
		if(this.data != null) {//가져온 data가 진짜 있는지 없는지 체크
			try {
				wait();//이미 세팅된 데이터가 있으면 기다려
			}catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		//null이면 세팅할 데이터가 있는 것이므로
		this.data = data;//데이터를 저장하고
		System.out.println("세팅한 데이터 : " + this.data);//세팅한 데이터를 출력하고
		System.out.println(Thread.currentThread().getName()
				+ " notify() 호출");//명시적으로 notify호출됐는지 보기위해 찍음
		notify();//세팅 했으니 데이터 없어서 웨잇으로 기다리고 있을 누군가를 깨움
	}
}

// 데이터를 세팅만 하는 스레드 => 실제 작업을 하는 쓰레드
class ProducerThread extends Thread {//생산자 쓰레드 생성 ==> 값을 세팅해주는 역할임. 데이터박스에 있는 데이터를 설정하는 객체
	private DataBox dataBox;//공유객체를 생성자로 받아서 세팅
	
	public ProducerThread(DataBox dataBox) {
		super("ProducerThread");
		this.dataBox = dataBox;
	}
	
	@Override
	public void run() {//10번 포문 돌리면서 데이터를 생성함.
		for(int i=1; i<=10; i++) {
			String data = "Data-" + i;//1부터 10까지 데이터를 만듬
			System.out.println("dataBox.setData(" + data + ") 호출");//공유객체 setter메서드를 이용하여 세팅
			dataBox.setData(data);//실질적으로 세팅(혹시 데이터 박스에 데이터가 존재하는 경우 대기상태로 있음)
		}
	}
}
// 데이터를 읽어만 오는 스레드
class ConsumerThread extends Thread {//데이터 소비하는 객체 생성
	private DataBox dataBox;//공유객체 생성자 생성
	
	public ConsumerThread(DataBox dataBox) {
		super("ConsumerThread");
		this.dataBox = dataBox;
	}
	
	@Override
	public void run() {
		for(int i=1; i<=10; i++) {//10번 돌음
			String data = dataBox.getData();//돌때마다 공유객체의 getter호출해서 데이터 가져옴(값을 소비)
			System.out.println("dataBox.getData() : " + data);//갖고온 데이터를 찍음
			//만약에 갖고올 데이터가 없으면 wait을 호출하여 기다림(데이터 박스에서의 처리)
		}
	}
}

