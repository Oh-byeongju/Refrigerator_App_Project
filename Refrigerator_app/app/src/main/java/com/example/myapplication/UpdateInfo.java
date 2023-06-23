package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateInfo extends AppCompatActivity {

    String p_name, p_date, s_name, s_st, s_date, s_id, s_memo;
    Integer s_cnt;
    EditText name, memo;
    EditText end_day;
    EditText start_day;
    Button birthday, btnPlus, btnMinus, regist, btn_ref1, btn_ref2, btn_ref3;

    TextView tvCount;

    static String storage = "냉장";
    static int num = 0;

    Date curDate = new Date(); // 현재
    final SimpleDateFormat dataFormat = new SimpleDateFormat("yy / MM / dd");
    // SimpleDateFormat 으로 포맷 결정
    String result = dataFormat.format(curDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        name = (EditText) findViewById(R.id.et_p_name);
        end_day = (EditText) findViewById(R.id.end);
        start_day = (EditText) findViewById(R.id.start);
        start_day.setText(result);

        memo = (EditText) findViewById(R.id.memo);

        birthday = (Button) findViewById(R.id.button);
        regist = (Button) findViewById(R.id.button3);
        btn_ref1 = (Button) findViewById(R.id.storage1);
        btn_ref2 = (Button) findViewById(R.id.storage2);
        btn_ref3 = (Button) findViewById(R.id.storage3);
        btnPlus = (Button)findViewById(R.id.btn_plus);
        btnMinus = (Button) findViewById(R.id.btn_minus);

        tvCount = (TextView) findViewById(R.id.count);

        Intent intent = getIntent();
        p_name = intent.getStringExtra("p_name");
        p_date = intent.getStringExtra("p_date");

        DatabaseHelper db = new DatabaseHelper(this);
        Cursor res = db.selectInfo(p_name, p_date);

        while(res.moveToNext()){
            s_id = res.getString(0);
            s_name = res.getString(1);
            s_st = res.getString(2);
            s_cnt = res.getInt(3);
            s_date = res.getString(4);
            s_memo = res.getString(5);
        }
        System.out.println(s_st);
        name.setText(s_name);
        tvCount.setText(s_cnt+"");
        end_day.setText(s_date);
        memo.setText(s_memo);

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

        if(s_st.equals("냉동")){
            btn_ref2.performClick();
        }
        else if(s_st.equals("실온")){
            btn_ref3.performClick();
        }
        else{
            btn_ref1.performClick();
        }

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_cnt++;
                tvCount.setText(s_cnt+"");
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(s_cnt > 0){
                    s_cnt--;
                    tvCount.setText(s_cnt+"");
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

        Button save = (Button) findViewById(R.id.button2);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStr = name.getText().toString();
                String ageStr = end_day.getText().toString();
                String startStr = start_day.getText().toString();

                if((nameStr.isEmpty() || nameStr == null) || (ageStr.isEmpty() || ageStr == null) || (startStr.isEmpty() || startStr == null)){
                    Toast.makeText(UpdateInfo.this, "모든 항목은 필수 입력사항입니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    String intStr = ageStr.replaceAll("[^0-9]", "");
                    String year = "20";
                    int myear = Integer.parseInt(year.concat(intStr.substring(0, 2)));
                    int mmonth = Integer.parseInt(intStr.substring(2, 4));
                    int mday = Integer.parseInt(intStr.substring(4, 6));

                    int d_day = countdday(myear, mmonth, mday);

                    if(d_day < 0){
                        Toast.makeText(UpdateInfo.this, "이미 유통기한이 지난 상품입니다.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        DatabaseHelper db = new DatabaseHelper(UpdateInfo.this);
                        db.updateData(name.getText().toString(), storage, s_cnt, end_day.getText().toString(), memo.getText().toString(), s_name, s_date);
                        Toast.makeText(UpdateInfo.this, "수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateInfo.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        }); // 저장 버튼 클릭 시 입력한 정보 표시

    }

    public int countdday(int myear, int mmonth, int mday) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Calendar todaCal = Calendar.getInstance(); //오늘날자 가져오기
            Calendar ddayCal = Calendar.getInstance(); //오늘날자를 가져와 변경시킴

            mmonth -= 1; // 받아온날자에서 -1을 해줘야함.
            ddayCal.set(myear,mmonth,mday);// D-day의 날짜를 입력

            long today = todaCal.getTimeInMillis()/86400000; //->(24 * 60 * 60 * 1000) 24시간 60분 60초 * (ms초->초 변환 1000)
            long dday = ddayCal.getTimeInMillis()/86400000;
            long count = dday - today; // 오늘 날짜에서 dday 날짜를 빼주게 됩니다.
            return (int) count;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
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

        DatePickerDialog dialog = new DatePickerDialog(this, birthDateSetListener, curYear, curMonth, curDay);
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