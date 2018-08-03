package customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView4 extends TextView {
    public CustomTextView4(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        Typeface type2 = Typeface.createFromAsset(context.getAssets(),"fonts/morvarid.ttf");
        this.setTypeface(type2,Typeface.NORMAL);
    }

    public CustomTextView4(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomTextView4(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
}
