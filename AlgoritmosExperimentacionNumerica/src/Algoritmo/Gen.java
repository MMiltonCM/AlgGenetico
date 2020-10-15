/*
 * Valor a ser heredado para los cromosomas resultantes del cruce
 */
package Algoritmo;
enum TIPO_GEN{IDENTIFICADOR, VARIABLE}

public class Gen {
    public TIPO_GEN tipo;
    public int valor;
    
    public Gen(String tipo, int valor){
        this.tipo = TIPO_GEN.valueOf(tipo);
        this.valor = valor;
    }
}
