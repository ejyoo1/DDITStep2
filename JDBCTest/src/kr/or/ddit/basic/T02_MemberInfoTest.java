package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import kr.or.ddit.util.JDBCUtil3;

/*
	회원정보를 관리하는 프로그램을 작성하는데 
	아래의 메뉴를 모두 구현하시오. (CRUD기능 구현하기)
	(DB의 MYMEMBER테이블을 이용하여 작업한다.)
	
	* 자료 삭제는 회원ID를 입력 받아서 삭제한다.
	
	예시메뉴)
	----------------------
		== 작업 선택 ==
		1. 자료 입력			---> insert
		2. 자료 삭제			---> delete
		3. 자료 수정			---> update
		4. 전체 자료 출력	---> select
		5. 작업 끝.
	----------------------
	 
	   
// 회원관리 프로그램 테이블 생성 스크립트 
create table mymember(
    mem_id varchar2(8) not null,  -- 회원ID
    mem_name varchar2(100) not null, -- 이름
    mem_tel varchar2(50) not null, -- 전화번호
    mem_addr varchar2(128),    -- 주소
    CONSTRAINT MYMEMBER_PK PRIMARY KEY (mem_id)
);

*/



public class T02_MemberInfoTest {
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private Scanner scan = new Scanner(System.in);

	
	public static void main(String[] args) {
		new T02_MemberInfoTest().start();
	}
	
	
	/**
	 * 메뉴를 출력하는 메서드
	 */
	public void displayMenu() {
		System.out.println();
		System.out.println("----------------------");
		System.out.println("  === 작 업 선 택 ===");
		System.out.println("  1. 자료 입력");
		System.out.println("  2. 자료 삭제");
		System.out.println("  3. 자료 수정");
		System.out.println("  4. 전체 자료 출력");
		System.out.println("  5. 작업 끝.");
		System.out.println("----------------------");
		System.out.print("원하는 작업 선택 >> ");
	}

	/**
	 * 프로그램 시작메서드
	 */
	public void start() {
		int choice;
		do {
			displayMenu(); // 메뉴 출력
			choice = scan.nextInt(); // 메뉴번호 입력받기
			switch (choice) {
			case 1: // 자료 입력
				insertMember();
				break;
			case 2: // 자료 삭제
				deleteMember();
				break;
			case 3: // 자료 수정
				updateMember();
				break;
			case 4: // 전체 자료 출력
				displayAll();
				break;
			case 5: // 작업 끝
				System.out.println("작업을 마칩니다.");
				break;
			default:
				System.out.println("번호를 잘못 입력했습니다. 다시입력하세요");
			}
		} while (choice != 5);
	}

