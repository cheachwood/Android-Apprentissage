package com.google.android.apps.plus.oob;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.api.services.plusi.model.MobileOutOfBoxRequest;
import com.google.api.services.plusi.model.MobileOutOfBoxRequestJson;

public class OutOfBoxRequestParcelable
  implements Parcelable
{
  public static final Parcelable.Creator<OutOfBoxRequestParcelable> CREATOR = new Parcelable.Creator()
  {
  };
  private MobileOutOfBoxRequest mRequest;

  private OutOfBoxRequestParcelable(Parcel paramParcel)
  {
    int i = paramParcel.readInt();
    if (i > 0)
    {
      byte[] arrayOfByte = new byte[i];
      paramParcel.readByteArray(arrayOfByte);
      this.mRequest = ((MobileOutOfBoxRequest)MobileOutOfBoxRequestJson.getInstance().fromByteArray(arrayOfByte));
    }
  }

  public OutOfBoxRequestParcelable(MobileOutOfBoxRequest paramMobileOutOfBoxRequest)
  {
    this.mRequest = paramMobileOutOfBoxRequest;
  }

  public int describeContents()
  {
    return 0;
  }

  public final MobileOutOfBoxRequest getRequest()
  {
    return this.mRequest;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    if (this.mRequest != null)
    {
      byte[] arrayOfByte = MobileOutOfBoxRequestJson.getInstance().toByteArray(this.mRequest);
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
 * Qualified Name:     com.google.android.apps.plus.oob.OutOfBoxRequestParcelable
 * JD-Core Version:    0.6.2
 */