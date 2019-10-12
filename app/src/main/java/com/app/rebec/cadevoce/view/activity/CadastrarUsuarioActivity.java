package com.app.rebec.cadevoce.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.rebec.cadevoce.R;
import com.app.rebec.cadevoce.model.DAO.UsuarioDAO;
import com.app.rebec.cadevoce.model.VO.UsuarioVO;

public class CadastrarUsuarioActivity extends AppCompatActivity implements UsuarioDAO.UsuarioDAODelegate {


    // private UsuarioRepository crud = new UsuarioRepository(getBaseContext());
    private UsuarioDAO db = new UsuarioDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);


        db.setDelegate(this);

        final EditText inputNome = (EditText) findViewById(R.id.inputNome);
        final EditText inputEmail = (EditText) findViewById(R.id.inputEmail);
        final EditText inputSenha = (EditText) findViewById(R.id.inputSenha);
        final EditText inputConfSenha = (EditText) findViewById(R.id.inputConfirmaSenha);
        Button btn = (Button) findViewById(R.id.btnCriar);

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

                        //Toast.makeText(CadastrarUsuarioActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();


                    }
                }
            }
        });
    }

    @Override
    public void usuarioGravado(int id) {
        if (id != 0) {
            final EditText inputNome = (EditText) findViewById(R.id.inputNome);
            final EditText inputEmail = (EditText) findViewById(R.id.inputEmail);
            final EditText inputSenha = (EditText) findViewById(R.id.inputSenha);
            final EditText inputConfSenha = (EditText) findViewById(R.id.inputConfirmaSenha);

            inputNome.setText("");
            inputEmail.setText("");
            inputSenha.setText("");
            inputConfSenha.setText("");
            Toast.makeText(CadastrarUsuarioActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CadastrarUsuarioActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
//        Toast.makeText(CadastrarUsuarioActivity.this, "Usuário cadastrado com sucesso(" + id + ")!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(CadastrarUsuarioActivity.this, "Email já cadastrado!", Toast.LENGTH_LONG).show();
        }


    }


}