package com.klzan.p2p.controller.admin;

import com.klzan.p2p.controller.BaseController;

/**
 * @description：后台管理基础 controller
 */
public abstract class BaseAdminController extends BaseController {
    protected final static String TEMPLATE_ADMIN_PATH = "admin/";

    protected String template(String path) {
        return TEMPLATE_ADMIN_PATH + path;
    }

}