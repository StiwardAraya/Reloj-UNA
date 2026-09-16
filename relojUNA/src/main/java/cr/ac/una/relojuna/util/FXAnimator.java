package cr.ac.una.relojuna.util;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    //Cumpleaños
    public static void felizCumpleanos(Node node, double x, double y) {
        SFXPlayer.reproducir("cumple.mp3");
        Pane pane = (Pane) node;

        Canvas canvas = new Canvas(pane.getWidth(), pane.getHeight());
        canvas.setMouseTransparent(true);
        pane.getChildren().add(canvas);

        Paint[] colors = new Paint[181];
        colors[0] = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.WHITE),
                new Stop(0.2, Color.hsb(59, 0.38, 1)),
                new Stop(0.6, Color.hsb(59, 0.38, 1, 0.1)),
                new Stop(1, Color.hsb(59, 0.38, 1, 0)));
        for (int h = 0; h < 360; h += 2) {
            colors[1 + (h / 2)] = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.WHITE),
                    new Stop(0.2, Color.hsb(h, 1, 1)),
                    new Stop(0.6, Color.hsb(h, 1, 1, 0.1)),
                    new Stop(1, Color.hsb(h, 1, 1, 0)));
        }

        List<FWParticle> particles = new ArrayList<>();
        int[] countdown = {20};
        long[] startTime = {-1};

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (startTime[0] == -1) {
                    startTime[0] = now;
                }

                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                Iterator<FWParticle> iter = particles.iterator();
                List<FWParticle> newParticles = new ArrayList<>();
                while (iter.hasNext()) {
                    FWParticle p = iter.next();
                    if (p.update()) {
                        iter.remove();
                        if (p.shouldExplodeChildren) {
                            if (p.size == 9) {
                                fwExplodeCircle(p, newParticles, colors);
                            } else if (p.size == 8) {
                                fwExplodeSmallCircle(p, newParticles);
                            }
                        }
                    }
                    p.draw(gc);
                }
                particles.addAll(newParticles);

                if (countdown[0] == 0) {
                    countdown[0] = 10 + (int) (Math.random() * 30);
                    particles.add(new FWParticle(
                            x, y,
                            Math.random() * 5 - 2.5, 0,
                            0, -(80 + Math.random() * 100),
                            colors[0], 9,
                            false, true, true));
                }
                countdown[0]--;

                double hue = (now / 10_000_000L) % 360;
                Font font = Font.font("Arial", FontWeight.BOLD, 52);
                gc.setFont(font);
                String texto = "¡Feliz Cumpleaños!";
                Text helper = new Text(texto);
                helper.setFont(font);
                double tw = helper.getBoundsInLocal().getWidth();
                double th = helper.getBoundsInLocal().getHeight();
                double pad = 18;
                double rx = x - tw / 2 - pad;
                double ry = y - 50 - th + 8 - pad;
                double rw = tw + pad * 2;
                double rh = th + pad * 2 - 8;
                gc.setGlobalAlpha(1.0);
                gc.setFill(Color.WHITE);
                gc.fillRoundRect(rx, ry + 10, rw, rh, 20, 20);
                gc.setFill(Color.hsb(hue, 1, 0.85));
                gc.fillText(texto, x - tw / 2, y - 50);

                if (now - startTime[0] > 10_000_000_000L) {
                    stop();
                    SFXPlayer.detener();
                    pane.getChildren().remove(canvas);
                }
            }
        };
        timer.start();
    }

    private static void fwExplodeCircle(FWParticle firework, List<FWParticle> newParticles, Paint[] colors) {
        final int count = 20 + (int) (60 * Math.random());
        final boolean shouldExplodeChildren = Math.random() > 0.5;
        final double angle = (Math.PI * 2) / count;
        final int color = (int) (Math.random() * colors.length);
        for (int i = count; i > 0; i--) {
            double vel = 8 + Math.random() * 8;
            double particleAngle = i * angle;
            newParticles.add(new FWParticle(
                    firework.posX, firework.posY,
                    Math.cos(particleAngle) * vel, Math.sin(particleAngle) * vel,
                    0, 0, colors[color], 8,
                    true, shouldExplodeChildren, true));
        }
    }

    private static void fwExplodeSmallCircle(FWParticle firework, List<FWParticle> newParticles) {
        final double angle = (Math.PI * 2) / 12;
        for (int count = 12; count > 0; count--) {
            double vel = 4 + Math.random() * 4;
            double particleAngle = count * angle;
            newParticles.add(new FWParticle(
                    firework.posX, firework.posY,
                    Math.cos(particleAngle) * vel, Math.sin(particleAngle) * vel,
                    0, 0, firework.color, 4,
                    true, false, false));
        }
    }

    //clase necesaria para los fireworks
    private static class FWParticle {

        private static final double GRAVITY = 0.06;
        double alpha, fade, posX, posY, velX, velY, lastPosX, lastPosY;
        final double easing, targetY;
        final Paint color;
        final int size;
        final boolean usePhysics, shouldExplodeChildren, hasTail;

        FWParticle(double posX, double posY, double velX, double velY,
                double targetX, double targetY, Paint color, int size,
                boolean usePhysics, boolean shouldExplodeChildren, boolean hasTail) {
            this.posX = posX;
            this.posY = posY;
            this.velX = velX;
            this.velY = velY;
            this.targetY = targetY;
            this.color = color;
            this.size = size;
            this.usePhysics = usePhysics;
            this.shouldExplodeChildren = shouldExplodeChildren;
            this.hasTail = hasTail;
            this.alpha = 1;
            this.easing = Math.random() * 0.02;
            this.fade = Math.random() * 0.03;
        }

        boolean update() {
            lastPosX = posX;
            lastPosY = posY;
            if (usePhysics) {
                velY += GRAVITY;
                posY += velY;
                alpha -= fade;
            } else {
                double distance = targetY - posY;
                posY += distance * (0.03 + easing);
                alpha = Math.min(distance * distance * 0.00005, 1);
            }
            posX += velX;
            return alpha < 0.005;
        }

        void draw(GraphicsContext gc) {
            gc.setGlobalAlpha(alpha);
            gc.setFill(color);
            gc.fillOval(posX - size, posY - size, size * 2, size * 2);
            if (hasTail) {
                double xVel = (posX - lastPosX) * -5;
                double yVel = (posY - lastPosY) * -5;
                gc.setFill(Color.rgb(255, 255, 255, 0.3));
                gc.fillPolygon(
                        new double[]{posX + 1.5, posX + xVel, posX - 1.5},
                        new double[]{posY, posY + yVel, posY}, 3);
            }
        }
    }
}
