package kr.or.ddit.basic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class T13_ImageRW {
	public static void main(String[] args) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		try {
			fis = new FileInputStream("d:/D_Other/Tulips.jpg");
			fos = new FileOutputStream("d:/D_Other/복사본_Tulips.jpg");
			
			byte[] buffer = new byte[1024];
			int readcount = 0;
			int runCount = 0;
			
			while((readcount=fis.read(buffer)) != -1) {
				fos.write(buffer, 0, readcount);
				runCount++;
			}
			
			System.out.println(runCount);
			fis.close();
			fos.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
