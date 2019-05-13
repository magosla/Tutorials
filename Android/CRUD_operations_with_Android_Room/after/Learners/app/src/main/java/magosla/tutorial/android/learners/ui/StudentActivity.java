package magosla.tutorial.android.learners.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import magosla.tutorial.android.learners.R;
import magosla.tutorial.android.learners.data.StudentDataSource;
import magosla.tutorial.android.learners.data.StudentViewModel;
import magosla.tutorial.android.learners.data.StudentViewModelFactory;

public class StudentActivity extends AppCompatActivity {

    private static final String EXTRA_STUDENT_ID = "extra_student";

    private Long mStudentId;

    private TextView mFullNameText;
    private TextView mEmailText;
    private CardView mProfileCard;
    private CardView mLoadingCard;
    private CardView mNotFoundCard;

    private StudentViewModel mStudentViewModel;

    public static Intent getActivityIntent(Context context, Long studentId) {
        Intent intent = new Intent(context, StudentActivity.class);
        intent.putExtra(EXTRA_STUDENT_ID, studentId);
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null) {
            mStudentId = getIntent().getLongExtra(EXTRA_STUDENT_ID,0);
        }

        if (mStudentId == null || mStudentId == 0) {
            throw new IllegalArgumentException("Student must be provided");
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mStudentViewModel = ViewModelProviders.
                of(this, new StudentViewModelFactory(new StudentDataSource(this)))
                .get(StudentViewModel.class);


        setContentView(R.layout.activity_student);
        mFullNameText = findViewById(R.id.full_name_text);
        mEmailText = findViewById(R.id.email_text);
        mProfileCard = findViewById(R.id.profile_card);
        mLoadingCard  = findViewById(R.id.loading_card);
        mNotFoundCard = findViewById(R.id.not_found_card);
        initializeUI();
    }

    private void initializeUI() {

        // Get the Student live data and observe for dataChange
        mStudentViewModel.getStudent(mStudentId).observe(this,
                student -> {
                    // Check if data was found
                    if(student!=null){
                        mFullNameText.setText(student.getFullName());
                        mEmailText.setText(student.getEmail());
                    }
                    mProfileCard.setVisibility(
                            student==null? View.GONE:View.VISIBLE
                    );

                    mNotFoundCard.setVisibility(
                            student!=null? View.GONE:View.VISIBLE
                    );
                    mLoadingCard.setVisibility(View.GONE);
                });
    }


}