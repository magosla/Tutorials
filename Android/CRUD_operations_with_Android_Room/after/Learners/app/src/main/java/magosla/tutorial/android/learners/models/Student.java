package magosla.tutorial.android.learners.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Student.TABLE_NAME)
public class Student{
    public static final String TABLE_NAME = "students";

    @PrimaryKey
    private Long id;
    @ColumnInfo(typeAffinity = ColumnInfo.TEXT, name = "full_name")
    private String fullName;
    @ColumnInfo(typeAffinity = ColumnInfo.TEXT)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}