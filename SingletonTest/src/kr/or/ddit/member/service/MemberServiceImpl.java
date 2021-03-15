package kr.or.ddit.member.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.member.dao.IMemberDao;
import kr.or.ddit.member.dao.MemberDaoImpl;
import kr.or.ddit.member.vo.MemberVO;
import kr.or.ddit.util.JDBCUtil3;

public class MemberServiceImpl implements IMemberService {
	
//	사용할 DAO의 객체 변수를 선언한다.
//	나중에 implements된 다른 메서드로 편하게 바꾸기 위해 인터페이스 객체 생성함(다형성)
	private IMemberDao memDao;
//	커넥션 객체 담기위한 메서드 객체 선언
	private Connection conn;
	
	private static IMemberService memService;
	
	private MemberServiceImpl() {
		memDao = MemberDaoImpl.getInstance();
	}
	
	public static IMemberService getInstance() {
		if(memService == null) {
			memService = new MemberServiceImpl();
		}
		return memService;
	}

	@Override
	public int insertMember(MemberVO mv) {
		int cnt = 0;
		
		try {//Dao에서 예외처리를 넘겼으므로 여기서 예외처리 설정함.
//			여러개 다오를 작업할 때 하나의 connection 객체를 사용하여 트랜잭션 처리를 하기 위함임.
			conn = JDBCUtil3.getConnection();
//			커밋을 하지 않고 트랜잭션으로 관리할때 사용함.
//			conn.setAutoCommit(false);
			cnt = memDao.insertMember(conn, mv);
//			로그 출력할 수 있음 : 메일발송이력DAO.writeLog(conn);
//			커밋 시킴
//			conn.commit();
		} catch (SQLException e) {
//			예외 발생 시 롤백처리
//			conn.rollback();
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}
		return cnt;
	}

	@Override
	public boolean checkMember(String memId) {
		
		boolean chk = false;
		
		try {
			conn = JDBCUtil3.getConnection();
			chk = memDao.checkMember(conn, memId);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}
		return chk;
	}

	@Override
	public List<MemberVO> getAllMemberList() {
		
		List<MemberVO> memList = new ArrayList<>();
		try {
			conn = JDBCUtil3.getConnection();
			memList = memDao.getAllMemberList(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}
		return memList;
	}

	@Override
	public int updateMember(MemberVO mv) {
		int cnt = 0;
		
		try {
			conn = JDBCUtil3.getConnection();
			cnt = memDao.updateMember(conn, mv);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}
		return cnt;
	}

	@Override
	public int deleteMember(String memId) {
		int cnt = 0;
		
		try {
			conn = JDBCUtil3.getConnection();
			cnt = memDao.deleteMember(conn, memId);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}
		
		return cnt;
	}

	@Override
	public List<MemberVO> getSearchMember(MemberVO mv) {
		List<MemberVO> memList = new ArrayList<>();
		
		try {
			conn = JDBCUtil3.getConnection();
			memList = memDao.getSearchMember(conn, mv);
		}catch(SQLException ex) {
			ex.printStackTrace();
		}finally {
			JDBCUtil3.disConnect(conn, null, null, null);
		}
		return memList;
	}

}
