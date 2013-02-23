package com.google.android.apps.plus.oob;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.google.android.apps.plus.R.drawable;
import com.google.api.services.plusi.model.OutOfBoxField;
import com.google.api.services.plusi.model.OutOfBoxInputField;
import java.util.HashMap;
import java.util.Map;

public class ImageFieldLayout extends BaseFieldLayout
{
  private static final Map<String, Integer> IMAGE_MAP;

  static
  {
    HashMap localHashMap = new HashMap();
    IMAGE_MAP = localHashMap;
    localHashMap.put("PICASA", Integer.valueOf(R.drawable.signup_picasa));
  }

  public ImageFieldLayout(Context paramContext)
  {
    super(paramContext);
  }

  public ImageFieldLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public ImageFieldLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public final void bindToField(OutOfBoxField paramOutOfBoxField, int paramInt, ActionCallback paramActionCallback)
  {
    super.bindToField(paramOutOfBoxField, paramInt, paramActionCallback);
    ImageView localImageView = (ImageView)getInputView();
    String str = getServerImageType();
    if (IMAGE_MAP.containsKey(str))
      localImageView.setImageResource(((Integer)IMAGE_MAP.get(str)).intValue());
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
 * Qualified Name:     com.google.android.apps.plus.oob.ImageFieldLayout
 * JD-Core Version:    0.6.2
 */