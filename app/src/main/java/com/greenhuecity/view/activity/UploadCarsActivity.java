package com.greenhuecity.view.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.greenhuecity.R;
import com.greenhuecity.data.contract.UploadCarContract;
import com.greenhuecity.data.model.Brands;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.data.model.Distributors;
import com.greenhuecity.data.model.Powers;
import com.greenhuecity.data.presenter.UploadCarPresenter;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UploadCarsActivity extends AppCompatActivity implements UploadCarContract.IView {

    Button GetImageFromGalleryButton, UploadImageOnServerButton;

    ImageView showSelectedImage, imgBack;

    EditText edtCarName, edtPrice, edtPlates, edtTopSpeed, edtHorsePower, edtMileage, edtDescription;
    TextView tvStatus, tvPower, tvBrand, tvDistributors, tvFromTime, tvEndTime;
    CheckBox checkBox;

    Bitmap FixBitmap;

    ByteArrayOutputStream byteArrayOutputStream;

    byte[] byteArray;

    String convertImage;

    String nameCar, licensePlates, description, status, fromTime, endTime, rndNamePhoto, price, topSpeed, horsePower, mileage;
    int power_id =0;
    int brand_id = 0;
    int distributor_id=0;
    int user_id = 0;

    private int GALLERY = 1, CAMERA = 2;
    UploadCarPresenter mPresenter;
    AlertDialog.Builder selectDialog;
    Calendar calendar;
    Date startDate = null;
    Date endDate = null;
    SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cars);
        initGUI();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        5);
            }
        }

        dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        calendar = Calendar.getInstance();

        selectDialog = new AlertDialog.Builder(this);
        mPresenter = new UploadCarPresenter(this, this);
        mPresenter.boldText();
        mPresenter.getDataList();
        user_id = mPresenter.getUsersId();
        rndNamePhoto = mPresenter.generateRandomString();

        byteArrayOutputStream = new ByteArrayOutputStream();

        GetImageFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        UploadImageOnServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    getDataUpload();
                    mPresenter.uploadCar(nameCar, price, description, licensePlates, status, fromTime, endTime, "Pending", power_id, brand_id, user_id, distributor_id, topSpeed, horsePower, mileage, convertImage, rndNamePhoto);
                } else {
                    Toast.makeText(UploadCarsActivity.this, "Chưa chấp nhận điều khoản sử dụng", Toast.LENGTH_SHORT).show();
                }
            }
        });
        setEventSelectDay();
    }

    private void getDataUpload() {
        nameCar = edtCarName.getText().toString();
        licensePlates = edtPlates.getText().toString();
        description = edtDescription.getText().toString();
        status = tvStatus.getText().toString();
        fromTime = tvFromTime.getText().toString();
        endTime = tvEndTime.getText().toString();
        price = edtPrice.getText().toString();
        topSpeed = edtTopSpeed.getText().toString();
        horsePower = edtHorsePower.getText().toString();
        mileage = edtMileage.getText().toString();

        if (FixBitmap != null && FixBitmap.getWidth() > 0 && FixBitmap.getHeight() > 0) {
            FixBitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
            byteArray = byteArrayOutputStream.toByteArray();
            convertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }

    }

    private void initGUI() {
        GetImageFromGalleryButton = findViewById(R.id.btn_imgcar);
        UploadImageOnServerButton = findViewById(R.id.button_upload_cars);
        showSelectedImage = findViewById(R.id.img_addcar);
        edtCarName = findViewById(R.id.edit_namecar);
        edtPrice = findViewById(R.id.edit_price);
        edtPlates = findViewById(R.id.edit_plates);
        edtTopSpeed = findViewById(R.id.edit_topSpeed);
        edtHorsePower = findViewById(R.id.edit_horse);
        edtMileage = findViewById(R.id.edit_mileage);
        edtDescription = findViewById(R.id.edit_discription);
        tvBrand = findViewById(R.id.edit_brandName);
        tvStatus = findViewById(R.id.edit_status);
        tvPower = findViewById(R.id.edit_power);
        tvDistributors = findViewById(R.id.edit_Distributor);
        tvFromTime = findViewById(R.id.edit_fromTime);
        tvEndTime = findViewById(R.id.edit_endTime);
        checkBox = findViewById(R.id.checkBox);
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(view->onBackPressed());
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

    @Override
    public void setDataDistributorList(List<Distributors> mList) {
        tvDistributors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.setTitle("Chọn nhà phân phối");
                List<String> distributorItems = new ArrayList<>();
                Map<String, Integer> distributorIdMap = new HashMap<>();

                for (Distributors distributors : mList) {
                    distributorItems.add(distributors.getName());
                    distributorIdMap.put(distributors.getName(), distributors.getId());
                }
                selectDialog.setItems(distributorItems.toArray(new String[distributorItems.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvDistributors.setText(distributorItems.get(which));
                        distributor_id = distributorIdMap.get(distributorItems.get(which));
                    }
                });
                selectDialog.create().show();
            }
        });
    }

    @Override
    public void setDataBrandList(List<Brands> mList) {
        tvBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.setTitle("Chọn hãng xe");
                List<String> brandItems = new ArrayList<>();
                Map<String, Integer> brandIdMap = new HashMap<>();
                for (Brands brands : mList) {
                    brandItems.add(brands.getBrand_name());
                    brandIdMap.put(brands.getBrand_name(), brands.getId());
                }
                selectDialog.setItems(brandItems.toArray(new String[brandItems.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvBrand.setText(brandItems.get(which));
                        brand_id = brandIdMap.get(brandItems.get(which));
                    }
                });
                selectDialog.create().show();
            }
        });
    }

    @Override
    public void setDataPowerList(List<Powers> mList) {
        tvPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.setTitle("Chọn phân khối xe");
                List<String> powerItems = new ArrayList<>();
                Map<String, Integer> powerIdMap = new HashMap<>(); // Tạo một bản đồ để lưu trữ

                for (Powers powers : mList) {
                    powerItems.add(powers.getPower_name() + " (" + powers.getValue() + ")");
                    powerIdMap.put(powers.getPower_name() + " (" + powers.getValue() + ")", powers.getId()); // Thêm  vào bản đồ
                }
                selectDialog.setItems(powerItems.toArray(new String[powerItems.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvPower.setText(powerItems.get(which));
                        power_id = powerIdMap.get(powerItems.get(which)); // Lấy  từ bản đồ
                    }
                });
                selectDialog.create().show();
            }
        });
    }

    @Override
    public void setDataStatusList(List<String> statusItems) {
        tvStatus.setText(statusItems.get(0));
        tvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDialog.setTitle("Chọn trạng thái cho thuê");
                selectDialog.setItems(statusItems.toArray(new String[statusItems.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvStatus.setText(statusItems.get(which));
                    }
                });
                selectDialog.create().show();
            }
        });
    }

    @Override
    public void notifiError(String mess) {
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
        }, 2000);
    }

    @Override
    public void notifiSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Cho thuê thành công.Chờ nhà phân phối xác nhận");
        AlertDialog dialog = builder.create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                onBackPressed();
            }
        }, 2000);
    }

    @Override
    public void notifiUploadFailed(String mess) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lỗi");
        builder.setMessage(mess);
        AlertDialog dialog = builder.create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 2000);
    }

    @Override
    public void setBoldTextCheckbox(SpannableString text) {
        checkBox.setText(text);
    }

    public void setEventSelectDay() {
        Date currentDate = calendar.getTime();
        calendar.add(Calendar.DATE, 2);
        Date nextDate = calendar.getTime();
        tvFromTime.setText(dateFormat.format(currentDate));
        tvEndTime.setText(dateFormat.format(nextDate));
        tvFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startDate = dateFormat.parse(tvFromTime.getText().toString());
                    endDate = dateFormat.parse(tvEndTime.getText().toString());
                    mPresenter.getDayTime(calendar, tvFromTime, startDate, endDate, 0);

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startDate = dateFormat.parse(tvFromTime.getText().toString());
                    endDate = dateFormat.parse(tvEndTime.getText().toString());
                    mPresenter.getDayTime(calendar, tvEndTime, startDate, endDate, 1);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
