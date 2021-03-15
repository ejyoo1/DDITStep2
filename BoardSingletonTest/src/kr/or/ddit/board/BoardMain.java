package kr.or.ddit.board;

import java.util.List;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.board.vo.BoardVO;
import kr.or.ddit.util.ScanUtil;

public class BoardMain {
	private IBoardService boardService;
	
	public BoardMain() {
		boardService = BoardServiceImpl.getInstance();
	}
	
	public static void main(String[] args) {
		new BoardMain().start();
	}
	
	/**
	 * 프로그램 시작
	 */
	public void start() {
		int userchoice;
		while(true) {
			displayMenu();
			userchoice = ScanUtil.nextInt();
			switch(userchoice) {
			case 1: //전체 목록 출력
				displayAll();
				break;
			case 2: //새글 작성
				insertBoard();
				break;
			case 3: //수정
				updateBoard();
				break;
			case 4: //삭제
				deleteBoard();
				break;
			case 5: //검색
				displaySearch();
				break;
			case 6: //프로그램 종료
				System.exit(0);
			default: //잘못입력한 경우
				System.out.println("번호를 잘못 입력했습니다. 다시 입력하세요.");
			}
		}
	}
	
	/**
	 * 게시글 검색
	 */
	private void displaySearch() {
		System.out.println("검색할 게시판의 제목을 입력하세요.");
		System.out.print("검색할 글자(일부만 입력해도 됨) > ");
		String boardTitle = ScanUtil.nextLine();
		System.out.println("검색할 작성자(정확히 입력할 것) > ");
		String boardWriter = ScanUtil.nextLine();
		System.out.println("검색할 내용(일부만 입력해도 됨) > ");
		String boardContent = ScanUtil.nextLine();
		BoardVO bv = new BoardVO();
		bv.setBoardTitle(boardTitle);
		bv.setBoardWriter(boardWriter);
		bv.setBoardContent(boardContent);
		
		System.out.println("검색 결과 확인합니다.");
		System.out.println("============================================");
		System.out.println("번호\t제목\t작성자\t작성일\t내용");
		System.out.println("============================================");
		
		List<BoardVO> boardList = boardService.boardSerch(bv);
		
		for(BoardVO bv1 : boardList) {
			System.out.println(
					bv1.getBoardNo() 		+ "\t" +
					bv1.getBoardTitle() 		+ "\t" +
					bv1.getBoardWriter() 	+ "\t" +
					bv1.getBoardDate() 		+ "\t" +
					bv1.getBoardContent()
					);
		}
		System.out.println("============================================");
		System.out.println("출력작업 끝");
		
	}

	/**
	 * 게시글 삭제
	 */
	private void deleteBoard() {
		boolean chk = false;
		String boardNo = null;
		
		do {
			System.out.println("삭제할 게시글 번호를 입력하세요.");
			System.out.print("게시글 ID >> ");
			boardNo = ScanUtil.nextLine();
			
			chk = boardService.checkBoard(boardNo);
			
			if(chk == false) {
				System.out.println("게시글 번호가 " + boardNo + "인 게시글이 없습니다.");
				System.out.println("다시 입력해주세요.");
			}
		} while(chk == false);
		
//		게시글 번호를 정상적으로 입력한 경우 아래 코드 실행
		int cnt = boardService.deleteBoard(boardNo);
		
		if(cnt > 0) {
			System.out.println(boardNo + "게시글 삭제 작업 성공");
		} else {
			System.out.println(boardNo + "게시글 삭제 작업 실패!!!");
		}
	}

	/**
	 * 게시글 수정
	 */
	private void updateBoard() {
		boolean chk = false;
		String boardNo = null;
		
		do {
			System.out.println("수정할 게시글 번호를 입력하세요.");
			System.out.print("게시글 ID >> ");
			boardNo = ScanUtil.nextLine();
			
			chk = boardService.checkBoard(boardNo);
			
			if(chk == false) {
				System.out.println("게시글 번호가 " + boardNo + "인 게시글이 없습니다.");
				System.out.println("다시 입력해주세요.");
			}
		} while(chk == false);
		
//		게시글 번호를 정상적으로 입력한 경우 아래 코드 실행
		System.out.println("게시글 제목 >> ");
		String boardTitle = ScanUtil.nextLine();
		System.out.println("게시글 내용 >> ");
		String boardContent = ScanUtil.nextLine();
		
		BoardVO bv = new BoardVO();
		bv.setBoardTitle(boardTitle);
		bv.setBoardContent(boardContent);
		
		int cnt = boardService.updateBoard(bv);
		
		if(cnt > 0) {
			System.out.println(boardNo + "게시글 수정 작업 성공");
		} else {
			System.out.println(boardNo + "게시글 수정 작업 실패!!!");
		}
	}

	/**
	 * 게시글 삽입
	 */
	private void insertBoard() {
		System.out.println("추가할 게시판을 정보를 입력하세요.");
		System.out.print("게시판 제목 > ");
		String boardTitle = ScanUtil.nextLine();
		System.out.print("작성자 이름 > ");
		String boardWriter = ScanUtil.nextLine();
		System.out.println("작성 내용 > ");
		String boardContent = ScanUtil.nextLine();
		
		BoardVO bv = new BoardVO();
		bv.setBoardTitle(boardTitle);
		bv.setBoardWriter(boardWriter);
		bv.setBoardContent(boardContent);
		
		int cnt = boardService.insertBoard(bv);
		
		if(cnt > 0) {
			System.out.println("게시글 작성 완료");
		}else {
			System.out.println("게시글 작성 실패!!!");
		}
		
	}
	
	/**
	 * 전체 게시판 출력
	 */
	private void displayAll() {
		System.out.println("전체 게시판을 확인합니다.");
		System.out.println("============================================");
		System.out.println("번호\t제목\t작성자\t작성일\t내용");
		System.out.println("============================================");
		
		List<BoardVO> boardList = boardService.getAllBoardList();
		
		for(BoardVO bv : boardList) {
			System.out.println(
					bv.getBoardNo() 		+ "\t" +
					bv.getBoardTitle() 		+ "\t" +
					bv.getBoardWriter() 	+ "\t" +
					bv.getBoardDate() 		+ "\t" +
					bv.getBoardContent()
					);
		}
		System.out.println("============================================");
		System.out.println("출력작업 끝");
	}

	/**
	 * 메뉴 출력
	 */
	private void displayMenu() {
		System.out.println();
		System.out.println("----------------------");
		System.out.println("  === 작 업 선 택 ===");
		System.out.println("  1. 전체 목록 출력");
		System.out.println("  2. 새글 작성");
		System.out.println("  3. 수정");
		System.out.println("  4. 삭제");
		System.out.println("  5. 검색");
		System.out.println("  6. 프로그램 종료");
		System.out.println("----------------------");
		System.out.print("원하는 작업 선택 >> ");
	}
}
