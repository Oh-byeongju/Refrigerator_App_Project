package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.ui.main.SectionsPagerAdapter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    //여기부터

    static String name = " ";
    FloatingActionButton fab_add, fab_edit, fab_camera, fab_recipe;
    Animation fabOpen, fabClose, fabRClockwise, fabRAntiClockwise;
    static String string;

    boolean isOpen = false;

    private long backBtnTime = 0;

    private NotificationHelper mNotificationhelper;

    //여기까지
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent.getStringExtra("u_name") != null){
            name = intent.getStringExtra("u_name");
            name = name+"의 냉장고";
        }

        TextView text1 = (TextView)findViewById(R.id.title);
        text1.setText(name);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        Cal_dday cd = new Cal_dday();

        mNotificationhelper = new NotificationHelper(this);

        List<String> item = new ArrayList<String>();
        List<Integer> d_day = new ArrayList<Integer>();
        List<String> mItemList = new ArrayList<>();
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor res = db.p_select();

        while(res.moveToNext()){
            item.add(res.getString(1));

            String intStr = res.getString(4).replaceAll("[^0-9]", "");
            if(intStr.isEmpty() || intStr == null) {
                d_day.add(0);
            }
            else{
                String year = "20";
                int myear = Integer.parseInt(year.concat(intStr.substring(0, 2)));
                int mmonth = Integer.parseInt(intStr.substring(2, 4));
                int mday = Integer.parseInt(intStr.substring(4, 6));

                d_day.add(cd.countdday(myear, mmonth, mday));
            }
        }

        for(int i=0; i<item.size(); i++){
            if(d_day.get(i) <= 3)
                mItemList.add(item.get(i));
        }

        if(mItemList.size() > 0){
            String str = "";
            for(int j=0; j<mItemList.size(); j++){
                if(j == mItemList.size()-1)
                    str += mItemList.get(j);
                else
                    str += mItemList.get(j) + ",";
            }
            String title = str + "의 유통기한이 얼마남지 않았어요!";
            sendOnChannel1(title);
        }

        /*FloatingActionButton fab = binding.fab;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //여기부터 끝까지
        fab_add = findViewById(R.id.add_button);
        fab_edit = findViewById(R.id.edit_button);
        fab_camera = findViewById(R.id.camera_button);
        fab_recipe = findViewById(R.id.recipe_button);

        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabRClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fabRAntiClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlocksiwse);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen) {
                    fab_edit.startAnimation(fabClose);
                    fab_camera.startAnimation(fabClose);
                    fab_add.startAnimation(fabRClockwise);

                    fab_edit.setVisibility(View.INVISIBLE);
                    fab_camera.setVisibility(View.INVISIBLE);

                    fab_edit.setClickable(false);
                    fab_camera.setClickable(false);

                    isOpen = false;
                } else {
                    fab_edit.startAnimation(fabOpen);
                    fab_camera.startAnimation(fabOpen);
                    fab_add.startAnimation(fabRAntiClockwise);

                    fab_edit.setVisibility(View.VISIBLE);
                    fab_camera.setVisibility(View.VISIBLE);

                    fab_edit.setClickable(true);
                    fab_camera.setClickable(true);

                    isOpen = true;
                }
            }
        });

        fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "직접 입력", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, InfoActivity2.class);
                startActivity(intent);
            }
        });

        fab_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "바코드 스캔", Toast.LENGTH_SHORT).show();
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setOrientationLocked(false);
                integrator.setPrompt("바코드를 빨간색 선에 맞춰주세요");     // 옆에 뜨는 문구를 바꿀 수 있다.
                integrator.initiateScan();
            }
        });

        fab_recipe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void sendOnChannel1(String title){
        NotificationCompat.Builder nb = mNotificationhelper.getChannel1Notification(title);
        mNotificationhelper.getManager().notify(1, nb.build());
    }


    @Override
    public void onBackPressed() {
        // 로그아웃 메시지
        showDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == IntentIntegrator.REQUEST_CODE){
                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (result != null) {
                    if (result.getContents() != null) {
                        // Toast.makeText(this, "Scanned: " + result.getContents() + "\nFormat:" + result.getFormatName(), Toast.LENGTH_LONG).show();
                        string = null;
                        string = result.getContents();

                        Intent intent = new Intent(this, camera.class);
                        intent.putExtra("barcode", string);
                        startActivity(intent);
                    }
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Alert")
                .setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "취소", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
}