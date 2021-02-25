package com.example.tarisiorase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tarisiorase.models.Country;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {
    private LinearLayout layout;
    ArrayList<Country> rsListPopulation=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        layout = findViewById(R.id.layout);


        if(getIntent().getParcelableArrayListExtra(ShowCountriesActivity.RESULT_SEARCH_BY_POPULATION)!=null){
            rsListPopulation=getIntent().getParcelableArrayListExtra(ShowCountriesActivity.RESULT_SEARCH_BY_POPULATION);
            Toast.makeText(getApplicationContext(),rsListPopulation.toString(),Toast.LENGTH_SHORT).show();
        }

        long[] populationValues=getNrPopulationFromList(rsListPopulation);


        layout.addView(new PieChartView(this, getGraphicData(populationValues)));
    }

    private long[] getNrPopulationFromList(ArrayList<Country> list){
        long[] populationValues=new long[list.size()];
        for(int i=0;i<list.size();i++){
            populationValues[i]=list.get(i).getPopulation();
        }
        return populationValues;
    }

    private float[] getGraphicData(long[] values){
        float total = 0;
        float[] pieValues = new float[values.length];
        for(int i=0;i<values.length;i++){
            total += values[i];
        }
        for(int i=0; i<values.length;i++){
            pieValues[i]= 360 * (values[i] / total);
        }
        return  pieValues;
    }

    public class PieChartView extends View {

        private float[] valuesDegree;
        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private int[] COLORS = {Color.BLUE, Color.GREEN, Color.GRAY, Color.YELLOW};
        private RectF rectangle = new RectF (10,10,500,500);
        private int tmp =0;



        public PieChartView(Context context, float[] values){
            super(context);
            valuesDegree = new float[values.length];
            for(int i=0; i<values.length;i++){
                valuesDegree[i] = values[i];
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            for(int i=0;i<valuesDegree.length; i++){
                paint.setColor(COLORS[i]);
                if(i==0){
                    canvas.drawArc(rectangle, 0,valuesDegree[i],true, paint);
                    paint.setColor(Color.BLACK);
                    paint.setTextSize(50);
                    canvas.drawText("titlu", 10,10,paint);
                }else {
                    tmp += (int) valuesDegree[i-1];
                    canvas.drawArc(rectangle, tmp, valuesDegree[i], true, paint);
                }
            }
        }
    }
}
