package kr.or.ddit.rmi.server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import kr.or.ddit.rmi.inf.RemoteInterface;
import kr.or.ddit.rmi.vo.FileInfoVO;
import kr.or.ddit.rmi.vo.TestVO;

/**
 * RMI(Remote Method Invocation)
 * - 로컬 컴퓨터에서 원격 컴퓨터의 메서드를 호출하는 기술
 * - 내부적으로는 TCP 방식의 소켓 프로그래밍이 돌고있는 것
 * 
 * 장점
 * - 구현하기 쉽다
 * - 신뢰성이 보장된다.
 * - JAVA 플랫폼을 사용한다.
 * 
 * Skeleton(서버)
 * - 소켓 연결을 통해 클라이언트 보조 객체(stub)에 보낸 요청을 받아서 실제 서비스 객체에 있는 메서드를 호출
 * - 서비스 객체로부터 리턴값을 받아서 포장해서 다시 클라이언트 보조 객체(Stub)로 보낸다.
 * 
 * 방식
 * - 클라이언트가 서버의 registry에 등록된 것을 lookup()으로 찾아낼 수 있다.
 * - 찾아낸 정보를 가지고 클라이언트가 원격으로 호출할 수 있음(멀리있는 시스템에서 실행된 결과를 받아낼 수 있음)
 * 
 * RemoteServer 작성규칙
 * - 무조건 public 이어야 한다.
 * - RMI용 인터페이스를 구현해야 한다.
 * -- RMI용 서비스를 제공하는 객체이기 때문
 * -- 구현하는 이유 : 클라이언트가 RMI용 인터페이스를 갖고있기 때문
 * - UnicastRemoteObject 클래스 상속받아야 한다.
 * -- 해당 통신의 하부 구조를 일부 구현해주고 이 클래스가 객체의 직렬화를 만들어주기 때문
 * - RemoteException 처리를 위해 디폴트 생성자 정의
 * -- 상속받은 UnicastRemoteObject 생성자에서 해당 예외가 발생하기 때문
 * 
 * ==> RMI기술을 사용하여 RemoteServer가 만든 객체를 원격으로 보낸다. 
 * @author 유은지
 */
public class RemoteServer extends UnicastRemoteObject implements RemoteInterface{//rmi용 클래스로 만듬(생성자, 인퍼테이스 구현 시 다른사람이 파일에 접근할 수 있는것임)

	//RemoteServer를 생성자를 통해 만들 때, RemoteException이 발생할 수 있으므로 Throws를 한다.
	protected RemoteServer() throws RemoteException {//UnicastRemoteObject를 상속받아서 RemoteException 처리를 해야함.
		super();
	}
	
	public static void main(String[] args) {
		try {
			//1. RMI용 인터페이스를 구현한 원격 객체 생성
			RemoteInterface inf = new RemoteServer();
			
			//2. 구현한 객체를 클라이언트에서 찾을 수 있도록 Registry객체를 생성해서 등록한다.
			//포트 번호를 지정하여 Registry객체 생성(기본포트값:1099) ==> 내가 원하는 포트번호를 담을 수 있음.
			Registry reg = LocateRegistry.createRegistry(8888);
			
			//3. Registry 서버에서 제공하고자 하는 객체등록
			//형식) Registry객체변수.rebind("객체의 Alias", 객체);
			//bind도 쓸 수 있으나 bind는 이미 bind되어 있으면 잘 안될 때가 있어서 확실한 rebind를 사용한다.
			//아무것도 bind되지 않은 상태에서도 rebind 사용 가능
			//rebind : 이전 객체가 있으면 덮어쓰기, 그렇지 않으면 새로 생성하는 특징
			reg.rebind("server", inf);//원격으로 등록할 객체를 담아 "server"라는 이름으로 등록함.
			
			System.out.println("서버가 준비되었습니다.");
		}catch(RemoteException ex) {
			ex.printStackTrace();
		}
	}

	//사용자가 원격객체를 이용해서 호출하면(멀리서) 파라미퍼로 스트링을 던저쥰다. 그러면 사이즈와 클라이언트 메시지를 가져와서 찍고 상대방에게 길이를 리턴해준다.
	@Override
	public int doRemotePrint(String str) throws RemoteException {
		int length = str.length();
		System.out.println("클라이언트에서 보내온 메시지 : " + str);
		System.out.println("출력 끝.");
		
		return length;
	}
	
	//사용자가 원격으로 호출하면서 리스트로 넘겨줄 수 있음.
	@Override
	public void doPrintList(List<String> list) throws RemoteException {
		//잘 전달이 됐는지 찍어보자
		System.out.println("클라이언트에서 보낸 List값들...");
		for(int i = 0 ; i < list.size(); i++) {
			System.out.println((i+1)+"번째 : " + list.get(i));
		}
		System.out.println("List 출력 끝...");     
	}

	//vo값을 넘겨줄 수 있음.
	@Override
	public void doPrintVO(TestVO vo) throws RemoteException {
		System.out.println("클라이언트에서 보내온 TestVO 객체 값 출력");
		System.out.println("testId : " + vo.getTestId());
		System.out.println("testNum : " + vo.getTestNum());
		System.out.println("testVO 객체 출력 끝...");
	}

	/**
	 * 원격객체를 사용하는 사람이 FileInfoVO를 넣어줌.
	 * 파라미터 데이터 이용하여 파일 저장하는 기능을 구현.
	 * @throws IOException 
	 * 
	 */
	@Override
	public void setFiles(FileInfoVO[] fInfo) throws RemoteException {
		FileOutputStream fos = null;
		
		String dir = "d:/C_Lib/"; //파일이 저장될 폴더 지정
		System.out.println("파일 저장 시작...");
		
		for(FileInfoVO fvo : fInfo) {//배열에서 꺼내서 
			try {
				fos = new FileOutputStream(dir + fvo.getFileName());//이것을 이용해서
				//클라이언트에서 전달한 파일데이터(byte[])를 서버측에 저장한다.
				fos.write(fvo.getFileData());//write(바이트이므로 가공없이 바로)
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("파일 저장 완료...");
	}		
}
