package com.google.android.apps.plus.oob;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.api.services.plusi.model.MobileOutOfBoxResponse;
import com.google.api.services.plusi.model.MobileOutOfBoxResponseJson;

public class OutOfBoxResponseParcelable
  implements Parcelable
{
  public static final Parcelable.Creator<OutOfBoxResponseParcelable> CREATOR = new Parcelable.Creator()
  {
  };
  private MobileOutOfBoxResponse mResponse;

  private OutOfBoxResponseParcelable(Parcel paramParcel)
  {
    int i = paramParcel.readInt();
    if (i > 0)
    {
      byte[] arrayOfByte = new byte[i];
      paramParcel.readByteArray(arrayOfByte);
      this.mResponse = ((MobileOutOfBoxResponse)MobileOutOfBoxResponseJson.getInstance().fromByteArray(arrayOfByte));
    }
  }

  public OutOfBoxResponseParcelable(MobileOutOfBoxResponse paramMobileOutOfBoxResponse)
  {
    this.mResponse = paramMobileOutOfBoxResponse;
  }

  public int describeContents()
  {
    return 0;
  }

  public final MobileOutOfBoxResponse getResponse()
  {
    return this.mResponse;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    if (this.mResponse != null)
    {
      byte[] arrayOfByte = MobileOutOfBoxResponseJson.getInstance().toByteArray(this.mResponse);
      paramParcel.writeInt(arrayOfByte.length);
      paramParcel.writeByteArray(arrayOfByte);
    }
    while (true)
    {
      return;
      paramParcel.writeInt(0);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.oob.OutOfBoxResponseParcelable
 * JD-Core Version:    0.6.2
 */