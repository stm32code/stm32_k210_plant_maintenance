package com.example.SmartFarmingk210.bean;

public class Send {






    private Integer cmd;

    public void setCmd(Integer cmd) {
        this.cmd = cmd;
    }

    public void setFlage(Integer flage) {
        this.flage = flage;
    }

    public void setPump(Integer pump) {
        this.pump = pump;
    }

    public void setFan(Integer fan) {
        this.fan = fan;
    }

    public void setWater(Integer water) {
        this.water = water;
    }

    public void setSoli_v(Integer soli_v) {
        this.soli_v = soli_v;
    }

    public void setTemp_v(Integer temp_v) {
        this.temp_v = temp_v;
    }



    private Integer flage;
    private Integer pump;
    private Integer fan;
    private Integer water;

    private Integer soli_v;
    private Integer temp_v;

    public void setLight_v(Integer light_v) {
        this.light_v = light_v;
    }

    public void setSomg_v(Integer somg_v) {
        this.somg_v = somg_v;
    }

    private Integer light_v;
    private Integer somg_v;


}
