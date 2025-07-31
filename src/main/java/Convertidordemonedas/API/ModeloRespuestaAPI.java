// Ubicación: src/main/java/Convertidordemonedas/API/ModeloRespuestaAPI.java
package Convertidordemonedas.API;

import java.util.Map;

public class ModeloRespuestaAPI {
    private String result;
    private String base_code;
    private Map<String, Double> conversion_rates;

    // Getters y Setters
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getBase_code() {
        return base_code;
    }

    public void setBase_code(String base_code) {
        this.base_code = base_code;
    }

    public Map<String, Double> getConversion_rates() {
        return conversion_rates;
    }

    public void setConversion_rates(Map<String, Double> conversion_rates) {
        this.conversion_rates = conversion_rates;
    }
}
