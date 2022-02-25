package im.vector.app.features.protection;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import im.vector.app.R;

public class PasswordActivity extends AppCompatActivity {

    private TextInputEditText inputPassword;
    private Button login;
    private SharedSettings sharedSettings;
    private final String APP_PASSWORD = "AppPassword";
    private final String APP_PASSWORD_ENABLED = "AppPasswordEnabled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        sharedSettings = new SharedSettings(this);
        inputPassword = findViewById(R.id.input_password_confirmation_text);
        login = findViewById(R.id.loginSubmit);

        if (!sharedSettings.getValueBoolean(APP_PASSWORD_ENABLED, false))
            return;

        login.setOnClickListener(v -> {
            if (BackgroundJobService.isWorking)
                Toast.makeText(this, R.string.not_allowed_yet, Toast.LENGTH_SHORT).show();
            else {
                String pass = sharedSettings.getValueString(APP_PASSWORD);
                if (inputPassword.getText().toString().equals(pass))
                    finish();
                else
                    Toast.makeText(this, R.string.wrong_password, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        if (!BackgroundJobService.isWorking)
//            super.onBackPressed();
    }
}