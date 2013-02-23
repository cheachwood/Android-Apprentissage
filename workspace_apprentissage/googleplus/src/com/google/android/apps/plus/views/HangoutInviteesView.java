package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.fragments.CircleNameResolver;
import com.google.android.apps.plus.phone.EsCursorLoader;
import java.util.ArrayList;

public class HangoutInviteesView extends FrameLayout
{
  private static final String[] INVITEE_PROJECTION = { "packed_circle_ids" };
  private EsAccount mAccount;
  private AvatarView mAvatarView;
  private View mCircleLogoView;
  private CircleNameResolver mCircleNameResolver;
  private TextView mCirclesView;
  private String mInviteeId;
  private ArrayList<PersonData> mInvitees = new ArrayList();
  private LinearLayout mMultipleInviteesContainer;
  private HorizontalScrollView mMultipleInviteesView;
  private TextView mNameView;
  private String mPackedCircleIds;
  private final PersonLoaderCallbacks mPersonLoaderCallbacks = new PersonLoaderCallbacks((byte)0);
  private View mSingleInviteeView;

  public HangoutInviteesView(Context paramContext)
  {
    super(paramContext);
    addView(inflate(R.layout.hangout_invitees_view));
    addView(createMultipleInviteesView());
    this.mAvatarView = ((AvatarView)findViewById(R.id.avatar));
    this.mNameView = ((TextView)findViewById(R.id.name));
    this.mCirclesView = ((TextView)findViewById(R.id.circles));
    this.mSingleInviteeView = findViewById(R.id.single_invitee_view);
    this.mCircleLogoView = findViewById(R.id.circle_logo);
  }

