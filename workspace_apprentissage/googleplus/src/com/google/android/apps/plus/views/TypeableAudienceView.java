package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ScrollView;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.styleable;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.fragments.PeopleSearchListAdapter;
import com.google.android.apps.plus.util.SoftInput;
import java.util.ArrayList;

public class TypeableAudienceView extends AudienceView
  implements TextWatcher, View.OnClickListener
{
  AudienceTextView mEditText;
  private int mEmptyAudienceHint;
  private int mMaxLines = -1;
  ScrollView mScrollView;

  static
  {
    if (!TypeableAudienceView.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public TypeableAudienceView(Context paramContext)
  {
    this(paramContext, null);
  }

  public TypeableAudienceView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public TypeableAudienceView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt, true);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.AudienceView, paramInt, 0);
    this.mMaxLines = localTypedArray.getInteger(0, -1);
    localTypedArray.recycle();
  }

  private void updateEditTextHint()
  {
    if (this.mEditText == null);
    while (true)
    {
      return;
      if ((this.mChips.isEmpty()) && (this.mEmptyAudienceHint != 0))
        this.mEditText.setHint(this.mEmptyAudienceHint);
      else
        this.mEditText.setHint("");
    }
  }

  public void afterTextChanged(Editable paramEditable)
  {
  }

  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public final void clearText()
  {
    this.mEditText.setText("");
    updateEditTextHint();
  }

  protected final int getChipCount()
  {
    return -1 + this.mChipContainer.getChildCount();
  }

  protected final void init()
  {
    addView(inflate(R.layout.typeable_audience_view));
    this.mScrollView = ((ScrollView)findViewById(R.id.audience_scrollview));
    this.mChipContainer = ((MultiLineLayout)findViewById(R.id.people_audience_view_chip_container));
    this.mChipContainer.setOnClickListener(this);
    this.mEditText = ((AudienceTextView)this.mChipContainer.getChildAt(0));
    this.mEditText.setThreshold(2);
    this.mEditText.setDropDownWidth(getResources().getDimensionPixelSize(R.dimen.audience_autocomplete_dropdown_width));
    this.mEditText.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public final void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        PeopleSearchListAdapter localPeopleSearchListAdapter = (PeopleSearchListAdapter)TypeableAudienceView.this.mEditText.getAdapter();
        if (localPeopleSearchListAdapter != null)
        {
          localPeopleSearchListAdapter.onItemClick(paramAnonymousInt);
          InputMethodManager localInputMethodManager = (InputMethodManager)TypeableAudienceView.this.getContext().getSystemService("input_method");
          if (localInputMethodManager.isFullscreenMode())
            localInputMethodManager.toggleSoftInput(0, 0);
        }
      }
    });
    this.mEditText.setOnKeyListener(new View.OnKeyListener()
    {
      public final boolean onKey(View paramAnonymousView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        boolean bool1 = true;
        InputMethodManager localInputMethodManager;
        boolean bool2;
        if (paramAnonymousKeyEvent.getAction() == 0)
        {
          localInputMethodManager = (InputMethodManager)TypeableAudienceView.this.getContext().getSystemService("input_method");
          bool2 = localInputMethodManager.isFullscreenMode();
        }
        switch (paramAnonymousInt)
        {
        default:
          bool1 = false;
        case 67:
        case 66:
        }
        while (true)
        {
          return bool1;
          if ((TypeableAudienceView.this.mEditText.getSelectionStart() > 0) || (TypeableAudienceView.this.mEditText.getSelectionEnd() > 0) || (bool2))
            break;
          TypeableAudienceView.this.removeLastChip();
          continue;
          if (!bool2)
            break;
          localInputMethodManager.toggleSoftInput(0, 0);
        }
      }
    });
    this.mEditText.setAudienceTextViewListener(new AudienceTextViewListener()
    {
      public final void onDeleteFromBeginning(TypeableAudienceView.AudienceTextView paramAnonymousAudienceTextView)
      {
        if (paramAnonymousAudienceTextView == TypeableAudienceView.this.mEditText)
          TypeableAudienceView.this.removeLastChip();
      }
    });
    this.mEditText.addTextChangedListener(this);
    this.mEditText.setImeOptions(1);
    setEmptyAudienceHint(0);
  }

  public void onClick(View paramView)
  {
    Context localContext = getContext();
    OzViews localOzViews = OzViews.getViewForLogging(localContext);
    EsAnalytics.recordActionEvent(localContext, this.mAccount, OzActions.PLATFORM_AUDIENCE_VIEW_CLICKED, localOzViews);
    if (this.mChipContainer.indexOfChild(paramView) == -1)
    {
      assert (paramView == this.mChipContainer);
      this.mEditText.requestFocus();
      SoftInput.show(this.mEditText);
    }
    while (true)
    {
      return;
      super.onClick(paramView);
    }
  }

  public void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if ((this.mChipContainer instanceof MultiLineLayout))
    {
      MultiLineLayout localMultiLineLayout = (MultiLineLayout)this.mChipContainer;
      int i = localMultiLineLayout.getNumLines();
      if ((this.mMaxLines == -1) || (i < this.mMaxLines))
        break label87;
      this.mScrollView.getLayoutParams().height = localMultiLineLayout.getHeightForNumLines(this.mMaxLines);
      this.mScrollView.scrollTo(0, localMultiLineLayout.getMeasuredHeight());
    }
    while (true)
    {
      return;
      label87: if (this.mScrollView.getLayoutParams().height != -2)
        this.mScrollView.getLayoutParams().height = -2;
    }
  }

  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    this.mEditText.setText(localSavedState.text);
    this.mEditText.setSelection(localSavedState.selectionStart, localSavedState.selectionEnd);
  }

  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.text = this.mEditText.getText().toString();
    localSavedState.selectionStart = this.mEditText.getSelectionStart();
    localSavedState.selectionEnd = this.mEditText.getSelectionEnd();
    return localSavedState;
  }

  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    this.mEdited = true;
  }

  public void setAutoCompleteAdapter(PeopleSearchListAdapter paramPeopleSearchListAdapter)
  {
    this.mEditText.setAdapter(paramPeopleSearchListAdapter);
  }

  public void setEmptyAudienceHint(int paramInt)
  {
    this.mEmptyAudienceHint = paramInt;
    updateEditTextHint();
  }

  protected final void update()
  {
    super.update();
    updateEditTextHint();
  }

  public static class AudienceTextView extends AutoCompleteTextView
  {
    private TypeableAudienceView.AudienceTextViewListener mListener;

    public AudienceTextView(Context paramContext)
    {
      super();
    }

    public AudienceTextView(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }

    public AudienceTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
      super(paramAttributeSet, paramInt);
    }

    public boolean onCheckIsTextEditor()
    {
      return true;
    }

    public InputConnection onCreateInputConnection(EditorInfo paramEditorInfo)
    {
      AudienceInputConnection localAudienceInputConnection = new AudienceInputConnection(super.onCreateInputConnection(paramEditorInfo), true);
      localAudienceInputConnection.setAudienceTextView(this);
      return localAudienceInputConnection;
    }

    public void setAudienceTextViewListener(TypeableAudienceView.AudienceTextViewListener paramAudienceTextViewListener)
    {
      this.mListener = paramAudienceTextViewListener;
    }

    public final class AudienceInputConnection extends InputConnectionWrapper
    {
      private TypeableAudienceView.AudienceTextView mAudienceTextView;

      public AudienceInputConnection(InputConnection paramBoolean, boolean arg3)
      {
        super(true);
      }

      public final boolean deleteSurroundingText(int paramInt1, int paramInt2)
      {
        int i = TypeableAudienceView.AudienceTextView.this.getSelectionStart();
        int j = TypeableAudienceView.AudienceTextView.this.getSelectionEnd();
        if ((paramInt1 > 0) && (paramInt2 <= 0) && (i <= 0) && (j <= 0) && (TypeableAudienceView.AudienceTextView.this.mListener != null) && (this.mAudienceTextView != null))
          TypeableAudienceView.AudienceTextView.this.mListener.onDeleteFromBeginning(this.mAudienceTextView);
        for (boolean bool = true; ; bool = super.deleteSurroundingText(paramInt1, paramInt2))
          return bool;
      }

      public final void setAudienceTextView(TypeableAudienceView.AudienceTextView paramAudienceTextView)
      {
        this.mAudienceTextView = paramAudienceTextView;
      }
    }
  }

  public static abstract interface AudienceTextViewListener
  {
    public abstract void onDeleteFromBeginning(TypeableAudienceView.AudienceTextView paramAudienceTextView);
  }

  public static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
    };
    public int selectionEnd;
    public int selectionStart;
    public String text;

    private SavedState(Parcel paramParcel)
    {
      super();
      this.text = paramParcel.readString();
      this.selectionStart = paramParcel.readInt();
      this.selectionEnd = paramParcel.readInt();
    }

    public SavedState(Parcelable paramParcelable)
    {
      super();
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeString(this.text);
      paramParcel.writeInt(this.selectionStart);
      paramParcel.writeInt(this.selectionEnd);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.TypeableAudienceView
 * JD-Core Version:    0.6.2
 */