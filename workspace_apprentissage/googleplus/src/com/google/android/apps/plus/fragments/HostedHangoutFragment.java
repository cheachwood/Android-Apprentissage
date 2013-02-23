package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.style;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.CircleData;
import com.google.android.apps.plus.content.EsAudienceData;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.hangout.GCommApp;
import com.google.android.apps.plus.hangout.GCommService;
import com.google.android.apps.plus.phone.EsCursorAdapter;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.ShakeDetector;
import com.google.android.apps.plus.realtimechat.ParticipantUtils;
import com.google.android.apps.plus.realtimechat.RealTimeChatService;
import com.google.android.apps.plus.realtimechat.RealTimeChatServiceListener;
import com.google.android.apps.plus.realtimechat.RealTimeChatServiceResult;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.HelpUrl;
import com.google.android.apps.plus.util.Property;
import com.google.android.apps.plus.views.HostActionBar;
import com.google.android.apps.plus.views.SuggestedPeopleListItemView;
import com.google.android.apps.plus.views.TypeableAudienceView;
import com.google.wireless.realtimechat.proto.Client.BunchServerResponse;
import com.google.wireless.realtimechat.proto.Client.Suggestion;
import com.google.wireless.realtimechat.proto.Client.SuggestionsRequest.SuggestionsType;
import com.google.wireless.realtimechat.proto.Client.SuggestionsResponse;
import com.google.wireless.realtimechat.proto.Data.Participant;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class HostedHangoutFragment extends HostedEsFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener, AdapterView.OnItemClickListener, PeopleSearchAdapter.SearchListAdapterListener
{
  private static final ActiveHangoutMode ACTIVE_HANGOUT_MODE_DEFAULT = ActiveHangoutMode.MODE_DISABLE;
  private List<Data.Participant> displayedSuggestedParticipants;
  private ActiveHangoutMode mActiveViewMode = ACTIVE_HANGOUT_MODE_DEFAULT;
  private View mAudienceOverlay;
  protected AudienceData mAudienceResult;
  protected TypeableAudienceView mAudienceView;
  private boolean mCacheSuggestionsResponse;
  private int mCircleUsageType;
  private boolean mFilterNullGaiaIds;
  private GridView mGridView;
  private boolean mIncludePhoneOnlyContacts = true;
  private boolean mIncludePlusPages;
  private TextView mListHeader;
  private View mListParent;
  private boolean mPreviouslyAudienceEmpty = true;
  private boolean mPreviouslyOvercapacity = false;
  private boolean mPublicProfileSearchEnabled;
  private RTCListener mRealTimeChatListener = new RTCListener((byte)0);
  private Integer mRequestId = null;
  private Button mResumeHangoutButton;
  private boolean mRingBeforeDisable;
  protected PeopleSearchListAdapter mSearchListAdapter;
  private boolean mShakeDetectorWasRunning;
  private boolean mShowSuggestedPeople;
  private Button mStartHangoutButton;
  private List<Data.Participant> mSuggestedPeople;
  private SuggestedPeopleAdpater mSuggestedPeopleAdapter;
  private ScrollView mSuggestedPeopleScrollView;
  private int mSuggestedPeopleSize = 0;
  private ImageButton mToggleHangoutRingButton;

  private void cacheSuggestedResponse(Client.SuggestionsResponse paramSuggestionsResponse)
  {
    if (this.mCacheSuggestionsResponse)
    {
      EsAudienceData.processSuggestionsResponse(getActivity(), this.mAccount, paramSuggestionsResponse);
      this.mCacheSuggestionsResponse = false;
    }
  }

  private void disableHangoutRing(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mRingBeforeDisable = false;
    this.mToggleHangoutRingButton.setImageResource(R.drawable.icn_ring_off);
    this.mToggleHangoutRingButton.setContentDescription(getString(R.string.hangout_ring_off_content_description));
    if (paramBoolean2)
      if (!paramBoolean1)
        break label47;
    label47: for (int i = R.string.ring_off_overcapacity_hangout_toast; ; i = R.string.ring_off_hangout_toast)
    {
      toast(i);
      return;
    }
  }

  private void enableHangoutRing(boolean paramBoolean)
  {
    this.mRingBeforeDisable = true;
    this.mToggleHangoutRingButton.setImageResource(R.drawable.icn_ring_on);
    this.mToggleHangoutRingButton.setContentDescription(getString(R.string.hangout_ring_on_content_description));
    if (paramBoolean)
      toast(R.string.ring_on_hangout_toast);
  }

  private boolean isInAudience(String paramString)
  {
    PersonData[] arrayOfPersonData = this.mAudienceView.getAudience().getUsers();
    int i = arrayOfPersonData.length;
    int j = 0;
    if (j < i)
    {
      String str = ParticipantUtils.getParticipantIdFromPerson(arrayOfPersonData[j]);
      if ((str == null) || (!str.equals(paramString)));
    }
    for (boolean bool = true; ; bool = false)
    {
      return bool;
      j++;
      break;
    }
  }

  private void loadSuggestedPeople(Client.SuggestionsResponse paramSuggestionsResponse)
  {
    Iterator localIterator1 = paramSuggestionsResponse.getSuggestionList().iterator();
    while (localIterator1.hasNext())
    {
      Iterator localIterator2 = ((Client.Suggestion)localIterator1.next()).getSuggestedUserList().iterator();
      while (localIterator2.hasNext())
      {
        Data.Participant localParticipant = (Data.Participant)localIterator2.next();
        this.mSuggestedPeople.add(localParticipant);
      }
    }
    updateSuggestedPeopleDisplay();
  }

  private void toast(int paramInt)
  {
    String str = getString(paramInt);
    Toast.makeText(getActivity(), str, 0).show();
  }

  private void updateSuggestedPeopleDisplay()
  {
    Iterator localIterator1 = this.mSuggestedPeople.iterator();
    while (localIterator1.hasNext())
    {
      Data.Participant localParticipant2 = (Data.Participant)localIterator1.next();
      Iterator localIterator3 = this.displayedSuggestedParticipants.iterator();
      do
      {
        boolean bool = localIterator3.hasNext();
        m = 0;
        if (!bool)
          break;
      }
      while (!((Data.Participant)localIterator3.next()).getParticipantId().equals(localParticipant2.getParticipantId()));
      int m = 1;
      if (m == 0)
      {
        this.displayedSuggestedParticipants.add(localParticipant2);
        if ((this.mListHeader != null) && (this.mListHeader.getVisibility() != 0))
          this.mListHeader.setVisibility(0);
      }
    }
    int i = 0;
    MatrixCursor localMatrixCursor = new MatrixCursor(SuggestedPeopleQuery.columnNames);
    Iterator localIterator2 = this.displayedSuggestedParticipants.iterator();
    if (localIterator2.hasNext())
    {
      Data.Participant localParticipant1 = (Data.Participant)localIterator2.next();
      Object[] arrayOfObject = new Object[4];
      int j = i + 1;
      arrayOfObject[0] = Integer.valueOf(i);
      arrayOfObject[1] = localParticipant1.getParticipantId();
      arrayOfObject[2] = localParticipant1.getFullName();
      if (isInAudience(localParticipant1.getParticipantId()));
      for (int k = 1; ; k = 0)
      {
        arrayOfObject[3] = Integer.valueOf(k);
        localMatrixCursor.addRow(arrayOfObject);
        i = j;
        break;
      }
    }
    this.mSuggestedPeopleAdapter.swapCursor(localMatrixCursor);
    if ((this.mSuggestedPeopleSize != this.mSuggestedPeopleAdapter.getCount()) && (this.mSuggestedPeopleAdapter.getCount() == this.mGridView.getChildCount()))
    {
      this.mSuggestedPeopleScrollView.scrollTo(0, 0);
      this.mSuggestedPeopleSize = this.mSuggestedPeopleAdapter.getCount();
    }
  }

  public final boolean audienceSizeIsGreaterThan(int paramInt)
  {
    AudienceData localAudienceData = this.mAudienceView.getAudience();
    boolean bool = false;
    if (localAudienceData == null);
    while (true)
    {
      return bool;
      int i = localAudienceData.getUserCount();
      int j = 0;
      if (i > 0)
        j = 0 + localAudienceData.getUserCount();
      CircleData[] arrayOfCircleData = localAudienceData.getCircles();
      int k = arrayOfCircleData.length;
      for (int m = 0; ; m++)
      {
        if (m >= k)
          break label125;
        CircleData localCircleData = arrayOfCircleData[m];
        if ((localCircleData.getType() == 9) || (localCircleData.getType() == 7) || (localCircleData.getType() == 8))
        {
          bool = true;
          break;
        }
        if (localCircleData.getSize() > 0)
          j += localCircleData.getSize();
      }
      label125: bool = false;
      if (j > 10)
        bool = true;
    }
  }

  public final AudienceData getAudience()
  {
    return this.mAudienceView.getAudience();
  }

  protected final void getSuggestedPeople()
  {
    AudienceData localAudienceData = this.mAudienceView.getAudience();
    boolean bool = isAudienceEmpty();
    this.mCacheSuggestionsResponse = bool;
    this.mRequestId = Integer.valueOf(RealTimeChatService.requestSuggestedParticipants(getActivity(), this.mAccount, localAudienceData, Client.SuggestionsRequest.SuggestionsType.HANGOUT));
    if (bool)
      getLoaderManager().initLoader(1, null, this);
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.HANGOUT;
  }

  public final boolean isAudienceEmpty()
  {
    boolean bool = true;
    AudienceData localAudienceData = this.mAudienceView.getAudience();
    if (localAudienceData == null);
    label90: 
    while (true)
    {
      return bool;
      if (localAudienceData.getUserCount() > 0)
      {
        bool = false;
      }
      else
      {
        CircleData[] arrayOfCircleData = localAudienceData.getCircles();
        int i = arrayOfCircleData.length;
        for (int j = 0; ; j++)
        {
          if (j >= i)
            break label90;
          CircleData localCircleData = arrayOfCircleData[j];
          if ((localCircleData.getSize() > 0) || (localCircleData.getType() == 9) || (localCircleData.getType() == 7))
          {
            bool = false;
            break;
          }
        }
      }
    }
  }

  public final boolean isEmpty()
  {
    return false;
  }

  public final void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    if (paramBundle == null)
    {
      AudienceData localAudienceData = (AudienceData)getActivity().getIntent().getParcelableExtra("audience");
      if (localAudienceData != null)
        this.mAudienceView.replaceAudience(localAudienceData);
    }
  }

  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt1 == 1) && (paramInt2 == -1) && (paramIntent != null))
      this.mAudienceResult = ((AudienceData)paramIntent.getParcelableExtra("audience"));
  }

  public final void onAddPersonToCirclesAction(String paramString1, String paramString2, boolean paramBoolean)
  {
  }

  public final void onChangeCirclesAction(String paramString1, String paramString2)
  {
  }

  public final void onCircleSelected(String paramString, CircleData paramCircleData)
  {
    this.mAudienceView.addCircle(paramCircleData);
    this.mAudienceView.clearText();
  }

  public void onClick(View paramView)
  {
    if (paramView.getId() == R.id.edit_audience)
    {
      AudienceData localAudienceData = this.mAudienceView.getAudience();
      startActivityForResult(Intents.getEditAudienceActivityIntent(getActivity(), this.mAccount, getString(R.string.realtimechat_edit_audience_activity_title), localAudienceData, this.mCircleUsageType, this.mIncludePhoneOnlyContacts, this.mIncludePlusPages, this.mPublicProfileSearchEnabled, this.mFilterNullGaiaIds), 1);
    }
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mSuggestedPeople = new LinkedList();
    this.displayedSuggestedParticipants = new LinkedList();
    String str;
    if (paramBundle != null)
    {
      if (paramBundle.containsKey("request_id"))
      {
        this.mRequestId = Integer.valueOf(paramBundle.getInt("request_id"));
        this.mCacheSuggestionsResponse = paramBundle.getBoolean("cache_suggestions_response");
        this.mShowSuggestedPeople = paramBundle.getBoolean("show_suggested_people");
        this.mPublicProfileSearchEnabled = paramBundle.getBoolean("public_profile_search");
        this.mIncludePhoneOnlyContacts = paramBundle.getBoolean("phone_only_contacts");
        this.mIncludePlusPages = paramBundle.getBoolean("plus_pages");
      }
    }
    else
    {
      this.mCircleUsageType = 10;
      this.mIncludePhoneOnlyContacts = false;
      this.mIncludePlusPages = false;
      this.mPublicProfileSearchEnabled = true;
      this.mShowSuggestedPeople = true;
      this.mFilterNullGaiaIds = true;
      str = Property.ACTIVE_HANGOUT_MODE.get();
      if (!"disable".equalsIgnoreCase(str))
        break label179;
      this.mActiveViewMode = ActiveHangoutMode.MODE_DISABLE;
    }
    while (true)
    {
      return;
      this.mRequestId = null;
      this.mCacheSuggestionsResponse = false;
      break;
      label179: if ("hide".equalsIgnoreCase(str))
        this.mActiveViewMode = ActiveHangoutMode.MODE_HIDE;
      else if ("none".equalsIgnoreCase(str))
        this.mActiveViewMode = ActiveHangoutMode.MODE_NONE;
      else
        this.mActiveViewMode = ACTIVE_HANGOUT_MODE_DEFAULT;
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    if (EsLog.isLoggable("HangoutFrag", 3))
      Log.d("HangoutFrag", "onCreateLoader " + paramInt);
    Uri localUri;
    if (paramInt == 1)
      localUri = EsProvider.appendAccountParameter(EsProvider.HANGOUT_SUGGESTIONS_URI, this.mAccount);
    for (EsCursorLoader localEsCursorLoader = new EsCursorLoader(getActivity(), localUri, HangoutSuggestionsQuery.PROJECTION, null, null, "sequence ASC", null); ; localEsCursorLoader = null)
      return localEsCursorLoader;
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.hosted_hangout_fragment, paramViewGroup, false);
    this.mGridView = ((GridView)localView.findViewById(16908298));
    this.mSuggestedPeopleScrollView = ((ScrollView)localView.findViewById(R.id.suggested_people_scroll_view));
    this.mListParent = localView.findViewById(R.id.list_layout_parent);
    this.mListHeader = ((TextView)localView.findViewById(R.id.list_header));
    this.mGridView.setOnItemClickListener(this);
    this.mSuggestedPeopleAdapter = new SuggestedPeopleAdpater(getActivity(), null);
    this.mGridView.setAdapter(this.mSuggestedPeopleAdapter);
    this.mToggleHangoutRingButton = ((ImageButton)localView.findViewById(R.id.toggle_hangout_ring_button));
    disableHangoutRing(false, false);
    this.mToggleHangoutRingButton.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        HostedHangoutFragment.access$500(HostedHangoutFragment.this);
      }
    });
    this.mStartHangoutButton = ((Button)localView.findViewById(R.id.start_hangout_button));
    this.mStartHangoutButton.setEnabled(false);
    this.mStartHangoutButton.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        HostedHangoutFragment.this.startActivity(Intents.getNewHangoutActivityIntent(HostedHangoutFragment.this.getActivity(), HostedHangoutFragment.this.mAccount, HostedHangoutFragment.this.mRingBeforeDisable, HostedHangoutFragment.this.getAudience()));
      }
    });
    this.mResumeHangoutButton = ((Button)localView.findViewById(R.id.resume_hangout_button));
    this.mResumeHangoutButton.setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        GCommApp localGCommApp = GCommApp.getInstance(HostedHangoutFragment.this.getActivity());
        if (localGCommApp.isInAHangout())
        {
          Intent localIntent = localGCommApp.getGCommService().getNotificationIntent();
          if (localIntent != null)
            HostedHangoutFragment.this.startActivity(localIntent);
        }
      }
    });
    this.mAudienceOverlay = localView.findViewById(R.id.audience_overlay);
    this.mAudienceOverlay.setOnTouchListener(null);
    this.mAudienceOverlay.setOnClickListener(null);
    if (Build.VERSION.SDK_INT >= 12)
      this.mAudienceOverlay.setOnGenericMotionListener(null);
    this.mAudienceOverlay.setOnKeyListener(null);
    this.mAudienceOverlay.setOnLongClickListener(null);
    return localView;
  }

  public final void onDismissSuggestionAction(String paramString1, String paramString2)
  {
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    this.mSuggestedPeopleAdapter.onItemClick(paramInt);
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == R.id.help)
    {
      String str = getResources().getString(R.string.url_param_help_hangouts);
      startExternalActivity(new Intent("android.intent.action.VIEW", HelpUrl.getHelpUrl(getActivity(), str)));
    }
    for (boolean bool = true; ; bool = super.onOptionsItemSelected(paramMenuItem))
      return bool;
  }

  public final void onPause()
  {
    super.onPause();
    if (this.mShowSuggestedPeople)
      RealTimeChatService.unregisterListener(this.mRealTimeChatListener);
  }

  public final void onPersonSelected(String paramString1, String paramString2, PersonData paramPersonData)
  {
    this.mAudienceView.addPerson(paramPersonData);
    this.mAudienceView.clearText();
  }

  protected final void onPrepareActionBar(HostActionBar paramHostActionBar)
  {
    paramHostActionBar.showTitle(R.string.home_screen_hangout_label);
  }

  public final void onResume()
  {
    super.onResume();
    this.mStartHangoutButton.setVisibility(0);
    this.mToggleHangoutRingButton.setVisibility(0);
    this.mResumeHangoutButton.setVisibility(8);
    this.mAudienceOverlay.setVisibility(8);
    this.mAudienceView.setVisibility(0);
    this.mListParent.setVisibility(0);
    this.mListHeader.setText(R.string.realtimechat_users_you_may_know);
    if (GCommApp.getInstance(getActivity()).isInAHangout())
    {
      this.mStartHangoutButton.setVisibility(8);
      this.mToggleHangoutRingButton.setVisibility(8);
      this.mResumeHangoutButton.setVisibility(0);
      switch (5.$SwitchMap$com$google$android$apps$plus$fragments$HostedHangoutFragment$ActiveHangoutMode[this.mActiveViewMode.ordinal()])
      {
      default:
      case 1:
      case 2:
      }
    }
    while (true)
    {
      if (this.mAudienceResult != null)
      {
        AudienceData localAudienceData = this.mAudienceResult;
        this.mAudienceView.replaceAudience(localAudienceData);
        this.mAudienceResult = null;
      }
      if (this.mShowSuggestedPeople)
        RealTimeChatService.registerListener(this.mRealTimeChatListener);
      if ((this.mRequestId != null) && (!RealTimeChatService.isRequestPending(this.mRequestId.intValue())))
      {
        RealTimeChatServiceResult localRealTimeChatServiceResult = RealTimeChatService.removeResult(this.mRequestId.intValue());
        if ((localRealTimeChatServiceResult != null) && (localRealTimeChatServiceResult.getErrorCode() == 1) && (localRealTimeChatServiceResult.getCommand() != null) && (localRealTimeChatServiceResult.getCommand().hasSuggestionsResponse()))
        {
          loadSuggestedPeople(localRealTimeChatServiceResult.getCommand().getSuggestionsResponse());
          cacheSuggestedResponse(localRealTimeChatServiceResult.getCommand().getSuggestionsResponse());
        }
      }
      return;
      this.mAudienceView.setVisibility(4);
      this.mListParent.setVisibility(4);
      continue;
      this.mAudienceOverlay.setVisibility(0);
    }
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mSearchListAdapter != null)
      this.mSearchListAdapter.onSaveInstanceState(paramBundle);
    if (this.mRequestId != null)
    {
      paramBundle.putInt("request_id", this.mRequestId.intValue());
      paramBundle.putBoolean("cache_suggestions_response", this.mCacheSuggestionsResponse);
    }
    paramBundle.putBoolean("show_suggested_people", this.mShowSuggestedPeople);
    paramBundle.putBoolean("public_profile_search", this.mPublicProfileSearchEnabled);
    paramBundle.putBoolean("phone_only_contacts", this.mIncludePhoneOnlyContacts);
    paramBundle.putBoolean("plus_pages", this.mIncludePlusPages);
  }

  public final void onSearchListAdapterStateChange(PeopleSearchAdapter paramPeopleSearchAdapter)
  {
    if (this.mListParent != null)
    {
      if (!paramPeopleSearchAdapter.isEmpty())
        break label23;
      this.mListParent.setVisibility(0);
    }
    while (true)
    {
      return;
      label23: this.mListParent.setVisibility(8);
    }
  }

  public final void onStart()
  {
    super.onStart();
    if (this.mSearchListAdapter != null)
      this.mSearchListAdapter.onStart();
    ShakeDetector localShakeDetector = ShakeDetector.getInstance(getActivity());
    if (localShakeDetector != null)
      this.mShakeDetectorWasRunning = localShakeDetector.stop();
  }

  public final void onStop()
  {
    super.onStart();
    if (this.mSearchListAdapter != null)
      this.mSearchListAdapter.onStop();
    if (this.mShakeDetectorWasRunning)
    {
      ShakeDetector localShakeDetector = ShakeDetector.getInstance(getActivity());
      if (localShakeDetector != null)
        localShakeDetector.start();
    }
  }

  public final void onUnblockPersonAction(String paramString, boolean paramBoolean)
  {
  }

  public final void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mAudienceView = ((TypeableAudienceView)paramView.findViewById(R.id.audience_view));
    this.mAudienceView.setEmptyAudienceHint(R.string.realtimechat_new_conversation_hint_text);
    this.mSearchListAdapter = new PeopleSearchListAdapter(new ContextThemeWrapper(getActivity(), R.style.CircleBrowserTheme), getFragmentManager(), getLoaderManager(), this.mAccount);
    this.mSearchListAdapter.setIncludePhoneNumberContacts(this.mIncludePhoneOnlyContacts);
    this.mSearchListAdapter.setIncludePlusPages(this.mIncludePlusPages);
    this.mSearchListAdapter.setPublicProfileSearchEnabled(this.mPublicProfileSearchEnabled);
    this.mSearchListAdapter.setCircleUsageType(this.mCircleUsageType);
    this.mSearchListAdapter.setFilterNullGaiaIds(this.mFilterNullGaiaIds);
    this.mSearchListAdapter.setListener(this);
    this.mSearchListAdapter.onCreate(paramBundle);
    this.mAudienceView.setAutoCompleteAdapter(this.mSearchListAdapter);
    this.mAudienceView.setAccount(this.mAccount);
    this.mAudienceView.initLoaders(getLoaderManager());
    paramView.findViewById(R.id.edit_audience).setOnClickListener(this);
    this.mAudienceView.setAudienceChangedCallback(new Runnable()
    {
      public final void run()
      {
        boolean bool1 = true;
        if (HostedHangoutFragment.this.mShowSuggestedPeople)
        {
          HostedHangoutFragment.this.getSuggestedPeople();
          if ((HostedHangoutFragment.this.mSuggestedPeopleAdapter.isEmpty()) && (HostedHangoutFragment.this.mListHeader != null))
            HostedHangoutFragment.this.mListHeader.setVisibility(8);
          HostedHangoutFragment.this.updateSuggestedPeopleDisplay();
        }
        boolean bool2 = HostedHangoutFragment.this.audienceSizeIsGreaterThan(10);
        boolean bool3 = HostedHangoutFragment.this.isAudienceEmpty();
        ImageButton localImageButton = HostedHangoutFragment.this.mToggleHangoutRingButton;
        boolean bool4;
        label119: Button localButton;
        if (!bool3)
        {
          bool4 = bool1;
          localImageButton.setEnabled(bool4);
          if ((!bool3) && (!bool2))
            break label179;
          HostedHangoutFragment.this.disableHangoutRing(false, false);
          HostedHangoutFragment.access$1302(HostedHangoutFragment.this, bool3);
          HostedHangoutFragment.access$1402(HostedHangoutFragment.this, bool2);
          if (HostedHangoutFragment.this.mStartHangoutButton != null)
          {
            localButton = HostedHangoutFragment.this.mStartHangoutButton;
            if (HostedHangoutFragment.this.isAudienceEmpty())
              break label220;
          }
        }
        while (true)
        {
          localButton.setEnabled(bool1);
          return;
          bool4 = false;
          break;
          label179: if (((!HostedHangoutFragment.this.mPreviouslyAudienceEmpty) && (!HostedHangoutFragment.this.mPreviouslyOvercapacity)) || (HostedHangoutFragment.this.mRingBeforeDisable))
            break label119;
          HostedHangoutFragment.this.enableHangoutRing(false);
          break label119;
          label220: bool1 = false;
        }
      }
    });
    if (this.mShowSuggestedPeople)
    {
      if ((this.mSuggestedPeopleAdapter.isEmpty()) && (this.mListHeader != null))
        this.mListHeader.setVisibility(8);
      getSuggestedPeople();
    }
  }

  private static enum ActiveHangoutMode
  {
    static
    {
      MODE_DISABLE = new ActiveHangoutMode("MODE_DISABLE", 1);
      MODE_HIDE = new ActiveHangoutMode("MODE_HIDE", 2);
      ActiveHangoutMode[] arrayOfActiveHangoutMode = new ActiveHangoutMode[3];
      arrayOfActiveHangoutMode[0] = MODE_NONE;
      arrayOfActiveHangoutMode[1] = MODE_DISABLE;
      arrayOfActiveHangoutMode[2] = MODE_HIDE;
    }
  }

  private static abstract interface HangoutSuggestionsQuery
  {
    public static final String[] PROJECTION = { "_id", "participant_id", "full_name", "first_name" };
  }

  private final class RTCListener extends RealTimeChatServiceListener
  {
    private RTCListener()
    {
    }

    public final void onResponseReceived$1587694a(int paramInt, RealTimeChatServiceResult paramRealTimeChatServiceResult)
    {
      if ((HostedHangoutFragment.this.mRequestId != null) && (paramInt == HostedHangoutFragment.this.mRequestId.intValue()) && (paramRealTimeChatServiceResult.getErrorCode() == 1) && (paramRealTimeChatServiceResult.getCommand().hasSuggestionsResponse()))
      {
        Client.SuggestionsResponse localSuggestionsResponse = paramRealTimeChatServiceResult.getCommand().getSuggestionsResponse();
        HostedHangoutFragment.this.loadSuggestedPeople(localSuggestionsResponse);
        HostedHangoutFragment.this.cacheSuggestedResponse(localSuggestionsResponse);
      }
    }

    public final void onResponseTimeout(int paramInt)
    {
      HostedHangoutFragment.this.mRequestId.intValue();
    }
  }

  private final class SuggestedPeopleAdpater extends EsCursorAdapter
  {
    final LayoutInflater mLayoutInflater;

    public SuggestedPeopleAdpater(Context paramCursor, Cursor arg3)
    {
      super(null);
      this.mLayoutInflater = ((LayoutInflater)paramCursor.getSystemService("layout_inflater"));
    }

    public final void bindView(View paramView, Context paramContext, Cursor paramCursor)
    {
      int i = 1;
      SuggestedPeopleListItemView localSuggestedPeopleListItemView = (SuggestedPeopleListItemView)paramView;
      localSuggestedPeopleListItemView.setPersonId(paramCursor.getString(i));
      localSuggestedPeopleListItemView.setParticipantName(paramCursor.getString(2).replaceAll(" .*", ""));
      if (paramCursor.getInt(3) > 0);
      while (true)
      {
        localSuggestedPeopleListItemView.setChecked(i);
        return;
        int j = 0;
      }
    }

    public final View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
    {
      return this.mLayoutInflater.inflate(R.layout.suggested_people_list_item_view, null);
    }

    public final void onItemClick(int paramInt)
    {
      Object localObject = null;
      Cursor localCursor = getCursor();
      localCursor.moveToPosition(paramInt);
      String str1 = localCursor.getString(2);
      String str2 = localCursor.getString(1);
      String str3;
      if (str2.startsWith("g:"))
        str3 = EsPeopleData.extractGaiaId(str2);
      while (true)
      {
        PersonData localPersonData = new PersonData(str3, str1, (String)localObject);
        if (HostedHangoutFragment.this.isInAudience(ParticipantUtils.getParticipantIdFromPerson(localPersonData)))
          HostedHangoutFragment.this.mAudienceView.removePerson(localPersonData);
        while (true)
        {
          return;
          if (str2.startsWith("e:"))
          {
            localObject = str2.substring(2);
            str3 = null;
            break;
          }
          if (!str2.startsWith("p:"))
            break label149;
          localObject = str2;
          str3 = null;
          break;
          HostedHangoutFragment.this.mAudienceView.addPerson(localPersonData);
        }
        label149: localObject = null;
        str3 = null;
      }
    }
  }

  private static abstract interface SuggestedPeopleQuery
  {
    public static final String[] columnNames = { "_id", "participant_id", "full_name", "in_audience" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.HostedHangoutFragment
 * JD-Core Version:    0.6.2
 */