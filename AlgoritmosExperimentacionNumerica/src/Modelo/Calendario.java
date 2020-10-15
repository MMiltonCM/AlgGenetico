/*
 * El conjunto de citas para la distribucion de un beneficio
 */
package Modelo;

import java.util.List;

public class Calendario {
    private Beneficio beneficio;
    private List<Cita> citas;

    /**
     * @return the beneficio
     */
    public Beneficio getBeneficio() {
        return beneficio;
    }

    /**
     * @param beneficio the beneficio to set
     */
    public void setBeneficio(Beneficio beneficio) {
        this.beneficio = beneficio;
    }

    /**
     * @return the citas
     */
    public List<Cita> getCitas() {
        return citas;
    }

    /**
     * @param citas the citas to set
     */
    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }
    
    // Metodo para agendar Cita
    
    private int calcularCantidadBeneficiariosBloquesHorario(LocalAtencion localDeAtencionDisponible, 
            BloqueHorario horarioLocalDeAtencionDisponible){
    
        int cantidadBeneficiariosBloquesHorario = 0;
        
        for (Cita citaNueva : citas){
        
            if((citaNueva.getHorario().getIdBloqueHorario() == horarioLocalDeAtencionDisponible.getIdBloqueHorario()) )
                if(citaNueva.getHorario().getLocal().getIdLocalAtencion() == localDeAtencionDisponible.getIdLocalAtencion())
                    cantidadBeneficiariosBloquesHorario++;
            
        }
        
        return cantidadBeneficiariosBloquesHorario;
        
    }
    
    public void actualizarCantidadBeneficiariosBloquesHorarios(List<LocalAtencion> localesDeAtencionDisponibles){
    
        for (LocalAtencion localDeAtencionDisponible : localesDeAtencionDisponibles){
        
            for (BloqueHorario horarioLocalDeAtencionDisponible : localDeAtencionDisponible.bloquesHorarios){
            
                int cantidadBeneficiariosBloquesHorario = 
                        calcularCantidadBeneficiariosBloquesHorario(localDeAtencionDisponible, horarioLocalDeAtencionDisponible);
                
                for (Cita citaNueva : citas){
        
                    if((citaNueva.getHorario().getIdBloqueHorario() == horarioLocalDeAtencionDisponible.getIdBloqueHorario()) 
                            && (citaNueva.getHorario().getLocal().getIdLocalAtencion() == localDeAtencionDisponible.getIdLocalAtencion()))
                
                        citaNueva.getHorario().setNumeroBeneficiariosAsignados(cantidadBeneficiariosBloquesHorario);
            
                }
                
            }
        }
    }
    
    private boolean evaluarFactibilidadCalendario(){
    
        boolean esFactible = true;
        
        for (Cita citaNueva : citas){

            esFactible = (citaNueva.getHorario().getNumeroBeneficiariosAsignados() <= citaNueva.getHorario().getLocal().getCapacidad());

            if (!esFactible)
                
                break;
        
        }
        
        return esFactible;
        
    }
    
    private double obtenerPuntuacionAFavor (List<LocalAtencion> localesDeAtencionDisponibles, List<Beneficiario> beneficiariosAtender){
    
        double puntajeAFavor = 0.0;
        
        for (Cita citaCalendario : citas){
            
            double puntajeAFavorCita = citaCalendario.obtenerPuntuacionAFavor(localesDeAtencionDisponibles, beneficiariosAtender);
            
            puntajeAFavor += puntajeAFavorCita;
            
        }
        
        return puntajeAFavor;
        
    }
    
    private double obtenerPuntuacionEnContra (List<LocalAtencion> localesDeAtencionDisponibles, List<Beneficiario> beneficiariosAtender){
    
        double puntajeEnContra = 0.0;
        
        for (Cita citaCalendario : citas){
            
            double puntajeEnContraCita = citaCalendario.obtenerPuntuacionEnContra(localesDeAtencionDisponibles, beneficiariosAtender);
            
            puntajeEnContra += puntajeEnContraCita;
            
        }
        
        return puntajeEnContra;
        
    }
    
    public double evaluarCalendario(List<LocalAtencion> localesDeAtencionDisponibles, List<Beneficiario> beneficiariosAtender){
        
        double resultadoEvaluacion;
        
        boolean esFactible = evaluarFactibilidadCalendario();
        
        if (esFactible){
        
            double puntajeAFavor = obtenerPuntuacionAFavor(localesDeAtencionDisponibles, beneficiariosAtender);
        
            double puntajeEnContra = obtenerPuntuacionEnContra(localesDeAtencionDisponibles, beneficiariosAtender);
        
            resultadoEvaluacion = puntajeAFavor - puntajeEnContra;
        
        }
        
        else
            
            resultadoEvaluacion = Double.MIN_VALUE;
        
        return resultadoEvaluacion;
    }
}
