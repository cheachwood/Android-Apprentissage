package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;

public class PhotoAlbumView extends RelativeLayout
{
  private TextView mDateDisplay;
  private int mDateVisibilityState = 8;
  private AlphaAnimation mFadeIn = new AlphaAnimation(0.0F, 1.0F);
  private AlphaAnimation mFadeOut;
  private AlphaAnimation mResetToOpaque;

  public PhotoAlbumView(Context paramContext)
  {
    this(paramContext, null);
  }

  public PhotoAlbumView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mFadeIn.setInterpolator(new DecelerateInterpolator());
    this.mFadeIn.setDuration(250L);
    this.mFadeIn.setAnimationListener(new Animation.AnimationListener()
    {
      public final void onAnimationEnd(Animation paramAnonymousAnimation)
      {
      }

      public final void onAnimationRepeat(Animation paramAnonymousAnimation)
      {
      }

      public final void onAnimationStart(Animation paramAnonymousAnimation)
      {
        PhotoAlbumView.this.mDateDisplay.setVisibility(0);
      }
    });
    this.mFadeOut = new AlphaAnimation(1.0F, 0.0F);
    this.mFadeOut.setInterpolator(new AccelerateInterpolator());
    this.mFadeOut.setStartOffset(500L);
    this.mFadeOut.setDuration(250L);
    this.mFadeOut.setAnimationListener(new Animation.AnimationListener()
    {
      public final void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        PhotoAlbumView.this.mDateDisplay.setVisibility(4);
      }

      public final void onAnimationRepeat(Animation paramAnonymousAnimation)
      {
      }

      public final void onAnimationStart(Animation paramAnonymousAnimation)
      {
      }
    });
    if (Build.VERSION.SDK_INT < 11)
      this.mResetToOpaque = new AlphaAnimation(1.0F, 1.0F);
  }

  public final void enableDateDisplay(boolean paramBoolean)
  {
    this.mDateDisplay = ((TextView)findViewById(R.id.date));
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    RelativeLayout.LayoutParams localLayoutParams;
    int i;
    int j;
    if (this.mDateDisplay != null)
    {
      localLayoutParams = (RelativeLayout.LayoutParams)this.mDateDisplay.getLayoutParams();
      if (getResources().getConfiguration().orientation != 2)
        break label108;
      i = 1;
      j = ((ColumnGridView)findViewById(R.id.grid)).getColumnSize();
      if (i == 0)
        break label114;
      localLayoutParams.setMargins(j - this.mDateDisplay.getWidth() / 2, 0, 0, 0);
      localLayoutParams.addRule(8, R.id.grid);
      this.mDateDisplay.setBackgroundResource(R.drawable.photos_date_h);
    }
    while (true)
    {
      return;
      label108: i = 0;
      break;
      label114: localLayoutParams.setMargins(0, j - this.mDateDisplay.getHeight() / 2, 0, 0);
      localLayoutParams.addRule(7, R.id.grid);
      this.mDateDisplay.setBackgroundResource(R.drawable.photos_date_v);
    }
  }

  public void setDate(String paramString)
  {
    this.mDateDisplay.setText(paramString);
  }

  public void setDateVisibility(int paramInt)
  {
    if ((this.mDateDisplay == null) || (this.mDateVisibilityState == paramInt))
      return;
    if (paramInt == 0)
      if (this.mFadeOut.hasEnded())
        this.mDateDisplay.startAnimation(this.mFadeIn);
    while (true)
    {
      this.mDateVisibilityState = paramInt;
      break;
      this.mFadeOut.cancel();
      if (Build.VERSION.SDK_INT >= 11)
        this.mDateDisplay.setAlpha(1.0F);
      while (true)
      {
        this.mDateDisplay.setVisibility(0);
        break;
        this.mDateDisplay.startAnimation(this.mResetToOpaque);
      }
      this.mFadeOut.reset();
      this.mDateDisplay.startAnimation(this.mFadeOut);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PhotoAlbumView
 * JD-Core Version:    0.6.2
 */