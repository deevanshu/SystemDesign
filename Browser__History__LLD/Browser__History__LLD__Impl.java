package Browser__History__LLD;

import java.util.List;

class HistoryNode {
	String url;
	HistoryNode prev;
	HistoryNode next;
	
	public HistoryNode(String url) { 
		this.url = url; 
	}
}

public class Browser__History__LLD__Impl {

	private HistoryNode head;
	private HistoryNode tail;
	private HistoryNode current;
	private int size;

	public Browser__History__LLD__Impl(String homepage) {
		if (homepage != null) 
		{
			visit(homepage);
		}
	}

	// Visit a new URL: clear forward history and append new node
	public void visit(String url) {
		if (url == null) throw new IllegalArgumentException("url cannot be null");

		HistoryNode node = new HistoryNode(url);

		if (current == null) {
			head = tail = current = node;
			size = 1;
			return;
		}

		// Append
		node.prev = current;
		current.next = node;
		current = node;
		tail = node;
		size = computeSizeOnAppend();

	}

	private int computeSizeOnAppend() {
		// We can increment if we tracked correct size; handle conservative approach:
		// If current was tail before append, then size++ works.
		// But if forward nodes existed before and were cleared, computing by incrementing
		// may be wrong unless we maintain size when clearing forward. For simplicity:
		int count = 0;
		HistoryNode p = head;
		while (p != null) { count++; p = p.next; }
		return count;
	}

	// Move back by one step. Returns true if moved, false if already at oldest.
	public boolean back() {
		if (current != null && current.prev != null) {
			current = current.prev;
			return true;
		}
		return false;
	}

	// Move forward by one step. Returns true if moved.
	public boolean forward() {
		if (current != null && current.next != null) {
			current = current.next;
			return true;
		}
		return false;
	}

	public String getCurrentPage() {
		return current == null ? null : current.url;
	}

	// Optional helper: go back up to steps times and return the final page
	public String back(int steps) {
		while (steps-- > 0 && current != null && current.prev != null) {
			current = current.prev;
		}
		return getCurrentPage();
	}

	// Optional helper: go forward up to steps times and return final page
	public String forward(int steps) {
		while (steps-- > 0 && current != null && current.next != null) {
			current = current.next;
		}
		return getCurrentPage();
	}

	// Debug method: returns a list of URLs from head to tail (useful for tests)
	public List<String> getAllHistory() {
		List<String> list = new java.util.ArrayList<>();

		HistoryNode p = head;
		while (p != null) {
			list.add(p.url + (p == current ? " [CURRENT]" : ""));
			p = p.next;
		}
		return list;
	}

	public void clear() {
		head = tail = current = null;
		size = 0;
	}

	public static void main(String[] args) {

		Browser__History__LLD__Impl bh = new Browser__History__LLD__Impl("homepage.com");
		System.out.println("Current: " + bh.getCurrentPage());   // homepage.com

		bh.visit("a.com");
		bh.visit("b.com");
		bh.visit("c.com");
		System.out.println("Current after visits: " + bh.getCurrentPage());   // c.com

		// Back operations
		bh.back();   // c -> b
		System.out.println("After back(): " + bh.getCurrentPage());  // b.com

		bh.back();   // b -> a
		System.out.println("After back(): " + bh.getCurrentPage());  // a.com

		// Forward operation
		bh.forward(); // a -> b
		System.out.println("After forward(): " + bh.getCurrentPage()); // b.com

		// Visiting new clears forward history
		bh.visit("d.com");  
		System.out.println("After visiting d.com: " + bh.getCurrentPage()); // d.com

		// Try forward (should not work now)
		boolean moved = bh.forward();
		System.out.println("Forward after new visit: " + moved);  // false

		// Print complete internal history
		System.out.println("\nFull History State:");
		for (String s : bh.getAllHistory()) {
			System.out.println(s);
		}
	}
}
