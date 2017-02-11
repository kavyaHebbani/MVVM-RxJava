package upday.mvvm.datamodel;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;
import upday.mvvm.model.Country;

import static upday.mvvm.model.Country.CountryCode;

public interface IDataModel {

    @NonNull
    Observable<List<Country>> getCountries();

    @NonNull
    Observable<String> getCapitalCity(@NonNull CountryCode code);
}
