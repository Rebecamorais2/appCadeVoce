package com.app.rebec.cadevoce.view.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.ProgressDialog;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.rebec.cadevoce.R;
import com.app.rebec.cadevoce.model.DAO.DesaparecidoDAO;
import com.app.rebec.cadevoce.model.VO.CorCabeloVO;
import com.app.rebec.cadevoce.model.VO.CorOlhosVO;
import com.app.rebec.cadevoce.model.VO.DesaparecidoVO;
import com.app.rebec.cadevoce.model.VO.RacaDesapVO;
import com.app.rebec.cadevoce.model.VO.SexoVO;
import com.app.rebec.cadevoce.presenter.PageController;
import com.app.rebec.cadevoce.view.activity.LoginActivity;
import com.app.rebec.cadevoce.view.activity.MainActivity;
import com.app.rebec.cadevoce.view.adapters.CorCabeloAdapter;
import com.app.rebec.cadevoce.view.adapters.CorOlhosAdapter;
import com.app.rebec.cadevoce.view.adapters.RacaAdapter;
import com.app.rebec.cadevoce.view.adapters.MySingleton;
import com.app.rebec.cadevoce.view.adapters.SexoAdapter;
import com.app.rebec.cadevoce.model.VO.State;
import com.app.rebec.cadevoce.view.adapters.StateAdapter;
import com.app.rebec.cadevoce.view.util.MaskEditUtil;
import com.app.rebec.cadevoce.view.util.Page;
import com.app.rebec.cadevoce.view.util.TargetPage;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.app.rebec.cadevoce.view.util.MaskEditUtil.FORMAT_ALTURA;
import static com.app.rebec.cadevoce.view.util.MaskEditUtil.FORMAT_DATE;
import static com.app.rebec.cadevoce.view.util.MaskEditUtil.FORMAT_FONE;
import static com.facebook.FacebookSdk.getApplicationContext;
import static android.app.Activity.RESULT_OK;


public class CadastrarDesaparecidoFragment extends Fragment implements TargetPage, DesaparecidoDAO.DesaparecidoDAODelegate {

    public static int idDesp; //Pega o id do desaparecido

    private PageController navigationController;

    protected EditText inputNomeDesap;
    protected EditText inputIdade;
    protected EditText inputAltura;
    protected EditText inputTeloneContato;
    protected EditText inputDataDesaparecimento;
    protected EditText inputDescricao;
    protected EditText inputParentesco;
    protected Button btncadastrarDesap;
    String base64;

    //Bloco referente ao upload da imagem
    ImageView image;
    Button choose, upload;
    int PICK_IMAGE_REQUEST = 111;
    String URL_FOTO = "https://cadevoce.azurewebsites.net/cadevoceWS/service/faces/upload_fotos";
    Bitmap bitmap;
    ProgressDialog progressDialog;


    private static final String KEY_STATE = "estado";
    private static final String KEY_CITIES = "cidades";
    Spinner stateSpinner;
    Spinner citiesSpinner;
    private ProgressDialog pDialog;
    private String cities_url = "https://cadevoce.azurewebsites.net/cadevoceWS/service/estado-cidades/list/";

    //Spinners
    //Raça
    private static final String KEY_RACA = "descricao";
    private static final String KEY_RACA_CODIGO = "codigo";
    MaterialBetterSpinner racaSpinner;
    public String raca_URL = "https://cadevoce.azurewebsites.net/cadevoceWS/service/racaDesap/list/";


    //Cor olhos
    private static final String KEY_CorOlhos = "descricao";
    private static final String KEY_CorOlhos_CODIGO = "codigo";
    MaterialBetterSpinner corOlhosSpinner;
    public String corOlhos_URL = "https://cadevoce.azurewebsites.net/cadevoceWS/service/corOlhos/list/";


    //Cor Cabelo
    private static final String KEY_CorCabelo = "descricao";
    private static final String KEY_CorCabelo_CODIGO = "codigo";
    MaterialBetterSpinner corCabeloSpinner;
    public String corCabelo_URL = "https://cadevoce.azurewebsites.net/cadevoceWS/service/corCabelo/list/";


    //Sexo
    private static final String KEY_sexo = "descricao";
    private static final String KEY_sexo_CODIGO = "codigo";
    //    Spinner sexoSpinner;
    MaterialBetterSpinner sexoSpinner;
    public String sexo_URL = "https://cadevoce.azurewebsites.net/cadevoceWS/service/sexo/list";


