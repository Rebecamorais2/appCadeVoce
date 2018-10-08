package com.app.rebec.cadevoce.service;

import java.util.List;

public interface CityListResultHandler {
    void handleCityList(List<String> cityList);

    void handleEstadoAtual(int estadoAtual);
}

