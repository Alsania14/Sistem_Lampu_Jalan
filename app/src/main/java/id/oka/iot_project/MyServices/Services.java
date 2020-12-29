package id.oka.iot_project.MyServices;

import retrofit2.Call;
import retrofit2.http.POST;

public interface Services {
    @POST("/api/onstate")
    Call<String> onState(

    );

    @POST("/api/offstate")
    Call<String> offState(

    );

    @POST("/api/readstate")
    Call<String> readGate(

    );
}
