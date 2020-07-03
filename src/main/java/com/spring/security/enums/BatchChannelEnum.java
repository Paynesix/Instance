package com.spring.security.enums;

public enum BatchChannelEnum implements EnumCommonFacade{
    //对账渠道枚举
    JSJKTAPP("09", "江苏健康通(微信公众号+APP)"),
    WEIXIN("08", "智慧医院-微信(窗口+公众号)"),
    ALIPAY("07", "智慧医院-支付宝(窗口+生活号)");

    private String batchChannel;
    private String batchChannelName;

    BatchChannelEnum(String batchChannel, String channelName) {
        this.batchChannel = batchChannel;
        this.batchChannelName = channelName;
    }
    public String getTypeName() {
        return batchChannelName;
    }

    public String getValue() {
        return batchChannel;
    }

    public static String nameOf(String batchChannel){

        BatchChannelEnum[] values = BatchChannelEnum.values();
        for(BatchChannelEnum enums:values){
            if(enums.getValue().equals(batchChannel)){
                return enums.getTypeName();
            }
        }
        return new String();
    }
}
