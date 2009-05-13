package org.examplesFluentjava;

public class Author {
	public static Author author(String firstName, String surname) {
		return new Author(firstName, surname);
	}

	public String firstName;
	public String surname;
	
	public Author(String firstName, String surname) {
		super();
		this.firstName = firstName;
		this.surname = surname;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@Override
	public String toString() {
		return String.format("Author: firstname = %s, surname = %s", firstName, surname);
	}
	
	
}
