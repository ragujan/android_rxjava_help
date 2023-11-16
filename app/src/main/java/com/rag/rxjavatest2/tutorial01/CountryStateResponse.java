package com.rag.rxjavatest2.tutorial01;

import com.rag.rxjavatest2.pojo.CountryStat;

import java.util.List;

public class CountryStateResponse {
    private List<CountryStat> response;
    public CountryStateResponse(List<CountryStat> response){
        this.response = response;
    }
    public List<CountryStat> getResponse() {
        return response;
    }

    public void setResponse(List<CountryStat> response) {
        this.response = response;
    }
}
