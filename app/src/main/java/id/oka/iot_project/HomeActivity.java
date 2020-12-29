package id.oka.iot_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import id.oka.iot_project.MyServices.ApiClient;
import id.oka.iot_project.MyServices.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnTurnon,btnTurnoff;
    private TextView tvState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnTurnon = findViewById(R.id.btnTurnon);
        btnTurnoff = findViewById(R.id.btnTurnoff);
        tvState = findViewById(R.id.tvState);

        this.realTimeStateReader();

        btnTurnon.setOnClickListener(this);
        btnTurnoff.setOnClickListener(this);
    }

    protected void realTimeStateReader(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Services services = ApiClient.getRetrofit().create(Services.class);
                Call<String> call = services.readGate();

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        assert response.body() != null;
                        String status = "CANT CONNECT TO SERVER";
                        if(response.body().matches("0")){
                            status = "LAMP OFF";
                            HomeActivity.this.tvState.setText(status);
                        }else if(response.body().matches("1")){
                            status = "LAMP ON";
                            HomeActivity.this.tvState.setText(status);
                        }else{
                            HomeActivity.this.tvState.setText(status);
                        }
                        HomeActivity.this.realTimeStateReader();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(HomeActivity.this, "Periksa Koneksi Anda ! "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        HomeActivity.this.realTimeStateReader();
                    }
                });
            }
        },3000);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnTurnon){
            Services services = ApiClient.getRetrofit().create(Services.class);
            Call<String> call = services.onState();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.body().matches("200")){
                        Toast.makeText(HomeActivity.this, "SUKSES TURN ON", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(HomeActivity.this, "GAGAL "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(view.getId() == R.id.btnTurnoff){
            Services services = ApiClient.getRetrofit().create(Services.class);
            Call<String> call = services.offState();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.body().matches("200")){
                        Toast.makeText(HomeActivity.this, "SUKSES TURN OFF", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(HomeActivity.this, "GAGAL "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}