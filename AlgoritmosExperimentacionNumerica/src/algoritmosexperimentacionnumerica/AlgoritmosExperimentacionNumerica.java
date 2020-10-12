/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmosexperimentacionnumerica;

import Algoritmo.AlgoritmoGenetico;
import Modelo.Beneficiario;
import Modelo.Beneficio;
import Modelo.Calendario;
import Modelo.Distribuidora;
import Modelo.LocalAtencion;
import Modelo.Padron;
import PSO.AlgoritmoPSO;
import PSO.ParticulaPSO;
import Utils.Hora;
import Utils.Printer;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diego Paredes
 */
public class AlgoritmosExperimentacionNumerica {

    public static void main(String[] args) {
        // NO BORRAR : EXPERIMENTO
        
        Distribuidora dist = new Distribuidora("", "");
        List<LocalAtencion> L = new ArrayList<>();
        for(Integer i = 0; i<551; i++)
            L.add( new LocalAtencion(i, 1, 1, null, 1, LocalTime.of(8, 0), LocalTime.of(19, 0)) );
        List<Beneficiario> LB = new ArrayList<>();
        for(Integer i = 0; i<500000; i++)
            LB.add((new Beneficiario(i)));
        dist.setAgencias(L);
        LocalDate fechaInicio = LocalDate.of(2020, 10, 12);
        Padron P = new Padron("Padron Meramente Experimental", LB);
        Beneficio bonoExp = new Beneficio("Bono Experimentacion", dist, P, fechaInicio);
        AlgoritmoPSO alg = new AlgoritmoPSO(bonoExp, fechaInicio,30); //tercer argumento son dias
        alg.ejecutar();
        
        // Primero crearemos la empresa que distribuye el beneficio
        Distribuidora banco = new Distribuidora("Banco Exp", "agentes.txt");
        
        // Ahora registramos un padron donde estaran los beneficiarios
        Padron personas = new Padron("Familias afectas economicamente", "padron.txt");
        
        // Con la informacion construimos el beneficio creara los bloques de horarios posibles
        Beneficio bono = new Beneficio("Bono Experimentacion", banco, personas, LocalDate.of(2020,9,10));
        
        // Despues le brindamos esta informacion a nuestros algoritmos para que nos devuelvan un Calendario de citas
        AlgoritmoGenetico AG = new AlgoritmoGenetico(bono);
        Calendario calGenetico = AG.ejecutar();
        
        // Algoritmo 2
        
        // Imprimir metricas de algoritmo
        Printer.ReporteEstadisticas(AG, calGenetico); 
        
        // METRICAS: CUANDO ES LA ULTIMA CITA ASIGNADA
        // LA SUMA TOTAL DE TIEMPOS DESDE EL INICIO HASTA LA CITA
    }
    
}
