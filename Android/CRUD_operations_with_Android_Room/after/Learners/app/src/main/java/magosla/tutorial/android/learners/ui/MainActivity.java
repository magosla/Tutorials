package magosla.tutorial.android.learners.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import magosla.tutorial.android.learners.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.students_btn).setOnClickListener(view -> {
            Intent intent = StudentsActivity.getActivityIntent(getApplicationContext());
            startActivity(intent);
        });

    }
}
