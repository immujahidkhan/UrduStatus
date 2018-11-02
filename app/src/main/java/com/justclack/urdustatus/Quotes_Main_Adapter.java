package com.justclack.urdustatus;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class Quotes_Main_Adapter extends RecyclerView.Adapter<Quotes_Main_Adapter.MyView> {

    ArrayList<QuotesModelClass> list;
    Activity context;
    Typeface typeface, fontawesome;
    int RC_IMG_CHOOSE = 1212;

    public Quotes_Main_Adapter(ArrayList<QuotesModelClass> list, Activity context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.main_card_view, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new MyView(rootView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, int position) {
        final String readMore = " Read more Quotes at: https://play.google.com/store/apps/details?id=" + context.getPackageName();
        //typeface = Typeface.createFromAsset(context.getAssets(), "NotoSerif-Regular.ttf");
        //fontawesome = Typeface.createFromAsset(context.getAssets(), "fontawesome.ttf");
        final QuotesModelClass data = list.get(position);
        final String txt_title = data.getTitle().replace("'","").replace("'", "").replace("we're", "we are").replace("We're", "We are").replace("don't", "do not").replace("it's", "it is").replace("It's", "It is").replace("doesn't", "does not").replace("didn't", "did not").replace("isn't", "is not");
        holder.title.setTypeface(typeface);
        holder.title.setText(data.getTitle());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyView extends RecyclerView.ViewHolder {

        TextView title, share, fav;
        View view;

        public MyView(View itemView) {
            super(itemView);
            view = itemView;
            title = itemView.findViewById(R.id.title);
            share = itemView.findViewById(R.id.share);
            fav = itemView.findViewById(R.id.fav);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                title.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            }
        }
    }

}