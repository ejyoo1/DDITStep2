package kr.or.ddit.basic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import util.ScanUtil;

public class T20_HotelManagement_Serializable {
	//hotelMember 변수 생성 => 이 변수는 호텔 프로그램 실행 시 호텔에 숙박하는 호수, 사용자 명을 담을 것임.
	private Map<String,HotelVO> hotelMembers;
	
	//hotelMembers 객체를 생성한다. (다형성 개념이 들어가서 Map안에 HashMap이 들어간것이다.)
	private T20_HotelManagement_Serializable() {
		hotelMembers = new HashMap<String,HotelVO>();
	}
	
	public static void main(String[] args) {
		//메인 메서드에서 start() 메서드를 호출한다.
		new T20_HotelManagement_Serializable().start();
	}
	
	private void start() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("호텔문을 열었습니다.");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
//		파일 객체에 파일 경로를 삽입한다.
		File f1 = new File("d:/D_Other/ejyooHotelManagement.bin");
		
//		파일이 존재하는 경우
		if(f1.exists()) {
//			파일로 저장된 폰북을 불러온다.
			HotelImport();
		}
		
		
		while(true) {
			System.out.println("어떤 업무를 하시겠습니까?");
			System.out.println("1.체크인\t2.체크아웃\t3.객실상태\t4.업무종료");
			System.out.print("입력 > ");
			int userInput = ScanUtil.nextInt();
			switch(userInput) {
				case 1:
					checkIn();
					break;
				case 2:
					checkOut();
					break;
				case 3:
					roomStatus();
					break;
				case 4: 
					HotelExport();
					System.out.println("호텔관리시스템을 종료합니다.");
					System.exit(0);
			}
		}
	}

	private void HotelExport() {
		//맵을 읽어온다.
		//EntrySet으로 변환하여 출력(구분자 넣을것이기 때문에 이 방법을 사용함)
		Set<String> keySet = hotelMembers.keySet();
		try {
			ObjectOutputStream oos = 
					new ObjectOutputStream(
							new BufferedOutputStream(
									new FileOutputStream("d:/D_Other/ejyooHotelManagement.bin")));
			
			for(String key : keySet) {
				System.out.println(key + " : " + hotelMembers.get(key));
//				쓰기 작업
				oos.writeObject(hotelMembers.get(key));
				System.out.println("바이너리 형태로 쓰기 작업 완료");
				
			}
			oos.close();	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void HotelImport() {
//저장한 객체를 읽어와 출력하기
		try {
			//입력용 스트림 객체 생성
			ObjectInputStream ois = 
					new ObjectInputStream(
							new BufferedInputStream(
									new FileInputStream("d:/D_Other/ejyooHotelManagement.bin")));
			
			Object obj = null;
		
		
			while((obj = ois.readObject()) != null) { //ois.readObject() 역직렬화(내부적으로 역직렬화 작업 수행)
				//마지막에 다다르면 EOF 예외 발생함.
				HotelVO hotelVo = (HotelVO) obj;
				hotelMembers.put(hotelVo.getRoomNum(), hotelVo);
			}
			ois.close();
		
		}catch(IOException ex) {//ioet 스페이스바 [마지막에 다다르면 EndOfFileException을 이용하여 끝났다 라는 것을 판단함]
			ex.printStackTrace();
			//더이상 읽어올 객체가 없으면 예외 발생함.
			System.out.println("출력 작업 끝...");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	private void checkOut() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("체크아웃");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("어느방을 체크아웃 하시겠습니까?");
		System.out.print("입력 > ");
		String userRoomNum = ScanUtil.nextLine();
		if(hotelMembers.get(userRoomNum) == null) {
			System.out.println(userRoomNum + "방에는 체크인한 사람이 없습니다.");
		}else {
			hotelMembers.remove(userRoomNum);
			System.out.println("체크아웃 되었습니다. 감사합니다.");
		}
	}

	private void roomStatus() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("투숙자 현황");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		
		Set<String> keySet = hotelMembers.keySet();
		if(keySet.size() == 0) {
			System.out.println("현재 체크인한 투숙자가 없습니다.");
		}else {
			for(String key : keySet) {
				System.out.println(hotelMembers.get(key));
			}
		}
	}

	private void checkIn() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("체크인");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		
		System.out.println("어느방에 체크인 하시겠습니까?");
		System.out.print("입력 > ");
		String userRoomNum = ScanUtil.nextLine();
		if(hotelMembers.get(userRoomNum) != null) {
			System.out.println(userRoomNum + "방에는 이미 사람이 있습니다.");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			return;
		}else {
			System.out.println("누구를 체크인 하시겠습니까?");
			String userName = ScanUtil.nextLine();
			HotelVO hvo = new HotelVO(userRoomNum,userName);
			hotelMembers.put(userRoomNum, hvo);
			System.out.println("체크인 완료");
		}
	}
}

class HotelVO implements Serializable {
	private String roomNum;
	private String checkMember;
	
	public HotelVO(String roomNum, String checkMember) {
		super();
		this.roomNum = roomNum;
		this.checkMember = checkMember;
	}
	
	@Override
	public String toString() {
		return "[방번호=" + roomNum + ", 투숙객=" + checkMember + "]";
	}
	public String getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}
	public String getCheckMember() {
		return checkMember;
	}
	public void setCheckMember(String checkMember) {
		this.checkMember = checkMember;
	}
}