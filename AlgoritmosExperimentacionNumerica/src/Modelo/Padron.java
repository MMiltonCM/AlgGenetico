/*
 * Lista de Beneficiarios con sus datos para repartirles un beneficio
 */
package Modelo;

import Utils.CargarArchivos;
import java.util.List;

public class Padron {
    public List<Beneficiario> beneficiarios;
    public String descripcion;
    
    public Padron(String descripcion, String archivoPadron) {
        this.descripcion = descripcion;
        this.beneficiarios = CargarArchivos.CargarPadron(archivoPadron);
    }
}
