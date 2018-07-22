package com.app.rebec.cadevoce.model.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.rebec.cadevoce.model.VO.UsuarioVO;

public class CadastrarUsuarioDAO extends SQLiteOpenHelper {

    //NOME DA BASE DE DADOS
    public static final String NOME_BANCO = "sistema.db";
    public static final String TABELA = "usuario";

    public static final String NOME = "nome";
    public static final String EMAIL = "email";
    public static final String SENHA = "senha";
    //VERSÃO DO BANCO DE DADOS
    public static final int VERSAO = 1;

    private static String  TABLE_CREATE = "create table usuario ( _id integer primary key, nome text, email text, senha text );";
    SQLiteDatabase db;

    //CONSTRUTOR
    public CadastrarUsuarioDAO(Context context){

        super(context,NOME_BANCO,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String sql = "CREATE TABLE "+TABELA+"("
        //      + NOME + " VARCHAR(60),"
        //    + EMAIL + " VARCHAR(60),"
        //  + SENHA + " VARCHAR(10) "
        // +");";
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        onCreate(db);

    }

    /*MÉTODO QUE VAMOS USAR NA CLASSE QUE VAI EXECUTAR AS ROTINAS NO
    BANCO DE DADOS*/
    public SQLiteDatabase getConexaoDataBase(){

        return this.getWritableDatabase();
    }

    public void cadastrar(UsuarioVO usuario){
        db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();

        String query = "select * from usuario";
        Cursor cursor = db.rawQuery(query, null);
        valores.put("nome", usuario.getNome());
        valores.put("email", usuario.getEmail());
        valores.put("senha", usuario.getSenha());
        db.insert("usuario", null, valores);
        db.close();

    }

    public String searchPass(String email, String pass){
        db = this.getReadableDatabase();
        String query = "SELECT email,senha from usuario where email = '" + email + "'";
        Cursor cursor = db.rawQuery(query, null);
        String user;
        String senha = "Not found";

        if(cursor.moveToFirst()){
            do {
                user = cursor.getString(0);
                if(user.equals(email)){
                    senha = cursor.getString(1);
                    break;

                }

            }
            while(cursor.moveToNext());

        }
        return  senha;
    }
}
