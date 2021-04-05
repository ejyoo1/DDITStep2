package kr.or.ddit.basic;

import java.io.IOException;

/* Java 샘플 코드 */


import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ApiExplorer {
	private static String dataUrl 		= "http://api.data.go.kr/openapi/tn_pubr_public_frhl_exprn_vilage_api";
	private static String serviceKey   	= "BpcJg%2Fn8YOcDavHhvF4u1hffBqdWIxXN7qgKC4QwKYbq%2BlGK9WkiORULVOKAXom9GnHQ5eQAGtbgquZ73cPsjQ%3D%3D";
	private static String pageNo 		= "1";
	private static String numOfRows		= "100";
	private static String type			= "json";
	
	//http://api.data.go.kr/openapi/tn_pubr_public_frhl_exprn_vilage_api
	//?serviceKey=BpcJg%2Fn8YOcDavHhvF4u1hffBqdWIxXN7qgKC4QwKYbq%2BlGK9WkiORULVOKAXom9GnHQ5eQAGtbgquZ73cPsjQ%3D%3D
	//&pageNo=0
	//&numOfRows=100
	//&type=json

	public static void main(String[] args) throws IOException, ParseException {
        
        
        URL url = new URL(dataUrl 
        		+ "?serviceKey=" + serviceKey
        		+ "&pageNo=" + pageNo
        		+ "&numOfRows=" + numOfRows
        		+ "&type=" + type);
        System.out.println("세팅한 URL 확인 : " + url.toString());//세팅한 URL확인 
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());//출력코드 가져옴 
        
        
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {//출력코드가 정상이면 
        	JSONParser parser = new JSONParser(); // JSON 객체 생성 
        	Object obj = parser.parse(new InputStreamReader(conn.getInputStream()));
        	
        	JSONObject jsonfile = (JSONObject)obj;
        	System.out.println("JSON FILE : " + jsonfile);
        	
        	JSONObject rootObj1 = (JSONObject)jsonfile.get("response");
        	System.out.println("JSON DATA Text1 : " + rootObj1);
        	
        	//body 가져오기 ==> json 객체라 또 다시 가져옴
        	JSONObject jsonfile2 = (JSONObject)rootObj1.get("body");
        	System.out.println("JSON DATA Text2 : " + jsonfile2);
        	
        	//body 가져오고 난 다음 전체 데이터수 출력 
        	System.out.println("전체 데이터 수 : " + jsonfile2.get("totalCount"));
//      String totalCnt = (String)jsonfile2.get("totalCount");//long으로 캐스팅 불가능..
        	
        	//items 가져오기 ==> items 가 JSON 배열임.
        	JSONArray list = (JSONArray)jsonfile2.get("items");
        	System.out.println("JSON DATA Text3 : " + list);
        	
        	Iterator<JSONObject> it = list.iterator();
        	
        	while(it.hasNext()) {
        		JSONObject tempJson = it.next();
        		
        		System.out.println("체험마을명(exprnVilageNm) : " 		+ tempJson.get("exprnVilageNm"));
				System.out.println("시도명(ctprvnNm) : " 				+ tempJson.get("ctprvnNm"));
				System.out.println("시군구명(signguNm) : " 			+ tempJson.get("signguNm"));
				System.out.println("체험프로그램구분(exprnSe) : " 		+ tempJson.get("exprnSe"));
				System.out.println("체험프로그램명(exprnCn) : " 		+ tempJson.get("exprnCn"));
				System.out.println("보유시설정보(holdFclty) : " 		+ tempJson.get("holdFclty"));
				System.out.println("체험휴양마을면적(exprnAr) : " 		+ tempJson.get("exprnAr"));
				System.out.println("체험휴양마을사진(exprnPicUrl) : " 	+ tempJson.get("exprnPicUrl"));
				System.out.println("소재지도로명주소(rdnmadr) : " 		+ tempJson.get("rdnmadr"));
				System.out.println("대표자성명(rprsntvNm) : " 			+ tempJson.get("rprsntvNm"));
				System.out.println("대표전화번호(phoneNumber) : " 		+ tempJson.get("phoneNumber"));
				System.out.println("지정일자(appnDate) : " 			+ tempJson.get("appnDate"));
				System.out.println("홈페이지주소(homepageUrl) : " 		+ tempJson.get("homepageUrl"));
				System.out.println("관리기관명(institutionNm) : " 		+ tempJson.get("institutionNm"));
				System.out.println("위도(latitude) : " 				+ tempJson.get("latitude"));
				System.out.println("경도(longitude) : " 				+ tempJson.get("longitude"));
				System.out.println("데이터기준일자(referenceDate) : " 	+ tempJson.get("referenceDate"));
				System.out.println("제공기관코드(instt_code) : " 		+ tempJson.get("insttCode"));//_있는것은 첫글자 대문자
				System.out.println("제공기관기관명(instt_nm) : " 		+ tempJson.get("insttNm"));//_있는것은 첫글자 대문자

				System.out.println("-------------------------");
        	}
        } else {
        	//실패
        	System.out.println("실패");
        }
        conn.disconnect();
    }
}
