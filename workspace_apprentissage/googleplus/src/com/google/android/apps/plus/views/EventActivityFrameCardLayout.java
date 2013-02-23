package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.apps.plus.R.array;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.content.EsEventData.EventPerson;
import com.google.android.apps.plus.util.Dates;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventActivityFrameCardLayout extends CardViewLayout
{
  private static int sAvatarLineupMarginBottom;
  private static int sAvatarLineupMarginLeft;
  private static int sAvatarLineupMarginRight;
  private static Drawable sCheckinIconDrawable;
  private static int sDateTextColor;
  private static int sDateTextSize;
  private static Drawable sGoingIconDrawable;
  private static boolean sInitialized;
  private static Drawable sInvitedIconDrawable;
  private static int sPaddingBottom;
  private static int sPaddingLeft;
  private static int sPaddingRight;
  private static int sPaddingTop;
  private AvatarLineupLayout mAvatarLineup;
  private TextView mDate;
  private TextView mDescription;
  private ImageView mIcon;

  public EventActivityFrameCardLayout(Context paramContext)
  {
    super(paramContext);
  }

  public EventActivityFrameCardLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public EventActivityFrameCardLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  private CharSequence getText(int paramInt, ArrayList<EsEventData.EventPerson> paramArrayList)
  {
    Resources localResources = getContext().getResources();
    String[] arrayOfString = null;
    String str1;
    switch (paramInt)
    {
    default:
      str1 = null;
      if (arrayOfString != null)
      {
        if (paramArrayList.size() < arrayOfString.length)
          break label149;
        String str3 = arrayOfString[(-1 + arrayOfString.length)];
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(paramArrayList.size());
        str1 = String.format(str3, arrayOfObject2);
      }
      break;
    case 2:
    case 3:
    case 4:
    case 1:
    }
    while (true)
    {
      return str1;
      arrayOfString = localResources.getStringArray(R.array.event_activity_invite_strings);
      break;
      arrayOfString = localResources.getStringArray(R.array.event_activity_rsvp_no_strings);
      break;
      arrayOfString = localResources.getStringArray(R.array.event_activity_rsvp_yes_strings);
      break;
      arrayOfString = localResources.getStringArray(R.array.event_activity_checked_in_strings);
      break;
      label149: ArrayList localArrayList = new ArrayList();
      Iterator localIterator = paramArrayList.iterator();
      if (localIterator.hasNext())
      {
        EsEventData.EventPerson localEventPerson = (EsEventData.EventPerson)localIterator.next();
        if (localEventPerson.numAdditionalGuests == 0);
        int j;
        int k;
        Object[] arrayOfObject1;
        for (String str2 = localEventPerson.name; ; str2 = localResources.getQuantityString(j, k, arrayOfObject1))
        {
          localArrayList.add(str2);
          break;
          j = R.plurals.event_invitee_with_guests;
          k = localEventPerson.numAdditionalGuests;
          arrayOfObject1 = new Object[2];
          arrayOfObject1[0] = localEventPerson.name;
          arrayOfObject1[1] = Integer.valueOf(localEventPerson.numAdditionalGuests);
        }
      }
      int i = localArrayList.size();
      str1 = null;
      if (i > 0)
        str1 = String.format(arrayOfString[(-1 + localArrayList.size())], localArrayList.toArray());
    }
  }

  public final void bind(int paramInt, long paramLong, List<EsEventData.EventPerson> paramList, EventActionListener paramEventActionListener)
  {
    Drawable localDrawable = null;
    switch (paramInt)
    {
    case 3:
    default:
    case 1:
    case 2:
    case 4:
    }
    ArrayList localArrayList;
    int i;
    while (true)
    {
      if (localDrawable != null)
        this.mIcon.setImageDrawable(localDrawable);
      localArrayList = new ArrayList();
      i = paramList.size();
      for (int j = 0; j < i; j++)
      {
        EsEventData.EventPerson localEventPerson = (EsEventData.EventPerson)paramList.get(j);
        if (localEventPerson.name != null)
          localArrayList.add(localEventPerson);
      }
      localDrawable = sCheckinIconDrawable;
      continue;
      localDrawable = sInvitedIconDrawable;
      continue;
      localDrawable = sGoingIconDrawable;
    }
    this.mAvatarLineup.bind(localArrayList, paramEventActionListener, i);
    this.mDate.setText(Dates.getRelativeTimeSpanString(getContext(), paramLong));
    this.mDescription.setText(getText(paramInt, localArrayList));
  }

  protected final void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super.init(paramContext, paramAttributeSet, paramInt);
    if (!sInitialized)
    {
      Resources localResources = paramContext.getResources();
      sInvitedIconDrawable = localResources.getDrawable(R.drawable.icn_events_activity_invited);
      sGoingIconDrawable = localResources.getDrawable(R.drawable.icn_events_activity_going);
      sCheckinIconDrawable = localResources.getDrawable(R.drawable.icn_events_activity_checkin);
      sDateTextColor = localResources.getColor(R.color.event_card_activity_time_color);
      sDateTextSize = localResources.getDimensionPixelSize(R.dimen.event_card_activity_time_size);
      sPaddingLeft = localResources.getDimensionPixelSize(R.dimen.event_card_activity_padding_left);
      sPaddingRight = localResources.getDimensionPixelSize(R.dimen.event_card_activity_padding_right);
      sPaddingTop = localResources.getDimensionPixelSize(R.dimen.event_card_activity_padding_top);
      sPaddingBottom = localResources.getDimensionPixelSize(R.dimen.event_card_activity_padding_bottom);
      sAvatarLineupMarginLeft = localResources.getDimensionPixelSize(R.dimen.event_card_activity_avatar_lineup_margin_left);
      sAvatarLineupMarginRight = localResources.getDimensionPixelSize(R.dimen.event_card_activity_avatar_lineup_margin_right);
      sAvatarLineupMarginBottom = localResources.getDimensionPixelSize(R.dimen.event_card_activity_avatar_lineup_margin_bottom);
      sInitialized = true;
    }
    addPadding(sPaddingLeft, sPaddingTop, sPaddingRight, sPaddingBottom);
    this.mDate = new TextView(paramContext, paramAttributeSet, paramInt);
    this.mDate.setLayoutParams(new ExactLayout.LayoutParams(-2, -2));
    this.mDate.setTextColor(sDateTextColor);
    this.mDate.setTextSize(0, sDateTextSize);
    addView(this.mDate);
    this.mIcon = new ImageView(paramContext, paramAttributeSet, paramInt);
    addView(this.mIcon);
    this.mAvatarLineup = new AvatarLineupLayout(paramContext, paramAttributeSet, paramInt);
    addView(this.mAvatarLineup);
    this.mDescription = new TextView(paramContext, paramAttributeSet, paramInt);
    this.mDescription.setLayoutParams(new ExactLayout.LayoutParams(-2, -2));
    addView(this.mDescription);
  }

  protected void measureChildren(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    int k = i + 0;
    int m = j + 0;
    this.mIcon.measure(View.MeasureSpec.makeMeasureSpec(i, -2147483648), View.MeasureSpec.makeMeasureSpec(j, -2147483648));
    int n = this.mIcon.getMeasuredWidth();
    setCorner(this.mIcon, 0, 0);
    int i1 = i - n;
    this.mDate.measure(View.MeasureSpec.makeMeasureSpec(i1, -2147483648), View.MeasureSpec.makeMeasureSpec(j, -2147483648));
    int i2 = this.mDate.getMeasuredWidth();
    int i3 = k - i2;
    setCorner(this.mDate, i3, 0);
    int i4 = i1 - i2 - (sAvatarLineupMarginLeft + sAvatarLineupMarginRight);
    this.mAvatarLineup.measure(View.MeasureSpec.makeMeasureSpec(i4, -2147483648), View.MeasureSpec.makeMeasureSpec(j, -2147483648));
    int i5 = 0 + this.mIcon.getMeasuredWidth() + sAvatarLineupMarginLeft;
    setCorner(this.mAvatarLineup, i5, 0);
    View[] arrayOfView1 = new View[3];
    arrayOfView1[0] = this.mAvatarLineup;
    arrayOfView1[1] = this.mDate;
    arrayOfView1[2] = this.mIcon;
    int i6 = getMaxHeight(arrayOfView1);
    View[] arrayOfView2 = new View[3];
    arrayOfView2[0] = this.mAvatarLineup;
    arrayOfView2[1] = this.mDate;
    arrayOfView2[2] = this.mIcon;
    verticallyCenter(i6, arrayOfView2);
    int i7 = 0 + this.mAvatarLineup.getMeasuredHeight() + sAvatarLineupMarginBottom;
    setCorner(this.mDescription, 0, i7);
    this.mDescription.measure(View.MeasureSpec.makeMeasureSpec(i, -2147483648), View.MeasureSpec.makeMeasureSpec(m - i7, View.MeasureSpec.getMode(paramInt2)));
  }

  public void onRecycle()
  {
    super.onRecycle();
    this.mIcon.setImageDrawable(null);
    this.mDate.setText(null);
    this.mDescription.setText(null);
    this.mAvatarLineup.clear();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventActivityFrameCardLayout
 * JD-Core Version:    0.6.2
 */