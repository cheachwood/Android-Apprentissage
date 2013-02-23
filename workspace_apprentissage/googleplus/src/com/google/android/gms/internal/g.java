package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.PlusClient.b;
import com.google.android.gms.plus.PlusClient.c;

public class g extends ad
  implements View.OnClickListener, GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener
{
  static final Uri dm = e.a("plus_one_button_popup_beak_up");
  static final Uri dn = e.a("plus_one_button_popup_beak_down");
  static final Uri jdField_do = e.a("plus_one_button_popup_bg");
  private final Runnable dA = new v(this);
  private final PlusClient.b dB = new a();
  private final PlusClient.b dC = new w(this);
  private final PlusClient.c dD = new y(this);
  private final Display dp;
  private PopupWindow dq;
  private boolean dr;
  private final ImageView ds;
  private final ImageView dt;
  private av du;
  private View.OnClickListener dv;
  private boolean dw;
  private boolean dx;
  protected boolean dy;
  private final Context mContext;

  public g(Context paramContext)
  {
    this(paramContext, null);
  }

  public g(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
    this.dp = ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay();
    this.ds = new ImageView(this.mContext);
    this.ds.setImageURI(dm);
    this.dt = new ImageView(this.mContext);
    this.dt.setImageURI(jdField_do);
    this.cD.setOnClickListener(this);
    this.cF.setOnClickListener(this);
  }

  private void ac()
  {
    if ((this.dr) && (this.dq != null))
    {
      this.dq.dismiss();
      this.dq = null;
    }
  }

  private void ae()
  {
    if ((this.dr) && (this.dw))
    {
      this.dw = false;
      if (this.cO == null)
        break label68;
      String str = this.cO.am();
      if (str == null)
        break label68;
      View localView = m("plus_popup_text");
      ((TextView)localView.findViewWithTag("text")).setText(str);
      b(b(localView));
    }
    while (true)
    {
      return;
      label68: if (Log.isLoggable("PlusOneButtonWithPopup", 3))
        Log.d("PlusOneButtonWithPopup", "Text confirmation popup requested but text is null");
      setType(3);
    }
  }

  private FrameLayout b(View paramView)
  {
    FrameLayout localFrameLayout = new FrameLayout(this.mContext);
    localFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
    localFrameLayout.addView(paramView, new FrameLayout.LayoutParams(-2, -2));
    return localFrameLayout;
  }

  private void b(FrameLayout paramFrameLayout)
  {
    b localb = new b(paramFrameLayout);
    ImageView localImageView = new ImageView(this.mContext);
    if (localb.cq);
    for (Uri localUri = dn; ; localUri = dm)
    {
      localImageView.setImageURI(localUri);
      FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-2, -2, localb.cv);
      int i = localb.cr;
      localb.getClass();
      int j = localb.ct;
      localb.getClass();
      localLayoutParams.setMargins(i, 0, j, 0);
      paramFrameLayout.addView(localImageView, localLayoutParams);
      this.dq = new PopupWindow(paramFrameLayout, paramFrameLayout.getMeasuredWidth(), paramFrameLayout.getMeasuredHeight());
      this.dq.setOutsideTouchable(true);
      if (this.dr)
      {
        this.dq.showAtLocation(this, 51, localb.cw, localb.cx);
        removeCallbacks(this.dA);
        postDelayed(this.dA, 3000L);
      }
      return;
    }
  }

  private View m(String paramString)
  {
    int i = this.cP.getIdentifier(paramString, "layout", "com.google.android.gms");
    return this.cQ.inflate(this.cP.getLayout(i), null);
  }

  public final void R()
  {
    super.R();
    ac();
    ae();
  }

  public final void S()
  {
    super.S();
    ac();
    ae();
  }

  public final void T()
  {
    super.T();
    ac();
    String str1;
    String str2;
    String str3;
    if ((this.dr) && (this.dw))
    {
      this.dw = false;
      if ((this.cO == null) || (this.du == null))
        break label265;
      str1 = this.du.F();
      str2 = this.cO.am();
      str3 = this.du.getDisplayName();
      if ((str2 == null) || (str3 == null))
        break label291;
    }
    label265: label291: for (String str4 = String.format(str2, new Object[] { str3 }); ; str4 = str2)
    {
      String str5 = this.cO.bM.getString("visibility");
      if ((str1 != null) && (str4 != null) && (str5 != null))
      {
        View localView = m("plus_popup_confirmation");
        au localau = new au(this.mContext);
        localau.a(null);
        float f = TypedValue.applyDimension(1, 32.0F, getResources().getDisplayMetrics());
        String str6 = bh.a(Uri.parse(str1), Integer.toString((int)f)).toString();
        localau.b(new Uri.Builder().scheme("content").authority("com.google.android.gms.plus").appendPath("images").appendQueryParameter("url", str6).build());
        ((FrameLayout)localView.findViewWithTag("profile_image")).addView(localau, 0);
        ((TextView)localView.findViewWithTag("text")).setText(str4);
        b(b(localView));
      }
      while (true)
      {
        return;
        if (Log.isLoggable("PlusOneButtonWithPopup", 3))
          Log.d("PlusOneButtonWithPopup", "Confirmation popup requested but content view cannot be created");
        setType(3);
      }
    }
  }

  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.dr = true;
  }

  public void onClick(View paramView)
  {
    if (this.dx)
      if (Log.isLoggable("PlusOneButtonWithPopup", 2))
        Log.v("PlusOneButtonWithPopup", "onClick: result pending, ignoring +1 button click");
    label92: 
    while (true)
    {
      return;
      this.dw = true;
      if (this.cO == null)
        this.dw = false;
      while (true)
      {
        if (this.dv == null)
          break label92;
        this.dv.onClick(paramView);
        break;
        if ((this.cO.ah()) && (Log.isLoggable("PlusOneButtonWithPopup", 2)))
          Log.v("PlusOneButtonWithPopup", "onClick: undo +1");
      }
    }
  }

  public final void onConnected()
  {
    if (Log.isLoggable("PlusOneButtonWithPopup", 2))
      Log.v("PlusOneButtonWithPopup", "onConnected");
    if (this.du == null)
      null.a(this.dD);
  }

  public final void onConnectionFailed$5d4cef71()
  {
    R();
  }

  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.dr = false;
    if (this.dq != null)
    {
      this.dq.dismiss();
      this.dq = null;
    }
  }

  public final void onDisconnected()
  {
  }

  public void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    if (paramOnClickListener == this)
      super.setOnClickListener(this);
    while (true)
    {
      return;
      this.dv = paramOnClickListener;
    }
  }

  protected final class a extends ad.a
  {
    protected a()
    {
      super();
    }

    public final void a(ConnectionResult paramConnectionResult, bk parambk)
    {
      super.a(paramConnectionResult, parambk);
      g.a(g.this, false);
    }
  }

  private final class b
  {
    public final boolean cq;
    public final int cr;
    public final int cs = 0;
    public final int ct;
    public final int cu = 0;
    public final int cv;
    public final int cw;
    public final int cx;

    public b(FrameLayout arg2)
    {
      Object localObject;
      localObject.measure(0, 0);
      int[] arrayOfInt = new int[2];
      g.this.cC.getLocationOnScreen(arrayOfInt);
      int i = arrayOfInt[0];
      int j = arrayOfInt[bool1];
      int m = j + g.this.cC.getMeasuredHeight() - g.this.cC.getPaddingBottom();
      int n = m + localObject.getMeasuredHeight();
      int i1;
      boolean bool2;
      label112: int i2;
      label138: label167: int i3;
      if (Build.VERSION.SDK_INT >= 11)
      {
        i1 = 48;
        if (i1 + n <= g.d(g.this).getHeight())
          break label268;
        bool2 = bool1;
        this.cq = bool2;
        if (i + localObject.getMeasuredWidth() >= g.d(g.this).getWidth())
          break label274;
        i2 = bool1;
        if (i + localObject.getMeasuredWidth() / 2 + g.this.cC.getMeasuredWidth() / 2 >= g.d(g.this).getWidth())
          break label280;
        i3 = g.this.cC.getMeasuredWidth() / 2 - g.e(g.this).getDrawable().getIntrinsicWidth() / 2;
        if (i2 == 0)
          break label294;
        if (!this.cq)
          break label285;
        this.cv = 80;
        label209: this.cr = i3;
        this.ct = 0;
        if (i2 == 0)
          break label369;
        this.cw = i;
        label231: if (!this.cq)
          break label421;
      }
      label268: label274: label280: label285: label421: for (int i4 = j - localObject.getMeasuredHeight() - g.this.cC.getPaddingTop(); ; i4 = m)
      {
        this.cx = i4;
        return;
        i1 = 0;
        break;
        bool2 = false;
        break label112;
        i2 = 0;
        break label138;
        bool1 = false;
        break label167;
        this.cv = 48;
        break label209;
        label294: if (bool1)
        {
          if (this.cq);
          for (this.cv = 81; ; this.cv = 49)
          {
            this.cr = 0;
            this.ct = 0;
            break;
          }
        }
        if (this.cq);
        for (this.cv = 85; ; this.cv = 53)
        {
          this.cr = 0;
          this.ct = i3;
          break;
        }
        label369: if (bool1)
        {
          this.cw = (i - localObject.getMeasuredWidth() / 2 + g.this.cC.getMeasuredWidth() / 2);
          break label231;
        }
        this.cw = (i - localObject.getMeasuredWidth() + g.this.cC.getMeasuredWidth());
        break label231;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.g
 * JD-Core Version:    0.6.2
 */