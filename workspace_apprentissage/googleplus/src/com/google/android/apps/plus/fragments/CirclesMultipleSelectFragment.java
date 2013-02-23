package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.style;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.AccountsUtil;
import com.google.android.apps.plus.views.CheckableListItemView;
import com.google.android.apps.plus.views.CheckableListItemView.OnItemCheckedChangeListener;
import com.google.android.apps.plus.views.CircleListItemView;
import java.util.ArrayList;

public class CirclesMultipleSelectFragment extends Fragment
  implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener, AlertFragmentDialog.AlertDialogListener, CirclePropertiesFragmentDialog.CirclePropertiesListener, CheckableListItemView.OnItemCheckedChangeListener
{
  private CirclesCursorAdapter mAdapter;
  private ArrayList<String> mCircleIdSnapshot;
  private int mCircleUsageType;
  private boolean mContactLoaded;
  private ListView mListView;
  private OnCircleSelectionListener mListener;
  private boolean mNewCircleEnabled;
  private ArrayList<String> mNewCircleIds;
  private ArrayList<String> mOldCircleIds;
  private Integer mPendingRequestId;
  private String mPersonId;
  private final EsServiceListener mServiceListener = new EsServiceListener()
  {
    public final void onCreateCircleRequestComplete$6a63df5(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      CirclesMultipleSelectFragment.this.handleServiceCallback(paramAnonymousInt, paramAnonymousServiceResult);
    }
  };
  private ContextThemeWrapper mThemeContext;

  private EsAccount getAccount()
  {
    return (EsAccount)getActivity().getIntent().getExtras().get("account");
  }

  private void parsePackedCircleIds(String paramString)
  {
    this.mOldCircleIds = new ArrayList();
    if (!TextUtils.isEmpty(paramString))
    {
      int j;
      for (int i = 0; i < paramString.length(); i = j + 1)
      {
        j = paramString.indexOf('|', i);
        if (j == -1)
          j = paramString.length();
        this.mOldCircleIds.add(paramString.substring(i, j));
      }
    }
    if (this.mNewCircleIds == null)
      this.mNewCircleIds = new ArrayList(this.mOldCircleIds);
    if (this.mListener != null)
      this.mListener.onCircleSelectionChange();
  }

  public final ArrayList<String> getOriginalCircleIds()
  {
    return this.mOldCircleIds;
  }

  public final ArrayList<String> getSelectedCircleIds()
  {
    return this.mNewCircleIds;
  }

