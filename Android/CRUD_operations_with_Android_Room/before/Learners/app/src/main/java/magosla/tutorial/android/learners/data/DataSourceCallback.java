package magosla.tutorial.android.learners.data;

public interface DataSourceCallback {
        /**
         * When a delete operation is performed
         *
         * @param totalDeleted total items deleted
         */
        default void onDelete(int totalDeleted) {
        }

        /**
         * When an updated operation is performed
         * @param totalUpdated total items updated
         */
        default void onUpdate(int totalUpdated) {
        }

        /**
         * When a create operation is performed
         * @param itemIds ids of items inserted
         */
        default void onCreate(long[] itemIds) {
        }
    }