package PSO;

import Algoritmo.Solucion;
import Modelo.Beneficiario;
import Modelo.Beneficio;
import Modelo.Calendario;
import Modelo.LocalAtencion;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AlgoritmoPSO {
    public int tiempoEjecucion;
    public double score;
    public Solucion solucion;
    
    public LocalDate fechaInicio;
    public List<Beneficiario> beneficiarios;
    public List<LocalAtencion> locales;
    public Integer dias;
    
    public List<LocalDateTime> bloques;
    
    public AlgoritmoPSO(Beneficio bono, LocalDate fechaInicio, Integer dias) {
        //this.solucion = new Solucion(bono);
        this.fechaInicio = fechaInicio;
        this.beneficiarios = null;
        this.dias = dias;
        this.locales = bono.getDist().getAgencias();
        this.beneficiarios = bono.getPad().getBeneficiarios();
        
        //
        //this.bloques = new ArrayList<>(); //truco
        
    }
    
    public void ejecutar(){
        // Iniciar contador de tiempo
        //Calendario cal = solucion.Distribuir_Citas();
        // Obtener el score de la FO
        
        List<ParticulaPSO> LPSO = new ArrayList<>();
        
        for(Integer i = 0; i < 30 ; i++){
            ParticulaPSO ppso = new ParticulaPSO(beneficiarios, locales, fechaInicio, dias);
            ppso.inicializarAleatoriamente(beneficiarios);
            LPSO.add(ppso);
            System.out.print("Se ha creado la particula " + i.toString() + "\n");
        }
        
        
        
    }
}
