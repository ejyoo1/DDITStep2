package Assignment0302;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class Student implements Comparable<Student>{
	private String stuNum;//학번
	private String stuName;//이름
	private int kor;//국어점수
	private int eng;//영어점수
	private int mat;//수학점수
	private int subSum;//총점
	private int stuRank;//등수
	
	Student(){
		
	}
	
	Student(String stuNum, String stuName, int kor, int eng, int mat){
		this.stuNum = stuNum;
		this.stuName = stuName;
		this.kor = kor;
		this.eng = eng;
		this.mat = mat; 
		this.subSum = kor + eng + mat;
		this.stuRank = 1;
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