package kr.or.ddit.basic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *  기본 타입 데이터 입출력 보조스트림 예제
 * @author PC-19
 *
 */
public class T14_DataIOStreamTest {
	public static void main(String[] args) {
		try {
			FileOutputStream fos = new FileOutputStream("d:/D_Other/test.dat");
			
//			DataOutputStram은 출력용 데이터를 자료형에 맞게 출력해준다.
			DataOutputStream dos = new DataOutputStream(fos);
			
			dos.writeUTF("홍길동"); 	//문자데이터 출력(UTF-8)
			dos.writeInt(17);		//정수형으로 데이터 출력
			dos.writeFloat(3.14f); 	//실수형(Float)으로 출력
			dos.writeDouble(3.14);	//실수형(Doublse)으로 출력
			dos.writeBoolean(true); //논리형으로 출력
			System.out.println("출력완료...");
			
			dos.close();
			//==============================================
			
			//출력한 자료 읽어오기
			FileInputStream fis = new FileInputStream("d:/D_Other/test.dat");
			DataInputStream dis = new DataInputStream(fis);
			
			System.out.println("문자열 자료 : " + dis.readUTF());//4byte
			System.out.println("정수형 자료 : " + dis.readInt());//4byte
			//실수형(Float,Double) 로 출력됐던 것을 Double,Float로 출력하면 
			//스트림에 저장된 값이 Float(4byte)이기 때문에 이것을 Double(8byte)로 출력하면 데이터 손실이 있다.
			//Fifo로 생각해도 좋음
			//스트림에 담은 대로 읽어오다가 모두 읽으면 endofFileException 발생하면서 끝남.
			System.out.println("실수형(Float) 자료 : "//4byte
					+ dis.readFloat());
			System.out.println("실수형(Double) 자료 : "//4byte
					+ dis.readDouble());
			System.out.println("논리형 자료 : " + dis.readBoolean());//1byte
			
			dis.close();
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}
