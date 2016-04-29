
package ServerSide.Repository;
import ClientSide.Coach.ECoachesState;
import ClientSide.Contestant.EContestantsState;
import ClientSide.Referee.ERefereeState;
import java.io.FileNotFoundException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Communication.*;
import Communication.Message.*;
import static java.lang.Thread.sleep;
import java.util.Arrays;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public class MRepository implements IContestantsRepository, IRefereeRepository, ICoachRepository{
        
    private final String fName;
    private final int nrCoaches; 
    private final int nrContestants; 
    private final PrintWriter pw;
    private final File log;
    
    private final Map<Integer, List<Player>> lst_player;
    private List<Coach> lst_coach = new ArrayList<>(2);
    private final Refere ref;
    
    private final int strength;
    private boolean fistLine;
    
    //private final Map<Integer, List<Integer>> playerInPull;  
    
    private final List<Integer> lstInPullA; 
    private final List<Integer> lstInPullB; 

    
    private int nrGame; 
    private int nrTrial; 
    private int posPull; 
    
    /**
    * Initializes the logger (gameoftherope.log) file
    *
    * @param fName to be given to the logger that will be created
    * @param nrCoaches is the number of the coaches present in the simulation 
    * @param nrContestants is the number of the contestants in the simulation 
    * @throws FileNotFoundException is thrown when fails to write/open the file
    */
    public MRepository(String fName, int nrCoaches, int nrContestants) throws FileNotFoundException{
        
        this.fName = fName;
        log = new File(fName);
        this.nrCoaches = nrCoaches; 
        this.nrContestants = nrContestants;
        strength = 0; 
        ref = new Refere(ERefereeState.START_OF_A_GAME); 
        lst_player = new HashMap<>(3); 

        for (int i =1; i<=nrCoaches; i++){
            lst_coach.add(new Coach(i, ECoachesState.WAIT_FOR_REFEREE_COMMAND)); 
            lst_player.put(i, new ArrayList<Player>()); 
        }

        for(int j =1; j<=nrContestants; j++){
            lst_player.get(1).add(new Player(1,j,strength,EContestantsState.SEAT_AT_THE_BENCH));
            lst_player.get(2).add(new Player(2,j,strength,EContestantsState.SEAT_AT_THE_BENCH));
        }
        
        List<Integer> lst = new ArrayList<>();
        for (int i =0; i<3; i++){
            lst.add(0); 
        }
        
        lstInPullA = new ArrayList<>();
        lstInPullB = new ArrayList<>();
        
        nrTrial = 0; 
        posPull =0; 
        
        pw = new PrintWriter(log);
        initWriting();
    }
    
    /*
    * Writes the first lines of the header in the logger file created .
    */
    public void initWriting(){
                
        StringBuilder sb = new StringBuilder("Ref");
        StringBuilder sb2 = new StringBuilder("Sta ");

        for(int i = 1; i <= nrCoaches; i++){
            sb.append(" Coa ").append(i);
            sb2.append(" Stat ");

            for (int j = 1; j<= nrContestants; j++){
                sb.append(" Cont ").append(j);
                sb2.append("Sta SG ");
            }
        }

        sb.append("              Trial ");
        sb2.append("3 2 1 . 1 2 3 NB PS ");
        if (fistLine){
            pw.println("\t \t *Game of the Rope - Description of the internal state*"); 
            fistLine = false; 
        }

        pw.println(sb.toString());
        pw.println(sb2.toString());

    }
        
    /*
    *  writes a line to the logger with the simulation information updated.
    */
    public synchronized void writeLine(){ 

        pw.printf("%3s ",ref.getState().getAcronym()); 

        for(int i = 0; i < nrCoaches; i++){
            pw.printf(" %4s ",lst_coach.get(i).getState().getAcronym());

            for (int j = 0; j< nrContestants; j++){
                pw.printf("%3s %2d ", lst_player.get(i+1).get(j).getState().getAcronym() , lst_player.get(i+1).get(j).getStrength()) ;
            }
        }

        for (int j = 1; j<3; j++ ){
            for(int i =0; i<3; i++){
                if (j ==1){
                    if (!indexExists(lstInPullA,i))
                        pw.printf("- ");
                    else
                       pw.printf("%1d ",lstInPullA.get(i)); 
                }
                else {
                    if (!indexExists(lstInPullB,i))
                        pw.printf("- ");
                    else
                       pw.printf("%1d ",lstInPullB.get(i)); 
                }    
            }
            if (j==1)
               pw.printf(". "); 
        }

        pw.printf(" %1d ", nrTrial);
        pw.printf(" %1d ", posPull); 
        
        pw.println();

    }
    
    /*
    * Writes the end of a logger file
    */

    public synchronized void endWriting(){
        pw.println();
        pw.println("SIMULATION ENDED!");
        pw.println();
        pw.println("Legend: \nRef Sta    – state of the referee \nCoa # Stat - state of the coach of team # (# - 1 .. 2) \nCont # Sta – state of the contestant # (# - 1 .. 5) of team whose coach was listed to the immediate left\nCont # SG  – strength of the contestant # (# - 1 .. 5) of team whose coach was listed to the immediate left\nTRIAL – ?  – contestant identification at the position ? at the end of the rope for present trial (? - 1 .. 3)\nTRIAL – NB – trial number\nTRIAL – PS – position of the centre of the rope at the beginning of the trial\n");
        pw.println("@authors Gabriel Vieira (68021) gabriel.vieira@ua.pt / Rui Oliveira (68779) ruipedrooliveira@ua.pt");
        pw.flush();
        pw.close();
            
    }
    
    /*
    * writes the number of a game in a line
    */
    public synchronized void writeLineGame(){
        pw.println("Game "+nrGame);
        initWriting();
    }
    
    /**
    * updates the state of the referee
    *
    * @param state The Referee's current state
    */
    @Override
    public synchronized void updateRefState(ERefereeState state){
        ref.setState(state);
        writeLine();
    }
    
     /**
    * updates the state of the coach
    *
    * @param state The Coach's current state
    * @param idCoach Is the coach Identifier (ID)
    */
    @Override
    public synchronized void updateCoachState(int idCoach, ECoachesState state){
        lst_coach.get(idCoach-1).setState(state);  
        writeLine();
    }
    
    /**
     * updates the state of the contestant
     * 
    * @param state The Contestant's current state
    * @param idTeam Is the team Identifier (ID) (or coach id)
    * @param idContest Is the contestant identifier (ID)
    * @param state The Contestant's current state 
    */
    @Override
    public synchronized void updateContestantState(int idTeam, int idContest, EContestantsState state){
        lst_player.get(idTeam).get(idContest-1).setState(state); 
        writeLine();
    }

    /**
     * updates the strength of the contestants
     * 
     * @param idTeam Is the team identifier (ID) (or coachID)
     * @param idContest Is the contestant identifier (ID)
     * @param contestStrength Is the contestant's strength
     */
    @Override
    public synchronized void updateStrength(int idTeam, int idContest, int contestStrength){ 
        lst_player.get(idTeam).get(idContest-1).setStrength(contestStrength); 
    }
    
    /**
     * updates and writes the strength of the contestants
     * 
     * @param idTeam Is the team identifier (ID) (or coachID)
     * @param contestId Is the contestant identifier (ID)
     * @param contestStrength Is the contestant's strength
     */
    @Override
    public synchronized void updateStrengthAndWrite(int idTeam,int contestId, int contestStrength){ 
        lst_player.get(idTeam).get(contestId-1).setStrength(contestStrength); 
        writeLine();
    }
    
    /**
     * updates the position of the pull
     * 
     * @param posPull is the position of the pull
     */
    @Override
    public synchronized void updatePullPosition(int posPull){
        this.posPull = posPull;
        writeLine();
    }
    
    /**
     * updates the number of the trial
     * 
     * @param nrTrial is the number of the trial 
     */
    @Override
    public synchronized void updateTrialNumber(int nrTrial){
        System.out.println(nrTrial); 
        this.nrTrial = nrTrial;
        writeLine();
    }
    
    /**
     * updates the number of the game
     * 
     * @param nrGame is the number of the game
     */
    @Override
    public synchronized void updateGameNumber(int nrGame){
        this.nrGame = nrGame;
        nrTrial =0;
        posPull = 0; 
        writeLineGame();
        writeLine();
    }
    
    /**
     * Say if the game was won by knock out and who won him
     * 
     * @param nrGame is the number of the game
     * @param nrTrial
     * @param team 
     */
    @Override
    public synchronized void isKnockOut(int nrGame, int nrTrial, String team ){
        pw.println("Game "+nrGame+" was won by team "+team+" by knock out in "+nrTrial+" trials.");
    }
    
    /*
    * Say that the game ends
    */
    public synchronized  void isEnd(int nrGame, String team){
        if (posPull != 4 )
            pw.println("Game "+nrGame+" was won by team "+team+" by points.");
        else if (posPull != -4)
            pw.println("Game "+nrGame+" was won by team "+team+" by points.");
        
    }
    
    /*
    * Say that the result of the game was a draw
    */
    public synchronized  void wasADraw(int nrGame){
        pw.println("Game "+nrGame+" was a draw");
    }       
    
    /*
    * Say that the match is over
    */
    public synchronized void endMatch(String team, int resultA, int resultB){
        
        if(resultA == resultB){
            pw.println("Match was a draw.");
        }
        else{
            pw.println("Match was won by team "+team+" ("+resultA+"-"+resultB+").");
        } 
        
        endWriting(); 
    }
    
    /**
     * 
     * @param idTeam Is the team identifier (ID) (or coachID)
     * @param idPlayer Is the Contestant's identifier (ID)
     */
    @Override
    public synchronized void addContestantsInPull(int idTeam, int idPlayer){
        
        if (idTeam ==1 ){
            lstInPullA.add(idPlayer); 
        }else{
            lstInPullB.add(idPlayer); 
        }
        
        writeLine();
    } 
    
    /**
     * 
     * @param idTeam idTeam Is the team identifier (ID) (or coachID)
     * @param idPlayer idPlayer Is the Contestant's identifier (ID)
     */
    @Override
    public synchronized void removeContestantsInPull(int idTeam, int idPlayer){

        if (idTeam ==1 ){
            lstInPullA.removeIf(p -> p.equals(idPlayer));
        }else{
            lstInPullB.removeIf(p -> p.equals(idPlayer));
        }

        writeLine();
    }

    /*
    * verify if the index exists
    * return true -> index exists
    * return false, otherwise
    */
    private boolean indexExists(final List list, final int index) {
        return index >= 0 && index < list.size();
    }

     /**
     * Terminates the servers when the clients no longer need the services.
     */
    public synchronized void terminateServers() {
        Message inMessage, outMessage;
        ClientComm con = new ClientComm(CommConst.playServerName, CommConst.playServerPort);
        while (!con.open()) {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.TERMINATE);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + inMessage.toString());
            System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            System.exit(1);
        }
        con.close();
        
        con = new ClientComm(CommConst.benchServerName, CommConst.benchServerPort);
        while (!con.open()) {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.TERMINATE);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + inMessage.toString());
            System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            System.exit(1);
        }
        con.close();
        
        con = new ClientComm(CommConst.siteServerName, CommConst.siteServerPort);
        while (!con.open()) {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.TERMINATE);
        con.writeObject(outMessage);
        inMessage = (Message) con.readObject();
        if (inMessage.getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + inMessage.toString());
            System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            System.exit(1);
        }
        con.close();
    }
    
}



