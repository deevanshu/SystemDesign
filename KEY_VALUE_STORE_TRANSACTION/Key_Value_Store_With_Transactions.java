package KEY_VALUE_STORE_TRANSACTION;

import java.util.*;

class Key_Value_Store_With_Transactions {
	
    // MAIN committed store
    private Map<String, String> store = new HashMap<>();
    
    // List used like a stack: last element = top transaction
    private List<Map<String, String>> transactions = new ArrayList<>();

    
    // --- BASIC OPERATIONS ---
    
    
    // Insert or update a key
    public void set(String key, String value) {
        if (!transactions.isEmpty()) {
            // Save into active transaction i.e. the latest entry from list (not main store yet)
            transactions.get(transactions.size() - 1).put(key, value); // The last element of the list is always the active transaction.
        } else {
            // No transaction active → update main store directly
            store.put(key, value);
        }
    }

    // Retrieve a key's value
    public String get(String key) {
        // Check from most recent transaction backwards i.e. this ensures we see the most recent value of a key
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Map<String, String> txn = transactions.get(i);
            if (txn.containsKey(key)) {
                return txn.get(key); // may be null if deleted
            }
        }
        // If not found in transactions, return from main store
        return store.get(key);
    }

    // Delete a key
    public void delete(String key) {
        if (!transactions.isEmpty()) {
            // Mark as deleted in active transaction so that on commit we can del from main memory seeing if it's value is null & also for rollback support
            transactions.get(transactions.size() - 1).put(key, null);
        } else {
            // Remove directly from main commited store
            store.remove(key);
        }
    }

    
    // --- TRANSACTION OPERATIONS ---
    
    // Start a new transaction
    public void begin() {
        transactions.add(new HashMap<>());
    }

    // Commit changes of the active transaction
    public void commit() {
        if (transactions.isEmpty()) {
            System.out.println("No transaction to commit.");
            return;
        }

        // Take top transaction
        Map<String, String> top = transactions.remove(transactions.size() - 1);

        if (transactions.isEmpty()) {
            // Commit directly into main store
            for (Map.Entry<String, String> entry : top.entrySet()) {
                if (entry.getValue() == null) {
                    store.remove(entry.getKey());
                } else {
                    store.put(entry.getKey(), entry.getValue());
                }
            }
        } else {
            // Merge into parent transaction (nested)
            transactions.get(transactions.size() - 1).putAll(top); // Nested transactions are committed to parent transactions, not main store,
                                                                  //  until outermost transaction commits.
        }
    }

    // Rollback the active transaction
    public void rollback() {
        if (transactions.isEmpty()) {
            System.out.println("No transaction to rollback.");
            return;
        }
        transactions.remove(transactions.size() - 1); // discard active the most recent txn
    }
    
    public static void main(String args[]) {
    	
    Key_Value_Store_With_Transactions kv = new Key_Value_Store_With_Transactions();

    kv.set("a", "10");
    System.out.println(kv.get("a")); // 10 , from the main store as no active txn

    kv.begin();  // start txn1
    kv.set("a", "20");     // set in txn list 
    System.out.println(kv.get("a")); // 20  , from the active txn list

    kv.begin();  // start txn2
    kv.delete("a");
    System.out.println(kv.get("a")); // null

    kv.rollback(); // rollback txn2
    System.out.println(kv.get("a")); // 20

    kv.commit();   // commit txn1
    System.out.println(kv.get("a")); // 20 in main store now
}

}
