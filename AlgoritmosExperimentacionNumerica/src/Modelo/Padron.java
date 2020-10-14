/*
 * Lista de Beneficiarios con sus datos para repartirles un beneficio
 */
package Modelo;

import Utils.CargarArchivos;
import java.util.List;

public class Padron {
    public List<Beneficiario> beneficiarios;
    public String descripcion;
    
    public Padron(String descripcion, List<Beneficiario> beneficiarios){
        this.beneficiarios = beneficiarios;
        this.descripcion = descripcion;
    }
    
    public Padron(String descripcion, String archivoPadron,Pais p) {
        this.descripcion = descripcion;
        this.beneficiarios = CargarArchivos.CargarPadron(archivoPadron,p);
    }

    public List<Beneficiario> getBeneficiarios() {
        return beneficiarios;
    }

    public void setBeneficiarios(List<Beneficiario> beneficiarios) {
        this.beneficiarios = beneficiarios;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
