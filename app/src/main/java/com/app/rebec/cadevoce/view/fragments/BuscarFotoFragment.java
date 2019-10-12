package com.app.rebec.cadevoce.view.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.graphics.*;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.rebec.cadevoce.R;
import com.app.rebec.cadevoce.model.VO.DesaparecidoVO;
import com.app.rebec.cadevoce.presenter.PageController;
import com.app.rebec.cadevoce.service.FaceId;

import com.app.rebec.cadevoce.view.activity.CadastrosRecentesActivity;
import com.app.rebec.cadevoce.view.activity.MeusCadastrosActivity;
import com.app.rebec.cadevoce.view.adapters.CustomRecyclerAdapter;
import com.app.rebec.cadevoce.view.util.Page;
import com.app.rebec.cadevoce.view.util.TargetPage;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.FaceRectangle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.support.v4.content.ContextCompat.startActivity;


public class BuscarFotoFragment extends Fragment implements FaceId, TargetPage {


    List<DesaparecidoVO> desaparecidoVOList;
    RecyclerView.Adapter mAdapter;
    RecyclerView recyclerView;
    RequestQueue rq;
    RecyclerView.LayoutManager layoutManager;

    private final int PICK_IMAGE = 1;
    private ProgressDialog detectionProgressDialog;
    private final String apiEndpoint = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0";
    private final String subscriptionKey = "13e05e9989d1420db8281cfb13980b2f";
    private final String URL_FACEID = "https://cadevoce.azurewebsites.net/cadevoceWS/service/desaparecidos/identificar?faceId=";
    static String faceId;
    Button cadastrarDesap;
    private PageController navigationController;
    ImageView imageView;
    private boolean existeFoto;


    ProgressDialog progressDialog;

    private FaceServiceClient faceServiceClient = new FaceServiceRestClient(apiEndpoint, subscriptionKey);


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        existeFoto = false;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buscar_foto, container, false);

        rq = Volley.newRequestQueue(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewContainer4);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        desaparecidoVOList = new ArrayList<>();


        //botão para carregar imagens e detectar rosto
        Button upImagem = (Button) view.findViewById(R.id.btn_escolher_foto);
        upImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallIntent = new Intent(Intent.ACTION_GET_CONTENT);
                gallIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(gallIntent, "Select Picture"), PICK_IMAGE);
            }
        });

        detectionProgressDialog = new ProgressDialog(getActivity());

        //botão para identificar
        Button buscaFoto = (Button) view.findViewById(R.id.btn_envia_foto);


        buscaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desaparecidoVOList.clear();
                if (!existeFoto) {
                    Toast.makeText(getActivity(), "Selecione uma foto!", Toast.LENGTH_SHORT).show();

                } else {

                    sendRequest();
                    faceId = "";
                }
//                    sendRequest();
//                    imageView.setImageDrawable(null);
//                    faceId = "";


//                progressDialog = new ProgressDialog(getActivity());
//                progressDialog.setMessage("Buscando...");
//                progressDialog.show();

            }
        });

        cadastrarDesap = view.findViewById(R.id.btncadastrarDesap3);
        cadastrarDesap.setVisibility(View.INVISIBLE);

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

    public void sendRequest() {

        final JsonArrayRequest jsonArrayFaceRequest = new JsonArrayRequest(Request.Method.GET, URL_FACEID + faceId, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("Resposta da Requisição: " + response);

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
                mAdapter = new CustomRecyclerAdapter(getActivity(), desaparecidoVOList, onClickDesaparecidoFoto());

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

    private CustomRecyclerAdapter.DesaparecidoOnClickListener onClickDesaparecidoFoto() {
        return new CustomRecyclerAdapter.DesaparecidoOnClickListener() {
            @Override
            public void onClickDesaparecido(View view, int idx) {
                DesaparecidoVO d = desaparecidoVOList.get(idx);
                Intent intent = new Intent(getContext(), MeusCadastrosActivity.class);
                intent.putExtra("desaparecido", d);
                startActivity(intent);

            }
        };

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                imageView = (ImageView) getView().findViewById(R.id.buscar_imagem);

                imageView.setImageBitmap(bitmap);
                // This is the new addition.
                detectAndFrame(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void detectAndFrame(final Bitmap imageBitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        AsyncTask<InputStream, String, Face[]> detectTask = new AsyncTask<InputStream, String, Face[]>() {
            @Override
            protected Face[] doInBackground(InputStream... params) {
                try {
                    publishProgress("Detecting...");
                    Face[] result = faceServiceClient.detect(params[0], true,         // returnFaceId
                            false,        // returnFaceLandmarks
                            null           // returnFaceAttributes: a string like "age, gender"
                /* If you want value of FaceAttributes, try adding 4th argument like below.
                            new FaceServiceClient.FaceAttributeType[] {
                    FaceServiceClient.FaceAttributeType.Age,
                    FaceServiceClient.FaceAttributeType.Gender }
                */

                    );
                    if (result == null) {
                        publishProgress("Detecção finalizada. Não foi possível a detecção facial");
                        return null;
                    } else {
                        publishProgress(String.format("Detecção finalizada. %d rosto(s) detectados", result.length));
                        //pega o faceId
                        final String faceId = String.valueOf(result[0].faceId);
                        System.out.println("FaceId " + faceId);
                        pegaFaceid(faceId);

                        return result;

                    }
                } catch (Exception e) {
                    publishProgress("Detecção Falhou");
                    return null;
                }

            }

            @Override
            protected void onPreExecute() {
                //TODO: show progress dialog
                detectionProgressDialog.show();
            }

            @Override
            protected void onProgressUpdate(String... progress) {
                //TODO: update progress
                detectionProgressDialog.setMessage(progress[0]);
            }

            @Override
            protected void onPostExecute(Face[] result) {
                //TODO: update face frames
                detectionProgressDialog.dismiss();
                if (result == null) return;
                ImageView imageView = (ImageView) getView().findViewById(R.id.buscar_imagem);
                imageView.setImageBitmap(drawFaceRectanglesOnBitmap(imageBitmap, result));
                existeFoto = true;
                imageBitmap.recycle();
            }
        };
        detectTask.execute(inputStream);
    }

    private static Bitmap drawFaceRectanglesOnBitmap(Bitmap originalBitmap, Face[] faces) {
        Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        int stokeWidth = 2;
        paint.setStrokeWidth(stokeWidth);
        if (faces != null) {
            for (Face face : faces) {
                FaceRectangle faceRectangle = face.faceRectangle;
                canvas.drawRect(faceRectangle.left, faceRectangle.top, faceRectangle.left + faceRectangle.width, faceRectangle.top + faceRectangle.height, paint);
                System.out.println("FaceId " + faceId);
            }
        }
        return bitmap;
    }


    @Override
    public void pegaFaceid(String id) {
        faceId = id;

    }

    @Override
    public void setPageController(PageController controller) {
        navigationController = controller;
    }

    @Override
    public void setParameter(Object parameter) {

    }
}
