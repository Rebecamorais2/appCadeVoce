package com.app.rebec.cadevoce.presenter;


import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;


public class LoginPresenter {

    public LoginViewOutput getLoginViewOutputDelegate() {
        return loginViewOutputDelegate;
    }

    public void setLoginViewOutputDelegate(LoginViewOutput loginViewOutputDelegate) {
        this.loginViewOutputDelegate = loginViewOutputDelegate;
    }

    public interface LoginViewOutput {
        void navigateToHome();
        void navigateToCadastroCliente();
    }

    private LoginViewOutput loginViewOutputDelegate;

    //Login Facebook
    private final FacebookCallback<LoginResult> mFacebookLoginCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            //VOlta pra Activiy
            getLoginViewOutputDelegate().navigateToHome();
        }

        @Override
        public void onCancel() {}

        @Override
        public void onError(FacebookException error) {}
    };

    public FacebookCallback<LoginResult> getFacebookLoginCallback() {
        return mFacebookLoginCallback;
    }

    public void registerUser() {
        getLoginViewOutputDelegate().navigateToCadastroCliente();
    }

    public void  login(String email, String password) {
        //Valida usuario e senha
        //Se ok, navega para home
        getLoginViewOutputDelegate().navigateToHome();
    }
}
