package com.app.rebec.cadevoce.view.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.app.rebec.cadevoce.R;
import com.app.rebec.cadevoce.model.DAO.DesaparecidoDAO;
import com.app.rebec.cadevoce.model.VO.DesaparecidoVO;
import com.app.rebec.cadevoce.model.VO.State;
import com.app.rebec.cadevoce.presenter.PageController;
//import com.app.rebec.cadevoce.service.DesaparecidoService;
import com.app.rebec.cadevoce.view.activity.MainActivity;
import com.app.rebec.cadevoce.view.activity.MeusCadastrosActivity;
import com.app.rebec.cadevoce.view.adapters.CustomRecyclerAdapter;
import com.app.rebec.cadevoce.view.adapters.CustonRecycleAdapaterBuscarNome;
import com.app.rebec.cadevoce.view.adapters.MySingleton;
import com.app.rebec.cadevoce.view.adapters.StateAdapter;
import com.app.rebec.cadevoce.view.util.Page;
import com.app.rebec.cadevoce.view.util.TargetPage;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BuscarNomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BuscarNomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//import static com.facebook.FacebookSdk.getApplicationContext;

public class BuscarNomeFragment extends Fragment implements TargetPage, DesaparecidoDAO.DesaparecidoDAODelegate {

    private String URL_NOME = "https://cadevoce.azurewebsites.net/cadevoceWS/service/desaparecidos/search?nome=";
    private String URL_ESTADO = "&estado=";
    private String URL_CIDADE = "&cidade=";

    List<DesaparecidoVO> desaparecidoVOList;
    RecyclerView.Adapter mAdapter;
    RecyclerView recyclerView;
    RequestQueue rq;
    RecyclerView.LayoutManager layoutManager;
    Button cadastrarDesap;


    protected EditText inputNome;

    private static final String KEY_STATE = "estado";
    private static final String KEY_CITIES = "cidades";

    Spinner stateSpinner;
    Spinner citiesSpinner;
    private ProgressDialog pDialog;
    private PageController navigationController;

    private String cities_url = "https://cadevoce.azurewebsites.net/cadevoceWS/service/estado-cidades/list/";

