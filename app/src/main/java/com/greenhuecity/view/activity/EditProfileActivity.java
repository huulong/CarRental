package com.greenhuecity.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.greenhuecity.R;
import com.greenhuecity.data.contract.EditProfileContract;

import com.greenhuecity.data.model.Users;
import com.greenhuecity.data.presenter.EditProfilePresenter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity implements EditProfileContract.IView {
    Button btnGetImageFromGallery, btnSave, btnCancel;

    ImageView showSelectedImage, imgBack;

    EditText edtName, edtEmail, edtPhone, edtAge, edtCCCD, edtAddress;
    Bitmap FixBitmap;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArray;
    String convertImage;
    String userName, phone, email, cccd, age, address;
    int user_id;
    private int GALLERY = 1, CAMERA = 2;
    EditProfilePresenter mPresenter;
    AlertDialog.Builder selectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initGUI();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        5);
            }
        }
        selectDialog = new AlertDialog.Builder(this);
        byteArrayOutputStream = new ByteArrayOutputStream();

        mPresenter = new EditProfilePresenter(this, this);
        mPresenter.getDataProfileFromShared();
        buttonClickEvent();

    }

    private void buttonClickEvent() {
        btnGetImageFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = edtName.getText().toString();
                phone = edtPhone.getText().toString();
                email = edtEmail.getText().toString();
                age = edtAge.getText().toString();
                address = edtAddress.getText().toString();
                cccd = edtCCCD.getText().toString();
                if (FixBitmap != null && FixBitmap.getWidth() > 0 && FixBitmap.getHeight() > 0) {
                    FixBitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    convertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                }else {
                    convertImage = "";
                }
                mPresenter.updateProfileInformation(user_id, convertImage, userName, phone, email, address, age, cccd);
            }

        });
        btnCancel.setOnClickListener(view -> onBackPressed());

    }


    private void initGUI() {
        btnGetImageFromGallery = findViewById(R.id.button_choosephoto);
        btnSave = findViewById(R.id.button_save);
        btnCancel = findViewById(R.id.button_cancel);

        showSelectedImage = findViewById(R.id.imgview_imgUser);
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(view -> onBackPressed());

        edtName = findViewById(R.id.edittext_UserName);
        edtAge = findViewById(R.id.edittext_age);
        edtAddress = findViewById(R.id.edittext_Addpress);
        edtEmail = findViewById(R.id.edittext_email);
        edtPhone = findViewById(R.id.edittext_phone);
        edtCCCD = findViewById(R.id.edittext_CCCD);
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Photo Gallery",
                "Camera"};
        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        choosePhotoFromGallary();
                        break;
                    case 1:
                        takePhotoFromCamera();
                        break;
                }
            }
        });
        pictureDialog.show();

    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    FixBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    showSelectedImage.setImageBitmap(FixBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            FixBitmap = (Bitmap) data.getExtras().get("data");
            showSelectedImage.setImageBitmap(FixBitmap);
        }
    }

    /////////
    @Override
    public void setDataProfile(Users users) {
        if (users != null) {
            user_id = users.getId();
            if (users.getFullname() != null) edtName.setText(users.getFullname());
            if (users.getAddress() != null) edtAddress.setText(users.getAddress());
            if (users.getEmail() != null) edtEmail.setText(users.getEmail());
            if (users.getPhone() != null) edtPhone.setText(users.getPhone());
            if (users.getCccd() != null) edtCCCD.setText(users.getCccd());
            if (users.getAge() != 0) edtAge.setText(users.getAge() + "");
            if (users.getPhoto() != null) {
                Glide.with(this).load(users.getPhoto()).into(showSelectedImage);
            }
        }
    }

    @Override
    public void updateSuccess() {
        onBackPressed();
    }


    @Override
    public void updateFailed(String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lỗi!");
        builder.setMessage(mess);
        AlertDialog dialog = builder.create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 3000);
    }

    @Override
    public void reUpdateSharedUser(Users users) {
        Gson gson = new Gson();
        String user = gson.toJson(users);
        SharedPreferences.Editor editor = getSharedPreferences("Success", MODE_PRIVATE).edit();
        editor.remove("users");
        editor.putString("users", user);
        editor.apply();
    }
}