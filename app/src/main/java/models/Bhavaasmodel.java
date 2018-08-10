package models;

public class Bhavaasmodel {
    private String status;
    private String message;
    private String bhaavasreg_information;
    private String hundredpercentage_information;
    private String regular_information;
    private String percentage;
    private String lagna_result;


    public Bhavaasmodel(String status, String message, String bhaavasreg_information, String hundredpercentage_information, String regular_information, String percentage, String lagna_result) {
        this.status = status;
        this.message = message;
        this.bhaavasreg_information = bhaavasreg_information;
        this.hundredpercentage_information = hundredpercentage_information;
        this.regular_information = regular_information;
        this.percentage = percentage;
        this.lagna_result = lagna_result;
    }

    public String getLagna_result() {
        return lagna_result;
    }

    public void setLagna_result(String lagna_result) {
        this.lagna_result = lagna_result;
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

    public String getHundredpercentage_information() {
        return hundredpercentage_information;
    }

    public void setHundredpercentage_information(String hundredpercentage_information) {
        this.hundredpercentage_information = hundredpercentage_information;
    }

    public String getRegular_information() {
        return regular_information;
    }

    public void setRegular_information(String regular_information) {
        this.regular_information = regular_information;
    }

    public String getBhaavasreg_information() {
        return bhaavasreg_information;
    }

    public void setBhaavasreg_information(String bhaavasreg_information) {
        this.bhaavasreg_information = bhaavasreg_information;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
