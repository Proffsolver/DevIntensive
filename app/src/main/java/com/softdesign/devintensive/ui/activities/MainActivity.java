package com.softdesign.devintensive.ui.activities;

import com.softdesign.devintensive.R;

import android.app.Application;
import android.os.Bundle;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.ActivityCompatApi23;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.softdesign.devintensive.data.managers.DataManager;
import com.softdesign.devintensive.utils.ConstantManager;
import com.softdesign.devintensive.utils.DevintensiveApplication;
import com.softdesign.devintensive.utils.RoundAvatar;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = ConstantManager.TAG_PREFIX+"DEV MainActivity: ";

    private DataManager mDataManager;
    private int mCurrentEditMode=0;
    private int mDrawerStart=0;

    private ImageView mCallImg, mMailImg, mGitImg, mVkImg, mProfileImage, mHeaderImage;
    private CoordinatorLayout mCoordinatorLayout;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private Toolbar mToolbar;
    private DrawerLayout mNavigationDrawer;
    private FloatingActionButton mFab;
    private EditText mUserPhone, mUserMail, mUserVk, mUserGit, mUserBio;
    private NavigationView mNavigationView;
    private Bitmap mBitmap;
    private LinearLayout mStatLinear;

    private AppBarLayout mAppBarLayout;
    private AppBarLayout.LayoutParams mAppBarParams = null;
    private File mPhotoFile = null;
    private Uri mSelectedImage = null;

    private RelativeLayout mProfilePlecaholder;

    private List<EditText> mUserInfoViews;

    private TextView mUserValuesRating, mUserValuesCodeLines, mUserValuesProjects, mHeaderName, mHeaderEmail;
    private List<TextView> mUserValueViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate()");
        // Инициализация объектов
        mDataManager = DataManager.getInstance();
        mCallImg = (ImageView)findViewById(R.id.call_img);
        mMailImg = (ImageView)findViewById(R.id.mail_img);
        mGitImg = (ImageView)findViewById(R.id.git_img);
        mVkImg = (ImageView)findViewById(R.id.vk_img);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_container);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationDrawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mProfileImage = (ImageView) findViewById(R.id.user_photo_img);
        mProfilePlecaholder = (RelativeLayout) findViewById(R.id.profile_placeholder);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        mUserPhone = (EditText) findViewById(R.id.phone_et);
        mUserMail = (EditText) findViewById(R.id.email_et);
        mUserVk = (EditText) findViewById(R.id.vkprof_et);
        mUserGit = (EditText) findViewById(R.id.gitrepo_et);
        mUserBio = (EditText) findViewById(R.id.aboutme_et);
        mStatLinear = (LinearLayout) findViewById(R.id.stat_li);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mHeaderImage = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.image_header);
        mHeaderName = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.user_name_txt);
        mHeaderEmail = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.user_email_txt);

        mUserValuesRating = (TextView) findViewById(R.id.user_info_rait_txt);
        mUserValuesCodeLines = (TextView) findViewById(R.id.user_info_code_lines_txt);
        mUserValuesProjects = (TextView) findViewById(R.id.user_info_projects_txt);



        // Загрузка данных профиля
        mUserInfoViews = new ArrayList<>();
        mUserInfoViews.add(mUserPhone);
        mUserInfoViews.add(mUserMail);
        mUserInfoViews.add(mUserVk);
   //     mUserInfoViews.add(mUserGit);
        mUserInfoViews.add(mUserBio);

        mUserValueViews = new ArrayList<>();
        mUserValueViews.add(mUserValuesRating);
        mUserValueViews.add(mUserValuesCodeLines);
        mUserValueViews.add(mUserValuesProjects);

        // Установка обработчиков на управляющие компоненты
        mFab.setOnClickListener(this);
        mProfilePlecaholder.setOnClickListener(this);
        mCallImg.setOnClickListener(this);
        mMailImg.setOnClickListener(this);
        mVkImg.setOnClickListener(this);
        mGitImg.setOnClickListener(this);

        //setEditViewMask();

        setupToolbar(); // Установка тулбара
        setupDrawer();  // Установка дровера
        initUserFields();
        initUserInfoValue();
        Picasso.with(this)
                .load(mDataManager.getPreferencesManager().loadUserPhoto())
                .placeholder(R.drawable.user_photo)
                .into(mProfileImage);

        // Обработка аватарки
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user_avatar);
        mHeaderImage.setBackground(new BitmapDrawable(getResources(), RoundAvatar.getRoundedBitmap(mBitmap)));
        // Проверка первого запуска приложения
        if (savedInstanceState == null){
        }
        else {
            mCurrentEditMode = savedInstanceState.getInt(ConstantManager.EDIT_MODE_KEY, 0);
            changeEditMode(mCurrentEditMode);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Загрузка меню дровера
        if (item.getItemId() == android.R.id.home){
            mNavigationDrawer.openDrawer(GravityCompat.START);
            mDrawerStart = 1;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
        saveUserFields();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.call_img:
                showProgress();
                // Вызов абонента
                Intent mIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mUserPhone.getText().toString()));
                startActivity(mIntent);
                break;
            case R.id.mail_img:
                // Отправка мэйла
                Intent intent = new Intent(this, SendMailActivity.class);
                startActivity(intent);
                break;
            case R.id.vk_img:
                // Вызов url
                Intent vkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://"+ mUserVk.getText().toString()));
                startActivity(vkIntent);
                break;
            case R.id.git_img:
                // Вызов url
                Intent gitIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://"+ mUserGit.getText().toString()));
                startActivity(gitIntent);
                break;
            case R.id.fab:
                // Обработка перехода в режим редактирования
                if (mCurrentEditMode == 0){
                    changeEditMode(1);
                    mCurrentEditMode = 1;
                    mUserPhone.setFocusableInTouchMode(true);
                    mUserPhone.requestFocus();
                } else {
                    changeEditMode(0);
                    mCurrentEditMode = 0;
                    mUserPhone.setFocusable(false);
                }
                break;
            case R.id.profile_placeholder:
                showDialog(ConstantManager.LOAD_PROFILE_PHOTO);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        // Сохранение данных профиля
        super.onSaveInstanceState(outState);
        outState.putInt(ConstantManager.EDIT_MODE_KEY, mCurrentEditMode);
    }

    @Override
    public void onBackPressed() {
        // Обработка BackPress
        if (mDrawerStart == 1){
            mDrawerStart = 0;
            mNavigationDrawer.closeDrawer(GravityCompat.START);
        } else {super.onBackPressed();}
    }

    private void showSnackbar(String message){
        // Вывод snackbar
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    private void setupToolbar() {
        // Функция установки тулбара
        setSupportActionBar(mToolbar);

        mAppBarParams = (AppBarLayout.LayoutParams) mCollapsingToolbar.getLayoutParams();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void setupDrawer(){
        // Функция установки дровера
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                showSnackbar(item.getTitle().toString());
                item.setChecked(true);
                mNavigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // Обработка результатов Intent
        switch (requestCode){
            case ConstantManager.REQUEST_GALLERY_PIRCTURE:
                if (resultCode == RESULT_OK && data != null){
                    mSelectedImage = data.getData();
                    insertProfileImage(mSelectedImage);
                }
                break;
            case ConstantManager.REQUEST_CAMERA_PICTURE:
                if (resultCode == RESULT_OK && mPhotoFile != null){
                    mSelectedImage = Uri.fromFile(mPhotoFile);
                    insertProfileImage(mSelectedImage);
                }
        }
    }

    private void changeEditMode(int mode){
        // Обработка перехода в режим редактирования и обратно
        if (mode == 1){
            mFab.setImageResource(R.drawable.ic_done_white_24dp);
            for (EditText userValue : mUserInfoViews){
                userValue.setEnabled(true);
                userValue.setFocusable(true);
                userValue.setFocusableInTouchMode(true);
                showProfilePlaceholder();
                lockToolbar();
                mCollapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);
            }
        } else{
            mFab.setImageResource(R.drawable.ic_create_white_24dp);
            for (EditText userValue : mUserInfoViews){
                userValue.setEnabled(false);
                userValue.setFocusable(false);
                userValue.setFocusableInTouchMode(false);
                saveUserFields();
                mCollapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.white));
                unlockToolbar();
                hideProfilePlaceholder();
            }
        }
    }

    private void initUserFields(){
        // Загрузка данных пользователя
        List<String> userData = mDataManager.getPreferencesManager().loadUserProfileData();
        for (int i = 0; i < userData.size(); i++){
            mUserInfoViews.get(i).setText(userData.get(i));
        }
    }

    private void initUserNames(){
        // Загрузка данных пользователя
        String mUserNames = "";
        List<String> userNames = mDataManager.getPreferencesManager().loadUserProfileNames();
        for (int i = 0; i < userNames.size(); i++){
            mUserNames = mUserNames + userNames.get(i);
        }
        mHeaderName.setText(mUserNames);


    }

    private void saveUserFields(){
        // Сохранение данных пользователя
        List<String> userData = new ArrayList<>();
        for (EditText  userFieldView : mUserInfoViews) {
            userData.add(userFieldView.getText().toString());
        }
        mDataManager.getPreferencesManager().saveUserProfileData(userData);
    }

    private void initUserInfoValue(){
        List<String> userData = mDataManager.getPreferencesManager().loadUserProfileValues();
        for (int i = 0; i < userData.size(); i++){
            mUserValueViews.get(i).setText(userData.get(i));
        }

    }

    private void loadPhotoFromGallery(){
        // Загрузка фотографии из галереи
        Intent takeGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        takeGalleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(takeGalleryIntent, getString(R.string.user_profile_choose_message)), ConstantManager.REQUEST_GALLERY_PIRCTURE);
    }
    private void loadPhotoFromCamera(){
        // Загрузка фотки с камеры
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Intent takeCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            try {File mPhotoFile = createImageFile();}
            catch (IOException e){
                e.printStackTrace();}
            if (mPhotoFile != null) {
                takeCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
                startActivityForResult(takeCaptureIntent, ConstantManager.REQUEST_CAMERA_PICTURE);
            }}else{
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, ConstantManager.CAMERA_REQUEST_PERMISSION_CODE);
            Snackbar.make(mCoordinatorLayout, "Для корректной работы приложения необходимо дать требуемые разрешения",
                    Snackbar.LENGTH_LONG).setAction("Разрешить", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openApplicationSettings();
                }}).show();
        }}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission, @NonNull int[] grantResults){
        // Обработка предоставления прав
        if (requestCode == ConstantManager.CAMERA_REQUEST_PERMISSION_CODE && grantResults.length== 2){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED){

            }
        }
        if(grantResults[1]==PackageManager.PERMISSION_GRANTED){

        }
    }

    private void hideProfilePlaceholder(){
        //Скрыть фотографии профиля
        mProfilePlecaholder.setVisibility(View.GONE);
    }

    private void showProfilePlaceholder(){
        //Возврат фотографии на место
        mProfilePlecaholder.setVisibility(View.VISIBLE);
    }

    private void lockToolbar(){
        // Блокировка тулбара
        mAppBarLayout.setExpanded(true,true);
        mAppBarParams.setScrollFlags(0);
        mCollapsingToolbar.setLayoutParams(mAppBarParams);
    }

    private void unlockToolbar(){
        // Разблокировка тулбара
        mAppBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL| AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        mCollapsingToolbar.setLayoutParams(mAppBarParams);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // Обработка диалога работы с фотографией профиля
        switch (id){
            case ConstantManager.LOAD_PROFILE_PHOTO:
                String[] selectItems = {getString(R.string.user_profile_dialog_camera),getString(R.string.user_profile_dialog_gallery),getString(R.string.user_profile_dialog_cancel), };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.user_profile_dialog_title));
                builder.setItems(selectItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int choiceItem) {
                        switch (choiceItem) {
                            case 0:
                                loadPhotoFromGallery();
                                //       showSnackbar("Load from gallery");
                                break;
                            case 1:
                                loadPhotoFromCamera();
                                //        showSnackbar("Load from camera");
                                break;
                            case 2:
                                dialog.cancel();
                                //       showSnackbar("Cancel");
                                break;
                        }
                    }
                });
                return builder.create();
            default:
                return null;
        }
    }

    private File createImageFile() throws IOException{
        // Создание фотографии
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm").format (new Date());
        String imageFileName = "JPEG_"+timeStamp+"_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, image.getAbsolutePath());

        this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        return image;
    }


    private void insertProfileImage(Uri selectedImage) {
        // Замена текущей фотографии на снимок
        Picasso.with(this)
                .load(selectedImage)
                .into(mProfileImage);
        mDataManager.getPreferencesManager().saveUserPhoto(selectedImage);
    }

    private void openApplicationSettings(){
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
        startActivityForResult(appSettingsIntent, ConstantManager.PERMISSION_REQUEST_SETTINGS_CODE);
    }

}
