package kr.or.ddit.rmi.vo;

import java.io.Serializable;

/**
 * RMI 기술이 내부적으로 객체는 직렬화, 역직렬화가 일어나고 있음.
 * RMI에서 데이터 전달용으로 사용할 객체
 * 네트워크로 객체를 보낼 것이기 때문에 직렬화가 필요하다.
 * - 네트웤 작업 시 input, output을 사용하여 연속된 데이터를 처리하기 때문에 직렬화는 필수이다.
 * - 따라서 Serializable을 구현해야 한다.
 * -- Serializable: Marker Interface(메서드 하나도 없고 단지 표식만 하는 역할)
 * 
 * @author 유은지
 */
public class TestVO implements Serializable {
	
	private String testId;
	private int testNum;
	
	public String getTestId() {
		return testId;
	}
	public void setTestId(String testId) {
		this.testId = testId;
	}
	public int getTestNum() {
		return testNum;
	}
	public void setTestNum(int testNum) {
		this.testNum = testNum;
	}
	
	
}
