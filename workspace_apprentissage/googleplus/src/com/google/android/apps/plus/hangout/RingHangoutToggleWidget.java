package com.google.android.apps.plus.hangout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.string;

public class RingHangoutToggleWidget extends LinearLayout
{
  private ImageView icon;
  private TextView label;
  private boolean ringInvitees = true;

  public RingHangoutToggleWidget(Context paramContext)
  {
    this(paramContext, null);
  }

  public RingHangoutToggleWidget(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setOrientation(0);
    this.icon = new ImageView(paramContext);
    this.icon.setImageResource(R.drawable.icn_ring_on);
    this.icon.setClickable(true);
    this.label = new TextView(paramContext);
    this.label.setText(R.string.ring_on_hangout_label);
    this.label.setPadding(20, 0, 0, 0);
    addView(this.icon);
    addView(this.label);
    setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        RingHangoutToggleWidget localRingHangoutToggleWidget = RingHangoutToggleWidget.this;
        if (!RingHangoutToggleWidget.this.getRingInvitees());
        for (boolean bool = true; ; bool = false)
        {
          localRingHangoutToggleWidget.setRingInvitees(bool);
          return;
        }
      }
    });
  }

  public final boolean getRingInvitees()
  {
    return this.ringInvitees;
  }

  public void setEnabled(boolean paramBoolean)
  {
    super.setEnabled(paramBoolean);
    if (paramBoolean)
    {
      setRingInvitees(true);
      this.label.setText(R.string.ring_on_hangout_label);
    }
    while (true)
    {
      return;
      setRingInvitees(false);
      this.label.setText(R.string.ring_disabled_hangout_label);
    }
  }

  public void setRingInvitees(boolean paramBoolean)
  {
    this.ringInvitees = paramBoolean;
    if (paramBoolean)
    {
      this.label.setText(R.string.ring_on_hangout_label);
      this.icon.setImageResource(R.drawable.icn_ring_on);
    }
    while (true)
    {
      return;
      this.label.setText(R.string.ring_off_hangout_label);
      this.icon.setImageResource(R.drawable.icn_ring_off);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.RingHangoutToggleWidget
 * JD-Core Version:    0.6.2
 */