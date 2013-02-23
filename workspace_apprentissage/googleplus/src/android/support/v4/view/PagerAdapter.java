package android.support.v4.view;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

public abstract class PagerAdapter
{
  private DataSetObservable mObservable = new DataSetObservable();

  public void destroyItem(View paramView, int paramInt, Object paramObject)
  {
    throw new UnsupportedOperationException("Required method destroyItem was not overridden");
  }

  public void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject)
  {
    destroyItem(paramViewGroup, paramInt, paramObject);
  }

  public void finishUpdate(ViewGroup paramViewGroup)
  {
    finishUpdate$3c7ec8c3();
  }

  public void finishUpdate$3c7ec8c3()
  {
  }

  public abstract int getCount();

  public int getItemPosition(Object paramObject)
  {
    return -1;
  }

  public Object instantiateItem(View paramView, int paramInt)
  {
    throw new UnsupportedOperationException("Required method instantiateItem was not overridden");
  }

  public Object instantiateItem(ViewGroup paramViewGroup, int paramInt)
  {
    return instantiateItem(paramViewGroup, paramInt);
  }

  public abstract boolean isViewFromObject(View paramView, Object paramObject);

  public final void notifyDataSetChanged()
  {
    this.mObservable.notifyChanged();
  }

  final void registerDataSetObserver(DataSetObserver paramDataSetObserver)
  {
    this.mObservable.registerObserver(paramDataSetObserver);
  }

  public void restoreState(Parcelable paramParcelable, ClassLoader paramClassLoader)
  {
  }

  public Parcelable saveState()
  {
    return null;
  }

  public void setPrimaryItem(ViewGroup paramViewGroup, int paramInt, Object paramObject)
  {
    setPrimaryItem$7e55ba3e(paramObject);
  }

  public void setPrimaryItem$7e55ba3e(Object paramObject)
  {
  }

  final void unregisterDataSetObserver(DataSetObserver paramDataSetObserver)
  {
    this.mObservable.unregisterObserver(paramDataSetObserver);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.view.PagerAdapter
 * JD-Core Version:    0.6.2
 */