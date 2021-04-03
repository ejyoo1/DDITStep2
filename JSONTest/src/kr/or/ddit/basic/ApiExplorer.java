package kr.or.ddit.basic;

/* Java 샘플 코드 */


import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class ApiExplorer {
	//http://api.data.go.kr/openapi/tn_pubr_public_frhl_exprn_vilage_api?
	//serviceKey=BpcJg%2Fn8YOcDavHhvF4u1hffBqdWIxXN7qgKC4QwKYbq%2BlGK9WkiORULVOKAXom9GnHQ5eQAGtbgquZ73cPsjQ%3D%3D
	//&pageNo=1
	//&numOfRows=100
	//&type=json
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://api.data.go.kr/openapi/tn_pubr_public_frhl_exprn_vilage_api"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=BpcJg%2Fn8YOcDavHhvF4u1hffBqdWIxXN7qgKC4QwKYbq%2BlGK9WkiORULVOKAXom9GnHQ5eQAGtbgquZ73cPsjQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*XML/JSON 여부*/
//        urlBuilder.append("&" + URLEncoder.encode("exprnVilageNm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*체험마을명*/
//        urlBuilder.append("&" + URLEncoder.encode("ctprvnNm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*시도명*/
//        urlBuilder.append("&" + URLEncoder.encode("signguNm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*시군구명*/
//        urlBuilder.append("&" + URLEncoder.encode("exprnSe","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*체험프로그램구분*/
//        urlBuilder.append("&" + URLEncoder.encode("exprnCn","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*체험프로그램명*/
//        urlBuilder.append("&" + URLEncoder.encode("holdFclty","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*보유시설정보*/
//        urlBuilder.append("&" + URLEncoder.encode("exprnAr","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*체험휴양마을면적*/
//        urlBuilder.append("&" + URLEncoder.encode("exprnPicUrl","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*체험휴양마을사진*/
//        urlBuilder.append("&" + URLEncoder.encode("rdnmadr","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*소재지도로명주소*/
//        urlBuilder.append("&" + URLEncoder.encode("rprsntvNm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*대표자성명*/
//        urlBuilder.append("&" + URLEncoder.encode("phoneNumber","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*대표전화번호*/
//        urlBuilder.append("&" + URLEncoder.encode("appnDate","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*지정일자*/
//        urlBuilder.append("&" + URLEncoder.encode("homepageUrl","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*홈페이지주소*/
//        urlBuilder.append("&" + URLEncoder.encode("institutionNm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*관리기관명*/
//        urlBuilder.append("&" + URLEncoder.encode("latitude","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*위도*/
//        urlBuilder.append("&" + URLEncoder.encode("longitude","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*경도*/
//        urlBuilder.append("&" + URLEncoder.encode("referenceDate","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*데이터기준일자*/
//        urlBuilder.append("&" + URLEncoder.encode("instt_code","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*제공기관코드*/
//        urlBuilder.append("&" + URLEncoder.encode("instt_nm","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*제공기관기관명*/
        URL url = new URL(urlBuilder.toString());
        System.out.println("url : " + url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }
}
