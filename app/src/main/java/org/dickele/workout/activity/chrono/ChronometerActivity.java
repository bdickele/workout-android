package org.dickele.workout.activity.chrono;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.dickele.workout.R;

public class ChronometerActivity extends AppCompatActivity {

    // --------------------------------------------

    private boolean chronoRunning;

    private Chronometer chronometer;

    private ImageButton buttonChronoPlay;

    private long pauseOffset = 0;

    // --------------------------------------------

    private boolean intervalometerRunning;

    private long intervalometerPauseOffset = 0;

    private Chronometer interval;

    private Chronometer intervalometer;

    private ImageButton buttonIntervalometerPlay;

    private int intervalCounter = 0;

    // When adding or substracting seconds to interval, here is the value to add/substract (in ms)
    private static final int INTERVAL_OFFSET = 2 * 1000;

    private int numberOfLapCurrent = 0;

    private int numberOfLapTarget = 0;

    private boolean numberOfLapTargetReached = true;

    private TextView textNbLap;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer);
        ((Toolbar) findViewById(R.id.toolbar_main)).setTitle(R.string.stopwatch);

        chronoRunning = false;

        chronometer = findViewById(R.id.chronometer);
        buttonChronoPlay = findViewById(R.id.button_chrono_play);
        buttonChronoPlay.setOnClickListener(v -> playOrPauseChrono());
        final ImageButton buttonChronoStop = findViewById(R.id.button_chrono_stop);
        buttonChronoStop.setOnClickListener(v -> stopChrono());

        intervalometerRunning = false;

        textNbLap = findViewById(R.id.nbLap);
        interval = findViewById(R.id.interval);
        intervalometer = findViewById(R.id.intervalometer);
        interval.setBase(now());
        findViewById(R.id.button_interval_plus).setOnClickListener(v -> increaseInterval());
        findViewById(R.id.button_interval_minus).setOnClickListener(v -> decreaseInterval());
        buttonIntervalometerPlay = findViewById(R.id.button_intervalometer_play);
        buttonIntervalometerPlay.setOnClickListener(v -> playOrPauseIntervalometer());
        findViewById(R.id.button_intervalometer_stop).setOnClickListener(v -> stopIntervalometer());
        numberOfLapCurrent = 0;
        updateIntervalText();
        defineIntervalometerListener();

        setSupportActionBar(findViewById(R.id.toolbar_main));
        // Used to display back button in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Management of back button in toolbar
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private long now() {
        return SystemClock.elapsedRealtime();
    }

    // -------------------------------------------------------------------

    private void playOrPauseChrono() {
        if (!chronoRunning) {
            playChrono();
        } else {
            pauseChrono();
        }
    }

    private void playChrono() {
        chronoRunning = true;
        buttonChronoPlay.setImageResource(R.drawable.ic_pause_black_32dp);
        chronometer.setBase(now() - pauseOffset);
        chronometer.start();
    }

    private void pauseChrono() {
        chronoRunning = false;
        buttonChronoPlay.setImageResource(R.drawable.ic_play_black_32dp);
        chronometer.stop();
        pauseOffset = now() - chronometer.getBase();
    }

    private void stopChrono() {
        pauseChrono();
        chronometer.setBase(now());
        pauseOffset = 0;
    }

    // -------------------------------------------------------------------

    private void increaseInterval() {
        intervalCounter++;
        interval.setBase(now() - intervalCounter * INTERVAL_OFFSET);
        updateIntervalText();
    }

    private void decreaseInterval() {
        intervalCounter--;
        if (intervalCounter < 0) {
            intervalCounter = 0;
        }
        updateIntervalText();
    }

    private void updateIntervalText() {
        interval.setBase(now() - intervalCounter * INTERVAL_OFFSET);
    }

    private void playOrPauseIntervalometer() {
        if (!intervalometerRunning) {
            playIntervalometer();
        } else {
            pauseIntervalometer();
        }
    }

    private void playIntervalometer() {
        if (intervalCounter < 1) {
            return;
        }
        // Clicking on that play button means we've just completed a lap
        if (numberOfLapTargetReached) {
            numberOfLapCurrent++;
            updateNumberOfLap();
            numberOfLapTarget = numberOfLapCurrent + 1;
        }

        intervalometerRunning = true;
        buttonIntervalometerPlay.setImageResource(R.drawable.ic_pause_black_32dp);
        intervalometer.setBase(now() - intervalometerPauseOffset);
        intervalometer.start();
    }

    private void pauseIntervalometer() {
        intervalometerRunning = false;
        buttonIntervalometerPlay.setImageResource(R.drawable.ic_play_black_32dp);
        intervalometer.stop();
        intervalometerPauseOffset = now() - intervalometer.getBase();
    }

    private void stopIntervalometer() {
        pauseIntervalometer();
        intervalometer.setBase(now());
        intervalometerPauseOffset = 0;
        numberOfLapCurrent = 0;
        numberOfLapTarget = 1;
        updateNumberOfLap();
    }

    private void updateNumberOfLap() {
        textNbLap.setText("" + numberOfLapCurrent);
    }

    private void defineIntervalometerListener() {
        intervalometer.setOnChronometerTickListener(chrono -> {
            if (!intervalometerRunning) {
                return;
            }

            final long timeElapsed = now() - chrono.getBase();
            final long timeTarget = (numberOfLapTarget - 1) * INTERVAL_OFFSET;
            if (timeElapsed >= timeTarget) {
                numberOfLapTargetReached = true;
                pauseIntervalometer();
                Toast.makeText(this, "Pause terminée : commencer série " + (numberOfLapCurrent + 1), Toast.LENGTH_SHORT).show();
            } else {
                numberOfLapTargetReached = false;
            }
        });
    }
}
