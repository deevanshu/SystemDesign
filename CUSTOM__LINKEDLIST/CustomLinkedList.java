package CUSTOM__LINKEDLIST;

class Node {

	int data;
	Node next;
	Node(int d)
	{
		data = d;
		next = null;
	}
}

public class CustomLinkedList {

	Node head;

	// Insert at end
	public void insert(int data) {
		Node newNode = new Node(data);
		if (head == null) {
			head = newNode;
			return;
		}

		Node current = head;
		while (current.next != null) {
			current = current.next;
		}

		current.next = newNode;
	}

	// Insert at beginning
	public void insertAtBeginning(int data) {
		Node newNode = new Node(data);
		newNode.next = head;
		head = newNode;
	}

	// Delete by value
	public void delete(int key) {
		if (head == null) return;

		if (head.data == key) {
			head = head.next;
			return;
		}

		Node current = head;
		while (current.next != null && current.next.data != key) { // so to have the ptr to next to next node  , 1 --> 2 --> 3 --> 4 , del 3 when @ 2 we exit loop
			current = current.next;
		}

		if (current.next != null) {    
			current.next = current.next.next;  //  make current(2) next pt. to 4(curr.next.next)
		}
	}

	// Print list
	public void printList() {
		Node current = head;
		while (current != null) {
			System.out.print(current.data + " -> ");
			current = current.next;
		}
		System.out.println("null");
	}
	
	public static void main(String[] args) {
		CustomLinkedList list = new CustomLinkedList();

        list.insert(10);
        list.insert(20);
        list.insert(30);
        list.insertAtBeginning(5);
        list.printList(); // 5 -> 10 -> 20 -> 30 -> null

        list.delete(20);
        list.printList(); // 5 -> 10 -> 30 -> null
    }
	

}
