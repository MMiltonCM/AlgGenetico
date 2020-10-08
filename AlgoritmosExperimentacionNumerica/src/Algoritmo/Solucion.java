/*
 * Clase que utiliza el algoritmo para crear el calendario de citas
 */
package Algoritmo;

import Modelo.Beneficiario;
import Modelo.Beneficio;
import Modelo.Calendario;
import java.util.Map;

public class Solucion {
    private final Beneficio bono;
    private Map<Beneficiario, Individuo> padron_algoritmo;
    
    public Solucion(Beneficio bono){
        this.bono = bono;
        // Crear individuos en base a los beneficiarios del padron
    }
    
    public Calendario Distribuir_Citas(){
        Calendario cal = new Calendario();
        
        // Crear los bloques de atencion de las agencias de la Distribuidora
        
        // Crear una poblacion con los beneficiario del padron. Asignar cita aleatoria
        
        // Empezar Bucle: Mientras no se llegue al limite de generaciones 
        
            // Escoger individuos a cruzar

            // Cruzar individuos en la poblacion para crear la descendencia
            
            // Evaluar la descendencia con la funcion objetivo
            
            // Escoger los mejores individuos sin repetir beneficiarios
        
        return cal;
    }
    
}
