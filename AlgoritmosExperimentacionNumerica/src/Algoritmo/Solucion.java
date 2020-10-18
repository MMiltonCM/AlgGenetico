/*
 * Clase que utiliza el algoritmo para crear el calendario de citas
 */
package Algoritmo;

import Modelo.Beneficiario;
import Modelo.Beneficio;
import Modelo.BloqueHorario;
import Modelo.Calendario;
import Modelo.LocalAtencion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solucion {
    private final Beneficio bono;
    private Map<Beneficiario, Individuo> padron_algoritmo;
    private Map<Integer, BloqueHorario> tablaBloquesHorarios;
    
    private final int TAMANO_TORNEO = 3;
    private final int NUM_PUNTOS_CORTE_CRUZAMIENTO_MULTIPUNTO = 3;
    
    public Solucion(Beneficio bono){
        this.bono = bono;
    }
    
    public void crearTablaBloqueHorarios(List<LocalAtencion> localesDeAtencionDisponibles){
    
        for (LocalAtencion localAtencionDisponible : localesDeAtencionDisponibles){
        
            for (BloqueHorario bloqueHorarioLocalDeAtencion : localAtencionDisponible.bloquesHorarios){
            
                tablaBloquesHorarios.put(bloqueHorarioLocalDeAtencion.getIdBloqueHorario(), bloqueHorarioLocalDeAtencion);
                
            }
            
        }
        
    }
    
    private void evaluarPoblacion(List<Individuo> poblacion, List<LocalAtencion> localesDeAtencionDisponibles, List<Beneficiario> beneficiariosAtender){
    
        for (Individuo individuoPoblacion : poblacion){
        
            individuoPoblacion.evaluarIndividuo(localesDeAtencionDisponibles, beneficiariosAtender, tablaBloquesHorarios);
            
        }
        
    }
    
    private List<Individuo> generarPoblacionInicial(int cant, List<Beneficiario> gente, List<LocalAtencion> locales){
    
        List<Individuo> poblacion = new ArrayList<>();
        
        int maxLocales = locales.size();
        
        for(int i=0; i<cant; i++){
            Individuo d = new Individuo();
            for(Beneficiario b : gente){
                Gen persona = new Gen("IDENTIFICADOR",b.getIdBeneficiario());
                d.agregarGenACromosoma(persona);
                LocalAtencion l = locales.get((int)(Math.random()*maxLocales));
                Gen loc = new Gen("VARIABLE", l.getIdLocalAtencion());
                d.agregarGenACromosoma(loc);
                int maxbh = l.bloquesHorarios.size();
                BloqueHorario bh = l.bloquesHorarios.get((int)(Math.random()*maxbh));
                Gen hor = new Gen("VARIABLE", bh.getIdBloqueHorario());
                d.agregarGenACromosoma(hor);
            }
            poblacion.add(d);
        }
        
        return poblacion;
        
    }
    
    private double obtenerFitnessPoblacion(List<Individuo> poblacion){
    
        double fitnessPoblacion = 0.0;
        
        for (Individuo individuoPoblacion : poblacion){
        
            fitnessPoblacion += individuoPoblacion.getFitness();
            
        }
        
        return fitnessPoblacion;
        
    }
    
    private double obtenerFitnessMaximoPoblacion(List<Individuo> poblacion){
    
        double fitnessMaximoPoblacion = Double.MIN_VALUE;
        
        for (Individuo individuoPoblacion : poblacion){
        
            double fitnessIndividuoPoblacion = individuoPoblacion.getFitness();
            
            if (fitnessIndividuoPoblacion > fitnessMaximoPoblacion)
            
            fitnessMaximoPoblacion = fitnessIndividuoPoblacion;
            
        }
        
        return fitnessMaximoPoblacion;
        
    }
    
    private double obtenerFitnessMinimoPoblacion(List<Individuo> poblacion){
    
        double fitnessMinimoPoblacion = Double.MAX_VALUE;
        
        for (Individuo individuoPoblacion : poblacion){
        
            double fitnessIndividuoPoblacion = individuoPoblacion.getFitness();
            
            if (fitnessIndividuoPoblacion < fitnessMinimoPoblacion)
            
            fitnessMinimoPoblacion = fitnessIndividuoPoblacion;
            
        }
        
        return fitnessMinimoPoblacion;
        
    }
    
    private List<Individuo> seleccionarPadrePorRuleta(List<Individuo> poblacion){
    
        List<Individuo> padresSeleccionados = new ArrayList<Individuo>();
        
        //TODO
        double fitnessMinimoPoblacion = obtenerFitnessMinimoPoblacion(poblacion);
        
        double fitnessMaximoPoblacion = obtenerFitnessMaximoPoblacion(poblacion);
        
        double fitnessDeCorte = (Math.random() * (fitnessMaximoPoblacion - fitnessMinimoPoblacion) + 1) + fitnessMinimoPoblacion;
        
        
        return padresSeleccionados;
        
    }
    
    private int obtenerIndiceMejorFitness(List<Double> listaFitnessIndividuosTorneo){
    
        double mejorFitness = Double.MIN_VALUE;
        
        int indiceMejorFitness = 0;
        
        int indice = 0;
        
        for (Double fitnessIndividuoTorneo : listaFitnessIndividuosTorneo){
        
            if (fitnessIndividuoTorneo > mejorFitness){
            
                mejorFitness = fitnessIndividuoTorneo;
                
                indiceMejorFitness = indice;
                
            }
            
            indice++;
            
        }
        
        return indiceMejorFitness;
        
    }
    
    private List<Individuo> seleccionarPadrePorTorneo(List<Individuo> poblacion){
    
        List<Individuo> padresSeleccionados = new ArrayList<Individuo>();
        
        //double[] listaFitnessIndividuosTorneo = new double[TAMANO_TORNEO];
        
        List<Double> listaFitnessIndividuosTorneo = new ArrayList<Double>();
        
        //TODO
        List<Integer> rangoX1 = IntStream.range(0, poblacion.size()).boxed().collect(Collectors.toList());
        
        Collections.shuffle(rangoX1);
        
        List<Integer> rangoY1 = rangoX1.subList(0, TAMANO_TORNEO);
        
        for (Integer indice : rangoY1){
        
            //listaFitnessIndividuosTorneo[i] = poblacion.get(indice).getFitness();
            double fitnessIndividuoSeleccionadoTorneo = poblacion.get(indice.intValue()).getFitness();
            
            listaFitnessIndividuosTorneo.add(fitnessIndividuoSeleccionadoTorneo);
            
        }
        
        int indicePadre1 = obtenerIndiceMejorFitness(listaFitnessIndividuosTorneo);
        
        List<Integer> rangoX2 = new ArrayList<Integer>();
        
        rangoX2.addAll(rangoX1);
        
        rangoX2.remove(indicePadre1);
        
        Collections.shuffle(rangoX2);
        
        listaFitnessIndividuosTorneo = new ArrayList<Double>();
        
        List<Integer> rangoY2 = rangoX2.subList(0, TAMANO_TORNEO);
        
        for (Integer indice : rangoY2){
        
            double fitnessIndividuoSeleccionadoTorneo = poblacion.get(indice.intValue()).getFitness();
            
            listaFitnessIndividuosTorneo.add(fitnessIndividuoSeleccionadoTorneo);
            
        }
        
        int indicePadre2 = obtenerIndiceMejorFitness(listaFitnessIndividuosTorneo);
        
        int indicePadre1EnPoblacion = rangoX1.get(indicePadre1);
        
        int indicePadre2EnPoblacion = rangoX2.get(indicePadre2);
        
        Individuo padre1 = poblacion.get(indicePadre1EnPoblacion);
        
        Individuo padre2 = poblacion.get(indicePadre2EnPoblacion);
        
        padresSeleccionados.add(padre1);
        
        padresSeleccionados.add(padre2);
        
        return padresSeleccionados;
        
    }
    
    private List<Individuo> seleccionarSobrevivientesRanking(List<Individuo> poblacion, List<Individuo> poblacionDescendiente, int numeroSobrevivientes){
    
        List<Individuo> siguientePoblacion = new ArrayList<Individuo>();
        
        List<Individuo> poblacionesUnidas = new ArrayList<Individuo>();
        
        //TODO
        poblacionesUnidas.addAll(poblacion);
        poblacionesUnidas.addAll(poblacionDescendiente);
        
        Collections.sort(poblacionesUnidas);
        
        siguientePoblacion = poblacionesUnidas.subList(0, numeroSobrevivientes);
        
        return siguientePoblacion;
        
    }
    
    private Individuo obtenerMejorIndividuo(List<Individuo> poblacion){
    
        Individuo mejorIndividuo = null;
        
        for (Individuo individuoPoblacion : poblacion){
        
            if (mejorIndividuo == null){
            
                mejorIndividuo = individuoPoblacion;
                
            }else{
            
                if (individuoPoblacion.getFitness() > mejorIndividuo.getFitness())
                    
                    mejorIndividuo = individuoPoblacion;
                
            }
            
        }
        
        return mejorIndividuo;
        
    }
    
    public Calendario Distribuir_Citas(int tamanoPoblacion, int numeroGeneraciones){
        
        List<LocalAtencion> localesDeAtencionDisponibles = bono.getDist().getAgencias();
        List<Beneficiario> beneficiariosAtender = bono.getPad().getBeneficiarios();
        
        crearTablaBloqueHorarios(localesDeAtencionDisponibles);
        
        // Crear citas de los bloquehorario de la Distribuidora 
        
        // Crear una poblacion con los beneficiario del padron. Asignar cita aleatoria
        
        List<Individuo> poblacion = generarPoblacionInicial(tamanoPoblacion, beneficiariosAtender,localesDeAtencionDisponibles);
        
        evaluarPoblacion(poblacion, localesDeAtencionDisponibles, beneficiariosAtender);
        
        // Empezar Bucle: Mientras no se llegue al limite de generaciones 
        
        int numParejasCrizamiento = tamanoPoblacion/2;
        
        for (int i = 0; i < numeroGeneraciones; i++){
        
            List<List<Individuo>> poolParejas = new ArrayList<List<Individuo>>();
            
            for (int j = 0; j < numParejasCrizamiento; j++){
            
                List<Individuo> pareja = seleccionarPadrePorTorneo(poblacion);
                
                poolParejas.add(pareja);
                
            }
            
            List<Individuo> poblacionDescendiente = new ArrayList<Individuo>();
            
            for (List<Individuo> pareja : poolParejas){
            
                //List<Individuo> nuevosIndividuos = pareja.get(0).realizarCruzamientoOnePoint(pareja.get(1).getCromosoma());
                List<Individuo> nuevosIndividuos = pareja.get(0).realizarCruzamientoMultiPoint(pareja.get(1).getCromosoma(), NUM_PUNTOS_CORTE_CRUZAMIENTO_MULTIPUNTO);
                
                poblacionDescendiente.addAll(nuevosIndividuos);
                
            }
            
            evaluarPoblacion(poblacion, localesDeAtencionDisponibles, beneficiariosAtender);
            
            poblacion = seleccionarSobrevivientesRanking(poblacion, poblacionDescendiente, tamanoPoblacion);
        
        }
        
        Individuo mejorIndividuo = obtenerMejorIndividuo(poblacion);
        
        //TODO Realizar reporte final
        
        Calendario cal = mejorIndividuo.obtenerCalendario(tablaBloquesHorarios);
        
        return cal;
    }
    
}

// INDIVIDIO JUAN + INDIVIDUO PEPE
// 1 INDIVIDIO JUAN + 1 INDIVIDUO PEPE 
// COMPARAR JUAN ANTIGUO CON JUAN NUEVO
// ESCOGIAS AL MEJOR JUAN