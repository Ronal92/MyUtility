package com.jinwoo.android.myutility;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends Fragment {

    Button btnLength, btnArea, btnWeight;
    LinearLayout layoutLength, layoutArea, layoutWeight;

    Spinner unitspinner1, unitspinner2;
    ArrayList<String> unitStr = new ArrayList<>(); // 단위들을 저장한다.

    EditText inputnum1;
    EditText inputnum2;

    String selectedUnit1 = "";
    String selectedUnit2 = "";

    MainActivity activity;

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);

        // 1. 위젯 가져오기
        btnLength = (Button) view.findViewById(R.id.btnLength);
        btnArea = (Button) view.findViewById(R.id.btnArea);
        btnWeight = (Button) view.findViewById(R.id.btnWeight);

        layoutLength = (LinearLayout)view.findViewById(R.id.layoutLengh);
        layoutArea = (LinearLayout)view.findViewById(R.id.layoutArea);
        layoutWeight = (LinearLayout)view.findViewById(R.id.layoutWeight);

        inputnum1 = (EditText) view.findViewById(R.id.inputnum1);
        inputnum2 = (EditText) view.findViewById(R.id.inputnum2);

        // 스피너
        unitspinner1 = (Spinner)view.findViewById(R.id.unitspinner1);
        unitspinner2 = (Spinner)view.findViewById(R.id.unitspinner2);

        unitStr.add("Milimeter(mm)");
        unitStr.add("Centimeter(cm)");
        unitStr.add("Meter(m)");
        unitStr.add("Kelometer(km)");
        unitStr.add("inch(in)");
        unitStr.add("feet(ft)");




        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // 2. 버튼 리스너 등록
        btnLength.setOnClickListener(clickListener);
        btnWeight.setOnClickListener(clickListener);
        btnArea.setOnClickListener(clickListener);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                activity, android.R.layout.simple_spinner_dropdown_item, unitStr
        );

        unitspinner1.setAdapter(adapter);
        unitspinner2.setAdapter(adapter);

        unitspinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            // 사용자가 Spinner 입력하면 EditText 초기화(왼쪽 스피너)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(activity, unitStr.get(position), Toast.LENGTH_SHORT).show();
                selectedUnit1 = unitStr.get(position);
                inputnum1.setText("");
                inputnum2.setText("");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        unitspinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(activity, unitStr.get(position), Toast.LENGTH_SHORT).show();
                selectedUnit2 = unitStr.get(position);
                inputnum1.setText("");
                inputnum2.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // EditText Listener
        inputnum1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            // 사용자가 단위를 바꾸면 계산한다. (값, 선택된 단위)
            @Override
            public void afterTextChanged(Editable s) {
                String changedInput = Converter(inputnum1.getText().toString(), selectedUnit1);
                inputnum2.setText(changedInput);
            }
        });
    }



    private String Converter(String inputnum, String selectedUnit){
        String res = "";
        double d = 0.0;

        //에러 처리
        if(inputnum.equals("")){
            inputnum = "0";
        } else if(selectedUnit.equals("Milimeter(mm)")){
            d = Double.parseDouble(inputnum);
            res = String.valueOf(d) ;
        } else if(selectedUnit.equals("Centimeter(cm)")){
            res = String.valueOf(Integer.parseInt(inputnum));
        }else if(selectedUnit.equals("Meter(m)")){
            res = String.valueOf(Integer.parseInt(inputnum) * 100) ;
        }
        return res;
    }

    View.OnClickListener clickListener = new View.OnClickListener(){
        public void onClick(View view){
            //클릭이 발생하면 모두 지운다.
            layoutLength.setVisibility(View.GONE);
            layoutArea.setVisibility(View.GONE);
            layoutWeight.setVisibility(View.GONE);

            // 해당하는 화면만 보여준다.
            switch(view.getId()){
                case R.id.btnLength:
                    layoutLength.setVisibility(View.VISIBLE);
                    break;
                case R.id.btnArea:
                    layoutArea.setVisibility(View.VISIBLE);
                    break;
                case R.id.btnWeight:
                    layoutWeight.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };
}
