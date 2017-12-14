package com.moez.QKSMS.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.moez.QKSMS.R;
import com.moez.QKSMS.common.google.PrefManager;
import com.moez.QKSMS.helpers.InputValidation;
import com.moez.QKSMS.sql.DatabaseHelper;
import com.moez.QKSMS.ui.MainActivity;
import com.moez.QKSMS.ui.view.QKEditText;
import com.moez.QKSMS.ui.view.QKTextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int LOGIN_REQUEST_CODE = 31411;

    public static boolean isLogin;

    private final AppCompatActivity activity = LoginActivity.this;

    private QKTextView tvPasswd;

    private QKEditText etPasswd;

    private Button btnLogin;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {

        tvPasswd = (QKTextView) findViewById(R.id.tv_passwd);

        etPasswd = (QKEditText) findViewById(R.id.et_passwd);

        btnLogin = (Button) findViewById(R.id.btn_login);

    }

    private void initListeners() {
        btnLogin.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                verifyFromSQLite();
                finish();
                break;
        }
    }

    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(etPasswd, tvPasswd, "Enter Valid Password")) {
            return;
        }

        if (databaseHelper.checkUser(etPasswd.getText().toString().trim())) {

            isLogin = true;
            emptyInputEditText();

        } else {
            // Snack Bar to show success message that record is wrong
            Toast.makeText(activity, "Wrong Password", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        etPasswd.setText(null);
    }
}
