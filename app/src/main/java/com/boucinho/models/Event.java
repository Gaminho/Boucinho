package com.boucinho.models;

import android.text.TextUtils;
import android.view.ViewGroup;

import com.boucinho.views.CardEvent;
import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Event {

    private String mID;
    private String mTitle;
    private String mDetails;
    private long mDate;
    private String mLocation = "";

    public Event() {
    }

    public Event(String title, String details, long date, String location) {
        mID = "";
        mTitle = title;
        mDetails = details;
        mDate = date;
        mLocation = location;
    }

    @Exclude
    public String getID() {
        return mID;
    }

    @Exclude
    public void setID(String ID) {
        mID = ID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDetails() {
        return mDetails;
    }

    public void setDetails(String details) {
        mDetails = details;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }

    @Exclude
    public void setDate(Date date) {
        mDate = date.getTime();
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    @Exclude
    public String getFriendlyDate(){
        if(this.mDate > 0){
            return new SimpleDateFormat("yyyy/MM/dd, E HH'h'mm", Locale.FRANCE).format(this.mDate);
        } else {
            return "Unknown";
        }
    }

    @Override
    public String toString() {
        return "Event{" +
                "mID='" + mID + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mDetails='" + mDetails + '\'' +
                ", mDate=" + mDate +
                ", mLocation='" + mLocation + '\'' +
                '}';
    }

    public static void verify(Event event) throws EventException {
        if(TextUtils.isEmpty(event.getTitle())){
            throw new EventException("Title can not be null");
        } else if(TextUtils.isEmpty(event.getDetails())){
            throw new EventException("Details can not be null");
        } else if(TextUtils.isEmpty(event.getLocation())){
            throw new EventException("Location can not be null");
        }
        else if (event.getDate() <= 0) {
            throw new EventException("Date can not be null");
        }
    }

    public static class EventException extends Exception {
        EventException(String message) {
            super(message);
        }
    }

    public static class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

        private List<Event> mEventList;
        private CardEvent.ClickOnEventListener mListener;

        public EventAdapter(List<Event> eventList) {
            this.mEventList = eventList;
        }

        @NonNull
        @Override
        public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new EventHolder(new CardEvent(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(@NonNull EventHolder holder, int position) {
            holder.mCardEvent.setEvent(mEventList.get(position));
            holder.mCardEvent.setClickOnEventListener(mListener);
        }

        @Override
        public int getItemCount() {
            return mEventList.size();
        }

        class EventHolder extends RecyclerView.ViewHolder {

            private CardEvent mCardEvent;

            EventHolder(@NonNull CardEvent itemView) {
                super(itemView);
                mCardEvent = itemView;
            }
        }

        public void setCardEventListener(CardEvent.ClickOnEventListener listener){
            mListener = listener;
        }
    }
}