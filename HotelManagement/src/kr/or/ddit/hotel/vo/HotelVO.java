package kr.or.ddit.hotel.vo;

/**
 * DB 테이블에 있는 컬럼을 기준으로 데이터를 객체화한 클래스이다.
 * @author 유은
 * DB테이블의 '컬럼'이 이 클래스의 '멤버변수' 가 된다.<br>
 * DB테이블의 컬럼과 클래스의 멤버변수를 매핑하는 역할을 수행한다.<br>
 */
public class HotelVO {
	private int roomNum; //room_num number not null --방번호
	private String guestName; //guest_name varchar2(10) not null --투숙객이름 
	
	public int getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	@Override
	public String toString() {
		return "HotelVO [roomNum=" + roomNum + ", guestName=" + guestName + "]";
	}
}
