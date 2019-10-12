package com.app.rebec.cadevoce.model.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.app.rebec.cadevoce.model.VO.DesaparecidoVO;
import com.app.rebec.cadevoce.view.activity.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

public class DesaparecidoDAO {


    //Variável global que recebe o id do usuário
    LoginActivity log = new LoginActivity();
    int codUser = log.codUser;

    public interface DesaparecidoDAODelegate {

        public void desaparecidoGravado(int id);

        public void desaparecidoEditado(int id);

        public void fotosGravadas(int id);

        public void buscarDesaparecido(int id);

    }

    private DesaparecidoDAO.DesaparecidoDAODelegate delegate;

    //    private static final String URL= "http://10.0.2.2:8080/cadevoceWS/service/desaparecidos";
    private static final String URL = "https://cadevoce.azurewebsites.net/cadevoceWS/service/desaparecidos";
    private static final String URL_FOTO = "https://cadevoce.azurewebsites.net/cadevoceWS/service/faces/upload_fotos";//para o upload de fotos


    private static String NAMESPACE = "";

    private static String INSERIR = "add";
    private static String EDITAR = "edit/";
    private static String EXCLUIR = "delete";
    private static String LISTAR = "list";
    private static String BUSCAR_POR_ID = "get";
    private static String BUSCAR = "search";

    //criar um método inserir foto {código que está na activity}

