package magosla.tutorial.android.learners.ui.adapters;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import magosla.tutorial.android.learners.R;
import magosla.tutorial.android.learners.models.Student;

class StudentViewHolder extends RecyclerView.ViewHolder {
    private StudentAdapter.OnEventListener mListener;

    private TextView mFullName;
    private Button mEditButton;
    private Button mDeleteButton;
    private View mSeparator;
    private Student mStudent;

    StudentViewHolder(@NonNull View itemView, StudentAdapter.OnEventListener listener) {
        super(itemView);
        mListener = listener;
        mFullName = itemView.findViewById(R.id.full_name_text);
        mEditButton = itemView.findViewById(R.id.edit_btn);
        mDeleteButton = itemView.findViewById(R.id.delete_btn);
        mSeparator = itemView.findViewById(R.id.separator);

        initialiseActions();
    }

    private void initialiseActions() {
        mEditButton.setOnClickListener(view -> mListener.onItemEditClick(mStudent));
        mDeleteButton.setOnClickListener(view -> mListener.onItemDeleteClick(mStudent));

        itemView.setOnClickListener(view -> mListener.onItemDetailClick(mStudent));
    }

    void bind(Student student, int position) {
        mStudent = student;

        if (position == 0) {
            // remove the separator for the first item in the list
            mSeparator.setVisibility(View.GONE);
        }

        mFullName.setText(student.getFullName());

    }
}
