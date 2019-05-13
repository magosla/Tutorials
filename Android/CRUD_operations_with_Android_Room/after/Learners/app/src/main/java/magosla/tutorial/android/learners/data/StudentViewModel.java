package magosla.tutorial.android.learners.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import magosla.tutorial.android.learners.models.Student;

public class StudentViewModel extends ViewModel {
    private StudentDataSource mStudentDataSource;

    StudentViewModel(StudentDataSource studentDataSource){
        mStudentDataSource = studentDataSource;
    }

    /**
     * Get a student identified by the provided id
     * @param id the id of the student
     * @return LiveData of Student if an entry is found with provided id
     */
    public LiveData<Student> getStudent(Long id){
        return mStudentDataSource.getStudent(id);
    }

    /**
     * Get the list of students
     * @return LiveData of Student List
     */
    public LiveData<List<Student>> getStudents(){
        return mStudentDataSource.listStudents();
    }
}
