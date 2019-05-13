package magosla.tutorial.android.learners.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import magosla.tutorial.android.learners.R;
import magosla.tutorial.android.learners.ui.adapters.StudentAdapter;
import magosla.tutorial.android.learners.data.DataSourceCallback;
import magosla.tutorial.android.learners.models.Student;
import magosla.tutorial.android.learners.data.StudentDataSource;
import magosla.tutorial.android.learners.data.StudentViewModel;
import magosla.tutorial.android.learners.data.StudentViewModelFactory;

public class StudentsActivity extends AppCompatActivity implements StudentAdapter.OnEventListener {
    private StudentDataSource mStudentDataSource;
    private StudentViewModel mStudentViewModel;

    private TextView mStudentPlaceholder;
    private RecyclerView mStudentList;
    private Button mAddStudentButton;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, StudentsActivity.class);
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
        mStudentDataSource = new StudentDataSource(this);

        setContentView(R.layout.activity_students);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mStudentViewModel = ViewModelProviders.of(this, new StudentViewModelFactory(mStudentDataSource))
                .get(StudentViewModel.class);

        mStudentPlaceholder = findViewById(R.id.student_placeholder);
        mAddStudentButton = findViewById(R.id.add_student_btn);
        mStudentList = findViewById(R.id.student_list);

        initializeUI();
    }

    private void initializeUI() {
        mStudentList.setLayoutManager(new LinearLayoutManager(this));
        StudentAdapter adapter = new StudentAdapter(this);
        mStudentList.setAdapter(adapter);

        mStudentViewModel.getStudents().observe(this, students -> {
            adapter.setStudents(students);
            updateListVisibility(students.size() > 0);
        });

        mAddStudentButton.setOnClickListener(view -> {
            Intent intent = StudentEditActivity.getActivityIntent(getApplicationContext(), null);
            startActivity(intent);
        });
    }

    /**
     * Determine if the Student list view should be displayed or a placeholder displayed in place of the RecyclerView when
     * no student was found
     *
     * @param hasStudent if at least a student was found
     */
    public void updateListVisibility(boolean hasStudent) {
        mStudentList.setVisibility(hasStudent ? View.VISIBLE : View.GONE);
        mStudentPlaceholder.setVisibility(hasStudent ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onItemEditClick(Student student) {
        Intent intent = StudentEditActivity.getActivityIntent(this, student.getId());
        startActivity(intent);
    }

    @Override
    public void onItemDeleteClick(Student student) {
        mStudentDataSource.deleteStudent(new DataSourceCallback() {
            @Override
            public void onDelete(int totalDeleted) {
                Toast.makeText(getApplicationContext(), String.format(getString(R.string.student_deleted), student.getFullName()), Toast.LENGTH_SHORT).show();
            }
        }, student);
    }

    @Override
    public void onItemDetailClick(Student student) {
        Intent intent = StudentActivity.getActivityIntent(this, student.getId());
        startActivity(intent);
    }
}