  public HangoutInviteesView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    addView(inflate(R.layout.hangout_invitees_view));
    addView(createMultipleInviteesView());
    this.mAvatarView = ((AvatarView)findViewById(R.id.avatar));
    this.mNameView = ((TextView)findViewById(R.id.name));
    this.mCirclesView = ((TextView)findViewById(R.id.circles));
    this.mSingleInviteeView = findViewById(R.id.single_invitee_view);
    this.mCircleLogoView = findViewById(R.id.circle_logo);
  }

  public HangoutInviteesView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    addView(inflate(R.layout.hangout_invitees_view));
    addView(createMultipleInviteesView());
    this.mAvatarView = ((AvatarView)findViewById(R.id.avatar));
    this.mNameView = ((TextView)findViewById(R.id.name));
    this.mCirclesView = ((TextView)findViewById(R.id.circles));
    this.mSingleInviteeView = findViewById(R.id.single_invitee_view);
    this.mCircleLogoView = findViewById(R.id.circle_logo);
  }

  private HorizontalScrollView createMultipleInviteesView()
  {
    Context localContext = getContext();
    this.mMultipleInviteesView = new HorizontalScrollView(localContext);
    FrameLayout.LayoutParams localLayoutParams1 = new FrameLayout.LayoutParams(-1, -1);
    this.mMultipleInviteesView.setLayoutParams(localLayoutParams1);
    this.mMultipleInviteesView.setBackgroundResource(R.color.hangout_common_menu_background);
    this.mMultipleInviteesView.setVisibility(8);
    this.mMultipleInviteesContainer = new LinearLayout(localContext);
    FrameLayout.LayoutParams localLayoutParams2 = new FrameLayout.LayoutParams(-2, -1);
    this.mMultipleInviteesView.addView(this.mMultipleInviteesContainer, localLayoutParams2);
    return this.mMultipleInviteesView;
  }

  private static String getGaiaId(PersonData paramPersonData)
  {
    if (paramPersonData.getObfuscatedId() != null);
    for (String str = paramPersonData.getObfuscatedId(); ; str = "")
      return str;
  }

  private View inflate(int paramInt)
  {
    return LayoutInflater.from(getContext()).inflate(paramInt, this, false);
  }

  public final int getAvatarCount()
  {
    return this.mInvitees.size();
  }

  public void setInvitees(AudienceData paramAudienceData, EsAccount paramEsAccount)
  {
    Context localContext = getContext();
    this.mAccount = paramEsAccount;
    this.mInvitees.clear();
    for (PersonData localPersonData : paramAudienceData.getUsers())
      if ((!TextUtils.isEmpty(localPersonData.getName())) && (!TextUtils.isEmpty(localPersonData.getObfuscatedId())))
        this.mInvitees.add(localPersonData);
    if (this.mInvitees.size() == 1)
    {
      this.mInviteeId = getGaiaId((PersonData)this.mInvitees.get(0));
      this.mSingleInviteeView.setVisibility(0);
      this.mMultipleInviteesView.setVisibility(8);
      String str2 = ((PersonData)this.mInvitees.get(0)).getName();
      this.mNameView.setText(str2);
      this.mAvatarView.setGaiaId(this.mInviteeId);
      LoaderManager localLoaderManager = ((FragmentActivity)getContext()).getSupportLoaderManager();
      localLoaderManager.initLoader(0, null, this.mPersonLoaderCallbacks);
      this.mCircleNameResolver = new CircleNameResolver(localContext, localLoaderManager, this.mAccount);
      this.mCircleNameResolver.initLoader();
      this.mCircleNameResolver.registerObserver(new DataSetObserver()
      {
        public final void onChanged()
        {
          HangoutInviteesView.access$100(HangoutInviteesView.this);
        }
      });
    }
    while (true)
    {
      return;
      if (this.mInvitees.size() > 1)
      {
        this.mSingleInviteeView.setVisibility(8);
        this.mMultipleInviteesView.setVisibility(0);
        this.mMultipleInviteesContainer.removeAllViews();
        int k = 0;
        int m = this.mInvitees.size();
        while (k < m)
        {
          String str1 = getGaiaId((PersonData)this.mInvitees.get(k));
          LinearLayout localLinearLayout = this.mMultipleInviteesContainer;
          AvatarView localAvatarView = new AvatarView(getContext());
          localAvatarView.setGaiaId(str1);
          int n = getResources().getDimensionPixelSize(R.dimen.hangout_invitees_view_height);
          LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(n, n);
          localLayoutParams.rightMargin = getResources().getDimensionPixelSize(R.dimen.hangout_avatar_margin);
          localAvatarView.setLayoutParams(localLayoutParams);
          localLinearLayout.addView(localAvatarView);
          k++;
        }
      }
    }
  }

  public void setName(String paramString)
  {
    this.mNameView.setVisibility(0);
    this.mNameView.setText(paramString);
  }

  public void setVisibility(int paramInt)
  {
    super.setVisibility(paramInt);
    if (paramInt == 8)
    {
      this.mSingleInviteeView.setVisibility(8);
      this.mMultipleInviteesView.setVisibility(8);
    }
  }

  private final class PersonLoaderCallbacks
    implements LoaderManager.LoaderCallbacks<Cursor>
  {
    private PersonLoaderCallbacks()
    {
    }

    public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
    {
      if ((HangoutInviteesView.this.mAccount == null) || (HangoutInviteesView.this.mInviteeId == null));
      final Context localContext;
      Uri localUri;
      String[] arrayOfString1;
      String[] arrayOfString2;
      for (Object localObject = null; ; localObject = new EsCursorLoader(localContext, localUri, arrayOfString1, "gaia_id=?", arrayOfString2, null)
      {
        public final Cursor esLoadInBackground()
        {
          EsPeopleData.ensurePeopleSynced(localContext, HangoutInviteesView.this.mAccount);
          return super.esLoadInBackground();
        }
      })
      {
        return localObject;
        localContext = HangoutInviteesView.this.getContext();
        localUri = EsProvider.appendAccountParameter(EsProvider.CONTACTS_URI, HangoutInviteesView.this.mAccount);
        arrayOfString1 = HangoutInviteesView.INVITEE_PROJECTION;
        arrayOfString2 = new String[1];
        arrayOfString2[0] = HangoutInviteesView.this.mInviteeId;
      }
    }

    public final void onLoaderReset(Loader<Cursor> paramLoader)
    {
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.HangoutInviteesView
 * JD-Core Version:    0.6.2
 */