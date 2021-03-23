package kr.or.ddit.basic;

public class T04_ThreadTest {
/**
   1~20억까지의 합계를 구하는데 걸린 시간 체크하기
   전체 합계를 구하는 작업을 단독으로 했을 때(1개의 쓰레드를 사용했을 때)와
   여러 스레드로 분할해서 작업할 때의 시간을 확인 해 보자.  
   
   코어가 많으면 많을 수록 작업 효율이 좋다
   컨텍스트 스위칭 작업은 CPU에게 안좋은 작업이다.(빡센작업 - 이것을 최소화 하는것이 좋다.)
  무조건 쓰레드를 많이 만든다고 좋은것은 아니다. 쓰레드 작업 후 어딘가에 저장을 하고 다른 쓰레드 작업한 뒤
  작업이 남아있는 쓰레드를 불러와서 작업을하는데 이때 용어가 컨텍스트 스위칭이라고 한다.
  이 컨텍스트 스위칭이 많이 일어나면 싱글 쓰레드 작업보다 효율이 안좋을 수 있다.
  
 멀티 쓰레드 프로그램 코딩하다 보면 디버깅하기가 어렵다. (어디에 영향이 되서 에러가 나는지 알 수 없다.)
*/
   public static void main(String[] args) {
      // 단독으로 처리 할때...
      SumThread sm = new SumThread(1L, 2000000000L);
      
      long startTime = System.currentTimeMillis();
      
      //이것이 되어야 콜스택에 적재되면서 쓰레드가 실행 됨.
      sm.start();
      
      try {
         sm.join();
      }catch(InterruptedException ex) {
         ex.printStackTrace();
      }
      
      long endTime = System.currentTimeMillis();
      
      System.out.println("단독으로 처리했을 때의 처리 시간 : " 
                  + (endTime - startTime));
      System.out.println("\n\n");
      
      // 여러 쓰레드가 협력해서 처리했을 때
      SumThread[] sumThs = new SumThread[] {
         new SumThread(         1L,  500000000L),   
         new SumThread( 500000001L, 1000000000L),   
         new SumThread(1000000001L, 1500000000L),   
         new SumThread(1500000001L, 2000000000L)   
      };
      
      startTime = System.currentTimeMillis();
      for(int i=0; i<sumThs.length; i++) {
         sumThs[i].start();
      }
      
      for(Thread th : sumThs) {
         try {
            th.join();
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      
      endTime = System.currentTimeMillis();
      
      System.out.println("협력 처리 했을때의 처리시간 : "
            + (endTime - startTime));
   }
}

class SumThread extends Thread {
   private long max, min;
   //생성자의 파라미터를 멤버변수로 저장함.
   public SumThread(long min, long max) {
      this.min = min;
      this.max = max;
   }
   
   @Override
   public void run() {
      long sum = 0L;
      for(long i=min; i<=max; i++) {
         sum += i;
      }
      
      System.out.println(min + " ~ " + max +"까지의 합 : " + sum);
   }
}