package com.jstevenperry.intro;

import java.util.logging.Logger;

public class Person {
	
	private static final Logger logger = Logger.getLogger(Person.class.getName());
	 	
	private String name;
	private int age;
	private int height;
	private int weight;
	private String eyeColor;
	private String gender;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getEyeColor() {
		return eyeColor;
	}
	public void setEyeColor(String eyeColor) {
		this.eyeColor = eyeColor;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Person(String name, int age, int height, int weight, String eyeColor, String gender) {
		super();
		this.name = name;
		this.age = age;
		this.height = height;
		this.weight = weight;
		this.eyeColor = eyeColor;
		this.gender = gender;
		
		logger.info("Created Person object with name '" + getName() + "'");
	}
	
	

}

