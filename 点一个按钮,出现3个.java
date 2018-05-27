<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="
    android:id="@+id/relativeLayout1"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent" >
    <Button
        android:id="@+id/btA"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerVertical="true"
        android:layout_centerHorizontal="true"
        android:text="按钮A" />
    <Button
        android:id="@+id/btB"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerVertical="true"
        android:layout_toLeftOf="@id/btA"
        android:text="按钮B" />
    <Button
        android:id="@+id/btD"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerVertical="true"
        android:layout_toRightOf="@id/btA"
        android:text="按钮D" />
    <Button
        android:id="@+id/btC"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_above="@+id/btA"
        android:layout_toLeftOf="@+id/btD"
        android:text="按钮C" />
</RelativeLayout> 
  
 TestActivity:
package com.example.androidtest;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
public class TestActivity extends Activity {
    private Button btA;
    private Button btB;
    private Button btC;
    private Button btD;
    private boolean isVisible = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        btA = (Button) findViewById(R.id.btA);
        btB = (Button) findViewById(R.id.btB);
        btC = (Button) findViewById(R.id.btC);
        btD = (Button) findViewById(R.id.btD);
        btB.setVisibility(View.INVISIBLE);
        btC.setVisibility(View.INVISIBLE);
        btD.setVisibility(View.INVISIBLE);
        btA.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible) {
                    btB.setVisibility(View.VISIBLE);
                    btC.setVisibility(View.VISIBLE);
                    btD.setVisibility(View.VISIBLE);
                    isVisible = false;
                } else {
                    btB.setVisibility(View.INVISIBLE);
                    btC.setVisibility(View.INVISIBLE);
                    btD.setVisibility(View.INVISIBLE);
                    isVisible = true;
                }
            }
        });
    }
}