package com.google.android.apps.plus.fragments;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.text.util.Rfc822Token;
import android.text.util.Rfc822Tokenizer;
import android.util.Patterns;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import com.android.common.widget.EsCompositeCursorAdapter;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.CircleData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.phone.EsMatrixCursor;
import com.google.android.apps.plus.util.AccountsUtil;
import com.google.android.apps.plus.util.MentionTokenizer;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PeopleSearchAdapter extends EsCompositeCursorAdapter
  implements LoaderManager.LoaderCallbacks<Cursor>, Filterable, AlertFragmentDialog.AlertDialogListener
{
  private static final String[] CIRCLES_PROJECTION = { "_id", "circle_id", "type", "circle_name", "contact_count" };
  private static final String[] CONTACT_PROJECTION = { "person_id", "lookup_key", "name", "email" };
  private static final String[] CONTACT_PROJECTION_WITH_PHONE = { "person_id", "lookup_key", "name", "email", "phone", "phone_type" };
  private static final String[] GAIA_ID_CIRCLE_PROJECTION = { "gaia_id", "packed_circle_ids" };
  private static final String[] LOCAL_PROFILE_PROJECTION;
  public static final String[] PEOPLE_PROJECTION = { "_id", "person_id", "gaia_id", "name", "avatar", "packed_circle_ids", "blocked", "profile_type" };
  private static final String[] PUBLIC_PROFILE_PROJECTION = { "_id", "gaia_id", "person_id", "name", "avatar", "profile_type", "snippet" };
  protected final EsAccount mAccount;
  private int mActiveLoaderCount;
  private String mActivityId;
  protected boolean mAddToCirclesActionEnabled;
  private final DataSetObserver mCircleContentObserver = new DataSetObserver()
  {
    public final void onChanged()
    {
      PeopleSearchAdapter.this.notifyDataSetChanged();
    }
  };
  protected final CircleNameResolver mCircleNameResolver;
  private int mCircleUsageType = -1;
  private boolean mCirclesError;
  private boolean mCirclesLoaded;
  private final int mCirclesLoaderId;
  private Cursor mContactsCursor;
  private boolean mContactsError;
  private boolean mContactsLoaded;
  private final int mContactsLoaderId;
  private Filter mFilter;
  private volatile CountDownLatch mFilterLatch;
  private boolean mFilterNullGaiaIds;
  private final FragmentManager mFragmentManager;
  private Cursor mGaiaIdCircleCursor;
  private final int mGaiaIdLoaderId;
  private final Handler mHandler = new Handler()
  {
    public final void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
      case 0:
      case 1:
      }
      while (true)
      {
        return;
        PeopleSearchAdapter.this.showEmptyPeopleSearchResults();
        continue;
        PeopleSearchAdapter.access$000(PeopleSearchAdapter.this);
      }
    }
  };
  private boolean mIncludePeopleInCircles = true;
  protected boolean mIncludePhoneNumberContacts;
  private boolean mIncludePlusPages;
  private boolean mIsMentionsAdapter;
  protected SearchListAdapterListener mListener;
  private final LoaderManager mLoaderManager;
  private boolean mLocalProfileError;
  private Cursor mLocalProfilesCursor;
  private boolean mLocalProfilesLoaded;
  private final int mPeopleLoaderId;
  private final int mProfilesLoaderId;
  private boolean mPublicProfileSearchEnabled;
  private Cursor mPublicProfilesCursor;
  private boolean mPublicProfilesError;
  private boolean mPublicProfilesLoading;
  private boolean mPublicProfilesNotFound;
  protected String mQuery;
  private PeopleSearchResults mResults = new PeopleSearchResults();
  private boolean mResultsPreserved;
  private boolean mShowPersonNameDialog = true;
  private boolean mShowProgressWhenEmpty = true;

  static
  {
    LOCAL_PROFILE_PROJECTION = new String[] { "_id", "person_id", "gaia_id", "name", "avatar", "packed_circle_ids", "email", "profile_type" };
  }

  public PeopleSearchAdapter(Context paramContext, FragmentManager paramFragmentManager, LoaderManager paramLoaderManager, EsAccount paramEsAccount)
  {
    this(paramContext, paramFragmentManager, paramLoaderManager, paramEsAccount, 0);
  }

  public PeopleSearchAdapter(Context paramContext, FragmentManager paramFragmentManager, LoaderManager paramLoaderManager, EsAccount paramEsAccount, int paramInt)
  {
    super(paramContext, (byte)0);
    for (int i = 0; i < 6; i++)
      addPartition(false, false);
    int j = 1024 + paramInt * 10;
    int k = j + 1;
    this.mCirclesLoaderId = j;
    int m = k + 1;
    this.mGaiaIdLoaderId = k;
    int n = m + 1;
    this.mPeopleLoaderId = m;
    int i1 = n + 1;
    this.mContactsLoaderId = n;
    this.mProfilesLoaderId = i1;
    SearchResultsFragment localSearchResultsFragment = (SearchResultsFragment)paramFragmentManager.findFragmentByTag("people_search_results");
    if (localSearchResultsFragment == null)
    {
      localSearchResultsFragment = new SearchResultsFragment();
      paramFragmentManager.beginTransaction().add(localSearchResultsFragment, "people_search_results").commitAllowingStateLoss();
    }
    while (true)
    {
      localSearchResultsFragment.setPeopleSearchResults(this.mResults);
      this.mFragmentManager = paramFragmentManager;
      this.mLoaderManager = paramLoaderManager;
      this.mAccount = paramEsAccount;
      this.mResults.setMyProfile(this.mAccount.getPersonId());
      this.mResults.setIncludePeopleInCircles(this.mIncludePeopleInCircles);
      this.mCircleNameResolver = new CircleNameResolver(paramContext, paramLoaderManager, this.mAccount, paramInt);
      this.mCircleNameResolver.registerObserver(this.mCircleContentObserver);
      return;
      PeopleSearchResults localPeopleSearchResults = localSearchResultsFragment.getPeopleSearchResults();
      if (localPeopleSearchResults != null)
      {
        this.mResults = localPeopleSearchResults;
        this.mQuery = this.mResults.getQuery();
        this.mResultsPreserved = true;
      }
    }
  }

  private void changeCursorForPeoplePartition()
  {
    this.mHandler.removeMessages(0);
    Cursor localCursor = this.mResults.getCursor();
    if (localCursor.getCount() == 0)
      this.mHandler.sendEmptyMessageDelayed(0, 500L);
    while (true)
    {
      return;
      changeCursor(4, localCursor);
    }
  }

  private String getWellFormedEmailAddress()
  {
    if (TextUtils.isEmpty(this.mQuery));
    for (String str = null; ; str = null)
      do
      {
        return str;
        Rfc822Token[] arrayOfRfc822Token = Rfc822Tokenizer.tokenize(this.mQuery);
        if ((arrayOfRfc822Token == null) || (arrayOfRfc822Token.length <= 0))
          break;
        str = arrayOfRfc822Token[0].getAddress();
      }
      while ((!TextUtils.isEmpty(str)) && (Patterns.EMAIL_ADDRESS.matcher(str).matches()));
  }

  private String getWellFormedSmsAddress()
  {
    boolean bool1 = TextUtils.isEmpty(this.mQuery);
    String str = null;
    if (bool1);
    while (true)
    {
      return str;
      boolean bool2 = PhoneNumberUtils.isWellFormedSmsAddress(this.mQuery);
      str = null;
      if (bool2)
      {
        int i = this.mQuery.length();
        int j = 1;
        int k = 0;
        while (true)
        {
          if (k >= i)
            break label100;
          char c = this.mQuery.charAt(k);
          boolean bool3 = PhoneNumberUtils.isDialable(c);
          str = null;
          if (!bool3)
            break;
          if (c == '+')
          {
            str = null;
            if (j == 0)
              break;
          }
          k++;
          j = 0;
        }
        label100: str = this.mQuery;
      }
    }
  }

  private void releaseLatch()
  {
    CountDownLatch localCountDownLatch = this.mFilterLatch;
    if (localCountDownLatch != null)
      localCountDownLatch.countDown();
  }

  private void updatePublicProfileSearchStatus()
  {
    if (!this.mPublicProfileSearchEnabled)
      return;
    EsMatrixCursor localEsMatrixCursor = new EsMatrixCursor(new String[] { "_id" });
    if ((!TextUtils.isEmpty(this.mQuery)) && (this.mQuery.trim().length() >= 2) && (this.mLocalProfilesLoaded) && (this.mContactsLoaded))
    {
      if (!this.mPublicProfilesError)
        break label110;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Integer.valueOf(3);
      localEsMatrixCursor.addRow(arrayOfObject3);
    }
    while (true)
    {
      if (localEsMatrixCursor.getCount() != 0)
        showEmptyPeopleSearchResults();
      changeCursor(5, localEsMatrixCursor);
      break;
      label110: if ((this.mPublicProfilesNotFound) && (!this.mIsMentionsAdapter))
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(2);
        localEsMatrixCursor.addRow(arrayOfObject2);
      }
      else if ((this.mPublicProfilesLoading) && (!this.mIsMentionsAdapter) && ((this.mShowProgressWhenEmpty) || (this.mResults.getCount() > 0)))
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(1);
        localEsMatrixCursor.addRow(arrayOfObject1);
      }
    }
  }

  protected final void continueLoadingPublicProfiles()
  {
    if (!this.mResults.hasMoreResults());
    while (true)
    {
      return;
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          PeopleSearchAdapter.access$100(PeopleSearchAdapter.this);
        }
      });
    }
  }

  public Filter getFilter()
  {
    if (this.mFilter == null)
      this.mFilter = new Filter()
      {
        public final CharSequence convertResultToString(Object paramAnonymousObject)
        {
          Cursor localCursor = (Cursor)paramAnonymousObject;
          String str;
          if ((localCursor == null) || (localCursor.isClosed()))
            str = "";
          while (true)
          {
            return str;
            int i = localCursor.getColumnIndex("circle_name");
            if (i != -1)
            {
              str = localCursor.getString(i);
            }
            else
            {
              int j = localCursor.getColumnIndex("name");
              if (j != -1)
              {
                str = localCursor.getString(j);
              }
              else
              {
                int k = localCursor.getColumnIndex("address");
                if (k != -1)
                  str = localCursor.getString(k);
                else
                  str = "";
              }
            }
          }
        }

        protected final Filter.FilterResults performFiltering(final CharSequence paramAnonymousCharSequence)
        {
          PeopleSearchAdapter.this.releaseLatch();
          CountDownLatch localCountDownLatch = new CountDownLatch(1);
          PeopleSearchAdapter.access$302(PeopleSearchAdapter.this, localCountDownLatch);
          PeopleSearchAdapter.this.mHandler.post(new Runnable()
          {
            public final void run()
            {
              String str;
              if (paramAnonymousCharSequence == null)
                str = null;
              while (true)
              {
                PeopleSearchAdapter.this.setQueryString(str);
                return;
                if (PeopleSearchAdapter.this.mIsMentionsAdapter)
                {
                  int i = paramAnonymousCharSequence.length();
                  if ((i > 0) && (MentionTokenizer.isMentionTrigger(paramAnonymousCharSequence.charAt(0))))
                    str = paramAnonymousCharSequence.subSequence(1, i).toString();
                  else
                    str = null;
                }
                else
                {
                  str = paramAnonymousCharSequence.toString();
                }
              }
            }
          });
          try
          {
            localCountDownLatch.await();
            label49: PeopleSearchAdapter.access$302(PeopleSearchAdapter.this, null);
            return new Filter.FilterResults();
          }
          catch (InterruptedException localInterruptedException)
          {
            break label49;
          }
        }

        protected final void publishResults(CharSequence paramAnonymousCharSequence, Filter.FilterResults paramAnonymousFilterResults)
        {
          paramAnonymousFilterResults.count = PeopleSearchAdapter.this.getCount();
        }
      };
    return this.mFilter;
  }

  protected final int getItemViewType(int paramInt1, int paramInt2)
  {
    return paramInt1;
  }

  public final int getItemViewTypeCount()
  {
    return 6;
  }

  public boolean isEmpty()
  {
    if ((TextUtils.isEmpty(this.mQuery)) || (!this.mCircleNameResolver.isLoaded()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean isError()
  {
    if ((this.mCirclesError) || (this.mLocalProfileError) || (this.mContactsError));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean isLoaded()
  {
    if ((this.mLocalProfilesLoaded) && (this.mContactsLoaded) && ((this.mCircleUsageType == -1) || (this.mCirclesLoaded)) && (this.mCircleNameResolver.isLoaded()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean isSearchingForFirstResult()
  {
    if ((!TextUtils.isEmpty(this.mQuery)) && (this.mResults.getCount() == 0) && ((!isLoaded()) || (this.mPublicProfilesLoading)));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void onCreate(Bundle paramBundle)
  {
    if (paramBundle != null)
    {
      paramBundle.setClassLoader(getClass().getClassLoader());
      this.mQuery = paramBundle.getString("search_list_adapter.query");
      if ((paramBundle.containsKey("search_list_adapter.results")) && (!this.mResultsPreserved))
        this.mResults = ((PeopleSearchResults)paramBundle.getParcelable("search_list_adapter.results"));
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    Object localObject;
    if (paramInt == this.mCirclesLoaderId)
      localObject = new CircleListLoader(getContext(), this.mAccount, this.mCircleUsageType, CIRCLES_PROJECTION, this.mQuery, 10);
    while (true)
    {
      return localObject;
      if (paramInt == this.mGaiaIdLoaderId)
      {
        localObject = new PeopleListLoader(getContext(), this.mAccount, GAIA_ID_CIRCLE_PROJECTION, null, this.mIncludePlusPages, this.mFilterNullGaiaIds);
      }
      else
      {
        if (paramInt == this.mContactsLoaderId)
        {
          Context localContext = getContext();
          if (this.mIncludePhoneNumberContacts);
          for (String[] arrayOfString = CONTACT_PROJECTION_WITH_PHONE; ; arrayOfString = CONTACT_PROJECTION)
          {
            localObject = new AndroidContactSearchLoader(localContext, arrayOfString, this.mQuery, 2, this.mIncludePhoneNumberContacts);
            break;
          }
        }
        if (paramInt == this.mPeopleLoaderId)
          localObject = new PeopleSearchListLoader(getContext(), this.mAccount, LOCAL_PROFILE_PROJECTION, this.mQuery, this.mIncludePlusPages, this.mIncludePeopleInCircles, this.mFilterNullGaiaIds, this.mActivityId, 10);
        else if (paramInt == this.mProfilesLoaderId)
          localObject = new PublicProfileSearchLoader(getContext(), this.mAccount, PUBLIC_PROFILE_PROJECTION, this.mQuery, 2, this.mIncludePlusPages, this.mFilterNullGaiaIds, this.mResults.getToken());
        else
          localObject = null;
      }
    }
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
    String str4;
    String str5;
    String str6;
    if ("add_email_dialog".equals(paramString))
    {
      str4 = paramBundle.getString("message");
      str5 = getWellFormedEmailAddress();
      if ((!TextUtils.isEmpty(str4)) && (!TextUtils.isEmpty(str5)))
      {
        str6 = "e:" + str5;
        if (!this.mAddToCirclesActionEnabled)
          break label82;
        this.mListener.onChangeCirclesAction(str6, str4);
      }
    }
    while (true)
    {
      return;
      label82: PersonData localPersonData2 = new PersonData(null, str4, str5);
      this.mListener.onPersonSelected(str6, null, localPersonData2);
      continue;
      if ("add_sms_dialog".equals(paramString))
      {
        String str1 = paramBundle.getString("message");
        String str2 = getWellFormedSmsAddress();
        if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2)))
        {
          String str3 = "p:" + str2;
          if (this.mAddToCirclesActionEnabled)
          {
            this.mListener.onChangeCirclesAction(str3, str1);
          }
          else
          {
            PersonData localPersonData1 = new PersonData(null, str1, str3);
            this.mListener.onPersonSelected(str3, null, localPersonData1);
          }
        }
      }
    }
  }

  public final void onItemClick(int paramInt)
  {
    Cursor localCursor = (Cursor)getItem(paramInt);
    if (localCursor == null);
    while (true)
    {
      return;
      switch (getPartitionForPosition(paramInt))
      {
      default:
        break;
      case 0:
        String str13 = localCursor.getString(1);
        PersonData localPersonData4 = new PersonData(localCursor.getString(2), localCursor.getString(3), null);
        this.mListener.onPersonSelected(str13, null, localPersonData4);
        break;
      case 1:
        final Context localContext = getContext();
        final String str11 = localCursor.getString(1);
        String str12 = localCursor.getString(3);
        int i = localCursor.getInt(2);
        final CircleData localCircleData = new CircleData(str11, i, str12, localCursor.getInt(4));
        if ((AccountsUtil.isRestrictedCircleForAccount(this.mAccount, i)) && (!EsAccountsData.hasSeenMinorPublicExtendedDialog(localContext, this.mAccount)))
        {
          AlertDialog.Builder localBuilder = new AlertDialog.Builder(localContext);
          localBuilder.setTitle(localCursor.getString(3));
          localBuilder.setMessage(R.string.dialog_public_or_extended_circle_for_minor);
          int j = R.string.ok;
          DialogInterface.OnClickListener local4 = new DialogInterface.OnClickListener()
          {
            public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
              PeopleSearchAdapter.this.mListener.onCircleSelected(str11, localCircleData);
              EsAccountsData.saveMinorPublicExtendedDialogSeenPreference(localContext, PeopleSearchAdapter.this.mAccount, true);
            }
          };
          localBuilder.setPositiveButton(j, local4);
          int k = R.string.cancel;
          DialogInterface.OnClickListener local5 = new DialogInterface.OnClickListener()
          {
            public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
            }
          };
          localBuilder.setNegativeButton(k, local5);
          localBuilder.show();
        }
        else
        {
          this.mListener.onCircleSelected(str11, localCircleData);
        }
        break;
      case 4:
        String str5 = localCursor.getString(1);
        String str6 = localCursor.getString(2);
        SearchListAdapterListener localSearchListAdapterListener = this.mListener;
        String str7 = localCursor.getString(3);
        String str8 = localCursor.getString(4);
        boolean bool1 = this.mIncludePhoneNumberContacts;
        String str9 = null;
        if (bool1)
        {
          String str10 = localCursor.getString(10);
          boolean bool2 = TextUtils.isEmpty(str10);
          str9 = null;
          if (!bool2)
          {
            StringBuilder localStringBuilder = new StringBuilder("p:");
            str9 = str10.trim();
          }
        }
        if (str9 == null)
          str9 = localCursor.getString(8);
        if (TextUtils.isEmpty(str9))
        {
          str9 = localCursor.getString(9);
          if (TextUtils.isEmpty(str9))
            str9 = null;
        }
        PersonData localPersonData3 = new PersonData(str7, str8, str9);
        localSearchListAdapterListener.onPersonSelected(str5, str6, localPersonData3);
        break;
      case 2:
        if (!this.mAddToCirclesActionEnabled)
          if (this.mShowPersonNameDialog)
          {
            showPersonNameDialog("add_email_dialog");
          }
          else
          {
            String str3 = getWellFormedEmailAddress();
            if (!TextUtils.isEmpty(str3))
            {
              String str4 = "e:" + str3;
              PersonData localPersonData2 = new PersonData(null, null, str3);
              this.mListener.onPersonSelected(str4, null, localPersonData2);
            }
          }
        break;
      case 3:
        if (!this.mAddToCirclesActionEnabled)
          if (this.mShowPersonNameDialog)
          {
            showPersonNameDialog("add_sms_dialog");
          }
          else
          {
            String str1 = getWellFormedSmsAddress();
            if (!TextUtils.isEmpty(str1))
            {
              String str2 = "p:" + str1;
              PersonData localPersonData1 = new PersonData(null, null, str2);
              this.mListener.onPersonSelected(str2, null, localPersonData1);
            }
          }
        break;
      }
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putString("search_list_adapter.query", this.mQuery);
    if (this.mResults.isParcelable())
      paramBundle.putParcelable("search_list_adapter.results", this.mResults);
  }

  public final void onStart()
  {
    this.mCircleNameResolver.initLoader();
    this.mLoaderManager.initLoader(this.mGaiaIdLoaderId, null, this);
    Bundle localBundle = new Bundle();
    localBundle.putString("query", this.mQuery);
    if (this.mCircleUsageType != -1)
      this.mLoaderManager.initLoader(this.mCirclesLoaderId, localBundle, this);
    this.mLoaderManager.initLoader(this.mPeopleLoaderId, localBundle, this);
    if (!this.mFilterNullGaiaIds)
      this.mLoaderManager.initLoader(this.mContactsLoaderId, localBundle, this);
    if (this.mPublicProfileSearchEnabled)
      this.mLoaderManager.initLoader(this.mProfilesLoaderId, localBundle, this);
    updatePublicProfileSearchStatus();
    AddEmailDialogListener localAddEmailDialogListener = (AddEmailDialogListener)this.mFragmentManager.findFragmentByTag("add_person_dialog_listener");
    if (localAddEmailDialogListener != null)
      localAddEmailDialogListener.setAdapter(this);
  }

  public final void onStop()
  {
    this.mHandler.removeMessages(0);
  }

  public final void setAddToCirclesActionEnabled(boolean paramBoolean)
  {
    this.mAddToCirclesActionEnabled = paramBoolean;
  }

  public final void setCircleUsageType(int paramInt)
  {
    this.mCircleUsageType = paramInt;
  }

  public final void setFilterNullGaiaIds(boolean paramBoolean)
  {
    this.mFilterNullGaiaIds = paramBoolean;
  }

  public final void setIncludePeopleInCircles(boolean paramBoolean)
  {
    this.mIncludePeopleInCircles = paramBoolean;
    this.mResults.setIncludePeopleInCircles(this.mIncludePeopleInCircles);
  }

  public final void setIncludePhoneNumberContacts(boolean paramBoolean)
  {
    this.mIncludePhoneNumberContacts = paramBoolean;
  }

  public final void setIncludePlusPages(boolean paramBoolean)
  {
    this.mIncludePlusPages = paramBoolean;
  }

  public final void setListener(SearchListAdapterListener paramSearchListAdapterListener)
  {
    this.mListener = paramSearchListAdapterListener;
  }

  public final void setMention(String paramString)
  {
    this.mActivityId = paramString;
    this.mIsMentionsAdapter = true;
  }

  public final void setPublicProfileSearchEnabled(boolean paramBoolean)
  {
    this.mPublicProfileSearchEnabled = paramBoolean;
  }

  public final void setQueryString(String paramString)
  {
    if (TextUtils.equals(this.mQuery, paramString))
      releaseLatch();
    while (true)
    {
      return;
      this.mResults.setQueryString(paramString);
      this.mHandler.removeMessages(0);
      this.mHandler.removeMessages(1);
      this.mQuery = paramString;
      this.mActiveLoaderCount = 0;
      if (TextUtils.isEmpty(paramString))
      {
        this.mLoaderManager.destroyLoader(this.mCirclesLoaderId);
        this.mLoaderManager.destroyLoader(this.mPeopleLoaderId);
        this.mLoaderManager.destroyLoader(this.mContactsLoaderId);
        this.mLoaderManager.destroyLoader(this.mProfilesLoaderId);
        clearPartitions();
        releaseLatch();
        if (this.mListener != null)
          this.mListener.onSearchListAdapterStateChange(this);
      }
      else
      {
        Bundle localBundle = new Bundle();
        localBundle.putString("query", this.mQuery);
        if (this.mCircleUsageType != -1)
        {
          this.mActiveLoaderCount = (1 + this.mActiveLoaderCount);
          this.mLoaderManager.restartLoader(this.mCirclesLoaderId, localBundle, this);
        }
        this.mActiveLoaderCount = (1 + this.mActiveLoaderCount);
        this.mLoaderManager.restartLoader(this.mPeopleLoaderId, localBundle, this);
        if (!this.mFilterNullGaiaIds)
        {
          this.mActiveLoaderCount = (1 + this.mActiveLoaderCount);
          this.mLoaderManager.restartLoader(this.mContactsLoaderId, localBundle, this);
        }
        if (this.mPublicProfileSearchEnabled)
        {
          this.mPublicProfilesError = false;
          this.mPublicProfilesNotFound = false;
          this.mPublicProfilesLoading = false;
          this.mHandler.sendEmptyMessageDelayed(1, 300L);
          this.mLoaderManager.destroyLoader(this.mProfilesLoaderId);
          this.mLoaderManager.initLoader(this.mProfilesLoaderId, localBundle, this);
          updatePublicProfileSearchStatus();
        }
      }
    }
  }

  public final void setShowPersonNameDialog(boolean paramBoolean)
  {
    this.mShowPersonNameDialog = false;
  }

  public final void setShowProgressWhenEmpty(boolean paramBoolean)
  {
    this.mShowProgressWhenEmpty = false;
  }

  protected final void showEmptyPeopleSearchResults()
  {
    this.mHandler.removeMessages(0);
    Cursor localCursor = this.mResults.getCursor();
    if (localCursor.getCount() == 0)
      changeCursor(4, localCursor);
  }

  protected final void showPersonNameDialog(String paramString)
  {
    AddEmailDialogListener localAddEmailDialogListener = (AddEmailDialogListener)this.mFragmentManager.findFragmentByTag("add_person_dialog_listener");
    if (localAddEmailDialogListener == null)
    {
      localAddEmailDialogListener = new AddEmailDialogListener();
      this.mFragmentManager.beginTransaction().add(localAddEmailDialogListener, "add_person_dialog_listener").commit();
    }
    localAddEmailDialogListener.setAdapter(this);
    Context localContext = getContext();
    EditFragmentDialog localEditFragmentDialog = EditFragmentDialog.newInstance$405ed676(localContext.getString(R.string.add_email_dialog_title), null, localContext.getString(R.string.add_email_dialog_hint), localContext.getString(17039370), localContext.getString(17039360), false);
    localEditFragmentDialog.setTargetFragment(localAddEmailDialogListener, 0);
    localEditFragmentDialog.show(this.mFragmentManager, paramString);
  }

  public static class AddEmailDialogListener extends Fragment
    implements AlertFragmentDialog.AlertDialogListener
  {
    private PeopleSearchAdapter mAdapter;

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
      if (this.mAdapter != null)
        this.mAdapter.onDialogPositiveClick(paramBundle, paramString);
    }

    public final void setAdapter(PeopleSearchAdapter paramPeopleSearchAdapter)
    {
      this.mAdapter = paramPeopleSearchAdapter;
    }
  }

  public static abstract interface SearchListAdapterListener
  {
    public abstract void onAddPersonToCirclesAction(String paramString1, String paramString2, boolean paramBoolean);

    public abstract void onChangeCirclesAction(String paramString1, String paramString2);

    public abstract void onCircleSelected(String paramString, CircleData paramCircleData);

    public abstract void onDismissSuggestionAction(String paramString1, String paramString2);

    public abstract void onPersonSelected(String paramString1, String paramString2, PersonData paramPersonData);

    public abstract void onSearchListAdapterStateChange(PeopleSearchAdapter paramPeopleSearchAdapter);

    public abstract void onUnblockPersonAction(String paramString, boolean paramBoolean);
  }

  public static class SearchResultsFragment extends Fragment
  {
    private PeopleSearchResults mResults;

    public SearchResultsFragment()
    {
      setRetainInstance(true);
    }

    public final PeopleSearchResults getPeopleSearchResults()
    {
      return this.mResults;
    }

    public final void setPeopleSearchResults(PeopleSearchResults paramPeopleSearchResults)
    {
      this.mResults = paramPeopleSearchResults;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.PeopleSearchAdapter
 * JD-Core Version:    0.6.2
 */