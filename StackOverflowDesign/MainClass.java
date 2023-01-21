package com.systemdesign.StackOverflowDesign;


public class MainClass {

}

/*
 

class User {

	int guestId;    identifies particular guest/user in particular session
	Search searchObj;   Allows guest to search based upon the search string provided

	 List<Question> getQuestions(String searchString); returns list of questns associated 

}

class Member extends User{ bocomes member if person has account

	Account account;   
	List<Badge> badges;

	 void addQuestion(Question question);
	 void addComment(Entity entity, Comment comment); runtime polymorphism as we can commnt to any questn/ans/commnt 
	 void addAnswer(Question question, Answer answer);
	 void vote(Entity entity, VoteType voteType);
	 void addTag(Question question, Tag tag);
	 void flag(Entity entity);
	 List<Badge> getBadges();

}

class Account {

	String name;
	Address address;
	int accountId;

	String userName;
	String password;
	String email;
	
	AccountStatus accountStatus;

	int reputation;   based upon no of questions we answer 
}

class Moderator extends Member {  special member with extra permissions

	 Boolean closeQuestion(Question question);
	 Boolean restoreQuestion(Question question);

}

class Admin extends Member {

	 Boolean blockMember(Member member);
	 Boolean unblockMember(Member member);

}

 enum AccountStatus {

	BLOCKED, ACTIVE, CLOSED;
}

 enum VoteType {

	UPVOTE, DOWNVOTE, CLOSEVOTE, DELETEVOTE;
}

 class Badge {

	String name;
	String description;	
}

 class Entity {  can bes question/ans/comment id  inheritance can be used here

	int entityId;        to uniquely identify question/ans
	Member creator;
	Map<VoteType, Integer> votes;   // to stores count of upvotes/downvotes
	Date createdDate;
	List<Comment> comments;

	 boolean flagEntity(Member member);
	 boolean voteEntity(VoteType voteType);
	 boolean addComment(Comment comment);

}

 class Comment extends Entity {

	String message;
}

 class Question extends Entity {

	List<EditHistory> editHistoryList;
	List<Answer> answerList;
	List<Tag> tags;
	String title;
	String description;
	QuestionStatus status;
 
	 boolean addQuestion();
	 boolean addTag(Tag tag);
}

 class Answer extends Entity {

	String answer;
	Boolean isAccepted;
	boolean addAnswer(Question question);

}

 enum QuestionStatus {

	ACTIVE, BOUNTIED, CLOSED, FLAGGED;
}

 class Tag {

	String name;
	String description;

}
 class EditHistory {

	int editHistoryId;
	Member creator;
	Date creationDate;
	Question prevQuestion;   
	Question updatedQuestion;
}

 */