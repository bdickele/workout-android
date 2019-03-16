package org.dickele.workout;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;
import org.dickele.workout.data.Workout;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.views.WorkoutMainFragment;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private static final Logger LOGGER = Logger.getLogger(MainActivity.class.getSimpleName());

    private static final String FILE_NAME = "workout.md";

    private List<Workout> workouts = new ArrayList<>();

    private WorkoutMainFragment workoutMainFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        workouts = InMemoryDb.getInstance().getWorkouts();
        if (workouts == null || workouts.isEmpty()) {
            loadWorkoutFile(false);
        } else {
            updateDataRelatedToWorkouts();
        }
        findViewById(R.id.button_refresh).setOnClickListener(v -> refreshWorkouts());
    }

    // ====================================================================================
    // Workout fragment configuration
    // ====================================================================================

    private void configureAndShowMainFragment() {
        workoutMainFragment = (WorkoutMainFragment) getSupportFragmentManager().findFragmentById(R.id.activity_workout_frame_layout);

        if (workoutMainFragment == null) {
            workoutMainFragment = new WorkoutMainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_workout_frame_layout, workoutMainFragment)
                    .commit();
        }
    }

    // ====================================================================================
    // Workout loading
    // ====================================================================================

    private void refreshWorkouts() {
        ((TextView) findViewById(R.id.text_workoutNumber)).setText("Refreshing...");
        loadWorkoutFile(true);
    }

    private void loadWorkoutFile(final boolean refresh) {
        final File file = new File(getBaseContext().getFilesDir(), FILE_NAME);
        boolean dataLoaded = false;
        if (!refresh) {
            if (file.exists()) {
                LOGGER.log(Level.INFO, "Workout file found in the device => let's try to extract data");
                try {
                    InMemoryDb.getInstance().loadWorkouts(file);
                    dataLoaded = true;
                } catch (final Exception e) {
                    LOGGER.log(Level.WARNING, "Data from workout file could not be extracted");
                }
            } else {
                LOGGER.log(Level.INFO, "Workout file not found in the device");
            }
        }
        if (!dataLoaded) {
            dataLoaded = loadWorkoutEmbeddedFile(file);
        }
        if (!dataLoaded) {
            throw new Error("Could not load workout data");
        }
        this.workouts = InMemoryDb.getInstance().getWorkouts();
        updateDataRelatedToWorkouts();
    }

    private void updateDataRelatedToWorkouts() {
        ((TextView) findViewById(R.id.text_workoutNumber)).setText(workouts.size() + " workouts loaded");
        configureAndShowMainFragment();
    }

    private boolean loadWorkoutEmbeddedFile(final File targetFile) {
        LOGGER.log(Level.INFO, "Let's try to create a workout file based on embedded one");
        try (final InputStream myInput = getBaseContext().getAssets().open(FILE_NAME, AssetManager.ACCESS_BUFFER)) {
            FileUtils.copyInputStreamToFile(myInput, targetFile);
            //getBaseContext().getAssets().list("")
        } catch (final Exception e) {
            LOGGER.severe("Could not copy asset file " + FILE_NAME + " : " + e.getCause());
            return false;
        }

        try {
            InMemoryDb.getInstance().loadWorkouts(targetFile);
            return true;
        } catch (final Exception e) {
            LOGGER.severe("Data from workout file could not be extracted : " + e.getCause());
            return false;
        }
    }

}
