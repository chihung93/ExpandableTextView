package com.hungnc.company.expandabletextview;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hungnc.expandabletextview.ExpandableTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ExpandableTextView textView = (ExpandableTextView) findViewById(R.id.textview2);
        textView.setText("Lorem Ipsum ch\u1ec9 \u0111\u01a1n gi\u1ea3n l\u00e0 m\u1ed9t \u0111o\u1ea1n v\u0103n b\u1ea3n gi\u1ea3, \u0111\u01b0\u1ee3c d\u00f9ng v\u00e0o vi\u1ec7c tr\u00ecnh b\u00e0y v\u00e0 d\u00e0n trang ph\u1ee5c v\u1ee5 cho in \u1ea5n. Lorem Ipsum \u0111\u00e3 \u0111\u01b0\u1ee3c s\u1eed d\u1ee5ng nh\u01b0 m\u1ed9t v\u0103n b\u1ea3n chu\u1ea9n cho ng\u00e0nh c\u00f4ng nghi\u1ec7p in \u1ea5n t\u1eeb nh\u1eefng n\u0103m 1500, khi m\u1ed9t h\u1ecda s\u0129 v\u00f4 danh gh\u00e9p nhi\u1ec1u \u0111o\u1ea1n v\u0103n b\u1ea3n v\u1edbi nhau \u0111\u1ec3 t\u1ea1o th\u00e0nh m\u1ed9t b\u1ea3n m\u1eabu v\u0103n b\u1ea3n. \u0110o\u1ea1n v\u0103n b\u1ea3n n\u00e0y kh\u00f4ng nh\u1eefng \u0111\u00e3 t\u1ed3n t\u1ea1i n\u0103m th\u1ebf k\u1ec9, m\u00e0 khi \u0111\u01b0\u1ee3c \u00e1p d\u1ee5ng v\u00e0o tin h\u1ecdc v\u0103n ph\u00f2ng, n\u1ed9i dung c\u1ee7a n\u00f3 v\u1eabn kh\u00f4ng h\u1ec1 b\u1ecb thay \u0111\u1ed5i. N\u00f3 \u0111\u00e3 \u0111\u01b0\u1ee3c ph\u1ed5 bi\u1ebfn trong nh\u1eefng n\u0103m 1960 nh\u1edd vi\u1ec7c b\u00e1n nh\u1eefng b\u1ea3n gi\u1ea5y Letraset in nh\u1eefng \u0111o\u1ea1n Lorem Ipsum, v\u00e0 g\u1ea7n \u0111\u00e2y h\u01a1n, \u0111\u01b0\u1ee3c s\u1eed d\u1ee5ng trong c\u00e1c \u1ee9ng d\u1ee5ng d\u00e0n trang, nh\u01b0 Aldus PageMaker.");
        textView.setMoreLessGravity(Gravity.LEFT);
        textView.setMaxLine(5);
        textView.setTextColor(Color.GREEN);
        textView.setMoreLessColor(Color.CYAN);
        textView.setTextSize(15);
        textView.setMoreLessTextSize(14);
        textView.setMoreLessAllCaps(false);
        textView.setContentTextStyle(Typeface.ITALIC);
        textView.setMoreLessTextStyle(Typeface.BOLD_ITALIC);
        textView.setContentClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.toggle();
            }
        });
        final ExpandableTextView textView2 = new ExpandableTextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(20, 20, 20, 20);
        textView2.setLayoutParams(layoutParams);
        textView2.setText("Lorem Ipsum is slechts een proeftekst uit het drukkerij- en zetterijwezen. Lorem Ipsum is de standaard proeftekst in deze bedrijfstak sinds de 16e eeuw, toen een onbekende drukker een zethaak met letters nam en ze door elkaar husselde om een font-catalogus te maken. Het heeft niet alleen vijf eeuwen overleefd maar is ook, vrijwel onveranderd, overgenomen in elektronische letterzetting. Het is in de jaren '60 populair geworden met de introductie van Letraset vellen met Lorem Ipsum passages en meer recentelijk door desktop publishing software zoals Aldus PageMaker die versies van Lorem Ipsum bevatten.");
        textView2.setMoreLessGravity(Gravity.RIGHT);
        textView2.setMaxLine(3);
        textView2.setTextColor(Color.rgb(244, 66, 212));
        textView2.setMoreLessColor(Color.rgb(244, 131, 65));
        textView2.setTextSize(20);
        textView2.setMoreLessTextSize(19);
        textView2.setMoreLessAllCaps(true);
        textView2.setContentTextStyle(Typeface.NORMAL);
        textView2.setMoreLessTextStyle(Typeface.BOLD_ITALIC);
        textView2.setContentClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // do nothing
            }
        });
        ((LinearLayout) findViewById(R.id.root)).addView(textView2);
    }
}
