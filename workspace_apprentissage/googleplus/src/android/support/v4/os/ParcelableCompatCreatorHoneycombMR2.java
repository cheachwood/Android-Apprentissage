package android.support.v4.os;

import android.os.Parcel;
import android.os.Parcelable.ClassLoaderCreator;

final class ParcelableCompatCreatorHoneycombMR2<T>
  implements Parcelable.ClassLoaderCreator<T>
{
  private final ParcelableCompatCreatorCallbacks<T> mCallbacks;

  public ParcelableCompatCreatorHoneycombMR2(ParcelableCompatCreatorCallbacks<T> paramParcelableCompatCreatorCallbacks)
  {
    this.mCallbacks = paramParcelableCompatCreatorCallbacks;
  }

  public final T createFromParcel(Parcel paramParcel)
  {
    return this.mCallbacks.createFromParcel(paramParcel, null);
  }

  public final T createFromParcel(Parcel paramParcel, ClassLoader paramClassLoader)
  {
    return this.mCallbacks.createFromParcel(paramParcel, paramClassLoader);
  }

  public final T[] newArray(int paramInt)
  {
    return this.mCallbacks.newArray(paramInt);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.os.ParcelableCompatCreatorHoneycombMR2
 * JD-Core Version:    0.6.2
 */