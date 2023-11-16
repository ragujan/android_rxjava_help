package com.rag.rxjavatest2;


import static com.rag.rxjavatest2.tutorial01.CryptoCurrencyService.BASE_URL;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rag.rxjavatest2.pojo.Crypto;
import com.rag.rxjavatest2.tutorial01.CryptoCurrencyService;
import com.rag.rxjavatest2.tutorial02.RecylerViewAdapter;

import java.util.LinkedList;
import java.util.List;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    RecyclerView recylerView;
    Retrofit retrofit;

    RecylerViewAdapter recylerViewAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recylerView = findViewById(R.id.recyclerView);
        recylerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recylerViewAdapter = new RecylerViewAdapter(new LinkedList<>());
        recylerView.setAdapter(recylerViewAdapter);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();



        callEndPoints();
    }

    private void callEndPoints(){
        CryptoCurrencyService cryptoCurrencyService = retrofit.create(CryptoCurrencyService.class);

        Observable<List<Crypto.Market>> ethObservable = cryptoCurrencyService.getCoinData("eth")
                .map(result -> Observable.fromIterable(result.ticker.markets))
                .flatMap(x->x).filter(y->{
                    y.coinName = "btc";
                    return true;
                }).toList().toObservable();
//
        Observable<List<Crypto.Market>> btcObservable = cryptoCurrencyService.getCoinData("btc")
                .map(result -> Observable.fromIterable(result.ticker.markets))
                .flatMap(x -> x).filter(y -> {
                    y.coinName = "btc";
                    return true;
                }).toList().toObservable();


        Observable.merge(ethObservable, btcObservable)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError);
    }
    private void handleResults(List<Crypto.Market> marketList){
        if(marketList != null && marketList.size() != 0){
            recylerViewAdapter.setData(marketList);
        }else{
            Toast.makeText(MainActivity.this,"NO RESULTS FOUND", Toast.LENGTH_SHORT);
        }
    }
    private void handleError(Throwable t) {

        Toast.makeText(this, "ERROR IN FETCHING API RESPONSE. Try again",
                Toast.LENGTH_LONG).show();
    }
}