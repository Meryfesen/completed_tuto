package au.edu.unsw.infs3634.week7;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Country> mCountry;
    private CountryAdapter mAdapter;
    private Country countries;

    RecyclerView recyclerView;

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        mCountry = new ArrayList<>();
        for(Country country : countries.getCountries()){
            Log.d(TAG, "onCreate: " + country);
            mCountry.add(country);
        }

       CountryAdapter.ClickListener listener = new CountryAdapter.ClickListener() {
            @Override
            public void oProductClick(View view, int position) {
                for (Country country : mCountry) {
                    int b = mCountry.indexOf(country);
                    if (b == position) {
                        String code = country.getCountryCode();
                        launchDetailActivity(code);

                    }
                }
            }
        };

        mAdapter = new CountryAdapter(mCountry, listener);
        recyclerView.setAdapter(mAdapter);




    }
    // Week 3 >> Called when the user taps the Launch Detail Activity button
    private void launchDetailActivity(String m){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.INTENT_MESSAGE, m);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    //connect sort id in XML file to main activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            //sort by ranking in ascending order (highest rank to lowest)
            case R.id.sortNc:
                mAdapter.sort(1);
                return true;
            //sort by ranking in descending order (lowest rank to Highest)
            case R.id.sortTc:
                mAdapter.sort(2);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }


}