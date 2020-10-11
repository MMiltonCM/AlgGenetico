package PSO;

import Modelo.Beneficiario;
import Modelo.Distribuidora;
import Modelo.LocalAtencion;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
    
    public ParticulaPSO(List<Beneficiario> beneficiarios){
        this.beneficiarios = beneficiarios;
        matriz = new HashMap<Integer, HashMap<Integer, List<Integer> > >();
    }
    
    public void crearMatriz(LocalDate diaInicio, Integer numDias, 
            Distribuidora dist){
        List<LocalAtencion> localesAtencion = dist.getAgencias();
        
        for(LocalAtencion LA : localesAtencion){
            Integer idla = LA.getIdLocalAtencion();
            matriz.put(idla, new HashMap<Integer, List<Integer> >());
            
            LocalDate diaActual = diaInicio;
            for(Integer j = 0; j<numDias; j++){
                for(Integer i = 0; i<LA.getNumeroBloques(); i++){
                    LocalDateTime horaInicioBloque = LA.getBloques().get(i).atDate(diaActual);
                    matriz.get(idla).put(horaInicioBloque.hashCode(), new ArrayList<>());
                }
                diaActual = diaActual.plusDays(1);
            }
        }
    }
    
    public void inicializarAleatoriamente(List<Beneficiario> benef){
        LinkedList<Beneficiario> LL = new LinkedList<Beneficiario>(benef);
        Collections.shuffle(LL);
        
        
    }
}