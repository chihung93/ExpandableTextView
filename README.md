# ExpandableTextView

An expandable TextView for Android applications **4.1 (JELLY_BEAN).

# Introduce

Hello you guys,This is a library for text collapse/expand.

This is the first time ,I created lib for myself and share this idea for everybody needed .
Please post your issues to me if there is an error

I have reference through the following library : 
https://github.com/Blogcat/Android-ExpandableTextView

# DEMO
![](https://github.com/chihung93/ExpandableTextView/blob/master/demo.gif)

# Add Lib
Currently, I have not yet purchased the account to upload it to the cloud (marven and jcenter - I'm looking for host files for free because I do not have enough money), so sorry for the inconvenience.
You can copy this module to import your project like this : 

`compile project(':ExpandableTextview')`


# USAGE 

Don't forget add this line in layout 

`xmlns:hnc="http://schemas.android.com/apk/res-auto"`


# Use it in your layout xml.


| Attrs                 | Types                 | Values     | Description                                                                         |
|-----------------------|-----------------------|------------|-------------------------------------------------------------------------------------|
| hnc_text              | string                | text       | Lorem Ipsum is simply du                                                            |
| hnc_maxLine           | integer               | number     | Makes the Content be at most this many lines tall.                                  |
| hnc_textColor         | color                 | integer    | Text color of Content                                                               |
| hnc_moreLessTextColor | color                 | integer    | Text color of More/Less                                                             |
| hnc_textSize          | dimension             | integer    | Size of the text in Content.                                                        |
| hnc_moreLessTextSize  | dimension             | integer    | Size of the More/Less.                                                              |
| hnc_moreLessShow      | boolean               | true/false | Set the show/hide state of More/Less.                                               |
| hnc_moreLessGravity   | hnc_moreLessGravity   | integer    | Gravity.LEFT and Gravity.RIGHT                                                      |
| hnc_animationDuration | hnc_animationDuration | integer    | Amount of time for the animation to run                                             |
| hnc_moreLessAllCaps   | hnc_moreLessAllCaps   | boolean    | Sets the properties of this Text (More/Less) to transform input to ALL CAPS display |
| hnc_moreLessTextStyle | hnc_moreLessTextStyle | integer    | Style (bold, italic, bold italic) for the text (More/Less).                         |
| hnc_TextStyle         | hnc_TextStyle         | integer    | Style (bold, italic, bold italic) for the text (Content).                           |
| hnc_defaultExpand     | hnc_defaultExpand     | boolean    | Only available in XML Layout, true = default content will open and vice versa       |




# Use it in your Code.

```

final ExpandableTextView textView2 = new ExpandableTextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(20, 20, 20, 20);
        textView2.setLayoutParams(layoutParams);
        textView2.setText("Lorem Ipsum is sle");
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
        textView2.setAnimationDuration(1000);
        textView2.setCollapseInterpolator( new AccelerateDecelerateInterpolator());
        textView2.setExpandInterpolator( new AccelerateDecelerateInterpolator());
        ((LinearLayout) findViewById(R.id.root)).addView(textView2);
        
```

# DEVELOP BY
[Chi Hung](https://github.com/chihung93)

