package kr.or.ddit.comm.dao;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import kr.or.ddit.member.vo.AtchFileVO;
import kr.or.ddit.util.SqlMapClientUtil;

/**
 * 첨부파일 관련 기능은 atchFileDao 에서 수행
 * @author 유은지
 *
 */
public class AtchFileDaoImpl implements IAtchFileDao {
	private static IAtchFileDao dao;
	private SqlMapClient smc;

	private AtchFileDaoImpl() {
		smc = SqlMapClientUtil.getInstance();
	}

	public static IAtchFileDao getInstance() {
		if(dao == null) {
			dao = new AtchFileDaoImpl();
		}

		return dao;
	}

	@Override
	public List<AtchFileVO> getAtchFileList(AtchFileVO fileVO) throws SQLException {

		List<AtchFileVO> atchFileList = Collections.EMPTY_LIST; // EMPTY_LIST : 비어있는 리스트를 던져주는 Util 기능(초기화 할 때 값을 빈 상태로 쓰겠다.) => immutable 객체가 됨. (변경을 못하는 것) (String 처럼 변경할 때 새로운 String 방을 만드는 것처럼 EMPTY_LIST 도 final 처럼 값을 변경할 수 없다.) ==> 멀티 쓰레드 할 때, 객체 생성 시 변경 못하게 막으로면 이와 비슷한 방법으로 immutable로 만든다.
		atchFileList = smc.queryForList("atchFile.getAtchFileList", fileVO);

		return atchFileList;
	}

	@Override
	public AtchFileVO getAtchFileDetail(AtchFileVO fileVO) throws SQLException {
		AtchFileVO atchFileVO = (AtchFileVO) smc.queryForObject("atchFile.getAtchFileDetail", fileVO); // attach 파일 명과 파일 순번을 기준으로 구분됨(key가 2개)
		return atchFileVO;
	}

	@Override
	public int insertAtchFile(AtchFileVO atchFileVO) throws SQLException {

		int cnt = 0;

		Object obj = smc.insert("atchFile.insertAtchFile", atchFileVO);

		if(obj == null) {
			cnt = 1;
		}
		return cnt;
	}

	@Override
	public int insertAtchFileDetail(AtchFileVO atchFileVO) throws SQLException {

		int cnt = 0;

		Object obj = smc.insert("atchFile.insertAtchFileDetail", atchFileVO);

		if(obj == null) {
			cnt = 1;
		}
		return cnt;
	}

}
