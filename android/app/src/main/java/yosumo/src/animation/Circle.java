package yosumo.src.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import yosumo.src.R;

/**
 * Created by a-pol_000 on 10/29/2016.
 */
public class Circle extends View {

    private static final int START_ANGLE_POINT = 90;

    private final Paint paint;
    private final RectF rect;

    private float angle;

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);

        final int strokeWidth = 40;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        //Circle color
       //paint.setColor(Color.parseColor(R.color.palette1_orange+""));
       paint.setColor(Color.RED);
        //size 200x200 example
        rect = new RectF(strokeWidth, strokeWidth, 200 + strokeWidth, 200 + strokeWidth);

        //Initial Angle (optional, it can be zero)
        angle = 120;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rect, START_ANGLE_POINT, angle, false, paint);
    }

    /**
     *
     * @param tag
     */
    public void setColor(String tag){
        if(tag.equalsIgnoreCase("orange")){
            //paint.setColor(Color.parseColor(R.color.palette1_orange+""));
            paint.setColor(Color.RED);
        }else if(tag.equalsIgnoreCase("blue")){
            //paint.setColor(Color.parseColor(R.color.palette1_blue+"")); // revisar el azul en illustrator
            paint.setColor(Color.BLUE);
        }else{
            Log.d("Not find ", tag);
        }
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}