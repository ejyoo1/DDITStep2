package kr.or.ddit_basic_ejyoo;

import java.util.LinkedList;

public class StackQueueTestRemind {
	public static void main(String[] args) {
//		잦은 데이터 삭제가 있는 경우 LinkedList가 좋음
//		Stack => 후입선출(LIFO)의 자료구조
//		Queue => 선입선출(FIFO)의 자료구조
//		Stack과 Queue는 LinkedList를 이용하여 사용할 수 있다.
		
		LinkedList<String> stack = new LinkedList<>();
		
//		Stack의 명령
//		1) 자료 입력 : push(저장할 값)
//		2) 자료 출력 : pop() => 자료를 꺼내온 후 꺼내온 자료를 stack에서 삭제한다.
		stack.push("홍길동");
		stack.push("일지매");
		stack.push("변학도");
		stack.push("강감찬");
//		stack를 출력하면 배열 형태로 출력 가능
		System.out.println("현재 Stack값들 : " + stack);
		
//		스택에서 꺼내서 변수에 담을 수 있음.
		String data = stack.pop();
		System.out.println("꺼내온 자료 : " + data);
//		스택에서 꺼낸 후 바로 출력할 수 있음.
		System.out.println("꺼내온 자료 : " + stack.pop());
		
		stack.push("성춘향");
		System.out.println("현재 Stack 값들 : " + stack);
		System.out.println("꺼내온 자료 : " + stack.pop());
		
		System.out.println("====================================");
		System.out.println();
		
		
		LinkedList<String> queue = new LinkedList<>();
//		queue의 명령
//		1) 자료 입력 : offer(저장할 값)
//		2) 자료 출력 : poll() => 자료를 꺼내온 후 꺼내온 자료는 Queue에서 삭제한다.
		queue.offer("홍길동");
		queue.offer("일지매");
		queue.offer("변학도");
		queue.offer("강감찬");
		
//		queue를 출력하면 배열 형태로 출력 가능
		System.out.println("현재 queue의 값 : " + queue);
		
//		큐에서 꺼내서 변수에 담을 수 있음.
		String temp = queue.poll();
		System.out.println("꺼내온 자료 : " + temp);
		System.out.println("꺼내온 자료 : " + queue.poll());
		System.out.println("현재 queue의 값 : " + queue);
		
		if(queue.offer("성춘향"))
			System.out.println("신규 등록 자료 : 성춘향");
		System.out.println("현재 queue의 값 : " + queue);
		System.out.println("꺼내온 자료 : " + queue.poll());
		
	}
}
