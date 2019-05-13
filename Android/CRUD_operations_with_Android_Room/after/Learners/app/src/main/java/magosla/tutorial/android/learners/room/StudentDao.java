package magosla.tutorial.android.learners.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import magosla.tutorial.android.learners.models.Student;

@Dao
public interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(Student... students);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(Student... students);

    @Delete
    int delete(Student... students);

    /**
     * Get a student with the provided id
     * @param id the student id
     * @return student LiveData
     */
    @Query("SELECT * FROM "+ Student.TABLE_NAME+ " WHERE id = :id")
    LiveData<Student> getStudent(Long id);

    /**
     * Get the list of students
      * @return LiveData of Student List
     */
    @Query("SELECT * FROM "+ Student.TABLE_NAME+ "")
    LiveData<List<Student>> getStudents();

}
