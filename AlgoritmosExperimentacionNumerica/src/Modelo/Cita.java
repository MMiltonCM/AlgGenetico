/*
 * Un espacio de tiempo donde un beneficiario puede recoger su beneficio
 */
package Modelo;

import java.time.LocalDateTime;

enum ESTADO_CITA{ATENDIDO, POR_ATENDER}

public class Cita {
    public LocalAtencion local;
    public LocalDateTime horaInicio;
    public LocalDateTime horaFin;
    public Beneficiario beneficiario;
    public ESTADO_CITA estado;
}
