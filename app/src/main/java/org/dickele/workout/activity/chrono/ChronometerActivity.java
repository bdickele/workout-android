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

    private boolean chronoRunning;

    private Chronometer chronometer;

    private ImageButton buttonChronoPlay;

    private ImageButton buttonChronoStop;

    private long pauseOffset = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer);
        ((Toolbar) findViewById(R.id.toolbar_main)).setTitle(R.string.stopwatch);

        chronoRunning = false;

        chronometer = findViewById(R.id.chronometer);
        buttonChronoPlay = findViewById(R.id.button_chrono_play);
        buttonChronoPlay.setOnClickListener(v -> playOrPauseChrono());
        buttonChronoStop = findViewById(R.id.button_chrono_stop);
        buttonChronoStop.setOnClickListener(v -> stopChrono());

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
}
