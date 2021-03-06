/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritmo;

import Modelo.Beneficio;
import Modelo.Calendario;

/**
 *
 * @author usuario
 */
public class AlgoritmoGenetico {
    public int tiempoEjecucion; // EN SEGUNDOS 
    public double score; // DEFINIRSE UNA METRICA STANDARD
    public Solucion solucion;
    
    public AlgoritmoGenetico(Beneficio bono) {
        this.solucion = new Solucion(bono);
    }
    
    public Calendario ejecutar(int tamanoPoblacion, int numeroGeneraciones){
        // Iniciar contador de tiempo
        Calendario cal = solucion.Distribuir_Citas(tamanoPoblacion, numeroGeneraciones);
        // Obtener el score de la FO
        return cal;
    }
}
