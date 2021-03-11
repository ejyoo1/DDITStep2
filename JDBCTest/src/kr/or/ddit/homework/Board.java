package kr.or.ddit.homework;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import kr.or.ddit.util.JDBCUtil3;
import kr.or.ddit.util.ScanUtil;

public class Board {
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public static void main(String[] args) {
		/**
		 * 기능 구현하기 ==> 전체 목록 출력, 새글작성, 수정, 삭제, 검색 
		 */
		new Board().start();
	}
	
	/**
	 * 프로그램 시작
	 */
	public void start() {
		while(true) {
		System.out.println("=========================================");
		System.out.println("게시판 관리 프로그램 입니다.");
		System.out.println("=========================================");
		System.out.println("1.전체목록출력\t2.새글작성\t3.수정\t4.삭제\t5.검색\t6.프로그램종료");
		System.out.print("번호입력 > ");
		int userInput = ScanUtil.nextInt();
			switch(userInput) {
				case 1:
					selectAll();
					break;
				case 2: 
					insertBoard();
					break;
				case 3:
					updateBoard();
					break;
				case 4:
					deleteBoard();
					break;
				case 5:
					selectOne();
					break;
				case 6:
					System.out.println("프로그램을 종료합니다.");
					System.exit(0);
				default: System.out.println("번호를 잘못입력했습니다. 다시입력하세요");
			}
		}
	}
	
