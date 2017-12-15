package com.moez.QKSMS.ui.register;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.moez.QKSMS.R;
import com.moez.QKSMS.helpers.InputValidation;
import com.moez.QKSMS.model.User;
import com.moez.QKSMS.sql.DatabaseHelper;
import com.moez.QKSMS.ui.settings.SettingsFragment;
import com.moez.QKSMS.ui.view.QKEditText;
import com.moez.QKSMS.ui.view.QKTextView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;

    public static final int REGISTER_REQUEST_CODE = 31415;

    private TextView tvPasswd;
    private TextView tvConfirmPasswd;

    private EditText etPasswd;
    private EditText etConfirmPasswd;

    private Button btnRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {
        tvPasswd = (TextView) findViewById(R.id.tv_passwd);
        tvConfirmPasswd = (TextView) findViewById(R.id.tv_confirm_passwd);

        etPasswd = (EditText) findViewById(R.id.et_passwd);
        etConfirmPasswd = (EditText) findViewById(R.id.et_confirm_passwd);

        btnRegister = (Button) findViewById(R.id.btn_register);
    }

    private void initListeners() {
        btnRegister.setOnClickListener(this);
    }

    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                postDataToSql();
                break;

        }
    }

    private void postDataToSql() {
        if (!inputValidation.isInputEditTextFilled(etPasswd, tvPasswd, "Enter Password")) {
            Toast.makeText(activity, "Password does not filled", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!inputValidation.isInputEditTextMatches(etPasswd, etConfirmPasswd,
                tvConfirmPasswd, "Password does not match")) {
            Toast.makeText(activity, "Password does not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!databaseHelper.checkUser(etPasswd.getText().toString().trim())) {

            user.setUsername("hsms");
            user.setPassword(etPasswd.getText().toString().trim());

            databaseHelper.addUser(user);

            // Dialog to show success message that record saved successfully
            Toast.makeText(activity, "Successfully Registered Password", Toast.LENGTH_SHORT).show();
            emptyInputEditText();

            SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences(this).edit();
            prefs.putBoolean(SettingsFragment.REGISTER_SEEN, true);
            prefs.apply();
            finish();

        }
    }

    private void emptyInputEditText() {
        etPasswd.setText(null);
        etConfirmPasswd.setText(null);
    }

}
