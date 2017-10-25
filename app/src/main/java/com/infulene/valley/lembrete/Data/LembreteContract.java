package com.infulene.valley.lembrete.Data;

import android.provider.BaseColumns;

/**
 * Created by user on 24-Oct-17.
 *
 * Classe responsavel pelo nome das colunas na Base de dadis SQLITE
 *
 * Cada variavel representa o nome de uma coluna na base de dados
 *
 *
 */

public class LembreteContract {


    private LembreteContract() {
    }

    public static final class LembreteEntry implements BaseColumns {


        public static final String TABLE_NAME = "lembretes";   //Nome da Tabela

        public static final String COLUMN_TITULO = "titulo"; //Coluna titulo

        public static final String COLUMN_DETALHES = "detalhes";

        public static final String COLUMN_HORA = "hora";

        public static final String COLUMN_TIMESTAMP = "timestamp";

    }
}
