package android.support.v4.app;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import java.util.ArrayList;
import java.util.Iterator;

public final class TaskStackBuilder
  implements Iterable<Intent>
{
  private static final TaskStackBuilderImpl IMPL;
  private final ArrayList<Intent> mIntents = new ArrayList();
  private final Context mSourceContext;

  static
  {
    if (Build.VERSION.SDK_INT >= 11);
    for (IMPL = new TaskStackBuilderImplHoneycomb(); ; IMPL = new TaskStackBuilderImplBase())
      return;
  }

  private TaskStackBuilder(Context paramContext)
  {
    this.mSourceContext = paramContext;
  }

  public static TaskStackBuilder create(Context paramContext)
  {
    return new TaskStackBuilder(paramContext);
  }

  public final TaskStackBuilder addNextIntent(Intent paramIntent)
  {
    this.mIntents.add(paramIntent);
    return this;
  }

  public final PendingIntent getPendingIntent(int paramInt1, int paramInt2, Bundle paramBundle)
  {
    if (this.mIntents.isEmpty())
      throw new IllegalStateException("No intents added to TaskStackBuilder; cannot getPendingIntent");
    Intent[] arrayOfIntent = (Intent[])this.mIntents.toArray(new Intent[this.mIntents.size()]);
    arrayOfIntent[0].addFlags(268484608);
    return IMPL.getPendingIntent$3140f641(this.mSourceContext, arrayOfIntent, 0, 134217728);
  }

  public final Iterator<Intent> iterator()
  {
    return this.mIntents.iterator();
  }

  public final void startActivities()
  {
    if (this.mIntents.isEmpty())
      throw new IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities");
    Intent[] arrayOfIntent = (Intent[])this.mIntents.toArray(new Intent[this.mIntents.size()]);
    arrayOfIntent[0].addFlags(268484608);
    if (!ContextCompat.startActivities(this.mSourceContext, arrayOfIntent, null))
    {
      Intent localIntent = arrayOfIntent[(-1 + arrayOfIntent.length)];
      localIntent.addFlags(268435456);
      this.mSourceContext.startActivity(localIntent);
    }
  }

  static abstract interface TaskStackBuilderImpl
  {
    public abstract PendingIntent getPendingIntent$3140f641(Context paramContext, Intent[] paramArrayOfIntent, int paramInt1, int paramInt2);
  }

  static final class TaskStackBuilderImplBase
    implements TaskStackBuilder.TaskStackBuilderImpl
  {
    public final PendingIntent getPendingIntent$3140f641(Context paramContext, Intent[] paramArrayOfIntent, int paramInt1, int paramInt2)
    {
      Intent localIntent = paramArrayOfIntent[(-1 + paramArrayOfIntent.length)];
      localIntent.addFlags(268435456);
      return PendingIntent.getActivity(paramContext, paramInt1, localIntent, paramInt2);
    }
  }

  static final class TaskStackBuilderImplHoneycomb
    implements TaskStackBuilder.TaskStackBuilderImpl
  {
    public final PendingIntent getPendingIntent$3140f641(Context paramContext, Intent[] paramArrayOfIntent, int paramInt1, int paramInt2)
    {
      paramArrayOfIntent[0].addFlags(268468224);
      return PendingIntent.getActivities(paramContext, paramInt1, paramArrayOfIntent, paramInt2);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.app.TaskStackBuilder
 * JD-Core Version:    0.6.2
 */