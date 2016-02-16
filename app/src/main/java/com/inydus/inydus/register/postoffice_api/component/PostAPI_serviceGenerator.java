package com.inydus.inydus.register.postoffice_api.component;

import android.util.Log;

import com.inydus.inydus.network.NetworkService;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

import retrofit.Retrofit;
import retrofit.SimpleXmlConverterFactory;

public class PostAPI_serviceGenerator {

    private static String baseUrl = "http://openapi.epost.go.kr";

    private static Retrofit.Builder builder = new Retrofit.Builder();

    public static NetworkService buildService() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient client = new OkHttpClient();
        client.setCookieHandler(cookieManager);
        client.interceptors().clear();
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Log.i("MyTag", original.httpUrl().toString());

                Request.Builder requestBuilder = original.newBuilder()
                        .header("State", "application/xml")
                        .header("State-Charset", "utf-8")
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(NetworkService.class);
    }
}
