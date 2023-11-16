package com.rag.rxjavatest2.tutorial02;

import com.rag.rxjavatest2.pojo.CountryStat;

import java.util.List;

public interface ContractMain {
    interface MainView{
        void showProgress();
        void hideProgress();
        void setDataToAdapter(List<CountryStat> countryStatList);
    }
    interface MainPresenter{
        void displayResult();
    }
}
