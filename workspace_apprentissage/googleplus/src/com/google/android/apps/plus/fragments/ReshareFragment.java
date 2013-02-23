package com.google.android.apps.plus.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.ApiUtils;
import com.google.android.apps.plus.api.OzServerException;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.AvatarRequest;
import com.google.android.apps.plus.content.DbAudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.content.SquareTargetData;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.StreamAdapter.StreamQuery;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.service.ImageCache;
import com.google.android.apps.plus.service.ImageCache.ImageConsumer;
import com.google.android.apps.plus.service.ImageCache.OnAvatarChangeListener;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.MentionTokenizer;
import com.google.android.apps.plus.util.PeopleUtils;
import com.google.android.apps.plus.util.SoftInput;
import com.google.android.apps.plus.views.AudienceView;
import com.google.android.apps.plus.views.MentionMultiAutoCompleteTextView;

public class ReshareFragment extends AudienceFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, TextView.OnEditorActionListener, AlertFragmentDialog.AlertDialogListener, ImageCache.ImageConsumer, ImageCache.OnAvatarChangeListener
{
  private static Bitmap sAuthorBitmap;
  private EsAccount mAccount;
  private String mActivityId;
  private String mAuthorId;
  private ImageCache mAvatarCache;
  private AvatarRequest mAvatarRequest;
  private ImageView mAvatarView;
  private MentionMultiAutoCompleteTextView mEditor;
  private boolean mLimited;
  private Integer mPendingRequestId;
  private TextView mReshareInfo;
  private ScrollView mScrollView;
  private final EsServiceListener mServiceListener = new ServiceListener((byte)0);
  private final TextWatcher mTextWatcher = new TextWatcher()
  {
    final MentionTokenizer mentionTokenizer = new MentionTokenizer();

    public final void afterTextChanged(Editable paramAnonymousEditable)
    {
    }

    public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
    }

