package kr.or.ddit.board.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.board.vo.BoardVO;

public class MemberDaoImpl implements IMemberDao {
	
	/**
	 * 로그 객체 생성
	 */
//	private static final Logger PARAM_LOGGER = Logger.getLogger("log4jexam.sql.Parameter"); // 파라미터 관련 로거
	private static final Logger RESULT_LOGGER = Logger.getLogger(MemberDaoImpl.class); // 최종 결과에 대한 로거
	
	/**
	 * 필요한 객체 생성
	 */
	private static IMemberDao memDao; // [private static MemberDaoImpl memDao;] 대신 인터페이스로 사용함.[유지보수 용이]
	private MemberDaoImpl() {}
	
	/**
	 * 싱글톤 패턴
	 * @return IMemberDao 리턴
	 */
	public static IMemberDao getInstance() { 
		if( memDao == null) { memDao = new MemberDaoImpl(); }
		return memDao;
	}
	
	/**
	 * 멤버 등록 관련 DAO - DB 호출 및 결과 Service 로 반환
	 */
	@Override
	public int insertMember(SqlMapClient smc, MemberVO mv) throws SQLException { 
//		PARAM_LOGGER.debug("★★Service 파라미터★★ : "
//				+ "[" 	+ mv.getMemId() + ", "
//				+ mv.getMemName() + ", "
//				+ mv.getMemTel() + ", "
//				+ mv.getMemAddr() + "]"
//				); // smc 찍기 가능 => 주소 값 반환됨.
		
		int cnt = 0;
		Object obj = smc.insert("member.insertMember", mv);
		if(obj == null) { cnt = 1;	} // 데이터 삽입 성공
		RESULT_LOGGER.debug("★★DAO 결과★★ [1:삽입성공,0:삽입실패] : " + cnt);
		
		return cnt;
	}

	/**
	 * DB에 값이 존재하는지 여부를 판단하기 위한 DAO - DB 호출 및 결과 Service 로 반환
	 */
	@Override
	public boolean checkMember(SqlMapClient smc, String memId) throws SQLException { 
		boolean chk = false;
		int cnt = (int) smc.queryForObject("member.getMember", memId);
		if(cnt > 0) { chk = true; } // 중복값이 있음.
		RESULT_LOGGER.debug("★★DAO 결과★★ [true:DB에 값이 존재함,false:DB에 값이 없음] : " + chk);
		
		return chk;
	}

	/**
	 * 전체 멤버 목록을 가져오는 DAO - DB 호출 및 결과 Service 로 반환
	 */
	@Override
	public List<MemberVO> getAllMemberList(SqlMapClient smc) throws SQLException {
		List<MemberVO> memList = smc.queryForList("member.getMemberAll");
		RESULT_LOGGER.debug("★★DAO 결과★★ [가져온 목록 수] : " + memList.size());
		
		return memList;
	}

	/**
	 * 멤버를 수정하는 DAO - DB 호출 및 결과 Service 로 반환
	 */
	@Override
	public int updateMember(SqlMapClient smc, MemberVO mv) throws SQLException {
		int cnt = 0;
		cnt = smc.update("member.updateMember", mv);
		RESULT_LOGGER.debug("★★DAO 결과★★ [1:수정성공,0:수정실패] : " + cnt);
		
		return cnt;
	}

	/**
	 * 멤버를 삭제하는 DAO - DB 호출 및 결과 Service 로 반환
	 */
	@Override
	public int deleteMember(SqlMapClient smc, String memId) throws SQLException {
		int cnt = 0;
		cnt = smc.delete("member.deleteMember", memId);
		RESULT_LOGGER.debug("★★DAO 결과★★ [1:삭제성공,0:삭제실패] : " + cnt);
		
		return cnt;
	}
	
	/**
	 * 필터 검색을 위한 DAO [Dynamic Query] - DB 호출 및 결과 Service 로 반환
	 */
	@Override
	public List<MemberVO> getSearchMember(SqlMapClient smc, MemberVO mv) throws SQLException {
		List<MemberVO> memList = smc.queryForList("member.getSearchMember", mv);
		RESULT_LOGGER.debug("★★DAO 결과★★ [가져온 목록 수] : " + memList.size());
		
		return memList;
	}

	@Override
	public MemberVO getMember(SqlMapClient smc, String memId) throws SQLException {
		MemberVO mv = (MemberVO) smc.queryForObject("member.getMemberInfo", memId);
	
		return mv;
	}
	
}
