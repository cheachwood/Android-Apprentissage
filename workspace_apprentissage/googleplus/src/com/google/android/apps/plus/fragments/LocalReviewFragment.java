package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData.ProfileAndContactData;
import com.google.android.apps.plus.phone.HostedFragment;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.util.MapUtils;
import com.google.android.apps.plus.views.AvatarView;
import com.google.api.services.plusi.model.AuthorProto;
import com.google.api.services.plusi.model.GoogleReviewProto;
import com.google.api.services.plusi.model.SimpleProfile;

public class LocalReviewFragment extends HostedFragment
  implements LoaderManager.LoaderCallbacks<EsPeopleData.ProfileAndContactData>, View.OnClickListener
{
  private EsAccount mAccount;
  private ViewGroup mContainer;
  private Activity mContext;
  private String mPersonId;
  private SimpleProfile mProfile;
  private GoogleReviewProto mReview;
  private int mReviewIndex;
  private int mReviewType;

  public final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.UNKNOWN;
  }

  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mContext = paramActivity;
  }

  public void onClick(View paramView)
  {
    int i = paramView.getId();
    if (i == R.id.author_avatar)
    {
      String str3 = ((AvatarView)paramView).getGaiaId();
      if (!TextUtils.isEmpty(str3))
        startActivity(Intents.getProfileActivityByGaiaIdIntent(getActivity(), this.mAccount, str3, null));
    }
    while (true)
    {
      return;
      if (i == R.id.more_reviews_text)
      {
        String str1 = this.mReview.author.profileId;
        String str2 = "http://maps.google.com/maps?q=*+by:" + Uri.encode(str1);
        MapUtils.launchMapsActivity(this.mContext, Uri.parse(str2));
      }
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = new Bundle();
    localBundle.putString("person_id", this.mPersonId);
    getLoaderManager().initLoader(100, localBundle, this);
  }

  public final Loader<EsPeopleData.ProfileAndContactData> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    if (Log.isLoggable("LocalReviewFragment", 3))
      Log.d("LocalReviewFragment", "Loader<ProfileAndContactData> onCreateLoader()");
    return new ProfileLoader(getActivity(), this.mAccount, paramBundle.getString("person_id"), true);
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mContainer = ((ViewGroup)paramLayoutInflater.inflate(R.layout.local_review_fragment, paramViewGroup, false));
    AvatarView localAvatarView = (AvatarView)this.mContainer.findViewById(R.id.author_avatar);
    localAvatarView.setRounded(true);
    localAvatarView.setAvatarSize(2);
    return this.mContainer;
  }

  public final void onLoaderReset(Loader<EsPeopleData.ProfileAndContactData> paramLoader)
  {
  }

  protected final void onSetArguments(Bundle paramBundle)
  {
    super.onSetArguments(paramBundle);
    this.mAccount = ((EsAccount)paramBundle.getParcelable("account"));
    this.mPersonId = paramBundle.getString("person_id");
    this.mReviewType = paramBundle.getInt("local_review_type");
    this.mReviewIndex = paramBundle.getInt("local_review_index");
  }

  public final void recordNavigationAction()
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.LocalReviewFragment
 * JD-Core Version:    0.6.2
 */