    public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      if (ReshareFragment.this.mEditor == null);
      while (true)
      {
        return;
        int i = ReshareFragment.this.mEditor.getSelectionEnd();
        if (this.mentionTokenizer.findTokenStart(paramAnonymousCharSequence, i) + ReshareFragment.this.mEditor.getThreshold() <= i)
        {
          int j = (int)ReshareFragment.this.getActivity().getResources().getDimension(R.dimen.plus_mention_suggestion_min_space);
          int[] arrayOfInt = new int[2];
          ReshareFragment.this.mEditor.getLocationOnScreen(arrayOfInt);
          Rect localRect = new Rect();
          ReshareFragment.this.getView().getWindowVisibleDisplayFrame(localRect);
          int k = arrayOfInt[1] + ReshareFragment.this.mEditor.getCursorYPosition();
          if (localRect.height() - k < j)
            ReshareFragment.this.mScrollView.smoothScrollTo(0, ReshareFragment.this.mEditor.getCursorYTop());
        }
      }
    }
  };

  private void handleServiceCallback(int paramInt, ServiceResult paramServiceResult)
  {
    if ((this.mPendingRequestId == null) || (this.mPendingRequestId.intValue() != paramInt));
    while (true)
    {
      return;
      this.mPendingRequestId = null;
      DialogFragment localDialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("req_pending");
      if (localDialogFragment != null)
        localDialogFragment.dismiss();
      if ((paramServiceResult != null) && (paramServiceResult.hasError()))
      {
        Exception localException = paramServiceResult.getException();
        if (((localException instanceof OzServerException)) && (((OzServerException)localException).getErrorCode() == 14))
        {
          AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(getString(R.string.post_not_sent_title), getString(R.string.post_restricted_mention_error), getString(R.string.ok), null);
          localAlertFragmentDialog.setTargetFragment(getTargetFragment(), 0);
          localAlertFragmentDialog.show(getFragmentManager(), "StreamPostRestrictionsNotSupported");
        }
        else
        {
          Toast.makeText(getActivity(), R.string.reshare_post_error, 0).show();
        }
      }
      else
      {
        getActivity().finish();
      }
    }
  }

  public void onAvatarChanged(String paramString)
  {
    if ((paramString != null) && (paramString.equals(String.valueOf(this.mAuthorId))))
      this.mAvatarCache.refreshImage(this, this.mAvatarRequest);
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Intent localIntent = getActivity().getIntent();
    this.mAccount = ((EsAccount)localIntent.getParcelableExtra("account"));
    this.mActivityId = localIntent.getStringExtra("activity_id");
    this.mLimited = localIntent.getBooleanExtra("limited", false);
    this.mAvatarCache = ImageCache.getInstance(getActivity());
    if (sAuthorBitmap == null)
      sAuthorBitmap = EsAvatarData.getTinyDefaultAvatar(getActivity(), true);
    if (this.mLimited);
    for (int i = 9; ; i = 5)
    {
      setCirclesUsageType(i);
      setIncludePhoneOnlyContacts(false);
      setIncludePlusPages(true);
      if ((paramBundle != null) && (paramBundle.containsKey("reshare_request_id")))
        this.mPendingRequestId = Integer.valueOf(paramBundle.getInt("reshare_request_id"));
      return;
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    Object localObject;
    switch (paramInt)
    {
    default:
      localObject = null;
    case 2:
    case 3:
    }
    while (true)
    {
      return localObject;
      localObject = new EsCursorLoader(getActivity(), EsProvider.appendAccountParameter(EsProvider.ACCOUNT_STATUS_URI, this.mAccount), AccountStatusQuery.PROJECTION, null, null, null);
      continue;
      Uri.Builder localBuilder = EsProvider.ACTIVITY_VIEW_BY_ACTIVITY_ID_URI.buildUpon();
      localBuilder.appendPath(this.mActivityId);
      EsProvider.appendAccountParameter(localBuilder, this.mAccount);
      localObject = new EsCursorLoader(getActivity(), localBuilder.build(), StreamAdapter.StreamQuery.PROJECTION_ACTIVITY, null, null, null);
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.reshare_fragment, paramViewGroup, false);
    this.mAvatarView = ((ImageView)localView.findViewById(R.id.reshare_avatar));
    this.mReshareInfo = ((TextView)localView.findViewById(R.id.reshare_info));
    return localView;
  }

  public final void onDestroyView()
  {
    this.mEditor.removeTextChangedListener(this.mTextWatcher);
    this.mEditor.destroy();
    this.mEditor = null;
    super.onDestroyView();
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
    if ("quit".equals(paramString))
      getActivity().finish();
  }

  public final void onDiscard()
  {
    SoftInput.hide(this.mEditor);
    int i;
    if (!TextUtils.isEmpty(this.mEditor.getText()))
    {
      i = 1;
      if (i == 0)
        break label81;
      AlertFragmentDialog localAlertFragmentDialog = AlertFragmentDialog.newInstance(getString(R.string.reshare_title), getString(R.string.post_quit_question), getString(R.string.yes), getString(R.string.no));
      localAlertFragmentDialog.setTargetFragment(this, 0);
      localAlertFragmentDialog.show(getFragmentManager(), "quit");
    }
    while (true)
    {
      return;
      i = 0;
      break;
      label81: getActivity().finish();
    }
  }

  public boolean onEditorAction(TextView paramTextView, int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramTextView == this.mEditor)
      switch (paramInt)
      {
      default:
      case 6:
      }
    for (boolean bool = false; ; bool = true)
    {
      return bool;
      SoftInput.hide(paramTextView);
    }
  }

  public final void onLoadFinished(Loader<Cursor> paramLoader, Cursor paramCursor)
  {
    switch (paramLoader.getId())
    {
    default:
    case 2:
    case 3:
    }
    while (true)
    {
      return;
      if ((!this.mAudienceView.isEdited()) && (paramCursor != null) && (paramCursor.moveToFirst()))
      {
        byte[] arrayOfByte = paramCursor.getBlob(0);
        if (arrayOfByte != null)
        {
          AudienceData localAudienceData = DbAudienceData.deserialize(arrayOfByte);
          this.mAudienceView.setDefaultAudience(localAudienceData);
          continue;
          FragmentActivity localFragmentActivity = getActivity();
          if ((paramCursor != null) && (paramCursor.moveToFirst()))
          {
            if (!TextUtils.isEmpty(paramCursor.getString(18)))
              this.mAuthorId = paramCursor.getString(18);
            for (String str = paramCursor.getString(19); ; str = paramCursor.getString(3))
            {
              this.mReshareInfo.setText(getString(R.string.originally_shared, new Object[] { str }).toUpperCase());
              this.mAvatarRequest = new AvatarRequest(this.mAuthorId, 0, true);
              this.mAvatarCache.loadImage(this, this.mAvatarRequest);
              break;
              this.mAuthorId = paramCursor.getString(2);
            }
          }
          EsService.getActivity(localFragmentActivity, this.mAccount, this.mActivityId, null);
        }
      }
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onPause()
  {
    EsService.unregisterListener(this.mServiceListener);
    ImageCache.unregisterAvatarChangeListener(this);
    super.onPause();
  }

  public final void onResume()
  {
    super.onResume();
    EsService.registerListener(this.mServiceListener);
    ImageCache.registerAvatarChangeListener(this);
    if ((this.mPendingRequestId != null) && (!EsService.isRequestPending(this.mPendingRequestId.intValue())))
    {
      ServiceResult localServiceResult = EsService.removeResult(this.mPendingRequestId.intValue());
      handleServiceCallback(this.mPendingRequestId.intValue(), localServiceResult);
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mPendingRequestId != null)
      paramBundle.putInt("reshare_request_id", this.mPendingRequestId.intValue());
  }

  public final void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mScrollView = ((ScrollView)paramView.findViewById(R.id.mention_scroll_view));
    this.mEditor = ((MentionMultiAutoCompleteTextView)paramView.findViewById(R.id.reshare_text));
    this.mEditor.init(this, this.mAccount, null, this.mAudienceView);
    this.mEditor.setOnEditorActionListener(this);
    this.mEditor.addTextChangedListener(this.mTextWatcher);
    this.mSearchListAdapter.setShowPersonNameDialog(false);
    getLoaderManager().initLoader(3, null, this);
    if (!this.mLimited)
      getLoaderManager().initLoader(2, null, this);
  }

  public final boolean reshare()
  {
    boolean bool = false;
    SoftInput.hide(this.mEditor);
    AudienceData localAudienceData1 = getAudience();
    if (PeopleUtils.isEmpty(localAudienceData1))
      this.mAudienceView.performClick();
    while (true)
    {
      return bool;
      FragmentActivity localFragmentActivity1 = getActivity();
      String str = ApiUtils.buildPostableString$6d7f0b14(this.mEditor.getText());
      this.mPendingRequestId = Integer.valueOf(EsService.reshareActivity(localFragmentActivity1, this.mAccount, this.mActivityId, str, localAudienceData1));
      ProgressFragmentDialog.newInstance(null, getString(R.string.post_operation_pending), false).show(getFragmentManager(), "req_pending");
      FragmentActivity localFragmentActivity2 = getActivity();
      FragmentActivity localFragmentActivity3 = getActivity();
      Bundle localBundle = null;
      if (localFragmentActivity3 != null)
      {
        AudienceView localAudienceView = this.mAudienceView;
        localBundle = null;
        if (localAudienceView != null)
        {
          AudienceData localAudienceData2 = this.mAudienceView.getAudience();
          localBundle = null;
          if (localAudienceData2 != null)
          {
            int i = localAudienceData2.getSquareTargetCount();
            localBundle = null;
            if (i > 0)
            {
              localBundle = new Bundle();
              localBundle.putString("extra_square_id", this.mAudienceView.getAudience().getSquareTarget(0).getSquareId());
            }
          }
        }
      }
      OzViews localOzViews = OzViews.getViewForLogging(localFragmentActivity2);
      EsAnalytics.recordActionEvent(localFragmentActivity2, this.mAccount, OzActions.RESHARE, localOzViews, localBundle);
      bool = true;
    }
  }

  public void setBitmap(Bitmap paramBitmap, boolean paramBoolean)
  {
    if (paramBitmap == null)
      this.mAvatarView.setImageBitmap(sAuthorBitmap);
    while (true)
    {
      return;
      this.mAvatarView.setImageBitmap(paramBitmap);
    }
  }

  protected final void setupAudienceClickListener()
  {
    this.mAudienceView.setOnClickListener(this);
  }

  private static abstract interface AccountStatusQuery
  {
    public static final String[] PROJECTION = { "audience_data" };
  }

  private final class ServiceListener extends EsServiceListener
  {
    private ServiceListener()
    {
    }

    public final void onReshareActivity$63505a2b(int paramInt, ServiceResult paramServiceResult)
    {
      ReshareFragment.this.handleServiceCallback(paramInt, paramServiceResult);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.ReshareFragment
 * JD-Core Version:    0.6.2
 */