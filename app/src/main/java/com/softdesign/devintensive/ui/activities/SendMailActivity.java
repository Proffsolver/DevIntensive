package com.softdesign.devintensive.ui.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;


import com.softdesign.devintensive.R;

public class SendMailActivity extends BaseActivity {
    ImageView mSend;
    EditText mMail, mSubj, mMailText;
    protected Handler mHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);

        // Наши поля и кнопка
        mSend = (ImageView) findViewById(R.id.mailsend);
        mMail = (EditText) findViewById(R.id.mailaddr_et);
        mSubj = (EditText) findViewById(R.id.mailsubj_et);
        mMailText = (EditText) findViewById(R.id.mailtext_et);

        mSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                        new String[] {mMail.getText().toString() });
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                        mSubj.getText().toString());
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        mMailText.getText().toString());
                SendMailActivity.this.onBackPressed();
//                 progressBarSendMail();
//                SendMailActivity.this.startActivity(Intent.createChooser(emailIntent,"Отправка письма..."));
            }
        });
    }
}

