package PSO;

import Modelo.Beneficiario;
import Modelo.Distribuidora;
import Modelo.LocalAtencion;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ParticulaPSO {
    private List<Beneficiario> beneficiarios;
    private HashMap<Integer, HashMap<Integer, List<Integer> > > matriz;
    // Fila : ID de LocalAtencion
    // Columna : ID de BloqueHorario de 30 minutos
    // Celda : ID de Beneficiario
    
    public void crearMatriz(LocalDateTime diaInicio, Integer numDias, 
            Distribuidora dist){
        List<LocalAtencion> localesAtencion = dist.getAgencias();
        for(LocalAtencion LA : localesAtencion){
            Integer idla = LA.getIdLocalAtencion();
            matriz.put(idla, new HashMap<Integer, List<Integer> >());
            for(Integer i = 0; i<numDias; i++){
                
            }
        }
    }
    
    public void inicializarAleatoriamente(List<Beneficiario> benef){
        LinkedList<Beneficiario> LL = new LinkedList<Beneficiario>(benef);
        Collections.shuffle(LL);
        
        
    }
}