
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
    
    private boolean doneRef;
    private boolean doneCoachA;
    private boolean doneCoachB;
    private boolean doneContTeamA;
    private boolean doneContTeamB;
    private boolean novoJogo;
    private boolean novoTrial;
    private boolean novaFormacao;
    private boolean upStrengthTeamA;
    private boolean upStrengthTeamB;
    private boolean upPosPull;
    private boolean readyA; // equipa A pronta
    private boolean readyB; // equipa B pronta
    
    
    /********************** REFEREE ********************/
    private int trialNumber;
    private int game;
    //********************* ESTADOS *********************
    //private ECoachesState coachState;
    //private EContestantsState contState;
    private ERefereeState refState;
    private Map<Integer, ECoachesState> coachState;
    private Map<Integer, EContestantsState> contState;

    /********************** BENCH ************************/
    private Map<Integer, List<Integer>> coachAndTeamBench;
    private Map<Integer, List<Integer>> coachAndTeamPull;
    private List<Integer> playersInPullTeamA; // equipa A na corda
    private List<Integer> playersInPullTeamB;  // equipa B na corda
    
    
    /********************* PLAYGROUND ******************/
    private int positionPull;
    private int forca;
   
    private Map<Integer, Integer> strengthTeam; // forca dos jogadores 
    
    
    private int vezes;
    /**
     * @param fName
     * @param nrCoaches
     * @param nrContestants 
     */
    public MRepository(String fName, int nrCoaches, int nrContestants){
       
        this.fName = fName; 
        this.nrCoaches = nrCoaches; 
        this.nrContestants = nrContestants;
        this.log = new File(fName);
        
        /********** BENCH ********/
        trialNumber = 0;
        game = 1;
        coachAndTeamBench = new HashMap<>();
        coachAndTeamPull = new HashMap<>();
        doneCoachA = false;
        doneCoachB = false;
     
               
        for (int i =1; i<= 2; i++){
            coachAndTeamBench.put(i, new ArrayList<Integer>(5));
            coachAndTeamPull.put(i, new ArrayList<Integer>(3));
        }
        
        
        /********** PLAYGROUND **********/
        positionPull = 0;
        doneRef = false;
        doneContTeamA = false;
        doneContTeamB = false;
        novaFormacao = false;
        upStrengthTeamA = false;
        upStrengthTeamB = false;
        upPosPull = false;
        readyA = false; // equipas prontas
        readyB = false;
        
        playersInPullTeamA = new ArrayList<Integer>(3);
        playersInPullTeamB = new ArrayList<Integer>(3);
        
        strengthTeam = new HashMap<>();
        coachState = new HashMap<>();
        contState = new HashMap<>();
        
        
        /**** inicializacoes dos respectivos estadps dos threads ****/
        refState = ERefereeState.START_OF_THE_MATCH; 
  
        for(int t = 1; t <= nrCoaches; t++){
            coachState.put(t, ECoachesState.WAIT_FOR_REFEREE_COMMAND);  
            
            for(int c = 0; c < nrContestants; c++){
                contState.put(c, EContestantsState.SEAT_AT_THE_BENCH);
                forca = 10;
                strengthTeam.put(c, forca);  // para cada jogador a forca e inicializada a zero
            }
        }
        
        
        
        /********* SITE ********************/
        novoJogo = false;
        novoTrial = false;
        
        
        vezes = 0;
        /*** begin of the process **/      
        initWriting();
        //endWriting();
    }
    
   
    private void initWriting(){
        try {
            pw = new PrintWriter(log);
            
            
            StringBuilder sb = new StringBuilder("Ref");
            StringBuilder sb2 = new StringBuilder("Sta    ");
            
            for(int i = 1; i <= nrCoaches; i++){
                sb.append("    Coa ").append(i);
                sb2.append("  Stat ");
                
                for (int j = 1; j<= nrContestants; j++){
                    sb.append(" Cont ").append(j);
                    sb2.append("Sta SG ");
                }
            }
            
            sb.append("              Trial ");
            sb2.append("    3 2 1 . 1 2 3 NB PS ");
            
            pw.println("            Game of the Rope - Description of the internal state"); 
            pw.println(sb.toString());
            pw.println(sb2.toString());
            
            
            
            writeLine();
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    
    public synchronized void writeLine(){ 
                // ESTADO INICIAL
        pw.printf("%3s ", refState.getAcronym()); 
       
          for(int i = 1; i <= nrCoaches; i++){
                pw.printf(" %4s ", coachState.get(i).getAcronym());
                
                
                
                for (int j = 0; j< nrContestants; j++){
                    pw.printf(" %3s %2d", contState.get(j).getAcronym(), strengthTeam.get(j));
                    
                    // aqui era para actualizar para o jogador da equipa A
                    /*if(upStrengthTeamA == true && i == 1){
                        pw.printf(" %3s %2d", contState.get(j).getAcronym(), strengthTeam.get(i));
                        upStrengthTeamA = false;
                    }*/
                }
          }
          
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
        
        pw.println();   
        //escreverProcessoTodo();
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
    public synchronized void endWriting(){
        pw.println();
        pw.println("SIMULATION ENDED!");
        pw.flush();
        pw.close();
        System.out.println("SIMULATION ENDED!");
            
    }
    
    @Override
    public synchronized void updateRefState(ERefereeState state)
    {   
        this.refState = state; // faz apenas update do estado arbitro aqui e diferente porque so ha 1 arbitro
        doneRef = true;
        writeLine();
    }
    
    @Override
    public synchronized void updateCoachState(int idCoach, ECoachesState state)
    {
        coachState.put(idCoach, state); // update do estado do treinador
        if(idCoach == 1)
            doneCoachA = true;
        
        else if(idCoach == 2)
            doneCoachB = true;
        
        writeLine();
    }
    
    @Override
    public synchronized void updateTeamAContestState(int idContest, EContestantsState state)
    {
        contState.put(idContest, state); // update do estado do jogador
        doneContTeamA = true;
        writeLine();
    }
    
    @Override
    public synchronized void updateTeamBContestState(int idContest, EContestantsState state)
    {
        contState.put(idContest, state); // update do estado do jogador
        doneContTeamB = true;
        writeLine();
    }
    
    @Override
    public synchronized void updateStrengthTeamA(int contestId, int contestStrength){ //  update da forca dos jogadores
        strengthTeam.put(contestId, contestStrength);
        upStrengthTeamA = true;
        writeLine();
    }
    
    @Override
    public synchronized void updateStrengthTeamB(int contestId, int contestStrength){ //  update da forca dos jogadores
        strengthTeam.put(contestId, contestStrength);
        upStrengthTeamB = true;
        writeLine();
    }
    
    
    // actualizacao da posicao da corda -> IrefereeRepository
    @Override
    public synchronized void updatePullPosition(int positionPull){
        this.positionPull = positionPull;
        upPosPull = true;
        writeLine();
    }
    
    // actualizacao do trial
    @Override
    public synchronized void updateTrialNumber(int trialNumber){
        this.trialNumber = trialNumber;
        novoTrial = true;
        writeLine();
    }
    
    // actualizacao do numero do jogo
    @Override
    public synchronized void updateGameNumber(int game){
        this.game = game;
        novoJogo = true;
        writeLine();
    }
    
    
    @Override
    public synchronized void contestantsInPullTeamA(int coachId, int contestId){
        playersInPullTeamA.add(contestId);
        coachAndTeamPull.put(coachId, playersInPullTeamA);
        readyA = true;
        writeLine();
        
    } 
    
    @Override
    public synchronized void contestantsInPullTeamB(int coachId, int contestId){
        playersInPullTeamB.add(contestId);
        coachAndTeamPull.put(coachId, playersInPullTeamB);
        readyB = true;
        writeLine();
    }
}
