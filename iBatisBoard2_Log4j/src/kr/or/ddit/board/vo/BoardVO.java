package kr.or.ddit.board.vo;

/**
 * DB 테이블에 있는 컬럼을 기준으로 데이터를 객체화한 클래스이다.
 * @author 유은지
 * <p>
 * DB테이블의 '컬럼'이 이 클래스의 '멤버변수' 가 된다. <br>
 * DB테이블의 컬럼과 클래스의 멤버변수를 매핑하는 역할을 수행한다. <br>
 */
public class BoardVO {
	private String boardNo; //board_no number not null,  -- 번호(자동증가)
    private String boardTitle; //board_title varchar2(100) not null, -- 제목
    private String boardWriter; //board_writer varchar2(50) not null, -- 작성자
    private String boardDate; //board_date date not null,   -- 작성날짜
    private String boardContent; //board_content clob,     -- 내용
    
	public String getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(String boardNo) {
		this.boardNo = boardNo;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardWriter() {
		return boardWriter;
	}
	public void setBoardWriter(String boardWriter) {
		this.boardWriter = boardWriter;
	}
	public String getBoardDate() {
		return boardDate;
	}
	public void setBoardDate(String boardDate) {
		this.boardDate = boardDate;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	
	@Override
	public String toString() {
		return "BoardVO [boardNo=" + boardNo + ", boardTitle=" + boardTitle + ", boardWriter=" + boardWriter
				+ ", boardDate=" + boardDate + ", boardContent=" + boardContent + "]";
	}
    
}
