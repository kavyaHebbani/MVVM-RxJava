package upday.mvvm.datamodel;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import upday.mvvm.model.Country;

import static upday.mvvm.model.Country.CountryCode;

public class DataModel implements IDataModel {

    @NonNull
    @Override
    public Observable<List<Country>> getCountries() {
        return Observable.fromCallable(this::getCountriesList);
    }

    @NonNull
    @Override
    public Observable<String> getCapitalCity(@NonNull CountryCode code) {
        switch (code) {
            case GERMANY:
                return Observable.just("Berlin");
            case AUSTRIA:
                return Observable.just("Vienna");
            case FRANCE:
                return Observable.just("Paris");
            default:
                return Observable.empty();
        }
    }

    @NonNull
    private List<Country> getCountriesList() {
        return Arrays.asList(new Country("Germany", CountryCode.GERMANY),
                             new Country("Austria", CountryCode.AUSTRIA),
                             new Country("France", CountryCode.FRANCE));
    }
}
