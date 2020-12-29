package id.oka.iot_project.MyServices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;
    public static final String BASE_URL ="https://okaserver.alindeveloper.xyz/";

    public static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new retrofit2.Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }
}