    private void displayLoader() {
        pDialog = new ProgressDialog(this.getActivity());
        pDialog.setMessage("Carregando.. Por favor espere...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    /**
     * Helps in downloading the state and city details
     * and populating the spinner
     */
    private void loadStateCityDetails() {
        final List<State> statesList = new ArrayList<>();
        final List<String> states = new ArrayList<>();

        JsonArrayRequest jsArrayRequest = new JsonArrayRequest(Request.Method.GET, cities_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
                pDialog.dismiss();
                try {
                    //Parse the JSON response array by iterating over it
                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject response = responseArray.getJSONObject(i);
                        String state = response.getString(KEY_STATE);
                        JSONArray cities = response.getJSONArray(KEY_CITIES);
                        List<String> citiesList = new ArrayList<>();
                        for (int j = 0; j < cities.length(); j++) {
                            citiesList.add(cities.getString(j));
                        }
                        statesList.add(new State(state, citiesList));
                        states.add(state);

                    }
                    final StateAdapter stateAdapter;
                    stateAdapter = new StateAdapter(getContext(), R.layout.state_list, R.id.spinnerText, statesList);
                    stateSpinner.setAdapter(stateAdapter);

                    stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            //Populate City list to the second spinner when
                            // a state is chosen from the first spinner
                            State cityDetails = stateAdapter.getItem(position);
                            List<String> cityList = cityDetails.getCities();
                            ArrayAdapter citiesAdapter = new ArrayAdapter<>(getContext(), R.layout.city_list, R.id.citySpinnerText, cityList);
                            citiesSpinner.setAdapter(citiesAdapter);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();

                //Display error message whenever an error occurs
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsArrayRequest);
    }


    public void buscarDesaparecido() throws Exception {
        String nome = inputNome.getText().toString();
        String estado = stateSpinner.getSelectedItem().toString();
        String cidade = citiesSpinner.getSelectedItem().toString();

        rq = Volley.newRequestQueue(getActivity());

        if (nome.isEmpty() || estado.isEmpty() || cidade.isEmpty()) {

            Toast.makeText(getActivity(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();


        } else {

            sendRequest(nome, estado, cidade);

        }

    }

    private DesaparecidoDAO db = new DesaparecidoDAO();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buscar_nome, container, false);
        db.setDelegate(this);
        inputNome = view.findViewById(R.id.inputNome);
        stateSpinner = view.findViewById(R.id.stateSpinner);
        citiesSpinner = view.findViewById(R.id.citiesSpinner);

        rq = Volley.newRequestQueue(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewContainer3);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        desaparecidoVOList = new ArrayList<>();

        displayLoader();
        loadStateCityDetails();

        Button burcarNome = view.findViewById(R.id.buscarNome);
        cadastrarDesap = view.findViewById(R.id.btncadastrarDesap2);
        cadastrarDesap.setVisibility(View.INVISIBLE);


        burcarNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    desaparecidoVOList.clear();
                    buscarDesaparecido();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        cadastrarDesap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if (navigationController != null) {
                        navigationController.changePage(Page.CadastrarDesaparecido, null);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        return view;

    }

    public void sendRequest(String nome, String estado, String cidade) {

        final JsonArrayRequest jsonArrayFaceRequest = new JsonArrayRequest(Request.Method.GET, URL_NOME + nome + URL_ESTADO + estado + URL_CIDADE + cidade, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                System.out.println("Conferindo a resposta da requisição: " + response);
                if (response.length() == 0) {
                    Toast.makeText(getActivity(), "Não foram encontrados resultados para a busca", Toast.LENGTH_SHORT).show();
                    cadastrarDesap.setVisibility(View.VISIBLE);
                } else {
                    cadastrarDesap.setVisibility(View.INVISIBLE);
                    for (int i = 0; i < response.length(); i++) {


                        DesaparecidoVO desaparecidoVO = new DesaparecidoVO();

                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            desaparecidoVO.setNome(jsonObject.getString("nome"));
                            desaparecidoVO.setCidadeDescricao(jsonObject.getString("cidDescricao"));
                            desaparecidoVO.setEstDescricao(jsonObject.getString("estDescricao"));
                            desaparecidoVO.setDataDesaparecimento(jsonObject.getString("dataDesaparecimento"));
                            desaparecidoVO.setURLface_01(jsonObject.getString("face_01"));
                            desaparecidoVO.setURLface_03(jsonObject.getString("face_03"));
                            desaparecidoVO.setIdade(jsonObject.getInt("idade"));
                            desaparecidoVO.setRacaDescricao(jsonObject.getString("racaDescricao"));
                            desaparecidoVO.setOlhosDescricao(jsonObject.getString("olhosDescricao"));
                            desaparecidoVO.setCabeloDescricao(jsonObject.getString("cabeloDescricao"));
                            desaparecidoVO.setAltura(jsonObject.getDouble("altura"));
                            desaparecidoVO.setSexoDescricao(jsonObject.getString("sexoDescricao"));
                            desaparecidoVO.setDataCadastro(jsonObject.getString("dataCadastro"));
                            desaparecidoVO.setParentesco(jsonObject.getString("parentesco"));
                            desaparecidoVO.setTelefoneContato(jsonObject.getString("telefoneContato"));
                            desaparecidoVO.setDescricao(jsonObject.getString("descricao"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        desaparecidoVOList.add(desaparecidoVO);

                    }
                }
                mAdapter = new CustomRecyclerAdapter(getActivity(), desaparecidoVOList, onClickDesaparecido()); /////////////Esses 2 itens seguintes não entendi

                recyclerView.setAdapter(mAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();

            }
        });
        rq.add(jsonArrayFaceRequest);

    }


    private CustomRecyclerAdapter.DesaparecidoOnClickListener onClickDesaparecido() {
        return new CustomRecyclerAdapter.DesaparecidoOnClickListener() {
            @Override
            public void onClickDesaparecido(View view, int idx) {
//                DesaparecidoVO d = desaparecidoVOList.get(idx);
//                Intent intent = new Intent (getContext(),MeusCadastrosActivity.class);
//                intent.putExtra("desaparecido",  d);
//                startActivity(intent);

            }
        };

    }

    @Override
    public void setPageController(PageController controller) {
        navigationController = controller;

    }

    @Override
    public void setParameter(Object parameter) {

    }

    @Override
    public void desaparecidoGravado(int id) {

    }

    @Override
    public void desaparecidoEditado(int id) {

    }

    @Override
    public void fotosGravadas(int id) {

    }

    @Override
    public void buscarDesaparecido(int id) {

    }
}