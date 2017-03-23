package com.jinwoo.android.myutility;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;



public class OneFragment extends Fragment implements View.OnClickListener{

    TextView result;
    TextView preview;
    Button calBtn0, calBtn1, calBtn2, calBtn3, calBtn4, calBtn5, calBtn6, calBtn7, calBtn8, calBtn9, calBtnCcl, calBtnRun, calBtnAdd, calBtnSub, calBtnDiv, calBtnMul;
    View view = null;


    public OneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 뷰를 재사용하기 위해 Holder 형태로 만들어준다.
        if( view == null ){
            view = inflater.inflate(R.layout.fragment_one, container, false);
        }


        result = (TextView)view.findViewById(R.id.result);
        preview = (TextView)view.findViewById(R.id.preview);
        calBtn0 = (Button)view.findViewById(R.id.calBtn0);
        calBtn1 = (Button)view.findViewById(R.id.calBtn1);
        calBtn2 = (Button)view.findViewById(R.id.calBtn2);
        calBtn3 = (Button)view.findViewById(R.id.calBtn3);
        calBtn4 = (Button)view.findViewById(R.id.calBtn4);
        calBtn5 = (Button)view.findViewById(R.id.calBtn5);
        calBtn6 = (Button)view.findViewById(R.id.calBtn6);
        calBtn7 = (Button)view.findViewById(R.id.calBtn7);
        calBtn8 = (Button)view.findViewById(R.id.calBtn8);
        calBtn9 = (Button)view.findViewById(R.id.calBtn9);
        calBtnCcl = (Button)view.findViewById(R.id.calBtnCcl);
        calBtnRun = (Button)view.findViewById(R.id.calBtnRun);
        calBtnAdd = (Button)view.findViewById(R.id.calBtnAdd);
        calBtnSub = (Button)view.findViewById(R.id.calBtnSub);
        calBtnDiv = (Button)view.findViewById(R.id.calBtnDiv);
        calBtnMul = (Button)view.findViewById(R.id.calBtnMul);


        result.setOnClickListener(this);
        preview.setOnClickListener(this);

        calBtn0.setOnClickListener(this);
        calBtn1.setOnClickListener(this);
        calBtn2.setOnClickListener(this);
        calBtn3.setOnClickListener(this);
        calBtn4.setOnClickListener(this);
        calBtn5.setOnClickListener(this);
        calBtn6.setOnClickListener(this);
        calBtn7.setOnClickListener(this);
        calBtn8.setOnClickListener(this);
        calBtn9.setOnClickListener(this);
        calBtnCcl.setOnClickListener(this);
        calBtnRun.setOnClickListener(this);
        calBtnAdd.setOnClickListener(this);
        calBtnSub.setOnClickListener(this);
        calBtnMul.setOnClickListener(this);
        calBtnDiv.setOnClickListener(this);

