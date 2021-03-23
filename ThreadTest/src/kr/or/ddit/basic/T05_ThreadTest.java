package kr.or.ddit.basic;

import javax.swing.JOptionPane;

/**
 * 단일 스레드에서의 사용자 입력을 받아 처리하는 예제
 * 카운트 다운 예제
 * @author 유은지
 */
public class T05_ThreadTest {
	public static void main(String[] args) {
		//스윙 사용
		String str = JOptionPane.showInputDialog("아무거나 입력");
		System.out.println("입력한 값은 " + str + "입니다.");
		
		for(int i = 10 ; i >= 1 ; i--) {
			System.out.println(i);
			
			try {
				Thread.sleep(1000);//1초동안 잠시 멈춘다.
			}catch(InterruptedException ex){
				ex.printStackTrace();
			}
		}
	}
}
