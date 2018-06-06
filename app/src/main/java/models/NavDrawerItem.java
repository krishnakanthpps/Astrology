package models;

/**
 * Created by Ravi on 29/07/15.
 */
public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private Integer titleIM;
    // private ImageView titleIM;
    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title, Integer integer) {
        this.showNotify = showNotify;
        this.title = title;
        this.titleIM = integer;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public Integer getTitleIM() {
        return titleIM;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageResource(Integer integer) {
        this.titleIM = integer;
    }
}
