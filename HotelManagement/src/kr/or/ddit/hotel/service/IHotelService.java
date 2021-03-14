package kr.or.ddit.hotel.service;

import java.util.List;

import kr.or.ddit.hotel.vo.HotelVO;

/**
 * 호텔 정보 처리를 수행하는 서비스 
 * @author yooeunji
 *
 */
public interface IHotelService {
	/**
	 * 호텔 등록 메서드 
	 * @param hv DB에 insert할 자료가 저장된 HotelVO 객체 
	 * @return DB작업이 성공하면 1이상의 값이 반환되고, 실패하면 0이 반환된다.
	 */
	public int insertHotel(HotelVO hv);
	
	/**
	 * 주어진 호텔번호가 존재하는지 여부를 알아내는 메서드 
	 * @param roomNum 호텔번호 
	 * @return 호텔 번호가 존재하면 true, 존재하지 않으면 false
	 */
	public boolean checkHotel(int roomNum);
	
	/**
	 * 호텔 전체 목록을 가져오는 메서드 
	 * @return 호텔 정보를 담고있는 List객체 
	 */
	public List<HotelVO> getAllHotelList();
	
	/**
	 * 호텔 정보를  삭제하는 메서드 
	 * @param roomNum 삭제할 호텔번호 
	 * @return 작업성공 : 1, 작업 실패 : 0
	 */
	public int deleteHotel(int roomNum);
}
