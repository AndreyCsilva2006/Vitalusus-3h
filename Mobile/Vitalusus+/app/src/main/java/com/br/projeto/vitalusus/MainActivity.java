package com.br.projeto.vitalusus;

import android.content.Intent;
import android.os.Bundle;

import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.network.RetrofitClient;
import com.br.projeto.vitalusus.network.ApiService;
//import com.br.projeto.vitalusus.view.ListarAlunos;
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
        imageView.setImageResource(R.drawable.img_tut0);

        //suporte actionbar
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().hide();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}