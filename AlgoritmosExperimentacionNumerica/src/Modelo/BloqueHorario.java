/*
 * Un espacio temporal en un local para ir a recoger un beneficio
 */
package Modelo;

import java.time.LocalDateTime;

public class BloqueHorario {
    public int idBloqueHorario;
    public int numeroBeneficiariosAsignados;
    // TAMANO DE BLOQUE PARAMETRIZABLE
    public LocalAtencion local; // Oficina BCP La Molina
    public LocalDateTime inicio; // 10 de Oct 2020 5pm
    public LocalDateTime fin; //10 de Oct 2020 6pm
    
    public static int contador = 0;

    public LocalAtencion getLocal() {
        return local;
    }

    public void setLocal(LocalAtencion local) {
        this.local = local;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }
    
    /**
     * @return the numeroBeneficiariosAsignados
     */
    public int getNumeroBeneficiariosAsignados() {
        return numeroBeneficiariosAsignados;
    }

    /**
     * @param numeroBeneficiariosAsignados the numeroBeneficiariosAsignados to set
     */
    public void setNumeroBeneficiariosAsignados(int numeroBeneficiariosAsignados) {
        this.numeroBeneficiariosAsignados = numeroBeneficiariosAsignados;
    }

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
    
    public void setIdBloqueHorario(){
        this.idBloqueHorario = ++contador;
    }

    public BloqueHorario(){};
    
    public BloqueHorario(LocalAtencion local, LocalDateTime inicio, LocalDateTime fin) {
        this.local = local;
        this.inicio = inicio;
        this.fin = fin;
    }
    
    
}
