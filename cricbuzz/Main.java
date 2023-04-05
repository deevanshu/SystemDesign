package com.datastructure.cricbuzz;

public class Main {


//  2 teams ---> Match | --> Innings 1  --> 50 overs --> Team A batt , Team B bowl  --> Players Info. --> Maintain Scorecard 
//	                   | --> Innings 2  --> 50 overs --> Team B bowl , Team A batt
	
	
//	OBJECTS -->    Team  ; Match ;Innings ; Overs ; Bowl ; Players ; Scorecard 
	

// *********************************
//	public class  MATCH {

//    Team teamA;
//    Team teamB;
//    Date matchDate;
//    String venue;
//    Team tossWinner;
//    InningDetails[] innings;
//    MatchType matchType;	
	
//	public void startMatch()
// **************************************

//  	public class Team {
	
//	    public String teamName;
//	    public Queue<PlayerDetails> playing11;
//	    public List<PlayerDetails> bench;
//	    public PlayerBattingController battingController;
//	    public PlayerBowlingController bowlingController;
//	    public boolean isWinner;	    
// *********************************************	
	
//	public class PlayerDetails {
//
//	    public Person person;
//	    public PlayerType playerType;
//	    public BattingScoreCard battingScoreCard;
//	    public BowlingScoreCard bowlingScoreCard;	
//  **********************************************
	
// public class Person {
//    public String name;
//    public int age;
//    public String address;
// **********************************************

//	public enum PlayerType {
//
//	    BATSMAN,
//	    BOWLER,
//	    WICKETKEEPER,
//	    CAPTAIN,
//	    ALLROUNDER;
//	}
// *************************************************
	
//	public class BattingScoreCard {
//	    public int totalRuns;
//	    public int totalBallsPlayed;
//	    public int totalFours;
//	    public int totalSix;
//	    public double strikeRate;
//	    public Wicket wicketDetails;
//	}
// *************************************************
	
//	public class BowlingScoreCard {
//	    public int totalOversCount;
//	    public int runsGiven;
//	    public int wicketsTaken;
//	    public int noBallCount;
//	    public int wideBallCount;
//	    public double economyRate;
//	}
// ************************************************
	
// public class PlayerBattingController {

//    Queue<PlayerDetails> yetToPlay;
//    PlayerDetails striker;
//    PlayerDetails nonStriker;	
//  }
// ************************************************
	
//	public class PlayerBowlingController {
//	    Deque<PlayerDetails> bowlersList;    // As Pick player from front & add at the back after it's over done for remain overs
//	    Map<PlayerDetails, Integer> bowlerVsOverCount;
//	    PlayerDetails currentBowler;
//	}
	
// ************************************************	

//	public interface MatchType {    // Can be Implemented by classes Oneday , T20 , Tests 
//	    public int noOfOvers();
//	    public int maxOverCountBowlers();
//	}
	
// ****************************************************	

//	public class InningDetails {
//	    Team battingTeam;
//	    Team bowlingTeam;
//	    MatchType matchType;
//	    List<OverDetails> overs;
//	public void startInnigs(int runsToWin){
//	}
	
// *******************************************************	
	
//	public class OverDetails {
//
//	    int overNumber;
//	    List<BallDetails> balls;
//	    int extraBallsCount;
//	    PlayerDetails bowledBy;	
//}
	
// *******************************************************
	
//	public class BallDetails {
//
//	    public int ballNumber;             // 1st , 2nd ball of the over etc.
//	    public BallType ballType;          // white , red
//	    public RunType runType;           //  6 ,4  ,1 
//	    public PlayerDetails playedBy;   // which player playing the ball
//	    public PlayerDetails bowledBy;  // which bowler is bowling the ball
//	    public Wicket wicket;
	
//  	public startBallDelivery()
	

	
//    List <ScoreUpdaterObserver> observerList = new ArrayList<>();
	
	
//    public void notifySubscribers() {
//
//	    for(NotificationObserver observer : observerList) {
//
//		 observer.update();
//	    }
//	  }
//	}
	
// ********************************************************
	
//	public enum BallType {
//
//	    NORMAL,
//	    WIDEBALL,
//	    NOBALL;
//	}	
	
// **********************************************************************************************
	
//	public enum RunType {
//
//	    ZERO,
//	    ONE,
//	    TWO,
//	    THREE,
//	    FOUR,
//	    SIX;
//	}	
	
// ************************************************************************************************
	
// OBSERVER DESIGN PATTERN CAN BE USED HERE FOR BALL BY BALL UPDATE	 :-
	
//	public interface ScoreUpdaterObserver {
//
//	    public void update(BallDetails ballDetails);
//	}
	
	
//	public class BattingScoreUpdater implements ScoreUpdaterObserver {
//	    @Override
//	    public void update(BallDetails ballDetails) {
//	        int run = 0;
//	        if (RunType.ONE == ballDetails.runType) {
//	            run = 1;
//	        } else if (RunType.TWO == ballDetails.runType) {
//	            run = 2;
//	        } else if (RunType.FOUR == ballDetails.runType) {
//	            run = 4;
//	            ballDetails.playedBy.battingScoreCard.totalFours++;
//	        } else if (RunType.SIX == ballDetails.runType) {
//	            run = 6;
//	            ballDetails.playedBy.battingScoreCard.totalSix++;
//	        }
//	        ballDetails.playedBy.battingScoreCard.totalRuns += run;
//
//	        ballDetails.playedBy.battingScoreCard.totalBallsPlayed++;
//
//	        if (ballDetails.wicket != null) {
//	            ballDetails.playedBy.battingScoreCard.wicketDetails = ballDetails.wicket;
//	        }
//	    }
//	}
	
	
//public class BowlingScoreUpdater implements ScoreUpdaterObserver {
//    @Override
//    public void update(BallDetails ballDetails) {
//
//        if (ballDetails.ballNumber == 6 && ballDetails.ballType == BallType.NORMAL) {
//            ballDetails.bowledBy.bowlingScoreCard.totalOversCount++;
//        }
//
//        if (RunType.ONE == ballDetails.runType) {
//            ballDetails.bowledBy.bowlingScoreCard.runsGiven += 1;
//        } else if (RunType.TWO == ballDetails.runType) {
//            ballDetails.bowledBy.bowlingScoreCard.runsGiven += 2;
//        } else if (RunType.FOUR == ballDetails.runType) {
//            ballDetails.bowledBy.bowlingScoreCard.runsGiven += 4;
//        } else if (RunType.SIX == ballDetails.runType) {
//            ballDetails.bowledBy.bowlingScoreCard.runsGiven += 6;
//        }
//
//        if (ballDetails.wicket != null) {
//            ballDetails.bowledBy.bowlingScoreCard.wicketsTaken++;
//        }
//
//        if (ballDetails.ballType == BallType.NOBALL) {
//            ballDetails.bowledBy.bowlingScoreCard.noBallCount++;
//        }
//
//        if (ballDetails.ballType == BallType.WIDEBALL) {
//            ballDetails.bowledBy.bowlingScoreCard.wideBallCount++;
//        }
//    }
//}
	
// *************************************************************************************************************
}
