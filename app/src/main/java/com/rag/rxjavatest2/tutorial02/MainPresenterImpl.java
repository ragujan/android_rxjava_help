package com.rag.rxjavatest2.tutorial02;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainPresenterImpl implements ContractMain.MainPresenter {
    private final CovidClient covidClient;
    private final ContractMain.MainView mainView;

    public MainPresenterImpl(CovidClient covidClient, ContractMain.MainView mainView) {
        this.covidClient = covidClient;
        this.mainView = mainView;
    }
    @Override
    public void displayResult() {
        mainView.showProgress();
        covidClient.getCountriesStats()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CountryStatResponse>() {
                    @Override
                    public void onNext(CountryStatResponse countriesStats) {
                        mainView.setDataToAdapter(countriesStats.getResponse());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mainView.hideProgress();
                    }

                    @Override
                    public void onComplete() {
                        mainView.hideProgress();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        // You can perform additional operations on subscription if needed
                    }
                });
    }
}
