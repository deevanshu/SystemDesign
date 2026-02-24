package SPLITWISE_SIMPLIFY;
import java.util.*;

class SPLITWISE_SIMPLIFY {
    
    static class Person {
        int id;
        long amount;
        Person(int id, long amount) {
            this.id = id;
            this.amount = amount;
        }
    }

    static class Transaction {
        int from, to;
        long amount;
        Transaction(int from, int to, long amount) {
            this.from = from;
            this.to = to;
            this.amount = amount;
        }
        @Override
        public String toString() {
            return "User " + from + " pays " + amount + " to User " + to;
        }
    }

    public static List<Transaction> minimizeTransactions(int[][] transactions, int n) {
        long[] balance = new long[n];

        // Step 1: Compute net balance for each user
        for (int[] t : transactions) {
            balance[t[0]] -= t[2]; // as he gave money so subtract it from current money if any
            balance[t[1]] += t[2]; // as he received money so add it from current money if any
        }

        // Step 2: Create heaps                                      // comparing like this to prev overflow
        PriorityQueue<Person> maxHeap = new PriorityQueue<>((a, b) -> Long.compare(b.amount, a.amount)); // creditors
        PriorityQueue<Person> minHeap = new PriorityQueue<>((a, b) -> Long.compare(a.amount, b.amount)); // debitors

        for (int i = 0; i < n; i++) {
            if (balance[i] > 0)
                maxHeap.add(new Person(i, balance[i]));   //  User 1 → +10
            else if (balance[i] < 0)
                minHeap.add(new Person(i, balance[i]));   //  User 0 → -5  ,  User 2 → -5
        }

        List<Transaction> result = new ArrayList<>();

        // Step 3: Settle debts greedily
        while (!minHeap.isEmpty() && !maxHeap.isEmpty()) {
            Person debtor = minHeap.poll();
            Person creditor = maxHeap.poll();

            long settled = Math.min(-debtor.amount, creditor.amount); // -ve sign in debitr to make it pos nd compare actual value

            result.add(new Transaction(debtor.id, creditor.id, settled)); // record txn with values 

            debtor.amount += settled; // as settled is +ve & debitr amt is already -ve , so it wud subtract nd give resultant 
            creditor.amount -= settled; // simply subtract the amt settled/received

            if (debtor.amount < 0) // if still amt left then add back to the heap and settled in next txn maybe
                minHeap.add(debtor);
            if (creditor.amount > 0)
                maxHeap.add(creditor);
        }

        return result;
    }

    public static void main(String[] args) {
        int[][] transactions = {
            {0, 1, 10}, // 0 -> gave 1 , 10 rs
            {2, 0, 5}  //  2 -> gave 0 , 5  rs
        };

        List<Transaction> result = minimizeTransactions(transactions, 3);
        for (Transaction t : result) {
            System.out.println(t);
        }
    }
}
