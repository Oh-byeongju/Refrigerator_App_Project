package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class JoinActivity extends AppCompatActivity {

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private static final String ip = "192.168.35.55";
    private static final int port = 57000;
    public static String check_id;

    Button btn_signup;
    TextInputEditText j_id, j_pw, j_name, j_tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        btn_signup = (Button) findViewById(R.id.btn_signup);
        j_id = (TextInputEditText) findViewById(R.id.j_edit_id);
        j_pw = (TextInputEditText) findViewById(R.id.j_edit_pw);
        j_name = (TextInputEditText) findViewById(R.id.j_edit_uname);
        j_tel = (TextInputEditText) findViewById(R.id.j_edit_tel);

        //btn_signup Button의 Click이벤트
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idStr = j_id.getEditableText().toString();
                String pwStr = j_pw.getEditableText().toString();
                String nameStr = j_name.getEditableText().toString();
                String telStr = j_tel.getEditableText().toString();

                if(idStr.isEmpty() || pwStr.isEmpty() || nameStr.isEmpty() || telStr.isEmpty()) {
                    Toast.makeText(JoinActivity.this, "모든 항목은 필수 입력사항입니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    String tmp = idStr + '#' + pwStr + '#' + nameStr + '#' + telStr;
                    ClientThread thread = new ClientThread(tmp);
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(check_id.equals("중복")){
                        Toast.makeText(JoinActivity.this, "이미 사용 중인 아이디입니다.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(JoinActivity.this, "가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                        intent.putExtra("info", tmp);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    class ClientThread extends Thread {

        private String text;

        public ClientThread(String text) {
            this.text = text;
        }

        @Override
        public void run() {
            try {
                Log.d("connect", "연결 시도 중");
                Socket socket = new Socket();  // 소켓 객체 생성
                socket.connect(new InetSocketAddress(ip, port));
                Log.d("success", "연결 완료");

                String deli;
                deli = "Insert";

                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());

                dos.writeUTF(deli+text);
                dos.flush();
                Log.w("ClientThread", "서버로 보냄");

                check_id = dis.readUTF();
                System.out.println("[데이터 받기 성공]: " + check_id);

                dis.close();
                dos.close();
                socket.close();

            }catch (IOException e1){
                Log.w("fail", "서버 접속 실패");
            }
        }
    }
}