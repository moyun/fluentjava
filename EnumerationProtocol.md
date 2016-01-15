# Introduction #

Smalltalk collection api makes heavy use of closures. The methods that are categorized (yes methods in smalltalk can be divided into categories) under Enumeration encompass such protocol:

  * allSatisfy:
  * anySatisfy:
  * associationsDo:
  * collect:
  * collect:thenSelect:
  * count:
  * detect:
  * detect:ifNone:
  * detectMax:
  * detectMin:
  * detectSum:
  * difference:
  * do:
  * do:separatedBy:
  * do:without:
  * groupBy:having:
  * inject:into:
  * intersection:
  * noneSatisfy:
  * reject:
  * select:
  * select:thenCollect:
  * union:

Further information on what they do can be found [here](http://wiki.squeak.org/squeak/uploads/SqueakClassesRef.html#Enumerating%20Collections)

## References ##
  * [Traits : Tools and Methodology](http://web.cecs.pdx.edu/~black/presentations/Traits%20Methodology.pdf)
  * [Ruby](http://www.ruby-doc.org/core/classes/Enumerable.html#M003163) also has a very similar protocol
  * [More](http://eigenclass.org/hiki.rb?Changes+in+Ruby+1.9#l55) ruby enumerable method on from version 1.9
  * [Scala](http://www.scala-lang.org/docu/files/api/scala/Iterable.html) also has an interesting enumeration protocol.