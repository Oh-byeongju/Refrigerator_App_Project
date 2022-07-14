package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

public class camera extends AppCompatActivity {

    TextView p1, p2;
    private ImageView imageView;
    private static final int REQUEST_IMAGE_CODE = 101;
    static Bitmap imageBitmap;
    static String str1, barcode, p_date, p_name;
    static byte[] arr;

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private static final String ip = "113.198.234.39";
    private static final int port = 58000;

    ProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        p_name = "";

        //로딩창 객체 생성
        customProgressDialog = new ProgressDialog(this);
        //로딩창을 투명하게
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Intent camera_intent = getIntent();
        barcode = camera_intent.getStringExtra("barcode");

        ClientThread thread = new ClientThread();
        thread.start();

        takePicture();


    }

    //사진찍기
    public void takePicture(){

        Toast toast = Toast.makeText(this, "유통기한을 촬영해주세요", Toast.LENGTH_LONG);
        toast.show();

        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(imageTakeIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CODE);
        }
    }



    //결과값 가져오기
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            arr = bitmapToByte(imageBitmap);
            //System.out.println(str1.length());
            //System.out.println(str1);


            ClientThread thread = new ClientThread();
            thread.start();

            Intent intent = new Intent(this, InfoActivity.class);

            customProgressDialog.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
               @Override
               public void run() {
                   intent.putExtra("p_name", p_name);
                   intent.putExtra("p_date", p_date);
                   startActivity(intent);
               }
            }, 1000);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static byte[] bitmapToByte(Bitmap bitmap){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageBytes = stream.toByteArray();
        System.out.println(imageBytes);

        return imageBytes;
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

                if(barcode != null){
                    str1 = "abc";
                    dos.writeUTF(str1+barcode);
                    dos.flush();
                    barcode = null;
                    Log.w("ClientThread", "서버로 보냄");

                    p_name = dis.readUTF();
                    System.out.println("[데이터 받기 성공]: " + p_name);
                }
                else{
                    dos.writeUTF(Integer.toString(arr.length));
                    dos.flush();

                    dos.write(arr);
                    dos.flush();

                    System.out.println(arr.toString());

                    Log.w("ClientThread", "서버로 보냄");
                    p_date = dis.readUTF();
                    System.out.println("[데이터 받기 성공]: " + p_date);

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