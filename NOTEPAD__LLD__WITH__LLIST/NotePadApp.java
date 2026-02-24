package NOTEPAD__LLD__WITH__LLIST;

import java.util.List;

public class NotePadApp {
    public static void main(String[] args) {
        List<String> lines = List.of(
            "Hello world!",
            "This is a simple notepad.",
            "We can move cursor around.",
            "LLD example in Java."
        );

        Notepad notepad = new Notepad(lines); // create LList & set cursor

        notepad.printCursorPosition(); // Line 1, Col 0
        notepad.moveDown();
        notepad.moveRight();
        notepad.printCursorPosition(); // Line 2, Col 1
        notepad.pageDown();
        notepad.printCursorPosition();
    }
}/*
	HEAD --> LineNode1 <---> LineNode2 <---> LineNode3 <---> LineNode4 --> NULL
	

                    Cursor:
					+--------------------------+
					| currentLine: LineNode1   |
					| columnPosition: 0        |
					+--------------------------+
	HEAD                     ↓
	↓                        ↓
	↓					     ↓			      
	LineNode1:               ↓               
	+---------------------------+
	| text: "Hello world!"      |
	| prev: null                |
	| next: LineNode2           |                  
	+---------------------------+
	 ↓
	 ↓
	LineNode2:
	+-------------------------------+
	| text: "This is a simple notepad." |
	| prev: LineNode1                |
	| next: LineNode3                |
	+-------------------------------+
	 ↓
	 ↓
	LineNode3:
	+-----------------------------------+
	| text: "We can move cursor around." |
	| prev: LineNode2                   |
	| next: LineNode4                   |
	+-----------------------------------+
	 ↓
	 ↓
	LineNode4:
	+---------------------------+
	| text: "LLD example in Java." |
	| prev: LineNode3             |
	| next: null                  |
	+---------------------------+
	 ↓
	 ↓
	 NULL
	 
	
*/
//Node for a single line
class LineNode {
 String text;
 LineNode prev, next;

 LineNode(String text) {
     this.text = text;
 }
}

//Cursor for tracking current position
class Cursor {
 LineNode currentLine;
 int columnPosition;  // index within current line
}

class Notepad {
    private LineNode head,tail;
    private Cursor cursor;
    private static final int PAGE_SIZE = 20; // assume visible lines per page

    public Notepad(List<String> lines) {
        // Build doubly linked list
        for (String line : lines) {
            LineNode node = new LineNode(line);
            if (head == null) head = node;
            if (tail != null) {
                tail.next = node;
                node.prev = tail;
            }
            tail = node;
        }
        cursor = new Cursor();
        cursor.currentLine = head;
        cursor.columnPosition = 0;
    }

    // Move left
    public void moveLeft() {
        if (cursor.columnPosition > 0) { 
            cursor.columnPosition--;
        } else if (cursor.currentLine.prev != null) {
            cursor.currentLine = cursor.currentLine.prev;
            cursor.columnPosition = cursor.currentLine.text.length() - 1;
        }
    }

    // Move right
    public void moveRight() {
        if (cursor.columnPosition < cursor.currentLine.text.length() - 1) {
            cursor.columnPosition++;
        } else if (cursor.currentLine.next != null) {
            cursor.currentLine = cursor.currentLine.next;
            cursor.columnPosition = 0;
        }
    }

    // Move up
    public void moveUp() {
        if (cursor.currentLine.prev != null) {
            cursor.currentLine = cursor.currentLine.prev;
            cursor.columnPosition = Math.min(cursor.columnPosition, cursor.currentLine.text.length()-1);
        }
    }

    // Move down
    public void moveDown() {
        if (cursor.currentLine.next != null) {
            cursor.currentLine = cursor.currentLine.next;
            cursor.columnPosition = Math.min(cursor.columnPosition, cursor.currentLine.text.length()-1); 
        }
    }

    // Page Up
    public void pageUp() {  // move up by fix size i.e. PAGE_SIZE here ,column position remain same even if line is shorter
        for (int i = 0; i < PAGE_SIZE && cursor.currentLine.prev != null; i++) {
            cursor.currentLine = cursor.currentLine.prev;
        }
    }

    // Page Down
    public void pageDown() { // move up by fix size i.e. PAGE_SIZE here ,column position remain same even if line is shorter
        for (int i = 0; i < PAGE_SIZE && cursor.currentLine.next != null; i++) {
            cursor.currentLine = cursor.currentLine.next;
        }
    }

    public void printCursorPosition() { 
        System.out.println("Cursor at line: " + getLineNumber(cursor.currentLine) +
                           ", column: " + cursor.columnPosition);
    }

    private int getLineNumber(LineNode node) {
        int count = 1;
        LineNode temp = head;
        while (temp != node) {
            temp = temp.next;
            count++;
        }
        return count;
    }
}
