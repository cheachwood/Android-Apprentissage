package com.google.android.apps.plus.oob;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.google.api.services.plusi.model.OutOfBoxAction;
import com.google.api.services.plusi.model.OutOfBoxField;
import com.google.api.services.plusi.model.OutOfBoxInputField;

public class ButtonFieldLayout extends BaseFieldLayout
  implements View.OnClickListener
{
  private OutOfBoxAction mAction;
  private Button mInput;

  public ButtonFieldLayout(Context paramContext)
  {
    super(paramContext);
  }

  public ButtonFieldLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public ButtonFieldLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public final void bindToField(OutOfBoxField paramOutOfBoxField, int paramInt, ActionCallback paramActionCallback)
  {
    super.bindToField(paramOutOfBoxField, paramInt, paramActionCallback);
    this.mAction = getField().action;
    this.mInput = ((Button)getInputView());
    if (this.mAction.text != null)
      this.mInput.setText(this.mAction.text);
    this.mInput.setOnClickListener(this);
  }

  public final boolean isEmpty()
  {
    return false;
  }

  public final OutOfBoxInputField newFieldFromInput()
  {
    throw new UnsupportedOperationException();
  }

  public void onClick(View paramView)
  {
    this.mActionCallback.onAction(this.mAction);
  }

  public void setActionEnabled(boolean paramBoolean)
  {
    this.mInput.setEnabled(paramBoolean);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.oob.ButtonFieldLayout
 * JD-Core Version:    0.6.2
 */