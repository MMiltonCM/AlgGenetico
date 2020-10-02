/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmosexperimentacionnumerica;

import Algoritmo.AlgoritmoGenetico;
import Modelo.Padron;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diego Paredes
 */
public class AlgoritmosExperimentacionNumerica {

    /**
     * @param args the command line arguments
     */
    
    public static List<Padron> cargarListaPadrones(){
        return new ArrayList<Padron>();
    }
    
    public static void main(String[] args) {
        List<Padron> padrones = cargarListaPadrones();
        
        AlgoritmoGenetico AG = new AlgoritmoGenetico(padrones);
        AG.ejecutar();
        
        // TODO code application logic here
    }
    
}
