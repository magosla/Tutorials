package magosla.tutorial.android.learners.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import magosla.tutorial.android.learners.models.Student;
import magosla.tutorial.android.learners.room.AppDatabase;
import magosla.tutorial.android.learners.room.StudentDao;

public class StudentDataSource {

    private final Executor mExecutor;
    private StudentDao mStudentDao;
    private WeakReference<DataSourceCallback> mCallback;
    // the Handler for the Main Thread
    private Handler mMyHandler;

    public StudentDataSource(Context context) {
        mStudentDao = AppDatabase.getDatabase(context).studentDao();

        mExecutor = Executors.newSingleThreadExecutor();
    }

    private void setCallback(@Nullable DataSourceCallback callback) {
        if (callback != null) {
            // the Handler for the Main Thread
            mMyHandler = new Handler(Looper.getMainLooper());
            // assign the callback as a WeakReference object
            mCallback = new WeakReference<>(callback);
        }
    }

    /**
     * Retrieve the student with the provided student id
     *
     * @param studentId the student id
     * @return {@link LiveData<Student>}
     */
    public LiveData<Student> getStudent(Long studentId) {
        return mStudentDao.getStudent(studentId);
    }

    /**
     * Retrieve the available students
     *
     * @return the list of {@link Student}
     */
    public LiveData<List<Student>> listStudents() {
        return mStudentDao.getStudents();
    }

    /**
     * Adds a new {@link Student}
     *
     * @param callback called when process completes
     * @param students the {@link Student} to add
     */
    public void addStudent(DataSourceCallback callback, @NonNull Student... students) {
        setCallback(callback);
        mExecutor.execute(() -> {

            // Add one or more Student in the database
            long[] itemIds = mStudentDao.insert(students);

            if (mCallback != null) {
                if (mCallback.get() != null) {
                    // Run the callback on the main thread
                    mMyHandler.post(() ->
                            mCallback.get().onCreate(itemIds));
                }
            }

        });
    }

    /**
     * Update a {@link Student} entry
     *
     * @param callback called when operation completes
     * @param students the {@link Student} to update
     */
    public void updateStudent(DataSourceCallback callback, @NonNull Student... students) {
        setCallback(callback);

        mExecutor.execute(() -> {

            // Update one or more Student in the database
            int totalUpdated = mStudentDao.update(students);

            if (mCallback != null) {
                if (mCallback.get() != null) {
                    // Run the callback on the main thread
                    mMyHandler.post(() ->
                            mCallback.get().onUpdate(totalUpdated));
                }
            }

        });
    }

    /**
     * Delete a {@link Student} entry
     *
     * @param callback called when operation completes
     * @param students the {@link Student} to update
     */
    public void deleteStudent(DataSourceCallback callback, @NonNull Student... students) {
        setCallback(callback);
        mExecutor.execute(() -> {

            // Delete one or more Student from the database
            int totalDeleted = mStudentDao.delete(students);

            if (mCallback != null) {
                if (mCallback.get() != null) {
                    // Run the callback on the main thread
                    mMyHandler.post(() ->
                            mCallback.get().onDelete(totalDeleted));
                }
            }

        });
    }
}

