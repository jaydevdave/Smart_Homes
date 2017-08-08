package com.example.jayde.carpaydium;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {

    String topic1 = "toandroidapp";
    String topic2 = "temp";
    String topic3 = "hum";
    String topic4 = "reedswitch";
    String topic5 = "toesp8266";


    Button r1, r2, r3, r4, r5, w1, w2, w3, d1;
    TextView rs, temperature, humidity;
    int flag1 = 0; //room1
    int flag2 = 0; //room2
    int flag3 = 0; //room3
    int flag4 = 0; //room4
    int flag5 = 0; //room5
    int flag6 = 0; //wash1
    int flag7 = 0; //wash2
    int flag8 = 0; //wash3
    int flag9 = 0; //entry



    MqttAndroidClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        rs = (TextView)findViewById(R.id.reedswitch);
        temperature = (TextView)findViewById(R.id.temp);
        humidity = (TextView)findViewById(R.id.hum);

        r1 = (Button) findViewById(R.id.room1);
        r2 = (Button) findViewById(R.id.room2);
        r3 = (Button) findViewById(R.id.room3);
        r4 = (Button) findViewById(R.id.room4);
        r5 = (Button) findViewById(R.id.room5);
        w1 = (Button) findViewById(R.id.wash1);
        w2 = (Button) findViewById(R.id.wash2);
        w3 = (Button) findViewById(R.id.wash3);
        d1 = (Button) findViewById(R.id.door);

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://54.172.186.236:1883", clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MainActivity.this,"Connected Successfully",Toast.LENGTH_LONG).show();
                    setsubscription();

                    r1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(flag1 == 0)
                            {
                                room11();
                            }
                            else if(flag1 == 1)
                            {
                                room10();
                            }
                                //method to publish
                        }
                    });

                    r2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(flag2 == 0)
                            {
                                room21();
                            }
                            else if(flag2 == 1)
                            {
                                room20();
                            }
                            //method to publish
                        }
                    });

                    r3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(flag3 == 0)
                            {
                                room31();
                            }
                            else if(flag3 == 1)
                            {
                                room30();
                            }
                            //method to publish
                        }
                    });

                    r4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(flag4 == 0)
                            {
                                room41();
                            }
                            else if(flag4 == 1)
                            {
                                room40();
                            }
                            //method to publish
                        }
                    });

                    r5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(flag5 == 0)
                            {
                                room51();
                            }
                            else if(flag5 == 1)
                            {
                                room50();
                            }
                            //method to publish
                        }
                    });

                    w1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(flag6 == 0)
                            {
                                wash11();
                            }
                            else if(flag6 == 1)
                            {
                                wash10();
                            }
                            //method to publish
                        }
                    });

                    w2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(flag7 == 0)
                            {
                                wash21();
                            }
                            else if(flag7 == 1)
                            {
                                wash20();
                            }
                            //method to publish
                        }
                    });

                    w3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(flag8 == 0)
                            {
                                wash31();
                            }
                            else if(flag8 == 1)
                            {
                                wash30();
                            }
                            //method to publish
                        }
                    });

                    d1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(flag9 == 0)
                            {
                                door11();
                            }
                            else if(flag9 == 1)
                            {
                                door10();
                            }
                            //method to publish
                        }
                    });

                }




                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MainActivity.this,"Connection Failure",Toast.LENGTH_LONG).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception{
                String msg = mqttMessage.toString();
                if(s.compareTo(topic2)==0){
                    temperature.setText(new String(mqttMessage.getPayload()));
                }
                else if(s.compareTo(topic3)==0){
                    humidity.setText(new String(mqttMessage.getPayload()));
                }
                else if(msg.compareTo("RS11")==0 && s.compareTo(topic4)==0){
                    rs.setText("Alert! Intruder opened the door!");
                }
                else if(msg.compareTo("RS10")==0 && s.compareTo(topic4)==0){
                    rs.setText("Alert! Intruder closed the door!");
                }
                else if(msg.compareTo("roomp11")==0 && s.compareTo(topic1)==0 && flag1==0){
                    r1.setBackgroundColor(Color.GREEN);
                    flag1=1;
                }
                else if(msg.compareTo("roomp10")==0 && s.compareTo(topic1)==0 && flag1==1){
                    r1.setBackgroundColor(Color.RED);
                    flag1=0;
                }
                else if(msg.compareTo("roomp21")==0 && s.compareTo(topic1)==0 && flag2==0){
                    r2.setBackgroundColor(Color.GREEN);
                    flag2=1;
                }
                else if(msg.compareTo("roomp20")==0 && s.compareTo(topic1)==0 && flag2==1){
                    r2.setBackgroundColor(Color.RED);
                    flag2=0;
                }
                else if(msg.compareTo("roomp31")==0 && s.compareTo(topic1)==0 && flag3==0){
                    r3.setBackgroundColor(Color.GREEN);
                    flag3=1;
                }
                else if(msg.compareTo("roomp30")==0 && s.compareTo(topic1)==0 && flag3==1){
                    r3.setBackgroundColor(Color.RED);
                    flag3=0;
                }
                else if(msg.compareTo("roomp41")==0 && s.compareTo(topic1)==0 && flag4==0){
                    r4.setBackgroundColor(Color.GREEN);
                    flag4=1;
                }
                else if(msg.compareTo("roomp40")==0 && s.compareTo(topic1)==0 && flag4==1){
                    r4.setBackgroundColor(Color.RED);
                    flag4=0;
                }
                else if(msg.compareTo("roomp51")==0 && s.compareTo(topic1)==0 && flag5==0){
                    r5.setBackgroundColor(Color.GREEN);
                    flag5=1;
                }
                else if(msg.compareTo("roomp50")==0 && s.compareTo(topic1)==0 && flag5==1){
                    r5.setBackgroundColor(Color.RED);
                    flag5=0;
                }
                else if(msg.compareTo("washp11")==0 && s.compareTo(topic1)==0 && flag6==0){
                    w1.setBackgroundColor(Color.GREEN);
                    flag6=1;
                }
                else if(msg.compareTo("washp10")==0 && s.compareTo(topic1)==0 && flag6==1){
                    w1.setBackgroundColor(Color.RED);
                    flag6=0;
                }
                else if(msg.compareTo("washp21")==0 && s.compareTo(topic1)==0 && flag7==0){
                    w2.setBackgroundColor(Color.GREEN);
                    flag7=1;
                }
                else if(msg.compareTo("washp20")==0 && s.compareTo(topic1)==0 && flag7==1){
                    w2.setBackgroundColor(Color.RED);
                    flag7=0;
                }
                else if(msg.compareTo("washp31")==0 && s.compareTo(topic1)==0 && flag8==0){
                    w3.setBackgroundColor(Color.GREEN);
                    flag8=1;
                }
                else if(msg.compareTo("washp30")==0 && s.compareTo(topic1)==0 && flag8==1){
                    w3.setBackgroundColor(Color.RED);
                    flag8=0;
                }
                else if(msg.compareTo("entryp11")==0 && s.compareTo(topic1)==0 && flag9==0){
                    d1.setBackgroundColor(Color.GREEN);
                    flag9=1;
                }
                else if(msg.compareTo("entryp10")==0 && s.compareTo(topic1)==0 && flag9==1){
                    d1.setBackgroundColor(Color.RED);
                    flag9=0;
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    public void room11() {
        String topic = topic5;
        String message = "room11";
        byte[] encodedPayload = new byte[0];

        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void room10() {
        String topic = topic5;
        String message = "room10";
        byte[] encodedPayload = new byte[0];

        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void room21() {
        String topic = topic5;
        String message = "room21";
        byte[] encodedPayload = new byte[0];

        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void room20() {
        String topic = topic5;
        String message = "room20";
        byte[] encodedPayload = new byte[0];

        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void room31() {
        String topic = topic5;
        String message = "room31";
        byte[] encodedPayload = new byte[0];

        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void room30() {
        String topic = topic5;
        String message = "room30";
        byte[] encodedPayload = new byte[0];

        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void room41() {
        String topic = topic5;
        String message = "room41";
        byte[] encodedPayload = new byte[0];

        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void room40() {
        String topic = topic5;
        String message = "room40";
        byte[] encodedPayload = new byte[0];

        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void room51() {
        String topic = topic5;
        String message = "room51";
        byte[] encodedPayload = new byte[0];

        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void room50() {
        String topic = topic5;
        String message = "room50";
        byte[] encodedPayload = new byte[0];

        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void wash11() {
        String topic = topic5;
        String message = "wash11";
        byte[] encodedPayload = new byte[0];

        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void wash10() {
        String topic = topic5;
        String message = "wash10";
        byte[] encodedPayload = new byte[0];

        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void wash21() {
        String topic = topic5;
        String message = "wash21";
        byte[] encodedPayload = new byte[0];

        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void wash20() {
        String topic = topic5;
        String message = "wash20";
        byte[] encodedPayload = new byte[0];

        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void wash31() {
        String topic = topic5;
        String message = "wash31";
        byte[] encodedPayload = new byte[0];

        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void wash30() {
        String topic = topic5;
        String message = "wash30";
        byte[] encodedPayload = new byte[0];

        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void door11() {
        String topic = topic5;
        String message = "entry11";
        byte[] encodedPayload = new byte[0];

        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void door10() {
        String topic = topic5;
        String message = "entry10";
        byte[] encodedPayload = new byte[0];

        try {
            client.publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void setsubscription(){
        try{
            client.subscribe(topic1,0);
            client.subscribe(topic2,0);
            client.subscribe(topic3,0);
            client.subscribe(topic4,0);
        } catch (MqttException e){
            e.printStackTrace();
        }
    }
}
