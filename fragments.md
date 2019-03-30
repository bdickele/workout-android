# Liste des sessions (sur ViewPager principal)

- fragment_routine_workouts / RoutineListFragment 
    - routines_recycler_view = liste des routines (puisqu'on groupe par routine)
        - fragment_routine_workouts_item = fragment regroupant la liste des sessions d'une routine
            - routine_workouts_recycler_view = liste des sessions
                - fragment_routine_workout_item = une session

## Fiche session (depuis liste des sessions)

- activity_workout
    - workout_main_viewpager = viewpager pour passer d'une session à l'autre
        - fragment_workout / WorkoutFragment
            - workout_exercises_frame_layout : FrameLayout dans lequel on mettra les exercices
            - fragment_workout_exercises / WorkoutExercisesFragment = fragment des exercices de la session
                - workout_exercises_recycler_view = liste des exercices 
                    - fragment_workout_exercise_item = une répétition d'un exercice de la session

## Fiche routine (depuis liste des sessions)

Affiche les différentes sessions pour chaque exercice durant la routine

- activity_routine
    - routine_viewpager = viewpager pour passer d'un exercice (de la routine) à l'autre
        - fragment_routine / RoutineFragment = vue d'une routine avec un exercice
            - exercise_reps_layout : FrameLayout dans lequel on mettra l'exercice et ses séries
            - fragment_exercise_reps / RoutineExerciseRepsFragment = fragment qui contient les  séries de l'exercice
                - exercise_reps_recycler_view = liste des séries de l'exercice
                    - fragment_routine_exercise_reps_item = une répétition de l'exercice (pour la routine courante)
   
# Liste des exercices (sur ViewPager principal)

Liste de tous les exercices pratiqués au moins une fois

- fragment_exercise_list / ExerciseListFragment
    - exercises_recycler_view = liste des exercices
        - fragment_exercise_list_item = un exercice (nom, difficulté, record)
        
## Fiche exercice

Toutes les sessions d'un exercice

- activity_exercise
    - exercise_viewpager = viewpager pour passer d'un exercice à l'autre
        - fragment_exercise / ExerciseFragment = vue complète d'un exercice
            - exercise_reps_layout = FrameLayout pour chaque série de l'exercice
            - fragment_exercise_reps.xml / ExerciseRepsFragment = fragment qui contient les  séries de l'exercice
                - exercise_reps_recycler_view = liste des séries
                    - fragment_exercise_reps_item = une série de l'exercice