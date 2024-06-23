package com.br.projeto.vitalusus.util;

import android.content.Context;
import android.widget.Toast;

public class MensagemUtil {

    public static void exibir(Context c, String texto){
        Toast t = Toast.makeText(c, texto, Toast.LENGTH_LONG);
        t.show();
    }
}
