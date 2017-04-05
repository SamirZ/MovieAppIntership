package com.example.zsamir.movieappintership.Cinema;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.zsamir.movieappintership.BaseActivity;
import com.example.zsamir.movieappintership.Firebase.CinemaSeat;
import com.example.zsamir.movieappintership.Firebase.FirebaseUtils;
import com.example.zsamir.movieappintership.R;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.net.RequestOptions;
import com.stripe.exception.AuthenticationException;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends BaseActivity {

    public static final String PUBLISHABLE_KEY_TEST = "pk_test_zE1ULZbOXShTdmPbVv4512s7";
    public static final String SECRET_KEY_TEST = "sk_test_ISToKmFs8kJcZsgsWdvnaHE3";
    //public static final String PUBLISHABLE_KEY_LIVE = "pk_live_SmYfjni1QmSnviqBaMVA7YKz";
    //public static final String SECRET_KEY_LIVE = "sk_live_theshnqdAkJ5WUVQfRHKqFOp";

    public String desc;
    public String amount;
    public Card card;
    public Token tok;
    private int playTimeId;
    private String day;
    private ProgressDialog progress;
    private ToggleButton t1;
    private ToggleButton t2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        t1 = (ToggleButton) findViewById(R.id.visa);
        t2 = (ToggleButton) findViewById(R.id.mastercard);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.setChecked(true);
                t1.setTextColor(getResources().getColor(R.color.colorAccent));
                t2.setChecked(false);
                t2.setTextColor(getResources().getColor(R.color.colorMovieItemText));
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t2.setChecked(true);
                t2.setTextColor(getResources().getColor(R.color.colorAccent));
                t1.setChecked(false);
                t1.setTextColor(getResources().getColor(R.color.colorMovieItemText));
            }
        });



        if(getIntent().hasExtra("CONTENT")){
            String[] content = getIntent().getStringExtra("CONTENT").split("~");
            //size 5

            desc = "Charge for: "+content[0]
                    +", Movie: "+content[1]
                    +", Date: "+content[2]
                    +", Num of tickets: "+content[3]+
                    ", Seats:"+content[4]+".";
            Log.d("Content",desc);

            String[] seatArray = getIntent().getStringExtra("CONTENT").split("~")[4].split(",");
            String time = getIntent().getStringExtra("CONTENT").split("~")[2].split(" - ")[1];
            for (String s:seatArray) {
                Log.d("Content",time);
                Log.d("Content",s);

            }
            amount = content[5];
            playTimeId = Integer.parseInt(content[6]);
            day = content[7];

            Button purchase =  (Button) findViewById(R.id.submit);
            purchase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitCard();
                }
            });
        }
    }

    public void submitCard() {

        progress = new ProgressDialog(this,ProgressDialog.THEME_HOLO_DARK);
        progress.setMessage("Processing...");
        progress.setCancelable(false);
        progress.show();
        EditText cardholderName = (EditText) findViewById(R.id.username);
        EditText cardNumberField = (EditText) findViewById(R.id.card_number);
        EditText expirationDate = (EditText) findViewById(R.id.expiration_date);
        EditText cvcField = (EditText) findViewById(R.id.security_code);



        cardholderName.getBackground().setColorFilter(getResources().getColor(R.color.colorMovieItemText), PorterDuff.Mode.SRC_IN);
        cardNumberField.getBackground().setColorFilter(getResources().getColor(R.color.colorMovieItemText), PorterDuff.Mode.SRC_IN);
        expirationDate.getBackground().setColorFilter(getResources().getColor(R.color.colorMovieItemText), PorterDuff.Mode.SRC_IN);
        cvcField.getBackground().setColorFilter(getResources().getColor(R.color.colorMovieItemText), PorterDuff.Mode.SRC_IN);

        //4 2 4 2 4 2 4 2 4 2 4  2  4  2  4  2
        //0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
        Log.d("INPUT NUM",cardNumberField.getText().toString());
        Log.d("INPUT NUM", String.valueOf(cardNumberField.getText().toString().length()));
        if(cardNumberField.getText().toString().length()==16){

            if(expirationDate.getText().length()==5 && expirationDate.getText().toString().contains("/")) {
                if (expirationDate.getText().toString().split("/")[0].length() == 2
                        && expirationDate.getText().toString().split("/")[1].length() == 2) {
                    if(cvcField.getText().toString().length()==3){

                        card = new Card(
                                cardNumberField.getText().toString(),
                                Integer.valueOf(expirationDate.getText().toString().split("/")[0]),
                                Integer.valueOf(expirationDate.getText().toString().split("/")[1]),
                                cvcField.getText().toString()
                        );
                        card.setName(cardholderName.getText().toString());

                        if (!card.validateCard()) {
                            //     something went wrong, whoops! add some error messages for this messup
                            Toast.makeText(this, "Card is not valid", Toast.LENGTH_SHORT).show();
                        }else{
                            createToken(card);
                        }

                    }else{
                        Toast.makeText(this, "Incorrect CVC", Toast.LENGTH_SHORT).show();
                    }

                }
            }else{
                Toast.makeText(this, "Wrong date format please use MM/YY", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Please enter the correct card number", Toast.LENGTH_SHORT).show();
        }


        //card = new Card("5555555555554444", 12, 20, "123");
        // Log.d("CVC", card.getCVC());
        //Log.d("NUM", "created charge");
        //Log.d("NUM", "4242424242424242"+ 12+ 20+ "123");


    }

    private void createToken(Card card) {
        try {
            Stripe stripe = new Stripe(this, PUBLISHABLE_KEY_TEST);
            stripe.createToken(card, PUBLISHABLE_KEY_TEST, new TokenCallback() {
                public void onSuccess(Token token) {

                    //Toast.makeText(getApplicationContext(), "Token created: " + token.getId(), Toast.LENGTH_LONG).show();
                    tok = token;
                    // TODO: NOT THE BEST IMPLEMENTATION
                    new StripeCharge(token).execute();

                }

                public void onError(Exception error) {
                    if(progress!=null)
                        progress.dismiss();
                    Toast.makeText(PaymentActivity.this, "Payment Unsuccessful", Toast.LENGTH_SHORT).show();
                    Log.d("Stripe error", error.getLocalizedMessage());
                }
            });
        } catch (com.stripe.android.exception.AuthenticationException e) {
            e.printStackTrace();
        }
    }

    public class StripeCharge extends AsyncTask<String, Void, String> {
        Token token;

        StripeCharge(Token token) {
            this.token = token;
        }

        @Override
        protected String doInBackground(String... params) {
            new Thread() {
                @Override
                public void run() {
                    chargeClient(amount,desc,"usd",token);
                    //postData(token,""+amount);
                }
            }.start();
            return "Done";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Result end",s);
        }
    }


    public void chargeClient(String price, String desc, String currency, Token token) {
        // set secret key
        com.stripe.Stripe.apiKey = SECRET_KEY_TEST;

        Map<String, Object> chargeParams = new HashMap<>();

        chargeParams.put("amount", (int) (Double.parseDouble(price)*100));
        chargeParams.put("currency", currency);
        // pass token or id?
        chargeParams.put("card", token.getId());
        chargeParams.put("description", desc);
        Log.d("NUM", "charge params put");


        try {

            RequestOptions.RequestOptionsBuilder builder = new RequestOptions.RequestOptionsBuilder();
            builder.setApiKey(SECRET_KEY_TEST);
            builder.setIdempotencyKey(desc);
            RequestOptions options = builder.build();

            Charge charge = Charge.create(chargeParams, options);
            //Charge charge = Charge.create(chargeParams, SECRET_KEY_TEST); //DEPRECATED

            if(charge!=null) {
                Log.d("Payment", "Successful");
                FirebaseUtils utils = new FirebaseUtils();
                String[] seatArray = getIntent().getStringExtra("CONTENT").split("~")[4].split(",");
                String movie = getIntent().getStringExtra("CONTENT").split("~")[1];
                movie = movie.substring(0, movie.length()-7);
                for (String seat:seatArray) {
                    for (CinemaSeat c:ReservationActivity.getSeatLocation()) {
                        if(c.getId().equals(seat)){
                            Log.d("SEATID",c.getId());
                            utils.occupySeats(day,movie,playTimeId,ReservationActivity.getSeatLocation().indexOf(c));
                        }
                    }
                }
                Intent i = new Intent(this,ConfirmationActivity.class);
                if(progress!=null)
                    progress.dismiss();
                startActivity(i);
                finish();
            }
            else {
                if(progress!=null)
                    progress.dismiss();
                Toast.makeText(this, "Payment Unsuccessful", Toast.LENGTH_SHORT).show();
                Log.d("Payment", "Unsuccessful");

            }
        } catch (InvalidRequestException | APIConnectionException | APIException | AuthenticationException | CardException e) {
            e.printStackTrace();
        }
    }

}