package upday.mvvm;

import android.app.Application;
import android.support.annotation.NonNull;

import upday.mvvm.datamodel.DataModel;
import upday.mvvm.datamodel.IDataModel;
import upday.mvvm.schedulers.ISchedulerProvider;
import upday.mvvm.schedulers.SchedulerProvider;
import upday.mvvm.viewmodel.MainViewModel;

public class MyApplication extends Application {

    @NonNull
    private final IDataModel mDataModel;

    public MyApplication() {
        mDataModel = new DataModel();
    }

    @NonNull
    public IDataModel getDataModel() {
        return mDataModel;
    }

    @NonNull
    public ISchedulerProvider getSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }

    @NonNull
    public MainViewModel getViewModel() {
        return new MainViewModel(getDataModel(), getSchedulerProvider());
    }

}
