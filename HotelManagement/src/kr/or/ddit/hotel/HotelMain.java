package kr.or.ddit.hotel;

import java.util.List;

import kr.or.ddit.hotel.service.HotelServiceImpl;
import kr.or.ddit.hotel.service.IHotelService;
import kr.or.ddit.hotel.vo.HotelVO;
import kr.or.ddit.util.ScanUtil;

public class HotelMain {
	private IHotelService hotelService;
	
	/**
	 * 메인 생성자 생성
	 * 메인  객체 생성 시 hotelService 객체 초기화
	 * 다형성 개념 적용 
	 */
	public HotelMain() {
		hotelService = new HotelServiceImpl();
	}
	
	/**
	 * 메인 메서드에서 start객체 호출 
	 * @param args
	 */
	public static void main(String[] args) {
		new HotelMain().start();
	}

	/**
	 * 프로그램  시작하는 메서드 
	 */
	private void start() {
		int userchoice;
		while(true) {
			displayMenu();
			userchoice = ScanUtil.nextInt();
			switch(userchoice) {
			case 1: // 체크인
				checkIn();
				break;
			case 2 : //체크아웃 
				checkOut();
				break;
			case 3: //객실상태 
				displayAll();
				break;
			case 4: //프로그램 종료 
				System.out.println("호텔관리 시스템을 종료합니다.");
				System.exit(0);
			default: //잘못 입력한 경우 
				System.out.println("번호를 잘못입력했습니다.");
				System.out.println("다시 입력하세요.");
			}
		}
		
	}
	
	private void displayAll() {
		System.out.println("=========객실상태 메뉴로 진입했습니다=========");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("객실번호\t이름");
		
		List<HotelVO> hotelList = hotelService.getAllHotelList();
		
		for(HotelVO hv : hotelList) {
			System.out.println(hv.getRoomNum() + "\t" + hv.getGuestName());
		}
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("객실 정보 출력 완료..");
	}

	private void checkOut() {
		System.out.println("=========체크아웃 메뉴로 진입했습니다=========");
		boolean chk = false;
		int roomNum = 0;
		
		/**
		 * chk DB에 객실이 존재하는 경우 true, 체크아웃할 객실이 존재하지 않는 경우 false
		 */
		
//		객실 검사 
		do {
			System.out.println("체크아웃할 객실 번호를 입력하세요. ");
			System.out.print("입력 > ");
			roomNum = ScanUtil.nextInt();
			
			chk = hotelService.checkHotel(roomNum);
			
			if(chk == false) {
				System.out.println("객실 정보가 없습니다. 다시 입력하세요.");
			} else {
				System.out.println("객실 체크아웃 가능한 상태입니다. 체크아웃을 시작합니다.");
			}
		} while(chk == false);
		
//		객실 체크아웃 시작 
		int cnt = hotelService.deleteHotel(roomNum);
		
//		객실 체크아웃 유효성 검사
		if(cnt > 0) {
			System.out.println(roomNum + "체크아웃 작업 성공");
		}else {
			System.out.println(roomNum + "체크아웃 작업 실패");
		}
	}
	
	/**
	 * chk DB에 객실이 존재하는 경우 : true, 체크인한 객실이 존재하지 않는 경우 false
	 */
	private void checkIn() {
		System.out.println("=========체크인 메뉴로 진입했습니다=========");
		boolean chk = false;
		int roomNum = 0;
		
		do {
			System.out.println("어느방에 체크인 하시겠습니까?");
			System.out.print("입력 > ");
			roomNum = ScanUtil.nextInt();
			
			chk = hotelService.checkHotel(roomNum);
			
			if(chk == false) {
				System.out.println("객실 체크인 가능한 상태입니다. 체크인을 시작합니다.");
			} else {
				System.out.println("이미 체크인된 객실입니다. 다시 입력하세요.");
			}
		} while(chk == true);
		
//		객실 체크인 시작 
		System.out.println("누구를 체크인 하시겠습니까? ");
		String guestName = ScanUtil.nextLine();
		
		HotelVO hv = new HotelVO();
		hv.setRoomNum(roomNum);
		hv.setGuestName(guestName);
		
		int cnt = hotelService.insertHotel(hv);
		
		if(cnt > 0) {
			System.out.println(roomNum + "체크인 작업 성공");
		} else {
			System.out.println(roomNum + "체크인 작업 실패!!!");
		}
	}

	private void displayMenu() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("호텔문을 열었습니다.");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("어떤 업무를 하시겠습니까?");
		System.out.println("  1.체크인");
		System.out.println("  2.체크아웃");
		System.out.println("  3.객실상태");
		System.out.println("  4.업무종료");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.print("입력 > ");
	}
	
}
