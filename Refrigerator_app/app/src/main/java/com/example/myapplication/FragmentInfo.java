package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FragmentInfo extends Fragment {

    EditText name, memo;
    EditText end_day;
    EditText start_day;
    Button birthday, btnPlus, btnMinus, regist, btn_ref1, btn_ref2, btn_ref3;

    TextView tvCount;
    String[] items = {"냉장", "냉동", "실온"};
    static int count;
    static String storage = "냉장";
    static int num = 0;
    static String p_name, p_date;
    String d_year,d_mon,d_day;


    Date curDate = new Date(); // 현재
    final SimpleDateFormat dataFormat = new SimpleDateFormat("yy / MM / dd");
    // SimpleDateFormat 으로 포맷 결정
    String result = dataFormat.format(curDate);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info, container, false);
        // 프래그먼트에 findViewByld 적용위해 View 선언
        name = view.findViewById(R.id.et_p_name);
        end_day = view.findViewById(R.id.end);
        start_day = view.findViewById(R.id.start);
        start_day.setText(result);

        memo = view.findViewById(R.id.memo);

        birthday = view.findViewById(R.id.button);
        regist = view.findViewById(R.id.button3);
        btn_ref1 = view.findViewById(R.id.storage1);
        btn_ref2 = view.findViewById(R.id.storage2);
        btn_ref3 = view.findViewById(R.id.storage3);

        Bundle bundle = getArguments();
        p_name = bundle.getString("p_name");
        p_date = bundle.getString("p_date");

        //birthday.setText(result); // 오늘 날짜로 초기화

        //수량 설정
        count = 1;
        tvCount = view.findViewById(R.id.count);
        tvCount.setText(count+"");
        btnPlus = view.findViewById(R.id.btn_plus);
        btnMinus = view.findViewById(R.id.btn_minus);

        btn_ref1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_ref1.setBackground(getResources().getDrawable(R.drawable.storage_border1));
                btn_ref2.setBackground(getResources().getDrawable(R.drawable.storage_border2_1));
                btn_ref3.setBackground(getResources().getDrawable(R.drawable.storage_border3_1));
                btn_ref1.setTextColor(Color.parseColor("#ffffff"));
                btn_ref2.setTextColor(Color.parseColor("#000000"));
                btn_ref3.setTextColor(Color.parseColor("#000000"));
                storage = "냉장";
            }
        });

        btn_ref2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_ref1.setBackground(getResources().getDrawable(R.drawable.storage_border1_1));
                btn_ref2.setBackground(getResources().getDrawable(R.drawable.storage_border2));
                btn_ref3.setBackground(getResources().getDrawable(R.drawable.storage_border3_1));
                btn_ref1.setTextColor(Color.parseColor("#000000"));
                btn_ref2.setTextColor(Color.parseColor("#ffffff"));
                btn_ref3.setTextColor(Color.parseColor("#000000"));
                storage = "냉동";
            }
        });

        btn_ref3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_ref1.setBackground(getResources().getDrawable(R.drawable.storage_border1_1));
                btn_ref2.setBackground(getResources().getDrawable(R.drawable.storage_border2_1));
                btn_ref3.setBackground(getResources().getDrawable(R.drawable.storage_border3));
                btn_ref1.setTextColor(Color.parseColor("#000000"));
                btn_ref2.setTextColor(Color.parseColor("#000000"));
                btn_ref3.setTextColor(Color.parseColor("#ffffff"));
                storage = "실온";
            }
        });

        if(p_name.isEmpty()){
            name.setText("");
        }
        else{
            String data[] = p_name.split("st_");
            name.setText(data[0]);

            if(data[1].equals("냉동")){
                btn_ref2.performClick();
            }
            else if(data[1].equals("실온")){
                btn_ref3.performClick();
            }
            else{
                btn_ref1.performClick();
            }
        }

        if(p_date.length() == 6){
            d_year = p_date.substring(0,2);
            d_mon = p_date.substring(2,4);
            d_day = p_date.substring(4,6);
            end_day.setText(d_year+ " / " + d_mon + " / " + d_day);
        }
        else{
            end_day.setText("");
        }
        p_date = null;
        p_name = null;

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                tvCount.setText(count+"");
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count > 0){
                    count--;
                    tvCount.setText(count+"");
                }
                else{

                }
            }
        });

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num = 1;
                showDateDialog();
            }
        });

        //날짜 정보 입력
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num = 2;
                showDateDialog(); // 생년월일 버튼 클릭 시 showDateDialog() 함수 호출
            }
        });

        Button save = view.findViewById(R.id.button2);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cal_dday cd = new Cal_dday();

                String nameStr = name.getText().toString();
                String ageStr = end_day.getText().toString();
                String startStr = start_day.getText().toString();
                String memoStr = memo.getText().toString();

                if((nameStr.isEmpty() || nameStr == null) || (ageStr.isEmpty() || ageStr == null) || (startStr.isEmpty() || startStr == null)){
                    Toast.makeText(getContext(), "모든 항목은 필수 입력사항입니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    String intStr = ageStr.replaceAll("[^0-9]", "");
                    String year = "20";
                    int myear = Integer.parseInt(year.concat(intStr.substring(0, 2)));
                    int mmonth = Integer.parseInt(intStr.substring(2, 4));
                    int mday = Integer.parseInt(intStr.substring(4, 6));

                    int d_day = cd.countdday(myear, mmonth, mday);

                    if(d_day < 0){
                        Toast.makeText(getContext(), "이미 유통기한이 지난 상품입니다.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        DatabaseHelper db = new DatabaseHelper(getActivity());
                        db.addInfo(nameStr, storage, count, ageStr, memoStr);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                }

            }
        }); // 저장 버튼 클릭 시 입력한 정보 표시

        return view;
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        try {
            curDate = dataFormat.parse(birthday.getText().toString());
            // 문자열로 된 생년월일을 Date로 파싱
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        calendar.setTime(curDate);

        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH);
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        // 년,월,일 넘겨줄 변수

        DatePickerDialog dialog = new DatePickerDialog(getContext(), birthDateSetListener, curYear, curMonth, curDay);
        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener birthDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(Calendar.YEAR, year);
            selectedCalendar.set(Calendar.MONTH, month);
            selectedCalendar.set(Calendar.DAY_OF_MONTH, day);
            // 달력의 년월일을 버튼에서 넘겨받은 년월일로 설정

            Date curDate = selectedCalendar.getTime(); // 현재를 넘겨줌
            setSelectedDate(curDate);
        }
    };

    private void setSelectedDate(Date curDate) {
        String selectedDateStr = dataFormat.format(curDate);
        // 버튼의 텍스트 수정
        if(num == 1)
            start_day.setText(selectedDateStr);
        else
            end_day.setText(selectedDateStr);
    }
}