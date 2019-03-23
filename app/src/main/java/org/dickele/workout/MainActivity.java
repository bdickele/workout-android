package org.dickele.workout;

import android.content.res.AssetManager;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import org.apache.commons.io.FileUtils;
import org.dickele.workout.data.Workout;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.util.ViewUtil;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    private static final Logger LOGGER = Logger.getLogger(MainActivity.class.getSimpleName());

    private static final String FILE_NAME = "workout.md";

    private List<Workout> workouts = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_main);

        final List<String> pageNames = Arrays.asList(
                ViewUtil.getLabel(getApplicationContext(), "menu_workouts"),
                ViewUtil.getLabel(getApplicationContext(), "menu_routines"),
                ViewUtil.getLabel(getApplicationContext(), "menu_exercises"));

        final ViewPager pager = findViewById(R.id.activity_main_viewpager);
        pager.setAdapter(new MainPageAdapter(getSupportFragmentManager(), pageNames) {
        });

        final TabLayout tabs = findViewById(R.id.activity_main_tabs);
        tabs.setupWithViewPager(pager);
        tabs.setTabMode(TabLayout.MODE_FIXED);

        workouts = InMemoryDb.getInstance().getWorkouts();
        if (workouts == null || workouts.isEmpty()) {
            loadWorkoutFile(false);
        } else {
            updateDataRelatedToWorkouts();
        }
    }

    private void updateDataRelatedToWorkouts() {
        setTitle(R.string.app_name);
    }

    // ====================================================================================
    // Workout loading
    // ====================================================================================

    //TODO Menu avec option Recharger
    private void refreshWorkouts() {
        //TODO Mettre un spinner pendant le chargement
        loadWorkoutFile(true);
    }

    private void loadWorkoutFile(final boolean refresh) {
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
