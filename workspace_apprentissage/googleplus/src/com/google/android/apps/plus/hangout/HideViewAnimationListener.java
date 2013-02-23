package com.google.android.apps.plus.hangout;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

final class HideViewAnimationListener
  implements Animation.AnimationListener
{
  private final View view;

  HideViewAnimationListener(View paramView)
  {
    this.view = paramView;
  }

  public final void onAnimationEnd(Animation paramAnimation)
  {
    this.view.setVisibility(8);
  }

  public final void onAnimationRepeat(Animation paramAnimation)
  {
  }

  public final void onAnimationStart(Animation paramAnimation)
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.HideViewAnimationListener
 * JD-Core Version:    0.6.2
 */