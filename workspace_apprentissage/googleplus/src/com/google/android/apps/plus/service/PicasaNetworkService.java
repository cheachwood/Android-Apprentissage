package com.google.android.apps.plus.service;

import android.app.IntentService;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.content.EsNetworkData;
import com.google.android.apps.plus.network.HttpTransactionMetrics;
import org.apache.http.impl.HttpConnectionMetricsImpl;
import org.apache.http.impl.io.HttpTransportMetricsImpl;

public class PicasaNetworkService extends IntentService
{
  public PicasaNetworkService()
  {
    super("PicasaNetworkService");
  }

  protected void onHandleIntent(Intent paramIntent)
  {
    String str = paramIntent.getStringExtra("op_name");
    long l1 = paramIntent.getLongExtra("total_time", 0L);
    long l2 = paramIntent.getLongExtra("net_duration", 0L);
    long l3 = paramIntent.getLongExtra("sent_bytes", 0L);
    long l4 = paramIntent.getLongExtra("received_bytes", 0L);
    int i = paramIntent.getIntExtra("transaction_count", 1);
    HttpTransactionMetrics localHttpTransactionMetrics = new HttpTransactionMetrics();
    HttpTransportMetricsImpl localHttpTransportMetricsImpl1 = new HttpTransportMetricsImpl();
    localHttpTransportMetricsImpl1.setBytesTransferred(l4);
    HttpTransportMetricsImpl localHttpTransportMetricsImpl2 = new HttpTransportMetricsImpl();
    localHttpTransportMetricsImpl2.setBytesTransferred(l3);
    HttpConnectionMetricsImpl localHttpConnectionMetricsImpl = new HttpConnectionMetricsImpl(localHttpTransportMetricsImpl1, localHttpTransportMetricsImpl2);
    for (int j = 0; j < i; j++)
    {
      localHttpConnectionMetricsImpl.incrementRequestCount();
      localHttpConnectionMetricsImpl.incrementResponseCount();
    }
    localHttpTransactionMetrics.onBeginTransaction(str);
    localHttpTransactionMetrics.setConnectionMetrics(localHttpConnectionMetricsImpl);
    localHttpTransactionMetrics.onEndTransaction(l1, l1 - l2);
    EsNetworkData.insertData(this, EsAccountsData.getActiveAccount(this), localHttpTransactionMetrics, null);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.PicasaNetworkService
 * JD-Core Version:    0.6.2
 */