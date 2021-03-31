package kr.or.ddit.rmi.vo;

import java.io.Serializable;

/**
 * 파일 전송용 VO 클래스( 정보를 담을 곳 ) 
 * @author 유은지
 *  
 */
public class FileInfoVO implements Serializable{
	private String fileName;
	private byte[] fileData;//파일데이터를 바이트 배열로 넣을 것임
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getFileData() {
		return fileData;
	}
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	
	
}
