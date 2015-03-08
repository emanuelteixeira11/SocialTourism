package com.tourism.social.socialtourism.utils.UI;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by emanuelteixeira on 06/03/15.
 */
public class RandomColor  {
    private ArrayList<Integer> colors;

    public RandomColor() {
        this.colors = new ArrayList<Integer>();
        this.addColor("#F44336");
        this.addColor("#3F51B5");
        this.addColor("#673AB7");
        this.addColor("#448AFF");
        this.addColor("#00BCD4");
        this.addColor("#009688");
        this.addColor("#4CAF50");
        this.addColor("#FFC107");
        this.addColor("#FF5722");
        this.addColor("#E91E63");
        this.addColor("#304FFE");
        this.addColor("#0091EA");
        this.addColor("#212121");
        this.addColor("#3E2723");
        this.addColor("#BF360C");
    }

    public void addColor(String color)
    {
        int r = Integer.valueOf(color.substring( 1, 3 ), 16 );
        int g = Integer.valueOf( color.substring( 3, 5 ), 16 );
        int b = Integer.valueOf( color.substring( 5, 7 ), 16 );
        colors.add(Color.rgb(r, g, b));
    }

    public Integer getRandomColor(){
        this.colors.trimToSize();
        Random random = new Random(Calendar.getInstance().getTimeInMillis());
        int index = random.nextInt(this.colors.size() - 1);
        return this.colors.get(index);
    }
}
