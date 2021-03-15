package ko.or.ddit.singleton;

public class ExampleClass3 {
	//Instance
	private static ExampleClass3 instance;
	
	//private construct
	private ExampleClass3() {}
	
	public static synchronized ExampleClass3 getInstance() {
		if(instance == null) {instance = new ExampleClass3();}
		return instance;
	}
}
