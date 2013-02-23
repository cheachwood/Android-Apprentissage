package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.drawable;

public abstract class CheckableListItemView extends ViewGroup
  implements Checkable, CompoundButton.OnCheckedChangeListener
{
  protected static final StyleSpan sBoldSpan = new StyleSpan(1);
  private static Drawable sCheckedStateBackground;
  protected static ForegroundColorSpan sColorSpan;
  protected CheckBox mCheckBox;
  protected boolean mCheckBoxVisible;
  protected boolean mChecked;
  private OnItemCheckedChangeListener mListener;

  public CheckableListItemView(Context paramContext)
  {
    this(paramContext, null);
  }

  public CheckableListItemView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (sCheckedStateBackground == null)
    {
      Resources localResources = paramContext.getApplicationContext().getResources();
      sCheckedStateBackground = localResources.getDrawable(R.drawable.list_selected_holo);
      sColorSpan = new ForegroundColorSpan(localResources.getColor(R.color.search_query_highlight_color));
    }
  }

  protected void drawBackground(Canvas paramCanvas, Drawable paramDrawable)
  {
    paramDrawable.setBounds(0, 0, getWidth(), getHeight());
    paramDrawable.draw(paramCanvas);
  }

  public boolean isChecked()
  {
    if (this.mCheckBoxVisible);
    for (boolean bool = this.mCheckBox.isChecked(); ; bool = this.mChecked)
      return bool;
  }

  public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
  {
    this.mListener.onItemCheckedChanged(this, this.mCheckBox.isChecked());
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if ((!this.mCheckBoxVisible) && (this.mChecked))
      drawBackground(paramCanvas, sCheckedStateBackground);
    super.onDraw(paramCanvas);
  }

  public void setCheckBoxVisible(boolean paramBoolean)
  {
    this.mCheckBoxVisible = paramBoolean;
    if (this.mCheckBoxVisible)
    {
      if (this.mCheckBox == null)
      {
        this.mCheckBox = new CheckBox(getContext());
        this.mCheckBox.setOnCheckedChangeListener(this);
        this.mCheckBox.setFocusable(false);
        addView(this.mCheckBox);
      }
      this.mCheckBox.setVisibility(0);
    }
    while (true)
    {
      return;
      if (this.mCheckBox != null)
        this.mCheckBox.setVisibility(8);
    }
  }

  public void setChecked(boolean paramBoolean)
  {
    if (this.mCheckBoxVisible)
    {
      this.mCheckBox.setChecked(paramBoolean);
      return;
    }
    if (paramBoolean != this.mChecked)
    {
      this.mChecked = paramBoolean;
      if (paramBoolean)
        break label47;
    }
    label47: for (boolean bool = true; ; bool = false)
    {
      setWillNotDraw(bool);
      invalidate();
      break;
      break;
    }
  }

  public void setEnabled(boolean paramBoolean)
  {
    super.setEnabled(paramBoolean);
    this.mCheckBox.setEnabled(paramBoolean);
  }

  public void setOnItemCheckedChangeListener(OnItemCheckedChangeListener paramOnItemCheckedChangeListener)
  {
    this.mListener = paramOnItemCheckedChangeListener;
  }

  public void toggle()
  {
    if ((this.mCheckBoxVisible) && (this.mCheckBox.isEnabled()))
    {
      this.mCheckBox.toggle();
      return;
    }
    if (!this.mChecked);
    for (boolean bool = true; ; bool = false)
    {
      this.mChecked = bool;
      break;
    }
  }

  public static abstract interface OnItemCheckedChangeListener
  {
    public abstract void onItemCheckedChanged(CheckableListItemView paramCheckableListItemView, boolean paramBoolean);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.CheckableListItemView
 * JD-Core Version:    0.6.2
 */