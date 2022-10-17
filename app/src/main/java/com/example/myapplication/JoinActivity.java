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
    private static final int port = 58000;

    static String id, check_id;

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
                    ClientThread thread = new ClientThread();
                    thread.start();

                    if(check_id != null){
                        Toast.makeText(JoinActivity.this, "중복된 아이디입니다.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        // 데이터 전달
                        String tmp = idStr + '#' + pwStr + '#' + nameStr + '#' + telStr;
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
        @Override
        public void run() {
            try {
                Log.d("connect", "연결 시도 중");
                Socket socket = new Socket();  // 소켓 객체 생성
                socket.connect(new InetSocketAddress(ip, port));
                Log.d("success", "연결 완료");

                String deli;

                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());

                if(id != null){
                    deli = "checkId";
                    dos.writeUTF(deli+id);
                    dos.flush();
                    id = null;
                    Log.w("ClientThread", "서버로 보냄");

                    check_id = dis.readUTF();
                    System.out.println("[데이터 받기 성공]: " + check_id);
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