package widgets;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class PersianEditView extends EditText
{

    public PersianEditView(Context context,String Fontname)
    {
        super(context);
        setTypeface(convert(context, Fontname));
    }

    public PersianEditView(Context context, AttributeSet attributeset,String Fontname)
    {
        super(context, attributeset);
        setTypeface(convert(context, Fontname));
    }

    public PersianEditView(Context context, AttributeSet attributeset, int i,String Fontname)
    {
        super(context, attributeset, i);
        setTypeface(convert(context, Fontname));
    }
    public PersianEditView(Context context)
    {
        super(context);
        setTypeface(convert(context, "BNazanin"));
    }

    public PersianEditView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        setTypeface(convert(context, "BNazanin"));
    }

    public PersianEditView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        setTypeface(convert(context, "BNazanin"));
    }
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
    }

    public void setText(CharSequence charsequence, android.widget.TextView.BufferType buffertype)
    {
        super.setText(charsequence, buffertype);
    }
    private Typeface convert(Context context, String s)
    {
        return Typeface.createFromAsset(context.getAssets(), (new StringBuilder()).append("fonts/").append(s).append(".ttf").toString());
        
    }
}
