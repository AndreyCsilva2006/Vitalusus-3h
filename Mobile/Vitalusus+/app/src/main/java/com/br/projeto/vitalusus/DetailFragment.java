package com.br.projeto.vitalusus;

import static okio.ByteString.decodeBase64;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.br.projeto.vitalusus.adapter.VideoAdapter;
import com.br.projeto.vitalusus.adapter.VideoCanalDetailAdapter;
import com.br.projeto.vitalusus.model.Canal;
import com.br.projeto.vitalusus.model.Treinador;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.model.Video;
import com.br.projeto.vitalusus.model.VideoResponse;
import com.br.projeto.vitalusus.network.ApiService;
import com.br.projeto.vitalusus.network.RetrofitClient;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailFragment extends Fragment {

    private static final String ARG_CANAL_ID = "canal_id";
    private static final String ARG_USUARIO_ID = "usuario_id";

    private int canalId;
    private int usuarioId;

    private List<Video> videoList = new ArrayList<>();
    private List<Canal> canalList = new ArrayList<>();
    private List<Treinador> treinadorList = new ArrayList<>();
    private List<Usuario> usuarioList = new ArrayList<>();
    private RecyclerView recyclerView;
    private VideoCanalDetailAdapter videoAdapter;

    public static DetailFragment newInstance(int canalId, int usuarioId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CANAL_ID, canalId);
        args.putInt(ARG_USUARIO_ID, usuarioId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            canalId = getArguments().getInt(ARG_CANAL_ID);
            usuarioId = getArguments().getInt(ARG_USUARIO_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        getActivity().setTitle("Detalhes do Canal");

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        videoAdapter = new VideoCanalDetailAdapter(usuarioList, canalList, treinadorList, videoList,
                (video, canal, usuario, treinador) -> {
                    openVideoPlayerFragment(video.getId(), canal.getId(), usuario.getId());
                }
        );

        recyclerView.setAdapter(videoAdapter);

        TextView nomeTextView = view.findViewById(R.id.txtNomeCanalDetail);
//        TextView seguidoresTextView = view.findViewById(R.id.tv_SeguidoresCanalDetail);
//        TextView visualizacoesTextView = view.findViewById(R.id.tv_VisualizacoesCanalDetail);
        TextView biografiaTextView = view.findViewById(R.id.tv_BiografiaCanalDetail);

        CircleImageView fotoUsuarioImageView = view.findViewById(R.id.imgFotoCanalDetail); // Aqui a ImageView é inicializada

        // Inicie o fetch de detalhes do canal e do usuário
        fetchCanalDetails(canalId, nomeTextView, biografiaTextView, fotoUsuarioImageView);
        fetchUsuarioDetails(usuarioId, fotoUsuarioImageView); // Carrega os detalhes do usuário e a foto
        fetchVideos();

        return view;
    }

    private void openVideoPlayerFragment(int videoId, int canalId, int usuarioId) {
        VideoPlayerFragment videoPlayerFragment = VideoPlayerFragment.newInstance(canalId, usuarioId, videoId);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, videoPlayerFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void fetchVideos() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        // Passa o canalId como argumento para a API
        apiService.getVideosByCanalId(canalId).enqueue(new Callback<List<VideoResponse>>() {
            @Override
            public void onResponse(Call<List<VideoResponse>> call, Response<List<VideoResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    videoList.clear();
                    canalList.clear();
                    usuarioList.clear();

                    List<VideoResponse> videoResponses = response.body();
                    for (VideoResponse videoResponse : videoResponses) {
                        Video video = videoResponse.getVideo();
                        Canal canal = videoResponse.getCanal();
                        Usuario usuario = videoResponse.getUsuario();

                        Log.d("DetailFragment", "Video: " + video);
                        Log.d("DetailFragment", "Canal: " + canal);
                        Log.d("DetailFragment", "Usuario: " + usuario);

                        if (video != null && canal != null && usuario != null) {
                            videoList.add(video);
                            canalList.add(canal);
                            usuarioList.add(usuario);
                        } else {
                            Log.e("DetailFragment", "Algum dos objetos é nulo.");
                        }
                    }

                    videoAdapter.notifyDataSetChanged();
                } else {
                    mostrarErro("Falha ao carregar vídeos", response.code());
                }
            }

            @Override
            public void onFailure(Call<List<VideoResponse>> call, Throwable t) {
                mostrarErro("Erro ao carregar vídeos: " + t.getMessage());
            }
        });
    }

//    private void fetchVideos() {
//        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
//        ApiService apiService = retrofit.create(ApiService.class);
//
//        apiService.findAllVideosComDetalhes().enqueue(new Callback<List<VideoResponse>>() {
//            @Override
//            public void onResponse(Call<List<VideoResponse>> call, Response<List<VideoResponse>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    videoList.clear();
//                    canalList.clear();
//                    usuarioList.clear();
//
//                    List<VideoResponse> videoResponses = response.body();
//                    for (VideoResponse videoResponse : videoResponses) {
//                        Video video = videoResponse.getVideo();
//                        Canal canal = videoResponse.getCanal();
//                        Usuario usuario = videoResponse.getUsuario();
//
//                        // Adicione logs para verificar se os objetos estão sendo preenchidos
//                        Log.d("HomeFragment", "Video: " + video);
//                        Log.d("HomeFragment", "Canal: " + canal);
//                        Log.d("HomeFragment", "Usuario: " + usuario);
//
//                        if (video != null && canal != null && usuario != null) {
//                            videoList.add(video);
//                            canalList.add(canal);
//                            usuarioList.add(usuario);
//                        } else {
//                            Log.e("HomeFragment", "Algum dos objetos é nulo.");
//                        }
//                    }
//
//                    videoAdapter.notifyDataSetChanged();
//                } else {
//                    mostrarErro("Falha ao carregar vídeos", response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<VideoResponse>> call, Throwable t) {
//                mostrarErro("Erro ao carregar vídeos: " + t.getMessage());
//            }
//        });
//
//
//
//    }

    private void fetchCanalDetails(int canalId, TextView nomeTextView, TextView biografiaTextView, CircleImageView fotoUsuarioImageView) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<Canal> callCanal = apiService.getCanalById(canalId);
        callCanal.enqueue(new Callback<Canal>() {
            @Override
            public void onResponse(Call<Canal> call, Response<Canal> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Canal canal = response.body();

                    // Atualiza a UI com as informações do canal
                    nomeTextView.setText(canal.getNome());

                    // Formata o número de seguidores e visualizações
//                    seguidoresTextView.setText(formatarNumeroAbreviado((int) canal.getSeguidores()));
//                    visualizacoesTextView.setText(formatarNumeroAbreviado((int) canal.getVisualizacoes()));

                    if (canal.getBio() != null){
                        biografiaTextView.setText(canal.getBio());
                    } else {
                        biografiaTextView.setText("Canal Sem Descrição");
                    }

                    // Verifica se o treinador_id não é nulo
                    Integer treinadorId = canal.getTreinadorId();
                    if (treinadorId != null) {
                        fetchTreinadorDetails(treinadorId, fotoUsuarioImageView); // Passar a ImageView
                    } else {
                        Log.e("Erro", "TreinadorId é nulo.");
                        Toast.makeText(getContext(), "Treinador não encontrado.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Erro", "Erro ao carregar canal: " + response.code());
                    Toast.makeText(getContext(), "Erro ao carregar canal.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Canal> call, Throwable t) {
                Log.e("Erro", "Erro: " + t.getMessage());
                Toast.makeText(getContext(), "Erro na requisição do canal.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void fetchTreinadorDetails(int treinadorId, CircleImageView fotoUsuarioImageView) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<Treinador> callTreinador = apiService.getTreinadorById(treinadorId);
        callTreinador.enqueue(new Callback<Treinador>() {
            @Override
            public void onResponse(Call<Treinador> call, Response<Treinador> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Treinador treinador = response.body();

                    Log.d("RespostaTreinador", "Treinador: " + treinador.toString());
                    Log.d("RespostaJSON", response.raw().toString());  // Loga a resposta bruta da API

                    // Verifica se o usuarioIdTreinador não é nulo
                    Integer usuarioIdTreinador = treinador.getUsuarioId();
                    if (usuarioIdTreinador != null) {
                        fetchUsuarioDetails(usuarioIdTreinador, fotoUsuarioImageView); // Passa a ImageView corretamente
                    } else {
                        Log.e("Erro", "Treinador ou UsuarioId é nulo.");
                        Toast.makeText(getContext(), "Usuário do treinador não encontrado.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Erro", "Erro ao carregar treinador: " + response.code());
                    Toast.makeText(getContext(), "Erro ao carregar treinador.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Treinador> call, Throwable t) {
                Log.e("Erro", "Erro: " + t.getMessage());
                Toast.makeText(getContext(), "Erro na requisição do treinador.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUsuarioDetails(int usuarioId, CircleImageView fotoUsuarioImageView) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<Usuario> callUsuario = apiService.getUsuarioById(usuarioId);
        callUsuario.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = response.body();

                    // Log para verificar o que a API está retornando
                    Log.d("Usuario", "Nome: " + usuario.getNome() + ", Foto: " + usuario.getFoto());

                    // Atualizar a UI com os detalhes do usuário
                    updateUIDetails(usuario, fotoUsuarioImageView);
                } else {
                    Log.e("Erro", "Erro ao carregar usuário: " + response.code());
                    Log.e("ErroResposta", "Mensagem de erro: " + response.message());
                    Toast.makeText(getContext(), "Erro ao carregar usuário.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Erro", "Erro na requisição do usuário: " + t.getMessage());
                Toast.makeText(getContext(), "Erro na requisição do usuário.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateUIDetails(Usuario usuario, CircleImageView fotoUsuarioImageView) {
        TextView nomeTreinadorTextView = getView().findViewById(R.id.txtNomeTreinadorCanalDetail);

        if (nomeTreinadorTextView != null) {
            nomeTreinadorTextView.setText(usuario.getNome());
        } else {
            Log.e("Erro", "NomeTreinadorTextView não foi encontrado");
        }

        // Decodificar e exibir a foto, caso exista
        if (usuario.getFoto() != null && !usuario.getFoto().isEmpty()) {
            Bitmap bitmap = decodeBase64(usuario.getFoto());
            if (bitmap != null) {
                fotoUsuarioImageView.setImageBitmap(bitmap);
            } else {
                fotoUsuarioImageView.setImageResource(R.drawable.ic_defaultuser); // Imagem padrão
                Log.e("Erro", "Falha ao decodificar imagem");
            }
        } else {
            fotoUsuarioImageView.setImageResource(R.drawable.ic_defaultuser); // Imagem padrão
            Log.e("Erro", "Campo foto do usuário está vazio");
        }
    }


    public Bitmap decodeBase64(String encodedImage) {
        try {
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } catch (IllegalArgumentException e) {
            Log.e("DecodeBase64", "Erro ao decodificar imagem Base64", e);
            return null;
        }
    }

    private String formatarNumeroAbreviado(int numero) {
        if (numero < 1_000) {
            return String.valueOf(numero); // Menos de mil, retorna o número normal
        } else if (numero < 1_000_000) {
            // Para milhares
            if (numero % 1_000 == 0) {
                return String.format(Locale.US, "%.0f mil", numero / 1_000.0).replace('.', ',');
            } else {
                return String.format(Locale.US, "%.1f mil", numero / 1_000.0).replace('.', ',');
            }
        } else {
            // Para milhões
            if (numero % 1_000_000 == 0) {
                return String.format(Locale.US, "%.0f mi", numero / 1_000_000.0).replace('.', ',');
            } else {
                return String.format(Locale.US, "%.1f mi", numero / 1_000_000.0).replace('.', ',');
            }
        }
    }
    private void mostrarErro(String mensagem, int codigo) {
        Toast.makeText(requireContext(), mensagem + " (Código: " + codigo + ")", Toast.LENGTH_SHORT).show();
        Log.e("Erro", mensagem + " - Código: " + codigo);
    }

    private void mostrarErro(String mensagem) {
        Toast.makeText(requireContext(), mensagem, Toast.LENGTH_SHORT).show();
        Log.e("Erro", mensagem);
    }
}