	/**
	 * 회원 정보를 삭제하는 메서드(입력받은 회원ID를 이용하여 삭제한다.)
	 */
	private void deleteMember() {
		boolean chk = false;
		String memId = null;

		do {
			System.out.println();
			System.out.println("삭제할 회원 정보를 입력하세요 ");
			System.out.print("회원 ID >> ");
			memId = scan.next();

			chk = checkMember(memId);

			if (chk == false) {
				System.out.println("회원ID가  " + memId + "인 회원이 없습니다.");
				System.out.println("다시입력해주세요.");
			}
		} while (chk == false);

		// 정상적인 회원 ID를 넣은경우 아래의 코드 실행

		try {
			conn = JDBCUtil3.getConnection();

			// Ststement를 사용하면 쿼리문 안에 변수가 ㅇ직접적으로 들어가기 때문에 : SQL Injection 발생할 수 있으며
			// 해커가 의도적으로 잘못된 데이터를 삽입하여 쿼리를 무력화 시킬 수 있음.
			/*
			 * select * from member where mem_id = 'b001' and mem_pass = '1004'; 라는 쿼리가 있을
			 * 때, 해커가 의도적으로 select * from member where mem_id = 'b001' and mem_pass = '' 일 때
			 * b001대신에 해커가 'or 1=1 -- 을 의도적으로 넣는다 . 이때, 들어가는 순간 true가 되며 뒤에있는 쿼리가 주석처리 되므로
			 * 모든 쿼리 결과를 불러오게 되는것이다. 그래서 쿼리가 무력화 된다.
			 */

			/*
			 * prepareStatement는 삽입하는 ?에 들어갈 value만 필요하기 때문에 이미 sql ?를 갖고있는 상태로 오라클에서 내부적으로
			 * 컴파일이 되어있으며, 실행할때 ?값이 넣어진다. 상수로만 판단하기 때무에 분석하고 해석하는 단계가 없다. ?를 대체하는 값으로만
			 * 생각하기때문에 아무런 문제가 없다. sqlInjection 발생할 수 없다. statement는 실행할떄 sql을 컴파일 한다.
			 */
			String sql = " delete from mymember where mem_id = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);

			int cnt = pstmt.executeUpdate();

			if (cnt > 0) {
				System.out.println(memId + "회원 정보 삭제 작업 성공");
			} else {
				System.out.println(memId + "회원 정보 삭제 실패!!!");
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("회원 삭제에 실패하였습니다.");
		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}
	}

	/**
	 * 회원정보를 수정하는 메서드
	 */
	private void updateMember() {
		boolean chk = false;
		String memId = null;

		do {
			System.out.println();
			System.out.println("수정할 회원 정보를 입력하세요 ");
			System.out.print("회원 ID >> ");
			memId = scan.next();

			chk = checkMember(memId);

			if (chk == false) {
				System.out.println("회원ID가  " + memId + "인 회원이 없습니다.");
				System.out.println("다시입력해주세요.");
			}
		} while (chk == false);

		// 정상적인 회원 ID를 넣은경우 아래의 코드 실행

		System.out.print("회원 이름 >> ");
		String memName = scan.next();

		System.out.print("회원 전화번호 >> ");
		String memTel = scan.next();

		scan.nextLine(); // 입력버퍼 지우기
		System.out.print("회원 주소 >> ");
		String memAddr = scan.nextLine();

		try {
			conn = JDBCUtil3.getConnection();

			String sql = " update mymember set " + "mem_name = ?, " + "mem_tel = ?, " + "mem_addr = ? "
					+ "where mem_id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memName);
			pstmt.setString(2, memTel);
			pstmt.setString(3, memAddr);
			pstmt.setString(4, memId);

			int cnt = pstmt.executeUpdate();

			if (cnt > 0) {
				System.out.println(memId + "회원 정보 수정 작업 성공");
			} else {
				System.out.println(memId + "회원 정보 수정 실패!!!");
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}
	}

	/**
	 * 전체 회원을 출력하는 메서드
	 */
	private void displayAll() {
		System.out.println();
		System.out.println("----------------------------------------");
		System.out.println("ID\t이름\t전화번호\t주소");
		System.out.println("----------------------------------------");

		try {
			conn = JDBCUtil3.getConnection();

			// Statement를 위한 쿼리
			String sql = "select * from mymember";

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String memId = rs.getString("mem_id");
				String memName = rs.getString("mem_name");
				String memTel = rs.getString("mem_tel");
				String memAddr = rs.getString("mem_addr");
				System.out.println(memId + "\t" + memName + "\t" + memTel + "\t" + memAddr);
			}
			System.out.println("----------------------------------------");
			System.out.println("출력작업 끝...");

		} catch (SQLException ex) {// 예외발생할 것이 없으면 컴파일러가 빨간줄 표시로 아려줌
			ex.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}

	}
	

	/**
	 * 회원을 추가하기 위한 메서드
	 */
	private void insertMember() {
		boolean chk = false;
		String memId = null;

		do {
			System.out.println();
			System.out.println("추가할 회원 정보를 입력하세요 ");
			System.out.print("회원 ID >> ");
			memId = scan.next();

			chk = checkMember(memId);

			if (chk == true) {
				System.out.println("회원ID가  " + memId + "인 회원이 이미 존재합니다.");
				System.out.println("다시입력해주세요.");
			}
		} while (chk == true);

		System.out.print("회원 이름 >> ");
		String memName = scan.next();

		System.out.print("회원 전화번호 >> ");
		String memTel = scan.next();

		scan.nextLine(); // 입력버퍼 지우기
		System.out.print("회원 주소 >> ");
		String memAddr = scan.nextLine();

		try {
			conn = JDBCUtil3.getConnection();

			String sql = " insert into mymember (mem_id, mem_name, mem_tel, mem_addr) " + " values (?, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			pstmt.setString(2, memName);
			pstmt.setString(3, memTel);
			pstmt.setString(4, memAddr);

			int cnt = pstmt.executeUpdate();

			if (cnt > 0) {
				System.out.println(memId + "회원 추가 작업 성공");
			} else {
				System.out.println(memId + "회원 추가 작업 실패!!!");
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}

	}

	/**
	 * 회원 id를 이용하여 회원이 있는지 알려주는 메서드
	 * 
	 * @param memId
	 *            : 사용자가 입력한 멤버 아이디
	 * @return 존재하면 true, 없으면 false
	 */
	private boolean checkMember(String memId) {
		boolean chk = false;

		try {
			conn = JDBCUtil3.getConnection();

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

		} catch (SQLException ex) {// 예외발생할 것이 없으면 컴파일러가 빨간줄 표시로 아려줌
			ex.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}

		return chk;
	}
	
	

}
