package kr.or.ddit.member.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.member.MemberMain;
import kr.or.ddit.member.vo.MemberVO;

public class MemberDaoImpl implements IMemberDao {
	
	
//	private static MemberDaoImpl memDao; 
	private static IMemberDao memDao;//인터페이스로 사용함.(나중에 유지보수 쉽게하기 위해서)
	
	private MemberDaoImpl() {

	}
	
	public static IMemberDao getInstance() {
		if( memDao == null) {
			memDao = new MemberDaoImpl();
		}
		
		return memDao;
	}
	
//	파라미터 관련 로거 생성
	private static final Logger PARAM_LOGGER = Logger.getLogger("log4jexam.sql.Parameter");
//	클래스에 대한 로거 생성
//	최종 결과에 대한 로그
	private static final Logger RESULT_LOGGER = Logger.getLogger(MemberDaoImpl.class);

	@Override
	public int insertMember(SqlMapClient smc, MemberVO mv) throws SQLException {
		int cnt = 0;
		
//		파라미터를 찍기위한 로거 생성
		PARAM_LOGGER.debug("파라미터 : ("
						+ "[" 	+ smc + "]"
						+ "[" 	+ mv.getMemId() + ", "
								+ mv.getMemName() + ", "
								+ mv.getMemTel() + ", "
								+ mv.getMemAddr() + "]"
					);
		
		Object obj = smc.insert("member.insertMember", mv);
		
		if(obj == null) {
//			데이터 삽입 성공
			cnt = 1;
		}
		
		RESULT_LOGGER.warn("결과 : " + cnt);
		
		return cnt;
	}

	@Override
	public boolean checkMember(SqlMapClient smc, String memId) throws SQLException {
		boolean chk = false;
		
		int cnt = (int) smc.queryForObject("member.getMember", memId);
		
		if(cnt > 0) {
//			중복이면 true;
			chk = true;
		}
		return chk;
	}

	@Override
	public List<MemberVO> getAllMemberList(SqlMapClient smc) throws SQLException {
		List<MemberVO> memList = smc.queryForList("member.getMemberAll");
		
		return memList;
	}

	@Override
	public int updateMember(SqlMapClient smc, MemberVO mv) throws SQLException {
		int cnt = 0;
		
		cnt = smc.update("member.updateMember", mv);
		
		return cnt;
	}

	@Override
	public int deleteMember(SqlMapClient smc, String memId) throws SQLException {
		int cnt = 0;
		
		cnt = smc.delete("member.deleteMember", memId);
		
		return cnt;
	}
	
	
	//쿼리작성
	//사용자 요청에 따라 쿼리가 달리 작성되어야 함. 다이나믹 쿼리
	@Override
	public List<MemberVO> getSearchMember(SqlMapClient smc, MemberVO mv) throws SQLException {
//		결과를 담을 List 정의
		List<MemberVO> memList = smc.queryForList("member.getSearchMember", mv);
		
		return memList;
	}
	
}
