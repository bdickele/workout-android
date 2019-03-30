package org.dickele.workout.activity.exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;

import org.dickele.workout.R;
import org.dickele.workout.activity.exercise.exercise.ExerciseRepsFragment;
import org.dickele.workout.data.Exercise;
import org.dickele.workout.data.WorkoutExercise;
import org.dickele.workout.reference.ExerciseRef;
import org.dickele.workout.repository.InMemoryDb;
import org.dickele.workout.service.ServiceRead;
import org.dickele.workout.util.GraphUtil;
import org.dickele.workout.util.StringUtil;
import org.dickele.workout.util.ViewUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ExerciseFragment extends Fragment {

    public static final String EXERCISE_NAME = "EXERCISE_NAME";

    private ExerciseRepsFragment exercisesFragment;

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

    @BindView(R.id.exercise_graph)
    GraphView graphView;

    public ExerciseFragment() {
        //
    }

    static ExerciseFragment newInstance(final Exercise exercise) {
        final Bundle args = new Bundle();
        args.putString(EXERCISE_NAME, exercise.getRef().name());

        final ExerciseFragment frag = new ExerciseFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        ButterKnife.bind(this, view);

        final ExerciseRef exerciseRef = ExerciseRef.valueOf(getArguments().getString(ExerciseFragment.EXERCISE_NAME));
        final Exercise exercise = new ServiceRead(InMemoryDb.getInstance()).getExercise(exerciseRef);
        final List<WorkoutExercise> exerciseExercises = exercise.getExercises();

        picDifficulty.setImageResource(getDifficultyPic(exercise.getRef().getDifficulty()));
        final WorkoutExercise bestPerformance = exercise.getBestPerformance();
        textBestTotal.setText("" + bestPerformance.getTotal());
        textBestReps.setText(StringUtil.getStringForReps(bestPerformance.getReps()));
        textBestDate.setText(bestPerformance.getDate().format(StringUtil.DATE_FORMATTER_DDMMYYYY));
        picBestPerformance.setImageResource(exercise.bestPerformanceIsAHotTopic() ?
                R.mipmap.baseline_whatshot_black_18 : R.mipmap.baseline_fitness_center_black_18);
        textDescription.setText(ViewUtil.getExerciseDescription(view.getContext(), exerciseRef));
        GraphUtil.configureExercisesGraph(graphView, getActivity(), exerciseExercises, false);

        configureAndShowExercisesFragment();

        exercisesFragment.updateExercises(exerciseExercises);

        return view;
    }

    private void configureAndShowExercisesFragment() {
        exercisesFragment = (ExerciseRepsFragment) getChildFragmentManager().findFragmentById(R.id.exercise_reps_layout);
        if (exercisesFragment == null) {
            exercisesFragment = new ExerciseRepsFragment();
            getChildFragmentManager().beginTransaction()
                    .add(R.id.exercise_reps_layout, exercisesFragment)
                    .commit();
        }
    }

    //TODO Mettre en commun
    private int getDifficultyPic(final int difficulty) {
        switch (difficulty) {
            case 1:
                return R.mipmap.baseline_looks_1_black_18;
            case 2:
                return R.mipmap.baseline_looks_2_black_18;
            case 3:
                return R.mipmap.baseline_looks_3_black_18;
            case 4:
                return R.mipmap.baseline_looks_4_black_18;
            default:
                return R.mipmap.baseline_looks_1_black_18;
        }
    }
}
