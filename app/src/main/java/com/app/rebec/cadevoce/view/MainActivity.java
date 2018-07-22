package com.app.rebec.cadevoce.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.app.rebec.cadevoce.presenter.PageController;
import com.app.rebec.cadevoce.R;

import java.util.EmptyStackException;
import java.util.Stack;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener,
        PageController {
    Stack <TargetPage> navegationStack = new Stack<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);




        navigationView.setNavigationItemSelectedListener(this);

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_home) {
            changePage(Page.Home,null);
        } else if (id == R.id.nav_procurar) {
          //  changePage(Page.OndeProcurar,null);
        } else if (id == R.id.nav_perdidos) {
          //  changePage(Page.Perdidos,null);
        } else if (id == R.id.nav_delegacias) {
            changePage(Page.GoogleMaps, null);
        } else if (id == R.id.nav_sobre) {
           // changePage(Page.Sobre,null);

        } else if (id == R.id.nav_sair) {

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
             //   fragmentClass = BuscarFragment.class;
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

   public boolean backToPreviousPage(){
        try{
            //joga fora a pagina atual
            navegationStack.pop();
            TargetPage previousPage = navegationStack.peek();

            if (previousPage instanceof  Fragment) {
                Fragment previousFragment = (Fragment)previousPage;
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                fragmentManager.beginTransaction().replace(R.id.fragment_base, previousFragment).commit();
            }
            return true;

        }catch(EmptyStackException esEx){
            return false;

        }

   }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch ( keyCode){
            case KeyEvent.KEYCODE_BACK:

                return backToPreviousPage();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
