package PSO;

import Algoritmo.Solucion;
import Modelo.Beneficio;
import Modelo.Calendario;

public class AlgoritmoPSO {
    public int tiempoEjecucion;
    public double score;
    public Solucion solucion;
    
    public AlgoritmoPSO(Beneficio bono) {
        this.solucion = new Solucion(bono);
    }
    
    public Calendario ejecutar(){
        // Iniciar contador de tiempo
        Calendario cal = solucion.Distribuir_Citas();
        // Obtener el score de la FO
        return cal;
    }
}
