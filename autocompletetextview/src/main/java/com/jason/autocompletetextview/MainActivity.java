package com.jason.autocompletetextview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button_search;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapter;
    private SharedPreferences sharedPreferences;
    private String[] hisArrays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //视图初始化
        initView();

        //初始化AutoCompleteTextView
        initAutoComplete("history", autoCompleteTextView);

    }

    /**
     * 初始化AutoCompleteTextView,最多显示5项提示
     * 使autocompleteTextView在一开始获得焦点的自动提示
     *
     * @param field
     * @param autoCompleteTextView
     */
    private void initAutoComplete(String field, final AutoCompleteTextView autoCompleteTextView) {
        //1.获取SharedPreferences对象
        sharedPreferences = getSharedPreferences("network_url", 0);

        //2. 获取其中的历史数据
        String longhistory = sharedPreferences.getString("history", "nothing");
        hisArrays = longhistory.split(",");

        //3. 构建适配器
        adapter = new ArrayAdapter<String>(this, android.R.layout
                .simple_dropdown_item_1line, hisArrays);

        //4. 只保留最近50条的记录
        if (hisArrays.length > 50) {
            String[] newArrays = new String[50];
            //拷贝源数组中的前50条数据到新的数组中
            System.arraycopy(hisArrays, 0, newArrays, 0, 50);
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
                    newArrays);
        }

        //5. 绑定适配器
        //autoCompleteTextView.setAdapter(adapter);

        //下拉菜单的高度
        autoCompleteTextView.setDropDownHeight(550);
        //垂直偏移量
        autoCompleteTextView.setDropDownVerticalOffset(20);
        //水平偏移量
        autoCompleteTextView.setDropDownHorizontalOffset(10);
        //设置用户至少输入多少个字符才会显示提示
        autoCompleteTextView.setThreshold(1);
        //设置出现在下拉菜单中的提示标题
        autoCompleteTextView.setCompletionHint("最近的5条记录");

//        autoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                AutoCompleteTextView view = (AutoCompleteTextView) v;
//
//                if (hasFocus) {
//                    view.showDropDown();
//                }
//            }
//        });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //显示下拉窗口
                autoCompleteTextView.showDropDown();

                sharedPreferences = getSharedPreferences("network_url", 0);
                String longhistory = sharedPreferences.getString("history", "nothing");
                hisArrays = longhistory.split(",");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 把指定AutoCompleteTextView中的内容保存到SharedPreference中指定的字符段
     *
     * @param field
     * @param autoCompleteTextView
     */
    private void saveHistory(String field, AutoCompleteTextView autoCompleteTextView) {
        //获取AutoCompleteTextView中的数据
        String text = autoCompleteTextView.getText().toString();
        //获取指定的SharedPreference
        SharedPreferences sharedPreferences = getSharedPreferences("network_url", 0);
        String longhistory = sharedPreferences.getString(field, "nothing");
        if (!longhistory.contains(text + ",")) {
            StringBuilder sb = new StringBuilder(longhistory);
            sb.insert(0, text + ",");
            sharedPreferences.edit().putString("history", sb.toString()).commit();
        }
    }

    /**
     * 视图初始化
     */
    private void initView() {
        button_search = (Button) findViewById(R.id.button1);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autocompletetextview);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存数据到SharedPreference中
                saveHistory("history", autoCompleteTextView);
            }
        });
    }
}













