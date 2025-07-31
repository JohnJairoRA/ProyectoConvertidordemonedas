// Ubicación: src/main/java/Convertidordemonedas/API/ConexionAPI.java
package Convertidordemonedas.API;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConexionAPI {

    // Usa variables de entorno o archivos de configuración para mayor seguridad.
    private static final String API_KEY = "8002b0351dcc13e3dedab634"; // Esta es una clave de ejemplo
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public ModeloRespuestaAPI getLatestExchangeRates(String baseCurrency)
            throws IOException, InterruptedException {

        String fullUrl = BASE_URL + API_KEY + "/latest/" + baseCurrency;
        System.out.println("ConexionAPI: Intentando conectar a: " + fullUrl);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println("ConexionAPI: Conexión exitosa. Código: " + response.statusCode());
            return new Gson().fromJson(response.body(), ModeloRespuestaAPI.class);
        } else {
            System.err.println("ConexionAPI: Error en la solicitud. Código: " + response.statusCode());
            System.err.println("ConexionAPI: Cuerpo del error: " + response.body());
            return null;
        }
    }
}