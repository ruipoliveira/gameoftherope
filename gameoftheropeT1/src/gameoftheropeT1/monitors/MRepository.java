
package gameoftheropeT1.monitors;
import com.sun.javafx.binding.Logging;
import gameoftheropeT1.interfaces.*; // import all interfaces
import gameoftheropeT1.state.*;
import java.io.FileNotFoundException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roliveira
 */
public class MRepository implements IContestantsRepository, IRefereeRepository, ICoachRepository{
        
    private String fName;
    private int nrCoaches; 
    private int nrContestants; 
    private PrintWriter pw;
    private File log;
    
    private List<Player> lst_player = new ArrayList<Player>(); 
    
    private List<Coach> lst_coach = new ArrayList<Coach>();
    
    private Refere ref;
    
    private int strength;
    
    private boolean fistLine;
    
    
    private Map<Integer, List<Integer>> playerInPull;  
    
    private int cont; 
    private int nrGame; 
    private int nrTrial; 
    private int posPull; 
   

    public MRepository(String fName, int nrCoaches, int nrContestants) throws FileNotFoundException{
   
        this.fName = fName;
        log = new File(fName);
        this.nrCoaches = nrCoaches; 
        this.nrContestants = nrContestants;

        strength = 10; 
        
        ref = new Refere(ERefereeState.START_OF_A_GAME); 

        for (int i =1; i<=nrCoaches; i++){
            lst_coach.add(new Coach(i, ECoachesState.WAIT_FOR_REFEREE_COMMAND)); 
            for (int j = 1; j<=nrContestants; j++){
                lst_player.add( new Player(i, j, strength, EContestantsState.SEAT_AT_THE_BENCH)); 
            }
        }
 
   
        
        
        nrTrial = 0; 
        posPull =0; 
        
        
        
        pw = new PrintWriter(log);
        
        initWriting();


        
        //System.out.println(lst_coach.toString()); 
        //System.out.println(lst_player.toString()); 

        
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
                pw.printf("%3s %2d ", lst_player.get(j).getState().getAcronym() , lst_player.get(j).getStrength());
            }
        }
        
        
    
        
        
        pw.printf("   %1d ", nrTrial);
        pw.printf("   %1d ", posPull); // posicao do centro da corda   
        
        
        pw.println();

        /*
        if(readyA == true || readyB == true){
            pw.printf("     %s   .   %s",playersInPullTeamA.toString(), playersInPullTeamB.toString());
            readyA = false;
            readyB = false;
        }
  
        
        pw.printf("   %1d ", trialNumber);
        pw.printf("   %1d ", positionPull); // posicao do centro da corda   
        
        vezes++;  // variavel usada para a cena do Game conforme o logging do prof -.-
        if(game == 1 && vezes == 1){
            pw.println();
            pw.printf("Game %1d", game);
        }
        
        if(novoJogo == true){
            pw.println();
            pw.printf("Game %1d", game);
            novoJogo = false;
        }
        
        pw.println();  */ 
        //escreverProcessoTodo();*/
    }
    
    public synchronized void escreverProcessoTodo(){
        
        
        /* if(doneRef == true){
            System.out.printf("%3s ", refState.getAcronym());
            doneRef = false;
        }
         
          if(doneCoachA == true){
           pw.printf("%4s", coachState.get(1).getAcronym());
           doneCoachA = false;
        }
          /*if(doneContTeamA == true){
               pw.printf(" %3s %2s", contState.get(c).getAcronym(), strengthTeam.get(c)); 
               doneContTeamA = false;
            }
         if(doneCoachB == true){
            pw.printf("%4s", coachState.get(1).getAcronym());
            doneCoachB = false;
        }
            if(doneContTeamB == true){
               pw.printf(" %3s %2s", contState.get(c).getAcronym(), strengthTeam.get(c)); 
               doneContTeamB = false;
            }*/
        
        
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
        pw.println();
        pw.println("Game "+nrGame);
        initWriting();
        cont++; 
        if (cont == 3){
            endWriting();
        }
    }
    
    @Override
    public synchronized void updateRefState(ERefereeState state)
    {   
    //    this.refState = state; // faz apenas update do estado arbitro aqui e diferente porque so ha 1 arbitro
  //      doneRef = true;
        writeLine();
    }
    
    @Override
    public synchronized void updateCoachState(int idCoach, ECoachesState state)
    {
  /*      coachState.put(idCoach, state); // update do estado do treinador
        if(idCoach == 1)
            doneCoachA = true;
        
        else if(idCoach == 2)
            doneCoachB = true;
    */    
        writeLine();
    }
    
    @Override
    public synchronized void updateTeamAContestState(int idContest, EContestantsState state)
    {
 //       contState.put(idContest, state); // update do estado do jogador
   //     doneContTeamA = true;
        writeLine();
    }
    
    @Override
    public synchronized void updateTeamBContestState(int idContest, EContestantsState state)
    {
   //     contState.put(idContest, state); // update do estado do jogador
    //    doneContTeamB = true;
        writeLine();
    }
    
    @Override
    public synchronized void updateStrengthTeamA(int contestId, int contestStrength){ //  update da forca dos jogadores
    //    strengthTeam.put(contestId, contestStrength);
   //     upStrengthTeamA = true;
        writeLine();
    }
    
    @Override
    public synchronized void updateStrengthTeamB(int contestId, int contestStrength){ //  update da forca dos jogadores
      //  strengthTeam.put(contestId, contestStrength);
     //   upStrengthTeamB = true;
        writeLine();
    }
    
    
    // actualizacao da posicao da corda -> IrefereeRepository
    @Override
    public synchronized void updatePullPosition(int posPull){
        this.posPull = posPull;
        writeLine();
    }
    
    // actualizacao do trial
    @Override
    public synchronized void updateTrialNumber(int nrTrial){
        this.nrTrial = nrTrial;
        writeLine();
    }
    
    // actualizacao do numero do jogo
    @Override
    public synchronized void updateGameNumber(int nrGame){
        this.nrGame = nrGame;
        nrTrial =0;
        posPull = 0; 
        writeLineGame();
        writeLine();
    }
    
    
    @Override
    public synchronized void contestantsInPullTeamA(int coachId, int contestId){
       // playersInPullTeamA.add(contestId);
       // coachAndTeamPull.put(coachId, playersInPullTeamA);
       // readyA = true;
        writeLine();
        
    } 
    
    @Override
    public synchronized void contestantsInPullTeamB(int coachId, int contestId){
   //     playersInPullTeamB.add(contestId);
     //   coachAndTeamPull.put(coachId, playersInPullTeamB);
       // readyB = true;
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
    
    public void setStrength(int idTeam,int idPlayer,int strength){
        if (this.idPlayer == idPlayer && this.idTeam == idTeam)
            this.strength = strength; 
    }

    
    public int getStrength(){
        return strength;
 
    }
    
    public void setState(int idTeam,int idPlayer,EContestantsState state){
        if (this.idPlayer == idPlayer && this.idTeam == idTeam)
            this.state = state; 
    }
    
    public EContestantsState getState(){
        return state;

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
    
    public void setState(int idTeam, ECoachesState state){
        if (this.idTeam == idTeam)
            this.state = state; 
    }
    
    public ECoachesState getState(){
        return state;

    }
        
}