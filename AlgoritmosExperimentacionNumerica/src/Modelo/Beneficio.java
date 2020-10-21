/*
 * El beneficio a ser reclamado
 */
package Modelo;

import Utils.Constantes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Beneficio {
    public String descripcion;
    public Distribuidora dist;
    public Padron pad;
    public LocalDate inicio;
    public List<BloqueHorario> horarios;
    
    public Beneficio(String descripcion, Distribuidora dist, Padron pad, LocalDate inicio){
        this.descripcion = descripcion;
        this.dist = dist;
        this.pad = pad;
        this.inicio = inicio;
        this.horarios = CrearHorarios(dist, pad);
    }
    
    private List<BloqueHorario> CrearHorarios(Distribuidora dist, Padron pad){
        List<BloqueHorario> h = new ArrayList<>();
        int cant_bloques = (int)(pad.beneficiarios.size()/dist.getAgencias().size());
        for(LocalAtencion la : dist.getAgencias()){
            int bloques_dia = la.getNumeroBloques();
            Constantes.numDias = (int)(cant_bloques/bloques_dia) + 1;
            for (int j=0; j <= (int)(cant_bloques/bloques_dia); j++){
                for(int k=0; k < bloques_dia; k++){
                    LocalTime start = la.horaInicioAtencion.plus(
                            Constantes.tiempoAtencion.multipliedBy(k));
                    LocalTime end = la.horaInicioAtencion.plus(
                            Constantes.tiempoAtencion.multipliedBy(k+1));
                    BloqueHorario bh = new BloqueHorario(la, 
                        LocalDateTime.of(inicio.plusDays(j), start),
                        LocalDateTime.of(inicio.plusDays(j), end));
                    h.add(bh);
                }
            }
        }
        
        return h;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Distribuidora getDist() {
        return dist;
    }

    public void setDist(Distribuidora dist) {
        this.dist = dist;
    }

    public Padron getPad() {
        return pad;
    }

    public void setPad(Padron pad) {
        this.pad = pad;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public List<BloqueHorario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<BloqueHorario> horarios) {
        this.horarios = horarios;
    }
}
