/*
 * Un espacio de tiempo donde un beneficiario puede recoger su beneficio
 */
package Modelo;

import java.util.List;

enum ESTADO_CITA{ATENDIDO, POR_ATENDER}

public class Cita {
    
    private static final int BONIFICACION_ADULTO_MAYOR = 100;
    private static final int BONIFICACION_DISCAPACITADO = 100;
    
    public BloqueHorario horario;
    public Beneficiario beneficiario;
    public ESTADO_CITA estado;
    
    private LocalAtencion obtenerLocalDeAtencionCita(List<LocalAtencion> localesDeAtencionDisponibles){
    
        LocalAtencion localDeAtencionCita = null;
        
        for (LocalAtencion localDeAtencionDisponible : localesDeAtencionDisponibles){
        
            if (horario.local.getIdLocalAtencion() == localDeAtencionDisponible.getIdLocalAtencion()){
            
                localDeAtencionCita = localDeAtencionDisponible;
                
                break;
                
            }
        }
        
        return localDeAtencionCita;
        
    }
    
    private Beneficiario obtenerBeneficiarioCita(List<Beneficiario> beneficiariosAtender){
    
        Beneficiario beneficiarioCita = null;
        
        for (Beneficiario beneficiarioAtender : beneficiariosAtender){
        
            if (beneficiario.getIdBeneficiario() == beneficiarioAtender.getIdBeneficiario()){
            
                beneficiarioCita = beneficiarioAtender;
                
                break;
                
            }
        }
        
        return beneficiarioCita;
        
    }
    
    private double obtenerBonificacionAdultoMayor(Beneficiario beneficiarioCita){
    
        double bonificacionAdultoMayor = 0.0;
        
        if (beneficiarioCita.isFlagAdultoMayor())
            
            bonificacionAdultoMayor += BONIFICACION_ADULTO_MAYOR;
        
        if (beneficiarioCita.isFlagDiscapacidad())
            
            bonificacionAdultoMayor += BONIFICACION_DISCAPACITADO;
        
        return bonificacionAdultoMayor;
        
    }
    
    private double obtenerGradoAglomeracion(BloqueHorario bloqueHorarioCita, LocalAtencion localDeAtencionCita){
    
        int numeroPersonasLocalDeAtencion = bloqueHorarioCita.getNumeroBeneficiariosAsignados();
        
        int capacidadLocalDeAtencion = localDeAtencionCita.getCapacidad();
        
        double gradoAglomeracion = ((double) numeroPersonasLocalDeAtencion) / capacidadLocalDeAtencion;
        
        return gradoAglomeracion;
    }
    
    public double obtenerPuntuacionAFavor(List<LocalAtencion> localesDeAtencionDisponibles, List<Beneficiario> beneficiariosAtender){
    
        double puntajeAFavor = 0.0;
        
        LocalAtencion localDeAtencionCita = obtenerLocalDeAtencionCita(localesDeAtencionDisponibles);
        
        Beneficiario beneficiarioCita = obtenerBeneficiarioCita(beneficiariosAtender);
        
        puntajeAFavor += obtenerBonificacionAdultoMayor(beneficiarioCita);
        
        return puntajeAFavor;
        
    }
    
    public double obtenerPuntuacionEnContra (List<LocalAtencion> localesDeAtencionDisponibles, List<Beneficiario> beneficiariosAtender){
    
        double puntajeEnContra = 0.0;
        
        LocalAtencion localDeAtencionCita = obtenerLocalDeAtencionCita(localesDeAtencionDisponibles);
        
        Beneficiario beneficiarioCita = obtenerBeneficiarioCita(beneficiariosAtender);
        
        puntajeEnContra += obtenerGradoAglomeracion(horario, localDeAtencionCita);
        
        return puntajeEnContra;
        
    }
}