  protected final void handleServiceCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mPendingRequestId == null) || (this.mPendingRequestId.intValue() != paramInt));
    while (true)
    {
      return;
      this.mPendingRequestId = null;
      DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("req_pending");
      if (localDialogFragment != null)
        localDialogFragment.dismiss();
      if (paramServiceResult.hasError())
        Toast.makeText(getActivity(), R.string.transient_server_error, 0).show();
    }
  }

  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mThemeContext = new ContextThemeWrapper(paramActivity, R.style.CircleSelectorList);
    this.mAdapter = new CirclesCursorAdapter(this.mThemeContext);
  }

  public final void onCirclePropertiesChange(String paramString1, String paramString2, boolean paramBoolean)
  {
    if (TextUtils.isEmpty(paramString2));
    while (true)
    {
      return;
      String str = paramString2.trim();
      if (this.mAdapter != null)
      {
        Cursor localCursor2 = this.mAdapter.getCursor();
        int i = 0;
        if (localCursor2 != null)
        {
          boolean bool = localCursor2.moveToFirst();
          i = 0;
          if (bool)
            label55: if (!str.equalsIgnoreCase(localCursor2.getString(2)))
              break label96;
        }
        for (i = 1; ; i = 0)
        {
          if (i == 0)
            break label112;
          Toast.makeText(getActivity(), R.string.toast_circle_already_exists, 0).show();
          break;
          label96: if (localCursor2.moveToNext())
            break label55;
        }
      }
      label112: ProgressFragmentDialog.newInstance(null, getString(R.string.new_circle_operation_pending), false).show(getFragmentManager(), "req_pending");
      this.mCircleIdSnapshot = new ArrayList();
      if (this.mAdapter != null)
      {
        Cursor localCursor1 = this.mAdapter.getCursor();
        if ((localCursor1 != null) && (localCursor1.moveToFirst()))
          do
            this.mCircleIdSnapshot.add(localCursor1.getString(1));
          while (localCursor1.moveToNext());
      }
      this.mPendingRequestId = EsService.createCircle(getActivity(), getAccount(), str, paramBoolean);
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.mNewCircleIds = paramBundle.getStringArrayList("new_circles");
      this.mCircleIdSnapshot = paramBundle.getStringArrayList("existing_circle_ids");
      if (paramBundle.containsKey("request_id"))
        this.mPendingRequestId = Integer.valueOf(paramBundle.getInt("request_id"));
    }
    if ((this.mCircleUsageType == 2) && (this.mPersonId != null))
      getLoaderManager().initLoader(0, null, this);
    getLoaderManager().initLoader(1, null, this);
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    Object localObject;
    switch (paramInt)
    {
    default:
      localObject = null;
    case 0:
    case 1:
    }
    while (true)
    {
      return localObject;
      FragmentActivity localFragmentActivity = getActivity();
      Uri localUri = EsProvider.appendAccountParameter(EsProvider.CONTACTS_URI, getAccount());
      String[] arrayOfString1 = { "name", "packed_circle_ids" };
      String[] arrayOfString2 = new String[1];
      arrayOfString2[0] = this.mPersonId;
      localObject = new EsCursorLoader(localFragmentActivity, localUri, arrayOfString1, "person_id=?", arrayOfString2, null);
      continue;
      localObject = new CircleListLoader(getActivity(), getAccount(), this.mCircleUsageType, new String[] { "_id", "circle_id", "circle_name", "contact_count", "type" });
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    LayoutInflater localLayoutInflater = LayoutInflater.from(this.mThemeContext);
    View localView1 = localLayoutInflater.inflate(R.layout.circles_multiple_select_fragment, null, false);
    this.mListView = ((ListView)localView1.findViewById(16908298));
    if (this.mNewCircleEnabled)
    {
      View localView2 = localLayoutInflater.inflate(R.layout.circles_item_new_circle, paramViewGroup, false);
      localView2.setContentDescription(getString(R.string.create_new_circle));
      this.mListView.addHeaderView(localView2);
    }
    this.mListView.setAdapter(this.mAdapter);
    this.mListView.setOnItemClickListener(this);
    return localView1;
  }

  public final void onDialogCanceled$20f9a4b7(String paramString)
  {
  }

  public final void onDialogListClick$12e92030(int paramInt, Bundle paramBundle)
  {
  }

  public final void onDialogNegativeClick$20f9a4b7(String paramString)
  {
  }

  public final void onDialogPositiveClick(Bundle paramBundle, String paramString)
  {
  }

  public final void onItemCheckedChanged(CheckableListItemView paramCheckableListItemView, boolean paramBoolean)
  {
    String str = ((CircleListItemView)paramCheckableListItemView).getCircleId();
    if (paramBoolean)
      if (!this.mNewCircleIds.contains(str))
        this.mNewCircleIds.add(str);
    while (true)
    {
      if (this.mListener != null)
        this.mListener.onCircleSelectionChange();
      return;
      this.mNewCircleIds.remove(str);
    }
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if ((this.mNewCircleEnabled) && (paramInt == 0))
    {
      getActivity();
      CirclePropertiesFragmentDialog localCirclePropertiesFragmentDialog = CirclePropertiesFragmentDialog.newInstance$47e87423();
      localCirclePropertiesFragmentDialog.setTargetFragment(this, 0);
      localCirclePropertiesFragmentDialog.show(getFragmentManager(), "new_circle_input");
    }
    while (true)
    {
      return;
      ((CircleListItemView)paramView).toggle();
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onPause()
  {
    super.onPause();
    EsService.unregisterListener(this.mServiceListener);
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mServiceListener);
    if ((this.mPendingRequestId != null) && (!EsService.isRequestPending(this.mPendingRequestId.intValue())))
    {
      ServiceResult localServiceResult = EsService.removeResult(this.mPendingRequestId.intValue());
      handleServiceCallback(this.mPendingRequestId.intValue(), localServiceResult);
      this.mPendingRequestId = null;
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putStringArrayList("new_circles", this.mNewCircleIds);
    paramBundle.putStringArrayList("existing_circle_ids", this.mCircleIdSnapshot);
    if (this.mPendingRequestId != null)
      paramBundle.putInt("request_id", this.mPendingRequestId.intValue());
  }

  public final void setCircleUsageType(int paramInt)
  {
    this.mCircleUsageType = 2;
  }

  public final void setNewCircleEnabled(boolean paramBoolean)
  {
    this.mNewCircleEnabled = true;
  }

  public final void setOnCircleSelectionListener(OnCircleSelectionListener paramOnCircleSelectionListener)
  {
    this.mListener = paramOnCircleSelectionListener;
  }

  public final void setPersonId(String paramString)
  {
    this.mPersonId = paramString;
  }

  private final class CirclesCursorAdapter extends CursorAdapter
  {
    public CirclesCursorAdapter(Context arg2)
    {
      super(null, 0);
    }

    public final void bindView(View paramView, Context paramContext, Cursor paramCursor)
    {
      CircleListItemView localCircleListItemView = (CircleListItemView)paramView;
      String str = paramCursor.getString(1);
      int i = paramCursor.getInt(4);
      localCircleListItemView.setCircle(str, i, paramCursor.getString(2), paramCursor.getInt(3), AccountsUtil.isRestrictedCircleForAccount(CirclesMultipleSelectFragment.this.getAccount(), i));
      localCircleListItemView.setChecked(CirclesMultipleSelectFragment.this.mNewCircleIds.contains(str));
      localCircleListItemView.updateContentDescription();
    }

    public final View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
    {
      CircleListItemView localCircleListItemView = new CircleListItemView(paramContext);
      localCircleListItemView.setAvatarStripVisible(false);
      localCircleListItemView.setCheckBoxVisible(true);
      localCircleListItemView.setOnItemCheckedChangeListener(CirclesMultipleSelectFragment.this);
      return localCircleListItemView;
    }
  }

  public static abstract interface OnCircleSelectionListener
  {
    public abstract void onCircleSelectionChange();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.CirclesMultipleSelectFragment
 * JD-Core Version:    0.6.2
 */