package magosla.tutorial.android.learners.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import magosla.tutorial.android.learners.R;
import magosla.tutorial.android.learners.models.Student;

public class StudentAdapter extends RecyclerView.Adapter<StudentViewHolder> {

    private List<Student> mStudents;
    private OnEventListener mListener;

    public StudentAdapter(OnEventListener listener) {
        mStudents = new ArrayList<>();
        mListener = listener;
    }

    public void setStudents(List<Student> students) {
        mStudents = students;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.bind(mStudents.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mStudents.size();
    }

    public interface OnEventListener {
        void onItemEditClick(Student student);

        void onItemDeleteClick(Student student);

        void onItemDetailClick(Student student);
    }
}
