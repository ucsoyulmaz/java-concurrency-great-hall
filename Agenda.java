/**
 * Agenda class (Monitor) which is used to handle the quests traffic.
 *
 * It is responsible for:
 *  - managing the new quests which have not been acquired by any knight;
 *  - managing the completed quests;
 *
 * @author 
 * 		Umut Cem Soyulmaz
 * 		usoyulmaz@student.unimelb.edu.au
 *		989654
 */

import java.util.Queue;
import java.util.LinkedList; 

public class Agenda {

	private String name; 	// The variable which holds the agenda name to be used in Agenda Object
	private Queue<Quest> newQuests;	// The queue variable which holds the new quests --> its size will be set to 1
	private Queue<Quest> completedQuests;  // The queue variable which holds the completed quests
	
	private final static int MAX_NUMBER_OF_NEW_QUESTS = 1;  //the limitation for new quests
	
	//Agenda constructor method
	Agenda(String name) {
		this.name = name;
		this.completedQuests = new LinkedList<>(); 
		this.newQuests = new LinkedList<>(); 
	}
	
	
	//This method is responsible for adding new quests to the agenda which holds the new quests.
	public synchronized void addNew(Quest newQuest) {
		
		//set its max size limit as 1
		while(newQuests.size() == MAX_NUMBER_OF_NEW_QUESTS) {
			try{
				wait();
			}
			catch( InterruptedException e) {}
		}
		
		System.out.println(newQuest.toString() + " added to New Agenda.");
		newQuests.add(newQuest);
		notifyAll();
	}
	
	//This method is responsible for the actions which need to be executed just after releasing a quest.
		public synchronized void removeComplete() {
			
			if(completedQuests.isEmpty() == false) {
				System.out.println(completedQuests.remove().toString() 
						+ " removed from Complete Agenda.");
			}
			
			notifyAll();
		}
	
	
	//This method is responsible for the actions which need to be executed just after acquiring a quest.
	public synchronized void acquireTheAvailableQuest(Knight knightObj) {
		
		while(newQuests.isEmpty()) {
			try{
				wait();
			}
			catch( InterruptedException e) {}
		}
		
		knightObj.setQuestAcquiredByTheKnight(newQuests.peek());
		System.out.println("Knight " + knightObj.getKnightId() +" acquires " 
				+ newQuests.peek().toString() + ".");
		newQuests.remove();
		notifyAll();
	}
	
	
	
	//This method is responsible for the actions which need to be executed just after completing a quest.
	public synchronized void completeTheAcquiredQuest(Knight knightObj) {
		System.out.println("Knight " + knightObj.getKnightId() + " completes " 
				+ knightObj.getQuestAcquiredByTheKnight().toString() + ".");
		notifyAll();
	}
	
	
	//Since completedQuests variable is private, this methods is responsible for returning its value as public.
		public Queue<Quest> getCompletedQuests(){
			return completedQuests;
		}
		
	//Since newQuests variable is private, this methods is responsible for returning its value as public.
		public Queue<Quest> getNewQuests(){
			return newQuests;
		}
}
