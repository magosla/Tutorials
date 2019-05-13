package magosla.tutorial.android.learners.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import magosla.tutorial.android.learners.models.Student;

@Database(entities = {Student.class}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static String DATABASE_NAME = "Learn";
    private static AppDatabase sInstance;

    /**
     * Get the database instance
     *
     * @param context the application context
     * @return the instance on the database
     */
    public static AppDatabase getDatabase(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context,
                    AppDatabase.class,
                    DATABASE_NAME).build();
        }
        return sInstance;
    }

    public abstract StudentDao studentDao();
}
