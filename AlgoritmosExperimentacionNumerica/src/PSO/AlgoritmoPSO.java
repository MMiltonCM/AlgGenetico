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
import java.util.List;
import java.util.Random;

public class AlgoritmoPSO {
    public int tiempoEjecucion;
    public double score;
    public Solucion solucion;
    
    public LocalDate fechaInicio;
    public List<Beneficiario> beneficiarios;
    public List<LocalAtencion> locales;
    public Integer dias;
    
    public List<LocalDateTime> bloques;
    
    public LocalAtencion getRandomLocalAtencion(){
        Integer random = (new Random()).nextInt(locales.size());
        return locales.get(random);
    }
    
    public LocalDateTime getRandomBloque(){
        Integer random = (new Random()).nextInt(bloques.size());
        return bloques.get(random);
    }
    
    public AlgoritmoPSO(Beneficio bono, LocalDate fechaInicio, Integer dias) {
        //this.solucion = new Solucion(bono);
        this.fechaInicio = fechaInicio;
        this.beneficiarios = null;
        this.dias = dias;
        this.locales = bono.getDist().getAgencias();
        this.beneficiarios = bono.getPad().getBeneficiarios();
        
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
            LPSO.add(ppso2);
            System.out.print("Se ha creado la particula " + ((Integer)(2*i+1)).toString() + "\n");
        }
        
        
        
    }
}
