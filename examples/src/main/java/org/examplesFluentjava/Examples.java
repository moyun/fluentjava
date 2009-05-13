package org.examplesFluentjava;

import static java.util.Arrays.asList;
import static org.examplesFluentjava.Author.author;
import static org.examplesFluentjava.Book.book;
import static org.fluentjava.Closures.getter;
import static org.fluentjava.Closures.identity;
import static org.fluentjava.FluentUtils.alist;
import static org.fluentjava.FluentUtils.asEnumerable;
import static org.fluentjava.FluentUtils.cast;
import static org.fluentjava.FluentUtils.fromMap;
import static org.fluentjava.FluentUtils.irange;
import static org.fluentjava.FluentUtils.list;
import static org.fluentjava.FluentUtils.map;
import static org.fluentjava.FluentUtils.pair;
import static org.fluentjava.FluentUtils.set;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.startsWith;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fluentjava.FluentUtils;
import org.fluentjava.collections.Enumerable;
import org.fluentjava.collections.FluentList;
import org.fluentjava.collections.FluentMap;
import org.fluentjava.collections.FluentSet;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.ImmutableSet;

import extra166y.CommonOps;
import extra166y.Ops.Op;

@SuppressWarnings("unused")
public class Examples {
	public static void main(String[] args) throws Exception {
		Examples examples = new Examples();
		int total = 0;
		for (Method method : examples.getClass().getMethods()) {
			if (method.getDeclaringClass().equals(Examples.class)
					&& !Modifier.isStatic(method.getModifiers())) {
				System.out.println("Invoking:" + method.getName());
				method.invoke(examples);
				System.out.println();
				total ++;
			}
		}
		System.out.println("\n\nTotal examples: " + total);
	}

	private FluentList<Book> books = list();

	public Examples() {
		Author mikeCohn = author("Mike", "Cohn");
		Author jaynePearl = author("Jayne", "Pearl");
		Author martinFolwer = author("Martin", "Fowler");
		Author kentBeck = author("Kent", "Beck");
		Author knuth = author("Donald", "Knuth");
		books.add(book(
				"Keep or Sell Your Business: How to Make the Decision Every Private Company Faces",
				2000, decimal(17, 90), mikeCohn, jaynePearl));
		books.add(book("Agile Estimating and Planning", 2005, decimal(38, 99), mikeCohn));
		books.add(book("Planning Extreme Programming", 2000, decimal(32, 9), martinFolwer, kentBeck));
		books.add(book("The Art of Computer Programming", 1998, decimal(148, 19), knuth));
		
	}

	private BigDecimal decimal(int intvalue, int decvalue) {
		BigDecimal dec = new BigDecimal(decvalue).divide(new BigDecimal(100));
		return new BigDecimal(intvalue).add(dec).setScale(2);
	}

	public void fluentCast() {
		Object o = new HashMap<Integer, List<String>>();
		Map<Integer, List<String>> map = cast(o);
	}

	
	public void regularCast() {
		Object o = new HashMap<Integer, List<String>>();
		@SuppressWarnings("unchecked")
		Map<Integer, List<String>> map = (Map<Integer, List<String>>) o;
	}

	public void fluentSetOps() {
		FluentSet<String> agilesIntersection =
				set("tdd", "planning", "refactoring").intersect(
						set("restrospective", "planning", "scrum of scrums"));
		System.out.println(agilesIntersection);
	}

	public void regularSetOps() {
		Set<String> xp = new HashSet<String>();
		xp.addAll(asList("tdd", "planning", "refactoring"));
		Set<String> scrum = new HashSet<String>();
		scrum.addAll(asList("restrospective", "planning", "scrum of scrums"));
		Set<String> result = new HashSet<String>(xp);
		result.retainAll(scrum);
		System.out.println(result);
	}

	public void fluentMap1() {
		FluentMap<String, String> clouds =
				map(pair("Google", "Google App Engine"), pair("Microsfot", "Azure"),
						pair("Amazon", "Elastic Compute Cloud"));
		System.out.println(clouds);
	}

	public void fluentMap2() {
		FluentMap<String, String> clouds = map();
		clouds.putAt("Google", "Google App Engine").putAt("Microsfot", "Azure").putAt(
				"Amazon", "Elastic Compute Cloud");
		System.out.println(clouds);
	}

	public void fluentMap3() {
		FluentMap<String, String> clouds =
				FluentUtils.<String, String>map().putAt("Google", "Google App Engine").putAt(
						"Microsfot", "Azure").putAt("Amazon", "Elastic Compute Cloud");
		System.out.println(clouds);
	}

	public void fluentMap4() {
		FluentMap<Object, Object> clouds =
				map().putAt("Google", "Google App Engine").putAt("Microsfot", "Azure").putAt(
						"Amazon", "Elastic Compute Cloud");
		System.out.println(clouds);
	}

	public void regularMap() {
		Map<String, String> clouds = new HashMap<String, String>();
		clouds.put("Google", "Google App Engine");
		clouds.put("Microsfot", "Azure");
		clouds.put("Amazon", "Elastic Compute Cloud");
		System.out.println(clouds);
	}

