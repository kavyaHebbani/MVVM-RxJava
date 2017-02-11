package upday.mvvm.model;

import android.support.annotation.NonNull;

public final class Country {

    public enum CountryCode {
        GERMANY, AUSTRIA, FRANCE
    }

    @NonNull
    private final String mName;

    @NonNull
    private final CountryCode mCode;

    public Country(@NonNull final String name, @NonNull final CountryCode code) {
        mName = name;
        mCode = code;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @NonNull
    public CountryCode getCode() {
        return mCode;
    }
}
