package lab3.NonRabbit;

public class Person {
	protected String name;
	protected int age;

	public Person(String givenName, int givenAge) {

	}
	  
	public String getName() {
		return this.name;
	}
	  
	public int getAge() {
		return this.age;
	}
	  
	public int getNameLength() {
		return this.name.length();
	}
}
