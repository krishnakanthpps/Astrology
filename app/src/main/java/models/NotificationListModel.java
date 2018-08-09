package models;

public class NotificationListModel {
    private String not_id;
    private String notification_type;
    private String notification_title;
    private String notification_description;
    private String schedule_time;

    public NotificationListModel(String not_id, String notification_type, String notification_title, String notification_description, String schedule_time) {
        this.not_id = not_id;
        this.notification_type = notification_type;
        this.notification_title = notification_title;
        this.notification_description = notification_description;
        this.schedule_time = schedule_time;
    }

    public String getNot_id() {
        return not_id;
    }

    public void setNot_id(String not_id) {
        this.not_id = not_id;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    public String getNotification_title() {
        return notification_title;
    }

    public void setNotification_title(String notification_title) {
        this.notification_title = notification_title;
    }

    public String getNotification_description() {
        return notification_description;
    }

    public void setNotification_description(String notification_description) {
        this.notification_description = notification_description;
    }

    public String getSchedule_time() {
        return schedule_time;
    }

    public void setSchedule_time(String schedule_time) {
        this.schedule_time = schedule_time;
    }
}
