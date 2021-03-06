/*
 * Persona beneficiada con un bono por tener ciertas caracteristicas
 */
package Modelo;

public class Beneficiario {
    public int idBeneficiario;
    public String DNI;
    public boolean flagDiscapacidad;
    private boolean flagAdultoMayor;
    public Ubigeo ubigeo;
    
    public Beneficiario(){}

    public Beneficiario(Integer idBeneficiario, String DNI, boolean flagDiscapacidad, 
            boolean flagAdultoMayor, Ubigeo ubigeo) {
        this.idBeneficiario = idBeneficiario;
        this.DNI = DNI;
        this.flagDiscapacidad = flagDiscapacidad;
        this.flagAdultoMayor = flagAdultoMayor;
        this.ubigeo = ubigeo;
    }
    
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

    /**
     * @return the flagAdultoMayor
     */
    public boolean isFlagAdultoMayor() {
        return flagAdultoMayor;
    }

    /**
     * @param flagAdultoMayor the flagAdultoMayor to set
     */
    public void setFlagAdultoMayor(boolean flagAdultoMayor) {
        this.flagAdultoMayor = flagAdultoMayor;
    }
    
    
}
