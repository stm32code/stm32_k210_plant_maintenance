package com.example.SmartFarmingk210.bean;

public class DataDTO {
    private Integer relay;
    private Integer fan;
    private Integer temp_v;

    public void setRelay(Integer relay) {
        this.relay = relay;
    }

    public void setFan(Integer fan) {
        this.fan = fan;
    }

    public void setTemp_v(Integer temp_v) {
        this.temp_v = temp_v;
    }

    @Override
    public String toString() {
        return "DataDTO{" +
                "relay=" + relay +
                ", temp_v=" + fan +
                ", temp_v=" + temp_v +

                '}';
    }
    // Getter and Setter methods for fields

}
