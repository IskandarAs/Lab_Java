package com.rumato.lab2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;

public class Lab2Activity extends AppCompatActivity {

    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, Lab2Activity.class);
    }

    private static final String STATE_VIEWS_COUNT = "views_count";

    private Lab2ViewsContainer lab2ViewsContainer;
    private Button button;
    private Button button2;
    private Button button3;
    private ScrollView scroll;
    private FrameLayout frame;

    @SuppressLint("StringFormatInvalid")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2);

        setTitle(getString(R.string.lab2_title, getClass().getSimpleName()));
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        scroll = (ScrollView) findViewById(R.id.scroll_view);
        frame = (FrameLayout) findViewById(R.id.frame);

        // findViewById - generic метод https://docs.oracle.com/javase/tutorial/extra/generics/methods.html,
        // который автоматически кастит (class cast) View в указанный класс.
        // Тип вью, в которую происходит каст, не проверяется, поэтому если здесь указать View,
        // отличную от View в XML, то приложение крашнется на вызове этого метода.
        lab2ViewsContainer = findViewById(R.id.container);
        findViewById(R.id.btn_add_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lab2ViewsContainer.incrementViews();
            }
        });

        // Восстанавливаем состояние нашего View, добавляя заново все View
        if (savedInstanceState != null) {
            lab2ViewsContainer.setViewsCount(savedInstanceState.getInt(STATE_VIEWS_COUNT));
        }

        scroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                button.setVisibility(View.VISIBLE);
                i = button.getTop();
                i1 = scroll.getTop();
                i2 = frame.getTop();
                i3 = scroll.getScrollY();
                i3 = i3-5000;
                int j = button.getBottom();
                int j1 = frame.getBottom();
                if (i - i1 <= i3){
                    button.setTranslationY(-(i - i1 + i2)); // расстояние до верхней границы скролла
                }
                if (i3 < i - i1 + i2){
                    button.setTranslationY(-i3);
                }
                if (j - j1 >= i3){
                    button.setTranslationY(frame.getHeight()-button.getHeight()-i); // расстояние до нижней границы скролла
                }

                button2.setVisibility(View.VISIBLE);
                int i4 = button2.getTop();
                int i5 = scroll.getTop();
                int i6 = frame.getTop();
                int i7 = scroll.getScrollY();
                i7 = i7-5000;
                int j2 = button2.getBottom();
                int j3 = frame.getBottom();
                if (i4 - i5 <= i7){
                    button2.setTranslationY(-(i4 - i5 + i6)); // расстояние до верхней границы скролла
                }
                if (i7 < i4 - i5 + i6){
                    button2.setTranslationY(-i7);
                }
                if (j2 - j3 >= i7){
                    button2.setTranslationY(frame.getHeight()-button2.getHeight()-i4); // расстояние до нижней границы скролла
                }

                button3.setVisibility(View.VISIBLE);
                int i8 = button3.getTop();
                int i9 = scroll.getTop();
                int i10 = frame.getTop();
                int i11 = scroll.getScrollY();
                i11 = i11-5000;
                int j4 = button3.getBottom();
                int j5 = frame.getBottom();
                if (i8 - i9 <= i11){
                    button3.setTranslationY(-(i8 - i9 + i10)); // расстояние до верхней границы скролла
                }
                if (i11 < i8 - i9 + i10){
                    button3.setTranslationY(-i11);
                }
                if (j4 - j5 >= i11){
                    button3.setTranslationY(frame.getHeight()-button3.getHeight()-i8); // расстояние до нижней границы скролла
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_VIEWS_COUNT, lab2ViewsContainer.getViewsCount());
    }
}
