package com.softdesign.devintensive.ui.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.utils.ConstantManager;

public class BaseActivity extends AppCompatActivity{
    static final String TAG = ConstantManager.TAG_PREFIX+"BaseActivity";
    protected ProgressDialog mProgressDialog;
    protected Handler mHandler;

    public void showProgress(){
        if (mProgressDialog==null) {
            mProgressDialog = new ProgressDialog(this, R.style.custom_dialog);
            mProgressDialog.setCancelable(false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.setContentView(R.layout.progress_splash);

        } else {
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_splash);
        }
    }

    public void hideProgress(){
        if (mProgressDialog != null){
            if(mProgressDialog.isShowing()){
                mProgressDialog.hide();
            }
        }

    }
    public void showError(String message, Exception error){
        showToast(message);
        Log.e(TAG, String.valueOf(error));
    }
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void runWithDelay(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {hideProgress();}}, 5000);
    }

    public void progressBarSendMail(){
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Отпавка mail...");
        mProgressDialog.setMessage("Ваше письмо отправляется подождите до окончания процесса.");
        // меняем стиль на индикатор
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // устанавливаем максимум
        mProgressDialog.setMax(1000);
        // включаем анимацию ожидания
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                // выключаем анимацию ожидания
                mProgressDialog.setIndeterminate(false);
                if (mProgressDialog.getProgress() < mProgressDialog.getMax()) {
                    // увеличиваем значения индикаторов
                    mProgressDialog.incrementProgressBy(50);
                    mProgressDialog.incrementSecondaryProgressBy(75);
                    mHandler.sendEmptyMessageDelayed(0, 100);
                } else { mProgressDialog.dismiss(); }
            }
        };
        mHandler.sendEmptyMessageDelayed(0, 2000);
    }
}
