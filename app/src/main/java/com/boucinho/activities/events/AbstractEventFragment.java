package com.boucinho.activities.events;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.boucinho.firebase.FirebaseOperator;
import com.boucinho.models.Event;

public abstract class AbstractEventFragment extends Fragment {

    protected EventFragmentListener mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResourceId(), container, false);
        initView(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (EventFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onViewSelected");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(mListener != null){
            mListener = null;
        }
    }

    protected abstract void initView(View view);
    protected abstract int getLayoutResourceId();

    public interface EventFragmentListener {
        Event getEvent();
        void updateEvent(Event event);
    }
}
