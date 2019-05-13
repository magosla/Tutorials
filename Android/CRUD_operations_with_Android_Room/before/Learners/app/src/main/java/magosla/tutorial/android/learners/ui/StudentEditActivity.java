package magosla.tutorial.android.learners.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;

import magosla.tutorial.android.learners.R;
import magosla.tutorial.android.learners.data.DataSourceCallback;
import magosla.tutorial.android.learners.data.StudentViewModel;
import magosla.tutorial.android.learners.data.StudentViewModelFactory;
import magosla.tutorial.android.learners.models.Student;
import magosla.tutorial.android.learners.data.StudentDataSource;

public class StudentEditActivity extends AppCompatActivity {
    private static final String EXTRA_STUDENT_ID = "extra_student";
    private TextInputEditText mFullNameEdit;
    private TextInputEditText mEmailEdit;
    private Student mStudent;
    private Long mStudentId;
    private boolean mIsItemEdit;
    private TextView mLoadingText;
    private TextView mNotFoundText;

    private StudentDataSource mStudentDataSource;

    private StudentViewModel mStudentViewModel;

    public static Intent getActivityIntent(Context context, @Nullable Long studentId) {
        Intent intent = new Intent(context, StudentEditActivity.class);
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

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent() != null) {
            mStudentId = getIntent().getLongExtra(EXTRA_STUDENT_ID, 0);
        }

        mStudent = new Student();

        mIsItemEdit = (mStudentId != null && mStudentId > 0);

        mStudentDataSource = new StudentDataSource(this);
        // Are we editing
        if (mIsItemEdit) {
            // change the toolbar title to display edit
            setTitle(R.string.edit_student);
            mStudentViewModel = ViewModelProviders.
                    of(this, new StudentViewModelFactory(new StudentDataSource(this)))
                    .get(StudentViewModel.class);
        }

        setContentView(R.layout.activity_student_edit);

        Button saveButton = findViewById(R.id.save_button);
        mFullNameEdit = findViewById(R.id.full_name_edit);
        mEmailEdit = findViewById(R.id.email_edit);

        mLoadingText = findViewById(R.id.loading);
        mNotFoundText = findViewById(R.id.not_found);

        saveButton.setOnClickListener(view -> saveStudent());

        initializeUI();

    }

    private void initializeUI() {
        if (mIsItemEdit) {
            mLoadingText.setVisibility(View.VISIBLE);
            // Get the Student data to edit
            mStudentViewModel.getStudent(mStudentId).observe(this,
                    new Observer<Student>() {
                        @Override
                        public void onChanged(Student student) {
                            if (student != null) {
                                mStudent = student;
                                updateView();
                            }

                            mNotFoundText.setVisibility(student != null ? View.GONE : View.VISIBLE);

                            mLoadingText.setVisibility(View.GONE);
                        }
                    });
        } else {
            // This is a new student
            updateView();
        }
    }

    private void updateView() {
        mFullNameEdit.setText(mStudent.getFullName());
        mEmailEdit.setText(mStudent.getEmail());
    }

    private void saveStudent() {
        String fullName = mFullNameEdit.getText() != null ? mFullNameEdit.getText().toString() : "";
        String email = mEmailEdit.getText() != null ? mEmailEdit.getText().toString() : "";

        // Validate the input data
        if (!(!TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(email))) {
            Toast.makeText(this, R.string.all_field_required, Toast.LENGTH_SHORT).show();
            return;
        }

        mStudent.setFullName(fullName);
        mStudent.setEmail(email);

        if (mStudent.getId() != null) {
            // Update the Student entry
            mStudentDataSource.updateStudent(new DataSourceCallback() {
                @Override
                public void onUpdate(int totalUpdated) {
                    if (totalUpdated > 0) {
                        Toast.makeText(getApplicationContext(), R.string.student_updated, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.student_not_updated, Toast.LENGTH_SHORT).show();
                    }
                }
            }, mStudent);
        } else {
            // Create a new Student entry
            mStudentDataSource.addStudent(new DataSourceCallback() {
                @Override
                public void onCreate(long[] itemIds) {
                    if (itemIds != null) {
                        Toast.makeText(getApplicationContext(), R.string.student_created, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.student_not_created, Toast.LENGTH_SHORT).show();
                    }
                }
            }, mStudent);
        }
    }


}
