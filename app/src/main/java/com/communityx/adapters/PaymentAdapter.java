package com.communityx.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.communityx.R;
import com.communityx.utils.AppConstant;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.EventHolder> implements AppConstant {
    private LayoutInflater mLayoutInflater;
    private Activity mActivity;
    private ArrayList<String> mCardList;
    private OnCardCheckedListener cardCheckedListener;

    public PaymentAdapter(ArrayList<String> cardList, Activity activity) {
     this.mCardList =  cardList;
     this.mActivity = activity;
     this.mLayoutInflater = LayoutInflater.from(activity);
    }

    public void setCardCheckedListener(OnCardCheckedListener cardCheckedListener) {
        this.cardCheckedListener = cardCheckedListener;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_saved_card, viewGroup, false);
        return new PaymentAdapter.EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder eventHolder, int viewType) {
        eventHolder.radioSavedButton.setChecked(false);
        eventHolder.setRadioSavedButton();
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.radio_saved_card_button)
        RadioButton radioSavedButton;
        @BindView(R.id.linear_saved_card)
        LinearLayout linearSavedCard;
        @BindView(R.id.text_saved_amount)
        TextView textSavedCard;

        public EventHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setRadioSavedButton() {
            radioSavedButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(cardCheckedListener != null) cardCheckedListener.onCardChecked(isChecked,radioSavedButton);
                if(isChecked) {
                    linearSavedCard.setVisibility(View.VISIBLE);
                    textSavedCard.setVisibility(View.VISIBLE);
                } else {
                    linearSavedCard.setVisibility(View.GONE);
                    textSavedCard.setVisibility(View.GONE);
                }
            });
        }
    }

    public interface OnCardCheckedListener{
        void onCardChecked(boolean isChecked, RadioButton radioButton);
    }
}
