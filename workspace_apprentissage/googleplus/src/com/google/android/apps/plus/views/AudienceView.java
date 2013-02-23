package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.CircleData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.content.SquareTargetData;
import com.google.android.apps.plus.fragments.CircleListLoader;
import com.google.android.apps.plus.util.AccountsUtil;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.PeopleUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AudienceView extends FrameLayout
  implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener
{
  protected EsAccount mAccount;
  protected Runnable mAudienceChangedCallback;
  protected final boolean mCanRemoveChips;
  protected ViewGroup mChipContainer;
  protected final ArrayList<AudienceData> mChips = new ArrayList();
  protected boolean mEdited;

  static
  {
    if (!AudienceView.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public AudienceView(Context paramContext)
  {
    this(paramContext, null);
  }

  public AudienceView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public AudienceView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, false);
  }

  public AudienceView(Context paramContext, AttributeSet paramAttributeSet, int paramInt, boolean paramBoolean)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
    this.mCanRemoveChips = paramBoolean;
  }

  private int getCloseIcon()
  {
    if (this.mCanRemoveChips);
    for (int i = R.drawable.ic_acl_x; ; i = 0)
      return i;
  }

  private void setAudience(AudienceData paramAudienceData)
  {
    ArrayList localArrayList1 = new ArrayList(this.mChips);
    AudienceData localAudienceData1 = getAudience();
    CircleData[] arrayOfCircleData1 = localAudienceData1.getCircles();
    PersonData[] arrayOfPersonData1 = localAudienceData1.getUsers();
    SquareTargetData[] arrayOfSquareTargetData1 = localAudienceData1.getSquareTargets();
    this.mChips.clear();
    if (paramAudienceData != null)
    {
      int i = 0;
      int j = localArrayList1.size();
      while (i < j)
      {
        AudienceData localAudienceData5 = (AudienceData)localArrayList1.get(i);
        if (PeopleUtils.in(paramAudienceData, localAudienceData5))
          this.mChips.add(localAudienceData5);
        i++;
      }
      for (CircleData localCircleData : paramAudienceData.getCircles())
        if (!PeopleUtils.in(arrayOfCircleData1, localCircleData))
        {
          ArrayList localArrayList4 = this.mChips;
          AudienceData localAudienceData4 = new AudienceData(localCircleData);
          localArrayList4.add(localAudienceData4);
          if ((localCircleData == null) && (EsLog.isLoggable("AudienceView", 4)))
            Log.i("AudienceView", "Added a null circle: " + Thread.currentThread().getStackTrace());
        }
      for (PersonData localPersonData : paramAudienceData.getUsers())
        if (!PeopleUtils.in(arrayOfPersonData1, localPersonData))
        {
          ArrayList localArrayList3 = this.mChips;
          AudienceData localAudienceData3 = new AudienceData(localPersonData);
          localArrayList3.add(localAudienceData3);
        }
      for (SquareTargetData localSquareTargetData : paramAudienceData.getSquareTargets())
        if (!PeopleUtils.in(arrayOfSquareTargetData1, localSquareTargetData))
        {
          ArrayList localArrayList2 = this.mChips;
          AudienceData localAudienceData2 = new AudienceData(localSquareTargetData);
          localArrayList2.add(localAudienceData2);
        }
    }
    update();
  }

  protected void addChip(int paramInt)
  {
    View localView = inflate(R.layout.people_audience_view_chip);
    if (this.mCanRemoveChips)
      localView.setOnClickListener(this);
    this.mChipContainer.addView(localView, paramInt);
  }

  public final void addCircle(CircleData paramCircleData)
  {
    this.mEdited = true;
    if (PeopleUtils.in(getAudience().getCircles(), paramCircleData));
    while (true)
    {
      return;
      Context localContext = getContext();
      OzViews localOzViews = OzViews.getViewForLogging(localContext);
      EsAnalytics.recordActionEvent(localContext, this.mAccount, OzActions.PLATFORM_AUDIENCE_VIEW_CIRCLE_ADDED, localOzViews);
      this.mChips.add(new AudienceData(paramCircleData));
      if ((paramCircleData == null) && (EsLog.isLoggable("AudienceView", 4)))
        Log.i("AudienceView", "Added a null circle: " + Thread.currentThread().getStackTrace());
      update();
    }
  }

  public final void addPerson(PersonData paramPersonData)
  {
    this.mEdited = true;
    if (PeopleUtils.in(getAudience().getUsers(), paramPersonData));
    while (true)
    {
      return;
      Context localContext = getContext();
      OzViews localOzViews = OzViews.getViewForLogging(localContext);
      EsAnalytics.recordActionEvent(localContext, this.mAccount, OzActions.PLATFORM_AUDIENCE_VIEW_PERSON_ADDED, localOzViews);
      this.mChips.add(new AudienceData(paramPersonData));
      update();
    }
  }

  public final AudienceData getAudience()
  {
    ArrayList localArrayList1 = this.mChips;
    ArrayList localArrayList2 = new ArrayList();
    ArrayList localArrayList3 = new ArrayList();
    ArrayList localArrayList4 = new ArrayList();
    Iterator localIterator = localArrayList1.iterator();
    while (localIterator.hasNext())
    {
      AudienceData localAudienceData = (AudienceData)localIterator.next();
      PersonData[] arrayOfPersonData = localAudienceData.getUsers();
      int i = arrayOfPersonData.length;
      for (int j = 0; j < i; j++)
        localArrayList2.add(arrayOfPersonData[j]);
      CircleData[] arrayOfCircleData = localAudienceData.getCircles();
      int k = arrayOfCircleData.length;
      for (int m = 0; m < k; m++)
        localArrayList3.add(arrayOfCircleData[m]);
      SquareTargetData[] arrayOfSquareTargetData = localAudienceData.getSquareTargets();
      int n = arrayOfSquareTargetData.length;
      for (int i1 = 0; i1 < n; i1++)
        localArrayList4.add(arrayOfSquareTargetData[i1]);
    }
    return new AudienceData(localArrayList2, localArrayList3, localArrayList4);
  }

  protected int getChipCount()
  {
    return this.mChipContainer.getChildCount();
  }

  protected final View inflate(int paramInt)
  {
    return LayoutInflater.from(getContext()).inflate(paramInt, this, false);
  }

  protected void init()
  {
    addView(inflate(R.layout.audience_view));
    this.mChipContainer = ((ViewGroup)findViewById(R.id.people_audience_view_chip_container));
  }

  public final void initLoaders(LoaderManager paramLoaderManager)
  {
    paramLoaderManager.initLoader(R.id.audience_circle_name_loader_id, null, this);
  }

  public final boolean isEdited()
  {
    return this.mEdited;
  }

  public void onClick(View paramView)
  {
    if (!this.mCanRemoveChips);
    while (true)
    {
      return;
      Context localContext = getContext();
      OzViews localOzViews = OzViews.getViewForLogging(localContext);
      EsAnalytics.recordActionEvent(localContext, this.mAccount, OzActions.PLATFORM_AUDIENCE_VIEW_CLICKED, localOzViews);
      int i = this.mChipContainer.indexOfChild(paramView);
      if (i != -1)
      {
        this.mEdited = true;
        this.mChips.remove(i);
        update();
      }
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    if (paramInt == R.id.audience_circle_name_loader_id)
      return new CircleListLoader(getContext(), this.mAccount, 5, CirclesQuery.PROJECTION);
    throw new AssertionError();
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    this.mChips.clear();
    this.mChips.addAll(localSavedState.audience);
    if (EsLog.isLoggable("AudienceView", 4))
    {
      Iterator localIterator = localSavedState.audience.iterator();
      while (localIterator.hasNext())
      {
        AudienceData localAudienceData = (AudienceData)localIterator.next();
        if (localAudienceData.getCircleCount() > 0)
        {
          CircleData[] arrayOfCircleData = localAudienceData.getCircles();
          int i = arrayOfCircleData.length;
          for (int j = 0; j < i; j++)
            if (arrayOfCircleData[j] == null)
              Log.i("AudienceView", "Added a null circle: " + Thread.currentThread().getStackTrace());
        }
      }
    }
    this.mEdited = localSavedState.edited;
    update();
  }

  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.audience = this.mChips;
    localSavedState.edited = this.mEdited;
    return localSavedState;
  }

  protected void removeLastChip()
  {
    if (this.mChips.isEmpty());
    while (true)
    {
      return;
      this.mEdited = true;
      int i = -1 + this.mChips.size();
      this.mChips.remove(i);
      update();
    }
  }

  public final void removePerson(PersonData paramPersonData)
  {
    this.mEdited = true;
    Iterator localIterator = this.mChips.iterator();
    AudienceData localAudienceData;
    do
    {
      boolean bool = localIterator.hasNext();
      localObject = null;
      if (!bool)
        break;
      localAudienceData = (AudienceData)localIterator.next();
    }
    while ((localAudienceData.getUserCount() != 1) || (localAudienceData.getCircleCount() != 0) || (!EsPeopleData.isSamePerson(localAudienceData.getUser(0), paramPersonData)));
    Object localObject = localAudienceData;
    if (localObject != null)
    {
      this.mChips.remove(localObject);
      update();
    }
  }

  public final void replaceAudience(AudienceData paramAudienceData)
  {
    this.mEdited = true;
    setAudience(paramAudienceData);
  }

  public void setAccount(EsAccount paramEsAccount)
  {
    this.mAccount = paramEsAccount;
  }

  public void setAudienceChangedCallback(Runnable paramRunnable)
  {
    this.mAudienceChangedCallback = paramRunnable;
  }

  public void setDefaultAudience(AudienceData paramAudienceData)
  {
    if (paramAudienceData != null)
      setAudience(paramAudienceData);
  }

  protected void update()
  {
    int i = 0;
    Iterator localIterator = this.mChips.iterator();
    while (localIterator.hasNext())
    {
      AudienceData localAudienceData = (AudienceData)localIterator.next();
      assert ((localAudienceData.getCircleCount() == 1) || (localAudienceData.getUserCount() == 1) || (localAudienceData.getSquareTargetCount() == 1));
      CircleData[] arrayOfCircleData = localAudienceData.getCircles();
      int m = arrayOfCircleData.length;
      int n = 0;
      if (n < m)
      {
        CircleData localCircleData = arrayOfCircleData[n];
        boolean bool = AccountsUtil.isRestrictedCircleForAccount(this.mAccount, localCircleData.getType());
        String str2;
        label132: int i5;
        int i6;
        if (!TextUtils.isEmpty(localCircleData.getName()))
        {
          str2 = localCircleData.getName();
          i5 = i + 1;
          switch (localCircleData.getType())
          {
          default:
            i6 = R.drawable.ic_acl_circles;
          case 9:
          case 7:
          case 8:
          }
        }
        while (true)
        {
          updateChip(i, i6, getCloseIcon(), str2, localCircleData, bool);
          n++;
          i = i5;
          break;
          str2 = getContext().getString(R.string.loading);
          break label132;
          i6 = R.drawable.ic_acl_public;
          continue;
          i6 = R.drawable.ic_acl_extended;
          continue;
          i6 = R.drawable.ic_acl_domain;
        }
      }
      PersonData[] arrayOfPersonData = localAudienceData.getUsers();
      int i1 = arrayOfPersonData.length;
      int i2 = 0;
      int i3 = i;
      if (i2 < i1)
      {
        PersonData localPersonData = arrayOfPersonData[i2];
        String str1;
        if (!TextUtils.isEmpty(localPersonData.getName()))
          str1 = localPersonData.getName();
        while (true)
        {
          int i4 = i3 + 1;
          updateChip(i3, 0, getCloseIcon(), str1, localPersonData, false);
          i2++;
          i3 = i4;
          break;
          if (!TextUtils.isEmpty(localPersonData.getEmail()))
            str1 = localPersonData.getEmail();
          else
            str1 = getResources().getString(17039374);
        }
      }
      i = i3;
    }
    int j = getChipCount();
    while (i < j)
    {
      this.mChipContainer.getChildAt(i).setVisibility(8);
      i++;
    }
    for (int k = -1 + this.mChipContainer.getChildCount(); k >= 0; k--)
    {
      View localView = this.mChipContainer.getChildAt(k);
      if (localView.getVisibility() == 8)
        this.mChipContainer.removeView(localView);
    }
    if (this.mAudienceChangedCallback != null)
      this.mAudienceChangedCallback.run();
  }

  protected void updateChip(int paramInt1, int paramInt2, int paramInt3, String paramString, Object paramObject, boolean paramBoolean)
  {
    if (paramInt1 > -1 + getChipCount())
      addChip(paramInt1);
    TextView localTextView = (TextView)this.mChipContainer.getChildAt(paramInt1);
    localTextView.setCompoundDrawablesWithIntrinsicBounds(paramInt2, 0, paramInt3, 0);
    localTextView.setText(paramString);
    int i;
    if (paramBoolean)
      i = R.drawable.chip_red;
    while (true)
    {
      localTextView.setBackgroundResource(i);
      if (localTextView.getVisibility() != 0)
        localTextView.setVisibility(0);
      localTextView.setTag(paramObject);
      return;
      CircleData localCircleData;
      if ((paramObject instanceof CircleData))
        localCircleData = (CircleData)paramObject;
      while (true)
        if (localCircleData != null)
          switch (localCircleData.getType())
          {
          default:
            i = R.drawable.chip_blue;
            break;
            localCircleData = null;
            break;
          case 7:
          case 8:
          case 9:
            i = R.drawable.chip_green;
            break;
          }
      i = R.drawable.chip_blue;
    }
  }

  protected static abstract interface CirclesQuery
  {
    public static final String[] PROJECTION = { "circle_id", "circle_name" };
  }

  public static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
    };
    public ArrayList<AudienceData> audience;
    public boolean edited;

    private SavedState(Parcel paramParcel)
    {
      super();
      this.audience = paramParcel.createTypedArrayList(AudienceData.CREATOR);
      if (paramParcel.readInt() != 0);
      for (boolean bool = true; ; bool = false)
      {
        this.edited = bool;
        return;
      }
    }

    public SavedState(Parcelable paramParcelable)
    {
      super();
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeTypedList(this.audience);
      if (this.edited);
      for (int i = 1; ; i = 0)
      {
        paramParcel.writeInt(i);
        return;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.AudienceView
 * JD-Core Version:    0.6.2
 */