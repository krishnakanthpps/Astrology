package models;

public class Janma {
    private String HouseName;
    private String HouseValue;

    public Janma(String houseName, String houseValue) {
        HouseName = houseName;
        HouseValue = houseValue;
    }

    public String getHouseName() {
        return HouseName;
    }

    public void setHouseName(String houseName) {
        HouseName = houseName;
    }

    public String getHouseValue() {
        return HouseValue;
    }

    public void setHouseValue(String houseValue) {
        HouseValue = houseValue;
    }
}
