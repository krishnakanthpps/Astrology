package models;

public class DailyPlanetResult {
    public String daily_Moon;
    public String daily_Jupiter;
    public String daily_Mercury;
    public String daily_Sun;
    public String daily_Mars;
    public String daily_Venus;
    public String daily_Saturn;

    public DailyPlanetResult(String daily_Moon, String daily_Jupiter, String daily_Mercury, String daily_Sun, String daily_Mars, String daily_Venus, String daily_Saturn) {
        this.daily_Moon = daily_Moon;
        this.daily_Jupiter = daily_Jupiter;
        this.daily_Mercury = daily_Mercury;
        this.daily_Sun = daily_Sun;
        this.daily_Mars = daily_Mars;
        this.daily_Venus = daily_Venus;
        this.daily_Saturn = daily_Saturn;
    }

    public String getDaily_Moon() {
        return daily_Moon;
    }

    public void setDaily_Moon(String daily_Moon) {
        this.daily_Moon = daily_Moon;
    }

    public String getDaily_Jupiter() {
        return daily_Jupiter;
    }

    public void setDaily_Jupiter(String daily_Jupiter) {
        this.daily_Jupiter = daily_Jupiter;
    }

    public String getDaily_Mercury() {
        return daily_Mercury;
    }

    public void setDaily_Mercury(String daily_Mercury) {
        this.daily_Mercury = daily_Mercury;
    }

    public String getDaily_Sun() {
        return daily_Sun;
    }

    public void setDaily_Sun(String daily_Sun) {
        this.daily_Sun = daily_Sun;
    }

    public String getDaily_Mars() {
        return daily_Mars;
    }

    public void setDaily_Mars(String daily_Mars) {
        this.daily_Mars = daily_Mars;
    }

    public String getDaily_Venus() {
        return daily_Venus;
    }

    public void setDaily_Venus(String daily_Venus) {
        this.daily_Venus = daily_Venus;
    }

    public String getDaily_Saturn() {
        return daily_Saturn;
    }

    public void setDaily_Saturn(String daily_Saturn) {
        this.daily_Saturn = daily_Saturn;
    }
}
