// Ubicación: src/main/java/Convertidordemonedas/service/ServicioConvertidorMonedas.java
package Convertidordemonedas.service;

import Convertidordemonedas.API.ConexionAPI;
import Convertidordemonedas.API.ModeloRespuestaAPI;
import java.io.IOException;
import java.util.Map;

public class ServicioConvertidorMonedas {

    private final ConexionAPI conexionAPI;

    public ServicioConvertidorMonedas(ConexionAPI conexionAPI) {
        this.conexionAPI = conexionAPI;
    }

    public double convert(double amount, String fromCurrency, String toCurrency) throws IOException, InterruptedException {
        System.out.println("ServicioConvertidorMonedas: Iniciando conversión...");

        ModeloRespuestaAPI ratesResponse = conexionAPI.getLatestExchangeRates(fromCurrency);

        if (ratesResponse == null || !"success".equals(ratesResponse.getResult())) {
            System.err.println("ServicioConvertidorMonedas: Error: No se pudieron obtener las tasas desde la API.");
            return -1.0;
        }

        Map<String, Double> conversionRates = ratesResponse.getConversion_rates();

        if (!conversionRates.containsKey(toCurrency)) {
            System.err.println("ServicioConvertidorMonedas: Error: Código de moneda de destino no válido.");
            return -1.0;
        }

        double rateTo = conversionRates.get(toCurrency);
        return amount * rateTo;
    }
}