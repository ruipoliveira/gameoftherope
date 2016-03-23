/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.monitors;
import com.sun.javafx.binding.Logging;
import gameoftheropeT1.interfaces.*; // import all interfaces
import gameoftheropeT1.main.Constant;
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
    private boolean doneCoach;
    private boolean doneCont;
    //********************* ESTADOS *********************
    //private ECoachesState coachState;
    //private EContestantsState contState;
    private ERefereeState refState;
    private Map<Integer, ECoachesState> coachState;
    private Map<Integer, EContestantsState> contState;

    /********************** BENCH ************************/
    private Map<Integer, List<Integer>> coachAndTeamBench;
    private Map<Integer, List<Integer>> coachAndTeamPull;
    private List<Integer> playersInPull;
    private int trialNumber;
   
    
    /********************* PLAYGROUND ******************/
    private int positionPull;
    //private int teamPlayerStrength [][][];
    
    private Map<Integer, Integer> strengthTeam; // forca dos jogadores 
    
    /****************** 
     * @param fName
     * @param nrCoaches
     * @param nrContestants **********************/
    public MRepository(String fName, int nrCoaches, int nrContestants){
       
        this.fName = fName; 
        this.nrCoaches = nrCoaches; 
        this.nrContestants = nrContestants;
        this.log = new File(fName);
        
        /********** BENCH ********/
        trialNumber = 0;
        coachAndTeamBench = new HashMap<>();
        coachAndTeamPull = new HashMap<>();
     
               
        for (int i =1; i<= 2; i++){
            coachAndTeamBench.put(i, new ArrayList<Integer>(5));
            coachAndTeamPull.put(i, new ArrayList<Integer>(3));
        }
        playersInPull = new ArrayList<Integer>(3);
        
        /********** PLAYGROUND **********/
        positionPull = 0;
        doneRef = false;
        doneCoach = false;
        doneCont = false;
        
        strengthTeam = new HashMap<>();
        coachState = new HashMap<>();
        contState = new HashMap<>();
        
        
        /**** inicializacoes dos respectivos estadps dos threads ****/
        refState = ERefereeState.START_OF_THE_MATCH; 
        //coachState = ECoachesState.WAIT_FOR_REFEREE_COMMAND;
        //contState = EContestantsState.SEAT_AT_THE_BENCH;
        for(int t = 0; t < nrCoaches; t++){
            coachState.put(t, ECoachesState.WAIT_FOR_REFEREE_COMMAND);
             
        }
        
        for(int c = 0; c < nrContestants; c++){
            contState.put(c, EContestantsState.SEAT_AT_THE_BENCH);
            strengthTeam.put(c, 0);  // para cada jogador a forca e inicializada a zero
        }
        
     
        
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
                sb.append(" Coa ").append(i);
                sb2.append("  Stat ");
                
                for (int j = 1; j<= nrContestants; j++){
                    sb.append(" Cont ").append(j);
                    sb2.append("Sta SG ");
                }
            }
            
            sb.append("              Trial ");
            sb2.append(" 3 2 1 . 1 2 3 NB PS ");
            
            pw.println("Game of the Rope - Description of the internal state"); 
            pw.println(sb.toString());
            pw.println(sb2.toString());
            
            
            
            writeLine();
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    
    public synchronized void writeLine(){ 
                
        pw.printf("%3s ", refState.getAcronym()); 
        if(doneRef == true){
            System.out.printf("%3s ", refState.getAcronym());
            doneRef = false;
        }
        
        
        pw.printf("%4s", coachState.get(0).getAcronym());
        if(doneCoach == true){
            System.out.printf("%4s", coachState.get(0).getAcronym());
            doneCoach = false;
        }
        
        for(int c = 0; c < contState.size(); c++){                
            pw.printf(" %3s %2s", contState.get(c).getAcronym(), strengthTeam.get(c));
            
            if(doneCont == true){
               System.out.printf(" %3s %2s", contState.get(c).getAcronym(), strengthTeam.get(c)); 
               doneCont = false;
            }
        }
       
        pw.printf("%4s ", coachState.get(1).getAcronym());        
        if(doneCoach == true){
            System.out.printf("%4s", coachState.get(1).getAcronym());
            doneCoach = false;
        }
        
        for(int c = 0; c < contState.size(); c++){                
            pw.printf(" %3s %2s", contState.get(c).getAcronym(), strengthTeam.get(c));
            
            if(doneCont == true){
               System.out.printf(" %3s %2s", contState.get(c).getAcronym(), strengthTeam.get(c)); 
               doneCont = false;
            }
        }
        
        pw.println();
       
    }
    
    public synchronized void endWriting(){
        pw.println();
        pw.println("SIMULATION ENDED!");
        pw.flush();
        pw.close();
        System.out.println("SIMULATION ENDED!");
            
    }
    
    @Override
    public synchronized void UpdateRefState(ERefereeState state)
    {   
        this.refState = state; // faz apenas update do estado arbitro aqui e diferente porque so ha 1 arbitro
        doneRef = true;
        writeLine();
    }
    
    @Override
    public synchronized void UpdateCoachState(int idCoach, ECoachesState state)
    {
        coachState.put(idCoach, state); // update do estado do treinador
        doneCoach = true;
        writeLine();
    }
    
    @Override
    public synchronized void UpdateContestState(int idContest, EContestantsState state)
    {
        contState.put(idContest, state); // update do estado do jogador
        doneCont = true;
        writeLine();
    }
    
    public synchronized void UpdateStrength(int contestId, int contestStrength){ //  update da forca dos jogadores
        strengthTeam.put(contestId, contestStrength);
        //done = true;
        writeLine();
    }
    
    // actualizacao da posicao da corda
    public synchronized void UpdatePullPosition(int positionPull){
        this.positionPull = positionPull;
        //done = true;
        writeLine();
    }
    
    // actualizacao do trial
    public synchronized void UpdateTrialNumber(int trialNumber){
        this.trialNumber = trialNumber;
        writeLine();
    }
   

    
        
    
}
