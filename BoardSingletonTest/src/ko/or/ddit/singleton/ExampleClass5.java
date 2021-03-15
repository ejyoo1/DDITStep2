package ko.or.ddit.singleton;

public class ExampleClass5 {
	//Instance
	private static ExampleClass5 instance;
	private int count = 0;
	
	//private construct
	private ExampleClass5() {}
	
	public static ExampleClass5 getInstance() {
		if(instance == null) {instance = new ExampleClass5();}
		return instance;
	}
	
	public void print(String input) {
		count++;
		System.out.println(input + "count : " + count);
	}
}