class Player{
    private final int idTeam; 
    private final int idPlayer;
    private int strength;
    private EContestantsState state; 
    
    public Player(int idTeam,int idPlayer,int strength, EContestantsState state){
        this.idTeam = idTeam; 
        this.idPlayer = idPlayer; 
        this.strength = strength; 
        this.state = state; 
    }
    
    public void setStrength(int strength){
            this.strength = strength; 
    }

    public int getStrength(){
        return strength;
    }
    
    public void setState(EContestantsState state){
        this.state = state; 
    }
    
    public EContestantsState getState(){
        return state;
    }
    
    public int getId(){
        return idPlayer; 
    }
        
}

class Refere{
    private ERefereeState state; 
    
    public Refere(ERefereeState state){
        this.state = state; 
    }
    
    public void setState(ERefereeState state){
        this.state = state; 
    }
    
    public ERefereeState getState(){
        return state; 
    }
}

class Coach{
    private final int idTeam; 
    private ECoachesState state; 
    
    public Coach(int idTeam,ECoachesState state){
        this.idTeam = idTeam; 
        this.state = state; 
    }
    
    public void setState(ECoachesState state){
        this.state = state; 
    }
    
    public ECoachesState getState(){
        return state;
    }  
    
    public int getId(){
        return idTeam; 
    }
}