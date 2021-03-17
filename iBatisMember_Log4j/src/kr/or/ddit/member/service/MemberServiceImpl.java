package kr.or.ddit.member.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.member.dao.IMemberDao;
import kr.or.ddit.member.dao.MemberDaoImpl;
import kr.or.ddit.member.vo.MemberVO;
import kr.or.ddit.util.SqlMapClientUtil;

public class MemberServiceImpl implements IMemberService {
	
//	사용할 DAO의 객체 변수를 선언한다.
//	나중에 implements된 다른 메서드로 편하게 바꾸기 위해 인터페이스 객체 생성함(다형성)
	private IMemberDao memDao;
//	커넥션 객체 담기위한 메서드 객체 선언
	private SqlMapClient smc;
	
	private static IMemberService memService;
	
	private MemberServiceImpl() {
		memDao = MemberDaoImpl.getInstance();
//		서비스 객체가 만들어지는 시점에 smc 객체도 만들어진다.
		smc = SqlMapClientUtil.getInstance();
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
//			트랜잭션 관련 처리
//			트랜잭션으로 관리를 시작하겠다.
//			smc.startTransaction();
			cnt = memDao.insertMember(smc, mv);
//			DB에서 commit 하겠다.
//			smc.commitTransaction();
		} catch (SQLException e) {//ibatis에서 처리하더라도 SQL작업에 대한 예외처리는 발생가능성이 있음.
			/*
			Sql문을 실행하여 예외가 발생한 경우 commit 코드가 진행되지 않고 바로 catch문을 들어오게된다. 
//			smc.endTransaction(); 이것을 사용하여 트랜잭션을 종료하면 롤백과 같은 효과를 볼 수 있다.
			
			try {
				smc.endTransaction();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			*/
			e.printStackTrace();
		}//ibatis에서 리소스 관리도 자동으로 해주기 때문에 자원 반납은 하지 않음.
		return cnt;
	}

	@Override
	public boolean checkMember(String memId) {
		
		boolean chk = false;
		
		try {
			chk = memDao.checkMember(smc, memId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chk;
	}

	@Override
	public List<MemberVO> getAllMemberList() {
		
		List<MemberVO> memList = new ArrayList<>();
		try {
			memList = memDao.getAllMemberList(smc);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memList;
	}

	@Override
	public int updateMember(MemberVO mv) {
		int cnt = 0;
		
		try {
			cnt = memDao.updateMember(smc, mv);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	@Override
	public int deleteMember(String memId) {
		int cnt = 0;
		
		try {
			cnt = memDao.deleteMember(smc, memId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cnt;
	}

	@Override
	public List<MemberVO> getSearchMember(MemberVO mv) {
		List<MemberVO> memList = new ArrayList<>();
		
		try {
			memList = memDao.getSearchMember(smc, mv);
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return memList;
	}

}
