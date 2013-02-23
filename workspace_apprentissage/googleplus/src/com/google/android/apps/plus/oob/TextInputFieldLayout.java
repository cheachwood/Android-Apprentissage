package com.google.android.apps.plus.oob;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.TextView;
import com.google.api.services.plusi.model.OutOfBoxField;
import com.google.api.services.plusi.model.OutOfBoxFieldValue;
import com.google.api.services.plusi.model.OutOfBoxInputField;

public class TextInputFieldLayout extends BaseFieldLayout
{
  private TextView mInput;

  public TextInputFieldLayout(Context paramContext)
  {
    super(paramContext);
  }

  public TextInputFieldLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public TextInputFieldLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public final void bindToField(OutOfBoxField paramOutOfBoxField, int paramInt, ActionCallback paramActionCallback)
  {
    super.bindToField(paramOutOfBoxField, paramInt, paramActionCallback);
    OutOfBoxInputField localOutOfBoxInputField = getField().input;
    getLabelView().setText(localOutOfBoxInputField.label);
    this.mInput = ((TextView)getInputView());
    String str = getServerStringValue();
    if (!TextUtils.isEmpty(str))
      this.mInput.setText(str);
    if (paramActionCallback != null)
      this.mInput.addTextChangedListener(new TextWatcher()
      {
        public final void afterTextChanged(Editable paramAnonymousEditable)
        {
        }

        public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
        {
        }

        public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
        {
          ActionCallback localActionCallback = TextInputFieldLayout.this.mActionCallback;
          localActionCallback.onInputChanged$7c32a9fe();
        }
      });
  }

  public final boolean isEmpty()
  {
    if ((this.mInput.getText() == null) || (TextUtils.isEmpty(this.mInput.getText().toString().trim())));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final OutOfBoxInputField newFieldFromInput()
  {
    OutOfBoxInputField localOutOfBoxInputField = OutOfBoxMessages.copyWithoutValue(getField().input);
    localOutOfBoxInputField.value = new OutOfBoxFieldValue();
    localOutOfBoxInputField.value.stringValue = this.mInput.getText().toString();
    return localOutOfBoxInputField;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.oob.TextInputFieldLayout
 * JD-Core Version:    0.6.2
 */