package models;

public class DailyPlanetResponse {
    private String status;
    private String message;
    private DailyPlanetResult result;

    public DailyPlanetResponse(String status, String message, DailyPlanetResult result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DailyPlanetResult getResult() {
        return result;
    }

    public void setResult(DailyPlanetResult result) {
        this.result = result;
    }
}
