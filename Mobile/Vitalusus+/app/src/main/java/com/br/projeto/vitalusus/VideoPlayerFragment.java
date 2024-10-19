package com.br.projeto.vitalusus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.br.projeto.vitalusus.model.Canal;
import com.br.projeto.vitalusus.model.Treinador;
import com.br.projeto.vitalusus.model.Usuario;
import com.br.projeto.vitalusus.model.Video;
import com.br.projeto.vitalusus.network.ApiService;
import com.br.projeto.vitalusus.network.RetrofitClient;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;

import java.io.IOException;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VideoPlayerFragment extends Fragment {

    private PlayerView playerView;
    private ExoPlayer exoPlayer;

    private static final String ARG_CANAL_ID = "canal_id";
    private static final String ARG_USUARIO_ID = "usuario_id";
    private static final String ARG_VIDEO_ID = "video_id";

    private int canalId;
    private int usuarioId;
    private int videoId;

    public static VideoPlayerFragment newInstance(int canalId, int usuarioId, int videoId) {
        VideoPlayerFragment fragment = new VideoPlayerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CANAL_ID, canalId);
        args.putInt(ARG_USUARIO_ID, usuarioId);
        args.putInt(ARG_VIDEO_ID, videoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            canalId = getArguments().getInt(ARG_CANAL_ID);
            usuarioId = getArguments().getInt(ARG_USUARIO_ID);
            videoId = getArguments().getInt(ARG_VIDEO_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        getActivity().setTitle("Player de Video");

        playerView = view.findViewById(R.id.player_view);

        TextView tvTituloVideo = view.findViewById(R.id.txtTituloVideoPlayer);
        TextView tvNomeCanal = view.findViewById(R.id.txtNomeCanalVideoPlayer);
        TextView tvDataPublic = view.findViewById(R.id.txtDataPubliVideoPlayer);
        TextView tvSeguidoresCanal = view.findViewById(R.id.txtSeguidoresCanalVideoPlayer);
        TextView tvVisualizacoesVideo = view.findViewById(R.id.txtVisualizacoesVideoPlayer);

        CircleImageView fotoUsuarioImageView = view.findViewById(R.id.imgFotoCanalVideoPlayer); // Aqui a ImageView é inicializada

        fetchCanalDetails(canalId, tvNomeCanal, tvSeguidoresCanal, tvTituloVideo, tvDataPublic, fotoUsuarioImageView);
        fetchVideo(videoId, tvTituloVideo, tvDataPublic, tvVisualizacoesVideo);
        fetchUsuarioDetails(usuarioId, fotoUsuarioImageView);

        // Inicializar o ExoPlayer
        exoPlayer = new ExoPlayer.Builder(requireContext()).build();
        playerView.setPlayer(exoPlayer);

        // Obter o ID do vídeo (você pode obter isso dos argumentos do fragmento)
//        long videoId = getArguments().getLong("videoId");

        // Buscar vídeo do banco de dados usando Retrofit
//        fetchVideoFromDatabase(videoId);

        return view;
    }

    private void fetchCanalDetails(int canalId, TextView tvNomeCanal, TextView tvSeguidoresCanal, TextView tvTituloVideo, TextView tvDataPublic, CircleImageView fotoUsuarioImageView) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<Canal> callCanal = apiService.getCanalById(canalId);
        callCanal.enqueue(new Callback<Canal>() {
            @Override
            public void onResponse(Call<Canal> call, Response<Canal> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Canal canal = response.body();

                    tvNomeCanal.setText(canal.getNome());
                    tvSeguidoresCanal.setText(formatarNumeroAbreviado((int) canal.getSeguidores()));

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

    private void fetchVideo(int videoId, TextView tvTituloVideo, TextView tvDataPublic, TextView tvVisualizacoesVideo) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<Video> callVideo = apiService.getVideolById(videoId);
        callVideo.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Video video = response.body();

                    tvTituloVideo.setText(video.getTitulo());
                    tvDataPublic.setText(video.getDataPubli());
                    tvVisualizacoesVideo.setText(video.getVisualizacoes().toString());

                } else {
                    Log.e("Erro", "Erro ao carregar Video: " + response.code());
                    Toast.makeText(getContext(), "Erro ao carregar video.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                Log.e("Erro", "Erro: " + t.getMessage());
                Toast.makeText(getContext(), "Erro na requisição do Video.", Toast.LENGTH_SHORT).show();
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

    public Bitmap decodeBase64(String encodedImage) {
        try {
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } catch (IllegalArgumentException e) {
            Log.e("DecodeBase64", "Erro ao decodificar imagem Base64", e);
            return null;
        }
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

//    private void fetchVideoFromDatabase(long videoId) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:3030/") // Insira a URL correta do servidor
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        VideoApi videoApi = retrofit.create(VideoApi.class);
//        Call<Video> call = videoApi.getVideoById(videoId);
//
//        call.enqueue(new Callback<Video>() {
//            @Override
//            public void onResponse(Call<Video> call, Response<Video> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Video video = response.body();
//
//                    // Exibir informações do vídeo
////                    tvTituloVideo.setText(video.getTitulo());
////                    tvChannelName.setText(video.getCanal().getNome());
////                    tvDatePosted.setText(video.getCanal().getBio());
////
//                    // Carregar e reproduzir o vídeo
////                    playVideoFromBytes(video.getThumbnail());
//                } else {
//                    Log.e("VideoPlayerFragment", "Erro ao buscar o vídeo");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Video> call, Throwable t) {
//                Log.e("VideoPlayerFragment", "Falha na conexão: " + t.getMessage());
//            }
//        });
//    }

    private void playVideoFromBytes(byte[] videoBytes) {
        try {
            // Converter os bytes do vídeo em um arquivo temporário e obter o URI
            Uri videoUri = PlayerVideo.saveVideoToFile(requireContext(), videoBytes);

            // Preparar o ExoPlayer com o arquivo de vídeo
            MediaItem mediaItem = MediaItem.fromUri(videoUri);
            exoPlayer.setMediaItem(mediaItem);
            exoPlayer.prepare();
            exoPlayer.play();
        } catch (IOException e) {
            Log.e("VideoPlayerFragment", "Erro ao salvar o vídeo temporariamente", e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}
