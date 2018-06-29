package retrofitrelated;

import models.Userdetailview;

public class ProfileViewResultResponse {
    private String status;
    private String message;
    private Userdetailview userdetailview;

    public ProfileViewResultResponse(String status, String message, Userdetailview userDetailView) {
        this.status = status;
        this.message = message;
        this.userdetailview = userDetailView;
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

    public Userdetailview getUserDetailView() {
        return userdetailview;
    }

    public void setUserDetailView(Userdetailview userDetailView) {
        this.userdetailview = userDetailView;
    }
}
