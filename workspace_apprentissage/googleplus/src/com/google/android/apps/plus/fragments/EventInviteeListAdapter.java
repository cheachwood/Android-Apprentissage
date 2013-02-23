package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.views.PeopleListItemView;
import com.google.android.apps.plus.views.PeopleListItemView.OnActionButtonClickListener;

public final class EventInviteeListAdapter extends CursorAdapter
  implements PeopleListItemView.OnActionButtonClickListener
{
  private OnActionListener mListener;
  private String mOwnerId;
  private String mViewerGaiaId;

  public EventInviteeListAdapter(Context paramContext)
  {
    super(paramContext, null, 0);
  }

  public final void bindView(View paramView, Context paramContext, Cursor paramCursor)
  {
    switch (paramCursor.getInt(0))
    {
    default:
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      return;
      String str3 = paramCursor.getString(9);
      int i2;
      label58: int i3;
      if (paramCursor.getInt(10) == 1)
      {
        i2 = 1;
        i3 = paramCursor.getInt(11);
        if (!"ATTENDING".equals(str3))
          break label151;
        if (i2 == 0)
          break label143;
      }
      label143: for (int i4 = R.string.event_invitee_list_section_attended; ; i4 = R.string.event_invitee_list_section_attending)
      {
        Context localContext = this.mContext;
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = Integer.valueOf(i3);
        String str4 = localContext.getString(i4, arrayOfObject3);
        ((TextView)paramView.findViewById(16908308)).setText(str4);
        break;
        i2 = 0;
        break label58;
      }
      label151: if ("MAYBE".equals(str3))
        i4 = R.string.event_invitee_list_section_maybe;
      while (true)
      {
        break;
        if ("NOT_ATTENDING".equals(str3))
        {
          if (i2 != 0);
          for (i4 = R.string.event_invitee_list_section_didnt_go; ; i4 = R.string.event_invitee_list_section_not_attending)
            break;
        }
        if ("REMOVED".equals(str3))
          i4 = R.string.event_invitee_list_section_removed;
        else if (i2 != 0)
          i4 = R.string.event_invitee_list_section_did_not_respond;
        else
          i4 = R.string.event_invitee_list_section_not_responded;
      }
      PeopleListItemView localPeopleListItemView = (PeopleListItemView)paramView;
      localPeopleListItemView.setOnActionButtonClickListener(this);
      String str2 = paramCursor.getString(3);
      localPeopleListItemView.setPersonId(paramCursor.getString(2));
      localPeopleListItemView.setGaiaId(str2);
      localPeopleListItemView.setContactName(paramCursor.getString(4));
      localPeopleListItemView.setWellFormedEmail(paramCursor.getString(5));
      int k = paramCursor.getInt(7);
      if (k > 0)
      {
        Resources localResources2 = this.mContext.getResources();
        int i1 = R.plurals.event_invitee_other_count;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(k);
        localPeopleListItemView.setCustomText(localResources2.getQuantityString(i1, k, arrayOfObject2));
      }
      localPeopleListItemView.setActionButtonLabel(R.string.accounts_title);
      if ((!this.mOwnerId.equals(this.mViewerGaiaId)) || (this.mViewerGaiaId.equals(str2)))
      {
        localPeopleListItemView.setActionButtonVisible(false);
        localPeopleListItemView.updateContentDescription();
      }
      else
      {
        int m;
        if (paramCursor.getInt(8) != 0)
        {
          m = 1;
          label425: if (m == 0)
            break label457;
        }
        label457: for (int n = R.string.event_reinvite_invitee; ; n = R.string.event_remove_invitee)
        {
          localPeopleListItemView.setActionButtonLabel(n);
          localPeopleListItemView.setActionButtonVisible(true);
          break;
          m = 0;
          break label425;
        }
        int i = paramCursor.getInt(11);
        Resources localResources1 = this.mContext.getResources();
        int j = R.plurals.event_invitee_other_count;
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(i);
        String str1 = localResources1.getQuantityString(j, i, arrayOfObject1);
        ((TextView)paramView.findViewById(16908308)).setText(str1);
      }
    }
  }

  public final int getItemViewType(int paramInt)
  {
    int i = ((Cursor)getItem(paramInt)).getInt(0);
    int j = 0;
    switch (i)
    {
    default:
    case 0:
    case 1:
    }
    for (j = 2; ; j = 1)
      return j;
  }

  public final int getViewTypeCount()
  {
    return 3;
  }

  public final boolean isEnabled(int paramInt)
  {
    int i = 1;
    if (getItemViewType(paramInt) == i);
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  public final View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    Object localObject;
    switch (paramCursor.getInt(0))
    {
    default:
      localObject = null;
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      return localObject;
      localObject = LayoutInflater.from(paramContext).inflate(R.layout.section_header, paramViewGroup, false);
      continue;
      localObject = PeopleListItemView.createInstance(paramContext);
      continue;
      localObject = LayoutInflater.from(paramContext).inflate(R.layout.event_invitee_list_section_footer, paramViewGroup, false);
    }
  }

  public final void onActionButtonClick(PeopleListItemView paramPeopleListItemView, int paramInt)
  {
    String str1;
    String str2;
    Cursor localCursor;
    if ((paramInt == 3) && (this.mListener != null))
    {
      str1 = paramPeopleListItemView.getGaiaId();
      str2 = paramPeopleListItemView.getWellFormedEmail();
      localCursor = getCursor();
      if (!localCursor.moveToFirst())
        break label159;
      if (localCursor.isNull(3))
        break label186;
    }
    label159: label165: label186: for (String str3 = localCursor.getString(3); ; str3 = null)
    {
      if (!localCursor.isNull(5));
      for (String str4 = localCursor.getString(5); ; str4 = null)
      {
        int i;
        if (((str3 != null) && (TextUtils.equals(str3, str1))) || ((str4 != null) && (TextUtils.equals(str4, str2))))
          if (localCursor.getInt(8) != 0)
          {
            i = 1;
            label125: if (i == 0)
              break label165;
            this.mListener.onReInviteInvitee(str1, str2);
          }
        while (true)
        {
          return;
          i = 0;
          break label125;
          if (localCursor.moveToNext())
            break;
          i = 0;
          break label125;
          this.mListener.onRemoveInvitee(str1, str2);
        }
      }
    }
  }

  public final void setEventOwnerId(String paramString)
  {
    this.mOwnerId = paramString;
  }

  public final void setOnActionListener(OnActionListener paramOnActionListener)
  {
    this.mListener = paramOnActionListener;
  }

  public final void setViewerGaiaId(String paramString)
  {
    this.mViewerGaiaId = paramString;
  }

  public static abstract interface OnActionListener
  {
    public abstract void onReInviteInvitee(String paramString1, String paramString2);

    public abstract void onRemoveInvitee(String paramString1, String paramString2);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.EventInviteeListAdapter
 * JD-Core Version:    0.6.2
 */