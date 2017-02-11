package upday.mvvm.schedulers;

import android.support.annotation.NonNull;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Implementation of {@link ISchedulerProvider} using Immediate Schedulers, for the purpose of unit
 * testing.
 */
public class ImmediateSchedulerProvider implements ISchedulerProvider {

    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.immediate();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return Schedulers.immediate();
    }
}
