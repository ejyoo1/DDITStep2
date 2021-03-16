package kr.or.ddit.basic;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import kr.or.ddit.member.vo.MemberVO;

public class MemberIbatisTest {
	public static void main(String[] args) {
//		iBatis를 이용하여 DB자료를 처리하는 작업순서
//		1. ibatis의 환경설정파일을 읽어와 실행시킨다.
		try {
//			1-1. xml 설정 문서 읽어오기
			Charset charset = Charset.forName("UTF-8");//설정 파일 인코딩
			Resources.setCharset(charset);
			Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
			
//			1-2. 위에서 읽어온 Reader객체를 이용하여 실제 작업을 진행할 객체 생성
//			SqlMapClient : idatis를 제공해주는 객체(설정정보를 통한 객체 데이터를 가져와야 함.)
			SqlMapClient smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			
			rd.close();//Reader 객체 닫기(자원반납!)//////////////////////////////대부분 여기까진 코드가 동일함.
			
//			2. 실행할 SQL문에 맞는 쿼리문을 호출해서 원하는 작업을 수행한다.

			
			

//			2-1. insert작업 연습
			System.out.println("insert 작업 시작...");
			
//			1) 저장할 데이터를 VO에 담는다.
			MemberVO mv = new MemberVO();
			mv.setMemId("e001");
			mv.setMemName("홍길동");
			mv.setMemTel("010-1111-1111");
			mv.setMemAddr("대구시 달서구");
			
//			2) SqlMapClient객체 변수를 이용하여 해당 쿼리문을 실행한다.
//			형식 ) smc.insert("namespace값.id값", 파라미터 객체)
//				반환값 : 성공하면 null이 반환된다.
//			return값이 null이므로 Object로 받아본다.
//			실행할 쿼리를 ibatis에게 알려준다.
//			SQL Exception 처리를 해야함(내부적으로 JDBC관련 코딩이 되어있는 것임.)
//			smc.insert("memberTest.insertMember", mv) => member.xml 참고할 것
//			member.xml에서의 namespace.id를 작성한다.
//			useStatementNamespaces="true"를 놓았기 때문에 "memberTest.insertMember"이부분을 id로 생각하지 않고 namespace.id로 인식하게 된다.
			Object obj = smc.insert("memberTest.insertMember", mv);
			if(obj == null) {
				System.out.println("insert작업 성공");
			}else {
				System.out.println("insert작업 실패");
			}
			System.out.println("==================================================");

			
//			2-2. update 연습
			System.out.println("update 작업 시작...");
			
			MemberVO mv2 = new MemberVO();
			mv2.setMemId("e001");
			mv2.setMemName("이순신");
			mv2.setMemTel("010-2222-2222");
			mv2.setMemAddr("부산시 해운대구");
			
//			update()메서드의 반환값은 성공한 레코드 수이다.
//			Exception in thread "main" com.ibatis.sqlmap.client.SqlMapException: There is no statement named memberTest.updateMember in this SqlMap. 발생 시
//			1) 설정파일에 등록을 안한경우
//			2) 설정파일에 만들어놨으나 쿼리가 잘못됐다.
//			3) 만들어놨지만 스펠링의 대소문자가 틀렸다.
			int cnt = smc.update("memberTest.updateMember", mv2);
			
			if(cnt > 0) {
				System.out.println("update작업 성공");
			}else {
				System.out.println("update작업 실패");
			}
			
			System.out.println("==================================================");
			
//			2-3. delete 연습
			System.out.println("delete 작업 시작...");
			
//			delete()메서드의 반환값 : 성공한 레코드 수
			
			cnt = smc.delete("memberTest.deleteMember", "e001");
			if(cnt > 0) {
				System.out.println("delete작업 성공");
			}else {
				System.out.println("delete작업 실패");
			}
			
			System.out.println("==================================================");
			

//			2-4. select 연습
//			1) 응답의 결과가 여러개일 경우
			System.out.println("select연습 시작(결과가 여러개일 경우)....");
//			최종 결과 받을 변수 선언
			List<MemberVO> memList = null;
			
//			응답결과가 여러개일 경우에는 queryForList메서드를 사용한다.
//			이 메서드는 여러개의 레코드를 VO에 담은 후 
//			이 VO데이터를 List에 추가해주는 작업을 자동으로 수행한다.
			memList = smc.queryForList("memberTest.getMemberAll");
			for(MemberVO mv3 : memList) {
				System.out.println("ID : " + mv3.getMemId());
				System.out.println("NAME : " + mv3.getMemName());
				System.out.println("TEL : " + mv3.getMemTel());
				System.out.println("ADDR : " + mv3.getMemAddr());
				System.out.println("==================================================");
			}
			
			System.out.println("출력 끝...");

			
//			2) 응답이 1개일 경우 (count, 집계함수 인 경우)
			System.out.println("select 연습 시작(결과가 1개인 경우)...");
			
//			응답 결과가 1개가 확실할 경우에는 queryForObject 메서드를 사용한다.
//			queryForObject의 리턴 타입이 Object이므로 적절한 타입으로 캐스팅이 필요함.
			MemberVO mv4 = (MemberVO) smc.queryForObject("memberTest.getMember", "ad001");
			System.out.println("ID : " + mv4.getMemId());
			System.out.println("NAME : " + mv4.getMemName());
			System.out.println("TEL : " + mv4.getMemTel());
			System.out.println("ADDR : " + mv4.getMemAddr());
			System.out.println("==================================================");
			System.out.println("출력 끝...");
			
			
		}catch(IOException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
