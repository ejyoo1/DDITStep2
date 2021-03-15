package ko.or.ddit.singleton;

public class ExampleClass1 {
	//Instance
	private static ExampleClass1 instance;
	
	//private construct
	private ExampleClass1() {}
	
	static {
		try {instance = new ExampleClass1();}
		catch(Exception e) { throw new RuntimeException(
				"Create instance fail. error mse = " + e.getMessage());}
	}
	
	public static ExampleClass1 getInstance() {
		return instance;
	}
}
