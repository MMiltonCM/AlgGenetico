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
import Modelo.Pais;
import PSO.AlgoritmoPSO;
import PSO.ParticulaPSO;
import Utils.CargarArchivos;
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
        // Data comun a los archivos. Ubicaciones geograficas del peru
        Pais peru = new Pais();
        CargarArchivos.CargarUbigeos("ubigeos.txt", peru);

        // NO BORRAR : EXPERIMENTO 
        /*
        Distribuidora dist = new Distribuidora("", "", peru);
        List<LocalAtencion> L = new ArrayList<>();
        for(Integer i = 0; i<3; i++) //En la data son 551, pero aqui cambio a gusto
            L.add( new LocalAtencion(i, 1, 1, null, 1, LocalTime.of(8, 0), LocalTime.of(19, 0)) );
        List<Beneficiario> LB = new ArrayList<>();
        for(Integer i = 0; i<7; i++)
            LB.add((new Beneficiario(i)));
        dist.setAgencias(L);
        LocalDate fechaInicio = LocalDate.of(2020, 10, 12);
        Padron P = new Padron("Padron Meramente Experimental", LB);
        Beneficio bonoExp = new Beneficio("Bono Experimentacion", dist, P, fechaInicio);
        AlgoritmoPSO alg = new AlgoritmoPSO(bonoExp, fechaInicio,1); //tercer argumento son dias
        alg.ejecutar(); */
        
        
        
        
        // Primero crearemos la empresa que distribuye el beneficio
        Distribuidora banco = new Distribuidora("Banco Exp", "agencias.txt",peru);
        
        // Ahora registramos un padron donde estaran los beneficiarios
        Padron personas = new Padron("Familias afectas economicamente", "padron_prueba.txt",peru);
        
        // Con la informacion construimos el beneficio creara los bloques de horarios posibles
        Beneficio bono = new Beneficio("Bono Experimentacion", banco, personas, LocalDate.of(2020,9,10));
        
        
        
        AlgoritmoPSO algPSO = new AlgoritmoPSO(bono, LocalDate.of(2020,9,10),30);
        algPSO.ejecutar();
        
        int tamanoPoblacion = 10;
        int numeroGeneraciones = 50;
        
        // Despues le brindamos esta informacion a nuestros algoritmos para que nos devuelvan un Calendario de citas
        AlgoritmoGenetico AG = new AlgoritmoGenetico(bono);
        Calendario calGenetico = AG.ejecutar(tamanoPoblacion, numeroGeneraciones);
        
        // Algoritmo 2
        
        // Imprimir metricas de algoritmo
        Printer.ReporteEstadisticas(AG, calGenetico); 
        
        // METRICAS: CUANDO ES LA ULTIMA CITA ASIGNADA
        // LA SUMA TOTAL DE TIEMPOS DESDE EL INICIO HASTA LA CITA
        
        
        
        
    }
    
}
