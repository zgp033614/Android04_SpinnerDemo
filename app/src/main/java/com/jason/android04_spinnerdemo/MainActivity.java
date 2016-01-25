package com.jason.android04_spinnerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Spinner spinner_city;
    //private static final String[] cities = new String[]{"北京", "上海", "广州", "深圳", "湖南","湖北"};
    private ArrayAdapter<CharSequence> adapter;
    //private SimpleAdapter adapter;
    private List<Map<String, Object>> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner_city = (Spinner) findViewById(R.id.spinner);

        //spinner的属性设置
        configSpinner();

        //获取数据源
        String[] cities = getResources().getStringArray(R.array.spinner_city);
        listData = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < cities.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("icon", R.mipmap.ic_launcher);
            map.put("name", cities[i]);
            listData.add(map);
        }

        //1. 构建适配器
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities);
        adapter=ArrayAdapter.createFromResource(this, R.array.spinner_city, android.R.layout
                .simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        //adapter = new SimpleAdapter(this, listData, R.layout.spinner_item, new String[]{"icon",
        //                "name"}, new int[]{R.id.imageView, R.id.textView});

        //2. 为Spinner绑定适配器
        spinner_city.setAdapter(adapter);

        //3. 为Spinner设置item点击事件监听器
        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "" + ((Map<String, Object>) parent
                // .getItemAtPosition(position))
                // .get("name"), Toast.LENGTH_SHORT)
                //       .show();

                Toast.makeText(MainActivity.this, "" + adapter.getItem(position), Toast
                        .LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Spinner的属性设置
     */
    private void configSpinner() {
        //说明：API16之后版本后才可以用
        //设置下拉项目选择窗口相对与Spinner窗口的水平偏移量
        spinner_city.setDropDownHorizontalOffset(5);
        //设置下拉项目选择窗口相对与Spinner串口的垂直偏移量
        spinner_city.setDropDownVerticalOffset(50);
        //设置下拉项目选择窗口的宽度
        spinner_city.setDropDownWidth(-2);
        //spinner_city.setEnabled(true);
    }
}



