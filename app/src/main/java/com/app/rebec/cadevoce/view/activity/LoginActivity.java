package com.app.rebec.cadevoce.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.rebec.cadevoce.R;
import com.app.rebec.cadevoce.model.DAO.UsuarioDAO;
import com.app.rebec.cadevoce.model.VO.UsuarioVO;
import com.app.rebec.cadevoce.presenter.LoginPresenter;
//import com.facebook.CallbackManager;
//import com.facebook.login.widget.LoginButton;

//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,

        LoginPresenter.LoginViewOutput, UsuarioDAO.LoginDAODelegate {
    public static int codUser;
    public static String userEmail;
    public static String userName;


    private LoginPresenter presenter;
    SharedPreferences sp;

    //    private CallbackManager mCallbackManager;
    private GoogleApiClient googleApiClient;
    private SignInButton signInButton;
    public static final int SIGN_IN_CODE = 777;
    private EditText inputEmail;
    private EditText inputSenha;
    private Button btnEntrar;
    // private DataBase db = new DataBase(this);
    private UsuarioDAO userDao = new UsuarioDAO();

    public void navigateToHome() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void navigateToCadastroCliente() {
        Intent it = new Intent(LoginActivity.this, CadastrarUsuarioActivity.class);
        startActivity(it);
    }

    public void navigateToRecuperarSenha() {
        Intent it = new Intent(LoginActivity.this, RecuperarSenhaActivity.class);
        startActivity(it);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userDao.setLoginDAODelegate(this);

        presenter = new LoginPresenter();
        presenter.setLoginViewOutputDelegate(this);

//        mCallbackManager = CallbackManager.Factory.create();

        /* Facebook login button */
//        LoginButton mLoginButton = (LoginButton) findViewById(R.id.login_buttonFacebook);
//        mLoginButton.registerCallback(mCallbackManager, presenter.getFacebookLoginCallback());


//        // Login com o Google
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//
////        signInButton = (SignInButton) findViewById(R.id.login_buttonGoogle);
//
//        signInButton.setSize(SignInButton.SIZE_WIDE);
//
//        signInButton.setColorScheme(SignInButton.COLOR_DARK);

//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
//                startActivityForResult(intent, SIGN_IN_CODE);
//            }
//        });

        TextView cadastroUser = (TextView) findViewById(R.id.idCadastrarConta);

        cadastroUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.registerUser();
                //   Intent it = new Intent(LoginActivity.this, CadastroUsuario.class);
                //    startActivity(it);
            }
        });


        // Recuperar Senha
        TextView recuperarSenha = (TextView) findViewById(R.id.recuperarSenha);

        recuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToRecuperarSenha();
            }
        });

        //Login usuário
        btnEntrar = (Button) findViewById(R.id.login_button);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputSenha = (EditText) findViewById(R.id.inputSenha);
        sp = getSharedPreferences("login", MODE_PRIVATE);

        if (sp.contains("inputEmail") && sp.contains("inputSenha")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();   //finish current activity
        }

        btnEntrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // presenter.login(inputEmail.getText().toString(),inputSenha.getText().toString());

                entrar();

            }
        });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        mCallbackManager.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == SIGN_IN_CODE){
//
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
//        }
//    }
//
//    private void handleSignInResult(GoogleSignInResult result) {
//        if(result.isSuccess()){
//            goMainScreen();
//
//        }else{
//            Toast.makeText(this,"Não pode iniciar sessão", Toast.LENGTH_SHORT).show();
//        }
//
//    }

//    public ProgressDialog pDialog;
//
//    private void displayLoader() {
//        pDialog = new ProgressDialog(LoginActivity.this);
//        pDialog.setMessage("Carregando.. Por favor espere...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();
//
//    }

    private ProgressDialog pDialog;

    private void displayLoader() {
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Carregando.. Por favor espere...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    public void entrar() {
        String email;
        String senha;
//      Integer idUser;
        List<UsuarioVO> lista = new ArrayList<>();
        email = inputEmail.getText().toString();
        senha = inputSenha.getText().toString();
//        LoginActivity log = new LoginActivity();
//        int codUser = log.codUser;
//        idUser = codUser;

//        SharedPreferences.Editor editor=sp.edit();
//        editor.putString("inputEmail","email");
//        editor.putString("inputSenha","senha");
//        editor.putInt("codUser", (int) Integer.parseInt("idUser"));
//        editor.commit();

        UsuarioVO user = new UsuarioVO();
        user.setEmail(email);
        user.setSenha(senha);
//        user.setCodigo(idUser);

//        ProgressDialog progressLogin = new ProgressDialog(LoginActivity.this);
//        progressLogin.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressLogin.setTitle("Login");
//        progressLogin.setMessage("Verificando Login e Senha");
//        progressLogin.setMax(10);
//        progressLogin.show();
//        progressLogin.setIndeterminate(false);

        if (!email.isEmpty() || !senha.isEmpty()) {

            try {

                userDao.login(user, LoginActivity.this);
//                progressLogin.isShowing();
                // progressLogin.dismiss();
            } catch (Exception e) {

                e.printStackTrace();
            }
        } else {


            Toast.makeText(LoginActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
//            progressLogin.dismiss();


        }
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public void loginUsuario(int id) {
        codUser = id;

        if (id != 0) {

            navigateToHome();
        } else {

            Toast.makeText(getApplicationContext(), "Usuário ou senha inválidos!", Toast.LENGTH_SHORT).show();
        }
        // Toast.makeText(LoginActivity.this, "Entrou no metodo loginUsuario", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getNomeEmail(String nome, String email) {

        userName = nome;
        userEmail = email;

    }


}