package utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class PaintView extends View {

    private int BACKGROUND_COLOR = Color.BLACK;

    private Paint paint;
    private Path path;


    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        path = new Path();

        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawPath(path, paint);



    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float posX = event.getX();
        float posY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(posX, posY);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(posX, posY);
                break;
            case MotionEvent.ACTION_UP:
                clear();
                break;

            default:
                return false;
        }

        invalidate();
        return true;
    }

    public void clear() {

        path.reset(); // clear all draw
        invalidate();
    }


}
