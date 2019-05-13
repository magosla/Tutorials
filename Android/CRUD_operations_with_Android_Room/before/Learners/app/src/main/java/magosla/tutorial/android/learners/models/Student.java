package magosla.tutorial.android.learners.models;

// TODO 3: Convert class to Room Entity
public class Student {
    //TODO 4: Make id Field a PrimaryKey
    private Long id;
    // TODO 5: Make fullName a TEXT field with name "full_name"
    private String fullName;
    // TODO 6: Make email a TEXT field
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
