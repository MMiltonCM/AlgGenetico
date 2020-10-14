package Utils;


public class Coordenadas {
    
    public static double distanciaEuclidiana(Integer G1S, Integer M1S, Integer S1S,
            Integer G1W, Integer M1W, Integer S1W,
            Integer G2S, Integer M2S, Integer S2S,
            Integer G2W, Integer M2W, Integer S2W){
        
        // (GMS)(12)(SW)
        //  Grados, minutos, segundos
        //  1 o 2: de la posicion 1 o posicion 2
        //  S o W: referido a hemisferio oeste (west) o este(east)
        
        double difS = (G1S-G2S) + (M1S-M2S)/60 + (S1S-S2S)/3600;
        double difW = (G1W-G2W) + (M1W-M2W)/60 + (S1W-S2W)/3600;
        return Math.sqrt( difS*difS + difW*difW );
    }
}
