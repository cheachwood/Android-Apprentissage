package com.google.android.apps.plus.hangout.multiwaveview;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import java.util.ArrayList;
import java.util.HashMap;

final class Tweener
{
  private static Animator.AnimatorListener mCleanupListener = new AnimatorListenerAdapter()
  {
    public final void onAnimationCancel(Animator paramAnonymousAnimator)
    {
      Tweener.access$000(paramAnonymousAnimator);
    }

    public final void onAnimationEnd(Animator paramAnonymousAnimator)
    {
      Tweener.access$000(paramAnonymousAnimator);
    }
  };
  private static HashMap<Object, Tweener> sTweens = new HashMap();
  ObjectAnimator animator;

  private Tweener(ObjectAnimator paramObjectAnimator)
  {
    this.animator = paramObjectAnimator;
  }

  private static void replace(ArrayList<PropertyValuesHolder> paramArrayList, Object[] paramArrayOfObject)
  {
    int i = paramArrayOfObject.length;
    int j = 0;
    if (j < i)
    {
      Object localObject = paramArrayOfObject[j];
      Tweener localTweener = (Tweener)sTweens.get(localObject);
      if (localTweener != null)
      {
        localTweener.animator.cancel();
        if (paramArrayList == null)
          break label73;
        localTweener.animator.setValues((PropertyValuesHolder[])paramArrayList.toArray(new PropertyValuesHolder[paramArrayList.size()]));
      }
      while (true)
      {
        j++;
        break;
        label73: sTweens.remove(localTweener);
      }
    }
  }

  public static void reset()
  {
    sTweens.clear();
  }

  public static Tweener to(Object paramObject, long paramLong, Object[] paramArrayOfObject)
  {
    long l = 0L;
    ValueAnimator.AnimatorUpdateListener localAnimatorUpdateListener = null;
    Animator.AnimatorListener localAnimatorListener = null;
    TimeInterpolator localTimeInterpolator = null;
    ArrayList localArrayList = new ArrayList(paramArrayOfObject.length / 2);
    int i = 0;
    if (i < paramArrayOfObject.length)
    {
      if (!(paramArrayOfObject[i] instanceof String))
        throw new IllegalArgumentException("Key must be a string: " + paramArrayOfObject[i]);
      String str = (String)paramArrayOfObject[i];
      Object localObject = paramArrayOfObject[(i + 1)];
      if (!"simultaneousTween".equals(str))
      {
        if (!"ease".equals(str))
          break label122;
        localTimeInterpolator = (TimeInterpolator)localObject;
      }
      while (true)
      {
        i += 2;
        break;
        label122: if (("onUpdate".equals(str)) || ("onUpdateListener".equals(str)))
          localAnimatorUpdateListener = (ValueAnimator.AnimatorUpdateListener)localObject;
        else if (("onComplete".equals(str)) || ("onCompleteListener".equals(str)))
          localAnimatorListener = (Animator.AnimatorListener)localObject;
        else if ("delay".equals(str))
          l = ((Number)localObject).longValue();
        else if (!"syncWith".equals(str))
          if ((localObject instanceof float[]))
          {
            float[] arrayOfFloat = new float[2];
            arrayOfFloat[0] = ((float[])localObject)[0];
            arrayOfFloat[1] = ((float[])localObject)[1];
            localArrayList.add(PropertyValuesHolder.ofFloat(str, arrayOfFloat));
          }
          else
          {
            if (!(localObject instanceof Number))
              break label302;
            localArrayList.add(PropertyValuesHolder.ofFloat(str, new float[] { ((Number)localObject).floatValue() }));
          }
      }
      label302: throw new IllegalArgumentException("Bad argument for key \"" + str + "\" with value " + localObject.getClass());
    }
    Tweener localTweener = (Tweener)sTweens.get(paramObject);
    ObjectAnimator localObjectAnimator;
    if (localTweener == null)
    {
      localObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(paramObject, (PropertyValuesHolder[])localArrayList.toArray(new PropertyValuesHolder[localArrayList.size()]));
      localTweener = new Tweener(localObjectAnimator);
      sTweens.put(paramObject, localTweener);
    }
    while (true)
    {
      if (localTimeInterpolator != null)
        localObjectAnimator.setInterpolator(localTimeInterpolator);
      localObjectAnimator.setStartDelay(l);
      localObjectAnimator.setDuration(paramLong);
      if (localAnimatorUpdateListener != null)
      {
        localObjectAnimator.removeAllUpdateListeners();
        localObjectAnimator.addUpdateListener(localAnimatorUpdateListener);
      }
      if (localAnimatorListener != null)
      {
        localObjectAnimator.removeAllListeners();
        localObjectAnimator.addListener(localAnimatorListener);
      }
      localObjectAnimator.addListener(mCleanupListener);
      localObjectAnimator.start();
      return localTweener;
      localObjectAnimator = ((Tweener)sTweens.get(paramObject)).animator;
      replace(localArrayList, new Object[] { paramObject });
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.multiwaveview.Tweener
 * JD-Core Version:    0.6.2
 */