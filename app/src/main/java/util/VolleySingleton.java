package util;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/** Singleton class to get volley request queue */
public class VolleySingleton {
    private static VolleySingleton m_instance;
    private RequestQueue m_requestQueue;
    private static Context m_ctx;

    private VolleySingleton(Context context) {
        m_ctx = context;
        m_requestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (m_instance == null) {
            m_instance = new VolleySingleton(context);
        }
        return m_instance;
    }

    public RequestQueue getRequestQueue() {
        if (m_requestQueue == null) {
            m_requestQueue = Volley.newRequestQueue(m_ctx.getApplicationContext());
        }
        return m_requestQueue;
    }

}
