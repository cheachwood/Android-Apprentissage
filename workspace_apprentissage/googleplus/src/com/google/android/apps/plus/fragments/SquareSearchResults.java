package com.google.android.apps.plus.fragments;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.apps.plus.phone.EsMatrixCursor;
import com.google.api.services.plusi.model.SquareId;
import com.google.api.services.plusi.model.SquareResult;
import java.util.ArrayList;
import java.util.List;

public class SquareSearchResults
  implements Parcelable
{
  public static final Parcelable.Creator<SquareSearchResults> CREATOR = new Parcelable.Creator()
  {
  };
  private String mContinuationToken;
  private EsMatrixCursor mCursor;
  private boolean mCursorValid;
  private boolean mHasMoreResults;
  private long mNextId;
  private final String[] mProjection;
  private String mQuery;
  private List<SquareResult> mResults;

  public SquareSearchResults(Parcel paramParcel)
  {
    this.mQuery = paramParcel.readString();
    this.mContinuationToken = paramParcel.readString();
    boolean bool1;
    int j;
    label62: SquareResult localSquareResult;
    if (paramParcel.readInt() != 0)
    {
      bool1 = true;
      this.mHasMoreResults = bool1;
      this.mProjection = paramParcel.createStringArray();
      int i = paramParcel.readInt();
      this.mResults = new ArrayList(i);
      j = 0;
      if (j >= i)
        return;
      localSquareResult = new SquareResult();
      localSquareResult.squareId = new SquareId();
      localSquareResult.squareId.obfuscatedGaiaId = paramParcel.readString();
      localSquareResult.displayName = paramParcel.readString();
      localSquareResult.photoUrl = paramParcel.readString();
      localSquareResult.snippetHtml = paramParcel.readString();
      if (paramParcel.readInt() != 0)
        localSquareResult.memberCount = Long.valueOf(paramParcel.readLong());
      if (paramParcel.readInt() != 0)
        if (paramParcel.readInt() == 0)
          break label185;
    }
    label185: for (boolean bool2 = true; ; bool2 = false)
    {
      localSquareResult.privatePosts = Boolean.valueOf(bool2);
      j++;
      break label62;
      bool1 = false;
      break;
    }
  }

  public SquareSearchResults(String[] paramArrayOfString)
  {
    this.mProjection = paramArrayOfString;
    this.mResults = new ArrayList();
  }

  public final void addResults(List<SquareResult> paramList)
  {
    this.mResults.addAll(paramList);
    this.mCursorValid = false;
  }

  public int describeContents()
  {
    return 0;
  }

  public final String getContinuationToken()
  {
    return this.mContinuationToken;
  }

  public final int getCount()
  {
    return this.mResults.size();
  }

  public final Cursor getCursor()
  {
    if (this.mCursorValid);
    for (EsMatrixCursor localEsMatrixCursor = this.mCursor; ; localEsMatrixCursor = this.mCursor)
    {
      return localEsMatrixCursor;
      this.mCursor = new EsMatrixCursor(this.mProjection);
      this.mCursorValid = true;
      int i = -1;
      int j = -1;
      int k = -1;
      int m = -1;
      int n = -1;
      int i1 = -1;
      int i2 = -1;
      int i3 = 0;
      if (i3 < this.mProjection.length)
      {
        String str = this.mProjection[i3];
        if ("_id".equals(str))
          i = i3;
        while (true)
        {
          i3++;
          break;
          if ("square_id".equals(str))
            j = i3;
          else if ("square_name".equals(str))
            k = i3;
          else if ("photo_url".equals(str))
            m = i3;
          else if ("post_visibility".equals(str))
            n = i3;
          else if ("member_count".equals(str))
            i1 = i3;
          else if ("snippet".equals(str))
            i2 = i3;
        }
      }
      int i4 = this.mResults.size();
      int i5 = 0;
      if (i5 < i4)
      {
        SquareResult localSquareResult = (SquareResult)this.mResults.get(i5);
        Object[] arrayOfObject = new Object[this.mProjection.length];
        if (i >= 0)
        {
          long l = this.mNextId;
          this.mNextId = (1L + l);
          arrayOfObject[i] = Long.valueOf(l);
        }
        if (j >= 0)
          arrayOfObject[j] = localSquareResult.squareId.obfuscatedGaiaId;
        if (k >= 0)
          arrayOfObject[k] = localSquareResult.displayName;
        if (m >= 0)
          arrayOfObject[m] = localSquareResult.photoUrl;
        if ((n >= 0) && (localSquareResult.privatePosts != null))
          if (!localSquareResult.privatePosts.booleanValue())
            break label395;
        label395: for (int i6 = 1; ; i6 = 0)
        {
          arrayOfObject[n] = Integer.valueOf(i6);
          if (i1 >= 0)
            arrayOfObject[i1] = localSquareResult.memberCount;
          if (i2 >= 0)
            arrayOfObject[i2] = localSquareResult.snippetHtml;
          this.mCursor.addRow(arrayOfObject);
          i5++;
          break;
        }
      }
    }
  }

  public final String getQuery()
  {
    return this.mQuery;
  }

  public final boolean hasMoreResults()
  {
    return this.mHasMoreResults;
  }

  public final boolean isEmpty()
  {
    return this.mResults.isEmpty();
  }

  public final boolean isParcelable()
  {
    if (getCount() <= 1000);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final void setContinuationToken(String paramString)
  {
    this.mContinuationToken = paramString;
  }

  public final void setHasMoreResults(boolean paramBoolean)
  {
    this.mHasMoreResults = paramBoolean;
  }

  public final void setQueryString(String paramString)
  {
    if (TextUtils.equals(this.mQuery, paramString));
    while (true)
    {
      return;
      this.mQuery = paramString;
      this.mResults.clear();
      this.mCursor = null;
      this.mCursorValid = false;
      this.mContinuationToken = null;
    }
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mQuery);
    paramParcel.writeString(this.mContinuationToken);
    int i;
    int j;
    label56: int k;
    label65: int m;
    if (this.mHasMoreResults)
    {
      i = 1;
      paramParcel.writeInt(i);
      paramParcel.writeStringArray(this.mProjection);
      if (this.mResults == null)
        break label196;
      j = this.mResults.size();
      paramParcel.writeInt(j);
      k = 0;
      if (k >= j)
        return;
      SquareResult localSquareResult = (SquareResult)this.mResults.get(k);
      paramParcel.writeString(localSquareResult.squareId.obfuscatedGaiaId);
      paramParcel.writeString(localSquareResult.displayName);
      paramParcel.writeString(localSquareResult.photoUrl);
      paramParcel.writeString(localSquareResult.snippetHtml);
      if (localSquareResult.memberCount == null)
        break label202;
      paramParcel.writeInt(1);
      paramParcel.writeLong(localSquareResult.memberCount.longValue());
      label152: if (localSquareResult.privatePosts == null)
        break label216;
      paramParcel.writeInt(1);
      if (!localSquareResult.privatePosts.booleanValue())
        break label210;
      m = 1;
      label179: paramParcel.writeInt(m);
    }
    while (true)
    {
      k++;
      break label65;
      i = 0;
      break;
      label196: j = 0;
      break label56;
      label202: paramParcel.writeInt(0);
      break label152;
      label210: m = 0;
      break label179;
      label216: paramParcel.writeInt(0);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.SquareSearchResults
 * JD-Core Version:    0.6.2
 */