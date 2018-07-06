package models;

/**
 * Created by support on 11/25/2015.
 */
public class SettingsOptions {
    private String optionName;
    private Integer optionId;
    private Integer optionImage;

    public SettingsOptions(Integer optionId,String optionName,Integer optionImage) {
        this.optionName = optionName;
        this.optionId = optionId;
        this.optionImage = optionImage;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public Integer getOptionImage() {
        return optionImage;
    }

    public void setOptionImage(Integer optionImage) {
        this.optionImage = optionImage;
    }
}
