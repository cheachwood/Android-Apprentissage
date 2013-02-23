package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.AnalyticsInfo;
import com.google.android.apps.plus.analytics.InstrumentedActivity;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsApiProvider;
import com.google.android.apps.plus.external.PlatformContractUtils;
import com.google.android.apps.plus.network.ApiaryApiInfo;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.views.AvatarView;

public class PlusOneFragment extends EsFragment
{
  private EsAccount mAccount;
  private ApiaryApiInfo mApiaryApiInfo;
  private boolean mInsert;
  private boolean mLoggedPreview;
  private final LoaderManager.LoaderCallbacks<Cursor> mPreviewLoaderCallbacks = new PreviewLoaderCallbacks();
  private ProgressBar mProgressView;
  private Integer mRequestId;
  private final EsServiceListener mServiceListener = new EsServiceListener()
  {
    public final void onPlusOneApplyResult(int paramAnonymousInt, ServiceResult paramAnonymousServiceResult)
    {
      if (Integer.valueOf(paramAnonymousInt).equals(PlusOneFragment.this.mRequestId))
        PlusOneFragment.this.onFinishedWrite(paramAnonymousServiceResult);
    }
  };
  private String mToken;
  private String mUrl;

  public final EsAccount getAccount()
  {
    return (EsAccount)getArguments().getParcelable("PlusOneFragment#mAccount");
  }

