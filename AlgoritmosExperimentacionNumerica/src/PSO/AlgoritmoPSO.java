package PSO;

import Algoritmo.Solucion;
import Modelo.Beneficiario;
import Modelo.Beneficio;
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
        
        for(Integer i = 0; i < 100 ; i++){
            ParticulaPSO ppso1 = new ParticulaPSO(beneficiarios, locales, this, 
                    fechaInicio, dias);
            ppso1.inicializarAleatoriamente();
            ParticulaPSO ppso2 = ppso1.transicion();
            Integer j = i;
            LPSO.add(ppso1);
            System.out.print("Se ha creado la particula " + ((Integer)(2*i)).toString() + "\n");
            System.out.print("Fitness " + (Double)(ppso1.evaluar(bono)) + "\n");
            LPSO.add(ppso2);
            System.out.print("Se ha creado la particula " + ((Integer)(2*i+1)).toString() + "\n");
            System.out.print("Fitness " + (Double)(ppso2.evaluar(bono)) + "\n");
            
            System.out.print(" ===================================== ");
        }
        
        
        
    }
}
