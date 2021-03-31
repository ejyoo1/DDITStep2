package kr.or.ddit.basic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.lang.model.element.Element;

/*
 * 주석처럼 사용, 프로그램에 영향을 주지 않음., 메타데이터 , 종류2가지(표준,메타 애너테이션)
   annotation에 대하여...
   
   프로그램 소스코드 안에 다른 프로그램을 위한 정보를 미리 약속된 형식으로 포함시킨것.
   (JDK 1.5부터 지원함)
  주석처럼 프로그래밍 언어에 영향을 미치지 않으면서도 다른 프로그램에게 유용한 정보를 제공함.
  
  종류 : 1. 표준 애너테이션(주로 컴파일러에게 유용한 정보를 제공하기 위한 애너테이션) 
       2. 메타 에너테이션(애너테이션을 위한 애너테이션, 즉 애너테이션을 정의할때 
                                         사용하는 애너테이션)
                                       
   애너테이션 타입 정의하기
   @interface 애너테이션이름 {
   	   타입요소이름(); // 반환값이 있고 매개변수는 없는 추상메서드 형태처럼 구성되어있음 (메서드는 아님)
   	   ...
   }
 애너테이션 요소의 규칙
 1. 요소의 타입은 기본형, String, enum, annotation, class만 허용된다.
 2. ()안에 매개변수를 선언할 수 없다.
 3. 예외를 선언할 수 없다.(메서드가 아니기 때문.)
 4. 요소의 타입에 타입 매개변수(지네릭타입문자)를 사용할 수 없다.
*/

//메타 annotation 선언 방법
@Target(ElementType.METHOD)	// annotation이 적용가능한 대상을 지정함. (타겟 정보를 메서드에 붙이겠다)
@Retention(RetentionPolicy.RUNTIME) // 유지되는 기간(SOURCE,CLASS,RUNTIME). ==> default class (언제까지 어노테이션을 유지할것인지) ==> source : 컴파일 전 class:컴파일 시에만 runtime : 객체 생성에도유지(항상 존재한다) 
public @interface PrintAnnotation {
	int id = 100; //상수 선언 가능. static final int id = 100
	String value() default "-"; //기본값을 '-'로 지정(default라는 키워드로 기본값을 넣어줄 수 있다.)
	int count() default 20; //기본값을 20으로 지정
	
	/*이런 과정들이 xml에서 설정정보 하는것과 같다.*/
}