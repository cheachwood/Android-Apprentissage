package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Checkable;
import android.widget.ListView;
import android.widget.SectionIndexer;
import com.android.common.widget.EsCompositeCursorAdapter;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.CircleData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.service.ImageCache;
import com.google.android.apps.plus.util.AccountsUtil;
import com.google.android.apps.plus.util.StringUtils;
import com.google.android.apps.plus.views.AudienceView;
import com.google.android.apps.plus.views.CheckableListItemView;
import com.google.android.apps.plus.views.CheckableListItemView.OnItemCheckedChangeListener;
import com.google.android.apps.plus.views.CircleListItemView;
import com.google.android.apps.plus.views.PeopleListItemView;
import com.google.android.apps.plus.views.SectionHeaderView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class EditAudienceFragment extends EsFragment
  implements LoaderManager.LoaderCallbacks<Cursor>, AbsListView.OnScrollListener, AdapterView.OnItemClickListener, CheckableListItemView.OnItemCheckedChangeListener
{
  private EditAudienceAdapter mAdapter;
  private Runnable mAudienceChangedCallback = new Runnable()
  {
    public final void run()
    {
      AudienceData localAudienceData1 = EditAudienceFragment.this.getAudience();
      AudienceData localAudienceData2 = EditAudienceFragment.this.getAudienceFromList();
      int i = localAudienceData1.getCircleCount();
      int j = localAudienceData2.getCircleCount();
      int k = localAudienceData1.getUserCount();
      int m = localAudienceData2.getUserCount();
      int n = 0;
      if (i != j);
      while (true)
      {
        if (n == 0)
        {
          EditAudienceFragment.this.setAudience(EditAudienceFragment.this.getAudience());
          EditAudienceFragment.this.mAdapter.notifyDataSetChanged();
        }
        return;
        n = 0;
        if (k == m)
        {
          ArrayList localArrayList1 = new ArrayList();
          CircleData[] arrayOfCircleData1 = localAudienceData2.getCircles();
          int i1 = arrayOfCircleData1.length;
          for (int i2 = 0; i2 < i1; i2++)
            localArrayList1.add(arrayOfCircleData1[i2].getId());
          CircleData[] arrayOfCircleData2 = localAudienceData1.getCircles();
          int i3 = arrayOfCircleData2.length;
          for (int i4 = 0; ; i4++)
          {
            if (i4 >= i3)
              break label188;
            boolean bool2 = localArrayList1.contains(arrayOfCircleData2[i4].getId());
            n = 0;
            if (!bool2)
              break;
          }
          label188: ArrayList localArrayList2 = new ArrayList();
          PersonData[] arrayOfPersonData1 = localAudienceData2.getUsers();
          int i5 = arrayOfPersonData1.length;
          for (int i6 = 0; i6 < i5; i6++)
            localArrayList2.add(arrayOfPersonData1[i6].getObfuscatedId());
          PersonData[] arrayOfPersonData2 = localAudienceData1.getUsers();
          int i7 = arrayOfPersonData2.length;
          for (int i8 = 0; ; i8++)
          {
            if (i8 >= i7)
              break label288;
            boolean bool1 = localArrayList2.contains(arrayOfPersonData2[i8].getObfuscatedId());
            n = 0;
            if (!bool1)
              break;
          }
          label288: n = 1;
        }
      }
    }
  };
  private boolean mAudienceSet;
  private AudienceView mAudienceView;
  private ImageCache mAvatarCache;
  private final DataSetObserver mCircleContentObserver = new DataSetObserver()
  {
    public final void onChanged()
    {
      EditAudienceFragment.this.updateView(EditAudienceFragment.this.getView());
    }
  };
  private CircleNameResolver mCircleNameResolver;
  private boolean mCircleSelectionEnabled;
  private int mCircleUsageType;
  private boolean mFilterNullGaiaIds;
  private boolean mIncludePlusPages;
  private boolean mIncomingAudienceIsReadOnly;
  private ListView mListView;
  private OnAudienceChangeListener mListener;
  private boolean mLoaderError;
  private boolean mLoadersInitialized;
  private final HashMap<String, CircleData> mSelectedCircles = new HashMap();
  private final HashMap<String, PersonData> mSelectedPeople = new HashMap();

  private void addToSelectedCircles(CircleListItemView paramCircleListItemView)
  {
    String str = paramCircleListItemView.getCircleId();
    CircleData localCircleData = new CircleData(str, paramCircleListItemView.getCircleType(), paramCircleListItemView.getCircleName(), paramCircleListItemView.getMemberCount());
    this.mSelectedCircles.put(str, localCircleData);
  }

  private EsAccount getAccount()
  {
    return (EsAccount)getActivity().getIntent().getExtras().get("account");
  }

  private AudienceData getAudienceFromList()
  {
    ArrayList localArrayList1 = new ArrayList(this.mSelectedCircles.size());
    Iterator localIterator1 = this.mSelectedCircles.values().iterator();
    while (localIterator1.hasNext())
      localArrayList1.add((CircleData)localIterator1.next());
    ArrayList localArrayList2 = new ArrayList(this.mSelectedPeople.size());
    Iterator localIterator2 = this.mSelectedPeople.values().iterator();
    while (localIterator2.hasNext())
      localArrayList2.add((PersonData)localIterator2.next());
    return new AudienceData(localArrayList2, localArrayList1);
  }

  private boolean isLoading()
  {
    boolean bool1;
    if ((this.mAdapter != null) && ((!this.mCircleSelectionEnabled) || (this.mAdapter.getCursor(1) != null)) && (this.mAdapter.getCursor(2) != null) && (this.mAdapter.getCursor(0) != null))
    {
      boolean bool2 = this.mCircleNameResolver.isLoaded();
      bool1 = false;
      if (bool2);
    }
    else
    {
      bool1 = true;
    }
    return bool1;
  }

  private void updateSelectionCount()
  {
    String str1;
    if (this.mListener != null)
    {
      OnAudienceChangeListener localOnAudienceChangeListener = this.mListener;
      if (isLoading())
      {
        str1 = "";
        localOnAudienceChangeListener.onAudienceChanged(str1);
      }
    }
    else
    {
      if (this.mAudienceView != null)
        this.mAudienceView.replaceAudience(getAudienceFromList());
      return;
    }
    Cursor localCursor;
    int i;
    int j;
    int k;
    if (this.mCircleSelectionEnabled)
    {
      localCursor = this.mAdapter.getCursor(1);
      if ((localCursor != null) && (!localCursor.isClosed()) && (localCursor.moveToFirst()))
      {
        i = 0;
        j = 0;
        k = 0;
        label167: 
        do
        {
          String str2 = localCursor.getString(2);
          if (this.mSelectedCircles.containsKey(str2))
            switch (localCursor.getInt(3))
            {
            case 6:
            default:
              k++;
            case 8:
            case 9:
            case 7:
            case 5:
            }
        }
        while (localCursor.moveToNext());
      }
    }
    while (true)
    {
      if (k + this.mSelectedPeople.size() == 0)
      {
        if (j != 0)
        {
          str1 = getString(R.string.audience_display_extended);
          break;
          str1 = localCursor.getString(1);
          break;
          j = 1;
          break label167;
          i = 1;
          break label167;
        }
        if (i != 0)
        {
          str1 = getString(R.string.audience_display_circles);
          break;
        }
        str1 = getString(R.string.audience_selection_count_zero);
        break;
      }
      int m = this.mSelectedPeople.size() + this.mSelectedCircles.size();
      Resources localResources = getResources();
      int n = R.plurals.audience_display_selection_count;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(m);
      str1 = localResources.getQuantityString(n, m, arrayOfObject);
      break;
      i = 0;
      j = 0;
      k = 0;
    }
  }

  public final void addSelectedCircle(String paramString, CircleData paramCircleData)
  {
    this.mSelectedCircles.put(paramString, paramCircleData);
    updateSelectionCount();
  }

  public final void addSelectedPerson(String paramString, PersonData paramPersonData)
  {
    this.mSelectedPeople.put(paramString, paramPersonData);
    if (this.mLoadersInitialized)
      getLoaderManager().restartLoader(0, null, this);
    updateSelectionCount();
  }

  public final AudienceData getAudience()
  {
    return this.mAudienceView.getAudience();
  }

  public final boolean hasAudience()
  {
    return this.mAudienceSet;
  }

  protected final boolean isEmpty()
  {
    boolean bool1;
    if (!isLoading())
    {
      boolean bool2 = this.mAdapter.isPartitionEmpty(1);
      bool1 = false;
      if (bool2)
      {
        boolean bool3 = this.mAdapter.isPartitionEmpty(2);
        bool1 = false;
        if (bool3)
        {
          boolean bool4 = this.mAdapter.isPartitionEmpty(0);
          bool1 = false;
          if (!bool4);
        }
      }
    }
    else
    {
      bool1 = true;
    }
    return bool1;
  }

  public final boolean isSelectionValid()
  {
    if ((!this.mSelectedPeople.isEmpty()) || (!this.mSelectedCircles.isEmpty()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.mAdapter = new EditAudienceAdapter(paramActivity);
    this.mAdapter.addPartition(false, true);
    this.mAdapter.addPartition(false, true);
    this.mAdapter.addPartition(false, false);
    this.mAvatarCache = ImageCache.getInstance(paramActivity);
    this.mCircleNameResolver = new CircleNameResolver(paramActivity, getLoaderManager(), getAccount());
    this.mCircleNameResolver.registerObserver(this.mCircleContentObserver);
  }

  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
      setAudience((AudienceData)paramBundle.getParcelable("audience"));
    getLoaderManager().initLoader(0, null, this);
    if (this.mCircleSelectionEnabled)
      getLoaderManager().initLoader(1, null, this);
    getLoaderManager().initLoader(2, null, this);
    this.mCircleNameResolver.initLoader();
    this.mLoadersInitialized = true;
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    Object localObject;
    switch (paramInt)
    {
    default:
      localObject = null;
    case 1:
    case 2:
    case 0:
    }
    while (true)
    {
      return localObject;
      localObject = new CircleListLoader(getActivity(), getAccount(), this.mCircleUsageType, new String[] { "_id", "circle_name", "circle_id", "type", "contact_count" });
      continue;
      localObject = new PeopleListLoader(getActivity(), getAccount(), new String[] { "_id", "name", "person_id", "gaia_id", "packed_circle_ids" }, null, this.mIncludePlusPages, this.mFilterNullGaiaIds);
      continue;
      localObject = new PeopleNotInCirclesLoader(getActivity(), getAccount(), new String[] { "_id", "name", "person_id", "gaia_id" }, this.mSelectedPeople, this.mFilterNullGaiaIds);
    }
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.edit_audience_fragment, paramViewGroup, false);
    this.mAudienceView = new AudienceView(getActivity(), null, 0, true);
    this.mAudienceView.setAccount(getAccount());
    this.mAudienceView.setAudienceChangedCallback(this.mAudienceChangedCallback);
    this.mAudienceView.findViewById(R.id.audience_to_text).setVisibility(8);
    this.mAudienceView.findViewById(R.id.edit_audience).setVisibility(8);
    this.mListView = ((ListView)localView.findViewById(16908298));
    this.mListView.addHeaderView(this.mAudienceView);
    this.mListView.setAdapter(this.mAdapter);
    this.mListView.setOnItemClickListener(this);
    this.mListView.setOnScrollListener(this);
    return localView;
  }

  public final void onItemCheckedChanged(final CheckableListItemView paramCheckableListItemView, boolean paramBoolean)
  {
    final CircleListItemView localCircleListItemView;
    String str5;
    if ((paramCheckableListItemView instanceof CircleListItemView))
    {
      localCircleListItemView = (CircleListItemView)paramCheckableListItemView;
      str5 = localCircleListItemView.getCircleId();
      if (paramBoolean)
      {
        final FragmentActivity localFragmentActivity = getActivity();
        final EsAccount localEsAccount = getAccount();
        if ((AccountsUtil.isRestrictedCircleForAccount(localEsAccount, localCircleListItemView.getCircleType())) && (!EsAccountsData.hasSeenMinorPublicExtendedDialog(localFragmentActivity, localEsAccount)))
        {
          AlertDialog.Builder localBuilder = new AlertDialog.Builder(getActivity());
          localBuilder.setTitle(localCircleListItemView.getCircleName());
          localBuilder.setMessage(R.string.dialog_public_or_extended_circle_for_minor);
          localBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
          {
            public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
              EditAudienceFragment.this.addToSelectedCircles(localCircleListItemView);
              EsAccountsData.saveMinorPublicExtendedDialogSeenPreference(localFragmentActivity, localEsAccount, true);
            }
          });
          localBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
          {
            public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
              paramCheckableListItemView.setChecked(false);
            }
          });
          localBuilder.show();
        }
      }
    }
    while (true)
    {
      updateSelectionCount();
      return;
      addToSelectedCircles(localCircleListItemView);
      continue;
      this.mSelectedCircles.remove(str5);
      continue;
      if ((paramCheckableListItemView instanceof PeopleListItemView))
      {
        PeopleListItemView localPeopleListItemView = (PeopleListItemView)paramCheckableListItemView;
        String str1 = localPeopleListItemView.getPersonId();
        if (paramBoolean)
        {
          String str2 = localPeopleListItemView.getGaiaId();
          String str3;
          if (str1.startsWith("e:"))
            str3 = str1.substring(2);
          while (true)
          {
            String str4 = localPeopleListItemView.getContactName();
            this.mSelectedPeople.put(str1, new PersonData(str2, str4, str3));
            break;
            boolean bool = str1.startsWith("p:");
            str3 = null;
            if (bool)
              str3 = str1;
          }
        }
        this.mSelectedPeople.remove(str1);
      }
    }
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if ((paramView instanceof Checkable))
      ((Checkable)paramView).toggle();
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void onResume()
  {
    super.onResume();
    updateView(getView());
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putParcelable("audience", getAudienceFromList());
  }

  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
  {
    if (paramInt == 2)
      this.mAvatarCache.pause();
    while (true)
    {
      return;
      this.mAvatarCache.resume();
    }
  }

  public final void setAudience(AudienceData paramAudienceData)
  {
    this.mAudienceSet = true;
    this.mSelectedPeople.clear();
    this.mSelectedCircles.clear();
    if (paramAudienceData != null)
    {
      for (CircleData localCircleData : paramAudienceData.getCircles())
        this.mSelectedCircles.put(localCircleData.getId(), localCircleData);
      PersonData[] arrayOfPersonData = paramAudienceData.getUsers();
      int k = arrayOfPersonData.length;
      int m = 0;
      if (m < k)
      {
        PersonData localPersonData = arrayOfPersonData[m];
        String str1 = localPersonData.getObfuscatedId();
        Object localObject;
        if (!TextUtils.isEmpty(str1))
          localObject = "g:" + str1;
        while (true)
        {
          if (localObject != null)
            this.mSelectedPeople.put(localObject, localPersonData);
          m++;
          break;
          boolean bool = TextUtils.isEmpty(localPersonData.getEmail());
          localObject = null;
          if (!bool)
          {
            String str2 = localPersonData.getEmail();
            if (str2.startsWith("p:"))
              localObject = str2;
            else
              localObject = "e:" + str2;
          }
        }
      }
    }
    updateSelectionCount();
  }

  public final void setCircleSelectionEnabled(boolean paramBoolean)
  {
    this.mCircleSelectionEnabled = true;
  }

  public final void setCircleUsageType(int paramInt)
  {
    this.mCircleUsageType = paramInt;
  }

  public final void setFilterNullGaiaIds(boolean paramBoolean)
  {
    this.mFilterNullGaiaIds = paramBoolean;
  }

  public final void setIncludePlusPages(boolean paramBoolean)
  {
    this.mIncludePlusPages = paramBoolean;
  }

  public final void setIncomingAudienceIsReadOnly(boolean paramBoolean)
  {
    this.mIncomingAudienceIsReadOnly = paramBoolean;
  }

  public final void setOnSelectionChangeListener(OnAudienceChangeListener paramOnAudienceChangeListener)
  {
    this.mListener = paramOnAudienceChangeListener;
  }

  protected final void updateView(View paramView)
  {
    View localView1 = paramView.findViewById(16908298);
    View localView2 = paramView.findViewById(R.id.server_error);
    if (this.mLoaderError)
    {
      localView1.setVisibility(8);
      localView2.setVisibility(0);
      showContent(paramView);
    }
    while (true)
    {
      updateSelectionCount();
      return;
      if (isLoading())
      {
        localView1.setVisibility(8);
        localView2.setVisibility(8);
        showEmptyViewProgress(paramView);
      }
      else if (isEmpty())
      {
        localView1.setVisibility(8);
        localView2.setVisibility(8);
        setupEmptyView(paramView, R.string.no_people_in_circles);
        showEmptyView(paramView);
      }
      else
      {
        localView1.setVisibility(0);
        localView2.setVisibility(8);
        showContent(paramView);
      }
    }
  }

  private final class EditAudienceAdapter extends EsCompositeCursorAdapter
    implements SectionIndexer
  {
    private EsAlphabetIndexer mIndexer;

    public EditAudienceAdapter(Context arg2)
    {
      super((byte)0);
    }

    protected final void bindView(View paramView, int paramInt1, Cursor paramCursor, int paramInt2)
    {
      switch (paramInt1)
      {
      default:
      case 0:
      case 2:
      case 1:
      }
      while (true)
      {
        return;
        PeopleListItemView localPeopleListItemView2 = (PeopleListItemView)paramView;
        localPeopleListItemView2.setCircleNameResolver(EditAudienceFragment.this.mCircleNameResolver);
        String str4 = paramCursor.getString(2);
        localPeopleListItemView2.setPersonId(str4);
        String str5 = paramCursor.getString(3);
        if (!TextUtils.isEmpty(str5))
          localPeopleListItemView2.setGaiaId(str5);
        localPeopleListItemView2.setContactName(paramCursor.getString(1));
        boolean bool3 = EditAudienceFragment.this.mSelectedPeople.containsKey(str4);
        localPeopleListItemView2.setChecked(bool3);
        localPeopleListItemView2.updateContentDescription();
        if ((!bool3) || (!EditAudienceFragment.this.mIncomingAudienceIsReadOnly));
        for (boolean bool4 = true; ; bool4 = false)
        {
          localPeopleListItemView2.setEnabled(bool4);
          break;
        }
        PeopleListItemView localPeopleListItemView1 = (PeopleListItemView)paramView;
        localPeopleListItemView1.setCircleNameResolver(EditAudienceFragment.this.mCircleNameResolver);
        String str2 = paramCursor.getString(2);
        localPeopleListItemView1.setPersonId(str2);
        localPeopleListItemView1.setGaiaId(paramCursor.getString(3));
        String str3 = paramCursor.getString(1);
        localPeopleListItemView1.setContactName(str3);
        localPeopleListItemView1.setPackedCircleIds(paramCursor.getString(4));
        boolean bool1 = EditAudienceFragment.this.mSelectedPeople.containsKey(str2);
        localPeopleListItemView1.setChecked(bool1);
        boolean bool2;
        label272: char c;
        if ((!bool1) || (!EditAudienceFragment.this.mIncomingAudienceIsReadOnly))
        {
          bool2 = true;
          localPeopleListItemView1.setEnabled(bool2);
          c = StringUtils.firstLetter(str3);
          if (paramCursor.moveToPrevious())
            break label316;
          localPeopleListItemView1.setSectionHeader(c);
        }
        while (true)
        {
          localPeopleListItemView1.updateContentDescription();
          break;
          bool2 = false;
          break label272;
          label316: if (StringUtils.firstLetter(paramCursor.getString(1)) != c)
            localPeopleListItemView1.setSectionHeader(c);
          else
            localPeopleListItemView1.setSectionHeaderVisible(false);
        }
        CircleListItemView localCircleListItemView = (CircleListItemView)paramView;
        String str1 = paramCursor.getString(2);
        int i = paramCursor.getInt(3);
        localCircleListItemView.setCircle(str1, i, paramCursor.getString(1), paramCursor.getInt(4), AccountsUtil.isRestrictedCircleForAccount(EditAudienceFragment.this.getAccount(), i));
        localCircleListItemView.setChecked(EditAudienceFragment.this.mSelectedCircles.containsKey(str1));
        localCircleListItemView.updateContentDescription();
      }
    }

    public final void changeCursor(int paramInt, Cursor paramCursor)
    {
      if ((paramInt == 2) && (paramCursor != null))
        this.mIndexer = new EsAlphabetIndexer(paramCursor, 1);
      super.changeCursor(paramInt, paramCursor);
    }

    protected final int getItemViewType(int paramInt1, int paramInt2)
    {
      return paramInt1;
    }

    public final int getItemViewTypeCount()
    {
      return 3;
    }

    public final int getPositionForSection(int paramInt)
    {
      if ((paramInt == 0) || (this.mIndexer == null));
      for (int i = 0; ; i = getPositionForPartition(2) + this.mIndexer.getPositionForSection(paramInt - 1))
        return i;
    }

    public final int getSectionForPosition(int paramInt)
    {
      EsAlphabetIndexer localEsAlphabetIndexer = this.mIndexer;
      int i = 0;
      if (localEsAlphabetIndexer == null);
      while (true)
      {
        return i;
        int j = getPositionForPartition(2);
        i = 0;
        if (paramInt >= j)
          i = 1 + this.mIndexer.getSectionForPosition(paramInt - j);
      }
    }

    public final Object[] getSections()
    {
      Object[] arrayOfObject2;
      if (this.mIndexer == null)
        arrayOfObject2 = null;
      while (true)
      {
        return arrayOfObject2;
        Object[] arrayOfObject1 = this.mIndexer.getSections();
        arrayOfObject2 = new Object[1 + arrayOfObject1.length];
        arrayOfObject2[0] = "◯";
        System.arraycopy(arrayOfObject1, 0, arrayOfObject2, 1, arrayOfObject1.length);
      }
    }

    protected final View newHeaderView$4ac0fa28(Context paramContext, int paramInt, ViewGroup paramViewGroup)
    {
      SectionHeaderView localSectionHeaderView = (SectionHeaderView)LayoutInflater.from(paramContext).inflate(R.layout.section_header, paramViewGroup, false);
      switch (paramInt)
      {
      default:
      case 0:
      case 1:
      }
      while (true)
      {
        return localSectionHeaderView;
        localSectionHeaderView.setText(R.string.edit_audience_header_added);
        continue;
        localSectionHeaderView.setText(R.string.edit_audience_header_circles);
      }
    }

    protected final View newView$54126883(Context paramContext, int paramInt, Cursor paramCursor, ViewGroup paramViewGroup)
    {
      Object localObject;
      switch (paramInt)
      {
      default:
        localObject = null;
      case 0:
      case 2:
      case 1:
      }
      while (true)
      {
        return localObject;
        localObject = PeopleListItemView.createInstance(paramContext);
        ((PeopleListItemView)localObject).setOnItemCheckedChangeListener(EditAudienceFragment.this);
        ((PeopleListItemView)localObject).setCheckBoxVisible(true);
        ((PeopleListItemView)localObject).setCircleNameResolver(EditAudienceFragment.this.mCircleNameResolver);
        continue;
        localObject = new CircleListItemView(paramContext);
        ((CircleListItemView)localObject).setOnItemCheckedChangeListener(EditAudienceFragment.this);
        ((CircleListItemView)localObject).setCheckBoxVisible(true);
        ((CircleListItemView)localObject).updateContentDescription();
      }
    }
  }

  public static abstract interface OnAudienceChangeListener
  {
    public abstract void onAudienceChanged(String paramString);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.EditAudienceFragment
 * JD-Core Version:    0.6.2
 */