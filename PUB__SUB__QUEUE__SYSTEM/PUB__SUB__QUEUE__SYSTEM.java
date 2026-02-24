package PUB__SUB__QUEUE__SYSTEM;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

// 📨 Represents a single message
class Message {
    private final String content;
    private final long timestamp;

    public Message(String content) {
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }
    public String getContent() {
        return content;
    }
    public long getTimestamp() {
        return timestamp;
    }
}
// 🧵 Topic = like a Kafka topic
class Topic {
    private final String name;
    private final ConcurrentHashMap<Integer, Message> messages; //   Thread-safe  for storing all messages as Multiple producers might publish at the same time  , 
    /*       messages: [
					    0: "Order #101 created",
					    1: "Order #102 shipped"
					 ]                                   */
    private final ConcurrentHashMap<String, Integer> consumerOffsets; // consumerName → index , ex: consumerOffsets = [ "Consumer-A" → 0, "Consumer-B" → 1 ]

    public Topic(String name) {
        this.name = name;
        this.messages = new ConcurrentHashMap<>();  // ConcurrentLinkedQueue can also be used it has O(1) for read & write unlike 
   //                                                     CopyOnWriteArrList which has O(n) for write as new copy everytime  & O(1) for read
        this.consumerOffsets = new ConcurrentHashMap<>();
    }
    public String getName() {
        return name;
    }
    // Add new message to topic
  /*   Multiple producers might call this method at the same time.
    * So we make it synchronized to ensure that adding a message
    * happens atomically (one producer at a time modifies list). */
    public synchronized void addMessage(Message message) {    // remove synchronized if used ConcurrentHashMap as it can handle internal synchronization
    	int index = messages.size();
    	messages.put(index , message);
        System.out.println("📩 Message added to topic [" + name + "]: " + message.getContent());
    }
    // Register a consumer
    public void registerConsumer(String consumerName) {
        consumerOffsets.putIfAbsent(consumerName, 0);
    }
    // Get next message for a consumer
    public synchronized Message getNextMessage(String consumerName) { // remove synchronized if used ConcurrentHashMap as it can handle internal synchronization
        int offset = consumerOffsets.getOrDefault(consumerName, 0);
        if (offset < messages.size()) {
            Message msg = messages.get(offset);
            consumerOffsets.put(consumerName, offset + 1);
            return msg;
        }
        return null; // no new message
    }
}

// 🧰 MessageQueue = manages multiple topics
class MessageQueue {
    private final ConcurrentHashMap<String, Topic> topics; // topicName vs Topic  map

    public MessageQueue() {
        topics = new ConcurrentHashMap<>();
    }

    public Topic createTopic(String topicName) {
        Topic topic = new Topic(topicName);
        topics.put(topicName, topic);
        System.out.println("✅ Created topic: " + topicName);
        return topic;
    }

    public Topic getTopic(String topicName) {
        return topics.get(topicName);
    }
}

// 🚀 Producer
class Producer {
    private final MessageQueue queue;

    public Producer(MessageQueue queue) {
        this.queue = queue;
    }
    public void publish(String topicName, String messageContent) {
        Topic topic = queue.getTopic(topicName);
        if (topic == null) {
            System.out.println("⚠️ Topic not found: " + topicName);
            return;
        }
        topic.addMessage(new Message(messageContent));
    }
}
// 👩‍💻 Consumer
class Consumer {
    private final String consumerName;
    private final Topic topic;

    public Consumer(String consumerName, Topic topic) {
        this.consumerName = consumerName;
        this.topic = topic;
        topic.registerConsumer(consumerName);
    }

    public void consume() {
        Message msg = topic.getNextMessage(consumerName);
        if (msg != null) {
            System.out.println("👀 " + consumerName + " consumed message from [" + topic.getName() + "]: " + msg.getContent());
        } else {
            System.out.println("ℹ️ " + consumerName + " has no new messages.");
        }
    }
}

// 🧪 Main class
public class PUB__SUB__QUEUE__SYSTEM {
    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue();

        // Create topics
        Topic topic1 = queue.createTopic("Orders");
        Topic topic2 = queue.createTopic("Payments");

        // Create producers
        Producer producer1 = new Producer(queue);
        Producer producer2 = new Producer(queue);

        // Create consumers
        Consumer consumerA = new Consumer("Consumer-A", topic1); // also puts entry of consumer in consumerOffset map of topic
        Consumer consumerB = new Consumer("Consumer-B", topic1);
        Consumer consumerC = new Consumer("Consumer-C", topic2);

        // Publish messages
        producer1.publish("Orders", "Order #101 created"); // add message to topic 
        producer2.publish("Orders", "Order #102 shipped"); // add message to topic 
        producer1.publish("Payments", "Payment received for Order #101"); // add message to topic 

        System.out.println("\n--- Consumers reading messages ---\n");

        consumerA.consume(); // should get 1st message from topic 1 
        consumerA.consume(); // should get 2nd message from topic 1 
        consumerA.consume(); // no new message in topic 1 

        consumerB.consume(); // should start from 1st message
        consumerC.consume(); // should get payment message
/*        
        Topic: Orders
        messages: [
           0: "Order #101 created",
           1: "Order #102 shipped"
        ]
        consumerOffsets: { A:0, B:0 }

        Topic: Payments
        messages: [
            0: "Payment received for Order #101"
        ]
        consumerOffsets: { C:0 }
*/
    }
}
