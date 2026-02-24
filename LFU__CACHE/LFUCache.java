package LFU__CACHE;

import java.util.*;

class Node {
    int key, value, freq;
    Node prev, next;
    Node(int key, int value) {
        this.key = key;
        this.value = value;
        this.freq = 1;
    }
}
// Doubly linked list to maintain LRU order for same frequency ( when tie is there)
class DoublyLinkedList {
    Node head, tail;
    int size;

    DoublyLinkedList() {
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    public void addNodeToHead(Node node) { // Always add node to the head i.e. next to Dummyhead and remove from tail LRU logic  , we r maintaining dummy head & tail here 
        node.next = head.next;    //  Dummyhead <-> newest <-> ... <-> oldest <-> Dummytail
        head.next.prev = node;
        head.next = node;
        node.prev = head;
        size++;
    }

    public void removeNode(Node node) { 
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    public Node removeLastFromTail() {
        if (size == 0) return null;
        Node last = tail.prev;        //  Dummyhead <-> newest <-> ... <-> oldest <-> Dummytail
        removeNode(last); // tail ptr changes in this func so no need to explicitly change
        return last;
    }
    public boolean isEmpty() {
      return size == 0;
    }
}

public class LFUCache {
    private int capacity, minFreq;
    private Map<Integer, Node> keyNodeMap;
    private Map<Integer, DoublyLinkedList> freqMap; 
     /* freqMap:
    	        1 → [2, 5]
    			2 → [1]
    			3 → [7] */

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;
        keyNodeMap = new HashMap<>();
        freqMap = new HashMap<>();
    }

    public int get(int key) {
        if (!keyNodeMap.containsKey(key)) return -1;
        Node node = keyNodeMap.get(key);
        updateNodeFreq(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;

        if (keyNodeMap.containsKey(key)) {
            Node node = keyNodeMap.get(key);
            node.value = value;
            updateNodeFreq(node);
        } else {
            if (keyNodeMap.size() == capacity) {
                DoublyLinkedList minList = freqMap.get(minFreq);
                Node toRemove = minList.removeLastFromTail(); // as we r adding to head , so remove from last
                keyNodeMap.remove(toRemove.key);
                if (minList.isEmpty()) { // if nothing left for that freq
                   freqMap.remove(minFreq);
                }
            }

            Node newNode = new Node(key, value);
            keyNodeMap.put(key, newNode);
            freqMap.computeIfAbsent(1, k -> new DoublyLinkedList()).addNodeToHead(newNode);
            minFreq = 1; // As putting this node for the 1st time so minFreq = 1
        }
    }

    private void updateNodeFreq(Node node) {
        int oldFreq = node.freq;
        DoublyLinkedList oldList = freqMap.get(oldFreq);
        oldList.removeNode(node);

        if (oldFreq == minFreq && oldList.size == 0) {
            minFreq++;
        }

        node.freq++;
        freqMap.computeIfAbsent(node.freq, k -> new DoublyLinkedList()).addNodeToHead(node);
/*
        If a list for frequency node.freq  already exists → returns that list.
        If not → creates a new list new DoublyLinkedList() and puts it into freqMap under key 2.
*/
    }
}