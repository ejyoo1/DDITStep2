package Assignment0302;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import util.ScanUtil;

public class HotelManagement {
	private Map<String,HotelVO> hotelMembers;
	
	
	private HotelManagement() {
		hotelMembers = new HashMap<String,HotelVO>();
	}
	
	public static void main(String[] args) {
		new HotelManagement().start();
	}
	
	private void start() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("호텔문을 열었습니다.");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
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
					System.out.println("호텔관리시스템을 종료합니다.");
					System.exit(0);
			}
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


class HotelVO{
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