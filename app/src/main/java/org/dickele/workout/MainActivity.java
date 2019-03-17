package org.dickele.workout;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;
import org.dickele.workout.activity.workout.WorkoutActivity;
import org.dickele.workout.data.Workout;
import org.dickele.workout.repository.InMemoryDb;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final Logger LOGGER = Logger.getLogger(MainActivity.class.getSimpleName());

    private static final String FILE_NAME = "workout.md";

    private List<Workout> workouts = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_main);

        workouts = InMemoryDb.getInstance().getWorkouts();
        if (workouts == null || workouts.isEmpty()) {
            loadWorkoutFile(false);
        } else {
            updateDataRelatedToWorkouts();
        }
        findViewById(R.id.button_reload).setOnClickListener(v -> refreshWorkouts());

        findViewById(R.id.button_workouts).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, WorkoutActivity.class)));
    }

    private void updateDataRelatedToWorkouts() {
        ((TextView) findViewById(R.id.text_workoutNumber)).setText(
                getString(R.string.nb_workout_loaded, workouts.size()));
    }

    // ====================================================================================
    // Workout loading
    // ====================================================================================

    private void refreshWorkouts() {
        ((TextView) findViewById(R.id.text_workoutNumber)).setText(getString(R.string.loading_workouts));
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
