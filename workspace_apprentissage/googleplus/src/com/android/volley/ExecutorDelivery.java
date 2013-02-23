package com.android.volley;

import android.os.Handler;
import java.util.concurrent.Executor;

public final class ExecutorDelivery
  implements ResponseDelivery
{
  private final Executor mResponsePoster;

  public ExecutorDelivery(final Handler paramHandler)
  {
    this.mResponsePoster = new Executor()
    {
      public final void execute(Runnable paramAnonymousRunnable)
      {
        paramHandler.post(paramAnonymousRunnable);
      }
    };
  }

  public final void postError(Request<?> paramRequest, VolleyError paramVolleyError)
  {
    paramRequest.addMarker("post-error");
    Response localResponse = Response.error(paramVolleyError);
    this.mResponsePoster.execute(new ResponseDeliveryRunnable(paramRequest, localResponse, null));
  }

  public final void postResponse(Request<?> paramRequest, Response<?> paramResponse)
  {
    paramRequest.markDelivered();
    paramRequest.addMarker("post-response");
    this.mResponsePoster.execute(new ResponseDeliveryRunnable(paramRequest, paramResponse, null));
  }

  private final class ResponseDeliveryRunnable
    implements Runnable
  {
    private final Request mRequest;
    private final Response mResponse;
    private final Runnable mRunnable;

    public ResponseDeliveryRunnable(Request paramResponse, Response paramRunnable, Runnable arg4)
    {
      this.mRequest = paramResponse;
      this.mResponse = paramRunnable;
      Object localObject;
      this.mRunnable = localObject;
    }

    public final void run()
    {
      if (this.mRequest.isCanceled())
        this.mRequest.finish("canceled-at-delivery");
      label32: label50: label93: label110: label120: 
      while (true)
      {
        return;
        int i;
        if (this.mResponse.error == null)
        {
          i = 1;
          if (i == 0)
            break label93;
          this.mRequest.deliverResponse(this.mResponse.result);
          if (!this.mResponse.intermediate)
            break label110;
          this.mRequest.addMarker("intermediate-response");
        }
        while (true)
        {
          if (this.mRunnable == null)
            break label120;
          this.mRunnable.run();
          break;
          i = 0;
          break label32;
          this.mRequest.deliverError(this.mResponse.error);
          break label50;
          this.mRequest.finish("done");
        }
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.volley.ExecutorDelivery
 * JD-Core Version:    0.6.2
 */