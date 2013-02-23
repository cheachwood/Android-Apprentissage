package android.support.v13.dreams;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.view.Window;

public class BasicDream extends Activity
{
  private boolean mPlugged = false;
  private final BroadcastReceiver mPowerIntentReceiver = new BroadcastReceiver()
  {
    public final void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      int i = 1;
      String str;
      if ("android.intent.action.BATTERY_CHANGED".equals(paramAnonymousIntent.getAction()))
      {
        if (i != paramAnonymousIntent.getIntExtra("plugged", 0))
          break label104;
        if (i != BasicDream.this.mPlugged)
        {
          StringBuilder localStringBuilder = new StringBuilder("now ");
          if (i == 0)
            break label109;
          str = "plugged in";
          label55: Log.d("BasicDream", str);
          BasicDream.access$002(BasicDream.this, i);
          if (!BasicDream.this.mPlugged)
            break label116;
          BasicDream.this.getWindow().addFlags(128);
        }
      }
      while (true)
      {
        return;
        label104: int j = 0;
        break;
        label109: str = "unplugged";
        break label55;
        label116: BasicDream.this.getWindow().clearFlags(128);
      }
    }
  };
  private View mView;

  public static void onDraw$262b7b90()
  {
  }

  public void onPause()
  {
    super.onPause();
    Log.d("BasicDream", "exiting onPause");
    finish();
  }

  public void onStart()
  {
    super.onStart();
    setContentView(new BasicDreamView(this));
    getWindow().addFlags(524289);
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.BATTERY_CHANGED");
    registerReceiver(this.mPowerIntentReceiver, localIntentFilter);
  }

  public void onStop()
  {
    super.onStop();
    unregisterReceiver(this.mPowerIntentReceiver);
  }

  public void onUserInteraction()
  {
    Log.d("BasicDream", "exiting onUserInteraction");
    finish();
  }

  public void setContentView(View paramView)
  {
    super.setContentView(paramView);
    this.mView = paramView;
  }

  class BasicDreamView extends View
  {
    public BasicDreamView(Context arg2)
    {
      super();
    }

    public void onAttachedToWindow()
    {
      setSystemUiVisibility(1);
    }

    public void onDraw(Canvas paramCanvas)
    {
      BasicDream.onDraw$262b7b90();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v13.dreams.BasicDream
 * JD-Core Version:    0.6.2
 */