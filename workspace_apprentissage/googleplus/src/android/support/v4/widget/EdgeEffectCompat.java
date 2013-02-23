package android.support.v4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.widget.EdgeEffect;

public final class EdgeEffectCompat
{
  private static final EdgeEffectImpl IMPL;
  private Object mEdgeEffect;

  static
  {
    if (Build.VERSION.SDK_INT >= 14);
    for (IMPL = new EdgeEffectIcsImpl(); ; IMPL = new BaseEdgeEffectImpl())
      return;
  }

  public EdgeEffectCompat(Context paramContext)
  {
    this.mEdgeEffect = IMPL.newEdgeEffect(paramContext);
  }

  public final boolean draw(Canvas paramCanvas)
  {
    return IMPL.draw(this.mEdgeEffect, paramCanvas);
  }

  public final void finish()
  {
    IMPL.finish(this.mEdgeEffect);
  }

  public final boolean isFinished()
  {
    return IMPL.isFinished(this.mEdgeEffect);
  }

  public final boolean onAbsorb(int paramInt)
  {
    return IMPL.onAbsorb(this.mEdgeEffect, paramInt);
  }

  public final boolean onPull(float paramFloat)
  {
    return IMPL.onPull(this.mEdgeEffect, paramFloat);
  }

  public final boolean onRelease()
  {
    return IMPL.onRelease(this.mEdgeEffect);
  }

  public final void setSize(int paramInt1, int paramInt2)
  {
    IMPL.setSize(this.mEdgeEffect, paramInt1, paramInt2);
  }

  static final class BaseEdgeEffectImpl
    implements EdgeEffectCompat.EdgeEffectImpl
  {
    public final boolean draw(Object paramObject, Canvas paramCanvas)
    {
      return false;
    }

    public final void finish(Object paramObject)
    {
    }

    public final boolean isFinished(Object paramObject)
    {
      return true;
    }

    public final Object newEdgeEffect(Context paramContext)
    {
      return null;
    }

    public final boolean onAbsorb(Object paramObject, int paramInt)
    {
      return false;
    }

    public final boolean onPull(Object paramObject, float paramFloat)
    {
      return false;
    }

    public final boolean onRelease(Object paramObject)
    {
      return false;
    }

    public final void setSize(Object paramObject, int paramInt1, int paramInt2)
    {
    }
  }

  static final class EdgeEffectIcsImpl
    implements EdgeEffectCompat.EdgeEffectImpl
  {
    public final boolean draw(Object paramObject, Canvas paramCanvas)
    {
      return ((EdgeEffect)paramObject).draw(paramCanvas);
    }

    public final void finish(Object paramObject)
    {
      ((EdgeEffect)paramObject).finish();
    }

    public final boolean isFinished(Object paramObject)
    {
      return ((EdgeEffect)paramObject).isFinished();
    }

    public final Object newEdgeEffect(Context paramContext)
    {
      return new EdgeEffect(paramContext);
    }

    public final boolean onAbsorb(Object paramObject, int paramInt)
    {
      ((EdgeEffect)paramObject).onAbsorb(paramInt);
      return true;
    }

    public final boolean onPull(Object paramObject, float paramFloat)
    {
      ((EdgeEffect)paramObject).onPull(paramFloat);
      return true;
    }

    public final boolean onRelease(Object paramObject)
    {
      EdgeEffect localEdgeEffect = (EdgeEffect)paramObject;
      localEdgeEffect.onRelease();
      return localEdgeEffect.isFinished();
    }

    public final void setSize(Object paramObject, int paramInt1, int paramInt2)
    {
      ((EdgeEffect)paramObject).setSize(paramInt1, paramInt2);
    }
  }

  static abstract interface EdgeEffectImpl
  {
    public abstract boolean draw(Object paramObject, Canvas paramCanvas);

    public abstract void finish(Object paramObject);

    public abstract boolean isFinished(Object paramObject);

    public abstract Object newEdgeEffect(Context paramContext);

    public abstract boolean onAbsorb(Object paramObject, int paramInt);

    public abstract boolean onPull(Object paramObject, float paramFloat);

    public abstract boolean onRelease(Object paramObject);

    public abstract void setSize(Object paramObject, int paramInt1, int paramInt2);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.widget.EdgeEffectCompat
 * JD-Core Version:    0.6.2
 */