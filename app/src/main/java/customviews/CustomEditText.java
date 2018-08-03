package customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class CustomEditText extends EditText {
    public CustomEditText(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        Typeface type2 = Typeface.createFromAsset(context.getAssets(),"fonts/morvarid.ttf");
        this.setTypeface(type2,Typeface.NORMAL);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
}
