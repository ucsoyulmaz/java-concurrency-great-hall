/**
 * KingArthur class (Thread) which is used to perform the actions of King Arthur.
 *
 *
 * @author 
 * 		Umut Cem Soyulmaz
 * 		usoyulmaz@student.unimelb.edu.au
 *		989654
 */

public class KingArthur extends Thread{
	
	private Hall hall;	//The Hall Object which is used in KingArthur Object to define which Hall that the King will enter.
	
	//KingArthur constructor method
	KingArthur(Hall hall) {
		this.hall = hall;
	}
	
	//The run method which starts the thread with a "start" command.
	//It includes the flow of events that the King Arthur Thread needs to execute.
	public void run() {
		try {
			while (!isInterrupted()) {
				
				sleep(Params.getKingWaitingTime());
				
				hall.enterTheHallKing();
				
				hall.startMeetingKing();
				
				hall.endMeetingKing();
				
				hall.leaveTheHallKing();
			}
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
