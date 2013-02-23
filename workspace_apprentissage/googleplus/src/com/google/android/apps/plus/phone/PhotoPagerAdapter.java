package com.google.android.apps.plus.phone;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.fragments.LoadingFragment;
import com.google.android.apps.plus.fragments.PhotoOneUpFragment;

public final class PhotoPagerAdapter extends EsCursorPagerAdapter
{
  final EsAccount mAccount;
  final boolean mAllowPlusOne;
  private final String mDefaultAlbumName;
  final boolean mDisableComments;
  final String mEventId;
  final Long mForceLoadId;
  private Pageable mPageable;
  final String mStreamId;

  public PhotoPagerAdapter(Context paramContext, FragmentManager paramFragmentManager, Cursor paramCursor, EsAccount paramEsAccount, Long paramLong, String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramContext, paramFragmentManager, null);
    this.mAccount = paramEsAccount;
    this.mForceLoadId = paramLong;
    this.mStreamId = paramString1;
    this.mEventId = paramString2;
    this.mDefaultAlbumName = paramString3;
    this.mAllowPlusOne = paramBoolean1;
    this.mDisableComments = paramBoolean2;
  }

  public final int getCount()
  {
    if ((this.mPageable != null) && (this.mPageable.hasMore()));
    for (int i = 1 + super.getCount(); ; i = super.getCount())
      return i;
  }

  public final Fragment getItem(int paramInt)
  {
    Cursor localCursor;
    if (isDataValid())
    {
      localCursor = getCursor();
      if ((localCursor == null) || ((!localCursor.isClosed()) && (paramInt < localCursor.getCount())))
        break label59;
      this.mPageable.loadMore();
    }
    label59: for (Object localObject = new LoadingFragment(); ; localObject = super.getItem(paramInt))
    {
      return localObject;
      localCursor = null;
      break;
    }
  }

  public final Fragment getItem$2282a066(Cursor paramCursor)
  {
    int i = 1;
    long l = paramCursor.getLong(i);
    String str1 = paramCursor.getString(2);
    String str2 = paramCursor.getString(3);
    String str3 = paramCursor.getString(4);
    boolean bool;
    Intents.PhotoViewIntentBuilder localPhotoViewIntentBuilder1;
    Intents.PhotoViewIntentBuilder localPhotoViewIntentBuilder2;
    if (paramCursor.getInt(6) != 0)
    {
      bool = "PLACEHOLDER".equals(paramCursor.getString(8));
      localPhotoViewIntentBuilder1 = Intents.newPhotoViewFragmentIntentBuilder(this.mContext);
      localPhotoViewIntentBuilder2 = localPhotoViewIntentBuilder1.setAccount(this.mAccount).setPhotoId(Long.valueOf(l)).setGaiaId(str2).setPhotoUrl(str1);
      if (i == 0)
        break label208;
    }
    label208: for (MediaRef.MediaType localMediaType = MediaRef.MediaType.PANORAMA; ; localMediaType = MediaRef.MediaType.IMAGE)
    {
      localPhotoViewIntentBuilder2.setMediaType(localMediaType).setDisplayName(str3).setAlbumName(this.mDefaultAlbumName).setStreamId(this.mStreamId).setEventId(this.mEventId).setAllowPlusOne(Boolean.valueOf(this.mAllowPlusOne)).setForceLoadId(this.mForceLoadId).setDisableComments(Boolean.valueOf(this.mDisableComments)).setIsPlaceholder(Boolean.valueOf(bool));
      PhotoOneUpFragment localPhotoOneUpFragment = new PhotoOneUpFragment();
      localPhotoOneUpFragment.setArguments(localPhotoViewIntentBuilder1.build().getExtras());
      return localPhotoOneUpFragment;
      i = 0;
      break;
    }
  }

  public final void setPageable(Pageable paramPageable)
  {
    this.mPageable = paramPageable;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.PhotoPagerAdapter
 * JD-Core Version:    0.6.2
 */