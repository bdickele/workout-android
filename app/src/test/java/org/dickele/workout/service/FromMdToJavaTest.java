package org.dickele.workout.service;

import org.dickele.workout.parser.FromMdToJava;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class FromMdToJavaTest {

    @Test
    public void extractReps_1() {
        assertThat(FromMdToJava.extractReps("0")).isEqualTo(Collections.singletonList(0));
        assertThat(FromMdToJava.extractReps("6, 5, 4")).isEqualTo(Arrays.asList(6, 5, 4));
        assertThat(FromMdToJava.extractReps(" 6,5,4 ")).isEqualTo(Arrays.asList(6, 5, 4));
    }

    @Test
    public void extractReps_2() {
        assertThat(FromMdToJava.extractReps("3x5")).isEqualTo(Arrays.asList(5, 5, 5));
        assertThat(FromMdToJava.extractReps(" 3x5 ")).isEqualTo(Arrays.asList(5, 5, 5));
    }

    @Test
    public void extractReps_3() {
        assertThat(FromMdToJava.extractReps("3x6 5")).isEqualTo(Arrays.asList(6, 6, 6, 5));
        assertThat(FromMdToJava.extractReps("6, 3x2 4")).isEqualTo(Arrays.asList(6, 2, 2, 2, 4));
        assertThat(FromMdToJava.extractReps("6, 3x2 4 5")).isEqualTo(Arrays.asList(6, 2, 2, 2, 4, 5));
        assertThat(FromMdToJava.extractReps("6, 4 5")).isEqualTo(Arrays.asList(6, 4, 5));
        assertThat(FromMdToJava.extractReps("6 3 x 2 4 5")).isEqualTo(Arrays.asList(6, 2, 2, 2, 4, 5));
        assertThat(FromMdToJava.extractReps("6, 3 x 2 4 5")).isEqualTo(Arrays.asList(6, 2, 2, 2, 4, 5));
    }

    @Test
    public void extractReps_4() {
        assertThat(FromMdToJava.extractReps(null)).isEqualTo(Collections.singletonList(0));
        assertThat(FromMdToJava.extractReps(" ")).isEqualTo(Collections.singletonList(0));
        assertThat(FromMdToJava.extractReps("")).isEqualTo(Collections.singletonList(0));
    }
}
