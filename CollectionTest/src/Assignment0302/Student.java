package Assignment0302;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



//2. Student클래스를 만든다.
public class Student implements Comparable<Student>{
//	1. 학번, 이름, 국어점수, 영어점수, 수학점수, 총점, 등수를 멤버로 갖는
	private String stuNum;//학번
	private String stuName;//이름
	private int kor;//국어점수
	private int eng;//영어점수
	private int mat;//수학점수
	private int subSum;//총점
	private int stuRank;//등수
	
	Student(){
		
	}
	
//	3. 생성자는 학번, 이름, 국어, 영어, 수학 점수만 매개변수로 받아서 처리한다.
	Student(String stuNum, String stuName, int kor, int eng, int mat){
		this.stuNum = stuNum;
		this.stuName = stuName;
		this.kor = kor;
		this.eng = eng;
		this.mat = mat; 
		this.subSum = kor + eng + mat;
		this.stuRank = 1;
	}
	
	
	
	@Override
	public String toString() {
		return "Student [stuNum=" + stuNum + ", stuName=" + stuName + ", kor=" + kor + ", eng=" + eng + ", mat=" + mat
				+ ", subSum=" + subSum + ", stuRank=" + stuRank + "]";
	}

	public String getStuNum() {
		return stuNum;
	}

	public void setStuNum(String stuNum) {
		this.stuNum = stuNum;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public int getKor() {
		return kor;
	}

	public void setKor(int kor) {
		this.kor = kor;
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}

	public int getMat() {
		return mat;
	}

	public void setMat(int mat) {
		this.mat = mat;
	}

	public int getSubSum() {
		return subSum;
	}

	public void setSubSum(int subSum) {
		this.subSum = subSum;
	}

	public int getStuRank() {
		return stuRank;
	}

	public void setStuRank(int stuRank) {
		this.stuRank = stuRank;
	}

//	5. List에 저장된 데이터들을 학번의 오름차순으로 정렬하여 출력하는 부분과
//	학번 정렬기준은 Student클래스 자체에서 제공하도록 하고,
	@Override
	public int compareTo(Student stu) {
		return this.getStuNum().compareTo(stu.getStuNum());
	}
	
	public static void setRanked(List<Student> students) {
		for(Student st1 : students) {
			int rank = st1.stuRank;
			for(Student st2 : students) {
				if(st1.getSubSum() < st2.getSubSum()) {
					rank++;
				}
			}
			st1.setStuRank(rank);
		}
	}
	
	
	
	
	
	public static void main(String[] args) {
//		4. 이 Student객체들은 List에 저장하여 관리한다.
		List<Student> students = new ArrayList<Student>();
		
		students.add(new Student("201263346", "홍길동", 75,65,52));
		students.add(new Student("201263344", "변학도", 70,60,20));
		students.add(new Student("201263343", "강감찬", 75,65,52));
		students.add(new Student("201263341", "성춘향", 10,95,83));
		students.add(new Student("201263342", "이순신", 75,65,52));
		students.add(new Student("201263345", "일지매", 75,65,52));
		
		setRanked(students);
		
		
		System.out.println("학생목록 정렬 전 : ");
		for(Student stu : students) {
			System.out.println(stu);
		}
		System.out.println("===================================");
		
		Collections.sort(students);
		
//		5. List에 저장된 데이터들을 학번의 오름차순으로 정렬하여 출력하는 부분과
		System.out.println("학생목록 정렬 후 : ");
		for(Student stu : students) {
			System.out.println(stu);
		}
		System.out.println("===================================");
		
		System.out.println("총점 같을 때 학번 정렬");
		Collections.sort(students, new SortSubSum());	
		for(Student stu : students) {
			System.out.println(stu);
		}
	}
}

//6. 총점의 역순으로 정렬하는 부분을 프로그램 하시오.
//총점 정렬기준은 외부클래스에서 제공하도록 한다.
//총점이 같으면 학번의 내림차순으로 정렬되도록 한다.
class SortSubSum implements Comparator<Student>{

	@Override
	public int compare(Student stu1, Student stu2) {
		if(stu1.getSubSum() == stu2.getSubSum()) {
			return stu1.getStuNum().compareTo(stu2.getStuNum()) * -1;
		}else {
			return Integer.compare(stu1.getSubSum(), stu2.getSubSum()) * -1;
		}
	}
	
}