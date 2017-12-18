package com.moez.QKSMS.ui.changepassword;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.moez.QKSMS.R;
import com.moez.QKSMS.helpers.InputValidation;
import com.moez.QKSMS.model.User;
import com.moez.QKSMS.sql.DatabaseHelper;
import com.moez.QKSMS.ui.register.RegisterActivity;
import com.moez.QKSMS.ui.settings.SettingsFragment;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = ChangePasswordActivity.this;

    public static final int CHANGE_PASSWD_REQUEST_CODE = 31415;

    private TextView tvOldPasswd;
    private TextView tvPasswd;
    private TextView tvConfirmPasswd;

    private EditText etOldPasswd;
    private EditText etPasswd;
    private EditText etConfirmPasswd;

    private Button btnSubmit;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {
        tvOldPasswd = (TextView) findViewById(R.id.tv_old_passwd);
        tvPasswd = (TextView) findViewById(R.id.tv_passwd);
        tvConfirmPasswd = (TextView) findViewById(R.id.tv_confirm_passwd);

        etOldPasswd = (EditText) findViewById(R.id.et_old_passwd);
        etPasswd = (EditText) findViewById(R.id.et_passwd);
        etConfirmPasswd = (EditText) findViewById(R.id.et_confirm_passwd);

        btnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    private void initListeners() {
        btnSubmit.setOnClickListener(this);
    }

    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                updateDataToSql();
                break;

        }
    }

    private void updateDataToSql() {
        if (!inputValidation.isInputEditTextFilled(etOldPasswd, tvOldPasswd, "Enter Password")) {
            Toast.makeText(activity, "Password does not filled", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!inputValidation.isInputEditTextFilled(etPasswd, tvPasswd, "Enter Password")) {
            Toast.makeText(activity, "Password does not filled", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!inputValidation.isInputEditTextFilled(etConfirmPasswd, tvConfirmPasswd, "Enter Password")) {
            Toast.makeText(activity, "Password does not filled", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!inputValidation.isInputEditTextMatches(etPasswd, etConfirmPasswd,
                tvConfirmPasswd, "Password does not match")) {
            Toast.makeText(activity, "Password does not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (inputValidation.isInputEditTextMatches(etPasswd, etOldPasswd,
                tvConfirmPasswd, "Password must be different from Old Password")) {
            Toast.makeText(activity, "Password must be different from Old Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (databaseHelper.checkUser(etOldPasswd.getText().toString().trim())) {

            user.setUsername("hsms");
            user.setPassword(etPasswd.getText().toString().trim());

            databaseHelper.updateUser(user);

            // Dialog to show success message that record saved successfully
            Toast.makeText(activity, "Successfully Change Password", Toast.LENGTH_SHORT).show();
            emptyInputEditText();
            Log.i("User: " , String.valueOf(user.getPassword()));
            finish();

        } else {
            Toast.makeText(activity, "Old Password is not correct", Toast.LENGTH_SHORT).show();
        }
    }

    private void emptyInputEditText() {
        etOldPasswd.setText(null);
        etPasswd.setText(null);
        etConfirmPasswd.setText(null);
    }
}
