package ru.valentin_gordienko.loftmoney;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class DiagramView extends View {

    private int income;
    private int consumption;

    private Paint incomePaint = new Paint();
    private Paint consumptionPaint = new Paint();

    public DiagramView(Context context) {
        this(context, null);
    }

    public DiagramView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiagramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        incomePaint.setColor(ContextCompat.getColor(context, R.color.appleGreen));
        consumptionPaint.setColor(ContextCompat.getColor(context, R.color.darkSkyBlue));
        if(isInEditMode()){
            income = 74000;
            consumption = 5400;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (consumption + income == 0)
            return;

        float expenseAngle = 360.f * consumption / (consumption + income);
        float incomeAngle = 360.f * income / (consumption + income);

        int space = 10; // space between income and expense
        int size = Math.min(getWidth(), getHeight()) - space * 2;
        int xMargin = (getWidth() - size) / 2;
        int yMargin = (getHeight() - size) / 2;


        canvas.drawArc(
                xMargin - space,
                yMargin,
                getWidth() - xMargin - space,
                getHeight() - yMargin,
                180 - expenseAngle / 2,
                expenseAngle,
                true,
                consumptionPaint
        );

        canvas.drawArc(
                xMargin + space, yMargin,
                getWidth() - xMargin + space,
                getHeight() - yMargin,
                360 - incomeAngle / 2, incomeAngle,
                true,
                incomePaint
        );

    }
}
