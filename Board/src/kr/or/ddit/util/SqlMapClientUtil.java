package kr.or.ddit.util;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * SqlMapClient객체를 제공하는 유틸 클래스
 * @author 유은지
 * SqlMapClient? ibatis를 제공해주는 객체 (설정 정보를 통한 객체 데이터를 가져와야 함)
 */
public class SqlMapClientUtil {
	private static SqlMapClient smc; // SqlMapClient 객체변수 선언
	private SqlMapClientUtil() { }
	
	public static SqlMapClient getInstance() { // 싱글톤 패턴
		if(smc == null) {
			try {
//				1-1. xml 설정 문서 읽어오기
				Charset charset = Charset.forName("UTF-8"); // 설정 파일 인코딩
				Resources.setCharset(charset);
				Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml"); // log4j xml 파일 이름(경로)
				
//				1-2. 위에서 읽어온 Reader객체를 이용하여 실제 작업을 진행할 객체 생성
				smc = SqlMapClientBuilder.buildSqlMapClient(rd);
				
				rd.close(); // Reader 객체 자원 반납 필수 
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		return smc;
	}
}
