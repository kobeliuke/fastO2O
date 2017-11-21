package com.will.o2o.enums;

public enum ShopStateEnum {

    CHECK(0,"under authentication"),
    OFFLINE(-1,"illegal shop"),
    SUCCESS(1,"operation success"),
    PASS(2,"pass authentication"),
    INNER_ERROR(-1001,"system inner error"),
    NULL_SHOPID(-1002,"null shopId"),
    NULL_SHOP(-1003,"null shop");


    private int state;
    private String stateInfo;

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    private ShopStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ShopStateEnum stateOf(int state){
        for(ShopStateEnum stateEnum : values()){
            if(stateEnum.getState() == state){
                return stateEnum;
            }
        }
        return null;
    }

}
