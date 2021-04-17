package com.example.jubicare_premium.rest_api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    //testing
    public static final String BASE_URL = "http://telemedicine.nutrisoft.in/apiV1-17/";
    public static final String IMAGE_URL = "http://telemedicine.nutrisoft.in/admin/assets/images/patient/";
    public static final String IMAGE_URL_COUNSELOR = "http://telemedicine.nutrisoft.in/admin/assets/images/counselor/";
    public static final String IMAGE_URL_DOCTOR = "http://telemedicine.nutrisoft.in/admin/assets/images/doctors/";
    public static final String IMAGE_URL_PHARMACIST = "http://telemedicine.nutrisoft.in/admin/assets/images/pharmacist/";
    public static final String IMAGE_URL_DOC = "http://telemedicine.nutrisoft.in/admin/assets/images/tests/";
    public static final String IMAGE_URL_DOC_APPO = "http://telemedicine.nutrisoft.in/admin/assets/images/appointment/";
    public static final String ADDRESS_URL = "http://www.postalpincode.in/api/pincode/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60000, TimeUnit.SECONDS)
                .readTimeout(60000, TimeUnit.SECONDS)
                .addInterceptor(logging).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit;
    }
//    public static Retrofit getClientAddress() {
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.NONE);
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(10000, TimeUnit.SECONDS)
//                .readTimeout(10000, TimeUnit.SECONDS)
//                .addInterceptor(logging).build();
//
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//
//        retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(client)
//                .build();
//
//        return retrofit;
//    }
}
