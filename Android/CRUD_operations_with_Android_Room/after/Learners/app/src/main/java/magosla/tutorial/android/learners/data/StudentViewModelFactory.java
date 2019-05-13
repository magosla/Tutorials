package magosla.tutorial.android.learners.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class StudentViewModelFactory  extends ViewModelProvider.NewInstanceFactory {
    private StudentDataSource mDataSource;
    public StudentViewModelFactory(StudentDataSource studentsDataSource){
        mDataSource=studentsDataSource;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new StudentViewModel(mDataSource);
    }
}