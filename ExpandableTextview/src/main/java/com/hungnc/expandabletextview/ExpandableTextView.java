package com.hungnc.expandabletextview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class ExpandableTextView extends LinearLayout {

    private View mMainLayout;
    private TextView content, moreLess;
    private int maxLine = Integer.MAX_VALUE;
    // animation
    private int collapsedHeight;
    private TimeInterpolator expandInterpolator;
    private TimeInterpolator collapseInterpolator;
    private long animationDuration = 300;

    private int moreLessGravity;
    private boolean moreLessShow;
    private int moreLessTextStyle;
    private int contentTextStyle;
    private boolean defaultExpand;

    public ExpandableTextView(Context context) {
        super(context);
        init(null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mMainLayout = inflate(getContext(), R.layout.expandable_text_view_layout, this);
        content = (TextView) findViewById(R.id.content);
        moreLess = (TextView) findViewById(R.id.moreLess);
        content.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
        moreLess.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
        content.setId(getID());
        // create default interpolators
        expandInterpolator = new AccelerateDecelerateInterpolator();
        collapseInterpolator = new AccelerateDecelerateInterpolator();
        applyXmlAttributes(attrs);
    }

    private void applyXmlAttributes(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
            try {
                maxLine = a.getInt(R.styleable.ExpandableTextView_hnc_maxLine, maxLine);
                defaultExpand = a.getBoolean(R.styleable.ExpandableTextView_hnc_defaultExpand, false);
                content.setText(a.getString(R.styleable.ExpandableTextView_hnc_text));
                if (defaultExpand) {
                    content.setMaxLines(Integer.MAX_VALUE);
                    moreLess.setText(getResources().getString(R.string.less));
                } else {
                    content.setMaxLines(maxLine);
                    moreLess.setText(getResources().getString(R.string.more));
                }
                content.setTextColor(a.getColor(R.styleable.ExpandableTextView_hnc_textColor, Color.BLACK));
                content.setTextSize(0, (float) a.getDimensionPixelSize(R.styleable.ExpandableTextView_hnc_textSize, 12));
                moreLess.setAllCaps(a.getBoolean(R.styleable.ExpandableTextView_hnc_moreLessAllCaps, true));
                moreLess.setTextColor(a.getColor(R.styleable.ExpandableTextView_hnc_moreLessTextColor, Color.BLACK));
                moreLess.setTextSize(0, (float) a.getDimensionPixelSize(R.styleable.ExpandableTextView_hnc_moreLessTextSize, 12));
                setMoreLessShow(a.getBoolean(R.styleable.ExpandableTextView_hnc_moreLessShow, true));
                setMoreLessGravity(a.getInt(R.styleable.ExpandableTextView_hnc_moreLessGravity, Gravity.LEFT));
                moreLessTextStyle = a.getInt(R.styleable.ExpandableTextView_hnc_moreLessTextStyle, Typeface.NORMAL);
                applyMoreLessStyle();
                contentTextStyle = a.getInt(R.styleable.ExpandableTextView_hnc_TextStyle, Typeface.NORMAL);
                applyStyle();
                animationDuration = (long) a.getInt(R.styleable.ExpandableTextView_hnc_animationDuration, 300);
            } finally {
                a.recycle();
            }
        }
    }

    private int getID() {
        Random generator = new Random();
        return generator.nextInt(Integer.MAX_VALUE);
    }

    public void toggle() {
        if (moreLess.getText().equals(getResources().getString(R.string.more))){
            toggle(true);
        } else{
            toggle(false);
        }
    }

    private void toggle(boolean expand) {
        if (expand) {
            expand();
            moreLess.setText(getResources().getString(R.string.less));
            return;
        }
        collapse();
        moreLess.setText(getResources().getString(R.string.more));
    }

    public TextView getContent() {
        return content;
    }

    public void setContent(TextView value) {
        content = value;
    }

    public TextView getMoreLess() {
        return moreLess;
    }

    public void setMoreLess(TextView value) {
        moreLess = value;
    }

    public int getMaxLine() {
        return maxLine;
    }

    public void setMaxLine(int value) {
        maxLine = value;
    }

    public void setText(String text) {
        content.setMaxLines(Integer.MAX_VALUE);
        content.setText(text);
        ViewTreeObserver vto = content.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = content.getViewTreeObserver();
                obs.removeOnGlobalLayoutListener(this);
                if (content.getLineCount() <= maxLine) {
                    moreLess.setVisibility(GONE);
                } else {
                    moreLess.setVisibility(VISIBLE);
                    content.setMaxLines(maxLine);
                }
            }
        });
    }

    /**
     * Expand this {@link TextView}.
     *
     * @return true if expanded, false otherwise.
     */
    public void expand() {

        // get collapsed height
        content.measure
                (
                        MeasureSpec.makeMeasureSpec(content.getMeasuredWidth(), MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                );

        collapsedHeight = content.getMeasuredHeight();

        // set maxLines to MAX Integer, so we can calculate the expanded height
        content.setMaxLines(Integer.MAX_VALUE);

        // get expanded height
        content.measure
                (
                        MeasureSpec.makeMeasureSpec(content.getMeasuredWidth(), MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                );

        final int expandedHeight = content.getMeasuredHeight();

        // animate from collapsed height to expanded height
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(collapsedHeight, expandedHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                final ViewGroup.LayoutParams layoutParams = content.getLayoutParams();
                layoutParams.height = (int) animation.getAnimatedValue();
                content.setLayoutParams(layoutParams);
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(final Animator animation) {
                // if fully expanded, set height to WRAP_CONTENT, because when rotating the device
                // the height calculated with this ValueAnimator isn't correct anymore
                final ViewGroup.LayoutParams layoutParams = content.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                content.setLayoutParams(layoutParams);
            }
        });

        // set interpolator
        valueAnimator.setInterpolator(expandInterpolator);

        // start the animation
        valueAnimator
                .setDuration(animationDuration)
                .start();

    }

    /**
     * Collapse this {@link TextView}.
     *
     * @return true if collapsed, false otherwise.
     */
    public void collapse() {
        // get expanded height
        final int expandedHeight = content.getMeasuredHeight();

        // animate from expanded height to collapsed height
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(expandedHeight, collapsedHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                final ViewGroup.LayoutParams layoutParams = content.getLayoutParams();
                layoutParams.height = (int) animation.getAnimatedValue();
                content.setLayoutParams(layoutParams);
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(final Animator animation) {
                // set maxLines to original value
                content.setMaxLines(maxLine);

                // if fully collapsed, set height to WRAP_CONTENT, because when rotating the device
                // the height calculated with this ValueAnimator isn't correct anymore
                final ViewGroup.LayoutParams layoutParams = content.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                content.setLayoutParams(layoutParams);
            }
        });

        // set interpolator
        valueAnimator.setInterpolator(collapseInterpolator);

        // start the animation
        valueAnimator
                .setDuration(animationDuration)
                .start();

    }

    /**
     * Sets the duration of the expand / collapse animation.
     *
     * @param animationDuration duration in milliseconds.
     */
    public void setAnimationDuration(final long animationDuration) {
        this.animationDuration = animationDuration;
    }

    /**
     * Sets a {@link TimeInterpolator} for expanding and collapsing.
     *
     * @param interpolator the interpolator
     */
    public void setInterpolator(final TimeInterpolator interpolator) {
        expandInterpolator = interpolator;
        collapseInterpolator = interpolator;
    }

    /**
     * Sets a {@link TimeInterpolator} for expanding.
     *
     * @param expandInterpolator the interpolator
     */
    public void setExpandInterpolator(final TimeInterpolator expandInterpolator) {
        this.expandInterpolator = expandInterpolator;
    }

    /**
     * Sets a {@link TimeInterpolator} for collpasing.
     *
     * @param collapseInterpolator the interpolator
     */
    public void setCollapseInterpolator(final TimeInterpolator collapseInterpolator) {
        this.collapseInterpolator = collapseInterpolator;
    }


    /**
    * Options
    * */
    public boolean isMoreLessShow() {
        return moreLessShow;
    }

    public void setMoreLessShow(boolean moreLessShow) {
        this.moreLessShow = moreLessShow;
        if (moreLessShow) {
            moreLess.setVisibility(VISIBLE);
        } else {
            moreLess.setVisibility(GONE);
        }
    }

    public long getAnimationDuration() {
        return animationDuration;
    }

    public int getMoreLessGravity() {
        return moreLessGravity;
    }

    public void setMoreLessGravity(int moreLessGravity) {
        int i = Gravity.LEFT;
        this.moreLessGravity = moreLessGravity;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        if (moreLessGravity != Gravity.LEFT) {
            i = Gravity.RIGHT;
        }
        params.gravity = i;
        moreLess.setLayoutParams(params);
    }

    public int getMoreLessTextStyle() {
        return moreLessTextStyle;
    }

    private void applyMoreLessStyle() {
        moreLess.setTypeface(null, moreLessTextStyle);
    }

    private void applyStyle() {
        content.setTypeface(null, contentTextStyle);
    }

    public void setMoreLessTextStyle(int textStyle) {
        moreLessTextStyle = textStyle;
        applyMoreLessStyle();
    }

    public int getContentTextStyle() {
        return contentTextStyle;
    }

    public void setContentTextStyle(int contentTextStyle) {
        this.contentTextStyle = contentTextStyle;
        applyStyle();
    }

    public void setTextColor(int color) {
        content.setTextColor(color);
    }

    public void setMoreLessColor(int color) {
        moreLess.setTextColor(color);
    }

    public void setMoreLessTextSize(int size) {
        moreLess.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) size);
    }

    public void setTextSize(int size) {
        content.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) size);
    }

    public void setMoreLessAllCaps(boolean allCaps) {
        moreLess.setAllCaps(allCaps);
    }

    public void setContentClick(OnClickListener listener) {
        if (listener != null) {
            content.setOnClickListener(listener);
        }
    }

    public boolean isDefaultExpand() {
        return defaultExpand;
    }
}
