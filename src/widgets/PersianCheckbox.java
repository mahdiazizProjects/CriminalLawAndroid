package widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class PersianCheckbox extends CheckBox {

	public PersianCheckbox(Context context) {
		super(context);
		setTypeface(convert(context, "BNazanin"));
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}
    private Typeface convert(Context context, String s)
    {
        return Typeface.createFromAsset(context.getAssets(), (new StringBuilder()).append("fonts/").append(s).append(".ttf").toString());
        
    }
    public PersianCheckbox(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        setTypeface(convert(context, "BNazanin"));
    }

    public PersianCheckbox(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        setTypeface(convert(context, "BNazanin"));
    }
}
