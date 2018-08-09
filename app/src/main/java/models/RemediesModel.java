package models;

public class RemediesModel {
    private String status;
    private String message;
    private String remedies;

    public RemediesModel(String status, String message, String remedies) {
        this.status = status;
        this.message = message;
        this.remedies = remedies;
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

    public String getRemedies() {
        return remedies;
    }

    public void setRemedies(String remedies) {
        this.remedies = remedies;
    }
}
