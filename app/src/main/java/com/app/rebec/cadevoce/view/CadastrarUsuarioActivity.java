package com.app.rebec.cadevoce.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.rebec.cadevoce.R;
import com.app.rebec.cadevoce.model.DAO.CadastrarUsuarioDAO;
import com.app.rebec.cadevoce.model.VO.UsuarioVO;

public class CadastrarUsuarioActivity extends AppCompatActivity {

    private EditText inputNome;
    private EditText inputEmail;
    private EditText inputSenha;
    private EditText inputConfirmaSenha;
    private Button btnCriar;

    // private UsuarioRepository crud = new UsuarioRepository(getBaseContext());
    private CadastrarUsuarioDAO db = new CadastrarUsuarioDAO(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Banco dados


        //tabela tarefas60
        // bancoDados.execSQL("CREATE TABLE IF NOT EXISTS usuario(id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR(40), email VARCHAR(60), senha VARCHAR(25) ) ");


        inputNome = (EditText) findViewById(R.id.inputNome);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputSenha = (EditText) findViewById(R.id.inputSenha);
        inputConfirmaSenha = (EditText) findViewById(R.id.inputConfirmaSenha);
        btnCriar = (Button) findViewById(R.id.btnCriar);

        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar2();

            }
        });
        cadastrar2();




    }


    public void cadastrar2(){
        String nome = inputNome.getText().toString();
        String email = inputEmail.getText().toString();
        String senha = inputSenha.getText().toString();
        String confirmaSenha = inputConfirmaSenha.getText().toString();
        UsuarioVO user = new UsuarioVO();
        if(nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmaSenha.isEmpty()){
            Toast.makeText(CadastrarUsuarioActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
        } else {

            if (!senha.equals(confirmaSenha)) {
                Toast.makeText(CadastrarUsuarioActivity.this, "As senhas não são iguais!", Toast.LENGTH_SHORT).show();
            } else {
                user.setNome(nome);
                user.setEmail(email);
                user.setSenha(senha);
                db.cadastrar(user);
                Toast.makeText(CadastrarUsuarioActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                inputNome.setText("");
                inputEmail.setText("");
                inputSenha.setText("");
                inputConfirmaSenha.setText("");
            }

        }

    }

}
