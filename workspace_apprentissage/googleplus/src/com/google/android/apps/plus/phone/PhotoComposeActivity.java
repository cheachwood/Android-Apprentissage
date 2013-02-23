package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.analytics.InstrumentedActivity;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.content.EsAccount;
import java.util.ArrayList;
import java.util.List;

public class PhotoComposeActivity extends InstrumentedActivity
  implements PhotoComposeFragment.RemoveImageListener, PhotoComposePagerAdapter.MediaRefProvider
{
  private EsAccount mAccount;
  private PhotoComposePagerAdapter mAdapter;
  private List<MediaRef> mMediaRefs;
  private List<MediaRef> mMediaRefsToRemove;
  private int mStartingPosition;
  private ViewPager mViewPager;

  private void finishActivity()
  {
    Intent localIntent = new Intent();
    MediaRef[] arrayOfMediaRef = new MediaRef[this.mMediaRefsToRemove.size()];
    for (int i = 0; i < this.mMediaRefsToRemove.size(); i++)
      arrayOfMediaRef[i] = ((MediaRef)this.mMediaRefsToRemove.get(i));
    localIntent.putExtra("photo_remove_from_compose", arrayOfMediaRef);
    setResult(-1, localIntent);
    finish();
  }

  protected final EsAccount getAccount()
  {
    return this.mAccount;
  }

  public final int getCount()
  {
    return this.mMediaRefs.size();
  }

  public final MediaRef getItem(int paramInt)
  {
    if ((paramInt >= 0) || (paramInt < this.mMediaRefs.size()));
    for (MediaRef localMediaRef = (MediaRef)this.mMediaRefs.get(paramInt); ; localMediaRef = null)
      return localMediaRef;
  }

  public final int getItemPosition(Object paramObject)
  {
    int i;
    if (!(paramObject instanceof MediaRef))
      i = -2;
    while (true)
    {
      return i;
      i = this.mMediaRefs.indexOf(paramObject);
      if (i == -1)
        i = -2;
    }
  }

  public final OzViews getViewForLogging()
  {
    return OzViews.PHOTO;
  }

  public void onBackPressed()
  {
    if (this.mMediaRefsToRemove.size() > 0)
      finishActivity();
    while (true)
    {
      return;
      super.onBackPressed();
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.photo_compose_activity);
    Intent localIntent = getIntent();
    this.mAccount = ((EsAccount)localIntent.getParcelableExtra("account"));
    this.mStartingPosition = localIntent.getIntExtra("photo_index", 0);
    if (localIntent.hasExtra("mediarefs"))
    {
      Parcelable[] arrayOfParcelable = localIntent.getParcelableArrayExtra("mediarefs");
      this.mMediaRefs = new ArrayList(arrayOfParcelable.length);
      for (int i = 0; i < arrayOfParcelable.length; i++)
        this.mMediaRefs.add((MediaRef)arrayOfParcelable[i]);
    }
    finish();
    if ((this.mStartingPosition < 0) || (this.mStartingPosition >= this.mMediaRefs.size()))
      this.mStartingPosition = 0;
    this.mAdapter = new PhotoComposePagerAdapter(this, getSupportFragmentManager(), this.mAccount, this, this);
    this.mViewPager = ((ViewPager)findViewById(R.id.view_pager));
    this.mViewPager.setAdapter(this.mAdapter);
    this.mViewPager.setCurrentItem(this.mStartingPosition);
    this.mMediaRefsToRemove = new ArrayList();
  }

  public final void onImageRemoved(MediaRef paramMediaRef)
  {
    this.mMediaRefsToRemove.add(paramMediaRef);
    this.mMediaRefs.remove(paramMediaRef);
    if (this.mMediaRefs.size() == 0)
      finishActivity();
    this.mAdapter.notifyDataSetChanged();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.PhotoComposeActivity
 * JD-Core Version:    0.6.2
 */