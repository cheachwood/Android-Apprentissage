package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v4.app.LoaderManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.phone.EsCursorAdapter;
import com.google.android.apps.plus.phone.OobDeviceActivity;
import com.google.android.apps.plus.service.CircleMembershipManager;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.views.PersonCardView;
import com.google.android.apps.plus.views.PersonCardView.OnPersonCardClickListener;
import com.google.api.services.plusi.model.DataCircleMemberProperties;
import com.google.api.services.plusi.model.DataCircleMemberPropertiesJson;
import com.google.api.services.plusi.model.DataSugggestionExplanation;
import com.google.api.services.plusi.model.DataSugggestionExplanationJson;
import java.util.ArrayList;

public final class SuggestionGridAdapter extends EsCursorAdapter
  implements PersonCardView.OnPersonCardClickListener
{
  public static final String[] PROJECTION = { "_id", "person_id", "gaia_id", "name", "avatar", "packed_circle_ids", "profile_type", "category", "category_label", "explanation", "properties", "suggestion_id" };
  private final EsAccount mAccount;
  private ArrayList<SuggestionCategoryAdapter> mCategories = new ArrayList();
  private final DataSetObserver mCircleContentObserver = new DataSetObserver()
  {
    public final void onChanged()
    {
      if ((SuggestionGridAdapter.this.getCursor() != null) && (!SuggestionGridAdapter.this.getCursor().isClosed()))
        SuggestionGridAdapter.this.notifyDataSetChanged();
    }
  };
  private final CircleNameResolver mCircleNameResolver;
  private CircleSpinnerAdapter mCircleSpinnerAdapter;
  private SuggestionGridAdapterListener mListener;
  private final String mSuggestionUi;
  private String mTooltipPersonId;
  private boolean mValid;

  public SuggestionGridAdapter(Context paramContext, LoaderManager paramLoaderManager, EsAccount paramEsAccount, int paramInt)
  {
    super(paramContext, null);
    this.mAccount = paramEsAccount;
    this.mCircleNameResolver = new CircleNameResolver(paramContext, paramLoaderManager, paramEsAccount, paramInt);
    this.mCircleNameResolver.registerObserver(this.mCircleContentObserver);
    this.mCircleSpinnerAdapter = new CircleSpinnerAdapter(this.mContext);
    this.mCircleSpinnerAdapter.setNotifyOnChange(false);
    if ((paramContext instanceof OobDeviceActivity));
    for (String str = "SIGNUP"; ; str = "ANDROID_PEOPLE_SUGGESTIONS_PAGE")
    {
      this.mSuggestionUi = str;
      return;
    }
  }

  private void recordSuggestionAction(PersonCardView paramPersonCardView, String paramString)
  {
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(paramPersonCardView.getPersonId());
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(paramPersonCardView.getSuggestionId());
    EsService.recordSuggestionAction(this.mContext, this.mAccount, this.mSuggestionUi, localArrayList1, localArrayList2, paramString);
  }

  public final void bindView(View paramView, Context paramContext, Cursor paramCursor)
  {
    String str1 = paramCursor.getString(1);
    String str2 = paramCursor.getString(2);
    String str3 = paramCursor.getString(4);
    String str4 = paramCursor.getString(11);
    PersonCardView localPersonCardView = (PersonCardView)paramView;
    localPersonCardView.setCircleNameResolver(this.mCircleNameResolver);
    localPersonCardView.setOnPersonCardClickListener(this);
    localPersonCardView.setContactName(paramCursor.getString(3));
    localPersonCardView.setPersonId(str1);
    localPersonCardView.setGaiaIdAndAvatarUrl(str2, EsAvatarData.uncompressAvatarUrl(str3));
    localPersonCardView.setSuggestionId(str4);
    Resources localResources = paramContext.getResources();
    byte[] arrayOfByte1 = paramCursor.getBlob(9);
    String str5;
    String str7;
    String str8;
    if (arrayOfByte1 != null)
    {
      DataSugggestionExplanation localDataSugggestionExplanation = (DataSugggestionExplanation)DataSugggestionExplanationJson.getInstance().fromByteArray(arrayOfByte1);
      Integer localInteger = localDataSugggestionExplanation.numberOfCommonFriends;
      str5 = null;
      if (localInteger != null)
      {
        int k = localDataSugggestionExplanation.numberOfCommonFriends.intValue();
        str5 = null;
        if (k > 0)
        {
          int m = R.plurals.common_friend_count;
          int n = localDataSugggestionExplanation.numberOfCommonFriends.intValue();
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = localDataSugggestionExplanation.numberOfCommonFriends;
          str5 = localResources.getQuantityString(m, n, arrayOfObject2);
        }
      }
      localPersonCardView.setPackedCircleIdsEmailAndDescription(null, null, str5, false, false);
      str7 = paramCursor.getString(5);
      str8 = paramCursor.getString(7);
      if (!CircleMembershipManager.isCircleMembershipRequestPending(str1))
        break label496;
      localPersonCardView.setShowCircleChangePending(true);
    }
    while (true)
    {
      boolean bool1 = TextUtils.equals(this.mTooltipPersonId, str1);
      boolean bool2 = false;
      if (bool1)
      {
        boolean bool3 = TextUtils.isEmpty(str7);
        bool2 = false;
        if (!bool3)
        {
          bool2 = true;
          this.mTooltipPersonId = null;
        }
      }
      int i = R.string.added_to_circles_tooltip;
      localPersonCardView.setShowTooltip(bool2, i);
      localPersonCardView.setDismissActionButtonVisible(TextUtils.isEmpty(str7));
      return;
      byte[] arrayOfByte2 = paramCursor.getBlob(10);
      str5 = null;
      if (arrayOfByte2 == null)
        break;
      DataCircleMemberProperties localDataCircleMemberProperties = (DataCircleMemberProperties)DataCircleMemberPropertiesJson.getInstance().fromByteArray(arrayOfByte2);
      if (localDataCircleMemberProperties.tagLine != null)
      {
        str5 = localDataCircleMemberProperties.tagLine;
        break;
      }
      if (localDataCircleMemberProperties.company != null)
      {
        if (localDataCircleMemberProperties.occupation != null)
        {
          int j = R.string.people_search_job;
          Object[] arrayOfObject1 = new Object[2];
          arrayOfObject1[0] = localDataCircleMemberProperties.occupation;
          arrayOfObject1[1] = localDataCircleMemberProperties.company;
          str5 = localResources.getString(j, arrayOfObject1);
          break;
        }
        str5 = localDataCircleMemberProperties.company;
        break;
      }
      if (localDataCircleMemberProperties.occupation != null)
      {
        str5 = localDataCircleMemberProperties.occupation;
        break;
      }
      if (localDataCircleMemberProperties.school != null)
      {
        str5 = localDataCircleMemberProperties.school;
        break;
      }
      String str6 = localDataCircleMemberProperties.location;
      str5 = null;
      if (str6 == null)
        break;
      str5 = localDataCircleMemberProperties.location;
      break;
      label496: localPersonCardView.setShowCircleChangePending(false);
      localPersonCardView.setOneClickCircles(str7, this.mCircleSpinnerAdapter, "#".equals(str8));
    }
  }

  public final ArrayList<SuggestionCategoryAdapter> getCategories()
  {
    ArrayList localArrayList;
    if (this.mValid)
      localArrayList = this.mCategories;
    while (true)
    {
      return localArrayList;
      Cursor localCursor = getCursor();
      if (localCursor == null)
      {
        this.mCategories.clear();
        this.mValid = true;
        localArrayList = this.mCategories;
      }
      else
      {
        boolean bool = localCursor.moveToFirst();
        SuggestionCategoryAdapter localSuggestionCategoryAdapter = null;
        int i = 0;
        String str;
        if (bool)
        {
          str = localCursor.getString(7);
          if ((localSuggestionCategoryAdapter == null) || (!TextUtils.equals(str, localSuggestionCategoryAdapter.mCategory)))
          {
            if (localSuggestionCategoryAdapter != null)
              SuggestionCategoryAdapter.access$102(localSuggestionCategoryAdapter, localCursor.getPosition() - localSuggestionCategoryAdapter.mOffset);
            if (i >= this.mCategories.size())
              break label228;
            localSuggestionCategoryAdapter = (SuggestionCategoryAdapter)this.mCategories.get(i);
          }
        }
        while (true)
        {
          SuggestionCategoryAdapter.access$002(localSuggestionCategoryAdapter, str);
          SuggestionCategoryAdapter.access$302(localSuggestionCategoryAdapter, localCursor.getString(8));
          SuggestionCategoryAdapter.access$202(localSuggestionCategoryAdapter, localCursor.getPosition());
          i++;
          if (localCursor.moveToNext())
            break;
          if (localSuggestionCategoryAdapter != null)
            SuggestionCategoryAdapter.access$102(localSuggestionCategoryAdapter, localCursor.getCount() - localSuggestionCategoryAdapter.mOffset);
          while (this.mCategories.size() > i)
            this.mCategories.remove(-1 + this.mCategories.size());
          label228: localSuggestionCategoryAdapter = new SuggestionCategoryAdapter();
          this.mCategories.add(localSuggestionCategoryAdapter);
        }
        this.mValid = true;
        localArrayList = this.mCategories;
      }
    }
  }

  public final View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    PersonCardView localPersonCardView = new PersonCardView(paramContext);
    localPersonCardView.setAutoWidthForHorizontalScrolling();
    return localPersonCardView;
  }

  public final void onActionButtonClick(PersonCardView paramPersonCardView, int paramInt)
  {
  }

  public final void onChangeCircles(PersonCardView paramPersonCardView)
  {
    if (paramPersonCardView.isOneClickAdd())
    {
      this.mListener.onAddPersonToCirclesAction(paramPersonCardView.getPersonId(), paramPersonCardView.getContactName(), paramPersonCardView.isForSharing());
      if (!EsAccountsData.hasOneClickAddTooltipBeenShown(this.mContext, this.mAccount))
      {
        EsAccountsData.setOneClickAddTooltipShown(this.mContext, this.mAccount);
        this.mTooltipPersonId = paramPersonCardView.getPersonId();
      }
      recordSuggestionAction(paramPersonCardView, "ACCEPT");
    }
    while (true)
    {
      return;
      this.mListener.onChangeCirclesAction(paramPersonCardView.getPersonId(), paramPersonCardView.getContactName());
    }
  }

  public final void onDismissButtonClick(PersonCardView paramPersonCardView)
  {
    this.mListener.onDismissSuggestionAction(paramPersonCardView.getPersonId(), paramPersonCardView.getSuggestionId());
  }

  public final void onItemClick(PersonCardView paramPersonCardView)
  {
    this.mListener.onPersonSelected(paramPersonCardView.getPersonId());
    recordSuggestionAction(paramPersonCardView, "CLICK");
  }

  public final void onStart()
  {
    this.mCircleNameResolver.initLoader();
  }

  public final void setCircleSpinnerAdapter(CircleSpinnerAdapter paramCircleSpinnerAdapter)
  {
    this.mCircleSpinnerAdapter = paramCircleSpinnerAdapter;
  }

  public final void setListener(SuggestionGridAdapterListener paramSuggestionGridAdapterListener)
  {
    this.mListener = paramSuggestionGridAdapterListener;
  }

  public final Cursor swapCursor(Cursor paramCursor)
  {
    this.mValid = false;
    return super.swapCursor(paramCursor);
  }

  public final class SuggestionCategoryAdapter extends BaseAdapter
  {
    private String mCategory;
    private String mCategoryLabel;
    private int mCount;
    private int mOffset;

    public SuggestionCategoryAdapter()
    {
    }

    public final String getCategory()
    {
      return this.mCategory;
    }

    public final String getCategoryLabel()
    {
      return this.mCategoryLabel;
    }

    public final int getCount()
    {
      return this.mCount;
    }

    public final Object getItem(int paramInt)
    {
      return SuggestionGridAdapter.this.getItem(paramInt + this.mOffset);
    }

    public final long getItemId(int paramInt)
    {
      return SuggestionGridAdapter.this.getItemId(paramInt + this.mOffset);
    }

    public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      View localView = SuggestionGridAdapter.this.getView(paramInt + this.mOffset, paramView, paramViewGroup);
      PersonCardView localPersonCardView;
      if ((localView instanceof PersonCardView))
      {
        localPersonCardView = (PersonCardView)localView;
        if (paramInt != 0)
          break label49;
      }
      label49: for (boolean bool = true; ; bool = false)
      {
        localPersonCardView.setWideMargin(bool);
        return localView;
      }
    }

    public final String toString()
    {
      return this.mCategoryLabel + ": " + this.mCount;
    }
  }

  public static abstract interface SuggestionGridAdapterListener
  {
    public abstract void onAddPersonToCirclesAction(String paramString1, String paramString2, boolean paramBoolean);

    public abstract void onChangeCirclesAction(String paramString1, String paramString2);

    public abstract void onDismissSuggestionAction(String paramString1, String paramString2);

    public abstract void onPersonSelected(String paramString);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.SuggestionGridAdapter
 * JD-Core Version:    0.6.2
 */