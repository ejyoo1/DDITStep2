package kr.or.ddit.comm.service;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;

import kr.or.ddit.comm.dao.AtchFileDaoImpl;
import kr.or.ddit.comm.dao.IAtchFileDao;
import kr.or.ddit.member.vo.AtchFileVO;
import kr.or.ddit.util.FileUploadRequestWrapper;

public class AtchFileServiceImpl implements IAtchFileService {

	private static IAtchFileService fileService;
	private IAtchFileDao fileDao;
	
	private AtchFileServiceImpl() {
		fileDao = AtchFileDaoImpl.getInstance();
	}
	
	public static IAtchFileService getInstance() {
		if(fileService == null) {
			fileService = new AtchFileServiceImpl();
		}
		
		return fileService;
	}
	
	/**
	 * 비즈니스 로직 추가 => 파일 중복 시 처리 해야 함
	 * FileItem : 아파치 톰캣 => pasing (requestMapper에서 따로 뽑아낸 정보)
	 * 파일 업로드 하는 작업은 공통 서비스에서 사용
	 * 
	 * 핸들러 쪽에서 fileItem을 가지고 saveAttachFile 호출할 것임.
	 */
	@Override
	public AtchFileVO saveAtchFile(FileItem item) throws Exception {
		
		// 파일 업로드 경로 체크
		File uploadDir = new File(FileUploadRequestWrapper.UPLOAD_DIRECTORY); // 현재 디렉토리에 업로드
		if(!uploadDir.exists()) { // uploadDir이 있는지 판단 false 면 경로 생성
			uploadDir.mkdir();
		}
		
		String orignFileName = new File(item.getName()).getName(); // 파일명만 추출하기
		long fileSize = item.getSize(); // 파일 사이즈 가져오기
		String storeFileName = "";
		String filePath = "";
		File storeFile = null;
		
		do {
			// 저장 파일명 생성
			// UUID 클래스 : 중복되지 않은 아이디를 만드는 역할. 랜덤해서 추출하지만 100% 완벽하지 않기에 do-while문으로 체크함. 
			storeFileName = UUID.randomUUID().toString().replace("-", "");
			filePath = FileUploadRequestWrapper.UPLOAD_DIRECTORY + File.separator + storeFileName; // 경로 세팅
			storeFile = new File(filePath); // 파일 패스가 새로운 객체에 적재됨.
		}while(storeFile.exists()); // 파일명이 중복되지 않을 때까지..
		
		// 확장자명 추출
		String fileExtension = orignFileName.lastIndexOf(".") < 0 ? "" : orignFileName.substring(orignFileName.lastIndexOf(".") + 1);
		
		item.write(storeFile); // 업로드 파일 저장 ★★★★★★ (랜덤한 이름으로 파일을 저장)
		
		// DB에 저장하여 관리하기 위해 파일 저장 서비스 호출
		AtchFileVO atchFileVO = new AtchFileVO();
		
		fileDao.insertAtchFile(atchFileVO); // 파일 정보 저장
		
		atchFileVO.setStreFileNm(storeFileName);
		atchFileVO.setFileSize(fileSize);
		atchFileVO.setOrignlFileNm(orignFileName);
		atchFileVO.setFileStreCours(filePath);
		atchFileVO.setFileExtsn(fileExtension);
		
		// 파일 세부 정보 저장 쿼리 실행
		fileDao.insertAtchFileDetail(atchFileVO); 
		
		item.delete(); // 임시 업로드 파일 삭제하기
		
		return atchFileVO; // 서비스 호출하는 곳으로 리턴하여 파일 정보를 사용할 수 있게함.
		
	}

	/**
	 * 비즈니스 로직 추가 => 파일 중복 시 처리 해야 함
	 */
	@Override
	public AtchFileVO saveAtchFileList(Map<String, Object> fileItemMap) throws Exception {
		
		return null;
	}

	@Override
	public List<AtchFileVO> getAtchFileList(AtchFileVO fileVO) throws SQLException {
		return fileDao.getAtchFileList(fileVO); // 파일을 바로 읽어오는 것으로 바로 리턴
	}

	@Override
	public AtchFileVO getAtchFileDetail(AtchFileVO fileVO) throws SQLException {
		
		return fileDao.getAtchFileDetail(fileVO);
	}
	
}
