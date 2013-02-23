package com.google.android.apps.plus.content;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.apps.plus.R.string;
import java.util.Arrays;
import java.util.List;

public class AudienceData
  implements Parcelable, Cloneable
{
  public static final Parcelable.Creator<AudienceData> CREATOR = new Parcelable.Creator()
  {
  };
  private CircleData[] mCircles;
  private SquareTargetData[] mSquareTargets;
  private int mTotalPersonCount;
  private PersonData[] mUsers;

  private AudienceData(Parcel paramParcel)
  {
    this.mUsers = new PersonData[paramParcel.readInt()];
    paramParcel.readTypedArray(this.mUsers, PersonData.CREATOR);
    this.mCircles = new CircleData[paramParcel.readInt()];
    paramParcel.readTypedArray(this.mCircles, CircleData.CREATOR);
    this.mSquareTargets = new SquareTargetData[paramParcel.readInt()];
    paramParcel.readTypedArray(this.mSquareTargets, SquareTargetData.CREATOR);
    this.mTotalPersonCount = paramParcel.readInt();
  }

  public AudienceData(CircleData paramCircleData)
  {
    this.mUsers = new PersonData[0];
    this.mCircles = new CircleData[1];
    this.mSquareTargets = new SquareTargetData[0];
    this.mCircles[0] = paramCircleData;
  }

  public AudienceData(PersonData paramPersonData)
  {
    this.mUsers = new PersonData[1];
    this.mCircles = new CircleData[0];
    this.mSquareTargets = new SquareTargetData[0];
    this.mUsers[0] = paramPersonData;
    this.mTotalPersonCount = 1;
  }

  public AudienceData(SquareTargetData paramSquareTargetData)
  {
    this.mUsers = new PersonData[0];
    this.mCircles = new CircleData[0];
    this.mSquareTargets = new SquareTargetData[1];
    this.mSquareTargets[0] = paramSquareTargetData;
  }

  public AudienceData(List<PersonData> paramList, List<CircleData> paramList1)
  {
    this(paramList, paramList1, null);
  }

  public AudienceData(List<PersonData> paramList, List<CircleData> paramList1, int paramInt)
  {
    this(paramList, null, null, paramInt);
  }

  public AudienceData(List<PersonData> paramList, List<CircleData> paramList1, List<SquareTargetData> paramList2)
  {
  }

  public AudienceData(List<PersonData> paramList, List<CircleData> paramList1, List<SquareTargetData> paramList2, int paramInt)
  {
    if (paramList != null)
    {
      this.mUsers = new PersonData[paramList.size()];
      paramList.toArray(this.mUsers);
      if (paramList1 == null)
        break label106;
      this.mCircles = new CircleData[paramList1.size()];
      paramList1.toArray(this.mCircles);
      label60: if (paramList2 == null)
        break label117;
      this.mSquareTargets = new SquareTargetData[paramList2.size()];
      paramList2.toArray(this.mSquareTargets);
    }
    while (true)
    {
      this.mTotalPersonCount = paramInt;
      return;
      this.mUsers = new PersonData[0];
      break;
      label106: this.mCircles = new CircleData[0];
      break label60;
      label117: this.mSquareTargets = new SquareTargetData[0];
    }
  }

  public final AudienceData clone()
  {
    return new AudienceData(Arrays.asList(this.mUsers), Arrays.asList(this.mCircles), Arrays.asList(this.mSquareTargets), this.mTotalPersonCount);
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof AudienceData))
    {
      AudienceData localAudienceData = (AudienceData)paramObject;
      if ((this.mTotalPersonCount != localAudienceData.mTotalPersonCount) || (!Arrays.equals(this.mUsers, localAudienceData.mUsers)) || (!Arrays.equals(this.mCircles, localAudienceData.mCircles)) || (!Arrays.equals(this.mSquareTargets, localAudienceData.mSquareTargets)));
    }
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final CircleData getCircle(int paramInt)
  {
    return this.mCircles[paramInt];
  }

  public final int getCircleCount()
  {
    return this.mCircles.length;
  }

  public final CircleData[] getCircles()
  {
    return this.mCircles;
  }

  public final int getHiddenUserCount()
  {
    return Math.max(0, this.mTotalPersonCount - this.mUsers.length);
  }

  public final SquareTargetData getSquareTarget(int paramInt)
  {
    return this.mSquareTargets[0];
  }

  public final int getSquareTargetCount()
  {
    return this.mSquareTargets.length;
  }

  public final SquareTargetData[] getSquareTargets()
  {
    return this.mSquareTargets;
  }

  public final PersonData getUser(int paramInt)
  {
    return this.mUsers[paramInt];
  }

  public final int getUserCount()
  {
    return this.mUsers.length;
  }

  public final PersonData[] getUsers()
  {
    return this.mUsers;
  }

  public int hashCode()
  {
    return 31 * (31 * (31 * (527 + this.mTotalPersonCount) + Arrays.hashCode(this.mUsers)) + Arrays.hashCode(this.mCircles)) + Arrays.hashCode(this.mSquareTargets);
  }

  public final boolean isEmpty()
  {
    if ((this.mUsers.length == 0) && (this.mCircles.length == 0) && (this.mSquareTargets.length == 0));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final String toNameList(Context paramContext)
  {
    Resources localResources = paramContext.getResources();
    String str1 = localResources.getString(R.string.compose_acl_separator);
    String str2 = localResources.getString(17039374);
    String str3 = localResources.getString(R.string.loading);
    String str4 = localResources.getString(R.string.square_unknown);
    int i = this.mCircles.length + this.mUsers.length + this.mSquareTargets.length;
    StringBuilder localStringBuilder = new StringBuilder();
    int j = 0;
    int k = 0;
    if (k < this.mCircles.length)
    {
      String str8 = this.mCircles[k].getName();
      if (!TextUtils.isEmpty(str8));
      while (true)
      {
        localStringBuilder.append(str8);
        j++;
        if (j < i)
          localStringBuilder.append(str1);
        k++;
        break;
        str8 = str3;
      }
    }
    int m = 0;
    if (m < this.mUsers.length)
    {
      Object localObject = this.mUsers[m].getName();
      String str7 = this.mUsers[m].getEmail();
      if (!TextUtils.isEmpty((CharSequence)localObject));
      while (true)
      {
        localStringBuilder.append((String)localObject);
        j++;
        if (j < i)
          localStringBuilder.append(str1);
        m++;
        break;
        if (!TextUtils.isEmpty(str7))
          localObject = str7;
        else
          localObject = str2;
      }
    }
    int n = 0;
    if (n < this.mSquareTargets.length)
    {
      String str5 = this.mSquareTargets[n].getSquareName();
      String str6 = this.mSquareTargets[n].getSquareStreamName();
      if (TextUtils.isEmpty(str5))
        str5 = str4;
      if (TextUtils.isEmpty(str6))
        localStringBuilder.append(str5);
      while (true)
      {
        j++;
        if (j < i)
          localStringBuilder.append(str1);
        n++;
        break;
        localStringBuilder.append(localResources.getString(R.string.square_name_and_topic, new Object[] { str5, str6 }));
      }
    }
    return localStringBuilder.toString();
  }

  public String toString()
  {
    return "Audience circles: " + Arrays.asList(this.mCircles) + ", users: " + Arrays.asList(this.mUsers) + ", squares: " + Arrays.asList(this.mSquareTargets) + ", hidden users: " + getHiddenUserCount();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.mUsers.length);
    paramParcel.writeTypedArray(this.mUsers, 0);
    paramParcel.writeInt(this.mCircles.length);
    paramParcel.writeTypedArray(this.mCircles, 0);
    paramParcel.writeInt(this.mSquareTargets.length);
    paramParcel.writeTypedArray(this.mSquareTargets, 0);
    paramParcel.writeInt(this.mTotalPersonCount);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.AudienceData
 * JD-Core Version:    0.6.2
 */