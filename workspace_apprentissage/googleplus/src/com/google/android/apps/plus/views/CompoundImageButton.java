package com.google.android.apps.plus.views;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View.BaseSavedState;
import android.view.ViewDebug.ExportedProperty;
import android.widget.Checkable;
import android.widget.ImageButton;

public class CompoundImageButton extends ImageButton
  implements Checkable
{
  private static final int[] CHECKED_STATE_SET = { 16842912 };
  private boolean mBroadcasting;
  private boolean mChecked;
  private OnCheckedChangeListener mOnCheckedChangeListener;

  public CompoundImageButton(Context paramContext)
  {
    this(paramContext, null);
  }

  public CompoundImageButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public CompoundImageButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  private void setChecked(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.mChecked != paramBoolean1)
    {
      this.mChecked = paramBoolean1;
      refreshDrawableState();
      if (!this.mBroadcasting)
        break label25;
    }
    while (true)
    {
      return;
      label25: this.mBroadcasting = true;
      if (this.mOnCheckedChangeListener != null);
      this.mBroadcasting = false;
    }
  }

  @ViewDebug.ExportedProperty
  public boolean isChecked()
  {
    return this.mChecked;
  }

  public int[] onCreateDrawableState(int paramInt)
  {
    int[] arrayOfInt = super.onCreateDrawableState(paramInt + 1);
    if (isChecked())
      mergeDrawableStates(arrayOfInt, CHECKED_STATE_SET);
    return arrayOfInt;
  }

  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    setChecked(localSavedState.checked);
    requestLayout();
  }

  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.checked = isChecked();
    return localSavedState;
  }

  public boolean performClick()
  {
    if (!this.mChecked);
    for (boolean bool = true; ; bool = false)
    {
      setChecked(bool, true);
      return super.performClick();
    }
  }

  public void setChecked(boolean paramBoolean)
  {
    setChecked(paramBoolean, false);
  }

  public void setOnCheckedChangeListener(OnCheckedChangeListener paramOnCheckedChangeListener)
  {
    this.mOnCheckedChangeListener = paramOnCheckedChangeListener;
  }

  public void toggle()
  {
    if (!this.mChecked);
    for (boolean bool = true; ; bool = false)
    {
      setChecked(bool);
      return;
    }
  }

  public static abstract interface OnCheckedChangeListener
  {
  }

  static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
    };
    boolean checked;

    private SavedState(Parcel paramParcel)
    {
      super();
      if (paramParcel.readInt() != 0);
      for (boolean bool = true; ; bool = false)
      {
        this.checked = bool;
        return;
      }
    }

    SavedState(Parcelable paramParcelable)
    {
      super();
    }

    public String toString()
    {
      String str = Integer.toHexString(System.identityHashCode(this));
      return "CompoundImageButton.SavedState{" + str + " checked=" + this.checked + "}";
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      if (this.checked);
      for (int i = 1; ; i = 0)
      {
        paramParcel.writeInt(i);
        return;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.CompoundImageButton
 * JD-Core Version:    0.6.2
 */