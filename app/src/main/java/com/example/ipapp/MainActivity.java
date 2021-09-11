package com.example.ipapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private TextView editText1;
    private TextView editText2;
    private TextView editText3;
    private TextView editText4;
    private TextView txtIp;
    private Button btnPing;
    private Button btnSearchHosts;
    private InetAddress inetAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1= findViewById(R.id.editText1);
        editText2= findViewById(R.id.editText2);
        editText3= findViewById(R.id.editText3);
        editText4= findViewById(R.id.editText4);
        txtIp= findViewById(R.id.txtIp);
        btnPing= findViewById(R.id.btnPing);
        btnSearchHosts= findViewById(R.id.btnSearchHosts);


        new Thread(
                ()->{
                    try {
                        inetAddress=InetAddress.getLocalHost();
                        String myIp= inetAddress.getHostAddress();
                        runOnUiThread(
                                ()->{
                                    txtIp.setText(myIp);
                                }
                        );
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
        ).start();






        btnPing.setOnClickListener(
                (v)->{
                    int part1= Integer.parseInt(editText1.getText().toString());
                    int part2= Integer.parseInt(editText2.getText().toString());
                    int part3= Integer.parseInt(editText3.getText().toString());
                    int part4= Integer.parseInt(editText4.getText().toString());

                    if(validIp(part1)&&validIp(part2)&&validIp(part3)&&validIp(part4)){

                        String ip= part1+"."+part2+"."+part3+"."+part4;
                        ping(ip);


                        //Toast.makeText(this, "Valid Ip", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "Not valid ip", Toast.LENGTH_SHORT).show();
                    }
                }
        );


        btnSearchHosts.setOnClickListener(
                (v)->{
                    new Thread(()->{
                        try {
                            String result="";
                            for(int i=0; i<256; i++) {

                                if(i!=10) {
                                    InetAddress temp;
                                    temp= InetAddress.getByName("192.168.0."+i);
                                    boolean reach=temp.isReachable(500);
                                    if(reach) {

                                        result+= "192.168.0."+i+"\n";

                                    }

                                }

                            }
                            Intent i= new Intent(this, ResultsActivity.class);
                            i.putExtra("result",result);

                            startActivity(i);

                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();

                }
        );

    }

    private  void ping(String ip) {

        new Thread(()->{
            String result="";
            InetAddress temp;
            try {
                temp= InetAddress.getByName(ip);
                for (int i=0; i<5; i++){
                    boolean t=temp.isReachable(500);
                    if(t){
                        result+= "Recibido\n";
                    }else{
                        result+= "Perdido\n";
                    }
                }
                Intent i= new Intent(this, ResultsActivity.class);
                i.putExtra("result",result);
                editText1.setText("");
                editText2.setText("");
                editText3.setText("");
                editText4.setText("");
                startActivity(i);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();


    }

    public boolean validIp(int ip){

        if(ip<256 && ip>-1){
            return true;
        }else{
            return false;
        }
    }
}