package NESTED__LIST__ITERATOR;

import java.util.*;

// class UnsupportedNestedElementException extends Exception {  // In case of Checked custom exception 
//
//    public UnsupportedNestedElementException(String message) {
//        super(message);
//    }
//
//    public UnsupportedNestedElementException(String message, Throwable cause) {
//        super(message, cause);
//    }
//}

public class NestedListIterator implements Iterator<Integer> {
	
    private final List<Integer> flattened = new ArrayList<>(); // stores flattened list like [ 1 ,2 ,3 ,4 ,5 ]
    private int nextIntIndex = 0;

    public NestedListIterator(List<Object> nestedList) { // throws UnsupportedNestedElementException  // passing i/p nested list of object and then flattening in below function
        flattenIterative(nestedList);  
    }

    // Flatten using explicit stack
    private void flattenIterative(List<Object> nestedList) { // throws UnsupportedNestedElementException  , In case of Checked custom exception
        Stack<Object> stack = new Stack<>();
        
        // push root list elements in reverse so we pop them left to right
        for (int i = nestedList.size() - 1; i >= 0; i--) {
            stack.push(nestedList.get(i));
        }

        while (!stack.isEmpty()) {
            Object val = stack.pop();

            if (val instanceof Integer) {
                flattened.add((Integer) val);   // Explicit casting it to Integer then adding
                
            } else if (val instanceof List<?>) { // can only check is some type of list , not specific type can't be checked for ex. List<Integer>
            	
                List<?> sublist = (List<?>) val;  // Explicit casting it to list
                // again push sublist elements in reverse order -> like push 5 , [3,4] , 2 
                for (int i = sublist.size() - 1; i >= 0; i--) {
                    stack.push(sublist.get(i));
                }
            } else {
                throw new IllegalArgumentException(  // Unchecked Exception , therefore no need of handling 
                    "Unsupported element type: " + val.getClass()
                );
//            	throw new UnsupportedNestedElementException(   // Custom Exception , can be checked or unchecked 
//            		    "Unsupported element type: " + val.getClass()
//            		);
            }
        }
    }

    @Override
    public boolean hasNext() {
        return nextIntIndex < flattened.size();
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException(); // Unchecked Exception , therefore no need of handling 
        }
        return flattened.get(nextIntIndex++);
    }

    
    // Demo
    public static void main(String[] args) { //   throws UnsupportedNestedElementException {
        List<Object> nestedList = Arrays.asList(  // [ 1 , [2 , [3 ,4 , [10,15]] , 5] , 6 , [ [7] , 8 ] ]
            1,
            Arrays.asList(2, Arrays.asList(3, 4), 5),
            6,
            Arrays.asList(Arrays.asList(7), 8)
        );

        NestedListIterator it = new NestedListIterator(nestedList);
        List<Integer> result = new ArrayList<>();
        while (it.hasNext()) {
            result.add(it.next());
        }

        System.out.println(result); // [1, 2, 3, 4, 5, 6, 7, 8]
    }
}

/*
 * 
T.C. -> O(N)

[1, [2, [3, 4], 5], 6]
Processing flow:

Push all top-level elements → total pushes = 3

When encountering [2, [3, 4], 5], push its 3 elements

When encountering [3, 4], push its 2 elements

Total pushes = total elements in structure.
*/
