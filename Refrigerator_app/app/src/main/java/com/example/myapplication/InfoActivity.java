package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

public class InfoActivity extends AppCompatActivity {

    static String p_name, p_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        p_name = intent.getStringExtra("p_name");
        p_date = intent.getStringExtra("p_date");

        FragmentInfo fragment = new FragmentInfo();
        // 추가할 fragment 생성

        FragmentManager fragmentManager = getSupportFragmentManager();
        // 프래그먼트 매니저 선언
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // 프래그먼트 트랜잭션 시작

        Bundle bundle = new Bundle();
        bundle.putString("p_name", p_name);
        bundle.putString("p_date", p_date);
        fragment.setArguments(bundle);

        fragmentTransaction.add(R.id.frame,fragment); // 삽입할 위치, 삽입할 fragment
        fragmentTransaction.commit(); // 트랜잭션 종료
    }

    // 뒤로가기 버튼
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}