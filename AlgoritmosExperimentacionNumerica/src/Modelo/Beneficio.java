/*
 * El beneficio a ser reclamado
 */
package Modelo;

import java.time.LocalDate;
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
    
    private static List<BloqueHorario> CrearHorarios(Distribuidora dist, Padron pad){
        List<BloqueHorario> l = new ArrayList<>();
        // FOR 1 TO DOS SEMANAS
        //    A PARTIR DE LA FECHA INICIO
        //    PARA CADA LOCAL (HORARIO ATENCION) HACERLE BLOQUES HORARIO QUE PUEDA ATENDER
        
        return l;
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
