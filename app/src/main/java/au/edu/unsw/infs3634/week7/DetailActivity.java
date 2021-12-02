package au.edu.unsw.infs3634.week7;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    public static final String INTENT_MESSAGE = "au.edu.unsw.infs3634.covid19tracker.intent_message";

    private TextView tvCountryName;
    private TextView tvNewCaseData;
    private TextView tvTotalCaseData;
    private TextView tvNewDeathsData;
    private TextView tvTotalDeathsData;
    private TextView tvNewRecoveredData;
    private TextView tvTotalRecoveredData;
    private ImageView search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //        WEEK 3 TUTORIAL
        tvCountryName = findViewById(R.id.tvCountryName);
        tvNewCaseData = findViewById(R.id.tvNewCaseData);
        tvTotalCaseData = findViewById(R.id.tvTotalCaseData);
        tvNewDeathsData = findViewById(R.id.tvNewDeathsData);
        tvTotalDeathsData = findViewById(R.id.tvTotalDeathsData);
        tvNewRecoveredData = findViewById(R.id.tvNewRecoveredData);
        tvTotalRecoveredData = findViewById(R.id.tvTotalRecoveredData);
        search = findViewById(R.id.btnSearch);

        //get the Intent that started this activity and extract the country code
        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_MESSAGE)){
            String countryCode = intent.getStringExtra(INTENT_MESSAGE);
            Log.d(TAG,"Country Code = " + countryCode);

            ArrayList<Country> countries = Country.getCountries();
            for (final Country country : countries){
                if (country.getCountryCode().equals(countryCode)){
                    DecimalFormat df = new DecimalFormat("#,###,###,###");
                    setTitle(country.getCountryCode());
                    tvCountryName.setText(country.getCountry());
                    tvNewCaseData.setText(String.valueOf(df.format(country.getNewConfirmed())));
                    tvTotalCaseData.setText(String.valueOf(df.format(country.getTotalConfirmed())));
                    tvNewDeathsData.setText(String.valueOf(df.format(country.getNewDeaths())));
                    tvTotalDeathsData.setText(String.valueOf(df.format(country.getTotalDeaths())));
                    tvNewRecoveredData.setText(String.valueOf(df.format(country.getNewRecovered())));
                    tvTotalRecoveredData.setText(String.valueOf(df.format(country.getTotalRecovered())));

                    //implement onClickListener for Search icon
                    search.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            searchCountry(country.getCountry());
                        }
                    });


                }
            }

        }

    }
    //called when user taps on search icon
    private void searchCountry(String country){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=COvid+"+ country));
        startActivity(intent);
    }
}
