/*
 * Un espacio temporal en un local para ir a recoger un beneficio
 */
package Modelo;

import java.time.LocalDateTime;

public class BloqueHorario {

    /**
     * @return the idBloqueHorario
     */
    public int getIdBloqueHorario() {
        return idBloqueHorario;
    }

    /**
     * @param idBloqueHorario the idBloqueHorario to set
     */
    public void setIdBloqueHorario(int idBloqueHorario) {
        this.idBloqueHorario = idBloqueHorario;
    }
    private int idBloqueHorario;

    public BloqueHorario(){};
    
    public BloqueHorario(LocalAtencion local, LocalDateTime inicio, LocalDateTime fin) {
        this.local = local;
        this.inicio = inicio;
        this.fin = fin;
    }
    
    // TAMANO DE BLOQUE PARAMETRIZABLE
    public LocalAtencion local; // Oficina BCP La Molina
    public LocalDateTime inicio; // 10 de Oct 2020 5pm
    public LocalDateTime fin; //10 de Oct 2020 6pm
}
