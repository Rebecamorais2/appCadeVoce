package com.app.rebec.cadevoce.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.rebec.cadevoce.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapsFragment extends Fragment implements OnMapReadyCallback {

    //localizacao das delegacias
    double lat = -22.9093198;
    double longi = -47.0801018;

    private LatLng distrito1 = new LatLng(-22.9069324, -47.07753);
    private LatLng batalhao47 = new LatLng(-22.9069878, -47.0775829);
    private LatLng seccional = new LatLng(22.9063926, -47.0748759);
    private LatLng deinter2 = new LatLng(-22.9064549, -47.075081);
    private LatLng sspSP = new LatLng(-22.9048659, -47.0707917);
    LatLng ll = new LatLng(lat, longi);


    private GoogleMap mMap;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_google_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setUpMap() {

        mMap.addMarker(new MarkerOptions().position(distrito1).title("Delegacia do 1° Distrito Policial de Campinas"));
        mMap.addMarker(new MarkerOptions().position(batalhao47).title("47º Batalhão de Polícia Militar"));
        mMap.addMarker(new MarkerOptions().position(seccional).title("1ª Delegacia Seccional de Polícia de Campinas"));
        mMap.addMarker(new MarkerOptions().position(deinter2).title("Deinter 2 Campinas-Sede"));
        mMap.addMarker(new MarkerOptions().position(sspSP).title("São Paulo Secretaria da Segurança Pública"));

        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(distrito1, 20);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(20);

        mMap.moveCamera(location);
        mMap.animateCamera(zoom, 3000, null);

        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

    }

    public void streetView(View v) {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(ll, 15);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(20);

        mMap.moveCamera(location);
        mMap.animateCamera(zoom, 3000, null);
    }

    public void normalView(View v) {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(ll, 15);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(20);

        mMap.moveCamera(location);
        mMap.animateCamera(zoom, 3000, null);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setUpMap();
    }
}