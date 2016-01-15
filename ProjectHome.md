# News #
<font color='blue' size='5'>11/05/2009: <a href='http://fluentjava.googlecode.com/files/fluentjava-0.3.jar'>fluentjava 0.3</a> Released</font>.

The whole [EnumerationProtocol](EnumerationProtocol.md) was implemented, as you can see [here](http://fluentjava.googlecode.com/svn/javadoc/apidocs/org/fluentjava/collections/Enumerable.html), with a little closure coercion as well. Fluent Sets, Fluent Maps and Fluent lists can be easily be created from other collections libraries. Lazy methods on enumeration protocol, so that you can cascade, filter, map, take and only invoke the actual closures when you actually need the result. And much [more](Releases.md).

Get started [here](GettingStarted.md).

# Introduction #
Project that attempts to reduce the verbosity, lack of fluency and unfriendly interface that is common to several tasks in Java, such as:

  * Blocks of code manipulation (Closures)
  * Collection manipulation
  * Reflection
  * Input/Output
  * Raw Type conversion

## Next few Steps ##
  * Really planned [list](ProductBacklog.md)
  * **Deprecated** as 14/01/2010 by Mark Reinhold's [assertion](http://blogs.sun.com/mr/entry/closures) that lambda expressions or even full blown closures will be brought to java7. Ok, evidences are ([here](http://javanicus.com/blog2/items/212-index.html), [here](http://www.slideshare.net/gal.marder/whats-expected-in-java-7-1116123) and [here](http://tech.puredanger.com/java7)): java 7 will very likely not have closures, which made me reconsider adding it to this project, while making it backwards compatible with all the bringing closure to java attempts. Automatic object to Closure coercion is vital to this, and little [AOP](http://www.eclipse.org/aspectj/) can make it even more _fluent_.
  * New File-like returning reades/writers, streams, and ExtendedIterators for paths and lines of content for simple files/Files for directories (many features are already included on commons-io, but all as top-level functions). Fluent style.
  * Make Files a Composite: File-like structure composed of folders and files.
  * Mirror getting Maps as input/output
  * Mirror being able to convert types like [Spring](http://springframework.org/) does on its IoC
  * Fluent output. System.out just doesn't cut. Also use [Xstream](http://xstream.codehaus.org/index.html) to allow output of any object in XML format.
  * Generic Decorators that turn any object into Serializable (via Xstream in the generic case) and Comparables (receiving Comparators)
  * Create Order objects, which require only lessThanMethod to be implemented (no more 0, -1 or negative, or +1 or positive that Comparators need). Comparator built from Orders. Inspiration: Python.

# General Concepts #
A few links to clear things up.

  * Fluent Style and DSLs:
    * http://martinfowler.com/dslwip/
    * http://martinfowler.com/bliki/FluentInterface.html
  * Closures:
    * Article that introduces closures and its uses, using apache commons:  http://www.ibm.com/developerworks/library/j-fp.html
    * Martin Fowler's post on the uses of closures and collections: http://martinfowler.com/bliki/CollectionClosureMethod.html
    * _No closures_ in java: http://weblogs.java.net/blog/brucechapman/archive/2008/03/anouncement_no.html
    * [Enumerable](http://www.ruby-doc.org/core/classes/Enumerable.html) in ruby: How closures are usefull.
    * [Scala](http://www.scala-lang.org/docu/files/api/scala/Iterable.html) also has its own  enumeration protocol.
    * [Closures in Smalltalk](http://www.gnu.org/software/smalltalk/manual-base/html_node/Collection_002denumeration.html): where all the oo + functional rage began. Also, they are called blocks here.
    * [Functional java](http://functionaljava.org/examples) is a project that aims to prepare the Java programming language for the inclusion of closures, and would benefit from java incorporating the [BGGA](http://javac.info/) _implementation_.
    * [Groovy](http://groovy.codehaus.org/Functional+Programming+with+Groovy) also makes [good](http://groovy.codehaus.org/JN1015-Collections) use of it. No need to wait for closures on java to use the project above. It is not pure java though.
    * [Fork join](http://gee.cs.oswego.edu/dl/concurrency-interest/index.html) [javadoc](http://gee.cs.oswego.edu/dl/jsr166/dist/extra166ydocs/): filter, map, find, etc. Not only are they usefull for expressing, but [IBM](http://www.ibm.com/developerworks/java/library/j-jtp03048.html?S_TACT=105AGX02&S_CMP=ART) has made it clear that they work very well with closures, and are great at making common tasks trivally parallel. **Unlike** closures, this made through the cut into java 7.


# Similar Projects #
  * [Apache Commons](http://commons.apache.org/) (formerly known as Jarkata Commons): similar feature-wise mostly to the features encompassed by commons lang, commons io and commons collections.
  * [Jaggregate](http://jaggregate.sourceforge.net/index.html): great project of Smalltalk-like collections. The project has its downfalls though, such as using Java collections classes names, which can be confusing.
  * [Generic algorithmns for java](http://jga.sourceforge.net/): closure api to Java. Still not as terse as real smtalltalk, python, ruby or scheme's syntax, but provides quite a few  pre-made closures.
  * [Google collections](http://code.google.com/p/google-collections/): also provides some closures, not as much as jga. Has several kinds of collections though, and several utility methods on them as well.
  * [Hamcrest Collections](http://code.google.com/p/hamcrest-collections/): very fluent interface for collections. Development is still at the beginning, and uses a lot of Global Scoping technique, which mostly comes from Hamcrest.
  * [http://functionalj.sourceforge.net/](http://functionalj.sourceforge.net/): Another project that tries to bring functional programming to java world. Yeah [google](http://labs.google.com/papers/mapreduce.html) did it a long time ago, and [Hadoop](http://hadoop.apache.org/core/) did it again. Yes, map and reduce are functional concepts.
  * [gparallelizer](http://code.google.com/p/gparallelizer/) By using Groovy, it wrapps around Fork-join and make it more usable.