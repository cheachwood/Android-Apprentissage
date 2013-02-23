package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.content.EsEventData.EventPerson;
import java.util.ArrayList;
import java.util.Iterator;

public class AvatarLineupLayout extends ExactLayout
  implements View.OnClickListener
{
  private static int sAvatarLineupItemPadding;
  private static int sAvatarLineupItemSize;
  private static int sDescriptionFontColor;
  private static float sDescriptionFontSize;
  private static boolean sInitialized;
  private ArrayList<AvatarView> mAvatars;
  private EventActionListener mListener;
  private TextView mOverflowText;
  private ArrayList<String> mPeople;
  private int mTotalPeopleCount;

  public AvatarLineupLayout(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null, 0);
  }

  public AvatarLineupLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet, 0);
  }

  public AvatarLineupLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet, paramInt);
  }

  private void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    if (!sInitialized)
    {
      Resources localResources = paramContext.getResources();
      sAvatarLineupItemPadding = localResources.getDimensionPixelSize(R.dimen.event_card_avatar_lineup_item_padding);
      sAvatarLineupItemSize = localResources.getDimensionPixelSize(R.dimen.event_card_avatar_lineup_item_size);
      sDescriptionFontSize = localResources.getDimension(R.dimen.event_card_avatar_lineup_overflow_text_size);
      sDescriptionFontColor = localResources.getColor(R.color.avatar_lineup_overflow_text_color);
      sInitialized = true;
    }
    this.mOverflowText = TextViewUtils.createText(paramContext, paramAttributeSet, paramInt, sDescriptionFontSize, sDescriptionFontColor, false, true);
    addView(this.mOverflowText);
    this.mAvatars = new ArrayList();
  }

  public final void bind(ArrayList<EsEventData.EventPerson> paramArrayList, EventActionListener paramEventActionListener, int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
      localArrayList.add(((EsEventData.EventPerson)localIterator.next()).gaiaId);
    this.mPeople = localArrayList;
    this.mListener = paramEventActionListener;
    this.mTotalPeopleCount = paramInt;
    requestLayout();
  }

  public final void bindIds(ArrayList<String> paramArrayList, EventActionListener paramEventActionListener, int paramInt)
  {
    this.mPeople = paramArrayList;
    this.mListener = paramEventActionListener;
    this.mTotalPeopleCount = paramInt;
    requestLayout();
  }

  public final void clear()
  {
    this.mPeople.clear();
    int i = this.mAvatars.size();
    for (int j = 0; j < i; j++)
    {
      AvatarView localAvatarView = (AvatarView)this.mAvatars.get(j);
      localAvatarView.setVisibility(8);
      localAvatarView.setGaiaId(null);
      removeView(localAvatarView);
    }
    this.mAvatars.clear();
    this.mListener = null;
  }

  protected void measureChildren(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = sAvatarLineupItemSize + sAvatarLineupItemPadding;
    int k = i / j;
    int m = this.mPeople.size();
    int n = 0;
    int i4;
    if ((k < m) || (this.mTotalPeopleCount > m))
    {
      int i3;
      do
      {
        int i1 = this.mTotalPeopleCount - m;
        Resources localResources = getContext().getResources();
        int i2 = R.plurals.event_invitee_other_count;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(i1);
        String str = localResources.getQuantityString(i2, i1, arrayOfObject);
        this.mOverflowText.setText(str);
        this.mOverflowText.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(sAvatarLineupItemSize, -2147483648));
        i3 = i - m * j;
        if ((this.mPeople.size() <= 1) || (i3 >= this.mOverflowText.getMeasuredWidth()))
          break;
        i4 = 1;
        if (i4 != 0)
          m--;
      }
      while (i4 != 0);
      this.mOverflowText.measure(View.MeasureSpec.makeMeasureSpec(i3, -2147483648), View.MeasureSpec.makeMeasureSpec(sAvatarLineupItemSize, -2147483648));
      this.mOverflowText.setVisibility(0);
      setCorner(this.mOverflowText, j * m, Math.max(0, (sAvatarLineupItemSize - this.mOverflowText.getMeasuredHeight()) / 2));
    }
    while (true)
    {
      for (int i5 = Math.max(0, m - this.mAvatars.size()); i5 > 0; i5--)
      {
        AvatarView localAvatarView1 = new AvatarView(getContext());
        localAvatarView1.setOnClickListener(this);
        localAvatarView1.setAvatarSize(0);
        addView(localAvatarView1);
        this.mAvatars.add(localAvatarView1);
      }
      i4 = 0;
      break;
      this.mOverflowText.setVisibility(8);
    }
    int i6 = this.mAvatars.size();
    int i7 = 0;
    if (i7 < i6)
    {
      AvatarView localAvatarView2 = (AvatarView)this.mAvatars.get(i7);
      int i8;
      if (i7 < m)
      {
        localAvatarView2.setGaiaId((String)this.mPeople.get(i7));
        localAvatarView2.setVisibility(0);
        localAvatarView2.measure(View.MeasureSpec.makeMeasureSpec(sAvatarLineupItemSize, 1073741824), View.MeasureSpec.makeMeasureSpec(sAvatarLineupItemSize, 1073741824));
        if (i7 > 0)
        {
          i8 = sAvatarLineupItemPadding;
          label415: int i9 = n + i8;
          setCorner(localAvatarView2, i9, 0);
          n = i9 + sAvatarLineupItemSize;
        }
      }
      while (true)
      {
        i7++;
        break;
        i8 = 0;
        break label415;
        localAvatarView2.setGaiaId(null);
        localAvatarView2.setVisibility(8);
      }
    }
  }

  public void onClick(View paramView)
  {
    if (((paramView instanceof AvatarView)) && (this.mListener != null))
      this.mListener.onAvatarClicked(((AvatarView)paramView).getGaiaId());
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.AvatarLineupLayout
 * JD-Core Version:    0.6.2
 */