package com.google.android.apps.plus.settings;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.preference.DialogPreference;
import android.preference.Preference.BaseSavedState;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.styleable;

public class EsListPreference extends DialogPreference
{
  private int mClickedDialogEntryIndex;
  private CharSequence[] mEntries;
  private CharSequence[] mEntrySummaries;
  private CharSequence mEntrySummaryArgument;
  private CharSequence[] mEntryValues;
  private LayoutInflater mInflater;
  private String mValue;

  public EsListPreference(Context paramContext)
  {
    this(paramContext, null);
  }

  public EsListPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.EsListPreference, 0, 0);
    this.mEntries = localTypedArray.getTextArray(0);
    this.mEntryValues = localTypedArray.getTextArray(1);
    this.mEntrySummaries = localTypedArray.getTextArray(2);
    localTypedArray.recycle();
    this.mInflater = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
  }

  private int getValueIndex()
  {
    String str = this.mValue;
    int i;
    if ((str != null) && (this.mEntryValues != null))
    {
      i = -1 + this.mEntryValues.length;
      if (i >= 0)
        if (!this.mEntryValues[i].equals(str));
    }
    while (true)
    {
      return i;
      i--;
      break;
      i = -1;
    }
  }

  protected void onDialogClosed(boolean paramBoolean)
  {
    super.onDialogClosed(paramBoolean);
    if ((paramBoolean) && (this.mClickedDialogEntryIndex >= 0) && (this.mEntryValues != null))
    {
      String str = this.mEntryValues[this.mClickedDialogEntryIndex].toString();
      if (callChangeListener(str))
        setValue(str);
    }
  }

  protected Object onGetDefaultValue(TypedArray paramTypedArray, int paramInt)
  {
    return paramTypedArray.getString(paramInt);
  }

  protected void onPrepareDialogBuilder(AlertDialog.Builder paramBuilder)
  {
    super.onPrepareDialogBuilder(paramBuilder);
    if ((this.mEntries == null) || (this.mEntryValues == null))
      throw new IllegalStateException("ListPreference requires an entries array and an entryValues array.");
    this.mClickedDialogEntryIndex = getValueIndex();
    paramBuilder.setSingleChoiceItems(new ListAdapter()
    {
      public final boolean areAllItemsEnabled()
      {
        return true;
      }

      public final int getCount()
      {
        return EsListPreference.this.mEntries.length;
      }

      public final Object getItem(int paramAnonymousInt)
      {
        return EsListPreference.this.mEntryValues[paramAnonymousInt];
      }

      public final long getItemId(int paramAnonymousInt)
      {
        return paramAnonymousInt;
      }

      public final int getItemViewType(int paramAnonymousInt)
      {
        return 0;
      }

      public final View getView(int paramAnonymousInt, View paramAnonymousView, ViewGroup paramAnonymousViewGroup)
      {
        boolean bool = true;
        View localView;
        TextView localTextView;
        label112: RadioButton localRadioButton;
        if (paramAnonymousView == null)
        {
          localView = EsListPreference.this.mInflater.inflate(R.layout.simple_list_item_2_single_choice, paramAnonymousViewGroup, false);
          ((TextView)localView.findViewById(16908308)).setText(EsListPreference.this.mEntries[paramAnonymousInt]);
          localTextView = (TextView)localView.findViewById(16908309);
          if (EsListPreference.this.mEntrySummaryArgument == null)
            break label152;
          String str = EsListPreference.this.mEntrySummaries[paramAnonymousInt].toString();
          Object[] arrayOfObject = new Object[bool];
          arrayOfObject[0] = EsListPreference.this.mEntrySummaryArgument;
          localTextView.setText(String.format(str, arrayOfObject));
          localRadioButton = (RadioButton)localView.findViewById(R.id.radio);
          if (paramAnonymousInt != EsListPreference.this.mClickedDialogEntryIndex)
            break label169;
        }
        while (true)
        {
          localRadioButton.setChecked(bool);
          return localView;
          localView = paramAnonymousView;
          break;
          label152: localTextView.setText(EsListPreference.this.mEntrySummaries[paramAnonymousInt]);
          break label112;
          label169: bool = false;
        }
      }

      public final int getViewTypeCount()
      {
        return 1;
      }

      public final boolean hasStableIds()
      {
        return true;
      }

      public final boolean isEmpty()
      {
        if (EsListPreference.this.mEntries.length == 0);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean isEnabled(int paramAnonymousInt)
      {
        return true;
      }

      public final void registerDataSetObserver(DataSetObserver paramAnonymousDataSetObserver)
      {
      }

      public final void unregisterDataSetObserver(DataSetObserver paramAnonymousDataSetObserver)
      {
      }
    }
    , this.mClickedDialogEntryIndex, new DialogInterface.OnClickListener()
    {
      public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        EsListPreference.access$502(EsListPreference.this, paramAnonymousInt);
        EsListPreference.this.onClick(paramAnonymousDialogInterface, -1);
        paramAnonymousDialogInterface.dismiss();
      }
    });
    paramBuilder.setPositiveButton(null, null);
  }

  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if ((paramParcelable == null) || (!paramParcelable.getClass().equals(SavedState.class)))
      super.onRestoreInstanceState(paramParcelable);
    while (true)
    {
      return;
      SavedState localSavedState = (SavedState)paramParcelable;
      super.onRestoreInstanceState(localSavedState.getSuperState());
      setValue(localSavedState.value);
    }
  }

  protected Parcelable onSaveInstanceState()
  {
    Object localObject = super.onSaveInstanceState();
    if (isPersistent());
    while (true)
    {
      return localObject;
      SavedState localSavedState = new SavedState((Parcelable)localObject);
      localSavedState.value = this.mValue;
      localObject = localSavedState;
    }
  }

  protected void onSetInitialValue(boolean paramBoolean, Object paramObject)
  {
    if (paramBoolean);
    for (String str = getPersistedString(this.mValue); ; str = (String)paramObject)
    {
      setValue(str);
      return;
    }
  }

  public final void setEntrySummaryArgument(CharSequence paramCharSequence)
  {
    this.mEntrySummaryArgument = paramCharSequence;
  }

  public final void setValue(String paramString)
  {
    this.mValue = paramString;
    persistString(paramString);
  }

  private static class SavedState extends Preference.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
    };
    String value;

    public SavedState(Parcel paramParcel)
    {
      super();
      this.value = paramParcel.readString();
    }

    public SavedState(Parcelable paramParcelable)
    {
      super();
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeString(this.value);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.settings.EsListPreference
 * JD-Core Version:    0.6.2
 */