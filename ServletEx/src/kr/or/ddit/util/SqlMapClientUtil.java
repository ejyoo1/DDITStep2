package kr.or.ddit.util;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * SqlMapClient객체를 제공하는 클래스
 * @author 유은지
 */
public class SqlMapClientUtil {
//	싱글톤 패턴으로 생성
	private static SqlMapClient smc; //SqlMapClient 객체변수 선언
	
	private SqlMapClientUtil() {
		
	}
	
	public static SqlMapClient getInstance() {
		if(smc == null) {
			try {
//				1-1. xml 설정 문서 읽어오기
				Charset charset = Charset.forName("UTF-8");//설정 파일 인코딩
				Resources.setCharset(charset);
				Reader rd = Resources.getResourceAsReader("SqlMapConfig.xml");
				
//				1-2. 위에서 읽어온 Reader객체를 이용하여 실제 작업을 진행할 객체 생성
//				SqlMapClient : idatis를 제공해주는 객체(설정정보를 통한 객체 데이터를 가져와야 함.)
				smc = SqlMapClientBuilder.buildSqlMapClient(rd);
				
				rd.close();//Reader 객체 닫기(자원반납!)//////////////////////////////대부분 여기까진 코드가 동일함.
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return smc;
	}
}
