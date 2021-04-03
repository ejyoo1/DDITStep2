package kr.or.ddit.basic;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * 공공데이터 포털의 OPEN API 예제
 * (낙농체험 목장정보 가져오는 예제)
 * (생명산업인 낙농산업의 특성을 살려 학생들에게 농업, 노동, 생명, 음식에 대한 교육적 기능을 제공함으로써
 * 우유생산, 유통, 소비 이르는 전 과정에 대한 이해와 직접적 경험을 할 수 있는 
 * 학교 교육과 연계된 교육 프로그램을 실시할 수 있는 목장정보)
 * 
 * 키워드 : 체험목장, 농가체험, 젖소
 * @author macween
 * URL 통해 요청 시 JSON 형식으로 데이터를 리턴해줌. ==> 그것을 파싱해서 출력
 * f2d1272f86ed502d67485fd2bf069a63b0e3bf4a56d6f8dad59b135f04ac2706
 */
public class JsonSimpleReadExam4 {
	private String apiKey 		= "f2d1272f86ed502d67485fd2bf069a63b0e3bf4a56d6f8dad59b135f04ac2706"; //발급받은 키
	private String type 		= "json"; //요청파일 타입 xml, json
	private String apiUrl 		= "Grid_20150407000000000218_1";  //openAPI 서비스 URL
	private String startIndex 	= "1";  	//요청 시작 위치
	private String endIndex 	= "1000";		//요청 종료 위치
	private String area			= "전라";		// 지역(필수)
	private String farmNm		= "다래목장";	// 목장명(선택)

	/**
	 * JSON
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	
	//http://211.237.50.150:7080/openapi/sample/xml/Grid_20150407000000000218_1/1/5?AREA="충청"&FARM_NM="다래목장"
	private JSONObject getJSONObject() throws IOException, ParseException {
		URL url = new URL("http://211.237.50.150:7080/openapi/"
				+ apiKey 		+ "/" 
				+ type 			+ "/"
				+ apiUrl 		+ "/"
				+ startIndex 	+ "/" 
				+ endIndex		+ "?"
				+ "AREA="		+ encoder(area) + "&"
				+ "FARM_NM="	+ encoder(farmNm));
		System.out.println("세팅한 URL 확인 : " + url.toString());//세팅된 URL 확인
		URLConnection urlConnection = url.openConnection();//URL 연결 후 결과를 가져옴
		
		JSONParser parser = new JSONParser();//JSON 객체 생성

		Object obj = parser.parse(new InputStreamReader(urlConnection.getInputStream()));//결과 읽어와서 Object 에 저장
		/*
		 Connection에서 Inputstream 읽어옴 -> InputStreamReader(바이트기반을 문자로 읽어올 수 있는 스트림)
		 -> JSONParser로 parse한 후 Object에 저장
		 */
		

		return (JSONObject)obj;//캐스팅 리턴
	}

	/**
	 * 시작!!
	 * @throws IOException
	 * @throws ParseException
	 */
	public void start() throws IOException, ParseException { //이 로직은 갖고totalCnt가
		//갖고오고자 하는 JSON 데이터의 전체 개수 구하고 자료 읽어오기
		JSONObject jsonfile = getJSONObject();//JSON 데이터 자료 가져옴
		
		JSONObject rootObj = (JSONObject) jsonfile.get(apiUrl);//JSON 객체에 저장된 apiURL 가져옴 ==> Grid_20200114000000000604_1
		System.out.println("JSON DATA Text : " + rootObj);
		long totalCnt = (long)rootObj.get("totalCnt"); //전체 데이터 수 가져오기 (JSON 에서 var 변수를 사용하므로 int보단 long으로 선언)
		System.out.println("전체 데이터 수 : " + totalCnt); //사이트에서 요청 데이터를 1000개로 제한을 뒀으므로 제한된 범위에서 출력할 것.
		/*
		// ★ 데이터 endIndex 재 세팅 하는 방법(아래 코드 사용)★
		endIndex = 10 + "";
		
		jsonfile = getJSONObject();// 구해온 전체 재료수 사용하여 endIndex 세팅한 후 다시 가져옴

		rootObj = (JSONObject) jsonfile.get(apiUrl);//JSON 객체에 저장된 apiURL 가져옴 ==> Grid_20200114000000000604_1
		
		System.out.println(rootObj);//확인용 JSON 파일 출력
		*/
		
		JSONObject result = (JSONObject)rootObj.get("result");//"code":"INFO-000", "message":"정상 처리되었습니다."
		
		String code = (String)result.get("code");//INFO-000

		if(code.equals("INFO-000")) { // 정상 상태일 때.
			//파싱 시작.
			JSONArray list = (JSONArray)rootObj.get("row");//키가 row 인 것을 들고옴 ==> JSON 데이터 배열 가져옴

			/*
			//향상된 for문 사용 가능
			for(Object tempObj : list) {

				JSONObject tempJson = (JSONObject) tempObj;

				System.ouㅍt.println("순번 : " + tempJson.get("ROW_NUM"));
				System.out.println("레시피ID : " + tempJson.get("RECIPE_ID"));
				System.out.println("재료명 : " + tempJson.get("IRDNT_NM"));
				System.out.println("용량 : " + tempJson.get("IRDNT_CPCTY"));
				System.out.println("재료구분 : " + tempJson.get("IRDNT_TY_NM"));

				System.out.println("-------------------------");
			}
			 */

			@SuppressWarnings("unchecked") 
			Iterator<JSONObject> it = list.iterator();//Map 형태이므로 순서가 없어 Iterator 사용

			while(it.hasNext()){
				JSONObject tempJson = it.next();//row안의 한 덩어리 읽어옴 {"K":"V","K":"V"...}

				System.out.println("출력순서 : " + tempJson.get("ROW_NUM"));
				System.out.println("순번 : " + tempJson.get("SN"));
				System.out.println("지역 : " + tempJson.get("AREA"));
				System.out.println("목장명 : " + tempJson.get("FARM_NM"));
				System.out.println("대표명 : " + tempJson.get("RPRSNTV"));
				System.out.println("목장 설립일 : " + tempJson.get("FOND_DE"));
				System.out.println("목장부지(㎡) : " + tempJson.get("FRAM_AR"));
				System.out.println("사육 두수 : " + tempJson.get("BRD_LVSTCK_CO"));
				System.out.println("1일 우유생산량(Kg) : " + tempJson.get("PRDCTN_QY"));
				System.out.println("목장 소개 : " + tempJson.get("FARM_INTRCN"));
				System.out.println("주소 : " + tempJson.get("ADDR"));
				System.out.println("전화번호 : " + tempJson.get("TLPHON_NO"));
				System.out.println("홈페이지 : " + tempJson.get("HMPG"));
				System.out.println("도로명주소 : " + tempJson.get("RDNMADR"));
				System.out.println("신_우편번호 : " + tempJson.get("NW_ZIP"));
				System.out.println("위도 : " + tempJson.get("LA"));
				System.out.println("경도 : " + tempJson.get("LO"));

				System.out.println("-------------------------");
			}
		}
	}

	public static void main(String[] args) throws IOException, ParseException {
		new JsonSimpleReadExam4().start();
	}

	private String encoder(String userInput) {	
		String encodedString = "";
		try {
			encodedString = URLEncoder.encode(userInput, "utf-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("인코딩 실패");
			e.printStackTrace();
		}
		return encodedString;
	}
	
	private String decoder(String userInput) {
		String decodedString = "";
		try {
			decodedString = URLDecoder.decode(userInput, "utf-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("디코딩 실패");
			e.printStackTrace();
		}
		return decodedString;
	}
	
}
