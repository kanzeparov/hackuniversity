package come.manager.direct.hackuniversity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import come.manager.direct.hackuniversity.model.Event;
import come.manager.direct.hackuniversity.model.Module;
import come.manager.direct.hackuniversity.org.web3j.sample.Ticket;

public class EventInfo extends AppCompatActivity {
    public final AsyncDownLoad asyncDownLoad= new AsyncDownLoad();
    Event event1;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        String[] event = getIntent().getStringArrayExtra("event");
         event1 = getIntent().getParcelableExtra("eventdat");

        TextView city = findViewById(R.id.city_event);
        TextView name = findViewById(R.id.name_event);
        progressBar = findViewById(R.id.progress_bar);
        TextView val1 = findViewById(R.id.result_vlad);
        TextView val2 = findViewById(R.id.result_fio);
        TextView val3 = findViewById(R.id.result_id_ticket);
        TextView val4 = findViewById(R.id.result_date);
        TextView val5 = findViewById(R.id.result_email);
        TextView val6 = findViewById(R.id.result_price);

        Button button1 = findViewById(R.id.open_btn);
        Button button2 = findViewById(R.id.send_button);
        Button buttonShow = findViewById(R.id.show_btn);

        ImageView imageView = findViewById(R.id.letter_status);
//0 item.getStatus(),
//1 item.getCity(),
//2 item.getDate(),
//3 item.getEmail(),
//4 item.getFio(),
//5 item.getTicketId(),
//6 item.getPrice(),
//7 item.getOwner(),
//8 item.getName(),
//9 item.getImage()
        city.setText(event[1]);
        name.setText(event[8]);
        val1.setText(event[7]);
        val2.setText(event[4]);
        val3.setText(event[5]);
        val4.setText(event[2]);
        val5.setText(event[3]);
        val6.setText(event[6]);

        if (event[0].equals("true")) {
            imageView.setImageResource(R.drawable.open_letter);
          button1.setEnabled(false);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    MyDialogFragment myDialogFragment = new MyDialogFragment();
                    myDialogFragment.show(getSupportFragmentManager(), "dialog");
                }
            });
            button2.setText("Найти");
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            buttonShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),  ImageActivity.class);
                    intent.putExtra("status",true);
                    startActivity(
                            intent
                    );
                }
            });
        } else {
            imageView.setImageResource(R.drawable.close_letter);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    MyDialogFragment myDialogFragment = new MyDialogFragment();
                    myDialogFragment.show(getSupportFragmentManager(), "dialog");
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragmentMy myDialogFragment = new DialogFragmentMy();
                    myDialogFragment.show(getSupportFragmentManager(), "dialog");
                }
            });
            buttonShow.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),  ImageActivity.class);
                    intent.putExtra("status",false);
                    startActivity(
                            intent
                    );
                }
            });
        }

    }

    @SuppressLint("ValidFragment")
    public static class MyDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String title = "Предупреждение!";
            String message = "Вы уверены, что хотите это сделать?";
            String button1String = "Да";
            String button2String = "Нет";

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(title);  // заголовок
            builder.setMessage(message); // сообщение
            builder.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(getActivity(), "Вы сделали правильный выбор, подождите",
                            Toast.LENGTH_LONG).show();

                    dismiss();
                    try {
                        new AsyncDownLoad().execute(((EventInfo)getActivity()).event1).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getActivity().finish();
                    startActivity(new Intent(getActivity(),PinActivity.class));

                }
            });
            builder.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(getActivity(), "Возможно вы правы", Toast.LENGTH_LONG)
                            .show();
                }
            });
            builder.setCancelable(true);

            return builder.create();
        }
    }
    @SuppressLint("ValidFragment")
    private static class DialogFragmentMy extends DialogFragment {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_signin, null);
            Button buttonSend = view.findViewById(R.id.send);
            Button buttonCancel = view.findViewById(R.id.cancel);
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Отмена",
                            Toast.LENGTH_LONG).show();
                    dismiss();
                }
            });

            buttonSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Отправить",
                            Toast.LENGTH_LONG).show();
                    dismiss();
                }
            });
            builder.setView(view);
            // Остальной код
            return builder.create();
        }
    }



    static class AsyncDownLoad extends AsyncTask<Event, Void, Void> {

        public String jsonUTC =
                "{\"version\":3,\"id\":\"c9590b3f-e2eb-4a3f-96d1-0e5dcd304f7c\",\"address\":\"20cf3a494baacae2a74bf81b0e55970b756baa70\",\"Crypto\":{\"ciphertext\":\"650c7e4f2f80ae80426a8e3ea4d1cab891beea3854f010e816d9e2e7d4396984\",\"cipherparams\":{\"iv\":\"110a91cc184227022877ed6b0931cfd1\"},\"cipher\":\"aes-128-ctr\",\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"salt\":\"ee158b8d1c315b6c6a777bd114896051e4e06e68e506f3df9ba4b556ce89e730\",\"n\":8192,\"r\":8,\"p\":1},\"mac\":\"2cec2b9449980cca057a62cdcd284d499f96a533093c559eb60132af61483747\"}}";



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Event... voids) {


            Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/1p6X1Vby9WW11tNcTTg0"));
            BigInteger _price = BigInteger.ONE;
            _price = BigInteger.valueOf(10);
                String address = voids[0].getOwner();
                BigInteger _steps = BigInteger.ONE;
                _steps = BigInteger.valueOf(3);
                // We then need to load our Ethereum wallet file
                // FIXME: Generate a new wallet file using the web3j command line tools https://docs.web3j.io/command_line.html
                Credentials credentials1 = Credentials.create("9c215ede75b688ce2f30372140068c815b78b2eedfe8bd9af04d8d7fddd8ef2e");

                Ticket contract1 =
                        Ticket.load(address, web3j, credentials1, ManagedTransaction.GAS_PRICE,
                                Contract.GAS_LIMIT);
            try {
                String s = contract1.Who_owner().send();
                contract1.Open().send().toString();
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }


}
