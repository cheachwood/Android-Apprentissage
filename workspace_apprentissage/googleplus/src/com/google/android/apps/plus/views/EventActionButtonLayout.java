package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.style;
import com.google.android.apps.plus.R.styleable;

public class EventActionButtonLayout extends ExactLayout
{
  private static int sSpacing;
  private ImageView mImage;
  private TextView mText;
  private boolean sInitialized;

  public EventActionButtonLayout(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null, 0);
  }

  public EventActionButtonLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet, 0);
  }

  public EventActionButtonLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet, paramInt);
  }

  private void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    if (!this.sInitialized)
    {
      sSpacing = paramContext.getResources().getDimensionPixelSize(R.dimen.event_card_Details_rsvp_action_button_internal_spacing);
      this.sInitialized = true;
    }
    setClickable(true);
    setFocusable(true);
    setWillNotDraw(false);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.Theme);
    setBackgroundDrawable(localTypedArray.getDrawable(5));
    localTypedArray.recycle();
    this.mText = new TextView(paramContext, paramAttributeSet, R.style.EventsRsvpActionButton);
    addView(this.mText);
    this.mImage = new ImageView(paramContext, paramAttributeSet, paramInt);
    addView(this.mImage);
  }

  public final void bind(String paramString, Drawable paramDrawable)
  {
    this.mImage.setBackgroundDrawable(paramDrawable);
    this.mText.setText(paramString);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    measure(this.mImage, Math.max(0, i + 0), -2147483648, j, 0);
    setCorner(this.mImage, 0, 0);
    int k = 0 + (this.mImage.getMeasuredWidth() + sSpacing);
    measure(this.mText, Math.max(0, i - k), -2147483648, j, 0);
    setCorner(this.mText, k, 0);
    int m = k + this.mText.getMeasuredWidth();
    View[] arrayOfView1 = new View[2];
    arrayOfView1[0] = this.mImage;
    arrayOfView1[1] = this.mText;
    verticallyCenter(j, arrayOfView1);
    int n = (i - m) / 2;
    View[] arrayOfView2 = new View[2];
    arrayOfView2[0] = this.mText;
    arrayOfView2[1] = this.mImage;
    for (int i1 = Math.max(-1 + arrayOfView2.length, 0); i1 >= 0; i1--)
    {
      View localView = arrayOfView2[i1];
      ExactLayout.LayoutParams localLayoutParams = (ExactLayout.LayoutParams)localView.getLayoutParams();
      if (localLayoutParams != null)
      {
        localLayoutParams.x = (n + localLayoutParams.x);
        localLayoutParams.y = (0 + localLayoutParams.y);
        localView.setLayoutParams(localLayoutParams);
      }
    }
    setMeasuredDimension(resolveSize(i, paramInt1), resolveSize(j, paramInt2));
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventActionButtonLayout
 * JD-Core Version:    0.6.2
 */