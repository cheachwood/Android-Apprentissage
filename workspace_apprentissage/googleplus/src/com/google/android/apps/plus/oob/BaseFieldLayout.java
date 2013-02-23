package com.google.android.apps.plus.oob;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.style;
import com.google.api.services.plusi.model.MobileCoarseDate;
import com.google.api.services.plusi.model.OutOfBoxAction;
import com.google.api.services.plusi.model.OutOfBoxField;
import com.google.api.services.plusi.model.OutOfBoxFieldValue;
import com.google.api.services.plusi.model.OutOfBoxImageField;
import com.google.api.services.plusi.model.OutOfBoxInputField;

public abstract class BaseFieldLayout extends LinearLayout
{
  protected ActionCallback mActionCallback;
  protected OutOfBoxField mField;
  private int mInputId;
  private int mLabelId;

  public BaseFieldLayout(Context paramContext)
  {
    super(paramContext);
  }

  public BaseFieldLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public BaseFieldLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public void bindToField(OutOfBoxField paramOutOfBoxField, int paramInt, ActionCallback paramActionCallback)
  {
    View localView1 = findViewById(R.id.label);
    if (localView1 != null)
    {
      this.mLabelId = paramInt;
      localView1.setId(this.mLabelId);
      paramInt++;
    }
    View localView2 = findViewById(R.id.input);
    if (localView2 != null)
    {
      this.mInputId = paramInt;
      localView2.setId(this.mInputId);
    }
    this.mField = paramOutOfBoxField;
    this.mActionCallback = paramActionCallback;
    if ((this.mField.input != null) && (this.mField.input.hasError != null) && (this.mField.input.hasError.booleanValue()))
    {
      TextView localTextView = getLabelView();
      if ((localTextView != null) && ((localTextView instanceof TextView)))
        ((TextView)localTextView).setTextAppearance(getContext(), R.style.SignupErrorAppearance);
    }
  }

  public final String getActionType()
  {
    if (this.mField.action != null);
    for (String str = this.mField.action.type; ; str = null)
      return str;
  }

  public final OutOfBoxField getField()
  {
    return this.mField;
  }

  public final View getInputView()
  {
    return findViewById(this.mInputId);
  }

  public final TextView getLabelView()
  {
    return (TextView)findViewById(this.mLabelId);
  }

  public final Boolean getServerBooleanValue()
  {
    OutOfBoxFieldValue localOutOfBoxFieldValue = getServerValue();
    if (localOutOfBoxFieldValue != null);
    for (Boolean localBoolean = localOutOfBoxFieldValue.boolValue; ; localBoolean = null)
      return localBoolean;
  }

  public final MobileCoarseDate getServerDateValue()
  {
    OutOfBoxFieldValue localOutOfBoxFieldValue = getServerValue();
    if (localOutOfBoxFieldValue != null);
    for (MobileCoarseDate localMobileCoarseDate = localOutOfBoxFieldValue.dateValue; ; localMobileCoarseDate = null)
      return localMobileCoarseDate;
  }

  public final String getServerImageType()
  {
    if (this.mField.image != null);
    for (String str = this.mField.image.type; ; str = null)
      return str;
  }

  public final String getServerStringValue()
  {
    OutOfBoxFieldValue localOutOfBoxFieldValue = getServerValue();
    if (localOutOfBoxFieldValue != null);
    for (String str = localOutOfBoxFieldValue.stringValue; ; str = null)
      return str;
  }

  public final OutOfBoxFieldValue getServerValue()
  {
    if (this.mField.input != null);
    for (OutOfBoxFieldValue localOutOfBoxFieldValue = this.mField.input.value; ; localOutOfBoxFieldValue = null)
      return localOutOfBoxFieldValue;
  }

  public abstract boolean isEmpty();

  public abstract OutOfBoxInputField newFieldFromInput();

  public void setActionEnabled(boolean paramBoolean)
  {
  }

  public final boolean shouldPreventCompletionAction()
  {
    OutOfBoxInputField localOutOfBoxInputField = this.mField.input;
    boolean bool1 = false;
    if (localOutOfBoxInputField != null)
    {
      boolean bool2 = "HIDDEN".equals(this.mField.input.type);
      bool1 = false;
      if (!bool2)
        break label38;
    }
    while (true)
    {
      return bool1;
      label38: boolean bool3 = this.mField.input.mandatory.booleanValue();
      bool1 = false;
      if (bool3)
      {
        boolean bool4 = isEmpty();
        bool1 = false;
        if (bool4)
          bool1 = true;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.oob.BaseFieldLayout
 * JD-Core Version:    0.6.2
 */