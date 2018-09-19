package com.app.rebec.cadevoce.model.DAO;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.app.rebec.cadevoce.model.VO.DesaparecidoVO;
import com.app.rebec.cadevoce.model.VO.UsuarioVO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.google.gson.internal.bind.TypeAdapters.URL;

public class DesaparecidoDAO {

    private static final String URL= "http://192.168.1.18:8080/cadevoceWS/service/desaparecidos";
    private static String NAMESPACE = "";

    private static String INSERIR = "add";
    private static String EDITAR = "edit";
    private static String EXCLUIR = "delete";
    private static String LISTAR = "list";
    private static String BUSCAR_POR_ID= "get";

    //Criar Desaparecido
    public int inserirDesaparecido(DesaparecidoVO desaparecido, Context context) throws Exception {
        int idGerado = 0;

        RequestQueue queue = Volley.newRequestQueue(context);

        final JSONObject desaparecidoJSON = new JSONObject();
        try {

            desaparecidoJSON.put("nome",desaparecido.getNome());
            desaparecidoJSON.put("idade",desaparecido.getIdade());
            desaparecidoJSON.put("altura",desaparecido.getAltura());

            desaparecidoJSON.put("sexo", desaparecido.getSexo());
            desaparecidoJSON.put("dataDesaparecimento", desaparecido.getDataDesaparecimento());
            desaparecidoJSON.put("dataCadastro", desaparecido.getDataCadastro());
            desaparecidoJSON.put("telefoneContato",desaparecido.getTelefoneContato());
            desaparecidoJSON.put("descricao",desaparecido.getDescricao());

          //  ByteArrayOutputStream stream = new ByteArrayOutputStream();
           // bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
           // byte[] imgByteArray = stream.toByteArray();

//            String imgArray = Base64.encodeToString(imgByteArray, Base64.DEFAULT);
//            JSONObject jsonImg = new JSONObject().put("imgByteArray", imgArray);

            desaparecidoJSON.put("idFaces",desaparecido.getIdFaces());
           desaparecidoJSON.put("fotoPrincipal",desaparecido.isFotoPrincipal());



        } catch (JSONException ex) {
            Log.d("USUARIO", "Error " + ex.getLocalizedMessage());
        }

        JsonObjectRequest addDesaparecidoRequest = new JsonObjectRequest
                (Request.Method.PUT, URL + "/" + INSERIR, desaparecidoJSON, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Desaparecido", "OK " + response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Desaparecido", "Error " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
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

}
