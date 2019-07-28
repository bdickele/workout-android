package org.dickele.workout.activity.chrono;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.widget.Chronometer;
import android.widget.ImageButton;

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

    private Chronometer interval;

    private long intervalBase = SystemClock.elapsedRealtime();

    // When adding or substracting seconds to interval, here is the value to add/substract (in ms)
    private final int intervalOffset = 30000;

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

        interval = findViewById(R.id.interval);
        interval.setBase(intervalBase);
        findViewById(R.id.button_interval_plus).setOnClickListener(v -> addInterval());
        findViewById(R.id.button_interval_minus).setOnClickListener(v -> substractInterval());

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
        chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
        chronometer.start();
    }

    private void pauseChrono() {
        chronoRunning = false;
        buttonChronoPlay.setImageResource(R.drawable.ic_play_black_32dp);
        chronometer.stop();
        pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
    }

    private void stopChrono() {
        pauseChrono();
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

    // -------------------------------------------------------------------

    private void addInterval() {
        intervalBase += intervalOffset;
        //interval.setBase(SystemClock.elapsedRealtime() + 1000);
        interval.setBase(intervalBase);
    }

    private void substractInterval() {

    }
}
