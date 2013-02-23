package com.google.android.apps.plus.views;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

public class PlusOneAnimatorView extends View
  implements Animator.AnimatorListener
{
  private static final Interpolator sAccelerateInterpolator = new AccelerateInterpolator(1.2F);
  private static final Interpolator sDecelerateInterpolator = new DecelerateInterpolator(1.2F);
  private int mAnimStage;
  private ClickableButton mCurrentButton;
  private ClickableButton mNextButton;
  private int mOriginalTranslateY;
  private PlusOneAnimListener mPlusOneAnimListener;

  public PlusOneAnimatorView(Context paramContext)
  {
    this(paramContext, null);
  }

  public PlusOneAnimatorView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public PlusOneAnimatorView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public void onAnimationCancel(Animator paramAnimator)
  {
    if (this.mAnimStage >= 2)
    {
      this.mCurrentButton = null;
      this.mNextButton = null;
      this.mPlusOneAnimListener.onPlusOneAnimFinished();
    }
  }

  public void onAnimationEnd(Animator paramAnimator)
  {
    switch (this.mAnimStage)
    {
    default:
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      return;
      this.mCurrentButton = this.mNextButton;
      this.mNextButton = null;
      animate().setDuration(75L).scaleX(2.0F).setInterpolator(sDecelerateInterpolator).setListener(this);
      invalidate();
      this.mAnimStage = (1 + this.mAnimStage);
      continue;
      animate().setDuration(270L).translationY(this.mOriginalTranslateY).scaleX(1.0F).scaleY(1.0F).setInterpolator(sDecelerateInterpolator).setListener(this);
      this.mAnimStage = (1 + this.mAnimStage);
      continue;
      this.mCurrentButton = null;
      this.mPlusOneAnimListener.onPlusOneAnimFinished();
      invalidate();
    }
  }

  public void onAnimationRepeat(Animator paramAnimator)
  {
  }

  public void onAnimationStart(Animator paramAnimator)
  {
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if (this.mCurrentButton != null)
      this.mCurrentButton.draw(paramCanvas);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mCurrentButton != null)
    {
      Rect localRect = this.mCurrentButton.getRect();
      setMeasuredDimension(localRect.width(), localRect.height());
    }
    while (true)
    {
      return;
      setMeasuredDimension(0, 0);
    }
  }

  public final void startPlusOneAnim(PlusOneAnimListener paramPlusOneAnimListener, ClickableButton paramClickableButton1, ClickableButton paramClickableButton2)
  {
    this.mAnimStage = 0;
    this.mPlusOneAnimListener = paramPlusOneAnimListener;
    this.mCurrentButton = paramClickableButton1;
    this.mNextButton = paramClickableButton2;
    Rect localRect = paramClickableButton1.getRect();
    setX(localRect.left);
    setY(localRect.top);
    localRect.offsetTo(0, 0);
    paramClickableButton2.getRect().offsetTo(0, 0);
    if (Build.VERSION.SDK_INT >= 12)
    {
      this.mOriginalTranslateY = ((int)getTranslationY());
      int i = 2 * this.mCurrentButton.getRect().height();
      animate().setDuration(270L).translationY(this.mOriginalTranslateY - i).scaleX(2.0F).scaleY(2.0F).setInterpolator(sAccelerateInterpolator).setListener(this);
    }
    requestLayout();
  }

  public static abstract interface PlusOneAnimListener
  {
    public abstract void onPlusOneAnimFinished();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PlusOneAnimatorView
 * JD-Core Version:    0.6.2
 */