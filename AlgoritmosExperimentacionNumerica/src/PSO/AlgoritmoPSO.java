package PSO;

import Algoritmo.Solucion;
import Modelo.Beneficiario;
import Modelo.Beneficio;
import Modelo.BloqueHorario;
import Modelo.Calendario;
import Modelo.LocalAtencion;
import Utils.Hora;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public class AlgoritmoPSO {
    public int tiempoEjecucion;
    public double score;
    public Solucion solucion;
    
    public LocalDate fechaInicio;
    public List<Beneficiario> beneficiarios;
    public LinkedHashMap<Integer, Beneficiario> mapBeneficiarios;
    public List<LocalAtencion> locales;
    public LinkedHashMap<Integer, LocalAtencion> mapLocales;
    public Integer dias;
    public Beneficio bono;
    
    public List<LocalDateTime> bloques;
    
    public LocalAtencion getRandomLocalAtencion(){
        Integer random = (new Random()).nextInt(locales.size());
        return locales.get(random);
    }
    
    public LocalDateTime getRandomBloque(){
        Integer random = (new Random()).nextInt(bloques.size());
        return bloques.get(random);
    }
    
    public LocalAtencion getLocalAtencion(Integer idLocal){
        return mapLocales.get(idLocal);
    }
    
    public Beneficiario getBeneficiario(Integer idBenef){
        return mapBeneficiarios.get(idBenef);
    }
    
    public BloqueHorario getBloqueHorario(Integer idLocalAsignado, Integer hashBloqueAsignado){
        LocalAtencion LA = getLocalAtencion(idLocalAsignado);
        for(BloqueHorario BH : LA.bloquesHorarios)
            if (BH.inicio.hashCode() == hashBloqueAsignado)
                return BH;
        return null;
    }
    
    public AlgoritmoPSO(Beneficio bono, LocalDate fechaInicio, Integer dias) {
        //this.solucion = new Solucion(bono);
        this.fechaInicio = fechaInicio;
        this.dias = dias;
        this.locales = bono.getDist().getAgencias();
        for(Integer i = 0; i < locales.size(); i++)
            locales.get(i).crearBloquesHorarios(fechaInicio, dias);
        this.mapLocales = new LinkedHashMap<Integer, LocalAtencion>();
        this.mapBeneficiarios = new LinkedHashMap<Integer, Beneficiario>();
        for(LocalAtencion LA : locales)
            mapLocales.put(LA.getIdLocalAtencion(), LA);
        this.beneficiarios = bono.getPad().getBeneficiarios();
        for(Beneficiario B : beneficiarios)
            mapBeneficiarios.put(B.getIdBeneficiario(), B);
        this.bono = bono;
    }
    
    public void ejecutar(){
        // Iniciar contador de tiempo
        //Calendario cal = solucion.Distribuir_Citas();
        // Obtener el score de la FO
        
        List<ParticulaPSO> LPSO = new ArrayList<>();
        
        for(Integer i = 0; i < 200 ; i++){
            ParticulaPSO ppso1 = new ParticulaPSO(beneficiarios, locales, this, 
                    fechaInicio, dias);
            ppso1.inicializarAleatoriamente();
            ParticulaPSO ppso2 = ppso1.transicion(1.0);
            Integer j = i;
            LPSO.add(ppso1);
            System.out.print("Se ha creado la particula " + ((Integer)(2*i)).toString() + "\n");
            System.out.print("Fitness " + (Double)(ppso1.evaluar(bono)) + "\n");
            LPSO.add(ppso2);
            System.out.print("Se ha creado la particula " + ((Integer)(2*i+1)).toString() + "\n");
            System.out.print("Fitness " + (Double)(ppso2.evaluar(bono)) + "\n");
            
            System.out.print(" ===================================== \n");
        }
        
        
        
    }
    
    public void imprimirParticulas(List<ParticulaPSO> LPSO, 
            ParticulaPSO global, Integer numero){
        System.out.print("========================\n");
        System.out.print("Iteracion #"+numero+"\n");
        for(Integer i = 0; i<LPSO.size(); i++){
            System.out.print("" + ((Double)LPSO.get(i).evaluar(bono)).toString() + "\n");
        }
        System.out.print("Mejor global: " + ((Double)global.evaluar(bono)).toString() + "\n");
        System.out.print("========================\n");
    }
    
    public Calendario ejecutarPSO(Integer limite, Integer numVecindades, 
            Integer numVecinos, Double prob){
        
        List<ParticulaPSO> LPSO = new ArrayList<>();
        for(Integer i = 0; i<numVecindades; i++){
            ParticulaPSO PPSO = new ParticulaPSO(beneficiarios, locales, this, fechaInicio, dias);
            PPSO.inicializarAleatoriamente();
            LPSO.add(PPSO);
        }
        
        
        for(Integer i = 0; i<limite; i++){ //bucle limite de veces
            
            for(Integer j = 0; j<numVecindades; j++){
                
                ParticulaPSO vecinoBase = LPSO.get(j);
                
                ParticulaPSO mejorPartLocal = vecinoBase;
                Double mejorFitLocal = mejorPartLocal.evaluar(bono);
                
                for(Integer k = 0; k<numVecinos; k++){
                    ParticulaPSO vecinoK = vecinoBase.transicion(prob);
                    Double fitVecinoK = vecinoK.evaluar(bono);
                    if (mejorFitLocal < fitVecinoK){
                        mejorPartLocal = vecinoK;
                        mejorFitLocal = fitVecinoK;
                    }
                }
                
                LPSO.set(j, mejorPartLocal);
                
            }
            //imprimirParticulas(LPSO, i+1);
        }
        
        ParticulaPSO mejorGlobal = LPSO.get(0);
        Double mejorFitnessGlobal = mejorGlobal.evaluar(bono);
        for(Integer i = 1; i<numVecindades; i++){
            Double fitness = LPSO.get(i).evaluar(bono);
            if (mejorFitnessGlobal < fitness){
                mejorGlobal = LPSO.get(i);
                mejorFitnessGlobal = fitness;
            }
            imprimirParticulas(LPSO, mejorGlobal, i+1);
        }
        
        
        
        return mejorGlobal.convertirACalendario(bono);
    }
    
    public Calendario ejecutarPSOv2(Integer limite, 
            Integer numParticulas, Double aleatorizador, Double inercia, Double pesoLocal, 
            Double pesoGlobal){
        
        List<Double> probs = new ArrayList<>();
        //probs.add(1.0 - inercia - pesoLocal - pesoGlobal);
        probs.add(inercia);
        probs.add(pesoLocal);
        probs.add(pesoGlobal);
        List<ParticulaPSO> particulas = new ArrayList<>();
        //particulas.add(null);
        particulas.add(null);
        particulas.add(null);
        particulas.add(null);
        
        List<ParticulaPSO> poblacion = new ArrayList<>();
        List<ParticulaPSO> pBest = new ArrayList<>();
        List<Double> fitnessPBest = new ArrayList<>();
        List<ParticulaPSO> partInercial = new ArrayList<>();
        ParticulaPSO global = new ParticulaPSO(beneficiarios, locales, this, fechaInicio, dias);
        Double fitnessGlobal = 0.0;
        
        for(Integer i = 0; i<numParticulas; i++){
            ParticulaPSO PPSO = new ParticulaPSO(beneficiarios, locales, this, fechaInicio, dias);
            PPSO.inicializarAleatoriamente();
            poblacion.add(PPSO);
            pBest.add(null);
            fitnessPBest.add(0.0);
            ParticulaPSO aleatorio = PPSO.transicion(aleatorizador);
            partInercial.add(aleatorio);
        }
        
        this.imprimirParticulas(poblacion, global, 0);
        
        for(Integer i = 0; i<limite; i++){
            
            for(Integer j = 0; j<numParticulas; j++){
                ParticulaPSO partc = poblacion.get(j);
                Double fitnessParticula = partc.evaluar(bono);
                
                if( fitnessPBest.get(j) < fitnessParticula ){
                    pBest.set(j, partc);
                    fitnessPBest.set(j, fitnessParticula);
                }
                if( fitnessGlobal < fitnessParticula ){
                    global = partc;
                    fitnessGlobal = fitnessParticula;
                }
            }
            
            for(Integer j = 0; j<numParticulas; j++){
                ParticulaPSO partc = poblacion.get(j);
                ParticulaPSO pbest = pBest.get(j);
                ParticulaPSO partIne = partInercial.get(j);
                Double fitnessParticula = partc.evaluar(bono);
                
                List<ParticulaPSO> partss = new ArrayList<ParticulaPSO>();
                particulas.set(0, partIne);
                particulas.set(1, pbest);
                particulas.set(2, global);
                
                ParticulaPSO nueva = partc.transicionMultiorientada(probs, particulas);
                if(nueva.evaluar(bono) < 1){
                    nueva = partc.transicionMultiorientada(probs, particulas);
                    if (nueva.evaluar(bono) < 1)
                        nueva = partc.transicionMultiorientada(probs, particulas);
                }
                
                poblacion.set(j, nueva);
                partInercial.set(j, nueva.transicion(aleatorizador));
            }
            
            this.imprimirParticulas(poblacion, global, i+1);
        }
        
        return global.convertirACalendario(bono);
    }
    
    public Calendario ejecutarPSOPrueba(){
        ParticulaPSO part1 = new ParticulaPSO(beneficiarios, locales, this, fechaInicio, dias);
        part1.inicializarAleatoriamente();
        ParticulaPSO part2 = part1.transicion(1.0);
        
        List<Double> probs = new ArrayList<>();
        probs.add(0.0);
        probs.add(1.0);
        List<ParticulaPSO> parts = new ArrayList<>();
        parts.add(part1);
        parts.add(part2);
        
        ParticulaPSO part3 = part1.transicionMultiorientada(probs, parts);
        
        return part3.convertirACalendario(bono);
    }
}
