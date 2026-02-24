package TWITTER__LLD;
import java.util.*;
/* 
 CLASSES ->
 
User : Stores user profile, tweets, followers, following list, notifications.

Tweet: Represents each post (tweet), holds author, content, timestamp, likes, and comments.

Feed : Generates and stores each user’s timeline, fetching tweets from users they follow.

Notification: Real-time alerts for likes, comments, follows, and mentions.


UML/Relationship Overview ->

User creates many Tweets

User follows and is followed by other Users (Many:Many Follow relationship)

Tweet has Likes, Comments, and Media

Feed aggregates Tweets from Followees

Notifications belong to Users


class User {
    String userId;
    String username;
    String profilePictureUrl;
    
    List<Tweet> tweets;
    List<User> followers;
    List<User> following;
   
    List<Notification> notifications;

    void follow(User user);
    void unfollow(User user);
    void postTweet(String text);
    List<Tweet> getFeed();
}

class Tweet {
    long tweetId;
    User author;
    String text;
    LocalDateTime createdAt;
    
    List<User> likes;
    List<Comment> comments;
    List<Media> media;

    void addLike(User user);
    void addComment(Comment comment);
}

class Comment {
    long commentId;
    User author;
    String text;
    LocalDateTime createdAt;
}

class Notification {
    long notificationId;
    User recipient;
    String message;
    boolean read;
    LocalDateTime createdAt;
}

class FeedService {
    Map<String, List<Tweet>> cachedFeeds;
    
    List<Tweet> generateFeed(User user, int limit);
}
 
userTweets map: ->
{
	  1: [Tweet(101), Tweet(103)]
	  2: [Tweet(102)]
	  3: [Tweet(104)]
}

followees map: ->
{
	  1: [2,3]    // User 1 follows 2 and 3
	  2: [3]     //  User 2 follows 3
}

*/  
public class twitter {

    private static int timeStamp = 0;

 /*  
    Follower: The user who follows someone. They see the followee’s posts in their feed.
    Followee: The user who is being followed. Their posts appear in the follower’s feed.    */
    
    private Map<Integer, HashSet<Integer>> followees;  // UserId -> set of followees(they follow)

    private Map<Integer, List<Tweet>> userTweets; // UserId -> list of tweets by user 

    public twitter() {
        followees  = new HashMap<>();
        userTweets = new HashMap<>();
    }
    
    // Tweet structure
    private static class Tweet {
        int tweetId;
        int time;
        String text;
        String userId;
        
        Tweet(int tweetId, int time, String text) { // For Simplicity take these 3 only in constructor
            this.tweetId = tweetId;
            this.time = time;
            this.text = text;
        }
    }

    // Post a new tweet
    public void postTweet(int userId, int tweetId) {
        userTweets.putIfAbsent(userId, new ArrayList<>());
        userTweets.get(userId).add(new Tweet(tweetId, timeStamp++,"tweet"));   // returns reference , no need to put back
    }

    // Follow
    public void follow(int followerId, int followeeId) {
        followees.putIfAbsent(followerId, new HashSet<>());
        followees.get(followerId).add(followeeId);
        /*        
        twitter.follow(1, 2); // User 1 (Alice) follows User 2 (Bob)
        twitter.follow(1, 3); // User 1 (Alice) follows User 3 (Charlie)       
        
 { 1: [2, 3]  } // Alice follows Bob and Charlie  */
      
    }

    // Unfollow
    public void unfollow(int followerId, int followeeId) {
        if (followees.containsKey(followerId)) {
            followees.get(followerId).remove(followeeId);
        }
    }

    // Get news feed for a user(most recent 10)
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> maxHeap = new PriorityQueue<>((a, b) -> b.time - a.time); // Sort tweets by desc order of time

        // Add user's own tweets
        if (userTweets.containsKey(userId)) {
            maxHeap.addAll(userTweets.get(userId));
        }

        // Add followees’ tweets
        if (followees.containsKey(userId)) { 
            for (int followee : followees.get(userId)) { // getting all the followee's of the user
                if (userTweets.containsKey(followee)) { // getting followee's tweet
                    maxHeap.addAll(userTweets.get(followee)); // adding followee's tweet
                }
            }
        }

        // Get top 10
        List<Integer> feed = new ArrayList<>();
        int count = 0;
        while (!maxHeap.isEmpty() && count < 10) {
            feed.add(maxHeap.poll().tweetId);
            count++;
        }
        return feed;
    }
    
    
    public static void main(String[] args) {
        twitter twitter = new twitter();

        // Users 1, 2, 3
        twitter.postTweet(1, 101); // User 1 posts tweet 101
        twitter.postTweet(1, 103); // User 1 posts tweet 103
        twitter.postTweet(2, 102); // User 2 posts tweet 102
        twitter.postTweet(3, 104); // User 3 posts tweet 104

        // Follow relationships
        twitter.follow(1, 2); // User 1 follows User 2
        twitter.follow(1, 3); // User 1 follows User 3
        twitter.follow(2, 3); // User 2 follows User 3

        // Print maps
        twitter.printMaps();
/* 
        userTweets map: ->
        {
        	  1: [Tweet(101), Tweet(103)]
        	  2: [Tweet(102)]
        	  3: [Tweet(104)]
        }

        followees map: ->
        {
        	  1: [2,3]  // User 1 follows 2 and 3
        	  2: [3]     // User 2 follows 3
        }

*/        

        // Get news feeds
        System.out.println("User 1 feed: " + twitter.getNewsFeed(1)); // Should see tweets from 1, 2, 3
        System.out.println("User 2 feed: " + twitter.getNewsFeed(2)); // Should see tweets from 2, 3
        System.out.println("User 3 feed: " + twitter.getNewsFeed(3)); // Only own tweets 3
/*        
        User 1 feed: [104, 103, 102, 101] (most recent first)
        User 2 feed: [104, 102]
        User 3 feed: [104]             */
    }
    
    public void printMaps() {
        System.out.println("User Tweets Map:");
        for (var entry : userTweets.entrySet()) {
            System.out.println("User " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Followees Map:");
        for (var entry : followees.entrySet()) {
            System.out.println("Follower " + entry.getKey() + " follows: " + entry.getValue());
        }
    }
}

