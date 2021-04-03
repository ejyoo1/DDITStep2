package kr.or.ddit.basic;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * 공공데이터 포털의 OPEN API 예제
 * (국립종자원 품종코드 가져오는 예제)
 * @author macween
 * URL 통해 요청 시 JSON 형식으로 데이터를 리턴해줌. ==> 그것을 파싱해서 출력
 * f2d1272f86ed502d67485fd2bf069a63b0e3bf4a56d6f8dad59b135f04ac2706
 */
public class JsonSimpleReadExam2 {
	private String apiKey 		= "f2d1272f86ed502d67485fd2bf069a63b0e3bf4a56d6f8dad59b135f04ac2706"; //발급받은 키
	private String type 		= "json"; //요청파일 타입 xml, json
	private String apiUrl 		= "Grid_20200114000000000604_1";  //openAPI 서비스 URL
	private String startIndex 	= "1";  	//요청 시작 위치
	private String endIndex 	= "1000";		//요청 종료 위치

	/**
	 * JSON
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	
	//http://211.237.50.150:7080/openapi/sample/json/Grid_20200114000000000604_1/1/5
	private JSONObject getJSONObject() throws IOException, ParseException {
		URL url = new URL("http://211.237.50.150:7080/openapi/"
				+ apiKey 	+ "/" 
				+ type 		+ "/"
				+ apiUrl 	+ "/"
				+ startIndex + "/" 
				+ endIndex);
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
				System.out.println("품종의 코드 : " + tempJson.get("VAR_CD"));
				System.out.println("품종의 한글명 : " + tempJson.get("VAR_NM_KOR"));
				System.out.println("품종의 영문명 : " + tempJson.get("VAR_NM_ENG"));

				System.out.println("-------------------------");
			}
		}
	}

	public static void main(String[] args) throws IOException, ParseException {
		new JsonSimpleReadExam2().start();
	}
}
