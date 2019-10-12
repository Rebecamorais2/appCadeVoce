package com.app.rebec.cadevoce.view.activity;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.rebec.cadevoce.presenter.PageController;
import com.app.rebec.cadevoce.R;
import com.app.rebec.cadevoce.view.fragments.BuscarNomeFragment;

import com.app.rebec.cadevoce.view.fragments.CadastrarDesaparecidoFragment;
import com.app.rebec.cadevoce.view.fragments.CadastrosRecentesDetails;
import com.app.rebec.cadevoce.view.fragments.CadastrosRecentesFragment;
import com.app.rebec.cadevoce.view.fragments.EditarCadastroFragment;
import com.app.rebec.cadevoce.view.fragments.EncontreiAlguemFragment;
import com.app.rebec.cadevoce.view.fragments.GoogleMapsFragment;
import com.app.rebec.cadevoce.view.fragments.HomeFragment;
import com.app.rebec.cadevoce.view.fragments.MeusCadastrosFragment;
import com.app.rebec.cadevoce.view.fragments.OndeProcurarFragment;
import com.app.rebec.cadevoce.view.util.Page;
import com.app.rebec.cadevoce.view.fragments.SobreAplicativo;
import com.app.rebec.cadevoce.view.util.TargetPage;
import com.app.rebec.cadevoce.view.fragments.TermosUsoFragment;
import com.app.rebec.cadevoce.view.fragments.BuscarFotoFragment;
import com.app.rebec.cadevoce.view.fragments.BuscarFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener, PageController {

    LoginActivity log = new LoginActivity();
    int codUser = log.codUser;
    String userName = log.userName;
    String userEmail = log.userEmail;

    private static final String deleteRequest = "https://cadevoce.azurewebsites.net/cadevoceWS/service/usuarios/delete/";

    private ShareActionProvider mActionProvider;
    Stack<TargetPage> navegationStack = new Stack<>();

    public void excluirUser() throws Exception {

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        StringRequest deletarRequest = new StringRequest(Request.Method.DELETE, deleteRequest + codUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //                Toast.makeText(getActivity() , (CharSequence) response, Toast.LENGTH_LONG).show();


                //                if (response.equals("true")) {
                //                    Toast.makeText((getActivity()), "Usuário excluído com sucesso!", Toast.LENGTH_LONG).show();
                //
                //
                //                } else {
                //
                //                    Toast.makeText((getActivity()), "Falha ao excluir desaparecido, tente novamente!", Toast.LENGTH_LONG).show();
//
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.e("Deletar Desaparecido", "Error " + error.toString());


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

                return headers;
            }
        };
        queue.add(deletarRequest);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        TextView txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nomeMenu);

        txtProfileName.setText(userName);
        TextView txtProfileEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.emailMenu);
        txtProfileEmail.setText(userEmail);


        changePage(Page.Home, null);// seta a pagina principal do app

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.remover_conta) {

            try {
                excluirUser();
                Toast.makeText(getApplicationContext(), "Conta removida com sucesso!", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(it);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_home) {
            changePage(Page.Home, null);
        } else if (id == R.id.nav_procurar) {
            changePage(Page.OndeProcurar, null);

        } else if (id == R.id.nav_perdidos) {
            changePage(Page.EncontreiAlguem, null);
        } else if (id == R.id.nav_delegacias) {
            changePage(Page.GoogleMaps, null);

        } else if (id == R.id.termoResp) {
            changePage(Page.TermosUso, null);


        } else if (id == R.id.nav_sobre) {
            changePage(Page.Sobre, null);


        } else if (id == R.id.nav_sair) {
            changePage(Page.Sair, null);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changePage(Page destination, Object parameter) {

        Fragment newFragment = null;
        Class fragmentClass = null;

        switch (destination) {
            case Home:
                fragmentClass = HomeFragment.class;
                break;
            case Buscar:
                fragmentClass = BuscarFragment.class;
                break;
            case CadastrarDesaparecido:

                fragmentClass = CadastrarDesaparecidoFragment.class;

                break;
            case CadastrosRecentes:
                fragmentClass = CadastrosRecentesFragment.class;
                break;
            case ManterDesaparecido:
                //   fragmentClass = BuscarFragment.class;
                break;
            case GoogleMaps:
                fragmentClass = GoogleMapsFragment.class;
                break;
            case BuscarFoto:
                fragmentClass = BuscarFotoFragment.class;
                break;
            case BuscarNome:
                fragmentClass = BuscarNomeFragment.class;
                break;
            case OndeProcurar:
                fragmentClass = OndeProcurarFragment.class;
                break;
            case EncontreiAlguem:
                fragmentClass = EncontreiAlguemFragment.class;
                break;
            case Sobre:
                fragmentClass = SobreAplicativo.class;
                break;
            case Sair:
                SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor e = sp.edit();
                e.clear();
                e.commit();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

            case CadastrosRecentesDetails:
                fragmentClass = CadastrosRecentesDetails.class;
                break;

            case MeusCadastros:
                fragmentClass = MeusCadastrosFragment.class;
                break;

            case TermosUso:
                fragmentClass = TermosUsoFragment.class;
                break;

            case EditarCadastro:
                fragmentClass = EditarCadastroFragment.class;
                break;


        }

        try {
            newFragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();

            if (newFragment instanceof TargetPage) {
                TargetPage target = (TargetPage) newFragment;
                target.setPageController(this);
                target.setParameter(parameter);

                navegationStack.push(target);

            }

            FragmentTransaction transaction = fragmentManager.beginTransaction();


            fragmentManager.beginTransaction().replace(R.id.fragment_base, newFragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean backToPreviousPage() {
        try {
            //joga fora a pagina atual
            navegationStack.pop();
            TargetPage previousPage = navegationStack.peek();

            if (previousPage instanceof Fragment) {
                Fragment previousFragment = (Fragment) previousPage;
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                fragmentManager.beginTransaction().replace(R.id.fragment_base, previousFragment).commit();
            }
            return true;

        } catch (EmptyStackException esEx) {
            return false;

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:

                return backToPreviousPage();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
