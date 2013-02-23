package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class ActionButton extends TextView
{
  public ActionButton(Context paramContext)
  {
    this(paramContext, null);
  }

  public ActionButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setup();
  }

  public ActionButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setup();
  }

  private void setup()
  {
    CharSequence localCharSequence = getText();
    if (localCharSequence != null)
      setText(localCharSequence.toString().toUpperCase());
  }

  public void setLabel(int paramInt)
  {
    setText(paramInt);
    setup();
  }

  public void setLabel(String paramString)
  {
    setText(paramString);
    setup();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ActionButton
 * JD-Core Version:    0.6.2
 */