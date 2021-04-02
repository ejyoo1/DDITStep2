package kr.or.ddit.basic;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * 이전에는 xml 파일을 많이 사용했다. ==> 치명적인 단점 : 뭘 의미하는 설정인지 태그로 감싸주어야 한다.<test>양식</test> ==> 바이트 단위로 데이터를 쏴서 용량이 커지게 됨 ==> 네트웍 환경을 많이 탐. ==> 속도 저하.
 * 따라서 이러한 xml의 단점으로 인해 그것을 극복하기 위해서 JSON을 사용한다.
 * JSON 파싱 기능을 자바에서 제공하지 않아서 파싱해주는 라이브러리 중 하나인 simplejson.jar을 사용한다.
 * @author 유은지
 * JSON : JavaScript Object Notation
 * - JSON에서 value값으로 가능한 데이터 타입
 * 1. string
 * 2. number
 * 3. Object(JSON object)
 * 4. array
 * 5. boolean
 * 6. null
 */
public class JSONSimpleWriterTest {
	public static void main(String[] args) throws IOException {
		//JSON 데이터 설정
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("name", "홍길동");
		jsonObj.put("job", "학생");
		jsonObj.put("age", 30);
		jsonObj.put("addr", "대전시 중구 대흥동 대덕인재개발원");
		
		//JSONArray 데이터 설정
		JSONArray singerList = new JSONArray();
		
		JSONObject singer = new JSONObject();
		singer.put("name", "싸이");
		singer.put("age", 40);
		singer.put("gender", "남자");
		singerList.add(singer);
		
		singer = new JSONObject();
		singer.put("name", "비욘세");
		singer.put("age", 44);
		singer.put("gender", "여자");
		singerList.add(singer);
		
		singer = new JSONObject();
		singer.put("name", "BTS");
		singer.put("age", 30);
		singer.put("gender", "남자");
		singerList.add(singer);
		
		jsonObj.put("singerList",  singerList);//JSON Object 추가
		
		FileWriter fw = 
				new FileWriter("d:/D_Other/myJsonFile.txt"); //문자기반스트림인 FileWriter 사용 ==> 해당 경로에 파일을 생성
		fw.write(jsonObj.toString());//JSON Object를 toString으로 변환하여 파일에 작성
		fw.flush();//혹시 남은 것이 있을 수 있으니 남은것까지 탈탈 털어 넣음
		fw.close();//파일 쓰기 종료
		
		System.out.println("JSON객체 내용 출력 : " + jsonObj);//어떤 데이터가 들어갔는지 JSONObj 출력(순서가 다르게 나올 수 있음)
	}
}