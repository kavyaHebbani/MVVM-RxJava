package upday.mvvm.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import upday.mvvm.MyApplication;
import upday.mvvm.R;
import upday.mvvm.model.Country;
import upday.mvvm.ui.LanguageSpinnerAdapter;
import upday.mvvm.viewmodel.MainViewModel;

public class MainFragment extends Fragment {

    @Nullable
    private MainViewModel mViewModel;

    @Nullable
    private TextView mCapitalCityTextView;

    @Nullable
    private Spinner mCountriesSpinner;

    @Nullable
    private LanguageSpinnerAdapter mLanguageSpinnerAdapter;

    @NonNull
    private CompositeSubscription mSubscription = new CompositeSubscription();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = getViewModel();
        setupViews(view);
        bind();
    }

    private void setupViews(View view) {
        mCapitalCityTextView = (TextView) view.findViewById(R.id.capital);
        mCountriesSpinner = (Spinner) view.findViewById(R.id.countries);
        assert mCountriesSpinner != null;
        mCountriesSpinner.setOnItemSelectedListener(getClickListener());
    }

    @NonNull
    private AdapterView.OnItemSelectedListener getClickListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view,
                                       final int position, final long id) {
                assert mLanguageSpinnerAdapter != null;

                Country countrySelected = mLanguageSpinnerAdapter.getItem(position);
                assert countrySelected != null;
                assert mViewModel != null;
                mViewModel.countrySelected(countrySelected);
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {
                //nothing to do here
            }
        };
    }

    private void bind() {
        assert mViewModel != null;

        if (mSubscription.isUnsubscribed()) {
            mSubscription = new CompositeSubscription();
        }

        mSubscription.add(mViewModel.getCapitalCity()
                                    .subscribeOn(Schedulers.computation())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(this::setCapitalCityTextView,
                                               this::logError));

        mSubscription.add(mViewModel.getCountries()
                                    .subscribeOn(Schedulers.computation())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(this::setLanguages,
                                               this::logError));
    }

    private void setCapitalCityTextView(@NonNull String capital) {
        assert mCapitalCityTextView != null;
        String prefix = getResources().getString(R.string.capital_city);
        mCapitalCityTextView.setText(String.format(prefix, capital));
    }

    private void setLanguages(@NonNull List<Country> countries) {
        assert mCountriesSpinner != null;
        mLanguageSpinnerAdapter = new LanguageSpinnerAdapter(
                getContext(), R.layout.country_item, countries);
        mCountriesSpinner.setAdapter(mLanguageSpinnerAdapter);
    }

    @Override
    public void onDestroyView() {
        unBind();
        super.onDestroyView();
    }

    private void unBind() {
        mSubscription.unsubscribe();
    }

    @NonNull
    private MainViewModel getViewModel() {
        return ((MyApplication) getActivity().getApplication()).getViewModel();
    }

    private void logError(Throwable throwable) {
        Log.e("MainFragment", "Error getting data from View Model: " + throwable.getMessage());
    }
}
