package CUSTOM__HASHMAP;



class Custom_Hashmap_Implementation<K, V> {
	private static final int DEFAULT_CAPACITY = 16;
	private Node<K, V>[] buckets;


	public Custom_Hashmap_Implementation(int capacity) {
		buckets = new Node[capacity];
	}

	public Custom_Hashmap_Implementation() {
		buckets = new Node[DEFAULT_CAPACITY];
	}


	class Node<K, V> {
		public K key;
		public V value;
		public Node<K, V> next;

		Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}



	private int getIndex(K key) {
		return Math.abs(key.hashCode()) % DEFAULT_CAPACITY;
	}

	public void put(K key, V value) {
		int index = getIndex(key);
		Node<K, V> node = buckets[index];

		Node<K, V> current = node;
		while (current != null) {
			if (current.key.equals(key)) {
				current.value = value; // update existing
				return;
			}
			current = current.next;
		}

		Node<K, V> newNode = new Node<>(key, value);
		newNode.next = node;
		buckets[index] = newNode;
	}

	public V get(K key) {
		int index = getIndex(key);
		Node<K, V> current = buckets[index];

		while (current != null) {
			if (current.key.equals(key)){
				return current.value;
			}
			current = current.next;
		}
		return null;
	}

	public boolean containsKey(K key) {
		return get(key) != null;
	}

	public void remove(K key) {
		int index = getIndex(key);
		Node<K, V> head = buckets[index];

		Node<K, V> current = head;
		Node<K, V> prev = null;

		while (current != null) {
			if (current.key.equals(key)) {
				if (prev == null) {
					buckets[index] = current.next;
				} else {
					prev.next = current.next;
				}
				return;
			}
			prev = current;
			current = current.next;
		}
	}

	public void printAll() {
		for (int i = 0; i < buckets.length; i++) {
			Node<K, V> current = buckets[i];
			while (current != null) {
				System.out.println("Key: " + current.key + ", Value: " + current.value);
				current = current.next;
			}
		}
	}

	public static void main(String[] args) {
		Custom_Hashmap_Implementation<String, Integer> map = new Custom_Hashmap_Implementation<>();

		map.put("apple", 10);
		map.put("banana", 20);
		map.put("orange", 30);

		System.out.println("apple: " + map.get("apple"));   // 10
		System.out.println("banana: " + map.get("banana")); // 20

		map.remove("banana");
		System.out.println("banana (after remove): " + map.get("banana")); // null

		System.out.println("\nAll entries:");
		map.printAll();
	}
}
