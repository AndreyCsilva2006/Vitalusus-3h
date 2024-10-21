package com.br.projeto.vitalusus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        // Chamando super.onCreate após definir o tema
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Recupera os dados do Intent
        Intent intent = getIntent();
        String usuarioNome = intent.getStringExtra("usuarioNome");
        String usuarioEmail = intent.getStringExtra("usuarioEmail");

        // Cria um Bundle para passar os dados à Fragment
        Bundle bundle = new Bundle();
        bundle.putString("usuarioNome", usuarioNome);
        bundle.putString("usuarioEmail", usuarioEmail);

        // Inicializa a Fragment e define os argumentos
        PerfilFragment perfilFragment = new PerfilFragment();
        perfilFragment.setArguments(bundle); // Passa os dados para a Fragment

        // Inicia a Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, perfilFragment) // Assume que `fragment_container` é o ID do container no layout
                .commit();

        // Inicializando toolbar e search view
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchView = findViewById(R.id.search_view); // A SearchView que estará no toolbar

        // Configurando o DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Configurando o NavigationView
        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        // Configurando o BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);

        // Configurando o listener do BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.bottom_home) {
                    openFragment(new HomeFragment());
                    return true;
                } else if (itemId == R.id.bottom_canais) {
                    openFragment(new CanaisFragment());
                    return true;
                } else if (itemId == R.id.bottom_estatisticas) {
                    openFragment(new EstatisticasFragment());
                    return true;
                } else if (itemId == R.id.bottom_perfil) {
                    PerfilFragment perfilFragment = new PerfilFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("usuarioNome", usuarioNome);
                    bundle.putString("usuarioEmail", usuarioEmail);
                    perfilFragment.setArguments(bundle);
                    openFragment(perfilFragment);
                    return true;
                }
                return false;
            }
        });

        fragmentManager = getSupportFragmentManager();
        openFragment(new HomeFragment()); // Abre o HomeFragment inicialmente
    }

    // Controla a navegação no drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_suporte) {
            openFragment(new RelatarProblemaFragment());
        } else if (itemId == R.id.nav_notificacoes) {
//            openFragment(new com.br.projeto.vitalusus.NotificacoesFragment());
        } else if (itemId == R.id.nav_sobrenos) {
            openFragment(new SobreNosFragment());
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Função para abrir fragmentos e controlar a visibilidade da SearchView
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();

        // Controla a visibilidade da SearchView
        if (fragment instanceof HomeFragment) {
            searchView.setVisibility(View.VISIBLE);  // Exibe a SearchView no HomeFragment
        } else {
            searchView.setVisibility(View.GONE);  // Oculta a SearchView em outros fragmentos
        }
    }
}
