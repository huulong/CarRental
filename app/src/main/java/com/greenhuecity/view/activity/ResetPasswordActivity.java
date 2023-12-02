package com.greenhuecity.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.greenhuecity.R;
import com.greenhuecity.data.contract.ResetPasswordContract;
import com.greenhuecity.data.presenter.ResetPasswordPresenter;
import com.greenhuecity.data.remote.ApiService;
import com.greenhuecity.data.remote.RetrofitClient;

public class ResetPasswordActivity extends AppCompatActivity implements ResetPasswordContract.View {
    private ResetPasswordContract.Presenter presenter;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpass);

        // Khởi tạo ApiService từ package com.greenhuecity.data.remote
        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Khởi tạo Presenter và chuyển ApiService vào
        presenter = new ResetPasswordPresenter(this, apiService);

        // Xử lý sự kiện khi người dùng bấm nút đặt lại mật khẩu
        Button resetButton = findViewById(R.id.reset_buttonResetPassword);
        resetButton.setOnClickListener(v -> {
            // Lấy dữ liệu từ EditTexts
            String email = ((EditText) findViewById(R.id.reset_textEmail)).getText().toString();
            String oldPassword = ((EditText) findViewById(R.id.reset_textOldPassword)).getText().toString();
            String newPassword = ((EditText) findViewById(R.id.reset_textNewPassword)).getText().toString();

            // Gọi phương thức resetPassword từ Presenter
            presenter.resetPassword(email, oldPassword, newPassword);
        });
    }

    @Override
    public void showResetSuccess(String message) {
        // Log khi đặt lại mật khẩu thành công
        Log.d("ResetPasswordActivity", "Reset password success: " + message);
    }

    @Override
    public void showResetError(String error) {
        // Log khi đặt lại mật khẩu thất bại
        Log.e("ResetPasswordActivity", "Reset password error: " + error);
    }


    // ... (các phương thức khác của Activity)
}
