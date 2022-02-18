package im.vector.app.features.protection;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import im.vector.app.R;

public class PasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
    }

    @Override
    public void onBackPressed() {
        if (!BackgroundJobService.isWorking)
            super.onBackPressed();
    }
}