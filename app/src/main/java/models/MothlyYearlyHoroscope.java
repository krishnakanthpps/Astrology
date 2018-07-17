package models;

public class MothlyYearlyHoroscope {
    private String status;
    private String message;
    private String moon;

    public MothlyYearlyHoroscope(String status, String message, String moon) {
        this.status = status;
        this.message = message;
        this.moon = moon;
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

    public String getMoon() {
        return moon;
    }

    public void setMoon(String moon) {
        this.moon = moon;
    }
}
