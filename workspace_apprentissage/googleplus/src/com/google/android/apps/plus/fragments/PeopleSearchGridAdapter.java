package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.views.PersonCardView;
import com.google.android.apps.plus.views.PersonCardView.OnPersonCardClickListener;

public final class PeopleSearchGridAdapter extends PeopleSearchAdapter
  implements PersonCardView.OnPersonCardClickListener
{
  private CircleSpinnerAdapter mCircleSpinnerAdapter;
  private boolean mShowMembership;
  private boolean mViewingAsPlusPage;

  public PeopleSearchGridAdapter(Context paramContext, FragmentManager paramFragmentManager, LoaderManager paramLoaderManager, EsAccount paramEsAccount)
  {
    super(paramContext, paramFragmentManager, paramLoaderManager, paramEsAccount);
    this.mViewingAsPlusPage = paramEsAccount.isPlusPage();
  }

  protected final void bindView(View paramView, int paramInt1, Cursor paramCursor, int paramInt2)
  {
    switch (paramInt1)
    {
    case 1:
    case 3:
    default:
    case 0:
    case 4:
    case 2:
      while (true)
      {
        return;
        PersonCardView localPersonCardView3 = (PersonCardView)paramView;
        localPersonCardView3.setOnPersonCardClickListener(this);
        localPersonCardView3.setPosition(paramInt2 + getPositionForPartition(paramInt1));
        localPersonCardView3.setCircleNameResolver(this.mCircleNameResolver);
        localPersonCardView3.setContactName(paramCursor.getString(3));
        localPersonCardView3.setPersonId(paramCursor.getString(1));
        localPersonCardView3.setGaiaIdAndAvatarUrl(paramCursor.getString(2), EsAvatarData.uncompressAvatarUrl(paramCursor.getString(4)));
        if (this.mShowMembership)
        {
          String str3 = paramCursor.getString(5);
          localPersonCardView3.setPackedCircleIdsEmailAndDescription(str3, null, null, false, false);
          int n;
          label162: boolean bool2;
          if (paramCursor.getInt(6) != 0)
          {
            n = 1;
            if (n == 0)
              break label226;
            localPersonCardView3.setForceAvatarDefault(true);
            localPersonCardView3.setActionButtonVisible(true, R.string.person_card_unblock_button, 1);
            if (paramCursor.getInt(7) != 2)
              break label220;
            bool2 = true;
            label198: localPersonCardView3.setPlusPage(bool2);
          }
          while (true)
          {
            localPersonCardView3.setDismissActionButtonVisible(false);
            break;
            n = 0;
            break label162;
            label220: bool2 = false;
            break label198;
            label226: if ((TextUtils.isEmpty(str3)) && (!this.mViewingAsPlusPage))
              localPersonCardView3.setActionButtonVisible(true, R.string.person_card_add_to_circles_button, 0);
            else
              localPersonCardView3.setActionButtonVisible(false, 0, 0);
          }
        }
        localPersonCardView3.setPackedCircleIdsEmailAndDescription(null, null, null, false, false);
        if (!this.mViewingAsPlusPage)
          localPersonCardView3.setActionButtonVisible(true, R.string.person_card_add_to_circles_button, 0);
        while (true)
        {
          localPersonCardView3.setDismissActionButtonVisible(true);
          break;
          localPersonCardView3.setActionButtonVisible(false, 0, 0);
        }
        PersonCardView localPersonCardView2 = (PersonCardView)paramView;
        localPersonCardView2.setOnPersonCardClickListener(this);
        localPersonCardView2.setPosition(paramInt2 + getPositionForPartition(paramInt1));
        localPersonCardView2.setHighlightedText(this.mQuery);
        localPersonCardView2.setCircleNameResolver(this.mCircleNameResolver);
        localPersonCardView2.setPersonId(paramCursor.getString(1));
        localPersonCardView2.setContactIdAndAvatarUrl(paramCursor.getString(3), paramCursor.getString(2), EsAvatarData.uncompressAvatarUrl(paramCursor.getString(6)));
        localPersonCardView2.setContactName(paramCursor.getString(4));
        String str1 = paramCursor.getString(12);
        if (str1 == null)
        {
          str1 = paramCursor.getString(8);
          if (str1 == null)
            str1 = paramCursor.getString(9);
        }
        String str2 = paramCursor.getString(7);
        int m = paramCursor.getInt(5);
        localPersonCardView2.setDescription(str1, true, false);
        CircleSpinnerAdapter localCircleSpinnerAdapter;
        if (!this.mViewingAsPlusPage)
        {
          localCircleSpinnerAdapter = this.mCircleSpinnerAdapter;
          if (m != 1)
            break label541;
        }
        label541: for (boolean bool1 = true; ; bool1 = false)
        {
          localPersonCardView2.setOneClickCircles(str2, localCircleSpinnerAdapter, bool1);
          if (paramInt2 == -1 + paramCursor.getCount())
            continueLoadingPublicProfiles();
          localPersonCardView2.updateContentDescription();
          localPersonCardView2.setDismissActionButtonVisible(false);
          break;
        }
        PersonCardView localPersonCardView1 = (PersonCardView)paramView;
        localPersonCardView1.setOnPersonCardClickListener(this);
        localPersonCardView1.setWellFormedEmail(this.mQuery);
        localPersonCardView1.setActionButtonVisible(this.mAddToCirclesActionEnabled, R.string.person_card_add_to_circles_button, 0);
        localPersonCardView1.updateContentDescription();
      }
    case 5:
    }
    int i = 8;
    int j = 8;
    int k = 8;
    switch (paramCursor.getInt(0))
    {
    default:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      paramView.findViewById(R.id.loading).setVisibility(i);
      paramView.findViewById(R.id.not_found).setVisibility(j);
      paramView.findViewById(R.id.error).setVisibility(k);
      break;
      i = 0;
      continue;
      j = 0;
      continue;
      k = 0;
    }
  }

  public final void changeCircleMembers$2c8bde3e(Cursor paramCursor, boolean paramBoolean)
  {
    this.mShowMembership = paramBoolean;
    changeCursor(0, paramCursor);
  }

  public final boolean isCursorClosingEnabled()
  {
    return false;
  }

  protected final View newView$54126883(Context paramContext, int paramInt, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    switch (paramInt)
    {
    default:
    case 5:
    }
    for (Object localObject = new PersonCardView(paramContext); ; localObject = LayoutInflater.from(paramContext).inflate(R.layout.people_search_status_card, paramViewGroup, false))
      return localObject;
  }

  public final void onActionButtonClick(PersonCardView paramPersonCardView, int paramInt)
  {
    if (this.mListener != null)
      switch (paramInt)
      {
      default:
      case 0:
      case 1:
      }
    while (true)
    {
      return;
      if (!TextUtils.isEmpty(paramPersonCardView.getWellFormedEmail()))
      {
        showPersonNameDialog("add_email_dialog");
      }
      else
      {
        this.mListener.onChangeCirclesAction(paramPersonCardView.getPersonId(), paramPersonCardView.getContactName());
        continue;
        this.mListener.onUnblockPersonAction(paramPersonCardView.getPersonId(), false);
      }
    }
  }

  public final void onChangeCircles(PersonCardView paramPersonCardView)
  {
    this.mListener.onChangeCirclesAction(paramPersonCardView.getPersonId(), paramPersonCardView.getContactName());
  }

  public final void onDismissButtonClick(PersonCardView paramPersonCardView)
  {
    if (this.mListener != null)
      this.mListener.onDismissSuggestionAction(paramPersonCardView.getPersonId(), paramPersonCardView.getSuggestionId());
  }

  public final void onItemClick(PersonCardView paramPersonCardView)
  {
    onItemClick(paramPersonCardView.getPosition());
  }

  public final void setCircleSpinnerAdapter(CircleSpinnerAdapter paramCircleSpinnerAdapter)
  {
    this.mCircleSpinnerAdapter = paramCircleSpinnerAdapter;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.PeopleSearchGridAdapter
 * JD-Core Version:    0.6.2
 */