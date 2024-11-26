package com.example.finalvalue.Customer.ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalvalue.Customer.SendEmail.GMailSender;
import com.example.finalvalue.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ResetCodeActivity extends AppCompatActivity {
    TextView email;
    EditText code_reset;
    TextView btn_reset_code;
    Button confirm;
    Random random = new Random();
    int code, idCus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_code);
        email = findViewById(R.id.get_email_reset);
        code_reset = findViewById(R.id.get_code_reset);
        btn_reset_code = findViewById(R.id.btn_reset_code);
        confirm = findViewById(R.id.confirm_code_reset);
        code = random.nextInt(999999 - 100000 + 1) + 100000; // random từ 100000 tới 999999
        getAndSetIntent();
        sendEmail("dklibrarysender@gmail.com","setraxwaatuevhso",email.getText().toString().trim(),"Reset Password","Vui lòng không chia sẻ code này cho bất kỳ ai<br>Reset Code: "+"<b>"+code+"</b>");
        btn_reset_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = random.nextInt(999999 - 100000 + 1) + 100000;
                sendEmail("dklibrarysender@gmail.com","setraxwaatuevhso",email.getText().toString().trim(),"Reset Password","Vui lòng không chia sẻ code này cho bất kỳ ai<br>Reset Code: "+"<b>"+code+"</b>");
                btn_reset_code.setActivated(false);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.valueOf(code_reset.getText().toString().trim()) == code){
                    Intent intent = new Intent(ResetCodeActivity.this, ResetPasswordActivity.class);
                    intent.putExtra("id", idCus);
                    startActivity(intent);
                }
            }
        });
    }
    public void getAndSetIntent(){
        if(getIntent().hasExtra("email") && getIntent().hasExtra("id")){
            email.setText(getIntent().getStringExtra("email"));
            idCus = getIntent().getIntExtra("id", 0);
        }
        else Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
    }
    private void sendEmail(final String Sender,final String Password,final String Receiver,final String Title,final String Message)
    {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender(Sender,Password);
                    sender.sendMail(Title, Message, Sender, Receiver);
                    makeAlert();
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }

        }).start();
    }
    private void makeAlert(){
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(ResetCodeActivity.this, "Đã gửi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}