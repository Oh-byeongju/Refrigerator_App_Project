package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecipeActivity extends AppCompatActivity {
    RecipeAdapter adapter;
    TextView tv_nr;
    EditText editText;

    // 데이터 통신
    static ArrayList<String> item = new ArrayList<String>();
    static String tmp;

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private static final String ip = "192.168.35.55";
    private static final int port = 57000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe1);

        editText = (EditText) findViewById(R.id.searchText);
        tv_nr = (TextView) findViewById(R.id.tv_nr);
        ListView listView = (ListView) findViewById(R.id.listView);

        item.clear();

        String ps;
        DatabaseHelper db = new DatabaseHelper(this);
        Cursor res = db.p_select();

        while(res.moveToNext()){
            item.add(res.getString(1));
        }

        ClientThread thread = new ClientThread();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String[] strArr = tmp.split("#");
        ArrayList<String> f_name = new ArrayList<String>(Arrays.asList(strArr));

        // 어댑터 안에 데이터 담기
        RecipeAdapter adapter = new RecipeAdapter();

        if(tmp.equals(""))
            tv_nr.setVisibility(View.VISIBLE);
        else{
            for(int i=0;i<f_name.size();i++){
                adapter.addItem(new RecipeItem(f_name.get(i)));
            }
        }


        // 리스트 뷰에 어댑터 설정
        listView.setAdapter(adapter);

        Button button = (Button) findViewById(R.id.button);

        // 이벤트 처리 리스너 설정
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecipeItem item = (RecipeItem) adapter.getItem(position);
                editText.setText(item.getName());
                button.performClick();
                //Toast.makeText(getApplicationContext(), "선택 :" + item.getName(), Toast.LENGTH_LONG).show();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                adapter.notifyDataSetChanged();

                if(name.trim().isEmpty()){
                    Toast.makeText(RecipeActivity.this, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(RecipeActivity.this, RecipeActivity2.class);
                    intent.putExtra("name",name);
                    startActivity(intent);
                }
            }
        });
    }

    class ClientThread extends Thread {
        @Override
        public void run() {
            try {
                Log.d("connect", "연결 시도 중");
                Socket socket = new Socket();  // 소켓 객체 생성
                socket.connect(new InetSocketAddress(ip, port));
                Log.d("success", "연결 완료");

                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());

                if(item != null){
                    Set<String> temp_item = new HashSet<>(item);
                    List<String> res_item = new ArrayList<>(temp_item);

                    StringBuilder sb = new StringBuilder();
                    sb.append("Recipe");

                    for (String val : res_item){
                        sb.append(val).append(" ");
                    }

                    String str = sb.toString();
                    System.out.println(str);

                    dos.writeUTF(str);
                    dos.flush();
                    Log.w("ClientThread", "서버로 보냄");

                    tmp = dis.readUTF();
                    System.out.println(tmp);
                }
                dis.close();
                dos.close();
                socket.close();

            }catch (IOException e1){
                Log.w("fail", "서버 접속 실패");
            }
        }
    }


    class RecipeAdapter extends BaseAdapter {
        ArrayList<RecipeItem> items = new ArrayList<RecipeItem>();


        // Generate > implement methods
        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(RecipeItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 뷰 객체 재사용
            RecipeIteamView view = null;
            if (convertView == null) {
                view = new RecipeIteamView(getApplicationContext());
            } else {
                view = (RecipeIteamView) convertView;
            }

            RecipeItem item = items.get(position);

            view.setName(item.getName());

            return view;
        }
    }
}


