package kr.or.ddit.basic;

public class T01_EnumHomework {
	
	public enum Planet { 
		수성(2439),
		금성(6052),
		지구(6371), 
		성(3390), 
		목성(69911), 
		토성(58232), 
		천왕성(25362), 
		해왕성(24622);
		
		private int radius;
		
		Planet(int data){
			radius = data;
		}
		
		public int getRadius() {
			return radius;
		}
	}
	
	public static void main(String[] args) {
		//원 면적 : 3.14 * 반지름^2
		Planet[] planetArr = Planet.values();
		for(int i = 0; i < planetArr.length; i++) {
			System.out.println(planetArr[i].name() + "의 면적은 : " + (4 * Math.PI * Math.pow(planetArr[i].getRadius(), 2)));
		}
	}
}