    private void displayLoader() {
        pDialog = new ProgressDialog(this.getActivity());
        pDialog.setMessage("Carregando.. Aguarde...");
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
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsArrayRequest);
    }

    /**
     * Helps in downloading the racas details
     * and populating the spinner
     */
    private void loadRacaDetails() {
        final List<RacaDesapVO> racasList = new ArrayList<>();

        JsonArrayRequest jsArrayRequest = new JsonArrayRequest(Request.Method.GET, raca_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
                pDialog.dismiss();
                try {
                    //Parse the JSON response array by iterating over it
                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject response = responseArray.getJSONObject(i);
                        String descricao = response.getString(KEY_RACA);
                        int codigo = response.getInt(KEY_RACA_CODIGO);
                        RacaDesapVO racaVO = new RacaDesapVO(codigo, descricao);

                        racasList.add(racaVO);
                    }
                    final RacaAdapter racaAdapter;
                    racaAdapter = new RacaAdapter(getContext(), R.layout.raca, R.id.spinnerText1, racasList);
                    racaSpinner.setAdapter(racaAdapter);

                    racaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsArrayRequest);
    }

    /**
     * Helps in downloading the cor olhos details
     * and populating the spinner
     */
    private void loadCorOlhosDetails() {
        final List<CorOlhosVO> corOlhosList = new ArrayList<>();

        JsonArrayRequest jsArrayRequest = new JsonArrayRequest(Request.Method.GET, corOlhos_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
                pDialog.dismiss();
                try {
                    //Parse the JSON response array by iterating over it
                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject response = responseArray.getJSONObject(i);
                        String descricao = response.getString(KEY_CorOlhos);
                        int codigo = response.getInt(KEY_CorOlhos_CODIGO);
                        CorOlhosVO corOlhosVO = new CorOlhosVO(codigo, descricao);

                        corOlhosList.add(corOlhosVO);
                    }
                    final CorOlhosAdapter corOlhosAdapter;
                    corOlhosAdapter = new CorOlhosAdapter(getContext(), R.layout.corolhos_list, R.id.spinnerText_corOlhos, corOlhosList);
                    corOlhosSpinner.setAdapter(corOlhosAdapter);

                    corOlhosSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsArrayRequest);
    }

    /**
     * Helps in downloading the cor cabelo details
     * and populating the spinner
     */
    private void loadCorCabeloDetails() {
        final List<CorCabeloVO> corCabeloList = new ArrayList<>();

        JsonArrayRequest jsArrayRequest = new JsonArrayRequest(Request.Method.GET, corCabelo_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
                pDialog.dismiss();
                try {
                    //Parse the JSON response array by iterating over it
                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject response = responseArray.getJSONObject(i);
                        String descricao = response.getString(KEY_CorCabelo);
                        int codigo = response.getInt(KEY_CorCabelo_CODIGO);
                        CorCabeloVO corCabeloVO = new CorCabeloVO(codigo, descricao);

                        corCabeloList.add(corCabeloVO);
                    }
                    final CorCabeloAdapter corCabeloAdapter;
                    corCabeloAdapter = new CorCabeloAdapter(getContext(), R.layout.corcabelo_list, R.id.spinnerText_corCabelo, corCabeloList);
                    corCabeloSpinner.setAdapter(corCabeloAdapter);

                    corCabeloSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsArrayRequest);
    }

    /**
     * Helps in downloading the sexo details
     * and populating the spinner
     */
    private void loadSexoDetails() {
        final List<SexoVO> sexoList = new ArrayList<>();

        JsonArrayRequest jsArrayRequest = new JsonArrayRequest(Request.Method.GET, sexo_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responseArray) {
                pDialog.dismiss();
                try {
                    //Parse the JSON response array by iterating over it
                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject response = responseArray.getJSONObject(i);
                        String descricao = response.getString(KEY_sexo);
                        int codigo = response.getInt(KEY_sexo_CODIGO);
                        SexoVO sexoVO = new SexoVO(codigo, descricao);

                        sexoList.add(sexoVO);
                    }
                    final SexoAdapter sexoAdapter;
                    sexoAdapter = new SexoAdapter(getContext(), R.layout.sexo_list, R.id.spinnerText_sexo, sexoList);
                    sexoSpinner.setAdapter(sexoAdapter);


                    sexoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsArrayRequest);
    }


    private DesaparecidoDAO db = new DesaparecidoDAO();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cadastrar_desaparecido, container, false);

        db.setDelegate(this);

        final EditText inputNomeDesap = (EditText) view.findViewById(R.id.inputNomeDesap);
        final EditText inputIdade = (EditText) view.findViewById(R.id.inputIdade);

        final EditText inputAltura = (EditText) view.findViewById(R.id.inputAltura2);
        inputAltura.addTextChangedListener(MaskEditUtil.mask(inputAltura, FORMAT_ALTURA)); //Máscara da altura

        final EditText inputDataDesaparecimento = (EditText) view.findViewById(R.id.inputDataDesaparecimento2);
        inputDataDesaparecimento.addTextChangedListener(MaskEditUtil.mask(inputDataDesaparecimento, FORMAT_DATE)); //Máscara da data

        final EditText inputTelefoneContato = (EditText) view.findViewById(R.id.inputTelefoneContato2);
        inputTelefoneContato.addTextChangedListener(MaskEditUtil.mask(inputTelefoneContato, FORMAT_FONE)); //Máscara do telefone

        final EditText inputDescricao = (EditText) view.findViewById(R.id.inputDescricao2);
        final EditText inputParentesco = (EditText) view.findViewById(R.id.inputParentesco);
        racaSpinner = view.findViewById(R.id.raca_spinner);
        sexoSpinner = view.findViewById(R.id.sexo_spinner);
        corOlhosSpinner = view.findViewById(R.id.corOlhos_spinner);
        corCabeloSpinner = view.findViewById(R.id.corCabelo_spinner);
        stateSpinner = view.findViewById(R.id.stateSpinner);
        citiesSpinner = view.findViewById(R.id.citiesSpinner);
        final Button btnCadastrar = (Button) view.findViewById(R.id.btncadastrarDesap);
        btnCadastrar.setVisibility(View.INVISIBLE);

        final CheckBox termos = view.findViewById(R.id.termoResp); //para tratar os termos
        termos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (termos.isChecked()) {
                    btnCadastrar.setVisibility(View.VISIBLE);
                } else {
                    btnCadastrar.setVisibility(View.INVISIBLE);
                }
            }
        });
        termos.setChecked(false);

        image = (ImageView) view.findViewById(R.id.image);
        choose = (Button) view.findViewById(R.id.choose);


        //botão escolher foto -> traz a foto da galeria e grava o cadastro do desaparecido
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inputNomeDesap.getText().toString().isEmpty() || inputTelefoneContato.getText().toString().isEmpty() || inputDataDesaparecimento.getText().toString().isEmpty() || inputDescricao.getText().toString().isEmpty() || racaSpinner.getText().toString().isEmpty() || corOlhosSpinner.getText().toString().isEmpty() || corCabeloSpinner.getText().toString().isEmpty() || sexoSpinner.getText().toString().isEmpty() || stateSpinner.getSelectedItem().toString().isEmpty() || citiesSpinner.getSelectedItem().toString().isEmpty() || inputParentesco.getText().toString().isEmpty() || inputAltura.getText().toString().trim().equals("") || inputIdade.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();

                } else {

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_PICK);
                    startActivityForResult(Intent.createChooser(intent, "Selecione a foto"), PICK_IMAGE_REQUEST);

                    //Cadastrando os dados do desaparecido
                    try {
                        cadastrarDesaparecido();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            public void cadastrarDesaparecido() throws Exception {
                Integer idade = null;
                Double altura = null;

                String nome = inputNomeDesap.getText().toString();
                if (inputAltura.getText().toString().trim().equals("") || inputIdade.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();

                } else {
                    idade = Integer.parseInt(inputIdade.getText().toString());
                    altura = Double.parseDouble(inputAltura.getText().toString());
                }
                String telefoneContato = inputTelefoneContato.getText().toString();
                String dataDesaparecimentoForm = inputDataDesaparecimento.getText().toString();
                String dataDesaparecimento = dataDesaparecimentoForm.replace("/", "-");
                String descricao = inputDescricao.getText().toString();
                String raca = racaSpinner.getText().toString();
                String corOlhos = corOlhosSpinner.getText().toString();
                String corCabelo = corCabeloSpinner.getText().toString();
                String sexo = sexoSpinner.getText().toString();
                String estado = stateSpinner.getSelectedItem().toString();
                String cidade = citiesSpinner.getSelectedItem().toString();
                String parentesco = inputParentesco.getText().toString();


                DesaparecidoVO desaparecido = new DesaparecidoVO();

                if (nome.isEmpty() || raca.isEmpty() | corOlhos.isEmpty() || corCabelo.isEmpty() || sexo.isEmpty() || estado.isEmpty() || cidade.isEmpty() || dataDesaparecimento.isEmpty() || parentesco.isEmpty() || descricao.isEmpty()) { //precisa fazer os tratamentos de dados nulos

                    Toast.makeText(getActivity(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
//                    idade.equals("")||altura.equals("")||telefoneContato.equals("")|| idade.equals("")||altura.equals("")||telefoneContato.equals("")||

                } else {


                    desaparecido.setNome(nome);
                    desaparecido.setIdade(idade);
                    desaparecido.setAltura(altura);
                    desaparecido.setRacaDescricao(raca);
                    desaparecido.setOlhosDescricao(corOlhos);
                    desaparecido.setCabeloDescricao(String.valueOf(corCabelo));
                    desaparecido.setSexoDescricao(sexo);
                    desaparecido.setEstDescricao(estado);
                    desaparecido.setCidDescricao(Collections.singletonList(cidade));
                    desaparecido.setDataDesaparecimento(dataDesaparecimento);
                    desaparecido.setParentesco(parentesco);
                    desaparecido.setTelefoneContato(telefoneContato);
                    desaparecido.setDescricao(descricao);

                    db.inserirDesaparecido(desaparecido, getActivity());
                    // progressLogin.isShowing();
                }
            }
        });


        displayLoader();
        loadRacaDetails();
        loadCorOlhosDetails();
        loadCorCabeloDetails();
        loadSexoDetails();
        loadStateCityDetails();


        //Botão salvar cadastro sobe a foto para o Cloudinary e Cognitive Services
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (bitmap == null) {
                    Toast.makeText(getActivity(), "Insira uma foto!", Toast.LENGTH_SHORT).show();

                } else {
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Gravando as informações, aguarde por favor...");
                    progressDialog.show();

                    try {
                        //converting image to base64 string
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageBytes = baos.toByteArray();
                        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        base64 = "data:image/jpeg;base64," + imageString;
                        System.out.println("base64 " + base64);

                        StringRequest request = new StringRequest(Request.Method.POST, URL_FOTO, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                progressDialog.dismiss();

                                if (s.equals("true")) {
                                    Toast.makeText(getActivity(), "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();

                                    navigateToHome();
                                } else {
                                    Toast.makeText(getActivity(), "Ocorreu um erro!!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(getActivity(), "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();

                            }
                        }) {
                            //Adicionando parametros para o upload de fotos,
                            //foto em base 64 e o número do desaparecido para ser o nome identificador no azure cognitive services
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> parameters = new HashMap<String, String>();
                                String base64 = "data:image/jpeg;base64," + imageString;
                                parameters.put("base64", base64);
                                parameters.put("fileName", String.valueOf(idDesp)); //pegar o valor do codDesap
                                return parameters;
                            }
                        };
                        //enviando somente uma requisição e descartando duplicidade
                        request.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        RequestQueue rQueue = Volley.newRequestQueue(getActivity());
                        rQueue.add(request);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                //Setting image to ImageView
                image.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void setPageController(PageController controller) {
        navigationController = controller;
    }

    public void setParameter(Object parameter) {

    }


    @Override
    public void desaparecidoGravado(int id) {
        idDesp = id;

        if (id != 0) {
//            Toast.makeText(getApplicationContext(), "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            System.out.println("Cadastrado com sucesso!");


        } else {
            Toast.makeText(getApplicationContext(), "Falha! Tente novamente", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void desaparecidoEditado(int id) {
        idDesp = id;

        // fazer verificação e mostrar mensagem se o desaparecido for editado
        Toast.makeText(getApplicationContext(), "Cadastrado alterado com sucesso!", Toast.LENGTH_LONG).show();
    }

    public void fotosGravadas(int id) {
    }

    @Override
    public void buscarDesaparecido(int id) {

    }

    public void navigateToHome() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}

