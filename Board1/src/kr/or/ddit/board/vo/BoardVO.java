package kr.or.ddit.board.vo;

public class BoardVO {
	private String BOARD_NO;
	private String BOARD_TITLE;
	private String BOARD_WRITER;
	private String BOARD_DATE;
	private String BOARD_CONTENT;
	private long atchFileId = -1;
	
	public String getBOARD_NO() {
		return BOARD_NO;
	}
	public void setBOARD_NO(String bOARD_NO) {
		BOARD_NO = bOARD_NO;
	}
	
	public String getBOARD_TITLE() {
		return BOARD_TITLE;
	}
	public void setBOARD_TITLE(String bOARD_TITLE) {
		BOARD_TITLE = bOARD_TITLE;
	}
	
	public String getBOARD_WRITER() {
		return BOARD_WRITER;
	}
	public void setBOARD_WRITER(String bOARD_WRITER) {
		BOARD_WRITER = bOARD_WRITER;
	}
	
	public String getBOARD_DATE() {
		return BOARD_DATE;
	}
	public void setBOARD_DATE(String bOARD_DATE) {
		BOARD_DATE = bOARD_DATE;
	}
	
	public String getBOARD_CONTENT() {
		return BOARD_CONTENT;
	}
	public void setBOARD_CONTENT(String bOARD_CONTENT) {
		BOARD_CONTENT = bOARD_CONTENT;
	}
	
	public long getAtchFileId() {
		return atchFileId;
	}
	public void setAtchFileId(long atchFileId) {
		this.atchFileId = atchFileId;
	}
	
	
}
