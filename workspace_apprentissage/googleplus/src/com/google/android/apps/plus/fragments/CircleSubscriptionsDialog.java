package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.style;
import com.google.android.apps.plus.views.CheckableListItemView;
import com.google.android.apps.plus.views.CheckableListItemView.OnItemCheckedChangeListener;
import com.google.android.apps.plus.views.CircleListItemView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CircleSubscriptionsDialog extends AlertFragmentDialog
  implements DialogInterface.OnClickListener, AdapterView.OnItemClickListener
{
  private static CheckableListItemView.OnItemCheckedChangeListener mOnCheckedListener = new CheckableListItemView.OnItemCheckedChangeListener()
  {
    public final void onItemCheckedChanged(CheckableListItemView paramAnonymousCheckableListItemView, boolean paramAnonymousBoolean)
    {
      CircleSubscriptionsDialog.CircleInfo localCircleInfo = (CircleSubscriptionsDialog.CircleInfo)paramAnonymousCheckableListItemView.getTag();
      if (paramAnonymousBoolean);
      for (int i = 4; ; i = 2)
      {
        localCircleInfo.setVolume(i);
        return;
      }
    }
  };
  private ArrayList<CircleInfo> mCircleInfo;
  private ContextThemeWrapper mThemeContext;

  private static ArrayList<CircleInfo> getCircleInfo(Bundle paramBundle)
  {
    return (ArrayList)paramBundle.getSerializable("circle_info");
  }

  public static CircleSubscriptionsDialog newInstance$51fb5134(ArrayList<CircleInfo> paramArrayList)
  {
    CircleSubscriptionsDialog localCircleSubscriptionsDialog = new CircleSubscriptionsDialog();
    Bundle localBundle = new Bundle();
    localBundle.putSerializable("circle_info", paramArrayList);
    localCircleSubscriptionsDialog.setArguments(localBundle);
    return localCircleSubscriptionsDialog;
  }

  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mThemeContext = new ContextThemeWrapper(paramActivity, R.style.CircleSubscriptionList);
  }

  public void onCancel(DialogInterface paramDialogInterface)
  {
    paramDialogInterface.dismiss();
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    switch (paramInt)
    {
    default:
    case -1:
    case -2:
    }
    while (true)
    {
      return;
      HashMap localHashMap = new HashMap();
      int i = this.mCircleInfo.size();
      for (int j = 0; j < i; j++)
      {
        CircleInfo localCircleInfo = (CircleInfo)this.mCircleInfo.get(j);
        if (localCircleInfo.isVolumeChanged())
          localHashMap.put(localCircleInfo.getId(), Integer.valueOf(localCircleInfo.getVolume()));
      }
      if (localHashMap.size() != 0)
        ((HostedStreamFragment)getTargetFragment()).doCircleSubscriptions(localHashMap);
      else
        paramDialogInterface.dismiss();
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
      this.mCircleInfo = getCircleInfo(paramBundle);
  }

  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    final LayoutInflater localLayoutInflater;
    View localView;
    if (paramBundle != null)
    {
      this.mCircleInfo = getCircleInfo(paramBundle);
      localLayoutInflater = LayoutInflater.from(this.mThemeContext);
      localView = localLayoutInflater.inflate(R.layout.circle_subscriptions_dialog, null);
      if (this.mThemeContext.getResources().getConfiguration().orientation != 2)
        break label187;
    }
    label187: for (final boolean bool = true; ; bool = false)
    {
      if (bool)
        localView.findViewById(R.id.message).setVisibility(8);
      ListView localListView = (ListView)localView.findViewById(R.id.list);
      localListView.setOnItemClickListener(this);
      localListView.setAdapter(new ArrayAdapter(this.mThemeContext, 0, this.mCircleInfo)
      {
        public final int getCount()
        {
          int i = super.getCount();
          if (bool)
            i++;
          return i;
        }

        public final int getItemViewType(int paramAnonymousInt)
        {
          int i = 1;
          if ((paramAnonymousInt == 0) && (bool))
            i = 0;
          return i;
        }

        public final View getView(int paramAnonymousInt, View paramAnonymousView, ViewGroup paramAnonymousViewGroup)
        {
          int i = 1;
          if (bool)
          {
            if (paramAnonymousInt == 0)
            {
              localObject = localLayoutInflater.inflate(R.layout.circle_subscriptions_dialog_message, null);
              return localObject;
            }
            paramAnonymousInt--;
          }
          Object localObject = new CircleListItemView(CircleSubscriptionsDialog.this.mThemeContext);
          ((CircleListItemView)localObject).setOnItemCheckedChangeListener(CircleSubscriptionsDialog.mOnCheckedListener);
          ((CircleListItemView)localObject).setAvatarStripVisible(false);
          ((CircleListItemView)localObject).setCheckBoxVisible(i);
          ((CircleListItemView)localObject).updateContentDescription();
          CircleSubscriptionsDialog.CircleInfo localCircleInfo = (CircleSubscriptionsDialog.CircleInfo)CircleSubscriptionsDialog.this.mCircleInfo.get(paramAnonymousInt);
          ((CircleListItemView)localObject).setTag(localCircleInfo);
          ((CircleListItemView)localObject).setCircle(localCircleInfo.getId(), i, localCircleInfo.getName(), localCircleInfo.getMemberCount(), false);
          if (localCircleInfo.getVolume() == 4);
          while (true)
          {
            ((CircleListItemView)localObject).setChecked(i);
            break;
            int j = 0;
          }
        }

        public final int getViewTypeCount()
        {
          if (bool);
          for (int i = 2; ; i = 1)
            return i;
        }
      });
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.mThemeContext);
      localBuilder.setTitle(getString(R.string.circle_subscriptions_dialog_title));
      localBuilder.setPositiveButton(17039370, this);
      localBuilder.setNegativeButton(17039360, this);
      localBuilder.setCancelable(true);
      localBuilder.setView(localView);
      return localBuilder.create();
      this.mCircleInfo = getCircleInfo(getArguments());
      break;
    }
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    ((CircleListItemView)paramView).toggle();
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putSerializable("circle_info", this.mCircleInfo);
  }

  public static final class CircleInfo
    implements Serializable
  {
    private String mId;
    private int mMemberCount;
    private String mName;
    private int mOriginalVolume;
    private int mVolume;

    public CircleInfo(String paramString1, String paramString2, int paramInt1, int paramInt2)
    {
      this.mId = paramString1;
      this.mName = paramString2;
      this.mMemberCount = paramInt1;
      this.mOriginalVolume = paramInt2;
      this.mVolume = paramInt2;
    }

    public final String getId()
    {
      return this.mId;
    }

    public final int getMemberCount()
    {
      return this.mMemberCount;
    }

    public final String getName()
    {
      return this.mName;
    }

    public final int getVolume()
    {
      return this.mVolume;
    }

    public final boolean isVolumeChanged()
    {
      if (this.mVolume != this.mOriginalVolume);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final void setVolume(int paramInt)
    {
      this.mVolume = paramInt;
    }

    public final String toString()
    {
      return "{" + this.mId + ", \"" + this.mName + "\", +" + this.mMemberCount + ", @" + this.mVolume + "}";
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.CircleSubscriptionsDialog
 * JD-Core Version:    0.6.2
 */