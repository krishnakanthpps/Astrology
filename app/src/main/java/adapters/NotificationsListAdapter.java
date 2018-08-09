package adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anagha.astrology.R;

import java.util.List;

import models.NotificationListModel;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.MyViewHolder> {
    private Activity context;
    List<NotificationListModel> notificationListtData;
    NotificationsListAdapter.OnCardClickListner onCardClickListner;
    //NotificationsListAdapter.OnCardClickListner onCardClickListner;
    public NotificationsListAdapter(Context notificationsctx, List<NotificationListModel> notificationListresponse) {
        context = (Activity) notificationsctx;
        notificationListtData = notificationListresponse;
    }

    @Override
    public NotificationsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NotificationsListAdapter.MyViewHolder myViewHolder;
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.notification_list_row, parent, false);
        myViewHolder = new NotificationsListAdapter.MyViewHolder(itemLayoutView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myHolder, final int position) {
        NotificationsListAdapter.MyViewHolder myViewHolderview = myHolder;

        myViewHolderview.notificationTitle.setText(getItem(position).getNotification_title());
        myViewHolderview.notificationDescription.setText(getItem(position).getNotification_description());
        myViewHolderview.notificationDate.setText(getItem(position).getSchedule_time());

        myViewHolderview.mainCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardClickListner.OnCardClicked(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationListtData.size();
    }

    public NotificationListModel getItem(int position) {
        return notificationListtData.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mainCardView;
        TextView notificationTitle;
        TextView notificationDescription;
        TextView notificationDate;
        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mainCardView = itemLayoutView.findViewById(R.id.card_view);
            notificationTitle = itemLayoutView.findViewById(R.id.notificationTitle_TV);
            notificationDescription = itemLayoutView.findViewById(R.id.notificationDetails_TV);
            notificationDate = itemLayoutView.findViewById(R.id.notificationdate_TV);
        }
    }

    public interface OnCardClickListner {
        void OnCardClicked(View view, int position);
    }

    public void setOnCardClickListner(NotificationsListAdapter.OnCardClickListner onCardClickListner) {
        this.onCardClickListner = onCardClickListner;
    }
}
