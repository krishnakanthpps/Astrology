package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.anagha.astrology.R;

import java.util.ArrayList;

import models.SettingsOptions;

/**
 * Created by support on 11/25/2015.
 */
public class SettingOptionsAdapter extends BaseAdapter {
    private Context _context;
    private ArrayList<SettingsOptions> optionsModel;

    public SettingOptionsAdapter(Context _context, ArrayList<SettingsOptions> optionsModel) {
        this._context = _context;
        this.optionsModel = optionsModel;
    }

    @Override
    public int getCount() {
        return optionsModel.size();
    }

    @Override
    public SettingsOptions getItem(int position) {
        return optionsModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) _context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.user_options_list_row, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.useroptions_listrow_textView);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.useroptions_listrow_imageView);
        textView.setText(optionsModel.get(position).getOptionName());
        // change the icon for Windows and iPhone
        imageView.setImageResource(optionsModel.get(position).getOptionImage());
        return rowView;
    }



}
