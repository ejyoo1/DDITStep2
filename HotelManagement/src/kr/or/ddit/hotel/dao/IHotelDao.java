package kr.or.ddit.hotel.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import kr.or.ddit.hotel.vo.HotelVO;

/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 작성한다.
 * @author yooeunji
 * 
 */
public interface IHotelDao {
	
	/**
	 * BoardVO 객체에 담겨진 자료를 DB에  insert하는 메서드이다.
	 * @param conn 커넥션 객체 
	 * @param hv DB에 insert할 자료가 저장된 HotelVO 객
	 * @return DB작업이 성공하면 1이상의 값이 반환되고, 실패하면 0이 반환된다.
	 * @throws SQLException JDBC관련 예외 객체 발
	 */
	public int insertHotel(Connection conn, HotelVO hv) throws SQLException;
	
	/**
	 * 주어진 roomNum가 존재하는지 여부를 알아내는 메서드 
	 * @param conn 커넥션 객체 
	 * @param roomNum 호텔 번호 
	 * @return 해당 호텔룸이 존재하면 true, 존재하지 않으면 false
	 * @throws SQLException JDBC관련 예외 객체 발생 
	 */
	public boolean checkHotel(Connection conn, int roomNum) throws SQLException;
	
	/**
	 * DB의 hotel_mng 테이블의  전체 레코드를 가져와서 List에 담아 반환하는 메서드 
	 * @param conn 커넥션 객체 
	 * @return 호텔정보를 담고있는 List 객체 
	 * @throws SQLException JDBC관련 예외객체 발생 
	 */
	public List<HotelVO> getAllHotelList(Connection conn) throws SQLException;
	
	
	/**
	 * 호텔 정보를 매개변수로 받아서 그 호텔룸을 삭제하는 메서드 
	 * @param conn 커넥션 객
	 * @param roomNum 삭제할 호텔룸 NO 
	 * @return 작업성공 : 1, 작업 실패 : 0
	 * @throws SQLException JDBC관련 예외객체 발생 
	 */
	public int deleteHotel(Connection conn, int roomNum) throws SQLException;
}
