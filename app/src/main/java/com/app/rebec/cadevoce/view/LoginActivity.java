package com.app.rebec.cadevoce.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.rebec.cadevoce.R;
import com.app.rebec.cadevoce.presenter.LoginPresenter;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends AppCompatActivity  implements GoogleApiClient.OnConnectionFailedListener,

                                                        LoginPresenter.LoginViewOutput{

    private LoginPresenter presenter;

    private CallbackManager mCallbackManager;
    private GoogleApiClient googleApiClient;
    private SignInButton signInButton;
    public static final int SIGN_IN_CODE = 777;
    private EditText inputEmail;
    private EditText inputSenha;
    private Button btnEntrar;
   // private DataBase db = new DataBase(this);


    public void navigateToHome() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void navigateToCadastroCliente() {
       Intent it = new Intent(LoginActivity.this, CadastrarUsuarioActivity.class);
       startActivity(it);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter();
        presenter.setLoginViewOutputDelegate(this);

        mCallbackManager = CallbackManager.Factory.create();

        /* Facebook login button */
        LoginButton mLoginButton = (LoginButton) findViewById(R.id.login_buttonFacebook);
        mLoginButton.registerCallback(mCallbackManager, presenter.getFacebookLoginCallback());


        // Login com o Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton = (SignInButton) findViewById(R.id.login_buttonGoogle);

        signInButton.setSize(SignInButton.SIZE_WIDE);

        signInButton.setColorScheme(SignInButton.COLOR_DARK);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });


        //Login com SQLITE
        TextView cadastroUser = (TextView) findViewById(R.id.idCadastrarConta);

        cadastroUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.registerUser();
             //   Intent it = new Intent(LoginActivity.this, CadastroUsuario.class);
            //    startActivity(it);
            }
        });

        btnEntrar = (Button) findViewById(R.id.login_button);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputSenha = (EditText) findViewById(R.id.inputSenha);

        btnEntrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                presenter.login(inputEmail.getText().toString(),inputSenha.getText().toString());
              //  entrar();
            }
        });
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_CODE){

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            goMainScreen();

        }else{
            Toast.makeText(this,"Não pode iniciar sessão", Toast.LENGTH_SHORT).show();
        }

    }

//    public void entrar(){
//        String email;
//        String senha;
//        email = inputEmail.getText().toString();
//        senha = inputSenha.getText().toString();
//       // String password = db.searchPass(email, senha);
//
//        if(password.equals(senha)){
//            Intent i = new Intent(LoginActivity.this, HomeFragment.class);
//            i.putExtra("nome", email);
//            startActivity(i);
//        } else {
//            Toast.makeText(LoginActivity.this, "Senha ou user incorreto!", Toast.LENGTH_SHORT).show();
//        }
//
//
//    }

    private void goMainScreen() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}