	/**
	 * 게시판 검색하기
	 */
	private void selectOne() {
		//전체 게시글 확인
		selectAll();
		System.out.println("검색할 게시판의 제목을 입력하세요.");
		System.out.print("검색할 글자(일부만 입력해도 됨) > ");
		String boardTitle = ScanUtil.nextLine();
		System.out.println("검색할 작성자(정확히 입력할 것) > ");
		String boardWriter = ScanUtil.nextLine();
		System.out.println("검색할 내용(일부만 입력해도 됨) > ");
		String boardContent = ScanUtil.nextLine();
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = " SELECT * FROM JDBC_BOARD "
					+ "WHERE BOARD_TITLE LIKE '%'||?||'%' AND "
					+ "BOARD_WRITER = ? AND "
					+ "BOARD_CONTENT LIKE '%'||?||'%' ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardTitle);
			pstmt.setString(2, boardWriter);
			pstmt.setString(3, boardContent);
			
			int cnt = pstmt.executeUpdate();
			if(cnt > 0) {
				rs = pstmt.executeQuery();
				System.out.println("============================================");
				System.out.println("검색결과");
				System.out.println("============================================");
				while(rs.next()) {
					String 	boardNo = rs.getString("BOARD_NO");
					boardTitle = rs.getString("BOARD_TITLE");
					boardWriter = rs.getString("BOARD_WRITER");
					String 	boardDate = rs.getString("BOARD_DATE");
					boardContent = rs.getString("BOARD_CONTENT");
					
					System.out.println(
							boardNo 		+ "\t" +
									boardTitle 		+ "\t" +
									boardWriter 	+ "\t" +
									boardDate 		+ "\t" +
									boardContent
							);
				}
			}else{
				System.out.println("검색결과가 없습니다.");
			}
			
			System.out.println("============================================");
			System.out.println("검색 끝");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}
	}

	/**
	 * 게시판 삭제하기
	 */
	private void deleteBoard() {
		System.out.println("==게시판 삭제==");
		boolean chk = false;
		String boardNo = null;
		
		do {
			System.out.println("삭제할 게시글의 번호를 입력하세요");
			System.out.print("게시글 번호 > ");
			boardNo = ScanUtil.nextLine();
			
			chk = checkBoard(boardNo);
			
			if(chk == false) {
				System.out.println("게시글 번호 : " + boardNo + "가 없습니다.");
				System.out.println("다시 입력하세요.");
			}
		} while (chk == false);
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = " DELECT FROM JDBC_BOARD WHERE BOARD_NO=? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardNo);
			int cnt = pstmt.executeUpdate();
			
			if(cnt > 0) {
				System.out.println("게시글 삭제 완료");
			} else {
				System.out.println("게시글 삭제 실패");
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}
	}

	/**
	 * 게시판 존재 여부 검사
	 * @param boardNo : 게시판 번호(사용자 입력)
	 * @return chk : 게시판 번호 검색 결과 [true : 게시글 존재 / false : 게시글 미존재]
	 */
	private boolean checkBoard(String boardNo) {
		boolean chk = false;
		
		try {
			conn = JDBCUtil3.getConnection();
					
			String sql = " SELECT COUNT(*) CNT FROM JDBC_BOARD WHERE BOARD_NO=? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardNo);
			rs = pstmt.executeQuery();
			
			int cnt = 0;
			while(rs.next()) {
				cnt = rs.getInt("CNT");
			}
			
			if(cnt > 0) {
				chk = true;
			}
			
		}catch (SQLException ex) {// 예외발생할 것이 없으면 컴파일러가 빨간줄 표시로 아려줌
			ex.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}
		return chk;
	}

	/**
	 * 게시판수정하기
	 */
	private void updateBoard() {
		System.out.println("==게시판 수정==");
		boolean chk = false;
		String boardNo = null;
		
		do {
			System.out.println("수정할 게시글의 번호를 입력하세요");
			System.out.print("게시글 번호 > ");
			boardNo = ScanUtil.nextLine();
			
			chk = checkBoard(boardNo);
			
			if(chk == false) {
				System.out.println("게시글 번호 : " + boardNo + "가 없습니다.");
				System.out.println("다시 입력하세요.");
			}
		} while (chk == false);
		
		try {
			conn = JDBCUtil3.getConnection();
		
			System.out.println("추가할 게시글을 정보를 입력하세요.");
			System.out.print("게시판 제목 > ");
			String boardTitle = ScanUtil.nextLine();
			System.out.println("작성 내용 > ");
			String boardContent = ScanUtil.nextLine();
			                                                                    
			String sql = " UPDATE JDBC_BOARD SET BOARD_TITLE = ?, BOARD_CONTENT = ? WHERE BOARD_NO = ? ";
		
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardTitle);
			pstmt.setString(2, boardContent);
			pstmt.setString(3, boardNo);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt > 0) {
				System.out.println("게시글 수정 완료");
			} else {
				System.out.println("게시글 수정 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}
	}

	/**
	 * 게시글 삽입
	 */
	private void insertBoard() {
		try {
			conn = JDBCUtil3.getConnection();
		
			System.out.println("추가할 게시판을 정보를 입력하세요.");
			System.out.print("게시판 제목 > ");
			String boardTitle = ScanUtil.nextLine();
			System.out.print("작성자 이름 > ");
			String boardWriter = ScanUtil.nextLine();
			System.out.println("작성 내용 > ");
			String boardContent = ScanUtil.nextLine();
			                                                                    
			String sql = " INSERT INTO JDBC_BOARD(BOARD_NO, BOARD_TITLE, BOARD_WRITER, BOARD_DATE, BOARD_CONTENT) " + 
					" VALUES(BOARD_SEQ.NEXTVAL, ?, ?, SYSDATE, ?)";
		
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardTitle);
			pstmt.setString(2, boardWriter);
			pstmt.setString(3, boardContent);
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt > 0) {
				System.out.println("게시글 작성 완료");
			} else {
				System.out.println("게시글 작성 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}
	}
	
	
	/**
	 * 게시글 전체 보기
	 */
	private void selectAll() {
		System.out.println("전체 게시판을 확인합니다.");
		System.out.println("============================================");
		System.out.println("번호\t제목\t작성자\t작성일\t내용");
		System.out.println("============================================");
		
		try {
			conn = JDBCUtil3.getConnection();
			
			String sql = "SELECT * FROM JDBC_BOARD";
		
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String boardNo = rs.getString("BOARD_NO");
				String boardTitle = rs.getString("BOARD_TITLE");
				String boardWriter = rs.getString("BOARD_WRITER");
				String boardDate = rs.getString("BOARD_DATE");
				String boardContent = rs.getString("BOARD_CONTENT");
				
				System.out.println(
						boardNo 		+ "\t" +
						boardTitle 		+ "\t" +
						boardWriter 	+ "\t" +
						boardDate 		+ "\t" +
						boardContent
						);
			}
			System.out.println("============================================");
			System.out.println("출력작업 끝");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil3.disConnect(conn, stmt, pstmt, rs);
		}
		
	}
}
