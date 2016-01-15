# Collection Theme #
  * Wrapped over Fork Join: 10 points

“It has to be easier to send snippets of code to frameworks for parallel
execution; otherwise no one will use them.” – Doug Lea, 2005


# Closures Theme #
  * Detecting [SAMs](http://docs.google.com/View?docid=k73_1ggr36h&) generated from Abstract Classes.
  * TimeIt: 4  points
  * Compose: 6 points
  * Curry: 1 point


# To think about it (not yet priorized) #
  * Support closures from: Scala, Jruby and Groovy. Maybe double Enumerable's methods to enable it receiving an Interface like Fork Join's Op, so that groovy and jruby will atomatically convert their closures into it (no trouble for us!). Scala does not support closure -> java interface implictily (unless you explictly use [views)](http://www.scala-lang.org/node/130), so it will probably have to be reflectively and actively supported.
  * Softening exceptions (on Closure)
  * Aspectj Aspect to make things like _my_ callable from static methods without one single argument (for those who won't/can't/don't like/don't feel like it/are grossed out about extending a third party class).
  * Arithmethic operations (smalltalk inspiration, again) on enumerable
  * Partition and iterablePartition on enum (ruby calls this group\_by, influenced by smalltalk's groupBy:having:)
  * Ruby's hash constructor (maybe extended interface on map, as the defaults do not allow this):
```
Hash.new => hash
Hash.new(obj) => aHash
Hash.new {|hash, key| block } => aHash
```
  * Look into using [codeworker](http://codeworker.free.fr/) or [janino](http://www.janino.net/) to enable _eval_, and make closures out of it.
  * cache to o ClosureOfAMethodName based on signature.
  * CharSequence to enumerable
  * More on Enumerable: FlatMap, Join (more alike [mkString](http://www.scala-lang.org/docu/files/api/scala/Iterable.html#mkString) methods from Scala), [Grep](http://ruby-doc.org/core-1.8.7/classes/Enumerable.html#M001137)