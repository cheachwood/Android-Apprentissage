package com.google.android.apps.plus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import com.google.android.apps.plus.R.id;

public class PhotoTagSpanner extends View
{
  private View mTagParent;

  public PhotoTagSpanner(Context paramContext)
  {
    this(paramContext, null);
  }

  public PhotoTagSpanner(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    setFixedWidth();
  }

  public void setFixedWidth()
  {
    if (this.mTagParent == null)
    {
      int n = R.id.tag_approval;
      Object localObject = this;
      ViewParent localViewParent = ((View)localObject).getParent();
      if ((localViewParent instanceof View))
      {
        localObject = (View)localViewParent;
        if (((View)localObject).getId() != n)
          break label96;
      }
      while (true)
      {
        this.mTagParent = ((View)localObject);
        if (this.mTagParent != null)
          break label104;
        throw new IllegalArgumentException("Error: " + PhotoTagSpanner.class.getName() + " must have a parent with and ID of 'tag_approval'");
        localObject = null;
        label96: if (localObject != null)
          break;
      }
    }
    label104: View localView1 = this.mTagParent.findViewById(R.id.avatar);
    View localView2 = this.mTagParent.findViewById(R.id.tag_buttons);
    int i = this.mTagParent.getMeasuredWidth();
    int j;
    int k;
    if (localView1 != null)
    {
      j = localView1.getMeasuredWidth();
      k = i - j;
      if (localView2 == null)
        break label190;
    }
    label190: for (int m = localView2.getMeasuredWidth(); ; m = 0)
    {
      setMeasuredDimension(k - m - getPaddingLeft() - getPaddingRight(), getMeasuredHeight());
      return;
      j = 0;
      break;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.PhotoTagSpanner
 * JD-Core Version:    0.6.2
 */