package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class LoginActivity extends AppCompatActivity {

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private static final String ip = "192.168.35.55";
    private static final int port = 57000;

    LinearLayout linear;
    Button btn_login;
    Button btn_signup;
    TextInputEditText ti_id, ti_pw;

    static String id, pw, check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        linear = (LinearLayout) findViewById(R.id.linear);
        linear.setFocusable(true);
        linear.setFocusableInTouchMode(true);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        ti_id = (TextInputEditText) findViewById(R.id.edit_id);
        ti_pw = (TextInputEditText) findViewById(R.id.edit_pwd);

        //btn_login Button의 Click이벤트
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idStr = ti_id.getEditableText().toString();
                String pwStr = ti_pw.getEditableText().toString();
                id = idStr;
                pw = pwStr;

                if(idStr.isEmpty()){
                    Toast.makeText(LoginActivity.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else if(pwStr.isEmpty()){
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    ClientThread thread = new ClientThread();
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // 로그인 성공할때 if문 여기서 check에 사용자 이름 들어가 있음
                    if(!check.equals("")){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("u_name", check);
                        startActivity(intent);
                        ti_id.setText(null);
                        ti_pw.setText(null);
                        ti_id.clearFocus();
                        ti_pw.clearFocus();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "등록되지 않은 아이디이거나 잘못 입력하였습니다.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //btn_signup Button의 Click이벤트
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ti_id.setText(null);
                ti_pw.setText(null);
                ti_id.clearFocus();
                ti_pw.clearFocus();
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
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

                if(id != null && pw != null){
                    dos.writeUTF("check" + id + "#" + pw);
                    dos.flush();

                    Log.w("ClientThread", "서버로 보냄");
                    check = dis.readUTF();
                    System.out.println("[데이터 받기 성공]: " + check);

                }

                dis.close();
                dos.close();
                socket.close();

            }catch (IOException e1){
                Log.w("fail", "서버 접속 실패");
            }
        }
    }
}