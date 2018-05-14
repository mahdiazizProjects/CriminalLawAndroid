package widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class PersianButton extends Button
{

    public PersianButton(Context context)
    {
        super(context);
        setTypeface(convert(context, "BNazanin"));
    }
    public PersianButton(Context context,String Fontname)
    {
        super(context);
        setTypeface(convert(context, Fontname));
    }
    public PersianButton(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        setTypeface(convert(context, "BNazanin"));
    }

    public PersianButton(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        setTypeface(convert(context, "BNazanin"));
    }

    public PersianButton(Context context, AttributeSet attributeset,String font)
    {
        super(context, attributeset);
        setTypeface(convert(context, font));
    }

    public PersianButton(Context context, AttributeSet attributeset, int i,String font)
    {
        super(context, attributeset, i);
        setTypeface(convert(context, font));
    }

    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
    }

    public void setText(String text, android.widget.TextView.BufferType buffertype)
    {
        super.setText(text, buffertype);
    }
    private Typeface convert(Context context, String s)
    {
        return Typeface.createFromAsset(context.getAssets(), (new StringBuilder()).append("fonts/").append(s).append(".ttf").toString());
        
    }
}