	public void regularMapJmockStyle() {
		@SuppressWarnings("serial")
		Map<String, String> clouds = new HashMap<String, String>() {
			{
				put("Google", "Google App Engine");
				put("Microsfot", "Azure");
				put("Amazon", "Elastic Compute Cloud");
			}
		};
		System.out.println(clouds);
	}

	public void fluentDoingSameThingNTimes() {
		StringBuilder builder = new StringBuilder();
		for (Integer i : irange(10)) {
			builder.append(i + " squared is: " + i * i).append("\n");
		}
		System.out.println(builder);
	}

	public void regularDoingSameThingNTimes() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			builder.append(i + " squared is: " + i * i).append("\n");
		}
		System.out.println(builder);
	}

	public void fluentCastingCollections() {
		FluentList<Object> list = alist("one", "two", "three");
		FluentList<String> casted = list.map(identity());
		System.out.println(casted);
	}

	public void regularCastingCollections() {
		List<Object> list = new ArrayList<Object>();
		list.addAll(asList("one", "two", "three"));
		List<String> casted = new ArrayList<String>();
		for (Object object : list) {
			casted.add((String) object);
		}
		System.out.println(casted);
	}
	
	public void fluentCollectBooksTittle() {
		FluentList<String> result = books.map(getter("title"));
		System.out.println(result);
	}
	
	public void regularCollectBooksTittle() {
		List<String> result = new ArrayList<String>();
		for (Book book : books) {
			result.add(book.getTitle());
		}
		System.out.println(result);
	}
	
	public void fluentCollectBooksTittleUsingForkJoinOpForTypeSafety() {
		FluentList<String> result = books.map(new Op<Book, String>() {
			public String op(Book book) {
				return book.getTitle();
			}
		});
		System.out.println(result);
	}
	
	
	public void fluentCollectBooksTittleUsingGoogleFunctionTypeSafety() {
		FluentList<String> result = books.map(new Function<Book, String>() {
			public String apply(Book book) {
				return book.getTitle();
			}
		});
		System.out.println(result);
	}

	
	public void fluentSelectExepensiveBooksWithHamcrest() {
		FluentList<Book> result = books.select(hasProperty("price", greaterThan(new BigDecimal(30))));
		System.out.println(result);
	}
	
	public void regularSelecExepensiveBookstWithoutHamCrest() {
		BigDecimal threshold = new BigDecimal(30);
		List<Book> result = new ArrayList<Book>();
		for (Book book : books) {
			if (book.getPrice().compareTo(threshold) >= 0) {
				result.add(book);
			}
		}
		System.out.println(result);
	}
	
	public void fluentAdaptImmutableSetFromGoogleCollections() {
		ImmutableSet<Book> set = new ImmutableSet.Builder<Book>().addAll(books).build();
		int result = asEnumerable(set)
			.count(hasProperty("price", greaterThan(new BigDecimal(30))));
		System.out.println(result);
	}
	
	public void fluentAdaptFromLinkedHashMap() {
		Map<String, String> clouds = new LinkedHashMap<String, String>();
		FluentMap<String, String> fluent = fromMap(clouds)
		.putAt("Google", "Google App Engine")
		.putAt("Microsfot", "Azure")
		.putAt("Amazon", "Elastic Compute Cloud");
		System.out.println(fluent);
		System.out.println(clouds);
	}
	
	public void fluentToStringCollectionWithGoogleFunctions() {
		FluentList<String> result = books.map(Functions.toStringFunction());
		System.out.println(result);
	}
	
	public void regularToStringCollectionWithoutGoogleFunctions() {
		List<String> result = new ArrayList<String>();
		for (Book book : books) {
			result.add(book.toString());
		}
		System.out.println(result);
	}
	
	public void fluentLazyAddingTitleLenghtsWithForkJoinCommonOps() {
		Enumerable<Integer> lenghs = books.imap(getter("title")).imap("length");
		Integer result = lenghs.reduce(CommonOps.intAdder());
		System.out.println(result);
	}
	
	public void regularLazyAddingTitleLenghtsWithoutForkJoinCommonOps() {
		int result = 0;
		for (Book book : books) {
			result += book.getTitle().length();
		}
		System.out.println(result);
	}
	
	public void fluentCombiningItAll() {
		Set<Object> result = books
			.findAll(hasProperty("year", greaterThan(1999)))
			.map("getAuthors")
			.flatten()
			.select(hasProperty("firstName", startsWith("M")))
			.toSet();
		System.out.println(result);
	}

	public void regularCombiningItAll() {
		Set<Object> result = new HashSet<Object>();
		List<Author> authors = new ArrayList<Author>();
		for (Book book : books) {
			if (book.getYear() > 1999) {
				authors.addAll(book.getAuthors());
			}
		}
		for (Author author : authors) {
			if (author.getFirstName().startsWith("M")) {
				result.add(author);
			}
		}
		System.out.println(result);
	}
}
