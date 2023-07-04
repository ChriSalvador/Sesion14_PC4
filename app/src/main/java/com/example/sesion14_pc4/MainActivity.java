package com.example.sesion14_pc4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sesion14_pc4.service.JokeApi;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroupCate, radioGroupIdio;
    private RadioButton radioBtnCualqui, radioBtnProgra, radioBtnIngles, radioBtnEspa;
    private MaterialButton btnObtener;
    private TextView txtChiste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroupCate = findViewById(R.id.radioGroupCategoria);
        radioGroupIdio = findViewById(R.id.radioGroupIdioma);
        radioBtnCualqui = findViewById(R.id.radioButtonCualquiera);
        radioBtnProgra = findViewById(R.id.radioButtonProgramacion);
        radioBtnIngles = findViewById(R.id.radioButtonIngles);
        radioBtnEspa = findViewById(R.id.radioButtonEspañol);
        btnObtener = findViewById(R.id.buttonObtener);
        txtChiste = findViewById(R.id.textChiste);

        btnObtener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoria = capturarCategoria();
                String idioma = capturarIdioma();

                obtenerChiste(categoria, idioma);
            }
        });

    }

    private String capturarCategoria() {
        String categoria = "";
        switch (radioGroupCate.getCheckedRadioButtonId()) {
            case R.id.radioButtonCualquiera:
                categoria = "Any";
                break;
            case R.id.radioButtonProgramacion:
                categoria = "Programming";
                break;
        }
        return categoria;
    }
    private String capturarIdioma() {
        String idioma = "";
        switch (radioGroupIdio.getCheckedRadioButtonId()) {
            case R.id.radioButtonIngles:
                idioma = "en";
                break;
            case R.id.radioButtonEspañol:
                idioma = "es";
                break;
        }
        return idioma;
    }
    private void obtenerChiste(String categ, String idio) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://v2.jokeapi.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JokeApi jokeApi = retrofit.create(JokeApi.class);
        Call<Object> call = jokeApi.getJoke(categ, idio);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    if(response.isSuccessful()) {
                        Object chisteObject = response.body();
                        if (chisteObject != null) {
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            String jsonString = gson.toJson(chisteObject);
                            txtChiste.setText(jsonString);
                        }
                    }
                }catch (Exception ex) {
                    Toast.makeText(MainActivity.this, "Error al obtener chiste", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error en la llamada al API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}