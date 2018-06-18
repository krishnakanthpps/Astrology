package models;

public class NavamsaResult {
    String housename;
    String housevalues;

    public NavamsaResult(String housename, String housevalues) {
        this.housename = housename;
        this.housevalues = housevalues;
    }

    public String getHousename() {
        return housename;
    }

    public void setHousename(String housename) {
        this.housename = housename;
    }

    public String getHousevalues() {
        return housevalues;
    }

    public void setHousevalues(String housevalues) {
        this.housevalues = housevalues;
    }
}
