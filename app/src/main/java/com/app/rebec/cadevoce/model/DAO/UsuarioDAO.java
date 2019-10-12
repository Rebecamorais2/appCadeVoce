package com.app.rebec.cadevoce.model.DAO;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.rebec.cadevoce.model.VO.UsuarioVO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static com.facebook.FacebookSdk.getApplicationContext;


public class UsuarioDAO extends AppCompatActivity {

    public interface UsuarioDAODelegate {

        public void usuarioGravado(int id);

    }

    public interface LoginDAODelegate {

        public void loginUsuario(int id);

        public void getNomeEmail(String nome, String email);

    }

    private UsuarioDAODelegate delegate;

    private LoginDAODelegate delegateLogin;

    ProgressDialog pDialog;

    //Criar usuário
    private static final String URL = "https://cadevoce.azurewebsites.net/cadevoceWS/service/usuarios/";
    // private static final String URL= "http://10.0.2.2:8080/cadevoceWS/service/usuarios";
    private static String INSERIR = "add";
    private static String EDITAR = "edit";
    private static String EXCLUIR = "delete";
    private static String LISTAR = "list";
    private static String BUSCAR_POR_ID = "get";
    private static String ACESSAR = "login";


    EditText editTextUsername, editTextEmail, editTextPassword, editTextConfPassword;

    //Criar usuário
    public int inserirUsuario(UsuarioVO usuario, Context context) throws Exception {
        int idGerado = 0;


        RequestQueue queue = Volley.newRequestQueue(context);

        final JSONObject usuarioJSON = new JSONObject();
        try {
            usuarioJSON.put("nome", usuario.getNome());
            usuarioJSON.put("email", usuario.getEmail());
            usuarioJSON.put("senha", usuario.getSenha());
            usuarioJSON.put("confSenha", usuario.getConfSenha());
            usuarioJSON.put("idFotoPerfil", usuario.getIdFotoPerfil());
            usuarioJSON.put("idFacebook", usuario.getIdFacebook());
            usuarioJSON.put("idGoogle", usuario.getIdGoogle());

        } catch (JSONException ex) {
            Log.d("USUARIO", "Error " + ex.getLocalizedMessage());
        }

        JsonObjectRequest addUsuarioRequest = new JsonObjectRequest(Request.Method.POST, URL + INSERIR, usuarioJSON, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("USUARIO", "Sucesso " + response.toString());
                Log.d("DELEGATE", "Delegate -> " + (delegate != null));
                if (response != null && delegate != null) {
                    try {
                        int id = response.getInt("codigo");
                        delegate.usuarioGravado(id);

                    } catch (Exception ex) {
                        Log.e("USUARIO", "Erro no objeto retornado");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Usuário ou senha inválidos!", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.e("USUARIO", "Error " + error.toString());

                try {
                    if (error.networkResponse != null) {


                        int statusCode = error.networkResponse.statusCode;
                        if (error.networkResponse.data != null) {

                            String body = new String(error.networkResponse.data, "UTF-8");
                            if (statusCode == 400) {


                                JSONObject obj = new JSONObject(body);
                                String errorMsg = obj.getString("message");

                                // getting error msg message may be different according to your API
                                //Display this error msg to user
                                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();

                                Log.d("TAG", "error message" + errorMsg);
                            }
                        }
                    }
                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                    Log.e("TAG", "UNKNOWN ERROR :" + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Something went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                //   headers.put("Accept","application/json");
                return headers;
            }

            @Override
            public byte[] getBody() {

                try {
                    return usuarioJSON.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    Log.e("USUARIO", "Error de encoding " + e.getLocalizedMessage());
                }
                return null;
            }
        };


        queue.add(addUsuarioRequest);

        return idGerado;
    }


    //Editar usuário
    public void editarUsuario(UsuarioVO usuario, int codUser) throws Exception {

    }

    //Remover usuário
    public void removerUsuario(int codUser) throws Exception {

    }

    //Listar todos os usuários
    public UsuarioVO[] listarUsuarios() throws Exception {
        List<UsuarioVO> lista = new ArrayList<>();


        return lista.toArray(new UsuarioVO[lista.size()]);
    }

    //Buscar usuarios por ID
    public UsuarioVO buscarUsuarioPorId(int codUser) throws Exception {

        UsuarioVO usuario = null;

        return usuario;

    }


    public int login(final UsuarioVO usuario, final Context context) throws Exception {
        int idGerado = 0;

        List<UsuarioVO> lista = new ArrayList<>();


        RequestQueue queue = Volley.newRequestQueue(context);

        final JSONObject loginJSON = new JSONObject();
        try {

            loginJSON.put("email", usuario.getEmail());
            loginJSON.put("senha", usuario.getSenha());


        } catch (JSONException ex) {
            Log.d("LOGIN", "Error " + ex.getLocalizedMessage());
        }


        JsonObjectRequest loginUsuarioRequest = new JsonObjectRequest(Request.Method.POST, URL + ACESSAR, loginJSON, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("LOGIN", "Sucesso " + response.toString());
                Log.d("DELEGATE", "delegateLogin -> " + (delegateLogin != null));
                try {
                    if (response.getInt("codigo") == 0 && delegateLogin == null) {
                        int indice = response.getInt("codigo");
                        delegateLogin.loginUsuario(indice);


                    } else {

                        try {
                            int indice = response.getInt("codigo");
                            delegateLogin.loginUsuario(indice);

                            String email = response.getString("email");
                            String nome = response.getString("nome");
                            delegateLogin.getNomeEmail(nome, email);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.e("USUARIO", "Error " + error.toString());


                try {
                    if (error.networkResponse != null) {


                        int statusCode = error.networkResponse.statusCode;
                        if (error.networkResponse.data != null) {

                            String body = new String(error.networkResponse.data, "UTF-8");
                            if (statusCode == 400) {


                                JSONObject obj = new JSONObject(body);
                                String errorMsg = obj.getString("message");

                                // getting error msg message may be different according to your API
                                //Display this error msg to user
                                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();

                                Log.d("TAG", "error message" + errorMsg);
                            }
                        }
                    }
                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                    Log.e("TAG", "UNKNOWN ERROR :" + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Algo deu errado!", Toast.LENGTH_SHORT).show();
                }
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                //   headers.put("Accept","application/json");
                return headers;
            }

            @Override
            public byte[] getBody() {

                try {
                    return loginJSON.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    Log.e("Login", "Error de encoding " + e.getLocalizedMessage());
                }
                return null;
            }
        };


        queue.add(loginUsuarioRequest);

        return idGerado;
    }


    public AppCompatDelegate getDelegate() {
        return (AppCompatDelegate) delegate;
    }

    public void setDelegate(UsuarioDAODelegate delegate) {
        this.delegate = delegate;
    }

    public LoginDAODelegate getDelegateLogin() {
        return delegateLogin;
    }

    public void setLoginDAODelegate(LoginDAODelegate delegateLogin) {
        this.delegateLogin = delegateLogin;
    }

}