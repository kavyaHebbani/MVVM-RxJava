package upday.mvvm.viewmodel;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import upday.mvvm.datamodel.IDataModel;
import upday.mvvm.model.Country;
import upday.mvvm.schedulers.ISchedulerProvider;

public class MainViewModel {

    @NonNull
    private final IDataModel mDataModel;

    @NonNull
    private final BehaviorSubject<Country> mSelectedCountry = BehaviorSubject.create();

    @NonNull
    private final ISchedulerProvider mSchedulerProvider;

    public MainViewModel(@NonNull IDataModel dataModel,
                         @NonNull ISchedulerProvider schedulerProvider) {
        mDataModel = dataModel;
        mSchedulerProvider = schedulerProvider;
    }

    @NonNull
    public Observable<List<Country>> getCountries() {
        return mDataModel.getCountries();
    }

    @NonNull
    public Observable<String> getCapitalCity() {
        return mSelectedCountry
                .observeOn(mSchedulerProvider.computation())
                .map(Country::getCode)
                .flatMap(mDataModel::getCapitalCity);
    }

    public void countrySelected(@NonNull Country country) {
        mSelectedCountry.onNext(country);
    }

}
