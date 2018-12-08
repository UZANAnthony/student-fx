package application;

import java.time.LocalDate;

public class Student {
	
	int id;
	String name;
	String gender;
	LocalDate birthDate;
	String photo;
	String mark;
	String comments;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public Student(int id, String name, String gender, LocalDate birthDate, String photo, String mark,
			String comments) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.birthDate = birthDate;
		this.photo = photo;
		this.mark = mark;
		this.comments = comments;
	}
	
	public Student(String name, String gender) {
		super();
		this.name = name;
		this.gender = gender;
	}
	
	public Student() {
		super();
	}
	
	

}
