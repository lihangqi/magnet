package org.eocencle.magnet.core.util;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 严格规范的map
 * @author: huan
 * @Date: 2020-01-12
 * @Description:
 */
public class StrictMap<V> extends LinkedHashMap<String, V> implements Serializable {
	private static final long serialVersionUID = -183269102104738443L;
	private String name;

	public StrictMap(String name, int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
		this.name = name;
	}

	public StrictMap(String name, int initialCapacity) {
		super(initialCapacity);
		this.name = name;
	}

	public StrictMap(String name) {
		super();
		this.name = name;
	}

	public StrictMap(String name, Map<String, ? extends V> m) {
		super(m);
		this.name = name;
	}

	public V put(String key, V value) {
		if (containsKey(key))
			throw new IllegalArgumentException(name + " already contains value for " + key);
		return super.put(key, value);
	}
	
	public boolean put(String key, V value, boolean ignore) {
		if (ignore) {
			boolean contain = this.containsKey(key);
			super.put(key, value);
			return contain;
		} else {
			try {
				this.put(key, value);
				return false;
			} catch (Exception e) {
				return true;
			}
		}
	}

	public V get(Object key) {
		V value = super.get(key);
		if (value == null) {
			throw new IllegalArgumentException(name + " does not contain value for " + key);
		}
		if (value instanceof Ambiguity) {
			throw new IllegalArgumentException(((Ambiguity) value).getSubject() + " is ambiguous in " + name
				+ " (try using the full name including the namespace, or rename one of the entries)");
		}
		return value;
	}

	private String getShortName(String key) {
		final String[] keyparts = key.split("\\.");
		final String shortKey = keyparts[keyparts.length - 1];
		return shortKey;
	}

	protected static class Ambiguity {
		private String subject;

		public Ambiguity(String subject) {
			this.subject = subject;
		}

		public String getSubject() {
			return subject;
		}
	}
}
