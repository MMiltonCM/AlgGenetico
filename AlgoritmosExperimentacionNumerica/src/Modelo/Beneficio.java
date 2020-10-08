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
        return l;
    }
}
