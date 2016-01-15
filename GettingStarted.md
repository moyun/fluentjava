
# Examples #
All examples can be downloaded from the repository (little snippet [here](http://code.google.com/p/fluentjava/source/browse/examples/src/main/java/org/examplesFluentjava/Examples.java)). For terseness sake, the following import statics were used:

```
import static java.util.Arrays.asList;
import static org.examplesFluentjava.Author.author;
import static org.examplesFluentjava.Book.book;
import static org.fluentjava.Closures.getter;
import static org.fluentjava.Closures.identity;
import static org.fluentjava.FluentUtils.alist;
import static org.fluentjava.FluentUtils.asEnumerable;
import static org.fluentjava.FluentUtils.call;
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
```

## Cast ##
### Fluent ###
```
Object o = new HashMap<Integer, List<String>>();
Map<Integer, List<String>> map = cast(o);

```
### Regular ###
```
Object o = new HashMap<Integer, List<String>>();
@SuppressWarnings("unchecked")
Map<Integer, List<String>> map = (Map<Integer, List<String>>) o;

```
## Set Ops ##
### Fluent ###
```
FluentSet<String> agilesIntersection =
		set("tdd", "planning", "refactoring").intersect(
				set("restrospective", "planning", "scrum of scrums"));
System.out.println(agilesIntersection);

```
### Regular ###
```
Set<String> xp = new HashSet<String>();
xp.addAll(asList("tdd", "planning", "refactoring"));
Set<String> scrum = new HashSet<String>();
scrum.addAll(asList("restrospective", "planning", "scrum of scrums"));
Set<String> result = new HashSet<String>(xp);
result.retainAll(scrum);
System.out.println(result);

```
## Maps ##
### Fluent 1 ###
```
FluentMap<String, String> clouds =
		map(pair("Google", "Google App Engine"), pair("Microsoft", "Azure"),
				pair("Amazon", "Elastic Compute Cloud"));
System.out.println(clouds);

```

### Regular ###
```
Map<String, String> clouds = new HashMap<String, String>();
clouds.put("Google", "Google App Engine");
clouds.put("Microsoft", "Azure");
clouds.put("Amazon", "Elastic Compute Cloud");
System.out.println(clouds);

```


### Fluent 2 ###
```
FluentMap<String, String> clouds = map();
clouds.putAt("Google", "Google App Engine")
 .putAt("Microsoft", "Azure")
 .putAt("Amazon", "Elastic Compute Cloud");
System.out.println(clouds);

```
### Fluent 3 ###
```
FluentMap<String, String> clouds =
		FluentUtils.<String, String>map()
 .putAt("Google", "Google App Engine")
 .putAt("Microsoft", "Azure")
 .putAt("Amazon", "Elastic Compute Cloud");
System.out.println(clouds);

```
### Fluent 4 ###
```
FluentMap<Object, Object> clouds = map()
 .putAt("Google", "Google App Engine")
 .putAt("Microsoft", "Azure")
 .putAt("Amazon", "Elastic Compute Cloud");
System.out.println(clouds);

```
### Regular Jmock Style ###
```
@SuppressWarnings("serial")
Map<String, String> clouds = new HashMap<String, String>() {{
		put("Google", "Google App Engine");
		put("Microsoft", "Azure");
		put("Amazon", "Elastic Compute Cloud");
}};
System.out.println(clouds);

```
## Doing Same Thing N Times ##
### Fluent ###
```
StringBuilder builder = new StringBuilder();
for (Integer i : irange(10)) {
	builder.append(i + " squared is: " + i * i).append("\n");
}
System.out.println(builder);

```
### Regular ###
```
StringBuilder builder = new StringBuilder();
for (int i = 0; i < 10; i++) {
	builder.append(i + " squared is: " + i * i).append("\n");
}
System.out.println(builder);

```
## Casting Collections ##
### Fluent ###
```
FluentList<Object> list = alist("one", "two", "three");
FluentList<String> casted = as(list);
System.out.println(casted);

```
### Regular ###
```
List<Object> list = new ArrayList<Object>();
list.addAll(asList("one", "two", "three"));
Object o = list;
@SuppressWarnings("unchecked")
List<String> casted = (List<String>) o;
System.out.println(casted);
```

## Book Examples Setup ##
The more complex examples below utilize this setup.
```
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

public static Book book(String title,
		Integer year,
		BigDecimal price,
		Author... authors) {
	return new Book(title, year, price, asList(authors));
}

public static Author author(String firstName, String surname) {
	return new Author(firstName, surname);
}

```

## Collect Books Tittle ##
### Fluent ###
```
FluentList<String> result = books.map(getter("title"));
System.out.println(result);

```
### Regular ###
```
List<String> result = new ArrayList<String>();
for (Book book : books) {
	result.add(book.getTitle());
}
System.out.println(result);

```
### Collect Books Tittle Using Fork Join Op For Type Safety ###
```
FluentList<String> result = books.map(new Op<Book, String>() {
	public String op(Book book) {
		return book.getTitle();
	}
});
System.out.println(result);

```
### Collect Books Tittle Using Google Function For Type Safety ###
```
FluentList<String> result = books.map(new Function<Book, String>() {
	public String apply(Book book) {
		return book.getTitle();
	}
});
System.out.println(result);

```
## Select Expensive Books With Hamcrest ##
### Fluent ###
```
FluentList<Book> result = books.select(hasProperty("price", greaterThan(new BigDecimal(30))));
System.out.println(result);

```

### Regular ###
```
BigDecimal threshold = new BigDecimal(30);
List<Book> result = new ArrayList<Book>();
for (Book book : books) {
	if (book.getPrice().compareTo(threshold) >= 0) {
		result.add(book);
	}
}
System.out.println(result);

```
## Adapt Immutable Set From Google Collections ##
```
ImmutableSet<Book> set = new ImmutableSet.Builder<Book>().addAll(books).build();
int result = asEnumerable(set)
	.count(hasProperty("price", greaterThan(new BigDecimal(30))));
System.out.println(result);

```
## Adapt From Linked Hash Map ##
```
Map<String, String> clouds = new LinkedHashMap<String, String>();
FluentMap<String, String> fluent = fromMap(clouds)
.putAt("Google", "Google App Engine")
.putAt("Microsoft", "Azure")
.putAt("Amazon", "Elastic Compute Cloud");
System.out.println(fluent);
System.out.println(clouds);

```
## To String Collection With Google Functions ##
### Fluent ###
```
FluentList<String> result = books.map(Functions.toStringFunction());
System.out.println(result);

```
### Regular ###
```
List<String> result = new ArrayList<String>();
for (Book book : books) {
	result.add(book.toString());
}
System.out.println(result);

```
## Lazy Adding Title Lenghts With Fork Join Common Ops ##
### Fluent ###
```
Enumerable<Integer> lenghs = books.imap(getter("title")).imap("length");
Integer result = lenghs.reduce(CommonOps.intAdder());
System.out.println(result);

```
### Regular ###
```
int result = 0;
for (Book book : books) {
	result += book.getTitle().length();
}
System.out.println(result);

```
## Combining Google Collections And Hamcrest Into One Closure ##
```
Predicate<Author> beginsWithA = ClosureCoercion.toClosure(startsWith("A")).as(Predicate.class);
Predicate<Author> beginsWithK = ClosureCoercion.toClosure(startsWith("K")).as(Predicate.class);
FluentList<Object> result = books.map(getter("title"))
	.select(Predicates.or(beginsWithA, beginsWithK));
System.out.println(result);

```
## Combining It All ##
### Fluent ###
```
Set<Object> result = books
	.findAll(hasProperty("year", greaterThan(1999)))
	.map("getAuthors")
	.flatten()
	.select(hasProperty("firstName", startsWith("M")))
	.toSet();
System.out.println(result);

```
### Regular ###
```
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

```
## Runnable From A Method ##
```
Closure closure = call(this, "requestScalaPage");
Thread thread = closure.asThread();
thread.start();
thread.join();

private void requestScalaPage() throws IOException {
	StringBuilder output = new StringBuilder();
	String urlname = "http://www.scala-lang.org/";
	BufferedReader b = new BufferedReader(new InputStreamReader(new URL(urlname).openStream()));
	for (String line = b.readLine(); line != null; line = b.readLine()) {
		output.append(line).append("\n");
	}
	b.close();
	System.out.println(output);
}

```


# Closure Coercions #
## Closure Coercion: from private methods ##
```
public class FluencyTest extends Fluency {
        private int squareOfInt(int i) {
	        return i * i;
        }

	@Test
	public void testClosureFromPrivateMethod() throws Exception {
		FluentList<Integer> list = list(1, 2, 3);
	        assertEquals(list(1, 4, 9), list.map(my("squareOfInt")));
	}
}
```

## Closure To Interfaces ##
```
Closure comparatorClosure = new Closure() {
	public Object call(Object... args) throws Exception {
		String i = first(args);
		String j = second(args);
		return i.length() - j.length();
	}
};
Comparator<String> comparator = comparatorClosure.toInteface(Comparator.class);
assertTrue(comparator.compare("small", "very lage String") < 0);
```


## Enumeration Protocol ##
```
Enumerable<Integer> list = list(1, 2, 3, 4, 5);
assertTrue(list.exists(greaterThan(4)));

private Predicate greaterThan(final int number) {
	Predicate anyGreaterThan = new Predicate() {
		@Override
		public boolean eval(Object... args) throws Exception {
			Integer i = first(args);
			return i > number;
		}
	};
	return anyGreaterThan;
}
```

## Closure Coercion: from Hamcrest ##
```
import static org.fluentjava.FluentUtils.list;
import static org.hamcrest.Matchers.endsWith;

FluentList<String> output = list("a", "fool", "with", "a", "tool", "is", "still", "a", "fool")
.select(endsWith("ol"));
assertEquals(list("fool", "tool", "fool"), output);
```
## Closure Coercion: name method ##
```
import static org.fluentjava.FluentUtils.list;

FluentList<String> list = list("a", "to", "you");
assertEquals(list(1, 2, 3), list.map("length"));
```


## Closure Coercion: annonymous inner class (Reusing Apache Commons Library!) ##
```
import static org.fluentjava.FluentUtils.list;
import org.apache.commons.collections.Predicate;

Predicate endsWithOl = new Predicate { 
       public boolean evaluate(Object object)  {
            String str = (String) object;
            return str.endsWith("ol");
       }
};
FluentList<String> output = list("a", "fool", "with", "a", "tool", "is", "still", "a", "fool")
.select(endsWithOl);
assertEquals(list("fool", "tool", "fool"), output);

```

## Python's Range And Xrange in Java ##
```
import static org.fluentjava.FluentUtils.*;
assertEquals(list(0, 1 , 2 , 3), range(4));
```

```
import static org.fluentjava.FluentUtils.*;
assertEquals(list(0, 1 , 2 , 3), listFromIterable(irange(4)));
assertFalse(list(0, 1 , 2 , 3).equals(irange(4)));
```