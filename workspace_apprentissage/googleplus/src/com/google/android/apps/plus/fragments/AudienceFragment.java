package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.style;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.CircleData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAudienceData;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.phone.EsCursorAdapter;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.realtimechat.ParticipantUtils;
import com.google.android.apps.plus.realtimechat.RealTimeChatService;
import com.google.android.apps.plus.realtimechat.RealTimeChatServiceListener;
import com.google.android.apps.plus.realtimechat.RealTimeChatServiceResult;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.views.AudienceView;
import com.google.android.apps.plus.views.SuggestedPeopleListItemView;
import com.google.android.apps.plus.views.TypeableAudienceView;
import com.google.wireless.realtimechat.proto.Client.BunchServerResponse;
import com.google.wireless.realtimechat.proto.Client.Suggestion;
import com.google.wireless.realtimechat.proto.Client.SuggestionsRequest.SuggestionsType;
import com.google.wireless.realtimechat.proto.Client.SuggestionsResponse;
import com.google.wireless.realtimechat.proto.Data.Participant;
import com.google.wireless.realtimechat.proto.Data.Participant.Builder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AudienceFragment extends EsFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener, AdapterView.OnItemClickListener, PeopleSearchAdapter.SearchListAdapterListener
{
  private List<Data.Participant> displayedSuggestedParticipants;
  private EsAccount mAccount;
  private Runnable mAudienceChangedCallback;
  protected AudienceData mAudienceResult;
  protected AudienceView mAudienceView;
  private boolean mCacheSuggestionsResponse;
  private int mCircleUsageType;
  private boolean mFilterNullGaiaIds;
  private GridView mGridView;
  private boolean mIncludePhoneOnlyContacts = true;
  private boolean mIncludePlusPages;
  private TextView mListHeader;
  private View mListParent;
  private boolean mPublicProfileSearchEnabled;
  private RTCListener mRealTimeChatListener = new RTCListener((byte)0);
  private Integer mRequestId = null;
  protected PeopleSearchListAdapter mSearchListAdapter;
  private boolean mShowSuggestedPeople;
  private List<Data.Participant> mSuggestedPeople;
  private SuggestedPeopleAdpater mSuggestedPeopleAdapter;
  private ScrollView mSuggestedPeopleScrollView;
  private int mSuggestedPeopleSize = 0;

  private void cacheSuggestedResponse(Client.SuggestionsResponse paramSuggestionsResponse)
  {
    if (this.mCacheSuggestionsResponse)
    {
      EsAudienceData.processSuggestionsResponse(getActivity(), this.mAccount, paramSuggestionsResponse);
      this.mCacheSuggestionsResponse = false;
    }
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

  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mAccount = ((EsAccount)getActivity().getIntent().getParcelableExtra("account"));
  }

  protected final void onAudienceChanged()
  {
    if (this.mAudienceChangedCallback != null)
      this.mAudienceChangedCallback.run();
  }

  public final void onChangeCirclesAction(String paramString1, String paramString2)
  {
  }

  public final void onCircleSelected(String paramString, CircleData paramCircleData)
  {
    this.mAudienceView.addCircle(paramCircleData);
    if ((this.mAudienceView instanceof TypeableAudienceView))
      ((TypeableAudienceView)this.mAudienceView).clearText();
  }

  public void onClick(View paramView)
  {
    int i = paramView.getId();
    if ((i == R.id.edit_audience) || (i == R.id.audience_view))
    {
      AudienceData localAudienceData = this.mAudienceView.getAudience();
      startActivityForResult(Intents.getEditAudienceActivityIntent(getActivity(), this.mAccount, getString(R.string.realtimechat_edit_audience_activity_title), localAudienceData, this.mCircleUsageType, this.mIncludePhoneOnlyContacts, this.mIncludePlusPages, this.mPublicProfileSearchEnabled, this.mFilterNullGaiaIds), 1);
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mSuggestedPeople = new LinkedList();
    this.displayedSuggestedParticipants = new LinkedList();
    if (paramBundle != null)
    {
      if (!paramBundle.containsKey("request_id"))
        break label111;
      this.mRequestId = Integer.valueOf(paramBundle.getInt("request_id"));
    }
    for (this.mCacheSuggestionsResponse = paramBundle.getBoolean("cache_suggestions_response"); ; this.mCacheSuggestionsResponse = false)
    {
      this.mShowSuggestedPeople = paramBundle.getBoolean("show_suggested_people");
      this.mPublicProfileSearchEnabled = paramBundle.getBoolean("public_profile_search");
      this.mIncludePhoneOnlyContacts = paramBundle.getBoolean("phone_only_contacts");
      this.mIncludePlusPages = paramBundle.getBoolean("plus_pages");
      return;
      label111: this.mRequestId = null;
    }
  }

  public Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    if (EsLog.isLoggable("Audience", 3))
      Log.d("Audience", "onCreateLoader " + paramInt);
    Uri localUri;
    if (paramInt == 1)
      localUri = EsProvider.appendAccountParameter(EsProvider.HANGOUT_SUGGESTIONS_URI, this.mAccount);
    for (EsCursorLoader localEsCursorLoader = new EsCursorLoader(getActivity(), localUri, HangoutSuggestionsQuery.PROJECTION, null, null, "sequence ASC", null); ; localEsCursorLoader = null)
      return localEsCursorLoader;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.audience_fragment, paramViewGroup, false);
    this.mGridView = ((GridView)localView.findViewById(16908298));
    this.mSuggestedPeopleScrollView = ((ScrollView)localView.findViewById(R.id.suggested_people_scroll_view));
    this.mListParent = localView.findViewById(R.id.list_layout_parent);
    this.mListHeader = ((TextView)localView.findViewById(R.id.list_header));
    this.mGridView.setOnItemClickListener(this);
    this.mSuggestedPeopleAdapter = new SuggestedPeopleAdpater(getActivity(), null);
    this.mGridView.setAdapter(this.mSuggestedPeopleAdapter);
    return localView;
  }

  public final void onDismissSuggestionAction(String paramString1, String paramString2)
  {
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    this.mSuggestedPeopleAdapter.onItemClick(paramInt);
  }

  public void onLoadFinished(Loader<Cursor> paramLoader, Cursor paramCursor)
  {
    if (EsLog.isLoggable("Audience", 3))
      Log.d("Audience", "onLoadFinished " + paramLoader.getId());
    if ((paramLoader.getId() == 1) && (paramCursor != null) && (paramCursor.moveToFirst()))
    {
      do
      {
        Data.Participant localParticipant = Data.Participant.newBuilder().setParticipantId(paramCursor.getString(1)).setFullName(paramCursor.getString(2)).setFirstName(paramCursor.getString(3)).build();
        this.mSuggestedPeople.add(localParticipant);
      }
      while (paramCursor.moveToNext());
      updateSuggestedPeopleDisplay();
    }
  }

  public void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public void onPause()
  {
    super.onPause();
    if (this.mShowSuggestedPeople)
      RealTimeChatService.unregisterListener(this.mRealTimeChatListener);
  }

  public final void onPersonSelected(String paramString1, String paramString2, PersonData paramPersonData)
  {
    this.mAudienceView.addPerson(paramPersonData);
    if ((this.mAudienceView instanceof TypeableAudienceView))
      ((TypeableAudienceView)this.mAudienceView).clearText();
  }

  public void onResume()
  {
    super.onResume();
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
  }

  public void onSaveInstanceState(Bundle paramBundle)
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
  }

  public final void onStop()
  {
    super.onStart();
    if (this.mSearchListAdapter != null)
      this.mSearchListAdapter.onStop();
  }

  public final void onUnblockPersonAction(String paramString, boolean paramBoolean)
  {
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mAudienceView = ((AudienceView)paramView.findViewById(R.id.audience_view));
    this.mSearchListAdapter = new PeopleSearchListAdapter(new ContextThemeWrapper(getActivity(), R.style.CircleBrowserTheme), getFragmentManager(), getLoaderManager(), this.mAccount);
    this.mSearchListAdapter.setIncludePhoneNumberContacts(this.mIncludePhoneOnlyContacts);
    this.mSearchListAdapter.setIncludePlusPages(this.mIncludePlusPages);
    this.mSearchListAdapter.setPublicProfileSearchEnabled(this.mPublicProfileSearchEnabled);
    this.mSearchListAdapter.setCircleUsageType(this.mCircleUsageType);
    this.mSearchListAdapter.setFilterNullGaiaIds(this.mFilterNullGaiaIds);
    this.mSearchListAdapter.setListener(this);
    this.mSearchListAdapter.onCreate(paramBundle);
    if ((this.mAudienceView instanceof TypeableAudienceView))
    {
      TypeableAudienceView localTypeableAudienceView = (TypeableAudienceView)this.mAudienceView;
      localTypeableAudienceView.setEmptyAudienceHint(R.string.realtimechat_new_conversation_hint_text);
      localTypeableAudienceView.setAutoCompleteAdapter(this.mSearchListAdapter);
    }
    this.mAudienceView.setAccount(this.mAccount);
    this.mAudienceView.initLoaders(getLoaderManager());
    setupAudienceClickListener();
    this.mAudienceView.setAudienceChangedCallback(new Runnable()
    {
      public final void run()
      {
        if (AudienceFragment.this.mShowSuggestedPeople)
        {
          AudienceFragment.this.getSuggestedPeople();
          if ((AudienceFragment.this.mSuggestedPeopleAdapter.isEmpty()) && (AudienceFragment.this.mListHeader != null))
            AudienceFragment.this.mListHeader.setVisibility(8);
          AudienceFragment.this.updateSuggestedPeopleDisplay();
        }
        AudienceFragment.this.onAudienceChanged();
      }
    });
    if (this.mShowSuggestedPeople)
    {
      if ((this.mSuggestedPeopleAdapter.isEmpty()) && (this.mListHeader != null))
        this.mListHeader.setVisibility(8);
      getSuggestedPeople();
    }
  }

  public final void setAudienceChangedCallback(Runnable paramRunnable)
  {
    this.mAudienceChangedCallback = paramRunnable;
  }

  public final void setCirclesUsageType(int paramInt)
  {
    this.mCircleUsageType = paramInt;
  }

  public final void setFilterNullGaiaIds(boolean paramBoolean)
  {
    this.mFilterNullGaiaIds = true;
  }

  public final void setIncludePhoneOnlyContacts(boolean paramBoolean)
  {
    this.mIncludePhoneOnlyContacts = paramBoolean;
  }

  public final void setIncludePlusPages(boolean paramBoolean)
  {
    this.mIncludePlusPages = paramBoolean;
  }

  public final void setPublicProfileSearchEnabled(boolean paramBoolean)
  {
    this.mPublicProfileSearchEnabled = true;
  }

  public final void setShowSuggestedPeople(boolean paramBoolean)
  {
    this.mShowSuggestedPeople = true;
  }

  protected void setupAudienceClickListener()
  {
    getView().findViewById(R.id.edit_audience).setOnClickListener(this);
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
      if ((AudienceFragment.this.mRequestId != null) && (paramInt == AudienceFragment.this.mRequestId.intValue()) && (paramRealTimeChatServiceResult.getErrorCode() == 1) && (paramRealTimeChatServiceResult.getCommand().hasSuggestionsResponse()))
      {
        Client.SuggestionsResponse localSuggestionsResponse = paramRealTimeChatServiceResult.getCommand().getSuggestionsResponse();
        AudienceFragment.this.loadSuggestedPeople(localSuggestionsResponse);
        AudienceFragment.this.cacheSuggestedResponse(localSuggestionsResponse);
      }
    }

    public final void onResponseTimeout(int paramInt)
    {
      AudienceFragment.this.mRequestId.intValue();
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
        if (AudienceFragment.this.isInAudience(ParticipantUtils.getParticipantIdFromPerson(localPersonData)))
          AudienceFragment.this.mAudienceView.removePerson(localPersonData);
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
          AudienceFragment.this.mAudienceView.addPerson(localPersonData);
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
 * Qualified Name:     com.google.android.apps.plus.fragments.AudienceFragment
 * JD-Core Version:    0.6.2
 */