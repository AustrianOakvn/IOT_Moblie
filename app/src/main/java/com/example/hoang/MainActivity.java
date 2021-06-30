package com.example.hoang;

import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import com.example.hoang.devices.DataHandler;
import com.example.hoang.devices.DoAm;
import com.example.hoang.devices.NhietDo;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String ARG_SECTION_NUMBER = "sth";
    public Button btnConnect;
    public Switch sw_den, sw_quat;
//    public MQTT mqttconnection;
    public DataHandler datahandler;
    public GraphView graph;
    public TextView NhietDo;
    public TextView DoAm;
    public Handler data_check = new Handler();
//    public LineGraphSeries<DataPoint> series;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NhietDo = findViewById(R.id.textView_NhietDo);
        NhietDo.setText("0.0");
        DoAm = findViewById(R.id.textView_DoAm);
        DoAm.setText("0.0");
        btnConnect=findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(this);
        sw_den = (Switch)findViewById(R.id.switch1);
        sw_quat = (Switch)findViewById(R.id.switch2);
        sw_den.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    datahandler.den.state = true;
                    datahandler.command(datahandler.username + datahandler.den.getTopic(), "ON");
                    Toast.makeText(MainActivity.this, "Bật quạt", Toast.LENGTH_LONG).show();

                }else{
                    datahandler.den.state = false;
                    datahandler.command(datahandler.username + datahandler.den.getTopic(), "OFF");
                    Toast.makeText(MainActivity.this, "Bật quạt", Toast.LENGTH_LONG).show();

                }
            }
        });
        sw_quat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(MainActivity.this, "Bật quạt", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Tắt quạt", Toast.LENGTH_LONG).show();
                }
            }
        });

        graph = (GraphView) findViewById(R.id.graph);
//        series = new LineGraphSeries<>(datahandler.CamBienNhietDo.getDatapoint());
        try {
            datahandler = new DataHandler();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Runnable r = new Runnable() {
            @Override
            public void run() {
                if (datahandler.CamBienNhietDo.new_data){
                    NhietDo.setText(datahandler.CamBienNhietDo.get_last_data());
                    datahandler.CamBienNhietDo.new_data = false;
                }
                if (datahandler.CamBienDoAm.new_data){
                    DoAm.setText(datahandler.CamBienDoAm.get_last_data());
                    datahandler.CamBienDoAm.new_data = false;
                }
                data_check.postDelayed(this, 500);
            }
        };
        data_check.postDelayed(r, 500);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i==R.id.btnConnect){
            try {
                if(datahandler.MQTT_connect()){
                    Toast.makeText(MainActivity.this, "Kết nối thành công", Toast.LENGTH_LONG).show();
                    datahandler.subscribe_to_topics();
                }
                else{
                    Toast.makeText(MainActivity.this, "Kết nối thất bại", Toast.LENGTH_LONG).show();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void update_loop(){
        datahandler.subscribe_to_topics();

    }
}
