package cr.ac.una.relojuna.util;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.util.Duration;

public final class FXAnimator {

    private static final Duration DEFAULT_DURATION = Duration.millis(300);

    private FXAnimator() {
    }

    public static void fadeIn(Node node) {
        fadeIn(node, DEFAULT_DURATION);
    }

    public static void fadeIn(Node node, Duration duration) {
        node.setOpacity(0);
        FadeTransition ft = new FadeTransition(duration, node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    public static void fadeOut(Node node) {
        fadeOut(node, DEFAULT_DURATION);
    }

    public static void fadeOut(Node node, Duration duration) {
        FadeTransition ft = new FadeTransition(duration, node);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();
    }

    public static void slideInFromLeft(Node node, double distance) {
        slideInFromLeft(node, distance, DEFAULT_DURATION);
    }

    public static void slideInFromLeft(Node node, double distance, Duration duration) {
        node.setTranslateX(-distance);
        TranslateTransition tt = new TranslateTransition(duration, node);
        tt.setFromX(-distance);
        tt.setToX(0);
        tt.setInterpolator(Interpolator.EASE_OUT);
        tt.play();
    }

    public static void slideInFromRight(Node node, double distance) {
        slideInFromRight(node, distance, DEFAULT_DURATION);
    }

    public static void slideInFromRight(Node node, double distance, Duration duration) {
        node.setTranslateX(distance);
        TranslateTransition tt = new TranslateTransition(duration, node);
        tt.setFromX(distance);
        tt.setToX(0);
        tt.setInterpolator(Interpolator.EASE_OUT);
        tt.play();
    }

    public static void slideInFromTop(Node node, double distance) {
        slideInFromTop(node, distance, DEFAULT_DURATION);
    }

    public static void slideInFromTop(Node node, double distance, Duration duration) {
        node.setTranslateY(-distance);
        TranslateTransition tt = new TranslateTransition(duration, node);
        tt.setFromY(-distance);
        tt.setToY(0);
        tt.setInterpolator(Interpolator.EASE_OUT);
        tt.play();
    }

    public static void slideInFromBottom(Node node, double distance) {
        slideInFromBottom(node, distance, DEFAULT_DURATION);
    }

    public static void slideInFromBottom(Node node, double distance, Duration duration) {
        node.setTranslateY(distance);
        TranslateTransition tt = new TranslateTransition(duration, node);
        tt.setFromY(distance);
        tt.setToY(0);
        tt.setInterpolator(Interpolator.EASE_OUT);
        tt.play();
    }

    public static void slideOutToLeft(Node node, double distance) {
        slideOutToLeft(node, distance, DEFAULT_DURATION);
    }

    public static void slideOutToLeft(Node node, double distance, Duration duration) {
        TranslateTransition tt = new TranslateTransition(duration, node);
        tt.setToX(-distance);
        tt.setInterpolator(Interpolator.EASE_IN);
        tt.play();
    }

    public static void slideOutToRight(Node node, double distance) {
        slideOutToRight(node, distance, DEFAULT_DURATION);
    }

    public static void slideOutToRight(Node node, double distance, Duration duration) {
        TranslateTransition tt = new TranslateTransition(duration, node);
        tt.setToX(distance);
        tt.setInterpolator(Interpolator.EASE_IN);
        tt.play();
    }

    public static void slideOutToTop(Node node, double distance) {
        slideOutToTop(node, distance, DEFAULT_DURATION);
    }

    public static void slideOutToTop(Node node, double distance, Duration duration) {
        TranslateTransition tt = new TranslateTransition(duration, node);
        tt.setToY(-distance);
        tt.setInterpolator(Interpolator.EASE_IN);
        tt.play();
    }

    public static void slideOutToBottom(Node node, double distance) {
        slideOutToBottom(node, distance, DEFAULT_DURATION);
    }

    public static void slideOutToBottom(Node node, double distance, Duration duration) {
        TranslateTransition tt = new TranslateTransition(duration, node);
        tt.setToY(distance);
        tt.setInterpolator(Interpolator.EASE_IN);
        tt.play();
    }

    public static void fadeSlideInFromBottom(Node node, double distance) {
        fadeSlideInFromBottom(node, distance, DEFAULT_DURATION);
    }

    public static void fadeSlideInFromBottom(Node node, double distance, Duration duration) {
        node.setOpacity(0);
        node.setTranslateY(distance);

        FadeTransition ft = new FadeTransition(duration, node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);

        TranslateTransition tt = new TranslateTransition(duration, node);
        tt.setFromY(distance);
        tt.setToY(0);
        tt.setInterpolator(Interpolator.EASE_OUT);

        new ParallelTransition(ft, tt).play();
    }

    public static void fadeSlideOutToTop(Node node, double distance) {
        fadeSlideOutToTop(node, distance, DEFAULT_DURATION);
    }

    public static void fadeSlideOutToTop(Node node, double distance, Duration duration) {
        FadeTransition ft = new FadeTransition(duration, node);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);

        TranslateTransition tt = new TranslateTransition(duration, node);
        tt.setToY(-distance);
        tt.setInterpolator(Interpolator.EASE_IN);

        new ParallelTransition(ft, tt).play();
    }
}
