package kr.or.ddit.basic;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

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
			SqlMapClient smc = SqlMapClientBuilder.buildSqlMapClient(rd);
			
			rd.close();//Reader 객체 닫기
			
//			2. 실행할 SQL문에 맞는 쿼리문을 호출해서 원하는 작업을 수행한다.

//			2-1. insert작업 연습
			System.out.println("insert 작업 시작...");
			
//			1) 저장할 데이터를 VO에 담는다.
			
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}
