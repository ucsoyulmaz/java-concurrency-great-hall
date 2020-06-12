/**
 * Hall class (Monitor) which is used to handle the general traffic of Great Hall.
 *
 * It is responsible for:
 *  - Managing the traffic between Agenda, King Arthur and Knights;
 *
 * @author 
 * 		Umut Cem Soyulmaz
 * 		usoyulmaz@student.unimelb.edu.au
 *		989654
 */

import java.util.ArrayList;

public class Hall {

	private String name;	//This variable is responsible for holding the name of hall to be used in Hall Object.
	private ArrayList<Knight> listOfKnightsInTheHall;	//The array list which holds the list of knight who entered the Great Hall.
	private ArrayList<Knight> listOfKnightsOnTheTable;	//The array list which holds the list of knight who sit on the Round Table.
	private Agenda agendaForNewQuests;	//The Agenda object be used in Hall Object for new quests.
	private Agenda agendaForCompletedQuests;	//The Agenda object be used in Hall Object for completed quests.
	private boolean isKingInside;	//The flag which indicates whether the King Arthur has entered to the Great Hall or not.
	private boolean isMeetingRunning;	//The flag which indicates whether the meeting has been started in the Great Hall or not.
	
	
	//Constructor method for Hall Object
	Hall (String name, Agenda agendaForNewQuests, Agenda agendaForCompletedQuests) {
		this.name = name;
		this.agendaForNewQuests = agendaForNewQuests;
		this.agendaForCompletedQuests = agendaForCompletedQuests;
		this.isKingInside = false;		//initialized as FALSE because king is outside initially
		this.isMeetingRunning = false;	//initialized as FALSE because the meeting has not been started initially
		this.listOfKnightsInTheHall = new ArrayList<Knight>();
		this.listOfKnightsOnTheTable = new ArrayList<Knight>();
	}
	
	//Setter method for isKingInside variable
	public synchronized void setIsKingInside(boolean isKingInside){
		this.isKingInside = isKingInside;
	}
	
	//Setter method for isMeetingRunning variable
	public synchronized void setIsMeetingRunning(boolean isMeetingRunning){
		this.isMeetingRunning = isMeetingRunning;
	}
	
	//This method is responsible for the actions which need to be executed when King Arthur enters the Great Hall.
	public synchronized void enterTheHallKing(){
		setIsKingInside(true);
		System.out.println("King Arthur enters the Great Hall.");
		notifyAll();
	}
	
	//This method is responsible for the actions which need to be executed when King Arthur leaves the Great Hall.
	public synchronized void leaveTheHallKing(){
		
		while(isKingInside == false) {
			try{
				wait();
			}
			catch( InterruptedException e) {}
		}
		
		setIsKingInside(false);
		System.out.println("King Arthur exits the Great Hall.");
		notifyAll();
	}
	
	//This method is responsible for the actions which need to be executed about starting the meeting.
	public synchronized void startMeetingKing(){
		
		while(listOfKnightsInTheHall.size() != listOfKnightsOnTheTable.size()) {
			try{
				wait();
			}
			catch( InterruptedException e) {}
		}
		
		setIsMeetingRunning(true);
		System.out.println("Meeting begins!");
		notifyAll();
	}
	
	
	//This method is responsible for the actions which need to be executed about ending the meeting.
	public synchronized void endMeetingKing(){
		
		while(listOfKnightsOnTheTable.size() != 0) {
			try{
				wait();
			}
			catch( InterruptedException e) {}
		}
		
		setIsMeetingRunning(false);
		System.out.println("Meeting ends!");
		notifyAll();
	}

	
	//This method is responsible for the actions which need to be executed when a Knight enters the Great Hall.
	public synchronized void enterTheHallKnight(Knight knight){
		
		while(isKingInside == true) {
			try{
				wait();
			}
			catch( InterruptedException e) {}
		}
		
		listOfKnightsInTheHall.add(knight);
		System.out.println("Knight " + knight.getKnightId() + " enters Great Hall.");
		notifyAll();
	}
	
	
	//This method is responsible for the actions which need to be executed when a Knight leaves the Great Hall.
	public synchronized void leaveTheHallKnight(Knight knight){
		
		while(isKingInside == true) {
			try{
				wait();
			}
			catch( InterruptedException e) {}
		}
		
		for(int i = 0; i < listOfKnightsInTheHall.size(); i++) {
			if(listOfKnightsInTheHall.get(i).getKnightId() == knight.getKnightId()) {
				listOfKnightsInTheHall.remove(i);
			}
		}
		System.out.println("Knight " + knight.getKnightId() + " exits from Great Hall.");
		System.out.println("Knight " + knight.getKnightId() + " sets off to complete Quest " + knight.getQuestAcquiredByTheKnight().getQuestId() + "!");
		notifyAll();
	}
	
	
	//This method is responsible for the actions which need to be executed when a Knight sits on the Round Table.
	public synchronized void sitOnTheTableKnight(Knight knight){
		
		listOfKnightsOnTheTable.add(knight);
		System.out.println("Knight " + knight.getKnightId() + " sits at the Round Table.");
		notifyAll();
	}
	
	
	//This method is responsible for the actions which need to be executed when a Knight releases its quest.
	public synchronized void releaseAcquiredQuestKnight(Knight knight) {
		
		while(isMeetingRunning == false) {
			try{
				wait();
			}
			catch( InterruptedException e) {}
		}
		agendaForCompletedQuests.getCompletedQuests().add(knight.getQuestAcquiredByTheKnight());
		System.out.println("Knight " + knight.getKnightId() + " releases " + knight.getQuestAcquiredByTheKnight().toString() + ".");
		notifyAll();
	}
	
	//This method is responsible for the actions which need to be executed when a Knight acquires a quest.
	public synchronized void acquireNewQuestKnight(Knight knight) {
		
		while(isMeetingRunning == false) {
			try{
				wait();
			}
			catch( InterruptedException e) {}
		}
		
		agendaForNewQuests.acquireTheAvailableQuest(knight);
		notifyAll();
	}
	
	//This method is responsible for the actions which need to be executed when a Knight stands from the Round Table.
	public synchronized void standUpFromTheTableKnight(Knight knight){
		
		while(isMeetingRunning == false) {
			try{
				wait();
			}
			catch( InterruptedException e) {}
		}
		
		for(int i = 0; i < listOfKnightsOnTheTable.size(); i++) {
			if(listOfKnightsOnTheTable.get(i).getKnightId() == knight.getKnightId()) {
				listOfKnightsOnTheTable.remove(i);
			}
		}
		System.out.println("Knight " + knight.getKnightId() + " stands from the Round Table.");
		notifyAll();
	}
	
	
	//This method is responsible for the actions which need to be executed when a Knight completes a quest.
	public synchronized void completeAcquiredQuestKnight(Knight knight) {
		agendaForCompletedQuests.completeTheAcquiredQuest(knight);
		notifyAll();
	}
}
