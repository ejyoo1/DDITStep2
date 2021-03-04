package Assignment0302;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

public class BaseBallSet {
	public static void main(String[] args) throws IOException {
		HashSet<Integer> intRnd = new HashSet<Integer>();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		while(intRnd.size() < 3) {
			int num = (int)(Math.random() * 10 + 1);
			intRnd.add(num);
		}
		
		boolean result = true;
		
		while(result) {
			List<Integer> intRndList = new ArrayList<Integer>(intRnd);
			Collections.shuffle(intRndList);

			int strike = 0;
			int ball = 0;
			int out = 0;
			
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("입력 > ");
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int num1 = Integer.parseInt(st.nextToken());
			int num2 = Integer.parseInt(st.nextToken());
			int num3 = Integer.parseInt(st.nextToken());
			
			
			
			if (intRndList.get(0) == num1) {
				strike++;
			} else if (intRndList.get(0) == num2 || intRndList.get(0) == num3) {
				ball++;
			} else{
				out++;
			}
			if (intRndList.get(1) == num2) {
				strike++;
			} else if (intRndList.get(1) == num1 || intRndList.get(1) == num3) {
				ball++;
			} else{
				out++;
			}
			if (intRndList.get(2) == num3) {
				strike++;
			} else if (intRndList.get(2) == num1 || intRndList.get(2) == num2) {
				ball++;
			} else{
				out++;
			}
			
			bw.write("스트라이크 : " + strike + " 볼 : " + ball + " 아웃 : " + out + "\n");
			System.out.println();
			
			if (strike == 3) {
				bw.write("성공!!!!!!!!!야구게임을 종료합니다." + "\n");
				System.out.println();
				break;
			} else if (strike == 2) {
				bw.write("오오!!! 조금만 더 시도해 보세요" + "\n");
				System.out.println();
			} else {
				bw.write("잘 생각해 보시오ㅋㅋ" + "\n");
				System.out.println();
			}
			System.out.println();
			
			bw.flush();
			
		}
		
		bw.close();
	}
}