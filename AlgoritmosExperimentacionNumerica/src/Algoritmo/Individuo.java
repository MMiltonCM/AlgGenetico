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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Individuo implements Comparable {
    private static final int CANTIDAD_CROMOSOMAS_DEFINEN_OBJETO = 2;
    private double fitness;
    private List<Gen> cromosoma;
    
    public Individuo(){
        fitness = Double.MIN_VALUE;
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
    
    public List<Individuo> realizarCruzamientoMultiPoint(List<Gen> cromosomaPareja, int numPuntosCorte){
    
        int numGenes = cromosoma.size();
        
        Random random = new Random();
        
        List<Integer> cortes = random.ints(0, numGenes).distinct().limit(numPuntosCorte).boxed().collect(Collectors.toCollection(ArrayList::new));
        Collections.sort(cortes);
        
        List<Gen> nuevoCromosoma1Parte1 = cromosoma.subList(0, cortes.get(0));
        List<Gen> nuevoCromosoma1Parte2 = cromosoma.subList(cortes.get(0), cortes.get(1));
        List<Gen> nuevoCromosoma1Parte3 = cromosoma.subList(cortes.get(1), cortes.get(2));
        List<Gen> nuevoCromosoma1Parte4 = cromosoma.subList(cortes.get(2), numGenes);
        
        List<Gen> nuevoCromosoma1 = new ArrayList<Gen>(nuevoCromosoma1Parte1);
        nuevoCromosoma1.addAll(nuevoCromosoma1Parte2);
        nuevoCromosoma1.addAll(nuevoCromosoma1Parte3);
        nuevoCromosoma1.addAll(nuevoCromosoma1Parte4);
        
        List<Gen> nuevoCromosoma2Parte1 = cromosoma.subList(0, cortes.get(0));
        List<Gen> nuevoCromosoma2Parte2 = cromosoma.subList(cortes.get(0), cortes.get(1));
        List<Gen> nuevoCromosoma2Parte3 = cromosoma.subList(cortes.get(1), cortes.get(2));
        List<Gen> nuevoCromosoma2Parte4 = cromosoma.subList(cortes.get(2), numGenes);
        
        List<Gen> nuevoCromosoma2 = new ArrayList<Gen>(nuevoCromosoma2Parte1);
        nuevoCromosoma2.addAll(nuevoCromosoma2Parte2);
        nuevoCromosoma2.addAll(nuevoCromosoma2Parte3);
        nuevoCromosoma2.addAll(nuevoCromosoma2Parte4);
        
        Individuo nuevoIndividuo1  = new Individuo();
        nuevoIndividuo1.setCromosoma(nuevoCromosoma1);
        
        Individuo nuevoIndividuo2  = new Individuo();
        nuevoIndividuo2.setCromosoma(nuevoCromosoma2);
        
        List<Individuo> individuosResultantesCruzamiento = new ArrayList<Individuo>();
        individuosResultantesCruzamiento.add(nuevoIndividuo1);
        individuosResultantesCruzamiento.add(nuevoIndividuo2);
        
        return individuosResultantesCruzamiento;
        
    }
    
    private Calendario construirCalendarioDesdeCromosoma(Map<Integer, BloqueHorario> tablaBloquesHorarios){
        Calendario calendarioConstruido = new Calendario();
        
        List<Cita> citasCromosoma = new ArrayList<Cita>();
        int numCitas = cromosoma.size() / CANTIDAD_CROMOSOMAS_DEFINEN_OBJETO;
        
        for (int i = 0; i < numCitas; i++){
        
            int indicePropiedadCita = i * CANTIDAD_CROMOSOMAS_DEFINEN_OBJETO;
            
            Cita citaCromosoma = new Cita();
            
            citaCromosoma.setBeneficiario(new Beneficiario());
            
            citaCromosoma.getBeneficiario().setIdBeneficiario(cromosoma.get(indicePropiedadCita).valor);
            
            indicePropiedadCita++;
            
            citaCromosoma.setHorario(new BloqueHorario());
            
            citaCromosoma.getHorario().setIdBloqueHorario(cromosoma.get(indicePropiedadCita).valor);
            
            citaCromosoma.getHorario().setLocal(tablaBloquesHorarios.get(citaCromosoma.getHorario().getIdBloqueHorario()).getLocal());
            
            citasCromosoma.add(citaCromosoma);
            
        }
        
        calendarioConstruido.setCitas(citasCromosoma);
        
        return calendarioConstruido;
    }
    
    public void evaluarIndividuo(List<LocalAtencion> localesDeAtencionDisponibles, List<Beneficiario> beneficiariosAtender, Map<Integer, BloqueHorario> tablaBloquesHorarios){
        Calendario calendarioConstruido = construirCalendarioDesdeCromosoma(tablaBloquesHorarios);
        
        calendarioConstruido.actualizarCantidadBeneficiariosBloquesHorarios(localesDeAtencionDisponibles);
        
        fitness = calendarioConstruido.evaluarCalendario(localesDeAtencionDisponibles, beneficiariosAtender);
    }
    
    public Calendario obtenerCalendario(Map<Integer, BloqueHorario> tablaBloquesHorarios){
    
        return construirCalendarioDesdeCromosoma(tablaBloquesHorarios);
        
    }

    @Override
    public int compareTo(Object o) {
        
        int comparacion = 0;
        
        Individuo individuoComparacion = (Individuo) o;
        
        double fitnessIndividuoComparacion = individuoComparacion.getFitness();
        
        //double diferencia = fitness - fitnessIndividuoComparacion;
        
        double diferencia = fitnessIndividuoComparacion - fitness;
        
        if (diferencia < 0)
            
            comparacion = -1;
        
        else
            
            comparacion = 1;
        
        return comparacion;
        
    }
}
