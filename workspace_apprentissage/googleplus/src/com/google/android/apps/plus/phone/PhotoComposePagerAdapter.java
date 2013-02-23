package com.google.android.apps.plus.phone;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.content.EsAccount;

public class PhotoComposePagerAdapter extends FragmentStatePagerAdapter
{
  private final EsAccount mAccount;
  private final Context mContext;
  private final MediaRefProvider mMediaRefProvider;
  private final PhotoComposeFragment.RemoveImageListener mRemoveImageListener;

  public PhotoComposePagerAdapter(Context paramContext, FragmentManager paramFragmentManager, EsAccount paramEsAccount, PhotoComposeFragment.RemoveImageListener paramRemoveImageListener, MediaRefProvider paramMediaRefProvider)
  {
    super(paramFragmentManager);
    if (paramMediaRefProvider == null)
      throw new IllegalArgumentException("MediaRefProvider was null!");
    this.mContext = paramContext;
    this.mAccount = paramEsAccount;
    this.mMediaRefProvider = paramMediaRefProvider;
    this.mRemoveImageListener = paramRemoveImageListener;
  }

  public final int getCount()
  {
    return this.mMediaRefProvider.getCount();
  }

  public final Fragment getItem(int paramInt)
  {
    Intents.PhotoViewIntentBuilder localPhotoViewIntentBuilder = Intents.newPhotoComposeFragmentIntentBuilder(this.mContext);
    Object localObject;
    if ((paramInt < 0) || (paramInt >= this.mMediaRefProvider.getCount()))
      localObject = null;
    while (true)
    {
      return localObject;
      MediaRef localMediaRef = this.mMediaRefProvider.getItem(paramInt);
      localPhotoViewIntentBuilder.setAccount(this.mAccount).setPhotoRef(localMediaRef);
      localObject = new PhotoComposeFragment();
      ((PhotoComposeFragment)localObject).setArguments(localPhotoViewIntentBuilder.build().getExtras());
      ((PhotoComposeFragment)localObject).setRemoveImageListener(this.mRemoveImageListener);
    }
  }

  public final int getItemPosition(Object paramObject)
  {
    return this.mMediaRefProvider.getItemPosition(paramObject);
  }

  public static abstract interface MediaRefProvider
  {
    public abstract int getCount();

    public abstract MediaRef getItem(int paramInt);

    public abstract int getItemPosition(Object paramObject);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.PhotoComposePagerAdapter
 * JD-Core Version:    0.6.2
 */