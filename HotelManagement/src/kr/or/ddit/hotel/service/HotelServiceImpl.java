package kr.or.ddit.hotel.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.hotel.dao.HotelDaoImpl;
import kr.or.ddit.hotel.dao.IHotelDao;
import kr.or.ddit.hotel.vo.HotelVO;
import kr.or.ddit.util.JDBCUtil3;

public class HotelServiceImpl implements IHotelService{
	/**
	 * HotelDao 호출 및 Connection획득 위함 
	 */
	private IHotelDao hotelDao;
	private Connection conn;
	
	public HotelServiceImpl() {
		hotelDao = new HotelDaoImpl();
	}

	@Override
	public int insertHotel(HotelVO hv) {
		int cnt = 0;
		
		try {
			conn = JDBCUtil3.getConnection();
		
			cnt = hotelDao.insertHotel(conn, hv);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}
		return cnt;
	}

	@Override
	public boolean checkHotel(int roomNum) {
		boolean chk = false;
		
		try {
			conn = JDBCUtil3.getConnection();
			chk = hotelDao.checkHotel(conn, roomNum);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}
		return chk;
	}

	@Override
	public List<HotelVO> getAllHotelList() {
		List<HotelVO> hotelList = new ArrayList<>();
		
		try {
			conn = JDBCUtil3.getConnection();
			hotelList = hotelDao.getAllHotelList(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}
		return hotelList;
	}

	@Override
	public int deleteHotel(int roomNum) {
		int cnt = 0;
		
		try {
			conn = JDBCUtil3.getConnection();
			cnt = hotelDao.deleteHotel(conn, roomNum);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}
		return cnt;
	}

}
