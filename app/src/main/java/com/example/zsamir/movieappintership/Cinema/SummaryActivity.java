package com.example.zsamir.movieappintership.Cinema;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.R;

public class SummaryActivity extends BaseActivity {

    private String[] content;

    private TextView name;
    private TextView movie;
    private TextView date;
    private TextView tickets;
    private TextView seats;
    private TextView price;

    private Button payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        setTitle("Tickets");

        setUpViews();

        if(getIntent().hasExtra("CONTENT")){
            content = getIntent().getStringExtra("CONTENT").split("~");
            setUpData();
        }

    }

    private void setUpData() {

        String text = "<font color="+"#898885"+">"+name.getText()+" </font> <font color="+"#7ff8ca00"+">"+content[0]+"</font>";
        name.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);

        text = "<font color="+"#898885"+">"+movie.getText()+" </font> <font color="+"#7ff8ca00"+">"+content[1]+"</font>";
        movie.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);

        text = "<font color="+"#898885"+">"+date.getText()+" </font> <font color="+"#7ff8ca00"+">"+content[2]+"</font>";
        date.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);

        text = "<font color="+"#898885"+">"+tickets.getText()+" </font> <font color="+"#7ff8ca00"+">"+content[3]+"</font>";
        tickets.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);

        text = "<font color="+"#898885"+">"+seats.getText()+" </font> <font color="+"#7ff8ca00"+">"+content[4]+"</font>";
        seats.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);

        text = "<font color="+"#898885"+">"+price.getText()+" </font> <font color="+"#7ff8ca00"+">"+content[5]+"</font>";
        price.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SummaryActivity.this,PaymentActivity.class);
                i.putExtra("CONTENT",getIntent().getStringExtra("CONTENT"));
                startActivity(i);
            }
        });

    }

    private void setUpViews() {
        name = (TextView)findViewById(R.id.summary_name);
        movie = (TextView)findViewById(R.id.summary_movie);
        date = (TextView)findViewById(R.id.summary_date);
        tickets = (TextView)findViewById(R.id.summary_tickets);
        seats = (TextView)findViewById(R.id.summary_seats);
        price =(TextView)findViewById(R.id.summary_total);

        payment = (Button)findViewById(R.id.proceed);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

}
