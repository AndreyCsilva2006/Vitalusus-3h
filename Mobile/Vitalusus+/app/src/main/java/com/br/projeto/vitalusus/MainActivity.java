package com.br.projeto.vitalusus;

import android.content.Intent;
import android.os.Bundle;

import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.network.RetrofitClient;
import com.br.projeto.vitalusus.network.ApiService;
import com.br.projeto.vitalusus.view.ListarAlunos;
import com.br.projeto.vitalusus.view.ListarCanal;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.br.projeto.vitalusus.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ImageView imageView = (ImageView) findViewById(R.id.logo);
        imageView.setImageResource(R.drawable.logo);

        //suporte actionbar
        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // infla o menu.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void listaAluno(MenuItem item) {
        // Intent = intenção
        // this = tela atual
        Intent liA = new Intent(this, ListarAlunos.class);
        startActivity(liA);
    }

    public void config(MenuItem item) {
        Intent con = new Intent(this, PlayerVideo.class);
        startActivity(con);
    }

    public void listaCanal(MenuItem item) {
        Intent liC = new Intent(this, ListarCanal.class);
        startActivity(liC);


        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Usuario>> call = apiService.getUsuarios();

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    List<Usuario> usuarios = response.body();
                    // Manipule os dados de usuários, como exibi-los na UI

                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                // Lide com o erro
            }
        });








    }
}