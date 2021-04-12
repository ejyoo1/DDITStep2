package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.member.vo.MemberVO;

/**
 * 회원정보 처리를 수행하는 서비스
 * @author 유은지
 *
 */
public interface IMemberService {
	
	/**
	 * 회원 등록 메서드
	 * @param mv DB에 insert할 자료가 저장된 MemberVO 객체
	 * @return DB작업이 성공하면 1이상의 값이 반환되고, 실패하면 0이 반환된다.
	 */
	public int insertMember(MemberVO mv);
	
	/**
	 * 주어진 회원 ID가 존재하는지 여부를 알아내는 메서드
	 * @param memId 회원 ID
	 * @return 해당 회원 ID가 존재하면 true, 존재하지 않으면 false
	 */
	public boolean checkMember(String memId);
	
	/**
	 * 회원정보 전체 목록을 가져오는 메서드
	 * @return 회원정보를 담고있는 List 객체
	 */
	public List<MemberVO> getAllMemberList();
	
	/**
	 * 회원정보를 수정하는 메서드
	 * @param mv 회원정보 객체
	 * @return 작업성공 : 1, 작업 실패 : 0
	 */
	public int updateMember(MemberVO mv);
	
	/**
	 * 회원정보를 삭제하는 메서드
	 * @param memId 삭제할 회원ID
	 * @return 작업성공 : 1, 작업 실패 : 0
	 */
	public int deleteMember(String memId);
	
	
	/**
	 * MemberVO 객체에 담긴 자료를 이용하여 회원을 검색하는 메서드
	 * @param mv 검색할 자료가 들어있는 MemberVO 객체
	 * @return 검색된 결과를 담은 List
	 */
	public List<MemberVO> getSearchMember(MemberVO mv);
	
	/**
	 * 주어진 회원 ID에 해당하는 회원정보를 조회하는 메서드
	 * @param memId 검색할 회원 ID
	 * @return 해당 회원 ID 에 해당하는 회원 정보
	 */
	public MemberVO getMember(String memId);
}
