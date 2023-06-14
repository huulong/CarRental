package com.greenhuecity.view.fragment.login;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.greenhuecity.R;
import com.greenhuecity.data.contract.RegisterContract;
import com.greenhuecity.data.model.Users;
import com.greenhuecity.data.presenter.RegisterPresenter;
import com.greenhuecity.view.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class RegisterFragment extends Fragment implements RegisterContract.IView {
    View view;
    EditText edtFullName, edtPassword, edtEmail, edtPhone;
    Button btnRegister;
    ImageView igBack;
    TextView tvLogin;
    RegisterPresenter mPresenter;
    LoginActivity loginActivity;
    List<Users> usersList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register,container,false);
        usersList = new ArrayList<>();
        initGUI();
        mPresenter = new RegisterPresenter(this,loginActivity);
        mPresenter.getUsersList();
        events();
        return view;
    }

    private void events() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = edtFullName.getText().toString();
                String email = edtEmail.getText().toString();
                String phone = edtPhone.getText().toString();
                String password  = edtPassword.getText().toString();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.register(fullName,password,email,phone,usersList);
                    }
                },500);
            }
        });
        igBack.setOnClickListener(view->loginActivity.viewPager2.setCurrentItem(0));
        tvLogin.setOnClickListener(view1->loginActivity.viewPager2.setCurrentItem(0));
    }


    private void initGUI(){
        edtEmail = view.findViewById(R.id.textEmail);
        edtFullName = view.findViewById(R.id.textUser);
        edtPassword = view.findViewById(R.id.text_Pass);
        edtPhone = view.findViewById(R.id.textPhone);
        btnRegister = view.findViewById(R.id.buttonRegister);
        igBack = view.findViewById(R.id.header_back);
        tvLogin = view.findViewById(R.id.text_Login);
        loginActivity = (LoginActivity) getActivity();
    }
    @Override
    public void showSuccessMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Đăng ký thành công");
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                loginActivity.viewPager2.setCurrentItem(0);
            }
        }, 3000);
    }

    @Override
    public void showErrorMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Failed!");
        builder.setMessage(message);
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
    public void setUsersList(List<Users> list) {
        usersList = list;
    }
}