    //Criar Desaparecido
    public int inserirDesaparecido(DesaparecidoVO desaparecido, Context context) throws Exception {
        int idGerado = 0;

        RequestQueue queue = Volley.newRequestQueue(context);

        final JSONObject desaparecidoJSON = new JSONObject();
        try {
            String nome = desaparecido.getNome();


            desaparecidoJSON.put("nome", desaparecido.getNome());
            desaparecidoJSON.put("idade", desaparecido.getIdade());
            desaparecidoJSON.put("racaDescricao", desaparecido.getRacaDescricao());
            desaparecidoJSON.put("olhosDescricao", desaparecido.getOlhosDescricao());
            desaparecidoJSON.put("cabeloDescricao", desaparecido.getCabeloDescricao());
            desaparecidoJSON.put("altura", desaparecido.getAltura());
            desaparecidoJSON.put("sexoDescricao", desaparecido.getSexoDescricao());
            String cidade1 = String.valueOf(desaparecido.getCidDescricao());
            String cidade = cidade1.substring(1, cidade1.length() - 1);
            desaparecidoJSON.put("cidDescricao", cidade);
            desaparecidoJSON.put("estDescricao", desaparecido.getEstDescricao());
            desaparecidoJSON.put("dataDesaparecimento", desaparecido.getDataDesaparecimento());
            desaparecidoJSON.put("parentesco", desaparecido.getParentesco());
            desaparecidoJSON.put("dataCadastro", desaparecido.getDataCadastro());
            desaparecidoJSON.put("telefoneContato", desaparecido.getTelefoneContato());
            desaparecidoJSON.put("descricao", desaparecido.getDescricao());
            desaparecidoJSON.put("idUsuario", codUser);


        } catch (JSONException ex) {
            Log.d("Desaparecido", "Error " + ex.getLocalizedMessage());
        }
//System.out.println("Verificação do Json gerado: " + desaparecidoJSON);

        JsonObjectRequest addDesaparecidoRequest = new JsonObjectRequest(Request.Method.POST, URL + "/" + INSERIR, desaparecidoJSON, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d("Desaparecido", "OK " + response.toString());
                if (response != null && delegate != null) {
                    try {
                        int id = response.getInt("codDesap");//estava como "codigo"

                        delegate.desaparecidoGravado(id);
                    } catch (Exception ex) {
                        Log.e("Desaparecido", "Erro no objeto retornado");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Desaparecido", "Error " + error.toString());

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

                                Log.d("TAG", "Mensagem de erro" + errorMsg);
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


                return headers;
            }

            @Override
            public byte[] getBody() {

                try {
                    return desaparecidoJSON.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    Log.e("Desaparecido", "Error de encoding " + e.getLocalizedMessage());
                }
                return null;
            }
        };

        //inserido para que não duplique o cadastro de desaparecido
        addDesaparecidoRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(addDesaparecidoRequest);
        return idGerado;
    }


    //Editar Desaparecido
    public void editarDesaparecido(DesaparecidoVO desaparecido, Context context) throws Exception {
        int codDesap = desaparecido.getCodDesap();

        RequestQueue queue = Volley.newRequestQueue(context);

        final JSONObject desaparecidoJSON = new JSONObject();
        try {
            String nome = desaparecido.getNome();


            desaparecidoJSON.put("nome", desaparecido.getNome());
            desaparecidoJSON.put("idade", desaparecido.getIdade());
            desaparecidoJSON.put("racaDescricao", desaparecido.getRacaDescricao());
            desaparecidoJSON.put("olhosDescricao", desaparecido.getOlhosDescricao());
            desaparecidoJSON.put("cabeloDescricao", desaparecido.getCabeloDescricao());
            desaparecidoJSON.put("altura", desaparecido.getAltura());
            desaparecidoJSON.put("sexoDescricao", desaparecido.getSexoDescricao());
            String cidade1 = String.valueOf(desaparecido.getCidDescricao());
            String cidade = cidade1.substring(1, cidade1.length() - 1);
            desaparecidoJSON.put("cidDescricao", cidade);
            desaparecidoJSON.put("estDescricao", desaparecido.getEstDescricao());
            desaparecidoJSON.put("dataDesaparecimento", desaparecido.getDataDesaparecimento());
            desaparecidoJSON.put("parentesco", desaparecido.getParentesco());
            desaparecidoJSON.put("dataCadastro", desaparecido.getDataCadastro());
            desaparecidoJSON.put("telefoneContato", desaparecido.getTelefoneContato());
            desaparecidoJSON.put("descricao", desaparecido.getDescricao());
            desaparecidoJSON.put("idUsuario", codUser);


        } catch (JSONException ex) {
            Log.d("Desaparecido", "Error " + ex.getLocalizedMessage());
        }
        //System.out.println("Verificação do Json gerado: " + desaparecidoJSON);

        JsonObjectRequest editarDesaparecidoRequest = new JsonObjectRequest(Request.Method.PUT, URL + "/" + EDITAR + codDesap, desaparecidoJSON, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d("Desaparecido", "OK " + response.toString());
                if (response != null && delegate != null) {
                    try {
                        int id = response.getInt("codDesap");//estava como "codigo"
                        delegate.desaparecidoEditado(id);

                    } catch (Exception ex) {
                        Log.e("Desaparecido", "Erro no objeto retornado");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Desaparecido", "Error " + error.toString());

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

                                Log.d("TAG", "Mensagem de erro" + errorMsg);
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


                return headers;
            }

            @Override
            public byte[] getBody() {

                try {
                    return desaparecidoJSON.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    Log.e("Desaparecido", "Error de encoding " + e.getLocalizedMessage());
                }
                return null;
            }
        };

        queue.add(editarDesaparecidoRequest);


    }


    public int buscarDesaparecido(DesaparecidoVO desaparecido, Context context) throws Exception {
        int idGerado = 0;

        RequestQueue queue = Volley.newRequestQueue(context);

        final JSONObject desaparecidoJSON = new JSONObject();
        try {

            desaparecidoJSON.put("nome", desaparecido.getNome());

            desaparecidoJSON.put("estado", desaparecido.getEstDescricao());
            desaparecidoJSON.put("cidade", desaparecido.getCidDescricao());


        } catch (JSONException ex) {
            Log.d("Desaparecido", "Error " + ex.getLocalizedMessage());
        }

        JsonObjectRequest addDesaparecidoRequest = new JsonObjectRequest(Request.Method.GET, URL + "/" + BUSCAR, desaparecidoJSON, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Desaparecido", "OK " + response.toString());
                if (response != null && delegate != null) {
                    try {
                        int id = response.getInt("codigo");

                        delegate.buscarDesaparecido(id);
                    } catch (Exception ex) {
                        Log.e("Desaparecido", "Erro no objeto retornado");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Desaparecido", "Error " + error.toString());

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

                                Log.d("TAG", "Mensagem de erro" + errorMsg);
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

                return headers;
            }

            @Override
            public byte[] getBody() {

                try {
                    return desaparecidoJSON.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    Log.e("Desaparecido", "Error de encoding " + e.getLocalizedMessage());
                }
                return null;
            }
        };

        queue.add(addDesaparecidoRequest);

        return idGerado;
    }


    public DesaparecidoDAO.DesaparecidoDAODelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(DesaparecidoDAO.DesaparecidoDAODelegate delegate) {
        this.delegate = delegate;
    }


}