        return view;
    }

    public void onClick(View view) {
        switch(view.getId()){
            case R.id.calBtn0 :
                addPreview("0");
                break;
            case R.id.calBtn1 :
                addPreview("1");
                break;
            case R.id.calBtn2 :
                addPreview("2");
                break;
            case R.id.calBtn3 :
                addPreview("3");
                break;
            case R.id.calBtn4 :
                addPreview("4");
                break;
            case R.id.calBtn5 :
                addPreview("5");
                break;
            case R.id.calBtn6 :
                addPreview("6");
                break;
            case R.id.calBtn7 :
                addPreview("7");
                break;
            case R.id.calBtn8 :
                addPreview("8");
                break;
            case R.id.calBtn9 :
                addPreview("9");
                break;
            case R.id.calBtnAdd :
                addPreview("+");
                break;
            case R.id.calBtnSub :
                addPreview("-");
                break;
            case R.id.calBtnMul :
                addPreview("*");
                break;
            case R.id.calBtnDiv :
                addPreview("/");
                break;
            case R.id.calBtnRun :
                // 현재까지 preview에 담긴 계산식을 스트링으로 calculate 함수로 보낸다.
                calculate(preview.getText().toString());
                break;

            case R.id.calBtnCcl :
                // preview 객체와 result에 저장한 객체를 초기화한다.
                preview.setText("");
                setResult("");
                break;
        }
    }



    // 문자열을 수식으로 계산하기
    private void calculate(String value){
        // 1. 문자열을 정규식을 이용해서 * / + - 를 기준으로 배열에 넣는다.
        String splited[] = value.split("(?<=[*/+-])|(?=[*/+-])");

        // 2. 동적배열을 사용하기 위해 ArrayList에 splited 문자열을 담는다.
        //    연산 이후의 계산된 값들을 재배열(이미 계산된 수식은 삭제된다)하기 위해 동적 배열이 필요하다.
        ArrayList<String> list = new ArrayList<String>();

        // 3. 2번에서 말한 재배열을 위해 splited 문자열을 list에 담는다.
        for(String item : splited){
            list.add(item);
        }

        // 4. list를 돌기 위해 사용하는 index
        // (동적배열 사용하지 말것!! 루프 중간에 배열 크기가 변동되기 때문에 에러 발생!)
        int index = 0;

        // 5. 연산자 우선순위가 높은 *,/ 를 먼저 계산한다음 +,-f를 계산한다.
        //    따라서 loop가 2개로 구성된다.
        //    배열을 돌면서 연산자를 기준으로 값을 꺼낸다.
        for(index = 0; index < list.size(); ){
            // 5.1 item 변수에 값을 담은 후, * /를 찾는다.
            String operator = list.get(index);

            double op1 = 0;
            double op2 = 0;
            double res = 0;
            boolean check = true;

            // 5.2 operator가 곱하기인 경우
            if(operator.equals("*")){
                // 5.2.1 연산자 앞의 숫자를 꺼내고
                op1 = Double.parseDouble(list.get(index-1));
                // 5.2.2 연산자 뒤의 숫자를 꺼낸다
                op2 = Double.parseDouble(list.get(index+1));
                // 5.2.3 두 숫자를 곱한다.
                res = op1 * op2;
                // 5.2.4 곱하기 연산을 하였다는 표식을 해준다.
                check = true;

                // 5.3 operator가 나누기인 경우
            }else if(operator.equals("/")){
                // 5.3.1 연산자 앞의 숫자를 꺼내고
                op1 = Double.parseDouble(list.get(index-1));
                // 5.3.2 연산자 뒤의 숫자를 꺼낸다
                op2 = Double.parseDouble(list.get(index+1));
                // 5.3.3 두 숫자를 나눈다..
                res = op1 / op2;
                // 5.3.4 나누기 연산을 하였다는 표식을 해준다.
                check = true;

                // 5.4  연산자에 걸리지 않으면 체크 플래그를 false 전환해서 반복문을 진행하게 한다.
            }else{
                check = false;
            }

            // 5.5 앞에서 * 또는 / 에 걸리면
            if(check){
                // 5.5.1 현재 내 연산자 위치에 결과값을 저장하고
                list.set(index,res+"");
                // 5.5.2 이미 연산된 내 연산자의 앞,뒤의 값을 제거한다.
                list.remove(index+1);
                list.remove(index-1);
                // 위의 제거된 연산자 때문에 배열이 재배열되면서
                // 인덱스를 증가시키지 않아도 인덱스는 다음 연산자를 가리키게 된다.
            }else{
                // 걸린게 없으면 인덱스를 증가시켜서 다음 연산자를 확인한다.
                index++;
            }
        }


        // 인덱스 값 초기화.
        index = 0;

        // 6. + - 를 검사한다.
        for( index=0 ; index < list.size() ;){
            String item = list.get(index);
            double one = 0;
            double two = 0;
            double sum = 0;
            boolean check = true;
            if(item.equals("+")) {
                one = Double.parseDouble(list.get(index-1));
                two = Double.parseDouble(list.get(index+1));
                sum = one + two;
                check = true;
            }else if(item.equals("-")){
                one = Double.parseDouble(list.get(index-1));
                two = Double.parseDouble(list.get(index+1));
                sum = one - two;
                check = true;
            }else{
                check = false;
            }

            if(check) {
                list.set(index, sum + "");
                list.remove(index + 1);
                list.remove(index - 1);
                index--;
            }else {
                index++;
            }
        }

        // 최종적으로 list 의 0번째 값을 꺼내면 결과를 확인할 수 있다.
        setResult("Result : "+list.get(0));
    }

    //result 객체에 string을 담는다.
    private void setResult(String string){
        result.setText(string);
    }

    //기존에 담겨있던 계산 내용 + 현재 추가된 내용을 previe 객체에 담는다.
    private void addPreview(String string){
        preview.setText((preview.getText() + string));
    }

}
