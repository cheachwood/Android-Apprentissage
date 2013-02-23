package com.google.android.apps.plus.oob;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.google.api.services.plusi.model.OutOfBoxField;
import com.google.api.services.plusi.model.OutOfBoxFieldValue;
import com.google.api.services.plusi.model.OutOfBoxInputField;

public class CheckboxFieldLayout extends BaseFieldLayout
  implements CompoundButton.OnCheckedChangeListener
{
  private CheckBox mInput;

  public CheckboxFieldLayout(Context paramContext)
  {
    super(paramContext);
  }

  public CheckboxFieldLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public CheckboxFieldLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public final void bindToField(OutOfBoxField paramOutOfBoxField, int paramInt, ActionCallback paramActionCallback)
  {
    super.bindToField(paramOutOfBoxField, paramInt, paramActionCallback);
    this.mInput = ((CheckBox)getInputView());
    OutOfBoxInputField localOutOfBoxInputField = getField().input;
    this.mInput.setText(localOutOfBoxInputField.label);
    Boolean localBoolean = getServerBooleanValue();
    if (localBoolean != null)
      this.mInput.setChecked(localBoolean.booleanValue());
    if (paramActionCallback != null)
      this.mInput.setOnCheckedChangeListener(this);
  }

  public final boolean isEmpty()
  {
    if ((this.mField.input.mandatory != null) && (this.mField.input.mandatory.booleanValue()) && (!this.mInput.isChecked()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final OutOfBoxInputField newFieldFromInput()
  {
    OutOfBoxInputField localOutOfBoxInputField = OutOfBoxMessages.copyWithoutValue(getField().input);
    localOutOfBoxInputField.value = new OutOfBoxFieldValue();
    localOutOfBoxInputField.value.boolValue = Boolean.valueOf(this.mInput.isChecked());
    return localOutOfBoxInputField;
  }

  public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
  {
    this.mActionCallback.onInputChanged$7c32a9fe();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.oob.CheckboxFieldLayout
 * JD-Core Version:    0.6.2
 */