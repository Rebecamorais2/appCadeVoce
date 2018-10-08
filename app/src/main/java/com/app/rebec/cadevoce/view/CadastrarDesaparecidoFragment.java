package com.app.rebec.cadevoce.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.app.rebec.cadevoce.R;

import com.app.rebec.cadevoce.model.DAO.CorCabeloDAO;
import com.app.rebec.cadevoce.model.DAO.CorOlhosDAO;
import com.app.rebec.cadevoce.model.DAO.DesaparecidoDAO;
import com.app.rebec.cadevoce.model.DAO.ParentescoDAO;
import com.app.rebec.cadevoce.model.DAO.RacaDesaparecidoDAO;
import com.app.rebec.cadevoce.model.DAO.SexoDAO;
import com.app.rebec.cadevoce.service.CityListResultHandler;
import com.app.rebec.cadevoce.service.DesaparecidoService;
import com.app.rebec.cadevoce.service.StateListResultHandler;
import com.app.rebec.cadevoce.presenter.PageController;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class CadastrarDesaparecidoFragment extends Fragment implements TargetPage {

    //Variável estática que recebe o valor do estado dentro do método handleEstadoAtual
    //O valor está sendo solicitado por outra variável na classe ManterDesaparecidoService
    //Como está fora do onCreate, necessita ser atualizado pelo recreate()
    //Recebe o valor de 30 por causa do [if] no ManterDesaparecidoService
    public static int atual=30;

    private PageController navigationController;


    private EditText inputNome;
    private EditText inputIdade;
    private EditText inputAltura;
    private EditText inputSexo;
    private DatePicker inputDataDesaparecimento;
    private  EditText inputTelefoneContato;
    private  EditText inputDescricao;
    private Spinner inputCidade;
    private Spinner inputEstado;
    private Spinner inputRaca;
    private Spinner inputCorOlhos;
    private Spinner inputCorCabelo;
    private Button btnCriar;


    private DesaparecidoDAO db = new DesaparecidoDAO();



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cadastrar_desaparecido, container, false);
        //Spinner Raça
        RacaDesaparecidoDAO racaDesp = new RacaDesaparecidoDAO();
        String[] racaSpinner = racaDesp.descricaoRaca;

        ArrayAdapter<String> adapterRaca = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, racaSpinner);
        MaterialBetterSpinner materialDesignSpinnerRaca = view.findViewById(R.id.raca_spinner);
        materialDesignSpinnerRaca.setAdapter(adapterRaca);


        //Spinner Cor do Cabelo
        CorCabeloDAO corCabelo = new CorCabeloDAO();
        String[] corCabeloSpinner = corCabelo.descricaoCorCabelo;

        ArrayAdapter<String> adapterCorCabelo = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, corCabeloSpinner);
        MaterialBetterSpinner materialDesignSpinnerCorCabelo = view.findViewById(R.id.cor_cabelo_spinner);
        materialDesignSpinnerCorCabelo.setAdapter(adapterCorCabelo);


        //Spinner Cor dos Olhos
        CorOlhosDAO corDosOlhos = new CorOlhosDAO();
        String[] corDosOlhosSpinner = corDosOlhos.descricaoCorOlhos;

        ArrayAdapter<String> adapterCorDosOlhos = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, corDosOlhosSpinner);
        MaterialBetterSpinner materialDesignSpinnerCorDosOlhos = view.findViewById(R.id.cor_dos_olhos_spinner);
        materialDesignSpinnerCorDosOlhos.setAdapter(adapterCorDosOlhos);


        //Spinner Sexo
        SexoDAO sexo = new SexoDAO();
        String[] sexoSpinner = sexo.descricaoSexo;

        ArrayAdapter<String> adapterSexo = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, sexoSpinner);
        MaterialBetterSpinner materialDesignSpinnerSexo = view.findViewById(R.id.sexo_spinner);
        materialDesignSpinnerSexo.setAdapter(adapterSexo);


        //Spinner Parentesco
        ParentescoDAO parentesco = new ParentescoDAO();
        String[] parentescoSpinner = parentesco.descricaoParentesco;

        ArrayAdapter<String> adapterParentesco = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, parentescoSpinner);
        MaterialBetterSpinner materialDesignSpinnerParentesco = view.findViewById(R.id.parentesco_spinner);
        materialDesignSpinnerParentesco.setAdapter(adapterParentesco);

