package kr.or.ddit.rmi.inf;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import kr.or.ddit.rmi.vo.FileInfoVO;
import kr.or.ddit.rmi.vo.TestVO;

/**
 * 서버와 클라이언트가 서로 가지고 있는 메서드를 공유할 목적으로 원격에서 호출할 수 있도록 만든 인터페이스
 * (로컬에 있지 않아도 멀리있는 사람이 실행할 수 있도록 한다.)
 * 클라이언트 입장에서는 이 인터페이스만 알고있으면 원격에 있는 객체를 가지고 오고싶을 때 인터페이스 방식대로 호출만 하면 됨
 * 
 * RemoteInterfase 작성 규칙
 * - Remote 인터페이스를 상속받아야 한다.
 * -- 이 인터페이스에 있는 메소드로는 로컬이 아닌 원격에서 사용할 수 있어야 한다는 것을 명시하기 위함이다.
 * -- Remote는 마커 인터페이스 이다.(직렬화 인터페이스와 동일)
 * - 무조건 public 이어야 한다.
 * -- 원격 접속이 가능하여야 하기 때문이다.
 * - 메소드는 무조건 RemoteException 처리해야 한다. (throws RemoteException)
 * -- 원격 처리를 하다가 인터넷상에서 예외가 발생할 수 있기 때문이다.(네크웤 끊김 등)
 * - 인터페이스 기반 코딩은 유지 보수가 좋다.
 * @author 유은지
 */
public interface RemoteInterface extends Remote {
	
	public int doRemotePrint(String str) throws RemoteException; //다른곳에 넘기자
	
	public void doPrintList(List<String> list) throws RemoteException;
	
	public void doPrintVO(TestVO vo) throws RemoteException;
	
	public void setFiles(FileInfoVO[] fInfo) throws RemoteException;//파일 전송 위함(여러 파일 담을것이므로 배열로)
}
