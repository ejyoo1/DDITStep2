package kr.or.ddit.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.member.vo.MemberVO;
import kr.or.ddit.util.JDBCUtil3;

public class MemberDaoImpl implements IMemberDao {
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
//	private static MemberDaoImpl memDao; 
	private static IMemberDao memDao;//인터페이스로 사용함.(나중에 유지보수 쉽게하기 위해서)
	
	private MemberDaoImpl() {
		
	}
	
	public static IMemberDao getInstance() {
		if(memDao == null) {
			memDao = new MemberDaoImpl();
		}
		
		return memDao;
	}

	@Override
	public int insertMember(Connection conn, MemberVO mv) throws SQLException {

		String sql = " insert into mymember (mem_id, mem_name, mem_tel, mem_addr) " + " values (?, ?, ?, ?)";

		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, mv.getMemId());
		pstmt.setString(2, mv.getMemName());
		pstmt.setString(3, mv.getMemTel());
		pstmt.setString(4, mv.getMemAddr());

		int cnt = pstmt.executeUpdate();
		
		return cnt;
	}

	@Override
	public boolean checkMember(Connection conn, String memId) throws SQLException {
		boolean chk = false;
		try {
			// PreparedStatement를 위한 쿼리
			String sql = "select count(*) cnt from mymember where mem_id = ? ";
	
			pstmt = conn.prepareStatement(sql);
			// 스트링은 ''이 필요하기에 메서드를 잘 써야 함.
			pstmt.setString(1, memId);
	
			rs = pstmt.executeQuery();
	
			int cnt = 0;
			while (rs.next()) { // if(rs.next()){와 같음(결과가 1건이기 때문)
				cnt = rs.getInt("cnt");
			}
	
			if (cnt > 0) {
				chk = true;
			}
		} finally {
			JDBCUtil3.disConnect(null, stmt, null, null);
		}
		return chk;
	}

	@Override
	public List<MemberVO> getAllMemberList(Connection conn) throws SQLException {
		List<MemberVO> memList = new ArrayList<MemberVO>();
		
		try {
			// Statement를 위한 쿼리
			String sql = "select * from mymember";

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				MemberVO mv = new MemberVO();
				String memId = rs.getString("mem_id");
				String memName = rs.getString("mem_name");
				String memTel = rs.getString("mem_tel");
				String memAddr = rs.getString("mem_addr");
				
				mv.setMemId(memId);
				mv.setMemName(memName);
				mv.setMemTel(memTel);
				mv.setMemAddr(memAddr);
				
				memList.add(mv);
			}
		} finally {
			JDBCUtil3.disConnect(null, stmt, null, null);
		}
		
		
		return memList;
	}

	@Override
	public int updateMember(Connection conn, MemberVO mv) throws SQLException {
		int cnt = 0;
		try {
			String sql = " update mymember set " + "mem_name = ?, " + "mem_tel = ?, " + "mem_addr = ? "
					+ "where mem_id = ?";
	
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mv.getMemName());
			pstmt.setString(2, mv.getMemTel());
			pstmt.setString(3, mv.getMemAddr());
			pstmt.setString(4, mv.getMemId());
	
			cnt = pstmt.executeUpdate();
		} finally {
			JDBCUtil3.disConnect(null, null, pstmt, null);
		}
		return cnt;
	}

	@Override
	public int deleteMember(Connection conn, String memId) throws SQLException {
		int cnt = 0;
		try {
			String sql = " delete from mymember where mem_id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			
			cnt = pstmt.executeUpdate();
		}finally {
			JDBCUtil3.disConnect(null, null, pstmt, null);
		}
		
		return cnt;
	}
	
	
	//쿼리작성
	//사용자 요청에 따라 쿼리가 달리 작성되어야 함. 다이나믹 쿼리
	@Override
	public List<MemberVO> getSearchMember(Connection conn, MemberVO mv) throws SQLException {
//		결과를 담을 List 정의
		List<MemberVO> memList = new ArrayList<>();
		
//		쿼리를 날리는 부분 작성(쿼리 누적)
		try {
			//and를 쓰기위해 1=1을 사용(다이나믹 쿼리 위함)
			String sql = "select * from mymember where 1=1";
//			갖고온게 null이 아니고 빈칸이 아니면
			if (mv.getMemId() != null && !mv.getMemId().equals("")) {
				sql += " and mem_id = ?";
			}
			if (mv.getMemName() != null && !mv.getMemName().equals("")) {
				sql += " and mem_name = ?";
			}
			if (mv.getMemTel() != null && !mv.getMemTel().equals("")) {
				sql += " and mem_tel = ?";
			}
			if (mv.getMemAddr() != null && !mv.getMemAddr().equals("")) {
				sql += " and mem_addr like '%' || ? || '%' ";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			//값이 존재할 때 index변수를 사용하여 ? 에 데이터 삽입
			int index = 1;
			if (mv.getMemId() != null && !mv.getMemId().equals("")) {
				pstmt.setString(index++, mv.getMemId());
			}
			if (mv.getMemName() != null && !mv.getMemName().equals("")) {
				pstmt.setString(index++, mv.getMemName());
			}
			if (mv.getMemTel() != null && !mv.getMemTel().equals("")) {
				pstmt.setString(index++, mv.getMemTel());
			}
			if (mv.getMemAddr() != null && !mv.getMemAddr().equals("")) {
				pstmt.setString(index++, mv.getMemAddr());
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberVO mv2 = new MemberVO();
				mv2.setMemId(rs.getString("mem_id"));
				mv2.setMemName(rs.getString("mem_name"));
				mv2.setMemTel(rs.getString("mem_tel"));
				mv2.setMemAddr(rs.getString("mem_addr"));
				
//				데이터 담기
				memList.add(mv2);
			}
		}finally {
			JDBCUtil3.disConnect(null, null, pstmt, rs);
		}
		
		return memList;
	}
	
}
