package com.google.android.apps.plus.oob;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.views.ActionButton;
import com.google.android.apps.plus.views.BottomActionBar;
import com.google.api.services.plusi.model.OutOfBoxAction;
import com.google.api.services.plusi.model.OutOfBoxField;
import com.google.api.services.plusi.model.OutOfBoxInputField;
import com.google.api.services.plusi.model.OutOfBoxView;
import java.util.List;

public final class OutOfBoxInflater
{
  private final BottomActionBar mBottomActionBar;
  private final LayoutInflater mInflater;
  private final ViewGroup mOuterLayout;
  private final ViewGroup mViewGroup;

  public OutOfBoxInflater(ViewGroup paramViewGroup1, ViewGroup paramViewGroup2, BottomActionBar paramBottomActionBar)
  {
    this.mOuterLayout = paramViewGroup1;
    this.mViewGroup = paramViewGroup2;
    this.mBottomActionBar = paramBottomActionBar;
    this.mInflater = LayoutInflater.from(paramViewGroup2.getContext());
  }

  public final void inflateFromResponse(OutOfBoxView paramOutOfBoxView, final ActionCallback paramActionCallback)
  {
    this.mViewGroup.removeAllViews();
    if (paramOutOfBoxView.title != null)
      ((TextView)this.mOuterLayout.findViewById(R.id.info_title)).setText(paramOutOfBoxView.title);
    if (paramOutOfBoxView.header != null)
      ((TextView)this.mOuterLayout.findViewById(R.id.info_header)).setText(paramOutOfBoxView.header);
    EditText localEditText = null;
    int i = R.id.oob_item_0;
    List localList = paramOutOfBoxView.field;
    int j = localList.size();
    int k = j;
    for (int m = j - 1; (m >= 0) && (((OutOfBoxField)localList.get(m)).action != null); m--)
      k--;
    int n = 0;
    if (n < k)
    {
      OutOfBoxField localOutOfBoxField = (OutOfBoxField)localList.get(n);
      OutOfBoxInputField localOutOfBoxInputField;
      int i5;
      label182: BaseFieldLayout localBaseFieldLayout;
      label209: int i4;
      if (localOutOfBoxField.input != null)
      {
        localOutOfBoxInputField = localOutOfBoxField.input;
        if ("TEXT_INPUT".equals(localOutOfBoxInputField.type))
        {
          i5 = R.layout.signup_text_input;
          LayoutInflater localLayoutInflater = this.mInflater;
          ViewGroup localViewGroup = this.mViewGroup;
          localBaseFieldLayout = (BaseFieldLayout)localLayoutInflater.inflate(i5, localViewGroup, false);
          if ((localOutOfBoxField.text == null) && (localOutOfBoxField.action == null))
            break label565;
          i4 = 1;
          label228: if (i4 == 0)
            break label610;
          localBaseFieldLayout.bindToField(localOutOfBoxField, i, paramActionCallback);
        }
      }
      while (true)
      {
        localBaseFieldLayout.setId(i + localBaseFieldLayout.getChildCount());
        this.mViewGroup.addView(localBaseFieldLayout);
        i = 1 + localBaseFieldLayout.getId();
        View localView = localBaseFieldLayout.getInputView();
        if ("TEXT_INPUT".equals(localOutOfBoxField.input))
        {
          localEditText = (EditText)localView;
          localEditText.setImeOptions(5);
        }
        n++;
        break;
        if ("DROPDOWN".equals(localOutOfBoxInputField.type))
        {
          i5 = R.layout.signup_spinner_input;
          break label182;
        }
        if ("CHECKBOX".equals(localOutOfBoxInputField.type))
        {
          i5 = R.layout.signup_checkbox_input;
          break label182;
        }
        if ("HIDDEN".equals(localOutOfBoxInputField.type))
        {
          i5 = R.layout.signup_hidden_input;
          break label182;
        }
        if ("BIRTHDAY_INPUT".equals(localOutOfBoxInputField.type))
        {
          i5 = R.layout.signup_birthday_input;
          break label182;
        }
        Log.e("OutOfBoxInflater", "Input field has unsupported type: " + localOutOfBoxInputField.type);
        localBaseFieldLayout = null;
        break label209;
        if (localOutOfBoxField.text != null)
        {
          localBaseFieldLayout = (BaseFieldLayout)this.mInflater.inflate(R.layout.signup_text_field, this.mViewGroup, false);
          break label209;
        }
        if (localOutOfBoxField.error != null)
        {
          localBaseFieldLayout = (BaseFieldLayout)this.mInflater.inflate(R.layout.signup_error_field, this.mViewGroup, false);
          break label209;
        }
        if (localOutOfBoxField.action != null)
        {
          localBaseFieldLayout = (BaseFieldLayout)this.mInflater.inflate(R.layout.signup_button_field, this.mViewGroup, false);
          break label209;
        }
        if (localOutOfBoxField.image != null)
        {
          localBaseFieldLayout = (BaseFieldLayout)this.mInflater.inflate(R.layout.signup_image_field, this.mViewGroup, false);
          break label209;
        }
        Log.e("OutOfBoxInflater", "Field doesn't have content.");
        localBaseFieldLayout = null;
        break label209;
        label565: if ((localOutOfBoxField.input != null) && (localOutOfBoxField.input.mandatory != null) && (localOutOfBoxField.input.mandatory.booleanValue()))
        {
          i4 = 1;
          break label228;
        }
        i4 = 0;
        break label228;
        label610: localBaseFieldLayout.bindToField(localOutOfBoxField, i, null);
      }
    }
    if (localEditText != null)
      localEditText.setImeOptions(6);
    this.mBottomActionBar.removeAllViews();
    int i1 = k;
    int i3;
    for (int i2 = i; i1 < j; i2 = i3)
    {
      final OutOfBoxAction localOutOfBoxAction = ((OutOfBoxField)localList.get(i1)).action;
      BottomActionBar localBottomActionBar = this.mBottomActionBar;
      i3 = i2 + 1;
      String str = localOutOfBoxAction.text;
      View.OnClickListener local1 = new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          paramActionCallback.onAction(localOutOfBoxAction);
        }
      };
      localBottomActionBar.addButton(i2, str, local1).setTag(localOutOfBoxAction);
      i1++;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.oob.OutOfBoxInflater
 * JD-Core Version:    0.6.2
 */