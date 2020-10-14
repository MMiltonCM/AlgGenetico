/*
 * Parte principal del algoritmo genetico
 */
package Algoritmo;

import Modelo.Beneficiario;
import Modelo.BloqueHorario;
import Modelo.Calendario;
import Modelo.Cita;
import Modelo.LocalAtencion;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Individuo {
    private static final int CANTIDAD_CROMOSOMAS_DEFINEN_OBJETO = 3;
    private double fitness;
    private List<Gen> cromosoma;
    
    public Individuo(){
        fitness = 0.0;
        cromosoma = new ArrayList<Gen>();
    }

    /**
     * @return the fitness
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * @param fitness the fitness to set
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    /**
     * @return the cromosoma
     */
    public List<Gen> getCromosoma() {
        return cromosoma;
    }

    /**
     * @param Cromosoma the Cromosoma to set
     */
    public void setCromosoma(List<Gen> cromosoma) {
        this.cromosoma = cromosoma;
    }
    
    public void agregarGenACromosoma(Gen genNuevo){
        cromosoma.add(genNuevo);
    }
    // Metodo para cruzarse
    
    public List<Individuo> realizarCruzamientoOnePoint(List<Gen> cromosomaPareja){
        
        int numGenes = cromosoma.size();
        
        Random random = new Random();
        
        int posicionCruce = random.nextInt(numGenes);
        
        List<Gen> nuevoCromosoma1Parte1 = cromosoma.subList(0, posicionCruce);
        List<Gen> nuevoCromosoma1Parte2 = cromosoma.subList(posicionCruce, numGenes);
        
        List<Gen> nuevoCromosoma1 = new ArrayList<Gen>(nuevoCromosoma1Parte1);
        nuevoCromosoma1.addAll(nuevoCromosoma1Parte2);
        
        List<Gen> nuevoCromosoma2Parte1 = cromosoma.subList(0, posicionCruce);
        List<Gen> nuevoCromosoma2Parte2 = cromosoma.subList(posicionCruce, numGenes);
        
        List<Gen> nuevoCromosoma2 = new ArrayList<Gen>(nuevoCromosoma2Parte1);
        nuevoCromosoma2.addAll(nuevoCromosoma2Parte2);
        
        Individuo nuevoIndividuo1  = new Individuo();
        nuevoIndividuo1.setCromosoma(nuevoCromosoma1);
        
        Individuo nuevoIndividuo2  = new Individuo();
        nuevoIndividuo2.setCromosoma(nuevoCromosoma2);
        
        List<Individuo> individuosResultantesCruzamiento = new ArrayList<Individuo>();
        individuosResultantesCruzamiento.add(nuevoIndividuo1);
        individuosResultantesCruzamiento.add(nuevoIndividuo2);
        
        return individuosResultantesCruzamiento;
        
    }
    
    private Calendario construirCalendarioDesdeCromosoma(){
        Calendario calendarioConstruido = new Calendario();
        
        List<Cita> citasCromosoma = new ArrayList<Cita>();
        int numCitas = cromosoma.size() / CANTIDAD_CROMOSOMAS_DEFINEN_OBJETO;
        
        for (int i = 0; i < numCitas; i++){
        
            int indicePropiedadCita = i * CANTIDAD_CROMOSOMAS_DEFINEN_OBJETO;
            
            Cita citaCromosoma = new Cita();
            
            citaCromosoma.beneficiario = new Beneficiario();
            
            citaCromosoma.beneficiario.setIdBeneficiario(cromosoma.get(indicePropiedadCita).valor);
            
            indicePropiedadCita++;
            
            citaCromosoma.horario = new BloqueHorario();
            
            citaCromosoma.horario.local = new LocalAtencion();
            
            citaCromosoma.horario.local.setIdLocalAtencion(cromosoma.get(indicePropiedadCita).valor);
            
            indicePropiedadCita++;
            
            citaCromosoma.horario.setIdBloqueHorario(cromosoma.get(indicePropiedadCita).valor);
            
            citasCromosoma.add(citaCromosoma);
            
        }
        
        calendarioConstruido.setCitas(citasCromosoma);
        
        return calendarioConstruido;
    }
    
    public void evaluarIndividuo(List<LocalAtencion> localesDeAtencionDisponibles, List<Beneficiario> beneficiariosAtender){
        Calendario calendarioConstruido = construirCalendarioDesdeCromosoma();
        
        fitness = calendarioConstruido.evaluarCalendario(localesDeAtencionDisponibles, beneficiariosAtender);
    }
}
