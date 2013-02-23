package com.google.android.apps.plus.phone;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.fragments.CircleNameResolver;
import com.google.android.apps.plus.fragments.EsAlphabetIndexer;
import com.google.android.apps.plus.util.StringUtils;
import com.google.android.apps.plus.views.PeopleListItemView;
import com.google.android.apps.plus.views.PeopleListItemView.OnActionButtonClickListener;

public final class PeopleCursorAdapter extends CursorAdapter
  implements SectionIndexer
{
  private boolean mAlwaysHideLetterSections = false;
  private int mAvatarUrlColumnIndex = 5;
  protected CircleNameResolver mCircleNameResolver;
  private final int mGaiaIdColumnIndex = 2;
  private EsAlphabetIndexer mIndexer;
  private final int mNameColumnIndex = 3;
  private PeopleListItemView.OnActionButtonClickListener mOnActionButtonClickListener;
  private final int mPackedCircleIdsColumnIndex = 4;
  private final int mPersonIdColumnIndex = 1;
  private boolean mShowAddButtonIfNeeded = false;

  public PeopleCursorAdapter(Context paramContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, CircleNameResolver paramCircleNameResolver)
  {
    super(paramContext, null, 0);
    this.mCircleNameResolver = paramCircleNameResolver;
  }

  public final void bindView(View paramView, Context paramContext, Cursor paramCursor)
  {
    PeopleListItemView localPeopleListItemView = (PeopleListItemView)paramView;
    localPeopleListItemView.setCircleNameResolver(this.mCircleNameResolver);
    localPeopleListItemView.setPersonId(paramCursor.getString(this.mPersonIdColumnIndex));
    localPeopleListItemView.setGaiaIdAndAvatarUrl(paramCursor.getString(this.mGaiaIdColumnIndex), EsAvatarData.uncompressAvatarUrl(paramCursor.getString(this.mAvatarUrlColumnIndex)));
    String str1 = paramCursor.getString(this.mNameColumnIndex);
    localPeopleListItemView.setContactName(str1);
    String str2 = paramCursor.getString(this.mPackedCircleIdsColumnIndex);
    localPeopleListItemView.setPackedCircleIds(str2);
    if (this.mShowAddButtonIfNeeded)
    {
      localPeopleListItemView.setAddButtonVisible(TextUtils.isEmpty(str2));
      if (this.mOnActionButtonClickListener != null)
        localPeopleListItemView.setOnActionButtonClickListener(this.mOnActionButtonClickListener);
    }
    int i;
    char c;
    if ((!this.mAlwaysHideLetterSections) && (paramCursor != null) && (paramCursor.getCount() > 20))
    {
      i = 1;
      if (i == 0)
        break label238;
      c = StringUtils.firstLetter(str1);
      if (paramCursor.moveToPrevious())
        break label201;
      localPeopleListItemView.setSectionHeader(c);
      label182: paramCursor.moveToNext();
    }
    while (true)
    {
      localPeopleListItemView.updateContentDescription();
      return;
      i = 0;
      break;
      label201: if (StringUtils.firstLetter(paramCursor.getString(this.mNameColumnIndex)) != c)
      {
        localPeopleListItemView.setSectionHeader(c);
        break label182;
      }
      localPeopleListItemView.setSectionHeaderVisible(false);
      break label182;
      label238: localPeopleListItemView.setSectionHeaderVisible(false);
    }
  }

  public final CharSequence convertToString(Cursor paramCursor)
  {
    return paramCursor.getString(this.mNameColumnIndex);
  }

  public final int getPositionForSection(int paramInt)
  {
    return this.mIndexer.getPositionForSection(paramInt);
  }

  public final int getSectionForPosition(int paramInt)
  {
    return this.mIndexer.getSectionForPosition(paramInt);
  }

  public final Object[] getSections()
  {
    if (this.mIndexer != null);
    for (Object[] arrayOfObject = this.mIndexer.getSections(); ; arrayOfObject = null)
      return arrayOfObject;
  }

  public final View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    return PeopleListItemView.createInstance(paramContext);
  }

  public final void setAlwaysHideLetterSections(boolean paramBoolean)
  {
    this.mAlwaysHideLetterSections = true;
  }

  public final void setOnActionButtonClickListener(PeopleListItemView.OnActionButtonClickListener paramOnActionButtonClickListener)
  {
    this.mOnActionButtonClickListener = paramOnActionButtonClickListener;
  }

  public final void setShowAddButtonIfNeeded(boolean paramBoolean)
  {
    this.mShowAddButtonIfNeeded = true;
  }

  public final Cursor swapCursor(Cursor paramCursor)
  {
    if (paramCursor != null)
      this.mIndexer = new EsAlphabetIndexer(paramCursor, this.mNameColumnIndex);
    return super.swapCursor(paramCursor);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.PeopleCursorAdapter
 * JD-Core Version:    0.6.2
 */