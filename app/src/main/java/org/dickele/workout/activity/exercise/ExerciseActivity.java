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
import org.dickele.workout.util.GraphExerciseSelectionListener;
import org.dickele.workout.util.GraphUtil;
import org.dickele.workout.util.StringUtil;
import org.dickele.workout.util.ViewUtil;

import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseActivity extends AppCompatActivity implements GraphExerciseSelectionListener {

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

    @BindView(R.id.routine_reps_viewpager)
    ViewPager pager;

    private Exercise exercise;

    private WorkoutExercise exerciseSelectedInGraph;

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

        exercise = new ServiceRead(InMemoryDb.getInstance()).getExercise(exerciseRef);

        picDifficulty.setImageResource(ViewUtil.getDifficultyPic_M(exercise.getRef().getDifficulty()));
        final WorkoutExercise bestPerformance = exercise.getBestPerformance();
        final String bestPerfTotal = "" + bestPerformance.getTotal();
        textBestTotal.setText(bestPerfTotal);
        textBestReps.setText(StringUtil.getStringForReps(bestPerformance.getReps()));
        textBestDate.setText(bestPerformance.getDate().format(StringUtil.DATE_FORMATTER_DDMMYYYY));
        picBestPerformance.setImageResource(exercise.bestPerformanceIsAHotTopic() ?
                R.drawable.ic_whatshot_black_18dp : R.drawable.ic_fitness_center_black_18dp);
        textDescription.setText(ViewUtil.getExerciseDescription(this, exerciseRef));

        pager = findViewById(R.id.routine_reps_viewpager);
        pager.setAdapter(new ExerciseRoutineAdapter(getSupportFragmentManager(), exercise) {
            //
        });

        final String routineName = getIntent().getStringExtra(ArgumentConst.ROUTINE_NAME);
        final RoutineRef routineRef = StringUtils.isEmpty(routineName) ? exercise.getRoutineRefs().get(0) : RoutineRef.valueOf(routineName);
        pager.setCurrentItem(exercise.getRoutineRefs().indexOf(routineRef));

        final List<WorkoutExercise> exerciseExercises = exercise.getExercises();
        GraphUtil.configureLineGraph(chart, exerciseExercises, Collections.singletonList(this));

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
                selectWorkoutExercise();
            }
        });
    }

    private RecyclerView getExerciseListForSelectedExercise() {
        Fragment routineFragment = null;

        for (final Fragment fragment : getSupportFragmentManager().getFragments()) {
            final Bundle bundle = fragment.getArguments();
            if (bundle == null) {
                continue;
            }
            final String routineName = bundle.getString(ArgumentConst.ROUTINE_NAME);
            final RoutineRef routineRef = RoutineRef.valueOf(routineName);
            if (exerciseSelectedInGraph == null || routineRef == exerciseSelectedInGraph.getRoutine()) {
                routineFragment = fragment;
                break;
            }
        }

        return (routineFragment == null || routineFragment.getView() == null) ?
                null : routineFragment.getView().findViewById(R.id.reps_recycler_view);
    }

    private void selectWorkoutExercise() {
        final RecyclerView recyclerView = getExerciseListForSelectedExercise();
        if (recyclerView == null || recyclerView.getAdapter() == null) {
            return;
        }

        // That call will trigger refresh of list items and thus background color
        recyclerView.getAdapter().notifyDataSetChanged();

        if (exerciseSelectedInGraph == null) {
            return;
        }

        final List<WorkoutExercise> exercises = exercise.getMapRoutineToExercises().get(exerciseSelectedInGraph.getRoutine());
        final int exerciseIndex = exercises == null ? -1 : exercises.indexOf(exerciseSelectedInGraph);

        final LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
        final int firstVisiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition();
        final int lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();

        if ((exerciseIndex < firstVisiblePosition) || (exerciseIndex > lastVisiblePosition)) {
            layoutManager.scrollToPosition(exerciseIndex);
        }
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

    public WorkoutExercise getExerciseSelectedInGraph() {
        return exerciseSelectedInGraph;
    }

    @Override
    public void onExerciseSelected(final WorkoutExercise workoutExercise) {
        exerciseSelectedInGraph = workoutExercise;
        final int currentPageIndex = pager.getCurrentItem();
        final int routineIndex = exercise.getRoutineRefs().indexOf(workoutExercise.getRoutine());
        if (routineIndex != currentPageIndex) {
            pager.setCurrentItem(routineIndex);
        } else {
            selectWorkoutExercise();
        }
    }

    @Override
    public void onNothingSelected() {
        exerciseSelectedInGraph = null;
        selectWorkoutExercise();
    }

}
