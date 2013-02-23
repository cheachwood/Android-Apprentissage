package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.google.android.apps.plus.R.layout;
import java.util.ArrayList;
import java.util.List;

public class BottomActionBar extends LinearLayout
{
  public BottomActionBar(Context paramContext)
  {
    this(paramContext, null);
  }

  public BottomActionBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setOrientation(0);
  }

  public BottomActionBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setOrientation(0);
  }

  public final ActionButton addButton(int paramInt1, int paramInt2, View.OnClickListener paramOnClickListener)
  {
    return addButton(paramInt1, getContext().getString(paramInt2), paramOnClickListener);
  }

  public final ActionButton addButton(int paramInt, String paramString, View.OnClickListener paramOnClickListener)
  {
    ActionButton localActionButton = (ActionButton)LayoutInflater.from(getContext()).inflate(R.layout.bottom_action_button, this, false);
    localActionButton.setId(paramInt);
    localActionButton.setLabel(paramString);
    localActionButton.setOnClickListener(paramOnClickListener);
    addView(localActionButton);
    return localActionButton;
  }

  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    if (((paramView instanceof ActionButton)) && (getChildCount() > 0))
      super.addView(LayoutInflater.from(getContext()).inflate(R.layout.tab_separator, this, false), -1, paramLayoutParams);
    super.addView(paramView, -1, new LinearLayout.LayoutParams(0, -1, 1.0F));
  }

  public LinearLayout.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LinearLayout.LayoutParams(-2, -1);
  }

  public final List<ActionButton> getButtons()
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < getChildCount(); i++)
    {
      View localView = getChildAt(i);
      if ((localView instanceof ActionButton))
        localArrayList.add((ActionButton)localView);
    }
    return localArrayList;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.BottomActionBar
 * JD-Core Version:    0.6.2
 */