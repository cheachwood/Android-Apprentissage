package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
import com.google.android.apps.plus.views.CircleListItemView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SimpleAudiencePickerDialog extends AlertFragmentDialog
  implements DialogInterface.OnClickListener, AdapterView.OnItemClickListener
{
  private ArrayList<CircleInfo> mOptions = new ArrayList();
  private ContextThemeWrapper mThemeContext;

  public static SimpleAudiencePickerDialog newInstance(String paramString1, String paramString2, boolean paramBoolean)
  {
    SimpleAudiencePickerDialog localSimpleAudiencePickerDialog = new SimpleAudiencePickerDialog();
    Bundle localBundle = new Bundle();
    localBundle.putString("domain_name", paramString1);
    localBundle.putString("domain_id", paramString2);
    localBundle.putBoolean("has_public_circle", paramBoolean);
    localSimpleAudiencePickerDialog.setArguments(localBundle);
    return localSimpleAudiencePickerDialog;
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
    case -2:
    }
    while (true)
    {
      return;
      paramDialogInterface.dismiss();
    }
  }

  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    Bundle localBundle = getArguments();
    String str1 = localBundle.getString("domain_name");
    String str2 = localBundle.getString("domain_id");
    boolean bool = localBundle.getBoolean("has_public_circle");
    View localView = LayoutInflater.from(this.mThemeContext).inflate(R.layout.simple_audience_picker_dialog, null);
    this.mOptions.add(new CircleInfo("1f", 7, getString(R.string.acl_extended_network)));
    if (str1 != null)
      this.mOptions.add(new CircleInfo(str2, 8, str1));
    if (bool)
      this.mOptions.add(new CircleInfo("0", 9, getString(R.string.acl_public)));
    this.mOptions.add(new CircleInfo("1c", 5, getString(R.string.acl_your_circles)));
    this.mOptions.add(new CircleInfo("v.private", 101, getString(R.string.acl_private)));
    this.mOptions.add(new CircleInfo("v.custom", -3, getString(R.string.post_create_custom_acl)));
    ListView localListView = (ListView)localView.findViewById(R.id.list);
    localListView.setOnItemClickListener(this);
    localListView.setAdapter(new ArrayAdapter(this.mThemeContext, 0, this.mOptions)
    {
      public final int getItemViewType(int paramAnonymousInt)
      {
        return 0;
      }

      public final View getView(int paramAnonymousInt, View paramAnonymousView, ViewGroup paramAnonymousViewGroup)
      {
        CircleListItemView localCircleListItemView = new CircleListItemView(SimpleAudiencePickerDialog.this.mThemeContext);
        localCircleListItemView.setAvatarStripVisible(false);
        localCircleListItemView.setCheckBoxVisible(false);
        localCircleListItemView.setMemberCountVisible(false);
        localCircleListItemView.updateContentDescription();
        SimpleAudiencePickerDialog.CircleInfo localCircleInfo = (SimpleAudiencePickerDialog.CircleInfo)getItem(paramAnonymousInt);
        localCircleListItemView.setTag(localCircleInfo);
        localCircleListItemView.setCircle(localCircleInfo.getId(), localCircleInfo.getType(), localCircleInfo.getName(), 0, false);
        return localCircleListItemView;
      }

      public final int getViewTypeCount()
      {
        return 1;
      }
    });
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.mThemeContext);
    localBuilder.setTitle(R.string.profile_edit_item_visibility);
    localBuilder.setNegativeButton(17039360, this);
    localBuilder.setCancelable(true);
    localBuilder.setView(localView);
    return localBuilder.create();
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    CircleInfo localCircleInfo = (CircleInfo)paramView.getTag();
    ((ProfileEditFragment)getTargetFragment()).onSetSimpleAudience(localCircleInfo.getId(), localCircleInfo.getType(), localCircleInfo.getName());
    getDialog().dismiss();
  }

  public static final class CircleInfo
    implements Serializable
  {
    private String mId;
    private String mName;
    private int mType;

    public CircleInfo(String paramString1, int paramInt, String paramString2)
    {
      this.mId = paramString1;
      this.mName = paramString2;
      this.mType = paramInt;
    }

    public final String getId()
    {
      return this.mId;
    }

    public final String getName()
    {
      return this.mName;
    }

    public final int getType()
    {
      return this.mType;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.SimpleAudiencePickerDialog
 * JD-Core Version:    0.6.2
 */