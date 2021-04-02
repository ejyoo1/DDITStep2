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
 * (레시피 재료정보 가져오는 예제)
 * @author macween
 * URL 통해 요청 시 JSON 형식으로 데이터를 리턴해줌. ==> 그것을 파싱해서 출력
 */
public class JsonSimpleReadExam {
	private String svcKey = "Grid_20150827000000000227_1";  // 레시피 재료 정보 조회 서비스
	private String apiKey = "1df7e8571e8df3f8cbc9b87691ca7d3e4d04f03c593d477e52bf67b03f0b6e1c"; // 개인별 발급.
	private String startIdx = "1";  	// 레시피 재료 시작 순번
	private String endIdx = "5";		// 레시피 재료 종료 순번
	private String recipeId = "377";	// 래시피가 궁금한 음식 ID

	/**
	 * JSON
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	private JSONObject getJSONObject() throws IOException, ParseException {
		URL url = new URL("http://211.237.50.150:7080/openapi/"+ apiKey
				+ "/json/"+ svcKey + "/"+startIdx +"/" + endIdx
				+"?RECIPE_ID=" +  recipeId);//api URL ==> 이 정보가 레시피 정보를 요청하기 위해 사용할 URL임. 
		URLConnection urlConnection = url.openConnection();
		
		System.out.println(url.toString());//URL 확인(여기에 Json이 있음. 인간이 보기에 정렬안된 데이터들)
		JSONParser parser = new JSONParser();

		Object obj = parser.parse(new InputStreamReader(urlConnection.getInputStream()));//json 형식을 object로 반환

		return (JSONObject)obj;//캐스팅
	}

	/**
	 * 시작!!
	 * @throws IOException
	 * @throws ParseException
	 */
	public void start() throws IOException, ParseException {
//처음에는 totalCnt 재료가 몇인지 몰라서 몇개인지 모르므로, endIdx를 아무거나 주어서 한번 호출한 뒤 얻어온 데이터 totalCnt를 다시 세팅해서 재호출한다.

		JSONObject jsonfile = getJSONObject();//받아온 것 저장

		JSONObject rootObj = (JSONObject) jsonfile.get(svcKey);//서비스 키를 가져옴

		long totalCnt = (long)rootObj.get("totalCnt"); // 전체 레시피 재료 수 14개

		endIdx = totalCnt + ""; // 레시피 재료 마지막 순번 설정 (14개로 사용)
		//-----------------------------------------------------------------------
		// 구해온 전체 재료수를 이용하여 다시 요청함.(여기가 진짜 데이터 호출하는 부분)


		jsonfile = getJSONObject();

		rootObj = (JSONObject) jsonfile.get(svcKey);//키에 해당하는 값 들고옴(Grid_20150827000000000227_1 ㅇ;흥,; 전체데이터를 가져옴)

		JSONObject result = (JSONObject)rootObj.get("result");//"code":"INFO-000", "message":"정상 처리되었습니다."
		String code = (String)result.get("code");//INFO-000

		if(code.equals("INFO-000")) { // 정상 상태이면...(온전한 데이터인지 확인)
			//파싱 시작.
			JSONArray list = (JSONArray)rootObj.get("row");//row 의 값인 JSON배열 (재료 들어있음) ==> 14개

//			for(Object tempObj : list) {
//
//				JSONObject tempJson = (JSONObject) tempObj;
//
//				System.out.println("순번 : " + tempJson.get("ROW_NUM"));
//				System.out.println("레시피ID : " + tempJson.get("RECIPE_ID"));
//				System.out.println("재료명 : " + tempJson.get("IRDNT_NM"));
//				System.out.println("용량 : " + tempJson.get("IRDNT_CPCTY"));
//				System.out.println("재료구분 : " + tempJson.get("IRDNT_TY_NM"));
//
//				System.out.println("-------------------------");
//			}


			@SuppressWarnings("unchecked")
			Iterator<JSONObject> it = list.iterator();

			while(it.hasNext()){

				JSONObject tempJson = it.next();//재료 덩어리 한개

				System.out.println("순번 : " + tempJson.get("ROW_NUM"));
				System.out.println("레시피ID(코드) : " + tempJson.get("RECIPE_ID"));
				System.out.println("3번째컬럼 : " + tempJson.get("IRDNT_SN"));
				System.out.println("재료명 : " + tempJson.get("IRDNT_NM"));
				System.out.println("용량 : " + tempJson.get("IRDNT_CPCTY"));
				System.out.println("w재료코드 : " + tempJson.get("IRDNT_TY_CODE"));
				System.out.println("재료구분 : " + tempJson.get("IRDNT_TY_NM"));

				System.out.println("-------------------------");
			}
		}
	}

	public static void main(String[] args) throws IOException, ParseException {
		new JsonSimpleReadExam().start();
	}
}
