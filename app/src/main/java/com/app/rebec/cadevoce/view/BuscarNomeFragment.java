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

import com.app.rebec.cadevoce.R;
import com.app.rebec.cadevoce.service.CityListResultHandler;
import com.app.rebec.cadevoce.service.DesaparecidoService;
import com.app.rebec.cadevoce.service.StateListResultHandler;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BuscarNomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BuscarNomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class BuscarNomeFragment extends Fragment implements StateListResultHandler, CityListResultHandler {

    //Variável estática que recebe o valor do estado dentro do método handleEstadoAtual
    //O valor está sendo solicitado por outra variável na classe ManterDesaparecidoService
    //Como está fora do onCreate, necessita ser atualizado pelo recreate()
    //Recebe o valor de 30 por causa do [if] no ManterDesaparecidoService
    public static int atual=30;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_buscar_nome, container, false);

        //Spinner Estados
        DesaparecidoService.loadStateList(getApplicationContext(), (StateListResultHandler) this);
        List<String> estados = new ArrayList<>();
        populateStateSpinner(estados);


        //AutoComplete Cidades
        DesaparecidoService.loadCityList(getApplicationContext(), (CityListResultHandler) this);
        List<String> cidades = new ArrayList<>();
        populaAutoCompleteCidades(cidades);

        return view;

    }

    //trata a resposta de estados
    public  void handleStateList(List<String> estados) {
        populateStateSpinner(estados);
    }


    public void populateStateSpinner(final List<String> estadosLista) {
        ArrayAdapter<String> adapterEstado = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, estadosLista);
        MaterialBetterSpinner materialDesignSpinnerEstado = null;
        materialDesignSpinnerEstado = materialDesignSpinnerEstado.findViewById(R.id.estado_spinner);
        materialDesignSpinnerEstado.setAdapter(adapterEstado);

        //encontrar o valor selecionado no spinner
        materialDesignSpinnerEstado.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Instância da interface CityResultHandler
                CityListResultHandler handler = new CityListResultHandler() {
                    //Implantação do método handleCityList (não utilizado aqui)
                    @Override
                    public void handleCityList(List<String> cityList) { }

                    //Método que fará a váriavel estática [atual] receber o valor da posição do estado
                    @Override
                    public void handleEstadoAtual(int pegaPosicao) {
                        atual= pegaPosicao;
                    }

                };

                //Para verificação de qual estado está sendo pego (no console)
                String estadoAtual = (String) adapterView.getItemAtPosition(i);
                //Imprimindo o estado e a posição pelo Logcat
                Log.d("ESTADO", "" + i + " " + l + " " + estadoAtual);
                //Utilizando o método handleEstadoAtual da interface para enviar a posição do estado para ManterDesaparecidoService
                handler.handleEstadoAtual(i);
                //Recriando a activity (para atualizar o valor da variável estática que está fora do onCreate)
                //Este método faz com que os dados inseridos nos campos com caixas de texto (nome, idade, altura, etc.)
                //Sejam reiniciados em branco, portanto É PRECISO REESTRUTURAR A UTILIZAÇÃO
                //  recreate();
            }

        });

    }

    //trata a resposta de cidades
    public  void handleCityList(List<String> cidades) {
        populaAutoCompleteCidades(cidades);
    }

    //Assinatura do método handleEstadoAtual, não utilizado aqui
    public void handleEstadoAtual(int estadoAtual) { }


    //Auto complete cidades
    private void populaAutoCompleteCidades(List<String> cidadesLista) {
        AutoCompleteTextView auto = null;
        auto = (AutoCompleteTextView)auto.findViewById(R.id.edtAutoCompleteCidade);
        ArrayAdapter<String> adapterCidade = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, cidadesLista);
        auto.setAdapter(adapterCidade);
    }


}
