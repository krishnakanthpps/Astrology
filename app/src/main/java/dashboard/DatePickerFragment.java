package dashboard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anagha.astrology.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
       /* return new DatePickerDialog(getActivity(), this, year, month, day);*/

        return new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        TextView dob= getActivity().findViewById(R.id.dateofbirthEt);
        int UpdatedMonth = view.getMonth() + 1;
        dob.setTextColor(getResources().getColor(R.color.black));
        dob.setText(view.getDayOfMonth() + "-" + UpdatedMonth + "-" + view.getYear());
        String date = "Selected Date : " + view.getDayOfMonth() + "-" + UpdatedMonth + "-" + view.getYear();
        Toast.makeText(getActivity(), date, Toast.LENGTH_LONG).show();
    }
}