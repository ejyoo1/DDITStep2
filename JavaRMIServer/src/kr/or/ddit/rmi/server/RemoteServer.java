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
 * RMI용 서비스를 제공하는 객체는 RMI용 인터페이스를 구현하고
 * UnicastRemoteObject객체를 상속해서 작성한다.
 * @author 유은지
 * RemoteServer가 만든 객체를 원격으로 보낼것임.
 * 인터페이스 구현 + unicastRemoteObject 필요.
 * RMI 기술을 이용한 원격 호출
 */
public class RemoteServer extends UnicastRemoteObject implements RemoteInterface{//rmi용 클래스로 만듬(생성자, 인퍼테이스 구현 시 다른사람이 파일에 접근할 수 있는것임)

	//이것이 필요한 이유는 
	//이 객체를 생성자를 통해서 만드는데, RemoteException가 붙어있다.
	//객체를 만드는 순간에도 RemoteException 발생할 수 있으니 Throws를 해야함.
	//따라서 생성자가 필요한 것이다.
	protected RemoteServer() throws RemoteException {
		super();
	}
	
	public static void main(String[] args) {
		//원격객체 처리(맨 마지막에 코드작성)
		try {
			//구현한 RMI용 객체를 클라이언트에서 사용할 수 있도록 RMI서버에 등록한다.
			//1. RMI용 인터페이스를 구현한 객체 생성
			RemoteInterface inf = new RemoteServer();
			
			//2. 구현한 객체를 클라이언트에서 찾을 수 있도록 Registry객체를 생성해서 등록한다.
			
			//포트 번호를 지정하여 Registry객체 생성(기본포트:1099)
			Registry reg = LocateRegistry.createRegistry(8888);//포트번호를 파라미터로 담아 넘겨준다.(내가 원하는 포트 번호)
			
			//Registry 서버에서 제공하고자 하는 객체등록
			//형식) Registry객체변수.rebind("객체의 Alias", 객체);
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
