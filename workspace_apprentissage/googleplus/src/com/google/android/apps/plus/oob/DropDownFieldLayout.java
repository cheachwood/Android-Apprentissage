package com.google.android.apps.plus.oob;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.api.services.plusi.model.OutOfBoxField;
import com.google.api.services.plusi.model.OutOfBoxFieldOption;
import com.google.api.services.plusi.model.OutOfBoxFieldValue;
import com.google.api.services.plusi.model.OutOfBoxInputField;

public class DropDownFieldLayout extends BaseFieldLayout
  implements AdapterView.OnItemSelectedListener
{
  private Spinner mInput;

  public DropDownFieldLayout(Context paramContext)
  {
    super(paramContext);
  }

  public DropDownFieldLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public DropDownFieldLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  private void dispatchOnInputChanged()
  {
    this.mActionCallback.onInputChanged$7c32a9fe();
  }

  public final void bindToField(OutOfBoxField paramOutOfBoxField, int paramInt, ActionCallback paramActionCallback)
  {
    super.bindToField(paramOutOfBoxField, paramInt, paramActionCallback);
    OutOfBoxInputField localOutOfBoxInputField = getField().input;
    getLabelView().setText(localOutOfBoxInputField.label);
    this.mInput = ((Spinner)getInputView());
    this.mInput.setPrompt(localOutOfBoxInputField.label);
    DropDownFieldAdapter localDropDownFieldAdapter = new DropDownFieldAdapter(localOutOfBoxInputField.valueOption);
    this.mInput.setAdapter(localDropDownFieldAdapter);
    OutOfBoxFieldValue localOutOfBoxFieldValue = getServerValue();
    if (localOutOfBoxFieldValue != null);
    for (int i = localDropDownFieldAdapter.indexOf(localOutOfBoxFieldValue); ; i = -1)
    {
      if (i != -1)
        this.mInput.setSelection(i);
      if (paramActionCallback != null)
        this.mInput.setOnItemSelectedListener(this);
      return;
    }
  }

  public final boolean isEmpty()
  {
    if (this.mInput.getSelectedItem() == null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final OutOfBoxInputField newFieldFromInput()
  {
    OutOfBoxInputField localOutOfBoxInputField = OutOfBoxMessages.copyWithoutValue(getField().input);
    OutOfBoxFieldOption localOutOfBoxFieldOption = (OutOfBoxFieldOption)this.mInput.getSelectedItem();
    if (localOutOfBoxFieldOption != null)
      localOutOfBoxInputField.value = localOutOfBoxFieldOption.value;
    return localOutOfBoxInputField;
  }

  public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    dispatchOnInputChanged();
  }

  public void onNothingSelected(AdapterView<?> paramAdapterView)
  {
    dispatchOnInputChanged();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.oob.DropDownFieldLayout
 * JD-Core Version:    0.6.2
 */