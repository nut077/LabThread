package labthread.freedomseven.com.labthread.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import labthread.freedomseven.com.labthread.R;
import labthread.freedomseven.com.labthread.databinding.FragmentMainBinding;


public class MainFragment extends Fragment {

    FragmentMainBinding binding;
    private int counter;

    Thread thread;
    Handler handler;

    HandlerThread backgroundHandlerThread;
    Handler backgroundHandler;
    Handler mainHandler;
    SampleSsyncTAsk sampleSsyncTAsk;

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        initInstances(savedInstanceState);
        return binding.getRoot();
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(Bundle savedInstanceState) {
        counter = 0;
        /*
        --- thread 1 ---
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    counter++;
                    try {
                        thread.sleep(1000);
                    } catch (InterruptedException e) {
                        return;
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.tvCounter.setText(String.valueOf(counter));
                        }
                    });
                }
            }
        });
        thread.start();*/

        // --- thread 2 ---
        /*
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                binding.tvCounter.setText(String.valueOf(msg.arg1));
            }
        };
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    counter++;
                    try {
                        thread.sleep(1000);
                    } catch (InterruptedException e) {
                        return;
                    }

                    Message message = new Message();
                    message.arg1 = counter;
                    handler.sendMessage(message);
                }
            }
        });
        thread.start();
        */

        // --- thread 3 ---
        /*
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                counter++;
                binding.tvCounter.setText(String.valueOf(counter));
                if (counter < 100) {
                    sendEmptyMessageDelayed(0, 1000);
                }
            }
        };
        handler.sendEmptyMessageDelayed(0, 1000);*/

        // --- thread 4 ---
        /*backgroundHandlerThread = new HandlerThread("BackgroundHandlerThread");
        backgroundHandlerThread.start();

        backgroundHandler = new Handler(backgroundHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Message msgMain = new Message();
                msgMain.arg1 = msg.arg1 + 1;
                mainHandler.sendMessage(msgMain);
            }
        };

        mainHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                binding.tvCounter.setText(String.valueOf(msg.arg1));
                if (msg.arg1 < 100) {
                    Message msgBack = new Message();
                    msgBack.arg1 = msg.arg1;
                    backgroundHandler.sendMessageDelayed(msgBack, 1000);
                }
            }
        };

        Message msgBack = new Message();
        msgBack.arg1 = 0;
        backgroundHandler.sendMessageDelayed(msgBack, 1000);
        */

        // --- thread 5 ---
        sampleSsyncTAsk = new SampleSsyncTAsk();
        sampleSsyncTAsk.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0, 100);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance (Fragment level's variables) State here
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance (Fragment level's variables) State here
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //thread.interrupt();
        //backgroundHandlerThread.quit();
        sampleSsyncTAsk.cancel(true);
    }

    class SampleSsyncTAsk extends AsyncTask<Integer, Float, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... params) {
            int start = params[0]; // 0
            int end = params[1];   // 100
            for (int i = start; i < end; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return false;
                }
                publishProgress(i + 0.0f);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Float... values) {
            // Run on Main Thread
            super.onProgressUpdate(values);
            float progress = values[0];
            binding.tvCounter.setText(progress + "%");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            // Run on Main Thread
            super.onPostExecute(aBoolean);

        }
    }
}