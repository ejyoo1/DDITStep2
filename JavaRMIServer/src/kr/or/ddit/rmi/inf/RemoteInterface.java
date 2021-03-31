package kr.or.ddit.rmi.inf;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.rmi.vo.FileInfoVO;
import kr.or.ddit.rmi.vo.TestVO;

/**
 * RMI는 인터페이스로 먼저 구현해야 함.
 * RMI용 인터페이스는 Remote를 상속해야 한다.
 * 로컬에 있지 않아도 멀리 있는 사람이 실행할 수 있도록 처리(원격)
 * @author 유은지
 * 직렬화와 같이 Remote에도 인터페이스 1개만 존재한다.
 */
public interface RemoteInterface extends Remote {
	//이 인터페이스는 선언된 모든 메서드에서 RemoteException을 Throws해야 한다.(던지도록 만들어줘야함.)
	//네트웤이 끊기거나, 중간에 다른 문제가 발생했을 떄 RemoteException으로 정의한다.
	//처음에 설계할 때부터 Exception처리를 할 수 있도록 한다.
	
	public int doRemotePrint(String str) throws RemoteException; //다른곳에 넘기자
	
	public void doPrintList(List<String> list) throws RemoteException;
	
	public void doPrintVO(TestVO vo) throws RemoteException;
	
	//파일 전송 위함(여러 파일 담을것이므로 배열로)
	public void setFiles(FileInfoVO[] fInfo) throws RemoteException;
}
