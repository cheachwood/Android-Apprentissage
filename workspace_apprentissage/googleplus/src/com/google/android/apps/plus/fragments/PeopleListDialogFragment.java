package com.google.android.apps.plus.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.views.AvatarView;
import java.util.ArrayList;

public class PeopleListDialogFragment extends DialogFragment
  implements View.OnClickListener, AdapterView.OnItemClickListener
{
  private EsAccount mAccount;
  private PeopleListAdapter mAdapter;

  public void onClick(View paramView)
  {
    dismiss();
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.list_layout_acl, paramViewGroup);
    Bundle localBundle = getArguments();
    ListView localListView = (ListView)localView.findViewById(16908298);
    AudienceData localAudienceData = (AudienceData)localBundle.getParcelable("audience");
    this.mAccount = ((EsAccount)localBundle.getParcelable("account"));
    this.mAdapter = new PeopleListAdapter(getActivity(), localAudienceData);
    localListView.setAdapter(this.mAdapter);
    localListView.setOnItemClickListener(this);
    localView.findViewById(R.id.ok).setOnClickListener(this);
    localView.findViewById(R.id.cancel).setVisibility(8);
    getDialog().setTitle(localBundle.getString("people_list_title"));
    return localView;
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    PeopleListItem localPeopleListItem = (PeopleListItem)this.mAdapter.getItem(paramInt);
    switch (localPeopleListItem.mType)
    {
    default:
    case 0:
    }
    while (true)
    {
      return;
      PersonData localPersonData = localPeopleListItem.mPerson;
      boolean bool = TextUtils.isEmpty(localPersonData.getObfuscatedId());
      String str1 = null;
      if (!bool)
        str1 = localPersonData.getObfuscatedId();
      String str2 = null;
      if (str1 != null)
        str2 = "g:" + str1;
      if (str2 != null)
      {
        Intent localIntent = Intents.getProfileActivityIntent(getActivity(), this.mAccount, str2, null, 0);
        dismiss();
        startActivity(localIntent);
      }
    }
  }

  private static final class PeopleListAdapter extends BaseAdapter
  {
    private final Context mContext;
    private final ArrayList<PeopleListDialogFragment.PeopleListItem> mItems = new ArrayList();

    public PeopleListAdapter(Context paramContext, AudienceData paramAudienceData)
    {
      for (PersonData localPersonData : paramAudienceData.getUsers())
        this.mItems.add(new PeopleListDialogFragment.PeopleListItem(localPersonData));
      int k = paramAudienceData.getHiddenUserCount();
      if (k > 0)
      {
        Resources localResources = paramContext.getResources();
        int m = R.plurals.audience_hidden_user_count;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(k);
        String str = localResources.getQuantityString(m, k, arrayOfObject);
        this.mItems.add(new PeopleListDialogFragment.PeopleListItem(1, str));
      }
      this.mContext = paramContext;
    }

    public final int getCount()
    {
      return this.mItems.size();
    }

    public final Object getItem(int paramInt)
    {
      return this.mItems.get(paramInt);
    }

    public final long getItemId(int paramInt)
    {
      return paramInt;
    }

    public final int getItemViewType(int paramInt)
    {
      return ((PeopleListDialogFragment.PeopleListItem)this.mItems.get(paramInt)).mType;
    }

    public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      View localView;
      PeopleListDialogFragment.PeopleListItem localPeopleListItem;
      AvatarView localAvatarView;
      if (paramView == null)
      {
        localView = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(R.layout.acl_row_view, paramViewGroup, false);
        localPeopleListItem = (PeopleListDialogFragment.PeopleListItem)this.mItems.get(paramInt);
        localView.setTag(localPeopleListItem);
        localAvatarView = (AvatarView)localView.findViewById(R.id.avatar);
        switch (localPeopleListItem.mType)
        {
        default:
        case 0:
        case 1:
        }
      }
      while (true)
      {
        ((TextView)localView.findViewById(R.id.name)).setText(localPeopleListItem.mContent);
        return localView;
        localView = paramView;
        break;
        if ((localPeopleListItem.mPerson != null) && (!TextUtils.isEmpty(localPeopleListItem.mPerson.getObfuscatedId())))
        {
          String str = localPeopleListItem.mPerson.getCompressedPhotoUrl();
          localAvatarView.setGaiaIdAndAvatarUrl(localPeopleListItem.mPerson.getObfuscatedId(), EsAvatarData.uncompressAvatarUrl(str));
        }
        localAvatarView.setVisibility(0);
        continue;
        localAvatarView.setVisibility(4);
      }
    }

    public final int getViewTypeCount()
    {
      return 2;
    }
  }

  private static final class PeopleListItem
  {
    public final String mContent;
    public final PersonData mPerson;
    public final int mType;

    public PeopleListItem(int paramInt, String paramString)
    {
      this.mType = 1;
      this.mContent = paramString;
      this.mPerson = null;
    }

    public PeopleListItem(PersonData paramPersonData)
    {
      this.mType = 0;
      this.mPerson = paramPersonData;
      this.mContent = paramPersonData.getName();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.PeopleListDialogFragment
 * JD-Core Version:    0.6.2
 */