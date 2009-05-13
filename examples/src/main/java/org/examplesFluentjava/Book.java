package org.examplesFluentjava;

import static java.util.Arrays.asList;

import java.math.BigDecimal;
import java.util.List;

public class Book {

	public static Book book(String title,
			Integer year,
			BigDecimal price,
			Author... authors) {
		return new Book(title, year, price, asList(authors));
	}

	public String title;
	public Integer year;
	public BigDecimal price;
	public List<Author> authors;

	public Book(String title, Integer year, BigDecimal price, List<Author> authors) {
		super();
		this.title = title;
		this.year = year;
		this.price = price;
		this.authors = authors;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	
	@Override
	public String toString() {
		String str = "\nBook: title = %s," +
				"\n  year = %d," +
				"\n  price = %s," +
				"\n  authors = %s";
		return String.format(str, title, year, price.toPlainString(), authors);
	}
	

}
