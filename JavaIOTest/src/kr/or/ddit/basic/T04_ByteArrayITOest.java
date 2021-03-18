package kr.or.ddit.basic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class T04_ByteArrayITOest {
	public static void main(String[] args) {
		byte[] inSrc = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		byte[] outSrc = null;
		
//		4개씩 읽으려고
		byte[] temp = new byte[4];// 자료를 읽을 때 사용할 배열
		
		ByteArrayInputStream bais = new ByteArrayInputStream(inSrc);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			//avaliable() ==> 읽어올 수 있는 byte 수를 반환
			while(bais.available() > 0) {
				/* 
					bais.read(temp);// temp배열 크기만큼 자료를 읽어와 temp 배열에 저장한다. 
					baos.write(temp);// temp배열의 내용을 출력한다.
					
					
					** 결과 ** => 문제 발생
					temp : [0, 1, 2, 3]
					temp : [4, 5, 6, 7]
					temp : [8, 9, 6, 7]
					
					inSrc => [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
					outSrc => [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 6, 7]
				*/
				
//				실제 읽어온 byte 수를 반환한다.(꼼꼼하게 따져서)
				int len = bais.read(temp); //4, 4, 2 개 읽어옴
				
//				배열의 내용 중에서 0번째부터 len개수만큼 출력한다. 이로인해 마지막의 6, 7은 읽어오지 않음.
				baos.write(temp, 0, len);
				
				System.out.println("temp : " + Arrays.toString(temp));
			}
			
			System.out.println();
			
			outSrc = baos.toByteArray();
			System.out.println("inSrc => " + Arrays.toString(inSrc));
			System.out.println("outSrc => " + Arrays.toString(outSrc));
			
			baos.close();
			bais.close();
		}catch (IOException e) {
		}
		
	}
}
