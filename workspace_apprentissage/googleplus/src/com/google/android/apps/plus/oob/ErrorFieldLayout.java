package com.google.android.apps.plus.oob;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import com.google.api.services.plusi.model.OutOfBoxError;
import com.google.api.services.plusi.model.OutOfBoxField;
import com.google.api.services.plusi.model.OutOfBoxInputField;

public class ErrorFieldLayout extends BaseFieldLayout
{
  public ErrorFieldLayout(Context paramContext)
  {
    super(paramContext);
  }

  public ErrorFieldLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public ErrorFieldLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public final void bindToField(OutOfBoxField paramOutOfBoxField, int paramInt, ActionCallback paramActionCallback)
  {
    super.bindToField(paramOutOfBoxField, paramInt, paramActionCallback);
    getLabelView().setText(Html.fromHtml(getField().error.text), TextView.BufferType.SPANNABLE);
  }

  public final boolean isEmpty()
  {
    return false;
  }

  public final OutOfBoxInputField newFieldFromInput()
  {
    throw new UnsupportedOperationException();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.oob.ErrorFieldLayout
 * JD-Core Version:    0.6.2
 */