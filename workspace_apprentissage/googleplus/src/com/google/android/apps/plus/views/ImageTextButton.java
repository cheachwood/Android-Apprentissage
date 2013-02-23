package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.styleable;

public class ImageTextButton extends RelativeLayout
{
  private ImageView mImage;
  private TextView mText;

  public ImageTextButton(Context paramContext)
  {
    this(paramContext, null);
  }

  public ImageTextButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }

  public ImageTextButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }

  private void init(Context paramContext, AttributeSet paramAttributeSet)
  {
    LayoutInflater.from(paramContext).inflate(R.layout.image_text_button, this);
    this.mText = ((TextView)findViewById(R.id.text_view));
    this.mImage = ((ImageView)findViewById(R.id.image_view));
    if (paramAttributeSet != null)
    {
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ImageTextButton);
      Drawable localDrawable = localTypedArray.getDrawable(1);
      this.mImage.setImageDrawable(localDrawable);
      String str = localTypedArray.getString(0);
      this.mText.setText(str);
      localTypedArray.recycle();
    }
  }

  public void setEnabled(boolean paramBoolean)
  {
    super.setEnabled(paramBoolean);
    float f;
    if (Build.VERSION.SDK_INT >= 11)
      if (paramBoolean)
      {
        f = 1.0F;
        setAlpha(f);
      }
    while (true)
    {
      return;
      f = 0.5F;
      break;
      this.mText.setEnabled(paramBoolean);
      this.mImage.setEnabled(paramBoolean);
    }
  }

  public void setText(String paramString)
  {
    this.mText.setText(paramString);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ImageTextButton
 * JD-Core Version:    0.6.2
 */