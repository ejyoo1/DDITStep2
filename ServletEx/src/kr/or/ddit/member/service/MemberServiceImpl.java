package kr.or.ddit.member.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.member.dao.IMemberDao;
import kr.or.ddit.member.dao.MemberDaoImpl;
import kr.or.ddit.member.vo.MemberVO;
import kr.or.ddit.util.SqlMapClientUtil;

public class MemberServiceImpl implements IMemberService {
	
	/**
	 * 로그 객체 생성
	 */
	private static final Logger RESULT_LOGGER = Logger.getLogger(MemberServiceImpl.class); // 최종 결과에 대한 로거
	
	/**
	 * 필요한 객체 생성
	 */
	private IMemberDao memDao; // [private static MemberDaoImpl memDao;] 대신 인터페이스로 사용함.[유지보수 용이]
	private SqlMapClient smc; // 커넥션 객체 담기위한 메서드 객체 선언
	private static IMemberService memService; // 서비스 객체 생성
	
	/**
	 * 생성자 호출 - memDao, smc 객체 생성 [싱글톤 패턴 적용]
	 */
	private MemberServiceImpl() {
		memDao = MemberDaoImpl.getInstance();
		smc = SqlMapClientUtil.getInstance();
	}
	
	/**
	 * 싱글톤 패턴
	 * @return IMemberService 리턴
	 */
	public static IMemberService getInstance() {
		if(memService == null) { memService = new MemberServiceImpl(); }
		return memService;
	}
	
	/**
	 * 멤버 등록 관련 Service - DAO 처리 결과 받아 Controller(Servlet)으로 전송
	 */
	@Override
	public int insertMember(MemberVO mv) {
		int cnt = 0;
		
		try { // DAO throws SQLException 처리 (ibatis 에서 처리하여도 SQL 예외 발생 여지 존재함.)
			/**
			 * 트랜잭션 관련 처리 코드
			 smc.startTransaction(); // 트랜잭션 관리 시작
			 */
			cnt = memDao.insertMember(smc, mv);
			RESULT_LOGGER.debug("★★Service 결과★★ [1:삽입성공,0:삽입실패] : " + cnt);
			/**
			 * 트랜잭션 관련 처리 코드
			 smc.commitTransaction(); // DB Commit 수행
			 
			 */
		} catch (SQLException e) {
			e.printStackTrace();
			/**
			 * 트랜잭션 관련 처리 코드
			try {
				smc.endTransaction(); // 트랜잭션 롤백과 동일
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			SQL문 실행 시 예외 발생하는 경우, 트랜잭션 롤백과 동일한 효과.
			SQL 문 실행 시 예외 발생하는 경우, commit 코드가 진행되지 않으며 catch로 이동됨.
			*/
		} // ibatis 내 리소스 관리 자동으로 수행되므로, 자원반납 코드 작성하지 않음.
		return cnt;
	}

	/**
	 * DB에 값이 존재하는지 여부를 판단하기 위한 Service - DAO 처리 결과 받아 Controller(Servlet)으로 전송
	 */
	@Override
	public boolean checkMember(String memId) {
		boolean chk = false;
		try {
			chk = memDao.checkMember(smc, memId);
			RESULT_LOGGER.debug("★★Service 결과★★ [true:DB에 값이 존재함,false:DB에 값이 없음] : " + chk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chk;
	}

	/**
	 * 전체 멤버 목록을 가져오는 Service - DAO 처리 결과 받아 Controller(Servlet)으로 전송
	 */
	@Override
	public List<MemberVO> getAllMemberList() {
		List<MemberVO> memList = new ArrayList<>();
		try {
			memList = memDao.getAllMemberList(smc);
			RESULT_LOGGER.debug("★★Service 결과★★ [가져온 목록 수] : " + memList.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memList;
	}

	/**
	 * 멤버를 수정하는 Service - DAO 처리 결과 받아 Controller(Servlet)으로 전송
	 */
	@Override
	public int updateMember(MemberVO mv) {
		int cnt = 0;
		try {
			cnt = memDao.updateMember(smc, mv);
			RESULT_LOGGER.debug("★★Service 결과★★ [1:수정성공,0:수정실패] : " + cnt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	/**
	 * 멤버를 삭제하는 Service - DAO 처리 결과 받아 Controller(Servlet)으로 전송
	 */
	@Override
	public int deleteMember(String memId) {
		int cnt = 0;
		try {
			cnt = memDao.deleteMember(smc, memId);
			RESULT_LOGGER.debug("★★Service 결과★★ [1:삭제성공,0:삭제실패] : " + cnt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	/**
	 * 필터 검색을 위한 Service [Dynamic Query] - DAO 처리 결과 받아 Controller(Servlet)으로 전송
	 */
	@Override
	public List<MemberVO> getSearchMember(MemberVO mv) {
		List<MemberVO> memList = new ArrayList<>();
		
		try {
			memList = memDao.getSearchMember(smc, mv);
			RESULT_LOGGER.debug("★★Service 결과★★ [가져온 목록 수] : " + memList.size());
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return memList;
	}

	@Override
	public MemberVO getMember(String memId) {
		
		MemberVO mv = null;
		
		try {
			mv = memDao.getMember(smc, memId);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return mv;
	}
}
