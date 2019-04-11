package org.dickele.workout.activity.exercise;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;

import org.apache.commons.lang3.StringUtils;
import org.dickele.workout.R;
import org.dickele.workout.activity.exercise.reps.ExerciseRoutineAdapter;
import org.dickele.workout.data.Exercise;
import org.dickele.workout.data.ExerciseRef;
import org.dickele.workout.data.RoutineRef;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.service.ServiceRead;
import org.dickele.workout.util.ArgumentConst;
import org.dickele.workout.util.GraphUtil;
import org.dickele.workout.util.StringUtil;
import org.dickele.workout.util.ViewUtil;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseActivity extends AppCompatActivity {

    @BindView(R.id.exercise_difficulty)
    ImageView picDifficulty;

    @BindView(R.id.exercise_best_pic)
    ImageView picBestPerformance;

    @BindView(R.id.exercise_best_total)
    TextView textBestTotal;

    @BindView(R.id.exercise_best_reps)
    TextView textBestReps;

    @BindView(R.id.exercise_best_date)
    TextView textBestDate;

    @BindView(R.id.exercise_description)
    TextView textDescription;

    @BindView(R.id.chart)
    LineChart chart;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        setSupportActionBar(findViewById(R.id.toolbar_main));
        // Used to display back button in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this, findViewById(R.id.activity_exercise));

        final ExerciseRef exerciseRef = ExerciseRef.valueOf(getIntent().getStringExtra(ArgumentConst.EXERCISE_NAME));
        setTitle(getString(R.string.exercise_and_code, exerciseRef.name()));

        final Exercise exercise = new ServiceRead(InMemoryDb.getInstance()).getExercise(exerciseRef);

        picDifficulty.setImageResource(ViewUtil.getDifficultyPic_M(exercise.getRef().getDifficulty()));
        final WorkoutExercise bestPerformance = exercise.getBestPerformance();
        final String bestPerfTotal = "" + bestPerformance.getTotal();
        textBestTotal.setText(bestPerfTotal);
        textBestReps.setText(StringUtil.getStringForReps(bestPerformance.getReps()));
        textBestDate.setText(bestPerformance.getDate().format(StringUtil.DATE_FORMATTER_DDMMYYYY));
        picBestPerformance.setImageResource(exercise.bestPerformanceIsAHotTopic() ?
                R.drawable.ic_whatshot_black_18dp : R.drawable.ic_fitness_center_black_18dp);
        textDescription.setText(ViewUtil.getExerciseDescription(this, exerciseRef));

        final List<WorkoutExercise> exerciseExercises = exercise.getExercises();
        GraphUtil.configureLineGraph(chart, exerciseExercises);

        final ViewPager pager = findViewById(R.id.routine_reps_viewpager);
        pager.setAdapter(new ExerciseRoutineAdapter(getSupportFragmentManager(), exercise) {
            //
        });

        final String routineName = getIntent().getStringExtra(ArgumentConst.ROUTINE_NAME);
        final RoutineRef routineRef = StringUtils.isEmpty(routineName) ? exercise.getRoutineRefs().get(0) : RoutineRef.valueOf(routineName);
        pager.setCurrentItem(exercise.getRoutineRefs().indexOf(routineRef));
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

}
