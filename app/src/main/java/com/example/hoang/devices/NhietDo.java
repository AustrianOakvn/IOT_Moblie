package com.example.hoang.devices;
import com.example.hoang.devices.Devices;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;


public class NhietDo extends Devices {

    private ArrayList<Double> real_time_data;
    private LineGraphSeries<DataPoint> series;
    int index = 0;
    private DataPoint[] datapoint = new DataPoint[] {new DataPoint(index, 0),
                                                    new DataPoint(index+1, 0),
                                                    new DataPoint(index+2, 0),
                                                    new DataPoint(index+3, 0)};
    public boolean new_data = false;

    public NhietDo(String name, String topic, String id) {
        super(name, topic, id);
        real_time_data = new ArrayList<Double>();
    }
    public void add_data(double data){

        this.real_time_data.add(data);
        index+=1;
        datapoint[3] = datapoint[2];
        datapoint[2] = datapoint[1];
        datapoint[1] = datapoint[0];
        datapoint[0] = new DataPoint(index, data);
        new_data = true;

    }
    public String get_last_data(){
        return Double.toString(real_time_data.get(real_time_data.size() - 1));
    }

    public DataPoint[] getDatapoint() {
        return datapoint;
    }

    public void setDatapoint(DataPoint[] datapoint) {
        this.datapoint = datapoint;
    }

    public LineGraphSeries<DataPoint> getSeries() {
        return series;
    }

    public void setSeries(LineGraphSeries<DataPoint> series) {
        this.series = series;
    }
}
