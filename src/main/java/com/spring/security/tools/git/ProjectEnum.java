package com.spring.security.tools.git;

/**
 * @program: baoku-svn-tool
 * @Title: ProjectEnum
 * @description: 项目枚举值
 * @author: 赵瑾强
 * @create: 2021-01-22 17:00
 * @version: 1.0-SNAPSHOT
 */
public enum ProjectEnum {
    baoku_parent("baoku-parent", "baoku-common"),
    BAOKU_COMMON_LIB("baoku-common-lib", "baoku-common"),
    BAOKU_AIR_SERVER("baoku-air-server", "baoku-air"),
    BAOKU_INSURANCE_SERVER("baoku-insurance-server", "baoku-air"),
    BAOKU_INTERAIR_SERVER("baoku-interair-server", "baoku-air"),
    BAOKU_air_tsf_server("baoku-air-tsf-server", "baoku-air"),
    BAOKU_CAR_SERVER("baoku-car-server", "baoku-car"),
    BAOKU_CRMORDER_SERVER("baoku-crmorder-server", "baoku-crm"),
    BAOKU_CRM_SERVER("baoku-crm-server", "baoku-crm"),
    BAOKU_MESSAGE_SERVER("baoku-message-server", "baoku-crm"),
    BAOKU_DATA_SERVER("baoku-data-server", "baoku-data"),
    BAOKU_HOTELBOOK_SERVER("baoku-hotelbook-server", "baoku-hotel"),
    BAOKU_HOTELINFO_SERVER("baoku-hotelinfo-server", "baoku-hotel"),
    BAOKU_hotel_tsf_server("baoku-hotel-tsf-server", "baoku-hotel"),
    BAOKU_PAY_SERVER("baoku-pay-server", "baoku-pay"),
    BAOKU_TRAIN_SERVER("baoku-train-server", "baoku-train"),
    BAOKU_train_tsf_server("baoku-train-tsf-server", "baoku-train"),
    BAOKU_API_WEB("baoku-api-web", "baoku-web"),
    BAOKU_HOTEL_WEB("baoku-hotel-web", "baoku-web"),
    BAOKU_MANAGE_WEB("baoku-manage-web", "baoku-web"),
    baoku_pay_web("baoku-pay-web", "baoku-web"),
    BAOKU_proxy_WEB("baoku-proxy-web", "baoku-web"),
    BAOKU_report_WEB("baoku-report-web", "baoku-web"),
    BAOKU_shopen_WEB("baoku-shopen-web", "baoku-web"),
    BAOKU_shop_WEB("baoku-shop-web", "baoku-web"),
    BAOKU_super_WEB("baoku-super-web", "baoku-web"),
    BAOKU_surface_WEB("baoku-surface-web", "baoku-web"),
    BAOKU_h5api_WEB("baoku-h5api-web", "baoku-web");

    //项目名
    private String name;
    //项目所属组名
    private String groupName;

    ProjectEnum(String name, String groupName) {
        this.name = name;
        this.groupName = groupName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
