/*
 * Local donde se distribuye un beneficio de una distribuidora
 */
package Modelo;

import Utils.Constantes;
import Utils.Hora;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class LocalAtencion {
    
    public Integer idLocalAtencion;
    public double latitud;
    public double longitud;
    public String ubigeo;
    public Integer capacidad;
    public LocalTime horaInicioAtencion;
    public LocalTime horaFinAtencion;
    public Integer numeroBloques; //No es atributo de BD
    public List<LocalTime> bloques; //No es atributo de BD
    public List<BloqueHorario> bloquesHorarios;
    
    public static Integer contador = 0;
    
    public LocalAtencion(Integer idLocalAtencion, double latitud, 
            double longitud, String ubigeo, Integer capacidad, 
            LocalTime horaInicioAtencion, LocalTime horaFinAtencion) {
        this.idLocalAtencion = idLocalAtencion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.ubigeo = ubigeo;
        this.capacidad = capacidad;
        this.horaInicioAtencion = horaInicioAtencion;
        this.horaFinAtencion = horaFinAtencion;
        
        this.numeroBloques = Hora.discretizar(horaInicioAtencion,
                horaFinAtencion,Constantes.tiempoAtencion);
        this.bloques = new ArrayList<>();
        for(Integer i = 0; i<numeroBloques; i++)
            bloques.add( horaInicioAtencion.plus(
                    Constantes.tiempoAtencion.multipliedBy((long)i)) );
        
    }
    
    public void crearBloquesHorarios(LocalDate diaInicio, Integer dias){
        bloquesHorarios = new ArrayList<BloqueHorario>();
        
        LocalDateTime bloqueInicial = bloques.get(0).atDate(diaInicio);;
        LocalDateTime bloqueActual = bloqueInicial;
        
        for(Integer i = 0; i<dias; i++){
            
            for(Integer j = 0; j<numeroBloques; j++){
                BloqueHorario BH = new BloqueHorario();
                BH.setInicio(bloqueActual);
                LocalDateTime bloqueFin = bloqueActual.plus(Constantes.tiempoAtencion);
                BH.setFin(bloqueFin);
                BH.setLocal(this);
                BH.setNumeroBeneficiariosAsignados(0);
                BH.setIdBloqueHorario();
                bloquesHorarios.add(BH);
                
                bloqueActual = bloqueFin;
            }
                bloqueActual = bloqueInicial.plusDays(i+1);
        }
    }
    
    public LocalAtencion(){
        bloquesHorarios = new ArrayList<BloqueHorario>();
    }
    
    public Integer getIdLocalAtencion() {
        return idLocalAtencion;
    }

    public void setIdLocalAtencion(Integer idLocalAtencion) {
        this.idLocalAtencion = idLocalAtencion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public LocalTime getHoraInicioAtencion() {
        return horaInicioAtencion;
    }

    public void setHoraInicioAtencion(LocalTime horaInicioAtencion) {
        this.horaInicioAtencion = horaInicioAtencion;
    }

    public LocalTime getHoraFinAtencion() {
        return horaFinAtencion;
    }

    public void setHoraFinAtencion(LocalTime horaFinAtencion) {
        this.horaFinAtencion = horaFinAtencion;
    }

    public Integer getNumeroBloques() {
        return numeroBloques;
    }

    public void setNumeroBloques(Integer numeroBloques) {
        this.numeroBloques = numeroBloques;
    }

    public List<LocalTime> getBloques() {
        return bloques;
    }

    public void setBloques(List<LocalTime> bloques) {
        this.bloques = bloques;
    }
    
}
