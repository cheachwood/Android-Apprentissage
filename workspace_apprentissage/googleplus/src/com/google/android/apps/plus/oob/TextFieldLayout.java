package com.google.android.apps.plus.oob;

import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import com.google.android.apps.plus.R.color;
import com.google.api.services.plusi.model.OutOfBoxField;
import com.google.api.services.plusi.model.OutOfBoxInputField;
import com.google.api.services.plusi.model.OutOfBoxTextField;

public class TextFieldLayout extends BaseFieldLayout
  implements ActionTagHandler.Callback
{
  public TextFieldLayout(Context paramContext)
  {
    super(paramContext);
  }

  public TextFieldLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public TextFieldLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public final void bindToField(OutOfBoxField paramOutOfBoxField, int paramInt, ActionCallback paramActionCallback)
  {
    super.bindToField(paramOutOfBoxField, paramInt, paramActionCallback);
    TextView localTextView = getLabelView();
    String str = getField().text.text;
    localTextView.setText(Html.fromHtml(str, null, new ActionTagHandler(ActionTagHandler.findActionIds(str), getContext().getResources().getColor(R.color.signup_action_link_color), this)), TextView.BufferType.SPANNABLE);
    localTextView.setMovementMethod(LinkMovementMethod.getInstance());
    localTextView.setFocusable(false);
  }

  public final boolean isEmpty()
  {
    return false;
  }

  public final OutOfBoxInputField newFieldFromInput()
  {
    throw new UnsupportedOperationException();
  }

  public final void onActionId(String paramString)
  {
    if (this.mActionCallback != null)
      this.mActionCallback.onActionId(paramString);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.oob.TextFieldLayout
 * JD-Core Version:    0.6.2
 */