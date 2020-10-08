/*
 * Un espacio de tiempo donde un beneficiario puede recoger su beneficio
 */
package Modelo;

enum ESTADO_CITA{ATENDIDO, POR_ATENDER}

public class Cita {
    public BloqueHorario horario;
    public Beneficiario beneficiario;
    public ESTADO_CITA estado;
}
