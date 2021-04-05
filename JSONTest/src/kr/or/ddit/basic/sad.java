package kr.or.ddit.basic;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * 공공데이터 포털의 OPEN API
 * (LH 공고 가져오기)
 * @author jinny
 *
 */
public class sad {
	private String svcKey = "pRghHcHsz%2FmSx8O2jF28UvEEopoQ0aSZh%2F3fS%2FpkpRzBO3rzSUz%2FVMn2o47vJhSqep6DYnP5EyC2xIA8YrFVkw%3D%3D";  // LH공고조회서비스
	private String page = "1";  			// 페이지	
	private String pageSize = "20";			// 한 페이지 게시글 수
	private String startDate = "2021-03-01";	// 검색 시작일
	private String endDate = "2021-05-01";		// 검색 종료일
	private String searchTitle = "%EA%B3%B5%EA%B3%A0";	// 검색어(제목)
	private String typeCode = "06";	// 상위유형코드
	
	
	/**
	 * JSON
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private JSONArray getJSONObject() throws IOException, ParseException {
		URL url = new URL("http://apis.data.go.kr/B552555/lhNoticeInfo/getNoticeInfo"
				+ "?serviceKey=" + svcKey
				+ "&PG_SZ=" + pageSize
				+ "&SCH_ST_DT=" + startDate
				+ "&SCH_ED_DT=" + endDate
				+ "&BBS_TL=" + searchTitle
				+ "&UPP_AIS_TP_CD=" + typeCode
				+ "&PAGE=" + page);
		URLConnection urlConnection = url.openConnection();
		
		JSONParser parser = new JSONParser();

		Object obj = parser.parse(new InputStreamReader(urlConnection.getInputStream()));

		return (JSONArray)obj;
	}

	/**
	 * 시작
	 * @throws IOException
	 * @throws ParseException
	 */
	public void start() throws IOException, ParseException {

		// 데이터 반환 형태가 Array이기 때문에 데이터 타입을 JSONArray로 받음
		JSONArray jsonfile = getJSONObject();
		
		// 출력할 데이터가 속한 1번 인덱스 추출
		JSONObject result = (JSONObject) jsonfile.get(1);
		System.out.println("result : " + result);
		
		// 상태 정보를 뽑기 위한 "resHeader"의 리스트 추출
		JSONArray resHeaders = (JSONArray) result.get("resHeader");
		System.out.println("resHeaders : " + resHeaders);
		
		
		// resHeader에서 상태 정보가 속한 0번 인덱스 값 추출
		JSONObject resHeader = (JSONObject) resHeaders.get(0);
		
		// 0번 인덱스에서 상태 정보인 SS_CODE의 값 추출 (정상 : "Y")
		String code = (String) resHeader.get("SS_CODE");
		
		if(code.equals("Y")) { // 정상 상태
			
			// result에서 출력 정보가 속한 "dsList" 가져오기
			JSONArray dsList = (JSONArray) result.get("dsList");
			
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> it = dsList.iterator();
	
			while(it.hasNext()){
	
				JSONObject tempJson = it.next();
	
				System.out.println("게시글 번호 : " + tempJson.get("RNUM"));
				System.out.println("게시일 : " + tempJson.get("BBS_WOU_DTTM"));
				System.out.println("조회수 : " + tempJson.get("INQ_CNT"));
				System.out.println("제목 : " + tempJson.get("BBS_TL"));
				System.out.println("공고유형 : " + tempJson.get("AIS_TP_CD_NM"));
	
				System.out.println("--------------------------------------------------");
			}
		}
	}

	public static void main(String[] args) throws IOException, ParseException {
		new sad().start();
	}
}
