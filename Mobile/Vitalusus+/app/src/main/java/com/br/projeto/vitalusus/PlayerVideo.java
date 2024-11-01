package com.br.projeto.vitalusus;

import android.content.Context;
import android.net.Uri;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PlayerVideo {

    public static Uri saveVideoToFile(Context context, byte[] videoBytes) throws IOException {
        // Cria um arquivo temporário
        File tempFile = File.createTempFile("video", ".mp4", context.getCacheDir());
        tempFile.deleteOnExit();

        // Escreve o conteúdo do vídeo no arquivo
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(videoBytes);
        }

        // Retorna o URI do arquivo
        return Uri.fromFile(tempFile);
    }
}