//        //Spinner Estados
//        DesaparecidoService.loadStateList(getApplicationContext(), this);
//        List<String> estados = new ArrayList<>();
//        populateStateSpinner(estados);
//
//
//        //AutoComplete Cidades
//        DesaparecidoService.loadCityList(getApplicationContext(), this);
//        List<String> cidades = new ArrayList<>();
//        populaAutoCompleteCidades(cidades);
//
//        return view;
//    }
//
//
//    //trata a resposta de estados
//    public  void handleStateList(List<String> estados) {
//        populateStateSpinner(estados);
//    }
//
//
//    public void populateStateSpinner(final List<String> estadosLista) {
//        ArrayAdapter<String> adapterEstado = new ArrayAdapter<String>(this.getActivity().getApplicationContext(), android.R.layout.simple_dropdown_item_1line, estadosLista);
//        final MaterialBetterSpinner materialDesignSpinnerEstado = getView().findViewById(R.id.estado_spinner);
//        materialDesignSpinnerEstado.setAdapter(adapterEstado);
//
//        //encontrar o valor selecionado no spinner
//        materialDesignSpinnerEstado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                //Instância da interface CityResultHandler
//                CityListResultHandler handler = new CityListResultHandler() {
//                    //Implantação do método handleCityList (não utilizado aqui)
//                    @Override
//                    public void handleCityList(List<String> cityList) { }
//
//                    //Método que fará a váriavel estática [atual] receber o valor da posição do estado
//                    @Override
//                    public void handleEstadoAtual(int pegaPosicao) {
//                        atual= pegaPosicao;
//                    }
//
//                };
//
//                //Para verificação de qual estado está sendo pego (no console)
//                String estadoAtual = (String) adapterView.getItemAtPosition(i);
//                //Imprimindo o estado e a posição pelo Logcat
//                Log.d("ESTADO", "" + i + " " + l + " " + estadoAtual);
//                //Utilizando o método handleEstadoAtual da interface para enviar a posição do estado para ManterDesaparecidoService
//                handler.handleEstadoAtual(i);
//                //Recriando a activity (para atualizar o valor da variável estática que está fora do onCreate)
//                //Este método faz com que os dados inseridos nos campos com caixas de texto (nome, idade, altura, etc.)
//                //Sejam reiniciados em branco, portanto É PRECISO REESTRUTURAR A UTILIZAÇÃO
//                //  recreate();
//            }
//
//        });
//
//    }
//
//    //trata a resposta de cidades
//    public  void handleCityList(List<String> cidades) {
//        populaAutoCompleteCidades(cidades);
//    }
//
//    //Assinatura do método handleEstadoAtual, não utilizado aqui
//    public void handleEstadoAtual(int estadoAtual) { }
//
//
//    //Auto complete cidades
//    private void populaAutoCompleteCidades(List<String> cidadesLista) {
//        AutoCompleteTextView auto = (AutoCompleteTextView)getView().findViewById(R.id.edtAutoCompleteCidade);
//        ArrayAdapter<String> adapterCidade = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, cidadesLista);
//        auto.setAdapter(adapterCidade);
//    }



    //  View view = inflater.inflate(R.layout.fragment_cadastrar_desaparecido, container, false);

//        final EditText inputNome = (EditText) view.findViewById(R.id.inputNome);
//        final EditText inputIdade = (EditText) view.findViewById(R.id.inputIdade);
//        final EditText inputAltura = (EditText) view.findViewById(R.id.inputAltura);
//        final EditText inputTelefoneContato = (EditText) view.findViewById(R.id.inputTelefoneContato);
//        final EditText inputDescricao = (EditText) view.findViewById(R.id.inputDescricao);
//        Button btn = (Button) view.findViewById(R.id.btnCriar);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                try {
//                    cadastrar2();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//
//
//            public void cadastrar2() throws Exception {
//                String nome = inputNome.getText().toString();
//                String idade = inputIdade.getText().toString();
//                String altura = inputAltura.getText().toString();
//                String telefoneContato = inputTelefoneContato.getText().toString();
//                String descricao = inputDescricao.getText().toString();
//
//                DesaparecidoVO desap = new DesaparecidoVO();
//                if (nome.isEmpty() || idade.isEmpty() || altura.isEmpty() || telefoneContato.isEmpty() || descricao.isEmpty()) {
//                    Toast.makeText(getActivity(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
//                }
////                else {
////
////                    if (!senha.equals(confirmaSenha)) {
////                        Toast.makeText(getActivity(), "As senhas não são iguais!", Toast.LENGTH_SHORT).show();
////                    }
//                else {
//                    desap.setNome(nome);
//                    desap.setIdade(Integer.parseInt(idade));
//                    desap.setAltura(Double.parseDouble(altura));
//                    desap.setTelefoneContato(Integer.parseInt(telefoneContato));
//                    desap.setDescricao(descricao);
//
//                    db.inserirDesaparecido(desap, getActivity());
//                    Toast.makeText(getActivity(), "Desaparecido cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
//
//                    inputNome.setText("");
//                    inputIdade.setText("");
//                    inputAltura.setText("");
//                    inputTelefoneContato.setText("");
//                    inputDescricao.setText("");
//                }
        return view;
    }
//        });



    public void setPageController(PageController controller) {
        navigationController = controller;
    }

    public void setParameter(Object parameter) {

    }
}
