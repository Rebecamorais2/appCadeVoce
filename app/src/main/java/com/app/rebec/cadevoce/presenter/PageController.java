package com.app.rebec.cadevoce.presenter;

import com.app.rebec.cadevoce.view.util.Page;

public interface PageController {
    void changePage(Page destination, Object parameter);

    boolean backToPreviousPage();

}
