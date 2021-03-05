package Assignment0302;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import util.ScanUtil;

public class LottoBall {
	public static void main(String[] args) {
		new LottoBall().Start();
	}
	
	private void Start() {
		while(true) {
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("Lotto 프로그램");
			System.out.println("--------------------------------------------");
			System.out.println("1. Lotto 구입");
			System.out.println("2. 프로그램 종료");
			System.out.println("--------------------------------------------");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.print("메뉴 선택 > ");
			int userInput = ScanUtil.nextInt();
			
			switch(userInput) {
				case 1:
					System.out.println("로또 구입 시작");
					System.out.println("[알림] : 로또 한 줄 가격은 1000원 입니다.");
					System.out.print("금액입력 > ");
					changeUserMoney(ScanUtil.nextInt());
					break;
				case 2: 
					System.out.println("프로그램을 종료합니다.");
					System.exit(0);
					
			}
		}
	}
	
	private void changeUserMoney(int userMoney) {
		int lottos = userMoney / 1000;
		int changeUserMoney = userMoney % 1000;
		
		ArrayList<ArrayList<Integer>> lottosList = lottoMachine(lottos);
		for(int i = 0 ; i < lottosList.size() ; i++) {
			System.out.print("로또번호" + (i+1) + " > ");
			System.out.println(lottosList.get(i).toString());
		}
		System.out.println("받은 금액은 " + userMoney + " 이며, "
				+ "로또 " + lottos + " 장 구매하셨습니다. "
				+ "거스름돈은 " + changeUserMoney + "원 입니다.");
	}


	private ArrayList<ArrayList<Integer>> lottoMachine(int lottos) {
		ArrayList<ArrayList<Integer>> lottosNumber = new ArrayList<>();
		for(int i = 0 ; i < lottos ; i++) {
			Set<Integer> intRnd = new HashSet<Integer>();
			while(intRnd.size() < 6) {
				int num = (int)(Math.random() * 45 + 1);
				System.out.println(num);
				intRnd.add(num);
			}
			ArrayList<Integer> intRndArr = new ArrayList<Integer>(intRnd);
			lottosNumber.add(intRndArr);
		}
		return lottosNumber;
	}
}