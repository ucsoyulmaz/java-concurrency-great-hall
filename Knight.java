/**
 * Knight class (Thread) which is used to perform the actions of knights.
 *
 *
 * @author 
 * 		Umut Cem Soyulmaz
 * 		usoyulmaz@student.unimelb.edu.au
 *		989654
 */

public class Knight extends Thread {

	private int knightId;	//The unique number which is associated with each different knight.
	private Hall hall;		//Hall Object to be used in Knight constructor for specifying which Hall is being used.
	private Quest questAcquiredByTheKnight;	// Quest Object which is used for specifying which quest has been acquired by the knight.
	private Agenda agendaForNewQuests;	// Agenda Object to be used in the Knight constructor for new agenda.
	private Agenda agendaForCompletedQuests; // Agenda Object to be used in the Knight constructor for completed agenda.
	
	
	//Knight constructor method
	Knight(int knightId, Agenda agendaForNewQuests, Agenda agendaForCompletedQuests, Hall hall){
		this.knightId = knightId;
		this.agendaForNewQuests = agendaForNewQuests;
		this.agendaForCompletedQuests = agendaForCompletedQuests;
		this.hall = hall;
		this.questAcquiredByTheKnight = null;
	}
	
	//The run method which starts the thread with a "start" command.
	//It includes the flow of events that a Knight Thread needs to execute.
	public void run() {
		try {
			while (!isInterrupted()) {
				
				hall.enterTheHallKnight(this);
				
				sleep(Params.getMinglingTime());
				
				hall.sitOnTheTableKnight(this);
				
				if(getQuestAcquiredByTheKnight() != null) {
					hall.releaseAcquiredQuestKnight(this);
				}
				
				hall.acquireNewQuestKnight(this);
				
				hall.standUpFromTheTableKnight(this);
				
				sleep(Params.getMinglingTime());
				
				hall.leaveTheHallKnight(this);
				
				if(getQuestAcquiredByTheKnight() != null) {
					sleep(Params.getQuestingTime());
					hall.completeAcquiredQuestKnight(this);
				}
			}
				
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	//Getter method for knightId variable
	public int getKnightId() {
		return knightId;
	}
	
	//Setter method for acquiredQuest variable
	public void setQuestAcquiredByTheKnight(Quest questObj) {
		this.questAcquiredByTheKnight = questObj;
	}
	
	//Getter method for acquiredQuest variable
	public Quest getQuestAcquiredByTheKnight() {
		return questAcquiredByTheKnight;
	}
}
