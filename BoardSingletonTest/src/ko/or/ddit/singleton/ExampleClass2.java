package ko.or.ddit.singleton;

public class ExampleClass2 {
	//Instance
	private static ExampleClass2 instance;
	
	//private construct
	private ExampleClass2() {}
	
	public static ExampleClass2 getInstance() {
		if(instance == null) {instance = new ExampleClass2();}
		return instance;
	}
}
