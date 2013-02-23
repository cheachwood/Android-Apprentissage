package android.support.v4.os;

import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class ParcelableCompat
{
  public static <T> Parcelable.Creator<T> newCreator(ParcelableCompatCreatorCallbacks<T> paramParcelableCompatCreatorCallbacks)
  {
    if (Build.VERSION.SDK_INT >= 13)
      new ParcelableCompatCreatorHoneycombMR2(paramParcelableCompatCreatorCallbacks);
    return new CompatCreator(paramParcelableCompatCreatorCallbacks);
  }

  static final class CompatCreator<T>
    implements Parcelable.Creator<T>
  {
    final ParcelableCompatCreatorCallbacks<T> mCallbacks;

    public CompatCreator(ParcelableCompatCreatorCallbacks<T> paramParcelableCompatCreatorCallbacks)
    {
      this.mCallbacks = paramParcelableCompatCreatorCallbacks;
    }

    public final T createFromParcel(Parcel paramParcel)
    {
      return this.mCallbacks.createFromParcel(paramParcel, null);
    }

    public final T[] newArray(int paramInt)
    {
      return this.mCallbacks.newArray(paramInt);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.os.ParcelableCompat
 * JD-Core Version:    0.6.2
 */