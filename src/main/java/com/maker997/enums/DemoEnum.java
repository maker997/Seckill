package com.maker997.enums;

/**
 *
 * @author liujiaqi
 * @date 2017/10/20
 */
public enum DemoEnum {
    /**
     * 购买成功
     */
    PURCHASE(1,"购买"),
    /**
     * 删除
     */
    DELETE(2,"删除"),
    /**
     * 展示
     */
    SHOW(3,"展示");
    private int state;

    private String stateInfo;

    DemoEnum(int state, String stateInfo)
    {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState()
    {
        return state;
    }

    public String getStateInfo()
    {
        return stateInfo;
    }

    public static DemoEnum getEnum(int index)
    {
        for (DemoEnum demo : values())
        {
            if (demo.getState()==index)
            {
                return demo;
            }
        }
        return null;
    }
}
