package org.dickele.workout;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.apache.commons.io.FileUtils;
import org.dickele.workout.activity.chrono.ChronometerActivity;
import org.dickele.workout.activity.exercise.ExerciseActivity;
import org.dickele.workout.activity.parameters.ParametersActivity;
import org.dickele.workout.activity.workoutlist.WorkoutListScreen2Activity;
import org.dickele.workout.data.Exercise;
import org.dickele.workout.data.RoutineRef;
import org.dickele.workout.data.Workout;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.util.ArgumentConst;
import org.dickele.workout.util.ViewUtil;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private static final Logger LOGGER = Logger.getLogger(MainActivity.class.getSimpleName());

    private static final String FILE_NAME = "workout.md";

    private List<Workout> workouts = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_main);

        // Let's load the data
        workouts = InMemoryDb.getInstance().getWorkouts();
        if (workouts == null || workouts.isEmpty()) {
            loadWorkoutFile(false);
        } else {
            updateDataRelatedToWorkouts();
        }

        // Configuration of main toolbar
        setSupportActionBar(findViewById(R.id.toolbar_main));
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_reload:
                reloadWorkouts();
                return true;
            case R.id.menu_main_parameters:
                goToParameters();
                return true;
            case R.id.menu_main_stopwatch:
                goToStopwatch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateDataRelatedToWorkouts() {
        // Pager is built here so that everything is rebuilt when data are re-loaded
        final List<String> pageNames = Arrays.asList(
                ViewUtil.getLabel(getApplicationContext(), "menu_workouts"),
                ViewUtil.getLabel(getApplicationContext(), "menu_exercises"));

        final ViewPager pager = findViewById(R.id.activity_main_viewpager);
        pager.setAdapter(new MainPageAdapter(getSupportFragmentManager(), pageNames) {
        });

        final TabLayout tabs = findViewById(R.id.activity_main_tabs);
        tabs.setupWithViewPager(pager);
    }

    public void goToRoutineWorkouts(final RoutineRef routine) {
        final Intent intent = new Intent(this, WorkoutListScreen2Activity.class);
        intent.putExtra(ArgumentConst.ROUTINE_NAME, routine.name());
        startActivity(intent);
    }

    private void goToParameters() {
        startActivity(new Intent(this, ParametersActivity.class));
    }

    public void gotToExercise(final Exercise exercise) {
        final Intent intent = new Intent(this, ExerciseActivity.class);
        intent.putExtra(ArgumentConst.EXERCISE_NAME, exercise.getRef().name());
        startActivity(intent);
    }

    public void goToStopwatch() {
        startActivity(new Intent(this, ChronometerActivity.class));
    }

    // ====================================================================================
    // Workout loading
    // ====================================================================================

    private void reloadWorkouts() {
        loadWorkoutFile(true);
    }

    private void loadWorkoutFile(final boolean refresh) {
        final ContentLoadingProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.show();

        final File file = new File(getBaseContext().getFilesDir(), FILE_NAME);
        boolean dataLoaded = false;
        if (!refresh) {
            if (file.exists()) {
                LOGGER.log(Level.INFO, "Workout file found in the device => let's try to extract data");
                try {
                    InMemoryDb.getInstance().loadData(file);
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

        progressBar.hide();
    }

    private boolean loadWorkoutEmbeddedFile(final File targetFile) {
        LOGGER.log(Level.INFO, "Let's try to create a workout file based on embedded one");
        try (final InputStream myInput = getBaseContext().getAssets().open(FILE_NAME, AssetManager.ACCESS_BUFFER)) {
            FileUtils.copyInputStreamToFile(myInput, targetFile);
        } catch (final Exception e) {
            LOGGER.severe("Could not copy asset file " + FILE_NAME + " : " + e.getCause());
            return false;
        }

        try {
            InMemoryDb.getInstance().loadData(targetFile);
            return true;
        } catch (final Exception e) {
            LOGGER.severe("Data from workout file could not be extracted : " + e.getCause());
            return false;
        }
    }

}
