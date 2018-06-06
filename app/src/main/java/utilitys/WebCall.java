package utilitys;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Patterns;

import com.anagha.astrology.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by harsha on 4/9/2018.
 */

public class WebCall {
    public static final String SharedPreference_Name = "astro_details";

    private Context _context;
    public WebCall(Context activitymain) {
        _context = activitymain;
    }


    public void DialogForWifi_Enable_CloseDialog(String alertTitle, String alertDescription, int icon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(_context, R.style.AppCompatAlertDialogStyle);
        builder.setCancelable(false);
        builder.setTitle(alertTitle).
                setMessage(alertDescription).setIcon(icon).
                setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    public void EmptyDialog(String alertTitle, String alertDescription, int icon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(_context, R.style.AppCompatAlertDialogStyle);
        builder.setCancelable(false);
        builder.setTitle(alertTitle).setMessage(alertDescription).setIcon(icon).
                setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }



    public static boolean isEmailValid(String email) {
        //abc@sad.com
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();


      /*String  expression="^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();*/
    }



    public static boolean isPasswordValid(String password) {
        boolean isPasswordValid = false;
        //Minimum 6 characters at least 1 Alphabet, 1 Number and 1 Special Character:
        ///^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9!@#$%^&*()_+\-=\[\]{};:"\\|,.<>\/?]{6,16}$/
        // String passwordRegularExpression = "^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$";

        String expression =  "((?=.*\\d)(?=.*[A-Za-z])(?=.*[0-9]).{6,20})";
        CharSequence inputStr = password;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isPasswordValid = true;
        }
        return isPasswordValid;
    }

}
