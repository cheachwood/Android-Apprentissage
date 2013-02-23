package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.google.android.apps.plus.R.id;

public class EventRsvpButtonLayout extends LinearLayout
  implements View.OnClickListener
{
  private EventRsvpListener mListener;
  private View mMaybeDivider;
  private View mMaybeView;
  private View mNoView;
  private View mYesView;

  public EventRsvpButtonLayout(Context paramContext)
  {
    super(paramContext);
  }

  public EventRsvpButtonLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public EventRsvpButtonLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public final void bind(EventRsvpListener paramEventRsvpListener, boolean paramBoolean)
  {
    this.mListener = paramEventRsvpListener;
    if (paramBoolean);
    for (int i = 8; ; i = 0)
    {
      this.mMaybeDivider.setVisibility(i);
      this.mMaybeView.setVisibility(i);
      return;
    }
  }

  public void onClick(View paramView)
  {
    if (this.mListener != null)
    {
      if (paramView != this.mYesView)
        break label27;
      this.mListener.onRsvpChanged("ATTENDING");
    }
    while (true)
    {
      return;
      label27: if (paramView == this.mMaybeView)
        this.mListener.onRsvpChanged("MAYBE");
      else if (paramView == this.mNoView)
        this.mListener.onRsvpChanged("NOT_ATTENDING");
    }
  }

  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mMaybeView = findViewById(R.id.maybeButton);
    this.mMaybeView.setOnClickListener(this);
    this.mYesView = findViewById(R.id.yesButton);
    this.mYesView.setOnClickListener(this);
    this.mNoView = findViewById(R.id.noButton);
    this.mNoView.setOnClickListener(this);
    this.mMaybeDivider = findViewById(R.id.maybeDivider);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventRsvpButtonLayout
 * JD-Core Version:    0.6.2
 */