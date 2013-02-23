package com.google.android.apps.plus.fragments;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.apps.plus.R.styleable;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.views.ParticipantsGalleryView;
import com.google.android.apps.plus.views.ParticipantsGalleryView.CommandListener;
import com.google.wireless.realtimechat.proto.Data.Participant;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ParticipantsGalleryFragment extends Fragment
{
  private EsAccount mAccount;
  private Integer mBackgroundColor;
  private ParticipantsGalleryView.CommandListener mCommandListener;
  private String mEmptyMessage;
  private boolean mParticipantListButtonVisibility = true;
  private ParticipantsGalleryView mView;

  public final void addParticipants(Collection<Data.Participant> paramCollection)
  {
    LayoutInflater localLayoutInflater = LayoutInflater.from(getActivity());
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      Data.Participant localParticipant = (Data.Participant)localIterator.next();
      this.mView.addParticipant(localLayoutInflater, localParticipant);
    }
  }

  public final ParticipantsGalleryView getParticipantsGalleryView()
  {
    return this.mView;
  }

  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mView = new ParticipantsGalleryView(getActivity());
    if (this.mBackgroundColor != null)
      this.mView.setBackgroundColor(this.mBackgroundColor.intValue());
    if (this.mEmptyMessage != null)
      this.mView.setEmptyMessage(this.mEmptyMessage);
    if (this.mAccount != null)
      this.mView.setAccount(this.mAccount);
    if (this.mCommandListener != null)
      this.mView.setCommandListener(this.mCommandListener);
    this.mView.setParticipantListButtonVisibility(this.mParticipantListButtonVisibility);
    return this.mView;
  }

  public final void onInflate(Activity paramActivity, AttributeSet paramAttributeSet, Bundle paramBundle)
  {
    TypedArray localTypedArray = paramActivity.obtainStyledAttributes(paramAttributeSet, R.styleable.ParticipantsGalleryFragment);
    if (localTypedArray.hasValue(0))
      this.mBackgroundColor = Integer.valueOf(localTypedArray.getColor(0, 0));
    if (localTypedArray.hasValue(1))
      this.mEmptyMessage = localTypedArray.getString(1);
    localTypedArray.recycle();
  }

  public final void onPause()
  {
    super.onPause();
    this.mView.dismissAvatarMenuDialog();
  }

  public final void removeAllParticipants()
  {
    this.mView.removeAllParticipants();
  }

  public final void setAccount(EsAccount paramEsAccount)
  {
    this.mAccount = paramEsAccount;
    if (this.mView != null)
      this.mView.setAccount(paramEsAccount);
  }

  public final void setCommandListener(ParticipantsGalleryView.CommandListener paramCommandListener)
  {
    this.mCommandListener = paramCommandListener;
    if (this.mView != null)
      this.mView.setCommandListener(paramCommandListener);
  }

  public final void setParticipantListButtonVisibility(boolean paramBoolean)
  {
    this.mParticipantListButtonVisibility = paramBoolean;
    if (this.mView != null)
      this.mView.setParticipantListButtonVisibility(paramBoolean);
  }

  public final void setParticipants(HashMap<String, Data.Participant> paramHashMap, HashSet<String> paramHashSet1, HashSet<String> paramHashSet2)
  {
    if (paramHashMap != null)
      this.mView.setParticipants(paramHashMap, paramHashSet1, paramHashSet2);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.ParticipantsGalleryFragment
 * JD-Core Version:    0.6.2
 */