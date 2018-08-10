package util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Factory {
    private static Retrofit retrofit;
    public static Retrofit getRetrofit(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(Option.BASE_URL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
