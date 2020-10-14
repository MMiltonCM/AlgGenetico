package PSO;

import Modelo.Beneficiario;
import Modelo.Distribuidora;
import Modelo.LocalAtencion;
import Utils.Constantes;
import Utils.Hora;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ParticulaPSO {
    private AlgoritmoPSO algoritmo;
    private List<Beneficiario> beneficiarios;
    private List<LocalAtencion> locales;
    private LocalDate diaInicio;
    private Integer dias;
    
    private LinkedHashMap<Integer, LinkedHashMap<Integer, List<Integer> > > matriz;
    private LinkedHashMap<Integer, LocalDateTime> inicios;
    private LinkedHashMap<Integer, List<Integer>> asignaciones; //Primer elemento
    // de la lista es el codigo del local y el segundo el es hash del bloque
    // Fila : ID de LocalAtencion
    // Columna : ID de BloqueHorario de 30 minutos
    // Celda : ID de Beneficiario
    
    public ParticulaPSO(List<Beneficiario> beneficiarios, 
            List<LocalAtencion> locales, AlgoritmoPSO algoritmo){
        this.beneficiarios = beneficiarios;
        this.locales = locales;
        this.algoritmo = algoritmo;
        
        matriz = new LinkedHashMap<Integer, LinkedHashMap<Integer, List<Integer> > >();
        inicios = new LinkedHashMap<Integer, LocalDateTime> ();
        asignaciones = new LinkedHashMap<Integer, List<Integer>>();
    }
    
    public LocalDateTime getRandomBloque(){
        List<LocalDateTime> LLDT = new ArrayList<>(inicios.values());
        Integer random = (new Random()).nextInt(LLDT.size());
        return LLDT.get(random);
    }
    
    public ParticulaPSO(List<Beneficiario> beneficiarios, List<LocalAtencion> localesAtencion,
            AlgoritmoPSO algoritmo, 
            LocalDate diaInicio, Integer numDias){
        this.beneficiarios = beneficiarios;
        this.locales = localesAtencion;
        this.algoritmo = algoritmo;
        
        this.diaInicio = diaInicio;
        this.dias = numDias;
        
        matriz = new LinkedHashMap<Integer, LinkedHashMap<Integer, List<Integer> > >();
        inicios = new LinkedHashMap<Integer, LocalDateTime> ();
        asignaciones = new LinkedHashMap<Integer, List<Integer>>();
        
        for(LocalAtencion LA : localesAtencion){
            Integer idla = LA.getIdLocalAtencion();
            matriz.put(idla, new LinkedHashMap<Integer, List<Integer> >());
            
            LocalDate diaActual = diaInicio;
            for(Integer j = 0; j<numDias; j++){
                for(Integer i = 0; i<LA.getNumeroBloques(); i++){
                    LocalDateTime horaInicioBloque = LA.getBloques().get(i).atDate(diaActual);
                    Integer hashHoraInicioBloque = Hora.hashVisual(horaInicioBloque);
                    if (inicios.containsKey(hashHoraInicioBloque) == false)
                        inicios.put(hashHoraInicioBloque, horaInicioBloque);
                    matriz.get(idla).put(hashHoraInicioBloque, new ArrayList<>());
                }
                diaActual = diaActual.plusDays(1);
            }
        }
        
    }
    
    public void crearMatriz(LocalDate diaInicio, Integer numDias, 
            Distribuidora dist){
        List<LocalAtencion> localesAtencion = dist.getAgencias();
        
        for(LocalAtencion LA : localesAtencion){
            Integer idla = LA.getIdLocalAtencion();
            matriz.put(idla, new LinkedHashMap<Integer, List<Integer> >());
            
            LocalDate diaActual = diaInicio;
            for(Integer j = 0; j<numDias; j++){
                for(Integer i = 0; i<LA.getNumeroBloques(); i++){
                    LocalDateTime horaInicioBloque = LA.getBloques().get(i).atDate(diaActual);
                    Integer hashHoraInicioBloque = Hora.hashVisual(horaInicioBloque);
                    if (inicios.containsKey(hashHoraInicioBloque) == false)
                        inicios.put(hashHoraInicioBloque, horaInicioBloque);
                    matriz.get(idla).put(hashHoraInicioBloque, new ArrayList<>());
                }
                diaActual = diaActual.plusDays(1);
            }
        }
    }
    
    public void inicializarAleatoriamente(){
        LinkedList<Beneficiario> LL = new LinkedList<Beneficiario>(beneficiarios);
        Collections.shuffle(LL);
        
        Iterator itLocal = matriz.keySet().iterator();
        Integer LA = (Integer)itLocal.next(); //ID del local
        //Integer LA2 = (Integer)itLocal.next(); //ID del local
        //Iterator itHorario = matriz.get(LA).keySet().iterator();
        Iterator itBloqueActual = inicios.keySet().iterator();
        Integer BA = (Integer)itBloqueActual.next();
        
        for(Beneficiario B : LL){
            
            List<Integer> L = matriz.get(LA).get(BA); //Lista de ID de beneficiarios
            //BA = (Integer)itBloqueActual.next();
            
            if (L.size() < Constantes.capacidad){
                L.add(B.idBeneficiario);
                List<Integer> asig = new ArrayList<>();
                asig.add(LA);
                asig.add(BA);
                asignaciones.put(B.idBeneficiario, asig);
                continue;
            }
            //¿Que pasa si se ha llenado un horario de un local?
            if (itLocal.hasNext()){ //Quedan mas locales por revisar
                LA = (Integer)itLocal.next();
                matriz.get(LA).get(BA).add(B.idBeneficiario);
                List<Integer> asig = new ArrayList<>();
                asig.add(LA);
                asig.add(BA);
                asignaciones.put(B.idBeneficiario, asig);
            } else { //No hay mas locales, desplazar al bloque siguiente y reubicar
                BA = (Integer)itBloqueActual.next();
                itLocal = matriz.keySet().iterator();
                LA = (Integer)itLocal.next();
                matriz.get(LA).get(BA).add(B.idBeneficiario);
                List<Integer> asig = new ArrayList<>();
                asig.add(LA);
                asig.add(BA);
                asignaciones.put(B.idBeneficiario, asig);
            }
            
        }
    }
    
    public ParticulaPSO transicion(){
        ParticulaPSO vecino = new ParticulaPSO(beneficiarios, locales, 
                algoritmo, diaInicio, dias);
        vecino.inicializarAleatoriamente();
        Random r = new Random();
        List<Integer> beneficiariosV = new ArrayList<>(vecino.asignaciones.keySet());
        for( Integer idbenef : beneficiariosV){
            
            Integer localActual = vecino.asignaciones.get(idbenef).get(0);
            Integer bloqueActual = vecino.asignaciones.get(idbenef).get(1);
            
            LocalAtencion localAlea = algoritmo.getRandomLocalAtencion();
            LocalDateTime datetAlea = vecino.getRandomBloque();
            Integer local = localAlea.getIdLocalAtencion();
            Integer block = Hora.hashVisual(datetAlea);
            
            List<Integer> asignadosActual = vecino.matriz.get(localActual).get(bloqueActual);
            List<Integer> asignadosDestino = vecino.matriz.get(local).get(block);
            if (asignadosDestino.size()<Constantes.capacidad){
                asignadosActual.remove(idbenef);
                vecino.asignaciones.remove(idbenef);
                asignadosDestino.add(idbenef);
                List<Integer> asig = new ArrayList<>();
                asig.add(local);
                asig.add(block);
                vecino.asignaciones.put(idbenef, asig);
            }
        }
        
        return vecino;
    }
    
    public void fitness(){
        //El fitness será calculado mediante la suma (a definir operación) de
        // los valores obtenidos por cada beneficiario, el cual aumenta
        // mientras mejor sea su asignación
        
        Iterator itAsignaciones = asignaciones.keySet().iterator();
        while(true){
            if (itAsignaciones.hasNext() == false)break;
            Integer idBenef = (Integer)itAsignaciones.next();
            List<Integer> valoresAsignados = asignaciones.get(idBenef);
            Integer idLocal = valoresAsignados.get(0);
            Integer hashBloque = valoresAsignados.get(1);
        }
    }
}