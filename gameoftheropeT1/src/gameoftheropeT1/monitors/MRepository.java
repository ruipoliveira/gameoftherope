
package gameoftheropeT1.monitors;
import gameoftheropeT1.interfaces.*;
import gameoftheropeT1.state.*;
import java.io.FileNotFoundException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */
public class MRepository implements IContestantsRepository, IRefereeRepository, ICoachRepository{
        
    private String fName;
    private int nrCoaches; 
    private int nrContestants; 
    private PrintWriter pw;
    private File log;
    
    private Map<Integer, List<Player>> lst_player;
    private List<Coach> lst_coach = new ArrayList<Coach>(2);
    private Refere ref;
    
    private int strength;
    private boolean fistLine;
    
    private Map<Integer, List<Integer>> playerInPull;  
    
    private int nrGame; 
    private int nrTrial; 
    private int posPull; 

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
        
        List<Integer> lst = new ArrayList<Integer>();
        for (int i =0; i<3; i++){
            lst.add(0); 
        }
        
        playerInPull = new HashMap<>(); 

        playerInPull.put(1, lst); 
        playerInPull.put(2, lst); 
        
        nrTrial = 0; 
        posPull =0; 
        pw = new PrintWriter(log);
        initWriting();
    }
    
    
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
        
    
    public void writeLine(){ 

        pw.printf("%3s ",ref.getState().getAcronym()); 

        for(int i = 0; i < nrCoaches; i++){
            pw.printf(" %4s ",lst_coach.get(i).getState().getAcronym());

            for (int j = 0; j< nrContestants; j++){
                pw.printf("%3s %2d ", lst_player.get(i+1).get(j).getState().getAcronym() , lst_player.get(i+1).get(j).getStrength()) ;
            }
        }

        for (int j = 1; j<3; j++ ){
            for(int i =0; i<3; i++){
                if (playerInPull.get(j).get(i) == 0)
                    pw.printf("- "); 
                else 
                    pw.printf("%1d ",playerInPull.get(j).get(i));
            }
            if (j==1)
               pw.printf(". "); 
        }

        pw.printf(" %1d ", nrTrial);
        pw.printf(" %1d ", posPull); 
        
        pw.println();

    }

    public void endWriting(){
        pw.println();
        pw.println("SIMULATION ENDED!");
        pw.println();
        pw.println("Legend: \nRef Sta    – state of the referee \nCoa # Stat - state of the coach of team # (# - 1 .. 2) \nCont # Sta – state of the contestant # (# - 1 .. 5) of team whose coach was listed to the immediate left\nCont # SG  – strength of the contestant # (# - 1 .. 5) of team whose coach was listed to the immediate left\nTRIAL – ?  – contestant identification at the position ? at the end of the rope for present trial (? - 1 .. 3)\nTRIAL – NB – trial number\nTRIAL – PS – position of the centre of the rope at the beginning of the trial\n");
        pw.println("@authors Gabriel Vieira (68021) gabriel.vieira@ua.pt / Rui Oliveira (68779) ruipedrooliveira@ua.pt");
        pw.flush();
        pw.close();
            
    }
    
    
    public void writeLineGame(){
        pw.println("Game "+nrGame);
        initWriting();
    }
    
    @Override
    public synchronized void updateRefState(ERefereeState state){
        ref.setState(state);
        writeLine();
    }
    
    @Override
    public synchronized void updateCoachState(int idCoach, ECoachesState state){
        lst_coach.get(idCoach-1).setState(state);  
        writeLine();
    }
    
    @Override
    public synchronized void updateContestantState(int idTeam, int idContest, EContestantsState state){
        lst_player.get(idTeam).get(idContest-1).setState(state); 
        writeLine();
    }

    
    @Override
    public synchronized void updateStrength(int idTeam, int idContest, int contestStrength){ 
        lst_player.get(idTeam).get(idContest-1).setStrength(contestStrength); 
    }
    
    @Override
    public synchronized void updateStrengthAndWrite(int idTeam,int contestId, int contestStrength){ 
        lst_player.get(idTeam).get(contestId-1).setStrength(contestStrength); 
        writeLine();
    }
    
    
    @Override
    public synchronized void updatePullPosition(int posPull){
        this.posPull = posPull;
        writeLine();
    }
    
    
    @Override
    public synchronized void updateTrialNumber(int nrTrial){
        this.nrTrial = nrTrial;
        writeLine();
    }
    

    @Override
    public synchronized void updateGameNumber(int nrGame){
        this.nrGame = nrGame;
        nrTrial =0;
        posPull = 0; 
        writeLineGame();
        writeLine();
    }
    
    
    @Override
    public synchronized void isKnockOut(int nrGame, int nrTrial, String team ){
        pw.println("Game "+nrGame+" was won by team "+team+" by knock out in "+nrTrial+" trials.");
    }
    
    
    public synchronized  void isEnd(int nrGame, String team){
        pw.println("Game "+nrGame+" was won by team "+team+" by points.");
    }
    

    public synchronized  void wasADraw(int nrGame){
        pw.println("Game "+nrGame+" was a draw");
    }       
            
    public synchronized void endMatch(String team, int resultA, int resultB){
        
        if(resultA == resultB){
            pw.println("Match was a draw.");
        }
        else{
            pw.println("Match was won by team "+team+" ("+resultA+"-"+resultB+").");
        } 
        
        endWriting(); 
    }
    
    
    @Override
    public synchronized void addContestantsInPull(int idTeam, List<Integer>  inPull){
        playerInPull.get(idTeam).clear();
        playerInPull.get(idTeam).addAll(inPull); 
        writeLine();
    } 
    
    @Override
    public synchronized void removeContestantsInPull(int idTeam){
        List<Integer> lst = new ArrayList<Integer>();
        for (int i =0; i<3; i++){
            lst.add(0); 
        }

        playerInPull.get(idTeam).clear();
        playerInPull.get(idTeam).addAll(lst); 
        
        writeLine();
    }
    
    
    
    
}
class Player{
    private int idTeam; 
    private int idPlayer;
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
    private int idTeam; 
    private int idPlayer;
    private int strength;
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
}