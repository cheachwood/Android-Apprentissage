package com.google.android.apps.plus.oob;

import android.content.Context;
import android.util.AttributeSet;
import com.google.api.services.plusi.model.OutOfBoxField;
import com.google.api.services.plusi.model.OutOfBoxInputField;

public class HiddenFieldLayout extends BaseFieldLayout
{
  public HiddenFieldLayout(Context paramContext)
  {
    super(paramContext);
  }

  public HiddenFieldLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public HiddenFieldLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public final boolean isEmpty()
  {
    return false;
  }

  public final OutOfBoxInputField newFieldFromInput()
  {
    return this.mField.input;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.oob.HiddenFieldLayout
 * JD-Core Version:    0.6.2
 */