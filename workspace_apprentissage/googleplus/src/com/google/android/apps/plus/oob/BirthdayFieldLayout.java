package com.google.android.apps.plus.oob;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View.BaseSavedState;
import android.widget.DatePicker;
import android.widget.TextView;
import com.google.api.services.plusi.model.MobileCoarseDate;
import com.google.api.services.plusi.model.OutOfBoxField;
import com.google.api.services.plusi.model.OutOfBoxFieldValue;
import com.google.api.services.plusi.model.OutOfBoxInputField;

public class BirthdayFieldLayout extends BaseFieldLayout
{
  private DatePicker mInput;

  public BirthdayFieldLayout(Context paramContext)
  {
    super(paramContext);
  }

  public BirthdayFieldLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public BirthdayFieldLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public final void bindToField(OutOfBoxField paramOutOfBoxField, int paramInt, ActionCallback paramActionCallback)
  {
    super.bindToField(paramOutOfBoxField, paramInt, paramActionCallback);
    OutOfBoxInputField localOutOfBoxInputField = getField().input;
    getLabelView().setText(localOutOfBoxInputField.label);
    this.mInput = ((DatePicker)getInputView());
    MobileCoarseDate localMobileCoarseDate = getServerDateValue();
    int i;
    int j;
    label88: int k;
    if (localMobileCoarseDate != null)
      if (localMobileCoarseDate.year != null)
      {
        i = localMobileCoarseDate.year.intValue();
        if (localMobileCoarseDate.month == null)
          break label126;
        j = -1 + localMobileCoarseDate.month.intValue();
        if (localMobileCoarseDate.day == null)
          break label132;
        k = localMobileCoarseDate.day.intValue();
        label106: this.mInput.updateDate(i, j, k);
      }
    while (true)
    {
      return;
      i = 0;
      break;
      label126: j = 0;
      break label88;
      label132: k = 0;
      break label106;
      this.mInput.updateDate(1970, 0, 1);
    }
  }

  public final boolean isEmpty()
  {
    return false;
  }

  public final OutOfBoxInputField newFieldFromInput()
  {
    OutOfBoxInputField localOutOfBoxInputField = OutOfBoxMessages.copyWithoutValue(getField().input);
    localOutOfBoxInputField.value = new OutOfBoxFieldValue();
    OutOfBoxFieldValue localOutOfBoxFieldValue = localOutOfBoxInputField.value;
    MobileCoarseDate localMobileCoarseDate = new MobileCoarseDate();
    localMobileCoarseDate.year = Integer.valueOf(this.mInput.getYear());
    localMobileCoarseDate.month = Integer.valueOf(1 + this.mInput.getMonth());
    localMobileCoarseDate.day = Integer.valueOf(this.mInput.getDayOfMonth());
    localOutOfBoxFieldValue.dateValue = localMobileCoarseDate;
    return localOutOfBoxInputField;
  }

  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    this.mInput.updateDate(localSavedState.year, localSavedState.month, localSavedState.day);
  }

  protected Parcelable onSaveInstanceState()
  {
    return new SavedState(super.onSaveInstanceState(), this.mInput.getYear(), this.mInput.getMonth(), this.mInput.getDayOfMonth());
  }

  public static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
    };
    public final int day;
    public final int month;
    public final int year;

    private SavedState(Parcel paramParcel)
    {
      super();
      this.year = paramParcel.readInt();
      this.month = paramParcel.readInt();
      this.day = paramParcel.readInt();
    }

    SavedState(Parcelable paramParcelable, int paramInt1, int paramInt2, int paramInt3)
    {
      super();
      this.year = paramInt1;
      this.month = paramInt2;
      this.day = paramInt3;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.year);
      paramParcel.writeInt(this.month);
      paramParcel.writeInt(this.day);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.oob.BirthdayFieldLayout
 * JD-Core Version:    0.6.2
 */