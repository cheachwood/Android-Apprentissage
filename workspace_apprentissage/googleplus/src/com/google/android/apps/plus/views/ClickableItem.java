package com.google.android.apps.plus.views;

import android.graphics.Rect;
import java.util.Comparator;

public abstract interface ClickableItem extends Comparator<ClickableItem>
{
  public static final ClickableItemsComparator sComparator = new ClickableItemsComparator();

  public abstract CharSequence getContentDescription();

  public abstract Rect getRect();

  public abstract boolean handleEvent(int paramInt1, int paramInt2, int paramInt3);

  public static final class ClickableItemsComparator
    implements Comparator<ClickableItem>
  {
    public static int compare(ClickableItem paramClickableItem1, ClickableItem paramClickableItem2)
    {
      Rect localRect1 = paramClickableItem1.getRect();
      Rect localRect2 = paramClickableItem2.getRect();
      int i;
      if (localRect1.bottom <= localRect2.top)
        i = -1;
      while (true)
      {
        return i;
        if (localRect1.top >= localRect2.bottom)
        {
          i = 1;
        }
        else
        {
          i = localRect1.left - localRect2.left;
          if (i == 0)
          {
            int j = localRect1.top - localRect2.top;
            if (j != 0)
            {
              i = j;
            }
            else
            {
              int k = localRect1.bottom - localRect2.bottom;
              if (k != 0)
              {
                i = k;
              }
              else
              {
                int m = localRect1.right - localRect2.right;
                if (m != 0)
                  i = m;
                else
                  i = paramClickableItem1.hashCode() - paramClickableItem2.hashCode();
              }
            }
          }
        }
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ClickableItem
 * JD-Core Version:    0.6.2
 */