package kr.or.ddit.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.hotel.vo.HotelVO;
import kr.or.ddit.util.JDBCUtil3;

public class HotelDaoImpl implements IHotelDao {
	/**
	 * DB에 접근하고 DB에서 가져온 데이터 처리 관련 객체 생성 
	 */
	private PreparedStatement pstmt;
	private ResultSet rs;

	@Override
	public int insertHotel(Connection conn, HotelVO hv) throws SQLException {
		conn = JDBCUtil3.getConnection();
		
		String sql = " INSERT INTO HOTEL_MNG(ROOM_NUM, GUEST_NAME) "
				+ " VALUES(?, ?) ";
		
//		컴파일 
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, hv.getRoomNum());
		pstmt.setString(2, hv.getGuestName());
		
		int cnt = pstmt.executeUpdate();
				
		return cnt;
	}

	@Override
	public boolean checkHotel(Connection conn, int roomNum) throws SQLException {
		boolean chk = false;
		
		String sql = " SELECT COUNT(*) CNT FROM HOTEL_MNG WHERE ROOM_NUM = ? ";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, roomNum);
		rs = pstmt.executeQuery();
		
		int cnt = 0;
		while(rs.next()) {
			cnt = rs.getInt("cnt");
		}
		
//		cnt결과 확인 후 개수가 0보다 큰 경우 true반환 (데이터가 존재한다는 의미)
		if(cnt > 0) {
			chk = true;
		}
		
		return chk;
	}

	@Override
	public List<HotelVO> getAllHotelList(Connection conn) throws SQLException {
		List<HotelVO> hotelList = new ArrayList<HotelVO>();
		
		String sql = " SELECT * FROM HOTEL_MNG ";
		
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			HotelVO hv = new HotelVO();
			int roomNum = rs.getInt("ROOM_NUM");
			String guestName = rs.getString("GUEST_NAME");
			
			hv.setRoomNum(roomNum);
			hv.setGuestName(guestName);
			
			hotelList.add(hv);
		}
		return hotelList;
	}

	@Override
	public int deleteHotel(Connection conn, int roomNum) throws SQLException {
		String sql = " DELETE FROM HOTEL_MNG WHERE ROOM_NUM = ? ";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, roomNum);
		int cnt = pstmt.executeUpdate();
		
		return cnt;
	}

}
