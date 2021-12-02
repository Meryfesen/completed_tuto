package au.edu.unsw.infs3634.week7;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyViewHolder> implements Filterable{
    private List<Country> mCountry;
    private List<Country> mCountryListFull;
    private List<Country> mCountryFiltered;
    ClickListener mlistener;

    public CountryAdapter(List<Country> mCountry, ClickListener listener) {
        this.mCountry = mCountry;
        mCountryListFull = new ArrayList<>(mCountry);
        this.mCountryFiltered = mCountry;
        mlistener = listener;
    }


    public interface ClickListener {
        void oProductClick(View view, int position);
    }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
            return new MyViewHolder(view, mlistener);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            final Country country = mCountry.get(position);
            int countryID = position;
            DecimalFormat df = new DecimalFormat("#,###,###,###");
            holder.country.setText(country.getCountry());
            holder.tCase.setText(String.valueOf(df.format(country.getTotalConfirmed())));
            holder.nCase.setText("+" + String.valueOf(df.format(country.getNewConfirmed())));
            holder.itemView.setTag(countryID);
        }

        @Override
        public int getItemCount() {
            return mCountry.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView country, tCase, nCase;
            CountryAdapter.ClickListener listener;

            public MyViewHolder(@NonNull View itemView, CountryAdapter.ClickListener listener) {
                super(itemView);
                this.listener = listener;
                country = itemView.findViewById(R.id.myText1);
                itemView.setOnClickListener(MyViewHolder.this);
                tCase = itemView.findViewById(R.id.myText2);
                nCase = itemView.findViewById(R.id.myText3);
            }

            @Override
            public void onClick(View v) {
                listener.oProductClick(v, (Integer) v.getTag());

            }
        }
    //Sorting function mechanism
    public void sort(final int sortMethod){
        if(mCountryFiltered.size() > 0){
            Collections.sort(mCountryFiltered, new Comparator<Country>() {

                @Override
                public int compare(Country o1, Country o2) {
                    if(sortMethod == 1)
                        return o1.getTotalConfirmed().compareTo(o2.getTotalConfirmed());
                    else if(sortMethod == 2)
                        return o2.getNewConfirmed().compareTo(o1.getNewConfirmed());
                    return o1.getTotalConfirmed().compareTo(o2.getTotalConfirmed());
                }
            });
        }
        notifyDataSetChanged();
    }

    //Filtering search function mechanism
    @Override
    public Filter getFilter(){
        return countryFiltered;
    }
    private Filter countryFiltered = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Country> filteredList= new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(mCountryListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Country item: mCountryListFull){
                    if(item.getCountry().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        //Obtain filtered songs and display
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mCountry.clear();
            mCountry.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}

