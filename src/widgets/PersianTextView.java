package widgets;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

public class PersianTextView extends TextView
{

    public PersianTextView(Context context,String Fontname)
    {
        super(context);
        setTypeface(convert(context, Fontname));
    }

    public PersianTextView(Context context, AttributeSet attributeset,String Fontname)
    {
        super(context, attributeset);
        setTypeface(convert(context, Fontname));
    }

    public PersianTextView(Context context, AttributeSet attributeset, int i,String Fontname)
    {
        super(context, attributeset, i);
        setTypeface(convert(context, Fontname));
    }
    public PersianTextView(Context context)
    {
        super(context);
        setTypeface(convert(context, "BNazanin"));
    }

    public PersianTextView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        setTypeface(convert(context, "BNazanin"));
    }

    public PersianTextView(Context context, AttributeSet attributeset, int i)
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
    public void SetText(String S,int sizediffcolor,int Idcolor1,int Idcolor2)
    {
    	String Text=S;
    	String text = "<font color="+String.valueOf(getResources().getColor(Idcolor1))+" >"+Text.substring(0,sizediffcolor)+"</font>"+Text.substring(sizediffcolor,Text.length());
    	super.setText(Html.fromHtml(text),BufferType.NORMAL);
    }
    private Typeface convert(Context context, String s)
    {
        return Typeface.createFromAsset(context.getAssets(), (new StringBuilder()).append("fonts/").append(s).append(".ttf").toString());
        
    }
}
