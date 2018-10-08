package com.app.rebec.cadevoce.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.rebec.cadevoce.view.BuscarNomeFragment;
import com.app.rebec.cadevoce.view.CadastrarDesaparecidoFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DesaparecidoService implements  CityListResultHandler {

    static String URL = "https://gist.githubusercontent.com/letanure/3012978/raw/36fc21d9e2fc45c078e0e0e07cce3c81965db8f9/estados-cidades.json";

    //método para requisição da lista de estados
    public static void loadStateList(Context context, final StateListResultHandler handler) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    List<String> estados = parseStateList(jsonObject);
                    handler.handleStateList(estados);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    //inicio do método de requisição para cidades
    public static void loadCityList(Context context, final CityListResultHandler handler) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    List<String> cidades = parseCityList(jsonObject);
                    handler.handleCityList(cidades);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
    //fim do método para requisição de cidades

    //Pegando nome dos estados
    private static List<String> parseStateList(JSONObject jsonObject) throws JSONException {

        ArrayList<String> estadosLista = new ArrayList<>();

        JSONArray jsonArrayEstados = jsonObject.getJSONArray("estados");
        for (int i = 0; i < jsonArrayEstados.length(); i++) {
            JSONObject jsonObject1 = jsonArrayEstados.getJSONObject(i);
            String nomeEstado = jsonObject1.getString("nome");
            estadosLista.add(nomeEstado);
        }
        return estadosLista;
    }

    // Pegando nome das cidades
    private static List<String> parseCityList(final JSONObject jsonObject)throws Exception{
        final ArrayList<String> cidadesLista = new ArrayList<>();

        JSONArray jsonArrayEstados = jsonObject.getJSONArray("estados");
        //Instancia da classe ManterDesaparecidoActivity
        CadastrarDesaparecidoFragment mda = new CadastrarDesaparecidoFragment();
        //Instancia da classe BuscarPorNome
        BuscarNomeFragment bpn = new BuscarNomeFragment();

        //Variável estadoAtual recebe o valor da variável estática [atual] da classe ManterDesaparecidoActivity

        int  estadoAtualBpn = bpn.atual;
        int  estadoAtual = mda.atual;

        if (estadoAtual !=30) {
            JSONObject jsonObject1 = jsonArrayEstados.getJSONObject(estadoAtual);
            JSONArray jsonArrayCidades = jsonObject1.getJSONArray("cidades");
            //Fazendo a verificação dos valores no Logcat
            String nomeEstado = jsonObject1.getString("nome");
            Log.d("CIDADEARRAYMDA", "" + estadoAtual + " do estado: " +nomeEstado);
            //Retorna o valor da variavel atual em ManterDesaparecidoActivity para 30 de novo
            mda.atual =30;

            for (int j = 0; j < jsonArrayCidades.length(); j++) {
                String nomeCidade = (String) jsonArrayCidades.get(Integer.parseInt(String.valueOf(j)));
                cidadesLista.add(nomeCidade);
            }

        } else{
            JSONObject jsonObject1 = jsonArrayEstados.getJSONObject(estadoAtualBpn);
            JSONArray jsonArrayCidades = jsonObject1.getJSONArray("cidades");
            //Fazendo a verificação dos valores no Logcat
            String nomeEstado = jsonObject1.getString("nome");
            Log.d("CIDADEARRAYBPN", "" + estadoAtualBpn + " do estado: " +nomeEstado);
            //Retorna o valor da variavel atual em BuscaPorNome para 30 de novo
            bpn.atual=30;

            for (int j = 0; j < jsonArrayCidades.length(); j++) {
                String nomeCidade = (String) jsonArrayCidades.get(Integer.parseInt(String.valueOf(j)));
                cidadesLista.add(nomeCidade);
            }

        }
        return cidadesLista;
    }
    //Assinatura do método handleCityList
    @Override
    public void handleCityList(List<String> cityList) { }
    //Assinatura do método handleEstadoAtual(está sendo usado na classe ManterDesaparecidoActivity)
    @Override
    public void handleEstadoAtual(int estadoAtual) { }

}
