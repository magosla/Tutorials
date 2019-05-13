package magosla.tutorial.android.learners.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import magosla.tutorial.android.learners.models.Student;

public class StudentDataSource {
    private final Executor mExecutor;
    private WeakReference<DataSourceCallback> mCallback;
    // the Handler for the Main Thread
    private Handler mMyHandler;


    //TODO 14: Declare a field variable of type StudentDao

    public StudentDataSource(Context context) {

        mExecutor = Executors.newSingleThreadExecutor();

        //TODO 15: Initialize StudentDao field variable for the AppDatabase class
    }

    private void setCallback(@Nullable DataSourceCallback callback) {
        if (callback != null) {
            // Get the handler for the main thread
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
        // TODO 16: Return result from StudentDao#getStudent method
        return new MutableLiveData<>();
    }

    /**
     * Retrieve the available students
     *
     * @return the list of {@link Student}
     */
    public LiveData<List<Student>> listStudents() {

        // TODO 17: Return result from StudentDao#getStudents method

        return new MutableLiveData<>();
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
            // TODO 18: Assign value from StudentDao#insert to "itemIds"
            long[] itemIds = new long[0];

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
            // TODO 19: Assign value from StudentDao#update to "totalUpdated"
            int totalUpdated = 0;

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
            // TODO 20: Assign value from StudentDao#delete to totalDeleted
            int totalDeleted = 0;

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

