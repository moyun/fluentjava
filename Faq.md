## So many nice static methods! But how to ease the development, instead of writing static imports all the time? ##
FluentUtils contains several static methods that eases up lots of constructions provided by this project.

Eclipse (since Europa) support favorite static imports. It allows you to auto complete static methods of a class even when you haven't imported the class (it adds them to the auto-complete list automatically). For this, go to Preferences -> Java -> Editor -> Content Assist -> Favorites. More on this, including some pics, [here](http://www.ralfebert.de/blog/eclipseide/static_methods/).

Therefore, if you want to make all static methods from FluentUtils your preferred ones, just add the type: org.fluentjava.FluentUtils to your favorites.

## What is this _type parameters of `<`T`>`T cannot be determined; no unique maximal instance exists for type variable T with ..._ error? ##
This can usually happen when using generic methods with primitives or generic types. For instance:

```
E e = closure.invoke();
boolean bol = closure.invoke();
```

Eclipse's compiler allows it, but javac doesn't. Some ways around this:

  * Specify the type:
```
E e = closure.<E>invoke();
boolean bol = closure.<Boolean>invoke();
```

  * This doesn't work on primitives, as they can't be generic types: do not use autoboxxing:
```
Boolean bol = closure.<Boolean>invoke();
```