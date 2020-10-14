/*
 * Persona beneficiada con un bono por tener ciertas caracteristicas
 */
package Modelo;

public class Beneficiario {
    public Integer idBeneficiario;
    public String DNI;
    public boolean flagDiscapacidad;
    // Agregar Ubigeo Region/Provincia/Distrito LIMALIMASANMIGUEL = 112035
    // Coordenadas por si acaso
    
    public Beneficiario(){}
    
    public Beneficiario(Integer id){
        idBeneficiario = id;
    }

    public Integer getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(Integer idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public boolean isFlagDiscapacidad() {
        return flagDiscapacidad;
    }

    public void setFlagDiscapacidad(boolean flagDiscapacidad) {
        this.flagDiscapacidad = flagDiscapacidad;
    }
    
    
}
