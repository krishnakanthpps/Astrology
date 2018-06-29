package models;

public class ChakrasResult {
    private String status;
    private String message;
    private String dateofbirth;
    private String timeofbirth;
    private String rasi;
    private DashBoardResults result;

    public ChakrasResult(String status, String message, String dateofbirth, String timeofbirth, String rasi, DashBoardResults result) {
        this.status = status;
        this.message = message;
        this.dateofbirth = dateofbirth;
        this.timeofbirth = timeofbirth;
        this.rasi = rasi;
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

    public DashBoardResults getResult() {
        return result;
    }

    public void setResult(DashBoardResults result) {
        this.result = result;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getTimeofbirth() {
        return timeofbirth;
    }

    public void setTimeofbirth(String timeofbirth) {
        this.timeofbirth = timeofbirth;
    }

    public String getRasi() {
        return rasi;
    }

    public void setRasi(String rasi) {
        this.rasi = rasi;
    }
}
