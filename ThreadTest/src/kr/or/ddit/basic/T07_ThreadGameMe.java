package kr.or.ddit.basic;

import javax.swing.JOptionPane;

/**
 * 컴퓨터와 가위바위보를 진행하는 프로그램을 작성하시오
 * @author 유은지
 * 컴퓨터의 가위 바위 보는 난수를 이용하여 구하고
 * 사용자의 가위 바위 보는 showInputDialog() 메서드를 이용하여 입력받는다.
 * 
 * 입력시간은 5초로 제한하고 카운트 다운을 진행한다.
 * 5초안에 입력이 없으면 게임을 진것으로 처리한다.
 * 
 * 5초안에 입력이 없으면 게임을 진것으로 처리한다.
 * 
 * 5초안에 입력이 완료되면 승패를 출력한다.
 * 
 * 결과예시)
 * ===결과===
 * 컴퓨터 : 가위
 * 당신 : 바위
 * 결과 : 당신이 이겼습니다.
 */
public class T07_ThreadGameMe {
//	모든 쓰레드에서 공통으로 사용될 변수
	public static boolean inputCheck = false;
	public static String userInput = "";
	
	public static void main(String[] args) {
		
		Thread th1 = new DataInput1();
		Thread th2 = new CountDown1();
		
		th1.start();
		th2.start();
	}
	

}

/**
 * 데이터 입력을 처리하는 클래스
 * @author 유은지
 *
 */
class DataInput1 extends Thread{
	@Override
	public void run() {
		String str = JOptionPane.showInputDialog("가위 바위 보를 입력하세요");
		
		//입력이 완료되면 inputCheck 변수를 true로 변경한다.
		T07_ThreadGameMe.inputCheck = true;
		T07_ThreadGameMe.userInput = str;
		System.out.println("입력한 값은 " + str + "입니다.");
		
		//가위바위 보 계산
		String[] arrGbb = {"가위","바위","보"};
		
//			컴퓨터 랜덤값 출력
		int comIdx = (int) Math.floor(Math.random() * 3);
		System.out.println("comIdx : " + comIdx);
//			사용자 입력값(인덱스)
		int userIdx = 0;
		for(int i = 0 ; i < arrGbb.length ; i++) {
			if(T07_ThreadGameMe.userInput.equals(arrGbb[i])) {
				userIdx = i;
			}
		}
		System.out.println("userIdx : " + userIdx);
		
		//값 설정
		String userVal = arrGbb[userIdx];
		String comVal = arrGbb[comIdx];
		
		/*
		 * ===결과===
 * 컴퓨터 : 가위
 * 당신 : 바위
 * 결과 : 당신이 이겼습니다.
		 * */
		//가위 바위 보 비교
	    if(comIdx == userIdx){//비겼을 때
	    	System.out.println("비겼습니다. \n컴퓨터 : " + comVal + " \n사용자 : " + userVal);
	    } else if(comIdx < userIdx){// 0,1 0,2 1,2
	        // 사용자가 이긴 경우 
	        // => (컴,사) : (0,1), (0,2), (1,2)
	        if(comIdx == 0 && userIdx == 2){//컴퓨터가 이긴 경우 ()
	        	System.out.println("졌습니다. \n컴퓨터 : " + comVal + " \n사용자 : " + userVal);
	        }else{//사용자가 이긴 경우
	        	System.out.println("이겼습니다. \n컴퓨터 : " + comVal + " \n사용자 : " + userVal);
	        }
	    } else if(comIdx > userIdx){// 1,0 2,0 2,1
	        // 사용자가 이김
	        // => (컴,사) : (1,0), (2,0), (2,1)
	        if(comIdx == 2 && userIdx == 0){//사용자가 이긴 경우 
	        	System.out.println("이겼습니다. \n컴퓨터 : " + comVal + " \n사용자 : " + userVal);
	        }else{//컴퓨터가 이긴 경우
	        	System.out.println("졌습니다. \n컴퓨터 : " + comVal + " \n사용자 : " + userVal);
	        }
	    }
	}
}

/**
 * 카운트 다운을 처리하는 스레드 클래스
 * @author 유은지
 *
 */
class CountDown1 extends Thread{
	@Override
	public void run() {
		//카운트 다운 시작
		for(int i = 5 ; i >= 1 ; i--) {
			if(T07_ThreadGameMe.inputCheck == true) {
				return; //run() 메서드 나감
			}
			
			System.out.println(i);
			try {
				Thread.sleep(1000);
			}catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		
		//시간이 경과되었는데도 입력이 없는경우, 프로그램 종료
		System.out.println("5초가 지났습니다. 프로그램을 종료합니다.");
		System.exit(0);
	}
	

}