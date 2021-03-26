package kr.or.ddit.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 10마리의 말들이 경주하는 경마 프로그램 작성하기
 * @author 유은지
 *말은 Horse라는 이름의 클래스로 구성하고
 *이 클래스는 말이름(String), 등수(int)를 멤버변수로 갖는다
 *그리고 이 클래스에는 등수를 오름차순으로 처리할 수 있는 기능이 있다.(Comparable 인터페이스 구현)
 *
 *경기구간은 1~50구간으로 되어있다.
 *경기 중 중간중간에 각 말들의 위치를 >로 나타내시오.
 *예)
 *1번말 -->--------------------------
 *2번말 ---------->------------------
 *
 *경기가 끝나면 등수를 기준으로 정렬하여 출력한다.
 */
public class T16_homework_경마 {
	public final static int SECTION = 50;
	static String strRank = "";
	static int ranks = 1;
	static Map<String,Integer> hLocation = new HashMap<String,Integer>();
	
	//말 생성
	public static void main(String[] args) {
		Horse[] horse = new Horse[] {
			new Horse("1번말"),
			new Horse("2번말"),
			new Horse("3번말"),
			new Horse("4번말"),
			new Horse("5번말"),
			new Horse("6번말"),
			new Horse("7번말"),
			new Horse("8번말"),
			new Horse("9번말"),
			new Horse("10번말"),
		};
		
		//말 쓰레드 시작
		for(int i = 0 ; i < horse.length ; i++) {
			horse[i].start();
		}
		
		for(Horse h : horse) {//모든 말 쓰레드가 종료될 때까지 기다림
			try {
				h.join();//게임이 다 끝날때 까지 기다림
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//게임이 끝나면
		System.out.println("경기 끝...");
		System.out.println("-------------------------------------");
		System.out.println();
		System.out.println("경기 결과");
		System.out.println("■■■순위■■■");
		System.out.println(strRank);
		
	}
	
}

/**
 * 말들이 경주하는 클래스
 * @author 유은지
 * 정렬기준 : rank
 */
class Horse extends Thread implements Comparable<Horse>{
	private String name;
	private int rank;
	
//	생성자
	public Horse(String name) {
		this.name = name;
		this.rank = 0;
	}
	
	@Override
	public void run() {
		int test = (int)(Math.random() * 10) + 1;
		for(int i = 0 ; i <= T16_homework_경마.SECTION ; i++) {
			//값 증가
			T16_homework_경마.hLocation.put(name,i);//막 달리다가
			try {
				Thread.sleep((int)(Math.random() * 301 + 200));//랜덤으로 슬립
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
			
			if(test==1) {
				printLocation();
			}
			
			if(rank==7) {
				test = (int)(Math.random() * 10) + 1;
				printLocation();
			}
		}
		
		System.out.println("■" + name + "■ 도착했습니다 !!!");
		rank = T16_homework_경마.ranks++;
		T16_homework_경마.strRank += "[" + name + "," + rank + "등]\n";
	}
	
	/*경마 출력 - 랜덤 출력 위해 현재 위치 정보 저장*/
	public void printLocation() {
		System.out.println();
		//순서가 없어서 순서가 있는 List로 데이터 가공
		Map<String,Integer> horseMap = T16_homework_경마.hLocation;
		Set<String> keySet = horseMap.keySet();
		List<String> keyName = new ArrayList<String>();
		for(String key : keySet) {
			keyName.add(key);
		}
		
		//현재 위치 기준으로 출력
		for(int i = 0 ; i < keyName.size() ; i++) {
			System.out.print(keyName.get(i) + "현재위치 : " + horseMap.get(keyName.get(i)));
			
			for(int j = 0 ; j < horseMap.get(keyName.get(i)) ; j++) {
				System.out.print("-");
			}
			
			System.out.print(">");
			
			for(int j = horseMap.get(keyName.get(i)) + 1 ; j <= T16_homework_경마.SECTION ; j++) {
				System.out.print("-");
			}
			System.out.println();
		}
		System.out.println();
	}

	//정렬
	@Override
	public int compareTo(Horse o) {
		return this.name.compareTo(o.name);
	}
	
	
}