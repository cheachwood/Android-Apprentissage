package com.google.android.apps.plus.phone;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import com.google.android.apps.plus.util.EsLog;
import java.util.HashMap;

public abstract class EsCursorPagerAdapter extends EsFragmentPagerAdapter
{
  Context mContext;
  private Cursor mCursor;
  private boolean mDataValid;
  private SparseIntArray mItemPosition;
  private HashMap<Object, Integer> mObjectRowMap = new HashMap();
  private int mRowIDColumn;

  public EsCursorPagerAdapter(Context paramContext, FragmentManager paramFragmentManager, Cursor paramCursor)
  {
    super(paramFragmentManager);
    boolean bool;
    if (paramCursor != null)
    {
      bool = true;
      this.mCursor = paramCursor;
      this.mDataValid = bool;
      this.mContext = paramContext;
      if (!bool)
        break label67;
    }
    label67: for (int i = paramCursor.getColumnIndexOrThrow("_id"); ; i = -1)
    {
      this.mRowIDColumn = i;
      return;
      bool = false;
      break;
    }
  }

  private boolean moveCursorTo(int paramInt)
  {
    if ((this.mCursor != null) && (!this.mCursor.isClosed()));
    for (boolean bool = this.mCursor.moveToPosition(paramInt); ; bool = false)
      return bool;
  }

  private void setItemPosition()
  {
    if ((!this.mDataValid) || (this.mCursor == null) || (this.mCursor.isClosed()));
    SparseIntArray localSparseIntArray;
    for (this.mItemPosition = null; ; this.mItemPosition = localSparseIntArray)
    {
      return;
      localSparseIntArray = new SparseIntArray(this.mCursor.getCount());
      this.mCursor.moveToPosition(-1);
      while (this.mCursor.moveToNext())
        localSparseIntArray.append(this.mCursor.getInt(this.mRowIDColumn), this.mCursor.getPosition());
    }
  }

  public final void destroyItem(View paramView, int paramInt, Object paramObject)
  {
    this.mObjectRowMap.remove(paramObject);
    super.destroyItem(paramView, paramInt, paramObject);
  }

  public int getCount()
  {
    if ((this.mDataValid) && (this.mCursor != null));
    for (int i = this.mCursor.getCount(); ; i = 0)
      return i;
  }

  public final Cursor getCursor()
  {
    return this.mCursor;
  }

  public Fragment getItem(int paramInt)
  {
    if ((this.mDataValid) && (moveCursorTo(paramInt)));
    for (Fragment localFragment = getItem$2282a066(this.mCursor); ; localFragment = null)
      return localFragment;
  }

  public abstract Fragment getItem$2282a066(Cursor paramCursor);

  public final int getItemPosition(Object paramObject)
  {
    int i = -2;
    Integer localInteger = (Integer)this.mObjectRowMap.get(paramObject);
    if ((localInteger == null) || (this.mItemPosition == null));
    while (true)
    {
      return i;
      i = this.mItemPosition.get(localInteger.intValue(), i);
    }
  }

  public final Object instantiateItem(View paramView, int paramInt)
  {
    if (!this.mDataValid)
      throw new IllegalStateException("this should only be called when the cursor is valid");
    if (moveCursorTo(paramInt));
    for (Integer localInteger = Integer.valueOf(this.mCursor.getInt(this.mRowIDColumn)); ; localInteger = null)
    {
      Object localObject = super.instantiateItem(paramView, paramInt);
      if (localObject != null)
        this.mObjectRowMap.put(localObject, localInteger);
      return localObject;
    }
  }

  public final boolean isDataValid()
  {
    return this.mDataValid;
  }

  protected final String makeFragmentName(int paramInt1, int paramInt2)
  {
    if (moveCursorTo(paramInt2));
    for (String str = "android:espager:" + paramInt1 + ":" + this.mCursor.getInt(this.mRowIDColumn); ; str = super.makeFragmentName(paramInt1, paramInt2))
      return str;
  }

  public final Cursor swapCursor(Cursor paramCursor)
  {
    int i;
    StringBuilder localStringBuilder2;
    if (EsLog.isLoggable("EsCursorPagerAdapter", 2))
    {
      StringBuilder localStringBuilder1 = new StringBuilder("swapCursor old=");
      if (this.mCursor != null)
        break label77;
      i = -1;
      localStringBuilder2 = localStringBuilder1.append(i).append("; new=");
      if (paramCursor != null)
        break label90;
    }
    label77: label90: for (int j = -1; ; j = paramCursor.getCount())
    {
      Log.v("EsCursorPagerAdapter", j);
      if (paramCursor != this.mCursor)
        break label101;
      localCursor = null;
      return localCursor;
      i = this.mCursor.getCount();
      break;
    }
    label101: Cursor localCursor = this.mCursor;
    this.mCursor = paramCursor;
    if (paramCursor != null)
      this.mRowIDColumn = paramCursor.getColumnIndexOrThrow("_id");
    for (this.mDataValid = true; ; this.mDataValid = false)
    {
      setItemPosition();
      notifyDataSetChanged();
      break;
      this.mRowIDColumn = -1;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.EsCursorPagerAdapter
 * JD-Core Version:    0.6.2
 */