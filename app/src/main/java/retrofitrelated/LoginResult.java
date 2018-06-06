package retrofitrelated;

import models.Result;

/**
 * Created by harsha on 5/30/2018.
 */

public class LoginResult {
    private String status;
    private String message;
    private Result result;

    public LoginResult(String status, String message, Result result) {
        this.status = status;
        this.message = message;
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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