  protected final boolean isEmpty()
  {
    return false;
  }

  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    Bundle localBundle = getArguments();
    this.mApiaryApiInfo = ((ApiaryApiInfo)localBundle.getSerializable("PlusOneFragment#mApiaryApiInfo"));
    this.mToken = localBundle.getString("PlusOneFragment#mToken");
    this.mUrl = localBundle.getString("PlusOneFragment#mUrl");
    this.mInsert = localBundle.getBoolean("PlusOneFragment#mInsert");
    this.mAccount = ((EsAccount)localBundle.getParcelable("PlusOneFragment#mAccount"));
    if ((paramBundle == null) && (this.mInsert))
    {
      InstrumentedActivity localInstrumentedActivity = (InstrumentedActivity)getActivity();
      PlatformContractUtils.getCallingPackageAnalytics(this.mApiaryApiInfo);
      AnalyticsInfo localAnalyticsInfo = localInstrumentedActivity.getAnalyticsInfo$7d6d37aa();
      this.mRequestId = Integer.valueOf(EsService.applyPlusOne(getActivity(), this.mAccount, localAnalyticsInfo, this.mApiaryApiInfo, this.mUrl, this.mInsert, this.mToken));
      this.mLoggedPreview = false;
    }
    while (true)
    {
      ((AvatarView)getView().findViewById(R.id.plus_one_user_avatar)).setGaiaId(this.mAccount.getGaiaId());
      TextView localTextView1 = (TextView)getView().findViewById(R.id.plus_one_user_name);
      Resources localResources = getResources();
      int i = R.string.plus_one_title;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mAccount.getDisplayName();
      localTextView1.setText(Html.fromHtml(localResources.getString(i, arrayOfObject)));
      String str = this.mApiaryApiInfo.getSourceInfo().getPackageName();
      PackageManager localPackageManager = getActivity().getPackageManager();
      ImageView localImageView = (ImageView)getView().findViewById(R.id.plus_one_app_icon);
      TextView localTextView2 = (TextView)getView().findViewById(R.id.plus_one_app_name);
      try
      {
        localImageView.setImageDrawable(localPackageManager.getApplicationIcon(str));
        CharSequence localCharSequence = localPackageManager.getApplicationLabel(localPackageManager.getApplicationInfo(str, 0));
        localTextView2.setText(Html.fromHtml(getResources().getString(R.string.plus_one_app, new Object[] { localCharSequence })));
        ((Button)getView().findViewById(R.id.share)).setOnClickListener(new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            PlusOneFragment.access$100(PlusOneFragment.this, OzActions.PLATFORM_CLICKED_SHARE_FROM_PLUSONE);
            ApiaryApiInfo localApiaryApiInfo = PlusOneFragment.this.mApiaryApiInfo.getSourceInfo();
            Intent localIntent1 = new Intent("com.google.android.apps.plus.SHARE_GOOGLE", Uri.parse(PlusOneFragment.this.mUrl));
            localIntent1.putExtra("com.google.android.apps.plus.API_KEY", localApiaryApiInfo.getApiKey());
            localIntent1.putExtra("com.google.android.apps.plus.CLIENT_ID", localApiaryApiInfo.getClientId());
            localIntent1.putExtra("com.google.android.apps.plus.VERSION", localApiaryApiInfo.getSdkVersion());
            localIntent1.putExtra("com.google.android.apps.plus.IS_FROM_PLUSONE", true);
            Intent localIntent2 = Intents.getTargetIntent(PlusOneFragment.this.getActivity(), localIntent1, PlusOneFragment.this.mApiaryApiInfo.getSourceInfo().getPackageName());
            localIntent2.putExtra("from_signup", true);
            localIntent2.putExtra("start_editing", true);
            PlusOneFragment.this.startActivityForResult(localIntent2, 1);
          }
        });
        Button localButton1 = (Button)getView().findViewById(R.id.plusone_confirm_button);
        Button localButton2 = (Button)getView().findViewById(R.id.plusone_cancel_button);
        View.OnClickListener local3 = new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            FragmentActivity localFragmentActivity;
            int i;
            if (PlusOneFragment.this.mInsert)
            {
              PlusOneFragment.access$100(PlusOneFragment.this, OzActions.PLATFORM_PLUSONE_CANCELED);
              AnalyticsInfo localAnalyticsInfo = new AnalyticsInfo(OzViews.PLATFORM_PLUS_ONE, OzViews.PLATFORM_THIRD_PARTY_APP, System.currentTimeMillis(), PlatformContractUtils.getCallingPackageAnalytics(PlusOneFragment.this.mApiaryApiInfo));
              EsService.applyPlusOne(PlusOneFragment.this.getActivity(), PlusOneFragment.this.mAccount, localAnalyticsInfo, PlusOneFragment.this.mApiaryApiInfo, PlusOneFragment.this.mUrl, false, PlusOneFragment.this.mToken);
              localFragmentActivity = PlusOneFragment.this.getActivity();
              boolean bool = PlusOneFragment.this.mInsert;
              i = 0;
              if (!bool)
                break label145;
            }
            while (true)
            {
              localFragmentActivity.setResult(i);
              PlusOneFragment.this.getActivity().finish();
              return;
              PlusOneFragment.access$100(PlusOneFragment.this, OzActions.PLATFORM_UNDO_PLUSONE_CONFIRMED);
              break;
              label145: i = -1;
            }
          }
        };
        View.OnClickListener local4 = new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            FragmentActivity localFragmentActivity;
            if (PlusOneFragment.this.mInsert)
            {
              PlusOneFragment.access$100(PlusOneFragment.this, OzActions.PLATFORM_PLUSONE_CONFIRMED);
              localFragmentActivity = PlusOneFragment.this.getActivity();
              if (!PlusOneFragment.this.mInsert)
                break label69;
            }
            label69: for (int i = -1; ; i = 0)
            {
              localFragmentActivity.setResult(i);
              PlusOneFragment.this.getActivity().finish();
              return;
              PlusOneFragment.access$100(PlusOneFragment.this, OzActions.PLATFORM_UNDO_PLUSONE_CANCELED);
              break;
            }
          }
        };
        localButton1.setText(R.string.plusone_ok);
        localButton1.setOnClickListener(local4);
        localButton2.setText(R.string.plusone_undo);
        localButton2.setOnClickListener(local3);
        getLoaderManager().initLoader(0, Bundle.EMPTY, this.mPreviewLoaderCallbacks);
        updateSpinner(this.mProgressView);
        return;
        if (paramBundle == null)
          continue;
        if (paramBundle.containsKey("PlusOneFragment#mRequestId"));
        for (Integer localInteger = Integer.valueOf(paramBundle.getInt("PlusOneFragment#mRequestId")); ; localInteger = null)
        {
          this.mRequestId = localInteger;
          this.mLoggedPreview = paramBundle.getBoolean("PlusOneFragment#mLoggedPreview");
          break;
        }
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        while (true)
        {
          localTextView2.setVisibility(4);
          localImageView.setVisibility(4);
        }
      }
    }
  }

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 1)
    {
      getActivity().setResult(paramInt2, paramIntent);
      getActivity().finish();
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(R.layout.plus_one_fragment, paramViewGroup, false);
  }

  protected final void onFinishedWrite(ServiceResult paramServiceResult)
  {
    this.mRequestId = null;
    FragmentActivity localFragmentActivity = getActivity();
    if (paramServiceResult.hasError())
      localFragmentActivity.showDialog(1);
    updateSpinner(this.mProgressView);
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
    if ((this.mRequestId != null) && (!EsService.isRequestPending(this.mRequestId.intValue())))
    {
      ServiceResult localServiceResult = EsService.removeResult(this.mRequestId.intValue());
      if (localServiceResult == null)
        break label60;
      onFinishedWrite(localServiceResult);
    }
    while (true)
    {
      updateSpinner(this.mProgressView);
      return;
      label60: this.mRequestId = null;
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mRequestId != null)
      paramBundle.putInt("PlusOneFragment#mRequestId", this.mRequestId.intValue());
    while (true)
    {
      paramBundle.putBoolean("PlusOneFragment#mLoggedPreview", this.mLoggedPreview);
      return;
      paramBundle.remove("PlusOneFragment#mRequestId");
    }
  }

  public final void setProgressBar(ProgressBar paramProgressBar)
  {
    this.mProgressView = paramProgressBar;
    updateSpinner(this.mProgressView);
  }

  protected final void updateSpinner(ProgressBar paramProgressBar)
  {
    ProgressBar localProgressBar;
    if (this.mProgressView != null)
    {
      localProgressBar = this.mProgressView;
      if (this.mRequestId != null)
        break label28;
    }
    label28: for (int i = 8; ; i = 0)
    {
      localProgressBar.setVisibility(i);
      return;
    }
  }

  final class PreviewLoaderCallbacks
    implements LoaderManager.LoaderCallbacks<Cursor>
  {
    PreviewLoaderCallbacks()
    {
    }

    public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
    {
      EsCursorLoader localEsCursorLoader = new EsCursorLoader(PlusOneFragment.this.getActivity());
      localEsCursorLoader.setUri(EsApiProvider.makePreviewUri(PlusOneFragment.this.mApiaryApiInfo));
      String[] arrayOfString = new String[1];
      arrayOfString[0] = PlusOneFragment.this.mUrl;
      localEsCursorLoader.setSelectionArgs(arrayOfString);
      return localEsCursorLoader;
    }

    public final void onLoaderReset(Loader<Cursor> paramLoader)
    {
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.PlusOneFragment
 * JD-Core Version:    0.6.2
 */