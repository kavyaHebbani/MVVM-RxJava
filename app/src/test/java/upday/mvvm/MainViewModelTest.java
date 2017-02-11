package upday.mvvm;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;
import upday.mvvm.datamodel.IDataModel;
import upday.mvvm.model.Country;
import upday.mvvm.schedulers.ImmediateSchedulerProvider;
import upday.mvvm.viewmodel.MainViewModel;

import static upday.mvvm.model.Country.CountryCode;

public class MainViewModelTest {

    @Mock
    private IDataModel mDataModel;

    private MainViewModel mViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mViewModel = new MainViewModel(mDataModel, new ImmediateSchedulerProvider());
    }

    @Test
    public void testGetCountries_returnsCorrectData() {
        List<Country> countries = Arrays.asList(new Country("Germany", CountryCode.GERMANY),
                                                new Country("Austria", CountryCode.AUSTRIA));
        Mockito.when(mDataModel.getCountries()).thenReturn(Observable.just(countries));
        TestSubscriber<List<Country>> testSubscriber = new TestSubscriber<>();

        mViewModel.getCountries().subscribe(testSubscriber);

        Mockito.verify(mDataModel).getCountries();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValue(countries);
    }

    @Test
    public void testGetCapitalCity_doesNotEmit_whenNoCountrySet() {
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        mViewModel.getCapitalCity().subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertNoValues();
    }

    @Test
    public void testGetCapitalCity_returnsCorrectData_whenCountrySet() {
        String capital = "Vienna";
        Country austria = new Country("Austria", CountryCode.AUSTRIA);
        Mockito.when(mDataModel.getCapitalCity(CountryCode.AUSTRIA))
               .thenReturn(Observable.just(capital));
        TestSubscriber<String> testSubscriber = new TestSubscriber<>();
        mViewModel.getCapitalCity().subscribe(testSubscriber);

        mViewModel.countrySelected(austria);

        Mockito.verify(mDataModel).getCapitalCity(CountryCode.AUSTRIA);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValue(capital);
    }
}

