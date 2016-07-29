package com.softdesign.devintensive.data.network.req;

import android.net.Uri;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadFileReq {

    private MultipartBody.Part mBody;

    public MultipartBody.Part photoSend(Uri uri) {
        File file = new File(uri.getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        mBody = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);
        return mBody;
    }
}