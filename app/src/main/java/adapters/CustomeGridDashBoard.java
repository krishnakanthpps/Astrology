package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.anagha.astrology.R;

import java.util.ArrayList;
import java.util.List;

import models.Item;


public class CustomeGridDashBoard extends BaseAdapter {
	private List<Item> items = new ArrayList<Item>();
	private LayoutInflater inflater;

	public CustomeGridDashBoard(Context context, List<Item> al_Items) {
		inflater = LayoutInflater.from(context);
		items = al_Items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int i) {
		return items.get(i);
	}

	@Override
	public long getItemId(int i) {
		return items.get(i).drawableId;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		View v = view;
		ImageView picture;
		LinearLayout lt;
		if (v == null) {
			v = inflater.inflate(R.layout.astro_main_dashboard_gridrow,
					viewGroup, false);
			v.setTag(R.id.summer_header_name,
					v.findViewById(R.id.summer_header_name));
			v.setTag(R.id.grid_row_LT, v.findViewById(R.id.grid_row_LT));
		}
		picture = (ImageView) v.getTag(R.id.summer_header_name);
		lt = (LinearLayout) v.getTag(R.id.grid_row_LT);
		Item item = (Item) getItem(i);
		picture.setImageResource(item.drawableId);
		/*if (i == 0) {
			lt.setBackgroundResource(R.drawable.dashboard_future);
		}
		if (i == 1) {
			lt.setBackgroundResource(R.drawable.dashboard_yearly);
		}
		if (i == 2) {
			lt.setBackgroundResource(R.drawable.dashboard_weekly);
		}
		if (i == 3) {
			lt.setBackgroundResource(R.drawable.new_cal_dashboard);
		}
		if (i == 4) {
			lt.setBackgroundResource(R.drawable.dashboard_proven);
		}
		if (i == 5) {
			lt.setBackgroundResource(R.drawable.dashboard_panchangam);
		}
		if (i == 6) {
			lt.setBackgroundResource(R.drawable.dashboard_yearly);
		}
		if (i == 7) {
			lt.setBackgroundResource(R.drawable.dashboard_future);
		}*/
		/*
		 * if (i == 8) {
		 * lt.setBackgroundResource(R.drawable.dashboard_panchangam); } if (i ==
		 * 9) { lt.setBackgroundResource(R.drawable.dashboard_calendar); }
		 */
		return v;
	}
}
