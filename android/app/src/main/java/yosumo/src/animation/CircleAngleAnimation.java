package yosumo.src.animation;

import android.graphics.Color;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import yosumo.src.R;

/**
 * Created by a-pol_000 on 10/29/2016.
 */
public class CircleAngleAnimation extends Animation {

    private Circle circle;

    private float oldAngle;
    private float newAngle;

    public CircleAngleAnimation(Circle circle, int newAngle) {
        this.oldAngle = circle.getAngle();
        this.newAngle = newAngle;
        this.circle = circle;

    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float angle = oldAngle + ((newAngle - oldAngle) * interpolatedTime);

        circle.setAngle(angle);
        circle.requestLayout();
    }

    /**
     *
     * @param tag
     */
    public void setColor(String tag){
        circle.setColor(tag);
    }

}