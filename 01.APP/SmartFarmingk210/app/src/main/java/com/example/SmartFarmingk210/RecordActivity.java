package com.example.SmartFarmingk210;

import static com.example.SmartFarmingk210.utils.TimeCycle.compareDateTime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.SmartFarmingk210.adapter.RecyclerViewAdapter;
import com.example.SmartFarmingk210.chart.CreationChart;
import com.example.SmartFarmingk210.dao.SysData;
import com.example.SmartFarmingk210.databinding.ActivityRecordBinding;
import com.example.SmartFarmingk210.dao.SysDataDao;


import com.example.SmartFarmingk210.utils.MToast;

import com.gyf.immersionbar.ImmersionBar;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class RecordActivity extends AppCompatActivity {
    private ActivityRecordBinding binding;
    private SysDataDao dao;
    private RecyclerViewAdapter adapter;
    private List<Object> objects = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dao = new SysDataDao(this);
        initView();
    }

    private void initView() {
        setSupportActionBar(binding.toolbar);
        binding.toolbarLayout.setTitle("记录");
        ImmersionBar.with(this).init();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        eventManager();
        objects = dao.query();
        CreationChart.initChart(binding.chartView);
        try {
            if (objects != null && objects.size() > 0) {
                showEmptyView(false);
                listViewConfig(objects);
            } else {
                showEmptyView(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MToast.mToast(this, e.toString());
        }
    }

    private void listViewConfig(List<Object> objects) {
        binding.chartView.clear();//清除之前的数据
        binding.chartView.invalidate();//刷新显示
        CreationChart.xValues.clear(); // 清除时间坐标
        for (Object object : objects) {
            SysData d = (SysData) object;
            CreationChart.addEntry(binding.chartView, d);
        }
        adapter = new RecyclerViewAdapter(objects);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(layoutManager);
    }

    /***
     * 事件监听
     */
    private void eventManager() {
        binding.startDateTime.setOnClickListener(view -> {
            showDateTimeDialog(binding.startDateTime, true);
        });
        binding.endDateTime.setOnClickListener(view -> {
            showDateTimeDialog(binding.endDateTime, true);
        });

        binding.searchBtn.setOnClickListener(view -> {
            if (!binding.startDateTime.getText().toString().equals("开始时间") && !binding.endDateTime.getText().equals("结束时间")) {
                if (compareDateTime(binding.endDateTime.getText().toString() + " 23:59:59", binding.startDateTime.getText().toString() + " 00:00:00") > 0) {
                    objects = dao.query(binding.startDateTime.getText().toString() + " 00:00:00", binding.endDateTime.getText().toString() + " 23:59:59");
                    try {
                        if (objects != null && objects.size() > 0) {
                            showEmptyView(false);
                            listViewConfig(objects);
                        } else {
                            showEmptyView(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        MToast.mToast(this, e.toString());
                    }
                } else {
                    MToast.mToast(RecordActivity.this, "结束时间必须大于开始时间");
                }
            } else {
                MToast.mToast(RecordActivity.this, "请先设置时间");

            }
        });

    }


    /**
     * 显示日期弹窗
     *
     * @param view   TextView
     * @param setMax 是否设置日期最大值为当前
     */
    private void showDateTimeDialog(TextView view, boolean setMax) {
        //获取当前系统时间
        Calendar currentTime = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (x_, year, month, day) -> view.setText(String.format("%4d-%02d-%02d", year, month + 1, day)), currentTime.get(Calendar.YEAR), currentTime.get(Calendar.MONTH), currentTime.get(Calendar.DAY_OF_MONTH));
        if (setMax) {
            // 设置最大日期值为当前日期
            datePickerDialog.getDatePicker().setMaxDate(currentTime.getTimeInMillis());
        }
        datePickerDialog.show();
    }

    /***
     * 是否显示数据空视图
     * @param isShow boolean
     */
    private void showEmptyView(boolean isShow) {
        binding.nullView.setVisibility(isShow ? View.VISIBLE : View.GONE);
        binding.recyclerView.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }
}