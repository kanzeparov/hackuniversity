package come.manager.direct.hackuniversity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.bouncycastle.math.raw.Mod;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import come.manager.direct.hackuniversity.model.Event;
import come.manager.direct.hackuniversity.model.Module;
import come.manager.direct.hackuniversity.org.web3j.sample.Ticket;

public class PinActivity extends AppCompatActivity{
    public static final String TAG = "PinLockView";

    private PinLockView mPinLockView;
    private ProgressBar progressBar;
    private IndicatorDots mIndicatorDots;

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            Log.d(TAG, "Pin complete: " + pin);

          //  if(pin.equals("1111")) {
            new AsyncDownLoadSmart().execute(pin);

            System.out.println();
        }

        @Override
        public void onEmpty() {
            Log.d(TAG, "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pin);

        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);
        //mPinLockView.setCustomKeySet(new int[]{2, 3, 1, 5, 9, 6, 7, 0, 8, 4});
        //mPinLockView.enableLayoutShuffling();

        mPinLockView.setPinLength(4);
        mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.white));

        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);
    }

    class AsyncDownLoad extends AsyncTask<Module[], Void, ArrayList<Event>> {

        public String jsonUTC =
                "{\"version\":3,\"id\":\"c9590b3f-e2eb-4a3f-96d1-0e5dcd304f7c\",\"address\":\"20cf3a494baacae2a74bf81b0e55970b756baa70\",\"Crypto\":{\"ciphertext\":\"650c7e4f2f80ae80426a8e3ea4d1cab891beea3854f010e816d9e2e7d4396984\",\"cipherparams\":{\"iv\":\"110a91cc184227022877ed6b0931cfd1\"},\"cipher\":\"aes-128-ctr\",\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"salt\":\"ee158b8d1c315b6c6a777bd114896051e4e06e68e506f3df9ba4b556ce89e730\",\"n\":8192,\"r\":8,\"p\":1},\"mac\":\"2cec2b9449980cca057a62cdcd284d499f96a533093c559eb60132af61483747\"}}";



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            mIndicatorDots.setVisibility(View.GONE);
            mPinLockView.setVisibility(View.GONE);
        }

        @Override
        protected ArrayList<Event> doInBackground(Module[]... voids) {








            Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/1p6X1Vby9WW11tNcTTg0"));
            BigInteger _price = BigInteger.ONE;
            ArrayList<Event> events = new ArrayList<>();
            _price = BigInteger.valueOf(10);
            for(int i = 0; i < voids[0].length; i++) {
                String address = voids[0][i].getSmartcontract();
                BigInteger _steps = BigInteger.ONE;
                _steps = BigInteger.valueOf(3);
                // We then need to load our Ethereum wallet file
                // FIXME: Generate a new wallet file using the web3j command line tools https://docs.web3j.io/command_line.html
                Credentials credentials1 = Credentials.create("9c215ede75b688ce2f30372140068c815b78b2eedfe8bd9af04d8d7fddd8ef2e");
                Log.d(TAG,"Response string: " +  credentials1.getEcKeyPair().getPublicKey().toString());

                Ticket contract1 =
                        Ticket.load(address, web3j, credentials1, ManagedTransaction.GAS_PRICE,
                                Contract.GAS_LIMIT);
                Event event = new Event();
                try {

                    String date = contract1.Date().send();
                    event.setDate(date);

                    String name = contract1.Name().send();
                    event.setName(name);

                    String owner = contract1.Person_info().send();
                    event.setFio(owner);

                    String city = contract1.Place().send();
                    event.setCity(city);

                    String id = contract1.Ticket_ID().send();
                    event.setTicketId(id);

                    event.setEmail("info@info.ru");

                    event.setImage(R.drawable.logo+"");
                    event.setPrice("1200");
                    event.setOwner(address);
                    Boolean status = contract1.Is_active().send();
                    Boolean status2 = !status;
                    event.setStatus(status2.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                events.add(event);

            }





            return events;
        }

        @Override
        protected void onPostExecute(ArrayList<Event> aVoid) {
            super.onPostExecute(aVoid);
            finish();
            Intent intent = new Intent(getApplicationContext(), ActivityWithWebView.class);
            intent.putParcelableArrayListExtra("events",aVoid);
            startActivity(
                    intent
            );
        }
    }



    class AsyncDownLoadSmart extends AsyncTask<String, Void, Module[]> {
        String myURL = "http://savetickets.ibisolutions.ru/gen_json.php?id=";

       @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            mIndicatorDots.setVisibility(View.GONE);
            mPinLockView.setVisibility(View.GONE);
        }

        @Override
        protected Module[] doInBackground(String... voids) {


            String s = "";
            try {
                s = doGet(myURL+voids[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Module[] modules = gson.fromJson(s, Module[].class);
            return modules;
        }

        @Override
        protected void onPostExecute(Module[] modules) {
            super.onPostExecute(modules);
            new AsyncDownLoad().execute(modules);

        }
    }

    public static String doGet(String url)
            throws Exception {

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0" );
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connection.setRequestProperty("Content-Type", "application/json");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = bufferedReader.readLine()) != null) {
            response.append(inputLine);
        }
        bufferedReader.close();

//      print result
        Log.d(TAG,"Response string: " + response.toString());


        return response.toString();
    }
}
