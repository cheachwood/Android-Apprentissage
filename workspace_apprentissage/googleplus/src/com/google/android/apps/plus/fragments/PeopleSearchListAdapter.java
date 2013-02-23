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
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.util.AccountsUtil;
import com.google.android.apps.plus.views.CircleListItemView;
import com.google.android.apps.plus.views.PeopleListItemView;
import com.google.android.apps.plus.views.PeopleListItemView.OnActionButtonClickListener;

public final class PeopleSearchListAdapter extends PeopleSearchAdapter
  implements PeopleListItemView.OnActionButtonClickListener
{
  public PeopleSearchListAdapter(Context paramContext, FragmentManager paramFragmentManager, LoaderManager paramLoaderManager, EsAccount paramEsAccount)
  {
    super(paramContext, paramFragmentManager, paramLoaderManager, paramEsAccount);
  }

  public PeopleSearchListAdapter(Context paramContext, FragmentManager paramFragmentManager, LoaderManager paramLoaderManager, EsAccount paramEsAccount, int paramInt)
  {
    super(paramContext, paramFragmentManager, paramLoaderManager, paramEsAccount, 1);
  }

  protected final void bindView(View paramView, int paramInt1, Cursor paramCursor, int paramInt2)
  {
    if ((paramCursor == null) || (paramCursor.isClosed()));
    while (true)
    {
      return;
      switch (paramInt1)
      {
      default:
        break;
      case 1:
        CircleListItemView localCircleListItemView = (CircleListItemView)paramView;
        int n = paramCursor.getInt(2);
        localCircleListItemView.setHighlightedText(this.mQuery);
        localCircleListItemView.setCircle(paramCursor.getString(1), n, paramCursor.getString(3), paramCursor.getInt(4), AccountsUtil.isRestrictedCircleForAccount(this.mAccount, n));
        break;
      case 4:
        PeopleListItemView localPeopleListItemView3 = (PeopleListItemView)paramView;
        localPeopleListItemView3.setHighlightedText(this.mQuery);
        localPeopleListItemView3.setCircleNameResolver(this.mCircleNameResolver);
        String str1 = paramCursor.getString(1);
        localPeopleListItemView3.setPersonId(str1);
        String str2 = paramCursor.getString(3);
        String str3 = paramCursor.getString(2);
        localPeopleListItemView3.setContactIdAndAvatarUrl(str2, str3, EsAvatarData.uncompressAvatarUrl(paramCursor.getString(6)));
        localPeopleListItemView3.setContactName(paramCursor.getString(4));
        String str4 = paramCursor.getString(12);
        String str5 = paramCursor.getString(7);
        int m;
        boolean bool2;
        boolean bool3;
        if (!TextUtils.isEmpty(str5))
        {
          m = 1;
          String str6 = paramCursor.getString(9);
          boolean bool1 = this.mIncludePhoneNumberContacts;
          String str7 = null;
          if (bool1)
            str7 = paramCursor.getString(10);
          localPeopleListItemView3.setPackedCircleIdsEmailAddressPhoneNumberAndSnippet(str5, str6, paramCursor.getString(8), str7, paramCursor.getString(11), str4);
          if ((!this.mAddToCirclesActionEnabled) || (m != 0) || (this.mAccount.getPersonId().equals(str1)))
            break label410;
          bool2 = true;
          localPeopleListItemView3.setAddButtonVisible(bool2);
          if ((this.mAddToCirclesActionEnabled) && (this.mListener != null))
            localPeopleListItemView3.setOnActionButtonClickListener(this);
          bool3 = true;
          if (str3 != null)
          {
            if (paramInt2 != 0)
              break label416;
            bool3 = true;
          }
        }
        while (true)
        {
          localPeopleListItemView3.setFirstRow(bool3);
          if (paramInt2 == -1 + paramCursor.getCount())
            continueLoadingPublicProfiles();
          localPeopleListItemView3.updateContentDescription();
          break;
          m = 0;
          break label236;
          bool2 = false;
          break label329;
          if (paramCursor.moveToPrevious())
          {
            if (TextUtils.equals(str3, paramCursor.getString(2)))
              bool3 = false;
            paramCursor.moveToNext();
          }
        }
      case 2:
        PeopleListItemView localPeopleListItemView2 = (PeopleListItemView)paramView;
        localPeopleListItemView2.setWellFormedEmail(this.mQuery);
        localPeopleListItemView2.setAddButtonVisible(this.mAddToCirclesActionEnabled);
        if ((this.mAddToCirclesActionEnabled) && (this.mListener != null))
          localPeopleListItemView2.setOnActionButtonClickListener(this);
        localPeopleListItemView2.updateContentDescription();
        break;
      case 3:
        label236: PeopleListItemView localPeopleListItemView1 = (PeopleListItemView)paramView;
        label329: localPeopleListItemView1.setWellFormedSms(this.mQuery);
        label410: label416: localPeopleListItemView1.setAddButtonVisible(this.mAddToCirclesActionEnabled);
        if ((this.mAddToCirclesActionEnabled) && (this.mListener != null))
          localPeopleListItemView1.setOnActionButtonClickListener(this);
        localPeopleListItemView1.updateContentDescription();
      case 5:
      }
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

  protected final View newView$54126883(Context paramContext, int paramInt, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    Object localObject;
    switch (paramInt)
    {
    default:
      localObject = null;
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    }
    while (true)
    {
      return localObject;
      localObject = new CircleListItemView(paramContext);
      continue;
      localObject = PeopleListItemView.createInstance(paramContext);
      continue;
      localObject = LayoutInflater.from(paramContext).inflate(R.layout.people_search_item_public_profiles, paramViewGroup, false);
    }
  }

  public final void onActionButtonClick(PeopleListItemView paramPeopleListItemView, int paramInt)
  {
    if (paramInt == 0)
    {
      if (TextUtils.isEmpty(paramPeopleListItemView.getWellFormedEmail()))
        break label21;
      showPersonNameDialog("add_email_dialog");
    }
    while (true)
    {
      return;
      label21: if (!TextUtils.isEmpty(paramPeopleListItemView.getWellFormedSms()))
        showPersonNameDialog("add_sms_dialog");
      else
        this.mListener.onAddPersonToCirclesAction(paramPeopleListItemView.getPersonId(), null, true);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.PeopleSearchListAdapter
 * JD-Core Version:    0.6.2
 */