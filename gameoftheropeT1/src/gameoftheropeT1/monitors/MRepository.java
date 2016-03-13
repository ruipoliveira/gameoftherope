/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.monitors;
import com.sun.javafx.binding.Logging;
import gameoftheropeT1.interfaces.*; // import all interfaces
import gameoftheropeT1.main.Constant;
import java.io.FileNotFoundException;
import java.io.*;
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
     
    public MRepository(String fName, int nrCoaches, int nrContestants){
        
        this.fName = fName; 
        this.nrCoaches = nrCoaches; 
        this.nrContestants = nrContestants; 
    }
    
   
    private void initWriting(){
        try {
            pw = new PrintWriter(log);
            
            
            StringBuilder sb = new StringBuilder("Ref ");
            StringBuilder sb2 = new StringBuilder("Sta ");
            
            for(int i = 1; i <= nrCoaches; i++){
                sb.append("Coa_").append(i);
                sb2.append(" Stat ");
                
                for (int j = 1; j<= nrContestants; j++){
                    sb.append("Cont_").append(i);
                    sb2.append("Sta SG ");
                }
            }
            
            sb.append("      Trial ");
            sb2.append(" 3 2 1 . 1 2 3 NB PS ");
            
            pw.println("Game of the Rope - Description of the internal state"); 
            pw.println(sb.toString());
            pw.println(sb2.toString());

            // WriteLine();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public synchronized void endWriting(){
        pw.println("SIMULATION ENDED!");
        pw.flush();
        pw.close();
        System.out.println("SIMULATION ENDED!");
            
    }
    
    
        
    
}
