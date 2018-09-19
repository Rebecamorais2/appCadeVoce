package com.app.rebec.cadevoce.view;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.rebec.cadevoce.R;
import com.app.rebec.cadevoce.model.DAO.CadastrarUsuarioDAO;
import com.app.rebec.cadevoce.model.DAO.UsuarioDAO;
import com.app.rebec.cadevoce.model.VO.UsuarioVO;

public class CadastrarUsuarioActivity extends AppCompatActivity {

    private EditText inputNome;
    private EditText inputEmail;
    private EditText inputSenha;
    private EditText inputConfirmaSenha;
    private Button btnCriar;



    // private UsuarioRepository crud = new UsuarioRepository(getBaseContext());
    private UsuarioDAO db = new UsuarioDAO();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        final EditText inputNome = (EditText)  findViewById(R.id.inputNome);
        final EditText inputEmail = (EditText)  findViewById(R.id.inputEmail);
        final EditText inputSenha = (EditText)  findViewById(R.id.inputSenha);
        final EditText inputConfSenha = (EditText)  findViewById(R.id.inputConfirmaSenha);
        Button btn = (Button)findViewById (R.id.btnCriar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    cadastrar2();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }


            public void cadastrar2() throws Exception {
                String nome = inputNome.getText().toString();
                String email = inputEmail.getText().toString();
                String senha = inputSenha.getText().toString();
                String confirmaSenha = inputConfSenha.getText().toString();

                UsuarioVO user = new UsuarioVO();
                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmaSenha.isEmpty()) {
                    Toast.makeText(CadastrarUsuarioActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                } else {

                    if (!senha.equals(confirmaSenha)) {
                        Toast.makeText(CadastrarUsuarioActivity.this, "As senhas não são iguais!", Toast.LENGTH_SHORT).show();
                    } else {
                        user.setNome(nome);
                        user.setEmail(email);
                        user.setSenha(senha);
                        db.inserirUsuario(user, CadastrarUsuarioActivity.this);
                        Toast.makeText(CadastrarUsuarioActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                        inputNome.setText("");
                        inputEmail.setText("");
                        inputSenha.setText("");
                        inputConfSenha.setText("");
                    }
                }
            }
        });
    }
}