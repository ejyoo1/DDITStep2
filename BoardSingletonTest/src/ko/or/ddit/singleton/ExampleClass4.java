package ko.or.ddit.singleton;

public class ExampleClass4 {
	//private construct
	private ExampleClass4() {}
	
	private static class InnerInstanceClazz() {
		private static final ExampleClass4 instance = new ExampleClass4();
	}
	
	public static ExampleClass4 getInstance() {
		return InnerInstanceClazz.instance;
	}
}
