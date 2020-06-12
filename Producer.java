/**
 * Produces new quests for the knights to complete.
 *
 * @author ngeard@unimelb.edu.au
 *
 *	(Just in case)  
 *	Umut Cem Soyulmaz - 989654
 */

public class Producer extends Thread {

	private Agenda agenda;
	
    // create a new producer
    Producer(Agenda newAgenda) {
        this.agenda = newAgenda;
    }

    // quests 
    public void run() {
        while(!isInterrupted()) {
            try {
                // create a new quest and send it to the agenda.
                Quest quest = Quest.getNewQuest();
                agenda.addNew(quest);

                // let some time pass before the next quest arrives
                sleep(Params.QUEST_ADDITION_TIME);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}