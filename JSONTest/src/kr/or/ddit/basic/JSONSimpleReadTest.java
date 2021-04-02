package kr.or.ddit.basic;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONSimpleReadTest {
	
	public static void main(String[] args) throws IOException, ParseException {
		FileReader fr = new FileReader("d:/D_Other/myJsonFile.txt");//문자 기반 스트림
		
		JSONParser parser = new JSONParser();
		
		//parse로 FileReader를 넣어 JSONObject 형태로 변환
		Object obj = parser.parse(fr);//parse에 FileReader 추가
		JSONObject jsonFile = (JSONObject) obj;
		
		String name = (String)jsonFile.get("name");
		String job = (String)jsonFile.get("job");
		long age = (long) jsonFile.get("age");//Java에서는 long 으로 처리해야 함 int로 처리 시 오류남. (javascript는 길이 개념이 없어서 무조건 long)
		String addr = (String) jsonFile.get("addr");
		
		System.out.println("name : " + name);
		System.out.println("job : " + job);
		System.out.println("age : " + age);
		System.out.println("addr : " + addr);
		
		//Jsonlist 추출 방법
		JSONArray list = (JSONArray) jsonFile.get("singerList");
		
		Iterator<JSONObject> it = list.iterator();
		
		JSONObject singer;
		while(it.hasNext()) {
			singer = it.next();
			System.out.printf("이름 : %s, \t성별 : %s, \t나이 : %d\n"
					,singer.get("name")
					,singer.get("gender")
					,singer.get("age"));
		}
	}
}
