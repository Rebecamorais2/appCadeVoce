package com.app.rebec.cadevoce.view.util;

import com.app.rebec.cadevoce.presenter.PageController;

public interface TargetPage {

    void setPageController(PageController controller);

    void setParameter(Object parameter);
}
