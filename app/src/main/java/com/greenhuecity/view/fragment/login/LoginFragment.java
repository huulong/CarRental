package com.greenhuecity.view.fragment.login;

import android.app.AlertDialog;
import android.content.Intent;
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

import com.google.android.material.textfield.TextInputEditText;
import com.greenhuecity.view.activity.MainActivity;
import com.greenhuecity.R;
import com.greenhuecity.data.contract.LoginContract;
import com.greenhuecity.data.presenter.LoginPresenter;
import com.greenhuecity.view.activity.LoginActivity;

public class LoginFragment extends Fragment implements LoginContract.IView {
    View view;
    TextInputEditText edtUser, edtPassword;
    Button btnLogin;
    TextView tvRegister;
    ImageView igBack;
    LoginActivity loginActivity;
    LoginPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        initGUI();
        mPresenter = new LoginPresenter(this,loginActivity);
        events();
        return view;
    }

    private void initGUI() {
        edtUser = view.findViewById(R.id.textUser);
        edtPassword = view.findViewById(R.id.text_Pass);
        btnLogin = view.findViewById(R.id.buttonLogin);
        tvRegister = view.findViewById(R.id.text_Register);
        igBack = view.findViewById(R.id.header_back);
        loginActivity = (LoginActivity) getActivity();
    }

    private void events() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uses = edtUser.getText().toString();
                String password = edtPassword.getText().toString();
                mPresenter.login(uses, password, requireContext());
            }
        });
        igBack.setOnClickListener(view -> startActivity(new Intent(requireContext(),MainActivity.class)));
        tvRegister.setOnClickListener(view -> loginActivity.viewPager2.setCurrentItem(1));

    }


    @Override
    public void showSuccessMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Đăng nhập thành công");
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(requireContext(), MainActivity.class));
            }
        }, 2000);
    }

    @Override
    public void showErrorMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Đăng nhập thất bại!");
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 2000);
    }


}
