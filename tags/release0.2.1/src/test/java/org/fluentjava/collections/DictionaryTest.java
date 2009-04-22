package org.fluentjava.collections;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.fluentjava.FluentUtils;
import org.fluentjava.closures.Predicate;
import org.junit.Test;

public class DictionaryTest {

	@Test
	public void testFluentStyle() {
		FluentMap<String, Integer> dictionary = new Dictionary<String, Integer>()
		.putAt("Item 1", 1)
		.putAt("Item 2", 2);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("Item 1", 1);
		map.put("Item 2", 2);
		assertEquals(map, dictionary);
	}
	
	@Test
	public void testFluentPutAll() throws Exception {
		FluentMap<String, Integer> map = new Dictionary<String, Integer>()
		.putAt("Item 2", 20)
		.putAt("Item 3", 3);
		FluentMap<CharSequence, Integer> dictionary = new Dictionary<CharSequence, Integer>()
		.putAt("Item 1", 1)
		.putAt("Item 2", 2)
		.insert(map);
		mapKey(dictionary, "Item 1", 1);
		mapKey(dictionary, "Item 2", 20);
		mapKey(dictionary, "Item 3", 3);
		
	}
	

	@Test
	public void testToList() throws Exception {
		FluentMap<String, Integer> map = new Dictionary<String, Integer>()
		.putAt("Item 2", 20)
		.putAt("Item 3", 3);
		FluentList<Pair<String, Integer>> x = new Sequence<Pair<String, Integer>>()
		.insert(FluentUtils.pair("Item 2", 20))
		.insert(FluentUtils.pair("Item 3", 3));
		assertEquals(x.size(), map.toList().size());
		assertEquals(set(x), set(map.toList()));
	}
	
	@Test
	public void testDetect() throws Exception {
		FluentMap<String, Integer> map = new Dictionary<String, Integer>()
		.putAt("Item 2", 20)
		.putAt("Item 3", 3);
		FluentList<Pair<String, Integer>> result = map.select(new Predicate() {
			@Override
			public boolean eval(Object... args) throws Exception {
				Pair<String, Integer> pair = first(args);
				return pair.first.startsWith("I") && pair.second > 0;
			}
		});
		assertEquals(new ExtendedSet<Pair<String, Integer>>()
				.insert(FluentUtils.pair("Item 2", 20))
				.insert(FluentUtils.pair("Item 3", 3)), result.toSet());
	}
	
	@Test
	public void testKeysIsSafeToIterate() throws Exception {
		FluentMap<String, Integer> map = new Dictionary<String, Integer>()
		.putAt("Item 2", 20)
		.putAt("Item 3", 3);
		ExtendedSet<String> result = new ExtendedSet<String>();
		for (String string : map.keys()) {
			result.add(string);
			map.clear();
		}
		assertEquals(new ExtendedSet<String>("Item 2", "Item 3"), result);
	}

	private HashSet<Pair<String, Integer>> set(List<Pair<String, Integer>> array) {
		return new HashSet<Pair<String, Integer>>(array);
	}
	
	private void mapKey(FluentMap<CharSequence, Integer> dictionary, String key, int value) {
		assertEquals(value, dictionary.get(key));
	}

}
