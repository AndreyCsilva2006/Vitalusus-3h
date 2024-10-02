package com.br.projeto.vitalusus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

//import com.br.projeto.vitalusus.dao.AlunoDAO;
//import com.br.projeto.vitalusus.dao.UsuarioDAO;
import com.br.projeto.vitalusus.model.Aluno;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.network.ApiService;
import com.br.projeto.vitalusus.network.RetrofitClient;
import com.br.projeto.vitalusus.util.MensagemUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

// código copiado e adaptador da ActivityAluno (com.br.projeto.vitalusus.view.ActivityAluno).
public class FormCadastro extends AppCompatActivity {

    View containerComponents;
    EditText editNome, editEmail, editSenha, editPSeguranca, editRSeguranca, editAltura, editPeso, editDataNasc;
    Button btnFormCadastroSalvar, btnFormCadastroAvancarPasso, btnFormCadastroOlharSenha;
    ImageView ic_seta;
    TextView text_tela_principal;

    Aluno alunoEditando = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);

        carregaFormulario();
//        carregaBundle();

        text_tela_principal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormCadastro.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        btnFormCadastroOlharSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tipoAtual = editSenha.getInputType();

                // Verifique se é um campo de senha (& ou | significa AND)
                boolean ehSenha = (tipoAtual & InputType.TYPE_TEXT_VARIATION_PASSWORD) == InputType.TYPE_TEXT_VARIATION_PASSWORD;

                // Alterne para o próximo tipo de entrada
                if (ehSenha) {
                    editSenha.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    editSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    private void carregaFormulario() {
        // passo 1
        editNome = findViewById(R.id.editFormCadastroLoginNome);
        editEmail = findViewById(R.id.editFormCadastroLoginEmail);
        editSenha = findViewById(R.id.editFormCadastroLoginSenha);
        editPSeguranca = findViewById(R.id.editFormCadastroPergSeguranca);
        editRSeguranca = findViewById(R.id.editFormCadastroRespSeguranca);
        btnFormCadastroAvancarPasso = findViewById(R.id.btnCadastroAvancarPasso);
        btnFormCadastroOlharSenha = findViewById(R.id.btnFormCadastroOlharSenha);
        editNome.setVisibility(View.VISIBLE);
        editEmail.setVisibility(View.VISIBLE);
        editSenha.setVisibility(View.VISIBLE);
        editPSeguranca.setVisibility(View.VISIBLE);
        editRSeguranca.setVisibility(View.VISIBLE);
        btnFormCadastroAvancarPasso.setVisibility(View.VISIBLE);
        btnFormCadastroOlharSenha.setVisibility(View.VISIBLE);

        // passo 2
        editAltura = findViewById(R.id.editFormCadastroAltura);
        editPeso = findViewById(R.id.editFormCadastroPeso);
        editDataNasc = findViewById(R.id.editFormCadastroDataNascimento);
        btnFormCadastroSalvar = findViewById(R.id.btnCadastroSalvar);
        editDataNasc.setVisibility(View.GONE);
        editAltura.setVisibility(View.GONE);
        editPeso.setVisibility(View.GONE);
        btnFormCadastroSalvar.setVisibility(View.GONE);

        // extra
        text_tela_principal = findViewById(R.id.text_tela_principal);
        containerComponents = findViewById(R.id.containerComponents);
        ic_seta = findViewById(R.id.FormCadastroSeta);
        ic_seta.setVisibility(View.GONE);
//      tamanho passo 1 em pixels
        ViewGroup.LayoutParams layoutParams = containerComponents.getLayoutParams();
        layoutParams.height = 1080;

        btnFormCadastroAvancarPasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
            }
        });
        ic_seta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregaFormulario();
            }
        });
        btnFormCadastroSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });
    }

    // carrega informações do Aluno
    // editar aluno

    // validações cadastro
    private Boolean validar() {

        // Fazendo com que o campo fique com a borda vermelha caso o campo esteja inválido.
        GradientDrawable redBorder = new GradientDrawable();
        redBorder.setColor(Color.WHITE); // Cor do fundo.
        redBorder.setCornerRadius(50); // Raio do arredondamento das bordas (em pixels fica 60px que é equivalente a 20dp).
        redBorder.setStroke(5, Color.RED); // Cor e espessura da borda (em pixels fica 6px que é equivalente a 2dp).

        // Fazendo com que o campo fique com a borda normal caso campo esteja válido.
        GradientDrawable blackBorder = new GradientDrawable();
        blackBorder.setColor(Color.WHITE); // Cor do fundo.
        blackBorder.setCornerRadius(50); // Raio do arredondamento das bordas (em pixels fica 60px que é equivalente a 20dp).
        blackBorder.setStroke(5, Color.BLACK); // Cor e espessura da borda (em pixels fica 6px que é equivalente a 2dp).

        if (editNome.getText().toString().trim().isEmpty()) {
            MensagemUtil.exibir(this, "Digite um Nome");

            // Aplicando o GradientDrawable ao EditText
            editNome.setBackground(redBorder);

            editNome.requestFocus();
            return false;
        }
        if (editNome.getText().toString().trim().length() < 3) {
            MensagemUtil.exibir(this, "Digite um Nome que tenha pelo menos 3 caracteres");
            editNome.requestFocus();
            return false;
        }
        editNome.setBackground(blackBorder);

        if (editEmail.getText().toString().trim().isEmpty()) {
            editEmail.setBackground(redBorder);

            MensagemUtil.exibir(this, "Digite um E-Mail");
            editEmail.requestFocus();
            return false;
        }
        // ! no início de uma expressão lógica é usado para negar o resultado dessa expressão. Ou seja, ele inverte o valor booleano. (Se o valor for falso)
        if (!editEmail.getText().toString().trim().contains("@")) {
            editEmail.setBackground(redBorder);

            MensagemUtil.exibir(this, "O Email precisa ter um @");
            editEmail.requestFocus();
            return false;
        }
        if (editEmail.getText().toString().trim().length() < 9) {
            editEmail.setBackground(redBorder);

            MensagemUtil.exibir(this, "Digite um E-Mail válido");
            editEmail.requestFocus();
            return false;
        }
        editEmail.setBackground(blackBorder);

        if (editSenha.getText().toString().trim().isEmpty()) {
            editSenha.setBackground(redBorder);

            MensagemUtil.exibir(this, "Digite uma Senha");
            editSenha.requestFocus();
            return false;
        }
        if (editSenha.getText().toString().trim().length() < 8) {
            editSenha.setBackground(redBorder);

            MensagemUtil.exibir(this, "Digite uma Senha que tenha pelo menos 8 caracteres");
            editSenha.requestFocus();
            return false;
        }
        editSenha.setBackground(blackBorder);

        if (editPSeguranca.getText().toString().trim().isEmpty()) {
            editPSeguranca.setBackground(redBorder);

            MensagemUtil.exibir(this, "Digite uma Pergunta de Segurança caso perca sua Senha para poder recuperar.");
            editPSeguranca.requestFocus();
            return false;
        }
        if (editPSeguranca.getText().toString().trim().length() < 4) {
            editPSeguranca.setBackground(redBorder);

            MensagemUtil.exibir(this, "Digite uma Pergunta que pelo menos 4 caracteres");
            editPSeguranca.requestFocus();
            return false;
        }
        editPSeguranca.setBackground(blackBorder);

        if (editRSeguranca.getText().toString().trim().isEmpty()) {
            editRSeguranca.setBackground(redBorder);

            MensagemUtil.exibir(this, "Digite uma Resposta para a Pergunta de Segurança.");
            editRSeguranca.requestFocus();
            return false;
        }
        if (editRSeguranca.getText().toString().trim().length() < 4) {
            editRSeguranca.setBackground(redBorder);

            MensagemUtil.exibir(this, "Digite uma Resposta para a Pergunta de Segurança que tenha pelo menos 4 caracteres");
            editRSeguranca.requestFocus();
            return false;
        } else {
            // Some
            editNome.setVisibility(View.GONE);
            editEmail.setVisibility(View.GONE);
            editSenha.setVisibility(View.GONE);
            editPSeguranca.setVisibility(View.GONE);
            editRSeguranca.setVisibility(View.GONE);
            btnFormCadastroAvancarPasso.setVisibility(View.GONE);
            btnFormCadastroOlharSenha.setVisibility(View.GONE);

            // Aparece
            editDataNasc.setVisibility(View.VISIBLE);
            editAltura.setVisibility(View.VISIBLE);
            editPeso.setVisibility(View.VISIBLE);
            btnFormCadastroSalvar.setVisibility(View.VISIBLE);
            ic_seta.setVisibility(View.VISIBLE);

            // tamanho passo 2 em pixel
            ViewGroup.LayoutParams layoutParams = containerComponents.getLayoutParams();
            layoutParams.height = 660;
        }
        editRSeguranca.setBackground(blackBorder);

        // passo 2
        if (editDataNasc.getText().toString().trim().isEmpty()) {
            editDataNasc.setBackground(redBorder);

            MensagemUtil.exibir(this, "Digite uma Data de Nascimento.");
            editDataNasc.requestFocus();
            return false;
        }
        // validação data nascimento correta
//        if (editDataNasc.getText().toString().trim().isEmpty()) {
//            editRSeguranca.setBackground(redBorder);
//
//            MensagemUtil.exibir(this, "Digite uma Data de Nascimento.");
//            editDataNasc.requestFocus();
//            return false;
//        }
        editDataNasc.setBackground(blackBorder);

        if (editAltura.getText().toString().trim().isEmpty()) {
            editAltura.setBackground(redBorder);

            MensagemUtil.exibir(this, "Digite sua Altura.");
            editAltura.requestFocus();
            return false;
        }
        if (editAltura.getText().toString().trim().length() < 1) {
            editAltura.setBackground(redBorder);

            MensagemUtil.exibir(this, "Digite sua Altura.");
            editAltura.requestFocus();
            return false;
        }
        editAltura.setBackground(blackBorder);

        return true;
    }


    private void salvar() {
        // se ele o método validar() não for verdadeiro.
        if (!validar()) {
            return;
        }

        // retrofit api
//    Usuario novoUsuario = new Usuario();
//    Aluno novoAluno = new Aluno();
//    Retrofit retrofit = RetrofitClient.getRetrofitInstance();
//        ApiService apiService = retrofit.create(ApiService.class);
//        Call<Usuario> call = apiService.createUsuario(novoUsuario);
//
//    call.enqueue(new Callback<Usuario>() {
//        @Override
//        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
//            if (response.isSuccessful()) {
//                // Aqui você poderia adicionar o aluno se necessário
//                novoAluno.setUsuario_id(response.body().getId()); // use o ID do usuário criado
//                apiService.createAluno(novoAluno).enqueue(new Callback<Aluno>() {
//                    @Override
//                    public void onResponse(Call<Aluno> call, Response<Aluno> response) {
//                        if (response.isSuccessful()) {
//                            // Sucesso na criação do aluno
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Aluno> call, Throwable t) {
//                        // Tratamento de erro
//                    }
//                });
//            }
//        }
//
//        @Override
//        public void onFailure(Call<Usuario> call, Throwable t) {
//            // Tratamento de erro
//        }
//    });


//        Aluno a = new Aluno();
//        Usuario u = new Usuario();

//        if (alunoEditando != null) {
//            a = alunoEditando;
//        }
//        u.setNome(editNome.getText().toString());
//        u.setEmail(editEmail.getText().toString());
//        u.setSenha(editSenha.getText().toString());
//        u.setpSeguranca(editPSeguranca.getText().toString());
//        u.setrSeguranca(editRSeguranca.getText().toString());
//        a.setDataNasc(editDataNasc.getText().toString());
//        a.setAltura(editAltura.getTextSize());
//        a.setPeso(editPeso.getTextSize());
//
//        AlunoDAO daoAlu = new AlunoDAO();
//        UsuarioDAO daoUsu = new UsuarioDAO();
//        if (alunoEditando != null) {
////            daoAlu.alterar(a);
//        } else {
//            daoAlu.cadastrarAluno(a, u);
//        }

        Intent intent = new Intent(FormCadastro.this, FormLogin.class);
        startActivity(intent);
    }
}